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
		<title>${titleOrganizatioName} - Edit Resource Attributes</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />

		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/resource/resource.prop.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>
		
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ResourceId = "${requestScope.resource.id}";
			OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.resource.id}";
		</script>
	</head>
	<body>
		<div id="title" class="title">
			Attributes for Resource: ${requestScope.resource.name}
		</div>
		<div class="frameContentDivider">
			<table cellspacing="1" id="propertyTable" class="yui" width="100%">
				<thead>
					<tr>
						<th><fmt:message key="contentprovider.provider.meta.data.type.prop.name" /></th>
						<th><fmt:message key="contentprovider.provider.meta.data.type.prop.val" /></th>
						<th><fmt:message key="openiam.ui.common.actions" /></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty requestScope.otherProperties or fn:length(requestScope.otherProperties) eq 0}">
							<tr>
								<td colspan="3" class="empty">
                                    <fmt:message key="openiam.ui.attr.resource.not.assoc" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="property" items="${requestScope.otherProperties}">
								<tr>
									<td>${property.name}</td>
									<td>${property.value}</td>
									<td class="delete">
										<a href="javascript:void(0);" resourcePropertyId="${property.id}">
											<img src="/openiam-ui-static/images/common/delete.png"/>
										</a>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
					<tr>
						<td>
							<input id="propertyName" type="text" class="full rounded" placeholder="<fmt:message key='contentprovider.provider.meta.data.type.prop.name' />..." />
						</td>
						<td>
							<input id="propertyValue" type="text" class="full rounded" placeholder="<fmt:message key='contentprovider.provider.meta.data.type.prop.val' />..." />
						</td>
						<td>
							<input id="propertySave" type="button" value="<fmt:message key='openiam.ui.am.auth.provider.attribute.save' />" class="redBtn"/>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</body>
</html>