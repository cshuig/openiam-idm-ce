<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<div id="title" class="title">
	${requestScope.pageTitle}
</div>
<div id="basicInfoElements">
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_FIRST_NAME']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_FIRST_NAME'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_FIRST_NAME'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_FIRST_NAME'].displayOrder}">
			<label for="firstName" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_FIRST_NAME'].name}
			</label>
			<input  id="firstName" type="text" value="${requestScope.user.firstName}" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_LAST_NAME']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_LAST_NAME'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_LAST_NAME'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_LAST_NAME'].displayOrder}">
			<label for="lastName" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_LAST_NAME'].name}
			</label>
			<input  id="lastName" type="text" value="${requestScope.user.lastName}" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_MIDDLE_INIT']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_MIDDLE_INIT'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_MIDDLE_INIT'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_MIDDLE_INIT'].displayOrder}">
			<label for="middleInit" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_MIDDLE_INIT'].name}
			</label>
			<input  id="middleInit" type="text" value="${requestScope.user.middleInit}" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_MAIDEN_NAME']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_MAIDEN_NAME'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_MAIDEN_NAME'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_MAIDEN_NAME'].displayOrder}">
			<label for="maidenName" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_MAIDEN_NAME'].name}
			</label>
			<input  id="maidenName" value="${requestScope.user.maidenName}" type="text" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_NICKNAME']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_NICKNAME'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_NICKNAME'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_NICKNAME'].displayOrder}">
			<label for="nickname" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_NICKNAME'].name}
			</label>
			<input  id="nickname" value="${requestScope.user.nickname}" type="text" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_DOB']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_DOB'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_DOB'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_DOB'].displayOrder}">
			<label for="dateOfBirth" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_DOB'].name}
			</label><br/>
			<input  id="dateOfBirth" value="${requestScope.userBean.birthdateAsStr}" type="text" class="full rounded date" readonly="readonly" />
		</div>
	</c:if>
	
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_START_DATE']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_START_DATE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_START_DATE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_START_DATE'].displayOrder}">
			<label for="startDate" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_START_DATE'].name}
			</label><br/>
			<input  id="startDate" value="${requestScope.userBean.startDateAsStr}" type="text" class="full rounded date" readonly="readonly" />
		</div>
	</c:if>
	
	
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_END_DATE']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_END_DATE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_END_DATE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_END_DATE'].displayOrder}">
			<label for="lastDate" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_END_DATE'].name}
			</label><br/>
			<input  id="lastDate" value="${requestScope.userBean.endDateAsStr}" type="text" class="full rounded date" readonly="readonly" />
		</div>
	</c:if>
	
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_TITLE']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_TITLE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_TITLE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_TITLE'].displayOrder}">
			<label for="functionalTitle" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_TITLE'].name}
			</label>
			<input  id="functionalTitle" value="${requestScope.user.title}" type="text" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_GENDER']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_GENDER'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_GENDER'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_GENDER'].displayOrder}">
			<label for="gender" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_GENDER'].name}
			</label>
			<select id="gender" class="select rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> >
				<option value=""><fmt:message key="openiam.ui.common.value.pleaseselect" /></option>
				<option value="M" <c:if test="${requestScope.user.sex eq 'M'}">selected="selected"</c:if> ><fmt:message key="openiam.ui.selfservice.user.create.gender.male" /></option>
				<option value="F" <c:if test="${requestScope.user.sex eq 'F'}">selected="selected"</c:if> ><fmt:message key="openiam.ui.selfservice.user.create.gender.female" /></option>
			</select>
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_EMPLOYEE_ID']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_EMPLOYEE_ID'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_EMPLOYEE_ID'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_EMPLOYEE_ID'].displayOrder}">
			<label for="employeeId" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_EMPLOYEE_ID'].name}
			</label>
			<input id="employeeId" value="${requestScope.user.employeeId}" type="text" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_EMPLOYEE_TYPE']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_EMPLOYEE_TYPE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_EMPLOYEE_TYPE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_EMPLOYEE_TYPE'].displayOrder}">
			<label for="employeeType" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_EMPLOYEE_TYPE'].name}
			</label>
			<select id="employeeType" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> >
				<option value="">- <fmt:message key="openiam.ui.common.value.pleaseselect" /> -</option>
				<c:forEach var="empType" items="${requestScope.employeeTypeList}">
					<option value="${empType.id}" <c:if test="${user.employeeTypeId eq empType.id}">selected="selected"</c:if> >${empType.name}</option>
				</c:forEach>
			</select>
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_LOCATION_CODE']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_LOCATION_CODE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_LOCATION_CODE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_LOCATION_CODE'].displayOrder}">
			<label for="locationCode" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_LOCATION_CODE'].name}
			</label>
			<input  id="locationCode" value="${requestScope.user.locationCd}" type="text" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_JOB_CODE']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_JOB_CODE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_JOB_CODE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_JOB_CODE'].displayOrder}">
			<label for="jobCode" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_JOB_CODE'].name}
			</label>
			<select id="jobCode" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> >
				<option value="">- <fmt:message key="openiam.ui.common.value.pleaseselect" /> -</option>
					<c:forEach var="job" items="${jobList}">
						<option value="${job.id}" <c:if test="${user.jobCodeId eq job.id}">selected="selected"</c:if> >${job.name}</option>
					</c:forEach>
				</select>
		</div>
	</c:if>
	
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_LOCATION_NAME']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_LOCATION_NAME'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_LOCATION_NAME'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_LOCATION_NAME'].displayOrder}">
			<label for="locationName" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_LOCATION_NAME'].name}
			</label>
			<input  id="locationName" value="${requestScope.user.locationName}" type="text" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_SELECT_SUPERVISOR'] and requestScope.newUser eq true and ! empty requestScope.currentUserId}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_SELECT_SUPERVISOR'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_SELECT_SUPERVISOR'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_SELECT_SUPERVISOR'].displayOrder}">
			<div class="clear">
				<label for="supervisor" <c:if test="${required eq true}">class="required"</c:if> >
					${requestScope.pageTemplate.uiFields['USER_SELECT_SUPERVISOR'].name}
				</label>
				<div style="position:relative">
					<input id="supervisor" type="text" readonly="readonly" class="full rounded readonly" />
					<div id="supervisorClear" class="clear-input" style="display:none"></div>
				</div>
				<input id="supervisorHidden" readonly="readonly" type="hidden" />
			</div>
			<c:if test="${readOnly eq false}">
				<div class="clear">
					<a id="supervisorSelect" href="javascript:void(0);"><fmt:message key="openiam.ui.user.supervisor.select" /></a>
				</div>
			</c:if>
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_CLASSICIATION']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_CLASSICIATION'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_CLASSICIATION'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_CLASSICIATION'].displayOrder}">
			<label for="classification" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_CLASSICIATION'].name}
			</label>
			<input id="classification" value="${requestScope.user.classification}" type="text" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_PREFIX']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_PREFIX'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_PREFIX'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_PREFIX'].displayOrder}">
			<label for="prefix" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_PREFIX'].name}
			</label>
			<input id="prefix" type="text" value="${requestScope.user.prefix}" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_STATUS']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_STATUS'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_STATUS'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_STATUS'].displayOrder}">
			<label for="status" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_STATUS'].name}
			</label>
			<select id="status" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> >
				<option value=""><fmt:message key="openiam.ui.user.select.user.status" /></option>
				<c:forEach var="status" items="${requestScope.userStatuses}">
					<option value="${status.value}" <c:if test="${requestScope.user.status eq status.value}">selected="selected"</c:if> >${status.value}</option>
				</c:forEach>
			</select>
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_SECONDARY_STATUS']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_SECONDARY_STATUS'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_SECONDARY_STATUS'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_SECONDARY_STATUS'].displayOrder}">
			<label for="secondaryStatus" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_SECONDARY_STATUS'].name}
			</label>
			<select id="secondaryStatus" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> >
				<option value=""><fmt:message key="openiam.ui.user.select.user.status" /></option>
				<c:forEach var="status" items="${requestScope.userStatuses}">
					<option value="${status.value}" <c:if test="${requestScope.user.secondaryStatus eq status.value}">selected="selected"</c:if> >${status.value}</option>
				</c:forEach>
			</select>
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_SUFFIX']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_SUFFIX'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_SUFFIX'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_SUFFIX'].displayOrder}">
			<label for="suffix" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_SUFFIX'].name}
			</label>
			<input id="suffix" type="text" value="${requestScope.user.suffix}" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_ALTERNATE_CONTACT'] and ! empty requestScope.currentUserId}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_ALTERNATE_CONTACT'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_ALTERNATE_CONTACT'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_ALTERNATE_CONTACT'].displayOrder}">
			<div class="clear">
				<label for="alternateContact" <c:if test="${required eq true}">class="required"</c:if> >
					${requestScope.pageTemplate.uiFields['USER_ALTERNATE_CONTACT'].name}
				</label>
				<div style="position:relative">
					<input id="alternateContact" readonly="readonly" type="text" class="full rounded readonly" value="${requestScope.alternateContact.displayName}" />
					<div id="alternateContactClear" class="clear-input" style="display:none"></div>
				</div>
				<input id="alternateContactHidden" readonly="readonly" type="hidden" value="${requestScope.alternateContact.id}" />
			</div>
			<c:if test="${readOnly eq false}">
				<div class="clear">
					<a id="alternateContactSelect" href="javascript:void(0);"><fmt:message key="openiam.ui.user.alternate.contact.select" /></a>
				</div>
			</c:if>
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_MAILCODE']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_MAILCODE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_MAILCODE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_MAILCODE'].displayOrder}">
			<label for="mailCode" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_MAILCODE'].name}
			</label>
			<input id="mailCode" type="text" value="${requestScope.user.mailCode}" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_COST_CENTER']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_COST_CENTER'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_COST_CENTER'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_COST_CENTER'].displayOrder}">
			<label for="costCenter" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_COST_CENTER'].name}
			</label>
			<input id="costCenter" type="text" value="${requestScope.user.costCenter}" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_USER_TYPE']}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_USER_TYPE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_USER_TYPE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_USER_TYPE'].displayOrder}">
			<label for="userTypeInd" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_USER_TYPE'].name}
			</label>
			<input id="userTypeInd" type="text" class="full rounded" value="${requestScope.user.userTypeInd}" class="full rounded <c:if test="${readOnly eq true}">disabled</c:if>" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> />
		</div>
	</c:if>
	
	<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_OBJECT_TYPE'] and ! empty requestScope.objClassList}">
		<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_OBJECT_TYPE'].required eq true}" />
		<c:set var="readOnly" value="${requestScope.pageTemplate.uiFields['USER_OBJECT_TYPE'].editable eq false}" />
		<div displayOrder="${requestScope.pageTemplate.uiFields['USER_OBJECT_TYPE'].displayOrder}">
			<label for="metadataUserType" <c:if test="${required eq true}">class="required"</c:if> >
				${requestScope.pageTemplate.uiFields['USER_OBJECT_TYPE'].name}
			</label>
			<select id="metadataUserType" name="metadataUserType" <c:if test="${readOnly eq true}">disabled="disabled"</c:if> >
				<option value="">- <fmt:message key="openiam.ui.common.value.pleaseselect" /> -</option>
				<c:forEach var="objClass" items="${requestScope.objClassList}">
						<option value="${objClass.id}"  <c:if test="${requestScope.user.mdTypeId eq objClass.id}">selected="selected"</c:if>>${objClass.name}</option>
				</c:forEach>
			</select>
		</div>
	</c:if>
</div>

<div class="frameContentDivider">
	<form id="editProfileForm" method="post" action="${requestScope.target}">
		<table cellpadding="8px" id="basicInfoTable" class="profileTable">
			<thead>
				<tr>
					<th colspan="3">
						<label>
							<fmt:message key="openiam.ui.selfservice.ui.template.basic.information" />
						</label>
					</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
	
		<table id="uiTemplateTable" class="profileTable" cellpadding="8px">
			<thead>
				<tr>
					<th colspan="3">
						<label>
							<fmt:message key='openiam.ui.selfservice.ui.template.additional.information' />
						</label>
					</th>
				</tr>
			</thead>
			<tbody id="uiTemplate">
					
			</tbody>
		</table>
	
		<c:if test="${requestScope.newUser eq true}">
			<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_SHOW_ROLES'] or ! empty requestScope.pageTemplate.uiFields['USER_SHOW_GROUPS']}">
				<table class="profileTable" cellpadding="8px">
					<thead>
						<tr>
							<th>
								<label>
									<fmt:message key='openiam.ui.selfservice.ui.template.membership' />
								</label>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_SHOW_ROLES']}">
							<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_SHOW_ROLES'].required eq true}" />
							<c:set var="additionalClasses"></c:set>
							<c:if test="${required eq true}">
								<c:set var="additionalClasses">required</c:set>
							</c:if>
							<tr>
								<td>
									<div>
										<a id="role" href="javascript:void(0);" class="entity-link role ui-search-enabled ${additionalClasses}"></a>
									</div>
								</td>
							</tr>
						</c:if>
						<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_SHOW_GROUPS']}">
							<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_SHOW_GROUPS'].required eq true}" />
							<c:set var="additionalClasses"></c:set>
							<c:if test="${required eq true}">
								<c:set var="additionalClasses">required</c:set>
							</c:if>
							<tr>
								<td>
									<div>
										<a id="group" href="javascript:void(0);" class="entity-link group ui-search-enabled ${additionalClasses}"></a>
									</div>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</c:if>
		</c:if>
		
		<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_CREATE_LOGIN'] and requestScope.newUser eq true}">
			<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_CREATE_LOGIN'].required eq true}" />
			<table class="profileTable" cellpadding="8px">
				<thead>
					<tr>
						<th>
							<label>
								<fmt:message key="openiam.ui.selfservice.ui.template.login.information" />
							</label>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<label <c:if test="${required eq true}">class="required"</c:if> >
								${requestScope.pageTemplate.uiFields['USER_CREATE_LOGIN'].name}
							</label>
							<input  id="login" type="text" class="full rounded" />
						</td>
					</tr>
				</tbody>
			</table>
		</c:if>
		
		
		<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_SHOW_ORGANIZATIONS'] and requestScope.newUser eq true}">
			<c:set var="required" value="${requestScope.pageTemplate.uiFields['USER_SHOW_ORGANIZATIONS'].required eq true}" />
			<div id="organizationsTable">
				<label <c:if test="${required eq true}">class="required"</c:if> >
					<fmt:message key="openiam.ui.selfservice.ui.template.organization.structure" />
				</label>
			</div>
		</c:if>
		
		<div class="contactInformation">
			<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_EMAIL_CREATABLE'] or ! empty requestScope.pageTemplate.uiFields['USER_ADDRESSES_CREATABLE'] or ! empty requestScope.pageTemplate.uiFields['USER_PHONES_CREATABLE']}">
				<label class="profileTableLabel">
					<fmt:message key="openiam.ui.selfservice.ui.template.contact.information" />
				</label>
				<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_EMAIL_CREATABLE']}">
					<div id="emailAddresses"></div>
				</c:if>
				<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_ADDRESSES_CREATABLE']}">
					<div id="addresses"></div>
					<div id="searchLocationContainer"></div>
				</c:if>
				<c:if test="${! empty requestScope.pageTemplate.uiFields['USER_PHONES_CREATABLE']}">
					<div id="phoneNumbers"></div>
				</c:if>
			</c:if>
			<div>
                   <ul class="formControls">
                       <li>
                           <a href="javascript:void(0)">
                               <input id="submitForm" type="submit" class="redBtn" value="<fmt:message key='openiam.ui.button.save'/>" />
                           </a>
                       </li>
                   </ul>
               </div>
		</div>
	</form>
</div>