/**
 * @actionClass vj/invest/initInvest - 投资初始化
*/
define("app/vj/controller/invest/initInvest", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["invest/initInvest"];
			//标题栏文字
			this.title = "投资";
			this.needUserId = true;
			this.initData = null;
			this.showBackHomeNav = true;
			this.id = this.getParam("id");
			
		},
		setInitData : function(callback, showLoadingBar){
			var router = this.getApiRouter("queryProductCanBuy", {
				productId : this.id
			});
			this.setXHR("queryProductCanBuy", function(){
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
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			
			this.setInitData(function(){
				this.initPayPasswdDialog(function(){
					this.view("invest/initInvest", function(view){
						this.renderContainer(view);
						this.container.find(".page .main-view").html(this.render("tpl-main-view"));
						this.init();
					});
				});
			});
		},
		_send : function(formData, callback){
			
			var router = this.getApiRouter("placeOrder", formData);
			this.setXHR("placeOrder", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		init : function(){
			
			var btn = $("#f-btn");
			var form =  $("#f-form");
			var input = $("#f-amount");
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
						if(_code == "200100"){
							_this.closePayPasswdDialog();
							_this.storage.removeItem("invest/index");
							_this.replaceDirect("invest/investSuccess", json.body);
							return;
						}else if(_code == "201301"){
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
						
						
					});
					
				})();
				
			
			}).on("input", "#f-amount", function(e){
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
				
				console.log(v);
				
				if(isNaN(v)){
					return false;
				}
				
				if(v == 0){
					return false;
				}
				
				if(v % mol != 0){
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

