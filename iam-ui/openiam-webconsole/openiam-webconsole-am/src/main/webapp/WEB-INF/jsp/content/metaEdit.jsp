
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${titleOrganizatioName}-<fmt:message key="contentprovider.provider.metaedit.head" /></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole-am/cp.meta.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
		<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
	    <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/multiselect/js/multiselect.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/contentprovider/metaEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/placeholder/jquery.placeholder.js"></script>

		<script type="text/javascript">
        	OPENIAM = window.OPENIAM || {};
        	OPENIAM.ENV = window.OPENIAM.ENV || {};
        	OPENIAM.ENV.ContextPath = "${pageContext.request.contextPath}";
        	OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        	OPENIAM.ENV.MenuTreeAppendURL = "id=${requestScope.uriPattern.id}&providerId=${requestScope.uriPattern.contentProviderId}";
        	OPENIAM.ENV.ProviderId = <c:choose><c:when test="${! empty requestScope.uriPattern.contentProviderId}">"${requestScope.uriPattern.contentProviderId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        	OPENIAM.ENV.PatternId = <c:choose><c:when test="${! empty requestScope.uriPattern.id}">"${requestScope.uriPattern.id}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        	OPENIAM.ENV.MetaId = <c:choose><c:when test="${! empty requestScope.uriPatternMetaId}">"${requestScope.uriPatternMetaId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
    	</script>
	</head>
	<body>
      	<div id="title" class="title">
      		<c:choose>
				<c:when test="${! empty requestScope.uriPatternMetaId}">
					<fmt:message key="contentprovider.provider.edit.meta.title" /> : ${requestScope.uriPattern.pattern}
        		</c:when>
				<c:otherwise>
					<fmt:message key="contentprovider.provider.new.meta.title" /> : ${requestScope.uriPattern.pattern}
        		</c:otherwise>
            </c:choose>
      </div>
      <div class="frameContentDivider">
            <table cellpadding="8px">
                  <tbody>
                        <tr>
							<td>
                              	<label class="required">
                              		<fmt:message key="contentprovider.provider.meta.data.name" />
                              	</label>
							</td>
							<td>
								<input type="text" id="name" name="name" class="full rounded _input_tiptip" autocomplete="off" title="<fmt:message key="contentprovider.provider.meta.data.name.tip" />" />
							</td>
                        </tr>
                        <tr>
							<td>
								<label class="required">
									<fmt:message key="contentprovider.provider.meta.data.type" />
								</label>
							</td>
                            <td>
                            	<select id="metaTypeId" name="metaTypeId" class="select _input_tiptip" autocomplete="off" title="<fmt:message key="contentprovider.provider.meta.data.type.tip"/>"
                                    <option value="">
                                    	<fmt:message key="contentprovider.provider.meta.data.type.search"/>
                                    </option>
                                    <c:forEach var="tp" items="${requestScope.metaTypeList}">
										<option value="${tp.id}">${tp.name}</option>
									</c:forEach>
                              	</select>
							</td>
                        </tr>
                  </tbody>
            </table>
            <div class="title">
                  <fmt:message key="contentprovider.provider.meta.data.type.attrs" />
            </div>
            <div id="properties">
            
            </div>
            <div style="margin-top: 20px;">
                  <ul class="formControls">
                        <li>
                        	<a id="saveBtn" href="javascript:void(0)" class="redBtn">
                        		<fmt:message key="openiam.ui.button.save" />
                        	</a>
                        </li>
                        <li>
                        	<a href="editProviderPattern.html?id=${requestScope.uriPattern.id}&providerId=${requestScope.uriPattern.contentProviderId}" class="whiteBtn">
                        		<fmt:message key="openiam.ui.button.cancel" />
                        	</a>
                        </li>
                        <c:if test="${! empty requestScope.uriPatternMetaId}">
                              <li class="rightBtn">
                              	<a id="deleteMetaData" href="javascript:void(0);" class="redBtn">
                              		<fmt:message key="openiam.ui.button.delete" />
                              	</a>
                              </li>
                        </c:if>
                  </ul>
            </div>
      </div>
      <div id="editDialog"></div>
</body>
</html>