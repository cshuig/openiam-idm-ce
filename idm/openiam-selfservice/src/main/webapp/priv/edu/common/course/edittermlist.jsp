<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>




<h4>Edit Terms for District: </h4>

<form:form commandName="editTermCmd"  cssClass="user-info" >

    <table class="resource alt">
    <tbody>
    <tr class="caption">
        <th></th>
        <th>Term Name </th>
        <th>School Year</th>
    </tr>

    <c:if test="${editTermCmd.termList != null}" >

        <c:forEach items="${editTermCmd.termList}" var="termObj" varStatus="term">

                <tr>
                    <td><form:checkbox  path="termList[${term.index}].selected" /></td>
                    <td>
                        <form:hidden path="termList[${term.index}].id"/>
                        <form:hidden path="termList[${term.index}].districtId"/>
                        <form:input path="termList[${term.index}].name" size="20" maxlength="60"/>
                     </td>
                    <td><form:input path="termList[${term.index}].schoolYear" size="20" maxlength="30"/></td>
                </tr>
        </c:forEach>


        </tbody>

     </c:if>

    </table>



    <fieldset>
        <div class="button">
            <input type="submit" name="btn" value="Delete" />
        </div>
        <div class="button">
            <input type="submit" name="btn" value="Submit" />
        </div>

        <div class="button">
            <input type="reset" />
        </div>
    </fieldset>




</form:form>



