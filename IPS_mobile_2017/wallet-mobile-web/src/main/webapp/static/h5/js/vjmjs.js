/**
 * Created by Harris_Zhu on 15/10/1.
 */


//弹出type1
$(document).ready(function(){
    $(".msgs").click(function(){
        $(".EjectType1").fadeIn(500);
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
                code.html(time+"秒");
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

/*折叠*/
$(function () {
    var Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;
        var links = this.el.find('.link');
        links.on('click', {
            el: this.el,
            multiple: this.multiple
        }, this.dropdown);
    };
    Accordion.prototype.dropdown = function (e) {
        var $el = e.data.el;
        $this = $(this), $next = $this.next();
        $next.slideToggle();
        $this.parent().toggleClass('open');
        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
        ;
    };
    var accordion = new Accordion($('#accordion'), false);
});

