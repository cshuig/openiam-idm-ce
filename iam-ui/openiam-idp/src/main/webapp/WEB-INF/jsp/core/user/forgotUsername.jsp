<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.idp.forgot.username"/></title>
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
    <script type="application/javascript">
        $(document).ready(function () {
            $("#forgot-principal-form").on("submit", function () {
                $("#email").addClass("emailField");

                if (!$("#email").val() || $("#email").val() == '') {
                    $("#email").addClass("red-error");
                    return false;
                }
            });
        });
    </script>
    <style type="text/css">
        .red-error {
            background-color: #ffc8c6 !important;
            width: 245px !important;
            border: 1px solid #efb6b5 !important;
            border-radius: 5px !important;
            color: #ad5b5f !important;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/jsp/core/login-header.jsp"/>
<div id="pagebg">
    <div id="login">
        <div id="llogo">
            <a href="javascript:void(0);" class="logo llogo">Openiam</a>
        </div>

        <div id="credentials">
            <fmt:message key="openiam.idp.forgot.username.information.message"/>
        </div>

        <form id="forgot-principal-form" action="forgotUsername.html" method="post">
            <c:if test="${! empty requestScope.error}">
                <p class="error">
                    <spring:message code="${requestScope.error.error.messageName}"
                                    arguments="${requestScope.error.params}"/>
                </p>
            </c:if>
            <c:choose>
                <c:when test="${! empty requestScope.success}">
                    <p class="success">
                        <spring:message code="${requestScope.success.message.messageName}"/>
                        <script type="application/javascript">
                            setTimeout(function () {
                                window.location.href = "${requestScope.redirectURL}";
                            }, 5000);
                        </script>
                    </p>
                </c:when>
                <c:otherwise>
                    <c:set var="emailPlaceholder">
                        <fmt:message key='openiam.idp.enter.email'/>
                    </c:set>

                    <input type="text" id="email" name="email" class="emailField" placeholder="${emailPlaceholder}"
                           value="${requestScope.email}" initialized="${not empty requestScope.email}"/>

                    <div>
                        <input id="submit" type="submit" class="redBtn loginBtn"
                               value="<fmt:message key='openiam.idp.send.login.information' />"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
    <c:import url="/WEB-INF/jsp/core/login-footer.jsp"/>
</div>
</body>
</html>