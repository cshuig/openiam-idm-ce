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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.webconsole.resource.type.menu.entitlements" /></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/dynatree/skin-vista/ui.dynatree.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole/menu.entitlements.css" rel="stylesheet" type="text/css" />
		
		<link href="/openiam-ui-static/plugins/contextmenu/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />

        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery.cookie.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/contextmenu/jquery.contextMenu-custom.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/dynatree/jquery.dynatree-1.2.2.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/menutree.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/menu/entitlements.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.principalType = "${requestScope.principalType}";
			OPENIAM.ENV.principalId = "${requestScope.principalId}";
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.principalId}">"id=${requestScope.principalId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<c:if test="${! empty requestScope.menuTrees}">
			<div id="title" class="title">
                <fmt:message key="openiam.webconsole.resource.type.menu.entitlements.for" /> ${requestScope.principalType}: ${requestScope.displayName}
			</div>
			<div class="frameContentDivider">
				<div id="legend">
					<ul>
						<li>
							<div class="bubble none"></div>
						</li>
						<li class="label">
							<label><fmt:message key="openiam.webconsole.resource.type.menu.not.entitled" />:</label>
						</li>
						<li>
							<div class="bubble public"></div>
						</li>
						<li class="label">
							<label><fmt:message key="openiam.webconsole.resource.type.menu.public" />:</label>
						</li>
						<li>
							<div class="bubble implicit"></div>
						</li>
						<li class="label">
							<label><fmt:message key="openiam.webconsole.resource.type.menu.implicitly.entitled" />:</label>
						</li>
						<li>
							<div class="bubble explicit"></div>
						</li>
						<li class="label">
							<label><fmt:message key="openiam.webconsole.resource.type.menu.explicitly.entitled" />:</label>
						</li>
						<li>
							<div class="bubble public implicit"></div>
						</li>
						<li class="label">
							<label><fmt:message key="openiam.webconsole.resource.type.menu.implicity.and.public" /></label>
						</li>
						<li>
							<div class="bubble public explicit"></div>
						</li>
						<li class="label">
							<label><fmt:message key="openiam.webconsole.resource.type.menu.explicitly.and.public" /></label>
						</li>
						<li>
							<div class="bubble public explicit implicit"></div>
						</li>
						<li class="label">
							<label><fmt:message key="openiam.webconsole.resource.type.menu.implicity.explicitly.and.public" /></label>
						</li>
					</ul>
				</div>
				<form>
					<select id="menuTree" autocomplete="off">
						<option value="-1"><fmt:message key="openiam.webconsole.resource.type.menu.select.tree" /></option>
						<c:forEach var="tree" items="${requestScope.menuTrees}">
							<option value="${tree.id}">
								<c:choose>
									<c:when test="${! empty tree.displayName}">${tree.displayName}</c:when>
									<c:otherwise>${tree.name}</c:otherwise>
								</c:choose>
							</option>
						</c:forEach>
					</select>
				</form>
				<div id="actionDescription" style="display:none">
					<p>**<fmt:message key="openiam.webconsole.resource.type.menu.double.click" /></p>
				</div>
				<div id="menuArea">
				</div>
				<input id="saveBtn" class="redBtn" type="submit" value="<fmt:message key='openiam.ui.common.save' />" style="display:none" />
			</div>
		</c:if>
	</body>
</html>