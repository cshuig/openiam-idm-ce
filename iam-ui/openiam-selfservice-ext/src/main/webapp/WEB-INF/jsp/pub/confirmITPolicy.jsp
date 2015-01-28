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
    <spring:message code="accountClaim.itPolicy.page.title" />
</c:set>

<t:pubpage>
    <jsp:attribute name="head">
        <style type="text/css">
            select {
                width: auto;
                float: none;
            }
        </style>
    </jsp:attribute>
    <jsp:body>

        <div class="frameContentDivider">
            <form action="${flowExecutionUrl}" method="post">
                <table cellpadding="8px" align="center">
                    <tbody>
                    <c:if test="${flowRequestContext.messageContext.hasErrorMessages()}">
                        <tr>
                            <td>
                                <div class="alert alert-error">
                                    <c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
                                        <p class="error">${message.text}</p>
                                    </c:forEach>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>
                            <div class="policyContent">${requestScope.itPolicy.policyContent}</div>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: center">
                            ${requestScope.itPolicy.confirmation}
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: center">
                            <select name="confirmation" autocomplete="off">
                                <option value=""><spring:message code="accountClaim.itPolicy.selectValue"/></option>
                                <option value="true"><spring:message code="accountClaim.common.yes"/></option>
                                <option value="false"><spring:message code="accountClaim.common.no"/></option>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td>
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