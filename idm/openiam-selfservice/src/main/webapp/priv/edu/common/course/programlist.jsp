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
            <th>Program Name </th>
            <th>Status</th>
            <th></th>
        </tr>

                <tr>
                    <td><input type="text" value="Program 1" SIZE="30"></td>
                    <td>
                        <select>
                            <option>ACTIVE</option>
                            <option>DISABLED</option>
                        </select>
                    </td>
                    <td><a href="">REMOVE</a></td>
                </tr>
        <tr>
            <td><input type="text" value="** ADD PROGRAM ***" SIZE="30"></td>
            <td>
                <select>
                    <option>ACTIVE</option>
                    <option>DISABLED</option>
                </select>
            </td>
            <td></td>
        </tr>


        </tbody>
    </table>


    <fieldset>
        <div class="button">
            <input type="submit" value="Submit" />
        </div>

        <div class="button">
            <input type="reset" />
        </div>
    </fieldset>




</form:form>



