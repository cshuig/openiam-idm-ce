OPENIAM = window.OPENIAM || {};

OPENIAM.Policy = {
	size : 10,
	
	getSize : function() {
		return this.size;
	},
	submit : function() {
		$("#passwordPolicyForm").submit();
	},
	resetPage : function() {
		$("#page").val(0);
	}
};

OPENIAM.Policy.Table = {
	init : function() {
		var offsetPage = OPENIAM.ENV.page;
		$("#tableOne").tablesorter({ 
			debug: false, 
			sortDisabled : true,
			/*sortList: [[0, 0]], */
			widgets: ['zebra'] }).tablesorterPager({ 
				container: $("#pagerOne"), 
				positionFixed: false,
				size : OPENIAM.Policy.getSize(),
				page : offsetPage,
				totalRows : OPENIAM.ENV.count,
				totalPages : OPENIAM.ENV.totalPages,
				onFirstClick : function() {
					$("#page").val(0);
					OPENIAM.Policy.submit();
				},
				onLastClick : function() {
					$("#page").val((OPENIAM.ENV.totalPages - 1));
					OPENIAM.Policy.submit();
				},
				onPrevClick : function() {
					if(offsetPage > 0) {
						$("#page").val((offsetPage - 1));
					}
					OPENIAM.Policy.submit();
				},
				onNextClick : function() {
					if(offsetPage < OPENIAM.ENV.totalPages) {
						$("#page").val((offsetPage + 1));
					}
					OPENIAM.Policy.submit();
				}
			});
			
			
		$("#tableOne tbody tr").click(function() {
			window.location.href = "editPolicy.html?id=" + $(this).attr("entityId");
		});
	}	
};

$(document).ready(function() {
	
	OPENIAM.Policy.Table.init();
});

$(window).load(function() {
	
});