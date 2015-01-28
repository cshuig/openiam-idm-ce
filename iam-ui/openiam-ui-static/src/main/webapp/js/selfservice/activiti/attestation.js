OPENIAM = window.OPENIAM || {};

OPENIAM.Attestation = {
	_groups : null,
	_roles : null,
	_resources : null,
	
	init : function() {
		this._requestGroups();
		this._requestResources();
		this._requestRoles();
	},
	_requestGroups : function() {
		var $this = this;
		this._requestEntitlementsEntity({url : "rest/api/entitlements/getGroupsForUser", setter : function(data) {$this._groups = data}});
	},
	_requestRoles : function() {
		var $this = this;
		this._requestEntitlementsEntity({url : "rest/api/entitlements/getRolesForUser", setter : function(data) {$this._roles = data}});
	},
	_requestResources : function() {
		var $this = this;
		this._requestEntitlementsEntity({url : "rest/api/entitlements/getResourcesForUser", setter : function(data) {$this._resources = data}});
	},
	_requestEntitlementsEntity : function(args) {
		var $this = this;
		$.ajax({
			url : args.url,
			"data" : { id : OPENIAM.ENV.EmployeeId, from : 0, size : 2000},
			type: "GET",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				var beans = data.beans;
				beans = ($.isArray(beans)) ? beans : null;
				args.setter(beans);
				$this._bindAndDraw();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	_bindAndDraw : function() {
		if($.isArray(this._groups) && $.isArray(this._roles) && $.isArray(this._resources)) {
			this._bind();
			this._draw();
		}
	},
	_bind : function() {
		var $this = this;
		$("#accept").click(function() {
			$this._submit();
			return false;
		});	
	},
	_draw : function() {
		this._drawGroups();
		this._drawRoles();
		this._drawResources();
	},
	_drawResources : function() {
		$("#resourceWrapper").persistentTable({
			emptyMessage : localeManager["openiam.ui.selfservice.no.resources"],
			createEnabled : false,
			editEnabledField : false,
			deleteEnabledField : true,
			objectArray : this._resources,
			headerFields : [localeManager["openiam.ui.common.name"]],
			fieldNames : ["name"],
			greyOutOnDelete : true,
			actionsColumnName : localeManager["openiam.ui.selfservice.actions.text"],
			tableTitle : localeManager["openiam.ui.selfservice.entitled.resources"],
			onDeleteClick : function(obj) {
				
			},
			equals : function(obj1, obj2) {
				return obj1.id = obj2.id;
			}
		});
	},
	_drawGroups : function() {
		$("#groupWrapper").persistentTable({
			emptyMessage : localeManager["openiam.ui.selfservice.no.groups"],
			createEnabled : false,
			editEnabledField : false,
			deleteEnabledField : true,
			objectArray : this._groups,
			headerFields : [localeManager["openiam.ui.common.name"]],
			fieldNames : ["name"],
			greyOutOnDelete : true,
			actionsColumnName : localeManager["openiam.ui.selfservice.actions.text"],
			tableTitle : localeManager["openiam.ui.selfservice.member.groups"],
			onDeleteClick : function(obj) {
				
			},
			equals : function(obj1, obj2) {
				return obj1.id = obj2.id;
			}
		});
	},
	_drawRoles : function() {
		$("#roleWrapper").persistentTable({
			emptyMessage : OPENIAM.ENV.Text.NoRoles,
			createEnabled : false,
			editEnabledField : false,
			deleteEnabledField : true,
			objectArray : this._roles,
			headerFields : [localeManager["openiam.ui.common.name"]],
			fieldNames : ["name"],
			greyOutOnDelete : true,
			actionsColumnName : localeManager["openiam.ui.selfservice.actions.text"],
			tableTitle : localeManager["openiam.ui.selfservice.member.roles"],
			onDeleteClick : function(obj) {
				
			},
			equals : function(obj1, obj2) {
				return obj1.id = obj2.id;
			}
		});
	},
	_getDeletedIds : function(objectArray) {
		var retVal = [];
		$.each(objectArray, function(idx, obj) {
			if(obj.isDisableClicked) {
				retVal.push(obj.id);
			}
		});
		return retVal;
	},
	_submit : function() {
		var obj = {
			resourceIds : this._getDeletedIds(this._resources),
			roleIds : this._getDeletedIds(this._roles),
			groupIds : this._getDeletedIds(this._groups),
			userId : OPENIAM.ENV.EmployeeId,
			taskId : OPENIAM.ENV.TaskId
		};
		$.ajax({
			url : "attestationRequest.html",
			data : JSON.stringify(obj),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						window.location.href = "myTasks.html";
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
};

$(document).ready(function() {
	OPENIAM.Attestation.init();
});