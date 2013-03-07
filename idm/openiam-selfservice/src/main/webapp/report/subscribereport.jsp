<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%--@elvariable id="reportCommand" type="org.openiam.selfsrvc.reports.SubscribeReportsCommand"--%>
<%--@elvariable id="reportParameters" type="java.util.List<org.openiam.idm.srvc.report.dto.ReportSubCriteriaParamDto>"--%>
<jsp:useBean id="reportCommand"
	type="org.openiam.selfsrvc.reports.SubscribeReportsCommand"
	class="org.openiam.selfsrvc.reports.SubscribeReportsCommand"
	scope="session">
</jsp:useBean>
<script type="text/javascript"
	src="<c:url value='/scripts/jquery-1.7.1.min.js'/>"></script>
<c:set var="reportCommand" value="${sessionScope.reportCommand}" />
<table width="800pt">
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td class="pageTitle" width="70%">
						<h2 class="contentheading">
							<c:choose>
								<c:when test="${reportCommand.report.reportId!=null}">Edit Subscription for BIRT Report ${reportCommand.report.reportName}</c:when>
								<c:otherwise>BIRT Report Subscription</c:otherwise>
							</c:choose>
						</h2>
					</td>
				</tr>
			</table>
		</td>
	<tr>
		<td><form:form method="POST" id="reportCommand"
				action="subscribeReportOld.selfserve" commandName="reportCommand"
				enctype="multipart/form-data">
				<input type="hidden" name="report.reportId"
					value="${reportCommand.report.reportId}" />
				<c:if test="${reportCommand.report.reportName!=null}">
					<input type="hidden" name="report.reportName"
						value="${reportCommand.report.reportName}" />
				</c:if>
				<table width="650pt" class="bodyTable" height="100%">
					<tr>
						<td>
							<fieldset class="userformSearch">

								<table class="fieldsetTable" width="100%" height="200pt">
									<c:if test="${reportCommand.report.reportId==null}">
										<tr>
											<td class="tddark" width="200pt"><label
												class="control-label" for="designFileInpId"> Report
													Name </label></td>
											<td><form:select path="report.reportName"
													items="${reportsList}" onchange="submit()" /></td>
										</tr>

									</c:if>
									<tr>
										<td class="tddark" width="200pt"><label
											class="control-label" for="designFileInpId"> Delivery
												Method </label></td>
										<td><select name="report.deliveryMethod">
												<c:forEach items="${deliveryMethodList}" var="paramType"
													varStatus="sts">
													<option
														<c:if test="${paramType.key == reportCommand.report.deliveryMethod}"> selected="selected" </c:if>
														value="${paramType.key}" label="${paramType.value}">${paramType.value}</option>
												</c:forEach>
										</select> </td>
									</tr>
									<tr>
										<td class="tddark" width="200pt"><label
											class="control-label" for="designFileInpId"> Delivery
												Format </label></td>
										<td>
										<select name="report.deliveryFormat">
												<c:forEach items="${deliveryFormatList}" var="paramType"
													varStatus="sts">
													<option
														<c:if test="${paramType.key == reportCommand.report.deliveryFormat}"> selected="selected" </c:if>
														value="${paramType.key}" label="${paramType.value}">${paramType.value}</option>
												</c:forEach>
										</select>
										</td>
									</tr>
									<tr>
										<td class="tddark" width="200pt"><label
											class="control-label" for="designFileInpId"> Delivery
												Audience </label></td>
										<td><select name="report.deliveryAudience">
												<c:forEach items="${deliveryAudienceList}" var="paramType"
													varStatus="sts">
													<option
														<c:if test="${paramType.key == reportCommand.report.deliveryAudience}"> selected="selected" </c:if>
														value="${paramType.key}" label="${paramType.value}">${paramType.value}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td class="tddark" width="200pt"><label
											class="control-label" for="designFileInpId"> Status </label>
										</td>
										<td><select name="report.status">
												<c:forEach items="${statusList}" var="paramType"
													varStatus="sts">
													<option
														<c:if test="${paramType.key == reportCommand.report.status}"> selected="selected" </c:if>
														value="${paramType.key}" label="${paramType.value}">${paramType.value}</option>
												</c:forEach>
										</select></td>
									</tr>

									<c:choose>
										<c:when test="${not empty reportParameters}">
											<tr>
												<td colspan="2">Parameters</td>
											</tr>
											<tr>
												<td colspan="2">
													<table id="paramTable">
														<c:forEach items="${reportParameters}" var="rep"
															varStatus="status">
															<tr>
																<td><span>Name: </span> <input type="text"
																	name="paramName" value="${rep.name}" /></td>
																<td><span>Type: </span> <input type="text"
																	name="paramTypeId" value="${rep.typeId}" /></td>
																<td><span>Value: </span> <input type="text"
																	name="paramValue" value="${rep.value}" /></td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
										</c:when>

									</c:choose>

									<tr>
										<td colspan="2" align="right"><input type="submit"
											name="cancel" value="Cancel"> <input type="submit"
											name="save" value="Save"></td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table>
			</form:form></td>
	</tr>
</table>
