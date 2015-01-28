OPENIAM = window.OPENIAM || {};

OPENIAM.History = {
    load: function () {
        $("#historyContainer").entitlemetnsTable({
            ajaxURL: "rest/api/activiti/task/history/search",
            emptyResultsText: localeManager["openiam.ui.selfservice.history.no.history"],
            columnHeaders: [
            	localeManager["openiam.ui.selfservice.history.task.name"], 
            	localeManager["openiam.ui.selfservice.history.task.description"], 
            	localeManager["openiam.ui.selfservice.history.created.date"], 
            	localeManager["openiam.ui.selfservice.history.completed.date"]
            ],
            entityUrl: "task.html",
            columnsMap: ["name", "description", "createdTime", "endDate"],
            dateFields: ["createdTime", "endDate"],
            getEntityURL: function (bean) {
                return "taskHistoryInstance.html?taskId=" + bean.id + "&id=" + bean.executionId;
            }
        });
    }
};

$(document).ready(function () {
    OPENIAM.History.load();
});

$(window).load(function () {

});