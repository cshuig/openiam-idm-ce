OPENIAM = window.OPENIAM || {};
OPENIAM.ContentProvider = window.OPENIAM.ContentProvider || {};
OPENIAM.ContentProvider.MetaRule = {
    Load : {
        onReady : function() {
            if (OPENIAM.ENV.PatternId != null) {
                OPENIAM.ContentProvider.MetaRule.Meta.load();
            }
        }
    },
    Common : {
        load : function(args) {
            var that = args.target;
            var inputelements = [];
            var addBtn = document.createElement("input");
            $(addBtn).attr("type", "submit");
            $(addBtn).attr("value", args.buttonTitle);
            addBtn.className = "redBtn";
            addBtn.id = "addBtn";
            inputelements.push("");
            inputelements.push(addBtn);

            $("#providerDataContainer").entitlemetnsTable({
                columnHeaders : args.columns,
                columnsMap : args.columnsMap,
                ajaxURL : args.ajaxURL,
                entityUrl : "",
                entityType : args.entityType,
                entityURLIdentifierParamName : "id",
                requestParamIdName : "patternId",
                requestParamIdValue : OPENIAM.ENV.PatternId,
                pageSize : 100,
                hasEditButton : args.hasEditButton,
                deleteOptions : {
                	warningMessage : localeManager["contentprovider.meta.delete"],
                	onDelete : function(bean) {
	                    that.remove(bean);
	                }
                },
                onEdit : function(bean) {
                    that.edit(bean);
                },
                emptyResultsText : localeManager["contentprovider.meta.empty.meta"],
                theadInputElements : inputelements,
                onAppendDone : function() {

                    var submit = this.find("#addBtn");
                    submit.click(function() {
                        that.create();
                    });
                }
            });
        }
    },
    Meta : {
        load : function() {
            OPENIAM.ContentProvider.MetaRule.Common.load({
                columns : [ localeManager["openiam.ui.common.matadate"], localeManager["openiam.ui.common.actions"] ],
                columnsMap : [ "name" ],
                ajaxURL : "getMetaDataForPattern.html",
                buttonTitle : localeManager["contentprovider.entitlements.create"],
                hasEditButton : true,
                target : this
            });
        },
        create : function() {
            window.location.href = "newPatternMetaData.html?patternId=" + OPENIAM.ENV.PatternId;
        },
        save : function(bean) {
        },
        edit : function(bean) {
            window.location.href = "editPatternMetaData.html?id=" + bean.id + "&patternId=" + OPENIAM.ENV.PatternId;
        },
        remove : function(bean) {
            $.ajax({
                url : "deletePatternMetaData.html",
                data : {
                    id : bean.id
                },
                type : "POST",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    if (data.status == 200) {
                        window.location.reload(true);
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
    }
};

$(document).ready(function() {

});

$(window).load(function() {
    OPENIAM.ContentProvider.MetaRule.Load.onReady();
});