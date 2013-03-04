<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--@elvariable id="reportCommand" type="org.openiam.selfsrvc.reports.ReportCommand"--%>

<!-- OpenIAM Legacy style sheets -->


<script type="text/javascript" src="<c:url value='/scripts/jquery-1.7.1.min.js'/>"></script>

<table width="800pt">
    <tr>
        <td>
            <table width="100%">
                <tr>
                    <td class="pageTitle" width="70%">
                        <h2 class="contentheading">Generated BIRT Reports</h2>
                    </td>
                </tr>
            </table>
        </td>
    <tr>
        <td>
            <form:form id="reportListForm" commandName="reportCommand">
                <input id="selectedReportId" type="hidden" name="report.reportId" value="" />
                <input id="selectedReportName" type="hidden" name="report.reportName" value="" />

                <table width="800pt" class="bodyTable" height="100%">
                    <tr>
                        <td>
                            <fieldset class="userformSearch">
                                <table class="fieldsetTable" width="100%" height="200pt">
                                    <tr>
                                        <th>Report Name</th>
                                        
                                    </tr>
                                    <c:choose>
                                    <c:when test="${reportCommand.files!=null}">
                                        <c:forEach var="item" items="${reportCommand.files}">
										<c:set var="path" value="file://${item.path}" />
                                        <tr>
                                            <td><a href="${path}" target="_blank">${item.name}</a></td>
                                            
                                        </tr>
                                    </c:forEach>
                                   </c:when>
                                   <c:otherwise>
                                        <tr><td colspan="4">No reports</td></tr>
                                   </c:otherwise>
                                   </c:choose>
                                </table>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </form:form>
        </td>
    </tr>
</table>

