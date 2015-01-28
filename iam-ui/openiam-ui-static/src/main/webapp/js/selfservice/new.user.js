OPENIAM = window.OPENIAM || {};

OPENIAM.EditProfileBootstrap.findUsers = function(onEntityClick) {
    $("#dialog").userSearchForm(
            {
                afterFormAppended : function() {
                    $("#dialog").dialog({
                        autoOpen : false,
                        draggable : false,
                        resizable : false,
                        title : localeManager["openiam.ui.common.search.users"],
                        width : "855px",
                        position : "center"
                    });
                    $("#dialog").dialog("open");
                },
                onSubmit : function(json) {
                    $("#dialog").userSearchResults(
                            {
                                "jsonData" : json,
                                "page" : 0,
                                "size" : 20,
                                initialSortColumn : "name",
                                initialSortOrder : "ASC",
                                url : "rest/api/users/search",
                                emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                                emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
                                columnHeaders : [ localeManager["openiam.ui.common.name"], localeManager["openiam.ui.common.phone.number"],
                                        localeManager["openiam.ui.common.email.address"], localeManager["openiam.ui.webconsole.user.status"],
                                        localeManager["openiam.ui.webconsole.user.accountStatus"] ],
                                onAppendDone : function() {

                                },
                                onEntityClick : function(bean) {
                                    $("#dialog").dialog("close");
                                    onEntityClick(bean);
                                },
                                onBack : function() {
                                    OPENIAM.EditProfileBootstrap.findUsers(onEntityClick);
                                }
                            });
                }
            });
};

$(document).ready(function() {
    OPENIAM.EditProfileBootstrap.onReady({
        postURL : $("#editProfileForm").attr("target"),
        toJSON : function() {
            var obj = {};

            var status = $("#status").val();
            status = (status != null && status != undefined && status != "") ? status : null;

            var secondaryStatus = $("#secondaryStatus").val();
            secondaryStatus = (secondaryStatus != null && secondaryStatus != undefined && secondaryStatus != "") ? secondaryStatus : null;
            obj.user = {
                id : OPENIAM.ENV.UserId,
                login : $("#login").val(),
                employeeId : $("#employeeId").val(),
                employeeTypeId : $("#employeeType").val(),
                jobCodeId : $("#jobCode").val(),
                locationCd : $("#locationCode").val(),
                locationName : $("#locationName").val(),
                mdTypeId : $("#metadataUserType").val(),
                classification : $("#classification").val(),
                middleInit : $("#middleInit").val(),
                prefix : $("#prefix").val(),
                sex : $("#gender").val(),
                status : status,
                secondaryStatus : secondaryStatus,
                suffix : $("#suffix").val(),
                title : $("#functionalTitle").val(),
                userTypeInd : $("#userTypeInd").val(),
                mailCode : $("#mailCode").val(),
                costCenter : $("#costCenter").val(),
                nickname : $("#nickname").val(),
                maidenName : $("#maidenName").val(),
                alternateContactId : $("#alternateContactHidden").val(),
                firstName : $("#firstName").val(),
                lastName : $("#lastName").val(),
                middleInit : $("#middleInit").val(),
                nickname : $("#nickname").val(),
                title : $("#functionalTitle").val(),
                sex : $("#gender").val()
            };
            obj.login = $("#login").val();
            obj.birthdateAsStr = $("#dateOfBirth").val();

            obj.endDateAsStr = $("#lastDate").val();
            obj.startDateAsStr = $("#startDate").val();

            obj.roleIds = [];
            if ($("#role").length > 0 && $("#role").selectableSearchResult("getId") != null) {
                obj.roleIds.push($("#role").selectableSearchResult("getId"));
            }

            obj.groupIds = [];
            if ($("#group").length > 0 && $("#group").selectableSearchResult("getId") != null) {
                obj.groupIds.push($("#group").selectableSearchResult("getId"));
            }

            obj.supervisorIds = [];
            if ($("#supervisorHidden").length > 0) {
                if ($("#supervisorHidden").val() != "") {
                    obj.supervisorIds.push($("#supervisorHidden").val());
                }
            }

            var hierarchy = OPENIAM.ENV.OrganizationHierarchy;
            if (hierarchy != null && hierarchy != undefined && hierarchy.length > 0 && $("#organizationsTable").length > 0) {
                obj.organizationIds = $("#organizationsTable").organizationHierarchyWrapper("getValues");
            }

            obj.pageTemplate = $("#uiTemplate").openiamUITemplate("getObject");
            obj.emails = OPENIAM.ENV.emailList, obj.phones = OPENIAM.ENV.phoneList, obj.addresses = OPENIAM.ENV.addressList
            return obj;
        }
    });

    var elements = $("#basicInfoElements").remove();
    var children = elements.children();
    children.sort(function(a, b) {
        var aOrder = parseInt($(a).attr("displayorder"));
        var bOrder = parseInt($(b).attr("displayorder"));
        return aOrder - bOrder;
    });
    var tr = null;
    var tbody = $("#basicInfoTable").find("tbody");
    children.each(function(idx, elmt) {
        if (idx == 0 || idx % 3 == 0) {
            tr = document.createElement("tr");
        }
        var td = document.createElement("td");
        $(td).append(elmt);
        $(tr).append(td);
        tbody.append(tr);
    });

    var dateCtrl = $("input.date");
    dateCtrl.datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true}).attr('readonly','readonly');

    $("#supervisorSelect").click(function() {
        OPENIAM.EditProfileBootstrap.findUsers(function(bean) {
            $("#supervisor").val(bean.name);
            $("#supervisorHidden").val(bean.id);
            $("#supervisorClear").show();
        });
        return false;
    });

    $("#supervisorClear").click(function() {
        $("#supervisor").val("");
        $("#supervisorHidden").val("");
        $(this).hide();
    });

    $("#alternateContactSelect").click(function() {
        OPENIAM.EditProfileBootstrap.findUsers(function(bean) {
            $("#alternateContact").val(bean.name);
            $("#alternateContactHidden").val(bean.id);
            $("#alternateContactClear").show();
        });
        return false;
    });

    $("#alternateContactClear").click(function() {
        $("#alternateContact").val("");
        $("#alternateContactHidden").val("");
        $(this).hide();
    });

    var hierarchy = OPENIAM.ENV.OrganizationHierarchy;
    if (hierarchy == null || hierarchy == undefined || hierarchy.length == 0 || $("#organizationsTable").length == 0) {
        $("#organizationsTable").hide();
    } else {
        $("#organizationsTable").organizationHierarchyWrapper({
            hierarchy : OPENIAM.ENV.OrganizationHierarchy,
            initialValues : OPENIAM.ENV.OrgParamList,
            draw : function(select, labelText) {
            	if(this.idx == undefined) {
            		this.idx = 0;
            		this.tr = null;
            	}
                if (this.idx == 0 || this.idx % 3 == 0) {
                    this.tr = document.createElement("tr");
                    this.append(this.tr);
                }
                var label = document.createElement("label");
                $(label).text(labelText);
                var td = document.createElement("td");
                $(td).append(label).append("<br/>");
                $(td).append(select);
                $(this.tr).append(td);
                this.idx++;
            },
            hide : function(select) {
                select.closest("td").hide();
            },
            show : function(select) {
                select.closest("td").show();
            }
        });
    }
    
    $("#role").selectableSearchResult({
		singleSearch : true,
		noneSelectedText : localeManager["openiam.ui.shared.role.search"],
		addMoreText : localeManager["openiam.ui.common.role.change"],
		onClick : function($that) {
			$("#editDialog").roleDialogSearch({
				closedDialogOnSelect : true,
				searchTargetElmt : "#editDialog",
				showResultsInDialog : true,
				onAdd : function(bean) {
					$that.selectableSearchResult("add", bean);
				},
				pageSize : 5
			});
		}
	});
	
	$("#group").selectableSearchResult({
		singleSearch : true,
		noneSelectedText : localeManager["openiam.ui.shared.group.search"],
		addMoreText : localeManager["openiam.ui.common.group.change"],
		onClick : function($that) {
			$("#editDialog").groupDialogSearch({
				closedDialogOnSelect : true,
				searchTargetElmt : "#editDialog",
				showResultsInDialog : true,
				onAdd : function(bean) {
					$that.selectableSearchResult("add", bean);
				},
				pageSize : 5
			});
		}
	});
});

$(window).load(function() {

});