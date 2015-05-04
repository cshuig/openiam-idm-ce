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
		<title>${titleOrganizatioName}-<fmt:message key="openiam.ui.authentication.debug.title" /></title>
		<link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/css/common/entitlements.css" rel="stylesheet" type="text/css" />
		<link href="/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css" rel="stylesheet" type="text/css" />
		<openiam:overrideCSS />
		
		<script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
		<script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
		
		<script type="text/javascript" src="/openiam-ui-static/js/webconsole-am/auth.debug.js"></script>
		
		<script type="text/javascript">
		    OPENIAM = window.OPENIAM || {};
		    OPENIAM.ENV = window.OPENIAM.ENV || {};
		</script>
	</head>
	<body>
		<div id="title" class="title">
			<fmt:message key="openiam.ui.authentication.debug.title" />
		</div>   
		
		<div class="frameContentDivider">
			<div id="title" class="title">
				<fmt:message key="openiam.ui.authentication.login.debug" />
			</div>
			<table cellpadding="8px" align="center" class="fieldset">
				<tbody>
					<tr>
						<td>
							<label>
								<fmt:message key="openiam.ui.user.login" />
							</label>
						</td>
						<td>
							<input autocomplete="off" type="text" id="login" name="login" class="full rounded _input_tiptip" />
						</td>
					</tr>
					<tr>
						<td>
							<label>
								<fmt:message key="openiam.ui.user.password" />
							</label>
						</td>
						<td>
							<input autocomplete="off" type="password" id="password" name="password" class="full rounded _input_tiptip" />
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2">
							<ul class="formControls">
								<li>
									<a href="javascript:void(0)"> 
										<input id="loginDebug" type="submit" class="redBtn" value="<fmt:message key="openiam.ui.common.test" />" />
									</a>
								</li>
							</ul>
						</td>
	               	</tr>
	               	<tr>
	               		<td colspan="2">
	               			<textarea style="width:600px; height:300px" autocomplete="off" id="loginDebugResponse" name="loginDebugResponse" class="full rounded _input_tiptip" placeholder="<fmt:message key="openiam.ui.authentication.debug.response.here" />"></textarea>
	               		</td>
	               	</tr>
				</tfoot>
			</table>
		</div>
		
		<div class="frameContentDivider">
			<div id="title" class="title">
				<fmt:message key="openiam.ui.authentication.renew.token.debug" />
			</div>
			<table cellpadding="8px" align="center" class="fieldset">
				<tbody>
					<tr>
						<td>
							<label>
								<fmt:message key="openiam.ui.authentication.token" />
							</label>
						</td>
						<td>
							<textarea autocomplete="off" id="token" name="token" class="full rounded _input_tiptip"></textarea>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2">
							<ul class="formControls">
								<li>
									<a href="javascript:void(0)"> 
										<input id="renewTokenDebug" type="submit" class="redBtn" value="<fmt:message key="openiam.ui.common.test" />" />
									</a>
								</li>
							</ul>
						</td>
	               	</tr>
	               	<tr>
	               		<td colspan="2">
	               			<textarea autocomplete="off" style="width:600px; height:600px" id="renewTokenDebugRespone" name="renewTokenDebugResponse" class="full rounded _input_tiptip" placeholder="<fmt:message key="openiam.ui.authentication.debug.response.here" />"></textarea>
	               		</td>
	               	</tr>
				</tfoot>
			</table>
		</div>
	</body>
</html>