/**
 * 函数对象
 */
var Validate = function(){
   var handlerInitValidate = function(){
       $("#inputForm").validate({
           errorElement: 'span',
           errorClass: 'help-block',
           // error：新添元素，element：触发元素
           errorPlacement: function (error, element){
               element.parent().parent().attr("class", "form-group has-error");
               error.insertAfter(element);
           }
       });
   };

   var handlerInitCustomValidate = function () {
       $.validator.addMethod("mobile",function () {
           var length = value.length;
           var mobile =  /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
           return this.optional(element) || (length == 11 && mobile.test(value));
       },"手机号码格式错误");
   };

   return {
       /**
        * 初始化
        */
       init:function(){
           handlerInitValidate();
           handlerInitCustomValidate();
       }

   }
}();
$(document).ready(function(){
    Validate.init();
});
