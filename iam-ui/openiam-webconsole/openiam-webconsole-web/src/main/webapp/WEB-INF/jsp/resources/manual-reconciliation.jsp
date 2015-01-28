
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ page import="org.openiam.idm.srvc.recon.result.dto.ReconciliationResultCase"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${titleOrganizatioName} - <fmt:message key="openiam.webconsole.resource.type.menu.edit.reconciliation.config" /></title>
	<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
	<link href="/openiam-ui-static/css/webconsole/manual.reconciliation.css" rel="stylesheet" type="text/css" />
	<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
	<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	<openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/resource/manual-recon.js"></script>
    <script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.ResourceId = <c:choose><c:when test="${! empty requestScope.entryBean.resourceId}">"${requestScope.entryBean.resourceId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.managedSystem.id}">"id=${requestScope.managedSystem.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			</script>
</head>
<body>
      <c:choose>
            <c:when test="${! empty requestScope.entryBean.error}">
                  <div id="title" class="title">
                        <spring:message code='${requestScope.entryBean.error}' />
                  </div>
            </c:when>
            <c:otherwise>
                  <div id="title" class="title"><fmt:message key="openiam.webconsole.resource.type.menu.manual.reconciliation.resource" />:
                        ${requestScope.entryBean.resourceName}</div>
                  <div id="contairner" class="frameContentDivider">
                        <div>
                              <table cellpadding="8px" width="100%" align="center" class="yui"
                                    style="text-align: center; border-width: 0px !important;"
                              >

                                    <thead>
                                          <tr>
                                                <td
                                                      colspan="${requestScope.entryBean.reconResultBean.header.fields.size()+3}"
                                                >
                                                      <div class="title"><fmt:message key="openiam.ui.common.type" />:
                                                            ${requestScope.entryBean.reconResultBean.objectType}. <fmt:message key="openiam.common.search.records" /></div>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td
                                                      colspan="${(requestScope.entryBean.reconResultBean.header.fields.size()+3)/2}"
                                                >
                                                      <div class="search-separator">
                                                            <label for="caseSearch"><fmt:message key="openiam.common.search.case" /></label><select
                                                                  id="caseSearch" class="search-select"
                                                            >
                                                                  <option value=""><fmt:message key="openiam.ui.selfservice.text.pleaseselect" /></option>
                                                                  <c:forEach var="rrc" varStatus="counter"
                                                                        items="<%=ReconciliationResultCase.values()%>"
                                                                  >
                                                                        <c:if test="${counter.index!=0}">
                                                                              <option value="${rrc}"
                                                                                    <c:if test="${requestScope.entryBean.searchBean.searchCase == rrc }">selected="selected"</c:if>
                                                                              >${rrc.value}</option>
                                                                        </c:if>
                                                                  </c:forEach>
                                                            </select>
                                                      </div> <c:if
                                                            test="${ !empty requestScope.entryBean.reconResultBean and !empty
                                          requestScope.entryBean.reconResultBean.header and !empty
                                          requestScope.entryBean.reconResultBean.header.fields and !empty
                                          requestScope.entryBean.reconResultBean.header.fields}"
                                                      >
                                                            <div class="search-separator">
                                                                  <label for="fieldOrder">Order by</label><select
                                                                        id="fieldOrder" class="search-select"
                                                                  >
                                                                        <option value=""><fmt:message key="openiam.ui.selfservice.text.pleaseselect" /></option>
                                                                        <c:forEach var="fieldItem"
                                                                              items="${requestScope.entryBean.reconResultBean.header.fields}"
                                                                        >
                                                                              <option value="${fieldItem.values[0]}"
                                                                                    <c:if test="${requestScope.entryBean.searchBean.orderByFieldName == fieldItem.values[0] }">selected="selected"</c:if>
                                                                              >${fieldItem.values[0]}</option>
                                                                        </c:forEach>
                                                                  </select>
                                                            </div>
                                                            <div class="search-separator">
                                                                  <label for="directionOrder"><fmt:message key="openiam.common.sort.direction" /></label><select
                                                                        id="directionOrder" class="search-select"
                                                                  >
                                                                        <option value="ASC"
                                                                              <c:if test="${requestScope.entryBean.searchBean.orderBy == 'ASC' }">selected="selected"</c:if>
                                                                        ><fmt:message key="openiam.ui.common.ascending" /></option>
                                                                        <option value="DECR"
                                                                              <c:if test="${requestScope.entryBean.searchBean.orderBy == 'DECR' }">selected="selected"</c:if>
                                                                        ><fmt:message key="openiam.ui.common.decreasing" /></option>
                                                                  </select>
                                                            </div>
                                                      </c:if>
                                                </td>

                                                <td
                                                      colspan="${(requestScope.entryBean.reconResultBean.header.fields.size()+3)-((requestScope.entryBean.reconResultBean.header.fields.size()+3)/2)+1}"
                                                ><c:if
                                                            test="${ !empty requestScope.entryBean.reconResultBean and !empty requestScope.entryBean.reconResultBean.header and !empty requestScope.entryBean.reconResultBean.header.fields and !empty requestScope.entryBean.reconResultBean.header.fields}"
                                                      >
                                                            <div class="search-separator">
                                                                  <label for="fieldSearch"><fmt:message key="openiam.common.search.case" /></label> <select
                                                                        id="fieldSearch" class="search-select"
                                                                  >
                                                                        <option value=""><fmt:message key="openiam.ui.selfservice.text.pleaseselect" /></option>
                                                                        <c:forEach var="fieldItem"
                                                                              items="${requestScope.entryBean.reconResultBean.header.fields}"
                                                                        >
                                                                              <option value="${fieldItem.values[0]}"
                                                                                    <c:if test="${requestScope.entryBean.searchBean.searchFieldName == fieldItem.values[0] }">selected="selected"</c:if>
                                                                              >${fieldItem.values[0]}</option>
                                                                        </c:forEach>
                                                                  </select>
                                                            </div>
                                                            <div class="search-separator">
                                                                  <label for="querySearch"><fmt:message key="openiam.common.search.query" /></label> <input
                                                                        id="querySearch" type="text" class="search-text"
                                                                        value="${requestScope.entryBean.searchBean.searchFieldValue }"
                                                                  >
                                                      </c:if>
                                                      </div>
                                                      <div class="search-separator">
                                                            <input id="searchButton" class="redBtn" type="button"
                                                                  value="<fmt:message key='openiam.ui.common.search' />"
                                                            >
                                                      </div></td>
                                    </thead>
                              </table>
                        </div>
                        <div style="overflow-x: scroll;">
                              <table cellpadding="8px" align="center" class="yui" style="text-align: center;">
                                    <tbody>
                                          <c:choose>
                                                <c:when test="${!empty requestScope.entryBean.reconResultBean}">
                                                      <tr class="tr-manual-recon"
                                                            rowId="${requestScope.entryBean.reconResultBean.header.rowId}"
                                                      >
                                                            <th class="headcol case"
                                                                  value="${requestScope.entryBean.reconResultBean.header.caseReconciliation}"
                                                            >ID/${requestScope.entryBean.reconResultBean.header.caseReconciliation.value}</th>
                                                            <c:forEach var="field"
                                                                  items="${requestScope.entryBean.reconResultBean.header.fields}"
                                                            >
                                                                  <th><p>
                                                                              <c:if test="${field.keyField}">
                                                                                    <span style="color: #D95B44">key
                                                                                          field</span>
                                                                              </c:if>
                                                                              <c:forEach var="value"
                                                                                    items="${field.values}"
                                                                              >
                                                                        ${value}
                                                                  </c:forEach>

                                                                        </p></th>
                                                            </c:forEach>
                                                            <th>Action</th>
                                                            <th>Ready</th>
                                                      </tr>
                                                      <c:choose>
                                                            <c:when
                                                                  test="${! empty requestScope.entryBean.reconResultBean.rows}"
                                                            >
                                                                  <c:forEach var="reconResultBeanRow"
                                                                        items="${requestScope.entryBean.reconResultBean.rows}"
                                                                  >

                                                                        <tr rowId="${reconResultBeanRow.rowId}"
                                                                              class=<c:choose><c:when test="${reconResultBeanRow.rowId%2==0}">"tr-manual-recon even"</c:when><c:otherwise>"tr-manual-recon odd"</c:otherwise></c:choose>
                                                                        >
                                                                              <td class="case"
                                                                                    style="background-color:${reconResultBeanRow.caseReconciliation.color}"
                                                                                    value="${reconResultBeanRow.caseReconciliation}"
                                                                              >${reconResultBeanRow.rowId}.&nbsp;${reconResultBeanRow.caseReconciliation.value}</td>
                                                                              <c:forEach var="field"
                                                                                    items="${reconResultBeanRow.fields}"
                                                                              >
                                                                                    <c:choose>
                                                                                          <c:when
                                                                                                test="${field.values.size()==1}"
                                                                                          >
                                                                                                <td class="field">${field.values.get(0)}</td>
                                                                                          </c:when>
                                                                                          <c:when
                                                                                                test="${field.values.size()==0}"
                                                                                          >
                                                                                                <td class="field">&nbsp;</td>
                                                                                          </c:when>
                                                                                          <c:otherwise>
                                                                                                <td class="field"><select
                                                                                                      class="field-select short "
                                                                                                >
                                                                                                            <c:forEach
                                                                                                                  var="value"
                                                                                                                  items="${field.values}"
                                                                                                            >
                                                                                                                  <option>${value}</option>
                                                                                                            </c:forEach>
                                                                                                </select></td>
                                                                                          </c:otherwise>
                                                                                    </c:choose>
                                                                              </c:forEach>
                                                                              <td class='action'><c:choose>
                                                                                          <c:when
                                                                                                test="${'NOT_EXIST_IN_RESOURCE' eq reconResultBeanRow.caseReconciliation}"
                                                                                          >
                                                                                                <select
                                                                                                      class="short select-action "
                                                                                                >
                                                                                                      <option value=""><fmt:message key="openiam.common.do.nothing" /></option>
                                                                                                      <c:forEach
                                                                                                            var="action"
                                                                                                            items="${requestScope.entryBean.idmNotTarget}"
                                                                                                      >

                                                                                                            <option
                                                                                                                  value="${action}"
                                                                                                                  <c:if test="${action eq reconResultBeanRow.action}">
                                                                                                selected="selected"
                                                                                                </c:if>
                                                                                                            >${action.value}</option>
                                                                                                      </c:forEach>
                                                                                                </select>
                                                                                          </c:when>
                                                                                          <c:when
                                                                                                test="${'NOT_EXIST_IN_IDM_DB' eq reconResultBeanRow.caseReconciliation}"
                                                                                          >
                                                                                                <select
                                                                                                      class="short select-action "
                                                                                                >
                                                                                                      <option value=""><fmt:message key="openiam.common.do.nothing" /></option>
                                                                                                      <c:forEach
                                                                                                            var="action"
                                                                                                            items="${requestScope.entryBean.targetNotIdm}"
                                                                                                      >

                                                                                                            <option
                                                                                                                  value="${action}"
                                                                                                                  <c:if test="${action eq reconResultBeanRow.action}">
                                                                                                selected="selected"
                                                                                                </c:if>
                                                                                                            >${action.value}</option>
                                                                                                      </c:forEach>
                                                                                                </select>
                                                                                          </c:when>
                                                                                          <c:when
                                                                                                test="${'MATCH_FOUND' eq reconResultBeanRow.caseReconciliation}"
                                                                                          >
                                                                                                <img
                                                                                                      src="/openiam-ui-static/images/common/check.png"
                                                                                                >
                                                                                          </c:when>
                                                                                          <c:when
                                                                                                test="${'MATCH_FOUND_DIFFERENT' eq reconResultBeanRow.caseReconciliation}"
                                                                                          >
                                                                                                <img
                                                                                                      src="/openiam-ui-static/images/common/edit.png"
                                                                                                >
                                                                                          </c:when>
                                                                                          <c:otherwise>
                                                                                                <img
                                                                                                      src="/openiam-ui-static/images/common/delete.png"
                                                                                                >
                                                                                          </c:otherwise>
                                                                                    </c:choose></td>
                                                                              <td><c:if
                                                                                          test="${
                                                                             'MATCH_FOUND_DIFFERENT' eq reconResultBeanRow.caseReconciliation or
                                                                             'NOT_EXIST_IN_IDM_DB' eq reconResultBeanRow.caseReconciliation or
                                                                             'NOT_EXIST_IN_RESOURCE' eq reconResultBeanRow.caseReconciliation}"
                                                                                    >
                                                                                          <input type="checkbox"
                                                                                                class="ready"
                                                                                          >
                                                                                    </c:if></td>
                                                                        </tr>
                                                                  </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                  <tr>
                                                                        <td
                                                                              colspan="${(requestScope.entryBean.reconResultBean.header.fields.size()+3)}"
                                                                        ><fmt:message key="openiam.ui.common.no.records" /></td>
                                                                  </tr>
                                                            </c:otherwise>
                                                      </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                </c:otherwise>
                                          </c:choose>
                                    </tbody>
                              </table>
                        </div>
                        <div>
                              <table cellpadding="8px" width="100%" align="center" class="yui"
                                    style="text-align: center; border-width: 0px !important;"
                              >
                                    <tfoot>
                                          <tr id="pagerOne">
                                                <td
                                                      colspan="${(requestScope.entryBean.reconResultBean.header.fields.size()+3)}"
                                                ><img class="first" id="pageFirst"
                                                      src="/openiam-ui-static/plugins/tablesorter/img/first.png"
                                                > <img class="prev" id="pagePrev"
                                                      src="/openiam-ui-static/plugins/tablesorter/img/prev.png"
                                                > <input type="text" id="pageNumber" class="pagedisplay"
                                                      autocomplete="off"
                                                      value="${requestScope.entryBean.searchBean.pageNumber}"
                                                > /<input type="text" id="pagesNumber" class="pagedisplay"
                                                      autocomplete="off"
                                                      value="${requestScope.entryBean.reconResultBean.pagesNumber}"
                                                ><img id="pageNext" class="next"
                                                      src="/openiam-ui-static/plugins/tablesorter/img/next.png"
                                                > <img class="last" id="pageLast"
                                                      src="/openiam-ui-static/plugins/tablesorter/img/last.png"
                                                > <select class="pagesize" name="size" id="pageSize"
                                                      autocomplete="off"
                                                >

                                                            <c:forEach var="i" begin="10" end="40" step="10">
                                                                  <option
                                                                        <c:if test="${requestScope.entryBean.searchBean.size == i}">selected="selected"</c:if>
                                                                        value="${i}"
                                                                  >${i}</option>
                                                            </c:forEach>
                                                </select></td>
                                          </tr>
                                          <tr>
                                                <td
                                                      colspan="${(requestScope.entryBean.reconResultBean.header.fields.size()+3)}"
                                                ><input id="saveReconRes" type="button" value="<fmt:message key='openiam.ui.common.save' />" class="redBtn"></td>
                                          </tr>
                                    </tfoot>
                              </table>
                        </div>
                  </div>
            </c:otherwise>
      </c:choose>
</body>
</html>





