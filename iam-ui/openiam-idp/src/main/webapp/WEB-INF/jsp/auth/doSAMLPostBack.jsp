<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>${titleOrganizatioName} - <fmt:message key="openiam.idp.saml.post" /></title>
	</head>
	<body>
		<form method="post" action="SAMLLogin.html" style="display:none">
			<input type="text" name="RelayState" value="${requestScope.RelayState}" />
			<input type="text" name="SAMLRequest" value="${requestScope.SAMLRequest}" />
			<input type="submit" value="Login" />
		</form>
	</body>
	
	<script type="text/javascript">
		window.onload = function() {
			document.forms[0].submit();
		};
	</script>
</html>