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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.user.impersonate"/></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
        <openiam:overrideCSS />

        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
        
        <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.form.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/impersonate/impersonate.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.page = 0;
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";

		</script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.ui.user.impersonate"/>
		</div>
		<div id="formContainer" class="frameContentDivider">
			
		</div>
		<div id="resultsArea" style="display:none">
		</div>
	</body>
</html>