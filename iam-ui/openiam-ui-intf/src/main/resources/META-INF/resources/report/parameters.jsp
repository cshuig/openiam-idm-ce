
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>

<%--@elvariable id="reportInfo" type="org.openiam.idm.srvc.report.dto.ReportInfoDto"--%>

<c:set var="errorMessage">
	<c:if test="${! empty requestScope.errorToken}">
		<spring:message code="${requestScope.errorToken.error.messageName}" arguments="${requestScope.errorToken.params}" />
	</c:if>
</c:set>

<c:set var="successMessage">
	<c:if test="${! empty requestScope.successToken}">
		<spring:message code="${requestScope.successToken.message.messageName}" />
	</c:if>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.common.report" /></title>
	    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
	    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    	<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
        <openiam:overrideCSS />

        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/group.search.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/role.search.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/resource.search.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.form.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/report/report.param.js"></script>

		<script type="text/javascript">
            OPENIAM = window.OPENIAM || {};
            OPENIAM.ENV = window.OPENIAM.ENV || {};
            OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.ReportName = "${requestScope.reportInfo.reportName}";
            OPENIAM.ENV.ReportBean = ${requestScope.reportAsJSON};
            OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
        </script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.ui.report.parameters"/>: ${requestScope.reportInfo.reportName}
		</div>

        <div class="frameContentDivider">
            <div id="parametersContainer"></div>
            <div class="frameSubcontentDivider">
            <table class="fields-grid" cellpadding="8" align="center" style="min-width:500px;">
                <tbody>
                <c:forEach var="parameter" items="${requestScope.reportParameters}">
                    <c:set var="labelClass" value="${parameter.isRequired?'required':''}" />
                    <tr>
                        <td>
                            <label for="${parameter.id}" class="${labelClass}">${parameter.caption}</label>
                        </td>
                        <c:choose>
                            <c:when test="${not empty parameter.metaTypeId}">
                                <c:choose>
                                    <c:when test="${parameter.metaTypeId eq 'GROUP'}">
                                        <td></td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.shared.group.search'/>" class="redBtn searchGroupBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'USER'}">
                                        <td></td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.common.search.users'/>" class="redBtn searchUsersBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'ROLE'}">
                                        <td>
                                            <input type="text" id="input${parameter.id}" class="full rounded" placeholder="<fmt:message key='openiam.ui.shared.type.role.name'/>" autocomplete="off" />
                                        </td><td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.shared.role.search'/>" class="redBtn searchRoleBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'RESOURCE'}">
                                        <td>
                                            <input type="text" id="input${parameter.id}" class="full rounded" placeholder="<fmt:message key='openiam.ui.shared.resource.name.type'/>" autocomplete="off" />
                                        </td><td>
                                        <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.shared.resource.search'/>" class="redBtn searchResBtn" />
                                    </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'SUPERVISOR'}">
                                        <td></td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.shared.supervisor.search'/>" class="redBtn searchSupervisorBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'RESOURCE_TYPE'}">
                                        <td>
                                            <select id="input${parameter.id}" class="rounded">
                                                <option value=""><fmt:message key='openiam.ui.common.please.select'/></option>
                                                <c:forEach var="bean" items="${requestScope.resourceTypes}">
                                                    <option value="${bean.id}">${bean.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.report.add.parameter'/>" class="redBtn addBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'ORGANIZATION'}">
                                        <td>
                                            <input type="text" id="input${parameter.id}" class="full rounded" placeholder="<fmt:message key='openiam.ui.shared.organization.type.name'/>" autocomplete="off" />
                                        </td><td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.shared.organization.search'/>" class="redBtn searchOrgBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'MANAGED_SYSTEM'}">
                                        <td>
                                            <select id="input${parameter.id}" class="rounded">
                                                <option value=""><fmt:message key='openiam.ui.common.please.select'/></option>
                                                <c:forEach var="bean" items="${requestScope.managedSystems}">
                                                    <option value="${bean.id}">${bean.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.report.add.parameter'/>" class="redBtn addBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'ACTION'}">
                                        <td>
                                            <input type="text" id="input${parameter.id}" class="full rounded" placeholder="<fmt:message key='openiam.ui.shared.log.action.type.name'/>" autocomplete="off" />
                                        </td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.shared.log.action.search'/>" class="redBtn searchActionBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'RISK'}">
                                        <td>
                                            <select id="input${parameter.id}" class="rounded">
                                                <option value=""><fmt:message key='openiam.ui.common.please.select'/></option>
                                                <c:forEach var="bean" items="${requestScope.riskList}">
                                                    <option value="${bean.id}">${bean.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.report.add.parameter'/>" class="redBtn addBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'USER_STATUS'}">
                                        <td>
                                            <select id="input${parameter.id}" class="rounded">
                                                <option value=""><fmt:message key='openiam.ui.common.please.select'/></option>
                                                <c:forEach var="bean" items="${requestScope.userStatuses}">
                                                    <option value="${bean.id}">${bean.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.report.add.parameter'/>" class="redBtn addBtn" />
                                        </td>
                                    </c:when>
                                    <c:when test="${parameter.metaTypeId eq 'USER_SEC_STATUS'}">
                                        <td>
                                            <select id="input${parameter.id}" class="rounded">
                                                <option value=""><fmt:message key='openiam.ui.common.please.select'/></option>
                                                <c:forEach var="bean" items="${requestScope.secondaryStatuses}">
                                                    <option value="${bean.id}">${bean.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.report.add.parameter'/>" class="redBtn addBtn" />
                                        </td>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:when test="${parameter.typeName eq 'DATE'}">
                                <td>
                                    <input type="text" id="input${parameter.id}" class="full rounded date" readonly="readonly" />
                                </td><td>
                                    <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.report.add.parameter'/>" class="redBtn addBtn" />
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <input type="text" id="input${parameter.id}" class="full rounded" />
                                </td><td>
                                    <input type="submit" id="${parameter.id}" value="<fmt:message key='openiam.ui.report.add.parameter'/>" class="redBtn addBtn" />
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </div>
            <div id="searchResultsContainer" style="display:none"></div>
        </div>
        <div id="dialog"></div>
	</body>
</html>