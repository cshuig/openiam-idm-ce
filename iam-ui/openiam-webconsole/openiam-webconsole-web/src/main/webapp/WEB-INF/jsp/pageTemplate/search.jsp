<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - <fmt:message key="openiam.ui.page.template.search.page.title"/></title>
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <openiam:overrideCSS />

    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>

    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/webconsole/pageTemplate/page.search.js"></script>
    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.totalPages = ${requestScope.totalPages};
        OPENIAM.ENV.page = ${requestScope.page};
        OPENIAM.ENV.count = ${requestScope.count};
        OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
    </script>
</head>
<body>
<form id="searchForm" method="get" action="pageTemplates.html">
    <div id="title" class="title">
        <fmt:message key="openiam.ui.page.template.search.title"/>:
    </div>
    <div class="frameContentDivider">
        <table cellspacing="1" id="tableOne" class="yui" width="100%">
            <thead>
                <tr>
                    <td class="filter">
                        <fmt:message key="openiam.ui.page.template.search"/>: <input id="templateName" name="templateName" value="${requestScope.templateName}" maxlength="30" size="30" type="text" />
                    </td>
                    <td class="filter" colspan="1">
                        <button  class="redBtn" type="submit" id="searchButton"><fmt:message key="openiam.ui.button.search"/></button>
                        <button class="whiteBtn" id="filterClearOne"><fmt:message key="openiam.ui.button.cancel"/></button>
                    </td>
                </tr>
                <tr>
                    <th colspan="2"><fmt:message key="openiam.ui.page.template.name.column"/></th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty requestScope.resultList or fn:length(requestScope.resultList) eq 0}">
                        <tr>
                            <td colspan="2">
                                <fmt:message key="openiam.ui.page.template.not.found"/>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="template" items="${requestScope.resultList}">
                            <tr entityId="${template.id}">
                                <td colspan="2">
                                        ${template.name}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
            <tfoot>
            <tr id="pagerOne">
                <td colspan="7" style="border-right: solid 3px #7f7f7f;">
                    <img src="/openiam-ui-static/plugins/tablesorter/img/first.png" class="first"/>
                    <img src="/openiam-ui-static/plugins/tablesorter/img/prev.png" class="prev"/>
                    <input type="text" class="pagedisplay"/>
                    <img src="/openiam-ui-static/plugins/tablesorter/img/next.png" class="next"/>
                    <img src="/openiam-ui-static/plugins/tablesorter/img/last.png" class="last"/>
                    <select class="pagesize" name="size" id="size">
                        <option <c:if test="${empty requestScope.size or requestScope.size eq 10}">selected="selected"</c:if> value="10">10</option>
                        <option <c:if test="${! empty requestScope.size and requestScope.size eq 20}">selected="selected"</c:if> value="20">20</option>
                        <option <c:if test="${! empty requestScope.size and requestScope.size eq 30}">selected="selected"</c:if> value="30">30</option>
                        <option <c:if test="${! empty requestScope.size and requestScope.size eq 40}">selected="selected"</c:if> value="40">40</option>
                    </select>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>
    <input type="hidden" id="page" name="page" value="${requestScope.page}" />
</form>
</body>
</html>