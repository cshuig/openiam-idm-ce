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
		<openiam:overrideCSS />
		
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.debug.http.request" /></title>
    </head>
    <body>
    	<table>
    		<thead>
    			<tr>
    				<td colspan="2">
    					<b><fmt:message key="openiam.ui.debug.headers.request" /></b>
    				</td>
    			</tr>
    			<tr>
    				<td><fmt:message key="openiam.ui.common.name" /></td>
    				<td><fmt:message key="openiam.ui.common.value" /></td>
    			</tr>
    		</thead>
    		<tbody>
    			<c:forEach var="nextHeader" items="${header}">
        			<tr>
        				<td>
        					<c:out value="${nextHeader.key}" escapeXml="false" />
        					
        				</td>
        				<td>
        					<c:out value="${nextHeader.value}" escapeXml="false" />
        				</td>
        			</tr>
      			</c:forEach>
    		</tbody>
    	</table>
    	
    	<table>
    		<thead>
    			<tr>
    				<td colspan="6">
    					<b><fmt:message key="openiam.ui.debug.cookies.request" /></b>
    				</td>
    			</tr>
    			<tr>
    				<td>
                        <fmt:message key="openiam.ui.common.name" />
    				</td>
    				<td>
                        <fmt:message key="openiam.ui.common.max.age" />
    				</td>
    				<td>
                        <fmt:message key="openiam.ui.common.path" />
    				</td>
    				<td>
                        <fmt:message key="openiam.ui.common.domain" />
    				</td>
    				<td>
                        <fmt:message key="openiam.ui.common.secure" />
    				</td>
    				<td>
                        <fmt:message key="openiam.ui.common.value" />
    				</td>
    			</tr>
    		</thead>
    		<tbody>
    			 <c:forEach items="${cookie}" var="currentCookie">
    			 	<tr>
    			 		<td>
    			 			<c:out value="${currentCookie.value.name}" escapeXml="false" />
    			 		</td>
    			 		<td>
    			 			${currentCookie.value.maxAge}
    			 		</td>
    			 		<td>
    			 			${currentCookie.value.path}
    			 		</td>
    			 		<td>
    			 			${currentCookie.value.domain}
    			 		</td>
    			 		<td>
    			 			${currentCookie.value.secure}
    			 		</td>
    			 		<td>
    			 			<c:out value="${currentCookie.value.value}" escapeXml="false" />
    			 		</td>
    			 	</tr>
    			</c:forEach>  
    		</tbody>
    	</table>
    </body>
</html>