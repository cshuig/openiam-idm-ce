
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.policy.edit" /></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />

        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/policy/attribute.policy.edit.js"></script>
		<script type="text/javascript">
				OPENIAM = window.OPENIAM || {};
				OPENIAM.ENV = window.OPENIAM.ENV || {};
				OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.PolicyId = <c:choose><c:when test="${! empty requestScope.policy.policyId}">"${requestScope.policy.policyId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.policy.policyId}">"id=${requestScope.policy.policyId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			</script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${! empty requestScope.policy and ! empty requestScope.policy.name}">
                    <fmt:message key="openiam.ui.webconsole.policy.edit" />: ${requestScope.policy.name}
					</c:when>
				<c:otherwise>
                    <fmt:message key="openiam.ui.webconsole.policy.create.new.attribute" />
					</c:otherwise>
			</c:choose>
		</div>
	
		<div class="frameContentDivider">
			<table cellpadding="8px" align="center" class="fieldset">
               	<tbody>
               		<c:if test="${! empty requestScope.policy.policyId}">
               			<tr>
               				<td>
               					<label><fmt:message key="openiam.ui.webconsole.policy.id" /></label>
               				</td>
               				<td>
               					<input type="text" id="policyId" name="policyId" disabled="disabled" class="full rounded" value="${requestScope.policy.policyId}"/>
               				</td>
               			</tr>
               		</c:if>
            		<tr>
              			<td>
              				<label class="required"><fmt:message key="openiam.ui.webconsole.policy.name" /></label>
              			</td>
              			<td>
              				<input type="text" id="policyName" name="policyName" class="full rounded" value="${requestScope.policy.name}"/>
              			</td>
               		</tr>
               		<tr>
               			<td>
               				<label><fmt:message key="openiam.ui.webconsole.policymap.col.status" /></label>
               			</td>
               			<td>
               				<c:set var="isSelected">
               					${ empty requestScope.policy.policyId or requestScope.policy.status eq 1}
               				</c:set>
               				<select autocomplete="off" id="status" class="select rounded">
               					<option value="1" <c:if test="${isSelected eq true}">selected="selected"</c:if> ><fmt:message key="openiam.ui.common.active" /></option>
               					<option value="0" <c:if test="${isSelected eq false}">selected="selected"</c:if>><fmt:message key="openiam.ui.common.inactive" /></option>
               				</select>
               			</td>
               		</tr>
               		<tr>
               			<td>
               				<label><fmt:message key="openiam.ui.common.description" /></label>
               			</td>
               			<td>
               				<textarea id="description" name="description" class="full rounded">${requestScope.policy.description}</textarea>
               			</td>
               		</tr>
               		<tr>
               			<td>
               				<label><fmt:message key="openiam.ui.webconsole.policy.rule.url" /></label>
               			</td>
               			<td>
               				<input type="text" id="ruleSrcUrl" name="ruleSrcUrl" class="full rounded" value="${requestScope.policy.ruleSrcUrl}"/>
               			</td>
               		</tr>
               		<tr>
               			<td>
               				<label><fmt:message key="openiam.ui.webconsole.policy.rule" /></label>
               			</td>
               			<td>
               				<textarea id="rule" name="rule" class="full rounded">${requestScope.policy.rule}</textarea>
               			</td>
               		</tr>
                </tbody>
                <tfoot>
					<tr>
						<td colspan="2">
							<ul class="formControls">
								<li class="leftBtn">
									<a id="savePolicy" href="javascript:void(0);"><input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.button.save' />" /></a>
								</li>
								<li class="leftBtn">
									<a href="attributePolicy.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel" /></a>
								</li>
								<c:if test="${! empty requestScope.policy.policyId}">
									<li class="rightBtn">
										<a id="deletePolicy" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete" /></a>
									</li>
								</c:if>
							</ul>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</body>
</html>