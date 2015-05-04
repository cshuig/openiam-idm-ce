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
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>

<c:set var="title">
    <c:choose>
        <c:when test="${! empty requestScope.location.id}">
            <fmt:message key="openiam.ui.shared.location.edit" />: ${requestScope.location.name}
        </c:when>
        <c:otherwise>
            <fmt:message key="openiam.ui.webconsole.location.create.new" />
        </c:otherwise>
    </c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title}</title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/organization/location.edit.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/language/language.admin.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Location = <c:choose><c:when test="${! empty requestScope.locationAsJSON}">${requestScope.locationAsJSON}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.LocationId = <c:choose><c:when test="${! empty requestScope.location.id}">"${requestScope.location.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.location.organizationId}">"id=${requestScope.location.organizationId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.OrganizationId = <c:choose><c:when test="${! empty requestScope.location.organizationId}">"${requestScope.location.organizationId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Text = {
            DeleteWarn : localeManager["openiam.ui.webconsole.location.delete.ask"]
        };
    </script>
</head>
<body>
<div id="title" class="title">${title}</div>
<div class="frameContentDivider">
    <form id="locationForm" method="post">
        <table cellpadding="8px" align="center" class="fieldset">
            <tbody>
            <tr>
                <td>
                    <label for="name" class="required"><fmt:message key="openiam.ui.webconsole.location.name" />:</label>
                </td>
                <td>
                    <input id="name" autocomplete="off" maxlength="200" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="country"><fmt:message key="openiam.ui.webconsole.location.country" />:</label>
                </td>
                <td>
                    <input id="country" autocomplete="off" maxlength="200" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="bldgNum"><fmt:message key="openiam.ui.webconsole.location.bldgNum" />:</label>
                </td>
                <td>
                    <input id="bldgNum" autocomplete="off" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="streetDirection"><fmt:message key="openiam.ui.webconsole.location.streetDirection" />:</label>
                </td>
                <td>
                    <input id="streetDirection" autocomplete="off" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="address1"><fmt:message key="openiam.ui.webconsole.location.address1" />:</label>
                </td>
                <td>
                    <input id="address1" autocomplete="off" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="address2"><fmt:message key="openiam.ui.webconsole.location.address2" />:</label>
                </td>
                <td>
                    <input id="address2" autocomplete="off" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="address3"><fmt:message key="openiam.ui.webconsole.location.address3" />:</label>
                </td>
                <td>
                    <input id="address3" autocomplete="off" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="city"><fmt:message key="openiam.ui.webconsole.location.city" />:</label>
                </td>
                <td>
                    <input id="city" autocomplete="off" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="state"><fmt:message key="openiam.ui.webconsole.location.state" />:</label>
                </td>
                <td>
                    <input id="state" autocomplete="off" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="postalCd"><fmt:message key="openiam.ui.webconsole.location.postalCd" />:</label>
                </td>
                <td>
                    <input id="postalCd" autocomplete="off" maxlength="10" type="text" class="full rounded" />
                </td>
            </tr>
            <tr>
                <td><fmt:message key="openiam.ui.common.description" /></td>
                <td>
                    <textarea id="description" autocomplete="off" maxlength="200"  name="description" class="full rounded"></textarea>
                </td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="2">
                    <ul class="formControls">
                        <li>
                            <a id="saveBtn" href="javascript:void(0)">
                                <input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.button.save' />" />
                            </a>
                        </li>
                        <li>
                            <a href="organizationTypeSearch.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel" /></a>
                        </li>
                        <c:if test="${! empty requestScope.location.id}">
                            <li class="rightBtn">
                                <a id="deleteBtn" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete" /></a>
                            </li>
                        </c:if>
                    </ul>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
</body>
</html>