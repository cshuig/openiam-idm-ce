<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--@elvariable id="synchReviewListCommand" type="org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization.SynchReviewListCommand"--%>

<ui:template command="${synchReviewListCommand}">

    <jsp:attribute name="searchScript">
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/synch/synchReviewList.search.js"></script>
    </jsp:attribute>

    <jsp:body>
        <script type="text/javascript">
            OPENIAM = window.OPENIAM || {};
            OPENIAM.ENV = window.OPENIAM.ENV || {};
            OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.synchReviewListCommand.synchConfigId}">"id=${requestScope.synchReviewListCommand.synchConfigId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        </script>
        <form:form id="searchForm" method="POST" commandName="synchReviewListCommand">
            <form:hidden path="synchConfigId" />
            <div id="title" class="title">
                <spring:message code="openiam.ui.idm.synch.synchReviewlist.header"/>
            </div>
            <div class="frameContentDivider">
                <table cellspacing="1" id="tableOne" class="yui" width="100%">
                    <thead>
                    <%--<tr>--%>
                        <%--<td class="filter" colspan="4">--%>
                            <%--<spring:message code="openiam.ui.common.search"/>--%>
                            <%--<img id="filterClearOne" class="clearInput" src="/openiam-ui-static/plugins/tablesorter/img/cross.png" title="<fmt:message key='openiam.ui.common.clear' />" />--%>
                            <%--<button class="redBtn" type="submit"><spring:message code="openiam.ui.button.search"/></button>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <th class="center"><input type="checkbox" id="selectAll" /></th>
                        <th><spring:message code="openiam.ui.idm.synchReview.table.col.createTime"/></th>
                        <th><spring:message code="openiam.ui.idm.synchReview.table.col.recordsNum"/></th>
                        <th><spring:message code="openiam.ui.idm.synchReview.table.col.modifyTime"/></th>
                        <th><spring:message code="openiam.ui.idm.synchReview.table.col.execTime"/></th>
                        <th><spring:message code="openiam.ui.common.actions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${not empty synchReviewList}">
                            <c:forEach var="entity" items="${synchReviewList}">
                                <tr entityId="${entity.synchReviewId}">
                                    <td class="center"><input type="checkbox" name="entityIds" value="${entity.synchReviewId}" /></td>
                                    <td>
                                        <a href="synchReview.html?id=${entity.synchReviewId}">
                                            <fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </a>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${entity.sourceRejected}">
                                                <spring:message code="openiam.ui.idm.synchReview.sourceRejected" />
                                            </c:when>
                                            <c:when test="${not empty entity.reviewRecords}">
                                                ${entity.reviewRecords.size()-1}
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td class="center">
                                        <c:if test="${not empty entity.modifyTime}">
                                            <div class="openiam-check-icon"></div>
                                        </c:if>
                                    </td>
                                    <td class="center">
                                        <c:if test="${not empty entity.execTime and entity.execTime ge entity.modifyTime}">
                                            <div class="openiam-check-icon"></div>
                                        </c:if>
                                    </td>
                                    <td>
                                        <a href="synchReview.html?id=${entity.synchReviewId}">
                                            <div class="openiam-edit-icon openiam-image-option"></div>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6">
                                    <spring:message code="openiam.ui.idm.synch.table.noresults"/>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                    <tfoot>
                    <tr id="pagerOne">
                        <td colspan="6" style="border-right: solid 3px #7f7f7f;">
                            <img src="/openiam-ui-static/plugins/tablesorter/img/first.png" class="first"/>
                            <img src="/openiam-ui-static/plugins/tablesorter/img/prev.png" class="prev"/>
                            <input type="text" class="pagedisplay"/>
                            <img src="/openiam-ui-static/plugins/tablesorter/img/next.png" class="next"/>
                            <img src="/openiam-ui-static/plugins/tablesorter/img/last.png" class="last"/>
                            <form:select class="pagesize" path="size" id="size" items="${synchReviewListCommand.pages}" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" style="text-align: left">
                            <input value="<fmt:message key='openiam.ui.common.delete.selected' />" name="deleteSelected" type="submit" class="redBtn">
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>
            <form:hidden path="page" />
        </form:form>

    </jsp:body>
</ui:template>