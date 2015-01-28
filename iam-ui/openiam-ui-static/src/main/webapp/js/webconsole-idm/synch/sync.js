function onSelectSyncSource() {
    var value = $("#synchAdapter").val();
    $('#soUser').show();

    var list_lbl = $('#processRule_lbl');
    var list = $('#processRule');
    $(".common-row").show();
    $(".csv-row").hide();
    $(".ldap-row").hide();
    $(".db-row").hide();
    $(".ws-row").hide();
    list_lbl.hide();
    list.hide();
    list.val("USER");
    if (value == "RDBMS") {
        $(".common-row").show();
        $(".db-row").show();
    } else if (value == "CSV") {
        $('#soUser').hide();
        list.show();
        list_lbl.show();
        $(".csv-row").show();
    } else if (value == "LDAP" || value == "AD" ) {
        $(".ldap-row").show();
    } else if (value == "WS") {
        $(".ws-row").show();
    } else if (value == "CUSTOM") {
        $(".common-row").show();
        $(".csv-row").show();
        $(".ldap-row").show();
        $(".db-row").show();
        $(".ws-row").show();
        list.val("USER");
    }
}
function onClickUseTransformationScript()  {
    if ($("#useTransformationScript").is(':checked')) {
        $(".transform-row").show();
    } else {
        $(".transform-row").hide();
    }
}
function onClickUseSystemPath() {
    if ("CSV" != $("#synchAdapter").val()) {
        return;
    }
    if ($("#useSystemPath").is(':checked')) {
        $(".system-path").show();
        $(".file-upload").hide();
    } else {
        $(".system-path").hide();
        $(".file-upload").show();
    }
}
function checkPolicyMapBeforeTransformation()  {
    if (!$("#usePolicyMap").is(':checked') || !$("#useTransformationScript").is(':checked')) {
        $(".policy-map-before-transform-row").hide();
    } else {
        $(".policy-map-before-transform-row").show();
    }
}

function updateUI() {
    onSelectSyncSource();
    onClickUseTransformationScript();
    checkPolicyMapBeforeTransformation();
    onClickUseSystemPath();
}

$(document).ready(function() {

    updateUI();

    $("#synchAdapter").change(function() {
        onSelectSyncSource();
    });

    $("#useTransformationScript").click(function() {
        onClickUseTransformationScript();
        checkPolicyMapBeforeTransformation();
    });

    $("#usePolicyMap").click(function() {
        checkPolicyMapBeforeTransformation();
    });

    $("#useSystemPath").click(function() {
        onClickUseSystemPath();
    });

    $("#uploadNewFile").click(function() {
        $(this).parent().hide();
        $("#fileUpload").show();
    });
});