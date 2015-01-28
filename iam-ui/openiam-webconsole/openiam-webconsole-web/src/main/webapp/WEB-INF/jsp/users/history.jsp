<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.user.history.page.title"/></title>
	
	    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
	    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
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
	    
	    
	    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/datetimepicker/jquery-ui-timepicker-addon.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/log/log.search.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/user/user.history.js"></script>
	
	    <script type="text/javascript">
	        OPENIAM = window.OPENIAM || {};
	        OPENIAM.ENV = window.OPENIAM.ENV || {};
	        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
	        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
	        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.user.id}">"id=${requestScope.user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
	        OPENIAM.ENV.Text = {
	        	EmptyText : localeManager["openiam.ui.user.history.empty.result"],
	        	DialogTitle : localeManager["openiam.ui.user.history.dialog.title"]
	        };
	        OPENIAM.ENV.UserId = "${requestScope.user.id}";
	        OPENIAM.ENV.TargetUserId = "${requestScope.user.id}";
            OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
	    </script>
	</head>
	<body>
	    <div id="title" class="title">
            <fmt:message key="openiam.ui.user.history"/> : ${requestScope.user.displayName}
	      <%--  <div id="usermenu">
	        <fmt:message key="openiam.ui.user.history"/> : ${requestScope.user.displayName}
	        <div id="usermenu">
				<li>
					<a <c:if test="${requestScope.type eq 'source'}">class="active"</c:if> href="userHistory.html?id=${requestScope.user.id}&type=source"><fmt:message key="openiam.ui.user.history.source.event"/></a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'target'}">class="active"</c:if> href="userHistory.html?id=${requestScope.user.id}&type=target"><fmt:message key="openiam.ui.user.history.target.event"/></a>
				</li>
			</div> --%>
	    </div>
	    <div class="frameContentDivider">
	       	<div id="entitlementsContainer"></div>
	    </div>
	</body>
</html>