OPENIAM = window.OPENIAM || {};

OPENIAM.MenuTree.OuterTree = Object.create(OPENIAM.MenuTree);

OPENIAM.Frame = {
	initMenus : function() {
		OPENIAM.MenuTree.OuterTree.initialize({
			tree : OPENIAM.ENV.MenuTree, 
			toHTML : function() {
				var ul = document.createElement("ul");
				if(this.getRoot() != null) {
					var node = this.getRoot().getChild();
					while(node != null) {
						var html = node.toHTML();
						if(html) {
							ul.appendChild(html);
						}
						node = node.getNext();
					}
				}
				return ul;
			},
			onNodeClick : function() {
				OPENIAM.Frame.changeFrame(this);
				
				var $this = this;
				setTimeout(function() {
					OPENIAM.Frame.showBreadcrumbs($this);
				}, 500);
			},
			toNodeHtml : function() {
				var that = this;
				var url = this.getURL();
				var li = document.createElement("li");
					var a = document.createElement("a"); a.href = (url != null) ? url : "javascript:void(0);";
					if(this.getLevel() <= 1) {
						var icon = document.createElement("div"); icon.className = "icon";
							if(this.getIcon()) { 
								var iconImg = document.createElement("img"); $(iconImg).attr("src", this.getIcon()); $(iconImg).attr("alt", "");
								$(icon).append(iconImg);
							}
							var displayText = document.createElement("div"); displayText.className = "tmenu"; displayText.innerHTML = this.getText();
							$(a).append(icon, displayText);
					} else {
						a.innerHTML = this.getText();
					}
					$(a).click(function() {
						if($.isFunction(that.onClick)) {
							that.onClick();
						}
						return false;
					});
				$(li).append(a);
				if(this.getChild() != null) {
					var ul = document.createElement("ul");
					var node = this.getChild();
					while(node != null) {
						var html = node.toHTML();
						if(html) {
							$(ul).append(html);
						}
						node = node.getNext();
					}
					$(li).append(ul);
				}
				return li;
			}
		});
		$("#nav").append(OPENIAM.MenuTree.OuterTree.toHTML());
	},
	initialize : function() {
		if(OPENIAM.ENV.MenuTree != null) {
			this.initMenus();
			$(document).OPENIAMAPI({debug : true});
			var initialMenu = null;
			if(OPENIAM.ENV.FrameURL != null && OPENIAM.ENV.FrameURL != undefined) {
				initialMenu = OPENIAM.ENV.FrameURL;
			} else {
				if(OPENIAM.ENV.initialMenu != null && OPENIAM.ENV.initialMenu != undefined) {
					initialMenu = new OPENIAM.MenuTree.Node(OPENIAM.ENV.initialMenu);
				} else {
					initialMenu = OPENIAM.MenuTree.OuterTree.findFirstWithURL();
				}
			}
			this.changeFrame(initialMenu);
			
			var $this = this;
			setTimeout(function() {
				$this.showBreadcrumbs(initialMenu);
			}, 500);
		}
	},
	changeFrame : function(menu) {
		if(menu != null) {
			var url = (typeof(menu) === "string") ? menu : menu.getFQURL();
			if(url != null && url != undefined && (url.indexOf("/") == 0 || url.indexOf("http") == 0)) {
				$("#contentFrame").attr("src", url);
			
				if(typeof(menu) !== "string") {
					try {
						var src = OPENIAM.ENV.Context + "/menu/" + menu.getDeepLink();
						window.history.pushState(null, menu.getText(), src);
					} catch(e) {
					
					}
				}
			}
		}
	},
	changeFrameToFirstMenu : function() {
		var firstMenu = OPENIAM.MenuTree.OuterTree.findFirstWithURL();
		if(firstMenu != null) {
			this.changeFrame(firstMenu);
			return true;
		} else {
			return false;
		}
	},
	showBreadcrumbs : function(menu) {
		/*
		$("#breadcrumb").empty();
		if(menu != null && typeof(menu) === "object") {
			var elmts = menu.toBreadCrumbs();
			$.each(elmts, function(idx, elmt) {
				$("#breadcrumb").append(elmt);
			});
		}
		*/
	}
};

OPENIAM.AjaxLoader.getURLOnAuthRedirect = function() {
	var loc = $("#contentFrame")[0].contentWindow.location
	var currentURL = loc.pathname;
	if(loc.search != null && loc.search != undefined) {
		currentURL += loc.search;
	}
	return currentURL;
}

OPENIAM.NotificationPanel = {
	setTimeouts : function() {
		setTimeout(function() {
			OPENIAM.NotificationPanel.updateActivitiStatus();
			OPENIAM.NotificationPanel.setTimeouts();
		}, 10000);
	},
	updateActivitiStatus : function() {
		$.ajax({
			url : OPENIAM.ENV.OuterFrameContextPath + "/rest/api/activiti/summary",
			"data" : null,
			type: "GET",
			dataType : "json",
			beforeSend : function() {},
			complete : function() {},
			success : function(data, textStatus, jqXHR) {
				var numOfCandidateTasks = data.numOfCandidateTasks;
				var numOfAssignedTasks = data.numOfAssignedTasks;
				var numOfCandidateTasksDiv = $(document.createElement("div"));
				if(numOfCandidateTasks > 0) {
					numOfCandidateTasksDiv.text(localeManager["openiam.ui.selfservice.candidate.tasks.notification"].format(numOfCandidateTasks));
				}
				var numOfAssignedTasksDiv = $(document.createElement("div"));
				if(numOfAssignedTasks > 0) {
					numOfAssignedTasksDiv.text(localeManager["openiam.ui.selfservice.assigned.tasks.notification"].format(numOfAssignedTasks));
				}
				$("#notifications").empty().append(numOfCandidateTasksDiv, numOfAssignedTasksDiv);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	}
};

$(document).ready(function() {
	$(function(){
		
		$('#content').corner("20px");
		$('#nav ul ul a').corner("5px");
		//$('#nav ul ul :hover > a').corner("5px");
		//$('#nav li:hover > a').corner("5px");
		$('#content').boxShadow( '5px', '10px', '10px' , '#ccc' );
		$("#top").css('min-height','215px');
	});	
	OPENIAM.Frame.initialize();
	
	if(OPENIAM.ENV.CheckActivitiTasks === true) {
		OPENIAM.NotificationPanel.updateActivitiStatus();
		OPENIAM.NotificationPanel.setTimeouts();
	}
});

$(window).load(function() {
	
});