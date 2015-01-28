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

<c:choose>
	<c:when test="${! empty requestScope.login}">
		<c:set var="pageTitle"><fmt:message key="openiam.ui.user.edit.login" />: ${requestScope.login.login}</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle"><fmt:message key="openiam.ui.user.identities.button.new" /></c:set>
	</c:otherwise>
</c:choose>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - ${pageTitle}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/selfservice/edit.identity.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.user.id}">"id=${requestScope.user.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.UserId = "${requestScope.user.id}";
			
			OPENIAM.ENV.LoginId = <c:choose><c:when test="${! empty requestScope.login}">"${requestScope.login.loginId}"</c:when><c:otherwise>null</c:otherwise></c:choose>

		</script>
	</head>
	<body>
		<div id="title" class="title">
			${pageTitle}
		</div>
		<div class="frameContentDivider">
			<form id="loginForm" method="POST" action="identity.html">
				<table cellpadding="8px" align="center" class="fieldset">
					<thead>
					</thead>
					<tbody>
						<tr>
							<td>
								<label class="required"><fmt:message key="openiam.ui.common.principal" /></label>
							</td>
							<td>
								<input autocomplete="off" type="text" id="principal" class="full rounded" value="<c:if test="${! empty requestScope.login}">${requestScope.login.login}</c:if>" />
							</td>
						</tr>
						<tr>
							<td>
								<label class="required"><fmt:message key="openiam.ui.shared.managed.system" /></label>
							</td>
							<td>
								<select autocomplete="off" id="managedSystemId" class="rounded select">
									<option value=""><fmt:message key="openiam.ui.shared.managed.system.select" /></option>
									<c:forEach var="managedSys" items="${requestScope.managedSystems}">
										<option value="${managedSys.id}" <c:if test="${! empty requestScope.login and requestScope.login.managedSysId eq managedSys.id}">selected="selected"</c:if> >${managedSys.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<c:if test="${! empty requestScope.login}">
							<input type="hidden" id="userId" name="userId" value="${requestScope.user.id}" />
            				<c:if test="${! empty requestScope.login.pwdChanged}">
            					<tr>
            						<td>
            							<label><fmt:message key="openiam.ui.user.password.changed.on" />:</label>
            						</td>
            						<td>
            							<fmt:formatDate value="${requestScope.login.pwdChanged}" pattern="yyyy-MM-dd HH:mm:ss"/>
            						</td>
            					</tr>
            				</c:if>
            				<c:if test="${! empty requestScope.login.pwdExp}">
            					<tr>
            						<td>
            							<label><fmt:message key="openiam.ui.user.identities.password.expires" />:</label>
            						</td>
            						<td>
            							<fmt:formatDate value="${requestScope.login.pwdExp}" pattern="yyyy-MM-dd HH:mm:ss"/>
            						</td>
            					</tr>
            				</c:if>
            				<c:if test="${! empty requestScope.login.gracePeriod}">
            					<tr>
            						<td>
            							<label><fmt:message key="openiam.ui.user.identities.grace.period" />:</label>
            						</td>
            						<td>
            							<fmt:formatDate value="${requestScope.login.gracePeriod}" pattern="yyyy-MM-dd HH:mm:ss"/>
            						</td>
            					</tr>
            				</c:if>
            				<c:if test="${! empty requestScope.login.createDate}">
            					<tr>
            						<td>
            							<label><fmt:message key="openiam.ui.user.identities.created.on" />:</label>
            						</td>
            						<td>
            							<fmt:formatDate value="${requestScope.login.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            						</td>
            					</tr>
            				</c:if>
            				<c:if test="${! empty requestScope.login.lastLogin}">
            					<tr>
            						<td>
            							<label><fmt:message key="openiam.ui.user.identities.last.login" />:</label>
            						</td>
            						<td>
            							<fmt:formatDate value="${requestScope.login.lastLogin}" pattern="yyyy-MM-dd HH:mm:ss"/>
            						</td>
            					</tr>
            				</c:if>
            				<c:if test="${! empty requestScope.login.prevLogin}">
            					<tr>
            						<td>
            							<label><fmt:message key="openiam.ui.user.identities.previous.login" />:</label>
            						</td>
            						<td>
            							<fmt:formatDate value="${requestScope.login.prevLogin}" pattern="yyyy-MM-dd HH:mm:ss"/>
            						</td>
            					</tr>
            				</c:if>
						</c:if>
					</tbody>
					 <tfoot>
		                <tr>
		                    <td colspan="2">
		                        <ul class="formControls">
		                            <li>
		                                <a href="javascript:void(0)">
		                                    <input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.button.save'/>" />
		                                </a>
		                            </li>
		                            <li>
		                                <a href="userIdenties.html?id=${requestScope.user.id}" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel"/></a>
		                            </li>
		                            <c:if test="${! empty requestScope.login.loginId}">
		                                <li class="rightBtn">
		                                    <a id="deleteLogin" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete"/></a>
		                                </li>
		                            </c:if>
		                        </ul>
		                    </td>
		                </tr>
		            </tfoot>
				</table>
			</form>
		</div>
	</body>
</html>