/**
 * @actionClass account/withdrawals
*/
define("app/vj/controller/account/withdrawals", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "提现";
			this.styleList = [
				"account/withdrawals/ma"
			];
			this.initData = null;
			this.needUserId = true;
			this.callbackPath = this.prevPath || "index/index";
			this.showBackHomeNav = true;
			
		},
		indexAction : function(){
			
			_class_.superClass.indexAction.apply(this, arguments);
			this.setInitData(function(){
				this.initPayPasswdDialog(function(){
					this.view("account/withdrawals/ma", function(view){
						this.renderContainer(view);
						this.container.find(".page").html(this.render("tpl-main-view"));
						this.init();
					});
				});
			});
			
		},
		setInitData: function(callback){
			var _this = this;
			var router = this.getApiRouter("queryWithdrawMaAccountAndEAccountInfo");
			this.setXHR("queryWithdrawMaAccountAndEAccountInfo", function(){
				return this.postData(router.url ,router.data, function(json){
					if(json.footer.status != 200){
						this.dialogList.tipDialog = FixDialog.sysTip2(json.footer.message, function(){
							_this.replaceDirect(_this.callbackPath);
						}, 2000);
						return;
					}
					if(json.footer.status == 200){
						this.initData = json.body;
					}
					callback.call(this);
				});
			});
		},
		init : function(){
			var _this = this;
			this.container.on("tap", "#tab-labels li", function(e){
				var t = $(this);
				var targetTab = $("#tab-content-" + t.attr("data-target-tab")); 
				t.addClass("current").siblings().removeClass("current");
				targetTab.show().siblings().hide();
			});
			this.initForm();
			this.initEaForm();
			
			

		},
		_send : function(formData, callback){
			
			var router = this.getApiRouter("withdrawMa", formData);
			this.setXHR("withdrawMa", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		_sendEa : function(formData, callback){
			
			var router = this.getApiRouter("withdrawEa", formData);
			this.setXHR("withdrawEa", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		
		initForm : function(){
			
			var btn = $("#btn-submit");
			var form =  $("#f-form");
			var input = $("#f-money");
			var _this = this;
			
			this.container.on("submit", "#f-form", function(e){
				e.preventDefault();
				if(btn.attr("disabled")){
					return;
				}
				btn.attr("disabled", "disabled");
				var formData = _this.getFormData(form);
				(function(){
					var _func = arguments.callee;
					_this._send(formData, function(json){
						btn.removeAttr("disabled");
						console.log(json);
						this.emptyPayPasswd();
						var _code = null;
						var messageData = null;
						if(json === null){
							return;
						}
						if(json.footer.status != 200){
							this.alertMsg(json.footer.message);
							return;
						}
						if(typeof(json.body.code) !== "undefined"){
							_code = json.body.code;
							messageData = json.body.message.split("|");
						}
						if(_code == null){
							_this.closePayPasswdDialog();
							_this.replaceDirect("account/withdrawalsSuccess", json.body);
						}else{
							//支付密码未启用
							if(_code == "201301"){
								formData.FirstResponseCode = _code;
								formData.paymentPassword = "";
								_func();
							}else if(_code == "201300"){
								//输入支付密码 
								_this.openPayPasswdDialog(function(message, jsonData, payPasswd){
									console.log(payPasswd);
									formData.FirstResponseCode = json.body.code;
									formData.paymentPassword = payPasswd;
									_func();
								}, false);
								
							}else if(_code == "200701"){
								//验证支付密码失败。还有两次机会
								_this._api_avlilableNumDialog(messageData);
								
							}else if(_code == "200702"){
								//小锁回调 关闭密码框
								_this.closePayPasswdDialog();
								_this._api_selfLockDialog(messageData);							
							}else if(_code == "200703"){
								//大锁回调 关闭密码框
								_this.closePayPasswdDialog();
								_this._api_serviceLockDialog(messageData);		
							}else if(_code == "201302"){
								//初始化提交（未输入密码）表单，小锁回调
								_this._api_selfLockDialog(messageData);							
							}else if(_code == "201303"){
								//初始化提交（未输入密码），大锁回调
								_this._api_serviceLockDialog(messageData);								
							}else{
								this.alertMsg(json.body.message);
							}
						}
						
					});
					
				})();
				
			
			}).on("input", "#f-money", function(e){
				var t = $(this);
				var _t = this;
				_this.onlyNum(this, 12);
				if(check()){
					btn.removeAttr("disabled");
				}else{
					btn.attr("disabled", "disabled");
				}
			});
			input.trigger("input");
			
			function check(){
				var _v = $.trim(input.val());
				var v = _v;
				var max = Number(input.attr("data-max"));
				var min = Number(input.attr("data-min"));
				
				console.log(_v);
				
				if(v == ""){
					return false;
				}
				v = Number(v);
				if(isNaN(v)){
					return false;
				}
				if(v == 0){
					return false;
				}
				if(v < min || v > max){
					return false;
				}
				
				return true;
				
			}
			
		},
		initEaForm : function(){
			
			var btn = $("#btn-submit2");
			var form =  $("#f-eaForm");
			var input = $("#f-money2");
			var _this = this;
			
			this.container.on("submit", "#f-eaForm", function(e){
				e.preventDefault();
				if(btn.attr("disabled")){
					return;
				}
				btn.attr("disabled", "disabled");
				var formData = _this.getFormData(form);
				(function(){
					var _func = arguments.callee;
					_this._sendEa(formData, function(json){
						btn.removeAttr("disabled");
						console.log(json);
						this.emptyPayPasswd();
						var _code = null;
						var messageData = null;
						if(json === null){
							return;
						}
						if(json.footer.status != 200){
							this.alertMsg(json.footer.message);
							return;
						}
						if(typeof(json.body.code) !== "undefined"){
							_code = json.body.code;
							messageData = json.body.message.split("|");
						}
						if(_code == null){
							_this.closePayPasswdDialog();
							_this.replaceDirect("account/withdrawalsEaSuccess", json.body);
						}else{
							//支付密码未启用
							if(_code == "201301"){
								formData.FirstResponseCode = _code;
								formData.paymentPassword = "";
								_func();
							}else if(_code == "201300"){
								//输入支付密码 
								_this.openPayPasswdDialog(function(message, jsonData, payPasswd){
									console.log(payPasswd);
									formData.FirstResponseCode = json.body.code;
									formData.paymentPassword = payPasswd;
									_func();
								}, false);
								
							}else if(_code == "200701"){
								//验证支付密码失败。还有两次机会
								_this._api_avlilableNumDialog(messageData);
								
							}else if(_code == "200702"){
								//小锁回调 关闭密码框
								_this.closePayPasswdDialog();
								_this._api_selfLockDialog(messageData);							
							}else if(_code == "200703"){
								//大锁回调 关闭密码框
								_this.closePayPasswdDialog();
								_this._api_serviceLockDialog(messageData);		
							}else if(_code == "201302"){
								//初始化提交（未输入密码）表单，小锁回调
								_this._api_selfLockDialog(messageData);							
							}else if(_code == "201303"){
								//初始化提交（未输入密码），大锁回调
								_this._api_serviceLockDialog(messageData);								
							}else{
								this.alertMsg(json.body.message);
							}
						}
						
					});
					
				})();
				
			
			}).on("input", "#f-money2", function(e){
				var t = $(this);
				var _t = this;
				_this.onlyNum(this, 12);
				if(check()){
					btn.removeAttr("disabled");
				}else{
					btn.attr("disabled", "disabled");
				}
			});
			input.trigger("input");
			
			function check(){
				var _v = $.trim(input.val());
				var v = _v;
				 
				var min = Number(input.attr("data-min"));
				
				console.log(_v);
				
				if(v == ""){
					return false;
				}
				v = Number(v);
				if(isNaN(v)){
					return false;
				}
				if(v == 0){
					return false;
				}
				if(v < min){
					return false;
				}
				
				return true;
				
			}
			
		}
		
		
	}, AppController);
	
	return _class_;
}); 
