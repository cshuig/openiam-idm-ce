
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
	
	<c:set var="errorMessage">
	<c:if test="${! empty requestScope.errorToken}">
		<spring:message code="${requestScope.errorToken.error.messageName}" arguments="${requestScope.errorToken.params}" />
	</c:if>
</c:set>

<c:set var="successMessage">
	<c:if test="${! empty requestScope.successToken}">
		<spring:message code="${requestScope.successToken.message.messageName}" />
	</c:if>
</c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.policy.upload" /></title>
<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
<openiam:overrideCSS />

<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole/policy/customPasswordPolicy.js"></script>
<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.PolicyId = <c:choose><c:when test="${! empty requestScope.policy.policyId}">"${requestScope.policy.policyId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.SuccessMessage = <c:choose><c:when test="${! empty successMessage}">"${successMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.policy.policyId}">"id=${requestScope.policy.policyId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
</head>
<body>
	<div id="title" class="title">
		<c:choose>
			<c:when test="${! empty requestScope.policy}">
                <fmt:message key="openiam.ui.webconsole.policy.password.edit.custom" />: ${requestScope.policy.name}
				</c:when>
			<c:otherwise>
                <fmt:message key="openiam.ui.webconsole.policy.password.upload.custom" />
				</c:otherwise>
		</c:choose>
	</div>

	<div class="frameContentDivider">

		<form id="CustomPolicyForm" method="post" action="customPswdPolicy.html" enctype="multipart/form-data">
			<input type="hidden" name="policyDefId" />

			<div class="title"><fmt:message key="openiam.ui.webconsole.policy.password.overview" /></div>
			<table cellpadding="8px" align="center" class="fieldset">
            <tbody>

				<c:if test="${! empty requestScope.policy}">
					<tr>
						<td><label for="policyId"><fmt:message key="openiam.ui.webconsole.policy.id" /></label></td>
						<td><input id="policyId" name="policyId" type="text"
							class="full rounded" value="${requestScope.policy.policyId}" />
						</td>
					</tr>
				</c:if>
				<tr>
					<td><label for="name" class="required"><fmt:message key="openiam.ui.webconsole.policy.name" /></label>
					</td>
					<td><input id="name" name="name" type="text"
						class="full rounded"
						value="<c:if test="${! empty requestScope.policy}">${requestScope.policy.name}</c:if>"
						<c:if test="${! empty requestScope.policy}">readonly="true"</c:if> />
					</td>
				</tr>
				<tr>
					<td><label for="description"><fmt:message key="openiam.ui.common.description" /></label></td>
					<td><textarea id="description" name="description"
							class="full rounded"><c:if test="${! empty requestScope.policy}">${requestScope.policy.description}</c:if></textarea></td>
				</tr>

				<!--<tr>
					<td>Policy DefId</td>
					<td><input id="policyDefId" name="policyDefId" type="hidden"
						class="full rounded" value="100"
						<c:if test="${! empty requestScope.policy}">readonly="true"</c:if> />
					</td>
				</tr>

				-->
				<tr>
					<td><label for="status"><fmt:message key="openiam.ui.webconsole.policy.status" /></label></td>
					<td><select id="status" name="status" class="rounded">
							<option value="1"
								<c:if test="${!empty requestScope.policy and requestScope.policy.status eq 1}">selected</c:if>><fmt:message key="openiam.ui.common.active" /></option>
							<option value="0"
								<c:if test="${! empty requestScope.policy and requestScope.policy.status eq 0}">selected</c:if>><fmt:message key="openiam.ui.common.inactive" /></option>
					</select></td>
				</tr>
				
				<tr>
					<td><label for="rule"><fmt:message key="openiam.ui.webconsole.policy.rule.text" /></label></td>
					<td><textarea id="rule" name="rule"
							class="full rounded"><c:if test="${! empty requestScope.policy}">${requestScope.policy.rule}</c:if></textarea></td>
				</tr>
				
				<tr class="fileUploadHolder">
					<td><label for="ruleSrcUrl"><fmt:message key="openiam.ui.webconsole.policy.rule.src" /></label></td>
					<td>
                        <c:if test="${! empty requestScope.policy}">${requestScope.policy.ruleSrcUrl}<br/></c:if>
                        <input type="file" id="ruleSrcUrl" name="ruleSrcUrl" class="full rounded"/>
					</td>
				</tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="2">
                        <ul class="formControls">
                            <li><a href="javascript:void(0)"> <input type="submit"
                                    class="redBtn" value="<fmt:message key='openiam.ui.button.save' />" /> </a></li>
                            <li><a href="passwordPolicy.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel" /></a>
                            </li>
                            <c:if test="${! empty requestScope.policy}">
                                <li class="rightBtn"><a id="deletePolicy"
                                    href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete" /></a></li>
                            </c:if>
                        </ul></td>
                </tr>
            </tfoot>
            </table>
		</form>

	</div>

</body>
</html>