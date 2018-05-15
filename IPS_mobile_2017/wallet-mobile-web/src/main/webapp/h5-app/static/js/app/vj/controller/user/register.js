
/**
 * @actionClass vj/user/register - 注册
*/
define("app/vj/controller/user/register", [], function(css){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.ref = _g_getParam("ref") ? decodeURIComponent(_g_getParam("ref")) : null;
			this.styleList = ["passport/login"];
			this.css = css;
			this.type = this.getParam("type") || "geren";
			this.title = this.type == "qiye" ? "快速激活" : "注册用户";
			this.sendToneCodePath = null;
			this.sendSMSCodePath = null;
			this.registerPath = null;
			this.updateCodePath();
			
		},
		updateCodePath : function(){
			this.sendToneCodePath = this.type == "geren" ? "user/sendRegisterToneNotice" : "user/sendActivateToneNotice";
			this.sendSMSCodePath = this.type == "geren" ? "user/sendRegisterSMSNotice" : "user/sendActivateSMSNotice";
			this.registerPath = this.type == "geren" ? "user/registerWithCode" : "user/updateActivateUserInfo";
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadAsyValidator(function(){
				this.init();
			});
		},
		
		init : function(){
			var _this = this;
			this.loginListener(function(){
				this.replaceDirect("index/index");
			}, function(){
				this.view("user/register", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.initValidator();
					
					
				});
			});
			

		},
		
		initValidator : function(){
			var _this = this;
			var form = $("#regform");
			var name = form.find("[name='phone']");
			var password = form.find("[name='password']");
			var randomCode =  form.find("[name='code']");
			var randomCodeBtn = $("#code-btn");
			var btn2 = $("#code-btn2");
			var btn = $("#btn-submit");
			var _stopAuto;
			var auto = null;
			function _stopAuto(){
				if(auto === null){
					return;
				}
				clearInterval(auto);
				auto = null;
			}
			
			
			o = new AsyValidator(form, {
				cacheValidResult : true,
				stopOnError : true,
				timely : false,				
				
				fields : {
					"phone" : {
						rule : "require;mobile",
						msg : {
							require : "请输入手机号"
						}
					},
					
					"password" : {
						rule : "require;password",
						
						msg : {
							require : "请输入密码"
						}
					},
					"code" : {
						rule : "require",
						msg : {
							require : "请输入验证码"
						}
					}
				},
				rules : {
					
					password : function(el, params, field){
						var password = $.trim(el.val());
						var rs = {type : "ok", msg : ""};
						if(password.length < 6){
							 rs.msg = "密码太短，不能少于6个字符";
							 rs.type = "error";
						}else if(password.length > 12){
							 rs.msg = "密码太长，不能超过12个字符";
							 rs.type = "error";
						}
						return rs;
						
					}
				}
			});
			o.bind("form.validSuccess", function(okList, form){
				_this.hideMsg();
				btn.attr("disabled", "disabled");
				var data = _this.getFormData(form);
				_this._register(data, function(json){
					btn.removeAttr("disabled");
					if(json === null){
						return;
					}
					if(json.footer.status == 200){
						this.setToken(this.json_encode(json.body));
						if(this.ref){
							
							window.location.replace(this.ref);
							//history.go(-1);
						}else{
							this.replaceDirect("index/index");
						}
					}else{
						_this.alertMsg(json.footer.message);
					}
					
				});
			}).bind("form.validError", function(errorList){
				_this.alertMsg(errorList[0].msg);
				
			});
			
			//子验证列表演示 
			(function(){
				var onceApi = new AsyValidator.Once();
				var btnNormalHtml = randomCodeBtn.html();
				function _auto(seconds, callback){
					callback = callback || function(){};
					var disabledCls = "ui-button-disabled";
					var start = seconds;
					randomCodeBtn.addClass(disabledCls);
					randomCodeBtn.html(start + "秒后重发");
					auto = setInterval(function(){
						if(start == 1){
							_stopAuto();
							randomCodeBtn.html(btnNormalHtml).removeClass(disabledCls);
							callback();
							return;
						};
						start -= 1;
						randomCodeBtn.html(start + "秒后重发");						
					}, 1000);
				}
				
				randomCodeBtn.on("click", function(e, data){
					e.preventDefault();
					e.stopPropagation();
					o.resetFieldState("phone");
					o.resetFieldState("password");
					onceApi.holder(function(){
						o.multiVald(["phone", "password"], function(errorList, okList){
							if(errorList.length == 0){
								_this.hideMsg();
								var router = _this.getApiRouter(data == "yuyin" ? _this.sendToneCodePath : _this.sendSMSCodePath, {
									phone : $.trim(name.val()),
									password : $.trim(password.val())
								});
								_this.setXHR("user/sendRandomCode", function(){
									this.postData(router.url, router.data, function(json){
										if(json === null){
											onceApi.reset();
											return;
										}
											
										if(json.footer.status == 200){
											_auto(json.body.remainTime, function(){
												onceApi.reset();
											});
										}else{
											_this.alertMsg(json.footer.message);
											
											onceApi.reset();
											
										}
									});
								});
							}else{
								_this.alertMsg(errorList[0].msg);
								onceApi.reset();
							}
						});
					});
					
				});
				btn2.on("click", function(e){
					e.preventDefault();
					e.stopPropagation();
					randomCodeBtn.trigger("click", "yuyin");
				});
			})();		
			
			
			this.addUninstallAction(function(){
				o.destroy();
				randomCodeBtn.off();
				btn2.off();
				btn.off();
				_stopAuto();
			
			});
			this.container.on("tap", "#eye", function(e){
				e.preventDefault();
				e.stopPropagation();
				var t = $(this);
				if(t.hasClass("eye-hide")){
					t.removeClass("eye-hide").addClass("eye-show");
					password[0].type = "text";
				}else{
					t.removeClass("eye-show").addClass("eye-hide");
					password[0].type = "password";
				}
			}).on("click", "#registeredAgreementBtn", function(){
				var router = _this.getApiRouter("user/registeredAgreement");
				window.location.href = router.url;
			});
		},
		
		_register : function(data, callback){
			var path = this.registerPath;
			var router = this.getApiRouter(path, data);
			this.setXHR(path, function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		}
		
		
		
		
	}, AppController);
	
	return _class_;
}); 
