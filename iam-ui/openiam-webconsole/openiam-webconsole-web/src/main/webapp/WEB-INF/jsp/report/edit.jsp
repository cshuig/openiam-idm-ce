
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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
	<c:set var="errorMessage">
	<c:if test="${! empty requestScope.errorToken}">
		<spring:message code="${requestScope.errorToken.error.messageName}" arguments="${requestScope.errorToken.params}" />
	</c:if>
</c:set>

<c:set var="successMessage">
	<c:if test="${! empty requestScope.successToken}">
		<spring:message code="${requestScope.successToken.message.messageName}" />
	</c:if>
</c:set>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.common.report"/></title>
	    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
	    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    	<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
    	<link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
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
	
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/report/report.edit.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/report/entitlements.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		
		
		<script type="text/javascript">
		
				OPENIAM = window.OPENIAM || {};
				OPENIAM.ENV = window.OPENIAM.ENV || {};
				OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.Id = <c:choose><c:when test="${! empty requestScope.reportInfoDto.reportId}">"${requestScope.reportInfoDto.reportId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.SuccessMessage = <c:choose><c:when test="${! empty successMessage}">"${successMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.reportInfoDto.reportId}">"id=${requestScope.reportInfoDto.reportId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
                OPENIAM.ENV.REPORT_DATASOURCE_TAKEN = '${requestScope.REPORT_DATASOURCE_TAKEN}';
                OPENIAM.ENV.REPORT_URL_TAKEN = '${requestScope.REPORT_URL_TAKEN}';
                OPENIAM.ENV.ParamTypeNameList=null;
		        <c:if test="${! empty requestScope.paramTypeNameList}">
		            OPENIAM.ENV.ParamTypeNameList=[];					
		            <c:forEach var="params" items="${paramTypeNameList}">		          
		                OPENIAM.ENV.ParamTypeNameList.push({id: '${params.id}', name:'${params.name}'});
		            </c:forEach>
                </c:if>
                OPENIAM.ENV.ParamMetaTypeNameList=null;
                <c:if test="${! empty requestScope.paramMetaTypeNameList}">
                    OPENIAM.ENV.ParamMetaTypeNameList=[];
                    <c:forEach var="params" items="${paramMetaTypeNameList}">
                        OPENIAM.ENV.ParamMetaTypeNameList.push({id: '${params.id}', name:'${params.name}'});
                    </c:forEach>
		        </c:if>
        </script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${! empty requestScope.reportInfoDto}">
                    <fmt:message key="openiam.ui.report.edit.report"/>: ${requestScope.reportInfoDto.reportName}
                </c:when>
				<c:otherwise>
                    <fmt:message key="openiam.ui.report.create.new.report"/>
                </c:otherwise>
			</c:choose>
		</div>
		
	
		<div class="frameContentDivider">
		<form id="ReportForm" method="post" action="saveReport.html" enctype="multipart/form-data">

            <input type="hidden" id="overwriteDesignFile" name="overwriteDesignFile" value="false" />
            <input type="hidden" id="overwriteDataSourceFile" name="overwriteDataSourceFile" value="false" />
            <input type="hidden" id="resourceId" name="resourceId" value="${requestScope.linkedResource.id}"/>

            <table cellpadding="8px" align="center" class="fieldset">
               	<tbody>
                    <c:if test="${! empty requestScope.linkedResource}">
                        <tr>
                            <td><spring:message code="openiam.ui.report.linked_resource" />
                            </td>
                            <td><a href="/webconsole/editResource.html?id=${requestScope.linkedResource.id}"
                                   >${requestScope.linkedResource.coorelatedName}</a>
                            </td>
                        </tr>
                    </c:if>

                    <c:if test="${! empty requestScope.reportInfoDto.reportId}">
               			<tr>
               				<td>
               					<input id="reportId" name="reportId" type="hidden" class="full rounded" value="${requestScope.reportInfoDto.reportId}"/>
               				</td>
               			</tr>
               		</c:if>
            		<tr>
              			<td><label class="required"><fmt:message key="openiam.ui.report.name"/></label></td>
              			<td>
              				<input type="text" id="reportName" name="reportName" class="full rounded" value="${requestScope.reportInfoDto.reportName}" size="200"/>
              			</td>
               		</tr>
                    <tr>
                        <td><label class="required" for="reportDataSourceName"><fmt:message key="openiam.ui.report.data.source.script"/></label></td>
                        <td>
                            <select id="reportDataSourceName" name="reportDataSourceName" class="rounded">
                                <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                <c:forEach var="item" items="${requestScope.reportDataSources}">
                                    <option value="${item.id}" <c:if test="${item.id eq requestScope.reportInfoDto.reportDataSource}">selected</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>

                    <tr class="fileUploadHolder">
                       <td><label class="required"><fmt:message key="openiam.ui.report.data.source.script"/></label></td>
                       <td>
                           <input type="file" id="reportDataSourceFile" name="reportDataSourceFile" class="full rounded"/>
                       </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td>
                            <input type="checkbox" id="newDataSourceFile" name="newDataSourceFile"><fmt:message key="openiam.ui.report.upload.new.file"/></input>
                        </td>
                    </tr>

                    <tr>
                        <td><label class="required"><fmt:message key="openiam.ui.report.design.file"/></label></td>
                        <td>
                            <select id="reportDesignName" name="reportDesignName" class="rounded">
                                <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                <c:forEach var="item" items="${requestScope.reportDesigns}">
                                    <option value="${item.id}" <c:if test="${item.id eq requestScope.reportInfoDto.reportUrl}">selected</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>

                    <tr class="fileUploadHolder">
                        <td><label class="required"><fmt:message key="openiam.ui.report.design.file"/></label></td>
                        <td>
                            <input type="file" id="reportDesignFile" name="reportDesignFile" class="full rounded"/>
                        </td>
                    </tr>

                    <tr>
                       <td></td>
                       <td>
                           <input type="checkbox" id="newDesignFile" name="newDesignFile"><fmt:message key="openiam.ui.report.upload.new.file"/></input>
                       </td>
                    </tr>

                </tbody>
                <tfoot>
					<tr>
						<td colspan="2">
							<ul class="formControls">
								<li>
									<a href="javascript:void(0);" id="saveReport">
										<input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.common.save'/>" />
									</a>
								</li>
								<li>
									<a href="report.html" class="whiteBtn"><fmt:message key="openiam.ui.common.cancel"/></a>
								</li>
								<c:if test="${! empty requestScope.reportInfoDto and !requestScope.reportInfoDto.isBuiltIn}">
									<li class="rightBtn">
										<a id="deleteReport" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.common.delete"/></a>
									</li>
								</c:if>
							</ul>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
		</div>
				<c:if test="${! empty requestScope.reportInfoDto.reportId}">
			<div>
				<div class="title">
                    <fmt:message key="openiam.ui.report.parameters"/>
				</div>
				<div class="frameContentDivider">
					<div id="parameters"></div>
				</div>
			</div>
		</c:if>
		<div id="editDialog"></div>
		
	</body>
</html>