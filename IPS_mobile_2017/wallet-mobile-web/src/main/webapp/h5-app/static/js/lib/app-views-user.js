;(function(){
	var prefix = "static/js/app/vj/view/user/"; 
	var fileList = {"getPasswordSuccess.html":"<script type=\"text/template\" data-tpl=\"tpl-main-view\">\n\t<div class=\"window\" >\n\t\t<div class=\"window-logo\">\n\t\t\t<img src=\"<# print(_g_dir.static_dir); #>img/passport/yes.png\">\n\t\t</div>\n\t\t<p class=\"window-logo1\">登录密码修改成功</p>\n\t\t<p class=\"window-logo2\">请重新登录</p>\n\t\t<input class=\"define\" id=\"btn\" type=\"button\" value=\"确认\"/>\n\t</div>\t\n</script>\n<div class=\"page\"></div>","getPwd.html":"<script type=\"text/template\" data-tpl=\"tpl-main-view\">\n\n\t<div  class=\"passport-wrap\">\n\t\t<div class=\"box box-1\">\n\t\t\t<div class=\"logel\">\n\t\t\t\t<img src=\"<# print(_g_dir.static_dir); #>img/logo.png\">\n\t\t\t</div>\n\t\t\t<div class=\"form-wrap\">\n\t\t\t\t<form id=\"regform\">\n\t\t\t\t\t<input type=\"text\" style=\"display:none;\">\n\t\t\t\t\t<input type=\"password\" style=\"display:none;\">\n\t\t\t\t\n\t\t\t\t\t<div class=\"text\">\n\t\t\t\t\t\t<div class=\"tubiao fix\">\n\t\t\t\t\t\t\t<img  class=\"phone\" src=\"<# print(_g_dir.static_dir); #>img/passport/phone.png\">\n\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t<input  class=\"intext\"  placeholder=\"请输入手机号码\" maxlength=\"11\" value=\"\" type=\"tel\" id=\"f-phone\" name=\"phone\">\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"tubiao fix\">\n\t\t\t\t\t\t\t<img class=\"password\" src=\"<# print(_g_dir.static_dir); #>img/passport/clock.png\">\n\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t<input  class=\"intext\" type=\"password\"  id=\"f-password\" name=\"password\" placeholder=\"请输入登录密码\">\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<span id=\"eye\" class=\"eye eye-hide\"></span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"tubiao fix\">\n\t\t\t\t\t\t\t<img  class=\"valid-code\" src=\"<# print(_g_dir.static_dir); #>img/passport/letter.png\">\n\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t<input  class=\"intext\" placeholder=\"请输入验证码\"  maxlength=\"6\"  value=\"\" type=\"tel\" id=\"f-code\" name=\"code\" >\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<span  id=\"code-btn\" g-act-tap=\"true\" class=\"verification-code\">发送验证码</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"tubiao1 fix\">\n\t\t\t\t\t\t\t<div class=\"allvoice\">\n\t\t\t\t\t\t\t\t<div class=\"voice\"></div>\n\t\t\t\t\t\t\t\t<span class=\"voice-detail\">如无法收到手机验证码，请选择</span>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div class=\"all-voice\" id=\"code-btn2\">\n\t\t\t\t\t\t\t\t<div class=\"voice-img\"></div>\n\t\t\t\t\t\t\t\t<div class=\"voice-text\" >语&nbsp;音&nbsp;验&nbsp;证&nbsp;码</div>\n\t\t\t\t\t\t\t</div>\t\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<input type=\"submit\" id=\"btn-submit\" class=\"btn\" value=\"下一步\">\n\t\t\t\t\t</div>\n\t\t\t\t</form>\n\t\t\t</div>\n\t\t\t\n\t\t</div>\n\t</div>\n\t\n\n</script>\n<div class=\"page\"></div>","login.html":"<script type=\"text/template\" data-tpl=\"tpl-main-view\">\n\t\n\t<div class=\"passport-wrap\">\n\t\t<div class=\"box\">\n\t\t\t<div class=\"logel\">\n\t\t\t\t<img src=\"<# print(_g_dir.static_dir); #>img/logo.png\">\n\t\t\t</div>\n\t\t\t<div class=\"form-wrap\">\n\t\t\t\t<form id=\"loginform\">\n\t\t\t\t\t<input type=\"text\" style=\"display:none;\">\n\t\t\t\t\t<input type=\"password\" style=\"display:none;\">\n\t\t\t\t\t<div class=\"text\">\n\t\t\t\t\t\t<div class=\"tubiao fix\">\n\t\t\t\t\t\t\t<img class=\"phone\" src=\"<# print(_g_dir.static_dir); #>img/passport/phone.png\">\n\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t<input  class=\"intext\"   placeholder=\"请输入手机号码\"  maxlength=\"11\" value=\"\" type=\"tel\" id=\"f-phone\" name=\"phone\">\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"tubiao fix\">\n\t\t\t\t\t\t\t<img class=\"password\" src=\"<# print(_g_dir.static_dir); #>img/passport/clock.png\">\n\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t<input  class=\"intext\"  placeholder=\"请输入登录密码\" maxlength=\"12\" value=\"\" type=\"password\"  id=\"f-password\" name=\"password\" >\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<span id=\"eye\" class=\"eye eye-hide\"></span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"tubiao1 fix\">\n\t\t\t\t\t\t\t<div data-tap-path=\"user/getPwd\" class=\"passrord\">激活/忘记密码？</div>\n\t\t\t\t\t\t\t<div data-tap-path=\"user/register\" class=\"zhuce\">注册</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<input type=\"submit\" class=\"btn\" id=\"btn-submit\"  value=\"登录\">\n\t\t\t\t\t</div>\n\t\t\t\t</form>\n\t\t\t</div>\n\t\t\t\n\t\t</div>\n\t</div>\n\t \n\t\t\t\n</script>\n<div class=\"page\"></div>","login.old.html":"<script type=\"text/template\" data-tpl=\"tpl-main-view\">\n\t<div class=\"page-login\">\n\t\t<div class=\"b-main\">\n\t\t\t<div class=\"widget-passport\">\n\t\t\t\t<form id=\"loginform\">\n\t\t\t\t\t<div class=\"widget-passport-wrap\">\n\t\t\t\t\t\t<input type=\"text\" style=\"display:none;\">\n\t\t\t\t\t\t<input type=\"password\" style=\"display:none;\">\n\t\t\t\t\t\t<div class=\"row fix\">\n\t\t\t\t\t\t\t<div class=\"input-wrap\">\n\t\t\t\t\t\t\t\t<div class=\"con\">\n\t\t\t\t\t\t\t\t\t<input placeholder=\"请输入手机号\" maxlength=\"11\" value=\"\" type=\"tel\" id=\"f-phone\" name=\"phone\" class=\"inp-text\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<label class=\"phone\" for=\"f-phone\">手机号</label>\n\t\t\t\t\t\t</div>\t\n\t\t\t\t\t\t<div class=\"row fix\">\n\t\t\t\t\t\t\t<div class=\"input-wrap\">\n\t\t\t\t\t\t\t\t<div class=\"con con-pwd\">\n\t\t\t\t\t\t\t\t\t<input placeholder=\"请输入登录密码\" maxlength=\"12\" value=\"\" type=\"password\"  id=\"f-password\" name=\"password\" class=\"inp-text\">\n\t\t\t\t\t\t\t\t\t<span id=\"eye\" class=\"eye eye-hide\">&nbsp;</span>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<label class=\"password\" for=\"f-password\">登录密码</label>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t\t<div class=\"tip-bar\">\n\t\t\t\t\t\t新用户请点击注册/激活\n\t\t\t\t\t</div>\n\t\t\t\t\t\n\t\t\t\t\t\n\t\t\t\t\t<div class=\"btn-bar\">\n\t\t\t\t\t\t<input type=\"submit\" g-act-tap=\"true\" value=\"登&nbsp;录\" id=\"btn-submit\" class=\"ui-button\">\n\t\t\t\t\t</div>\n\n\t\t\t\t\t<div class=\"text-bar fix\">\n\t\t\t\t\t\t<div class=\"col-l\">\n\t\t\t\t\t\t\t<a  data-tap-path=\"user/getPwd\" href=\"javascript:;\">忘记密码？</a>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"col-r\">\n\t\t\t\t\t\t\t<a data-tap-path=\"user/registerChoose\" href=\"javascript:;\">注册/激活</a>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</form>\t\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t\n</script>\n<div class=\"page\"></div>","register.html":"<script type=\"text/template\" data-tpl=\"tpl-main-view\">\n\t\n\t<div  class=\"passport-wrap\">\n\t\t<div class=\"box box-2\">\n\t\t\t<div class=\"logel\">\n\t\t\t\t<img src=\"<# print(_g_dir.static_dir); #>img/logo.png\">\n\t\t\t</div>\n\t\t\t<div class=\"form-wrap\">\n\t\t\t\t<form id=\"regform\">\n\t\t\t\t\t<input type=\"text\" style=\"display:none;\">\n\t\t\t\t\t<input type=\"password\" style=\"display:none;\">\n\t\t\t\t\t<div class=\"text text-1\">\n\t\t\t\t\t\t<div class=\"tubiao fix\">\n\t\t\t\t\t\t\t<img src=\"<# print(_g_dir.static_dir); #>img/passport/phone.png\" class=\"phone\">\n\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t<input type=\"tel\" name=\"phone\" id=\"f-phone\" value=\"\" maxlength=\"11\" placeholder=\"请输入手机号码\" class=\"intext\">\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"tubiao fix\">\n\t\t\t\t\t\t\t<img src=\"<# print(_g_dir.static_dir); #>img/passport/clock.png\" class=\"password\">\n\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t<input type=\"password\" placeholder=\"请输入登录密码\" name=\"password\" id=\"f-password\" class=\"intext\">\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<span id=\"eye\" class=\"eye eye-hide\"></span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"tubiao fix\">\n\t\t\t\t\t\t\t<img src=\"<# print(_g_dir.static_dir); #>img/passport/letter.png\" class=\"valid-code\">\n\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t<input type=\"tel\" name=\"code\" id=\"f-code\" value=\"\" maxlength=\"6\" placeholder=\"请输入验证码\" class=\"intext\">\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<span class=\"verification-code\" g-act-tap=\"true\" id=\"code-btn\">发送验证码</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"tubiao1 fix\">\n\t\t\t\t\t\t\t<div class=\"allvoice\">\n\t\t\t\t\t\t\t\t<div class=\"voice\"></div>\n\t\t\t\t\t\t\t\t<span class=\"voice-detail\">如无法收到手机验证码，请选择</span>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div class=\"all-voice\">\n\t\t\t\t\t\t\t\t<div class=\"voice-img\"></div>\n\t\t\t\t\t\t\t\t<div class=\"voice-text\">语&nbsp;音&nbsp;验&nbsp;证&nbsp;码</div>\n\t\t\t\t\t\t\t</div>\t\n\t\t\t\t\t\t</div>\n\n\t\t\t\t\t\t<div class=\"recommender-wrap\">\n\t\t\t\t\t\t\t<div class=\"tubiao  fix\">\n\t\t\t\t\t\t\t\t<img src=\"<# print(_g_dir.static_dir); #>img/passport/person.png\" class=\"friend-phone\">\n\t\t\t\t\t\t\t\t<div class=\"inp-wrap\">\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<input type=\"tel\" name=\"inviteCode\" id=\"f-inviteCode\" value=\"\" maxlength=\"11\" placeholder=\"填写推荐人手机号\" class=\"intext\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<div class=\"choose\">*&nbsp;选填</div>\t\t\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\n\t\t\t\t\t\t\n\t\t\t\t\t\t<input type=\"submit\" id=\"btn-submit\" class=\"btn\" value=\"下一步\">\n\t\t\t\t\t</div>\n\n\t\t\t\t</form>\n\t\t\t</div>\n\t\t\t\n\t\t</div>\n\t</div>\n\t<div class=\"bottom\" id=\"registeredAgreementBtn\" >我同意并已仔细阅读&lt;工资钱包服务协议&gt;</div>\n\t\t\t\n</script>\n<div class=\"page\"></div>","register.old.html":"<script type=\"text/template\" data-tpl=\"tpl-main-view\">\n\t\n\t\n\n\t<div class=\"page-login\">\n\t\t<div class=\"b-main\">\n\t\t\t<div class=\"widget-passport\">\n\t\t\t\t<form id=\"regform\">\n\t\t\t\t\t<div class=\"widget-passport-wrap\">\n\t\t\t\t\t\t<input type=\"text\" style=\"display:none;\">\n\t\t\t\t\t\t<input type=\"password\" style=\"display:none;\">\n\t\t\t\t\t\t<div class=\"row fix\">\n\t\t\t\t\t\t\t<div class=\"input-wrap\">\n\t\t\t\t\t\t\t\t<div class=\"con\">\n\t\t\t\t\t\t\t\t\t<input placeholder=\"请输入手机号\" maxlength=\"11\" value=\"\" type=\"tel\" id=\"f-phone\" name=\"phone\" class=\"inp-text\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<label class=\"phone\" for=\"f-phone\">手机号</label>\n\t\t\t\t\t\t</div>\t\n\t\t\t\t\t\t<div class=\"row fix\">\n\t\t\t\t\t\t\t<div class=\"input-wrap\">\n\t\t\t\t\t\t\t\t<div class=\"con con-pwd\">\n\t\t\t\t\t\t\t\t\t<input placeholder=\"请设置登录密码\" maxlength=\"12\" value=\"\" type=\"password\"  id=\"f-password\" name=\"password\" class=\"inp-text\">\n\t\t\t\t\t\t\t\t\t<span id=\"eye\" class=\"eye eye-hide\">&nbsp;</span>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<label class=\"password\" for=\"f-password\">设置密码</label>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"row fix\">\n\t\t\t\t\t\t\t<div class=\"input-wrap input-wrap-code\">\n\t\t\t\t\t\t\t\t<div class=\"con\">\n\t\t\t\t\t\t\t\t\t<input placeholder=\"请输入验证码\" maxlength=\"6\" value=\"\" type=\"tel\" id=\"f-code\" name=\"code\" class=\"inp-text\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<label class=\"msg\" for=\"f-code\">验证码</label>\n\t\t\t\t\t\t\t<span class=\"code-btn\" id=\"code-btn\" g-act-tap=\"true\">发送验证码</span>\n\t\t\t\t\t\t</div>\t\n\t\t\t\t\t</div>\n\t\t\t\t\t<div class=\"tip-bar\">\n\t\t\t\t\t\t如无法收到手机验证码，请选择<span id=\"code-btn2\" class=\"code-btn2\">语音验证码</span>\n\t\t\t\t\t</div>\n\t\t\t\t\t<div class=\"btn-bar  btn-bar-2\">\n\t\t\t\t\t\t<input type=\"submit\" g-act-tap=\"true\" value=\"完&nbsp;成\" id=\"btn-submit\" class=\"ui-button\">\n\t\t\t\t\t</div>\n\t\t\t\t\t<# if(self.type == \"geren\"){ #>\n\t\t\t\t\t<div class=\"agree-bar\" id=\"agree-bar\">\n\t\t\t\t\t\t<a data-tap-path=\"user/registeredAgreement\">我同意并已阅读&lt;工资钱包服务协议&gt;</a>\n\t\t\t\t\t</div>\n\t\t\t\t\t<# } #>\n\t\t\t\t\t<input type=\"hidden\" name=\"type\" id=\"f-type\">\n\t\t\t\t</form>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</script>\n<div class=\"page\"></div>","registerChoose.html":"<script type=\"text/template\" data-tpl=\"tpl-main-view\">\n\t\n\t<div class=\"page-bg\" id=\"page-bg\">\n\t\t<div class=\"page-item\" style=\"background:url('<# print(_g_dir.static_dir + \"img/geren.jpg?t=\" + REQUIRE_CONFIG.urlVersion); #>') center 0 no-repeat; background-size:100% 100%;<# if(self.type == \"geren\") { #> display:block; z-index:1; <# }else{ #> display:none; z-index:0; <# } #> \">\n\t\t\t<p class=\"s-1\">WELCOME CUSTOMER</p>\n\t\t\t<p class=\"s-2\">请选择您所在的用户组</p>\n\t\t</div>\n\t\t<div class=\"page-item\" style=\"background:url('<# print(_g_dir.static_dir + \"img/qiye.jpg?t=\" + REQUIRE_CONFIG.urlVersion); #>') center 0 no-repeat; background-size:100% 100%; <# if(self.type == \"qiye\") { #> display:block; z-index:1; <# }else{ #> display:none; z-index:0; <# } #> \">\n\t\t\t<p class=\"s-1\">WELCOME CUSTOMER</p>\n\t\t\t<p class=\"s-2\">请选择您所在的用户组</p>\n\t\t</div>\n\t</div>\n\t\n\t<div class=\"swiper-container\" id=\"p-swiper-container\" style=\"display:block;\">\n\t\t<div class=\"swiper-wrapper\">\n\t\t\t<div class=\"swiper-slide\" >\n\t\t\t\t<div class=\"item\" >\n\t\t\t\t\t<div class=\"top geren\">&nbsp;</div>\n\t\t\t\t\t<div class=\"bottom\">\n\t\t\t\t\t\t<div class=\"ico ico-geren\"></div>\n\t\t\t\t\t\t<div class=\"text\">我是个人用户</div>\n\t\t\t\t\t\t<div class=\"split-bar\"></div>\n\t\t\t\t\t\t<div class=\"btn-bar\">\n\t\t\t\t\t\t\t<a data-type=\"geren\" href=\"javascript:;\">确定</a>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t\t<div class=\"swiper-slide\" >\n\t\t\t\t<div class=\"item\" >\n\t\t\t\t\t<div class=\"top qiye\">&nbsp;</div>\n\t\t\t\t\t<div class=\"bottom\">\n\t\t\t\t\t\t<div class=\"ico ico-qiye\"></div>\n\t\t\t\t\t\t<div class=\"text\">我是企业绑定的个人用户</div>\n\t\t\t\t\t\t<div class=\"split-bar\"></div>\n\t\t\t\t\t\t<div class=\"btn-bar\">\n\t\t\t\t\t\t\t<a data-type=\"qiye\" href=\"javascript:;\">确定</a>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t\t\n\t\t</div>\n\t</div>\t\n\n</script>\n<div class=\"page\"></div>","registeredAgreement.html":"<script type=\"text/template\" data-tpl=\"tpl-main-view\">\n\t<div class=\"page-user-registeredAgreement\">\n\t\t<iframe width=\"100%\" class=\"safeIframe\" height=\"100%\" frameborder=\"0\" style=\"position:absolute;left:0;top:0;\"></iframe>\t\n\t</div>\n</script>\n<div class=\"page\"></div>"};
	for(var key in fileList){
		var moduleName = "js/lib/text-v2.0.10!" + prefix + key;
		var v = fileList[key];
		define(moduleName, v);
	}
})();