OPENIAM = window.OPENIAM || {};
OPENIAM.User = window.OPENIAM.User || {};

OPENIAM.User.Form = {
    init: function () {
        $("#formContainer").userSearchForm({
                onSubmit: function (json) {
                    json.fromDirectoryLookup = true;
                    $("#userResultsArea").userSearchResults({
                        "jsonData": json,
                        page: 0,
                        size: 20,
                        initialSortColumn: "name",
                        initialSortOrder: "ASC",
                        url: "rest/api/users/search",
                        entityURL: OPENIAM.ENV.ShowDetails ? "viewUser.html" : null,
                        emptyFormText: localeManager["openiam.ui.common.user.search.empty"],
                        emptyResultsText: localeManager["openiam.ui.common.user.search.no.results"],
                        onEntityClick: OPENIAM.ENV.ShowDetails ?
                            function (bean) {
                                window.location.href = "viewUser.html?id=" + bean.id;
                            }
                            :
                            function (bean) {
                                return false;
                            },
                        columnHeaders: OPENIAM.ENV.СolumnList
                    });
                }
            }
        )
        ;

    }
}
;

$(document).ready(function () {
    OPENIAM.User.Form.init();
});
