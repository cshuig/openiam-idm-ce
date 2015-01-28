package reports

import org.openiam.base.OrderConstants
import org.openiam.base.ws.SortParam
import org.openiam.idm.searchbeans.AuditLogSearchBean
import org.openiam.idm.searchbeans.UserSearchBean
import org.openiam.idm.srvc.audit.constant.AuditAction
import org.openiam.idm.srvc.audit.dto.AuditLogTarget
import org.openiam.idm.srvc.audit.dto.IdmAuditLog
import org.openiam.idm.srvc.audit.dto.IdmAuditLogCustom
import org.openiam.idm.srvc.audit.service.AuditLogService
import org.openiam.idm.srvc.lang.dto.Language
import org.openiam.idm.srvc.org.dto.Organization
import org.openiam.idm.srvc.org.service.OrganizationDataService
import org.openiam.idm.srvc.report.dto.ReportQueryDto
import org.openiam.idm.srvc.report.service.ReportDataSetBuilder
import org.openiam.idm.srvc.report.dto.ReportDataDto
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext

import org.openiam.idm.srvc.report.dto.ReportTable
import org.openiam.idm.srvc.report.dto.ReportRow
import org.openiam.idm.srvc.report.dto.ReportRow.ReportColumn
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.mngsys.service.ManagedSystemService
import org.openiam.idm.srvc.mngsys.domain.ManagedSysEntity
import org.openiam.idm.srvc.res.service.ResourceService
import org.openiam.idm.srvc.res.domain.ResourceEntity
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.role.domain.RoleEntity
import org.openiam.idm.srvc.grp.service.GroupDataService
import org.openiam.idm.srvc.grp.domain.GroupEntity

import java.text.DateFormat

class AuditReport implements ReportDataSetBuilder {

    private ApplicationContext context

    final static String NOT_FOUND = '[ Not found ]'
    final static Language DEFAULT_LANGUAGE = new Language(id: 1)
    final static int USERS_LIMIT = 10000

    private OrganizationDataService organizationService
    private UserDataService userDataService
    private ManagedSystemService managedSystemService
    private ResourceService resourceService
    private GroupDataService groupDataService
    private RoleDataService roleDataService
    private AuditLogService auditLogService

    DateFormat dateFormat

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    ReportDataDto getReportData(ReportQueryDto query) {

        def listParameter = query.getParameterValue("GET_VALUES")
        if (listParameter) {
            def table = listValues(listParameter)
            return packReportTable(table)
        }

        def String startDate = query.getParameterValue("ACTION_DATETIME_START")
        def String endDate = query.getParameterValue("ACTION_DATETIME_END")
        def String logId = query.getParameterValue("LOG_ID")
        def String userId = query.getParameterValue("USER_ID")
        def String orgId = query.getParameterValue("ORG_ID")
        def String action = query.getParameterValue("ACTION_ID")
        def String outputTable = query.getParameterValue("TABLE")

        if (outputTable == "DETAILS" && !logId) {
            return null;
        }

        organizationService = context.getBean("orgManager") as OrganizationDataService
        userDataService = context.getBean("userManager") as UserDataService
        resourceService = context.getBean(ResourceService.class)
        managedSystemService = context.getBean(ManagedSystemService.class)
        groupDataService = context.getBean(GroupDataService.class)
        roleDataService = context.getBean(RoleDataService.class)
        auditLogService = context.getBean(AuditLogService.class)

        if (outputTable == "HEAD") {
            ReportTable reportTable = new ReportTable()
            reportTable.name = "head"
            ReportRow row = new ReportRow()
            if (userId) {
                row.column.add(new ReportColumn('USER_FULLNAME', getUserFullName(getUser(userId))))
            }
            if (orgId) {
                def Organization bean = organizationService.getOrganizationLocalized(orgId, null, DEFAULT_LANGUAGE)
                row.column.add(new ReportColumn('ORGANIZATION', bean?.name ?: NOT_FOUND))
            }
            reportTable.row.add(row)
            return packReportTable(reportTable)
        }

        def List<IdmAuditLog> auditLogs = [];

        if (logId) {
            def IdmAuditLog log = auditLogService.findById(logId)
            if (log != null) {
                auditLogs = [log] as List
            }
        } else {
            def AuditLogSearchBean logSearchBean = new AuditLogSearchBean()
            def Date sDate = startDate ? dateFormat.parse(startDate) : null
            def Date eDate = endDate ? dateFormat.parse(endDate) : null

	        if (startDate) {
                logSearchBean.from = sDate
            }
	        if (endDate) {
                logSearchBean.to = eDate
            }
	        if (action) {
                logSearchBean.action = action
            }
	        if (userId) {
                logSearchBean.userId = userId
            }
            logSearchBean.sortBy = [new SortParam(OrderConstants.ASC, "timestamp")] as List
            auditLogs = auditLogService.findBeans(logSearchBean, 0, 1000)
        }


        ReportTable reportTable = new ReportTable()
        switch (outputTable) {
            case "MAIN":
                reportTable.name = "AuditReportTable"
                def userIds = null
                if (orgId) {
                    def searchBean = new UserSearchBean()
                    searchBean.addOrganizationId(orgId)
                    userIds = userDataService.findBeans(searchBean, 0, USERS_LIMIT)?.id
                }
                for(IdmAuditLog a : auditLogs) {
                    if (!orgId || (a.userId in userIds)) {
                        addMainTableRow(a, reportTable, "")
                    }
                }
                break;
            case "DETAILS":
                reportTable.name = "AuditReportDetails"
                for(IdmAuditLog a : auditLogs) {
                    addDetailsRow(a, reportTable, "")
                    for(IdmAuditLog child: a.childLogs) {
                        addDetailsRow(child, reportTable, child.id)
                    }
                }
                break;
            case "ATTRIBUTE":
                reportTable.name = "AuditReportAttribute"
                for(IdmAuditLog a : auditLogs) {
                    addAttributeRow(a, reportTable)
                }
                break;
            case "TARGET":
                reportTable.name = "AuditReportTarget"
                for(IdmAuditLog a : auditLogs) {
                    addTargetRow(a, reportTable)
                }
                break;
        }
        return packReportTable(reportTable)
    }

    private void addMainTableRow(IdmAuditLog a, ReportTable reportTable, String parentActionId) {
        ReportRow row = new ReportRow()
        row.column.add(new ReportColumn('LOG_ID', a.id))
        row.column.add(new ReportColumn('ACTION_ID', a.action))
        row.column.add(new ReportColumn('PARENT_ACTION_ID', parentActionId))
        row.column.add(new ReportColumn('ACTION_STATUS', a.result))
        row.column.add(new ReportColumn('ACTION_DATETIME', a.timestamp.toString()))
        row.column.add(new ReportColumn('LOGIN_ID', a.principal))
        row.column.add(new ReportColumn('HOST', a.nodeIP))
        row.column.add(new ReportColumn('CLIENT_ID', a.clientIP))
        row.column.add(new ReportColumn('TARGET_SYSTEM_ID', a.managedSysId))
        row.column.add(new ReportColumn('TARGET_SYSTEM_NAME', getManagedSys(a.managedSysId)?.name))
        row.column.add(new ReportColumn('SESSION_ID', a.sessionID))

        row.column.add(new ReportColumn('REQ_USER_ID', a.userId))
		row.column.add(new ReportColumn('REQ_FULLNAME', getUserFullName(getUser(a.userId))))

        def String targetUserId = getTargetUser(a.targets)
        row.column.add(new ReportColumn('TARGET_USER_ID', targetUserId))
        row.column.add(new ReportColumn('TARGET_FULLNAME', getUserFullName(getUser(targetUserId))))

        row.column.add(new ReportColumn('DEPT_ID', ""))
        row.column.add(new ReportColumn('DEPT_NAME', ""))
        reportTable.row.add(row)
    }

    private void addDetailsRow(IdmAuditLog a, ReportTable reportTable, String parentActionId) {
        addMainTableRow(a, reportTable, parentActionId)
    }

    private static void addAttributeRow(IdmAuditLog a, ReportTable reportTable) {
        for(IdmAuditLogCustom rec : a.customRecords) {
            if ("EXCEPTION" == rec.key) {
                continue;
            }
            ReportRow row = new ReportRow()
            row.column.add(new ReportColumn('LOG_ID', a.id))
            row.column.add(new ReportColumn('ATTR_DATETIME', rec.timestamp.toString()))
            row.column.add(new ReportColumn('ATTR_KEY', rec.key))
            row.column.add(new ReportColumn('ATTR_VALUE', rec.value))
            reportTable.row.add(row)
        }
    }

    private void addTargetRow(IdmAuditLog a, ReportTable reportTable) {
        for(AuditLogTarget rec in a.targets) {
            ReportRow row = new ReportRow()
            row.column.add(new ReportColumn('LOG_ID', a.id))
            row.column.add(new ReportColumn('ID', rec.id))
            row.column.add(new ReportColumn('TARGET_TYPE', rec.targetType))
            row.column.add(new ReportColumn('TARGET_ID', rec.targetId))
            switch (rec.targetType) {
                case "USER":
                    UserEntity user = userDataService.getUser(rec.targetId)
                    if (user) {
                        def String info = String.format("%s, %s", getUserFullName(user), user.type.id)
                        row.column.add(new ReportColumn('TARGET_INFO', info))
                    }
                    break;
                case "RESOURCE":
                    ResourceEntity resource = resourceService.findResourceById(rec.targetId)
                    if (resource) {
                        def String info = String.format("%s (%s), %S",
                            resource.name,
                            resource.resourceType.id,
                            resource.isPublic ? "Is public" : "Is not public")
                        row.column.add(new ReportColumn('TARGET_INFO', info))
                    }
                    break;
                case "ROLE":
                    RoleEntity role = roleDataService.getRole(rec.targetId)
                    if (role) {
                        row.column.add(new ReportColumn('TARGET_INFO', role.name))
                    }
                    break;
                case "GROUP":
                    GroupEntity group = groupDataService.getGroup(rec.targetId)
                    if (group) {
                        def String info = group.name
                        if (group.description) {
                            info += " (" + group.description + ")"
                        }
                        row.column.add(new ReportColumn('TARGET_INFO', info))
                    }
                    break;
            }
            reportTable.row.add(row)
        }
    }

    private static String getTargetUser(Set<AuditLogTarget> targets) {
        for(AuditLogTarget target in targets) {
            if ("USER" == target.targetType) {
                return target.targetId
            }
        }
        return null;
    }

    private Map<String, UserEntity> userCache = new HashMap<String, UserEntity>()
    private Map<String, ManagedSysEntity> manSysCache = new HashMap<String, ManagedSysEntity>()

    private UserEntity getUser(String userId) {
        if (userCache.containsKey(userId)) {
            return userCache.get(userId)
        }
		UserEntity user = userDataService.getUser(userId)
        userCache.put(userId, user)
        return user;
	}

    private ManagedSysEntity getManagedSys(String managedSysId) {
        if (manSysCache.containsKey(managedSysId)) {
            return manSysCache.get(managedSysId)
        }
        ManagedSysEntity manSys = managedSystemService.getManagedSysById(managedSysId)
        manSysCache.put(managedSysId, manSys)
        return manSys;
    }

    private static String getUserFullName(UserEntity user) {
        return user ? (user.firstName +
            (user.middleInit ? ' ' + user.middleInit : '') +
            (user.lastName ? ' ' + user.lastName : '')) : '';
    }

    /**
     * Fills the output table with the requested enumeration. Used to fill a parameter listbox in the report
     * @param parameter the required enumeration
     * @return the ReportDataDto filled with the enumeration
     */
    private static ReportTable listValues(String parameter) {
        ReportTable reportTable = new ReportTable()
        reportTable.name = "values"

        def ReportRow row;
        switch(parameter) {
            case "ACTION":
                for(AuditAction action : AuditAction.values()) {
                    row = new ReportRow()
                    row.column.add(new ReportColumn('NAME', action.value()))
                    reportTable.row.add(row)
                }
                break;
        }
        return reportTable;
    }

    private static ReportDataDto packReportTable(ReportTable reportTable) {
        return new ReportDataDto(
            tables : [ reportTable ] as List<ReportTable>
        )
    }
}
