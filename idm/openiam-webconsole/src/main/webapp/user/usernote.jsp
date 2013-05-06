<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<table width="900pt">
    <tr>
        <td>
            <table width="100%">
                <tr>
                    <td class="pageTitle" width="70%">
                        <h2 class="contentheading">User Management - Comments</h2>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <% String msg = (String) request.getAttribute("msg");
        if (msg != null) { %>
    <tr>
        <td class="msg" colspan="5" align="center">
            <b><%=msg %>
            </b>
        </td>
    </tr>
    <% } %>
    <tr>
        <td>
            <form:form commandName="userNoteCmd">
                <form:hidden path="perId"/>
                <table width="900pt">
                    <tr>
                        <td align="center" height="100%">
                            <fieldset class="userform">
                                <legend>EDIT COMMENTS</legend>

                                <table class="resourceTable" cellspacing="2" cellpadding="2" width="100%">
                                    <tr class="header">
                                        <th></th>
                                        <th>DATE</th>
                                        <th>COMMENT</th>
                                    </tr>
                                    <c:forEach items="${userNoteCmd.noteList}" var="userNote" varStatus="note">

                                        <tr>
                                            <td class="tableEntry"><form:checkbox
                                                    path="noteList[${note.index}].selected"/></td>
                                            <td class="tableEntry"> ${userNoteCmd.noteList[note.index].createDate}</td>

                                            <td class="tableEntry">
                                                <form:hidden path="noteList[${note.index}].userNoteId"/>
                                                <form:hidden path="noteList[${note.index}].userId"/>
                                                <form:hidden path="noteList[${note.index}].noteType"/>
                                                <form:hidden path="noteList[${note.index}].createDate"/>
                                                <form:hidden path="noteList[${note.index}].createdBy"/>
                                                <form:textarea path="noteList[${note.index}].description" cols="60" rows="4" />

                                            </td>

                                        </tr>

                                    </c:forEach>
                                </table>
                                </fieldset>
                        </td>
                    </tr>
                    <tr>
                        <td class="buttonRow" align="right">
                            <input type="submit" name="saveBtn" value="Delete"/>
                            <input type="submit" name="saveBtn" value="Save"/> <input type="submit" name="_cancel" value="Cancel"/>
                        </td>
                    </tr>

                </table>
            </form:form>
        </td>
    </tr>
</table>

