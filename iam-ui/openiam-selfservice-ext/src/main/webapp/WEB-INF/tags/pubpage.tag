<%@tag description="Public page template" pageEncoding="UTF-8"%>
<%@attribute name="head" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="openiam" uri="http://www.openiam.com/tags/openiam" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${titleOrganizatioName} - ${requestScope.pageTitle}</title>
    <link href="/openiam-ui-static/js/common/jquery/css/smoothness/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tiptip/tipTip.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/plugins/tablesorter/themes/yui/style.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/css/common/style.client.css" rel="stylesheet" type="text/css" />
    <link href="/openiam-ui-static/css/common/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/openiam-ui-static/css/common/hideShowPassword.css" rel="stylesheet" type="text/css" />
    <openiam:overrideCSS />
    <script type="text/javascript" src="/openiam-ui-static/_dynamic/openiamResourceBundle.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/jquery/jquery.cookies.2.2.0.min.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/json/json.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/plugins/tiptip/jquery.tipTip.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/plugins/uiTemplate/ui.template.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/openiam.common.js"></script>
    <script type="text/javascript" src="/openiam-ui-static/js/common/hideShowPassword.min.js"></script>

    <script type="text/javascript">
        OPENIAM = window.OPENIAM || {};
        OPENIAM.ENV = window.OPENIAM.ENV || {};
        OPENIAM.ENV.DateFormatDP = "${requestScope.dateFormatDP}";
    </script>
    <jsp:invoke fragment="head"/>
</head>
<body>
<div id="top" class="standalone">
    <div class="wrapper">
        <div id="header">
            <div id="logo">
                <a href="${requestScope.currentURI}" class="logo slogo">Openiam</a>
            </div>
        </div>
    </div>
</div>
<div id="middle">
    <div class="wrapper">
        <div id="submenu" style="display:none"></div>
        <div id="leftshadow"></div>
        <div id="rightshadow"></div>
        <div id="content">
            <div id="title" class="title">
                ${requestScope.pageTitle}
            </div>
            <jsp:doBody />
        </div>
    </div>
</div>
<div id="bottom">
    <div class="wrapper">
        <div id="footer">
            <jsp:invoke fragment="footer"/>
            <div id="copy">${footerCopyright}</div>
            <div id="footnav">
                ${footerNav}
            </div>
        </div>
    </div>
</div>
</body>
</html>