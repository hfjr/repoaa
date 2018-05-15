/**
 * @actionClass salaryLoan/reserve 
*/
define("app/vj/controller/salaryLoan/reserve", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"salaryLoan/salaryLoan1"
			];
			this.title = "工资易贷";
			this.showFooterNav = true;
			this.appUserId = _g_getParam("userId");
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadAsyValidator(function(){
				this.view("salaryLoan/reserve", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		init : function(){
			var _this = this;
			this.initValidator();
		},
		initValidator : function(){
			var _this = this;
			var form = $("#regform");
			var name = form.find("[name='phone']");
			var randomCode =  form.find("[name='code']");
			var randomCodeBtn = $("#code-btn");
			 
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
					"userName" : {
						rule : "require;checkUserName",
						msg : {
							require : "请输入姓名"
						}
					},
					
					"phone" : {
						rule : "require;mobile",
						msg : {
							require : "请输入手机号"
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
					
					
					checkUserName : function(el, params, field){
						var v = $.trim(el.val());
						var len = XDK.core.str.len(v);
						var rs = {type : "ok", msg : ""};
						if(len > 20){
							rs.type = "error";
							rs.msg = "姓名长度不能超过20个字符";
						}
						return rs;
					}
				}
			});
			o.bind("form.validSuccess", function(okList, form){
				_this.hideMsg();
				btn.attr("disabled", "disabled");
				var data = _this.getFormData(form);
				_this._submit(data, function(json){
					
					if(json === null){
						btn.removeAttr("disabled");	
						return;
					}
					if(json.footer.status == 200){
						
						this._showDialog();
						
					}else{
						btn.removeAttr("disabled");	
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
					
					onceApi.holder(function(){
						o.multiVald(["phone"], function(errorList, okList){
							if(errorList.length == 0){
								_this.hideMsg();
								var router = _this.getApiRouter( "salaryLoan/reserve/sendSMSCode" , {
									phone : $.trim(name.val())
								});
								_this.setXHR("salaryLoan/reserve/sendSMSCode", function(){
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
							
			})();		

			
			
			
			this.addUninstallAction(function(){
				o.destroy();
				randomCodeBtn.off();
				 
				btn.off();
				_stopAuto();
			
			});
		},
		
		_submit : function(data, callback){
			var router = this.getApiRouter("salaryLoan/reserve/submit", data);
			this.setXHR("salaryLoan/reserve/submit", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		_showDialog : function(){
			var _this = this;
			var html = this.render("successDialog");
			this.objList.successDialog = FixDialog.getInstance({
				showHandlebar : false,
				dialogClassName : "dialog-reserve-success",
				content : html,
				buttons : [
					{
						text : "确定",
						fn : function(){
							history.go(-1);
							//_this.redirect("salaryLoan/index");
						}
					}
				]
			});
		}
		
		
	}, AppController);
	
	return _class_;
}); 
