<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.audit.log.viewer"/></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
        <link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/datetimepicker/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/datetimepicker/jquery-ui-timepicker-addon.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/log/auditlog.search.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
		</script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.ui.audit.log.records"/>
		</div>
		<div class="frameContentDivider">

            <form method="post" action="" id="userSearchForm">

                <select multiple="multiple" autocomplete="off" name="" id="searchFilter">
                    <option value="requestorField"><fmt:message key="openiam.ui.audit.log.requestor.login" /></option>
                    <option value="managedSystemField"><fmt:message key="openiam.ui.idm.prov.mngsys.header" /></option>
                    <option value="targetTypeField"><fmt:message key="openiam.ui.audit.log.target.type" /></option>
                    <option value="targetIdField"><fmt:message key="openiam.ui.audit.log.target.id" /></option>
                    <option value="targetLoginField"><fmt:message key="openiam.ui.audit.log.target.login" /></option>
                    <option value="secondaryTargetTypeField"><fmt:message key="openiam.ui.audit.log.secondary.target.type" /></option>
                    <option value="secondaryTargetIdField"><fmt:message key="openiam.ui.audit.log.secondary.target.id" /></option>
                </select>
                <table width="100%" cellspacing="1" id="userSearchFormTable" class="yui">
                    <thead>
                    <tr>
                        <td colspan="2">
                            <div style="position:relative;" class="info center"><fmt:message key="openiam.ui.search.dialog.searchfilter.request" /></div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div style="width: 100%;">
                                <div id="fromDateField" class="userSearchCell">
                                    <label for="fromDate" class="required"><fmt:message key="openiam.ui.audit.log.from.date" /></label>
                                    <input type="text" class="date" autocomplete="off" maxlength="30" size="23" value="" name="fromDate" id="fromDate">
                                </div>
                                <div id="toDateField" class="userSearchCell">
                                    <label for="toDate" class="required"><fmt:message key="openiam.ui.audit.log.to.date" /></label>
                                    <input type="text" class="date" autocomplete="off" maxlength="50" size="23" value="" name="toDate" id="toDate">
                                </div>
                                <div id="actionField" class="userSearchCell">
                                    <label for="action"><fmt:message key="openiam.ui.audit.log.action" /></label>
                                    <select name="action" id="action">
                                        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                        <c:forEach items="${metaData.auditTargetActions}" var="item">
                                            <option value="${item.name}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div id="resultField" class="userSearchCell">
                                    <label for="result"><fmt:message key="openiam.ui.audit.log.result" /></label>
                                    <select name="result" id="result">
                                        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                        <c:forEach items="${metaData.auditTargetStatus}" var="item">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div id="resultField2" class="userSearchCell">
                                    <label for="showChild"><fmt:message key="openiam.ui.audit.log.showChild" /></label>
                                    <input type="checkbox" name="showChildren" id="showChild">
                                </div>
                                <div id="requestorField" class="userSearchCell" style="display: none">
                                    <label for="requestor"><fmt:message key="openiam.ui.audit.log.requestor.login" /></label>
                                    <input type="text" autocomplete="off" maxlength="200" size="23" value="" name="requestor" id="requestor">
                                </div>
                                <div id="managedSystemField" class="userSearchCell" style="display: none">
                                    <label for="managedSystem"><fmt:message key="openiam.ui.idm.prov.mngsys.header" /></label>
                                    <select name="managedSystem" id="managedSystem">
                                        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                        <c:forEach items="${metaData.managedSystems}" var="item">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div id="targetTypeField" class="userSearchCell" style="display: none">
                                    <label for="targetType"><fmt:message key="openiam.ui.audit.log.target.type" /></label>
                                    <select name="targetType" id="targetType">
                                        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                        <c:forEach items="${metaData.auditTargetTypes}" var="item">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div id="targetIdField" class="userSearchCell" style="display: none">
                                    <label for="targetId"><fmt:message key="openiam.ui.audit.log.target.id" /></label>
                                    <input type="text" autocomplete="off" maxlength="32" size="23" value="" name="targetId" id="targetId">
                                </div>
                                <div id="targetLoginField" class="userSearchCell" style="display: none">
                                    <label for="targetLogin"><fmt:message key="openiam.ui.audit.log.target.login" /></label>
                                    <input type="text" autocomplete="off" maxlength="32" size="23" value="" name="targetLogin" id="targetLogin">
                                </div>
                                <div id="secondaryTargetTypeField" class="userSearchCell" style="display: none">
                                    <label for="secondaryTargetType"><fmt:message key="openiam.ui.audit.log.secondary.target.type" /></label>
                                    <select name="secondaryTargetType" id="secondaryTargetType">
                                        <option value=""><fmt:message key="openiam.ui.common.please.select"/></option>
                                        <c:forEach items="${metaData.auditTargetTypes}" var="item">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div id="secondaryTargetIdField" class="userSearchCell" style="display: none">
                                    <label for="secondaryTargetId"><fmt:message key="openiam.ui.audit.log.secondary.target.id" /></label>
                                    <input type="text" autocomplete="off" maxlength="32" size="23" value="" name="secondaryTargetId" id="secondaryTargetId">
                                </div>
                            </div>
                        </td>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <td class="filter" colspan="2">
                            <input type="submit" class="redBtn" id="searchLogs" autocomplete="off" value="<fmt:message key='openiam.ui.common.search' />">
                            <a href="javascript:void(0);" class="whiteBtn" id="cleanSearchForm"><fmt:message key="openiam.ui.common.clear" /></a>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </form>
		</div>
        <div id="entitlementsContainer"></div>
	</body>
</html>