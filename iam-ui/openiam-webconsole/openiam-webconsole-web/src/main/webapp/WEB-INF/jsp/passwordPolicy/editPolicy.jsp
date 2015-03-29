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
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.policy.edit"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/css/webconsole/password.policy.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
          type="text/css"/>
    <openiam:overrideCSS/>

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/policy/policy.edit.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.PolicyId = <c:choose><c:when test="${! empty requestScope.policy.policyId}">"${requestScope.policy.policyId}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.PolicyDefId = <c:choose><c:when test="${! empty requestScope.policy.policyDefId}">"${requestScope.policy.policyDefId}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.policy.policyId}">"id=${requestScope.policy.policyId}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Text = {
            DeleteWarn: localeManager["openiam.ui.webconsole.policy.password.delete.warn"]
        };
    </script>
</head>
<body>
<div id="title" class="title">
    <c:choose>
        <c:when test="${! empty requestScope.policy}">
            <fmt:message key="openiam.ui.webconsole.policy.edit"/>: ${requestScope.policy.name}
        </c:when>
        <c:otherwise>
            <fmt:message key="openiam.ui.webconsole.policy.password.edit.create.new.password"/>
        </c:otherwise>
    </c:choose>
</div>

<div class="frameContentDivider">
    <input type="hidden" name="policyDefId"/>

    <div class="title"><fmt:message key="openiam.ui.webconsole.policy.password.overview"/></div>
    <table cellpadding="8px" align="center" class="fieldset">
        <tr>
            <td><label for="name" class="required"><fmt:message key="openiam.ui.common.name"/></label>
            </td>
            <td><input id="name" name="name" type="text"
                       class="full rounded"
                       value="<c:if test="${! empty requestScope.policy}">${requestScope.policy.name}</c:if>"
                    />
            </td>
        </tr>
        <tr>
            <td><fmt:message key="openiam.ui.common.description"/></td>
            <td><textarea id="description" name="description" size="100" class="full rounded"><c:if
                    test="${! empty requestScope.policy}">${requestScope.policy.description}
            </c:if></textarea></td>
        </tr>
        <tr>
            <td><fmt:message key="openiam.ui.webconsole.policy.status"/></td>
            <td><select id="status" name="status" class="rounded">
                <option value="1"
                        <c:if test="${!empty requestScope.policy and requestScope.policy.status eq 1}">selected</c:if> >
                    <fmt:message key="openiam.ui.common.active"/></option>
                <option value="0"
                        <c:if test="${! empty requestScope.policy and requestScope.policy.status eq 0}">selected</c:if> >
                    <fmt:message key="openiam.ui.common.inactive"/></option>
            </select></td>
        </tr>
    </table>

    <div class="title"><fmt:message key="openiam.ui.webconsole.policy.password.composition"/></div>
    <table id="passwordCompositionAttributes" align="center" cellpadding="8px">
    </table>

    <div class="title"><fmt:message key="openiam.ui.webconsole.policy.password.forget.parameter"/></div>
    <table id="passwordForgotAttributes" align="center" cellpadding="8px">
    </table>
    <div class="title"><fmt:message key="openiam.ui.webconsole.policy.password.change.rule"/></div>
    <table id="changeRuleAttributes" align="center" cellpadding="8px">
    </table>
    <p>
        <b><fmt:message key="openiam.ui.webconsole.policy.password.note"/>: </b><fmt:message
            key="openiam.ui.webconsole.policy.password.denotes.mandatoryoptional"/>
    </p>

    <table width="100%">
        <tr>
            <td colspan="2">
                <ul class="formControls">
                    <li class="leftBtn">
                        <a href="javascript:void(0)"><input type="button" class="redBtn" id="saveBtn"
                                                            value="<fmt:message key='openiam.ui.button.save' />"/></a>
                    </li>
                    <li class="leftBtn">
                        <a href="passwordPolicy.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel"/></a>
                    </li>
                    <c:if test="${! empty requestScope.policy}">
                        <li class="rightBtn">
                            <a id="deletePolicy" href="javascript:void(0);" class="redBtn"><fmt:message
                                    key="openiam.ui.button.delete"/></a>
                        </li>
                    </c:if>
                </ul>
            </td>
        </tr>
    </table>
</div>
</body>
</html>