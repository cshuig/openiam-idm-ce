<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ taglib prefix="iam" uri="http://openiam.com/taglibs/functions"%>

<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.user.delegation.page.title"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />

    <%--<link href="/openiam-ui-static/plugins/multiselect/css/multiselect.css" rel="stylesheet" type="text/css" />--%>
    <openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/organization.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/role.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/group.search.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/user/user.delegation.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.UserId = <c:choose><c:when test="${! empty user.id}">"${user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty user.id}">"id=${user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.DEL_FILTER = <c:choose><c:when test="${! empty requestScope.filter}">${requestScope.filter}</c:when><c:otherwise>null</c:otherwise></c:choose>;

        OPENIAM.ENV.Text = {
            EmptyResults:  localeManager["openiam.ui.common.no.data.were.found"]
        }
    </script>
</head>
<body>
<div class="title">
    <fmt:message key="openiam.ui.delegation.filter.user" />: ${user.displayName}
</div>
<div class="frameContentDivider">
    <form id="delegationForm">
        <input id="userId" name="userId" type="hidden" value="${user.id}" />
        <table cellpadding="8px" align="center" class="fieldset">
            <tbody>
                <tr>
                    <td><fmt:message key="openiam.ui.common.organizations" /></td>
                    <td>
                        <a id="orgFilterLink" class="entity-link organization" href="javascript:void(0);"><fmt:message key="openiam.ui.common.organization.select"/></a>

                        <ul id="orgFilterContainer" class="multi-choices" style="display: none">
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="openiam.ui.common.divisions"/></td>
                    <td>
                        <a id="divFilterLink" class="entity-link organization" href="javascript:void(0);"><fmt:message key="openiam.ui.common.divisions.select"/></a>
                        <ul id="divFilterContainer" class="multi-choices" style="display: none">
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="openiam.ui.common.departments"/></td>
                    <td>
                        <a id="deptFilterLink" class="entity-link organization" href="javascript:void(0);"><fmt:message key="openiam.ui.common.departments.select"/></a>
                        <ul id="deptFilterContainer" class="multi-choices" style="display: none">
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="openiam.ui.user.delegation.org.inheritance"/></td>
                    <td>
                        <input id="useOrgInhFlag" type="checkbox"  value="true" name="useOrgInhFlag">
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="openiam.ui.webconsole.user.group"/></td>
                    <td>
                        <a id="groupListLink" class="entity-link group" href="javascript:void(0);"><fmt:message key="openiam.ui.group.select"/></a>
                        <ul id="groupListContainer" class="multi-choices" style="display: none">
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="openiam.ui.webconsole.user.role"/> </td>
                    <td>
                        <a id="roleListLink" class="entity-link role" href="javascript:void(0);"><fmt:message key="openiam.ui.role.select"/></a>
                        <ul id="roleListContainer" class="multi-choices" style="display: none">
                        </ul>
                    </td>
                </tr>
                <%--<tr>--%>
                    <%--<td><fmt:message key="openiam.ui.user.delegation.applications"/></td>--%>
                    <%--<td>--%>
                        <%--<a id="appListLink" class="entity-link resource" href="javascript:void(0);"><fmt:message key="openiam.ui.resource.select"/></a>--%>
                        <%--<ul id="appListContainer" class="multi-choices" style="display: none">--%>
                        <%--</ul>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <td><fmt:message key="openiam.ui.user.delegation.direct.report"/></td>
                    <td>
                        <input id="mngRptFlag" type="checkbox"  value="true" name="mngRptFlag">
                    </td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="2">
                        <ul class="formControls">
                            <li>
                                <a href="javascript:void(0)">
                                    <input type="submit" class="redBtn" value='<fmt:message key="openiam.ui.button.save"/>' />
                                </a>
                            </li>
                            <li>
                                <a href="editUser.html?id=${user.id}" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel"/></a>
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