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
                            <form:options items="${termCmd.districtList}" itemValue="orgId" itemLabel="organizationName"/>
                        </form:select>
                    </div>
                    <div class="button">
                        <input type="submit" name="btn" value="Next" />
                    </div>

                </div>
                <div class="col-1">


                </div>
            </div>
        </div>
    </fieldset>







</form:form>



