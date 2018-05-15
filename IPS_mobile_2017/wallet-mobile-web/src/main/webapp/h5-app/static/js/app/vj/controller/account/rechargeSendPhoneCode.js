/**
 * @actionClass account/rechargeSendPhoneCode
*/
define("app/vj/controller/account/rechargeSendPhoneCode", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "手机验证";
			this.styleList = ["account/recharge/sendPhoneCode"];
			this.callbackPath = this.getParam("callbackPath") || "index/index";
			console.log(this.callbackPath);
			this.needUserId = true;
			this.amount = this.getParam("amount");
			this.cardId = this.getParam("cardId");
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadAsyValidator(function(){
				this.init();
			});
		},
		init : function(){
			var _this = this;
			
			this.view("account/recharge/sendPhoneCode", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.initValidator();
			});
		},
		
		initValidator : function(){
			var _this = this;
			var form = $("#recharge-form");
			var btn = $("#btn-submit");
			var randomCode =  form.find("[name='dynamicCode']");
			var randomCodeBtn = $("#code-btn");
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
					
					"dynamicCode" : {
						rule : "require",
						msg : {
							require : "请输入验证码"
						}
					}
				},
				rules : {
					
				}
			});
			o.bind("form.validSuccess", function(okList, form){
				_this.hideMsg();
				btn.attr("disabled", "disabled");
				
				var data = _this.getFormData(form);
				console.log("startRecharge", data);
				_this._recharge(data, function(json){
					btn.removeAttr("disabled");
					if(json === null){
						return;
					}
					if(json.footer.status == 200){
						if(json.body.result == "true"){
							_this.replaceDirect("account/rechargeQueryNotice", {
								orderNo : data.orderNo,
								callbackPath : _this.callbackPath
							});
						}else{
							_this.alertMsg(json.footer.message);
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
					
					onceApi.holder(function(){
						
						var formData = _this.getFormData(form);
						_this.hideMsg();
						var router = _this.getApiRouter("rechargeToMaAlreadyBindCardSendCode", {
							amount : formData.amount,
							cardId : formData.cardId
						});
						_this.setXHR("rechargeToMaAlreadyBindCardSendCode", function(){
							this.postData(router.url, router.data, function(json){
								if(json === null){
									onceApi.reset();
									return;
								}
									
								if(json.footer.status == 200){
									form.find("[name='orderNo']").val(json.body.orderNo);
									_auto(json.body.remainTime, function(){
										onceApi.reset();
									});
								}else{
									_this.alertMsg(json.footer.message);
									onceApi.reset();
									
								}
							});
						});
							
						
						
					});
					
				});
				
			})();		
			
			
			this.addUninstallAction(function(){
				o.destroy();
				randomCodeBtn.off();
				btn.off();
				_stopAuto();
			
			});
			
		},
		_recharge : function(data, callback){
			callback = callback || function(){};
			var router = this.getApiRouter("doRechargeToMaAlreadyBind", data);
			this.setXHR("doRechargeToMaAlreadyBind", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
			
			
		}
		
		
	}, AppController);
	
	return _class_;
}); 
