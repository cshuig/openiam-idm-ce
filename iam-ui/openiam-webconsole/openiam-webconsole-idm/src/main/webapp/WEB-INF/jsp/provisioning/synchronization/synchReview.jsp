<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ui:template command="${synchReviewCommand}">
<jsp:body>
<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
<link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
    OPENIAM = window.OPENIAM || {};
    OPENIAM.ENV = window.OPENIAM.ENV || {};
    OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.synchReviewCommand.synchConfigId}">"id=${requestScope.synchReviewCommand.synchConfigId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
</script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/search/search.result.js"></script>
<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/synch/synchReview.search.js"></script>

<form:form id="searchForm" commandName="synchReviewCommand" method="POST">
<form:hidden path="synchConfigId" />
<form:hidden path="synchReviewId" />
<div id="title" class="title">
    <spring:message code="openiam.ui.idm.synchReview.header"/>
</div>
<div class="frameContentDivider">
<table cellpadding="8px" align="center" class="fieldset">
<tbody>
<tr>
    <td>
        <label><spring:message code="openiam.ui.idm.synchReview.table.col.createTime" /></label> <input type="text" readonly value='<fmt:formatDate value="${synchReviewCommand.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' />
        <label><spring:message code="openiam.ui.idm.synchReview.table.col.modifyTime" /></label> <input type="text" readonly value='<fmt:formatDate value="${synchReviewCommand.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' />
        <label><spring:message code="openiam.ui.idm.synchReview.table.col.execTime" /></label> <input type="text" readonly value='<fmt:formatDate value="${synchReviewCommand.execTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' />
    </td>
</tr>
<tr>
    <td>
        <c:choose>
        <c:when test="${synchReviewCommand.sourceRejected}">
            <spring:message code="openiam.ui.idm.synchReview.sourceRejected" />
        </c:when>
        <c:otherwise>
            <div style="width:858px; overflow-x: auto;">
            <table cellspacing="1" id="tableRecords" class="yui" width="100%">
                <c:if test="${not empty requestScope.reviewHeader}">
                    <thead>
                    <tr>
                        <c:forEach var="column" items="${requestScope.reviewHeader.reviewValues}">
                            <th>${column.value}</th>
                        </c:forEach>
                    </tr>
                    </thead>
                </c:if>
                <tbody>
                <c:choose>
                <c:when test="${not empty synchReviewCommand.reviewRecords}">
                    <c:forEach varStatus="i" var="record" items="${synchReviewCommand.reviewRecords}">
                        <tr class="record even">
                            <input type="hidden" name="recordId" value="${record.synchReviewRecordId}" />
                            <c:forEach varStatus="j" var="item" items="${record.reviewValues}">
                                <td>
                                    <input type="hidden" name="recordValueId" value="${item.synchReviewRecordValueId}" />
                                    <input type="text" name="recordValue" value="${item.value}" />
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr class="even">
                        <td colspan="${requestScope.reviewHeader.reviewValues.size()}">
                            <spring:message code="openiam.ui.idm.synch.table.noresults"/>
                        </td>
                    </tr>
                </c:otherwise>
                </c:choose>
                </tbody>
                <tfoot>
                <tr id="pagerOne">
                    <td colspan="${requestScope.reviewHeader.reviewValues.size()}" style="border-right: solid 3px #7f7f7f; height: 25px;">
                        <div style="width: 858px;">
                            <div style="position: relative; text-align: center">
                            <img src="/openiam-ui-static/plugins/tablesorter/img/first.png" class="first"/>
                            <img src="/openiam-ui-static/plugins/tablesorter/img/prev.png" class="prev"/>
                            <input type="text" class="pagedisplay"/>
                            <img src="/openiam-ui-static/plugins/tablesorter/img/next.png" class="next"/>
                            <img src="/openiam-ui-static/plugins/tablesorter/img/last.png" class="last"/>
                                <form:select cssStyle="width: 150px;" class="pagesize" path="size" id="size" items="${synchReviewCommand.pages}" />
                            </div>
                        </div>
                    </td>
                </tr>
                </tfoot>
            </table>
            </div>
        </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>
        <form:checkbox path="skipSourceValid" id="skipSourceValid" />&nbsp;&nbsp;<form:label path="skipSourceValid"><spring:message code="openiam.ui.idm.synch.synchReview.label.skipSourceValid"/></form:label>
        <form:checkbox path="skipRecordValid" id="skipRecordValid" />&nbsp;&nbsp;<form:label path="skipRecordValid"><spring:message code="openiam.ui.idm.synch.synchReview.label.skipRecordValid"/></form:label>
    </td>
</tr>
</tbody>
    <tfoot>
    <tr>
        <td colspan="2">
            <ul class="formControls">
                <li class="leftBtn">
                    <a href="/webconsole-idm/provisioning/synchReviewList.html?id=${synchReviewCommand.synchConfigId}" class="whiteBtn"><spring:message code="openiam.ui.common.cancel" /></a>
                </li>
                <li class="leftBtn">
                    <a href="javascript:void(0)">
                        <a id="save" href="#" class="redBtn"><spring:message code="openiam.ui.common.save" /></a>
                    </a>
                </li>
                <li class="leftBtn">
                    <a href="javascript:void(0)">
                        <a id="execute" href="#" class="redBtn"><spring:message code="openiam.ui.common.execute" /></a>
                    </a>
                </li>
            </ul>
        </td>
    </tr>
    </tfoot>
<tfoot>
</tfoot>
</table>
</div>
<form:hidden path="page" />
</form:form>
<div id="editDialog"></div>
</jsp:body>
</ui:template>