<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
    var changeRequestForm = function(obj,action) {
        var selectOption = $(obj).find('option:selected').val();
        if(selectOption != '-1') {
            jQuery('#actionType').val(action);
            jQuery('form#courseSelCmd').submit();
        }
        return false;
    }

</script>

<h4>Course Management - Select Courses</h4>

<form:form commandName="courseSelCmd"  cssClass="user-info" >
    <form:hidden id="actionType" path="submitType" />
    <fieldset>
        <div class="block">
            <div class="wrap alt">
                <div class="col-1">
                    <div class="row">
                        <label>Name</label>
                        <form:input path="search.name" size="40" maxlength="40"  />
                    </div>

                    <div class="row">
                        <label>District</label>

                        <form:select path="search.districtId" onchange="return changeRequestForm(this, 'SELECT_DISTRICT')">
                            <form:option value="-1" label="-Please Select-"/>
                            <form:options items="${courseSelCmd.districtList}" itemValue="orgId" itemLabel="organizationName"/>
                        </form:select>
                    </div>

                    <div class="row">
                        <label>School</label>

                        <form:select path="search.schoolId" onchange="return changeRequestForm(this, 'SELECT_SCHOOL')">
                            <form:option value="-1" label="-Please Select-"/>
                            <form:options items="${courseSelCmd.schoolList}" itemValue="orgId" itemLabel="organizationName"/>
                        </form:select>
                    </div>

                    <div class="row">
                        <label>Teacher</label>

                        <form:select path="search.instructorId">
                            <form:option value="" label="-Please Select-"/>
                            <c:forEach items="${courseSelCmd.teacherList}" var="teacher">
                                <form:option value="${teacher.userId}" label="${teacher.deptCd} -> ${teacher.firstName} ${teacher.lastName}" />
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="row">
                        <label>Term</label>
                        <form:select path="search.term">
                            <form:option value="" label="-Please Select-"/>

                        </form:select>
                    </div>
                    <div class="row">
                        <label>Program</label>
                        <form:select path="search.program">
                            <form:option value="" label="-Please Select-"/>
                            <form:options items="${courseSelCmd.programList}" itemValue="id" itemLabel="name"/>

                        </form:select>
                    </div>

                </div>
                <div class="col-1">
                </div>


            </div>
        </div>
    </fieldset>
    <fieldset>
        <div class="button">
            <input type="submit" value="Submit" />
        </div>
        <div class="button">
            <input type="submit" value="Add Course" />
        </div>

        <div class="button">
            <input type="reset" />
        </div>
    </fieldset>




</form:form>


<table class="resource alt">
    <tbody>
    <tr class="caption">
        <th>Name </th>
        <th>Course Number</th>
        <th>Term</th>
        <th>Section</th>
        <th>District</th>
        <th>School</th>

    </tr>

    <c:if test="${courseList != null}" >

        <c:forEach items="${courseList}" var="courseSearchResult">
            <tr>
                <td><a href="courseDetail.selfserve?courseid=${courseSearchResult.courseId}&termid=${courseSearchResult.courseTermId}">${courseSearchResult.name}</a></td>
                <td>${courseSearchResult.courseNumber}</td>
                <td>${courseSearchResult.termName}</td>
                <td>${courseSearchResult.sectionNbr}</td>
                <td>${courseSearchResult.districtName}</td>
                <td>${courseSearchResult.schoolName}</td>
            </tr>

        </c:forEach>
    </c:if>


    </tbody>
</table>

