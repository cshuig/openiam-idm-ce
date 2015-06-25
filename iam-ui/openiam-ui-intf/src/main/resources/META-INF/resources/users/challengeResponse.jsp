<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.challenge.response" /></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/selfservice/challenge.response.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/common/hideShowPassword.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/hideShowPassword.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/challengeresponse/challenge.response.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.userId}">"id=${requestScope.userId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
            OPENIAM.ENV.SecureAnswers = <c:choose><c:when test="${requestScope.secureAnswers}">1</c:when><c:otherwise>0</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.idp.answer.response.questions.unlock.account" />
		</div>
		<div class="frameContentDivider">
            <input name="postbackUrl" id="postbackUrl" type="hidden" value="${requestScope.postbackUrl}" />
            <c:if test="${requestScope.secureAnswers}">
                <input name="unchangedValue" id="unchangedValue" type="hidden" value="${requestScope.unchangedValue}" />
            </c:if>
			<table cellpadding="8px" align="center" class="fieldset">
				<tbody>
					<c:forEach var="questionModel" items="${requestScope.modelList}">
						<tr class="question <c:if test="${questionModel.markedAsDeleted}">marked-as-deleted _input_tiptip</c:if>" 
											<c:if test="${questionModel.markedAsDeleted}">title="This Question will be marked as deleted"</c:if>
											<c:if test="${! empty questionModel.answer}">answerId="${questionModel.answer.id}"</c:if> >
							<td>
								<table>
									<tr>
										<td>
											<select name="identityQuestion" class="select rounded" autocomplete="off">
												<option value=""><fmt:message key='openiam.ui.selfservice.challenge.response.noquestiontaken' /></option>
												<c:forEach var="question" items="${questionModel.questionList}">
													<option value="${question.id}" <c:if test="${! empty questionModel.answer and questionModel.answer.questionId eq question.id}">selected="selected"</c:if> >${question.displayName}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
                                    <c:if test="${!requestScope.secureAnswers}">
                                        <tr>
                                            <td>
                                                <c:if test="${!empty questionModel.answer}">
                                                    <input name="identityAnswer" answerId="${questionModel.answer.id}" type="password" class="full rounded hideShowPassword-field" value="${questionModel.answer.questionAnswer}" autocomplete="off" />
                                                </c:if>
                                                <c:if test="${empty questionModel.answer}">
                                                    <input name="identityAnswer" type="password" class="full rounded hideShowPassword-field" autocomplete="off" />
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${requestScope.secureAnswers}">
                                        <c:if test="${!empty questionModel.answer}">
                                            <tr>
                                                <td>
                                                    <input name="identityAnswer" answerId="${questionModel.answer.id}" type="password" class="full rounded mainAnswer" value="${requestScope.unchangedValue}" placeholder="<fmt:message key='openiam.idp.enter.new.answer' />" autocomplete="off" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <input name="confirmAnswer" answerId="${questionModel.answer.id}" type="password" class="full rounded confirmAnswer" value="${requestScope.unchangedValue}" placeholder="<fmt:message key='openiam.idp.confirm.new.answer' />" autocomplete="off" />
                                                </td>
                                            </tr>
                                        </c:if>

                                        <c:if test="${empty questionModel.answer}">
                                            <tr>
                                                <td>
                                                    <input name="identityAnswer" type="password" class="full rounded mainAnswer" placeholder="<fmt:message key='openiam.idp.enter.new.answer' />" autocomplete="off" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <input name="confirmAnswer" type="password" class="full rounded confirmAnswer" placeholder="<fmt:message key='openiam.idp.confirm.new.answer' />" autocomplete="off" />
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:if>
								</table>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
	                <tr>
	                    <td colspan="2">
	                        <ul class="formControls">
                                <c:if test="${not requestScope.readOnly}">
                                    <li class="leftBtn">
                                        <c:set var="cancelUrl" value="${requestScope.auth? '/idp/logout.html': '/selfservice/myInfo.html'}" />
                                        <a href="${cancelUrl}" class="whiteBtn"><spring:message code="openiam.ui.common.cancel" /></a>
                                    </li>
                                    <li class="rightBtn">
                                        <a href="javascript:void(0)">
                                            <input id="save" type="submit" class="redBtn" value='<spring:message code="openiam.ui.common.save" />' />
                                        </a>
                                    </li>
                                </c:if>
	                        </ul>
	                    </td>
	                </tr>
	            </tfoot>
			</table>
		</div>
	</body>
</html>