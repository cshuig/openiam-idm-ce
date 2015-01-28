
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${titleOrganizatioName}-<fmt:message key="contentprovider.provider.uri.pattern.head" /></title>
<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
      type="text/css"
/>
<link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet"
      type="text/css"
/>
<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
      type="text/css"
/>
<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
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

<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/contentprovider/patternEdit.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/contentprovider/meta.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/contentprovider/authlevel.grouping.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
<script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.uriPattern.id}&providerId=${requestScope.uriPattern.contentProviderId}";
        OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.SuccessMessage = <c:choose><c:when test="${! empty successMessage}">"${successMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ProviderId = <c:choose><c:when test="${! empty requestScope.provider.id}">"${requestScope.provider.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.PatternId = <c:choose><c:when test="${! empty requestScope.uriPattern.id}">"${requestScope.uriPattern.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Pattern = ${requestScope.uriPatternAsJSON};
    </script>
</head>
<body>
      <div id="title" class="title">
            <c:choose>
                  <c:when test="${! empty requestScope.uriPattern.id}">
	            ${requestScope.uriPattern.pattern}  <fmt:message key="contentprovider.provider.uri.pattern.edit" />:  ${requestScope.provider.name}
	        </c:when>
                  <c:otherwise>
                        <fmt:message key="contentprovider.provider.uri.pattern.new" />    : ${requestScope.provider.name}
	        </c:otherwise>
            </c:choose>
      </div>
      <div class="frameContentDivider">
            <form id="uriPatternForm" method="post" action="editProviderPattern.html">
                  <input id="resourceId" name="resourceId" type="hidden" class="full rounded"
                        value="${requestScope.uriPattern.resourceId}"
                  /> <input id="providerId" name="providerId" type="hidden" class="full rounded"
                        value="${requestScope.provider.id}"
                  /> <input id="id" name="id" type="hidden" class="full rounded" value="${requestScope.uriPattern.id}" />
                  <table cellpadding="8px" align="center" class="fieldset">
                        <tbody>
                              <c:if test="${! empty requestScope.uriPattern.resourceId}">
                                    <tr>
                                          <td><label> <fmt:message key="contentprovider.linked.to.res" />:
                                          </label></td>
                                          <td><a
                                                href="/webconsole/editResource.html?id=${requestScope.uriPattern.resourceId}"
                                          >${requestScope.uriPattern.resourceName}</a></td>
                                    </tr>
                              </c:if>
                              <tr>
                                    <td><label class="required"><fmt:message
                                                      key="contentprovider.entitlements.pattern"
                                                /></label></td>
                                    <td><input type="text" id="pattern" name="pattern"
                                          class="full rounded _input_tiptip"
                                          title="<fmt:message key='openiam.ui.pattern.edit.current.impl' />"
                                          maxlength="100"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label><fmt:message
                                                      key="contentprovider.entitlements.is.auth.delete"
                                                /></label></td>
                                    <td class="_input_tiptip"
                                          title="<fmt:message
                                                      key="contentprovider.provider.uri.pattern.is.auth.text"
                                                />"
                                    ><input id="isPublicOn" autocomplete="off" type="radio" name="isPublic"
                                          value="true"
                                    /> <fmt:message key="openiam.ui.common.yes" /><input id="isPublicOff"
                                          autocomplete="off" type="radio" name="isPublic" value="false"
                                    /> <fmt:message key="openiam.ui.common.no" /></td>
                              </tr>
                              <tr>
                                    <td><label> <fmt:message key="ui.theme.name" /></label></td>
                                    <td><select id="themeId" name="themeId" class="select _input_tiptip"
                                          title="<fmt:message key="contentprovider.provider.uri.pattern.ui.theme.text" />"
                                    >
                                                <option value="">
                                                      <fmt:message
                                                            key="contentprovider.provider.inherit.from.content.provider"
                                                      /></option>
                                                <c:forEach var="theme" items="${requestScope.uiThemes}">
                                                      <option value="${theme.id}">${theme.name}</option>
                                                </c:forEach>
                                    </select></td>
                              </tr>
                        </tbody>
                        <tfoot>
                              <tr>
                                    <td colspan="2">
                                          <ul class="formControls">
                                                <li><a href="javascript:void(0)"> <input type="submit"
                                                            class="redBtn"
                                                            value="<fmt:message
                                          key="openiam.ui.button.save"
                                    />"
                                                      />
                                                </a></li>
                                                <li><a
                                                      href="editContentProvider.html?providerId=${requestScope.provider.id}"
                                                      class="whiteBtn"
                                                ><fmt:message key="openiam.ui.button.cancel" /></a></li>
                                                <c:if test="${! empty requestScope.uriPattern.id}">
                                                      <li class="rightBtn"><a id="deletePattern"
                                                            href="javascript:void(0);" class="redBtn"
                                                      ><fmt:message key="openiam.ui.button.delete" /></a></li>
                                                </c:if>
                                          </ul>
                                    </td>
                              </tr>
                        </tfoot>
                  </table>
            </form>
      </div>
      <div>
            <div class="title"><fmt:message key="openiam.ui.pattern.supported.auth.level" /></div>
            <div class="frameContentDivider">
                  <div id="authenticationLevels"></div>
            </div>
      </div>
      <c:if test="${! empty requestScope.uriPattern and ! empty requestScope.uriPattern.id}">
            <div id="title" class="title">Meta Rules</div>
            <div class="frameContentDivider">
                  <div id="providerDataContainer"></div>
            </div>
      </c:if>
      <div id="editDialog"></div>
</body>
</html>