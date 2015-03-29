OPENIAM = window.OPENIAM || {};
OPENIAM.USEPolicy = {
    init: function () {
        OPENIAM.USEPolicy.checkScrollHeight();
        document.getElementsByName("licenseAgreement")[0].addEventListener("scroll", OPENIAM.USEPolicy.checkScrollHeight, false);
    },
    checkScrollHeight: function () {
        var agreementTextElement = document.getElementsByName("licenseAgreement")[0]
        if ((agreementTextElement.scrollTop + agreementTextElement.offsetHeight) >= agreementTextElement.scrollHeight) {
            $(document.getElementsByName("accept")[0]).css("display", "block");
        }
    }
};

$(document).ready(function () {

    OPENIAM.USEPolicy.init();
});