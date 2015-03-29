OPENIAM = window.OPENIAM || {};
OPENIAM.ProfilePic = window.OPENIAM.ProfilePic || {};

OPENIAM.ProfilePic.Form = {
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
    successHandler: function(data) {
        if (data.status == 200) {
            OPENIAM.Modal.Success({
                message : data.successMessage,
                showInterval : 2000,
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
    isAjaxUploadSupported: function() {
        var myNav = navigator.userAgent.toLowerCase();
        return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) > 9 : true;
    }
};

$(document).ready(function() {
    $("#uploadProfilePic").bind('change', function(e) {
        if (OPENIAM.ProfilePic.Form.isAjaxUploadSupported()) {
            OPENIAM.ProfilePic.Form.processProfilePic();
        } else {
            $("#uploadForm").submit(); // submit form with pic
        }
        return false;
    });
    $("#showProfilePic").click(function() {
        OPENIAM.ProfilePic.Form.showProfilePic();
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
                    OPENIAM.ProfilePic.Form.deleteProfilePic();
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
        OPENIAM.ProfilePic.Form.changeProfilePic();
        return false;
    });

});

$(window).load(function() {
});