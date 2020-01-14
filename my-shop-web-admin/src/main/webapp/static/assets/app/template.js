var App = function(){
    var handlerInit = function () {
        console.log("handlerInit");
    }

    var handlerShow = function () {
        console.log("handlerShow");
    }

    return {
        init:function () {
            handlerInit();
        },
        show:function () {
            handlerShow();
        }
    }
}();

$(document).ready(function () {
    App.init();
    App.show();
});