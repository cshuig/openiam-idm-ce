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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} -
            <c:choose>
				<c:when test="${requestScope.type eq 'children'}">
                    <fmt:message key="openiam.webconsole.organization.type.child" />
				</c:when>
				<c:when test="${requestScope.type eq 'parents'}">
                    <fmt:message key="openiam.webconsole.organization.type.parent" />
				</c:when>
				<c:when test="${requestScope.type eq 'organizations'}">
                    <fmt:message key="openiam.webconsole.organization.type.list.organizations" />
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
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

		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/organization/organization.type.membership.js"></script>
		
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.OrganizationTypeId = "${requestScope.organizationType.id}";
			OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.organizationType.id}";
			OPENIAM.ENV.EntitlementType = "${requestScope.type}";
			OPENIAM.ENV.Text = {
				
			};
			
			<c:choose>
				<c:when test="${requestScope.type eq 'children'}">
					OPENIAM.ENV.Text.EmptyChildren = localeManager["openiam.ui.webconsole.organization.no.children"];
					OPENIAM.ENV.Text.SearchDialogTitle = localeManager["openiam.ui.webconsole.organization.type.search.dialog.title"];
					OPENIAM.ENV.Text.EmptySearch = localeManager["openiam.ui.webconsole.organization.type.empty.search"];
				</c:when>
				<c:when test="${requestScope.type eq 'parents'}">
					OPENIAM.ENV.Text.EmptyChildren = localeManager["openiam.ui.webconsole.organization.type.empty.parent"];
				</c:when>
				<c:when test="${requestScope.type eq 'organizations'}">
					OPENIAM.ENV.Text.EmptyChildren = localeManager["openiam.ui.webconsole.organization.type.no.this.type"];
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${requestScope.type eq 'children'}">
                    <fmt:message key="openiam.ui.webconsole.organization.type.child.org.types" />: ${requestScope.organizationType.name}
				</c:when>
				<c:when test="${requestScope.type eq 'parents'}">
                    <fmt:message key="openiam.ui.webconsole.organization.type.parent.org.types" />: ${requestScope.organizationType.name}
				</c:when>
				<c:when test="${requestScope.type eq 'organizations'}">
                    <fmt:message key="openiam.ui.webconsole.organization.type.org.types" />: ${requestScope.organizationType.name}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			<div id="usermenu">
				<li>
					<a <c:if test="${requestScope.type eq 'children'}">class="active"</c:if> href="organizationTypeMembership.html?id=${requestScope.organizationType.id}&type=children"><fmt:message key="openiam.webconsole.organization.type.child" /></a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'parents'}">class="active"</c:if> href="organizationTypeMembership.html?id=${requestScope.organizationType.id}&type=parents"><fmt:message key="openiam.webconsole.organization.type.parent" /></a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'organizations'}">class="active"</c:if> href="organizationTypeMembership.html?id=${requestScope.organizationType.id}&type=organizations"><fmt:message key="openiam.webconsole.organization.type.list.organizations" /></a>
				</li>
			</div>
		</div>
		<div class="frameContentDivider">
			<div id="entitlementsContainer"></div>
		</div>
		<div id="dialog"></div>
	</body>
</html>