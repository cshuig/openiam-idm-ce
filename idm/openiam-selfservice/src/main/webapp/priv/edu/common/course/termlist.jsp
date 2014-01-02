<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>




<h4>Term Management</h4>

<form:form commandName="termCmd"  cssClass="user-info" >

    <table class="resource alt">
        <tbody>
        <tr class="caption">
            <th>Term Name </th>
            <th>Description</th>
            <th></th>
        </tr>

                <tr>
                    <td><input type="text" value="September 2013" SIZE="30"></td>
                    <td><input type="text" value="" SIZE="50">

                    </td>
                    <td><a href="">REMOVE</a></td>
                </tr>
        <tr>
            <td><input type="text" value="January 2014" SIZE="30"></td>
            <td><input type="text" value="" SIZE="50">

            </td>
            <td><a href="">REMOVE</a></td>
        </tr>


        <tr>
            <td><input type="text" value="** ADD TERM ***" SIZE="30"></td>
            <td><td><input type="text" value="" SIZE="50">
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



