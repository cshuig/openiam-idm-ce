
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
<title>${titleOrganizatioName}-<fmt:message key="webconsole.ui.theme.name.edit.title" /></title>
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

<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/webconsole/uithemes/ui.theme.edit.js"></script>
<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ThemeId = <c:choose><c:when test="${! empty requestScope.theme.id}">"${requestScope.theme.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.theme.id}">"id=${requestScope.theme.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Theme = ${requestScope.uiThemeAsJSON};
		</script>
</head>
<body>
      <div id="title" class="title">
            <c:choose>
                  <c:when test="${! empty requestScope.theme.name}">
                        <fmt:message key="webconsole.ui.theme.name.edit.title" />: ${requestScope.theme.name}
				</c:when>
                  <c:otherwise>
                        <fmt:message key="webconsole.ui.theme.name.new.title" />
                  </c:otherwise>
            </c:choose>
      </div>
      <div class="frameContentDivider">
            <form method="post">
                  <table cellpadding="8px" align="center" class="fieldset">
                        <tbody>
                              <tr>
                                    <td><label class="required"> <fmt:message
                                                      key="webconsole.ui.theme.name"
                                                />
                                    </label></td>
                                    <td><input id="name" name="name" type="text" class="full rounded" /></td>
                              </tr>
                              <tr>
                                    <td><label class="required"
                                          title="<fmt:message
                                                      key="webconsole.ui.theme.style.url.title"
                                                />"
                                    ><fmt:message key="webconsole.ui.theme.style.url" /></label></td>
                                    <td><input id="url" name="url" type="text" class="full rounded" /></td>
                              </tr>
                        </tbody>
                        <tfoot>
                              <tr>
                                    <td colspan="2">
                                          <ul class="formControls">
                                                <li class="leftBtn"><a id="saveEntity" href="javascript:void(0)">
                                                            <input type="submit" class="redBtn"
                                                            value="<fmt:message
                                                                  key="openiam.ui.common.save"
                                                            />"
                                                      />
                                                </a></li>
                                                <li class="leftBtn"><a href="uiThemes.html" class="whiteBtn"><fmt:message
                                                                  key="openiam.ui.common.cancel"
                                                            /></a></li>
                                                <c:if test="${! requestScope.isNew}">
                                                      <li class="rightBtn"><a id="deleteEntity"
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