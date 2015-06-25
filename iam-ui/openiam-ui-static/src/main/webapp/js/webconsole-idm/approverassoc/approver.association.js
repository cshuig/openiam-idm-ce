console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.ENV = window.OPENIAM.ENV || {};
OPENIAM.ENV.Text = window.OPENIAM.ENV.Text || {};
OPENIAM.ENV.Text.ClickToChange = localeManager["openiam.ui.idm.prov.approver.assoc.change"];
OPENIAM.ENV.Text.ClickToFind = localeManager["openiam.ui.idm.prov.approver.assoc.find"];
OPENIAM.ENV.Text.ClickToFindApprover = localeManager["openiam.ui.idm.prov.approver.assoc.find.approver"];
OPENIAM.ENV.Text.ClickToFindApproveNotifier = localeManager["openiam.ui.idm.prov.approver.assoc.find.approve.notifier"];
OPENIAM.ENV.Text.ClickToFindRejectNotifier = localeManager["openiam.ui.idm.prov.approver.assoc.find.reject.notifier"];
OPENIAM.ENV.Text.ApproverType = localeManager["openiam.ui.idm.prov.approver.assoc.approver.type"];
OPENIAM.ENV.Text.NotifyOnRejectType = localeManager["openiam.ui.idm.prov.approver.assoc.notify.reject.type"];
OPENIAM.ENV.Text.NotifyOnApproveType = localeManager["openiam.ui.idm.prov.approver.assoc.notify.approve.type"];

OPENIAM.ApproverAssociation = {
	_onTypeChange : function(args) {
		var $this = args.$this;
		var linkId = args.linkId;
		var showableArray = args.showableArray;
		var selector = args.selector;
		var entityIdSelector = args.entityIdSelector;
		var displayNameSelector = args.displayNameSelector;
		
		var searchType = $this.find(selector).val();
		if($.inArray(searchType, showableArray) > -1) {
			$this.find(linkId).text(OPENIAM.ENV.Text.ClickToFind + ' ' + searchType).show();
		} else {
			$this.find(linkId).hide();
		}
		$this.find(displayNameSelector).text("");
		$this.find(entityIdSelector).val("");
	},
	_onAfterEditDialogShow : function(bean) {
		var approverDisplayName = bean.approverDisplayName;
		var onApproveDisplayName = bean.onApproveDisplayName;
		var onRejectDisplayName = bean.onRejectDisplayName;
		
		var _dialog = $("#associationDialog"); 
		if(approverDisplayName != null && approverDisplayName != undefined) {
			_dialog.find("#approverDisplayName").text(approverDisplayName).show();
			_dialog.find("#approverEntityId-link").show().text(OPENIAM.ENV.Text.ClickToChange + ' ' + $("#approverEntityType").val());
		} else {
			_dialog.find("#approverDisplayName").text("");
		}
				
		if(onApproveDisplayName != null && onApproveDisplayName != undefined) {
			_dialog.find("#onApproveDisplayName").text(onApproveDisplayName).show();
			_dialog.find("#onApproverEntityId-link").show().text(OPENIAM.ENV.Text.ClickToChange + ' ' + $("#onApproveEntityType").val());
		} else {
			_dialog.find("#onApproveDisplayName").text("");
		}
				
		if(onRejectDisplayName != null && onRejectDisplayName != undefined) {
			_dialog.find("#onRejectDisplayName").text(onRejectDisplayName).show();
			_dialog.find("#onRejectEntityId-link").show().text(OPENIAM.ENV.Text.ClickToChange + ' ' + $("#onRejectEntityType").val());
		} else {
			_dialog.find("#onRejectDisplayName").text("");
		}
	},
	_onTypeSelect : function(args) {
		var selector = args.selector;
		var entityIdSelector = args.entityIdSelector;
		var displayNameSelector = args.displayNameSelector;
		var linkId = args.linkId;
		var $this = args.$this;
		
    	var searchType = $this.find(selector).val();
    	if(searchType == "GROUP" || searchType == "ROLE") {
    		var searchURL = null;
    		var dialogTitle = null;
    		var emptyText = null;
    		if(searchType == "GROUP") {
    			searchURL = "/webconsole-idm/rest/api/entitlements/searchGroups";
    			dialogTitle = localeManager["openiam.ui.common.group.select"];
    			emptyText = localeManager["openiam.ui.shared.group.search.empty"];
    		} else if(searchType == "ROLE") {
    			searchURL = "/webconsole-idm/rest/api/entitlements/searchRoles";
    			dialogTitle = localeManager["openiam.ui.common.role.select"];
    			emptyText = localeManager["openiam.ui.shared.role.search.empty"];
    		}
    		var html = $("#simpleSearch").clone().show();
    		$("#dialog").html(html).dialog({ autoOpen: true, draggable : false, resizable : false,
                title : localeManager["openiam.ui.common.search"] + " : " + searchType});
    		$("#dialog").find("a").click(function() {
    			var input = $("#dialog").find("input[type='text']"); 
    			$("#dialog").modalSearch({
 					onElementClick : function(bean) {
 						$("#dialog").dialog("close");
 						$this.find(entityIdSelector).val(bean.id);
 						$this.find(displayNameSelector).text(searchType + ": " + bean.name);
 						$this.find(linkId).text(OPENIAM.ENV.Text.ClickToChange + ' ' + searchType);
					},
					ajaxURL : searchURL,
					dialogTitle : dialogTitle,
					emptyResultsText : emptyText
				});
				$("#dialog").modalSearch("show");
    		});
    	} else if(searchType == "USER") {
    		$("#dialog").userSearchForm({
    			  restfulURLPrefix : "/webconsole-idm/",
	              url : "/webconsole-idm/rest/api/users/getUserFormAttributes",
	              afterFormAppended : function() {
	                  $("#dialog").dialog({autoOpen: false, draggable : false, resizable : false,
                          title : localeManager["openiam.ui.common.search.users"], width: "auto", position : "center"});
	                  $("#dialog").dialog("open");
	              },
	              	onSubmit : function(json) {
	                  	$("#dialog").userSearchResults({
	                  		contextPath : "/webconsole-idm/",
	                      	"jsonData" : json,
	                      	"page" : 0,
	                      	"size" : 20,
                            initialSortColumn : "name",
                            initialSortOrder : "ASC",
	                      	url : "/webconsole-idm/rest/api/users/search",
	                      	emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                		  	emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
	                      	onAppendDone : function() {
	                      		
	                      	},
	                      	onEntityClick : function(bean) {
	                      		$("#dialog").dialog("close");
	                      		$this.find(entityIdSelector).val(bean.id);
	                      		$this.find(displayNameSelector).text(searchType + ": " + bean.name);
	                      		$this.find(linkId).text(OPENIAM.ENV.Text.ClickToChange + ' ' + searchType);
	                      	}
	                  	});
	              	}
	          });
    	}
	},
	save : function() {
		$.ajax({
			url : "saveApproverAssociations.html",
			data : JSON.stringify({beans : OPENIAM.ENV.AssociationModels, type : OPENIAM.ENV.AssociationTypeEnum, entityId : OPENIAM.ENV.AssociationId}),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
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
	},
	load : function() {
		$("#saveAssociations").click(function() {
			OPENIAM.ApproverAssociation.save();
		});
		
		$.each(OPENIAM.ENV.AssociationModels, function(idx, obj) {
			obj.uid = Math.random();
		});
		
		var modalFields = [
								{fieldName : "id", type:"hidden", label : ""},
		                        {fieldName: "approverEntityType", type:"select",label:OPENIAM.ENV.Text.ApproverType, items : OPENIAM.ENV.ApproverTypes, required : true, onChange : function() {
		                        	OPENIAM.ApproverAssociation._onTypeChange({
		                        		$this : this,
		                        		linkId : "#approverEntityId-link",
		                        		showableArray : ["USER", "GROUP", "ROLE"],
		                        		selector : "#approverEntityType",
		                        		entityIdSelector : "#approverEntityId",
		                        		displayNameSelector : "#approverDisplayName"
		                        	});
		                        }},
		                        {fieldName: "approverEntityId", type:"hidden",label:"", readonly:true},
		                        {fieldName : "approverDisplayName", type : "div", linkLabel : "", label : ""},
		                        {type : "link", fieldName : "approverEntityId-link", label : "", linkLabel : OPENIAM.ENV.Text.ClickToFindApprover, hidden : true, additionaClazz : "approver-search", onClick : function(arg) {
		                        	OPENIAM.ApproverAssociation._onTypeSelect({
		                        		$this : this,
		                        		linkId : "#approverEntityId-link",
		                        		selector : "#approverEntityType",
		                        		entityIdSelector : "#approverEntityId",
		                        		displayNameSelector : "#approverDisplayName"
		                        	});
		                        }},
		            		    
		            		    {fieldName: "onApproveEntityType", type:"select",label:OPENIAM.ENV.Text.NotifyOnApproveType, items : OPENIAM.ENV.NotifiableTypes, onChange : function() {
		            		    	OPENIAM.ApproverAssociation._onTypeChange({
		                        		$this : this,
		                        		linkId : "#onApproverEntityId-link",
		                        		showableArray : ["USER", "GROUP", "ROLE"],
		                        		selector : "#onApproveEntityType",
		                        		entityIdSelector : "#onApproveEntityId",
		                        		displayNameSelector : "#onApproveDisplayName"
		                        	});
		            		    }},
		            		    {fieldName: "onApproveEntityId", type:"hidden",label:"", readonly:true},
		            		    {fieldName : "onApproveDisplayName", type : "div", linkLabel : "", label : ""},
		            		    {type : "link", fieldName : "onApproverEntityId-link", label : "", linkLabel : OPENIAM.ENV.Text.ClickToFindApproveNotifier, hidden : true, additionaClazz : "approver-search", onClick : function(arg) {
		                        	OPENIAM.ApproverAssociation._onTypeSelect({
		                        		$this : this,
		                        		linkId : "#onApproverEntityId-link",
		                        		selector : "#onApproveEntityType",
		                        		entityIdSelector : "#onApproveEntityId",
		                        		displayNameSelector : "#onApproveDisplayName"
		                        	});
		            		    }},
		            		    
		            		    {fieldName: "onRejectEntityType", type:"select", label:OPENIAM.ENV.Text.NotifyOnRejectType, items : OPENIAM.ENV.NotifiableTypes, onChange : function() {
		            		    	OPENIAM.ApproverAssociation._onTypeChange({
		                        		$this : this,
		                        		linkId : "#onRejectEntityId-link",
		                        		showableArray : ["USER", "GROUP", "ROLE"],
		                        		selector : "#onRejectEntityType",
		                        		entityIdSelector : "#onRejectEntityId",
		                        		displayNameSelector : "#onRejectDisplayName"
		                        	});
		            		    }},
		            		    {fieldName: "onRejectEntityId", type:"hidden", label:"", readonly:true},
		            		    {fieldName : "onRejectDisplayName", type : "div", linkLabel : "", label : ""},
		            		    {type : "link", fieldName : "onRejectEntityId-link", label : "", linkLabel : OPENIAM.ENV.Text.ClickToFindRejectNotifier, hidden : true, additionaClazz : "approver-search", onClick : function(arg) {
		            		    	OPENIAM.ApproverAssociation._onTypeSelect({
		                        		$this : this,
		                        		linkId : "#onRejectEntityId-link",
		                        		selector : "#onRejectEntityType",
		                        		entityIdSelector : "#onRejectEntityId",
		                        		displayNameSelector : "#onRejectDisplayName"
		                        	});
		            		    }},
		            		    {fieldName: "uid", type:"hidden", label:"", readonly:true},
		            		    {fieldName: "applyDelegationFilter", type:"hidden", label:"", readonly:true}
		            		];
		$("#container").persistentTable({
			emptyMessage : localeManager["openiam.ui.idm.prov.approver.assoc.empty"],
			createEnabled : true,
			objectArray : OPENIAM.ENV.AssociationModels,
			createText : localeManager["openiam.ui.idm.prov.approver.assoc.create.new"],
			headerFields : [
                localeManager["openiam.ui.common.approver"],
                localeManager["openiam.ui.idm.prov.approver.assoc.notify.approve"],
                localeManager["openiam.ui.idm.prov.approver.assoc.notify.reject"]
            ],
			fieldNames : ["approverDisplayName", "onApproveDisplayName", "onRejectDisplayName"],
			deleteEnabledField : true,
			editEnabledField : true,
			actionsColumnName : localeManager["openiam.ui.common.actions"],
			tableTitle : "",
			sortEnabled : true,
			equals : function(obj1, obj2) {
				return (obj1.uid == obj2.uid);
			},
			onDeleteClick : function(obj) {
				
			},
			onEditClick : function(obj) {
				var $this = this;
				$("#associationDialog").modalEdit({
					dialogHandle : "#associationDialog",
                    fields: modalFields,
                    dialogTitle: localeManager["openiam.ui.idm.prov.approver.assoc.edit"],
                    onSubmit: function(bean){
                    	$.extend(obj, bean);
                    	obj.approverDisplayName = $("#associationDialog").find("#approverDisplayName").text(); 
                    	obj.onRejectDisplayName = $("#associationDialog").find("#onRejectDisplayName").text();
                    	obj.onApproveDisplayName = $("#associationDialog").find("#onApproveDisplayName").text();
                    	
                    	//handle supervisor case
                    	obj.approverEntityId = (obj.approverEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.approverEntityId;
                    	obj.approverDisplayName = (obj.approverEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.approverDisplayName;
                    	obj.onApproveEntityId = (obj.onApproveEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.onApproveEntityId;
                    	obj.onApproveDisplayName = (obj.onApproveEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.onApproveDisplayName;
                    	obj.onRejectEntityId = (obj.onRejectEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.onRejectEntityId;
                    	obj.onRejectDisplayName = (obj.onRejectEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.onRejectDisplayName;
                    	$("#associationDialog").modalEdit("hide");
                    	$this.persistentTable("draw");
                    }
                });
				$("#associationDialog").modalEdit("show", obj);
				OPENIAM.ApproverAssociation._onAfterEditDialogShow(obj);
			},
			onCreateClick : function() {
				var obj = {};
                obj.uid = new Date().getTime();
				
				var $this = this;
				$("#associationDialog").modalEdit({
					dialogHandle : "#associationDialog",
                    fields: modalFields,
                    dialogTitle: localeManager["openiam.ui.idm.prov.approver.assoc.new"],
                    onSubmit: function(bean){
                    	$.extend(obj, bean);
                    	obj.associationType = OPENIAM.ENV.AssocationType;
                    	obj.associationEntityId = OPENIAM.ENV.AssociationId;
                    	obj.approverDisplayName = $("#associationDialog").find("#approverDisplayName").text();
                    	obj.onRejectDisplayName = $("#associationDialog").find("#onRejectDisplayName").text();
                    	obj.onApproveDisplayName = $("#associationDialog").find("#onApproveDisplayName").text();
                    	
                    	//handle supervisor case
                    	obj.approverEntityId = (obj.approverEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.approverEntityId;
                    	obj.approverDisplayName = (obj.approverEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.approverDisplayName;
                    	obj.onApproveEntityId = (obj.onApproveEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.onApproveEntityId;
                    	obj.onApproveDisplayName = (obj.onApproveEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.onApproveDisplayName;
                    	obj.onRejectEntityId = (obj.onRejectEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.onRejectEntityId;
                    	obj.onRejectDisplayName = (obj.onRejectEntityType == "SUPERVISOR") ? localeManager["openiam.ui.user.supervisor"] : obj.onRejectDisplayName;
                    	
                    	$("#associationDialog").modalEdit("hide");
                    	$this.persistentTable("addObject", obj);
                    	$this.persistentTable("draw");
                    	$this.persistentTable("onOrderEdit");
                    }
                });
                
                OPENIAM.ApproverAssociation._onAfterEditDialogShow(obj);
				$("#associationDialog").modalEdit("show", obj);
			},
			onOrderEdit : function(idx, obj) {
				obj.approverLevel = idx;
			}
		});
	}	
};

$(document).ready(function() {
	OPENIAM.ApproverAssociation.load();
});

$(window).load(function() {
	
});