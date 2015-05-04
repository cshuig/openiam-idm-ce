<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.user.edit.page.title"/></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole/user.edit.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
        <openiam:overrideCSS />

        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

        <script type="text/javascript" src="/openiam-ui-static/js/common/menutree.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/user/user.edit.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.form.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/orghierarchy/organization.hierarchy.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/role.search.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/group.search.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
            OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.initialMenu = <c:choose><c:when test="${! empty requestScope.initialMenu}">${requestScope.initialMenu}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.UserId = <c:choose><c:when test="${! empty user.id}">"${user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty user.id}">"id=${user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.OrganizationHierarchy = ${requestScope.orgHierarchy};
            OPENIAM.ENV.ProfilePicSrc = <c:choose><c:when test="${not empty profilePicSrc}">"${profilePicSrc}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
            OPENIAM.ENV.CurrentOrgId=<c:choose><c:when test="${! empty requestScope.currentOrgId}">"${requestScope.currentOrgId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;

		</script>
	</head>
	<body>
		<div class="title">
			<c:choose>
				<c:when test="${! empty user and !empty user.id}">
					<fmt:message key="openiam.ui.user.edit.title"/>: ${user.firstName} ${user.lastName}
				</c:when>
				<c:otherwise>
                    <fmt:message key="openiam.ui.user.new.title"/>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="frameContentDivider">
			<form id="editUserForm" method="post" enctype="multipart/form-data">
                <c:if test="${! empty user and empty user.id}">
                    <div class="title">
                        <fmt:message key="openiam.ui.user.credentials"/>:
                    </div>

                    <table cellpadding="8px" <c:if test="${!provisionFlag}">align="center"</c:if>>
                        <tr>
                            <td>
                                <label for="login" <c:if test="${!provisionFlag}">class="required"</c:if>><fmt:message key="openiam.ui.user.login"/>:</label><c:if test="${provisionFlag}"><br /></c:if>
                                <input id="login" type="text" class="full rounded" value="${user.login}" autocomplete="off" />
                            </td>
                            <c:choose>
                                <c:when test="${!provisionFlag}">
                                    <td>
                                        <label for="password" class="required"><fmt:message key="openiam.ui.user.password"/>:</label>
                                        <input id="password" type="password" class="full rounded" value="${user.password}" autocomplete="off" />
                                    </td>
                                    <td>
                                        <label for="confirmPassword" class="required"><fmt:message key="openiam.ui.user.password.confirm"/>:</label>
                                        <input id="confirmPassword" type="password" class="full rounded" value="${user.confirmPassword}" autocomplete="off" />
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </table>
                </c:if>
				<div class="title">
                    <fmt:message key="openiam.ui.user.information"/>:
				</div>
				<table cellpadding="8px" align="center" style="width:100%">
					<tbody>
                        <c:if test="${! empty user and ! empty user.id}">
                            <tr>
                                <td>
                                    <label for="userId"><fmt:message key="openiam.ui.user.id"/>:</label><br/>
                                    <input id="userId" type="text" disabled="disabled" class="full rounded" value="${user.id}">
                                </td>
                                <td>
                                    <label for="status"><fmt:message key="openiam.ui.user.status"/>:</label><br/>
                                    <input id="status" type="text" disabled="disabled" class="full rounded" value="${user.status}">
                                </td>
                                <td>
                                    <label for="secondaryStatus"><fmt:message key="openiam.ui.user.account.status"/>:</label><br/>
                                    <input id="secondaryStatus" type="text" disabled="disabled" class="full rounded" value="${user.secondaryStatus}">
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${empty user or empty user.id or empty user.metadataTypeId}">
                                        <label for="metadataTypeId" class="required"><fmt:message key="openiam.ui.user.object.class"/>:</label><br/>
                                        <select id="metadataTypeId" name="metadataTypeId">
                                            <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                            <c:forEach var="objClass" items="${objClassList}">
                                                <option value="${objClass.id}"  <c:if test="${! empty user and user.metadataTypeId eq objClass.id}">selected="selected"</c:if>>${objClass.name}</option>
                                            </c:forEach>
                                        </select>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" id="metadataTypeId" value="${user.metadataTypeId}"/>
                                        <%--<select id="metadataTypeId" name="metadataTypeId">--%>
                                            <%--<option value=""><fmt:message key="openiam.ui.common.please.select"/></option>--%>
                                            <%--<c:forEach var="objClass" items="${objClassList}">--%>
                                                <%--<option value="${objClass.id}"  <c:if test="${! empty user and user.metadataTypeId eq objClass.id}">selected="selected"</c:if>>${objClass.name}</option>--%>
                                            <%--</c:forEach>--%>
                                        <%--</select>--%>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                        </tr>
                        <%--<c:if test="${empty user or empty user.id or empty user.metadataTypeId}">--%>
                        	<%--<tr>--%>
                                <%--<td>--%>
                                    <%--<label for="metadataTypeId" class="required"><fmt:message key="openiam.ui.user.object.class"/>:</label><br/>--%>
                                    <%--<select id="metadataTypeId" name="metadataTypeId">--%>
                                        <%--<option value=""><fmt:message key="openiam.ui.common.please.select"/></option>--%>
                                        <%--<c:forEach var="objClass" items="${objClassList}">--%>
                                            <%--<option value="${objClass.id}"  <c:if test="${! empty user and user.metadataTypeId eq objClass.id}">selected="selected"</c:if>>${objClass.name}</option>--%>
                                        <%--</c:forEach>--%>
                                    <%--</select>--%>
                                <%--</td>--%>
                                <%--<td>--%>
                                <%--</td>--%>
                                <%--<td>--%>
                                <%--</td>--%>
                            <%--</tr>--%>
                        <%--</c:if>--%>
						<tr>
                            <td>
                                <label for="firstName" class="required"><fmt:message key="openiam.ui.user.firstname"/>:</label><br/>
                                <input id="firstName" type="text"  class="full rounded" value="${user.firstName}">
                            </td>
                            <td>
                                <label for="middleInit"><fmt:message key="openiam.ui.user.middle"/>:</label><br/>
                                <input id="middleInit" type="text" class="full rounded" value="${user.middleInit}">
                            </td>
                            <td>
                                <label for="lastName" class="required"><fmt:message key="openiam.ui.user.lastname"/>:</label><br/>
                                <input id="lastName" type="text" class="full rounded" value="${user.lastName}">
                            </td>
						</tr>
                        <tr>
                            <td>
                                <label for="nickname"><fmt:message key="openiam.ui.user.nickname"/>:</label><br/>
                                <input id="nickname" type="text"  class="full rounded" value="${user.nickname}">
                            </td>
                            <td>
                                <label for="maidenName"><fmt:message key="openiam.ui.user.maiden"/>:</label><br/>
                                <input id="maidenName" type="text" class="full rounded" value="${user.maidenName}">
                            </td>
                            <td>
                                <label for="suffix"><fmt:message key="openiam.ui.user.suffix"/>:</label><br/>
                                <input id="suffix" type="text" class="full rounded" value="${user.suffix}">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="birthdate"><fmt:message key="openiam.ui.user.birthday"/>:</label><br/>
                                <input id="birthdate" type="text" class="full rounded date" value="${user.birthdateAsStr}" readonly="readonly"/>
                            </td>
                            <td>
                                <label for="sex"><fmt:message key="openiam.ui.user.gender"/>:</label><br/>
                                <select id="sex" name="sex">
                                    <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                    <option value="M" <c:if test="${! empty user and user.sex eq 'M'}">selected="selected"</c:if> ><fmt:message key="openiam.ui.selfservice.user.create.gender.male" /></option>
                                    <option value="F" <c:if test="${! empty user and user.sex eq 'F'}">selected="selected"</c:if> ><fmt:message key="openiam.ui.selfservice.user.create.gender.female" /></option>
                                    <option value="D" <c:if test="${! empty user and user.sex eq 'D'}">selected="selected"</c:if> ><fmt:message key="openiam.ui.gender.decline.state" /></option>
                                </select>
                            </td>
                            <td>
                                <c:if test="${not empty user and not empty user.id}">
                                    <label for="uploadProfilePic"><fmt:message key="openiam.ui.user.profile.pic"/>:</label><br/>
                                    <c:if test="${not empty profilePicSrc}">
                                        <div id="profilePicLinks">
                                        <a id="showProfilePic" href="javascript:void(0);"><fmt:message key="openiam.ui.user.profile.pic.uploaded"/></a>
                                        <a id="deleteProfilePic" href="javascript:void(0);" class="blue"><fmt:message key="openiam.ui.common.delete"/></a>
                                        <a id="changeProfilePic" href="javascript:void(0);" class="blue"><fmt:message key="openiam.ui.common.change"/></a>
                                        </div>
                                    </c:if>
                                    <div id="profilePicForm" <c:if test="${not empty profilePicSrc}">style='display:none;'</c:if> >
                                    <input type="file" id="uploadProfilePic" accept="image/gif, image/jpeg, image/png" class="full rounded" />
                                    </div>

                                </c:if>
                            </td>
                            <%--<td>--%>
                                <%--<label for="showInSearch"><fmt:message key="openiam.ui.user.show.in.search"/>:</label><br/>--%>
                                <%--<select id="showInSearch" name="showInSearch">--%>
                                    <%--<option value="0" <c:if test="${! empty user and user.showInSearch eq 0}">selected="selected"</c:if> >0</option>--%>
                                    <%--<option value="1" <c:if test="${! empty user and user.showInSearch eq 1}">selected="selected"</c:if> >1</option>--%>
                                    <%--<option value="2" <c:if test="${! empty user and user.showInSearch eq 2}">selected="selected"</c:if> >2</option>--%>
                                    <%--<option value="3" <c:if test="${! empty user and user.showInSearch eq 3}">selected="selected"</c:if> >3</option>--%>
                                    <%--<option value="4" <c:if test="${! empty user and user.showInSearch eq 4}">selected="selected"</c:if> >4</option>--%>
                                    <%--<option value="5" <c:if test="${! empty user and user.showInSearch eq 5}">selected="selected"</c:if> >5</option>--%>
                                <%--</select>--%>
                            <%--</td>--%>
                        </tr>
					</tbody>
				</table>
                <c:if test="${! empty user and empty user.id}">
                    <div class="title">
                        <fmt:message key="openiam.ui.user.access.rule"/>:
                    </div>
                    <table cellpadding="8px">
                        <tbody>
                        <tr>
                            <td >
                               	<div>
                               		<a id="userRoleId" href="javascript:void(0);" class="entity-link role ui-search-enabled"></a>
                               	</div>
                            </td>
                            <td>
                                <div>
                                    <a id="userGroupId" href="javascript:void(0);" class="entity-link group ui-search-enabled"></a>
                                </div>
                            </td>
                            <td>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="title">
                        <fmt:message key="openiam.ui.user.contact.info"/>:
                    </div>
                    <div class="subTitle">
                        <fmt:message key="openiam.ui.common.email.address"/>:
                    </div>
                    <table cellpadding="8px"  align="center" style="width:100%">
                        <tbody>
                            <tr>
                                <td >
                                    <label for="emailTypeId"><fmt:message key="openiam.ui.common.email.address.type.full"/>:</label><br/>
                                    <select id="emailTypeId" name="emailTypeId">
                                        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                        <c:forEach var="emailType" items="${emailTypeList}">
                                            <option value="${emailType.id}">${emailType.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <label for="email"><fmt:message key="openiam.ui.common.email.address"/>:</label><br/>
                                    <input id="email" type="text" class="full rounded" autocomplete="off" />
                                </td>
                                <td style="width:30%">
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="subTitle">
                        <fmt:message key="openiam.ui.common.address"/>:
                    </div>
                    <table cellpadding="8px" align="center"  style="width:100%">
                        <tbody>
                            <tr>
                                <td>
                                    <label for="addressTypeId"><fmt:message key="openiam.ui.common.address.type"/>:</label><br/>
                                    <select id="addressTypeId" name="addressTypeId">
                                        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                        <c:forEach var="addressType" items="${addressTypeList}">
                                            <option value="${addressType.id}">${addressType.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <label for="building"><fmt:message key="openiam.ui.common.address.building"/>:</label><br/>
                                    <input id="building" type="text" class="full rounded" autocomplete="off" />
                                </td>
                                <td>
                                    <label for="address1" ><fmt:message key="openiam.ui.common.address.1"/>:</label><br/>
                                    <input id="address1" type="text" class="full rounded" autocomplete="off" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="address2" ><fmt:message key="openiam.ui.common.address.2"/>:</label><br/>
                                    <input id="address2" type="text" class="full rounded" autocomplete="off" />
                                </td>
                                <td>
                                    <label for="city" ><fmt:message key="openiam.ui.common.address.city"/>:</label><br/>
                                    <input id="city" type="text" class="full rounded" autocomplete="off" />
                                </td>
                                <td>
                                    <label for="state" ><fmt:message key="openiam.ui.common.address.state"/>:</label><br/>
                                    <input id="state" type="text" class="full rounded" autocomplete="off" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="postalCode"><fmt:message key="openiam.ui.common.address.postal.code"/>:</label><br/>
                                    <input id="postalCode" type="text" class="full rounded" autocomplete="off" />
                                </td>
                                <td>
                                </td>
                                <td>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="subTitle">
                        <fmt:message key="openiam.ui.common.phone"/>:
                    </div>
                    <table cellpadding="8px" align="center"  style="width:100%">
                        <tbody>
                            <tr>
                                <td>
                                    <label for="phoneTypeId"><fmt:message key="openiam.ui.common.phone.type"/>:</label><br/>
                                    <select id="phoneTypeId" name="phoneTypeId">
                                        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                        <c:forEach var="phoneType" items="${phoneTypeList}">
                                            <option value="${phoneType.id}">${phoneType.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                </td>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="areaCode" ><fmt:message key="openiam.ui.common.phone.area.code"/>:</label><br/>
                                    <input id="areaCode" type="text" class="full rounded" autocomplete="off" />
                                </td>
                                <td>
                                    <label for="phoneNumber" ><fmt:message key="openiam.ui.common.phone.number"/>:</label><br/>
                                    <input id="phoneNumber" type="text" class="full rounded" autocomplete="off" />
                                </td>
                                <td>
                                    <label for="extension" ><fmt:message key="openiam.ui.common.phone.extension"/>:</label><br/>
                                    <input id="extension" type="text" class="full rounded" autocomplete="off" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </c:if>
				<div class="title">
                    <fmt:message key="openiam.ui.user.org.info"/>:
				</div>
				<table cellpadding="8px" align="center" style="width:100%">
					<tbody>
                        <tr>
                            <td>
                                <label for="title"><fmt:message key="openiam.ui.user.functional.title"/>:</label><br/>
                                <input id="title" type="text"  class="full rounded" value="${user.title}">
                            </td>
                            <td>
                                <label for="jobCode"><fmt:message key="openiam.ui.user.job.code"/>:</label><br/>
                                <select id="jobCode" name="jobCode">
                                    <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                    <c:forEach var="job" items="${jobList}">
                                        <option value="${job.id}" <c:if test="${! empty user and user.jobCodeId eq job.id}">selected="selected"</c:if> >${job.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <label for="classification"><fmt:message key="openiam.ui.user.classification"/>:</label><br/>
                                <input id="classification" type="text" class="full rounded" value="${user.classification}">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="employeeId"><fmt:message key="openiam.ui.user.employee.id"/>:</label><br/>
                                <input id="employeeId" type="text"  class="full rounded" value="${user.employeeId}">
                            </td>
                            <td>
                                <label for="userTypeInd"><fmt:message key="openiam.ui.user.type"/>:</label><br/>
                                <input id="userTypeInd" type="text" class="full rounded" value="${user.userTypeInd}">
                            </td>
                            <td>
                                <label for="employeeType"><fmt:message key="openiam.ui.common.employee.type"/>:</label><br/>
                                <select id="employeeType" name="employeeType">
                                    <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                    <c:forEach var="empType" items="${employeeTypeList}">
                                        <option value="${empType.id}" <c:if test="${! empty user and user.employeeTypeId eq empType.id}">selected="selected"</c:if> >${empType.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="startDate"><fmt:message key="openiam.ui.user.start.date"/>:</label><br/>
                                <input id="startDate" type="text"  class="full rounded date" value="${user.startDateAsStr}" readonly="readonly"/>
                            </td>
                            <td>
                                <label for="lastDate"><fmt:message key="openiam.ui.user.end.date"/>:</label><br/>
                                <input id="lastDate" type="text" class="full rounded date" value="${user.lastDateAsStr}" readonly="readonly"/>
                            </td>
                            <td>
                                <c:if test="${! empty user and !empty user.id}">
                                    <label for="mailCode" ><fmt:message key="openiam.ui.user.mailcode"/>:</label><br/>
                                    <input id="mailCode" type="text" class="full rounded" value="${user.mailCode}" />
                                </c:if>
                            </td>
                        </tr>
                        <c:if test="${! empty user and !empty user.id}">
                            <tr>
                                <td>
                                    <label for="costCenter"><fmt:message key="openiam.ui.user.costcenter"/>:</label><br/>
                                    <input id="costCenter" type="text"  class="full rounded" value="${user.costCenter}"/>
                                </td>
                                <td>
                                    <label for="claimDate"><fmt:message key="openiam.ui.user.claim.date"/>:</label><br/>
                                    <input id="claimDate" type="text" class="full rounded" value="${user.claimDateAsStr}" readonly="readonly"/>
                                </td>
                                <td>
                                </td>
                            </tr>
                        </c:if>
					</tbody>
				</table>
				<div id="organizationsTable">
				
				</div>
				<div class="title">
                    <c:choose>
                        <c:when test="${empty user or empty user.id}">
                            <fmt:message key="openiam.ui.user.supervisor.and.alternate"/>:
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="openiam.ui.user.alternate"/>:
                        </c:otherwise>
                    </c:choose>
				</div>
				<table cellpadding="8px">
					<tbody>
                        <c:if test="${empty user or empty user.id}">
						<tr>
                            <td  align="left">
                                <label for="supervisorName"><fmt:message key="openiam.ui.user.supervisor"/>:</label><br/>
                                <input id="supervisorName" type="text"  class="full rounded" value="${user.supervisorName}" readonly="readonly">
                                <input id="supervisorId" type="hidden" value="" name="supervisorId">
                            </td>
                            <td align="left">
                                <a id="selectSupervisor" href="javascript:void(0);"><fmt:message key="openiam.ui.user.supervisor.select"/></a>
                            </td>
                            <td align="left">
                            </td>
						</tr>
                        </c:if>
                        <tr>
                            <td  align="left">
                                <label for="altName"><fmt:message key="openiam.ui.user.alternate.contact"/>:</label><br/>
                                <input id="altName" type="text"  class="full rounded" value="${user.alternateContactName}" readonly="readonly">
                                <input id="alternateContactId" type="hidden" value="" name="alternateContactId">
                            </td>
                            <td align="left">
                                <a id="selectalternateContact" href="javascript:void(0);"><fmt:message key="openiam.ui.user.alternate.contact.select"/></a>
                            </td>
                            <td align="left">
                            </td>
                        </tr>
					</tbody>
				</table>
                <c:if test="${! empty user and empty user.id}">
                    <div class="title">
                        <fmt:message key="openiam.ui.user.notifications"/>:
                    </div>
                    <table cellpadding="8px">
                        <tr>
                            <td>
                                <input id="notifyUserViaEmail" type="checkbox" checked="checked" value="true" name="notifyUserViaEmail">
                                <i><fmt:message key="openiam.ui.user.notifications.email"/></i>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                        </tr>
                        <c:if test="${provisionFlag==true}">
                            <tr>
                                <td>
                                    <input id="notifySupervisorViaEmail" type="checkbox" value="true" name="notifySupervisorViaEmail">
                                    <i><fmt:message key="openiam.ui.user.notifications.supervisor"/></i>
                                </td>
                                <td>
                                </td>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input id="provisionOnStartDate" type="checkbox" value="true" name="provisionOnStartDate">
                                    <i><fmt:message key="openiam.ui.user.provision.delay"/></i>
                                </td>
                                <td>
                                </td>
                                <td>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </c:if>
                <div>
                    <ul class="formControls" id="buttonsPanel">
                        <li class="leftBtn">
                            <a href="javascript:void(0)">
                                <input type="submit" class="redBtn" value='<fmt:message key="openiam.ui.button.save"/>' />
                            </a>
                        </li>
                        <li class="leftBtn">
                            <a href="users.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel"/></a>
                        </li>
                    </ul>
                </div>
			</form>
		</div>

        <div id="userResultsArea"></div>
        <div id="dialog"></div>
        <div id="editDialog"></div>
	</body>
</html>