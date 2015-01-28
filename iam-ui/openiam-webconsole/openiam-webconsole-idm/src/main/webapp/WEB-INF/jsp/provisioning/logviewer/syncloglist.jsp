<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--@elvariable id="logViewerListCommand" type="org.openiam.ui.webconsole.idm.web.mvc.provisioning.logviewer.LogViewerListCommand" --%>

<ui:template command="${logViewerListCommand}">

    <jsp:attribute name="searchScript">
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/logviewer/synclog.search.js"></script>
        <script type="text/javascript">
            OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
        </script>
    </jsp:attribute>

    <jsp:body>
        <div class="frameContentDivider">
            <form:form id="searchForm" method="GET" commandName="logViewerListCommand">
                <div id="title" class="title">
                    <fmt:message key="openiam.ui.idm.prov.synclog.header"/>
                </div>
                <spring:hasBindErrors name="logViewerListCommand">
                    <div class="alert alert-error">
                        <form:errors path="*" />
                    </div>
                </spring:hasBindErrors>
                <div class="frameContentDivider">
                    <table cellspacing="1" id="tableOne" class="yui" width="100%">
                        <thead>
                        <tr>
                            <td class="filter" colspan="3">
                                <form:select path="configId" items="${configList}" />
                                <fmt:message key="openiam.ui.idm.prov.synclog.date.label">
                                    <fmt:param value="${requestScope.dateFormatJSTL}"/>
                                </fmt:message>
                                <form:input cssClass="select _input_tiptip" path="startDate" maxlength="10" size="10" type="text" /> -
                                <form:input cssClass="select _input_tiptip" path="endDate" maxlength="10" size="10" type="text" />
                                <img id="filterClearOne" class="clearInput" src="/openiam-ui-static/plugins/tablesorter/img/cross.png" title="<fmt:message key='openiam.ui.idm.prov.connector.clearFilter' />" alt="Clear Filter Image" />
                                <button type="submit"><fmt:message key="openiam.ui.common.search"/></button>
                            </td>
                        </tr>
                        <tr>
                            <th><fmt:message key="openiam.ui.idm.prov.table.col.dateTime"/></th>
                            <th><fmt:message key="openiam.ui.idm.prov.table.col.synchConfigID"/></th>
                            <th><fmt:message key="openiam.ui.idm.prov.table.col.sessionID"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${not empty syncLogList and fn:length(syncLogList) >= 0}">
                                <c:forEach var="it" items="${syncLogList}">
                                    <tr entityId="${it.sessionId}">
                                        <td>
                                            <fmt:formatDate pattern="${requestScope.dateFormatJSTLFull}" value="${it.actionDatetime}" />
                                        </td>
                                        <td>
                                            ${it.objectId}
                                        </td>
                                        <td>
                                            ${it.sessionId}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="3">
                                        <fmt:message key="openiam.ui.idm.prov.table.noresults"/>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                        <tfoot>
                        <tr id="pagerOne">
                            <td colspan="7" style="border-right: solid 3px #7f7f7f;">
                                <img src="/openiam-ui-static/plugins/tablesorter/img/first.png" class="first"/>
                                <img src="/openiam-ui-static/plugins/tablesorter/img/prev.png" class="prev"/>
                                <input type="text" class="pagedisplay"/>
                                <img src="/openiam-ui-static/plugins/tablesorter/img/next.png" class="next"/>
                                <img src="/openiam-ui-static/plugins/tablesorter/img/last.png" class="last"/>
                                <form:select class="pagesize" path="size" id="size" items="${logViewerListCommand.pages}" />
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <form:input type="hidden" path="page" />
            </form:form>
        </div>
    </jsp:body>
</ui:template>