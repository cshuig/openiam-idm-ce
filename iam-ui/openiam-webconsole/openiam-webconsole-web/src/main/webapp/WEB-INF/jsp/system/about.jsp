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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.about" /></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
	<openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>

    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
    </script>
</head>
<body>

<div class="title">
    <fmt:message key="openiam.ui.webconsole.about.system.information" />
</div>
<div class="frameContentDivider">
<table cellspacing="1" class="yui" width="100%">
    <thead>
    <tr>
        <th class="header"><fmt:message key="openiam.ui.webconsole.about.parameter" /></th>
        <th class="header"><fmt:message key="openiam.ui.webconsole.about.value" /></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${sys.developmentMode}">
        <tr class="even">
            <td><fmt:message key="openiam.ui.webconsole.about.os" /></td>
            <td>${sys.uiOsInfo}</td>
        </tr>
        <tr class="odd">
            <td><fmt:message key="openiam.ui.webconsole.about.java" /></td>
            <td>${sys.uiJavaInfo}</td>
        </tr>
        <tr class="even">
            <td><fmt:message key="openiam.ui.webconsole.about.javamemory" /></td>
            <td>${sys.uiJavaMemInfo}</td>
        </tr>
    </c:if>
    <tr class="odd">
        <td><fmt:message key="openiam.ui.webconsole.about.buildinfo" /></td>
        <td>${sys.uiBuildInfo}</td>
    </tr>
    </tbody>
</table>
</div>
<div class="title">
    <fmt:message key="openiam.ui.webconsole.about.esbsysteminfo" />
</div>
<div class="frameContentDivider">
<table cellspacing="1" class="yui" width="100%">
    <thead>
    <tr>
        <th class="header"><fmt:message key="openiam.ui.webconsole.about.parameter" /></th>
        <th class="header"><fmt:message key="openiam.ui.webconsole.about.value" /></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${sys.developmentMode}">
        <tr class="even">
            <td><fmt:message key="openiam.ui.webconsole.about.os" /></td>
            <td>${sys.esbOsInfo}</td>
        </tr>
        <tr class="odd">
            <td><fmt:message key="openiam.ui.webconsole.about.java" /></td>
            <td>${sys.esbJavaInfo}</td>
        </tr>
        <tr class="even">
            <td><fmt:message key="openiam.ui.webconsole.about.javamemory" /></td>
            <td>${sys.esbJavaMemInfo}</td>
        </tr>
    </c:if>
    <tr class="odd">
        <td><fmt:message key="openiam.ui.webconsole.about.buildinfo" /></td>
        <td>${sys.esbBuildInfo}</td>
    </tr>
    </tbody>
</table>
</div>
</body>
</html>