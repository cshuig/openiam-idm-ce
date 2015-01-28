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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.selfservice.user.my.identities" /></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/selfservice/my.identities.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.user.id}">"id=${requestScope.user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.ui.selfservice.user.identities.user" />: ${requestScope.user.displayName}
		</div>
    	<div class="frameContentDivider">
    		<table cellspacing="1" id="tableOne" class="yui" width="100%">
    			<thead>
            		<tr>
            			<th><fmt:message key="openiam.ui.selfservice.user.identity" /></th>
            			<th><fmt:message key="openiam.ui.shared.managed.system" /></th>
            			<th><fmt:message key="openiam.ui.selfservice.user.identity.status" /></th>
            			<th><fmt:message key="openiam.ui.common.locked" />?</th>
            			<th><fmt:message key="openiam.ui.user.identities.last.password.change" /></th>
            			<th><fmt:message key="openiam.ui.user.my.info.password.expiration" /></th>
            		</tr>
            	</thead>
            	<tbody>
            		<c:choose>
            			<c:when test="${! empty requestScope.loginList and fn:length(requestScope.loginList) > 0}">
            				<c:forEach var="identity" items="${requestScope.loginList}">
            					<tr entityId="${identity.loginId}">
            						<td>${identity.login}</td>
            						<td>${requestScope.managedSysMap[identity.managedSysId].name}</td>
            						<td>${identity.status}</td>
            						<td>
            							<c:choose>
            								<c:when test="${identity.isLocked eq 1}"><fmt:message key="openiam.ui.common.yes" /></c:when>
            								<c:otherwise><fmt:message key="openiam.ui.common.no" /></c:otherwise>
            							</c:choose>
            						</td>
            						<td>
            							<c:choose>
            								<c:when test="${! empty identity.pwdChanged}">
            									<fmt:formatDate value="${identity.pwdChanged}" pattern="yyyy-MM-dd HH:mm:ss"/>
            								</c:when>
            								<c:otherwise></c:otherwise>
            							</c:choose>
            						</td>
            						<td>
            							<c:choose>
            								<c:when test="${! empty identity.pwdExp}">
            									<fmt:formatDate value="${identity.pwdExp}" pattern="yyyy-MM-dd HH:mm:ss"/>
            								</c:when>
            								<c:otherwise></c:otherwise>
            							</c:choose>
            						</td>
            					</tr>
            				</c:forEach>
            			</c:when>
            			<c:otherwise>
            				<tr>
            					<td colspan="5">
                                    <fmt:message key="openiam.ui.user.identities.not.any.identities" />
            					</td>
            				</tr>
            			</c:otherwise>
            		</c:choose>
            	</tbody>
    		</table>
    	</div>
	</body>
</html>