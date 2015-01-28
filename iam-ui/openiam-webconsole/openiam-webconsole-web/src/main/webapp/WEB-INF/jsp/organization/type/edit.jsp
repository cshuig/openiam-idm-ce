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

<c:set var="title">
	<c:choose>
		<c:when test="${! empty requestScope.organizationType.id}">
            <fmt:message key="openiam.ui.shared.organization.type.edit" />: ${requestScope.organizationType.name}
		</c:when>
		<c:otherwise>
            <fmt:message key="openiam.ui.webconsole.organization.type.create.new" />
		</c:otherwise>
	</c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${title}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />

        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/organization/organization.type.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/language/language.admin.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.OrganizationTypeId = <c:choose><c:when test="${! empty requestScope.organizationType.id}">"${requestScope.organizationType.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.organizationType.id}">"id=${requestScope.organizationType.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.OrganizationType = ${requestScope.typeAsJSON};
			OPENIAM.ENV.Text = {
				DeleteWarn : localeManager["openiam.ui.webconsole.organization.type.delete.ask"]
			};
		</script>
	</head>
	<body>
		<div id="title" class="title">${title}</div>
		<div class="frameContentDivider">
			<form id="organizationForm" method="post">
				<table cellpadding="8px" align="center" class="fieldset">
					<tbody>
						<tr>
							<td>
								<label for="name" class="required"><fmt:message key="openiam.ui.webconsole.organization.type.name" />:</label>
							</td>
							<td>
								<input id="name" autocomplete="off" maxlength="200" type="text" class="full rounded" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="displayNameMap"><fmt:message key="openiam.ui.common.display.name" /></label>
							</td>
							<td>
								<div id="displayNameMap"></div>
							</td>
						</tr>
						<tr>
							<td><fmt:message key="openiam.ui.common.description" /></td>
							<td>
								<textarea id="description" autocomplete="off" maxlength="200"  name="description" class="full rounded"></textarea>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2">
								<ul class="formControls">
									<li>
										<a id="saveBtn" href="javascript:void(0)">
											<input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.button.save' />" />
										</a>
									</li>
									<li>
										<a href="organizationTypeSearch.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel" /></a>
									</li>
									<c:if test="${! empty requestScope.organizationType.id}">
										<li class="rightBtn">
											<a id="deleteBtn" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete" /></a>
										</li>
									</c:if>
								</ul>
							</td>
						</tr>
					</tfoot>
				</table>
			</form>
		</div>
	</body>
</html>