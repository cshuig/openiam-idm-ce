<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>




<h4>Term Management</h4>

<form:form commandName="termCmd"  cssClass="user-info" >
    <fieldset>
        <div class="block">
            <div class="wrap alt">
                <div class="col-1">

                    <div class="row">
                        <label for="t-1">District</label>

                        <form:select path="districtId">
                            <form:option value="" label="-Please Select-"/>
                            <form:options items="${district}" itemValue="orgId" itemLabel="organizationName"/>
                        </form:select>
                    </div>
                    <div class="button">
                        <input type="submit" name="btn" value="Search" />
                    </div>

                </div>
                <div class="col-1">


                </div>
            </div>
        </div>
    </fieldset>


    <c:if test="${termCmd.termList != null}" >
    <table class="resource alt">
        <tbody>
        <tr class="caption">
            <th>Term Name </th>
            <th>School Year</th>
            <th></th>
        </tr>

        <c:forEach items="${termCmd.termList}" var="termObj" varStatus="term">

                <tr>
                    <td><form:hidden path="termList[${term.index}].id"/>
                        <form:hidden path="termList[${term.index}].districtId"/>
                        <form:input path="termList[${term.index}].name" size="20" maxlength="60"/>
                     </td>
                    <td><form:input path="termList[${term.index}].schoolYear" size="20" maxlength="30"/></td>
                    <td><a href="">REMOVE</a></td>
                </tr>
        </c:forEach>


        </tbody>
    </table>

    </c:if>

    <fieldset>
        <div class="button">
            <input type="submit" name="btn" value="Submit" />
        </div>

        <div class="button">
            <input type="reset" />
        </div>
    </fieldset>




</form:form>



