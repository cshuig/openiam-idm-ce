<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<h4>Course Detail </h4>

<form:form commandName="courseDetailCmd" cssClass="user-info">
    <fieldset>


        <div class="block">
            <div class="wrap alt">
                <div class="col-1">

                    <div class="row">
                        <label for="t-1">District</label>
                        Holland
                    </div>
                    <div class="row">
                        <label for="t-1">School</label>
                        Holland
                    </div>

                    <div class="row">
                        <label for="t-1">Course Name</label>
                        <input type=text value="Intro to Calculus">
                    </div>
                    <div class="row">
                        <label for="t-1">Course #</label>
                        <input type=text value="101">
                    </div>

                    <div class="row">
                        <label for="t-1">Select Term</label>
                        <select>
                            <option>Please Select</option>
                        </select>
                    </div>

                    <div class="row">
                        <label for="t-1">Section</label>
                        <select>
                            <option>Please Select</option>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                        </select>
                    </div>
                    <div class="row">
                        <label for="t-1">Program Membership:</label>
                        <select>
                            <option>Please Select</option>
                            <option>Program 1</option>
                            <option>Program 2</option>
                            <option>Program 3</option>
                        </select>
                    </div>

                    <div class="row">
                        <label for="t-1">Course Group:</label>
                        <select>
                            <option>Please Select</option>
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
        <th>Start Date</th>
        <th></th>
    </tr>



    <tr>

        <td>Issac Newton</td>
        <td>inewton</td>
        <td>inewton@holland.edu</td>
        <td>(123)456-1234</td>
        <td>Active</td>
        <td>
            <select>
                <option>Please Select</option>
                <option>Role A</option>
                <option>Role B</option>
            </select>
        </td>
        <td></td>
        <td>Remove</td>
    </tr>

    <tr>

        <td>Some Assistant</td>
        <td>s_assistant</td>
        <td>s_assistant@holland.edu</td>
        <td>(123)456-1235</td>
        <td>Active</td>
        <td>
            <select>
                <option>Please Select</option>
                <option>Role A</option>
                <option>Role B</option>
            </select>
        </td>
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