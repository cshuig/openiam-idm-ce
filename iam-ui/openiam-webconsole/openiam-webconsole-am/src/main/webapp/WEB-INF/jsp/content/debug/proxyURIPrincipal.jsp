
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
<title>${titleOrganizatioName}-<fmt:message key="contentprovider.debug.proxy.uri.principal.debug" /></title>
<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
      type="text/css"
/>
<openiam:overrideCSS />
<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
<script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>

<script type="text/javascript">
    OPENIAM = window.OPENIAM || {};
    OPENIAM.ENV = window.OPENIAM.ENV || {};
</script>
</head>
<body>
      <div id="title" class="title">
            <fmt:message key="contentprovider.debug.proxy.uri.principal.debug" />
      </div>
      <div class="frameContentDivider">
            <form method="POST" action="proxyURIPrincipalDebug.html">
                  <table cellpadding="8px" align="center">
                        <thead></thead>
                        <tbody>
                              <tr>
                                    <td><label><fmt:message key="openiam.ui.webconsole.user.principal" /></label></td>
                                    <td><input autocomplete="off" type="text" name="principal" class="full rounded"
                                          value="${requestScope.principal}"
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label><fmt:message key="contentprovider.debug.req.url" /></label></td>
                                    <td><input autocomplete="off" type="text" name="proxyURI" class="full rounded"
                                          value="${requestScope.proxyURI}"
                                    /></td>
                              </tr>
                        </tbody>
                        <tfoot>
                              <tr>
                                    <td colspan="2">
                                          <ul class="formControls">
                                                <li><a href="javascript:void(0)"> <input type="submit"
                                                            class="redBtn"
                                                            value="<fmt:message key="contentprovider.debug.check.federation" />"
                                                      />
                                                </a></li>
                                          </ul>
                                    </td>
                              </tr>
                        </tfoot>
                  </table>
            </form>
      </div>
      <c:if test="${! empty requestScope.debugResponse}">
            <div id="title" class="title">
                  <fmt:message key="contentprovider.debug.login.response" />
            </div>
            <div class="frameContentDivider">
                  <table cellpadding="8px" align="center">
                        <tbody>
                              <c:choose>
                                    <c:when test="${requestScope.debugResponse.status eq 'SUCCESS'}">
                                          <tr>
                                                <td><fmt:message key="contentprovider.debug.success.login.resp" /></td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="contentprovider.debug.token.resp"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.tokenType}</span>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message key="contentprovider.debug.token" /></label>
                                                      <span>${requestScope.debugResponse.ssoToken.token}</span></td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="openiam.ui.webconsole.am.authlevel"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.authLevel}</span>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="contentprovider.debug.provider"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.provider}</span>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="openiam.ui.audit.log.client.ip"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.clientIP}</span>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="contentprovider.debug.principal"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.principal}</span>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="contentprovider.debug.max.idle.time"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.maxIdleTime}</span>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="contentprovider.debug.exp.time"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.expirationTime}</span>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="contentprovider.debug.create.time"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.createTime}</span>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td><label><fmt:message
                                                                  key="openiam.ui.idm.prov.table.col.user.id"
                                                            /></label> <span>${requestScope.debugResponse.ssoToken.userId}</span>
                                                </td>
                                          </tr>
                                    </c:when>
                                    <c:otherwise>
                                          <tr>
                                                <td><fmt:message key="contentprovider.debug.fail.login.resp" /></td>
                                          </tr>
                                          <tr>
                                                <td><fmt:message key="openiam.ui.idm.prov.table.col.reason" />:
                                                      ${requestScope.debugResponse.errorCode}</td>
                                          </tr>
                                          <tr>
                                                <td><fmt:message key="contentprovider.debug.login.error" />:
                                                      ${requestScope.debugResponse.loginError}</td>
                                          </tr>
                                    </c:otherwise>
                              </c:choose>
                        </tbody>
                        <tfoot>
                        </tfoot>
                  </table>
            </div>
      </c:if>
</body>
</html>