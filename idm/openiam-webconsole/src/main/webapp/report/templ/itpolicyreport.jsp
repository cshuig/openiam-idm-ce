<%@ page language="java"%>
<%@ page import="java.util.*,javax.servlet.http.*,org.openiam.idm.srvc.menu.dto.Menu, org.openiam.idm.srvc.auth.dto.Login ,org.openiam.idm.srvc.user.dto.User" %>
<%@ page import="org.openiam.idm.srvc.rpt.qryobject.dto.UserLoginStatusReport" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>




<%
List<UserLoginStatusReport> statusList = (List<UserLoginStatusReport>)request.getSession().getAttribute("result");
	
%>
<table width="100%">
	<tr>
		<td colspan="3"><b>IT Policy Status Report</b></td>
	</tr>

	<% if (statusList != null) { %>
	<tr>
		<td colspan="3">User Count:<%=statusList.size() %></td>
	</tr>
	<tr class="tdheader">
        <td>IT POLICY DATE</td>
        <td>POLICY RESPONSE</td>
        <td>FIRST NAME</td>
        <td>MIDDLE INITIAL</td>
        <td>LAST NAME</td>
        <td>NICKNAME</td>
		<td>DEPT</td>
		<td>TITLE</td>
		<td>EMAIL</td>
	</tr>
	<% }else { %>
	<tr>
		<td colspan="3">No Users Found.</td>
	</tr>
    <tr class="tdheader">
        <td>IT POLICY DATE</td>
        <td>POLICY RESPONSE</td>
        <td>FIRST NAME</td>
        <td>MIDDLE INITIAL</td>
        <td>LAST NAME</td>
        <td>NICKNAME</td>
        <td>DEPT</td>
        <td>TITLE</td>
        <td>EMAIL</td>
    </tr>

	<% } %>

	<%
		if (statusList != null) {
			for (UserLoginStatusReport user : statusList) {

	%>

	<tr class="plaintext">
        <td><%=user.getTimeStamp()%></td>
        <td><%=user.getResponse()%></td>
        <td><%=user.getFirstName() %></td>
        <td><%=user.getMiddleInit() %></td>
        <td><%=user.getLastName()%></td>
        <td><%=user.getNickName() %></td>
		<td><%=user.getDeptCd() %></td>
        <td><%=user.getTitle()%></td>
		<td><%=user.getEmailAddress()%></td>


	</tr>
	<%
			}
		}
	%>

</table>

