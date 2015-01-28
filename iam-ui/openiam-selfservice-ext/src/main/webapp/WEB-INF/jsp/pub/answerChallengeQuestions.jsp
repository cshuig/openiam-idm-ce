<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="pageTitle" scope="request">
    <spring:message code="accountClaim.challengeQuestions.page.title" />
</c:set>

<t:pubpage>
    <jsp:attribute name="head">
        <style type="text/css">
            select, input[type="text"] {
                width: 400px;
            }
            input[type="password"].hideShowPassword-field,
            input[type="text"].hideShowPassword-field {
                width: 345px;
            }
        </style>
        <script type="text/javascript">
            OPENIAM = window.OPENIAM || {};

            OPENIAM.ChallengeResponse = {
                lists : {
                    questions : [],
                    init : function() {
                        var that = this;
                        var list = $("select[name='identityQuestion']").first();
                        list.find("option").each(function(){
                            var opt = this;
                            if (opt.value) {
                                that.questions.push({val: opt.value, text: opt.text});
                            }
                        });

                        $("select[name='identityQuestion']").change(function() {
                            that.rebuild();
                        });

                        that.rebuild();
                    },
                    rebuild : function() {
                        var that = this;
                        var selected = [];
                        $("select[name='identityQuestion'] ").not(".question.marked-as-deleted").each(function(){
                            var val = $(this).val();
                            if (val) {
                                selected.push(val);
                            }
                        });
                        $("select[name='identityQuestion'] ").not(".question.marked-as-deleted").each(function(){
                            var sel = $(this);
                            var val = sel.val();
                            sel.find("option[value!='']").remove();
                            $.each(that.questions, function(key, item) {
                                if (item.val == val || $.inArray(item.val, selected) == -1 ) {
                                    sel.append("<option value='"+ item.val +"'>"+ item.text +"</option>");
                                }
                            });
                            sel.val(val);
                        });
                    }
                }
            };

            $(document).ready(function() {
                OPENIAM.ChallengeResponse.lists.init();
                $('.hideShowPassword-field').hidePassword(true);
            });

            $(window).load(function() {
            });
        </script>
    </jsp:attribute>
    <jsp:body>

        <div class="frameContentDivider">
            <form action="${flowExecutionUrl}" method="post">
                <table cellpadding="8px" align="center">
                    <tbody>
                    <c:if test="${flowRequestContext.messageContext.hasErrorMessages()}">
                        <tr>
                            <td colspan="2">
                                <div class="alert alert-error">
                                    <c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
                                        <p class="error">${message.text}</p>
                                    </c:forEach>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach begin="0" end="${challengeQuestionsNum-1}" var="i">
                        <tr class="question">
                            <td>
                                <table>
                                    <tr>
                                        <td>
                                            <select name="identityQuestion" class="select" autocomplete="off">
                                                <option value=""><spring:message code="accountClaim.challengeQuestions.selectQuestion"/></option>
                                                <c:forEach items="${challengeQuestions}" var="question" varStatus="index">
                                                    <option value="${question.key}" <c:if test="${! empty identityQuestion and identityQuestion[i] eq question.key}">selected="selected"</c:if> >${question.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input name="identityAnswer" type="password" class="full rounded hideShowPassword-field" />
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="2">
                            <ul class="formControls">
                                <li class="rightBtn">
                                    <input class="redBtn" name="_eventId_next" id="_eventId_next" type="submit" value='<spring:message code="accountClaim.common.next"/>'>
                                </li>
                                <li class="leftBtn">
                                    <input class="whiteBtn" name="_eventId_cancel" type="submit" value='<spring:message code="accountClaim.common.cancel"/>'>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </form>
        </div>

    </jsp:body>
</t:pubpage>