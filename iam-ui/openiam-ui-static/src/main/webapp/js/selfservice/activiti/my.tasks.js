OPENIAM = window.OPENIAM || {};
OPENIAM.MyTasks = {
    init : function() {
    	var $this = this;
		$("#assignedTasks").entitlemetnsTable({
			columnHeaders : [
                localeManager["openiam.ui.common.name"],
                localeManager["openiam.ui.common.description"],
                localeManager["openiam.ui.common.actions"]
			],
			columnsMap : ["name", "description"],
			hasEditButton : true,
			onEdit : function(bean) {
				window.location.href = "task.html?id=" + bean.id;
			},
			ajaxURL : "rest/api/activiti/tasks/assigned",
			entityUrl : "task.html",
			entityURLIdentifierParamName : "id",
            deleteOptions : {
            	isDeletable : function(bean) {
            		return (bean.deletable);
            	},
            	onDelete : function(bean) {
					$this.deleteTask(bean.id);
				}
            },
			emptyResultsText : localeManager["openiam.ui.selfservice.my.tasks.have.tasks.assigned"]
		});
		
		$("#candidateTasks").entitlemetnsTable({
			columnHeaders : [
                localeManager["openiam.ui.common.name"],
                localeManager["openiam.ui.common.description"],
                localeManager["openiam.ui.common.actions"]
			],
			columnsMap : ["name", "description"],
			ajaxURL : "rest/api/activiti/tasks/candidate",
			entityUrl : "task.html",
			entityURLIdentifierParamName : "id",
			emptyResultsText : localeManager["openiam.ui.selfservice.my.tasks.no.tasks.claim"],
			preventOnclickEvent : true,
			onAdd : function(bean) {
				$this.claimTask(bean.id);
			}
		});
    },
    claimTask : function(taskId) {
    	$.ajax({
			url : "rest/api/activiti/task/claim",
			data : { id : taskId},
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
    },
    deleteTask : function(taskId) {
    	$.ajax({
			url : "rest/api/activiti/task/delete",
			data : { id : taskId},
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
};

$(document).ready(function() {
    OPENIAM.MyTasks.init();
});

$(window).load(function() {

});