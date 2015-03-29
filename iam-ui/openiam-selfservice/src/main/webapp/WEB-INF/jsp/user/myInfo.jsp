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
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.user.my.info"/></title>
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
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = null;
    </script>
</head>
<body>
<div id="title" class="title">
    <fmt:message key="openiam.ui.common.welcome"/> ${requestScope.user.displayName}

</div>
<div class="frameContentDivider">
    <div>
        <c:if test="${! empty requestScope.profilePicture}">
            <p>
                <img src='<c:url context="${pageContext.request.contextPath}" value="/rest/api/images/${requestScope.profilePicture}" />'/>
            </p>
        </c:if>
        <p>
            <span>
            <c:if test="${! empty user.defaultPhone}">
                <c:if
                        test="${! empty  user.defaultPhone.typeDescription}">${ user.defaultPhone.typeDescription}</c:if>:&nbsp;
                <c:if test="${! empty  user.defaultPhone.countryCd}">${ user.defaultPhone.countryCd} </c:if>
                <c:if test="${! empty  user.defaultPhone.areaCd}">(${ user.defaultPhone.areaCd}) </c:if>
                ${ user.defaultPhone.phoneNbr},&nbsp;
            </c:if>
            <c:if test="${! empty user.defaultAddress}">
                <c:if test="${! empty user.defaultAddress.typeDescription}">${user.defaultAddress.typeDescription}</c:if>:
                <c:if test="${! empty user.defaultAddress.postalCd}">${user.defaultAddress.postalCd}</c:if>
                <c:if test="${! empty user.defaultAddress.country}">${user.defaultAddress.country}</c:if>
                <c:if test="${! empty user.defaultAddress.state}">${user.defaultAddress.state}</c:if>
                <c:if test="${! empty user.defaultAddress.city}">${user.defaultAddress.city}</c:if>
                <c:if test="${! empty user.defaultAddress.address1}">${user.defaultAddress.address1}</c:if>
                <c:if test="${! empty user.defaultAddress.address2}">${user.defaultAddress.address2}</c:if>
                <c:if test="${! empty user.defaultAddress.bldgNumber}">${user.defaultAddress.bldgNumber}</c:if>
            </c:if>
            </span>
        </p>
        <c:choose>
            <c:when test="${! empty requestScope.supervisorList and fn:length(requestScope.supervisorList) > 0}">
                <span><fmt:message key="openiam.ui.user.supervisor"/>:
                   <c:forEach var="supervisor" items="${requestScope.supervisorList}">
                       ${supervisor.displayName}&nbsp;
                   </c:forEach>
                </span>

            </c:when>
            <c:otherwise>
                <p><fmt:message key="openiam.ui.user.supervisor"/>: <fmt:message key="openiam.ui.common.na"/></p>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${! empty requestScope.roles and fn:length(requestScope.roles) > 0}">
                <p><fmt:message key="openiam.ui.user.entitlement.group.title"/>:</p>
                <ul>
                    <c:forEach var="group" items="${requestScope.groups}">
                        <li>${group.name}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p><fmt:message key="openiam.ui.user.entitlement.group.title"/>: <fmt:message
                        key="openiam.ui.common.na"/></p>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${! empty requestScope.roles and fn:length(requestScope.roles) > 0}">
                <p><fmt:message key="openiam.ui.user.entitlement.role.title"/>:</p>
                <ul>
                    <c:forEach var="role" items="${requestScope.roles}">
                        <li>${role.name}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p><fmt:message key="openiam.ui.user.entitlement.role.title"/>: <fmt:message
                        key="openiam.ui.common.na"/></p>
            </c:otherwise>
        </c:choose>


        <p><fmt:message key="openiam.ui.user.my.info.password.expiration"/>: <c:choose><c:when
                test="${! empty requestScope.login}"><fmt:formatDate value="${requestScope.login.pwdExp}"
                                                                     pattern="yyyy-MM-dd HH:mm:ss"/>
        </c:when><c:otherwise>NA</c:otherwise> </c:choose></p>

        <p><fmt:message key="openiam.ui.user.identities.last.login"/>: <c:choose><c:when
                test="${! empty requestScope.login}"><fmt:formatDate value="${requestScope.login.lastAuthAttempt}"
                                                                     pattern="yyyy-MM-dd HH:mm:ss"/>
        </c:when><c:otherwise>NA</c:otherwise> </c:choose></p>
        <c:if test="${not empty requestScope.itPolicyStatus}">
            <p><fmt:message key="openiam.ui.webconsole.it.policy"/>: <c:choose><c:when
                    test="${requestScope.itPolicyStatus}"><fmt:message key="openiam.ui.user.accepted"/> <fmt:formatDate
                    value="${requestScope.user.dateITPolicyApproved}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </c:when><c:otherwise><a href="/selfservice/usePolicy.html" class="blue"><fmt:message
                    key="openiam.ui.user.my.info.review.accept.policy"/></a></c:otherwise> </c:choose></p>
        </c:if>
    </div>
</div>
</body>
</html>