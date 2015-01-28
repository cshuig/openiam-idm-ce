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
		<c:when test="${! empty requestScope.organization}">
			<fmt:message key="openiam.ui.shared.organization.edit" />: ${requestScope.organization.name}
		</c:when>
		<c:otherwise>
			<fmt:message key="openiam.ui.shared.organization.create.new" />
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
        <script type="text/javascript" src="/openiam-ui-static/js/common/search/organization.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/organization/organization.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/attribute.table.edit.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Organization = ${requestScope.organizationAsJSON};
            //OPENIAM.ENV.ParentOrganizations = ${requestScope.parentOrgsAsJSON};
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.organization.id}">"id=${requestScope.organization.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<div id="title" class="title">${pageTitle}</div>
		<div class="frameContentDivider">
			<form id="organizationForm" method="post">
				<table cellpadding="8px" align="center" class="fieldset">
					<tbody>
                        <tr>
							<td><label for="organizationName" class="required"><fmt:message key="openiam.ui.report.parameters.organization.name" /> :</label></td>
							<td>
								<input id="organizationName" autocomplete="off" maxlength="200" type="text" class="full rounded" />
							</td>
						</tr>
						
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.shared.organization.abbreviation" />
								</label>
							</td>
							<td>
								<input id="abbreviation" autocomplete="off" maxlength="20" type="text" class="full rounded" />
							</td>
						</tr>
						
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.shared.organization.symbol" />
								</label>
							</td>
							<td>
								<input id="symbol" autocomplete="off" type="text" maxlength="10"  class="full rounded" />
							</td>
						</tr>
						
						<tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.common.description" />
								</label>
							</td>
							<td>
								<textarea id="description" autocomplete="off" maxlength="512" name="description" class="full rounded"></textarea>
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
                            <td><label for="internalOrgId"><fmt:message key="openiam.ui.shared.organization.internal.id" /> :</label></td>
                            <td>
                                <input id="internalOrgId" autocomplete="off" maxlength="200" type="text" class="full rounded" />
                            </td>
                        </tr>

		  				<c:if test="${! empty requestScope.organizationTypes and fn:length(requestScope.organizationTypes) > 0}">
			  				<tr>
			  					<td><label for="organizationTypeId" class="required"><fmt:message key="openiam.ui.common.organization.type" /> :</label></td>
			  					<td>
			  						<select id="organizationTypeId" name="classification">
			  							<option value="">
			  								<fmt:message key="openiam.ui.shared.organization.select.organization.type" />...
			  							</option>
			  							<c:forEach var="orgType" items="${requestScope.organizationTypes}">
			  								<option value="${orgType.id}">${orgType.name}</option>
			  							</c:forEach>
									</select>
			  					</td>
			  				</tr>
		  				</c:if>
		  				<c:if test="${! empty requestScope.organization.adminResourceId}">
							<tr>
								<td>
									<label>
										<fmt:message key="openiam.ui.shared.protected.by.resource" />
									</label>
								</td>
								<td>
									<a href="editResource.html?id=${requestScope.organization.adminResourceId}">${requestScope.organization.adminResourceCoorelatedName}</a>
								</td>
							</tr>
						</c:if>
		  				<tr>
              				<td>
              					<label>
              						<fmt:message key="openiam.ui.shared.organization.alias" />
              					</label>
              				</td>
			  				<td>
			  					<input id="alias" autocomplete="off" maxlength="200" type="text" class="full rounded" />
			  				</td>
		  				</tr>
                        <tr>
							<td>
								<label>
									<fmt:message key="openiam.ui.shared.organization.domain.name" />
								</label>
							</td>
							<td>
                              	<textarea id="domainName" autocomplete="off" maxlength="250" type="text" class="full rounded"></textarea>
							</td>
                        </tr>
						<tr>
              				<td>
              					<label>
              						<fmt:message key="openiam.ui.shared.organization.ldap.string" />
              					</label>
              				</td>
			  				<td>
			  					<input id="ldapString" autocomplete="off" maxlength="255" type="text" class="full rounded" />
			  				</td>
		  				</tr>
		  				<tr>
		                	<td>
			                    <label>
			                    	<fmt:message key="openiam.ui.shared.organization.selectable" />
			                    </label>
			                </td>
			                <c:set var="selectableExplanation">
			                	<fmt:message key='openiam.ui.shared.organization.selectable.explanation' />
			                </c:set>
		    	            <td class="_input_tiptip" title="${selectableExplanation}">
		        	            <input id="isSelectable" type="checkbox" autocomplete="off" />
		                	</td>
		            	</tr>
					</tbody>
				</table>
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
                                        <input id="save" type="submit" class="redBtn" value="<fmt:message key='openiam.ui.common.save' />" />
                                    </a>
                                </li>
                                <li class="leftBtn">
                                    <a href="organizations.html" class="whiteBtn">
                                    	<fmt:message key="openiam.ui.common.cancel" />
                                    </a>
                                </li>
                                <c:if test="${not requestScope.isNew}">
                                    <li class="rightBtn">
                                        <a id="deleteOrganization" href="javascript:void(0);" class="redBtn">
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
		<c:if test="${! empty requestScope.organization.id}">
			<div class="title">
				<fmt:message key="openiam.ui.common.parent.organizations" />
			</div>
			<div class="frameContentDivider">
				<div id="parentOrganizations"></div>
			</div>
		</c:if>
		<div id="editDialog"></div>
        <div id="parentOrganizationSearchResult"></div>
	</body>
</html>