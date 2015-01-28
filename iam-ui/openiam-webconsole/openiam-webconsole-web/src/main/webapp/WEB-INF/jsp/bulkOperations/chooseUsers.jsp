<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.user.bulk.operations.page.title"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />

    <openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/bulkOperations/bulkOperations.search.group.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/bulkOperations/bulkOperations.search.organization.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/bulkOperations/bulkOperations.search.role.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>

    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.page = 0;
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
    </script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#selectAll").click(function() {
                var checked = $(this).is(':checked');
                $("#resultsArea [name='userIds']").prop('checked', checked);
            });

            $("#cleanUserSearchForm").click(function() {
                $("#userSearchForm").find("input, select").not(":input[type=submit]").val('');
            });

            $("select#pageSize").change(function() {
                $("#searchUsers").click();
            });

            $("#searchFilter").multiselect({
                header : false,
                click: function(event, ui) {
                    var $el = $("#userSearchFormTable #" + ui.value);
                    if(ui.checked) {
                        $el.show();
                    } else {
                        $el.find("input, select").val('');
                        $el.hide();
                    }
                },
                noneSelectedText : localeManager["openiam.ui.user.add.more.search.criteria"]
            });

        });
    </script>
</head>
<body>
<div id="title" class="title">
    <fmt:message key="openiam.ui.webconsole.user.bulk.operations.page.title"/>
</div>
    <div id="formContainer" class="frameContentDivider">
        <form:form action="${flowExecutionUrl}" method="post" commandName="bulkOperationBean" id="userSearchForm">

        <form:select id="searchFilter" path="userSearchBean.searchCriteria" autocomplete="off" multiple="true" items="${searchCriteriaItems}" />
        <table width="100%" cellspacing="1" class="yui" id="userSearchFormTable">
            <thead>
            <tr>
                <td colspan="2">
                    <div style="position:relative;" class="info center"><fmt:message key="openiam.ui.common.user.search.algorithm.explanation" /></div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div style="width: 100%;">
                        <div class="userSearchCell" id="lastName">
                            <form:label path="userSearchBean.lastName"><fmt:message key="openiam.ui.webconsole.user.lastName" /></form:label>
                            <form:input path="userSearchBean.lastName" size="23" maxlength="30" autocomplete="off" />
                        </div>
                        <div class="userSearchCell" id="emailAddress">
                            <form:label path="userSearchBean.emailAddress"><fmt:message key="openiam.ui.webconsole.user.email" /></form:label>
                            <form:input path="userSearchBean.emailAddress" size="23" maxlength="50" autocomplete="off" />
                        </div>
                        <div class="userSearchCell" id="principalName">
                            <form:label path="userSearchBean.principalName"><fmt:message key="openiam.ui.webconsole.user.principal" /></form:label>
                            <form:input path="userSearchBean.principalName" size="23" maxlength="200" autocomplete="off" />
                        </div>
                        <div class="userSearchCell" id="employeeId">
                            <form:label path="userSearchBean.employeeId"><fmt:message key="openiam.ui.webconsole.user.employeeId" /></form:label>
                            <form:input path="userSearchBean.employeeId" size="23" maxlength="32" autocomplete="off" />
                        </div>
                        <div class="userSearchCell" id="userStatus" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('userStatus')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.userStatus"><fmt:message key="openiam.ui.webconsole.user.status" /></form:label>
                            <form:select path="userSearchBean.userStatus" autocomplete="off" items="${userStatuses}" />
                        </div>
                        <div class="userSearchCell" id="accountStatus" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('accountStatus')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.accountStatus"><fmt:message key="openiam.ui.webconsole.user.accountStatus" /></form:label>
                            <form:select path="userSearchBean.accountStatus" autocomplete="off" items="${accountStatuses}" />
                        </div>
                        <div class="userSearchCell" id="application" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('application')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.resourceIds"><fmt:message key="openiam.ui.common.application" /></form:label>
                            <form:select path="userSearchBean.resourceIds" multiple="false" autocomplete="off" items="${applications}" />
                        </div>
                        <div class="userSearchCell" id="organization" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('organization')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.organizationName"><fmt:message key="openiam.ui.webconsole.user.organization" /></form:label>
                            <c:choose>
                                <c:when test="${not empty bulkOperationBean.userSearchBean.organizationIds}">
                                    <c:forEach var="o" items="${bulkOperationBean.userSearchBean.organizationIds}">
                                        <input type="hidden" id="organizationId" name="userSearchBean.organizationIds" value="${o}" />
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" id="organizationId" name="userSearchBean.organizationIds" value="" />
                                </c:otherwise>
                            </c:choose>
                            <form:input id="organizationName" path="userSearchBean.organizationName" readonly="true" />
                            <a id="searchOrganization" href="javascript:void(0);" class="icon-link search16"></a>
                            <a id="clearOrganization" href="javascript:void(0);" class="icon-link delete16"></a>
                        </div>
                        <div class="userSearchCell" id="role" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('role')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.roleName"><fmt:message key="openiam.ui.webconsole.user.role" /></form:label>
                            <c:choose>
                                <c:when test="${not empty bulkOperationBean.userSearchBean.roleIds}">
                                    <c:forEach var="r" items="${bulkOperationBean.userSearchBean.roleIds}">
                                        <input type="hidden" id="roleId" name="userSearchBean.roleIds" value="${r}" />
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" id="roleId" name="userSearchBean.roleIds" value="" />
                                </c:otherwise>
                            </c:choose>
                            <form:input id="roleName" path="userSearchBean.roleName" readonly="true" />
                            <a id="searchRole" href="javascript:void(0);" class="icon-link search16"></a>
                            <a id="clearRole" href="javascript:void(0);" class="icon-link delete16"></a>
                        </div>
                        <div class="userSearchCell" id="group" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('group')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.groupName"><fmt:message key="openiam.ui.webconsole.user.group" /></form:label>
                            <c:choose>
                                <c:when test="${not empty bulkOperationBean.userSearchBean.groupIds}">
                                    <c:forEach var="g" items="${bulkOperationBean.userSearchBean.groupIds}">
                                        <input type="hidden" id="groupId" name="userSearchBean.groupIds" value="${g}" />
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" id="groupId" name="userSearchBean.groupIds" value="" />
                                </c:otherwise>
                            </c:choose>
                            <form:input id="groupName" path="userSearchBean.groupName" readonly="true" />
                            <a id="searchGroup" href="javascript:void(0);" class="icon-link search16"></a>
                            <a id="clearGroup" href="javascript:void(0);" class="icon-link delete16"></a>
                        </div>
                        <div class="userSearchCell" id="employeeType" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('employeeType')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.employeeType"><fmt:message key="openiam.ui.common.employee.type" /></form:label>
                            <form:select path="userSearchBean.employeeType" autocomplete="off" items="${employeeTypes}" />
                        </div>
                        <div class="userSearchCell" id="maidenName" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('maidenName')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.maidenName"><fmt:message key="openiam.ui.user.maiden" /></form:label>
                            <form:input path="userSearchBean.maidenName" size="23" maxlength="30" autocomplete="off" />
                        </div>
                        <div class="userSearchCell" id="jobCode" <c:if test="${not bulkOperationBean.userSearchBean.searchCriteria.contains('jobCode')}">style="display: none"</c:if> >
                            <form:label path="userSearchBean.jobCode"><fmt:message key="openiam.ui.webconsole.meta.type.grouping.JOB_CODE" /></form:label>
                            <form:select path="userSearchBean.jobCode" autocomplete="off" items="${jobCodes}" />
                        </div>
                    </div>
                </td>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <td colspan="2" class="filter">
                    <input id="searchUsers" type="submit" class="redBtn" name="_eventId_searchUsers" value="<fmt:message key='openiam.ui.common.search' />" />
                    <a href="javascript:void(0);" class="whiteBtn" id="cleanUserSearchForm"><fmt:message key="openiam.ui.common.clear" /></a>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>

    <c:if test="${bulkOperationBean.usersNum > 0}">
        <p>Displaying ${bulkOperationBean.startPos} - ${bulkOperationBean.endPos < bulkOperationBean.usersNum ? bulkOperationBean.endPos : bulkOperationBean.usersNum} from ${bulkOperationBean.usersNum} (Selected ${bulkOperationBean.userIds.size()} users)</p>
    </c:if>

    <div style="" id="resultsArea" class="">
        <table width="100%" cellspacing="1" class="yui">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"></th>
                <th class="header"><fmt:message key="openiam.ui.common.name" /></th>
                <th class="header"><fmt:message key="openiam.ui.common.phone.number" /></th>
                <th class="header"><fmt:message key="openiam.ui.common.email.address" /></th>
                <th class="header"><fmt:message key="openiam.ui.user.status" /></th>
                <th class="header"><fmt:message key="openiam.ui.webconsole.user.accountStatus" /></th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty userList}">
                    <c:forEach var="user" varStatus="status" items="${userList}">
                        <tr entityid="${user.id}" class="${(status.index%2==0) ? 'even' : 'odd'}">
                            <td class="center"><input type="checkbox" name="userIds" value="${user.id}" <c:if test="${bulkOperationBean.userIds.contains(user.id)}">checked='true'</c:if> /></td>
                            <td><a href='<c:url value="editUser.html?id=${user.id}"/>' >${user.name}</a></td>
                            <td>${user.phone}</td>
                            <td>${user.email}</td>
                            <td class="center">${user.userStatus}</td>
                            <td class="center">${user.accountStatus}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr class="even">
                        <td class="empty" colspan="6"><fmt:message key="openiam.ui.common.user.search.no.results" /></td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
            <tfoot>
            <tr class="pager">
                <td colspan="6" style="border-right: solid 3px #7f7f7f;">
                    <input type="image" src="/openiam-ui-static/plugins/tablesorter/img/first.png" name="_eventId_firstResults" <c:choose><c:when test="${bulkOperationBean.startPos==1}">disabled="disabled" class="first disabled"</c:when><c:otherwise>class="first"</c:otherwise></c:choose> />
                    <input type="image" src="/openiam-ui-static/plugins/tablesorter/img/prev.png" name="_eventId_prevResults" <c:choose><c:when test="${bulkOperationBean.startPos==1}">disabled="disabled" class="prev disabled"</c:when><c:otherwise>class="prev"</c:otherwise></c:choose> />
                    <input type="text" class="pagedisplay" value="${pageDisplay}">
                    <input type="image" src="/openiam-ui-static/plugins/tablesorter/img/next.png" name="_eventId_nextResults" <c:choose><c:when test="${bulkOperationBean.endPos>=bulkOperationBean.usersNum}">disabled="disabled" class="next disabled"</c:when><c:otherwise>class="next"</c:otherwise></c:choose> />
                    <input type="image" src="/openiam-ui-static/plugins/tablesorter/img/last.png" name="_eventId_lastResults" <c:choose><c:when test="${bulkOperationBean.endPos>=bulkOperationBean.usersNum}">disabled="disabled" class="last disabled"</c:when><c:otherwise>class="last"</c:otherwise></c:choose> />
                    <form:select path="pageSize" items="${pageSizeList}" class="pageSize" />
                </td>
            </tr>
            </tfoot>
        </table>
    </div>

    <br/>

    <ul class="formControls">
        <li class="leftBtn">
            <input class="whiteBtn" type="submit" name="_eventId_cancel" value="<fmt:message key='openiam.ui.button.cancel' />" />
        </li>
        <li class="rightBtn">
            <input class="redBtn" type="submit" name="_eventId_chooseOperations" value="<fmt:message key="openiam.ui.challenge.response.choose.operations" /> &raquo;" />
        </li>
    </ul>&nbsp;

</form:form>
<div id="dialog"></div>

</body>
</html>
