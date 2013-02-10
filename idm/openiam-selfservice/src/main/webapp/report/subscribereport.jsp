<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--@elvariable id="subscribeReportsCommand" type="org.openiam.selfsrvc.reports.SubscribeReportsCommand"--%>
<%--@elvariable id="reportParameters" type="java.util.List<org.openiam.idm.srvc.report.dto.ReportSubCriteriaParamDto>"--%>

<script type="text/javascript" src="<c:url value='/scripts/jquery-1.7.1.min.js'/>"></script>
<table width="800pt">
    <tr>
        <td>
            <table width="100%">
                <tr>
                    <td class="pageTitle" width="70%">
                        <h2 class="contentheading"><c:choose><c:when test="${subscribeReportsCommand.report.reportName!=null}">Edit BIRT Report ${subscribeReportsCommand.report.reportName} Subscription</c:when><c:otherwise>BIRT Report Subscription</c:otherwise></c:choose></h2>
                    </td>
                </tr>
            </table>
        </td>
    <tr>
        <td>
            <form:form method="POST" id="subscribeReportsCommand" action="subscribeReport.selfserve" commandName="subscribeReportsCommand" enctype="multipart/form-data">
                <input type="hidden" name="report.reportId" value="${subscribeReportsCommand.report.reportId}" />
                <c:if test="${subscribeReportsCommand.report.reportName!=null}">
                    <input type="hidden" name="report.reportName" value="${subscribeReportsCommand.report.reportName}" />
                </c:if>
                <input type="hidden" name="report.deliveryMethod" value="${subscribeReportsCommand.report.deliveryMethod}" />
                <input type="hidden" name="report.deliveryFormat" value="${subscribeReportsCommand.report.deliveryFormat}" />
                <input type="hidden" name="report.deliveryAudience" value="${subscribeReportsCommand.report.deliveryAudience}" />
                <input type="hidden" name="report.status" value="${subscribeReportsCommand.report.status}" />
                <table width="650pt" class="bodyTable" height="100%">
                    <tr>
                        <td>
                            <fieldset class="userformSearch">

                                <table class="fieldsetTable" width="100%" height="200pt">
                                    <c:if test="${subscribeReportsCommand.report.reportName==null}">
                                       <tr>
                                        <td class="tddark" width="200pt">
                                            <label class="control-label" for="designFileInpId">
                                               Report Name
                                            </label>
                                        </td><td> 
									<form:select path="report.reportName" items="${reportsList}" onchange="submit()"/></td></tr>
									
                                    </c:if>
                                    <tr>
                                        <td class="tddark" width="200pt">
                                            <label class="control-label" for="designFileInpId">
                                               Delivery Method
                                            </label>
                                        </td><td> 
									<form:select path="report.deliveryMethod" items="${deliveryMethodList}" /></td></tr>
									<tr>
                                        <td class="tddark" width="200pt">
                                            <label class="control-label" for="designFileInpId">
                                               Delivery Format
                                            </label>
                                        </td><td> 
									<form:select path="report.deliveryFormat" items="${deliveryFormatList}" /></td></tr>
									<tr>
                                        <td class="tddark" width="200pt">
                                            <label class="control-label" for="designFileInpId">
                                               Delivery Audience
                                            </label>
                                        </td><td> 
									<form:select path="report.deliveryAudience" items="${deliveryAudienceList}" /></td></tr>
									<tr>
                                        <td class="tddark" width="200pt">
                                            <label class="control-label" for="designFileInpId">
                                               Status
                                            </label>
                                        </td><td> 
									<form:select path="report.status" items="${statusList}" /></td></tr>

                                            <c:choose>
                                            <c:when test="${not empty reportParameters}">
                                                                                <tr><td colspan="2">Parameters</td></tr>
                                    <tr>
                                    <td colspan="2">
                                     <table id="paramTable">
                                            <c:forEach items="${reportParameters}" var="rep" varStatus="status">
                                            <tr>
                                                <td>
                                                    <span>Name: </span>
                                                    <input type="text"
                                                           name="paramName"
                                                           value="${rep.name}"/>
                                                </td>
                                                <td>
                                                    <span>Type: </span>
                                                    <input type="text"
                                                           name="paramName"
                                                           value="${rep.typeId}"/>
                                                    
                                                </td>
                                                <td>
                                                    <span>Value: </span>
                                                    <input type="text"
                                                           name="paramName"
                                                           value="${rep.value}"/>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                            </table>
                                    </td>
                                    </tr>
                                            </c:when>
                                            
                                            </c:choose>
                                       
                                    <tr>
                                        <td colspan="2" align="right">
                                            <input type="submit" name="cancel" value="Cancel">
                                            <input type="submit" name="save" value="Save">
                                        </td>
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
