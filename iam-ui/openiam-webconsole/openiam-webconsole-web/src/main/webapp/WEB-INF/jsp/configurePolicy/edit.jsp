<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.it.policy.page.title" /></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/css/webconsole/role.edit.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	<openiam:overrideCSS />
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/configurePolicy/it.policy.js"></script>

    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Text = {
            ResetWarn :  localeManager["openiam.ui.it.policy.reset"]
        }
    </script>
</head>
<body>

    <form:form id="itPolicyForm" commandName="itPolicyBean">
    <form:hidden path="policyId" />
    <div class="title">
        <fmt:message key="openiam.ui.it.policy.page.title" />
    </div>
    <div class="frameContentDivider">
    <table cellpadding="8px" align="center" class="fieldset">
        <thead>
        <tr>
            <th colspan="2">
                <spring:hasBindErrors name="itPolicyBean">
                    <div class="alert alert-error">
                        <form:errors path="*" cssClass="error"/>
                    </div>
                </spring:hasBindErrors>
                <c:if test="${! empty messages}">
                    ${messages}
                </c:if>
            </th>
        </tr>
        </thead>
        <tbody>
        <tr class="common-row">
            <td>
                <label><fmt:message key="openiam.ui.it.policy.label.approve" /></label>
            </td>
            <td>
                <form:radiobuttons path="approveType" delimiter="&nbsp;&nbsp;" items="${approveTypeItems}" />
            </td>
        </tr>
        <tr class="common-row">
            <td>
                <label><fmt:message key="openiam.ui.common.status" /></label>
            </td>
            <td>
                <form:select path="active" cssClass="select rounded" items="${statusItems}" />
            </td>
        </tr>
        </tbody>
    </table>
    <table cellpadding="8px" align="center">
        <tbody>
        <tr>
            <td colspan="2">
                <form:textarea path="policyContent" cssClass="full rounded _input_tiptip" cssStyle="width: 700px; height: 350px;" />
            </td>
        </tr>
        </tbody>
    </table>
        <table cellpadding="8px" align="center" class="fieldset">
             <tbody>
            <tr class="common-row">
                <td>
                    <label><fmt:message key="openiam.ui.it.policy.label.confirmation" /></label>
                </td>
                <td>
                    <form:input maxlength="255" path="confirmation" cssClass="full rounded" />
                </td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="2" align="right">
                    <ul class="formControls">
                        <c:if test="${not empty itPolicyBean.policyId}">
                            <li>
                                <a id="resetITPolicy" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.common.reset" /></a>
                            </li>
                        </c:if>
                        <li>
                            <a href="javascript:void(0)">
                                <input type="submit" class="redBtn" value='<fmt:message key="openiam.ui.common.save" />' />
                            </a>
                        </li>
                    </ul>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>
    </form:form>
</body>
</html>