OPENIAM = window.OPENIAM || {};

Object.size = function(obj) {
    var size = 0, key;
    if(obj != null && obj != undefined) {
    	for (key in obj) {
	        if (obj.hasOwnProperty(key)) size++;
	    }
    }
    return size;
};

OPENIAM.Matrix = function(args) {
	var $this = this;
	this.groups = {};
	this.roles = {};
	this.resources = {};
	this.publicResources = {};
	this.metadata = new OPENIAM.Matrix.MetaData(args);
	
	this.treeGroupMap = {};
	this.treeRoleMap = {};
	this.treeResourceMap = {};
	if(args.groupIds != null) {
		$.each(args.groupIds, function(idx, id) {
			if(id != null) {
				$this.groups[id] = $this.metadata.getGroup(id);
			}
		});
	}
	
	if(args.roleIds != null) {
		$.each(args.roleIds, function(idx, id) {
			if(id != null) {
				$this.roles[id] = $this.metadata.getRole(id);
			}
		});
	}
	
	var allResourceIdCopy = {};
	if(args.resourceIds != null) {
		$.each(args.resourceIds, function(idx, id) {
			if(id != null) {
				allResourceIdCopy[id] = true;
				$this.resources[id] = $this.metadata.getResource(id);
			}
		});
	}
	
	if(args.publicResourceIds != null) {
		$.each(args.publicResourceIds, function(idx, id) {
			if(id != null) {
				allResourceIdCopy[id] = true;
				$this.publicResources[id] = $this.metadata.getResource(id);
			}
		});
	}
	
	var allResourceIds = [];
	for(var i in allResourceIdCopy) {
		allResourceIds.push(i);
	}
	
	$.each(allResourceIds, function(idx, id) {
		var root = $this.metadata.getResource(id).getRoot();
		if(root != null) {
			$this.treeResourceMap[root.getId()] = root;
		}
	});
	
	$.each(args.roleIds, function(idx, id) {
		var root = $this.metadata.getRole(id).getRoot();
		if(root != null) {
			$this.treeRoleMap[root.getId()] = root;
		}
	});
	
	$.each(args.groupIds, function(idx, id) {
		var root = $this.metadata.getGroup(id).getRoot();
		if(root != null) {
			$this.treeGroupMap[root.getId()] = root;
		}
	});
	
	this.compiledGroups = null;
	this.compiledRoles = null;
	this.compiledResources = null;
};

OPENIAM.Matrix.prototype = {
	getCompiledGroups : function() {
		if(this.compiledGroups == null) {
			var groupSet = {};
			$.each(this.groups, function(idx, entity) {
				groupSet[entity.getId()] = entity;
				$.each(entity.getCompiledParents(), function(idx, parent) {
					groupSet[parent.getId()] = parent;
				});
			});
			this.compiledGroups = groupSet;
		}
		return this.compiledGroups;
	},
	getCompiledRoles : function() {
		if(this.compiledRoles == null) {
			var compiledGroups = this.getCompiledGroups();
			var directRoles = {};
			$.each(compiledGroups, function(idx, group) {
				$.each(group.getRoles(), function(idx2, role) {
					directRoles[role.getId()] = role;
				});	
			});
			
			$.each(this.roles, function(idx, role) {
				directRoles[role.getId()] = role;
			});
			
			var roleSet = {};
			$.each(directRoles, function(idx, entity) {
				roleSet[entity.getId()] = entity;
				$.each(entity.getCompiledParents(), function(idx, parent) {
					roleSet[parent.getId()] = parent;
				});
			});
			this.compiledRoles = roleSet;
		}
		return this.compiledRoles;
	},
	getCompiledResources : function() {
		if(this.compiledResources == null) {
			var groups = this.getCompiledGroups();
			var roles = this.getCompiledRoles();
			
			var resources = {};
			$.each(groups, function(idx, entity){
				$.each(entity.getResources(), function(idx, resource) {
					resources[resource.getId()] = resource;
				});
			});
			
			$.each(roles, function(idx, entity){
				$.each(entity.getResources(), function(idx, resource) {
					resources[resource.getId()] = resource;
				});
			});
			
			$.each(this.resources, function(idx, resource) {
				resources[resource.getId()] = resource;
			});
			
			$.each(this.publicResources, function(idx, resource) {
				resources[resource.getId()] = resource;
			});
			
			var resourceSet = {};
			$.each(resources, function(idx, entity) {
				resourceSet[entity.getId()] = entity;
				$.each(entity.getCompiledParents(), function(idx, parent) {
					resourceSet[parent.getId()] = parent;
				});
			});
			this.compiledResources = resourceSet;
		}
		return this.compiledResources;
	},
	isDirectlyEntitledTo : function(entity) {
		var retVal = null;
		if(entity.getType() == "GROUP") {
			retVal = (this.groups[entity.getId()]);
		} else if(entity.getType() == "ROLE") {
			retVal = (this.roles[entity.getId()]);
		} else if(entity.getType() == "RESOURCE") {
			retVal = (this.resources[entity.getId()]);
		}
		return (retVal != null && retVal != undefined);
	},
	isPublicResource : function(entity) {
		var retVal = this.publicResources[entity.getId()];
		return (retVal != null && retVal != undefined);
	},
	getEntitlementReasons : function(entity) {
		var reasons = [];
			/*
			if(entity.getType() == "GROUP") {
				if(this.isDirectlyEntitledTo(entity)) {
					reasons.push("Because the user is a Direct Member of this Group");
				} else {
					
				}
			} else if(entity.getType() == "ROLE") {
				if(this.isDirectlyEntitledTo(entity)) {
					reasons.push("Because the user is a Direct Member of this Role");
				}
			} else if(entity.getType() == "RESOURCE") {
				if(this.isDirectlyEntitledTo(entity)) {
					reasons.push("Because the user is  Directly Entitled to this resource");
				}
				
				if(this.isPublicResource(entity)) {
					reasons.push("This resource is public");
				}
			}
		*/
		return reasons;
	},
	toListDynaTreeView : function() {
		var children = [];
			var obj = {};
				obj.title = localeManager["openiam.ui.user.entitlement.tree"];
				obj.key = "DYNAMETA_" + new Date().getTime();
				obj.children = [];
				obj.children.push(this.getRoleDynatree());
				obj.children.push(this.getGroupDynatree());
				obj.children.push(this.getResourceDynatree());
				obj.children.push(this.getPublicResourceDynatree());
			children.push(obj);
		return children;
	},
	getRoleDynatree : function() {
		var obj = {};
		obj.title = localeManager["openiam.ui.user.entitlement.role"];
		obj.key = "DYNAMETA_" + new Date().getTime();
		obj.children = [];
		obj.icon = "spacer.png";
		$.each(this.roles, function(id, role) {
			$.each(role.toListDynaTreeView(), function(idx, entry) {
				obj.children.push(entry);
			});
		});
		return obj;
	},
	getGroupDynatree : function() {
		var obj = {};
		obj.title = localeManager["openiam.ui.user.entitlement.group"];
		obj.key = "DYNAMETA_" + new Date().getTime();
		obj.children = [];
		obj.icon = "spacer.png";
		$.each(this.groups, function(id, group) {
			$.each(group.toListDynaTreeView(), function(idx, entry) {
				obj.children.push(entry);
			});
		});
		return obj;
	},
	getResourceDynatree : function() {
		var obj = {};
		obj.title = localeManager["openiam.ui.user.entitlement.resource"];
		obj.key = "DYNAMETA_" + new Date().getTime();
		obj.children = [];
		obj.icon = "spacer.png";
		$.each(this.resources, function(id, resource) {
			$.each(resource.toListDynaTreeView(), function(idx, entry) {
				obj.children.push(entry);
			});
		});
		return obj;
	},
	getPublicResourceDynatree : function() {
		var obj = {};
		obj.title = localeManager["openiam.ui.user.entitlement.resource.public"];
		obj.key = "DYNAMETA_" + new Date().getTime();
		obj.children = [];
		obj.icon = "spacer.png";
		$.each(this.publicResources, function(id, resource) {
			$.each(resource.toListDynaTreeView(), function(idx, entry) {
				obj.children.push(entry);
			});
		});
		return obj;
	},
	toResourceTreeDynaTreeView : function() {
		var $this = this;
		var _children = [];
		var obj = {};
		obj.title = (Object.size(this.treeResourceMap) > 0) ? localeManager["openiam.ui.user.entitlement.resource.treeview.title"] : localeManager["openiam.ui.user.entitlement.resource.not.found"];
		obj.key = "DYNAMETA_" + new Date().getTime();
		obj.children = [];
		obj.icon = "spacer.png";
		
		var compiledResources = this.getCompiledResources();
		$.each(this.treeResourceMap, function(idx, resource) {
			$.each(resource.toTreeDynaTreeView(compiledResources), function(idx, entry) {
				obj.children.push(entry);
			});
		});
		_children.push(obj);
		return _children;
	},
	toGroupTreeDynaTreeView : function() {
		var _children = [];
		var obj = {};
		obj.title = (Object.size(this.treeGroupMap) > 0) ? localeManager["openiam.ui.user.entitlement.group"] : localeManager["openiam.ui.user.entitlement.group.not.found"];
		obj.key = "DYNAMETA_" + new Date().getTime();
		obj.children = [];
		obj.icon = "spacer.png";
		
		var compiledParents = this.getCompiledGroups();
		$.each(this.treeGroupMap, function(idx, group) {
			$.each(group.toTreeDynaTreeView(compiledParents), function(idx, entry) {
				obj.children.push(entry);
			});
		});
		_children.push(obj);
		return _children;
	},
	toRoleTreeDynaTreeView : function() {
		var _children = [];
		var obj = {};
		obj.title = (Object.size(this.treeRoleMap) > 0) ? localeManager["openiam.ui.user.entitlement.role"] : localeManager["openiam.ui.user.entitlement.role.not.found"];
		obj.key = "DYNAMETA_" + new Date().getTime();
		obj.children = [];
		obj.icon = "spacer.png";
		
		var compiledParents = this.getCompiledRoles();
		$.each(this.treeRoleMap, function(idx, role) {
			$.each(role.toTreeDynaTreeView(compiledParents), function(idx, entry) {
				obj.children.push(entry);
			});
		});
		_children.push(obj);
		return _children;
	}
};

OPENIAM.Matrix.MetaData = function(args) {
	$this = this;
	this.groupMap = {};
	this.roleMap = {};
	this.resourceMap = {};
	
	if(args.groupMap != null) {
		$.each(args.groupMap, function(idx, val) {
			var entity = new OPENIAM.Matrix.Group(val);
			$this.groupMap[entity.getId()] = entity;
		});
	}
	
	if(args.resourceMap != null) {
		$.each(args.resourceMap, function(idx, val) {
			var entity = new OPENIAM.Matrix.Resource(val);
			$this.resourceMap[entity.getId()] = entity;
		});
	}
	
	if(args.roleMap != null) {
		$.each(args.roleMap, function(idx, val) {
			var entity = new OPENIAM.Matrix.Role(val);
			$this.roleMap[entity.getId()] = entity;
		});
	}
	
	this.groupToGroupMap = args.groupToGroupMap || null;
	this.groupToResourceMap = args.groupToResourceMap || null;
	this.groupToRoleMap = args.groupToRoleMap || null;
	
	this.roleToResourceMap = args.roleToResourceMap || null;
	this.roleToRoleMap = args.roleToRoleMap || null;
	
	this.resourceToResourceMap = args.resourceToResourceMap || null;
	this.init();
};

OPENIAM.Matrix.MetaData.prototype = {
	init : function() {
		var $this = this;
		if(this.resourceToResourceMap != null) {
			$.each(this.resourceToResourceMap, function(memberId, parentIds) {
				if(parentIds != null) {
					$.each(parentIds, function(idx, parentId) {
						if(parentId != null) {
							$this.getResource(memberId).addParent($this.getResource(parentId));
							$this.getResource(parentId).addInvertedParent($this.getResource(memberId))
						}
					});
				}
			});
		}
		
		if(this.roleToRoleMap != null) {
			$.each(this.roleToRoleMap, function(memberId, parentIds) {
				if(parentIds != null) {
					$.each(parentIds, function(idx, parentId) {
						if(parentId != null) {
							$this.getRole(memberId).addParent($this.getRole(parentId));
							$this.getRole(parentId).addInvertedParent($this.getRole(memberId));
						}
					});
				}
			});
		}
		
		if(this.roleToResourceMap != null) {
			$.each(this.roleToResourceMap, function(memberId, resourceIds) {
				if(resourceIds != null) {
					$.each(resourceIds, function(idx, resourceId) {
						if(resourceId != null) {
							$this.getRole(memberId).addResource($this.getResource(resourceId));
						}
					});
				}
			});
		}
		
		if(this.groupToGroupMap != null) {
			$.each(this.groupToGroupMap, function(memberId, parentIds) {
				if(parentIds != null) {
					$.each(parentIds, function(idx, parentId) {
						if(parentId != null) {
							$this.getGroup(memberId).addParent($this.getGroup(parentId));
							$this.getGroup(parentId).addInvertedParent($this.getGroup(memberId));
						}
					});
				}
			});
		}
		
		if(this.groupToResourceMap != null) {
			$.each(this.groupToResourceMap, function(memberId, resourceIds) {
				if(resourceIds != null) {
					$.each(resourceIds, function(idx, resourceId) {
						if(resourceId != null) {
							$this.getGroup(memberId).addResource($this.getResource(resourceId));
						}
					});
				}
			});
		}
		
		
		if(this.groupToRoleMap != null) {
			$.each(this.groupToRoleMap, function(memberId, roleIds) {
				if(roleIds != null) {
					$.each(roleIds, function(idx, roleId) {
						if(roleId != null) {
							$this.getGroup(memberId).addRole($this.getRole(roleId));
						}
					});
				}
			});
		}
	},
	getResource : function(resourceId) {
		var val = this.resourceMap[resourceId];
		return (val != null && val != undefined) ? val : null;
	},
	getGroup : function(groupId) {
		var val = this.groupMap[groupId];
		return (val != null && val != undefined) ? val : null;
	},
	getRole : function(roleId) {
		var val = this.roleMap[roleId];
		return (val != null && val != undefined) ? val : null;
	}
};

OPENIAM.Matrix.Role = function(args) {
	this.id = args.id;
	this.name = args.name;
	this.resources = {};
	this.parents = {};
	this.invertedParents = {};
};

OPENIAM.Matrix.Role.prototype = {
	getId : function() {
		return this.id;
	},
	getName : function() {
		return this.name;	
	},
	getType : function() {
		return "ROLE";
	},
	addParent: function(role) {
		if(role != null && this.getParent(role.getId()) == null) {
			this.parents[role.getId()] = role;
		}
	},
	getParents : function() {
		return this.parents;
	},
	getCompiledParents : function() {
		var parentSet = {};
		$.each(this.parents, function(idx, entity) {
			parentSet[entity.getId()] = entity;
			$.each(entity.getCompiledParents(), function(idx, parent) {
				parentSet[parent.getId()] = parent;
			});
		});
		return parentSet;
	},
	getParent : function(id) {
		var val = this.parents[id];
		return (val != null && val != undefined) ? val : null;
	},
	addResource : function(resource) {
		if(resource != null && this.getResource(resource.getId()) == null) {
			this.resources[resource.getId()] = resource;
		}
	},
	getResource : function(id) {
		var val = this.resources[id];
		return (val != null && val != undefined) ? val : null;
	},
	getResources : function() {
		return this.resources;
	},
	addInvertedParent : function(role) {
		if(role != null && this.getInvertedParent(role.getId()) == null) {
			this.invertedParents[role.getId()] = role;
		}
	},
	getInvertedParent : function(id) {
		var val = this.invertedParents[id];
		return (val != null && val != undefined) ? val : null;
	},
	isRoot : function() {
		return (Object.size(this.parents) == 0);
	},
	getRoot : function() {
		var retVal = null;
		if(this.isRoot()) {
			retVal = this;
		} else {
			$.each(this.getParents(), function(idx, role) {
				if(retVal == null) {
					retVal = role.getRoot();
				}
			});
		}
		return retVal;
	},
	toTreeDynaTreeView : function(compiledIds) {
		var _children = [];
			var actualObj = {};
			actualObj.title = this.getName();
			actualObj.key = "ROLE_" + this.getId();
			actualObj.children = [];
			actualObj.icon = "role.png";
			actualObj.addClass = "role";
			actualObj.entId = this.getId();
			
			if(Object.size(this.invertedParents) > 0) {
				$.each(this.invertedParents, function(id, role) {
					if(compiledIds[role.getId()]) {
						$.each(role.toTreeDynaTreeView(compiledIds), function(idx, entry) {
							actualObj.children.push(entry);
						});
					}
				});
			}
			
		_children.push(actualObj);
		return _children;
	},
	toListDynaTreeView : function() {
		var _children = [];
			var actualObj = {};
			actualObj.title = this.getName();
			actualObj.key = "ROLE_" + this.getId();
			actualObj.children = [];
			actualObj.icon = "role.png";
			actualObj.addClass = "role";
			actualObj.entId = this.getId();
		
			if(Object.size(this.resources) > 0) {
				var resourceObj = {};
				resourceObj.title = localeManage["openiam.ui.user.entitlement.resource.title"]+":";
				resourceObj.key = "DYNAMETA_" + new Date().getTime();
				resourceObj.children = [];
				resourceObj.icon = "spacer.png"
				$.each(this.resources, function(id, resource) {
					$.each(resource.toListDynaTreeView(), function(idx, entry) {
						resourceObj.children.push(entry);
					});
				});
				actualObj.children.push(resourceObj);
			}
			
			if(Object.size(this.parents) > 0) {
				var roleObj = {};
				roleObj.title = localeManage["openiam.ui.user.entitlement.role.inherit"]+":";
				roleObj.key = "DYNAMETA_" + new Date().getTime();
				roleObj.children = [];
				roleObj.icon = "spacer.png";
				$.each(this.parents, function(id, role) {
					$.each(role.toListDynaTreeView(), function(idx, entry) {
						roleObj.children.push(entry);
					});
				});
				actualObj.children.push(roleObj);
			}
		_children.push(actualObj);
		return _children;
	}
};

OPENIAM.Matrix.Group = function(args) {
	this.id = args.id;
	this.name = args.name;
	this.parents = {};
	this.roles = {};
	this.resources = {};
	this.invertedParents = {};
};

OPENIAM.Matrix.Group.prototype = {
	getId : function() {
		return this.id;
	},
	getName : function() {
		return this.name;	
	},
	getType : function() {
		return "GROUP";
	},
	addParent: function(group) {
		if(group != null && this.getParent(group.getId()) == null) {
			this.parents[group.getId()] = group;
		}
	},
	getParent : function(id) {
		var val = this.parents[id];
		return (val != null && val != undefined) ? val : null;
	},
	getParents : function() {
		return this.parents;
	},
	getCompiledParents : function() {
		var parentSet = {};
		$.each(this.parents, function(idx, entity) {
			parentSet[entity.getId()] = entity;
			$.each(entity.getCompiledParents(), function(idx, parent) {
				parentSet[parent.getId()] = parent;
			});
		});
		return parentSet;
	},
	addResource : function(resource) {
		if(resource != null && this.getResource(resource.getId()) == null) {
			this.resources[resource.getId()] = resource;
		}
	},
	addInvertedParent : function(group) {
		if(group != null && this.getInvertedParent(group.getId()) == null) {
			this.invertedParents[group.getId()] = group;
		}
	},
	getInvertedParent : function(id) {
		var val = this.invertedParents[id];
		return (val != null && val != undefined) ? val : null;
	},
	getResource : function(id) {
		var val = this.resources[id];
		return (val != null && val != undefined) ? val : null;
	},
	getResources : function() {
		return this.resources;
	},
	addRole : function(role) {
		if(role != null && this.getRole(role.getId()) == null) {
			this.roles[role.getId()] = role;
		}
	},
	getRole : function(id) {
		var val = this.roles[id];
		return (val != null && val != undefined) ? val : null;
	},
	getRoles : function() {
		return this.roles;
	},
	isRoot : function() {
		return (Object.size(this.parents) == 0);
	},
	getRoot : function() {
		var retVal = null;
		if(this.isRoot()) {
			retVal = this;
		} else {
			$.each(this.getParents(), function(idx, group) {
				if(retVal == null) {
					retVal = group.getRoot();
				}
			});
		}
		return retVal;
	},
	toTreeDynaTreeView : function(compiledIds) {
		var _children = [];
			var actualObj = {};
			actualObj.title = this.getName();
			actualObj.key = "GROUP_" + this.getId();
			actualObj.children = [];
			actualObj.addClass = "group";
			actualObj.icon = "group.png";
			actualObj.entId = this.getId();
			
			if(Object.size(this.invertedParents) > 0) {
				$.each(this.invertedParents, function(id, group) {
					if(compiledIds[group.getId()]) {
						$.each(group.toTreeDynaTreeView(compiledIds), function(idx, entry) {
							actualObj.children.push(entry);
						});
					}
				});
			}
			
		_children.push(actualObj);
		return _children;
	},
	toListDynaTreeView : function() {
		var _children = [];
			var actualObj = {};
			actualObj.title = this.getName();
			actualObj.key = "GROUP_" + this.getId();
			actualObj.children = [];
			actualObj.addClass = "group";
			actualObj.icon = "group.png";
			actualObj.entId = this.getId();
		
			if(Object.size(this.resources) > 0) {
				var resourceObj = {};
				resourceObj.title = localeManage["openiam.ui.user.entitlement.resource.title"]+":";
				resourceObj.key = "DYNAMETA_" + new Date().getTime();
				resourceObj.children = [];
				resourceObj.icon = "spacer.png";
				$.each(this.resources, function(id, resource) {
					$.each(resource.toListDynaTreeView(), function(idx, entry) {
						resourceObj.children.push(entry);
					});
				});
				actualObj.children.push(resourceObj);
			}
			
			if(Object.size(this.roles) > 0) {
				var roleObj = {};
				roleObj.title = localeManage["openiam.ui.user.entitlement.role.inherit"]+":";
				roleObj.key = "DYNAMETA_" + new Date().getTime();
				roleObj.children = [];
				roleObj.icon = "spacer.png";
				$.each(this.roles, function(id, role) {
					$.each(role.toListDynaTreeView(), function(idx, entry) {
						roleObj.children.push(entry);
					});
				});
				actualObj.children.push(roleObj);
			}
			
			if(Object.size(this.parents) > 0) {
				var groupObj = {};
				groupObj.title = localeManage["openiam.ui.user.entitlement.group.inherit"]+":";
				groupObj.key = "DYNAMETA_" + new Date().getTime();
				groupObj.children = [];
				groupObj.icon = "spacer.png";
				$.each(this.parents, function(id, group) {
					$.each(group.toListDynaTreeView(), function(idx, entry) {
						groupObj.children.push(entry);
					});
				});
				actualObj.children.push(groupObj);
			}
			
		_children.push(actualObj);
		return _children;
	}
};

OPENIAM.Matrix.Resource = function(args) {
	this.id = args.id;
	this.name = args.name;
	this.parents = {};
	this.invertedParents = {};
};

OPENIAM.Matrix.Resource.prototype = {
	getId : function() {
		return this.id;
	},
	getName : function() {
		return this.name;	
	},
	getType : function() {
		return "RESOURCE";
	},
	addInvertedParent : function(resource) {
		if(resource != null && this.getInvertedParent(resource.getId()) == null) {
			this.invertedParents[resource.getId()] = resource;
		}
	},
	getInvertedParent : function(id) {
		var val = this.invertedParents[id];
		return (val != null && val != undefined) ? val : null;
	},
	addParent : function(resource) {
		if(resource != null && this.getParent(resource.getId()) == null) {
			this.parents[resource.getId()] = resource;
		}
	},
	getParent : function(id) {
		var val = this.parents[id];
		return (val != null && val != undefined) ? val : null;
	},
	getParents : function() {
		return this.parents;
	},
	isRoot : function() {
		return (Object.size(this.parents) == 0);
	},
	getCompiledParents : function() {
		var parentSet = {};
		$.each(this.parents, function(idx, entity) {
			parentSet[entity.getId()] = entity;
			$.each(entity.getCompiledParents(), function(idx, parent) {
				parentSet[parent.getId()] = parent;
			});
		});
		return parentSet;
	},
	getRoot : function() {
		var retVal = null;
		if(this.isRoot()) {
			retVal = this;
		} else {
			$.each(this.getParents(), function(idx, resource) {
				if(retVal == null) {
					retVal = resource.getRoot();
				}
			});
		}
		return retVal;
	},
	toTreeDynaTreeView : function(compiledIds) {
		var _children = [];
			var actualObj = {};
			actualObj.title = this.getName();
			actualObj.key = "RESOURCE_" + this.getId();
			actualObj.children = [];
			actualObj.addClass = "resource";
			actualObj.icon = "resource.png";
			actualObj.entId = this.getId();
			
			if(Object.size(this.invertedParents) > 0) {
				$.each(this.invertedParents, function(id, resource) {
					if(compiledIds[resource.getId()]) {
						$.each(resource.toTreeDynaTreeView(compiledIds), function(idx, entry) {
							actualObj.children.push(entry);
						});
					}
				});
			}
			
		_children.push(actualObj);
		return _children;
	},
	toListDynaTreeView : function() {
		var _children = [];
			var obj = {};
			obj.title = this.getName();
			obj.key = "RESOURCE_" + this.getId();
			obj.children = [];
			obj.addClass = "resource";
			obj.icon = "resource.png";
			obj.entId = this.getId();
			$.each(this.parents, function(id, resource) {
				$.each(resource.toListDynaTreeView(), function(idx, entry) {
					obj.children.push(entry);
				});
			});
		_children.push(obj);
		return _children;
	}
};


OPENIAM.EntitlementsView = {
	_typeEvent : null,
	matrix : null,
	load : function() {
		var $this = this;
		$.ajax({
			url : "getUserEntitlementsMatrix.html",
			data : {id : OPENIAM.ENV.UserId},
			type: "GET",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				$this.matrix = new OPENIAM.Matrix(data);
				$this.render();
				$this.bind();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	render : function() {
		if(OPENIAM.ENV.ViewType == "list") {
			this.ListView.bind();
			this.ListView.render();
		} else {
			this.TreeView.bind();
			this.TreeView.render();
		}
	},
	bind : function() {
		var $this = this;
		$("#entityAutocomplete").keyup(function(e) {
			var elmt = this;
			if(!$(this).is(":disabled")) {
				if($this._typeEvent != null) {
					clearTimeout($this._typeEvent);
				}
				$this._typeEvent = setTimeout(function() {
					OPENIAM.AjaxLoader.show();
					$(elmt).attr("disabled", true);
					$this.hideNotMatching($(elmt).val());
					$(elmt).attr("disabled", false);
					OPENIAM.AjaxLoader.hide();
				}, 1000);
			}
		});
	},
	/* returns true or false.  true means hide.  False means show */
	transverseHide : function(node, val) {
		var hideMe = false;
		var $this = this;
		var addClass = node.data.addClass;
		
		var li = node.li;
		var isEntity = (addClass == "resource" || addClass == "group" || addClass == "role");
		if(isEntity) {
			var title = node.data.title;
			title = (typeof(title) === "string") ? title.toLowerCase() : "";
			if(val == "") {
				hideMe = false;
				$(li).unhighlight();
			} else if(title.indexOf(val) == -1) {
				hideMe = true;
				$(li).unhighlight();
			} else {
				hideMe = false;
				$(li).unhighlight();
				$(li).highlight(val);
			}
		}
		
		var anyChildIsVisible = false;
		if(node.childList != null && node.childList != undefined) {
			$.each(node.childList, function(idx, child) {
				if(!$this.transverseHide(child, val)) {
					anyChildIsVisible = true;
				}
			});
		}
		
		if(anyChildIsVisible) {
			hideMe = false;
		}
		
		if(!anyChildIsVisible && !isEntity) {
			hideMe = true;
		}
		
		if(hideMe) {
			$(li).hide();
		} else {
			$(li).show();
		}
		return hideMe;
	},
	hideNotMatching : function(val) {
		val = (typeof(val) === "string") ? val.toLowerCase() : "";
		if(val.length >= 3 || val == "") {
			var $this = this;
			var root = $("#tree").dynatree("getRoot");
			if(root.childList != null && root.childList != undefined) {
				$.each(root.childList, function(idx, child) {
					$this.transverseHide(child, val);
				});
			}
		}
	},
	showMetadata : function(entity) {
		$("#entitlementInformation").show();
		$("#entitlementMetadata .id").text(entity.getId());
		$("#entitlementMetadata .name").text(entity.getName());
		$("#entitlementMetadata .type").text(entity.getType());
		
		var reasonsElmt = $("#entitlementMetadata .reasons");
		var reasons = this.matrix.getEntitlementReasons(entity);
		
		reasonsElmt.empty();
		if(reasons.length == 0) {
			$("#entitlementReasons").hide();
		} else {
			$("#entitlementReasons").show();
			$.each(reasons, function(idx, reason) {
				var li = document.createElement("li"); $(li).addClass("reason"); li.innerHTML = reason;
				reasonsElmt.append(li);
			});
		}
	},
	hideMetadata : function() {
		$("#entitlementInformation").hide();
	},
	hideTree : function() {
		$("#tree").hide();
	},
	showTree : function() {
		$("#tree").show();
	},
	renderTree : function(dynaTreeChildCalllback) {
		if($("#tree").attr("initialized") == "true") {
			$("#tree").dynatree('destroy');
		}
		$("#tree").attr("initialized", true);
		$("#tree").dynatree({
			children : dynaTreeChildCalllback(),
			rootVisible : true,
			minExpandLevel : 100,
			debugLevel : 0,
			onRender: function(node, nodeSpan) {
				
			},
			onClick: function(node, event) {
				var data = node.data;
				var key = data.key;
				if(key) {
					var addClass = data.addClass;
					
					var getFunction = null;
					var idPrefix = null;
					if(addClass == "resource") {
						idPrefix = "RESOURCE_";
						getFunction = OPENIAM.EntitlementsView.matrix.metadata.getResource;
					} else if(addClass == "group") {
						idPrefix = "GROUP_";
						getFunction = OPENIAM.EntitlementsView.matrix.metadata.getGroup;
					} else if(addClass == "role") {
						idPrefix = "ROLE_";
						getFunction = OPENIAM.EntitlementsView.matrix.metadata.getRole;
					}
					if(idPrefix && getFunction) {
						key = key.substring(key.indexOf(idPrefix) + idPrefix.length);
						var entity = getFunction.call(OPENIAM.EntitlementsView.matrix.metadata, key);
						OPENIAM.EntitlementsView.showMetadata(entity);
					} else {
						OPENIAM.EntitlementsView.hideMetadata();
					}
				}
			},
			onKeydown: function(node, event) {
				// Eat keyboard events, when a menu is open
				if( $(".contextMenu:visible").length > 0 ) {
					return false;
				}
			},
			onCreate: function(node, span){
				
			}
		});
	},
	TreeView : {
		_getCallback : function() {
			var val = $("#entityType").val(); 
			var callback = null;
			if(val == "group") {
				callback = function() {return OPENIAM.EntitlementsView.matrix.toGroupTreeDynaTreeView();};
			} else if(val == "role") {
				callback = function() {return OPENIAM.EntitlementsView.matrix.toRoleTreeDynaTreeView();};
			} else if(val == "resource") {
				callback = function() {return OPENIAM.EntitlementsView.matrix.toResourceTreeDynaTreeView();};
			}
			return callback;
		},
		bind : function() {
			var $this = this;
			$("#entityType").change(function() {
				var callback = $this._getCallback();
				if(callback != null) {
					OPENIAM.EntitlementsView.renderTree(callback);
				}
			});
		},
		render : function() {
			var callback = this._getCallback();
			if(callback != null) {
				OPENIAM.EntitlementsView.renderTree(callback);
			}
		}
	},
	ListView : {
		bind : function() {
			$("#entityAutocomplete").show();
		},
		render : function() {
			OPENIAM.EntitlementsView.renderTree(function() {return OPENIAM.EntitlementsView.matrix.toListDynaTreeView();});
		}
	}
}


$(document).ready(function() {
	OPENIAM.EntitlementsView.load();
});

$(window).load(function() {

});