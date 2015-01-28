OPENIAM = window.OPENIAM || {};

OPENIAM.ProcessFlow = {
	request : function() {
		var $that = this;
		$.ajax({
			url : "rest/api/activiti/task/executiongroup/find",
			data : { id : OPENIAM.ENV.ExecutionId},
			type: "GET",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				$that.draw(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	draw : function(data) {
		if(data != null && data != undefined && data.treeBeans != null && data.treeBeans != undefined && data.treeBeans.length > 0) {
			var ul = document.createElement("ul"); ul.id="org"; $(ul).css('display', 'none');
			var map = {};
			for(var i in data.treeBeans) {
				var bean = data.treeBeans[i];
				map[bean.id] = bean;
			}
			ul.appendChild(this.getTree(data.treeBeans[0], map));
			$("#appendUL").prepend(ul);
			$("#org").jOrgChart({
				chartElement : '#chart'
        	});
		} else {
			console.log("none");
		}
	},
	getTree : function(bean, map) {
		var li = document.createElement("li");
		if(bean.task != null) {
			$(li).append("<div class='taskName'>" + ((bean.activityName != null && bean.activityName != undefined) ? bean.activityName : bean.task.name) + "</div>");
			$(li).append("<div class='completedBy'>Completed by: " + bean.assigneeName + "</div>");
			
		} else {
			$(li).append("<div class='taskName'>" + ((bean.activityName != null && bean.activityName != undefined) ? bean.activityName : bean.activityId) + "</div>");
		}
		
		$(li).append("<div class='date'>Start Time: " + new Date(bean.startTime).toUTCString() + "</div>");
		if(bean.endTime != null) {
			$(li).append("<div class='date'>End Time: " + new Date(bean.endTime).toUTCString() + "</div>");
		}
		
		if(bean.task != null) {
			$(li).append("<img src=\"/openiam-ui-static/images/common/userTask.png\"/>");
		}
		
		if(bean.nextIds != null && bean.nextIds != undefined) {
			var ul = $(document.createElement("ul"));
			for(var i in bean.nextIds) {
				ul.append(this.getTree(map[bean.nextIds[i]], map))
			}
			$(li).append(ul);
		}
		return li;
	}
};

$(document).ready(function() {
	OPENIAM.ProcessFlow.request();
});

$(window).load(function() {

});