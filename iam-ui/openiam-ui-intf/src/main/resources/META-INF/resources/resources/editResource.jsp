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
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<c:set var="pageTitle">
	<c:choose>
		<c:when test="${! empty requestScope.resource.name}">
			<fmt:message key="openiam.ui.shared.resource.edit.resource" />: ${requestScope.resource.name}
		</c:when>
		<c:otherwise>
			<fmt:message key="openiam.ui.shared.resource.create.new.resource" />
		</c:otherwise>
	</c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName} - ${pageTitle}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/plugins/multiselect/css/multiselect.css" rel="stylesheet" type="text/css" />
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
        <script type="text/javascript" src="/openiam-ui-static/plugins/multiselect/js/multiselect.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/resource/resource.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/metadata.type.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/attribute.table.edit.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ResourceId = <c:choose><c:when test="${! empty requestScope.resource.id}">"${requestScope.resource.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.resource.id}">"id=${requestScope.resource.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.Resource = <c:choose><c:when test="${! empty requestScope.resourceAsJSON}">${requestScope.resourceAsJSON}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            <c:if test="${not empty requestScope.managedSystem}">
                OPENIAM.ENV.MngSysProps = <c:choose><c:when test="${not empty requestScope.mngSysPropsAsJSON}">${requestScope.mngSysPropsAsJSON}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            </c:if>
		</script>
	</head>
	<body>
      <div id="title" class="title">${pageTitle}</div>
      <div class="frameContentDivider">
		<form id="resourceForm" method="post">
			<table cellpadding="8px" align="center" class="fieldset">
				<tbody>
					<tr>
						<td>
							<label class="required">
								<fmt:message key="openiam.ui.common.resource.type" />
							</label>
						</td>
						<td>
							<select id="resourceType" name="resourceType" class="select rounded">
								<option value="">
									<fmt:message key="openiam.ui.common.category.select" />
								</option>
								<c:forEach var="type" items="${requestScope.resourceTypes}">
									<option value="${type.id}">${type.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class="required">
								<fmt:message key="openiam.ui.common.name" />
							</label>
						</td>
						<td>
							<input id="resourceName" name="resourceName" type="text" class="full rounded" />
						</td>
					</tr>
					<tr>
						<td>
							<label>
								<fmt:message key="openiam.ui.resource.coorelated.name" />
							</label>
						</td>
						<td>
							<input id="coorelatedName" name="coorelatedName" title="<fmt:message key='openiam.ui.resource.coorelated.name.description' />" type="text" class="full rounded _input_tiptip" />
						</td>
					</tr>
					<tr>
						<td>
							<label>
								<fmt:message key="openiam.ui.common.description" />
							</label>
						</td>
						<td>
							<textarea id="description" name="description" maxlength="512" class="full rounded"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<label>
								<fmt:message key="openiam.ui.webconsole.meta.type" />
							</label>
						</td>
						<td>
							<div id="metadataType"></div>
						</td>
					</tr>
					<tr>
                        <td>
                            <label>
                            	<fmt:message key="openiam.ui.common.risk" />
                            </label>
                        </td>
                        <td>
                            <select id="risk" name="resourceType" class="select risk">
                                <option value="">
                                	<fmt:message key="openiam.ui.common.risk.select" />
                                </option>
                                <c:forEach var="risk" items="${requestScope.riskList}">
                                    <option value="${risk}">${risk}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
					<c:if test="${! empty requestScope.managedSystem}">
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.common.linked.to.managed.system" />
								</label>
							</td>
							<td>
								<a href="/webconsole-idm/provisioning/mngsystem.html?id=${requestScope.managedSystem.id}">${requestScope.managedSystem.name}</a>
							</td>
						</tr>
					</c:if>
					<c:if test="${! empty requestScope.resource.adminResourceId}">
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.shared.protected.by.resource" />
								</label>
							</td>
							<td>
								<a href="editResource.html?id=${requestScope.resource.adminResourceId}">${requestScope.resource.adminResourceName}</a>
							</td>
						</tr>
					</c:if>
					<tr>
						<td>
							<label>
								<fmt:message key="openiam.ui.common.URL" />
							</label>
						</td>
						<td>
							<input id="resourceURL" name="resourceURL" type="text" class="full rounded" />
						</td>
					</tr>
				</tbody>
			</table>
			<c:if test="${(not requestScope.isNew) and (empty requestScope.managedSystem)}">
				<div>
					<div class="title">
						<fmt:message key="openiam.ui.common.attributes" />
					</div>
					<div class="frameContentDivider">
						<div id="attributesContainer"></div>
					</div>
				</div>
			</c:if>
			<table width="100%">
				<tfoot>
					<tr>
						<td colspan="2">
							<ul class="formControls">
								<li class="leftBtn">
									<a href="javascript:void(0)"> 
										<input id="save" type="submit" class="redBtn" value="<fmt:message key="openiam.ui.common.save" />" />
									</a>
								</li>
								<li class="leftBtn">
									<a href="resources.html" class="whiteBtn">
										<fmt:message key="openiam.ui.common.cancel" />
									</a>
								</li>
								<c:if test="${! requestScope.isNew}">
                                    <li class="rightBtn">
                                        <a id="deleteResoruce" href="javascript:void(0);" class="redBtn">
                                        	<fmt:message key="openiam.ui.common.delete" />
                                        </a>
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