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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - ${requestScope.authProviderType.description}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole-am/authenticationProvider.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/authprovider/authenticationProvider.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.provider.providerId}">"id=${requestScope.provider.providerId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.SuccessMessage = <c:choose><c:when test="${! empty successMessage}">"${successMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ProviderId = <c:choose><c:when test="${! empty requestScope.provider.providerId}">"${requestScope.provider.providerId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${! empty requestScope.provider}">
					Edit ${requestScope.authProviderType.description}: ${requestScope.provider.name}
				</c:when>
				<c:otherwise>
					Create ${requestScope.authProviderType.description}
				</c:otherwise>
			</c:choose>
		</div>
		<div class="frameContentDivider">
			<form id="authProviderForm" method="post" action="editAuthProvider.html" enctype="multipart/form-data">
				<input id="providerType" name="providerType" type="hidden" value="${requestScope.authProviderType.id}" />
				<input id="resourceId" name="resourceId" type="hidden" class="full rounded" value="${requestScope.provider.resourceId}" />
				<input id="clearPublicKey" name="clearPublicKey" type="hidden" class="full rounded" value="false" />
				<input id="clearPrivateKey" name="clearPrivateKey" type="hidden" class="full rounded" value="false" />
				<input id="providerId" name="providerId" type="hidden" class="full rounded" value="${requestScope.provider.providerId}" />
				<table cellpadding="8px" align="center" class="fieldset">
					<thead>
						<c:if test="${empty requestScope.publicKeyCert and ! empty requestScope.provider.publicKey}">
							<tr>
								<th colspan="2">
									<p class="warning">
										<spring:message code="org.openiam.am.auth.public.key.warn.invalidX509" />
									</p>
								</th>
							</tr>
						</c:if>
					</thead>
					<tbody>
						<c:if test="${! empty requestScope.provider.resource and ! empty requestScope.provider.resource.id}">
							<tr>
								<td>
									<label><fmt:message key="openiam.ui.common.linked.to.resource"/>:</label>
								</td>
								<td>
									<a href="/webconsole/editResource.html?id=${requestScope.provider.resource.id}">${requestScope.resourceCoorelatedName}</a>
								</td>
							</tr>
						</c:if>
						<tr>
							<td>
								<label class="required"><fmt:message key="openiam.ui.am.auth.provider.name"/></label>
							</td>
							<td>
								<input type="text" 
									id="name" 
									name="name" 
									class="full rounded _input_tiptip"
									title='<fmt:message key="openiam.ui.am.auth.provider.name.title"/>'
									value="${requestScope.provider.name}" />
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.am.auth.provider.application.url"/></label>
							</td>
							<td>
								<input type="text" 
									id="applicationURL" 
									name="applicationURL" 
									class="full rounded _input_tiptip"
									title='<fmt:message key="openiam.ui.am.auth.provider.application.url.title"/>'
									value="<c:if test="${! empty requestScope.provider.resource}">${requestScope.provider.resource.URL}</c:if>" />
							</td>
						</tr>
						<c:if test="${! empty requestScope.managedSystems and fn:length(requestScope.managedSystems) > 0}">
							<tr>
								<td>
									<label class="required"><fmt:message key="openiam.ui.am.auth.provider.mngsys"/></label>
								</td>
								<td>
									<select id="managedSysId" 
										name="managedSysId" 
										class="select _input_tiptip" 
										autocomplete="off"
										title='<fmt:message key="openiam.ui.am.auth.provider.mngsys.title"/>'>
										<option value=""><fmt:message key="openiam.ui.shared.managed.system.select"/></option>
										<c:forEach var="managedSystem" items="${requestScope.managedSystems}">
											<option <c:if test="${requestScope.provider.managedSysId eq managedSystem.id}">selected="selected"</c:if> value="${managedSystem.id}">${managedSystem.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</c:if>
						<c:if test="${requestScope.authProviderType.hasPrivateKey or requestScope.authProviderType.hasPublicKey}">
							<tr>
								<td>
									<label class="required"><fmt:message key="openiam.ui.am.auth.provider.sign.response"/></label>
								</td>
								<td>
									<input id="signResponse" autocomplete="off" type="radio" name="signRequest" value="true" <c:if test="${requestScope.provider.signRequest eq true}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.yes" />
									<input id="dontSignResponse" autocomplete="off" type="radio" name="signRequest" value="false" <c:if test="${requestScope.provider.signRequest eq false}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.no" />
								</td>
							</tr>
						</c:if>
						<c:if test="${requestScope.authProviderType.hasPublicKey}">
							<tr class="fileUploadHolder">
								<td>
									<label class="required"><fmt:message key="openiam.ui.am.auth.provider.public.key"/></label>
								</td>
								<td>
									<c:if test="${! empty requestScope.provider.publicKey}">
										<p><fmt:message key="openiam.ui.am.auth.provider.public.key.uploaded"/> <a id="uploadNewPublicKey" class="blue" href="javascript:void(0);"><fmt:message key="openiam.ui.am.auth.provider.key.upload"/></a></p>
										<c:if test="${! empty requestScope.publicKeyCert}">
											<div class="publicKeyProperties">
												<div class="publicKeyTitle"><fmt:message key="openiam.ui.am.auth.provider.key.props"/></div>
												<c:if test="${! empty requestScope.publicKeyCert.startDate}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.start.date"/>:</label>
														<span>${requestScope.publicKeyCert.startDate}</span>
													</div>
												</c:if>
												<c:if test="${! empty requestScope.publicKeyCert.expirationDate}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.expiration.date"/>:</label>
														<span>${requestScope.publicKeyCert.expirationDate}</span>
													</div>
												</c:if>
												<c:if test="${! empty requestScope.publicKeyCert.version}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.version"/>:</label>
														<span>${requestScope.publicKeyCert.version}</span>
													</div>
												</c:if>
												<c:if test="${! empty requestScope.publicKeyCert.certificateType}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.type"/>:</label>
														<span>${requestScope.publicKeyCert.certificateType}</span>
													</div>
												</c:if>
												<c:if test="${! empty requestScope.publicKeyCert.principalName}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.principal"/>:</label>
														<span>${requestScope.publicKeyCert.principalName}</span>
													</div>
												</c:if>
												<c:if test="${! empty requestScope.publicKeyCert.subjectDN}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.subject"/>:</label>
														<span>${requestScope.publicKeyCert.subjectDN}</span>
													</div>
												</c:if>
												<c:if test="${! empty requestScope.publicKeyCert.issuerDN}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.issuer"/>:</label>
														<span>${requestScope.publicKeyCert.issuerDN}</span>
													</div>
												</c:if>
												<c:if test="${! empty requestScope.publicKeyCert.signatureAlgorithmName}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.algorithm"/>:</label>
														<span>${requestScope.publicKeyCert.signatureAlgorithmName}</span>
													</div>
												</c:if>
												<c:if test="${! empty requestScope.publicKeyCert.signatureAlgorithmOID}">
													<div class="property">
														<label><fmt:message key="openiam.ui.am.auth.provider.public.key.algorithm.id"/>:</label>
														<span>${requestScope.publicKeyCert.signatureAlgorithmOID}</span>
													</div>
												</c:if>
											</div>
										</c:if>
									</c:if>
									<input type="file" 
										autocomplete="off" 
										id="publicKey" 
										name="publicKey" 
										value="Browse" 
										class="full rounded" 
										<c:if test="${! empty requestScope.provider.publicKey}">style="display:none"</c:if> />
								</td>
							</tr>
						</c:if>
						<c:if test="${requestScope.authProviderType.hasPrivateKey}">
							<tr class="fileUploadHolder">
								<td>
									<label class="required"><fmt:message key="openiam.ui.am.auth.provider.private.key"/></label>
								</td>
								<td>
									<c:if test="${! empty requestScope.provider.privateKey}">
										<p><fmt:message key="openiam.ui.am.auth.provider.private.key.uploaded"/>  <a id="uploadNewPrivateKey" class="blue" href="javascript:void(0);"><fmt:message key="openiam.ui.am.auth.provider.key.upload"/></a></p>
									</c:if>
									<input type="file" 
										autocomplete="off" 
										id="privateKey" 
										name="privateKey" 
										value="Browse" 
										class="full rounded" 
										<c:if test="${! empty requestScope.provider.privateKey}">style="display:none"</c:if> />
								</td>
							</tr>
						</c:if>
						<c:if test="${! empty requestScope.attributeList}">
							<c:forEach var="attribute" items="${requestScope.attributeList}">
								<tr>
									<td>
										<label <c:if test="${attribute.required}">class="required"</c:if>>${attribute.attributeName}</label>
										<c:if test="${attribute.dataType eq 'listValue'}"><sup><fmt:message key="openiam.ui.am.auth.provider.edit.attribute.help"/></sup></c:if>
									</td>
									<td>
										<c:set var="displayValue">${requestScope.provider.providerAttributeMap[attribute.attributeName].value}</c:set>
										<c:if test="${empty displayValue}">
											<c:set var="displayValue">${attribute.defaultValue}</c:set>
										</c:if>
										<c:choose>
											<c:when test="${attribute.dataType eq 'singleValue'}">
												<input type="text"
													name="attributeMap['${attribute.authAttributeId}']" 
													class="full rounded _input_tiptip"
													title="${attribute.description}" 
													value="${displayValue}" />
											</c:when>
											<c:when test="${attribute.dataType eq 'booleanValue'}">
												<c:set var="attributeName">attributeMap['${attribute.authAttributeId}']</c:set>
												<c:set var="attributeValue">${requestScope.provider.providerAttributeMap[attribute.attributeName].value eq 'true'}</c:set>
												<input class="_input_tiptip" autocomplete="off" type="radio" name="${attributeName}" value="true" <c:if test="${attributeValue eq true}">checked="checked"</c:if> title="${attribute.description}" /> <fmt:message key="openiam.ui.common.yes"/>
												<input class="_input_tiptip" autocomplete="off" type="radio" name="${attributeName}" value="false" <c:if test="${attributeValue eq false}">checked="checked"</c:if> title="${attribute.description}" /> <fmt:message key="openiam.ui.common.no"/>
											</c:when>
											<c:otherwise>
												<textarea 
													name="attributeMap['${attribute.authAttributeId}']"
													class="full rounded _input_tiptip"
													title="${attribute.description}">${displayValue}</textarea>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<tr>
							<td><fmt:message key="openiam.ui.common.description"/></td>
							<td>
								<textarea id="description" 
									name="description" 
									autocomplete="off" 
									class="full rounded">${requestScope.provider.description}</textarea>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2">
								<ul class="formControls">
									<li>
										<a href="javascript:void(0)">
											<input type="submit" class="redBtn" value='<fmt:message key="openiam.ui.button.save"/>' />
										</a>
									</li>
									<li>
										<a href="authenticationProviders.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel"/></a>
									</li>
									<c:if test="${not empty requestScope.provider.providerId}">
										<c:choose>
											<c:when test="${requestScope.authProviderType.id eq 'SAML_SP_PROVIDER'}">
												<li>
													<a href="/idp/sp/metadata/${requestScope.provider.providerId}" target="_blank">
														<fmt:message key="openiam.ui.webconsole.saml.sp.metadata"/>
													</a>
												</li>
											</c:when>
											<c:when test="${requestScope.authProviderType.id eq 'SAML_PROVIDER'}">
												<li>
													<a href="/idp/SAMLMetadata.html?id=${requestScope.provider.providerId}" target="_blank">
														<fmt:message key="openiam.ui.webconsole.saml.sp.metadata"/>
													</a>
												</li>
											</c:when>
										</c:choose>
									</c:if>
									<c:if test="${! empty requestScope.provider.providerId}">
										<li class="rightBtn">
											<a id="deleteProvider" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete"/></a>
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