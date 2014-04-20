<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<h4>Course Detail </h4>

<form:form commandName="courseDetailCmd" cssClass="user-info">
    <form:hidden path="course.id"  />
    <form:hidden path="course.districtId"  />
    <form:hidden path="course.schoolId"  />

    <fieldset>


        <div class="block">
            <div class="wrap alt">
                <div class="col-1">

                    <div class="row">
                        <label for="t-1">District:</label>
                        ${courseDetailCmd.course.districtName}
                    </div>
                    <div class="row">
                        <label for="t-1">School</label>
                            ${courseDetailCmd.course.schoolName}
                    </div>

                    <div class="row">
                        <label for="t-1">Course Name</label>
                        <form:input path="course.name" maxlength="30"  />
                    </div>
                    <div class="row">
                        <label for="t-1">Course #</label>
                        <form:input path="course.courseNumber" maxlength="30"  />
                    </div>

                    <div class="row">
                        <label for="t-1">Select Term</label>
                        <form:select path="termId">
                            <form:option value="" label="-Please Select-"/>
                            <form:options items="${courseDetailCmd.termList}" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </div>

                    <div class="row">
                        <label for="t-1">Section</label>
                        <form:input path="section" maxlength="30"  />

                    </div>
                    <div class="row">
                        <label for="t-1">Program Membership:</label>
                        <form:select path="course.programMembership" multiple="true" cssClass="multiselect">
                            <form:option value="" label="-Please Select-"/>
                            <form:options items="${courseDetailCmd.programList}" itemValue="id" itemLabel="name"/>

                        </form:select>
                    </div>

                    <div class="row">
                        <label for="t-1">Course Group:</label>
                        <select multiple class="multiselect">
                            <option>Group A</option>
                            <option>Group B</option>
                        </select>
                    </div>


                    <div class="row">
                        <label for="t-1">Status</label>
                        <select>
                            <option>ACTIVE</option>
                        </select>
                    </div>


                </div>


                <div class="col-1">

                </div>
            </div>
        </div>
        </div>




        <div class="button">
            <input type="submit" name="btn" value="Submit" />
        </div>
        <div class="button">
            <input type="submit" name="btn" value="Delete" />
        </div>

        <div class="button">
            <input type="submit" name="btn" value="Synch" />
        </div>

        <div class="button">
            <input type="submit" name="_cancel" value="Cancel" />
        </div>


    </fieldset>



</form:form>

<h4>Staff</h4>
<table class="resource alt">
    <tbody>
    <tr class="caption">

        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Status</th>
        <th>Role</th>
        <th></th>
    </tr>



    <tr>

        <td>Issac Newton</td>
        <td>inewton</td>
        <td>inewton@holland.edu</td>
        <td>(123)456-1234</td>
        <td>Active</td>
        <td>
            <select multiple class="multiselect">
                <option>BUILDING ADMIN</option>
                <option>COURSE ADMIN</option>
                <option>ORG ADMIN</option>
                <option>REPORT ONLY</option>
                <option>TEACHER</option>
            </select>
        </td>
        <td>Remove</td>
    </tr>

    <tr>

        <td>Some Assistant</td>
        <td>s_assistant</td>
        <td>s_assistant@holland.edu</td>
        <td>(123)456-1235</td>
        <td>Active</td>
        <td>
            <select multiple class="multiselect">
                <option>BUILDING ADMIN</option>
                <option>COURSE ADMIN</option>
                <option>ORG ADMIN</option>
                <option>REPORT ONLY</option>
                <option>TEACHER</option>
            </select>
        </td>
        <td>Remove</td>
    </tr>

    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="2">Add Instructor</td>

    </tr>

    </tbody>
</table>


<h4>Students</h4>
<table class="resource alt">
    <tbody>
    <tr class="caption">

        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Status</th>
        <th>Start Date</th>
        <th></th>
    </tr>



    <tr>

        <td>Adam Smith</td>
        <td>asmith</td>
        <td>asmith@xyz.com</td>
        <td>(123)456-1238</td>
        <td>Active</td>
        <td></td>
        <td>Remove</td>
    </tr>

    <tr>

        <td>Adam2 Smith2</td>
        <td>asmith2</td>
        <td>asmith2@xyz.com</td>
        <td>(123)456-1237</td>
        <td>Active</td>
        <td></td>
        <td>Remove</td>
    </tr>

    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>Add Student</td>

    </tr>

    </tbody>
</table>