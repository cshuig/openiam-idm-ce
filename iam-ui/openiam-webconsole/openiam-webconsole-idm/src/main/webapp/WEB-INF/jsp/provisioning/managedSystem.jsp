<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <spring:message code="openiam.ui.idm.prov.mngsys.title.managesSystem"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
          type="text/css"/>
    <link href="/openiam-ui-static/plugins/multiselect/css/multiselect.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet"
          type="text/css"/>
    <link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css"/>
    <openiam:overrideCSS/>

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/multiselect/js/multiselect.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
    <script type="text/javascript"
            src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/mngsys/managedsys.edit.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/metadata.type.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/attribute.table.edit.js"></script>

    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ManagedSysId = <c:choose><c:when test="${! empty requestScope.mngSystemCommand.id}">"${requestScope.mngSystemCommand.id}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ObjectSearchId = <c:choose><c:when test="${! empty requestScope.mngSystemCommand.objectSearchId}">"${requestScope.mngSystemCommand.objectSearchId}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ObjectSearchIdGroup = <c:choose><c:when test="${! empty requestScope.mngSystemCommand.objectSearchIdGroup}">"${requestScope.mngSystemCommand.objectSearchIdGroup}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ResourceId = <c:choose><c:when test="${! empty requestScope.mngSystemCommand.resourceId}">"${requestScope.mngSystemCommand.resourceId}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.mngSystemCommand.id}">"id=${requestScope.mngSystemCommand.id}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Resource = <c:choose><c:when test="${! empty requestScope.resourceAsJSON}">${requestScope.resourceAsJSON}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MngSysProps = <c:choose><c:when test="${not empty requestScope.mngSysPropsAsJSON}">${requestScope.mngSysPropsAsJSON}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Text = {
            DeleteWarn: localeManager["openiam.ui.idm.prov.mngsys.warning.message"]
        }
    </script>
</head>
<body>
<div id="title" class="title">
    <c:choose>
        <c:when test="${! empty requestScope.mngSystemCommand.id}">
            <spring:message
                    code="openiam.ui.idm.prov.mngsys.title.editManagesSystem"/>: ${requestScope.mngSystemCommand.name}
        </c:when>
        <c:otherwise>
            <spring:message code="openiam.ui.idm.prov.mngsys.title.createManagesSystem"/>
        </c:otherwise>
    </c:choose>
</div>
<div class="frameContentDivider">
    <table cellpadding="8px" align="center" class="fieldset">
        <tbody>
        <c:if test="${! empty requestScope.linkedResource}">
            <tr>
                <td><spring:message code="openiam.ui.idm.prov.mngsys.field.linked_resource"/>
                </td>
                <td><a
                        href="/webconsole/editResource.html?id=${requestScope.linkedResource.id}"
                        >${requestScope.linkedResource.coorelatedName}</a></td>
            </tr>
        </c:if>
        <tr>
            <td><label class="required"> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.name"
                    />
            </label></td>
            <td><input id="name" name="name" type="text" class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.name}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.name'/>"
                    /></td>
        </tr>
        <!--<tr>
						<td>
                               <label>
                               	<spring:message code="openiam.ui.idm.prov.mngsys.field.description" />
                               </label>
                           </td>
                           <td>
                           	<input id="description" name="description" type="text" class="full rounded _input_tiptip" value="${requestScope.mngSystemCommand.description}" title="<spring:message code='openiam.ui.idm.prov.mngsys.title.description'/>" />
                           </td>
                       </tr>
                       -->
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.status"/>
            </label></td>
            <td><select id="status" class="rounded select _input_tiptip"
                        title="<spring:message code='openiam.ui.idm.prov.mngsys.title.status'/>"
                    >
                <option value="ACTIVE"
                        <c:if test="${requestScope.mngSystemCommand.status eq 'ACTIVE'}">selected="selected"</c:if>
                        ><fmt:message key="openiam.ui.common.active"/></option>
                <option value="IN-ACTIVE"
                        <c:if test="${requestScope.mngSystemCommand.status eq 'IN-ACTIVE'}">selected="selected"</c:if>
                        ><fmt:message key="openiam.ui.common.inactive"/></option>
            </select></td>
        </tr>
        <c:if test="${! empty requestScope.connectors}">
            <tr>
                <td><label class="required"> <spring:message
                        code="openiam.ui.idm.prov.mngsys.field.connector"
                        />
                </label></td>
                <td><select id="connectorId" class="rounded select _input_tiptip"
                            title="<spring:message code='openiam.ui.idm.prov.mngsys.title.connector'/>"
                        >
                    <option value=""><fmt:message key="openiam.ui.common.value.select"/></option>
                    <c:forEach var="bean" items="${requestScope.connectors}">
                        <option value="${bean.id}"
                                <c:if test="${requestScope.mngSystemCommand.connectorId eq bean.id}">selected="selected"</c:if>
                                >${bean.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
        </c:if>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.changedByEndUser"/>
            </label></td>
            <td>
                <input id="changedByEndUser" type="checkbox" class="_input_tiptip"
                       <c:if test="${requestScope.mngSystemCommand.changedByEndUser}">checked="checked"</c:if>
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.changedByEndUser'/>"
                        />
            </td>
        </tr>

        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.host_url"/>
            </label></td>
            <td><input id="hostUrl" name="hostUrl" type="text" class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.hostUrl}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.host_url'/>"
                    /></td>
        </tr>
        <tr>
            <td><label><spring:message code="openiam.ui.idm.prov.mngsys.field.port"/></label></td>
            <td><input id="port" name="port" type="text" class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.port}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.port'/>"
                    /></td>
        </tr>

        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.protocol"/>
            </label></td>
            <td><select id="clientCommProtocol" class="rounded select _input_tiptip"
                        title="<spring:message code='openiam.ui.idm.prov.mngsys.title.protocol'/>"
                    >
                <option value=""><fmt:message key="openiam.ui.common.value.select"/></option>
                <option value="SSL"
                        <c:if test="${requestScope.mngSystemCommand.clientCommProtocol eq 'SSL'}">selected="selected"</c:if>
                        >SSL
                </option>
                <option value="CLEAR"
                        <c:if test="${requestScope.mngSystemCommand.clientCommProtocol eq 'CLEAR'}">selected="selected"</c:if>
                        >CLEAR
                </option>
            </select>
                <c:if test="${! empty requestScope.mngSystemCommand.id and requestScope.mngSystemCommand.clientCommProtocol eq 'SSL'}">
                    <a id="requestSSLCert" href="javascript:void(0)">
                        <input type="submit" class="redBtn"
                               value='<fmt:message key="openiam.ui.idm.prov.mngsys.button.requestSSLCert" />'/>
                    </a>
                </c:if>
            </td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.jdbc_url"/>
            </label></td>
            <td><input id="jdbcDriverUrl" name="jdbcDriverUrl" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.jdbcDriverUrl}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.jdbc_url'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.conn_str"/>
            </label></td>
            <td><input id="connectionString" name="connectionString" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.connectionString}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.conn_str'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.login"/>
            </label></td>
            <td><input id="login" name="login" type="text" class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.login}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.login'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.passw"/>
            </label></td>
            <td><input id="password" name="password" type="password"
                       class="full rounded _input_tiptip" value="${requestScope.mngSystemCommand.password}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.passw'/>"
                    /></td>
        </tr>
        <!-- User Match Fields -->
        <tr>
            <td><label style="font-weight: bold"> <spring:message
                    code="openiam.ui.idm.prov.mngsys.matchUser"
                    />
            </label></td>
            <td></td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.keyField"/>
            </label></td>
            <td><input id="keyField" name="keyField" type="text"
                       class="full rounded _input_tiptip" value="${requestScope.mngSystemCommand.keyField}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.keyField'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.baseDn"/>
            </label></td>
            <td><input id="baseDn" name="baseDn" type="text" class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.baseDn}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.baseDn'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.searchBaseDn"
                    />
            </label></td>
            <td><input id="searchBaseDn" name="searchBaseDn" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.searchBaseDn}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.searchBaseDn'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.searchFilter"
                    />
            </label></td>
            <td><textarea id="searchFilter" name="searchFilter"
                          class="full rounded _input_tiptip"
                          title="<spring:message code='openiam.ui.idm.prov.mngsys.title.searchFilter'/>"
                    >${requestScope.mngSystemCommand.searchFilter}</textarea></td>
        </tr>

        <!-- Group Match Fields -->
        <tr>
            <td><label style="font-weight: bold"> <spring:message
                    code="openiam.ui.idm.prov.mngsys.matchGroup"
                    />
            </label></td>
            <td></td>
        </tr>

        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.skipGroupProvision"/>
            </label></td>
            <td><input id="skipGroupProvision" type="checkbox" class="_input_tiptip"
                       <c:if test="${requestScope.mngSystemCommand.skipGroupProvision}">checked="checked"</c:if>
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.skipGroupProvision'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.keyField"/>
            </label></td>
            <td><input id="keyFieldGroup" name="keyFieldGroup" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.keyFieldGroup}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.keyField'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.baseDn"/>
            </label></td>
            <td><input id="baseDnGroup" name="baseDnGroup" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.baseDnGroup}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.baseDn'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.searchBaseDn"
                    />
            </label></td>
            <td><input id="searchBaseDnGroup" name="searchBaseDnGroup" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.searchBaseDnGroup}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.searchBaseDn'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.searchFilter"
                    />
            </label></td>
            <td><input id="searchFilterGroup" name="searchFilterGroup" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.searchFilterGroup}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.searchFilter'/>"
                    /></td>
        </tr>
        <tr>
            <td><label style="font-weight: bold"> <spring:message
                    code="openiam.ui.idm.prov.mngsys.customConfiguration"
                    />
            </label></td>
            <td></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.searchScope"
                    />
            </label></td>
            <td><select id="searchScope" class="rounded select _input_tiptip"
                        title="<spring:message code='openiam.ui.idm.prov.mngsys.title.searchScope'/>"
                    >
                <c:forEach var="scope" items="${requestScope.searchScopeItems}">
                    <option value="${scope}"
                            <c:if test="${requestScope.mngSystemCommand.searchScope eq scope}">selected="selected"</c:if>
                            >${scope.label}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.primaryRepository"
                    />
            </label></td>
            <td><input id="primaryRepository" type="checkbox" class="_input_tiptip"
                       <c:if test="${requestScope.mngSystemCommand.primaryRepository eq 1}">checked="checked"</c:if>
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.primaryRepository'/>"
                    /></td>
        </tr>
        <c:if test="${! empty requestScope.systems}">
            <tr>
                <td><label> <spring:message
                        code="openiam.ui.idm.prov.mngsys.field.secondaryRepositoryId"
                        />
                </label></td>
                <td><select id="secondaryRepositoryId" class="rounded select _input_tiptip"
                            title="<spring:message code='openiam.ui.idm.prov.mngsys.title.secondaryRepositoryId'/>"
                        >
                    <option value=""><fmt:message key="openiam.ui.common.value.select"/></option>
                    <c:forEach var="bean" items="${requestScope.systems}">
                        <option value="${bean.id}"
                                <c:if test="${requestScope.mngSystemCommand.secondaryRepositoryId eq bean.id}">selected="selected"</c:if>
                                >${bean.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
        </c:if>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.updateSecondary"
                    />
            </label></td>
            <td><input id="updateSecondary" type="checkbox" class="_input_tiptip"
                       <c:if test="${requestScope.mngSystemCommand.updateSecondary eq 1}">checked="checked"</c:if>
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.updateSecondary'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message code="openiam.ui.idm.prov.mngsys.field.handler5"/>
            </label></td>
            <td><select id="handler5" class="rounded select _input_tiptip"
                        title="<spring:message code='openiam.ui.idm.prov.mngsys.title.handler5'/>"
                    >
                <option value=""><fmt:message key="openiam.ui.common.value.select"/></option>
                <option value="LDAP"
                        <c:if test="${requestScope.mngSystemCommand.handler5 eq 'LDAP'}">selected="selected"</c:if>
                        ><fmt:message key="openiam.ui.idm.prov.mngsys.LDAP"/></option>
                <option value="ACTIVE_DIRECTORY"
                        <c:if test="${requestScope.mngSystemCommand.handler5 eq 'ACTIVE_DIRECTORY'}">selected="selected"</c:if>
                        ><fmt:message key="openiam.ui.idm.prov.mngsys.AD"/></option>
            </select></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.addHandler"
                    />
            </label></td>
            <td><input id="addHandler" name="addHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.addHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.addHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.modifyHandler"
                    />
            </label></td>
            <td><input id="modifyHandler" name="modifyHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.modifyHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.modifyHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.deleteHandler"
                    />
            </label></td>
            <td><input id="deleteHandler" name="deleteHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.deleteHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.deleteHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.passwordHandler"
                    />
            </label></td>
            <td><input id="passwordHandler" name="passwordHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.passwordHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.passwordHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.suspendHandler"
                    />
            </label></td>
            <td><input id="suspendHandler" name="suspendHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.suspendHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.suspendHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.resumeHandler"
                    />
            </label></td>
            <td><input id="resumeHandler" name="resumeHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.resumeHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.resumeHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.searchHandler"
                    />
            </label></td>
            <td><input id="searchHandler" name="searchHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.searchHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.searchHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.lookupHandler"
                    />
            </label></td>
            <td><input id="lookupHandler" name="lookupHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.lookupHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.lookupHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.testConnectionHandler"
                    />
            </label></td>
            <td><input id="testConnectionHandler" name="testConnectionHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.testConnectionHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.testConnectionHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.reconcileResourceHandler"
                    />
            </label></td>
            <td><input id="reconcileResourceHandler" name="reconcileResourceHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.reconcileResourceHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.reconcileResourceHandler'/>"
                    /></td>
        </tr>
        <tr>
            <td><label> <spring:message
                    code="openiam.ui.idm.prov.mngsys.field.attributeNamesHandler"
                    />
            </label></td>
            <td><input id="attributeNamesHandler" name="attributeNamesHandler" type="text"
                       class="full rounded _input_tiptip"
                       value="${requestScope.mngSystemCommand.attributeNamesHandler}"
                       title="<spring:message code='openiam.ui.idm.prov.mngsys.title.attributeNamesHandler'/>"
                    /></td>
        </tr>
        </tbody>
    </table>
    <c:if test="${not empty requestScope.linkedResource}">
        <div>
            <div class="title">
                <fmt:message key="openiam.ui.common.attributes"/>
            </div>
            <div class="frameContentDivider">
                <div id="attributesContainer"></div>
            </div>
        </div>
    </c:if>
    <table width="100%">
        <tfoot>
        <tr>
            <td colspan="2">
                <ul class="formControls">
                    <li class="leftBtn"><a id="save" href="javascript:void(0)"> <input
                            type="submit" class="redBtn" value='<fmt:message key="openiam.ui.common.save" />'
                            />
                    </a></li>
                    <c:if test="${! empty requestScope.mngSystemCommand.id}">
                        <li class="leftBtn"><a id="deleteBean" href="javascript:void(0);"
                                               class="redBtn"
                                ><fmt:message key="openiam.ui.common.delete"/></a></li>
                    </c:if>
                    <li class="leftBtn"><a href="mngsystemlist.html" class="whiteBtn"><fmt:message
                            key="openiam.ui.common.cancel"/></a>
                    </li>
                    <c:if test="${! empty requestScope.mngSystemCommand.id}">
                        <li class="rightBtn"><a id="testConnection"
                                                href="javascript:void(0)"
                                > <input type="submit" class="redBtn"
                                         value='<fmt:message key="openiam.ui.idm.prov.mngsys.button.testConnect" />'/>
                        </a></li>
                    </c:if>
                </ul>
            </td>
        </tr>
        </tfoot>
    </table>
</div>
<div id="editDialog"></div>
</body>
</html>