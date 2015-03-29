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
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.user.title.user.policy"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/css/selfservice/selfservice.my.info.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
          type="text/css"/>
    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css"/>
    <openiam:overrideCSS/>
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/use.policy.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = null;
    </script>
</head>
<body>
<div class="title">
    <fmt:message key="openiam.ui.selfservice.user.it.use.policy"/>
</div>
<div class="frameContentDivider">
    <form id="itPolicyForm" action="usePolicy.html" method="post">
        <input type="hidden" name="postbackUrl" value="${requestScope.postbackUrl}"/>
        <table cellpadding="8px" align="center">
            <c:if test="${! empty messages}">
                <thead>
                <tr>
                    <th colspan="2">
                        <div class="alert alert-error">
                                ${messages}
                        </div>
                    </th>
                </tr>
                </thead>
            </c:if>
            <tbody>
            <tr>
                <td>
                    <div name="licenseAgreement" class="policyContent">${requestScope.usePolicy.policyContent}</div>
                </td>
            </tr>
            <tr align="center">
                <td>
                    ${requestScope.usePolicy.confirmation}
                </td>
            </tr>
            <tr align="center">
                <td>
                    <ul class="formControls">
                        <li class="leftBtn">
                            <input type="submit" name="decline" class="whiteBtn"
                                   value="<fmt:message key='openiam.ui.common.decline' />"/>
                        </li>
                        <li class="rightBtn">
                            <input type="submit" name="accept" class="redBtn" style="display: none;"
                                   value="<fmt:message key='openiam.ui.common.accept' />"/>
                        </li>
                    </ul>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>