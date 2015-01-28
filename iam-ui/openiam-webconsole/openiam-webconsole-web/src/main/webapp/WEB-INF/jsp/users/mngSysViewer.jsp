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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="mngSysViewerListCommand" type="org.openiam.ui.webconsole.web.model.MngSysViewerListCommand"--%>

<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.user.edit.page.title"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/uiTemplate/ui.template.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/user/mngSysViewer.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.UserId = <c:choose><c:when test="${! empty user.id}">"${user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty user.id}">"id=${user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.PrincipalId = "${principalId}";
        OPENIAM.ENV.IDMAttrs = ${requestScope.idmAttrsMapAsJSON};
        OPENIAM.ENV.MngSysAttrs = ${requestScope.mngSysAttrsMapAsJSON};
        OPENIAM.ENV.MngSysUserExists = ${requestScope.mngSysUserExist};
        OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
    </script>

</head>
<body>
<div class="title">
    <c:if test="${! empty user and !empty user.id}">
        ${user.firstName} ${user.lastName} - ${managedSys.name}
    </c:if>
</div>
<div class="frameContentDivider">
    <table cellspacing="1" id="tableOne" class="yui" width="100%">
        <thead>
        <tr>
            <th><fmt:message key="openiam.ui.user.managed.system.viewer.header.attributeName" /></th>
            <th><fmt:message key="openiam.ui.user.managed.system.viewer.header.mngSysValue" /></th>
            <th><fmt:message key="openiam.ui.user.managed.system.viewer.header.idmValue" /></th>
            <th><fmt:message key="openiam.ui.common.actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty viewerList}">
                <c:forEach varStatus="status" var="attr" items="${viewerList}">
                    <tr class="${(status.index%2==0)?'even':'odd'}">
                        <td>${attr.attributeName}</td>
                        <td <c:if test="${attr.readOnly}">style="color: #777;"</c:if> >${attr.mngSysAttribute}</td>
                        <td class="IDMAttr" <c:if test="${attr.changed}">style="font-weight: bold"</c:if> >${attr.idmAttribute}</td>
                        <td>
                            <c:if test="${not attr.readOnly}">
                                <a href="javascript:void(0);" class="editBtn" entityId="${attr.attributeName}">
                                    <img src="/openiam-ui-static/images/common/edit.png"/>
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr class="empty">
                    <td colspan="4" class="empty">
                        <spring:message code="openiam.ui.idm.synch.table.noresults"/>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    <table align="center" cellpadding="8px" width="100%">
    <tfoot>
    <tr>
        <td>
            <ul class="formControls">
                <li class="leftBtn">
                    <input id="saveBtn" type="submit" class="redBtn" value='<fmt:message key="openiam.ui.button.save"/>' />
                </li>
                <li class="rightBtn">
                    <a href="/webconsole/mngSysViewer.html?id=${user.id}&pid=${principalId}" class="redBtn"><spring:message code="openiam.ui.common.refresh" /></a>
                </li>
                <li class="rightBtn">
                    <a href="/webconsole/mngSysViewerList.html?id=${user.id}" class="whiteBtn"><spring:message code="openiam.ui.common.cancel" /></a>
                </li>
            </ul>
        </td>
    </tr>
    </tfoot>
    </table>
</div>
<div id="editDialog"></div>
</body>
</html>