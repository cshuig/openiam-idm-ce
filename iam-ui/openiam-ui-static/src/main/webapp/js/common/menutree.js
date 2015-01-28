OPENIAM = window.OPENIAM || {};

OPENIAM.MenuTree = function() {
	
};

OPENIAM.MenuTree.prototype = {
	_root : null,
	_rootId : null,
	initialize : function(args) {
		this._root = (args.tree != null) ? new OPENIAM.MenuTree.Node(args.tree, null, 0, args.onNodeClick, args.toNodeHtml) : null;
		this._rootId = this._root.getId();
		this.toHTML = args.toHTML || this.toHTML;
	},
	toHTML : function() {
		$.error("toHTML not implemented.  You should override this function");
	},
	findById : function(id) {
		var retVal = null;
		if(this._root != null && id != null && id != undefined) {
			retVal = this._root.find(id);
		}
		return retVal;
	},
	findByName : function(name) {
		var retVal = null;
		if(this.getRoot() != null && name != null && name != undefined) {
			retVal = this.getRoot().findByName(name);
		}
		return retVal;
	},
	findFirstWithURL : function() {
		var retval = null;
		if(this._root != null) {
			retval = this._root.findFirstWithURL();
		}
		return retval;
	},
	getRootId : function() {
		return this._rootId;
	},
	getRoot : function() {
		return this._root;
	}
};

OPENIAM.MenuTree.Node = function(menu, parent, level, onNodeClick, toNodeHtml) {
	this.level = level;
	this.displayNameMap = menu.displayNameMap;
	if(this.displayNameMap == null || this.displayNameMap == undefined) {
		this.displayNameMap = {};
	}
	this.displayName = menu.displayName || null;
    this.risk = menu.risk || null;
	this.icon = menu.icon || null;
	this.id = menu.id || null;
	this.isPublic = (menu.isPublic != null && menu.isPublic != undefined) ? menu.isPublic : true;
	this.isVisible = (menu.visible != null && menu.visible != undefined) ? menu.visible : true;
	this.name = menu.name || null;
	this.url = menu.url || null;
	this.order = menu.order || null;
	this.entitlementTypes = menu.entitlementType || null;
	this.onClick = onNodeClick || this.onClick;
	var _toHtml = toNodeHtml || this.toHTML;
	
	var that = this;
	this.toHTML = function() {
		return (that.getIsVisible()) ? _toHtml.call(that) : null;
	};
	
	this.parent = parent || null;
	this.isRoot = (parent == null);
	this.nextSibling = null;
	this.firstChild = null;
	this.urlParams = menu.urlParams || null;
	
	if(menu.nextSibling != null && menu.nextSibling != undefined) {
		this.nextSibling = new OPENIAM.MenuTree.Node(menu.nextSibling, this.parent, level, onNodeClick, toNodeHtml);
	}
	
	if(menu.firstChild != null && menu.firstChild != undefined) {
		this.firstChild = new OPENIAM.MenuTree.Node(menu.firstChild, this, level + 1, onNodeClick, toNodeHtml);
	}
};

OPENIAM.MenuTree.Node.prototype = {
	getIsPublic : function() {
		return this.isPublic;
	},
	getIsVisible : function() {
		return this.isVisible;
	},
	getName : function() {
		return this.name;
	},
	getIsRoot : function() {
		return this.isRoot;
	},
	getOrder : function() {
		return this.order;
	},
	getChild : function() {
		return this.firstChild;
	},
	getNext : function() {
		return this.nextSibling;
	},
	setNext : function(node) {
		this.nextSibling = node;
	},
	setFirstChild : function(node) {
		this.firstChild = node;
	},
	setOrder : function(val) {
		this.order = val;
	},
	setName : function(val) {
		this.name = val;
	},
	getDisplayNameMap : function() {
		return this.displayNameMap;
	},
	setDisplayNameMap : function(val) {
		this.displayNameMap = val;
	},
	setDisplayname : function(val) {
		this.displayName = val;
	},
    setRisk : function(val){
        this.risk = val;
    },
	setIcon : function(val) {
		this.icon = val;
	},
	setIsPublic : function(val) {
		this.isPublic = (val == "true" || val == true);
	},
	setIsVisible : function(val) {
		this.isVisible = (val == "true" || val == true);
	},
	setURL : function(val) {
		this.url = val;
	},
	getURLParams : function() {
		return this.urlParams;
	},
	setURLParams : function(arg) {
		this.urlParams = arg;
	},
	isExplicitlyEntitled : function() {
		return this._isEntitled("EXPLICIT");
	},
	isImplicitlyEntitled : function() {
		return this._isEntitled("IMPLICIT");
	},
	addExplicitEntitlement : function() {
		if(!this.isExplicitlyEntitled()) {
			if(this.entitlementTypes == null || this.entitlementTypes == undefined) {
				this.entitlementTypes = [];
			}
			this.entitlementTypes.push("EXPLICIT");
		}
	},
	removeExplicitEntitlement : function() {
		if(this.isExplicitlyEntitled()) {
			var types = this.entitlementTypes;
			var idx = -1;
			for(var i = 0; i < types.length; i++) {
				if(types[i] == "EXPLICIT") {
					idx = i;
					break;
				}
			}
			this.entitlementTypes.splice(idx, 1);
		}
	},
	_isEntitled : function(type) {
		var retval = false;
		var types = this.entitlementTypes;
		if(types != null) {
			for(var i = 0; i < types.length; i++) {
				if(types[i] == type) {
					retval = true;
					break;
				}
			}
		}
		return retval;
	},
	getFQURL : function() {
		var url = this.getURL();
		if(url != null) {
			if(url.indexOf("?") == -1) {
				url = url + "?";
			} else {
				url = url + "&";
			}
			url = url + "OPENIAM_MENU_ID=" + this.getId();
			if(this.getURLParams() != null) {
				url = url + "&" + this.getURLParams();
			}
		}
		return url;
	},
	getDeepLink : function() {
		var name = this.getName();
		if(this.getURLParams() != null) {
			name = name + "?" + this.getURLParams();
		}
		return name;
	},
	getPrev : function() {
		var prev = null;
		var parent = this.getParent();
		if(parent != null && parent != undefined) {
			prev = parent.getChild();
			if(prev.getId() == this.getId()) {
				prev = null;
			} else {
				while(prev.getId() != this.getId()) {
					if(prev.getNext().getId() == this.getId()) {
						break;
					}
					prev = prev.getNext();
				}
			}
		}
		return prev;
	},
	isFirstChild : function() {
		var retval = false;
		var parent = this.getParent();
		if(parent != null && parent != undefined) {
			retval = parent.getChild().getId() == this.getId();
		}
		return retval;
	},
	getParent : function() {
		return this.parent;
	},
	onClick : function() {
		$.error("onClick not implemented.  You should override this function");
	},
	toHTML : function() {
		$.error("toHTML not implemented.  You should override this function");
	},
	getBreadCrumbHtml : function() {
		var a = document.createElement("a"); a.href = this.getURL() || "javascript:void(0)"; a.innerHTML = this.getText();
		
		var that = this;
		$(a).click(function() {
			if($.isFunction(that.onClick)) {
				that.onClick();
			}
			return false;
		});
		return a;
	},
	toBreadCrumbs : function() {
		var elmts = [];
		
		var node = this;
		while(node != null) {
			if(!node.getIsRoot()) {
				elmts.unshift(node.getBreadCrumbHtml(), " >> ");
			}
			node = node.getParent();
		}
		elmts.splice(elmts.length - 1, 1);
		return elmts;
	},
	getId : function() {
		return this.id;
	},
	getURL : function() {
		return this.url;
	},
	getIcon : function() {
		return this.icon;
	},
	getText : function() {
		return this.displayName;
	},
    getRisk : function(){
      return this.risk;
    },
	getLevel : function() {
		return this.level;
	},
	findByName : function(name) {
		var retVal = null;
		if(name == this.getName()) {
			retVal = this;
		} else {
			if(this.getChild() != null) {
				var node = this.getChild();
				while(node != null) {
					retVal = node.findByName(id);
					if(retVal != null) {
						break;
					}
					node = node.getNext();
				}
			}
		}
		return retVal;
	},
	find : function(id) {
		var retVal = null;
		if(id == this.getId()) {
			retVal = this;
		} else {
			if(this.getChild() != null) {
				var node = this.getChild();
				while(node != null) {
					retVal = node.find(id);
					if(retVal != null) {
						break;
					}
					node = node.getNext();
				}
			}
		}
		return retVal;
	},
	findFirstWithURL : function() {
		var retVal = null;
		if(this.getURL() != null && this.getURL() != "javascript:void(0);" && !this.getIsRoot()) {
			retVal = this;
		} else {
			if(this.getChild() != null) {
				var node = this.getChild();
				while(node != null) {
					retVal = node;
					if(retVal != null && retVal.getURL() != null) {
						break;
					}
					node = node.getNext();
				}
			}
		}
		return retVal;
	}
};
