<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.audit.log.viewer"/></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole/log.view.css" rel="stylesheet" type="text/css" />
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
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/log/view.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.sourceId}">"id=${requestScope.sourceId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.SourceId = <c:choose><c:when test="${! empty requestScope.sourceId}">"${requestScope.sourceId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Source = <c:choose><c:when test="${! empty requestScope.source}">"${requestScope.source}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.Log = {
				ID : "${requestScope.logId}",
				Children : <c:choose><c:when test="${! empty requestScope.children}">${requestScope.children}</c:when><c:otherwise>null</c:otherwise></c:choose>,
				Parents : <c:choose><c:when test="${! empty requestScope.parents}">${requestScope.parents}</c:when><c:otherwise>null</c:otherwise></c:choose>,
				Targets : <c:choose><c:when test="${! empty requestScope.targets}">${requestScope.targets}</c:when><c:otherwise>null</c:otherwise></c:choose>
			};
		</script>
	</head>
	<body>
		<div class="frameContentDivider">
			<div id="title" class="title">
				<span><fmt:message key="openiam.ui.audit.log.event.info"/></span>
			</div>
			<div>
				<table>
					<tbody>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.user.id"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.userId}">${requestScope.log.userId}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.common.principal"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.principal}">${requestScope.log.principal}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.audit.log.mngsys.id"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.managedSysId}">${requestScope.log.managedSysId}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.audit.log.timestamp"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.timestamp}">${requestScope.log.timestamp}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.audit.log.source"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.source}">${requestScope.log.source}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.audit.log.client.ip"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.clientIP}">${requestScope.log.clientIP}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.audit.log.node.ip"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.nodeIP}">${requestScope.log.nodeIP}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.audit.log.action"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.action}">${requestScope.log.action}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.audit.log.result"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.result}">${requestScope.log.result}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label><fmt:message key="openiam.ui.audit.log.session.id"/></label>
							</td>
							<td>
								<span>
									<c:choose>
										<c:when test="${! empty requestScope.log.sessionID}">${requestScope.log.sessionID}</c:when>
										<c:otherwise><fmt:message key="openiam.ui.audit.log.not.available"/></c:otherwise>
									</c:choose>
								</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableContainer">
			<div class="containerTitle"><fmt:message key="openiam.ui.audit.log.event.target"/></div>
			<div id="targetContainer">
			</div>
		</div>

		<div class="tableContainer">
			<div class="containerTitle"><fmt:message key="openiam.ui.audit.log.event.parent"/></div>
			<div id="parentEventsContainer">
			</div>
		</div>
		
		<div class="tableContainer">
			<div class="containerTitle"><fmt:message key="openiam.ui.audit.log.event.child"/></div>
			<div id="childEventsContainer">
			</div>
		</div>

        <div class="frameContentDivider">
            <div class="title">
                <span><fmt:message key="openiam.ui.audit.log.event.attribute"/></span>
            </div>
            <div>
                <table>
                    <tbody>
                    <c:choose>
                        <c:when test="${! empty requestScope.log.customRecords and fn:length(requestScope.log.customRecords) > 0}">
                            <c:forEach var="record" items="${requestScope.log.customRecords}">
                                <tr>
                                    <td>
                                        <label>${record.key}</label>
                                    </td>
                                    <td>
                                        <div>
                                            <c:out value="${record.value}" escapeXml="true" />
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="2">
                                    <fmt:message key="openiam.ui.audit.log.event.attribute.empty"/>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
        <div style="height: 25px;">
            <ul class="formControls">
                <li class="rightBtn">
                    <a href="viewRecentLogRecord.html?from=${requestScope.nextFrom}&size=${requestScope.size}&page=${requestScope.nextPage}&totalSize=${requestScope.totalSize}&fromDate=${requestScope.fromDate}&toDate=${requestScope.toDate}&sourceId=${requestScope.sourceId}&source=${requestScope.source}&result=${requestScope.result}&action=${requestScope.action}&secondaryTargetId=${requestScope.secondaryTargetId}&secondaryTargetType=${requestScope.secondaryTargetType}&targetId=${requestScope.targetId}&targetType=${requestScope.targetType}&managedSystem=${requestScope.managedSystem}&requestorId=${requestScope.requestorId}&userId=${requestScope.userId}" class="redBtn"><spring:message code="openiam.ui.audit.log.event.attribute.recent" /></a>
                </li>
                <li class="leftBtn">
                    <a href="viewAuditLog.html" class="whiteBtn"><spring:message code="openiam.ui.common.back.to.search" /></a>
                </li>
                <c:if test="${not empty requestScope.nextId}">
                <li class="rightBtn">
                    <a href="viewLogRecord.html?id=${requestScope.nextId}&from=${requestScope.nextFrom}&size=${requestScope.size}&page=${requestScope.nextPage}&totalSize=${requestScope.totalSize}&fromDate=${requestScope.fromDate}&toDate=${requestScope.toDate}&sourceId=${requestScope.sourceId}&source=${requestScope.source}&result=${requestScope.result}&action=${requestScope.action}&secondaryTargetId=${requestScope.secondaryTargetId}&secondaryTargetType=${requestScope.secondaryTargetType}&targetId=${requestScope.targetId}&targetType=${requestScope.targetType}&managedSystem=${requestScope.managedSystem}&requestorId=${requestScope.requestorId}&userId=${requestScope.userId}" class="redBtn"><spring:message code="openiam.ui.common.next" /></a>
                </li>
                </c:if>
                <c:if test="${not empty requestScope.prevId}">
                <li class="rightBtn">
                    <a href="viewLogRecord.html?id=${requestScope.prevId}&from=${requestScope.prevFrom}&size=${requestScope.size}&page=${requestScope.prevPage}&totalSize=${requestScope.totalSize}&fromDate=${requestScope.fromDate}&toDate=${requestScope.toDate}&sourceId=${requestScope.sourceId}&source=${requestScope.source}&result=${requestScope.result}&action=${requestScope.action}&secondaryTargetId=${requestScope.secondaryTargetId}&secondaryTargetType=${requestScope.secondaryTargetType}&targetId=${requestScope.targetId}&targetType=${requestScope.targetType}&managedSystem=${requestScope.managedSystem}&requestorId=${requestScope.requestorId}&userId=${requestScope.userId}" class="redBtn"><spring:message code="openiam.ui.common.prev" /></a>
                </li>
                </c:if>
            </ul>
        </div>
	</body>
</html>