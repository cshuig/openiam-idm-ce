<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<json:object escapeXml="false">
	<json:property name="status" value="${requestScope.response.status}" />
	<json:property name="redirectURL" value="${requestScope.response.redirectURL}" />
	<c:if test="${! empty requestScope.response.errorList and fn:length(requestScope.response.errorList) > 0}">
		<json:array name="errorList">
			<c:forEach var="error" items="${requestScope.response.errorList}">
				<json:object>
                    <c:choose>
                        <c:when test="${! empty error.i18nError}">
                            <json:property name="message" value="${error.i18nError}" />
                        </c:when>
                        <c:when test="${! empty error.validationError}">
                            <json:property name="message">
                                <spring:message code="${error.validationError}" arguments="${error.params}" />
                            </json:property>
                        </c:when>
                        <c:otherwise>
                            <json:property name="code" value="${error.error.code}" />
                            <json:property name="message">
                                <spring:message code="${error.error.messageName}" arguments="${error.params}" />
                            </json:property>
                        </c:otherwise>
                    </c:choose>
                </json:object>
			</c:forEach>
		</json:array>
	</c:if>
	<c:if test="${! empty requestScope.response.successToken}">
		<json:property name="successMessage">
			<spring:message code="${requestScope.response.successToken.message.messageName}" arguments="${error.params}" />
		</json:property>
	</c:if>
	<c:if test="${! empty requestScope.response.contextValues}">
		<json:object name="contextValues">
			<c:forEach var="contextValue" items="${requestScope.response.contextValues}">
				<json:property name="${contextValue.key}" value="${contextValue.value}" />
			</c:forEach>
		</json:object>
	</c:if>
</json:object>