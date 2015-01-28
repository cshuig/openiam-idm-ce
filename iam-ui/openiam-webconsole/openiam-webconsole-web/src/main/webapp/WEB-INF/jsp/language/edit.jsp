
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

<c:set var="pageTitle">
      <c:choose>
            <c:when test="${requestScope.isAdd}">
                <fmt:message key="openiam.ui.language.create.new" />
		</c:when>
            <c:otherwise>
                <fmt:message key="openiam.ui.language.edit" />
		</c:otherwise>
      </c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${titleOrganizatioName}-${pageTitle}</title>
<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/css/webconsole/language.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
      type="text/css"
/>
<openiam:overrideCSS />

<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole/language/language.edit.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/language/language.admin.js"></script>
<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Bean = ${requestScope.jsonLanguage};
			OPENIAM.ENV.LocaleView = ${requestScope.jsonLanguageView};
		</script>
</head>
<body>
      <div id="title" class="title"></div>
      <div class="frameContentDivider">
            <table cellpadding="8px" align="center" class="fieldset">
                  <tbody>
                        <tr>
                              <td><label for="displayNameMap" class="required"><spring:message
                                                code='openiam.ui.common.display.name'
                                          /></label></td>
                              <td>
                                    <div id="displayNameMap"></div>
                              </td>
                        </tr>
                        <c:if test='${requestScope.isAdd}'>
                              <tr>
                                    <td><label for="name" class="required"><spring:message
                                                      code='openiam.ui.common.display.name.cur'
                                                /></label></td>
                                    <td><input id="name" autocomplete="off" type="text" class="full rounded"
                                          maxlength="25"
                                    /></td>
                              </tr>
                        </c:if>
                        <tr>
                              <td><label for="code" class="required"><spring:message
                                                code='openiam.ui.common.code'
                                          /></label></td>
                              <td><input id="code" autocomplete="off" type="text" class="full rounded"
                                    maxlength="2"
                              /></td>
                        </tr>
                        <tr>
                              <td><label for="localesMap" class="required"><spring:message
                                                code='openiam.ui.webconsole.languages.locale'
                                          /></label></td>
                              <td>
                                    <div id="localesMap"></div>
                              </td>
                        </tr>
                        <tr>
                              <td><label for="active"><label for="localesMap" class="required"><spring:message
                                                      code='openiam.ui.common.is.active'
                                                /></label></td>
                              <td><input id="active" autocomplete="off" type="checkbox" class="rounded" /></td>
                        </tr>
                        <tr>
                              <td><label for="isDefault"><spring:message
                                                code='openiam.ui.common.is.default'
                                          /></label></td>
                              <td><input id="isDefault" autocomplete="off" type="checkbox" class="rounded" /></td>
                        </tr>
                  </tbody>
                  <tfoot>
                        <tr>
                              <td colspan="2">
                                    <ul class="formControls">
                                          <li><a id="saveBtn" href="javascript:void(0)" class="redBtn"><spring:message
                                                            code='openiam.ui.common.save'
                                                      /></a></li>
                                          <li><a href="languages.html" class="whiteBtn"><spring:message
                                                            code='openiam.ui.common.cancel'
                                                      /></a></li>
                                    </ul>
                              </td>
                        </tr>
                  </tfoot>
            </table>
      </div>
</body>
</html>
