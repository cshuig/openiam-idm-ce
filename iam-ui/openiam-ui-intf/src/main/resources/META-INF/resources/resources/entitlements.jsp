<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>

<c:set var="pageTitle">
	<c:choose>
		<c:when test="${requestScope.type eq 'childresources'}">
			<fmt:message key="openiam.ui.shared.resource.children" />
		</c:when>
		<c:when test="${requestScope.type eq 'parentresources'}">
			<fmt:message key="openiam.ui.shared.resource.parents" />
		</c:when>
		<c:when test="${requestScope.type eq 'groups'}">
			<fmt:message key="openiam.ui.shared.resource.groups" />
		</c:when>
		<c:when test="${requestScope.type eq 'roles'}">
			<fmt:message key="openiam.ui.shared.resource.roles" />
		</c:when>
		<c:when test="${requestScope.type eq 'users'}">
			<fmt:message key="openiam.ui.shared.resource.users" />
		</c:when>
		<c:otherwise>
					
		</c:otherwise>
	</c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - ${pageTitle}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
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
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/resource/resource.entitlements.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.form.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/group.search.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/resource.search.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/role.search.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ResourceId = "${requestScope.resource.id}";
			OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.resource.id}";
			OPENIAM.ENV.ResourceTypeId = "${requestScope.resource.resourceType.id}";
			OPENIAM.ENV.EntitlementType = "${requestScope.type}";
			OPENIAM.ENV.PreventOnClick = ${not fn:contains(pageContext.request.contextPath, 'webconsole')};
			OPENIAM.ENV.Text = {
				
			};
			
			<c:choose>
				<c:when test="${requestScope.type eq 'childresources'}">
					OPENIAM.ENV.Text.EmptyChildren = localeManager["openiam.ui.shared.resource.children.empty"];
				</c:when>
				<c:when test="${requestScope.type eq 'parentresources'}">
					OPENIAM.ENV.Text.EmptyChildren = localeManager["openiam.ui.shared.resource.parents.empty"];
				</c:when>
				<c:when test="${requestScope.type eq 'groups'}">
					OPENIAM.ENV.Text.EmptyChildren = localeManager["openiam.ui.shared.resource.groups.empty"];
				</c:when>
				<c:when test="${requestScope.type eq 'roles'}">
					OPENIAM.ENV.Text.EmptyChildren = localeManager["openiam.ui.shared.resource.roles.empty"];
				</c:when>
				<c:when test="${requestScope.type eq 'users'}">
					OPENIAM.ENV.Text.EmptyChildren = localeManager["openiam.ui.shared.resource.users.empty"];
					OPENIAM.ENV.Text.UserTableDescription = localeManager["openiam.ui.shared.resource.user.select.to.entitle"];
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${requestScope.type eq 'childresources'}">
					<fmt:message key="openiam.ui.shared.resource.children.title" />: ${requestScope.resource.name}
				</c:when>
				<c:when test="${requestScope.type eq 'parentresources'}">
					<fmt:message key="openiam.ui.shared.resource.parent.title" />: ${requestScope.resource.name}
				</c:when>
				<c:when test="${requestScope.type eq 'groups'}">
					<fmt:message key="openiam.ui.shared.resource.groups.title" />: ${requestScope.resource.name}
				</c:when>
				<c:when test="${requestScope.type eq 'roles'}">
					<fmt:message key="openiam.ui.shared.resource.roles.title" />: ${requestScope.resource.name}
				</c:when>
				<c:when test="${requestScope.type eq 'users'}">
					<fmt:message key="openiam.ui.shared.resource.users.title" />: ${requestScope.resource.name}
				</c:when>
				<c:otherwise>
							
				</c:otherwise>
			</c:choose>
			<div id="usermenu">
				<li>
					<a <c:if test="${requestScope.type eq 'childresources'}">class="active"</c:if> href="resourceEntitlements.html?id=${requestScope.resource.id}&type=childresources">
						<fmt:message key="openiam.ui.shared.resource.children" />
					</a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'parentresources'}">class="active"</c:if> href="resourceEntitlements.html?id=${requestScope.resource.id}&type=parentresources">
						<fmt:message key="openiam.ui.shared.resource.parents" />
					</a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'groups'}">class="active"</c:if> href="resourceEntitlements.html?id=${requestScope.resource.id}&type=groups">
						<fmt:message key="openiam.ui.shared.resource.groups" />
					</a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'roles'}">class="active"</c:if> href="resourceEntitlements.html?id=${requestScope.resource.id}&type=roles">
						<fmt:message key="openiam.ui.shared.resource.roles" />
					</a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'users'}">class="active"</c:if> href="resourceEntitlements.html?id=${requestScope.resource.id}&type=users">
						<fmt:message key="openiam.ui.shared.resource.users" />
					</a>
				</li>
			</div>
		</div>
		<div class="frameContentDivider">
			<div id="entitlementsContainer"></div>
		</div>
		<div id="searchResultsContainer"></div>
		<div id="editDialog"></div>
		<div id="dialog"></div>
	</body>
</html>