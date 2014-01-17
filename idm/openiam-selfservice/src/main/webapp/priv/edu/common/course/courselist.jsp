<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>




<h4>Course Management - Select Courses</h4>

<form:form commandName="courseSelCmd"  cssClass="user-info" >

    <fieldset>
        <div class="block">
            <div class="wrap alt">
                <div class="col-1">
                    <div class="row">
                        <label for="t-1">Name</label>
                        <form:input path="search.name" size="40" maxlength="40"  />
                    </div>

                    <div class="row">
                        <label for="t-1">District</label>

                        <form:select path="search.districtId">
                            <form:option value="" label="-Please Select-"/>
                            <form:options items="${district}" itemValue="orgId" itemLabel="organizationName"/>
                        </form:select>
                    </div>

                    <div class="row">
                        <label for="t-1">School</label>

                        <form:select path="search.schoolId">
                            <form:option value="" label="-Please Select-"/>
                            <form:options items="${school}" itemValue="orgId" itemLabel="organizationName"/>
                        </form:select>
                    </div>

                    <div class="row">
                        <label for="t-1">Teacher</label>

                        <form:select path="search.instructorId">
                            <form:option value="" label="-Please Select-"/>
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

