<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.common.unlock.user" /></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/webconsole/login.css" rel="stylesheet" type="text/css" media="screen" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery.corner.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery.boxshadow.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/login.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>
	</head>
	<body>
    <c:import url="/WEB-INF/jsp/core/login-header.jsp"/>
    <div id="pagebg">
			<div id="login">
				<div id="llogo">
					<a href="javascript:void(0);" class="logo llogo">Openiam</a>
				</div>
				
				<div id="credentials">
                    <fmt:message key="openiam.idp.enter.username.email" />
				</div>
				
				<form:form id="unlock-form" action="unlockPassword.html" commandName="passwordBean">
                   	<c:choose>
						<c:when test="${! empty requestScope.error}">
							<p class="error">
								<spring:message code="${requestScope.error.error.messageName}" arguments="${requestScope.error.params}" />
							</p>
						</c:when>
                    	<c:otherwise>
                        	<spring:hasBindErrors name="passwordBean">
                            	<div class="alert alert-error">
                                	<form:errors path="*" cssClass="error" />
                            	</div>
                        	</spring:hasBindErrors>
                    	</c:otherwise>
                   	</c:choose>
                   	<c:set var="loginPlaceholder">
                   		<fmt:message key='openiam.idp.enter.username' />
                   	</c:set>
                   	<c:if test="${requestScope.isUnlockSecure and requestScope.isEmailUnlockEnabled}">
                   		<c:set var="emailPlaceholder">
                   			<fmt:message key='openiam.idp.enter.email' />
                   		</c:set>
                   	</c:if>
					<form:input path="principal" cssClass="loginField" placeholder="${loginPlaceholder}" initialized="${not empty requestScope.passwordBean.principal}" />
                    <c:if test="${requestScope.isUnlockSecure and requestScope.isEmailUnlockEnabled}">
                        <form:input path="email" cssClass="emailField" placeholder="${emailPlaceholder}" initialized="${not empty requestScope.passwordBean.email}" />
                    </c:if>
					<div>
						<input id="submit" type="submit" class="redBtn loginBtn" value="<fmt:message key='openiam.idp.unlock.account' />" />
					</div>
				</form:form>
				
				<div id="lbuttons">
					
				</div>
			</div>		
			<c:import url="/WEB-INF/jsp/core/login-footer.jsp" />
		</div>
	</body>
</html>