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
    <link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />

    <openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/bulkOperations/bulkOperations.search.group.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/bulkOperations/bulkOperations.search.organization.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/bulkOperations/bulkOperations.search.role.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/bulkOperations/bulkOperations.search.resource.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/bulkOperations/bulkOperations.choose.operations.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.page = 0;
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${not empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
        OPENIAM.ENV.ObjectOperations = <c:choose><c:when test="${not empty requestScope.objectOperationsAsJson}">${requestScope.objectOperationsAsJson}</c:when><c:otherwise>null</c:otherwise></c:choose>;
    </script>
</head>
<body>
<div id="title" class="title">
    <fmt:message key="openiam.ui.webconsole.user.bulk.operations.page.title"/>
</div>

<form:form id="operationForm" action="${flowExecutionUrl}" method="post" commandName="operationBean">
    <div id="formContainer" class="frameContentDivider">
    <table align="center" cellpadding="8px">
    <tbody>
    <c:if test="${flowRequestContext.messageContext.hasErrorMessages()}">
        <tr>
            <td colspan="2">
                <div class="alert alert-error">
                    <c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
                        <p class="error">${message.text}</p>
                    </c:forEach>
                </div>
            </td>
        </tr>
    </c:if>
    <c:if test="${not empty objectTypes}">
        <tr id="chooseObjectRow">
            <td>
                <form:label path="objectType"><fmt:message key="openiam.ui.common.choose.object" /></form:label>
            </td>
            <td>
                <form:select path="objectType" items="${objectTypes}" />
            </td>
        </tr>
    </c:if>

    </tbody>
    <tfoot>
    <tr>
        <td colspan="2">
            <ul class="formControls">
                <li class="rightBtn" id="addOperation" style="display: none;">
                    <form:button class="redBtn" type="submit" name="_eventId_addOperation"><fmt:message key="openiam.ui.common.add.operation" /></form:button>
                </li>
            </ul>
        </td>
    </tr>
    </tfoot>
    </table>

    </div>

    <p><fmt:message key="openiam.ui.user.selected" />: ${bulkOperationBean.userIds.size()}</p>

    <div style="" id="resultsArea" class="">
        <table width="100%" cellspacing="1" class="yui">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"></th>
                <th class="header"><fmt:message key="openiam.ui.common.object" /></th>
                <th class="header"><fmt:message key="openiam.ui.common.name" /></th>
                <th class="header"><fmt:message key="openiam.ui.common.operation" /></th>
            </tr>
            </thead>
            <tbody>
            <c:choose >
                <c:when test="${not empty bulkOperationBean.operations}">
                    <c:forEach var="operation" varStatus="status" items="${bulkOperationBean.operations}">
                        <tr class="${(status.index%2==0) ? 'even' : 'odd'}">
                            <td class="center">
                                <input type="checkbox" name="operationIds" value="${status.index}" />
                            </td>
                            <td>${operation.objectType}</td>
                            <td>${operation.objectName}</td>
                            <td class="center">${operation.operation.label}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr class="even">
                        <td class="empty" colspan="4"><fmt:message key="openiam.ui.bulk.no.operation" /></td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>

    <br/>

    <ul class="formControls">
        <li class="leftBtn">
            <input class="redBtn" type="submit" name="_eventId_back" value="&laquo; <fmt:message key='openiam.ui.user.choose.user'/>" />
        </li>
        <c:if test="${not empty bulkOperationBean.operations}">
        <li class="leftBtn">
            <input class="redBtn" type="submit" name="_eventId_deleteOperations" value="&laquo; <fmt:message key='openiam.ui.common.delete.selected'/>" />
        </li>
        </c:if>
        <li class="leftBtn">
            <input class="whiteBtn" type="submit" name="_eventId_cancel" value="<fmt:message key='openiam.ui.button.cancel' />" />
        </li>
        <li class="rightBtn">
            <input class="redBtn" type="submit" name="_eventId_start" value="<fmt:message key='openiam.ui.bulk.start.provisioning' />" />
        </li>
    </ul>

</form:form>

<div id="dialog"></div>
</body>
</html>