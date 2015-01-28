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
    <spring:message code="accountClaim.success.page.title" />
</c:set>

<t:pubpage>
    <jsp:body>

        <div class="frameContentDivider">
            <form action="${flowExecutionUrl}" method="post">
                <table cellpadding="8px">
                    <tbody>
                    <c:choose>
                    <c:when test="${not claimError}">
                    <tr>
                        <td style="text-align: center">
                            <spring:message code="accountClaim.success.description" arguments="${user.firstName},${user.lastName},${loginID}" />
                        </td>
                    </tr>
                    </c:when>
                        <c:otherwise>
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
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td>
                            <ul class="formControls">
                                <li class="rightBtn">
                                    <input class="redBtn" name="_eventId_finish" type="submit" value='<spring:message code="accountClaim.common.finish"/>'>
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