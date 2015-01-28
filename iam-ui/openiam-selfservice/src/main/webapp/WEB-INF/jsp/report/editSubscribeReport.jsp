
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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.report.subscription"/></title>
	    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
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
	
		<script type="text/javascript" src="/openiam-ui-static/js/selfservice/report/subscribe.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/selfservice/report/entitlements.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
    	<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		
		<script type="text/javascript">
				OPENIAM = window.OPENIAM || {};
				OPENIAM.ENV = window.OPENIAM.ENV || {};
				OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.Id = <c:choose><c:when test="${! empty requestScope.reportSubscription.reportId}">"${requestScope.reportSubscription.reportId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.reportSubscription.reportId}">"reportId=${requestScope.reportSubscription.reportId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
				OPENIAM.ENV.GetParamNameInfo=null;
		        <c:if test="${! empty requestScope.getParamNameInfo}">
		            OPENIAM.ENV.GetParamNameInfo=[];
		            <c:forEach var="paramName" items="${getParamNameInfo}">
		                OPENIAM.ENV.GetParamNameInfo.push({name:'${paramName.name}', id: '${paramName.id}'});
		            </c:forEach>
		        </c:if>
        </script>
	</head>
	<body>
		<div id="title" class="title">
			<c:choose>
				<c:when test="${! empty requestScope.reportSubscription}">
                    <fmt:message key="openiam.ui.report.subscription.edit"/>: ${requestScope.reportSubscription.reportName}
                </c:when>
				<c:otherwise>
                    <fmt:message key="openiam.ui.report.subscription.create.new"/>
                </c:otherwise>
			</c:choose>
		</div>

		<div class="frameContentDivider">
			<table cellpadding="8px" align="center" class="fieldset">
               	<tbody>
               		<c:if test="${! empty requestScope.reportSubscription.reportId}">
               			<tr>
               				<td>
               					<label for="reportId"><fmt:message key="openiam.ui.report.subscription.id"/></label>
               				</td>
               				<td>
               					<input id="reportId" type="text" name="reportId" disabled="disabled" class="full rounded" value="${requestScope.reportSubscription.reportId}"/>
               				</td>
               			</tr>
               		</c:if>
            		<tr>
              			<td>
              				<label for="reportName" class="required"><fmt:message key="openiam.ui.report.name"/></label>
              			</td>
              			<td>
					    <c:choose>
						<c:when test="${! empty requestScope.reportSubscription}">
                            <input id="reportName" type="text" name="reportName" disabled="disabled" class="full rounded" value="${requestScope.reportSubscription.reportName}"/>
                            <input id="reportInfoId" name="reportInfoId" type="hidden" value="${requestScope.reportSubscription.reportInfoId}"/>
                        </c:when>
                        <c:otherwise>
					        <select name="name" id="name" class="full rounded">
						        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                <c:forEach items="${requestScope.reportNameInfo}" var="rn">
                                    <option value="${rn.id}" >${rn.name}</option>
                                </c:forEach>
                            </select>
						</c:otherwise>
			            </c:choose>
              			</td>
               		</tr>
               		
               		<tr>
              			<td>
              				<label for="dmethod" class="required"><fmt:message key="openiam.ui.report.subscription.delivery.method"/></label>
              			</td>
              			<td>
               				<select id="dmethod" class="select rounded">
               			        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
               					<option value="email" <c:if test="${requestScope.reportSubscription.deliveryMethod eq 'email'}">selected</c:if>>
                                    <fmt:message key="openiam.ui.report.subscription.delivery.email"/>
                                </option>
               					<option value="view" <c:if test="${requestScope.reportSubscription.deliveryMethod eq 'view'}">selected</c:if>>
                                       <fmt:message key="openiam.ui.report.subscription.delivery.view"/>
                                </option>
               				</select>
              			</td>
               		</tr>
               		
               		<tr>
              			<td>
              				<label for="dformat" class="required"><fmt:message key="openiam.ui.report.subscription.delivery.format"/></label>
              			</td>
              			<td>
               				<select id="dformat" class="select rounded">
               			       <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
               					<option value=".pdf" <c:if test="${requestScope.reportSubscription.deliveryFormat eq '.pdf'}">selected</c:if> >Pdf</option>
               					<option value=".html" <c:if test="${requestScope.reportSubscription.deliveryFormat eq '.html'}">selected</c:if>>Html</option>
               					<option value=".xls" <c:if test="${requestScope.reportSubscription.deliveryFormat eq '.xls'}">selected</c:if>>Excel</option>
               				</select>
              			</td>
               		</tr>
               		
               		<tr>
              			<td>
              				<label for="daudience" class="required"><fmt:message key="openiam.ui.report.subscription.delivery.audience"/></label>
              			</td>
              			<td>
              				<select id="daudience" class="select rounded">
               			        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
               					<option value="SELF" <c:if test="${requestScope.reportSubscription.deliveryAudience eq 'SELF'}">selected</c:if> >SELF</option>
               				</select>
              			</td>
               		</tr>
               		<tr>
               			<td>
               				<label for="status"><fmt:message key="openiam.ui.report.subscription.status"/></label>
               			</td>
               			<td>
               				<c:set var="isSelected">
               					${ empty requestScope.reportSubscription.reportId or (requestScope.reportSubscription.status eq true)}
               				</c:set>
               				<select id="status" class="select rounded">
               					<option value="true" <c:if test="${isSelected eq true}">selected="selected"</c:if> ><fmt:message key="openiam.ui.report.subscription.status.active"/></option>
               					<option value="false" <c:if test="${isSelected eq false}">selected="selected"</c:if>><fmt:message key="openiam.ui.report.subscription.status.inactive"/></option>
               				</select>
               			</td>
               		</tr>
                </tbody>
                <tfoot>
					<tr>
						<td colspan="2">
							<ul class="formControls">
								<li class="leftBtn">
									<a id="saveSubscribeReport" href="javascript:void(0);">
										<input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.common.save'/>" />
									</a>
								</li>
								<li class="leftBtn">
									<a href="subscribe.html" class="whiteBtn"><fmt:message key="openiam.ui.common.cancel"/></a>
								</li>
                                <c:if test="${! empty requestScope.reportSubscription.reportId}">
                                    <li class="leftBtn">
                                        <a id="testSubscription" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.report.subscription.test"/></a>
                                    </li>
									<li class="rightBtn">
										<a id="deleteSubscribeReport" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.common.delete"/></a>
									</li>
								</c:if>
							</ul>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		
        <c:if test="${! empty requestScope.reportSubscription}">
			<div>
				<div id="subtitle" class="title">
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