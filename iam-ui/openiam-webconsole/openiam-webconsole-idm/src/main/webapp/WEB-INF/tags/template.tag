<%@tag description="OpenIAM IDM Template 3.0" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="searchScript" fragment="true" %>
<%@attribute name="command" type="org.openiam.ui.web.model.PaginationCommand" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<!DOCTYPE html>
<html>
<head>
    <title>OpenIAM IDM</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
	<openiam:overrideCSS />
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <c:if test="${! empty command}">
        <script type="text/javascript">
        (function() {
            OPENIAM = window.OPENIAM || {};
            OPENIAM.ENV = window.OPENIAM.ENV || {};
            OPENIAM.ENV.totalPages = ${command.total};
            OPENIAM.ENV.page = ${command.page};
            OPENIAM.ENV.count = ${command.count};
            OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>
        })();
        </script>
    </c:if>
    <jsp:invoke fragment="searchScript" />
</head>
<body>

    <jsp:invoke fragment="header"/>

    <jsp:doBody/>

    <jsp:invoke fragment="footer"/>

</body>
</html>