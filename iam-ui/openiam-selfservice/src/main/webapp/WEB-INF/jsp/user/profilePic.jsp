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
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.user.my.info" /></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        div#profilePicContent::after { display:block; content:""; clear:both; }
        div#profilePic, div#profilePicLinks, div#profilePicForm { float: left; }
        div#profilePicLinks, div#profilePicForm { margin-top: 10px; margin-left: 10px; }
    </style>
    <openiam:overrideCSS />
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/selfservice/user/profile.pic.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = null;
        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
        OPENIAM.ENV.UserId = <c:choose><c:when test="${not empty requestScope.user.id}">"${requestScope.user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ProfilePicSrc = <c:choose><c:when test="${not empty requestScope.profilePicSrc}">"${requestScope.profilePicSrc}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
    </script>
</head>
<body>
<div id="title" class="title">
    <fmt:message key="openiam.ui.selfservice.ui.template.edit.profile.pic" />
</div>
<div class="frameContentDivider">
    <div id="profilePicContent">
    <c:if test="${not empty requestScope.profilePicSrc}">
        <div id="profilePic">
        <img src='<c:url context="${pageContext.request.contextPath}" value="/rest/api/images/${requestScope.profilePicSrc}" />' />
        </div>
        <div id="profilePicLinks">
        <a id="deleteProfilePic" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.common.delete"/></a>
        <a id="changeProfilePic" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.common.change"/></a>
        </div>
    </c:if>
    <div id="profilePicForm" <c:if test="${not empty requestScope.profilePicSrc}">style='display:none;'</c:if> >
        <input type="file" id="uploadProfilePic" accept="image/gif, image/jpeg, image/png" class="full rounded" />
        <br style="clear:both;"/>
    </div>
    </div>
</div>
<div id="dialog"></div>
</body>
</html>