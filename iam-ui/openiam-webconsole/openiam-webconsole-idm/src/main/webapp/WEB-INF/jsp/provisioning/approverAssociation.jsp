<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ui" %>

<ui:template>
    <jsp:body>
    	<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		
				<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter-2.0.3.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.filer.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tablesorter/js/jquery.tablesorter.pager.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalsearch/modal.search.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/persistentTable/persistent.table.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.form.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/webconsole/plugins/usersearch/user.search.results.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole-idm/approverassoc/approver.association.js"></script>
		
        <script type="text/javascript">
        	OPENIAM = window.OPENIAM || {};
        	OPENIAM.ENV = window.OPENIAM.ENV || {};
        	OPENIAM.ENV.AssocationType = "${requestScope.associationType}";
        	OPENIAM.ENV.AssociationId = "${requestScope.entityId}";
        	OPENIAM.ENV.AssociationTypeEnum = "${requestScope.assocationTypeEnum}";
        	OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
        	OPENIAM.ENV.MenuTreeAppendURL = <c:choose><c:when test="${! empty requestScope.entityId}">"id=${requestScope.entityId}"</c:when><c:otherwise>null</c:otherwise></c:choose>;
        	OPENIAM.ENV.NotifiableTypes = ${requestScope.notifiables};
        	OPENIAM.ENV.ApproverTypes = ${requestScope.approvables};
        	OPENIAM.ENV.AssociationModels = ${requestScope.associationModels};
        </script>
        <div id="title" class="title">
            <fmt:message key="openiam.ui.idm.prov.approver.assoc.title">
                <fmt:param value="${requestScope.assocationTypeEnum}"/>
                <fmt:param value="${requestScope.entityName}"/>
            </fmt:message>
        </div>
        <div class="frameContentDivider">
           <div id="container"></div>
        </div>
        <div>
        	<input id="saveAssociations" type="submit" class="redBtn" value="<fmt:message key='openiam.ui.common.save'/>" />
        </div>
        <div id="associationDialog"></div>
        <div id="editDialog"></div>
        <div id="dialog"></div>
        <div id="simpleSearch" style="display:none">
        	<div>
        		<ul class="formControls">
        			<li>
        				<input type="text" class="full rounded" maxlength="100" />
        			</li>
	            	<li>
	                	<a href="javascript:void(0)">
	                    	<input type="submit" class="redBtn" value="<fmt:message key='openiam.ui.common.search'/>" />
						</a>
					</li>
				</ul>
        	</div>
        </div>
    </jsp:body>
</ui:template>