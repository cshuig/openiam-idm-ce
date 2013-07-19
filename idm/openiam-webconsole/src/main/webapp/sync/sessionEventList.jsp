<%@ page import="org.openiam.idm.srvc.audit.dto.IdmAuditLog, org.openiam.webadmin.admin.JSPUtil" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<head>
    <title>OpenIAM Identity Manager 2.3.2 - Administration Console</title>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/screen.css">

</head>

<% List<IdmAuditLog> auditLogList = (List<IdmAuditLog>)session.getAttribute("eventList"); %>

<table  width="1800pt">
    <tr>
        <td>
            <table width="100%">
                <tr>
                    <td class="pageTitle" width="70%">
                        <h2 class="contentheading">Synchronization Transaction Log Viewer</h2>
                    </td>
                </tr>
            </table>
        </td>

</table>

<% if (auditLogList != null ) {  %>


<table width="1800pt" >
    <tr>
        <td align="center" height="100%">
            <fieldset class="userform" >
                <legend>Synchronization Session Details </legend>

                <table class="resourceTable" cellspacing="1" cellpadding="1" width="1800pt">



                    <tr>
                        <th>Row</th>
                        <th>Date/Time</th>
                        <th>Object Type</th>
                        <th>User GUID</th>
                        <th>Principal</th>
                        <th>System</th>
                        <th>Action</th>
                        <th>Result</th>
                        <th>Reason</th>
                        <th>Explanation</th>
                    </tr>

                    <ul>
                            <%   int ctr = 1;
		for (IdmAuditLog log : auditLogList) { %>


                        <tr>
                            <td class="tableEntry"><%=ctr%></td>
                            <td class="tableEntry">

                                <% if ( log.getObjectTypeId().equalsIgnoreCase("USER") && !log.getTargetSystemId().equals('0') ) { %>

                                        <% } %>
                                <fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="<%=log.getActionDatetime() %>" /></td>

                            <td class="tableEntry"><%=log.getObjectTypeId()%></td>
                            <td class="tableEntry">
                                <% if ( !log.getObjectTypeId().equalsIgnoreCase("SYNCH_USER")  ) { %>
                                <%=log.getObjectId() %>
                                <% } %>
                            </td>
                            <td class="tableEntry"><%=JSPUtil.display(log.getCustomAttrvalue3())%></td>
                            <td class="tableEntry"><%=JSPUtil.display(log.getTargetSystemId())%></td>
                            <td class="tableEntry"><%=JSPUtil.display(log.getActionId())%></td>
                            <td class="tableEntry"><%=JSPUtil.display(log.getActionStatus())%></td>
                            <td class="tableEntry"><%=JSPUtil.display(log.getReason())%></td>
                            <td class="tableEntry"><%=JSPUtil.display(log.getReasonDetail())%></td>
                        </tr>
                            <%	ctr++;
  	} %>


                </table>
                </ul>


</table>

<% } %>
