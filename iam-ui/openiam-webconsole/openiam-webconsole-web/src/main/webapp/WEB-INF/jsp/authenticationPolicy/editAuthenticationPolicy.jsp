
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
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<c:set var="pageTitle">
	<c:choose>
		<c:when test="${! empty requestScope.policy}">
			<fmt:message key="openiam.ui.webconsole.policy.edit" />: ${requestScope.policy.name}
		</c:when>
		<c:otherwise>
			<fmt:message key="openiam.ui.webconsole.policy.create.new.authentication" />
		</c:otherwise>
	</c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<title>${titleOrganizatioName} - ${pageTitle}</title>
			<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
			<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
			<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
			<openiam:overrideCSS />

			<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
			<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
			<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
			<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

			<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
			<script type="text/javascript" src="/openiam-ui-static/js/webconsole/authenticationPolicy/authentication.edit.js"></script>
			<script type="text/javascript">
				OPENIAM = window.OPENIAM || {};
				OPENIAM.ENV = window.OPENIAM.ENV || {};
				OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.PolicyId = <c:choose><c:when test="${! empty requestScope.policy.policyId}">"${requestScope.policy.policyId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.Policy = <c:choose><c:when test="${! empty requestScope.policyAsJSON}">${requestScope.policyAsJSON}</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.MenuTreeAppendURL = null;
			</script>
		</head>
		<body>
			<div id="title" class="title">${pageTitle}</div>
			<div class="frameContentDivider">
				<div class="title">
					<fmt:message key="openiam.ui.webconsole.policy.authentication.overview" />
				</div>
				<table cellpadding="8px" align="center" class="fieldset">
					<tr>
						<td>
							<label for="name" class="required">
								<fmt:message key="openiam.ui.webconsole.policy.name" />
							</label>
						</td>
						<td>
							<input id="name" name="name" type="text" class="full rounded" />
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="openiam.ui.common.description" />
						</td>
						<td>
							<textarea id="description" name="description" class="full rounded"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="openiam.ui.webconsole.policy.status" />
						</td>
						<td>
							<select id="status" name="status" class="rounded">
								<option value="1">
									<fmt:message key="openiam.ui.common.active" />
								</option>
								<option value="0">
									<fmt:message key="openiam.ui.common.inactive" />
								</option>
						</select>
						</td>
					</tr>
	
				</table>
				<div class="title">
					<fmt:message key="openiam.ui.webconsole.policy.composition.options" />
				</div>
				<table cellpadding="8px" align="center" class="fieldset">
								
					<c:forEach items="${requestScope.policyAttributes}" var="policyAttributes">
							<c:choose>
								<c:when test="${policyAttributes.name == 'AUTH_REPOSITORY'}">							
									<tr>
										<td class="tableEntry"> 
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<select id="AUTH_REPOSITORY" class="rounded">
												<option value="">-<fmt:message key="openiam.ui.selfservice.text.pleaseselect" />-</option>
											</select>
										</td>
									</tr>
								</c:when>
	
								<c:when test="${policyAttributes.name == 'AUTO_UNLOCK_TIME'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="AUTO_UNLOCK_TIME" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'DEFAULT_LOGIN_MOD'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<select id="DEFAULT_LOGIN_MOD" class="pair rounded">
												<option value="">
													<fmt:message key="openiam.ui.common.value.pleaseselect" />
												</option>
												<option value="defaultLoginModule"><fmt:message key="openiam.ui.authentication.policy.default.login.module" /></option>
												<option value="org.openiam.idm.srvc.auth.spi.LDAPLoginModule"><fmt:message key="openiam.ui.authentication.policy.login.module.ldap" /></option>
												<option value="org.openiam.idm.srvc.auth.spi.ActiveDirectoryLoginModule"><fmt:message key="openiam.ui.authentication.policy.login.module.ad" /></option>
												<option value="CUSTOM">
													<fmt:message key="openiam.ui.webconsole.policy.authentication.custom" />
												</option>
											</select>
											-
											<input id="DEFAULT_LOGIN_MOD" value2="true" type="text" class="pair rounded"/>
										</td>
									</tr>
								</c:when>
	
								<c:when test="${policyAttributes.name == 'FAILED_AUTH_COUNT'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="FAILED_AUTH_COUNT" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
	
								<c:when test="${policyAttributes.name == 'FAIL_URL'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="FAIL_URL" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
	
								<c:when test="${policyAttributes.name == 'LOGIN_MODULE_SEL_POLCY'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="LOGIN_MODULE_SEL_POLCY" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
	
								<c:when test="${policyAttributes.name == 'LOGIN_MOD_TYPE'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="LOGIN_MOD_TYPE" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
	
								<c:when test="${policyAttributes.name == 'SUCCESS_URL'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>				
										</td>
										<td class="tableEntry">
											<input id="SUCCESS_URL" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
								<c:when test="${policyAttributes.name == 'TOKEN_ISSUER'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="TOKEN_ISSUER" class="full rounded" type="text" />
										</td>
									</tr>
								</c:when>
	
								<c:when test="${policyAttributes.name == 'TOKEN_LIFE'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="TOKEN_LIFE" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
	
								<c:when test="${policyAttributes.name == 'TOKEN_TYPE'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<select id="TOKEN_TYPE" class="rounded">
												<option value="">
													<fmt:message key="openiam.ui.common.value.pleaseselect" />
												</option>
												<option value="OPENIAM_TOKEN">
													<spring:message code="openiam.ui.webconsole.policy.authentication.OPENIAM_TOKEN" />
												</option>
												<option value="SAML1_TOKEN">
													<spring:message code="openiam.ui.webconsole.policy.authentication.SAML_TOKEN" arguments="1" />
												</option>
												<option value="SAML2_TOKEN">
													<spring:message code="openiam.ui.webconsole.policy.authentication.SAML_TOKEN" arguments="2" />
												</option>
											</select>
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'FAILED_AUTH_COUNT'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="FAILED_AUTH_COUNT" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'HOST_LOGIN'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="HOST_LOGIN" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'HOST_PASSWORD'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="HOST_PASSWORD" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'HOST_URL'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="HOST_URL" class="full rounded" type="text"/>
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'KEY_ATTRIBUTE'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="KEY_ATTRIBUTE" class="full rounded" type="text" />
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'MANAGED_SYS_ID'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<select id="MANAGED_SYS_ID" class="rounded">
												<option value="">
													<fmt:message key="openiam.ui.shared.managed.system.select" />
												</option>
												<c:forEach var="mSys" items="${requestScope.managedSystems}">
													<option value="${mSys.id}">${mSys.name}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'PROTOCOL'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<select id="PROTOCOL" class="rounded">
												<option value="">
													<fmt:message key="openiam.ui.common.value.pleaseselect" />
												</option>
												<option value="CLEAN">
													<fmt:message key="openiam.ui.webconsole.policy.authentication.CLEAN" />
												</option>
												<option value="SSL">
													<fmt:message key="openiam.ui.webconsole.policy.authentication.SSL" />
												</option>
												<option value="TLS">
													<fmt:message key="openiam.ui.webconsole.policy.authentication.TLS" />
												</option>
											</select>
										</td>
									</tr>
								</c:when>
								
								<c:when test="${policyAttributes.name == 'BASEDN'}">								
									<tr>
										<td class="tableEntry">
											<label>${policyAttributes.name}</label>
										</td>
										<td class="tableEntry">
											<input id="BASEDN" class="full rounded" type="text" />
										</td>
									</tr>
								</c:when>
							</c:choose>
					</c:forEach>
	            <tfoot>
				<tr>
					<td colspan="2">
						<ul class="formControls">
							<li class="leftBtn">
	                            <a href="javascript:void(0)">
	                            	<input id="save" type="submit" class="redBtn" value="<fmt:message key='openiam.ui.button.save' />" />
	                            </a>
							</li>
							<li class="leftBtn">
	                            <a href="authenticationPolicy.html" class="whiteBtn">
	                            	<fmt:message key="openiam.ui.button.cancel" />
	                            </a>
							</li>
							<c:if test="${! empty requestScope.policy}">
								<li class="rightBtn">
	                                <a id="deleteAuthenticationPolicy" href="javascript:void(0);" class="redBtn">
	                                	<fmt:message key="openiam.ui.button.delete" />
	                                </a>
								</li>
							</c:if>
						</ul>
					</td>
				</tr>
	            </tfoot>
	        </table>
		</div>
	</body>
</html>