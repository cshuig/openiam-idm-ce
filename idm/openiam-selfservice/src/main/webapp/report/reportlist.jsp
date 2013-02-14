<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--@elvariable id="reports" type="java.util.List<org.openiam.idm.srvc.report.dto.ReportSubscriptionDto>"--%>
<%--@elvariable id="reportCommand" type="org.openiam.selfsrvc.reports.ReportCommand"--%>

<!-- OpenIAM Legacy style sheets -->


<script type="text/javascript" src="<c:url value='/scripts/jquery-1.7.1.min.js'/>"></script>
<script type="text/javascript">
    var selectReport = function(reportId, reportName, deliveryMethod, status, actionName) {
        $('#selectedReportId').val(reportId);
        $('#selectedReportName').val(reportName);
        $('#selectedDeliveryMethod').val(deliveryMethod);
        $('#selectedDeliveryFormat').val(deliveryFormat);
        $('#selectedDeliveryAudience').val(deliveryAudience);
        $('#selectedUserId').val(userId);
        $('#selectedStatus').val(status);
        if(actionName == 'open') {
          $('#reportListForm').attr('target','_blank');
        } else {
           $('#reportListForm').removeAttr('target');
        }
        this.form.action="subscribeReportOld.selfserve";
        return true;
    };
</script>
<table width="800pt">
    <tr>
        <td>
            <table width="100%">
                <tr>
                    <td class="pageTitle" width="70%">
                        <h2 class="contentheading">BIRT Reports Subscriptions</h2>
                    </td>
                </tr>
            </table>
        </td>
    <tr>
        <td>
            <form:form id="reportListForm" commandName="reportCommand" action="subscribeReportOld.selfserve">
                <input id="selectedReportId" type="hidden" name="report.reportId" value="" />
                <input id="selectedReportName" type="hidden" name="report.reportName" value="" />
                <input id="selectedDeliveryMethod" type="hidden" name="report.deliveryMethod" value="" />
                <input id="selectedDeliveryFormat" type="hidden" name="report.deliveryFormat" value="" />
                <input id="selectedDeliveryAudience" type="hidden" name="report.deliveryAudience" value="" />
                <input id="selectedUserId" type="hidden" name="report.userId" value="" />
                <input id="selectedStatus" type="hidden" name="report.status" value="" />

                <table width="800pt" class="bodyTable" height="100%">
                    <tr>
                        <td>
                            <fieldset class="userformSearch">
                                <table class="fieldsetTable" width="100%" height="200pt">
                                    <tr>
                                        <th>Report Name</th>
                                        <th>Delivery Method</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                    <c:choose>
                                    <c:when test="${reportCommand.reports!=null}">
                                        <c:forEach var="item" items="${reportCommand.reports}">
                                        <tr>
                                            <td>${item.reportName}</td>
                                            <td>${item.deliveryMethod}</td>
                                            <td>${item.status}</td>
                                            <td>
                                                <input type="submit" name="edit_btn" value="Edit" onclick="return selectReport('${item.reportId}','${item.reportName}','${item.deliveryMethod}','${item.deliveryFormat}','${item.deliveryAudience}','${item.status}','${item.userId}','edit');">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                   </c:when>
                                   <c:otherwise>
                                        <tr><td colspan="4">No reports</td></tr>
                                   </c:otherwise>
                                   </c:choose>
                                    <tr>
									<td colspan="4" align="left"><a href="subscribeReportOld.selfserve">New Subscription</a></td>
									</tr>
                                </table>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </form:form>
        </td>
    </tr>
</table>

