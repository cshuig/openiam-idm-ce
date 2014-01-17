<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>




<h4>Course Group</h4>

<form:form commandName="groupCmd"  cssClass="user-info" >
    <fieldset>
        <div class="block">
            <div class="wrap alt">
                <div class="col-1">

                    <div class="row">
                        <label for="t-1">District</label>

                        <select>
                            <option>Holland</option>
                            <option>Hamilton</option>
                        </select>

                    </div>

                    <div class="row">
                        <label for="t-1">Course Group</label>

                        <select>
                            <option>Group A</option>
                        </select>

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
            <input type="reset" />
        </div>
    </fieldset>



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




        <tr>
            <td><a href="courseDetail.selfserve?courseid=1&termid=1">Intro to Calculus</a></td>
            <td>101</td>
            <td>JANUARY 2014</td>
            <td>1</td>
            <td>HOLLAND</td>
            <td>Holland Elementary School</td>
        </tr>


        <tr>
            <td><a href="courseDetail.selfserve?courseid=1&termid=2">Intro to Calculus</a></td>
            <td>101</td>
            <td>JANUARY 2014</td>
            <td>2</td>
            <td>HOLLAND</td>
            <td>Holland Elementary School</td>
        </tr>





        </tbody>
    </table>







</form:form>



