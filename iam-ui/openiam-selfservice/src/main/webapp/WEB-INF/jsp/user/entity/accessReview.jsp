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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>
        <c:choose>
            <c:when test="${requestScope.type eq 'roles'}">
            <fmt:message key="openiam.ui.selfservice.user.access.review.roles" />
            </c:when>
            <c:when test="${requestScope.type eq 'groups'}">
                Groups
            </c:when>
            <c:when test="${requestScope.type eq 'resources'}">
                <fmt:message key="openiam.ui.selfservice.user.access.review.resources" />
            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>
    </title>
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/css/selfservice/access.review.css"  rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/treeGrid/tree.grid.css" rel="stylesheet" type="text/css" />
	<openiam:overrideCSS />
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery.cookies.2.2.0.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/menutree.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/resource.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/group.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/search/role.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/treeGrid/tree.grid.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/selfservice/user/access.review.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>



    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.RiskList = <c:choose><c:when test="${! empty requestScope.riskList}">${requestScope.riskList}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.UserId = "${requestScope.user.id}";
        OPENIAM.ENV.UserName = "${requestScope.user.displayName}"
        OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.user.id}";
        OPENIAM.ENV.EntitlementType = "${requestScope.type}";
        OPENIAM.ENV.RECERTIFICATION_TASK_ID=<c:choose><c:when test="${! empty requestScope.taskId}">${requestScope.taskId}</c:when><c:otherwise>null</c:otherwise></c:choose>;

        OPENIAM.ENV.AccessReviewTabs=<c:choose><c:when test="${! empty requestScope.accessReviewTabs}">${requestScope.accessReviewTabs}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.AccessReviewButtons=<c:choose><c:when test="${! empty requestScope.accessReviewButtons}">${requestScope.accessReviewButtons}</c:when><c:otherwise>null</c:otherwise></c:choose>;
    </script>
</head>
<body>
<div id="title" class="title">
    <c:choose>
        <c:when test="${! empty requestScope.taskId}">
            <fmt:message key="openiam.ui.selfservice.user.access.recertification" />
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${requestScope.type eq 'roles'}">
                    <fmt:message key="openiam.ui.selfservice.user.access.review.roles" />
                </c:when>
                <%--<c:when test="${requestScope.type eq 'groups'}">--%>
                <%--Groups--%>
                <%--</c:when>--%>
                <c:when test="${requestScope.type eq 'resources'}">
                    <fmt:message key="openiam.ui.selfservice.user.access.review.resources" />
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    &nbsp;<fmt:message key="openiam.ui.selfservice.user.access.review.view.for.user" />: ${requestScope.user.displayName}
</div>

<div id="subTitle" class="subTitle">
    ${requestScope.userInfo}
</div>

<c:if test="${ empty requestScope.taskId}">
    <div id="usermenu">
        <%--<li>--%>
            <%--<a <c:if test="${requestScope.type eq 'resources'}">class="active"</c:if> href="accessReview.html?id=${requestScope.user.id}&type=resources"><fmt:message key="openiam.ui.selfservice.user.access.review.resources" /></a>--%>
        <%--</li>--%>
            <%--<li>--%>
            <%--<a <c:if test="${requestScope.type eq 'groups'}">class="active"</c:if> href="accessReview.html?id=${requestScope.user.id}&type=groups">Groups</a>--%>
            <%--</li>--%>
        <%--<li>--%>
            <%--<a <c:if test="${requestScope.type eq 'roles'}">class="active"</c:if> href="accessReview.html?id=${requestScope.user.id}&type=roles"><fmt:message key="openiam.ui.selfservice.user.access.review.roles" /></a>--%>
        <%--</li>--%>
    </div>
</c:if>


<div class="clear"></div>
<div class="frameContentDivider">
    <div id="entitlementsContainer"></div>
    <div id="exceptionsTitle" class="subTitle" style="display:none"><fmt:message key="openiam.ui.selfservice.user.access.review.exceptions" />:</div>
    <div id="exceptionsContainer" style="display:none"></div>

    <c:if test="${! empty taskId}">
        <div>
            <ul class="formControls">
                <li class="leftBtn">
                    <a id="certifyBtn" class="redBtn" href="javascript:void(0);"><fmt:message key="openiam.ui.selfservice.user.access.review.certify" /></a>
                </li>
                <li class="leftBtn">
                    <a id="dontCertifyBtn" class="whiteBtn" href="javascript:void(0);"><fmt:message key="openiam.ui.selfservice.user.access.review.dontCertify" /></a>
                </li>
            </ul>
        </div>
    </c:if>

    <div id="fixedIdentityInformationContainer">
        <div id="identityInformation" style="display:none">
            <div class="identityInfoTitle">
                <div class="title"><fmt:message key="openiam.ui.selfservice.user.access.review.identity.information" /></div>
                <span class="openiam-close-icon"></span>
                <div style="clear: both;"></div>
            </div>
            <div id="identityMetadata">
                <div>
                    <label><fmt:message key="openiam.ui.common.principal" />:</label>
                    <span class="name"></span>
                </div>
                <div>
                    <label><fmt:message key="openiam.ui.shared.managed.system" />:</label>
                    <span class="mngsys"></span>
                </div>
                <div>
                    <label><fmt:message key="openiam.ui.common.status" />:</label>
                    <span class="status"></span>
                </div>
                <div>
                    <label><fmt:message key="openiam.ui.user.identities.last.login" />:</label>
                    <span class="lastLogin"></span>
                </div>
                <div>
                    <label><fmt:message key="openiam.ui.user.identities.password.expired" />:</label>
                    <span class="pwdExp"></span>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog"></div>

<div id="editDialog"></div>
<%--<div id="roleDialog"></div>--%>
<%--<div id="groupDialog"></div>--%>

<div id="searchResultsContainer" style="display:none"></div>
<%--<div id="resSearchResultsContainer" style="display:none"></div>--%>
<%--<div id="roleSearchResultsContainer" style="display:none"></div>--%>

</body>
</html>