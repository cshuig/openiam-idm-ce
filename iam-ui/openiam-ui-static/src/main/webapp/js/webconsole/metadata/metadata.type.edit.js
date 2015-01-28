OPENIAM = window.OPENIAM || {};
OPENIAM.MetadataType = {
    init : function() {
    	this.populatePage();
        $("#saveBtn").click(function() {
            OPENIAM.MetadataType.save();
            return false;
        });

        $("#deleteBtn").click(function() {
            OPENIAM.MetadataType.deleteType();
            return false;
        });
    },
    populatePage : function() {
    	var obj = OPENIAM.ENV.MetadataType;
    	$("#description").val(obj.description);
    	$("#grouping").val(obj.grouping);
    	$("#active").prop("checked", obj.active)
    	$("#syncManagedSys").prop("checked", obj.syncManagedSys);
    	$("#binary").prop("checked", obj.binary);
    	$("#displayNameMap").languageAdmin({ bean : OPENIAM.ENV.MetadataType, beanKey : "displayNameMap"});
    	
    	if(obj.sensitive) {
    		$("#description").prop("disabled", true);
    		$("#grouping").prop("disabled", true);
    		$("#active").prop("disabled", true);
    		$("#syncManagedSys").prop("disabled", true);
    		$("#binary").prop("disabled", true);
    	}
    },
    toJSON : function() {
    	var obj = OPENIAM.ENV.MetadataType;
		obj.description = $("#description").val();
		obj.grouping = $("#grouping").val();
		obj.grouping = (obj.grouping == "") ? null : obj.grouping;
		obj.active = $("#active").is(":checked");
		obj.syncManagedSys = $("#syncManagedSys").is(":checked");
		obj.binary = $("#binary").is(":checked");
		obj.displayNameMap = $("#displayNameMap").languageAdmin("getMap");
    },
    save : function() {
    	this.toJSON();
        $.ajax({
            url : "metaDataTypeEdit.html",
            data : JSON.stringify(OPENIAM.ENV.MetadataType),
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
    },
    deleteType : function() {
        $.ajax({
            url : "metadataTypeDelete.html",
            data : {
                id : OPENIAM.ENV.MetadataType.id
            },
            type : "POST",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 2000,
                        onIntervalClose : function() {
                            window.location.href = "metaDataTypeSearch.html";
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

OPENIAM.Metadata = {
    _getInputs : function(args) {
        var select = $(document.createElement("select"));
        select.attr("id", "metadataType");
        select.append("<option value=\"\">" + localeManager['contentprovider.provider.meta.data.type.search'] + "</option>");
        select.hide();
//        if (OPENIAM.ENV.metadataTypes != null) {
//            for (var i = 0; i < OPENIAM.ENV.metadataTypes.length; i++) {
//                var type = OPENIAM.ENV.metadataTypes[i];
//                var option = $(document.createElement("option"));
//                option.val(type.id);
//                option.text(type.name);
//                if (args && args.initialMetadataType && args.initialMetadataType == type.id) {
//                    option.attr("selected", "selected");
//                }
//                select.append(option);
//            }
//        }
        if (OPENIAM.ENV.metadataTypeId) {
            var option = $(document.createElement("option"));
            option.attr("selected", "selected");
            option.val(OPENIAM.ENV.metadataTypeId);
            option.text(OPENIAM.ENV.metadataTypeId);
            select.append(option);
            select.hide();
        }

        var myInput = document.createElement("input");
        $(myInput).attr("type", "text");
        myInput.className = "full rounded";
        $(myInput).attr("placeholder", localeManager["metadata.search.name.placeholder"]);
        $(myInput).attr("autocomplete", "off");
        myInput.id = "searchInput";
        if (args && args.initialSearchValue) {
            $(myInput).val(args.initialSearchValue);
        }
        OPENIAM.FN.applyPlaceholder(myInput);
        var mySearch = document.createElement("input");
        $(mySearch).attr("type", "button");
        $(mySearch).attr("value", localeManager['openiam.ui.idm.synch.synchlist.searchBtn']);
        mySearch.className = "redBtn";
        mySearch.id = "search";

        var inputs = [];
        inputs.push(select);
        inputs.push(myInput);
        inputs.push(mySearch);
        return inputs;
        return inputs;
    },
    init : function(args) {
        var inputelements = this._getInputs(args);

        $("#entitlementsContainer").entitlemetnsTable(
            {
                columnHeaders : [ localeManager["openiam.ui.common.name"], localeManager["openiam.ui.idm.synch.table.col.type"],
                    localeManager["openiam.ui.common.actions"] ],
                columnsMap : [ "name", "typeName" ],
                hasEditButton : true,
                onEdit : function(bean) {
                    window.location.href = "metaDataEdit.html?id=" + bean.id;
                },
                theadInputElements : inputelements,
                ajaxURL : "rest/api/metadata/element/search",
                entityUrl : "metaDataEdit.html",
                entityURLIdentifierParamName : "id",
                pageSize : 10,
                emptyResultsText : localeManager["metadata.search.empty"],
                getAdditionalDataRequestObject : function() {
                    var obj = {
                        name : $("#searchInput").val(),
                        type : (OPENIAM.ENV.metadataTypeId != null) ? OPENIAM.ENV.metadataTypeId : $("#metadataType").val(),
                        returnRootsOnMenuRequest : true,
                    };
                    return obj;
                },
                onAppendDone : function() {
                    this.find("#search").click(function() {
                        OPENIAM.Metadata.reinit();
                    });
                }
            });
    },
    reinit : function() {
        OPENIAM.Metadata.init({
            initialMetadataType : $("#metadataType").val(),
            initialSearchValue : $("#searchInput").val()
        });
    }
};

$(document).ready(function() {
    OPENIAM.MetadataType.init();
    if (OPENIAM.ENV.metadataTypeId) {
        OPENIAM.Metadata.init();
    }
});

$(window).load(function() {

});