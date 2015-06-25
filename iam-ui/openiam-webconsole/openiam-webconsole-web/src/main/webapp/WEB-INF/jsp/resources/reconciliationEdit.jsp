
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ page import="org.openiam.ui.constants.CommonFrequencyOptions"%>
<%@ page import="org.openiam.ui.constants.CommonStatusOptions"%>
<%@ page import="org.openiam.ui.constants.ReconciliationTextSeparatorOptions"%>
<%@ page import="org.openiam.idm.srvc.recon.service.ReconciliationSituationResponseOptions"%>
<%@ page import="org.openiam.ui.constants.ReconConfigTypeOptions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${titleOrganizatioName} - <fmt:message key="openiam.webconsole.resource.type.menu.edit.reconciliation.config" /></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />

    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css"/>
    <openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/resource/resource.recon.js"></script>
    <script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.ResourceId = <c:choose><c:when test="${! empty requestScope.reconCommand.reconConfig.resourceId}">"${requestScope.reconCommand.reconConfig.resourceId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.managedSystem.id}">"id=${requestScope.managedSystem.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.errorToken = <c:choose><c:when test="${! empty requestScope.errorToken}">"<spring:message code='${requestScope.errorToken.error.messageName}' />"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.successToken = <c:choose><c:when test="${! empty requestScope.successToken}">"<spring:message code='${requestScope.successToken.message.messageName}' />"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Text = {
				DeleteWarn : localeManager["openiam.ui.reconcilation.delete.warn"]
			};
            OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
		</script>
<script type="text/javascript">
    var matchTypeChanged = function(obj){
        var selectedItem = jQuery(obj).find("option:selected").val();
        var inputField = jQuery('#reconConfig\\.matchSrcFieldName');
        if("ATTRIBUTE"==selectedItem){
            jQuery(inputField).removeAttr('readonly');
        } else {
            jQuery(inputField).attr('readonly', true);
        }
        return true;
    };
    var customScriptFlagSwitch = function(obj) {
        var selectedItem = jQuery(obj).is(':checked');
        if(selectedItem == true) {
            jQuery('tr#scriptProcessorFlagRow').show();
        } else {
            jQuery('tr#scriptProcessorFlagRow').hide();
        }
        return true;
    }
</script>
</head>
<body>
      <c:choose>
            <c:when test="! empty requestScope.error">
            </c:when>
            <c:otherwise>
                  <div id="title" class="title"><fmt:message key="openiam.ui.reconcilation.config.res" />:
                        ${requestScope.reconCommand.resourceName}</div>
                  <form id="uploadForm" action="save-reconciliation-config.html" method="post"
                        enctype="multipart/form-data">
                        <div class="frameContentDivider">
                              <input type="hidden" id="reconConfig.reconConfigId" name="reconConfig.reconConfigId"
                                    value="${requestScope.reconCommand.reconConfig.reconConfigId}"
                              />
                              <input type="hidden" id="reconConfig.resourceId" name="reconConfig.resourceId"
                                    value="${requestScope.reconCommand.reconConfig.resourceId}"
                              />
                              <input type="hidden" id="reconConfig.managedSysId" name="reconConfig.managedSysId"
                                   value="${requestScope.reconCommand.reconConfig.managedSysId}"
                              />
                              <input type="hidden" id="reconConfig.execStatus" name="reconConfig.execStatus"
                                     value="${requestScope.reconCommand.reconConfig.execStatus.name()}"
                              />
                              <table cellpadding="8px" align="center" class="fieldset">
                                    <tbody>
                                        <tr>
                                            <td><label><spring:message code="openiam.ui.common.type"/></label></td>
                                            <td><select id="reconConfig.reconType" name="reconConfig.reconType"
                                                    <c:if test="${requestScope.reconCommand.reconConfig.reconConfigId != null}"> readonly='true' </c:if>
                                                        class="short select rounded">
                                                <c:forEach var="st"
                                                           items="<%=ReconConfigTypeOptions.values()%>">
                                                    <option
                                                            <c:if test="${st.value eq requestScope.reconCommand.reconConfig.reconType}">selected="selected"</c:if>
                                                            value="${st.value}"
                                                            >${st.value}</option>
                                                </c:forEach>
                                            </select></td>
                                        </tr>
                                        <tr>
                                            <td><label><spring:message code="openiam.ui.idm.reconcile.field.custom.processor.flag"/></label></td>
                                            <td><input type="checkbox"
                                                       id="reconConfig.useCustomScript"
                                                       name="useCustomScript"
                                                       class="short select rounded"
                                                       onchange="return customScriptFlagSwitch(this)"
                                            <c:if test="${requestScope.reconCommand.reconConfig.useCustomScript}"> checked="checked"</c:if>
                                                    ></td>
                                        </tr>
                                        <tr id="scriptProcessorFlagRow" <c:if test="${!requestScope.reconCommand.reconConfig.useCustomScript}"> style="display:none"</c:if>>
                                            <td>
                                                <label><spring:message code="openiam.ui.idm.reconcile.field.custom.processor"/></label>
                                            </td>
                                            <td><input name="reconConfig.customProcessorScript"
                                                       value="${requestScope.reconCommand.reconConfig.customProcessorScript}"
                                                       class="full rounded"
                                                       type='text' maxlength="120"/>
                                            </td>
                                        </tr>
                                          <tr>
                                                <td><fmt:message key="openiam.ui.common.status" /></td>
                                                <td><select id="reconConfig.status" name="reconConfig.status"
                                                      class="short select rounded">
                                                            <c:forEach var="st"
                                                                  items="<%=CommonStatusOptions.values()%>">
                                                                  <option
                                                                        <c:if test="${st.value eq requestScope.reconCommand.reconConfig.status}">selected="selected"</c:if>
                                                                        value="${st.value}"
                                                                  >${st.value}</option>
                                                            </c:forEach>
                                                </select></td>
                                          </tr>
                                          <tr>
                                                <td><fmt:message key="openiam.ui.common.frequency" /></td>
                                                <td><select id="reconConfig.frequency" name="reconConfig.frequency"
                                                      class="short select rounded"
                                                >
                                                            <option value="">-<fmt:message key="openiam.ui.common.please.select" />-</option>
                                                            <c:forEach var="freq"
                                                                  items="<%=CommonFrequencyOptions.values()%>"
                                                            >
                                                                  <option
                                                                        <c:if test="${freq.value eq requestScope.reconCommand.reconConfig.frequency}">selected="selected"</c:if>
                                                                        value="${freq.value}"
                                                                  >${freq.label}</option>
                                                            </c:forEach>
                                                </select></td>
                                          </tr>
                                          <tr>
                                                <td><fmt:message key="openiam.ui.manual.reconciliation.title" /></td>
                                                <td><input type="checkbox"
                                                      id="reconConfig.manualReconciliationFlag"
                                                      name="reconConfig.manualReconciliationFlag"
                                                      class="short select rounded"
                                                      <c:if test="${requestScope.reconCommand.reconConfig.manualReconciliationFlag}"> checked="checked"</c:if>
                                                ></td>
                                          </tr>
                                          <c:if test="${requestScope.reconCommand.isCSV}">

                                                <tr class="fileUploadHolder">
                                                      <td><fmt:message key="openiam.ui.common.source.csv.title" /></td>
                                                      <td id="csvFileUploader"><input type="file"
                                                            id="csvFileUpload" name="csvFileUpload" class="full rounded"/>
                                                          <c:if test="${! empty requestScope.reconCommand.CSVFileName}">
                                                                  <td id="csvFileUploadName"><a
                                                                        href="javascript:void(0)"
                                                                  >${requestScope.reconCommand.CSVFileName}</a></td>
                                                            </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                      <td><fmt:message key="openiam.ui.reconcilation.separator" /></td>
                                                      <td><div>
                                                                  <select id="reconConfig.separator"
                                                                        name="reconConfig.separator"
                                                                        class="short select rounded">
                                                                        <c:forEach var="ts"
                                                                              items="<%=ReconciliationTextSeparatorOptions.values()%>">
                                                                              <option
                                                                                    <c:if test="${ts.value eq requestScope.reconCommand.reconConfig.separator}">selected="selected"</c:if>
                                                                                    value="${ts.value}"
                                                                              >${ts.value}</option>
                                                                        </c:forEach>
                                                                  </select>
                                                            </div>
                                                            <div>
                                                                  <select id="reconConfig.endOfLine"
                                                                        name="reconConfig.endOfLine"
                                                                        class="short select rounded">
                                                                        <c:forEach var="eol"
                                                                              items="<%=ReconciliationTextSeparatorOptions.values()%>">
                                                                              <option
                                                                                    <c:if test="${eol.value eq requestScope.reconCommand.reconConfig.endOfLine}">selected="selected"</c:if>
                                                                                    value="${eol.value}">${eol.value}</option>
                                                                        </c:forEach>
                                                                  </select>
                                                            </div></td>
                                                </tr>
                                          </c:if>
                                          <tr>
                                                <td><fmt:message key="openiam.ui.common.email.address.notification.completion" /></td>
                                                <td><input id="reconConfig.notificationEmailAddress"
                                                      class="full rounded"
                                                      name="reconConfig.notificationEmailAddress"
                                                      value="${requestScope.reconCommand.reconConfig.notificationEmailAddress}"
                                                      type='text' maxlength="100"/>
                                                </td>
                                          </tr>
                                          <tr>
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.reconcile.field.targetSystemMatchScript"/></label>
                                              </td>
                                              <td><input name="reconConfig.targetSystemMatchScript"
                                                      value="${requestScope.reconCommand.reconConfig.targetSystemMatchScript}"
                                                      class="full rounded"
                                                      type='text' maxlength="120"/>
                                              </td>
                                          </tr>
                                          <tr>
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.reconcile.field.targetSystemSearchFilter"/></label>
                                              </td>
                                              <td><textarea name="reconConfig.targetSystemSearchFilter"
                                                   class="full rounded"
                                                   type='text'>${requestScope.reconCommand.reconConfig.targetSystemSearchFilter}</textarea>
                                              </td>
                                          </tr>
                                          <tr>
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.reconcile.field.matchScript"/></label>
                                              </td>
                                              <td><input name="reconConfig.matchScript"
                                                         class="full rounded"
                                                         value='${requestScope.reconCommand.reconConfig.matchScript}' type='text' maxlength="120"/>
                                              </td>
                                          </tr>
                                          <tr>
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.reconcile.field.searchFilter"/></label>
                                              </td>
                                              <td><input name="reconConfig.searchFilter"
                                                       value='<c:out value="${requestScope.reconCommand.reconConfig.searchFilter}" escapeXml="true" />'
                                                       class="full rounded"
                                                       type='text' maxlength="200"/>
                                              </td>
                                          </tr>
                                          <tr>
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.reconcile.field.updatedSince"/></label>
                                              </td>
                                              <td>
                                                  <fmt:formatDate value="${requestScope.reconCommand.reconConfig.updatedSince}" pattern="${requestScope.dateFormatJSTL}" var="dv" />
                                                  <input name="reconConfig.updatedSince" type="text" class="full rounded date"
                                                         value="${dv}" readonly="readonly"/>
                                              </td>
                                          </tr>
                                          <tr class="common-row">
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.matchFieldName"/></label>
                                              </td>
                                              <td>
                                                  <spring:message var="matchFieldNameTitle" code="openiam.ui.idm.synch.synch_edit.title.matchFieldName" />
                                                  <select id="reconConfig.matchFieldName"
                                                          name="reconConfig.matchFieldName"
                                                          class="short select rounded"
                                                          onchange="matchTypeChanged(this)">
                                                      <c:forEach var="eol"
                                                                 items="${matchFieldNameItems}">
                                                          <option
                                                                  <c:if test="${eol.key eq requestScope.reconCommand.reconConfig.matchFieldName}">selected="selected"</c:if>
                                                                  value="${eol.key}">${eol.value}</option>
                                                      </c:forEach>
                                                  </select>

                                              </td>
                                          </tr>
                                          <tr class="common-row">
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.matchSrcFieldName"/></label>
                                              </td>
                                              <td>
                                                  <spring:message var="matchSrcFieldNameTitle" code="openiam.ui.idm.synch.synch_edit.title.matchSrcFieldName" />
                                                  <input id="reconConfig.matchSrcFieldName"
                                                         name="reconConfig.matchSrcFieldName"
                                                         disabled="disabled"
                                                         class="full rounded"
                                                         value="${requestScope.reconCommand.reconConfig.matchSrcFieldName}"
                                                         type='text' maxlength="40" title="${matchSrcFieldNameTitle}" />
                                              </td>
                                          </tr>
                                          <tr class="common-row">
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.customMatchAttr"/></label>
                                              </td>
                                              <td>
                                                  <spring:message var="customMatchAttrTitle" code="openiam.ui.idm.synch.synch_edit.title.customMatchAttr" />
                                                  <input id="reconConfig.customMatchAttr"
                                                         name="reconConfig.customMatchAttr"
                                                         class="full rounded"
                                                         value="${requestScope.reconCommand.reconConfig.customMatchAttr}"
                                                         type='text' maxlength="40" title="${customMatchAttrTitle}" />
                                              </td>
                                          </tr>

                                          <tr class="common-row">
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.synch.synch_edit.field.customMatchRule"/></label>
                                              </td>
                                              <td>
                                                  <spring:message var="customMatchRuleTitle" code="openiam.ui.idm.synch.synch_edit.title.customMatchRule" />
                                                  <input id="reconConfig.customIdentityMatchScript"
                                                         name="reconConfig.customIdentityMatchScript"
                                                         class="full rounded"
                                                         value="${requestScope.reconCommand.reconConfig.customIdentityMatchScript}"
                                                         type='text' maxlength="100" title="${customMatchRuleTitle}" />
                                              </td>
                                          </tr>
                                          <tr class="common-row">
                                                <td><fmt:message key="openiam.ui.common.report" /></td>
                                                <td><select id="reportType" name="reportType"
                                                      class="short select rounded"
                                                >
                                                            <option value="HTML" selected="selected">HTML</option>
                                                            <%--<option value="CSV">CSV</option>--%>
                                                </select><input id="reconReport" type="button" class="redBtn" value="<fmt:message key='openiam.ui.common.report' />"></td>
                                          </tr>
                                          <tr class="common-row">
                                              <td>
                                                  <label><spring:message code="openiam.ui.idm.synch.synch_edit.title.lastExecTime"/></label>
                                              </td>
                                              <td>
                                                  <spring:message var="lastExecTimeTitle" code="openiam.ui.idm.synch.synch_edit.title.lastExecTime" />
                                                  <fmt:formatDate value="${requestScope.reconCommand.reconConfig.lastExecTime}" pattern="${requestScope.dateFormatJSTLFull}" var="let" />
                                                  <input type="text" id="reconConfig.lastExecTime"
                                                         class="full rounded"
                                                         name="reconConfig.lastExecTime" readonly="true" maxlength="40" value="${let}"
                                                         title="${lastExecTimeTitle}" />
                                              </td>
                                          </tr>
                                          <tr class="common-row">
                                            <td>
                                                <label><spring:message code="openiam.ui.idm.reconcile.field.preprocessor"/></label>
                                            </td>
                                            <td><input name="reconConfig.preProcessor"
                                                       value="${requestScope.reconCommand.reconConfig.preProcessor}"
                                                       class="full rounded"
                                                       type='text' maxlength="120"/>
                                          </td>
                                        </tr>
                                        <tr class="common-row">
                                            <td>
                                                <label><spring:message code="openiam.ui.idm.reconcile.field.postprocessor"/></label>
                                            </td>
                                            <td><input name="reconConfig.postProcessor"
                                                       value="${requestScope.reconCommand.reconConfig.postProcessor}"
                                                       class="full rounded"
                                                       type='text' maxlength="120"/>
                                            </td>
                                        </tr>
                                    </tbody>
                              </table>

                        </div>
                        <div id="situationsTitle" class="title"><fmt:message key="openiam.ui.reconcilation.situation.list.res" />:
                              ${requestScope.reconCommand.resourceName}</div>
                        <!--             // Recon situations -->
                        <div class="frameContentDivider">
                              <table cellpadding="8px" align="center">
                                    <thead>
                                          <td><fmt:message key="openiam.ui.reconcilation.recon.situation" /></td>
                                          <td><fmt:message key="openiam.ui.common.response" /></td>
                                          <td><fmt:message key="openiam.ui.common.script" /></td>
                                          <td><fmt:message key="openiam.ui.common.custom_command_script" /></td>
                                    </thead>
                                    <tbody>
                                          <c:choose>
                                                <c:when test="${!empty requestScope.reconCommand.situationSet}">
                                                      <c:forEach var="rs"
                                                            items="${requestScope.reconCommand.situationSet}"
                                                            varStatus="rsCounter">
                                                            <tr class="situation-row">
                                                                  <input type="hidden"
                                                                        name="situationSet[${rsCounter.index}].reconSituationId"
                                                                        id="situationSet[${rsCounter.index}].reconSituationId"
                                                                        value="${rs.reconSituationId}"/>
                                                                  <input type="hidden"
                                                                        name="situationSet[${rsCounter.index}].reconConfigId"
                                                                        id="situationSet[${rsCounter.index}].reconConfigId"
                                                                        value="${rs.reconConfigId}"/>
                                                                  <td>${rs.situation}<input type="hidden"
                                                                        name="situationSet[${rsCounter.index}].situation"
                                                                        id="situationSet[${rsCounter.index}].situation"
                                                                        value="${rs.situation}"/></td>
                                                                  <td><select
                                                                        name="situationSet[${rsCounter.index}].situationResp"
                                                                        id="situationSet[${rsCounter.index}].situationResp"
                                                                        class="short select rounded situation-response">
                                                                              <c:forEach var="response"
                                                                                    items="<%=ReconciliationSituationResponseOptions.values()%>">
                                                                                    <option
                                                                                          <c:if test="${response.value eq rs.situationResp}">selected="selected"</c:if>
                                                                                          value="${response.value}">${response.label}</option>
                                                                              </c:forEach>
                                                                  </select></td>
                                                                  <td><input
                                                                        name="situationSet[${rsCounter.index}].script"
                                                                        id="situationSet[${rsCounter.index}].script"
                                                                        class="full rounded situation-script" value="${rs.script}"
                                                                        type="text"/></td>
                                                                  <td><input
                                                                        name="situationSet[${rsCounter.index}].customCommandScript"
                                                                        id="situationSet[${rsCounter.index}].customCommandScript"
                                                                        class="full rounded situation-script" value="${rs.customCommandScript}"
                                                                        type="text"/></td>
                                                            </tr>
                                                      </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                      <tr>
                                                            <td colspan="3">none</td>
                                                      </tr>
                                                </c:otherwise>
                                          </c:choose>
                                    </tbody>
                                    <tfoot>
                                          <tr>
                                                <td colspan="3">
                                                      <ul class="formControls">
                                                          <c:if test="${requestScope.reconCommand.reconConfig.execStatus != 'STARTED' && requestScope.reconCommand.reconConfig.execStatus != 'STARTING' &&  requestScope.reconCommand.reconConfig.execStatus != 'STOPPING'}">
                                                            <li><a href="javascript:void(0)"> <input
                                                                        id="reconSave" type="button" class="redBtn"
                                                                        value="<fmt:message key='openiam.ui.button.save' />"
                                                                  >
                                                            </a></li>
                                                            <li><a id="reconNow" href="javascript:void(0);"
                                                                  class="redBtn"
                                                            ><fmt:message key="openiam.ui.reconcilation.save.reconcile" /></a></li>
                                                          </c:if>
                                                          <c:if test="${requestScope.reconCommand.reconConfig.execStatus != 'STOPPED' &&  requestScope.reconCommand.reconConfig.execStatus != 'STOPPING' &&  requestScope.reconCommand.reconConfig.execStatus != 'FINISHED'}">
                                                          <li><a href="javascript:void(0)"> <input
                                                                  id="reconStop" type="button" class="redBtn"
                                                                  value="<fmt:message key='openiam.ui.common.stop' />"
                                                                  >
                                                          </a></li>
                                                          </c:if>
                                                          <c:if test="${requestScope.reconCommand.reconConfig.execStatus == 'STOPPING'}">
                                                              <li>
                                                                  <a href="javascript:void(0);" style="color:blue"><fmt:message key="openiam.ui.reconcilation.stopping" />.... </a>
                                                              </li>
                                                          </c:if>
                                                          <c:if test="${requestScope.reconCommand.reconConfig.execStatus == 'STARTING'}">
                                                              <li>
                                                                  <a href="javascript:void(0);" style="color:blue"><fmt:message key="openiam.ui.reconcilation.starting" />.... </a>
                                                              </li>
                                                          </c:if>
                                                            <li><a
                                                                  href="/webconsole-idm/provisioning/mngsystemlist.html"
                                                                  class="whiteBtn"
                                                            ><fmt:message key="openiam.ui.button.cancel" /></a></li>
                                                            <%--         TODO ADD DELETE                                        <c:if test="${! empty requestScope.reconCommand.resource}"> --%>
                                                            <!--                                                       <li><a id="deleteBtn" href="javascript:void(0);" -->
                                                            <!--                                                             class="redBtn" -->
                                                            <!--                                                       >Delete</a></li> -->
                                                            <%--                                                 </c:if> --%>
                                                      </ul>
                                                </td>
                                          </tr>
                                    </tfoot>
                              </table>
                        </div>
                  </form>
            </c:otherwise>
      </c:choose>
</body>
</html>