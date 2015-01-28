
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<c:set var="title">
      <c:choose>
            <c:when test="${! empty requestScope.metadataElement.id}">
                  <fmt:message key="metadata.edit.title.edit" /> : ${requestScope.metadataElement.attributeName}
		</c:when>
            <c:otherwise>
                  <fmt:message key="metadata.edit.title.new" />
            </c:otherwise>
      </c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${title}</title>
<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
      type="text/css"
/>
<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet"
      type="text/css"
/>
<link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
<openiam:overrideCSS />

<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>

<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole/metadata/metadata.edit.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/language/language.admin.js"></script>
<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MetadataElement = ${requestScope.elementAsJSON};
		</script>
</head>
<body>
      <div id="title" class="title">${title}</div>
      <div class="frameContentDivider">
            <form id="metadataElementForm" method="post">
                  <table cellpadding="8px" align="center" class="fieldset">
                        <tbody>
                              <tr>
                                    <td><label for="attributeName"><fmt:message
                                                      key="openiam.ui.common.display.name.cur"
                                                />:</label></td>
                                    <td><input id="attributeName" autocomplete="off" type="text"
                                          class="full rounded"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label for="languageMap"><fmt:message
                                                      key="openiam.ui.common.display.name"
                                                />:</label></td>
                                    <td>
                                          <div id="languageMap"></div>
                                    </td>
                              </tr>
                              <tr>
                                    <td><label for="metadataTypeId" class="required"><fmt:message
                                                      key="openiam.ui.idm.prov.conn.field.type"
                                                />:</label></td>
                                    <td><select id="metadataTypeId" class="rounded">
                                                <option value=""><fmt:message
                                                            key="openiam.ui.common.value.pleaseselect"
                                                      />
                                                </option>
                                                <c:forEach var="item" items="${requestScope.metaTypes}">
                                                      <option value="${item.id}">${item.name}</option>
                                                </c:forEach>
                                    </select></td>
                              </tr>
                              <tr>
                                    <td><label for="description"><fmt:message
                                                      key="openiam.ui.idm.mngconn.table.col.descr"
                                                />:</label></td>
                                    <td><input id="description" autocomplete="off" type="text" class="full rounded" />
                                    </td>
                              </tr>
                              <tr>
                                    <td><label for="dataType"><fmt:message
                                                      key="metadata.edit.meta.data.type"
                                                />:</label></td>
                                    <td><input id="dataType" autocomplete="off" type="text" class="full rounded" />
                                    </td>
                              </tr>
                              <tr>
                                    <td><label for="staticDefaultValue"><fmt:message
                                                      key="metadata.edit.meta.data.static.def.val"
                                                />:</label></td>
                                    <td><input id="staticDefaultValue" autocomplete="off" type="text"
                                          class="full rounded"
                                    /></td>
                              </tr>
                              <c:if test="${! empty requestScope.resource}">
                                    <tr>
                                          <td><label for="metadataResource"><fmt:message
                                                            key="metadata.edit.meta.data.res"
                                                      />:</label></td>
                                          <td><a id="metadataResource"
                                                href="/webconsole/editResource.html?id=${requestScope.resource.id}"
                                          >${requestScope.resource.coorelatedName}</a></td>
                                    </tr>
                              </c:if>
                              <tr>
                                    <td><label for="auditable"><fmt:message
                                                      key="metadata.edit.meta.data.is.audit"
                                                /></label></td>
                                    <td><input id="auditable" autocomplete="off" type="checkbox"
                                          class="rounded"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label for="required"><fmt:message
                                                      key="openiam.ui.report.parameter.required"
                                                /></label></td>
                                    <td><input id="required" autocomplete="off" type="checkbox"
                                          class="rounded"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label for="selfEditable"><fmt:message
                                                      key="metadata.edit.meta.data.is.selfeditable"
                                                /></label></td>
                                    <td><input id="selfEditable" autocomplete="off" type="checkbox"
                                          class="rounded"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label for="isPublic"><fmt:message
                                                      key="openiam.ui.common.is.public"
                                                /></label></td>
                                    <td><input id="isPublic" autocomplete="off" type="checkbox"
                                          class="rounded"
                                    /></td>
                              </tr>
                        </tbody>
                        <tfoot>
                              <tr>
                                    <td colspan="2">
                                          <ul class="formControls">
                                                <li class="leftBtn"><a href="javascript:void(0)"> <input
                                                            type="submit" id="saveBtn" class="redBtn"
                                                            value="<fmt:message
                                                      key="openiam.ui.common.save"
                                                />"
                                                      />
                                                </a></li>
                                                <li class="leftBtn"><a href="/webconsole/metaDataTypeEdit.html?OPENIAM_MENU_ID=META_TYPE_EDIT&id=${requestScope.metaType.id}" class="whiteBtn"><fmt:message
                                                                  key="openiam.ui.common.cancel"
                                                            /></a></li>
                                                <c:if test="${! empty requestScope.metadataElement.id}">
                                                      <li class="rightBtn"><a id="deleteBtn"
                                                            href="javascript:void(0);" class="redBtn"
                                                      ><fmt:message key="openiam.ui.common.delete" /></a></li>
                                                </c:if>
                                          </ul>
                                    </td>
                              </tr>
                        </tfoot>
                  </table>
            </form>
      </div>
      <div id="editDialog"></div>
</body>
</html>