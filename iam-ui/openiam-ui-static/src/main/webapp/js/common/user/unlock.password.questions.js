OPENIAM = window.OPENIAM || {};

$(document).ready(function () {
    $("td#error").hide();
    $('.hideShowPassword-field').hidePassword(true);
    $('#unlockUserForm').submit(function () {
        $("td#error").hide();
        var data = new Object();
        data.userId = $("#userId").val();
        data.token = $("#token").val();
        data.answers = [];
        $("input.answer").each(function () {
            data.answers.push({questionId: $(this).attr("oiamQuestionId"), answerValue: $(this).val()});
        });
        $.ajax({
            url: "unlockPasswordQuestions.html",
            "data": JSON.stringify(data),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                        window.location.href = data.redirectURL;
                    } else {
                        window.location.href = "/selfservice"
                    }
                } else {
                    $("td#error").show();
                    $("p.error").empty();
                    if (data.errorList != null && data.errorList != undefined && data.errorList.length > 0) {
                        var text = "";
                        for (var i = 0; i < data.errorList.length; i++) {
                            text += localeManager[data.errorList[i].message];
                        }
                        $("p.error").text(text);
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $("td#error").show();
                $("p.error").empty();
                $("p.error").text(localeManager["openiam.ui.internal.error"]);
            }

        });
        return false;
    });
});

$(document).load(function () {

});