
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
<title>${titleOrganizatioName}-${pageTitle}</title>
<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
<openiam:overrideCSS />
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery.corner.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/jquery.boxshadow.js"></script>
<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>

<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/menutree.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/outer.frame.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/api/openiam.api.js"></script>
<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.initialMenu = <c:choose><c:when test="${! empty requestScope.initialMenu}">${requestScope.initialMenu}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Context = "${requestScope.contextPath}";
			OPENIAM.ENV.OuterFrameContextPath = "${requestScope.contextPath}";
			OPENIAM.ENV.FrameURL = <c:choose><c:when test="${! empty requestScope.frameURL}">"${requestScope.frameURL}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
</head>
<body>
      <div id="top">
            <div class="wrapper">
                  <div id="header">
                        <div id="logo">
                              <a href="${requestScope.currentURI}" class="logo slogo">Openiam</a>
                        </div>
                        <div id="right-header">
                              <div id="notification">
                                    <div class="row">${welcomeMessage}&nbsp;${requestScope.userName}</div>
                              </div>
                              <div id="logout">
                                    <a href="/idp/logout.html" class="redBtn">${logoutBtn}</a>
                              </div>
                              <c:if test="${not empty supportedLanguageList}">
                                  <div id="lang-header">
                                        <ul id="lang">
                                              <li><c:forEach var="ln" items="${supportedLanguageList}">
                                                          <c:if test="${selectedLanguage.id==ln.id}">
                                                                <a class="first" onclick="javascript:return false;"
                                                                      href="javascript:void(0);"
                                                                ><span style="${openiam:getFlagStyle(selectedCountry)}">&nbsp;</span>${ln.name}</a>
                                                          </c:if>
                                                    </c:forEach>

                                                    <ul>
                                                          <c:forEach var="ln" items="${supportedLanguageList}">
                                                                <c:forEach var="loc" items="${ln.locales}">
                                                                      <li><a href="javascript:void(0);"
                                                                            onclick="OPENIAM.Language.switchLanguage('${localeParam}', '${loc.key}');"
                                                                      ><span style="${openiam:getFlagStyle(loc.key)}">&nbsp;</span>${ln.name}</a></li>
                                                                </c:forEach>
                                                          </c:forEach>
                                                    </ul></li>
                                        </ul>
                                  </div>
                              </c:if>
                        </div>
                  </div>
                  <div id="nav"></div>
                  <div id="breadcrumb"></div>
            </div>
      </div>

      <div id="middle">
            <div class="wrapper">
                  <div id="submenu" style="display: none"></div>
                  <div id="leftshadow"></div>
                  <div id="rightshadow"></div>
                  <div id="content">
                        <iframe id="contentFrame" frameborder="0" src=""  scrolling="no"></iframe>
                  </div>
            </div>
      </div>
      <div id="bottom">
          <div class="wrapper">
              <div id="footer">
                  <div id="footnav">
                      ${footerNav}
                  </div>
                  <div id="copy">
                      ${footerCopyright}
                  </div>
              </div>
          </div>
      </div>
</body>
</html>