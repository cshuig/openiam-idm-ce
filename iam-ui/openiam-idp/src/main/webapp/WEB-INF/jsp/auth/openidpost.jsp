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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.idp.openid.confirmatin" /></title>
		<style type="text/css">
    		html { visibility:hidden; }
		</style>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/webconsole/login.css" rel="stylesheet" type="text/css" media="screen" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<link rel="openid.server" href="${requestScope.openIdUrlWithoutPrincipal}"/>
		<link rel="openid.delegate" href="${requestScope.openIdWithPrincipal}"/>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery.corner.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery.boxshadow.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/idp/openid.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
		</script>
	</head>
	<body>
		<div id="pagebg">
			<div id="login">
				<div id="llogo">
					<a href="javascript:void(0);" class="logo llogo">Openiam</a>
				</div>
				
				<form id="openidForm" action="${requestScope.openIdTarget}" method="POST">
					<div>
						<c:if test="${! empty requestScope.requiredMap}">
							<div id="credentials">
                                <fmt:message key="openiam.idp.appl.access.info" />
							</div>
							<div>
								<c:if test="${! empty requestScope.requiredMap['email']}">
									<div style="clear:both">
										<label>Email Address</label>
										<select class="full rounded grant" disabled="disabled">
											<option>Allow</option>
										</select>
									</div>
								</c:if>
								<c:if test="${! empty requestScope.requiredMap['firstname']}">
									<div style="clear:both">
										<label><fmt:message key="openiam.ui.user.firstname" /></label>
										<select class="full rounded grant" disabled="disabled">
											<option><fmt:message key="openiam.ui.common.value.allow" /></option>
										</select>
									</div>
								</c:if>
								<c:if test="${! empty requestScope.requiredMap['lastname']}">
									<div style="clear:both">
										<label><fmt:message key="openiam.ui.user.lastname" /></label>
										<select class="full rounded grant" disabled="disabled">
											<option><fmt:message key="openiam.ui.common.value.allow" /></option>
										</select>
									</div>
								</c:if>
							</div>
						</c:if>
					</div>
					<div>
						<c:if test="${! empty requestScope.optionalMap}">
							<div id="credentials">
                                <fmt:message key="openiam.idp.addition.appl.access.info" />
							</div>
						</c:if>
					</div>
					<div>
						<input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.common.continue' />" />
						<a href="/selfservice" class="whiteBtn"><fmt:message key="openiam.ui.common.cancel" /></a>
					</div>
				</form>
			</div>		
			<c:import url="/WEB-INF/jsp/core/login-footer.jsp" />
		</div>
	</body>
</html>