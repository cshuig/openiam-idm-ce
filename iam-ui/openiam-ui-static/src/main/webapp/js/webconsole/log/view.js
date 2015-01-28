OPENIAM = window.OPENIAM || {};

OPENIAM.LogView = {
	init : function() {
		$("#targetContainer").persistentTable({
			emptyMessage : localeManager["openiam.ui.audit.log.event.target.empty"],
			objectArray : OPENIAM.ENV.Log.Targets,
			headerFields : [
                localeManager["openiam.ui.audit.log.target.principal"],
                localeManager["openiam.ui.audit.log.target.type"]
            ],
			fieldNames : ["objectPrincipal", "targetType"],
			tableTitle : localeManager["openiam.ui.audit.log.event.target"]
		});
		$("#parentEventsContainer").persistentTable({
			emptyMessage : localeManager["openiam.ui.audit.log.event.parent.empty"],
			objectArray : OPENIAM.ENV.Log.Parents,
			headerFields : [
                localeManager["openiam.ui.audit.log.requestor"],
                localeManager["openiam.ui.audit.log.target"],
                localeManager["openiam.ui.audit.log.action"],
                localeManager["openiam.ui.common.date"],
                localeManager["openiam.ui.audit.log.result"]
            ],
			fieldNames : ["principal", "target", "action", "timestamp", "result"],
			fieldDisplayers : {
				timestamp : function(obj) {
					return (typeof(obj) === "number") ? new Date(obj) : localeManager["openiam.ui.audit.log.not.available"]
				}
			},
			editEnabledField : true,
			tableTitle : localeManager["openiam.ui.audit.log.event.parent"],
			actionsColumnName : localeManager["openiam.ui.common.actions"],
			onEditClick : function(bean) {
				var url = "viewLogRecord.html?id=" + bean.id;
				if(OPENIAM.ENV.SourceId != null && OPENIAM.ENV.Source != null) {
					url = url + "&source=" + OPENIAM.ENV.Source + "&sourceId=" + OPENIAM.ENV.SourceId;
				}
				window.location.href = url;
			}
		});
		$("#childEventsContainer").persistentTable({
			emptyMessage : localeManager["openiam.ui.audit.log.event.child.empty"],
			objectArray : OPENIAM.ENV.Log.Children,
            headerFields : [
                localeManager["openiam.ui.audit.log.requestor"],
                localeManager["openiam.ui.audit.log.target"],
                localeManager["openiam.ui.audit.log.action"],
                localeManager["openiam.ui.common.date"],
                localeManager["openiam.ui.audit.log.result"]
            ],
            fieldNames : ["principal", "target", "action", "timestamp", "result"],
			fieldDisplayers : {
				timestamp : function(obj) {
					return (typeof(obj) === "number") ? new Date(obj) : localeManager["openiam.ui.audit.log.not.available"]
				}
			},
			editEnabledField : true,
			tableTitle : localeManager["openiam.ui.audit.log.event.child"],
			actionsColumnName : localeManager["openiam.ui.common.actions"],
			onEditClick : function(bean) {
				var url = "viewLogRecord.html?id=" + bean.id;
				if(OPENIAM.ENV.SourceId != null && OPENIAM.ENV.Source != null) {
					url = url + "&source=" + OPENIAM.ENV.Source + "&sourceId=" + OPENIAM.ENV.SourceId;
				}
				window.location.href = url;
			}
		});
	}
};

$(document).ready(function() {
	OPENIAM.LogView.init();
});