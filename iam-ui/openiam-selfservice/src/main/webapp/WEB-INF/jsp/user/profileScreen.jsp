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

<c:set var="pageTitle" scope="request"></c:set>
<c:choose>
	<c:when test="${requestScope.pageType eq 'SELF_REGISTRATION'}">
		<c:set var="pageTitle" scope="request">
			<spring:message code="openiam.ui.selfservice.ui.template.self.registration"/>
		</c:set>	
	</c:when>
	<c:when test="${requestScope.pageType eq 'NEW_USER_NO_APPROVER'}">
		<c:set var="pageTitle" scope="request">
			<spring:message code="openiam.ui.selfservice.ui.template.new.user.no.approver"/>
		</c:set>
	</c:when>
	<c:when test="${requestScope.pageType eq 'NEW_USER_WITH_APPROVER'}">
		<c:set var="pageTitle" scope="request">
			<spring:message code="openiam.ui.selfservice.ui.template.new.user.with.approver"/>
		</c:set>	
	</c:when>
	<c:when test="${requestScope.pageType eq 'EDIT_USER'}">
		<c:set var="pageTitle" scope="request">
			<spring:message code="openiam.ui.selfservice.ui.template.edit.user" arguments="${requestScope.theUser.displayName}"/>
		</c:set>	
	</c:when>
	<c:when test="${requestScope.pageType eq 'EDIT_PROFILE'}">
		<c:set var="pageTitle" scope="request">
			<spring:message code="openiam.ui.selfservice.ui.template.edit.profile" />
		</c:set>
	</c:when>
</c:choose>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - ${requestScope.pageTitle}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/css/selfservice/edit.profile.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery.cookies.2.2.0.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/uiTemplate/ui.template.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.form.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/orghierarchy/organization.hierarchy.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/selfservice/edit.profile.bootstrap.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/selfservice/new.user.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/role.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/group.search.js"></script>
		
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.user.id}">"id=${requestScope.user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.UITEMPLATE = <c:choose><c:when test="${! empty requestScope.template}">${requestScope.template}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.emailList = <c:choose><c:when test="${! empty requestScope.emailList}">${requestScope.emailList}</c:when><c:otherwise>[]</c:otherwise></c:choose>;
			OPENIAM.ENV.phoneList = <c:choose><c:when test="${! empty requestScope.phoneList}">${requestScope.phoneList}</c:when><c:otherwise>[]</c:otherwise></c:choose>;
			OPENIAM.ENV.addressList = <c:choose><c:when test="${! empty requestScope.addressList}">${requestScope.addressList}</c:when><c:otherwise>[]</c:otherwise></c:choose>;
			OPENIAM.ENV.supportsEmailCreation = ${! empty requestScope.pageTemplate.uiFields['USER_EMAIL_CREATABLE']};
			OPENIAM.ENV.supportsAddressCreation = ${! empty requestScope.pageTemplate.uiFields['USER_ADDRESSES_CREATABLE']};
			OPENIAM.ENV.supportsPhoneCreation = ${! empty requestScope.pageTemplate.uiFields['USER_PHONES_CREATABLE']};
			OPENIAM.ENV.OrganizationHierarchy = <c:choose><c:when test="${! empty requestScope.orgHierarchy}">${requestScope.orgHierarchy}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.EmailTypes = ${requestScope.emailTypes};
			OPENIAM.ENV.PhoneTypes = ${requestScope.phoneTypes};
			OPENIAM.ENV.AddressTypes = ${requestScope.addressTypes};
			OPENIAM.ENV.UserId = "${requestScope.user.id}";
			OPENIAM.ENV.EmailTypeMap =${requestScope.emailTypeMap};
			OPENIAM.ENV.PhoneTypeMap =${requestScope.phoneTypeMap};
			OPENIAM.ENV.AddressTypeMap =${requestScope.addressTypeMap};
			OPENIAM.ENV.OrgParamList = <c:choose><c:when test="${! empty requestScope.orgList}">${requestScope.orgList}</c:when><c:otherwise>[]</c:otherwise></c:choose>;
			OPENIAM.ENV.EmailRequired = <c:choose><c:when test="${! empty requestScope.pageTemplate.uiFields['USER_EMAIL_CREATABLE']}">${requestScope.pageTemplate.uiFields['USER_EMAIL_CREATABLE'].required}</c:when><c:otherwise>false</c:otherwise></c:choose>;
			OPENIAM.ENV.PhoneRequired = <c:choose><c:when test="${! empty requestScope.pageTemplate.uiFields['USER_PHONES_CREATABLE']}">${requestScope.pageTemplate.uiFields['USER_PHONES_CREATABLE'].required}</c:when><c:otherwise>false</c:otherwise></c:choose>;
			OPENIAM.ENV.AddressRequired = <c:choose><c:when test="${! empty requestScope.pageTemplate.uiFields['USER_ADDRESSES_CREATABLE']}">${requestScope.pageTemplate.uiFields['USER_ADDRESSES_CREATABLE'].required}</c:when><c:otherwise>false</c:otherwise></c:choose>;
            OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
			OPENIAM.ENV.CurrentOrgId=<c:choose><c:when test="${! empty requestScope.currentOrgId}">"${requestScope.currentOrgId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<c:choose>
			<c:when test="${requestScope.addBranding}">
				<c:import url="/WEB-INF/jsp/common/branded.page.jsp" />
			</c:when>
			<c:otherwise>
				<c:import url="/WEB-INF/jsp/user/profile.screen.template.jsp" />
			</c:otherwise>
		</c:choose>
		<div id="editDialog"></div>
		<div id="dialog"></div>
	</body>
</html>