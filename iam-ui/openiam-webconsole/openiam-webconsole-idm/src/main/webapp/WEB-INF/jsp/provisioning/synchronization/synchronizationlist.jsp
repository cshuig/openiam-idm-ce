<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--@elvariable id="synchListCommand" type="org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization.SynchronizationListCommand"--%>

<ui:template command="${synchListCommand}">

    <jsp:attribute name="searchScript">
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/synch/synchconfig.search.js"></script>
    </jsp:attribute>

    <jsp:body>
            <form:form id="searchForm" method="GET" commandName="synchListCommand">
                <div id="title" class="title">
                    <spring:message code="openiam.ui.idm.synch.synchlist.header"/>
                </div>
                <div class="frameContentDivider">
                    <table cellspacing="1" id="tableOne" class="yui" width="100%">
                        <thead>
                        <tr>
                            <td class="filter" colspan="3">
                                <spring:message code="openiam.ui.idm.synch.synchlist.searchInpt.label"/>
                                <form:input id="synchConfigName" path="name" maxlength="30" size="30" type="text" />
                                <img id="filterClearOne" class="clearInput" src="/openiam-ui-static/plugins/tablesorter/img/cross.png" title="<fmt:message key='openiam.ui.idm.prov.connector.clearFilter' />" alt="Clear Filter Image" />
                                <button type="submit"><spring:message code="openiam.ui.idm.synch.synchlist.searchBtn"/></button>
                            </td>
                        </tr>
                        <tr>
                            <th><spring:message code="openiam.ui.idm.synch.table.col.name"/></th>
                            <th><spring:message code="openiam.ui.idm.synch.table.col.type"/></th>
                            <th><spring:message code="openiam.ui.idm.synch.table.col.status"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${!empty configList and fn:length(configList) >= 0}">
                                <c:forEach var="entity" items="${configList}">
                                    <tr entityId="${entity.synchConfigId}">
                                        <td>
                                            <a href="synchronization.html?id=${entity.synchConfigId}">
                                                ${entity.name}
                                            </a>
                                        </td>
                                        <td>
                                                ${entity.synchType}
                                        </td>
                                        <td>
                                                ${entity.status}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="3">
                                        <spring:message code="openiam.ui.idm.synch.table.noresults"/>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                        <tfoot>
                        <tr id="pagerOne">
                            <td colspan="3" style="border-right: solid 3px #7f7f7f;">
                                <img src="/openiam-ui-static/plugins/tablesorter/img/first.png" class="first"/>
                                <img src="/openiam-ui-static/plugins/tablesorter/img/prev.png" class="prev"/>
                                <input type="text" class="pagedisplay"/>
                                <img src="/openiam-ui-static/plugins/tablesorter/img/next.png" class="next"/>
                                <img src="/openiam-ui-static/plugins/tablesorter/img/last.png" class="last"/>
                                <form:select class="pagesize" path="size" id="size" items="${synchListCommand.pages}" />
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <form:hidden path="page" />
            </form:form>

    </jsp:body>
</ui:template>