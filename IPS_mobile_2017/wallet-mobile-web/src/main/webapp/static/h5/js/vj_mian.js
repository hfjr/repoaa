//信息提示
$(document).ready(function(){
    $(".poptype").click(function(){
        $(".alert").fadeIn(500);
        $(".alert").fadeOut(3500);
    });
});

//短信倒计时
$(function  () {
    //获取短信验证码
    var validCode=true;
    $(".msgs").click (function  () {
        var time=30;
        var code=$(this);
        if (validCode) {
            validCode=false;
            code.addClass("msgs1");
            var t=setInterval(function  () {
                time--;
                code.html(time+"秒后重发");
                if (time==0) {
                    clearInterval(t);
                    code.html("重新发送");
                    validCode=true;
                    code.removeClass("msgs1");
                }
            },1000)
        }
    })
})