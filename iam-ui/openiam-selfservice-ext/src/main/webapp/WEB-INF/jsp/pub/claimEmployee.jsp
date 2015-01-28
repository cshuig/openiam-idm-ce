<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="pageTitle" scope="request">
    <spring:message code="accountClaim.employee.page.title" />
</c:set>

<t:pubpage>
    <jsp:body>

        <div class="frameContentDivider">
            <form action="${flowExecutionUrl}" method="post">
            <table cellpadding="8px" align="center">
                <tbody>
                <c:if test="${flowRequestContext.messageContext.hasErrorMessages()}">
                <tr>
                    <td colspan="2">
                        <div class="alert alert-error">
                            <c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
                                <p class="error">${message.text}</p>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
                </c:if>
                <tr>
                    <td><label for="employeeID" class="required"><spring:message code="accountClaim.employee.employeeID.label"/></label></td>
                    <td><input type="text" id="employeeID" name="employeeID" value="${employeeID}" class="full rounded" autocomplete="off" /></td>
                </tr>
                <tr>
                    <td><label for="nationalID" class="required"><spring:message code="accountClaim.employee.nationalID.label"/></label></td>
                    <td><input type="text" id="nationalID" name="nationalID" maxlength="4" value="${nationalID}" class="full rounded" autocomplete="off" /></td>
                </tr>
                <tr>
                    <td><label for="dateOfBirth" class="required"><spring:message code="accountClaim.employee.dateOfBirth.label"/></label></td>
                    <td><input type="text" id="dateOfBirth" name="dateOfBirth" value="${dateOfBirth}" class="full rounded" autocomplete="off" /></td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="2">
                        <ul class="formControls">
                            <li class="leftBtn">
                                <input class="whiteBtn" name="_eventId_cancel" type="submit" value='<spring:message code="accountClaim.common.cancel"/>'>
                            </li>
                            <li class="rightBtn">
                                <input class="redBtn" name="_eventId_next" type="submit" value='<spring:message code="accountClaim.common.next"/>'>
                            </li>
                        </ul>
                    </td>
                </tr>
                </tfoot>
            </table>
            </form>
        </div>

    </jsp:body>
</t:pubpage>