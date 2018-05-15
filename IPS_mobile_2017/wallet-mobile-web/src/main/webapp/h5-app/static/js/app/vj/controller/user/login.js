/**
 * @actionClass user/login - 登录
*/
define("app/vj/controller/user/login", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			var pat = /^(user\/register)|(user\/getPwd)|(user\/activeAccount)/;
			this.ref = _g_getParam("ref") ? decodeURIComponent(_g_getParam("ref")) : null;
			if(pat.test(this.prevUrl)){
				this.prevUrl = "index/index";
			};
			this.styleList = [
				"passport/login"
			];
			this.title = "登录";
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.init();
		},
		init : function(){
			var _this = this;
			this.loginListener(function(){
				this.replaceDirect("index/index");
			}, function(){
				this.view("user/login", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					var form = $("#loginform");
					var loginBtn = $("#btn-submit");
					var name = $("#f-phone");
					var pwd = $("#f-password");
					var pat = /^1[3-9]\d{9}$/;
					 
					function valid(){
						var nameValue = $.trim(name.val());
						var pwdValue = $.trim(pwd.val());
						if(nameValue == ""){
							_this.alertMsg("请输入手机号");
							return false;
						}
						
						if(!pat.test(nameValue)){
							_this.alertMsg("请输入正确的手机号码");
							return false;
						}
						
						
						if(pwdValue == ""){
							_this.alertMsg("请输入密码");
							return false;
						}
						if(pwdValue.length < 6 || pwdValue.length > 12){
							_this.alertMsg("手机号或密码错误");
							return false;
						}
						
						_this.hideMsg();
						return true;
					}
					form.on("submit", function(e){
						e.preventDefault();
						if(!valid()){
							return false;
						}
						loginBtn.attr("disabled", "disabled");
						_this._login(_this.getFormData(form), function(json){
							
							if(json === null){
								loginBtn.removeAttr("disabled");
								return;
							};
							if(json.footer.status == 200){
								this.setToken(this.json_encode(json.body));
								if(this.ref){
									
									window.location.replace(this.ref);
									//history.go(-1);
								}else{
									this.replaceDirect(this.prevUrl);
								}
							}else{
								loginBtn.removeAttr("disabled");
								_this.alertMsg(json.footer.message);
							}
						});
					});
					
					this.container.on("tap", "[data-act='btn-reg']", function(e){
						e.preventDefault();
						e.stopPropagation();
						_this.redirect("user/register");
					}).on("tap", "#eye", function(e){
						e.preventDefault();
						e.stopPropagation();
						var t = $(this);
						if(t.hasClass("eye-hide")){
							t.removeClass("eye-hide").addClass("eye-show");
							pwd[0].type = "text";
						}else{
							t.removeClass("eye-show").addClass("eye-hide");
							pwd[0].type = "password";
						}
					});
					this.addUninstallAction(function(){
						form.off();
						loginBtn.off();
						
					});				
					
				});
			});
			

		},
		_login : function(data, callback){
			var router = this.getApiRouter("user/login", data);
			this.setXHR("user/login", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				}, this.unlogincallback, true, function(){
					callback.call(this, null);
				});
			});
		}
		
		
	}, AppController);
	
	return _class_;
}); 
