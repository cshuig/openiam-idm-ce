OPENIAM = window.OPENIAM || {};
OPENIAM.Location = {
    init : function() {
        $("#saveBtn").click(function() {
            OPENIAM.Location.save();
            return false;
        });

        $("#deleteBtn").click(function() {
            OPENIAM.Location.deleteType();
            return false;
        });
        this.populate();
    },
    populate : function() {
        var obj = OPENIAM.ENV.Location;
        $("#name").val(obj.name);
        $("#country").val(obj.country);
        $("#bldgNum").val(obj.bldgNum);
        $("#streetDirection").val(obj.streetDirection);
        $("#address1").val(obj.address1);
        $("#address2").val(obj.address2);
        $("#address3").val(obj.address3);
        $("#city").val(obj.city);
        $("#state").val(obj.state);
        $("#postalCd").val(obj.postalCd);
        $("#description").val(obj.description);
    },
    toJSON : function() {
        var obj = OPENIAM.ENV.Location;
        obj.name = $("#name").val();
        obj.country = $("#country").val();
        obj.bldgNum = $("#bldgNum").val();
        obj.streetDirection = $("#streetDirection").val();
        obj.address1 = $("#address1").val();
        obj.address2 = $("#address2").val();
        obj.address3 = $("#address3").val();
        obj.city = $("#city").val();
        obj.state = $("#state").val();
        obj.postalCd = $("#postalCd").val();
        obj.description = $("#description").val();
    },
    save : function() {
        this.toJSON();
        $.ajax({
            url : "locationEdit.html",
            data : JSON.stringify(OPENIAM.ENV.Location),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        window.location.href = "organizationLocation.html?id="+OPENIAM.ENV.OrganizationId;
                    }});
                } else {
                    OPENIAM.Modal.Error({errorList : data.errorList});
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    deleteType : function() {
        $.ajax({
            url : "locationDelete.html",
            data : {id : OPENIAM.ENV.LocationId},
            type: "POST",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        window.location.href = "organizationLocation.html?id="+OPENIAM.ENV.OrganizationId;
                    }});
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

$(document).ready(function() {
    OPENIAM.Location.init();
});

$(window).load(function() {

});