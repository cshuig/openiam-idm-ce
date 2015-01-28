OPENIAM = window.OPENIAM || {};

OPENIAM.ChallengeResponse = {
    lists : {
        questions : [],
        init : function() {
            var that = this;
            var list = $("select[name='identityQuestion']").first();
            list.find("option").each(function(){
                var opt = this;
                if (opt.value) {
                    that.questions.push({val: opt.value, text: opt.text});
                }
            });

            $("select[name='identityQuestion']").change(function() {
                that.rebuild();
            });

            that.rebuild();
        },
        rebuild : function() {
            var that = this;
            var selected = [];
            $("select[name='identityQuestion'] ").not(".question.marked-as-deleted").each(function(){
                var val = $(this).val();
                if (val) {
                    selected.push(val);
                }
            });
            $("select[name='identityQuestion'] ").not(".question.marked-as-deleted").each(function(){
                var sel = $(this);
                var val = sel.val();
                sel.find("option[value!='']").remove();
                $.each(that.questions, function(key, item) {
                    if (item.val == val || $.inArray(item.val, selected) == -1 ) {
                        sel.append("<option value='"+ item.val +"'>"+ item.text +"</option>");
                    }
                });
                sel.val(val);
            });
        }
    },
	save : function() {
		$.ajax({
			url : "challengeResponse.html",
			data : JSON.stringify(this.toJSON()),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
							window.location.href = data.redirectURL;
						} else {
							if(OPENIAM.API != null && OPENIAM.API != undefined && parent != self && $.isFunction(OPENIAM.API.goToFirstMenu)) {
								OPENIAM.API.goToFirstMenu();
							} else {
								window.location.reload(true);
							}
						}
					}});
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	toJSON : function() {
		var obj = { answerList : [], deleteList : [], unchangedValue : $("#unchangedValue").val(), postbackUrl : $("#postbackUrl").val() };
		$(".question").each(function() {
			if($(this).hasClass("marked-as-deleted")) {
				obj.deleteList.push($(this).attr("answerId"));
			} else {
				var question = $(this).find("select[name='identityQuestion']");
				var answer = $(this).find("input[name='identityAnswer']");
				var confirm = $(this).find("input[name='confirmAnswer']");
				var answerObj = {
					questionId : question.val(),
					questionAnswer : answer.val(),
					confirmAnswer : confirm.val(),
					id : answer.attr("answerId")
				};
				obj.answerList.push(answerObj);
			}
		});
		return obj;
	}
};

$(document).ready(function() {
	$("#save").click(function() {
		OPENIAM.ChallengeResponse.save();
		return false;
	});

	$(".question.marked-as-deleted").find("input").attr("disabled", true);
	$(".question.marked-as-deleted").find("select").attr("disabled", true);

    $('.hideShowPassword-field').hidePassword(true);

    OPENIAM.ChallengeResponse.lists.init();
});

$(window).load(function() {
});