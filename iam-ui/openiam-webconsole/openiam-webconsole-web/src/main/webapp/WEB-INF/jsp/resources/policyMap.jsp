<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="html" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ page import="org.openiam.idm.srvc.mngsys.dto.PolicyMapObjectTypeOptions"%>
<%@ page import="org.openiam.ui.constants.CommonStatusOptions"%>
<%@ page import="org.openiam.ui.constants.ReconResourceTypeOptions"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.policy.policymap" /></title>
	<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
	<link href="/openiam-ui-static/css/webconsole/resource.policymap.css" rel="stylesheet" type="text/css" />
	<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
	<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	<openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/tablesorter/jquery.tablesorter.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/resource/resource.policyMap.js"></script>
    <script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            <c:choose>
            <c:when test="${not empty requestScope.resource}">
				OPENIAM.ENV.ResourceId = <c:choose><c:when test="${! empty requestScope.resource.id}">"${requestScope.resource.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            	OPENIAM.ENV.ManagedSysId = <c:choose><c:when test="${! empty requestScope.mngSystem.id}">"${requestScope.mngSystem.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            	OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.mngSystem.id}">"id=${requestScope.mngSystem.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            	OPENIAM.ENV.Text = {
                	DeleteWarn : localeManager["openiam.ui.policy.delete.warn"]
            	};
            </c:when>
            <c:when test="${not empty requestScope.synchConfig}">
            	OPENIAM.ENV.SynchConfigId = <c:choose><c:when test="${! empty requestScope.synchConfig.synchConfigId}">"${requestScope.synchConfig.synchConfigId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            	OPENIAM.ENV.SynchConfigName = <c:choose><c:when test="${! empty requestScope.synchConfig.name}">"${requestScope.synchConfig.name}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            	OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.synchConfig.synchConfigId}">"id=${requestScope.synchConfig.synchConfigId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            	OPENIAM.ENV.Text = {
            		DeleteWarn : localeManager["openiam.ui.policy.delete.warn2"]
            	};
            </c:when>
		</c:choose>
		</script>
</head>
<body>
      <div id="title" class="title">
            <c:choose>
                  <c:when test="${! empty requestScope.synchConfig}">
                      <fmt:message key="openiam.ui.policy.edit" />: ${requestScope.synchConfig.name}
				</c:when>
                <c:when test="${! empty requestScope.resource}">
                    <fmt:message key="openiam.ui.policy.edit" />: ${requestScope.resource.name}
                </c:when>
            </c:choose>
      </div>
      <div class="frameContentDivider">
            <form id="policyMapForm" method="post">
                  <table id="policyMapTable" resourceId="${requestScope.resource.id}"
                        resourceName="${requestScope.resource.name}" mSysId="${requestScope.mngSystem.id}"
                        synchConfigId="${requestScope.synchConfig.synchConfigId}" synchConfigName="${requestScope.synchConfig.name}"
                        class="yui" cellpadding="8px" align="center">
                        <thead>
                              <c:choose>
                                    <c:when test="${requestScope.isPrincipalExist}">
                                          <tr>
                                                <th colspan="8"><fmt:message key="openiam.ui.policy.must.contain" /></th>
                                          </tr>
                                    </c:when>
                              </c:choose>
                              <tr>

                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.object_type" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.attribute_name" /></th>
                                    <th><fmt:message key="openiam.ui.idm.prov.table.col.type" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.policy" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.data_type" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.defval" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.status" /></th>
                                    <th><fmt:message key="openiam.ui.common.select" /></th>
                              </tr>
                        </thead>
                        <tbody>
                              <c:choose>
                                    <c:when test="${!empty requestScope.attributesMap}">
                                          <c:forEach var="attributeMap" items="${requestScope.attributesMap}">
                                                <tr class="attr-map-row even" entityId="${attributeMap.attributeMapId}"
                                                      reconResId="${attributeMap.reconResAttribute.reconciliationResourceAttributeMapId}"
                                                >
                                                      <td><select class="attr-map-obj-type short select rounded">
                                                                  <c:forEach var="pmot"
                                                                        items="${policyMapObjectTypeOptions}">
                                                                        <option
                                                                              <c:if test="${pmot.key eq attributeMap.mapForObjectType}">selected="selected"</c:if>
                                                                              value="${pmot.key}"
                                                                        >${pmot.value}</option>
                                                                  </c:forEach>
                                                      </select></td>
                                                      <td><c:choose>
                                                                  <c:when
                                                                        test="${empty requestScope.targetSystemAttributes}"
                                                                  >
                                                                        <input type="text" class="attr-map-name rounded short"
                                                                              value="${attributeMap.attributeName}"
                                                                              maxlength="50"
                                                                        >
                                                                  </c:when>
                                                                  <c:otherwise>
                                                                        <select
                                                                              class="attr-map-name-list short select rounded"
                                                                        >
                                                                              <c:forEach var="item"
                                                                                    items="${requestScope.targetSystemAttributes}"
                                                                              >
                                                                                    <c:if test="${! empty item}">
                                                                                          <option
                                                                                                <c:if test="${! empty requestScope.targetSystemAttributes and attributeMap.attributeName eq item}">selected="selected"</c:if>
                                                                                          >${item}</option>
                                                                                    </c:if>
                                                                              </c:forEach>
                                                                        </select>
                                                                  </c:otherwise>
                                                            </c:choose></td>
                                                      <td><select class="attr-map-policy-type short select rounded">
                                                                  <c:forEach var="it" items="${reconResourceTypeOptions}">
                                                                      <option value="${it.key}" <c:if test="${it.key eq attributeMap.reconResAttribute}">selected="selected"</c:if> >${it.value}</option>
                                                                  </c:forEach>
                                                      </select></td>
                                                      <td><select
                                                            class="<c:if test="${empty attributeMap.reconResAttribute.attributePolicy}">hide</c:if> custom-policy short select rounded"
                                                      >
                                                                  <c:forEach var="policy"
                                                                        items="${requestScope.policyList}"
                                                                  >
                                                                        <option
                                                                              <c:if test="${! empty attributeMap.reconResAttribute.attributePolicy and attributeMap.reconResAttribute.attributePolicy.policyId eq policy.policyId}">selected="selected"</c:if>
                                                                              value="${policy.policyId}"
                                                                        >${policy.name}</option>
                                                                  </c:forEach>
                                                      </select> <select
                                                            class="<c:if test='${empty attributeMap.reconResAttribute.defaultAttributePolicy}'>hide</c:if> default-policy short select rounded"
                                                      >
                                                                  <c:forEach var="defValue"
                                                                        items="${requestScope.defaultReconList}"
                                                                  >
                                                                        <option
                                                                              <c:if test="${! empty requestScope.defaultReconList and ! empty attributeMap.reconResAttribute.defaultAttributePolicy and attributeMap.reconResAttribute.defaultAttributePolicy.defaultAttributeMapId eq defValue.defaultAttributeMapId}">selected="selected"</c:if>
                                                                              value="${defValue.defaultAttributeMapId}"
                                                                        >${defValue.defaultAttributeMapName}</option>
                                                                  </c:forEach>
                                                      </select></td>
                                                      <td>
                                                          <select class="attr-map-date-type short select rounded">
                                                              <c:forEach var="it" items="${policyMapDataTypeOptions}">
                                                                  <option value="${it.key}" <c:if test="${it.key eq attributeMap.dataType}">selected="selected"</c:if> >${it.value}</option>
                                                              </c:forEach>
                                                          </select>
                                                      </td>
                                                      <td><input type="text" class="attr-map-def-value rounded short"
                                                            value="${ attributeMap.defaultValue}"/></td>
                                                      <td>
                                                          <select class="attr-map-status short select rounded">
                                                              <c:forEach var="st"
                                                                    items="<%=CommonStatusOptions.values()%>"
                                                              >
                                                                    <option
                                                                          <c:if test="${st.value eq attributeMap.status}">selected="selected"</c:if>
                                                                          value="${st.value}"
                                                                    >${st.value}</option>
                                                              </c:forEach>
                                                        </select>
                                                      </td>
                                                      <td><input type="checkbox" class="attr-map-selected" /></td>
                                                      <!--                                                  TODO set selected -->
                                                </tr>
                                          </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                          <tr>
                                                <td colspan="8"><fmt:message key="openiam.ui.policy.there.no.attribute" /></td>
                                          </tr>
                                    </c:otherwise>
                              </c:choose>
                        </tbody>
                        <tfoot>
                              <tr>

                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.object_type" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.attribute_name" /></th>
                                    <th><fmt:message key="openiam.ui.idm.prov.table.col.type" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.policy" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.data_type" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.defval" /></th>
                                    <th><spring:message code="openiam.ui.webconsole.policymap.col.status" /></th>
                                    <th><fmt:message key="openiam.ui.common.delete" /></th>
                              </tr>
                              <tr class="attr-map-row">
                                    <td><select class="attr-map-obj-type short select rounded">
                                                <c:forEach var="pmot" items="<%=PolicyMapObjectTypeOptions.values()%>">
                                                      <option>${pmot}</option>
                                                </c:forEach>
                                    </select></td>
                                    <td><c:choose>
                                                <c:when test="${empty requestScope.targetSystemAttributes}">
                                                      <input type="text" class="attr-map-name rounded short"
                                                            value="${attributeMap.attributeName}" maxlength="50"
                                                      >
                                                </c:when>
                                                <c:otherwise>
                                                      <select class="attr-map-name-list short select rounded">
                                                            <option value="" label="-<fmt:message key='openiam.ui.common.value.pleaseselect' />-" />
                                                            <c:forEach var="item"
                                                                  items="${requestScope.targetSystemAttributes}"
                                                            >
                                                                  <c:if test="${! empty item}">
                                                                        <option value="${item}"
                                                                              <c:if test="${! empty requestScope.targetSystemAttributes and attributeMap.attributeName eq item}">selected="selected"</c:if>
                                                                        >${item}</option>
                                                                  </c:if>
                                                            </c:forEach>
                                                      </select>
                                                </c:otherwise>
                                          </c:choose></td>
                                    <td><select class="attr-map-policy-type short select rounded">
                                        <c:forEach var="it" items="${reconResourceTypeOptions}">
                                            <option value="${it.key}">${it.value}</option>
                                        </c:forEach>
                                    </select></td>
                                    <td><select class="show custom-policy short select rounded">
                                                <option value="" label="-<fmt:message key='openiam.ui.common.value.pleaseselect' />-" />
                                                <c:forEach var="policy" items="${requestScope.policyList}">
                                                      <option value="${policy.policyId}">${policy.name}</option>
                                                </c:forEach>
                                    </select> <select class="hide default-policy short select rounded">
                                                <option value="" label="-<fmt:message key='openiam.ui.common.value.pleaseselect' />-" />
                                                <c:forEach var="defValue" items="${requestScope.defaultReconList}">
                                                      <option value="${defValue.defaultAttributeMapId}">${defValue.defaultAttributeMapName}</option>
                                                </c:forEach>
                                    </select></td>
                                    <td>
                                        <select class="attr-map-date-type short select rounded">
                                            <c:forEach var="it" items="${policyMapDataTypeOptions}">
                                                <option value="${it.key}">${it.value}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><input type="text" class="attr-map-def-value rounded short" value="" /></td>
                                    <td><select class="attr-map-status short select rounded">
                                                <c:forEach var="st" items="<%=CommonStatusOptions.values()%>">
                                                      <option>${st.value}</option>
                                                </c:forEach>
                                    </select></td>
                                    <td><a class="delete-new-row" href="javascript:void(0);"> <img
                                                src="/openiam-ui-static/images/common/delete.png"
                                          >
                                    </a></td>
                              </tr>
                              <tr>
                                    <td id="addRow" colspan='8'><p><fmt:message key="openiam.ui.policy.add.one.or.more" /></p></td>
                              </tr>
                              <tr>
                                    <td colspan="8">
                                          <ul class="formControls">
                                                <li><a href="javascript:void(0)"> <input type="submit"
                                                            class="redBtn" value="<fmt:message key='openiam.ui.button.save' />"
                                                      />
                                                </a></li>
                                                <li><a href="resources.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel" /></a></li>
                                                <c:if test="${! empty requestScope.resource || ! empty requestScope.synchConfig}">
                                                      <li><a id="deleteSelected" href="javascript:void(0);"
                                                            class="redBtn"
                                                      ><fmt:message key="openiam.ui.common.delete.selected" /></a></li>
                                                </c:if>
                                          </ul>
                                    </td>
                              </tr>
                        </tfoot>
                  </table>
            </form>
      </div>
</body>
</html>




