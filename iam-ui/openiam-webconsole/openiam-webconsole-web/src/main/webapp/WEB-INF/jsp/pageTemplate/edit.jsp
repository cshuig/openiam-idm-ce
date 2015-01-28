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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.page.template.edit.page.title"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
	<openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
	<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
	<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/pageTemplate/page.edit.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/pageTemplate/entitlements.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>


    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.TemplateId = <c:choose><c:when test="${! empty requestScope.template.id}">"${requestScope.template.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.template.id}">"id=${requestScope.template.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.SuccessMessage = <c:choose><c:when test="${! empty successMessage}">"${successMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.FieldTypeList=null;
        <c:if test="${! empty requestScope.typeList}">
            OPENIAM.ENV.FieldTypeList=[];
            <c:forEach var="type" items="${requestScope.typeList}">
                OPENIAM.ENV.FieldTypeList.push({id:'${type.id}',name:'${type.description}'});
            </c:forEach>
        </c:if>
        OPENIAM.ENV.LanguageList=null;
        <c:if test="${! empty requestScope.languageList}">
            OPENIAM.ENV.LanguageList=[];
            <c:forEach var="lang" items="${requestScope.languageList}">
                OPENIAM.ENV.LanguageList.push({languageCode:'${lang.languageCode}',name:'${lang.name}', languageId: '${lang.id}'});
            </c:forEach>
        </c:if>

    </script>
</head>
<body>
<div id="title" class="title">
    <c:choose>
        <c:when test="${! empty requestScope.template.id}">
            <fmt:message key="openiam.ui.page.template.edit.title"/>: ${requestScope.template.name}
        </c:when>
        <c:otherwise>
            <fmt:message key="openiam.ui.page.template.new.title"/>
        </c:otherwise>
    </c:choose>
</div>
<div class="frameContentDivider">
<form id="pageTemplateForm" method="post">
    <table cellpadding="8px" align="center" class="fieldset">
        <tbody>
            <c:if test="${! empty requestScope.template.id}">
                <tr>
                    <td>Page Template ID</td>
                    <td>
                        <input id="templateId" name="templateId" type="text" disabled="disabled" class="full rounded" value="${requestScope.template.id}" />
                    </td>
                </tr>
            </c:if>
            <c:if test="${! empty requestScope.template.resourceId}">
                <tr>
                    <td>
                        <label><fmt:message key="openiam.ui.common.linked.to.resource"/>:</label>
                    </td>
                    <td>
                        <input type="hidden" id="resourceId" value="${requestScope.template.resourceId}" />
                        <a href="/webconsole/editResource.html?id=${requestScope.template.resourceId}">${requestScope.template.resourceName}</a>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td>
                    <label class="required"><fmt:message key="openiam.ui.page.template.name"/></label>
                </td>
                <td>
                    <input type="text" id="templateName" name="templateName" class="full rounded _input_tiptip"
                           title="The name to help us identify this template" value="${requestScope.template.name}" />
                </td>
            </tr>
            <tr>
                <td>
                    <label class="required"><fmt:message key="openiam.ui.page.template.type"/></label>
                </td>
                <td>
                    <select id="templateType">
                        <option value=""><fmt:message key="openiam.ui.page.template.type.select"/></option>
                        <c:forEach var="tpElement" items="${requestScope.templateTypeList}">
                            <option value="${tpElement.id}" <c:if test="${requestScope.template.templateTypeId eq tpElement.id}">selected="selected"</c:if>>${tpElement.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label><fmt:message key="openiam.ui.common.is.public"/>?</label>
                </td>
                <td>
                    <input id="isPublicOn" autocomplete="off" type="radio" name="isPublic" value="true" <c:if test="${requestScope.template.isPublic eq true}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.yes" />
                    <input id="isPublicOff" autocomplete="off" type="radio" name="isPublic" value="false" <c:if test="${requestScope.template.isPublic eq false}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.no" />
                </td>
            </tr>
        </tbody>
    </table>


    <c:if test="${! empty requestScope.template and ! empty requestScope.template.id}">
        <div>
            <div class="title">
                <fmt:message key="openiam.ui.page.template.field"/>:
            </div>
            <div id="templateFieldWarn" class="info center"><fmt:message key="openiam.ui.page.template.field.order"/></div>
            <div class="frameContentDivider">
                <div id="templateFieldContainer"></div>
            </div>
        </div>
        <div>
            <div class="title">
                <fmt:message key="openiam.ui.page.template.custom.field"/>:
            </div>
            <div id="customFieldWarn" class="info center"><fmt:message key="openiam.ui.page.template.custom.field.order"/></div>
            <div class="frameContentDivider">
                <div id="customFieldContainer"></div>
            </div>
        </div>
        <div>
            <div class="title">
                <fmt:message key="openiam.ui.page.template.uri.patterns"/>
            </div>
            <div class="frameContentDivider">
                <div id="uriPatternContainer"></div>
            </div>
        </div>
    </c:if>

    <div style="margin-top:20px;">
        <ul class="formControls">
            <li class="leftBtn">
                <a href="javascript:void(0)"><input type="submit" class="redBtn" value='<fmt:message key="openiam.ui.button.save"/>' /></a>
            </li>
            <li class="leftBtn">
                <a href="pageTemplates.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel"/></a>
            </li>
            <c:if test="${! empty requestScope.template and ! empty requestScope.template.id}">
                <li class="rightBtn">
                    <a id="deleteTemplate" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete"/></a>
                </li>
            </c:if>
        </ul>
    </div>
</form>
</div>
<div id="editDialog"></div>
</body>
</html>