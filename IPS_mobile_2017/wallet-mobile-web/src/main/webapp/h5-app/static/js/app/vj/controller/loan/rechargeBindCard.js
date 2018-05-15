/**
 * @actionClass loan/recharge
*/
define("app/vj/controller/loan/rechargeBindCard", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "绑定银行卡";
			this.styleList = ["loan/recharge/bindCard"];
			this.bankName = this.getParam("bankName");
			this.bankCode = this.getParam("bankCode");
			this.amount = this.getParam("amount");
			this.callbackPath = this.getParam("callbackPath") || "index/index";
			this.needUserId = true;
			this.initData = null;
			this.phoneNoHelpImg = null;
			this.isLoadingPhoneNoHelpImg = false;
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("loan/recharge/bindCardInit", {
				bankCode : this.bankCode
			});
			this.setXHR("bindCardInit", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;	
					}
					this.initData = json.body;
					callback.call(this);
				});
			});
		},
		_setPhoneNoHelpImg : function(callback){
			var _this = this;
			var url = this.initData.cardBindMobilePhoneNoHelpURL;
			var img = null;
			if(_this.isLoadingPhoneNoHelpImg){
				return;
			}
			_this.isLoadingPhoneNoHelpImg = true;
			_this.showLoadingBar();
			if(url){
				img = new Image();
				img.onload = function(){
					_this.isLoadingPhoneNoHelpImg = false;
					_this.phoneNoHelpImg = this;
					_this.hideLoadingBar();
					callback.call(_this);
				};
				img.src = url;
			}else{
				_this.isLoadingPhoneNoHelpImg = false;
				_this.hideLoadingBar();
				callback.call(this);
			}
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadAsyValidator(function(){
				this.setInitData(function(){
					this.init();
				});
			});
		},
		init : function(){
			var _this = this;
			
			this.view("loan/recharge/bindCard", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.initValidator();
			});
		},
		initValidator : function(){
			var _this = this;
			var form = $("#bind-form");
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
					"realName" : {
						rule : "require;checkRealName",
						msg : {
							require : "请输入持卡人姓名"
						}
					},
					
					"certNo" : {
						rule : "require;checkCertNo",
						msg : {
							require : "请输入身份证号"
						}
					},
					"cardNo" : {
						rule : "require",
						msg : {
							require : "请输入银行卡号"
						}
					},
					
					"cardBindMobilePhoneNo" : {
						rule : "require;mobile",
						msg : {
							require : "请输入银行预留手机号"
						}
					},
					
					"dynamicCode" : {
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
					},
					checkRealName : function(el, params, field){
						var v = $.trim(el.val());
						var len = XDK.core.str.len(v);
						var rs = {type : "ok", msg : ""};
						if(len > 20){
							rs.type = "error";
							rs.msg = "持卡人姓名长度不能超过20个字符";
						}
						return rs;
					},
					checkCard : function(el, params, field){
						var v = $.trim(el.val());
						var pat = /^\d{16,19}$/;
						var rs = {type : "ok", msg : ""};
						if(!pat.test(v)){
							rs.type = "error";
							rs.msg = "请输入正确的的银行卡号";
						}
						return rs;
					}
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
					//json.footer.status = 200;
					//json.body = { 
						//result : "true"
					//};
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
					o.resetFieldState("realName");
					o.resetFieldState("certNo");
					o.resetFieldState("cardNo");
					o.resetFieldState("cardBindMobilePhoneNo");
					
					
					onceApi.holder(function(){
						o.multiVald(["realName", "certNo", "cardNo", "cardBindMobilePhoneNo"], function(errorList, okList){
							if(errorList.length == 0){
								var formData = _this.getFormData(form);
								_this.hideMsg();
								var router = _this.getApiRouter("rechargeToMaSendCode", {
									amount : formData.amount,
									cardNo : formData.cardNo,
									cardBindMobilePhoneNo : formData.cardBindMobilePhoneNo,
									certNo : formData.certNo,
									realName : formData.realName,
									bankCode : formData.bankCode
								});
								_this.setXHR("rechargeToMaSendCode", function(){
									this.postData(router.url, router.data, function(json){
										if(json === null){
											onceApi.reset();
											return;
										}
											
										if(json.footer.status == 200){
											form.find("[name='orderNo']").val(json.body.orderNo);
											form.find("[name='cardId']").val(json.body.cardId);
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
				
			})();		
			
			
			this.container.on("click", "#cardNoticeBtn", function(e){
				e.preventDefault();
				e.stopPropagation();
				_this._setPhoneNoHelpImg(function(){
					_this.dialogList.phoneNumberTip = FixDialog.getInstance({
						id : "phoneNumberTip",
						dialogClassName : "dialog-phoneNumberTip-wrap",
						showHandlebar : false,
						closeOnAlphaClick : true,
						content : "<div class='imgWraper'></div>",
						events : {
							init : function(dialog){
								this.DOM.dialogWrap.find(".imgWraper").append(_this.phoneNoHelpImg).bind("click", function(){
									dialog.close();
								});
							},
							beforeclose : function(){
								this.DOM.dialogWrap.find(".imgWraper").off();
							}
						}
					});
				});
				
			})
			
			this.addUninstallAction(function(){
				o.destroy();
				randomCodeBtn.off();
				btn.off();
				_stopAuto();
			
			});
			
		},
		_recharge : function(data, callback){
			callback = callback || function(){};
			var router = this.getApiRouter("doRechargeToMa", data);
			this.setXHR("doRechargeToMa", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
			
			
		}
	}, AppController);
	
	return _class_;
}); 
