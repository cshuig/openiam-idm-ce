
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${titleOrganizatioName}-<fmt:message key="selfservice.sso.head" /></title>
<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/css/selfservice/selfservice.my.apps.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
<openiam:overrideCSS />
<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
<script type="text/javascript">
    OPENIAM = window.OPENIAM || {};
    OPENIAM.ENV = window.OPENIAM.ENV || {};
</script>
</head>
<body>
      <div id="title" class="title">
            <fmt:message key="selfservice.sso.title" />
      </div>
      <div class="frameContentDivider">
            <c:choose>
                  <c:when test="${! empty requestScope.applications and fn:length(applications) > 0}">
                        <div>
                              <fmt:message key="selfservice.sso.empty" /> :
                        </div>
                        <div class="app-list">
                        <ul>
                              <c:forEach var="application" items="${requestScope.applications}">
                                    <li id="app-${application.id}"><c:choose>
                                                <c:when test="${! empty application.url}">
                                                      <a href="${application.url}" target="_blank" class="app-link"><span>${application.name}</span></a>
                                                </c:when>
                                                <c:otherwise>
                                                      <span>${application.name}</span>
                                                </c:otherwise>
                                          </c:choose></li>
                              </c:forEach>
                        </ul>
                  </c:when>
                  <c:otherwise>
                        <div>
                              <fmt:message key="selfservice.sso.no.apps" />
                        </div>
                  </c:otherwise>
            </c:choose>
      </div>
</body>
</html>