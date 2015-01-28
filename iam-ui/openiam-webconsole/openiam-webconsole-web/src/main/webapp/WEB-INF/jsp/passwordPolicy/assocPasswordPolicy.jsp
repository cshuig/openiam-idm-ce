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
<%@ page language="java" pageEncoding="utf-8"
         contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.policy.edit"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet"
          type="text/css"/>
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css"/>
    <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet"
          type="text/css"/>
    <link href="/openiam-ui-static/plugins/multiselect/css/multiselect.css" rel="stylesheet" type="text/css"/>
    <openiam:overrideCSS/>

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/policy/policyAssoc.edit.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/organization.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/role.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
    <script type="text/javascript"
            src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
    <script type="text/javascript"
            src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
    <script type="text/javascript"
            src="/openiam-ui-static/plugins/multiselect/js/multiselect.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.PolicyId = <c:choose><c:when test="${! empty requestScope.AssocList.policyId}">'${requestScope.AssocList.policyId}'
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.policy.policyId}">"id=${requestScope.policy.policyId}"
        </c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Text = {};
        OPENIAM.ENV.ROLE = <c:choose><c:when test="${! empty requestScope.role}">${requestScope.role}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ORGANIZATION = <c:choose><c:when test="${! empty requestScope.organization}">${requestScope.organization}</c:when><c:otherwise>null</c:otherwise></c:choose>;
    </script>
</head>
<body>

<div class="title"><fmt:message key="openiam.ui.webconsole.policy.password.association.associate.policy"/></div>
<div class="frameContentDivider">
    <form id="associateform" method="post">
        <input type="hidden" id="policyObjectId"
               value="<c:if test="${! empty requestScope.policyAssociation && requestScope.policyAssociation.policyObjectId != null}">${requestScope.policyAssociation.policyObjectId}</c:if>"/>
        <input type="hidden" id="policyId" value="${requestScope.policyAssociation.policyId}"/>
        <table cellpadding="8px" align="center" class="fieldset">
            <tbody>
            <tr>
                <td><fmt:message key="openiam.ui.webconsole.policy.password.association.level"/></td>
                <td class="tdlightnormal">
                    <select id="associationLevel" class="rounded select">
                        <option value="GLOBAL"
                                <c:if test="${requestScope.policyAssociation.associationLevel eq 'GLOBAL'}">selected</c:if> >
                            <fmt:message key="openiam.ui.webconsole.policy.password.association.level.global"/></option>
                        <option value="ROLE"
                                <c:if test="${requestScope.policyAssociation.associationLevel eq 'ROLE'}">selected</c:if> >
                            <fmt:message key="openiam.ui.webconsole.policy.password.association.level.role"/></option>
                        <option value="ORGANIZATION"
                                <c:if test="${requestScope.policyAssociation.associationLevel eq 'ORGANIZATION'}">selected</c:if> >
                            <fmt:message
                                    key="openiam.ui.webconsole.policy.password.association.level.organization"/></option>
                    </select>
                </td>
            </tr>

            <tr id="assocValueTr">
                <td><fmt:message key="openiam.ui.webconsole.policy.password.association.value"/></td>
                <td id="roleTd" class="tdlightnormal">
                    <div>
                        <a id="userRoleId" href="javascript:void(0);" class="entity-link role ui-search-enabled"></a>
                        <ul id="roleContainer" class="multi-choices" style="display: none"></ul>
                    </div>
                </td>
                <td class="tdlightnormal" id="organizationTd">
                    <div>
                        <a id="userOrgId" href="javascript:void(0);"
                           class="entity-link organization ui-search-enabled"></a>
                        <ul id="orgContainer" class="multi-choices" style="display: none"></ul>
                    </div>
                </td>

            </tr>


            </tbody>
            <tfoot>
            <tr>
                <td colspan="2">
                    <ul class="formControls">
                        <li><a href="javascript:void(0)"> <input type="submit"
                                                                 class="redBtn"
                                                                 value="<fmt:message key='openiam.ui.common.save' />"/>
                        </a>
                        </li>
                        <li><a href="editPolicy.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel"/></a>
                        </li>

                    </ul>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
<div id="editDialog"></div>
<div id="searchResultsContainer"></div>
</body>
</html>