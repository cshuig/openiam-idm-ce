OPENIAM = window.OPENIAM || {};
OPENIAM.ContentProvider = window.OPENIAM.ContentProvider || {};
OPENIAM.ContentProvider.Entitlements = {
    Load : {
        onReady : function() {

        },
        onLoad : function() {
            if (OPENIAM.ENV.ProviderId != null) {
                OPENIAM.ContentProvider.Entitlements.Servers.load();
                OPENIAM.ContentProvider.Entitlements.Patterns.load();
            }
        }
    },
    Common : {
        load : function(args) {
            var that = args.target;
            var inputelements = [];
            var addBtn = $(document.createElement("input")).attr("type", "submit").attr("value", args.buttonTitle).addClass("redBtn").attr("id", "addBtn");
            switch (that.getEntityType()) {
            case "servers":
                inputelements.push("");
                break;
            case "patterns":
                inputelements.push("");
                inputelements.push(
                	$(document.createElement("input")).attr("type", "submit").attr("value", localeManager["openiam.ui.content.provider.add.default.pattners"]).addClass("redBtn").attr("id", "addDefaultPatterns")
                );
                break;
            default:
                break;
            }
            inputelements.push(addBtn);

            $(that.getRootElement()).entitlemetnsTable({
                columnHeaders : args.columns,
                columnsMap : args.columnsMap,
                ajaxURL : args.ajaxURL,
                entityUrl : "",
                entityType : args.entityType,
                entityURLIdentifierParamName : "id",
                requestParamIdName : "providerId",
                requestParamIdValue : OPENIAM.ENV.ProviderId,
                pageSize : 100,
                deleteOptions : {
                	onDelete : function(bean) {
                		that.remove(bean);
                	},
                	warningMessage : args.deleteWarningMessage
                },
                hasEditButton : args.hasEditButton,
                onEmptyResults : args.onEmptyResults,
                onNonEmptyResults : args.onNonEmptyResults,
                onEdit : function(bean) {
                    that.edit(bean);
                },
                emptyResultsText : localeManager["contentprovider.entitlements.EmptyChildren"],
                theadInputElements : inputelements,
                onAppendDone : function() {
                    var submit = this.find("#addBtn");
                    submit.click(function() {
                        that.create();
                    });
                    this.find("#addDefaultPatterns").click(function() {
                    	OPENIAM.ContentProvider.Entitlements.Patterns.addDefaultPatterns();
                    	return false;
                    });
                }
            });
        },
        saveOrRemove : function(args) {
            var data = args.entity;
            var that = args.target;
            data["providerId"] = OPENIAM.ENV.ProviderId;
            $.ajax({
                url : args.url,
                data : JSON.stringify(data),
                type : "POST",
                dataType : "json",
                contentType : "application/json",
                success : function(data, textStatus, jqXHR) {
                    if (data.status == 200) {
                        if (that.getEntityType() == "servers") {
                            try {
                                $("#editDialog").modalEdit("hide");
                            } catch (e) {
                                // not initilaized - not really an error
                            }
                        }
                        that.load(0);
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
    },
    Servers : {
        getEntityType : function() {
            return "servers";
        },
        getRootElement : function() {
            return "#applicationServerContainer";
        },
        load : function() {
            OPENIAM.ContentProvider.Entitlements.Common.load({
                columns : [ localeManager["contentprovider.entitlements.server.url"], localeManager["openiam.ui.common.actions"] ],
                columnsMap : [ "serverURL" ],
                ajaxURL : "getServersForProvider.html",
                buttonTitle : "Create",
                deleteWarningMessage : localeManager["contentprovider.entitlements.server.delete"],
                hasEditButton : true,
                target : this,
                onEmptyResults : function() {
                    $("#serverWarning").show();
                },
                onNonEmptyResults : function() {
                    $("#serverWarning").hide();
                }
            });
        },
        create : function() {
            var bean = {};
            this._modal(bean);
        },
        save : function(bean) {
            OPENIAM.ContentProvider.Entitlements.Common.saveOrRemove({
                entity : bean,
                url : "saveProviderServer.html",
                target : this
            });
        },
        edit : function(bean) {
            this._modal(bean);
        },
        _modal : function(bean) {
            var that = this;
            $("#editDialog").modalEdit({
                fields : [ {
                    fieldName : "id",
                    type : "hidden",
                    label : ""
                }, {
                    fieldName : "serverURL",
                    type : "text",
                    label : localeManager["contentprovider.entitlements.server.url"],
                    required : true
                } ],
                dialogTitle : localeManager["contentprovider.entitlements.server.edit"],
                onSubmit : function(bean) {
                    that.save(bean);
                }
            });
            $("#editDialog").modalEdit("show", bean);
        },
        remove : function(bean) {
            OPENIAM.ContentProvider.Entitlements.Common.saveOrRemove({
                entity : bean,
                url : "deleteProviderServer.html",
                target : this
			});
        }
    },
    Patterns : {
        getEntityType : function() {
            return "patterns";
        },
        getRootElement : function() {
            return "#uriPatternContainer";
        },
        addDefaultPatterns : function() {
        	OPENIAM.Modal.Warn({ 
				message : localeManager["openiam.ui.content.provider.add.default.pattners.warning"], 
				buttons : true, 
				OK : {
					text : localeManager["openiam.ui.common.yes"],
					onClick : function() {
						$.ajax({
							url : "createDefaultURIPatterns.html",
							data : {id : OPENIAM.ENV.ContentProvider.id},
							type: "POST",
							dataType : "json",
							success : function(data, textStatus, jqXHR) {
								if(data.status == 200) {
									OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
										if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
											window.location.href = data.redirectURL;
										} else {
											window.location.reload(true);
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
					}
				},
				Cancel : {
					text : localeManager["openiam.ui.common.cancel"],
					onClick : function() {
						OPENIAM.Modal.Close();
					}
				}
			});
        },
        load : function() {
            OPENIAM.ContentProvider.Entitlements.Common.load({
                columns : [ localeManager["contentprovider.entitlements.pattern"], localeManager["contentprovider.entitlements.is.auth.delete"],
                        localeManager["openiam.ui.common.actions"] ],
                columnsMap : [ "pattern", "isPublic" ],
                ajaxURL : "getPatternsForProvider.html",
                buttonTitle : localeManager["contentprovider.entitlements.create"],
                deleteWarningMessage : localeManager["contentprovider.entitlements.uri.delete"],
                hasEditButton : true,
                target : this
            });
        },
        create : function() {
            window.location.href = "newProviderPattern.html?providerId=" + OPENIAM.ENV.ProviderId;
        },
        save : function(bean) {
        },
        edit : function(bean) {
            window.location.href = "editProviderPattern.html?id=" + bean.id + "&providerId=" + OPENIAM.ENV.ProviderId;
        },
        remove : function(bean) {
            OPENIAM.ContentProvider.Entitlements.Common.saveOrRemove({
                entity : bean,
                url : "deleteProviderPattern.html",
                target : this
            });
        }
    }
};

$(document).ready(function() {
    OPENIAM.ContentProvider.Entitlements.Load.onReady();
    OPENIAM.ContentProvider.Entitlements.Load.onLoad();
});

$(window).load(function() {
});