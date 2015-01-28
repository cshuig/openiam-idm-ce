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

<c:set var="pageTitle">
	<c:choose>
    	<c:when test="${! empty requestScope.metadataType.id}">
        	<fmt:message key="metadata.type.search.edit.title.edit" />: ${requestScope.metadataType.description}
		</c:when>
        <c:otherwise>
        	<fmt:message key="metadata.type.search.edit.title.new" />
		</c:otherwise>
	</c:choose>
</c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName}-${pageTitle}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />

        <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet"
              type="text/css"
                />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
		
		<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/metadata/metadata.type.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/language/language.admin.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MetadataType = ${requestScope.metadataTypeAsJSON};
            OPENIAM.ENV.metadataTypeId = "${requestScope.metadataTypeId}";
            OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
            OPENIAM.ENV.metadataTypes = <c:choose><c:when test="${! empty requestScope.metadataTypes}">${requestScope.metadataTypes}</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
	      <div id="title" class="title">${pageTitle}</div>
	      <div class="frameContentDivider">
	            <c:if test="${requestScope.metadataType.sensitive eq true}">
	                  <div class="warning">
	                        <fmt:message key="metadata.type.search.edit.edit.warning" />
	                  </div>
	            </c:if>
	            <table cellpadding="8px" align="center" class="fieldset">
	                  <tbody>
	                        <tr>
								<td>
	                              	<label for="description" class="required"> 
	                              		<fmt:message key="openiam.ui.idm.mngconn.table.col.descr" />:
	                              	</label>
								</td>
								<td>
									<input id="description" autocomplete="off" maxlength="40" type="text" class="full rounded" />
								</td>
	                        </tr>
	                        <tr>
								<td>
	                            	<label for="displayNameMap">
	                              		<fmt:message key="openiam.ui.common.display.name" />:
	                              	</label>
								</td>
								<td>
									<div id="displayNameMap"></div>
								</td>
	                        </tr>
	                        <tr>
								<td>
									<label for="grouping">
										<fmt:message key="metadata.type.search.edit.grouping" />:
									</label>
								</td>
								<td>
									<select id="grouping" autocomplete="off" class="rounded">
										<option value="">
											<fmt:message key="openiam.ui.common.value.select" />
										</option>
										<c:forEach var="grouping" items="${requestScope.groupings}">
											<option value="${grouping}" <c:if test="${! grouping.creatable}">disabled="disabled"</c:if>>
												<fmt:message key="openiam.ui.webconsole.meta.type.grouping.${grouping}" />
											</option>
										</c:forEach>
									</select>
								</td>
	                        </tr>
	                        <tr>
								<td>
	                            	<label for="active">
	                              		<fmt:message key="openiam.ui.common.is.active" />
	                              	</label>
								</td>
	                            <td>
	                            	<input id="active" autocomplete="off" maxlength="100" type="checkbox" class="rounded" />
								</td>
	                        </tr>
	                        <tr>
								<td>
									<label for="syncManagedSys">
										<fmt:message key="metadata.type.search.edit.is.sync" />
									</label>
								</td>
	                            <td>
	                            	<input id="syncManagedSys" autocomplete="off" maxlength="100" type="checkbox" class="rounded" />
	                            </td>
	                        </tr>
	                        <tr>
								<td>
									<label>
										<fmt:message key="metadata.type.search.edit.is.binary" />
									</label>
								</td>
	                            <td>
	                            	<input id="binary" autocoplete="off" type="checkbox" class="rounded" />
	                            </td>
	                        </tr>
	                  </tbody>
	                  <tfoot>
						<tr>
	                    	<td colspan="2">
	                        	<ul class="formControls">
	                            	<li class="leftBtn">
	                            		<a id="saveBtn" href="javascript:void(0)" class="redBtn">
	                            			<fmt:message key="openiam.ui.common.save" />
	                            		</a>
	                            	</li>
	                                <li class="leftBtn">
	                                	<a href="metaDataTypeSearch.html" class="whiteBtn">
	                                		<fmt:message key="openiam.ui.common.cancel" />
	                                	</a>
	                                </li>
									<c:if test="${! empty requestScope.metadataType.id and requestScope.metadataType.sensitive eq false}">
										<li class="rightBtn">
											<a id="deleteBtn" href="javascript:void(0);" class="redBtn">
												<fmt:message key="openiam.ui.common.delete" />
											</a>
										</li>
									</c:if>
								</ul>
							</td>
						</tr>
	                  </tfoot>
	            </table>
	      </div>
          <c:if test="${! empty requestScope.metadataTypeId}">
          <div id="" class="title">
              <fmt:message key="openiam.ui.metadata.elements" />
          </div>
          <div style="width: 100%;height: 20px;">
              <ul class="formControls">
                  <li class="rightBtn">
                      <a href="/webconsole/metaDataEdit.html?OPENIAM_MENU_ID=META_EDIT&typeid=${requestScope.metadataTypeId}" class="redBtn">
                          <fmt:message key="metadata.edit.title.new" />
                      </a>
                  </li>
              </ul>
          </div>
          <div class="frameContentDivider" style="border-top: none;">
              <div id="entitlementsContainer"></div>
          </div>
          </c:if>
	</body>
</html>