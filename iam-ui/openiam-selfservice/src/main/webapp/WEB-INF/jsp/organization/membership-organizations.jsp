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
		<title> ${titleOrganizatioName} -
            <c:choose>
				<c:when test="${requestScope.type eq 'childorganizations'}">
                    <fmt:message key="openiam.ui.common.child.organizations"/>
				</c:when>
				<c:when test="${requestScope.type eq 'parentorganizations'}">
                    <fmt:message key="openiam.ui.common.parent.organizations"/>
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
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/selfservice/organization/organization.membership.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.OrganizationId = "${requestScope.organization.id}";
			OPENIAM.ENV.OrganizationTypeId = "${requestScope.organization.organizationTypeId}";
			OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.organization.id}";
			OPENIAM.ENV.EntitlementType = "${requestScope.type}";
			
			<c:choose>
				<c:when test="${requestScope.type eq 'childorganizations'}">
					OPENIAM.ENV.Text.EmptyChildren = <fmt:message key="openiam.ui.selfservice.organization.empty.children" />;
					OPENIAM.ENV.Text.SearchDialogTitle = <fmt:message key="openiam.ui.selfservice.organization.search.dialog.title" />;
					OPENIAM.ENV.Text.EmptySearch = <fmt:message key="openiam.ui.selfservice.organization.empty.search" />;
                    OPENIAM.ENV.childClassification=null;
                    <c:if test="${! empty requestScope.organization.organizationTypeId}">
                		OPENIAM.ENV.childClassification="${requestScope.organization.organizationTypeId}";
                	</c:if>
				</c:when>
				<c:when test="${requestScope.type eq 'parentorganizations'}">
					OPENIAM.ENV.Text.EmptyChildren = <fmt:message key="openiam.ui.selfservice.organization.empty.parent" />;
                    OPENIAM.ENV.parentClassification=null;
                    <c:if test="${! empty requestScope.organization.organizationTypeId}">
            			OPENIAM.ENV.parentClassification="${requestScope.organization.organizationTypeId}";
            		</c:if>
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${requestScope.type eq 'childorganizations'}">
                    <fmt:message key="openiam.ui.selfservice.organization.child.organizations.organization" />: ${requestScope.organization.name}
				</c:when>
				<c:when test="${requestScope.type eq 'parentorganizations'}">
                    <fmt:message key="openiam.ui.selfservice.organization.parent.organizations.organization" />: ${requestScope.organization.name}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			<div id="usermenu">
				<li>
					<a <c:if test="${requestScope.type eq 'childorganizations'}">class="active"</c:if> href="membership-organizations.html?id=${requestScope.organization.id}&type=childorganizations"><fmt:message key="openiam.ui.common.child.organizations" /></a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'parentorganizations'}">class="active"</c:if> href="membership-organizations.html?id=${requestScope.organization.id}&type=parentorganizations"><fmt:message key="openiam.ui.common.parent.organizations" /></a>
				</li>
			</div>
		</div>
		<div class="frameContentDivider">
			<div id="entitlementsContainer"></div>
		</div>
		<div id="dialog"></div>
	</body>
</html>