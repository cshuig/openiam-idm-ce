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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.am.auth.provider.attribute.page.title"/></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole-am/auth.prop.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/authprovider/authprovider.am.attributes.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>
		
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ProviderId = "${requestScope.provider.providerId}";
			OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.provider.providerId}";
		</script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.ui.am.auth.provider.attribute.title"/>: ${requestScope.provider.name}
		</div>
		<div class="frameContentDivider">
			<table cellspacing="1" id="propertyTable" class="yui" width="100%">
				<thead>
					<tr>
						<th><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.name"/></th>
						<th><fmt:message key="openiam.ui.am.auth.provider.attribute.data.type"/></th>
						<th><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.type"/></th>
						<th><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.value"/></th>
						<th><fmt:message key="openiam.ui.common.actions"/></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty requestScope.provider.resourceAttributeMap or fn:length(requestScope.provider.resourceAttributeMap) eq 0}">
							<tr>
								<td colspan="5" class="empty">
									<fmt:message key="openiam.ui.am.auth.provider.attribute.empty"/>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="attribute" items="${requestScope.provider.resourceAttributeMap}">
								<tr>
									<td>
										<span>${attribute.key}</span>
									</td>
									<td>
										<span>${attribute.value.attributeType.displayName}</span>
									</td>
									<c:choose>
										<c:when test="${! empty attribute.value.amResAttributeName}">
											<td>
												<span><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.user"/></span>
											</td>
											<td>
												<span>${attribute.value.amResAttributeName}</span>
											</td>
										</c:when>
										<c:when test="${! empty attribute.value.attributeValue}">
											<td>
												<span><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.static"/></span>
											</td>
											<td>
												<span>${attribute.value.attributeValue}</span>
											</td>
										</c:when>
										<c:when test="${! empty attribute.value.amPolicyUrl}">
											<td>
												<span><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.groovy"/></span>
											</td>
											<td>
												<span>${attribute.value.amPolicyUrl}</span>
											</td>
										</c:when>
									</c:choose>
									<td class="delete">
										<a href="javascript:void(0);" propertyId="${attribute.value.attributeMapId}">
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
							<input id="propertyName" type="text" class="full rounded" placeholder='<fmt:message key="openiam.ui.am.auth.provider.attribute.prop.name"/>...'  autocomplete="off" />
						</td>
						<td>
							<label for="dataType"><fmt:message key="openiam.ui.am.auth.provider.attribute.data.type"/>:</label>
							<select id="dataType" autocomplete="off">
								<c:forEach var="dataType" items="${requestScope.attributeTypeMap}">
									<option value="${dataType.key}" <c:if test="${dataType.value == 'Any'}">selected="selected"</c:if> >${dataType.value}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<label for="valueType"><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.type"/>:</label>
							<select id="valueType"  autocomplete="off">
								<option value="userValue"><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.user"/></option>
								<option value="staticValue"><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.static"/></option>
								<option value="groovyScript"><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.groovy"/></option>
							</select>
						</td>
						<td>
							<div>
								<select id="userValue"  autocomplete="off">
									<option value=""><fmt:message key="openiam.ui.am.auth.provider.attribute.prop.user.select"/></option>
									<c:forEach var="attribute" items="${requestScope.amAttributes}">
										<option value="${attribute.id}">${attribute.attributeName}</option>
									</c:forEach>
								</select>
							</div>
							<div style="display:none">
								<input id="staticValue" placeholder='<fmt:message key="openiam.ui.am.auth.provider.attribute.prop.static.placeholder"/>' type="text" class="full rounded"  autocomplete="off"/>
							</div>
							<div style="display:none">
								<input id="groovyScript" placeholder='<fmt:message key="openiam.ui.am.auth.provider.attribute.prop.groovy.placeholder"/>' type="text" class="full rounded"  autocomplete="off" />
							</div>
						</td>
						<td>
							<input id="propertySave" type="button" value='<fmt:message key="openiam.ui.am.auth.provider.attribute.save"/>' class="redBtn"/>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</body>
</html>