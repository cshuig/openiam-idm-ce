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

<c:set var="errorMessage">
	<c:if test="${! empty requestScope.errorToken}">
		<spring:message code="${requestScope.errorToken.error.messageName}" arguments="${requestScope.errorToken.params}" />
	</c:if>
</c:set>

<c:set var="successMessage">
	<c:if test="${! empty requestScope.successToken}">
		<spring:message code="${requestScope.successToken.message.messageName}" />
	</c:if>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.attributes.edit.title" /></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
        <openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/authlevel/attibute.edit.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
            OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.grouping.id}">"id=${requestScope.grouping.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.GroupingId = "${requestScope.grouping.id}";
			OPENIAM.ENV.AttributeId = "${requestScope.attribute.id}";
			OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.SuccessMessage = <c:choose><c:when test="${! empty successMessage}">"${successMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Location = "editAuthLevelGroupingAttibute.html?groupingId=${requestScope.grouping.id}&id=${requestScope.attribute.id}";
			OPENIAM.ENV.TypeMap = ${requestScope.typeMap};
			OPENIAM.ENV.HasFile = ${requestScope.attribute.hasFile};
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.grouping.id}">"id=${requestScope.grouping.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${! empty requestScope.attribute.id}">
                    <fmt:message key="openiam.ui.role.edit.auth.level.attr" />: ${requestScope.attribute.name}
				</c:when>
				<c:otherwise>
                    <fmt:message key="openiam.ui.role.creat.auth.level.attr" />
				</c:otherwise>
			</c:choose>
		</div>
		<div class="frameContentDivider">
			<form method="POST" action="editAuthLevelGroupingAttibute.html" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id" value="${requestScope.attribute.id}" />
				<input type="hidden" id="groupingId" name="groupingId" value="${requestScope.grouping.id}" />
				<input type="hidden" id="modifiedFile" name="modifiedFile" value="false" />
				<table cellpadding="8px" align="center">
					<tbody>
						<tr>
							<td>
								<label class="required">Name</label>
							</td>
							<td>
								<input type="text" id="name" name="name" value="${requestScope.attribute.name}" class="full rounded" />
							</td>
						</tr>
						<tr>
							<td>
								<label class="required"><fmt:message key="openiam.ui.idm.prov.table.col.type" /></label>
							</td>
							<td>
								<select id="typeId" name="typeId" class="full rounded">
									<option value=""><fmt:message key="openiam.ui.common.select.type" /></option>
									<c:forEach var="type" items="${requestScope.types}">
										<option value="${type.id}" <c:if test="${requestScope.attribute.typeId eq type.id}">selected="selected"</c:if>>${type.description}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr style="display:none">
							<td>
								<label><fmt:message key="openiam.ui.common.string.value" /></label>
							</td>
							<td>
								<input type="text" id="valueAsString" name="valueAsString" value="${requestScope.attribute.valueAsString}" class="full rounded" />
							</td>
						</tr>
						<tr style="display:none">
							<td>
								<label><fmt:message key="openiam.ui.common.byte.value" /></label>
							</td>
							<td>
								<a id="newFile" href="javascript:void(0);"><fmt:message key="openiam.ui.role.attr.bytearray" /></a>
								<input type="file" 
									autocomplete="off" 
									id="bytes" 
									name="bytes" 
									value="<fmt:message key='openiam.ui.common.select.file' />"
									class="full rounded"  />	
							</td>
						</tr>
					</tbody>
				</table>
				<table width="100%">
					<tfoot>
						<tr>
							<td colspan="2">
								<ul class="formControls">
									<li>
										<a id="save" href="javascript:void(0)">
											<input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.button.save' />" />
										</a>
									</li>
									<li>
										<a href="editAuthLevelGrouping.html?id=${requestScope.grouping.id}" class="whiteBtn"><fmt:message key='openiam.ui.button.cancel' /></a>
									</li>
									<c:if test="${! empty requestScope.attribute.id}">
										<li class="rightBtn">
											<a id="delete" href="javascript:void(0);" class="redBtn"><fmt:message key='openiam.ui.button.delete' /></a>
										</li>
									</c:if>
								</ul>
							</td>
						</tr>
					</tfoot>
				</table>
			</form>
		</div>
		<div id="editDialog"></div>
	</body>
</html>