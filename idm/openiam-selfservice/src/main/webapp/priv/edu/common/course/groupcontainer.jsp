<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>




<h4>Group Container</h4>

<form:form commandName="groupCmd"  cssClass="user-info" >
    <fieldset>
        <div class="block">
            <div class="wrap alt">
                <div class="col-1">

                    <div class="row">
                        <label for="t-1">Group Container</label>

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
            <th>Course Name </th>
            <th>Description</th>
        </tr>

                <tr>
                    <td>Intro to Calculus</td>
                    <td></td>
                    <td></td>
                </tr>
        </tbody>
    </table>






</form:form>



