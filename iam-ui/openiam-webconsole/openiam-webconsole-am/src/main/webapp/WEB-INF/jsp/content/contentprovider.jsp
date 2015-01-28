
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
<title>${titleOrganizatioName}-<fmt:message key="contentprovider.content.provider" />
</title>
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
<link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
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
<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/contentprovider/edit.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/contentprovider/authlevel.grouping.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/contentprovider/entitlements.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
<script type="text/javascript">
	        OPENIAM = window.OPENIAM || {};
	        OPENIAM.ENV = window.OPENIAM.ENV || {};
	        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
	        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.provider.id}">"providerId=${requestScope.provider.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
	        OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
	        OPENIAM.ENV.SuccessMessage = <c:choose><c:when test="${! empty successMessage}">"${successMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
	        OPENIAM.ENV.ProviderId = <c:choose><c:when test="${! empty requestScope.provider.id}">"${requestScope.provider.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
	        OPENIAM.ENV.ContentProvider = ${requestScope.providerAsJSON};
	    </script>
</head>
<body>
      <div id="title" class="title">
            <c:choose>
                  <c:when test="${! empty requestScope.provider.id}">
                        <fmt:message key="contentprovider.content.provider.edit" />   ${requestScope.provider.name}
		        </c:when>
                  <c:otherwise>
                        <fmt:message key="contentprovider.content.provider.new" />
                  </c:otherwise>
            </c:choose>
      </div>
      <div class="frameContentDivider">
            <form id="contentProviderForm" method="post" action="editContentProvider.html">
                  <input id="cpResourceId" name="resourceId" type="hidden" class="full rounded"
                        value="${requestScope.provider.resourceId}"
                  /> <input id="id" name="id" type="hidden" class="full rounded" value="${requestScope.provider.id}" />
                  <table cellpadding="8px" align="center" class="fieldset">
                        <tbody>
                              <c:if test="${! empty requestScope.provider.resourceId}">
                                    <tr>
                                          <td><label> <fmt:message key="contentprovider.linked.to.res" />:
                                          </label></td>
                                          <td><a
                                                href="/webconsole/editResource.html?id=${requestScope.provider.resourceId}"
                                          >${requestScope.provider.resourceName}</a></td>
                                    </tr>
                              </c:if>
                              <tr>
                                    <td><label class="required"><fmt:message
                                                      key="openiam.ui.common.linked.to.managed.system"
                                                /></label></td>
                                    <td><select id="managedSysId" name="managedSysId"
                                          class="rounded _input_tiptip"
                                          title="<fmt:message key="contentprovider.linked.msys.content.provider" />"
                                    >
                                                <c:forEach var="mSys" items="${requestScope.managedSystems}">
                                                      <option value="${mSys.id}">${mSys.name}</option>
                                                </c:forEach>
                                    </select></td>
                              </tr>
                              <tr>
                                    <td><label class="required"><fmt:message
                                                      key="contentprovider.search.cont.prov.name"
                                                /></label></td>
                                    <td><input type="text" id="name" name="name" class="full rounded _input_tiptip"
                                          title="<fmt:message key="contentprovider.name.provider.help" />"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label><fmt:message key="openiam.ui.common.URL" /></label></td>
                                    <td><input type="text" id="url" name="url" class="full rounded _input_tiptip"
                                          title="<fmt:message key="contentprovider.provider.landing.url" />"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label class="required"><fmt:message
                                                      key="contentprovider.search.domain.pattern"
                                                /></label></td>
                                    <td><input type="text" id="domainPattern" name="domainPattern"
                                          class="full rounded _input_tiptip "
                                          title="<fmt:message
                                                      key="contentprovider.search.domain.pattern.tip"
                                                />"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label><fmt:message key="ui.theme.name" /></label></td>
                                    <td><select id="themeId" name="themeId" class="select _input_tiptip"
                                          title="<fmt:message key="ui.theme.name.tip" />"
                                    >
                                                <option value=""><fmt:message key="ui.theme.use.default" /></option>
                                                <c:forEach var="theme" items="${requestScope.uiThemes}">
                                                      <option value="${theme.id}">${theme.name}</option>
                                                </c:forEach>
                                    </select></td>
                              </tr>
                              <tr>
                                    <td><label><fmt:message key="contentprovider.provider.is.ssl" /></label></td>
                                    <td><select id="isSSL" name="isSSL" class="select _input_tiptip"
                                          autocomplete="off"
                                          title="<fmt:message key="contentprovider.provider.is.ssl.tip" />"
                                    >
                                                <option value=""><fmt:message
                                                            key="openiam.ui.common.please.select"
                                                      /></option>
                                                <option value="true"><fmt:message key="openiam.ui.common.yes" /></option>
                                                <option value="false"><fmt:message key="openiam.ui.common.no" /></option>
                                    </select></td>
                              </tr>
                              <tr>
                                    <td><label><fmt:message key="contentprovider.provider.is.auth.disabled" /></label></td>
                                    <td>
                                    	<div class="radioContainer _input_tiptip" title="<fmt:message key="contentprovider.provider.is.auth.tip" />" >
                                    		<input id="cpIsPublicOn" autocomplete="off" type="radio" name="isPublic" value="true" /> 
                                    		<fmt:message key="openiam.ui.common.yes" /> 
                                    		<input id="cpIsPublicOff" autocomplete="off" type="radio" name="isPublic" value="false" /> 
                                    		<fmt:message key="openiam.ui.common.no" />
                                    	</div>
                                    </td>
                              </tr>
                              <tr>
                                    <td>
                                    	<label>
                                    		<fmt:message key="openiam.ui.content.provider.show.on.appplication.page" />
                                    	</label>
                                    </td>
                                    <td>
                                    	<div class="radioContainer _input_tiptip" title="<fmt:message key="openiam.ui.content.provider.show.on.appplication.page" />" >
                                    		<input id="showOnApplicationPageOn" autocomplete="off" type="radio" name="showOnApplicationPage" value="true" /> 
                                    		<fmt:message key="openiam.ui.common.yes" /> 
                                    		<input id="showOnApplicationPageOff" autocomplete="off" type="radio" name="showOnApplicationPage" value="false" /> 
                                    		<fmt:message key="openiam.ui.common.no" />
                                    	</div>
                                    </td>
                              </tr>
                        </tbody>
                        <tfoot>
                              <tr>
                                    <td colspan="2">
                                          <ul class="formControls">
                                                <li><a href="javascript:void(0)"> <input type="submit"
                                                            class="redBtn"
                                                            value="<fmt:message key="openiam.ui.button.save" />"
                                                      />
                                                </a></li>
                                                <li><a href="contentProviders.html" class="whiteBtn"><fmt:message
                                                                  key="openiam.ui.button.cancel"
                                                            /></a></li>
                                                <c:if test="${! empty requestScope.provider.id}">
                                                      <li class="rightBtn"><a id="deleteProvider"
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
            <div class="title">
                  <fmt:message key="contentprovider.provider.supp.auth.levels" />
            </div>
            <div class="frameContentDivider">
                  <div id="authenticationLevels"></div>
            </div>
      </div>
      <c:if test="${! empty requestScope.provider and ! empty requestScope.provider.id}">
            <div>
                  <div id="title" class="title">
                        <fmt:message key="contentprovider.provider.app.server" />
                  </div>
                  <div class="frameContentDivider">
                        <div id="serverWarning" class="warning" style="display: none">
                              <fmt:message key="contentprovider.provider.app.server.req" />
                        </div>
                        <div id="applicationServerContainer"></div>
                  </div>
            </div>
            <div>
                  <div id="title" class="title">
                        <fmt:message key="contentprovider.provider.uri.patterns" />
                  </div>
                  <div class="frameContentDivider">
                        <div id="uriPatternContainer"></div>
                  </div>
            </div>
      </c:if>
      <div id="editDialog"></div>
</body>
</html>