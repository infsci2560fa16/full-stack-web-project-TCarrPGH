/*jslint browser: true*/ /*global  $*/
$.get("header.html", function (data) {
    "use strict";
    $("#header-placeholder").replaceWith(data);
});