OPENIAM = window.OPENIAM || {};
OPENIAM.MenuTree = window.OPENIAM.MenuTree || {};

OPENIAM.MenuTree.ContextTree = Object.create(OPENIAM.MenuTree);

OPENIAM.MenuTree.ContextTree.toDynaTreeView = function() {
	var children = [];
	children.push(this.getRoot().toDynaTreeView());
	return children;
};

OPENIAM.MenuTree.ContextTree.toJSON = function() {
	var json =  {
					tree : (this.getRoot() != null) ? this.getRoot().toJSON() : null,
		    	 	rootId : this.getRootId()
		    	};
	return json;
};

OPENIAM.MenuTree.ContextTree.getDirtyNodes = function() {
	var nodes = [];
	if(this.getRoot() != null) {
		$.each(this.getRoot().getDirtyNodes(), function(idx, val) {
			nodes.push(val);
		});
	}
	return nodes;
};

OPENIAM.MenuTree.Node.prototype.getDirtyNodes = function() {
	var nodes = [];
	var node = this;
	while(node != null) {
		if(node.getChild() != null) {
			$.each(node.getChild().getDirtyNodes(), function(idx, val) {
				nodes.push(val);
			});
		}
		
		if(node.getIsDirty()) {
			nodes.push(node);
		}
		
		node = node.getNext();
	}
	return nodes;
};

OPENIAM.MenuTree.Node.prototype.toJSON = function() {
	var json = {
		id : this.getId(),
		name : this.getName(),
		url : this.getURL(),
		displayName : this.getText(),
        risk : this.getRisk(),
		order : this.getOrder(),
		icon : this.getIcon(),
		visible : this.getIsVisible(),
		isPublic : this.getIsPublic(),
		firstChild : (this.getChild() != null) ? this.getChild().toJSON() : null,
		nextSibling : (this.getNext() != null) ? this.getNext().toJSON() : null,
		displayNameMap : this.getDisplayNameMap()
	};
	
	return json;
};

OPENIAM.MenuTree.Node.prototype.moveAfter = function(node) {
	var _prev = this.getPrev();
	var _parent = this.getParent();
	
	if(_prev == null) {
		_parent.setFirstChild(this.getNext());
		_parent.setDirty();
	}
	
	if(_prev != null) {
		_prev.setNext(this.getNext());
		_prev.setDirty();
	}
	
	this.setNext(node.getNext());
	node.setNext(this);
	node.setDirty();
	_parent._changeOrders();
};

OPENIAM.MenuTree.Node.prototype.moveNewNodeBefore = function(node) {
	var _parent = node.getParent();
	if(this.getPrev() == null) {
		_parent.setFirstChild(node);
		_parent.setDirty();
	} else {
		this.getPrev().setDirty();
		this.getPrev().setNext(node);
	}
	node.setDirty();
	node.setNext(this);
	_parent._changeOrders();
};

OPENIAM.MenuTree.Node.prototype.moveNewNodeAfter = function(node) {
	node.setDirty();
	this.setDirty();
	node.setNext(this.getNext());
	this.setNext(node);
	var _parent = this.getParent();
	_parent._changeOrders();
};

OPENIAM.MenuTree.Node.prototype.moveBefore = function(node) {
	var _parent = node.getParent();
	if(this.getPrev() != null) {
		this.getPrev().setDirty();
		this.getPrev().setNext(this.getNext());
	} else {
		_parent.setDirty();
		_parent.setFirstChild(this.getNext());
	}
	var _prev = node.getPrev();
	
	if(_prev == null) {
		_parent.setDirty();
		_parent.setFirstChild(this);
	}
	
	if(_prev != null) {
		_prev.setDirty();
		_prev.setNext(this);
	}
	
	this.setDirty();
	this.setNext(node);
	_parent._changeOrders();
};

OPENIAM.MenuTree.Node.prototype.deleteNode = function() {
	var _parent = this.getParent();
	if(this.getPrev() == null) {
		this.getParent().setDirty();
		this.getParent().setFirstChild(this.getNext());
	} else {
		this.getPrev().setDirty();
		this.getPrev().setNext(this.getNext());
	}
	
	_parent._changeOrders();
};

OPENIAM.MenuTree.Node.prototype.deleteTree = function() {
	OPENIAM.MenuTree.ContextTree._root = null;
};

OPENIAM.MenuTree.Node.prototype.getTextTrail = function() {
	var elmts = [];
	var node = this;
	while(node != null) {
		elmts.unshift(node.getText(), " >> ");
		node = node.getParent();
	}
	elmts.splice(elmts.length - 1, 1);
	var str = "";
	$.each(elmts, function(idx, val) {
		str += val;
	});
	return str;
};

OPENIAM.MenuTree.Node.prototype._changeOrders = function() {
	var idx = 1;
	var node = this.getChild();
	var keys = [];
	while(node != null) {
		var order = node.getOrder();
		node.setOrder(idx);
		if(order == null || order == undefined || order != idx) {
			node.setDirty();
			keys.push(node.getId());
		}
		idx = idx + 1;
		node = node.getNext();
	}
	return keys;
};

OPENIAM.MenuTree.Node.prototype.setAsFirstChild = function(node) {
	node.setNext(this.getChild());
	this.setFirstChild(node);
	this._changeOrders();
};

OPENIAM.MenuTree.Node.prototype.setDirty = function() {
	this.isDirty = true;
};

OPENIAM.MenuTree.Node.prototype.getIsDirty = function() {
	return (this.isDirty == true);
};

OPENIAM.MenuTree.Node.prototype.toDynaTreeView = function() {
	var obj = {
		title : (this.getText() != null) ? this.getText() : localeManager["openiam.ui.entitlements.no.display.name.set"],
		key: this.getId(),
		children : null,
		addClass : (this.getIsDirty() ? "dirty" : "")
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

OPENIAM.MenuEdit = {
	save : function(){
		$.ajax({
			url : "saveMenuTree.html",
			data : JSON.stringify(OPENIAM.MenuTree.ContextTree.toJSON()),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : localeManager["openiam.ui.menu.edit.save.success"]});
					setTimeout(function() {
						if(data.redirectURL != null && data.redirectURL != undefined) {
							window.location.href = data.redirectURL;	
						} else {
							window.location.reload(true);
						}
					}, 1500);
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	deleteMenu : function(node) {
		if(node.getChild() != null) {
			OPENIAM.Modal.Error(localeManager["openiam.ui.menu.edit.save.error"]);
		} else {
			OPENIAM.Modal.Warn({
				message : localeManager["openiam.ui.menu.edit.delete.warn"],
				buttons : true, 
				OK : {
					text : localeManager["openiam.ui.menu.edit.delete.confirm"],
					onClick : function() {
						if(node.getIsRoot()) {
							node.deleteTree();
						} else {
							node.deleteNode();
						}
						OPENIAM.MenuEdit.save();
					}
				},
				Cancel : {
					text : localeManager["openiam.ui.common.cancel"],
					onClick : function() {
						OPENIAM.Modal.Close();
					}
				}
			});
		}
	},
	lightbox : function(node, mode) {
		var isNew = $.inArray("NEW", mode) > -1;
		var menuTitle = (isNew) ? localeManager["openiam.ui.menu.create.new"] : localeManager["openiam.ui.menu.edit"];
		var $dialog = null;
		
		var htmlForm = $("#menuForm").clone().css("display", "block");
		htmlForm.find("#menuCancel").click(function() {
			$dialog.dialog("close");
		});
		htmlForm.find("thead th").html("Editing: " + node.getTextTrail());
		if(!isNew) {
			htmlForm.find("#menuId").val(node.getId());
			htmlForm.find("#menuName").val(node.getName());
			htmlForm.find("#menuURL").val(node.getURL());
			htmlForm.find("#menuDisplayName").languageAdmin({map : node.getDisplayNameMap()});
            htmlForm.find("#menuRisk").val(node.getRisk());

			htmlForm.find("#menuIcon").val(node.getIcon());
			htmlForm.find("#menuPublic").val("" + node.getIsPublic());
			htmlForm.find("#menuVisible").val("" + node.getIsVisible());
		} else {
			htmlForm.find("#menuId").hide();
			htmlForm.find("#menuDisplayName").languageAdmin({map : {}});
		}
		
		htmlForm.find("#menuSave").click(function() {
			var _menuName = htmlForm.find("#menuName").val();
			var _url = htmlForm.find("#menuURL").val();
			var _displayNameMap = htmlForm.find("#menuDisplayName").languageAdmin("getMap")
            var _risk = htmlForm.find("#menuRisk").val();
			var _icon = htmlForm.find("#menuIcon").val();
			var _public = (htmlForm.find("#menuPublic").val() == "true");
			var _visible = (htmlForm.find("#menuVisible").val() == "true");
			
			if(!isNew) {
				node.setName(_menuName);
				node.setURL(_url);
				node.setDisplayNameMap(_displayNameMap);
                node.setRisk(_risk);
				node.setIcon(_icon);
				node.setIsPublic(_public);
				node.setIsVisible(_visible);
				node.setDirty();
				
				var treeNode = $("#tree").dynatree("getTree").getNodeByKey(node.getId());
				treeNode.setTitle(node.getText());
				OPENIAM.MenuEdit.rerenderDirtyNodes();
				$dialog.dialog("close");
			} else {
				var opts = {
					displayNameMap : _displayNameMap,
                    risk : _risk,
					icon : _icon,
					isPublic : _public,
					name : _menuName,
					visible : _visible,
					url : _url
				};
				
				var isChild = $.inArray("CHILD", mode) > -1;
				var isBefore = $.inArray("BEFORE", mode) > -1;
				var isAfter = $.inArray("AFTER", mode) > -1;
				var _parent = isChild ? node : node.getParent();
				var _level = isChild ? node.getLevel() + 1 : node.getLevel();
				var _newMenu = new OPENIAM.MenuTree.Node(opts, _parent, _level);
				_newMenu.setDirty();
				if(isChild) {
					node.setAsFirstChild(_newMenu);
				} else if(isBefore) {
					node.moveNewNodeBefore(_newMenu);
				} else if(isAfter) {
					node.moveNewNodeAfter(_newMenu);
				}
				OPENIAM.MenuEdit.rerenderDirtyNodes();
				OPENIAM.MenuEdit.save();
			}
			$dialog.dialog("close");
			return false;
		});
		
		$dialog = $("#dialog").html(htmlForm).dialog({ autoOpen: true, draggable : false, resizable : false, title : menuTitle, width: 400});
		//$dialog.dialog('open');
	},
	bindContextMenu : function(span, isRoot) {
		$(span).contextMenu({menu: (isRoot) ? "myRootMenu" : "myMenu"}, function(action, el, pos) {
			var node = $.ui.dynatree.getNode(el);
			var mynode = OPENIAM.MenuTree.ContextTree.findById(node.data.key);
			switch( action ) {
				case "edit":
					OPENIAM.MenuEdit.lightbox(mynode, ["EDIT"]);
					break;
				case "delete":
					OPENIAM.MenuEdit.deleteMenu(mynode);
					break;
				case "addchild":
					OPENIAM.MenuEdit.lightbox(mynode, ["NEW", "CHILD"]);
					break;
				case "insertbefore":
					OPENIAM.MenuEdit.lightbox(mynode, ["NEW", "BEFORE"]);
					break;
				case "insertafter":
					OPENIAM.MenuEdit.lightbox(mynode, ["NEW", "AFTER"]);
					break;
				case "quit":
					break;
				default:
					break;
			};
		});
	},
	/*
	redrawTree : function() {
		OPENIAM.MenuEdit.displayTree(5);
	},
	*/
	displayTree : function(expandLevel) {
		$("#tree").empty();
		$("#tree").dynatree({
			children : OPENIAM.MenuTree.ContextTree.toDynaTreeView(),
			rootVisible : true,
			minExpandLevel : expandLevel || 2,
			debugLevel : 0,
			onRender: function(node, nodeSpan) {
				/*
				 * timeouts remove the class
				var mynode = OPENIAM.MenuTree.ContextTree.findById(node.data.key);
				if(mynode != null && mynode != undefined && mynode.getIsDirty() == true) {
					setTimeout(function() {
						$(nodeSpan).addClass("dirty");
					}, 200);
				}
				*/
			},
			onClick: function(node, event) {
				// Close menu on click
				var mynode = OPENIAM.MenuTree.ContextTree.findById(node.data.key);
				console.log(mynode);
				
				$("#menuMetadata .id").html(mynode.id);
				$("#menuMetadata .name").html(mynode.name);
				$("#menuMetadata .url").html(mynode.url);
				$("#menuMetadata .displayName").html(mynode.displayName);
                $("#menuMetadata .risk").html(mynode.risk);

				$("#menuMetadata .icon").html(mynode.icon);
				$("#menuMetadata .public").html("" + mynode.isPublic);
				$("#menuMetadata .visble").html("" +mynode.isVisible);
				$("#menuInformation").show();
				
				
				$("#menuUsers").menuEntitlements("fire", node.data.key);
				$("#menuGroups").menuEntitlements("fire", node.data.key);
				$("#menuRoles").menuEntitlements("fire", node.data.key);;
				
				if( $(".contextMenu:visible").length > 0 ){
					$(".contextMenu").hide();
				}
			},
			onKeydown: function(node, event) {
				// Eat keyboard events, when a menu is open
				if( $(".contextMenu:visible").length > 0 ) {
					return false;
				}
			},
			onCreate: function(node, span){
				var myNode = OPENIAM.MenuTree.ContextTree.findById(node.data.key);
				OPENIAM.MenuEdit.bindContextMenu(span, myNode.getIsRoot());
			},
			dnd: {
				preventVoidMoves: true, // Prevent dropping nodes 'before self', etc.
				onDragStart: function(node) {
					var id = node.data.key;
					var realNode = OPENIAM.MenuTree.ContextTree.findById(id);
					if(realNode != null && realNode != undefined) {
						if(realNode.getIsRoot()) {
							return false;
						}
					}
					return true;
				},
				onDragEnter: function(node, sourceNode) {
					if(node.parent !== sourceNode.parent){
						return false;
					}
					return ["before", "after"];
				},
				onDrop: function(node, sourceNode, hitMode, ui, draggable) {
					/** This function MUST be defined to enable dropping of items on
					 *  the tree.
					 */
					sourceNode.move(node, hitMode);
					var source = OPENIAM.MenuTree.ContextTree.findById(sourceNode.data.key);
					var dest = OPENIAM.MenuTree.ContextTree.findById(node.data.key);
					if(hitMode == "before") {
						source.moveBefore(dest);
					} else if(hitMode =="after") {
						source.moveAfter(dest);
					}
					OPENIAM.MenuEdit.rerenderDirtyNodes();
				}
			}
		});
		
		$("#expandTree").click(function() {
		
		});
		
		$("#collapseTree").click(function() {
		
		});
	},
	rerenderDirtyNodes : function() {
		var tree = $("#tree").dynatree("getTree");
		$.each(OPENIAM.MenuTree.ContextTree.getDirtyNodes(), function(idx, val) {
			var node = tree.getNodeByKey(val.getId());
			if(node != null && node != undefined) { /* new nodes are null */
				node.data.addClass = "dirty";
				node.render();
			}
		});
	}
};

$(document).ready(function() {
	OPENIAM.MenuTree.ContextTree.initialize({tree : OPENIAM.ENV.EditableMenuTree});
	//$("#menu-container").append(OPENIAM.MenuTree.ContextTree.toHTML());
	OPENIAM.MenuEdit.displayTree();
	$("#menuTreeSave").click(function() {
		OPENIAM.MenuEdit.save();
		return false;
	});
	
	$("#menuUsers").menuEntitlements({type : "users", title : localeManager["openiam.ui.menu.entitlements.users"]});
	$("#menuGroups").menuEntitlements({type : "groups", title : localeManager["openiam.ui.menu.entitlements.groups"]});
	$("#menuRoles").menuEntitlements({type : "roles", title : localeManager["openiam.ui.menu.entitlements.roles"]});
});

$(window).load(function() {
	
});