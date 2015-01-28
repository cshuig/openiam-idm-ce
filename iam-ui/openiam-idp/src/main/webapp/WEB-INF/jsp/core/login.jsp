<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.idp.login"/></title>
    <style type="text/css">
        html {
            visibility: hidden;
        }
    </style>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/openiam-ui-static/css/webconsole/login.css" rel="stylesheet" type="text/css" media="screen"/>
    <openiam:overrideCSS/>
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery.corner.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery.boxshadow.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/login.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.PostbackURL = "${requestScope.frameBustPostbackURL}";
    </script>
</head>
<body>
<c:import url="/WEB-INF/jsp/core/login-header.jsp"/>
<div id="pagebg">
    <div id="login">
        <div id="llogo">
            <a href="javascript:void(0);" class="logo llogo">Openiam</a>
        </div>

        <div id="credentials">
            <fmt:message key="openiam.idp.enter.user.credentials"/>
        </div>

        <form id="login-form" action="login.html" method="post">
            <c:if test="${! empty requestScope.error}">
                <p class="error">
                    <spring:message code="${requestScope.error.error.messageName}"
                                    arguments="${requestScope.error.params}"/>
                </p>
                <c:if test="${requestScope.error.error eq 'ACCOUNT_LOCKED'}">
                    <div class="infoLink">
                        <fmt:message key="openiam.ui.common.click"/> <a href="${requestScope.unlockURL}"><fmt:message
                            key="openiam.ui.common.here"/></a> <fmt:message key="openiam.idp.to.unlock.account"/>
                    </div>
                </c:if>
            </c:if>
            <input type="text" autocomplete="off" name="login" id="principal" class="loginField"
                   placeholder="Enter Login ID" initialized="${! empty requestScope.login}"
                   <c:if test="${! empty requestScope.login}">value="${requestScope.login}"</c:if> />
            <input type="password" autocomplete="off" name="password" id="input_password" class="passwordField"
                   placeholder="Enter your password" value="" initialized="false"/>

            <c:if test="${! empty requestScope.hiddenAttributes}">
                <c:forEach var="hiddenField" items="${requestScope.hiddenAttributes}">
                    <input type="hidden" name="${hiddenField.key}" value="${hiddenField.value}"/>
                </c:forEach>
            </c:if>
            <div class="lrow">
                <div class="login-options">
                    <c:if test="${requestScope.isSelfRegistrationEnabled eq true}">
                        <div>
                            <a id="selfRegistration" href="${requestScope.selfRegistrationURL}"><fmt:message
                                    key="openiam.ui.selfservice.ui.template.self.registration"/></a>
                        </div>
                    </c:if>
                    <c:if test="${! empty requestScope.displayHandler}">
                        <c:if test="${! empty requestScope.displayHandler.additionalHyperlinks and fn:length(requestScope.displayHandler.additionalHyperlinks) > 0}">
                            <c:forEach var="link" items="${requestScope.displayHandler.additionalHyperlinks}">
                                <div><a href="${link.href}">${link.text}</a></div>
                            </c:forEach>
                        </c:if>
                    </c:if>
                    <c:if test="${requestScope.forgotUsernameEnabled}">
                        <div>
                            <a id="forgotUsername" href="forgotUsername.html"><fmt:message
                                    key="openiam.idp.forgot.username"/></a>
                        </div>
                    </c:if>
                    <c:if test="${requestScope.passwordUnlockEnabled}">
                        <div>
                            <a id="unlockPswd" href="unlockPassword.html"><fmt:message
                                    key="openiam.idp.forgot.password"/></a>
                        </div>
                    </c:if>
                </div>
                <input type="submit" class="redBtn loginBtn" value="<fmt:message key='openiam.idp.login' />"/>
            </div>
        </form>

        <div id="lbuttons">

        </div>
    </div>
    <c:import url="/WEB-INF/jsp/core/login-footer.jsp"/>
</div>
</body>
</html>