OPENIAM = window.OPENIAM || {};
OPENIAM.User = window.OPENIAM.User || {};

OPENIAM.User.Form = {
    init : function() {
        var $this = this;
        var isNew = OPENIAM.ENV.UserId == null;
        var hierarchy = OPENIAM.ENV.OrganizationHierarchy;
        if (isNew && (hierarchy != null || hierarchy != undefined || hierarchy.length > 0)) {
            $("#organizationsTable").organizationHierarchyWrapper({
                hierarchy : OPENIAM.ENV.OrganizationHierarchy,
                draw : function(select, labelText) {
                	if(this.idx == undefined) {
	            		this.idx = 0;
	            		this.tr = null;
	            	}
                    if (this.idx == 0 || this.idx % 3 == 0) {
                        this.tr = document.createElement("tr");
                        $(this).append(this.tr);
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

        var curYear = (new Date).getFullYear();
        var dateCtrl = $("input.date");
        $("#startDate, #lastDate").datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true, yearRange: "".concat(curYear - 100,":",curYear + 5) }).attr('readonly','readonly');
        $("#birthdate").datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true, minDate:new Date(curYear - 100, 1-1, 1), maxDate: new Date(), yearRange: "".concat(curYear - 100,":",curYear) }).attr('readonly','readonly');
        // try to fix icon position. Temporary solution. Still under
        // investigation
        dateCtrl.each(function() {
            var element = $(this);
            var icon = element.next();
            var el_pos = element.position(), el_h = element.outerHeight(false), el_mt = parseInt(element.css('marginTop'), 10) || 0, el_w = element
                    .outerWidth(false), el_ml = parseInt(element.css('marginLeft'), 10) || 0,

            i_w = icon.outerWidth(true), i_h = icon.outerHeight(true);

            var new_icon_top = el_pos.top + el_mt + ((el_h - i_h) / 2);
            var new_icon_left = el_pos.left + el_ml + el_w - i_w;

            var icon_pos = icon.position();

            if (icon_pos.top != new_icon_top) {
                icon.css('top', new_icon_top);
            }
            if (icon_pos.left != new_icon_left) {
                icon.css('left', new_icon_left);
            }
            icon.css('margin', '0 0 0 -30px');

        });

        $("#selectSupervisor").click(
                function() {
                    $("#dialog").userSearchForm(
                            {
                                afterFormAppended : function() {
                                    $("#dialog").dialog({
                                        autoOpen : false,
                                        draggable : false,
                                        resizable : false,
                                        title : localeManager["openiam.ui.common.search.users"],
                                        width : "auto",
                                        position : "center"
                                    });
                                    $("#dialog").dialog("open");
                                },
                                onSubmit : function(json) {
                                    $("#userResultsArea").userSearchResults(
                                            {
                                                "jsonData" : json,
                                                "page" : 0,
                                                "size" : 20,
                                                initialSortColumn : "name",
                                                initialSortOrder : "ASC",
                                                url : OPENIAM.ENV.ContextPath + "/rest/api/users/search",
                                                emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                                                emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
                                                columnHeaders : [ localeManager["openiam.ui.common.name"], localeManager["openiam.ui.common.phone.number"],
                                                        localeManager["openiam.ui.common.email.address"], localeManager["openiam.ui.webconsole.user.status"],
                                                        localeManager["openiam.ui.webconsole.user.accountStatus"] ],
                                                onAppendDone : function() {
                                                    $("#dialog").dialog("close");
                                                    $("#userResultsArea").prepend("<div class=\"\">" + localeManager["openiam.ui.user.supervisor.table.description"] + "</div>")
                                                            .dialog({
                                                                autoOpen : true,
                                                                draggable : false,
                                                                resizable : false,
                                                                title : localeManager["openiam.ui.user.search.result.title"],
                                                                width : "auto"
                                                            })
                                                },
                                                onEntityClick : function(bean) {
                                                    $("#userResultsArea").dialog("close");
                                                    $("#supervisorId").val(bean.id);
                                                    $("#supervisorName").val(bean.name);

                                                }
                                            });
                                }
                            });
                });

        $("#selectalternateContact").click(
                function() {
                    $("#dialog").userSearchForm(
                            {
                                afterFormAppended : function() {
                                    $("#dialog").dialog({
                                        autoOpen : false,
                                        draggable : false,
                                        resizable : false,
                                        title : localeManager["openiam.ui.common.search.users"],
                                        width : "auto",
                                        position : "center"
                                    });
                                    $("#dialog").dialog("open");
                                },
                                onSubmit : function(json) {
                                    $("#userResultsArea").userSearchResults(
                                            {
                                                "jsonData" : json,
                                                "page" : 0,
                                                "size" : 20,
                                                initialSortColumn : "name",
                                                initialSortOrder : "ASC",
                                                url : OPENIAM.ENV.ContextPath + "/rest/api/users/search",
                                                emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                                                emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
                                                columnHeaders : [ localeManager["openiam.ui.common.name"], localeManager["openiam.ui.common.phone.number"],
                                                        localeManager["openiam.ui.common.email.address"], localeManager["openiam.ui.webconsole.user.status"],
                                                        localeManager["openiam.ui.webconsole.user.accountStatus"] ],
                                                onAppendDone : function() {
                                                    $("#dialog").dialog("close");
                                                    $("#userResultsArea").prepend(
                                                            "<div class=\"\">" + localeManager["openiam.ui.user.alt.contact.table.description"] + "</div>").dialog({
                                                        autoOpen : true,
                                                        draggable : false,
                                                        resizable : false,
                                                        title : localeManager["openiam.ui.user.search.result.title"],
                                                        width : "auto"
                                                    })
                                                },
                                                onEntityClick : function(bean) {
                                                    $("#userResultsArea").dialog("close");
                                                    $("#alternateContactId").val(bean.id);
                                                    $("#altName").val(bean.name);

                                                }
                                            });
                                }
                            });
                });
                
		$("#userRoleId").selectableSearchResult({
			singleSearch : true,
			noneSelectedText : localeManager["openiam.ui.shared.role.search"],
			addMoreText : localeManager["openiam.ui.common.role.add.another"],
			onClick : function($that) {
				$("#editDialog").roleDialogSearch({
					searchTargetElmt : "#editDialog",
					showResultsInDialog : true,
					onAdd : function(bean) {
						$that.selectableSearchResult("add", bean);
					},
					pageSize : 5
				});
			}
		});
    },
    getDependendOptionList : function(parentId, dataMap) {
        var dataList = dataMap[parentId];
        var newOptionList = [];
        newOptionList.push(OPENIAM.User.Form.getEmptyOptionElement());
        if (dataList != null && dataList.length > 0) {
            for (var j = 0; j < dataList.length; j++) {
                var opt = document.createElement("option");
                opt.value = dataList[j].id;
                opt.innerHTML = dataList[j].name;
                newOptionList.push(opt);
            }
        }
        return newOptionList;
    },
    getEmptyOptionElement : function() {
        var opt = document.createElement("option");
        opt.value = "";
        opt.innerHTML = "- "+localeManager["openiam.ui.common.value.pleaseselect"]+" -";
        return opt;
    },
    saveUser : function() {
        this.postJSON(OPENIAM.ENV.ContextPath + "/rest/api/prov/saveUser", this.toUserJSON());
    },
    toUserJSON : function() {
        var obj = {};

        obj.id = OPENIAM.ENV.UserId;
        var tmp = $("#status").val();
        if (tmp)
            obj.status = tmp;

        tmp = $("#secondaryStatus").val();
        if (tmp)
            obj.secondaryStatus = tmp;
        obj.firstName = $("#firstName").val();
        obj.middleInit = $("#middleInit").val();
        obj.lastName = $("#lastName").val();
        obj.nickname = $("#nickname").val();
        obj.maidenName = $("#maidenName").val();
        obj.suffix = $("#suffix").val();

        tmp = $("#birthdate").val();
        if (tmp)
            obj.birthdateAsStr = tmp;

        obj.sex = $("#sex").val();
        obj.showInSearch = $("#showInSearch").val();
        obj.delAdmin = $("#delAdmin").val();
        obj.title = $("#title").val();
        obj.jobCodeId = $("#jobCode").val();
        obj.classification = $("#classification").val();
        obj.employeeId = $("#employeeId").val();
        obj.userTypeInd = $("#userTypeInd").val();
        obj.employeeTypeId = $("#employeeType").val();

        tmp = $("#startDate").val();
        if (tmp)
            obj.startDateAsStr = tmp;

        tmp = $("#lastDate").val();
        if (tmp)
            obj.lastDateAsStr = tmp;

        obj.supervisorId = $("#supervisorId").val();
        obj.supervisorName = $("#supervisorName").val();

        obj.alternateContactId = $("#alternateContactId").val();
        obj.alternateContactName = $("#altName").val();

        obj.login = $("#login").val();
        obj.password = $("#password").val();
        obj.confirmPassword = $("#confirmPassword").val();

        obj.mailCode = $("#mailCode").val();
        obj.costCenter = $("#costCenter").val();

        if (OPENIAM.ENV.UserId == null) {
            obj.organizationIds = $("#organizationsTable").organizationHierarchyWrapper("getValues");

            var emailObj = {};
            emailObj.email = $("#email").val();
            emailObj.typeId = $("#emailTypeId").val();

            if (emailObj.email || emailObj.typeId)
                obj.email = emailObj;

            var addressObj = {};
            addressObj.bldgNumber = $("#building").val();
            addressObj.address1 = $("#address1").val();
            addressObj.address2 = $("#address2").val();
            addressObj.city = $("#city").val();
            addressObj.postalCd = $("#postalCode").val();
            addressObj.state = $("#state").val();
            addressObj.typeId = $("#addressTypeId").val();

            if (addressObj.bldgNumber || addressObj.address1 || addressObj.address2 || addressObj.city || addressObj.postalCd || addressObj.state
                    || addressObj.typeId)
                obj.address = addressObj;

            var phoneObj = {};
            phoneObj.areaCd = $("#areaCode").val();
            phoneObj.phoneNbr = $("#phoneNumber").val();
            phoneObj.phoneExt = $("#extension").val();
            phoneObj.typeId = $("#phoneTypeId").val();

            if (phoneObj.areaCd || phoneObj.phoneNbr || phoneObj.phoneExt || phoneObj.typeId)
                obj.phone = phoneObj;

            obj.roleId = $("#userRoleId").selectableSearchResult("getId");
        }
        obj.metadataTypeId = $("#metadataTypeId").val();

        obj.notifyUserViaEmail = $("#notifyUserViaEmail").is(':checked');
        if (obj.notifyUserViaEmail == null || obj.notifyUserViaEmail == undefined)
            obj.notifyUserViaEmail = false;

        obj.notifySupervisorViaEmail = $("#notifySupervisorViaEmail").is(':checked');
        if (obj.notifySupervisorViaEmail == null || obj.notifySupervisorViaEmail == undefined)
            obj.notifySupervisorViaEmail = false;

        obj.provisionOnStartDate = $("#provisionOnStartDate").is(':checked');
        if (obj.provisionOnStartDate == null || obj.provisionOnStartDate == undefined)
            obj.provisionOnStartDate = false;
        return obj;
    },
    statusWorker:function(userIdObj) {
        var obj = {};
        obj.id = userIdObj;
        $.ajax({
            url: OPENIAM.ENV.ContextPath + "/rest/api/prov/checkStatus",
            data : obj,
            type : "POST",
            dataType : "json",
            success: function(data) {
               OPENIAM.Modal.init();
                $("#dialog div").slice(0).remove();
                var mess = document.createElement("div");
                mess.className = "success";
                mess.innerHTML = data.successMessage;
                $("#dialog").append(mess);

                OPENIAM.Modal.init();
                if(data.contextValues.checkStatusInProgress == true) {
                    setTimeout(function() {
                    	OPENIAM.User.Form.statusWorker(data.contextValues.userId);
                    }, 5000);
                } else {
                    if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                        window.location.href = data.redirectURL;
                    } else {
                        window.location.reload(true);
                    }
                }

            }
        });
    },
    postData : function(url, data, callback) {
        $.ajax({
            url : url,
            data : data,
            type : "POST",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 2000,
                        onIntervalClose : function() {
                            if (data.contextValues) {
                                OPENIAM.User.Form.statusWorker(data.contextValues.userId);
                            }
                        },
                        afterClose : function() {
                            if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    showProfilePic : function() {
        var profilePicSrc = OPENIAM.ENV.ProfilePicSrc;
        if (profilePicSrc) {
            var $html = $("<div/>")[0];
            var $img = $('<img/>',{src: OPENIAM.ENV.ContextPath + "/rest/api/images/" + profilePicSrc})[0];
            $html.appendChild($img);
            $("#dialog").html($html).dialog({
                draggable : false,
                resizable : false,
                width : "auto",
                position: { at: "left top", of: "#profilePicLinks" },
                open: function( event, ui ) {
                },
                close: function( event, ui ) {
                    $("#dialog").dialog( "destroy" );
                }
            });
        }
    },
    deleteProfilePic : function() {
        var id = OPENIAM.ENV.UserId;
        if (id) {
            var obj = {};
            obj.id = id;
            this.postData(OPENIAM.ENV.ContextPath + "/rest/api/prov/deleteProfilePic.html", obj);
        }
    },
    changeProfilePic : function() {
        $("#profilePicLinks").hide();
        $("#profilePicForm").show();
        $('#profilePicForm').on('click', function(e) {
            e.stopPropagation();
        });
        $(document).on('click', function (e) {
            $("#profilePicLinks").show();
            $("#profilePicForm").hide();
        });
    },
    processProfilePic : function() {
        var file = $("#uploadProfilePic")[0].files[0];
        if (file) {
            var formData = new FormData();
            formData.append("id", OPENIAM.ENV.UserId);
            formData.append("pic", file);
            this.uploadFile(OPENIAM.ENV.ContextPath + "/rest/api/prov/addProfilePic", formData);
        }
    },
    uploadFile : function(url, data, callback) {
        $this = this;
        $.ajax({
            url : url,
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success : function(data, textStatus, jqXHR) {
                $this.successHandler(data);
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    postJSON : function(url, data, callback) {
        $this = this;
        $.ajax({
            url : url,
            data : JSON.stringify(data),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                $this.successHandler(data);
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    successHandler: function(data) {
        if (data.status == 200) {
            OPENIAM.Modal.Success({
                message : data.successMessage,
                showInterval : 2000,
                onIntervalRun : function() {
                    if (data.contextValues) {
                        OPENIAM.User.Form.statusWorker(data.contextValues.userId);
                    }
                },
                afterClose : function() {
                    if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                        window.location.href = data.redirectURL;
                    } else {
                        window.location.reload(true);
                    }
                }

            });
        } else {
            OPENIAM.Modal.Error({
                errorList : data.errorList
            });
        }
    },

    Actions : {
        Links : [],
        ExtendedActions : [],
        init : function() {
            var $this = this;
            this.ExtendedActions["RESET_CHALLENGE_RESPONSE"] = function() {
                $this.postWithWarn(
                    "RESET_CHALLENGE_RESPONSE",
                    localeManager["openiam.ui.user.challengeresponse.message"],
                    localeManager["openiam.ui.user.challengeresponse.confirm"]
                );
            };

            this.ExtendedActions["RESET_ACCOUNT"] = function() {
                $this.postWithWarn(
                    "RESET_ACCOUNT",
                    localeManager["openiam.ui.user.account.reset.message"],
                    localeManager["openiam.ui.user.account.reset.confirm"]
                );
            };

            this.ExtendedActions["DEACTIVATE_USER"] = function() {
                $this.postWithWarn(
                    "DEACTIVATE_USER",
                    localeManager["openiam.ui.user.deactivate.message"],
                    localeManager["openiam.ui.user.deactivate.confirm"]
                );
            };

            this.ExtendedActions["REMOVE_USER"] = function() {
                $this.postWithWarn(
                    "REMOVE_USER",
                    localeManager["openiam.ui.user.delete.message"],
                    localeManager["openiam.ui.user.delete.confirm"]
                );
            };
        },

        execute : function(actionId) {
            var extendedAction = this.ExtendedActions[actionId];
            if (extendedAction != null && extendedAction != 'undefined') {
                extendedAction();
            } else {
                this.post(actionId);
            }
            return false;
        },

        postWithWarn : function(actionId, message, confirmText) {
            OPENIAM.Modal.Warn({
                message : message,
                buttons : true,
                OK : {
                    text : confirmText,
                    onClick : function() {
                        OPENIAM.Modal.Close();
                        OPENIAM.User.Form.Actions.post(actionId);
                    }
                },
                Cancel : {
                    text : localeManager["openiam.ui.button.cancel"],
                    onClick : function() {
                        OPENIAM.Modal.Close();
                    }
                }
            });
        },

        post : function(actionId) {
            var url = this.Links[actionId];
            if (url) {
                var obj = {id: OPENIAM.ENV.UserId};
                OPENIAM.User.Form.postData(url, obj)
            }
        }
    },

    ButtonsPanel: {
        titles : {
            'REMOVE_USER': localeManager["openiam.ui.button.delete.title"],
            'DEACTIVATE_USER': localeManager["openiam.ui.user.button.deactivate.title"],
            'RESET_CHALLENGE_RESPONSE': localeManager["openiam.ui.user.button.challengeresponse.reset.title"],
            'RESET_ACCOUNT': localeManager["openiam.ui.user.button.account.reset.title"]
        },
        draw : function() {
            var $this = this;
            if (OPENIAM.ENV.initialMenu != null && OPENIAM.ENV.initialMenu != 'undefined') {
                OPENIAM.User.Form.Menu = Object.create(OPENIAM.MenuTree);
                OPENIAM.User.Form.Menu.initialize({
                    tree : OPENIAM.ENV.initialMenu,
                    toHTML : function() {
                        var ul = $('#buttonsPanel').get(0);
                        if(this.getRoot() != null) {
                            var buttons = [];
                            var node = this.getRoot().getChild();
                            while(node != null) {
                                var html = node.toHTML();
                                if(html) {
                                    buttons.push(html);
                                }
                                node = node.getNext();
                            }
                            $(buttons.reverse()).each(function(i, html) {
                                ul.appendChild(html);
                            });
                        }
                        return ul;
                    },
                    onNodeClick : function() {
                    },
                    toNodeHtml : function() {
                        var url = this.getURL();
                        var id = this.getName();
                        if (url) {
                            OPENIAM.User.Form.Actions.Links[id] = url;
                        }
                        var li = document.createElement("li");
                        $(li).addClass("rightBtn");
                        var a = document.createElement("a"); a.href = "javascript:void(0);";
                        $(a).attr("id", id).addClass("redBtn").append(this.getText());
                        if ($this.titles[id]) {
                            $(a).attr("title", $this.titles[id]);
                        }
                        $(a).click( function() {
                            OPENIAM.User.Form.Actions.execute(id);
                            return false;
                        });
                        $(li).append(a);
                        return li;
                    }
                });
                OPENIAM.User.Form.Menu.toHTML();
            }
        }
    }
};

$(document).ready(function() {
    if (OPENIAM.ENV.UserId != null && OPENIAM.ENV.UserId != 'undefined') {
        OPENIAM.User.Form.ButtonsPanel.draw();
        OPENIAM.User.Form.Actions.init();
    }
    OPENIAM.User.Form.init();

    $("#editUserForm").submit(function() {
        OPENIAM.User.Form.saveUser();
        return false;
    });
    $("#uploadProfilePic").bind('change', function(e) {
        OPENIAM.User.Form.processProfilePic();
        return false;
    });
    $("#showProfilePic").click(function() {
        OPENIAM.User.Form.showProfilePic();
        return false;
    });
    $("#deleteProfilePic").click(function() {
        OPENIAM.Modal.Warn({
            message : localeManager["openiam.ui.user.profile.pic.delete.confirmation"],
            buttons : true,
            OK : {
                text : localeManager["openiam.ui.button.delete"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    OPENIAM.User.Form.deleteProfilePic();
                }
            },
            Cancel : {
                text : localeManager["openiam.ui.button.cancel"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                }
            }
        });
        return false;
    });
    $("#changeProfilePic").click(function() {
        OPENIAM.User.Form.changeProfilePic();
        return false;
    });

});

$(window).load(function() {
});