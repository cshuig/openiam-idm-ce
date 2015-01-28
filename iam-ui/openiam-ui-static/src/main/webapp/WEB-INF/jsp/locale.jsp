<%
//    response.setHeader("Content-Type","text/javascript");
//    response.setHeader("Cache-Control","no-store, must-revalidate");
//    response.setDateHeader("Expires", System.currentTimeMillis() + 604800000L);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

var localeManager={};
<c:if test="${not empty messagesMap}">
    localeManager=${messagesMap};
</c:if>