<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>

<c:set var="pageTitle">
	<c:choose>
		<c:when test="${! empty requestScope.group.id}">
			<fmt:message key="openiam.ui.shared.group.edit" />: ${requestScope.group.name}
		</c:when>
		<c:otherwise>
			<fmt:message key="openiam.ui.shared.group.new" />
		</c:otherwise>
	</c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - ${pageTitle}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
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
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/metadata.type.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/group/group.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/organization.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/attribute.table.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/group.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.form.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/orghierarchy/organization.hierarchy.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.GroupId = <c:choose><c:when test="${! empty requestScope.group.id}">"${requestScope.group.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Group = ${requestScope.groupAsJSON};
			OPENIAM.ENV.OrganizationHierarchy = ${requestScope.orgHierarchy};
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.group.id}">"id=${requestScope.group.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<div id="title" class="title">${pageTitle}</div>
		<div class="frameContentDivider">
			<form id="groupForm" method="post">
				<table cellpadding="8px" align="center" class="fieldset">
					<tbody>
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.group.type" />
								</label>
							</td>
							<td>
								<input id="groupTypeId" name="groupTypeId"  type="hidden"  />
								<input id="groupType" name="groupType"  type="text" readonly class="full rounded" />
							</td>
						</tr>
						<tr>
							<td>
								<label class="required">
									<fmt:message key="openiam.ui.common.name" />
								</label>
							</td>
							<td>
								<input id="groupName" name="groupName"  type="text" class="full rounded" />
							</td>
						</tr>
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.common.description" />
								</label>
							</td>
							<td>
								<textarea id="description" name="description" class="full rounded"></textarea>
							</td>
						</tr>

						<c:if test="${! empty requestScope.managedSystems and fn:length(requestScope.managedSystems) > 0}">
							<tr>
								<td>
									<label>
										<fmt:message key="openiam.ui.shared.managed.system" />
									</label>
								</td>
								<td>
									<select id="managedSysId" name="type">
										<option value="">
											<fmt:message key="openiam.ui.common.value.select" />
										</option>
										<c:forEach var="bean" items="${requestScope.managedSystems}">
											<option value="${bean.id}">${bean.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</c:if>
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.group.classification" />
								</label>
							</td>
							<td>
								<div id="groupClassification"/>
							</td>
						</tr>
						<c:if test="${! empty requestScope.group.mdTypeId and requestScope.group.mdTypeId=='AD_GROUP'}">

							<tr>
								<td>
									<label>
										<fmt:message key="openiam.ui.group.ad.type" />
									</label>
								</td>
								<td>
									<div id="groupADType"/>
								</td>
							</tr>

							<tr>
								<td>
									<label>
										<fmt:message key="openiam.ui.group.ad.scope" />
									</label>
								</td>
								<td>
									<div id="groupADScope"/>
								</td>
							</tr>
						</c:if>
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.common.risk" />
								</label>
							</td>
							<td>
								<div id="groupRisk"/>
							</td>
						</tr>


						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.group.max.user.count" />
								</label>
							</td>
							<td>
								<input id="groupMaxUserCount" name="groupMaxUserCount"  type="text" class="full rounded" />
							</td>
						</tr>
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.group.membership.duration" />
								</label>
							</td>
							<td>
								<input id="groupMembershipDuration" name="groupMembershipDuration"  type="text" class="full rounded" />
							</td>
						</tr>

						<c:if test="${empty requestScope.group.id}">
							<tr>
								<td>
									<label>
										<fmt:message key="openiam.ui.group.parent" />
									</label>
								</td>
								<td>
									<div id="groupParent"/>
								</td>
							</tr>
							<tr>
								<td>
									<label>
										<fmt:message key="openiam.ui.group.owner" />
									</label>
								</td>
								<td>
									<input id="groupOwner" type="text"  class="full rounded"  readonly="readonly">
									<input id="groupOwnerId" type="hidden" value="" name="groupOwnerId">
									<input id="groupOwnerType" name="groupOwnerType"  type="hidden" class="full rounded" />
									<a id="selectGroupOwner" href="javascript:void(0);"><fmt:message key="openiam.ui.group.owner.select"/></a>
								</td>
							</tr>
						</c:if>

						<c:if test="${! empty requestScope.group.adminResourceId}">
							<tr>
								<td>
									<label>
										<fmt:message key="openiam.ui.shared.protected.by.resource" />
									</label>
								</td>
								<td>
									<a href="editResource.html?id=${requestScope.group.adminResourceId}">${requestScope.group.adminResourceCoorelatedName}</a>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
				<div id="organizationsTable">
				</div>
				<c:if test="${! requestScope.isNew}">
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
											<input id="save" type="submit" class="redBtn" value="<fmt:message key="openiam.ui.button.save" />" />
										</a>
									</li>
									<li class="leftBtn">
										<a href="groups.html" class="whiteBtn">
											<fmt:message key="openiam.ui.button.cancel" />
										</a>
									</li>
									<c:if test="${! requestScope.isNew}">
										<li class="rightBtn">
											<a id="deleteGroup" href="javascript:void(0);" class="redBtn">
												<fmt:message key="openiam.ui.button.delete" />
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
		<div id="userResultsArea"></div>
	</body>
</html>