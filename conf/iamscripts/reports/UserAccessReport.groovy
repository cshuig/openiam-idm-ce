package reports

import org.openiam.authmanager.model.UserEntitlementsMatrix
import org.openiam.authmanager.service.AuthorizationManagerAdminService
import org.openiam.idm.searchbeans.UserSearchBean
import org.openiam.idm.srvc.audit.service.AuditLogService
import org.openiam.idm.srvc.grp.service.GroupDataService
import org.openiam.idm.srvc.lang.dto.Language
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.org.dto.Organization
import org.openiam.idm.srvc.org.service.OrganizationDataService
import org.openiam.idm.srvc.report.dto.ReportDataDto
import org.openiam.idm.srvc.report.dto.ReportQueryDto
import org.openiam.idm.srvc.report.dto.ReportRow
import org.openiam.idm.srvc.report.dto.ReportRow.ReportColumn
import org.openiam.idm.srvc.report.dto.ReportTable
import org.openiam.idm.srvc.report.service.ReportDataSetBuilder
import org.openiam.idm.srvc.res.service.ResourceService
import org.openiam.idm.srvc.role.dto.Role
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.service.UserDataService
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext

public class UserAccessReport implements ReportDataSetBuilder {

    final static String NOT_FOUND = '[ Not found ]'
    final static int USERS_LIMIT = 10000
    final static Language DEFAULT_LANGUAGE = new Language(id: 1)
    final static String DIRECT_RESOURCES = 'Directly assigned'
    final static String CONTAINER_GROUP = 'Group'
    final static String CONTAINER_ROLE = 'Role'

    private def role2ResCache = new HashMap<String, Set<String>>()
    private def group2ResCache = new HashMap<String, Set<String>>()
    private def resCache = new HashMap<String, ResBean>()

    private String risk = null
    private String manSysResourceId = null

    private ApplicationContext context
    private UserDataService userDataService
    private OrganizationDataService organizationService
    private RoleDataService roleService
    private GroupDataService groupService
    private ResourceService resourceService
    private AuditLogService auditLogService
    private ManagedSystemWebService managedSystemService
    private AuthorizationManagerAdminService authManagerService

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext
    }

    @Override
    ReportDataDto getReportData(ReportQueryDto query) {
        return processDataRequest(query)
    }

    ReportDataDto processDataRequest(query) {

        println "TerminatingAccounts data source, request: " + query.queryParams.values().join(", ")
        userDataService = context.getBean("userManager") as UserDataService
        organizationService = context.getBean("orgManager") as OrganizationDataService
        roleService = context.getBean(RoleDataService.class)
        groupService = context.getBean(GroupDataService.class)
        auditLogService = context.getBean(AuditLogService.class)
        managedSystemService = context.getBean(ManagedSystemWebService.class)
        authManagerService = context.getBean(AuthorizationManagerAdminService.class)
        resourceService = context.getBean(ResourceService.class)

        def String orgId = query.getParameterValue("ORG_ID")
        def String roleId = query.getParameterValue("ROLE_ID")
        def String userId = query.getParameterValue("USER_ID")
        risk = query.getParameterValue("RISK")?.toUpperCase()
        def String manSysId = query.getParameterValue("MANAGED_SYS_ID")
        def String[] resTypeIds = query.getParameterValues("RES_TYPE_IDS")

        def ReportTable reportTable = new ReportTable()

        def isHeadRequest = query.getParameterValue("TABLE") == "HEAD"
        if (isHeadRequest) {

            def ReportRow row = new ReportRow()
            reportTable.name = "head"

            if (orgId) {
                def Organization bean = organizationService.getOrganizationLocalized(orgId, null, DEFAULT_LANGUAGE)
                row.column.add(new ReportColumn('ORGANIZATION', bean?.name ?: NOT_FOUND))
            }
            if (roleId) {
                def Role bean = roleService.getRoleDTO(roleId)
                row.column.add(new ReportColumn('ROLE', bean?.name ?: NOT_FOUND))
            }
            if (userId) {
                def searchBean = new UserSearchBean(deepCopy: false, key: userId)
                def users = userDataService.findBeans(searchBean)
                if (users) {
                    def user = users.get(0)
                    def fullName = user.userAttributes.get("FULL_NAME")?.value ?:
                            user.firstName + ' ' + (user.middleInit ? user.middleInit + ' ' : '') + user.lastName
                    row.column.add(new ReportColumn('USER', user ? fullName : NOT_FOUND))
                }
            }
            if (risk) {
                row.column.add(new ReportColumn('RISK', risk?.toUpperCase()))
            }
            if (manSysId) {
                def ManagedSysDto bean = managedSystemService.getManagedSys(manSysId)
                row.column.add(new ReportColumn('MANAGED_SYSTEM', bean?.name ?: NOT_FOUND))
            }
            if (!EmptyMultiValue(resTypeIds)) {
                def names = ""
                resTypeIds.each { resTypeId ->
                    def name = resourceService.findResourceTypeById(resTypeId)?.description
                    if (name) {
                        names += (names ? ", " : "") + name
                    }
                }
                row.column.add(new ReportColumn('RES_TYPES', names ?: NOT_FOUND))
            }
            reportTable.row.add(row)

        } else {

            reportTable.setName("details")

            def messages = validateParameters(orgId, roleId, userId, risk, manSysId) as String[]
            if (messages) {
                for(def msg : messages) {
                    def ReportRow row = new ReportRow()
                    row.column.add(new ReportColumn('ERROR', msg))
                    reportTable.row.add(row)
                }
            } else {

                if (manSysId) {
                    def ManagedSysDto managedSystem = managedSystemService.getManagedSys(manSysId)
                    manSysResourceId = managedSystem?.resourceId
                }

                def searchBean = new UserSearchBean(deepCopy: false)
                if (userId) {
                    searchBean.key = userId
                } else {
                    if (orgId) searchBean.organizationIdSet = [orgId] as Set
                    if (roleId) searchBean.roleIdSet = [roleId] as Set
                }

                def users = userDataService.findBeans(searchBean, 0, USERS_LIMIT)

                users.each { UserEntity user ->

                    // retrieve resources entitled to the user
                    def includeMenu = !EmptyMultiValue(resTypeIds) && resTypeIds.find {it=='MENU_ITEM'}
                    def entitlementBeans = getEntitlementBeans(user.id, includeMenu)
                    entitlementBeans.each { eb ->
                        eb.resources.each { res ->
                            if (EmptyMultiValue(resTypeIds) || resTypeIds.find {it==res.typeId}) {
                                def row = new ReportRow()
                                row.column.add(new ReportColumn('FULL_NAME', user.userAttributes.get("FULL_NAME")?.value ?:
                                        user.firstName + ' ' + (user.middleInit ? user.middleInit + ' ' : '') + user.lastName))
                                row.column.add(new ReportColumn('EMPLOYEE_ID', user.employeeId))
                                row.column.add(new ReportColumn('CONTAINER_TYPE', eb.type))
                                row.column.add(new ReportColumn('CONTAINER_NAME', eb.name))
                                row.column.add(new ReportColumn('RES_NAME', res.name))
                                row.column.add(new ReportColumn('RES_TYPE', res.typeName))
                                row.column.add(new ReportColumn('RISK', res.risk))
                                reportTable.row.add(row)
                            }
                        }
                    }
                }
            }
        }
        return packReportTable(reportTable)
    }

    static def validateParameters(String orgId, String roleId, String userId, String risk, String managedSysId) {
        def violations = [] as List
        if (!userId && !orgId && !roleId)
            violations.add "Your have to specify at least one of the next parameters: 'Organization', 'Role', 'User'"
        if (userId && (orgId || roleId))
            violations.add "Parameters 'Organization' and 'Role' should not be set if the parameter 'User' is specified"
        return violations
    }

    static ReportDataDto packReportTable(ReportTable reportTable)
    {
        ReportDataDto reportDataDto = new ReportDataDto()
        List<ReportTable> reportTables = new ArrayList<ReportTable>()
        reportTables.add(reportTable)
        reportDataDto.setTables(reportTables)
        return reportDataDto
    }

    List<EntitlementBean> getEntitlementBeans(String userId, boolean includeMenu) {

        def matrix = authManagerService.getUserEntitlementsMatrix(userId)
        def result = [] as List<EntitlementBean>
        if (matrix.resourceIds) {
            def directResources = new EntitlementBean(type: DIRECT_RESOURCES)
            directResources.resources += getResourceBeans(matrix.resourceIds, includeMenu)
            result += directResources
        }

        matrix.roleIds.each { roleId ->
            def resourceIds = getResourcesForRole(roleId, matrix)
            if (resourceIds) {
                def roleBean = getRoleBean(roleId)
                roleBean.resources += getResourceBeans(resourceIds, includeMenu)
                result += roleBean
            }
        }

        matrix.groupIds.each { groupId ->
            def resourceIds = getResourcesForGroup(groupId, matrix)
            if (resourceIds) {
                def groupBean = getGroupBean(groupId)
                groupBean.resources += getResourceBeans(resourceIds, includeMenu)
                result += groupBean
            }
        }

        return result
    }

    Set<String> getResourcesForRole(String roleId, UserEntitlementsMatrix matrix){
        if(!role2ResCache.containsKey(roleId)) {
            def compiledRoles = compileTreeBranch(roleId, matrix.childRoleToParentRoleMap, [] as HashSet)
            compiledRoles += roleId
            def resourcesForRoles = joinCompiledBlocks(compiledRoles, matrix.roleToResourceMap)
            Set<String> allResources = resourcesForRoles + joinTreeBlocks(resourcesForRoles, matrix.childResToParentResMap)
            role2ResCache.put(roleId, allResources)
        }
        return role2ResCache.get(roleId)
    }

    Set<String> getResourcesForGroup(String groupId, UserEntitlementsMatrix matrix){
        if(!group2ResCache.containsKey(groupId)) {
            def compiledRoles = [] as HashSet
            matrix.groupToRoleMap.get(groupId).each { String roleId ->
                compiledRoles += roleId
                compiledRoles += compileTreeBranch(roleId, matrix.childRoleToParentRoleMap, [] as HashSet)
            }
            def compiledGroups = compileTreeBranch(groupId, matrix.childGroupToParentGroupMap, [] as HashSet)
            compiledGroups += groupId

            Set<String> resultIds = joinCompiledBlocks(compiledGroups, matrix.groupToResourceMap)
            compiledRoles += joinCompiledBlocks(compiledGroups, matrix.groupToRoleMap)
            resultIds += joinCompiledBlocks(compiledRoles, matrix.roleToResourceMap)
            resultIds += joinTreeBlocks(resultIds, matrix.childResToParentResMap)
            group2ResCache.put(groupId, resultIds)
        }
        return group2ResCache.get(groupId)
    }

    List<ResBean> getResourceBeans(Set<String> resIds, boolean includeMenu) {
        def resBeans = [] as List<ResBean>
        def notCachedIds = [] as List<String>
        resIds.each { resId ->
            def res = resCache.get(resId)//getResourceBean(resId)
            if (res) {
                if (resourceMatchesFilter(res, includeMenu)) {
                    resBeans += res
                }
            } else {
                notCachedIds += resId
            }
        }
        // Build new beans for not cached resources
        def resources = resourceService.findResourcesByIds(notCachedIds)
        resources.each { resEntity ->
            def resBean = new ResBean(
                    id: resEntity.id,
                    name: resEntity.coorelatedName ?: resEntity.name,
                    risk: resEntity.risk,
                    typeId: resEntity.resourceType.id,
                    typeName: resEntity.resourceType.displayNameMap?.get(DEFAULT_LANGUAGE.id)?.value)
            resCache.put(resEntity.id, resBean)
            if (resourceMatchesFilter(resBean, includeMenu)) {
                resBeans += resBean
            }
        }
        return resBeans
    }

    EntitlementBean getRoleBean(String roleId) {
        def role = roleService.getRole(roleId)
        return new EntitlementBean(
                name: role?.name ?: NOT_FOUND,
                type: CONTAINER_ROLE)
    }

    EntitlementBean getGroupBean(String groupId) {
        def group = groupService.getGroup(groupId)
        return new EntitlementBean(
                name: group?.name ?: NOT_FOUND,
                type: CONTAINER_GROUP)
    }

    boolean resourceMatchesFilter(ResBean resource, boolean includeMenu) {
        if (includeMenu || resource.typeId != 'MENU_ITEM') {
            if (!risk || resource.risk == risk) {
                if (!manSysResourceId || (resource.typeId == 'MANAGED_SYS' && resource.id == manSysResourceId)) {
                    return true
                }
            }
        }
        return false
    }

    static Set<String> joinCompiledBlocks(def blockIds, def compiledMap) {
        def resultIds = [] as HashSet
        blockIds.each { blockId ->
            if (compiledMap.get(blockId)) {
                resultIds += compiledMap.get(blockId)
            }
        }
        return resultIds
    }

    static Set<String> joinTreeBlocks(def blockIds, def treeMap) {
        def resultIds = [] as HashSet
        blockIds.each { final String blockId ->
            resultIds += compileTreeBranch(blockId, treeMap, [] as HashSet)
        }
        return resultIds;
    }

    static Set<String> compileTreeBranch(String branchId, def treeMap, def visitedIds) {
        def resultIds = [] as HashSet
        if(!(branchId in visitedIds)) {
            visitedIds += branchId
            treeMap.get(branchId).each { String childId ->
                resultIds += childId
                resultIds += compileTreeBranch(childId, treeMap, visitedIds)
            }
        }
        return resultIds
    }

    private static boolean EmptyMultiValue(String[] values) {
        return !values || (values.length == 1 && !values[0])
    }

    static class EntitlementBean {
        // A name declared with no access modifier generates a private field with public getter and setter
        String type
        String name
        Set<ResBean> resources = [] as Set<ResBean>
    }

    static class ResBean {
        String id
        String typeId
        String typeName
        String name
        String risk
    }

}
