$(document).ready(function () {
    var that = this;
    $(".jRate").each(function (k, v) {
        $(v).jRate({
            rating: $(v).data("rating"),
            readOnly: true,
            startColor: "cyan",
            endColor: "blue"
        });
    });
});