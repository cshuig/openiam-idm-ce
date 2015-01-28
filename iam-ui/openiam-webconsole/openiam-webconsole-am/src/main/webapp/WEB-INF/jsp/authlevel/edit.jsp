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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.am.authlevel.edit.role" /></title>
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
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/authlevel/edit.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.grouping.id}">"id=${requestScope.grouping.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Grouping = ${requestScope.groupingAsJSON};
			OPENIAM.ENV.GroupingId = "${requestScope.grouping.id}";
			OPENIAM.ENV.Attributes = ${requestScope.attributes};
		</script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${! empty requestScope.grouping.id}">
                    <fmt:message key="openiam.ui.webconsole.am.authlevel.grouping" />: ${requestScope.grouping.name}
				</c:when>
				<c:otherwise>
                    <fmt:message key="openiam.ui.webconsole.am.authlevel.grouping.create" />
				</c:otherwise>
			</c:choose>
		</div>
		<div class="frameContentDivider">
			<table cellpadding="8px" align="center" class="fieldset">
				<tbody>
					<tr>
						<td>
							<label class="required"><fmt:message key="openiam.ui.webconsole.am.authlevel.authenticationlevel.name" /></label>
						</td>
						<td>
							<input type="text" id="name" name="name" class="full rounded" />
						</td>
					</tr>
					<tr>
						<td>
							<label class="required"><fmt:message key="openiam.ui.webconsole.am.authlevel.authenticationlevel" /></label>
						</td>
						<td>
							<select id="authLevel" name="authLevel" class="rounded">
								<option value=""><fmt:message key="openiam.ui.webconsole.am.authlevel.authenticationlevel.select" /></option>
								<c:forEach var="level" items="${requestScope.authLevels}">
									<option value="${level.id}">${level.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</tbody>

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
									<a href="authLevelGroupings.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel" /></a>
								</li>
								<c:if test="${! empty requestScope.grouping.id}">
									<li class="rightBtn">
										<a id="delete" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete" /></a>
									</li>
								</c:if>
							</ul>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<c:if test="${! empty requestScope.grouping.id}">
			<div>
				<div class="title">
                    <fmt:message key="openiam.ui.common.attributes" />
				</div>
				<div class="frameContentDivider">
					<div id="attributesContainer"></div>
				</div>
			</div>
		</c:if>
		<div id="editDialog"></div>
	</body>
</html>