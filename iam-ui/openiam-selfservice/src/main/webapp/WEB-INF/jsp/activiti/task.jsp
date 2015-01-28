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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.selfservice.task" />: ${taskWrapper.name}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/selfservice/activiti/task.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery.cookies.2.2.0.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/selfservice/activiti/task.decision.js"></script>
		
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = null;
			OPENIAM.ENV.TaskId = "${taskWrapper.id}"

		</script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.ui.selfservice.task" />: ${taskWrapper.name}
		</div>
		<div class="frameContentDivider">
			<table cellpadding="8px" class="activiti-requestinfo">
				<thead>
					<tr>
						<td colspan="2">
							<label><fmt:message key="openiam.ui.selfservice.task.request.information" /></label>
						</td>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty taskWrapper.requestMetadataMap}">
						<c:forEach var="mapEntry" items="${taskWrapper.requestMetadataMap}">
							<tr>
								<td>
									<label>${mapEntry.key}</label>
								</td>
								<td>
									<span>${mapEntry.value}</span>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<table cellpadding="8px">
				<thead>
					
				</thead>
				<tbody>
					<c:if test="${! empty taskWrapper.description}">
						<tr>
							<td><fmt:message key="openiam.ui.common.description" />:</td>
							<td>${taskWrapper.description}</td>
						</tr>
					</c:if>
					<tr>
						<td>
                            <fmt:message key="openiam.ui.selfservice.task.reason.your.decision" />
						</td>
						<td>
							<textarea id="comment" name="comment" class="full rounded"></textarea>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2">
							<ul class="formControls">
								<li>
									<a href="javascript:void(0)">
										<input id="accept" type="submit" class="redBtn" value="<fmt:message key='openiam.ui.selfservice.task.accept.request' />" />
									</a>
								</li>
								<li>
									<a href="javascript:void(0)">
										<input id="reject" type="submit" class="whiteBtn" value="<fmt:message key='openiam.ui.selfservice.task.reject.request' />" />
									</a>
								</li>
							</ul>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</body>
</html>