<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>

<c:set var="pageTitle">
    <fmt:message key="openiam.ui.group.bulk.op.select.users" />
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - ${pageTitle}</title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
    <%--<link href="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />--%>

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

    <script type="text/javascript" src="/openiam-ui-static/js/selfservice/group/selectUsers.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.form.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.RequesterId = ${requestScope.requesterId};
        OPENIAM.ENV.GroupId = "${requestScope.group.id}";
        OPENIAM.ENV.GroupName = "${requestScope.group.name}";
    </script>
</head>
<body>
<div class="frameContentDivider">
    <div class="title">
        <fmt:message key="openiam.ui.group.bulk.op"/>: <c:out value="${requestScope.group.name}"/>
    </div>
    <div class="title">
        <fmt:message key="openiam.ui.group.bulk.op.select.operation"/>
    </div>
    <label for="operation"></label>
    <select id="operation" name="operation" class="ctrlElement">
        <option value=""><fmt:message key="openiam.ui.selfservice.text.pleaseselect"/></option>
        <c:forEach items="${operations}" var="op">
            <option value="${op}"><fmt:message key="openiam.ui.bulk.op.${op}"/></option>
        </c:forEach>
    </select>

    <div class="title">
        <fmt:message key="openiam.ui.group.bulk.op.select.users"/>
    </div>
    <br/>
    <div id="selectedUsers"><fmt:message key="openiam.ui.user.selected"/>: 0</div>
    <br/>

    <div id="usersToAdd"></div>
    <div id="searchResultsContainer"></div>
    <div id="usersToDelete"></div>


    <br/>

    <ul class="formControls">
        <li class="leftBtn">
            <a href="groupsBulkOperations.html" class="whiteBtn"><fmt:message key="openiam.ui.button.back"/></a>
        </li>
        <li class="leftBtn">
            <a href="groups.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel"/></a>
        </li>
        <li class="rightBtn">
            <a href="javascript:void(0);" class="redBtn" id="applyBtn"><fmt:message key="openiam.ui.bulk.start.provisioning"/></a>
        </li>
    </ul>

</div>


<div id="editDialog"></div>
<div id="dialog"></div>
</body>
</html>