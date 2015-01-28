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
<div id="top" class="standalone">
	<div class="wrapper">
		<div id="header">
			<div id="logo">
				<a href="${requestScope.currentURI}" class="logo slogo">Openiam</a>
			</div>
		</div>
	</div>
</div>
<div id="middle">
	<div class="wrapper">
		<div id="submenu" style="display:none"></div>
		<div id="leftshadow"></div>
		<div id="rightshadow"></div>
		<div id="content">
			Example public page
		</div>
	</div>
</div>	
<div id="bottom">
	<div class="wrapper">
		<div id="footer">
            <div id="copy">
                ${footerCopyright}
            </div>
            <div id="footnav">
                ${footerNav}
            </div>
		</div>
	</div>
</div>