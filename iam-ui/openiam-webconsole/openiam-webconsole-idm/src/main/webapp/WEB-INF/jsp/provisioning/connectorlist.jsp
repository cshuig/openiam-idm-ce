<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--@elvariable id="connectors" type="java.util.List<org.openiam.idm.srvc.mngsys.dto.ProvisionConnector>"--%>
<%--@elvariable id="connector" type="org.openiam.idm.srvc.mngsys.dto.ProvisionConnector"--%>
<%--@elvariable id="provConnectorTypeList" type="java.util.List<org.openiam.idm.srvc.meta.dto.MetadataType>"--%>
<%--@elvariable id="connectorListCommand" type="org.openiam.ui.webconsole.idm.web.mvc.provisioning.connectors.ConnectorListCommand" --%>

<ui:template command="${connectorListCommand}">

    <jsp:attribute name="searchScript">
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/connector/connector.search.js"></script>
    </jsp:attribute>

    <jsp:body>
            <form:form id="searchForm" method="GET" commandName="connectorListCommand">
                <div id="title" class="title">
                    <spring:message code="openiam.ui.idm.prov.connlist.header"/>
                </div>
                <div class="frameContentDivider">
                    <table cellspacing="1" id="tableOne" class="yui" width="100%">
                        <thead>
                        <tr>
                            <td class="filter">
                                <form:select id="provConnectorTypeId" path="connectorTypeId">
                                    <option value=""><spring:message code="openiam.ui.idm.prov.connlist.provConnectorTypeId.defaultmsg"/></option>
                                   <c:if test="${fn:length(provConnectorTypeList) gt 0}">
                                     <form:options items="${provConnectorTypeList}" itemLabel="description" itemValue="id" />
                                   </c:if>
                                </form:select>
                            </td>
                            <td class="filter" colspan="2">
                                <spring:message code="openiam.ui.idm.prov.connlist.searchInpt.label"/>
                                <form:input id="connectorName" path="connectorName" maxlength="30" size="30" type="text" />
                                <img id="filterClearOne" class="clearInput" src="/openiam-ui-static/plugins/tablesorter/img/cross.png" title='<spring:message code="openiam.ui.idm.prov.connector.clearFilter" />' />
                                <button type="submit"><spring:message code="openiam.ui.idm.prov.connlist.searchBtn"/></button>
                            </td>
                        </tr>
                        <tr>
                            <th><spring:message code="openiam.ui.idm.prov.table.col.name"/></th>
                            <th><spring:message code="openiam.ui.idm.prov.table.col.type"/></th>
                            <th><spring:message code="openiam.ui.idm.prov.table.col.url"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${!empty connectors and fn:length(connectors) >= 0}">
                                <c:forEach var="connector" items="${connectors}">
                                    <tr entityId="${connector.connectorId}">
                                        <td>
                                            <a href="connector.html?id=${connector.connectorId}">
                                                ${connector.name}
                                            </a>
                                        </td>
                                        <td>
                                            ${connector.metadataTypeId}
                                        </td>
                                        <td>
                                            ${connector.serviceUrl}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="3">
                                        <spring:message code="openiam.ui.idm.prov.table.noresults"/>
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
                                <form:select class="pagesize" path="size" id="size" items="${connectorListCommand.pages}" />
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <form:input type="hidden" path="page" />
            </form:form>
    </jsp:body>
</ui:template>