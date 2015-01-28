<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.selfservice.user.directory.lookup"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/css/selfservice/selfservice.my.info.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
          type="text/css"/>
    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css"/>
    <openiam:overrideCSS/>
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript"
            src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/selfservice/user/dir.lookup.js"></script>

    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = null;
        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
    </script>
</head>
<body>
<div class="title">
    <fmt:message key="openiam.ui.selfservice.user.directory.lookup"/>
</div>
<div class="frameContentDivider" id="formContainer">
    <form id="userSearchForm" name="userSearchForm">
        <table cellspacing="1" class="yui" width="100%">
            <thead>
            <tr>
                <td colspan="4">
                    <div class="info center"><fmt:message
                            key="openiam.ui.common.user.search.algorithm.explanation"/></div>
                </td>
            </tr>
            <tr>
                <td class="filter">
                    <label for="lastName"><fmt:message key='openiam.ui.webconsole.user.lastName'/>:</label>
                </td>
                <td class="filter">
                    <input id="lastName" name="lastName" maxlength="30" size="30" type="text"/>
                </td>
                <td class="filter">
                    <label for="firstName"><fmt:message key='openiam.ui.webconsole.user.firstName'/>:</label>
                </td>
                <td class="filter">
                    <input id="firstName" name="firstName" maxlength="30" size="30" type="text"/>
                </td>
            </tr>
            <tr>
                <td class="filter">
                    <label for="email"><fmt:message key='openiam.ui.webconsole.user.email'/>:</label>
                </td>
                <td class="filter">
                    <input id="email" name="email" maxlength="50" size="30" type="text"/>
                </td>
                <td class="filter">
                    <label for="phoneCode"><fmt:message key='openiam.ui.webconsole.user.phone'/>:</label>
                </td>
                <td class="filter">
                    <input id="phoneCode" name="phoneCode" maxlength="3" size="3" type="text"/>
                    <input id="phoneNumber" name="phoneNumber" maxlength="10" size="21" type="text"/>
                </td>
            </tr>
            <%--<tr>--%>
            <%--<td class="filter">--%>
            <%--<label for="organizationId"><fmt:message key='openiam.ui.webconsole.user.organization' />:</label>--%>
            <%--</td>--%>
            <%--<td class="filter">--%>
            <%--<select id="organizationId" name="organizationId">--%>
            <%--<option value="">Select an Organization...</option>--%>
            <%--<c:forEach var="organization" items="${requestScope.organizationList}">--%>
            <%--<option value="${organization.id}">${organization.name}</option>--%>
            <%--</c:forEach>--%>
            <%--</select>--%>
            <%--</td>--%>
            <%--<td class="filter">--%>
            <%--</td>--%>
            <%--<td class="filter">--%>
            <%--</td>--%>
            <%--</tr>--%>
            </thead>
            <tfoot id="searchFormButtons">
            <tr>
                <td class="filter" colspan="4">
                    <input type="submit" value="<fmt:message key='openiam.ui.common.search' />" class="redBtn">
                    <a id="cleanUserSearchForm" class="whiteBtn" href="javascript:void(0);"><fmt:message
                            key="openiam.ui.button.clear"/></a>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
<div id="userResultsArea"></div>
</body>
</html>