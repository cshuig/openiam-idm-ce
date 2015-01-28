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
		<title>${titleOrganizatioName} - <fmt:message key="openiam.webconsole.resource.type.menu.tree.edit" /> - ${requestScope.editableMenuTree.name}</title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/plugins/dynatree/skin-vista/ui.dynatree.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/webconsole/menu.edit.css" rel="stylesheet" type="text/css" />
		
		<link href="/openiam-ui-static/plugins/contextmenu/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />

        <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery.cookie.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/contextmenu/jquery.contextMenu-custom.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/plugins/dynatree/jquery.dynatree-1.2.2.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
        <script type="text/javascript" src="/openiam-ui-static/js/common/menutree.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/resource/menu.edit.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole/resource/menu.entitlements.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/plugins/language/language.admin.js"></script>
		<script type="text/javascript">
			OPENIAM = window.OPENIAM || {};
			OPENIAM.ENV = window.OPENIAM.ENV || {};
			OPENIAM.ENV.MenuTree = <c:choose><c:when test="${! empty requestScope.menuTree}">${requestScope.menuTree}</c:when><c:otherwise>null</c:otherwise></c:choose>;
			OPENIAM.ENV.EditableMenuTree = <c:choose><c:when test="${! empty requestScope.editableMenuTreeAsString}">${requestScope.editableMenuTreeAsString}</c:when><c:otherwise>null</c:otherwise></c:choose>;
		</script>
	</head>
	<body>
		<div id="title" class="title">
            <fmt:message key="openiam.webconsole.resource.type.menu.tree" /> ${requestScope.editableMenuTree.name}
		</div>
		<div class="frameContentDivider">
			<!--
			<div class="menu-header">
				<ul>
					<li>
						<a href="javascript:void(0)" id="expandTree" title="Expand Tree View">
							<img src="/openiam-ui-static/images/webconsole/common/expand_tree.png" alt="Collapse Tree" />
						</a>
					</li>
					<li>
						<a href="javascript:void(0);" id="collapseTree" title="Collapse Tree View">
							<img src="/openiam-ui-static/images/webconsole/common/collapse_tree.png" alt="Collapse Tree" />
						</a>
					</li>
				</ul>
			</div>
			-->
			<div id="menu-container">
				<div id="tree">
					
				</div>
			</div>
			<div style="height:20px"></div>
			<input id="menuTreeSave" type="button" value="<fmt:message key='openiam.webconsole.resource.type.menu.tree.save' />" class="redBtn"/>
			
			<div id="fixedMenuInformationContainer">
				<div id="menuInformation" style="display:none">
					<div class="title menuInfoTitle"><fmt:message key="openiam.webconsole.resource.type.menu.metadata" /></div>
					<div id="menuMetadata">
						<div>
							<label><fmt:message key="openiam.ui.common.id" />:</label>
							<span class="id"></span>
						</div>
						<div>
							<label><fmt:message key="openiam.ui.common.name" />:</label>
							<span class="name"></span>
						</div>
						<div>
							<label><fmt:message key="openiam.ui.common.URL" />:</label>
							<span class="url"></span>
						</div>
						<div>
							<label><fmt:message key="openiam.ui.common.display.name" />:</label>
							<span class="displayName"></span>
						</div>
						<div>
							<label><fmt:message key="openiam.common.icon" />:</label>
							<span class="icon"></span>
						</div>
                        <div>
                            <label><fmt:message key="openiam.ui.common.risk" />:</label>
                            <span class="risk"></span>
                        </div>
						<div>
							<label><fmt:message key="openiam.ui.common.is.public" />:</label>
							<span class="public"></span>
						</div>
						<div>
							<label><fmt:message key="openiam.ui.common.is.visible" />:</label>
							<span class="visble"></span>
						</div>
					</div>
					<div id="menuUsers" class="menuMetadata"></div>
					<div id="menuGroups" class="menuMetadata"></div>
					<div id="menuRoles" class="menuMetadata"></div>
				</div>
			</div>
		</div>
		<!-- Definition of context menu -->
		<ul id="myMenu" class="contextMenu">
			<li class="edit"><a href="#edit"><fmt:message key="openiam.common.edit" /></a></li>
			<li class="delete"><a href="#delete"><fmt:message key="openiam.ui.common.delete" /></a></li>
			<li class="addchild"><a href="#addchild"><fmt:message key="openiam.webconsole.resource.type.add.new.child.node" /></a></li>
			<li class="insertbefore"><a href="#insertbefore"><fmt:message key="openiam.webconsole.resource.type.insert.new.node.before" /></a></li>
			<li class="insertafter"><a href="#insertafter"><fmt:message key="openiam.webconsole.resource.type.insert.new.node.after" /></a></li>
			<li class="quit separator"><a href="#quit"><fmt:message key="openiam.common.quit" /></a></li>
		</ul>
		<ul id="myRootMenu" class="contextMenu">
			<li class="edit"><a href="#edit"><fmt:message key="openiam.common.edit" /></a></li>
			<li class="delete"><a href="#delete"><fmt:message key="openiam.ui.common.delete" /></a></li>
			<li class="addchild"><a href="#addchild"><fmt:message key="openiam.webconsole.resource.type.add.new.child.node" /></a></li>
			<li class="quit separator"><a href="#quit"><fmt:message key="openiam.common.quit" /></a></li>
		</ul>
		<form id="menuForm" style="display:none;overflow:hidden;">
			<table cellspacing="15">
				<thead>
					<tr>
						<th colspan="2" class="messages">
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><fmt:message key="openiam.ui.common.id" />:</td>
						<td>
							<input type="text" id="menuId" class="full rounded" disabled="disabled" autocomplete="off" />
						</td>
					</tr>
					<tr>
						<td><fmt:message key="openiam.ui.common.name" />:</td>
						<td>
							<input type="text" id="menuName" class="full rounded" autocomplete="off" />
						</td>
					</tr>
					<tr>
						<td><fmt:message key="openiam.ui.common.URL" />:</td>
						<td>
							<input type="text" id="menuURL" class="full rounded" autocomplete="off" />
						</td>
					</tr>
					<tr>
						<td><fmt:message key="openiam.ui.common.display.name" />:</td>
						<td>
							<div id="menuDisplayName">
							
							</div>
						</td>
					</tr>
                    <tr>
                        <td><fmt:message key="openiam.ui.common.risk" />:</td>
                        <td>
                            <select id="menuRisk">
                                <option value="">Select a Risk</option>
                                <c:forEach var="risk" items="${requestScope.riskList}">
                                    <option value="${risk}">${risk}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
					<tr>
						<td><fmt:message key="openiam.common.icon" />:</td>
						<td>
							<input type="text" id="menuIcon" class="full rounded" autocomplete="off" />
						</td>
					</tr>
					<tr>
						<td><fmt:message key="openiam.ui.common.is.public" />:</td>
						<td>
							<select id="menuPublic">
								<option value="false"><fmt:message key="openiam.ui.common.no" /></option>
								<option value="true" selected="selected"><fmt:message key="openiam.ui.common.yes" /></option>
							</select>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="openiam.ui.common.is.visible" />:</td>
						<td>
							<select id="menuVisible">
								<option value="true" selected="selected"><fmt:message key="openiam.ui.common.yes" /></option>
								<option value="false"><fmt:message key="openiam.ui.common.no" /></option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input id="menuSave" type="submit" value="<fmt:message key='openiam.ui.common.save' />" class="redBtn"/>
							<a id="menuCancel" class="whiteBtn" href="javascript:void(0);"><fmt:message key='openiam.ui.common.cancel' /></a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<div id="dialog"></div>
	</body>
</html>