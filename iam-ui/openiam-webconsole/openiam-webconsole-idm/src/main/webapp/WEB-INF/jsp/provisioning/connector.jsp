<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<%--@elvariable id="connectorCommand" type="org.openiam.ui.webconsole.idm.web.mvc.provisioning.connectors.ConnectorCommand"--%>

<ui:template>
    <jsp:body>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/connector/connector.edit.js"></script>
        <script type="text/javascript">
            OPENIAM = window.OPENIAM || {};
            OPENIAM.ENV = window.OPENIAM.ENV || {};
            OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.ConnectorId = <c:choose><c:when test="${! empty requestScope.connectorCommand.connectorId}">"${requestScope.connectorCommand.connectorId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.connectorCommand.connectorId}">"id=${requestScope.connectorCommand.connectorId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.Text = {
    				DeleteWarn : localeManager["openiam.ui.idm.prov.connector.warning.message"]
    			};
        </script>
       
            <form:form commandName="connectorCommand" method="POST">
                <form:hidden id="actionType" path="submitType" />
                <div id="title" class="title">
                    <spring:message code="openiam.ui.idm.prov.conn_edit.header"/>
                </div>
                <div class="frameContentDivider">
                <table cellpadding="8px" align="center" class="fieldset">
                <thead>
                <tr>
                    <th colspan="2">
                        <spring:hasBindErrors name="connectorCommand">
                            <div class="alert alert-error">
                                <form:errors path="*" cssClass="error"/>
                            </div>
                        </spring:hasBindErrors>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${! empty connectorCommand.connectorId}">
                    <tr>
                        <td><spring:message code="openiam.ui.idm.prov.conn.field.id"/></td>
                        <td>
                            <form:input path="connectorId" size="32" cssClass="full rounded" maxlength="32" readonly="true" />
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                        <label class="required"><spring:message code="openiam.ui.idm.prov.conn.field.name"/></label>
                    </td>
                    <td>
                        <spring:message code='openiam.ui.idm.prov.conn.title.name' var="nameLabel"/>
                        <form:input path="name" cssClass="full rounded _input_tiptip" size="40" maxlength="40" title="${nameLabel}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label><spring:message code="openiam.ui.idm.prov.conn.field.type"/></label>
                    </td>
                    <td>
                        <spring:message code='openiam.ui.idm.prov.conn.title.type' var="metadataTypeIdLabel"/>
                        <form:input path="metadataTypeId" cssClass="full rounded _input_tiptip" title="${metadataTypeIdLabel}" size="40" maxlength="40" />
                    </td>
                </tr>
                <tr>
                    <td>
                       <label class="required"><spring:message code="openiam.ui.idm.prov.conn.field.protocol"/></label>
                    </td>
                    <td>
                        <spring:message code='openiam.ui.idm.prov.conn.title.protocol' var="clientCommProtocolLabel"/>
                        <form:select cssClass="rounded select _input_tiptip" title="${clientCommProtocolLabel}" path="clientCommProtocol">
                            <spring:message code="openiam.ui.common.value.pleaseselect" var="selectLabel" />
                            <form:option label="${selectLabel}" value="" />
                            <spring:message code="openiam.ui.idm.prov.connector.SSL" var="selectSSL" />
                            <form:option label="${selectSSL}" value="SSL" />
                            <spring:message code="openiam.ui.idm.prov.connector.CLEAR" var="selectClear" />
                            <form:option label="${selectClear}" value="CLEAR" />
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label class="required"><spring:message code="openiam.ui.idm.prov.conn.field.interface"/></label>
                    </td>
                    <td>
                        <spring:message code='openiam.ui.idm.prov.conn.title.interface' var="connectorInterfaceLabel"/>
                        <form:select cssClass="rounded select _input_tiptip" title="${connectorInterfaceLabel}" path="connectorInterface">
                            <spring:message code="openiam.ui.idm.prov.connector.local" var="selectLocal" />
                            <form:option value="LOCAL" label="${selectLocal}" />
                            <spring:message code="openiam.ui.idm.prov.connector.remote" var="selectRemote" />
                            <form:option value="REMOTE" label="${selectRemote}" />
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label><spring:message code="openiam.ui.idm.prov.conn.field.serviceurl"/></label>
                    </td>
                    <td>
                        <spring:message code='openiam.ui.idm.prov.conn.title.serviceurl' var="serviceUrlLabel"/>
                        <form:input path="serviceUrl" cssClass="full rounded _input_tiptip" title="${serviceUrlLabel}" size="80" maxlength="100" />
                    </td>
                </tr>

                <tr>
                    <td>
                        <label><spring:message code="openiam.ui.idm.prov.conn.field.servicenamespace"/></label>
                    </td>
                    <td>
                        <spring:message code='openiam.ui.idm.prov.conn.title.servicenamespace' var="serviceNameSpaceLabel"/>
                        <form:input path="serviceNameSpace" cssClass="full rounded _input_tiptip" title="${serviceNameSpaceLabel}" size="80" maxlength="100" />
                    </td>
                </tr>

                <tr>
                    <td>
                        <label><spring:message code="openiam.ui.idm.prov.conn.field.serviceport"/></label>
                    </td>
                    <td>
                        <spring:message code='openiam.ui.idm.prov.conn.title.serviceport' var="servicePortLabel"/>
                        <form:input path="servicePort" cssClass="full rounded _input_tiptip" title="${servicePortLabel}" size="60" maxlength="60" />
                    </td>
                </tr>

                </tbody>
                <tfoot>
                <tr>
                    <td colspan="2">
                        <ul class="formControls">
                            <li class="leftBtn">
                                <a href="javascript:void(0)">
                                    <input type="submit" class="redBtn" value='<spring:message code="openiam.ui.common.save" />' />
                                </a>
                            </li>
                            <li class="leftBtn">
                                <a href="/webconsole-idm/provisioning/connectorlist.html" class="whiteBtn"><spring:message code="openiam.ui.common.cancel" /></a>
                            </li>
                            <c:if test="${! empty connectorCommand.connectorId}">
                                <li  class="rightBtn">
                                    <a id="deleteProvider"  class="redBtn"><spring:message code="openiam.ui.common.delete" /></a>
                                </li>
                            </c:if>
                        </ul>
                    </td>
                </tr>
                </tfoot>
                </table>
                </div>
            </form:form>
    </jsp:body>
</ui:template>