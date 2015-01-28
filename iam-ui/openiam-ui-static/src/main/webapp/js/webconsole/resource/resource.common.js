OPENIAM.MenuTree.InnerTree = Object.create(OPENIAM.MenuTree);

OPENIAM.MenuTree.InnerTree.toHTML = function() {
	var userMenu = document.createElement("div"); userMenu.id = "usermenu";
	if(this._root != null) {
		var node = this._root.getChild();
		while(node != null) {
			userMenu.appendChild(node.toHTML());
			node = node.getNext();
		}
	}
	return userMenu;
};

OPENIAM.MenuTree.Node.prototype.toHTML = function() {
	var li = document.createElement("li");
		var a = document.createElement("a"); $(a).attr("href", this.getURL()); a.innerHTML = this.getText();
		/*
		$(a).click(function() {
			that.onClick();
		});
		*/
	$(li).append(a);
	return li;
};

OPENIAM.MenuTree.Node.prototype.onClick = function() {
	
};

$(document).ready(function() {
	OPENIAM.MenuTree.InnerTree.initialize(OPENIAM.ENV.MenuTree);
	$("#title").append(OPENIAM.MenuTree.InnerTree.toHTML());
});