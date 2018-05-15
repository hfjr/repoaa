/**
 * @actionClass account/payPasswdLock
*/
define("app/vj/controller/account/payPasswdLock", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "解锁支付密码";
			this.styleList = ["account/payPasswdForget"];
			this.needUserId = true;
			//下一页设置成功后回调页面
			this.callbackPath = this.prevPath || "account/payPasswd";
			this.initData = null;
			
		},
		
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("resetPaymentPasswordForForgetPasswordIniti");
			this.setXHR("resetPaymentPasswordForForgetPasswordIniti", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
					};
					this.initData = json.body;
					callback.call(this);
				});
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadAsyValidator(function(){
				this.setInitData(function(){
					this.view("account/payPasswd/forget", function(view){
						this.renderContainer(view);
						this.container.find(".page").html(this.render("tpl-main-view"));
						this.init();
					});
				});
			});
		},
		
		init : function(){
			var _this = this;
			this.initValidator();
		},
		initValidator : function(){
			var _this = this;
			var form = $("#bind-form");
			var btn = $("#btn-submit");
			var randomCode =  form.find("[name='code']");
			var randomCodeBtn = $("#code-btn");
			var btn2 = $("#code-btn2");
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
					"paymentCardNo" : {
						rule : "require",
						msg : {
							require : "请输入银行卡号"
						}
					},					
					"identityNo" : {
						rule : "require;checkCertNo",
						msg : {
							require : "请输入身份证号"
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
					checkCertNo : function(el, params, field){
						var pat = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
						var v = $.trim(el.val());
						var rs = {type : "ok", msg : ""};
						if(!pat.test(v)){
							rs.type = "error";
							rs.msg = "请输入正确的身份证号码";
						}
						return rs;
					}
				}
			});
			o.bind("form.validSuccess", function(okList, form){
				_this.hideMsg();
				btn.attr("disabled", "disabled");
				var data = _this.getFormData(form);
				
				_this._updatePasswd(data, function(json){
					btn.removeAttr("disabled");
					var dialogData = null;
					if(json === null){
						return;
					}
					if(json.footer.status == 200){
						
						if(json.body.code == "201000"){
							_this.replaceDirect("account/payPasswdSet", {
								callbackPath : _this.callbackPath
							});
						}else if(json.body.code == "201001"){
							dialogData = json.body.message.split("|");
							_this.dialogList.errorNumDialog = FixDialog.getInstance({
								showHandlebar : false,
								content : "<div class=\"c-sysTip\">"+ dialogData[0] +"</div>",
								buttons : [
									{
										text : dialogData[1]
									},
									{
										text : dialogData[2]
									}
									
								]
							});
						}else if(json.body.code == "201002"){
							dialogData = json.body.message.split("|");
							//已锁定 ,联系客服解锁
							_this.dialogList.serviceHelpDialog = FixDialog.getInstance({
								showHandlebar : false,
								content : "<div class=\"c-sysTip\">"+ dialogData[0] +"</div>",
								buttons : [
									{
										text : dialogData[1],
										fn : function(){
											history.go(-1);
										}
									},
									{
										text : "<a href='tel:"+ _this.serviceTel +"' class='telBtn' style='display:block; color:#4ac0f0;'>" + dialogData[2] + "</span>",
										fn : function(){
											
										}
									}
									
								],
								events : {
									init : function(){
										var dialog = this;
										this.DOM.btnBar.find(".telBtn").on("tap", function(e){
											e.stopPropagation();
											
											
										});
									},
									beforeclose : function(){
										this.DOM.btnBar.find(".telBtn").off();
									}
									
								}
							});
						}else{
							_this.alertMsg(json.body.message);
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
					o.resetFieldState("paymentCardNo");
					o.resetFieldState("identityNo");
					
					
					onceApi.holder(function(){
						o.multiVald(["paymentCardNo", "identityNo"], function(errorList, okList){
							if(errorList.length == 0){
								var formData = _this.getFormData(form);
								_this.hideMsg();
								var router = _this.getApiRouter(data == "yuyin" ? "sendForgetToneNoticeForUnlockPaymentPassword" : "sendForgetSMSNoticeForUnlockPaymentPassword", {
									paymentCardNo : formData.paymentCardNo,
									identityNo : formData.identityNo
								});
								_this.setXHR("sendRandomCode", function(){
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
				btn.off();
				_stopAuto();
			
			});
			
		},
		_updatePasswd : function(data, callback){
			callback = callback || function(){};
			var router = this.getApiRouter("unlockPaymentPassword", data);
			this.setXHR("unlockPaymentPassword", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		}
		
		
	}, AppController);
	return _class_;
}); 
