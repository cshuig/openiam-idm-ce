<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ui:template>
    <jsp:attribute name="searchScript">
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/logviewer/syncreport.form.js"></script>
    </jsp:attribute>
    <jsp:body>
        <script type="text/javascript">
            OPENIAM = window.OPENIAM || {};
            OPENIAM.ENV = window.OPENIAM.ENV || {};
            OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.reportCommand.synchConfigId}";
            OPENIAM.ENV.SynchConfigId = <c:choose><c:when test="${! empty requestScope.reportCommand.synchConfigId}">"${requestScope.reportCommand.synchConfigId}"</c:when><c:otherwise>""</c:otherwise></c:choose>;
            OPENIAM.ENV.ReportHeight = 1100;
            OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
        </script>
        <div id="title" class="title">
            <spring:message code="openiam.ui.idm.synch.synch_report.header"/>
        </div>
        <div class="frameContentDivider">
            <form:form id="reportForm" commandName="reportCommand">
                <form:input type="hidden" path="synchConfigId" />
                <table cellpadding="8px" align="center">
                    <tbody>
                    <tr>
                        <td><label for="auditLogDate"><fmt:message key="openiam.ui.idm.synch.report.date"/></label></td>
                        <td>
                            <fmt:formatDate value="${requestScope.reportCommand.auditLogDate}" pattern="MM/dd/yyyy" var="dLog" />
                            <input name="auditLogDate" id="auditLogDate" type="text" class="full rounded date" value="${dLog}" readonly="readonly" />
                        </td>
                    </tr>
                    <tr>
                        <td><fmt:message key="openiam.ui.idm.synch.report.event"/></td>
                        <td id="auditLogList"></td>
                    </tr>
                    <tr>
                        <td><label for="reportType"><fmt:message key="openiam.ui.report.format"/></label></td>
                        <td>
                            <form:select path="reportType" cssClass="short select rounded" autocomplete="off">
                                <form:options items="${formatList}" />
                            </form:select>
                        </td>
                    </tr>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="2">
                            <ul class="formControls">
                                <li class="leftBtn">
                                    <a href="javascript:void(0)">
                                        <a id="runReport" href="javascript:void(0)" class="redBtn"><fmt:message key="openiam.ui.common.report"/></a>
                                    </a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </form:form>
        </div>
    </jsp:body>
</ui:template>