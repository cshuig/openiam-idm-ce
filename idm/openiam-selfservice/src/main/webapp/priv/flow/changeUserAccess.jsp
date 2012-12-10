<%@ page language="java" contentType="text/html; charset=utf-8"     pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 



			<div class="block">
				<div class="wrap">
					<form:form commandName="changeUserAccess">

						
						<fieldset>
							<label for="t-1">Operation:<span>*</span></label>
                            <form:select path="operation" multiple="false">
                                <form:option value="ADD" label="ADD"/>
                                <form:option value="REMOVE" label="REMOVE"/>
                            </form:select>

                            <label for="t-2">Role Membership:</label>
                            <form:select path="roleId" multiple="false">
                            <form:option value="" label="-Please Select-"/>
                            <c:forEach items="${roleList}" var="role">
                                <form:option value="${role.id.serviceId}*${role.id.roleId}" label="${role.id.serviceId}-${role.roleName}" />
                            </c:forEach>
                            </form:select>
                            <p><form:errors path="roleId"/></p>

							<label for="t-3">Resource</label>
                            <form:select path="resourceId" multiple="false">
                                <form:option value="" label="-Please Select-"/>
                                <form:options items="${resourceList}" itemValue="resourceId" itemLabel="name"/>
                            </form:select>

                            <label for="t-3">Group Membership: </label>
                            <form:select path="groupId" multiple="false">
                                <form:option value="" label="-Please Select-"/>
                                <form:options items="${groupList}" itemValue="grpId" itemLabel="grpName"/>
                            </form:select>


                            <div class="button">
                                <input type="submit" value="Save" name="btnSave">
                            </div>

                            <div class="button">
								<input type="submit" name="_cancel" value="Cancel" />
							</div>

						</fieldset>
					</form:form> 
				</div>
			</div>