/**
 * @actionClass eAccount/maToEa
*/
define("app/vj/controller/eAccount/maToEa", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "转入";
			this.styleList = [
				"account/withdrawals/ma"
			];
			this.initData = null;
			this.needUserId = true;
			this.callbackPath = this.prevPath || "index/index";
			this.showBackHomeNav = true;
			
		},
		setInitData : function(callback){
			var router = this.getApiRouter("cunqianguan/queryTAccountInComeBalance");
			this.setXHR("queryTAccountInComeBalance", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}	
					if(json.footer.status != 200){
						this.alertMsg(json.body.message);
						return;
					}
					this.initData = json.body;
					callback.call(this);
				});
			});
		},
		indexAction : function(){
			
			_class_.superClass.indexAction.apply(this, arguments);
			this.setInitData(function(){
				this.initPayPasswdDialog(function(){
					this.view("eAccount/maToEa", function(view){
						this.renderContainer(view);
						this.container.find(".page").html(this.render("tpl-main-view"));
						this.init();
					});
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
		 
			
			

		},
		_send : function(formData, callback){
			
			var router = this.getApiRouter("cunqianguan/shengou", formData);
			this.setXHR("cunqianguan/shengou", function(){
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
				var mol = Number(input.attr("data-mol"));
				if(v == ""){
					return false;
				}
				v = Number(v);
				
				if(isNaN(v)){
					return false;
				}
				
				if(v % mol != 0){
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
		}
	}, AppController);
	
	return _class_;
}); 
