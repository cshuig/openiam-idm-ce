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
    <title>${titleOrganizatioName} - <fmt:message key="openiam.idp.openiam.unlock.user"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/css/common/hideShowPassword.css" rel="stylesheet" type="text/css"/>
    <openiam:overrideCSS/>
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery.corner.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery.boxshadow.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/hideShowPassword.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/user/unlock.password.questions.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
    </script>
</head>
<body>
<c:import url="/WEB-INF/jsp/core/login-header.jsp"/>
<div id="top" class="standalone">
    <div class="wrapper">
        <div id="header">
            <div id="logo">
                <a href="javascript:void(0);" class="logo slogo">Openiam</a>
            </div>
        </div>
    </div>
</div>

<div id="middle">
    <div class="wrapper">
        <div id="submenu" style="display:none"></div>
        <div id="leftshadow"></div>
        <div id="rightshadow"></div>
        <div id="content">
            <div id="title" class="title">
                <fmt:message key="openiam.idp.answer.response.questions.unlock.account"/>
            </div>
            <c:choose>
                <c:when test="${! empty requestScope.unlockUserToken and ! empty requestScope.unlockUserToken.questionList and fn:length(requestScope.unlockUserToken.questionList) > 0 }">
                    <div class="frameContentDivider">
                        <form id="unlockUserForm">
                            <c:if test="${! empty requestScope.unlockUserToken.hiddenAttributes}">
                                <c:forEach var="attribute" items="${requestScope.unlockUserToken.hiddenAttributes}">
                                    <input type="hidden" id="${attribute.key}" name="${attribute.key}"
                                           value="${attribute.value}"/>
                                </c:forEach>
                            </c:if>
                            <table cellpadding="8px" align="center">
                                <thead>
                                <tr>
                                    <td id="error">
                                        <p class="error">
                                        </p>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="question" items="${requestScope.unlockUserToken.questionList}">
                                    <tr>
                                        <td>
                                            <label>${question.displayName}</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <c:if test="${not requestScope.secureAnswers}">
                                                <input type="password"
                                                       class="answer full rounded hideShowPassword-field"
                                                       oiamQuestionId="${question.id}"/>
                                            </c:if>
                                            <c:if test="${requestScope.secureAnswers}">
                                                <input type="password" class="answer full rounded"
                                                       oiamQuestionId="${question.id}"/>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td>
                                        <ul class="formControls">
                                            <li>
                                                <a href="javascript:void(0)">
                                                    <input id="save" type="submit" class="redBtn"
                                                           value="<fmt:message key='openiam.ui.common.submit' />"/>
                                                </a>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="warn"><fmt:message key="openiam.ui.login.invalid.configuration"/></div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/jsp/core/login-footer.jsp"/>
</body>
</html>