<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.openiam.ui.constants.CommonFrequencyOptions" %>

<%--@elvariable id="synchCommand" type="org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization.SynchronizationCommand"--%>

<ui:template>
<jsp:body>
<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css"/>
<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet"
      type="text/css"/>
<link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css"/>
<link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
    OPENIAM = window.OPENIAM || {};
    OPENIAM.ENV = window.OPENIAM.ENV || {};
    OPENIAM.ENV.SynchConfigId = <c:choose><c:when test="${! empty requestScope.synchCommand.synchConfigId}">"${requestScope.synchCommand.synchConfigId}"
    </c:when><c:otherwise>""</c:otherwise></c:choose>;
    OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
    OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.synchCommand.synchConfigId}">"id=${requestScope.synchCommand.synchConfigId}"
    </c:when><c:otherwise>null</c:otherwise></c:choose>;
    OPENIAM.ENV.Organization = <c:choose><c:when test="${! empty requestScope.organization}">${requestScope.organization}</c:when><c:otherwise>null</c:otherwise></c:choose>;
</script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/synch/sync.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/synch/edit.synch.config.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/search/organization.search.js"></script>
<script type="text/javascript"
        src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
<script type="text/javascript">
    var pressSubmit = function (actionType) {
        jQuery('#actionType').val(actionType);
        jQuery('form#synchCommand').submit();
        return false;
    }
</script>

<form:form commandName="synchCommand" method="POST" enctype="multipart/form-data">
<form:hidden id="actionType" path="submitType" value="submit"/>
<div id="title" class="title">
    <spring:message code="openiam.ui.idm.synch.synch_edit.header"/>
</div>
<div class="frameContentDivider">
<table cellpadding="8px" align="center" class="fieldset">
<thead>
<tr>
    <th colspan="2">
        <spring:hasBindErrors name="synchCommand">
            <div class="alert alert-error">
                <form:errors path="*" cssClass="error"/>
            </div>
        </spring:hasBindErrors>
        <c:if test="${! empty messages}">
            ${messages}
        </c:if>
    </th>
</tr>
</thead>
<tbody>
<tr class="common-row">
    <td>
        <label class="required"><spring:message code="openiam.ui.idm.synch.synch_edit.field.name"/></label>
    </td>
    <td>
        <c:if test="${! empty synchCommand.synchConfigId}">
            <form:hidden id="synchConfigId" path="synchConfigId"/>
        </c:if>
        <form:hidden id="managedSysId" path="managedSysId"/>
        <spring:message var="nameTitle" code="openiam.ui.idm.synch.synch_edit.title.name"/>
        <form:input path="name" cssClass="full rounded _input_tiptip" size="60" maxlength="60" title="${nameTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.status"/></label>
    </td>
    <td>
        <spring:message var="statusTitle" code="openiam.ui.idm.synch.synch_edit.title.status"/>
        <form:select path="status" title="${statusTitle}" cssClass="rounded select _input_tiptip"
                     items="${statusItems}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label class="required"><spring:message code="openiam.ui.idm.synch.synch_edit.field.synchAdapter"/></label>
    </td>
    <td>
        <spring:message var="synchAdapterTitle" code="openiam.ui.idm.synch.synch_edit.title.synchAdapter"/>
        <form:select path="synchAdapter" title="${synchAdapterTitle}" cssClass="rounded select _input_tiptip"
                     items="${synchAdapterItems}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.customAdapterScript"/></label>
    </td>
    <td>
        <spring:message var="customAdapterScriptTitle"
                        code="openiam.ui.idm.synch.synch_edit.title.customAdapterScript"/>
        <form:input path="customAdatperScript" cssClass="full rounded _input_tiptip" size="80" maxlength="80"
                    title="${customAdapterScriptTitle}"/>
    </td>
</tr>

<tr class="common-row">
    <td>
        <span id="processRule_lbl"> <label><spring:message
                code="openiam.ui.idm.synch.synch_edit.field.processRule"/></label> </span>
    </td>
    <td>
        <spring:message var="processRuleTitle" code="openiam.ui.idm.synch.synch_edit.title.processRule"/>
        <form:select path="processRule" title="${processRuleTitle}" cssClass="rounded select _input_tiptip"
                     items="${processRuleItems}"/>
        <p id="soUser">
            <b><spring:message var="processRuleTitle" code="openiam.ui.webconsole.idm.synch.config.USER"/></b>
        </p>
    </td>
</tr>

<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.synchType"/></label>
    </td>
    <td>
        <spring:message var="synchTypeTitle" code="openiam.ui.idm.synch.synch_edit.title.synchType"/>
        <form:select path="synchType" title="${synchTypeTitle}" cssClass="rounded select _input_tiptip"
                     items="${synchTypeItems}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.synchFrequency"/></label>
    </td>
    <td>
        <spring:message var="synchFrequencyTitle" code="openiam.ui.idm.synch.synch_edit.title.synchFrequency"/>
        <form:select path="synchFrequency" title="${synchFrequencyTitle}" cssClass="rounded select _input_tiptip"
                     items="${synchFrequencyItems}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label>
            <fmt:message key="openiam.ui.idm.synch.synch_edit.field.organization"/>
        </label>
    </td>
    <td>
        <a id="organization" href="javascript:void(0);" class="entity-link organization ui-search-enabled"></a>
        <!-- This is here because this form doesn't use ajax form submission -->
        <input type="hidden" id="companyId" name="companyId" value="${requestScope.synchCommand.companyId}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label class="required"><spring:message code="openiam.ui.idm.synch.synch_edit.field.validationRule"/></label>
    </td>
    <td>
        <spring:message var="validationRuleTitle" code="openiam.ui.idm.synch.synch_edit.title.validationRule"/>
        <form:input path="validationRule" cssClass="full rounded _input_tiptip" size="80" maxlength="80"
                    title="${validationRuleTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.preSyncScript"/></label>
    </td>
    <td>
        <spring:message var="preSyncScriptTitle" code="openiam.ui.idm.synch.synch_edit.title.preSyncScript"/>
        <form:input path="preSyncScript" cssClass="full rounded _input_tiptip" size="80" maxlength="80"
                    title="${preSyncScriptTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.postSyncScript"/></label>
    </td>
    <td>
        <spring:message var="postSyncScriptTitle" code="openiam.ui.idm.synch.synch_edit.title.postSyncScript"/>
        <form:input path="postSyncScript" cssClass="full rounded _input_tiptip" size="80" maxlength="80"
                    title="${postSyncScriptTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.usePolicyMap"/></label>
    </td>
    <td>
        <spring:message var="usePolicyMapTitle" code="openiam.ui.idm.synch.synch_edit.title.usePolicyMap"/>
        <form:checkbox id="usePolicyMap" path="usePolicyMap" title="${usePolicyMapTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.useTransformationScript"/></label>
    </td>
    <td>
        <spring:message var="useTransformationScriptTitle"
                        code="openiam.ui.idm.synch.synch_edit.title.useTransformationScript"/>
        <form:checkbox id="useTransformationScript" path="useTransformationScript"
                       title="${useTransformationScriptTitle}"/>
    </td>
</tr>
<tr class="common-row policy-map-before-transform-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.policyMapBeforeTransformation"/></label>
    </td>
    <td>
        <spring:message var="policyMapBeforeTransformationTitle"
                        code="openiam.ui.idm.synch.synch_edit.title.policyMapBeforeTransformation"/>
        <form:checkbox id="policyMapBeforeTransformation" path="policyMapBeforeTransformation"
                       title="${policyMapBeforeTransformationTitle}"/>
    </td>
</tr>
<tr class="common-row transform-row">
    <td>
        <label class="required"><spring:message
                code="openiam.ui.idm.synch.synch_edit.field.transformationRule"/></label>
    </td>
    <td>
        <spring:message var="transformationRuleTitle" code="openiam.ui.idm.synch.synch_edit.title.transformationRule"/>
        <form:input path="transformationRule" cssClass="full rounded _input_tiptip" size="80" maxlength="80"
                    title="${transformationRuleTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.matchFieldName"/></label>
    </td>
    <td>
        <spring:message var="matchFieldNameTitle" code="openiam.ui.idm.synch.synch_edit.title.matchFieldName"/>
        <form:select path="matchFieldName" title="${matchFieldNameTitle}" cssClass="rounded select _input_tiptip"
                     items="${matchFieldNameItems}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.matchSrcFieldName"/></label>
    </td>
    <td>
        <spring:message var="matchSrcFieldNameTitle" code="openiam.ui.idm.synch.synch_edit.title.matchSrcFieldName"/>
        <form:input path="matchSrcFieldName" cssClass="full rounded _input_tiptip" size="40" maxlength="40"
                    title="${matchSrcFieldNameTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.customMatchAttr"/></label>
    </td>
    <td>
        <spring:message var="customMatchAttrTitle" code="openiam.ui.idm.synch.synch_edit.title.customMatchAttr"/>
        <form:input path="customMatchAttr" cssClass="full rounded _input_tiptip" size="40" maxlength="40"
                    title="${customMatchAttrTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.customMatchRule"/></label>
    </td>
    <td>
        <spring:message var="customMatchRuleTitle" code="openiam.ui.idm.synch.synch_edit.title.customMatchRule"/>
        <form:input path="customMatchRule" cssClass="full rounded _input_tiptip" size="100" maxlength="100"
                    title="${customMatchRuleTitle}"/>
    </td>
</tr>
<tr class="ws-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.wsScript"/></label>
    </td>
    <td>
        <spring:message var="wsScriptTitle" code="openiam.ui.idm.synch.synch_edit.title.wsScript"/>
        <form:input path="wsScript" cssClass="full rounded _input_tiptip" size="100" maxlength="100"
                    title="${wsScriptTitle}"/>
    </td>
</tr>
<tr class="ws-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.wsUrl"/></label>
    </td>
    <td>
        <spring:message var="wsUrlTitle" code="openiam.ui.idm.synch.synch_edit.title.wsUrl"/>
        <form:input path="wsUrl" cssClass="full rounded _input_tiptip" size="100" maxlength="100"
                    title="${wsUrlTitle}"/>
    </td>
</tr>
<tr class="csv-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.useSystemPath"/></label>
    </td>
    <td>
        <spring:message var="useSystemPathTitle" code="openiam.ui.idm.synch.synch_edit.title.useSystemPath"/>
        <form:checkbox path="useSystemPath" id="useSystemPath" title="${useSystemPathTitle}"/>
    </td>
</tr>
<tr class="csv-row system-path">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.fileName"/></label>
    </td>
    <td>
        <input type="text"
               <c:if test="${synchCommand.useSystemPath}">value="${synchCommand.fileName}"</c:if> name="fileName"
               id="fileName" class="full rounded _input_tiptip"/>
    </td>
</tr>
<tr class="csv-row file-upload">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.fileName"/></label>
    </td>
    <td>
        <c:if test="${not empty synchCommand.fileName and not synchCommand.useSystemPath}">
                                <span class="uploadFileArea">
                                    ${synchCommand.fileNameWTS}
                                    <a id="uploadNewFile" class="blue" href="javascript:void(0);">Upload a new one</a>
                                </span>
            <form:hidden path="hiddenFileName"/>
        </c:if>
        <input type="file" name="fileUpload" id="fileUpload" class="full rounded _input_tiptip" <c:if
                test="${not empty synchCommand.fileName and not synchCommand.useSystemPath}"> style="display:none"</c:if> />
    </td>
</tr>
<tr class="ldap-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.srcHost"/></label>
    </td>
    <td>
        <spring:message var="srcHostTitle" code="openiam.ui.idm.synch.synch_edit.title.srcHost"/>
        <form:input path="srcHost" cssClass="full rounded _input_tiptip" size="100" maxlength="100"
                    title="${srcHostTitle}"/>
    </td>
</tr>
<tr class="ldap-row db-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.srcLoginId"/></label>
    </td>
    <td>
        <spring:message var="srcLoginIdTitle" code="openiam.ui.idm.synch.synch_edit.title.srcLoginId"/>
        <form:input path="srcLoginId" cssClass="full rounded _input_tiptip" size="100" maxlength="100"
                    title="${srcLoginIdTitle}"/>
    </td>
</tr>
<tr class="ldap-row db-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.srcPassword"/></label>
    </td>
    <td>
        <spring:message var="srcPasswordTitle" code="openiam.ui.idm.synch.synch_edit.title.srcPassword"/>
        <form:input path="srcPassword" type="password" cssClass="full rounded _input_tiptip" size="100" maxlength="100"
                    title="${srcPasswordTitle}"/>
    </td>
</tr>
<tr class="db-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.driver"/></label>
    </td>
    <td>
        <spring:message var="driverTitle" code="openiam.ui.idm.synch.synch_edit.title.driver"/>
        <form:input path="driver" cssClass="full rounded _input_tiptip" size="50" maxlength="50"
                    title="${driverTitle}"/>
    </td>
</tr>
<tr class="db-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.connectionUrl"/></label>
    </td>
    <td>
        <spring:message var="connectionUrlTitle" code="openiam.ui.idm.synch.synch_edit.title.connectionUrl"/>
        <form:input path="connectionUrl" cssClass="full rounded _input_tiptip" size="100" maxlength="100"
                    title="${connectionUrlTitle}"/>
    </td>
</tr>
<tr class="common-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.query"/></label>
    </td>
    <td>
        <spring:message var="queryTitle" code="openiam.ui.idm.synch.synch_edit.title.query"/>
        <form:textarea path="query" cssClass="full rounded _input_tiptip" size="100" title="${queryTitle}"/>
    </td>
</tr>
<tr class="ldap-row">
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.baseDn"/></label>
    </td>
    <td>
        <spring:message var="baseDnTitle" code="openiam.ui.idm.synch.synch_edit.title.baseDn"/>
        <form:input path="baseDn" cssClass="full rounded _input_tiptip" size="50" maxlength="250"
                    title="${baseDnTitle}"/>
    </td>
</tr>
<tr class="ldap-row">
    <td>
        <label>
            <spring:message code="openiam.ui.idm.synch.synch_edit.field.attributeNamesLookup"/>
        </label>
    </td>
    <td>
        <spring:message var="attributeNamesLookupTitle"
                        code="openiam.ui.idm.synch.synch_edit.title.attributeNamesLookup"/>
        <form:input path="attributeNamesLookup" maxlength="120" size="50" class="full rounded _input_tiptip"
                    title="${attributeNamesLookupTitle}"/>
    </td>
</tr>
<tr class="ldap-row">
    <td>
        <label>
            <spring:message code="openiam.ui.idm.synch.synch_edit.field.searchScope"/>
        </label>
    </td>
    <td>
        <spring:message var="searchScopeTitle" code="openiam.ui.idm.synch.synch_edit.title.searchScope"/>
        <form:select path="searchScope" itemLabel="label" title="${searchScopeTitle}"
                     cssClass="rounded select _input_tiptip" items="${searchScopeItems}"/>
    </td>
</tr>
<tr>
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.queryTimeField"/></label>
    </td>
    <td>
        <spring:message var="queryTimeFieldTitle" code="openiam.ui.idm.synch.synch_edit.title.queryTimeField"/>
        <form:input path="queryTimeField" cssClass="full rounded _input_tiptip" size="50" maxlength="50"
                    title="${queryTimeFieldTitle}"/>
    </td>
</tr>
<tr>
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.lastExecTime"/></label>
    </td>
    <td>
        <spring:message var="lastExecTimeTitle" code="openiam.ui.idm.synch.synch_edit.title.lastExecTime"/>
        <form:input readonly="true" path="lastExecTime" cssClass="full rounded _input_tiptip" size="40" maxlength="40"
                    title="${lastExecTimeTitle}"/>
    </td>
</tr>
<tr>
    <td>
        <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.lastRecProcessed"/></label>
    </td>
    <td>
        <spring:message var="lastRecProcessedTitle" code="openiam.ui.idm.synch.synch_edit.title.lastRecProcessed"/>
        <form:input readonly="true" path="lastRecProcessed" cssClass="full rounded _input_tiptip" size="32"
                    maxlength="32" title="${lastRecProcessed}"/>
    </td>
</tr>
</tbody>
<tfoot>
<tr>
    <td colspan="2">
        <ul class="formControls">
            <li class="leftBtn">
                <a href="javascript:void(0)">
                    <input type="submit" class="redBtn" value='<spring:message code="openiam.ui.common.save" />'/>
                </a>
            </li>
            <c:if test="${! empty synchCommand.synchConfigId}">
                <li class="leftBtn">
                    <a id="deleteProvider" href="javascript:pressSubmit('delete');" class="redBtn"><spring:message
                            code="openiam.ui.common.delete"/></a>
                </li>
            </c:if>
            <li class="leftBtn">
                <a href="/webconsole-idm/provisioning/synchronizationlist.html" class="whiteBtn"><spring:message
                        code="openiam.ui.common.cancel"/></a>
            </li>
            <c:if test="${! empty synchCommand.synchConfigId}">
                <li class="rightBtn">
                    <a id="startSynch" href="#" class="redBtn"><spring:message
                            code="openiam.ui.webconsole.idm.synch.config.button.syncNow"/></a>
                </li>
            </c:if>
            <li class="rightBtn">
                <a id="testConnect" href="#" class="redBtn"><spring:message
                        code="openiam.ui.webconsole.idm.synch.config.button.testConnect"/></a>
            </li>
        </ul>
    </td>
</tr>
</tfoot>
</table>
</div>
</form:form>
<div id="editDialog"></div>
</jsp:body>
</ui:template>