OPENIAM = window.OPENIAM || {};
OPENIAM.UserContacts = {
    Load : {
        onReady : function() {
            switch(OPENIAM.ENV.ContactType) {
                case "emails":
                    OPENIAM.UserContacts.Emails.load();
                    break;
                case "addresses":
                    OPENIAM.UserContacts.Addresses.load();
                    break;
                case "phones":
                    OPENIAM.UserContacts.Phones.load();
                    break;
                default:
                    break;
            }
        }
    },
    Common : {
        load : function(args) {
            var that = args.target;
            var inputelements = [];
            var addBtn = document.createElement("input"); $(addBtn).attr("type", "submit"); $(addBtn).attr("value", args.buttonTitle); addBtn.className = "redBtn"; addBtn.id = "addBtn";
            inputelements.push("");
            inputelements.push("");
            inputelements.push("");
            inputelements.push("");
            inputelements.push(addBtn);

            $("#contactUserContainer").entitlemetnsTable({
                columnHeaders : args.columns,
                columnsMap : args.columnsMap,
                ajaxURL : args.ajaxURL,
                entityUrl : "",
                entityType : args.entityType,
                entityURLIdentifierParamName : "id",
                requestParamIdName : "id",
                requestParamIdValue : OPENIAM.ENV.UserId,
                pageSize : 10,
                deleteOptions : {
                	onDelete : function(bean) {
	                    that.remove(bean);
	                }
                },
                hasEditButton : args.hasEditButton,
                onEdit : function(bean) {
                    that.edit(bean);
                },
                emptyResultsText : args.emptyResultsText,
                theadInputElements : inputelements,
                onAppendDone : function() {

                    $("#editDialog").modalEdit({
                        fields: args.editFields,
                        dialogTitle: args.dialogTitle,
                        onSubmit: function(bean){
                            that.save(bean);
                        }
                    });

                    var submit = this.find("#addBtn");
                    submit.click(function() {
                        var bean = {};
                        bean.active=true;
                        $("#editDialog").modalEdit("show", bean);
                    });
                }
            });
        },
        saveOrRemove : function(args) {
            var data = args.entity;
            data["userId"] = OPENIAM.ENV.UserId;
            $.ajax({
                url : args.url,
                data : JSON.stringify(data),
                type: "POST",
                dataType : "json",
                contentType: "application/json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        $("#editDialog").modalEdit("hide");
                        args.target.load(0);
                    } else {
                        OPENIAM.Modal.Error({errorList : data.errorList});
                    }
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        },
        isAdded: function(args){
            var data = args.entity;
            var that = args.target;
            var found=false;

            $("#contactUserContainer").find("tr[entityId]").each(function(){
                var curBean = $(this).data("entity");

                if(curBean.id!=data.id && curBean.typeId==data.typeId){
                    found=true;
                    return false;
                }
            });
            return found;
        }
    },
    Emails : {
        load : function() {
            OPENIAM.UserContacts.Common.load({
                columns : [
                    localeManager["openiam.ui.common.type"],
                    localeManager["openiam.ui.common.email.address"],
                    localeManager["openiam.ui.common.is.default"],
                    localeManager["openiam.ui.common.is.active"],
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["type", "email","default","active"],
                ajaxURL : "getEmailsForUser.html",
                buttonTitle : localeManager["openiam.ui.button.add.email"],
                emptyResultsText:localeManager["openiam.ui.user.contact.email.empty"],
                hasEditButton : true,
                target : this,
                editFields:[{fieldName: "id", type:"hidden",label:""},
                            {fieldName: "typeId", itemText:"type", type:"select", items:OPENIAM.ENV.TypeList, label:localeManager["openiam.ui.common.type"], required:true},
                            {fieldName: "email", type:"text",label:localeManager["openiam.ui.common.email.address"], required:true},
                            {fieldName: "description", type:"text",label:localeManager["openiam.ui.common.description"]},
                            {fieldName: "default", type:"checkbox",label:localeManager["openiam.ui.common.is.default"]},
                            {fieldName: "active", type:"checkbox",label:localeManager["openiam.ui.common.is.active"]}],
                dialogTitle:localeManager["openiam.ui.selfservice.ui.template.edit.email"]
            });
        },
        save : function(bean) {

            if(!OPENIAM.UserContacts.Common.isAdded({target : this, entity : bean})){
                OPENIAM.UserContacts.Common.saveOrRemove({
                    entity : bean,
                    url : "saveOrRemoveUserEmail.html",
                    target : this
                });
            } else{
                OPENIAM.Modal.Error(localeManager["openiam.ui.common.email.type.added"]);
            }
        },
        edit : function(bean){
            $("#editDialog").modalEdit("show", bean);
        },
        remove : function(bean) {
            bean.operation="DELETE";
            OPENIAM.UserContacts.Common.saveOrRemove({
                entity : bean,
                url : "saveOrRemoveUserEmail.html",
                target : this
            });
        }
    },
    Addresses : {
        load : function() {
            OPENIAM.UserContacts.Common.load({
                columns : [
                    localeManager["openiam.ui.common.type"],
                    localeManager["openiam.ui.common.address.column"],
                    localeManager["openiam.ui.common.is.default"],
                	localeManager["openiam.ui.common.is.active"],
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["type","description","default","active"],
                ajaxURL : "getAddressesForUser.html",
                buttonTitle : localeManager["openiam.ui.button.add.address"],
                emptyResultsText:localeManager["openiam.ui.user.contact.address.empty"],
                hasEditButton : true,
                target : this,
                editFields:[{fieldName: "id", type:"hidden",label:""},
                            {fieldName: "typeId", itemText:"type", type:"select", items:OPENIAM.ENV.TypeList, label:localeManager["openiam.ui.common.type"], required:true},
                            {fieldName: "bldgNumber", type:"text",label:localeManager["openiam.ui.common.address.building"]},
                            {fieldName: "address1", type:"text",label:localeManager["openiam.ui.common.address.1"]},
                            {fieldName: "address2", type:"text",label:localeManager["openiam.ui.common.address.2"]},
                            {fieldName: "city", type:"text",label:localeManager["openiam.ui.common.address.city"]},
                            {fieldName: "state", type:"text",label:localeManager["openiam.ui.common.address.state"]},
                            {fieldName: "country", type:"text",label:localeManager["openiam.ui.common.address.country"]},
                            {fieldName: "postalCd", type:"text",label:localeManager["openiam.ui.common.address.postal.code"]},
                            {fieldName: "default", type:"checkbox",label:localeManager["openiam.ui.common.is.default"]},
                            {fieldName: "active", type:"checkbox",label:localeManager["openiam.ui.common.is.active"]}
                           ],
                dialogTitle: localeManager["openiam.ui.selfservice.ui.template.edit.address"]
            });
        },
        save : function(bean) {
            if(!OPENIAM.UserContacts.Common.isAdded({target : this, entity : bean})){
                OPENIAM.UserContacts.Common.saveOrRemove({
                    entity : bean,
                    url : "saveOrRemoveUserAddress.html",
                    target : this
                });
            } else{
                OPENIAM.Modal.Error(localeManager["openiam.ui.common.address.type.added"]);
            }
        },
        edit : function(bean){
            $("#editDialog").modalEdit("show", bean);
        },
        remove : function(bean) {
            bean.operation="DELETE";
            OPENIAM.UserContacts.Common.saveOrRemove({
                entity : bean,
                url : "saveOrRemoveUserAddress.html",
                target : this
            });
        }
    },
    Phones : {
        load : function() {
            OPENIAM.UserContacts.Common.load({
                columns : [
                    localeManager["openiam.ui.common.type"],
                	localeManager["openiam.ui.common.phone.number"],
                    localeManager["openiam.ui.common.is.default"],
                    localeManager["openiam.ui.common.is.active"],
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["type","description","default","active"],
                ajaxURL : "getPhonesForUser.html",
                buttonTitle : localeManager["openiam.ui.button.add.phone"],
                emptyResultsText:localeManager["openiam.ui.user.contact.address.empty"],
                hasEditButton : true,
                target : this,
                editFields:[{fieldName: "id", type:"hidden",label:""},
                            {fieldName: "typeId", itemText:"type", type:"select", items:OPENIAM.ENV.TypeList, label:localeManager["openiam.ui.common.type"], required:true},
                            {fieldName: "areaCd", type:"text",label:localeManager["openiam.ui.common.phone.area.code"], required:true},
                            {fieldName: "phoneNbr", type:"text",label:localeManager["openiam.ui.common.phone.number"], required:true},
                            {fieldName: "phoneExt", type:"text",label:localeManager["openiam.ui.common.phone.extension"]},
                            {fieldName: "countryCd", type:"text",label:localeManager["openiam.ui.common.phone.country.code"]},
                            {fieldName: "default", type:"checkbox",label:localeManager["openiam.ui.common.is.default"]},
                            {fieldName: "active", type:"checkbox",label:localeManager["openiam.ui.common.is.active"]}
                           ],
                dialogTitle:localeManager["openiam.ui.selfservice.ui.template.edit.phone"]
            });
        },
        save : function(bean) {
            if(!OPENIAM.UserContacts.Common.isAdded({target : this, entity : bean})){
                OPENIAM.UserContacts.Common.saveOrRemove({
                    entity : bean,
                    url : "saveOrRemoveUserPhone.html",
                    target : this
                });
            } else{
                OPENIAM.Modal.Error(localeManager["openiam.ui.common.phone.type.added"]);
            }
        },
        edit : function(bean){
            $("#editDialog").modalEdit("show", bean);
        },
        remove : function(bean) {
            bean.operation="DELETE";
            OPENIAM.UserContacts.Common.saveOrRemove({
                entity : bean,
                url : "saveOrRemoveUserPhone.html",
                target : this
            });
        }
    }
};

$(document).ready(function() {
    OPENIAM.UserContacts.Load.onReady();
});

$(document).load(function() {

});