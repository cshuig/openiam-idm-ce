<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>




<h4>Program Management</h4>

<form:form commandName="programCmd"  cssClass="user-info" >

    <table class="resource alt">
        <tbody>
        <tr class="caption">
            <th></th>
            <th>Program Name </th>
            <th>Status</th>
            <th></th>
        </tr>


        <c:if test="${programCmd.programList != null}" >

        <c:forEach items="${programCmd.programList}" var="prgObj" varStatus="program">

            <tr>
                <td><form:checkbox  path="programList[${program.index}].selected" /></td>
                <td>
                    <form:hidden path="programList[${program.index}].id"/>
                    <form:input path="programList[${program.index}].name" size="30" maxlength="60"/>
                </td>
                <td><form:select path="programList[${program.index}].status">
                        <form:option value="ACTIVE" label="ACTIVE"/>
                        <form:option value="DISABLED" label="DISABLED"/>
                    </form:select>
                </td>
            </tr>
        </c:forEach>


        </tbody>

        </c:if>



        </tbody>
    </table>


    <fieldset>
        <div class="button">
            <input type="submit" name="btn" value="Delete" />
        </div>

        <div class="button">
            <input type="submit"  name="btn" value="Submit" />
        </div>

        <div class="button">
            <input type="reset" />
        </div>
    </fieldset>




</form:form>



