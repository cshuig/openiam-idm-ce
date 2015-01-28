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
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.custom.fields.edit.custom.field" /></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/css/webconsole/custom.field.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
    <openiam:overrideCSS />
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/customFields/field.edit.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>

    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.FieldId = <c:choose><c:when test="${! empty requestScope.field.id}">"${requestScope.field.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.field.id}">"id=${requestScope.field.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.ErrorMessage = <c:choose><c:when test="${! empty errorMessage}">"${errorMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.SuccessMessage = <c:choose><c:when test="${! empty successMessage}">"${successMessage}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        OPENIAM.ENV.LanguageList=null;
        <c:if test="${! empty requestScope.languageList}">
            OPENIAM.ENV.LanguageList=[];
            <c:forEach var="lang" items="${requestScope.languageList}">
                OPENIAM.ENV.LanguageList.push({languageCode:'${lang.languageCode}',name:'${lang.name}', id: ${lang.id}});
            </c:forEach>
        </c:if>

    </script>
</head>
<body>
<div id="title" class="title">
    <c:choose>
        <c:when test="${! empty requestScope.field.id}">
            <fmt:message key="openiam.ui.custom.fields.edit.custom.field" />: ${requestScope.field.name}
        </c:when>
        <c:otherwise>
            <fmt:message key="openiam.ui.custom.fields.custom.fields.create.new" />
        </c:otherwise>
    </c:choose>
</div>
<div class="frameContentDivider">
    <form id="customFieldForm" method="post">
        <table cellpadding="8px" align="center" class="fieldset">
            <tbody>
                <c:if test="${! empty requestScope.field.id}">
                    <tr>
                        <td><fmt:message key="openiam.ui.custom.fields.custom.field.id" /></td>
                        <td>
                            <input id="fieldId" name="fieldId" type="text" disabled="disabled" class="full rounded" value="${requestScope.field.id}" />
                        </td>
                    </tr>
                </c:if>
                <c:if test="${! empty requestScope.field.resourceId}">
                    <tr>
                        <td>
                            <label><fmt:message key="openiam.ui.common.linked.to.resource" />:</label>
                        </td>
                        <td>
                            <input type="hidden" id="resourceId" value="${requestScope.field.resourceId}" />
                            <a href="/webconsole/editResource.html?id=${requestScope.field.resourceId}">${requestScope.field.resourceName}</a>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${! empty requestScope.field.pageTemplates}">
                    <tr>
                        <td>
                            <label><fmt:message key="openiam.ui.custom.fields.linked.page.templates" />:</label>
                        </td>
                        <td>
                            <ul>
                                <c:forEach var="template" items="${requestScope.field.pageTemplates}">
                                    <li>
                                        <a href="/webconsole/editPageTemplate.html?id=${template.id}">${template.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                        <label class="required"><fmt:message key="openiam.ui.custom.fields.field.name" /></label>
                    </td>
                    <td>
                        <input type="text" id="name" name="name" class="full rounded _input_tiptip"
                               title="<fmt:message key='openiam.ui.custom.fields.field.name.title' />" value="${requestScope.field.name}" />
                    </td>
                </tr>
                <tr>
                    <td style="vertical-align:top;">
                        <div>
                            <label class="required"><fmt:message key="openiam.ui.common.display.name" /></label>
                        </div>
                    </td>
                    <td class="languageMap">
                        <c:forEach var="lang" items="${requestScope.languageList}">
                            <div>
                                <span class="languageLable">${lang.name}:</span>
                                <span class="languageInput">
                                    <input type="text" id="${lang.languageCode}_displayName" name="${lang.languageCode}_displayName" languageCode="${lang.languageCode}" languageId="${lang.id}"
                                           class="full rounded _input_tiptip"
                                           title="<fmt:message key="openiam.ui.custom.fields.name.show.ui.form" /> ${lang.name}"
                                           <c:if test="${!empty requestScope.field.displayNameLanguageMap
                                                         and !empty requestScope.field.displayNameLanguageMap[lang.id]}">
                                                value="${requestScope.field.displayNameLanguageMap[lang.id].value}"
                                           </c:if> />
                                </span>
                                <div style="clear:both;"/>
                            </div>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label><fmt:message key="openiam.ui.custom.fields.is.required" />?</label>
                    </td>
                    <td>
                        <input id="isRequiredOn" autocomplete="off" type="radio" name="isRequired" value="true" <c:if test="${requestScope.field.isRequired eq true}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.yes" />
                        <input id="isRequiredOff" autocomplete="off" type="radio" name="isRequired" value="false" <c:if test="${requestScope.field.isRequired eq false}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.no" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label><fmt:message key="openiam.ui.common.is.public" />?</label>
                    </td>
                    <td>
                        <input id="isPublicOn" autocomplete="off" type="radio" name="isPublic" value="true" <c:if test="${requestScope.field.isPublic eq true}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.yes" />
                        <input id="isPublicOff" autocomplete="off" type="radio" name="isPublic" value="false" <c:if test="${requestScope.field.isPublic eq false}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.no" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label><fmt:message key="openiam.ui.custom.fields.is.editable" />?</label>
                    </td>
                    <td>
                        <input id="isEditableOn" autocomplete="off" type="radio" name="isEditable" value="true" <c:if test="${requestScope.field.isEditable eq true}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.yes" />
                        <input id="isEditableOff" autocomplete="off" type="radio" name="isEditable" value="false" <c:if test="${requestScope.field.isEditable eq false}">checked="checked"</c:if> /> <fmt:message key="openiam.ui.common.no" />
                    </td>
                </tr>
                <tr>
                   <td>
                       <label class="required"><fmt:message key="openiam.ui.custom.fields.custom.fields.type" /></label>
                   </td>
                   <c:choose>
                       <c:when test="${! empty requestScope.field.id}">
                            <td>
                                <c:forEach var="type" items="${requestScope.typeList}">
                                    <c:if test="${! empty requestScope.field.typeId and requestScope.field.typeId eq type.id}">
                                        <input type="hidden" id="typeId" value="${requestScope.field.typeId}" />
                                        <input type="text" disabled="disabled" class="full rounded" value="${type.description}" />
                                    </c:if>
                                </c:forEach>
                            </td>
                       </c:when>
                       <c:otherwise>
                           <td>
                               <select id="typeId" name="typeId" class="select _input_tiptip"
                                       autocomplete="off" title="The Type of Custom Field.">
                                   <option value=""><fmt:message key="openiam.ui.common.field.type.select.custom" /></option>
                                   <c:forEach var="type" items="${requestScope.typeList}">
                                       <option <c:if test="${! empty requestScope.field.typeId and requestScope.field.typeId eq type.id}">selected="selected"</c:if> value="${type.id}">${type.displayName}</option>
                                   </c:forEach>
                               </select>

                           </td>
                       </c:otherwise>
                   </c:choose>
                </tr>
            </tbody>
        </table>

        <c:if test="${(! empty requestScope.field.typeId and requestScope.field.typeId eq 'TEXT')
                       or (empty requestScope.field.typeId)}">
            <div id="TEXT_Params"
                 <c:if test="${empty requestScope.field.typeId}">style="display:none;"</c:if>>
                <div class="title">
                    <fmt:message key="openiam.ui.custom.fields.default.value" />
                </div>
                <table cellpadding="8px" align="center" >
                    <tbody>
                    <tr>
                        <td style="vertical-align:top;">
                            <div>
                                <label ><fmt:message key="openiam.ui.custom.fields.default.value" /></label>
                            </div>
                        </td>
                        <td>
                            <c:forEach var="lang" items="${requestScope.languageList}">
                                <div>
                                    <span class="languageLable">${lang.name}:</span>
                                                <span class="languageInput">
                                                    <input type="text" id="${lang.languageCode}_defaultValue" name="${lang.languageCode}_defaultValue"
                                                           languageCode="${lang.languageCode}"  languageId="${lang.id}" class="full rounded _input_tiptip"
                                                           title="The default value for textbox in ${lang.name}"
                                                            <c:if test="${!empty requestScope.field.defaultValueLanguageMap
                                                                 and !empty requestScope.field.defaultValueLanguageMap[lang.id]}">
                                                                value="${requestScope.field.defaultValueLanguageMap[lang.id].value}"
                                                            </c:if> />
                                                </span>
                                </div>
                            </c:forEach>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${(! empty requestScope.field.typeId and requestScope.field.typeId eq 'TEXTAREA')
                       or (empty requestScope.field.typeId)}">
            <div id="TEXTAREA_Params"
                 <c:if test="${empty requestScope.field.typeId}">style="display:none;"</c:if>>
                <div class="title">
                    <fmt:message key="openiam.ui.custom.fields.default.value" />
                </div>
                <table cellpadding="8px" align="center" >
                    <tbody>
                    <tr>
                        <td style="vertical-align:top;">
                            <div>
                                <label ><fmt:message key="openiam.ui.custom.fields.default.value" /></label>
                            </div>
                        </td>
                        <td>
                            <c:forEach var="lang" items="${requestScope.languageList}">
                                <div>
                                    <span class="languageLable">${lang.name}:</span>
                                                <span class="languageInput">
                                                	<c:set var="textareaValue"></c:set>
                                                	<c:if test="${!empty requestScope.field.defaultValueLanguageMap
                                                                             and !empty requestScope.field.defaultValueLanguageMap[lang.id]}">
														<c:set var="textareaValue">${requestScope.field.defaultValueLanguageMap[lang.id].value}</c:set>
													</c:if>
                                                    <textarea id="${lang.languageCode}_defaultValue" name="${lang.languageCode}_defaultValue" languageCode="${lang.languageCode}" languageId="${lang.id}">${textareaValue}</textarea>
                                                </span>
                                </div>
                            </c:forEach>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${(! empty requestScope.field.typeId and (requestScope.field.typeId eq 'SELECT' or requestScope.field.typeId eq 'MULTI_SELECT' or requestScope.field.typeId eq 'CHECKBOX' or requestScope.field.typeId eq 'RADIO'))
                                 or (empty requestScope.field.typeId)}">
            <div id="SELECT_Params" <c:if test="${empty requestScope.field.typeId}">style="display:none;"</c:if>>
                <div class="title">
                    <fmt:message key="openiam.ui.custom.fields.valid.values" />:
                </div>
                <div class="info center"><fmt:message key="openiam.ui.custom.fields.change.order" /></div>
                <input type="hidden" id="staticDefaultValue" value="${requestScope.field.staticDefaultValue}" />
                <table cellspacing="1" id="optionsList" class="yui" width="100%">
                    <thead>
                    <tr>
                        <td class="filter" colspan="3">
                        </td>
                        <td class="filter" colspan="1">
                            <button class="redBtn" id="addBtn"><fmt:message key="openiam.ui.button.create" /></button >
                        </td>
                    </tr>
                    <tr>
                        <th><fmt:message key="openiam.ui.common.value" /></th>
                        <th><fmt:message key="openiam.ui.common.display.name" /></th>
                        <th><fmt:message key="openiam.ui.common.is.default" /></th>
                        <th><fmt:message key="openiam.ui.common.actions" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty requestScope.field.validValues or fn:length(requestScope.field.validValues) eq 0}">
                            <tr class="empty">
                                <td colspan="4" class="empty">
                                    <fmt:message key="openiam.ui.custom.fields.options.no.found" />
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="opt" items="${requestScope.field.validValues}">
                                <tr entityId="${opt.id}" displayOrder="${opt.displayOrder}"<c:if test="${!empty requestScope.field.staticDefaultValue and requestScope.field.staticDefaultValue eq opt.uiValue}">
                                    isDefault="true"
                                </c:if>>
                                    <td class="uiValue">${opt.uiValue}</td>
                                    <td class="displayName">
                                        <ul>
                                            <c:forEach var="lang" items="${requestScope.languageList}">
                                                <c:if test="${!empty opt.languageMap and !empty opt.languageMap[lang.id]}">
                                                    <li languageId="${lang.id}" languageCode="${lang.languageCode}" >
                                                        <span class="languageName" >${lang.name}:</span>
                                                        <span class="languageValue">${opt.languageMap[lang.id].value}</span>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </td>
                                    <td class="defaultFlag">
                                        <c:if test="${!empty requestScope.field.staticDefaultValue and requestScope.field.staticDefaultValue eq opt.uiValue}">
                                            <img src="/openiam-ui-static/images/common/check.png"/>
                                        </c:if>
                                    </td>
                                    <td class="action">
                                        <a href="javascript:void(0);" entityid="${opt.id}" class="editBtn">
                                            <img src="/openiam-ui-static/images/common/edit.png">
                                        </a>
                                        <a href="javascript:void(0);" entityid="${opt.id}" class="deleteBtn">
                                            <img src="/openiam-ui-static/images/common/delete.png">
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </c:if>

        <div style="margin-top:20px;">
            <ul class="formControls">
                <li class="leftBtn">
                    <a href="javascript:void(0)"><input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.button.save' />" /></a>
                </li>
                <li class="leftBtn">
                    <a href="customFields.html" class="whiteBtn"><fmt:message key="openiam.ui.button.cancel" /></a>
                </li>
                <c:if test="${! empty requestScope.field and ! empty requestScope.field.id}">
                    <li class="rightBtn">
                        <a id="deleteField" href="javascript:void(0);" class="redBtn"><fmt:message key="openiam.ui.button.delete" /></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </form>
</div>
<div id="editDialog"></div>
</body>
</html>