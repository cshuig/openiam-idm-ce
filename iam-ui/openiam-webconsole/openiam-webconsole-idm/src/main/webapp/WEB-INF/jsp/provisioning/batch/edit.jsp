<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.webconsole.idm.batch.task.title" /></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/batch/batch.task.edit.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.TaskId = <c:choose><c:when test="${! empty requestScope.batchTask.id}">"${requestScope.batchTask.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.batchTask.id}">"id=${requestScope.batchTask.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.Text = {
            DeleteWarn : localeManager["openiam.ui.webconsole.idm.batch.task.delete.warning.message"]
        };
        OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
    </script>
</head>
<body>
<div id="title" class="title">
    <c:choose>
        <c:when test="${! empty requestScope.batchTask.id}">
            <fmt:message key="openiam.ui.webconsole.idm.batch.task.title.edit"/> ${requestScope.batchTask.name}
        </c:when>
        <c:otherwise>
            <fmt:message key="openiam.ui.webconsole.idm.batch.task.title.create"/>
        </c:otherwise>
    </c:choose>
</div>
<div class="frameContentDivider">
    <form id="theForm" method="post">
        <table cellpadding="8px" align="center" class="fieldset">
            <tbody>
            <c:if test="${! empty requestScope.batchTask.id}">
                <tr>
                    <td><label><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.id"/></label></td>
                    <td>
                        <input type="text" disabled="disabled" class="full rounded" value="${requestScope.batchTask.id}" />
                    </td>
                </tr>
            </c:if>
            <tr>
                <td>
                    <label class="required"><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.name"/></label>
                </td>
                <td>
                    <input type="text" id="taskName" name="taskName" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.name" />'
                           value="${requestScope.batchTask.name}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.isEnabled"/></label>
                </td>
                <td class="_input_tiptip" title="Is this task enabled?">
                    <input id="isEnabled" autocomplete="off" type="radio" name="isPublic" value="true" <c:if test="${requestScope.batchTask.enabled eq true}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.yes"/>
                    <input id="isDisabled" autocomplete="off" type="radio" name="isPublic" value="false" <c:if test="${requestScope.batchTask.enabled eq false}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.no"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="required"><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.execTime"/></label>
                </td>
                <td>
                    <select id="executionType" class="rounded">
                        <option value=""><fmt:message key="openiam.ui.webconsole.idm.batch.task.select.execTime"/></option>
                        <option value="cron"><fmt:message key="openiam.ui.webconsole.idm.batch.task.select.cronJob"/></option>
                        <option value="date"><fmt:message key="openiam.ui.webconsole.idm.batch.task.select.execOnceOnDate"/></option>
                    </select>
                </td>
            </tr>
            <tr style="display:none">
                <td>
                    <label class="required"><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.cronExpession"/></label>
                </td>
                <td>
                    <input type="text" id="cronExpression" name="cronExpression" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.cronExpession"/>'
                           value="${requestScope.batchTask.cronExpression}"/>
                </td>
            </tr>
            <tr style="display:none">
                <td>
                    <label class="required"><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.runOn"/></label>
                </td>
                <td>
                    <input type="text" id="runOnAsString" name="runOnAsString" class="full rounded date _input_tiptip" readonly="readonly"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.runOn"/>' style="margin-right: 10px;"
                           value="${requestScope.batchTask.runOnAsString}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="required"><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.execScript"/></label>
                </td>
                <td>
                    <select id="executionScript" class="rounded">
                        <option value=""><fmt:message key="openiam.ui.webconsole.idm.batch.task.select.execScript"/></option>
                        <option value="groovy"><fmt:message key="openiam.ui.webconsole.idm.batch.task.select.groovyScript"/></option>
                        <option value="springBean"><fmt:message key="openiam.ui.webconsole.idm.batch.task.select.execSpringBean"/></option>
                    </select>
                </td>
            </tr>
            <tr style="display:none">
                <td>
                    <label class="required"><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.groovyScriptUrl"/></label>
                </td>
                <td>
                    <input type="text" id="taskUrl" name="taskUrl" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.groovyScriptUrl"/>'
                           value="${requestScope.batchTask.taskUrl}"/>
                </td>
            </tr>
            <tr style="display:none">
                <td>
                    <label class="required"><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.springBeanName"/></label>
                </td>
                <td>
                    <input type="text" id="springBean" name="springBean" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.springBeanName"/>'
                           value="${requestScope.batchTask.springBean}"/>
                </td>
            </tr>
            <tr style="display:none">
                <td>
                    <label class="required"><fmt:message key="openiam.ui.webconsole.idm.batch.task.label.springBeanMethod"/></label>
                </td>
                <td>
                    <input type="text" id="springBeanMethod" name="springBeanMethod" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.springBeanMethod"/>'
                           value="${requestScope.batchTask.springBeanMethod}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label><spring:message code="openiam.ui.webconsole.idm.batch.task.label.param" arguments="1"/></label>
                </td>
                <td>
                    <input type="text" id="param1" name="param1" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.param"/>'
                           value="${requestScope.batchTask.param1}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label><spring:message code="openiam.ui.webconsole.idm.batch.task.label.param" arguments="2"/></label>
                </td>
                <td>
                    <input type="text" id="param2" name="param2" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.param"/>'
                           value="${requestScope.batchTask.param2}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label><spring:message code="openiam.ui.webconsole.idm.batch.task.label.param" arguments="3"/></label>
                </td>
                <td>
                    <input type="text" id="param3" name="param3" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.param"/>'
                           value="${requestScope.batchTask.param3}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label><spring:message code="openiam.ui.webconsole.idm.batch.task.label.param" arguments="4"/></label>
                </td>
                <td>
                    <input type="text" id="param4" name="param4" class="full rounded _input_tiptip"
                           title='<fmt:message key="openiam.ui.webconsole.idm.batch.task.title.param"/>'
                           value="${requestScope.batchTask.param4}"/>
                </td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="2">
                    <ul class="formControls">
                        <li class="leftBtn">
                            <a id="saveBtn" href="javascript:void(0)"><input type="submit" class="redBtn" value='<spring:message code="openiam.ui.common.save" />' /></a>
                        </li>
                        <li class="leftBtn">
                            <a href="batchTaskSearch.html" class="whiteBtn"><fmt:message key="openiam.ui.common.cancel"/></a>
                        </li>
                        <c:if test="${! empty requestScope.batchTask.id}">
                        	<li class="leftBtn">
                        		<a id="execute" href="javascript:void(0)"><input type="submit" class="redBtn" value='<spring:message code="openiam.ui.common.execute" />' /></a>
                        	</li>
                            <li class="rightBtn">
                                <a id="deleteResoruce" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.common.delete"/></a>
                            </li>
                        </c:if>
                    </ul>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
</body>
</html>