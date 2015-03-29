OPENIAM = window.OPENIAM || {};
OPENIAM.GroupType = {
    init : function() {
        $("#groupType").change(function() {
            window.location.href = "editGroup.html?groupTypeId=" + $(this).val();
        });
    }
};

$(document).ready(function() {
    OPENIAM.GroupType.init();
});