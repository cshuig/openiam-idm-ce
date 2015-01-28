
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
<title>${titleOrganizatioName}-<fmt:message key="openiam.ui.federation.debug.uri" /></title>
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
	        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
	        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
	    </script>
</head>
<body>
      <div id="title" class="title">
            <fmt:message key="contentprovider.provider.debug.uri.federation" />

      </div>
      <div class="frameContentDivider">
            <form method="POST" action="federateURIDebug.html">
                  <table cellpadding="8px" align="center">
                        <thead></thead>
                        <tbody>
                              <tr>
                                    <td><label> <fmt:message key="contentprovider.provider.debug.user.id" /></label></td>
                                    <td><input autocomplete="off" type="text" name="userId" class="full rounded"
                                          <c:if test="${! empty requestScope.uriFederationRequestModel}">value="${requestScope.uriFederationRequestModel.userId}"</c:if>
                                    /></td>
                              </tr>
                              <tr>
                                    <td><label> <fmt:message key="contentprovider.debug.req.url" /></label></td>
                                    <td><input autocomplete="off" type="text" name="url" class="full rounded"
                                          <c:if test="${! empty requestScope.uriFederationRequestModel}">value="${requestScope.uriFederationRequestModel.url}"</c:if>
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
      <c:if test="${! empty requestScope.federationResponse}">
            <div id="title" class="title">
                  <fmt:message key="contentprovider.provider.debug.uri.federation.response" />
            </div>
            <div class="frameContentDivider">
                  <table cellpadding="8px" align="center">
                        <tbody>
                              <c:choose>
                                    <c:when test="${requestScope.federationResponse.status eq 'SUCCESS'}">
                                          <tr>
                                                <td><fmt:message
                                                            key="contentprovider.provider.debug.uri.federation.success"
                                                      /></td>
                                          </tr>
                                          <tr>
                                                <td><fmt:message
                                                            key="contentprovider.provider.debug.content.provider.id"
                                                      />: ${requestScope.federationResponse.cpId}</td>
                                          </tr>
                                          <tr>
                                                <td><fmt:message key="contentprovider.provider.debug.pattern.id" />:
                                                      ${requestScope.federationResponse.patternId}</td>
                                          </tr>
                                          <c:if test="${! empty requestScope.federationResponse.server}">
                                                <tr>
                                                      <td>
                                                            <table cellpadding="8px" align="center">
                                                                  <thead>
                                                                        <tr>
                                                                              <td><fmt:message
                                                                                          key="contentprovider.provider.debug.server"
                                                                                    /></td>
                                                                        </tr>
                                                                  </thead>
                                                                  <tbody>
                                                                        <tr>
                                                                              <td><fmt:message
                                                                                          key="contentprovider.provider.debug.id"
                                                                                    />:
                                                                                    ${requestScope.federationResponse.server.id}
                                                                              </td>
                                                                        </tr>
                                                                        <tr>
                                                                              <td><fmt:message
                                                                                          key="contentprovider.provider.debug.server.url"
                                                                                    />:
                                                                                    ${requestScope.federationResponse.server.serverURL}
                                                                              </td>
                                                                        </tr>
                                                                        <tr>
                                                                              <td><fmt:message
                                                                                          key="contentprovider.provider.debug.content.provider.id"
                                                                                    />:
                                                                                    ${requestScope.federationResponse.server.contentProviderId}
                                                                              </td>
                                                                        </tr>
                                                                  </tbody>
                                                            </table>
                                                      </td>
                                                </tr>
                                          </c:if>
                                          <c:if
                                                test="${! empty requestScope.federationResponse.ruleTokenList and fn:length(requestScope.federationResponse.ruleTokenList) > 0}"
                                          >
                                                <c:forEach var="token"
                                                      items="${requestScope.federationResponse.ruleTokenList}"
                                                >
                                                      <tr>
                                                            <td>
                                                                  <table cellpadding="8px" align="center">
                                                                        <thead>
                                                                              <tr>
                                                                                    <td colspan="2"><fmt:message
                                                                                                key="openiam.ui.report.parameter.meta.type"
                                                                                          /></td>
                                                                              </tr>
                                                                              <tr>
                                                                                    <td><fmt:message
                                                                                                key="contentprovider.provider.debug.id"
                                                                                          />:</td>
                                                                                    <td>${token.metaType.id}</td>
                                                                              </tr>
                                                                              <tr>
                                                                                    <td><fmt:message
                                                                                                key="openiam.ui.idm.mngconn.table.col.name"
                                                                                          />:</td>
                                                                                    <td>${token.metaType.name}</td>
                                                                              </tr>
                                                                        </thead>
                                                                        <c:if
                                                                              test="${! empty token.valueList and fn:length(token.valueList) > 0}"
                                                                        >
                                                                              <tbody>
                                                                              <thead>
                                                                                    <tr>
                                                                                          <td colspan="2">
                                                                                          	<fmt:message key="contentprovider.provider.debug.pair"/> :
                                                                                          </td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                          <td>
                                                                                          	<fmt:message key="contentprovider.provider.debug.key" />
                                                                                          </td>
                                                                                          <td>
                                                                                          	<fmt:message key="contentprovider.provider.debug.value" />
                                                                                          </td>
                                                                                          <td>
                                                                                          	<fmt:message key="openiam.ui.meta.rule.propagate.through.proxy" />
                                                                                          </td>
                                                                                    </tr>
                                                                              </thead>
                                                                              <c:forEach var="value"
                                                                                    items="${token.valueList}"
                                                                              >
                                                                                    <tr>
                                                                                          <td>${value.key}</td>
                                                                                          <td>${value.value}</td>
                                                                                          <td>${value.propagate}</td>
                                                                                    </tr>
                                                                              </c:forEach>
                                                                              </tbody>
                                                                        </c:if>
                                                                  </table>
                                                            </td>
                                                      </tr>
                                                </c:forEach>
                                          </c:if>
                                          <table cellpadding="8px" align="center">
                                                <thead>
                                                      <tr>
                                                            <th><fmt:message
                                                                        key="contentprovider.provider.debug.auth.level.token"
                                                                  /></th>
                                                      </tr>
                                                </thead>
                                                <tbody>
                                                      <c:choose>
                                                            <c:when
                                                                  test="${! empty requestScope.federationResponse.authLevelTokenList and fn:length(requestScope.federationResponse.authLevelTokenList) > 0}"
                                                            >
                                                                  <c:forEach var="authLevelToken"
                                                                        items="${requestScope.federationResponse.authLevelTokenList}"
                                                                  >
                                                                        <tr>
                                                                              <td>
                                                                                    <table>
                                                                                          <thead>
                                                                                                <tr>
                                                                                                      <td><fmt:message
                                                                                                                  key="contentprovider.provider.debug.auth.level.id"
                                                                                                            />:
                                                                                                            ${authLevelToken.authLevelId}
                                                                                                      </td>
                                                                                                </tr>
                                                                                          </thead>
                                                                                          <c:if
                                                                                                test="${! empty authLevelToken.attributes and fn:length(authLevelToken.attributes) > 0}"
                                                                                          >
                                                                                                <tbody>
                                                                                                      <c:forEach
                                                                                                            var="authLevelAtribute"
                                                                                                            items="${authLevelToken.attributes}"
                                                                                                      >
                                                                                                            <tr>
                                                                                                                  <td>
                                                                                                                        <table>
                                                                                                                              <tbody>
                                                                                                                                    <tr>
                                                                                                                                          <td>
                                                                                                                                                <label><fmt:message
                                                                                                                                                            key="openiam.ui.idm.mngconn.table.col.name"
                                                                                                                                                      /></label>
                                                                                                                                          </td>
                                                                                                                                          <td>
                                                                                                                                                ${authLevelAtribute.name}
                                                                                                                                          </td>
                                                                                                                                    </tr>
                                                                                                                                    <tr>
                                                                                                                                          <td>
                                                                                                                                                <label><fmt:message
                                                                                                                                                            key="contentprovider.provider.debug.string.value"
                                                                                                                                                      /></label>
                                                                                                                                          </td>
                                                                                                                                          <td>
                                                                                                                                                ${authLevelAtribute.valueAsString}
                                                                                                                                          </td>
                                                                                                                                    </tr>
                                                                                                                                    <tr>
                                                                                                                                          <td>
                                                                                                                                                <label><fmt:message
                                                                                                                                                            key="contentprovider.provider.debug.byte.value"
                                                                                                                                                      /></label>
                                                                                                                                          </td>
                                                                                                                                          <td>
                                                                                                                                                ${authLevelAtribute.valueAsByteArray}
                                                                                                                                          </td>
                                                                                                                                    </tr>
                                                                                                                                    <tr>
                                                                                                                                          <td>
                                                                                                                                                <label><fmt:message
                                                                                                                                                            key="contentprovider.provider.debug.type.name"
                                                                                                                                                      /></label>
                                                                                                                                          </td>
                                                                                                                                          <td>
                                                                                                                                                ${authLevelAtribute.typeName}
                                                                                                                                          </td>
                                                                                                                                    </tr>
                                                                                                                                    <tr>
                                                                                                                                          <td>
                                                                                                                                                <label><fmt:message
                                                                                                                                                            key="contentprovider.provider.debug.type.id"
                                                                                                                                                      /></label>
                                                                                                                                          </td>
                                                                                                                                          <td>
                                                                                                                                                ${authLevelAtribute.typeId}
                                                                                                                                          </td>
                                                                                                                                    </tr>
                                                                                                                              </tbody>
                                                                                                                        </table>
                                                                                                                  </td>
                                                                                                            </tr>
                                                                                                      </c:forEach>
                                                                                                </tbody>
                                                                                          </c:if>

                                                                                    </table>
                                                                              </td>
                                                                        </tr>
                                                                  </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                  <tr>
                                                                        <td><fmt:message
                                                                                    key="contentprovider.provider.debug.no.auth.send"
                                                                              /></td>
                                                                  </tr>
                                                            </c:otherwise>
                                                      </c:choose>
                                                </tbody>
                                          </table>
                                    </c:when>
                                    <c:otherwise>
                                          <tr>
                                                <td><fmt:message key="contentprovider.provider.debug.failure" /></td>
                                          </tr>
                                          <tr>
                                                <td><fmt:message key="contentprovider.provider.debug.reason" />:
                                                      ${requestScope.federationResponse.errorCode}</td>
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