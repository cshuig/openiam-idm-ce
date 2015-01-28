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
				<c:when test="${requestScope.type eq 'roles'}">
                    <fmt:message key="openiam.ui.user.entitlement.role.title"/>
				</c:when>
				<c:when test="${requestScope.type eq 'groups'}">
                    <fmt:message key="openiam.ui.user.entitlement.group.title"/>
				</c:when>
				<c:when test="${requestScope.type eq 'resources'}">
					<fmt:message key="openiam.ui.user.entitlement.resource.title"/>
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/dynatree/skin-vista/ui.dynatree.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole/menu.edit.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole/user.entitlements.view.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />

        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/contextmenu/jquery.contextMenu-custom.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/dynatree/jquery.dynatree-1.2.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/user/user.entitlements.view.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/highlight/jquery.highlight.js"></script>
		
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.UserId = "${requestScope.user.id}";
			OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.user.id}";
			OPENIAM.ENV.ViewType = "${requestScope.type}";
			OPENIAM.ENV.Text = {
					EmptyChildren : localeManager["openiam.ui.entitlements.empty.children"]
			};
		</script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.ui.entitlements.view.user" /> : ${requestScope.user.displayName}
			<div id="usermenu">
				<li>
					<a <c:if test="${requestScope.type eq 'list'}">class="active"</c:if> href="userEntitlementGraph.html?id=${requestScope.user.id}&type=list"><fmt:message key="openiam.ui.user.entitlement.list.view"/></a>
				</li>
				<li>
					<a <c:if test="${requestScope.type eq 'tree'}">class="active"</c:if> href="userEntitlementGraph.html?id=${requestScope.user.id}&type=tree"><fmt:message key="openiam.ui.user.entitlement.tree.view"/></a>
				</li>
			</div>
		</div>
		<c:choose>
			<c:when test="${requestScope.type eq 'list'}">
				<div>
					<input type="text" id="entityAutocomplete" name="name" class="full rounded _input_tiptip" style="display:none" placeholder='<fmt:message key="openiam.ui.user.entitlement.tree.view.filter"/>'
					title='<fmt:message key="openiam.ui.user.entitlement.tree.view.filter.title"/>' />
				</div>
			
				<div id="menu-container">
					<!--
					<div id="tree-key">
						<ul>
							<li>
								<div class="group">
									<label>Group</label>
								</div>
							</li>
							<li>
								<div class="role">
									<label>Role</label>
								</div>
							</li>
							<li>
								<div class="resource">
									<label>Resource</label>
								</div>
							</li>
						</ul>
					</div>
					-->
					<div id="tree">
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<select id="entityType">
					<option value="group"><fmt:message key="openiam.ui.webconsole.user.group"/></option>
					<option value="role"><fmt:message key="openiam.ui.webconsole.user.role"/></option>
					<option value="resource"><fmt:message key="openiam.ui.webconsole.user.resource"/></option>
				</select>
				<input type="text" id="entityAutocomplete" name="name" class="full rounded _input_tiptip" placeholder='<fmt:message key="openiam.ui.user.entitlement.tree.view.filter"/>'
					title='<fmt:message key="openiam.ui.user.entitlement.tree.view.filter.title"/>' />
				<div id="tree">
				</div>
			</c:otherwise>
		</c:choose>
		<div id="dialog"></div>
		<div id="fixedMenuInformationContainer">
			<div id="entitlementInformation" style="display:none">
				<div class="title entitlementInfoTitle"><fmt:message key="openiam.ui.user.entitlement.metadata"/></div>
				<div id="entitlementMetadata">
					<div>
						<label><fmt:message key="openiam.ui.common.id"/> :</label>
						<span class="id"></span>
					</div>
					<div>
						<label><fmt:message key="openiam.ui.common.name"/> :</label>
						<span class="name"></span>
					</div>
					<div>
						<label><fmt:message key="openiam.ui.common.type"/> :</label>
						<span class="type"></span>
					</div>
					<div id="entitlementReasons">
						<label><fmt:message key="openiam.ui.user.entitlement.reason"/> :</label>
						<ul class="reasons">
						
						</ul>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>