$(document).ready(function ($) {
    console.log("init");

    var itemList = new Vue({
            el : '#itemList',
            data : {
                itemList
            }
        });

    $.get("/api/mainPage", function(response){
        itemList.itemList = response.data;
    });

})(jQuery);