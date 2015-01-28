<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <title>${titleOrganizatioName} - IDM</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 20px;">

<div id="title" class="title">
    <fmt:message key="openiam.ui.idm.prov.synclog.header"/>
</div>

<table cellpadding="5" cellspacing="1" border="1">
    <thead>
    <tr style="color: #fff; background-color: #555;">
        <th><fmt:message key="openiam.ui.idm.prov.table.col.dateTime"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.object.type"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.user.id"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.principal"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.system"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.action"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.result"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.reason"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.explanation"/></th>
        <th><fmt:message key="openiam.ui.idm.prov.table.col.request.id"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="it" items="${eventList}">
        <tr>
            <td><fmt:formatDate pattern="${requestScope.dateFormatJSTLFull}" value="${it.actionDatetime}" /></td>
            <td>${it.getObjectTypeId()}</td>
            <td>${it.getObjectId()}</td>
            <td>${it.getUserId()}</td>
            <td>${it.getTargetSystemId()}</td>
            <td>${it.getActionId()}</td>
            <td>${it.getActionStatus()}</td>
            <td>${it.getReason()}</td>
            <td>${it.getReasonDetail()}</td>
            <td>${it.getRequestId()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>