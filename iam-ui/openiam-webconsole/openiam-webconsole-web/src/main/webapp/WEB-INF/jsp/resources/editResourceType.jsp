<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<c:set var="title">
	<c:choose>
		<c:when test="${! empty requestScope.resourceType.id}">
            <fmt:message key="openiam.ui.resource.type.edit.title" />: ${requestScope.resourceType.description}
		</c:when>
		<c:otherwise>
            <fmt:message key="openiam.webconsole.resource.type.create.new" />
		</c:otherwise>
	</c:choose>
</c:set>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName} - ${title}</title>
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
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/resource/resource.type.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/language/language.admin.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.ResourceType = ${requestScope.resourceTypeAsJSON};
			OPENIAM.ENV.Text = {
				DeleteWarn :  localeManager["openiam.common.edit.resource.type.delete.resource.question"]
			};
		</script>
	</head>
	<body>
		<div id="title" class="title">${title}</div>
		<div class="frameContentDivider">
			<table cellpadding="8px" align="center" class="fieldset">
				<tbody>
					<tr>
						<td>
							<label for="description" class="required"><fmt:message key="openiam.ui.common.description" /></label>
						</td>
						<td>
							<input id="description" name="description" type="text" class="full rounded" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="displayNameMap"><fmt:message key="openiam.ui.common.display.name" /></label>
						</td>
						<td>
							<div id="displayNameMap"></div>
						</td>
					</tr>
					<tr>
						<td>
							<label for="provisionResource"><fmt:message key="openiam.common.type.search.provision.resource" /></label>
						</td>
						<td>
							<input id="provisionResource" name="provisionResource" type="text" class="full rounded" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="processName"><fmt:message key="openiam.common.type.search.process.name" /></label>
						</td>
						<td>
							<input id="processName" name="processName" type="text" class="full rounded" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="supportsHierarchy"><fmt:message key="openiam.common.edit.resource.type.support.hierarchy" /></label>
						</td>
						<td>
							<input id="supportsHierarchy" name="supportsHierarchy" type="checkbox" class="full rounded" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="searchable"><fmt:message key="openiam.common.type.search.is.searchable" /></label>
						</td>
						<td>
							<input id="searchable" name="searchable" type="checkbox" class="full rounded" />
						</td>
					</tr>
                    <c:if test="${! empty requestScope.resourceType.id}">
                          <form id="uploadForm" method="post" action="uploadIcon.html" enctype="multipart/form-data">
                                <tr style="display: none;">
                                      <td></td>
                                      <td><input id="resourceTypeId" name="resourceTypeId" type="hidden"
                                            class="full rounded" value="${requestScope.resourceType.id}"
                                      /></td>
                                </tr>
                                <tr style="display: none;">
                                      <td><input id="resourceTypeImageType" name="resourceTypeImageType"
                                            type="hidden" class="full rounded"
                                            value="${requestScope.resourceType.imageType}"
                                      /></td>
                                      <td><input id="resourceTypeURL" name="resourceTypeURL" type="hidden"
                                            class="full rounded" value="${requestScope.resourceType.url}"
                                      /></td>
                                </tr>
                                <tr>
                                      <td><label for="iconUploader"><fmt:message key="openiam.common.edit.resource.type.upload.icon" /></label></td>
                                      <td><input name=iconUploader id="iconUploader" type="file" /></td>
                                </tr>
                                <tr>
                                      <td><c:if
                                                  test="${! empty requestScope.resourceType.url and ! empty requestScope.resourceType.imageType}"
                                            >
                                                  <img id="iconImage"
                                                        src="data:image/${requestScope.resourceType.imageType};base64,${requestScope.resourceType.url}"
                                                        width=64 height=64
                                                  >
                                            </c:if></td>
                                      <td><input name=iconUploader id="iconUploader" type="button"
                                            value="<fmt:message key='openiam.common.edit.resource.type.upload' />" onClick="OPENIAM.ResourceType.Form.uploadIcon()"
                                      /></td>
                                </tr>
                          </form>
                    </c:if>
	                  </tbody>
	
	                  <tfoot>
	                        <tr>
	                              <td colspan="2">
	                                    <ul class="formControls">
	                                          <li class="leftBtn"><a href="javascript:void(0)"> <input
	                                                      type="button" id="saveButton" class="redBtn" value="<fmt:message key='openiam.ui.common.save' />"
	                                                />
	                                          </a></li>
	                                          <li class="leftBtn"><a href="resourceTypes.html" class="whiteBtn"><fmt:message key='openiam.ui.common.cancel' /></a>
	                                          </li>
	                                          <c:if test="${! requestScope.isNew}">
	                                                <li class="rightBtn"><a id="deleteResoruce"
	                                                      href="javascript:void(0);" class="redBtn"
	                                                ><fmt:message key='openiam.ui.common.delete' /></a></li>
	                                          </c:if>
	                                    </ul>
	                              </td>
	                        </tr>
	                  </tfoot>
	            </table>
	      </div>
	      <div id="editDialog"></div>
	</body>
</html>