OPENIAM = window.OPENIAM || {};
OPENIAM.MenuEntitlements = {
	tree : null,
	init : function() {
		var $this = this;
		$("#menuTree").change(function() {
			if($(this).val() != -1) {
				$this.request();
			}
		});
		
		$("#saveBtn").click(function() {
			OPENIAM.MenuEntitlements.save();
		});
	},
	request : function() {
		var menuId = $("#menuTree").val();
		$.ajax({
			url : "getCurrentMenuTreeForPrincipal.html",
			data : {"menuId" : menuId, principalId : OPENIAM.ENV.principalId, principalType : OPENIAM.ENV.principalType },
			type: "GET",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				OPENIAM.MenuEntitlements.afterGet(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error("Can't fetch menu tree");
			}
		});
	},
	afterGet : function(tree) {
		var $this = this;
		this.tree = Object.create(OPENIAM.MenuTree);
		this.tree.toDynaTreeView = function() {
			var children = [];
			children.push(this.getRoot().toDynaTreeView());
			return children;
		};
		this.tree.getAllNodes = function() {
			var nodes = [];
			if(this.getRoot() != null) {
				$.each(this.getRoot().getAllNodes(), function(idx, val) {
					nodes.push(val);
				});
			}
			return nodes;
		};
		
		this.tree.initialize({tree : tree});
		$("#menuArea").empty().html("<div id=\"menuContainer\"></div>");
		$("#menuContainer").dynatree({
			children : this.tree.toDynaTreeView(),
			rootVisible : true,
			minExpandLevel : 4,
			debugLevel : 0,
			onDblClick: function(node, event) {
				var source = $this.tree.findById(node.data.key);
				source.toggleDirty();
				OPENIAM.MenuEntitlements.rerenderDirtyNodes();
			}
		});
		$("#actionDescription").show();
		$("#saveBtn").show();
	},
	rerenderDirtyNodes : function() {
		var dTree = $("#menuContainer").dynatree("getTree");
		$.each(this.tree.getAllNodes(), function(idx, val) {
			var node = dTree.getNodeByKey(val.getId());
			if(node != null && node != undefined) {
				var isDirty = (val.getIsDirty());
				var clazz = isDirty ? "dirty" : "";
				clazz += " ";
				clazz += val.getEntitlementsClasses();
				node.data.addClass = clazz;
				node.render();
			} 
		});
	},
	save : function() {
		var $this = this;
		var obj = {
			principalId : OPENIAM.ENV.principalId,
			principalType : OPENIAM.ENV.principalType,
			newlyEntitled : [],
			disentitled : []
		};
		$.each(this.tree.getAllNodes(), function(idx, node) {
			if(node.getIsDirty()) {
				if(node.isExplicitlyEntitled()) {
					obj.newlyEntitled.push(node.getId());
				} else {
					obj.disentitled.push(node.getId());
				}
			}
		});
		$.ajax({
			url : "saveMenuEntitlements.html",
			data : JSON.stringify(obj),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : localeManager["openiam.ui.entitlements.save.success.message"]});
					setTimeout(function() {
						OPENIAM.MenuEntitlements.request();
						OPENIAM.Modal.Close();
					}, 1500);
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

OPENIAM.MenuTree.Node.prototype.toDynaTreeView = function() {
	var obj = {
		title : (this.getText() != null) ? this.getText() : localeManager["openiam.ui.entitlements.no.display.name.set"],
		key: this.getId(),
		children : null,
		addClass : this.getEntitlementsClasses()
	};
	
	if(this.getChild() != null) {
		obj.children = [];
		var node = this.getChild();
		while(node != null) {
			obj.children.push(node.toDynaTreeView());
			node = node.getNext();
		}
	}
	return obj;
};

OPENIAM.MenuTree.Node.prototype.getAllNodes = function() {
	var nodes = [];
	var node = this;
	while(node != null) {
		if(node.getChild() != null) {
			$.each(node.getChild().getAllNodes(), function(idx, val) {
				nodes.push(val);
			});
		}
		
		nodes.push(node);
		
		node = node.getNext();
	}
	return nodes;
};

OPENIAM.MenuTree.Node.prototype.getEntitlementsClasses = function() {
	var clazz = "";
	if(this.isExplicitlyEntitled() || this.isImplicitlyEntitled()) {
		if(this.isExplicitlyEntitled()) {
			clazz += "explicit";
		}
		
		if(this.isImplicitlyEntitled()) {
			clazz += " implicit";
		}
	}
	
	if(this.getIsPublic()) {
		clazz += " public";
	}
	
	if(clazz == "") {
		clazz = "none";
	}
	
	return clazz;
};

OPENIAM.MenuTree.Node.prototype.toggleDirty = function() {
	this.isDirty = !(this.isDirty == true);
	if(this.isExplicitlyEntitled()) {
		this.removeExplicitEntitlement();
	} else {
		this.addExplicitEntitlement();
	}
};

OPENIAM.MenuTree.Node.prototype.getIsDirty = function() {
	return (this.isDirty == true);
};

$(document).ready(function() {
	OPENIAM.MenuEntitlements.init();
});

$(window).load(function() {

});