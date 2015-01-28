OPENIAM = window.OPENIAM || {};
OPENIAM.Languages = {
    init : function() {
        this.populatePage();
        $("#saveBtn").click(function() {
            OPENIAM.Languages.save();
            return false;
        });
        $("#isDefault").on('change', function(ev) {
            if ($("#isDefault").is(':checked')) {
                ev.stopPropagation();
                OPENIAM.Modal.Warn({
                    message : localeManager["openiam.ui.webconsole.languages.sure.default"],
                    buttons : true,
                    OK : {
                        text : localeManager["openiam.ui.common.yes"],
                        onClick : function() {
                            OPENIAM.Modal.Close();
                            $("#isDefault").prop('checked', 'checked');
                            return false;
                        }
                    },
                    Cancel : {
                        text : localeManager["openiam.ui.common.no"],
                        onClick : function() {
                            OPENIAM.Modal.Close();
                            $("#isDefault").prop('checked', false);
                            return false;
                        }
                    }
                });
            }

        });
    },

    populatePage : function() {
        var obj = OPENIAM.ENV.Bean;
        var viewObj = OPENIAM.ENV.LocaleView;
        var selectedLocales = obj.locales;
        $("#title").text(
                localeManager["openiam.ui.webconsole.languages.language"]
                        + (obj.name ? obj.name : localeManager["openiam.ui.webconsole.languages.new.language"]));
        $("#displayNameMap").languageAdmin({
            bean : OPENIAM.ENV.Bean,
            beanKey : "displayNameMap",
        });
        $("#active").prop("checked", obj.isUsed);

        $("#code").val(obj.languageCode);
        $("#isDefault").prop("checked", obj.isDefault);
        OPENIAM.Languages.populateLocales({
            locales : selectedLocales,
        });
    },
    createLi : function(curSelLocale) {
        var li = document.createElement("li");
        var input = document.createElement("input");

        $(input).prop("type", "text");
        $(input).prop("value", curSelLocale.locale);
        $(input).addClass('rounded');
        $(input).on('change', function() {
            var url = OPENIAM.Language.getFlagURL($(this).val());
            if (url) {
                $(img).css('display', 'inline');
                $(img).prop('src', OPENIAM.Language.getFlagURL($(this).val()));
            } else {
                $(img).css('display', 'none');
            }
        });
        var img = document.createElement("img");
        var a = document.createElement("a");
        $(a).text(" " + localeManager["openiam.ui.webconsole.languages.delete.locale"]);
        $(a).prop('href', 'javascript:void(0);');
        $(a).on('click', function() {
            $(a).parent().addClass('locale-delete');
        });
        var flagurl = OPENIAM.Language.getFlagURL(curSelLocale.locale);
        if (flagurl) {
            $(img).css('display', 'inline');
            $(img).prop('src', flagurl);
        } else {
            $(img).css('display', 'none');
        }
        $(li).attr('localeId', curSelLocale.id);
        $(li).attr('languageId', curSelLocale.languageId);
        $(li).addClass('locale-row');
        $(li).append(input, img, a);

        return li;
    },
    populateLocales : function(options) {
        var selectedLocales = options.locales;
        var ul = document.createElement("ul");
        Object.keys(selectedLocales).map(function(v) {
            $(ul).append(OPENIAM.Languages.createLi(selectedLocales[v]));
        });
        // selectedLocales.forEach(function(curSelLocale) {
        // $(ul).append(OPENIAM.Languages.createLi(curSelLocale));
        // });
        $(ul).css("list-style-type", "none");
        $(ul).css("padding-left", "0px");
        $(ul).css("margin-bottom", "6px");
        var addli = document.createElement("li");
        var add = document.createElement("a");
        $(addli).append(add);
        $(add).text(localeManager["openiam.ui.webconsole.languages.add.locale"]);
        $(add).prop('href', 'javascript:void(0);');
        $(add).on('click', function() {
            $(add).parent().parent().prepend(OPENIAM.Languages.createLi(""));
        });
        $(ul).append(addli);
        $("#localesMap").append(ul);
    },
    toJSON : function() {
        var obj = OPENIAM.ENV.Bean;
        obj.displayNameMap = $("#displayNameMap").languageAdmin("getMap");
        obj.isUsed = $("#active").is(":checked");
        obj.isDefault = $("#isDefault").is(":checked");
        obj.languageCode = $("#code").val();
        if (!obj.id) {
            obj.name = $("#name").val();
        }
        obj.locales = OPENIAM.Languages.toJSONLocale();

    },
    toJSONLocale : function() {
        var map = {};
        $(".locale-row").each(function(index, val) {
            var loc = OPENIAM.Languages.getLocale(val);
            if (loc) {
                map[loc.locale] = {
                    id : loc.id,
                    locale : loc.locale,
                    languageId : loc.languageId
                };
            }
        });
        return map;
    },
    getLocale : function(li) {
        if ($(li).hasClass('locale-delete'))
            return null;
        locale = {};
        locale.id = $(li).attr("localeId");
        locale.languageId = $(li).attr("languageId");
        locale.locale = $(li).children("input").val();
        if (!locale.locale)
            return null;
        return locale;
    },
    save : function() {
        this.toJSON();
        $.ajax({
            url : "languageEdit.html",
            data : JSON.stringify(OPENIAM.ENV.Bean),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 2000,
                        onIntervalClose : function() {
                            window.location.href = data.redirectURL;
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    }
};

$(document).ready(function() {
});

$(window).load(function() {
    OPENIAM.Languages.init();
});