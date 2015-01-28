OPENIAM.User = window.OPENIAM.User || {};
OPENIAM.User.Attribute={
    attributeList: null,
    populate : function() {
        var $this = this;
        $.ajax({
            url : "rest/api/users/attributes",
            data : {id : OPENIAM.ENV.UserId},
            type: "GET",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                $this.attributeList = data.beans;
                var modalFields = [
                    {fieldName: "name", type:"text",label: localeManager["openiam.ui.common.attribute.name"], required : true},
                    {fieldName: "metadataId", type:"select",label: localeManager["openiam.ui.metadata.element"], required:false, items : this._elements},
                    {fieldName: "values", type:"multiText",label: localeManager["openiam.ui.common.attribute.values"], required:true}
                ];
                $("#userAttribureContainer").attributeTableEdit({
                    objectArray : $this.attributeList,
                    dialogModalFields : modalFields,
                    fieldNames : [
                    	"name", 
                    	"metadataName",
                    	"values"
                    ],
                    editEnabledField : function(bean) {
                    	if(bean.metadataName === "password") {
                			return false;	
                		} else {
                			return true;
                		}
                    },
                    readOnlyFildsOnEdit:["name"],
                    emptyMessage: localeManager["openiam.ui.user.attribute.not.found"],
                    createEnabled: (OPENIAM.ENV.UserTypeId == null) ? false:true,
                    tableTitle: (OPENIAM.ENV.UserTypeId == null) ? localeManager["openiam.ui.user.attribute.object.class.message"] :"",
                    getMetadataTypeId:function(){
                        return OPENIAM.ENV.UserTypeId;
                    },
                    OnSubmit: function(args, callback){
                        $this.preprocess(args);
                        $this.save(args, callback);
                    },
                    OnDelete: function(beanId){
                        $this.remove(beanId);
                    }
                });

            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    preprocess : function(args) {
        // preserve meta type
        if (args.targetBean
                && args.sourceBean.name == args.targetBean.name
                && !args.sourceBean.metadataId
                && args.targetBean.metadataId)
        {
            args.sourceBean.metadataId = args.targetBean.metadataId;
            args.sourceBean.metadataName = args.targetBean.metadataName;
        }
    },
    save : function(args, callback) {
        args.sourceBean.parentId = OPENIAM.ENV.UserId;
        $.ajax({
            url : "saveUserAttribute.html",
            data : JSON.stringify(args.sourceBean),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : localeManager["openiam.ui.webconsole.user.attribute.saved.success"],
                        showInterval : 2000, afterClose: function(){window.location.reload(true);}});
//                    if(callback!=null && $.isFunction(callback)){
//                        callback(args);
//                    }
                } else {
                    OPENIAM.Modal.Error({errorList : data.errorList});
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    remove : function(attributeId) {
        if(attributeId){
            $.ajax({
                url : "deleteUserAttribute.html",
                data : {id : attributeId},
                type: "POST",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        OPENIAM.Modal.Success({message : localeManager["openiam.ui.webconsole.user.attribute.delete.success"],
                            showInterval : 2000, afterClose: function(){window.location.reload(true);}});
                    } else {
                        OPENIAM.Modal.Error({errorList : data.errorList});
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
    OPENIAM.User.Attribute.populate();
});

$(window).load(function() {

});