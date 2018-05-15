/**
 * @actionClass account/payPasswd
*/
define("app/vj/controller/account/payPasswd", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "支付密码";
			this.styleList = ["account/payPasswd"];
			this.needUserId = true;
			//初始化数据
			this.initData = {
				isSetted : false
			};
			this.showBackHomeNav = true;
			this.payPasswdHtml = null;
		},
		
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("paymentPasswordIniti");
			this.setXHR("paymentPasswordIniti", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;	
					}
					this.initData.isSetted = json.body.IsSwitch == "yes";
					callback.call(this);
				});
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.initPayPasswdDialog(function(){
				
				this.setInitData(function(){
					this.view("account/payPasswd/index", function(view){
						this.renderContainer(view);
						this.container.find(".page").html(this.render("tpl-main-view"));
						this.init();
						 
					});
				});
			});
		},
		
		
		
		init : function(){
			var _this = this;
			var btn = $("#stateBtn");
			var state = btn.find(".state");
			var on = "state-on";
			var off = "state-off";
			this.container.on("tap swipeleft swiperight", "#stateBtn", function(e){
				e.preventDefault();
				e.stopPropagation();
				toggle();
			}).on("tap", "[data-act='editPayPasswd']", function(e){
				e.preventDefault();
				e.stopPropagation();
				
				//验证支付密码，验证成功后跳转到支付密码修改页面
				_this.openPayPasswdDialog(function(message, data, passwd){
					
					_this.redirect("account/payPasswdResetForRemember");
				});
				
				
				
			}).on("tap", "[data-act='forgetPayPasswd']", function(e){
				e.preventDefault();
				e.stopPropagation();
				
			});

			
			function toggle(){
				if(state.hasClass(on)){
					
					//关闭支付密码
					_this.openPayPasswdDialog(function(message, data, passwd){
						
						var router = this.getApiRouter("closePaymentPassword", {
							paymentPassword : passwd
						});
						this.setXHR("closePaymentPassword", function(){
							return this.postData(router.url, router.data, function(json){
								if(json == null){
									return;
								}
								if(json.footer.status != 200){
									this.alertMsg(json.footer.message);
									return;
								}
								//还有几次机会
								if(json.body.code == "200701"){
									this.emptyPayPasswd();
									messageData = json.body.message.split("|");
									this.dialogList.confirmDialog = FixDialog.getInstance({
										showHandlebar : false,
										content : "<div class=\"c-sysTip\">"+ messageData[0] +"</div>",
										buttons : [
											{
												text : messageData[1],
												fn : function(){
													_this.closePayPasswdDialog();
												}
											},
											{
												text : messageData[2],
												fn : function(){
													
												}
											}
											
										]
									});
									return;
								}
								//触发小锁
								if(json.body.code == "200702"){
									
									this.emptyPayPasswd();
									messageData = json.body.message.split("|");
									this.dialogList.confirmDialog = FixDialog.getInstance({
										showHandlebar : false,
										content : "<div class=\"c-sysTip\">"+ messageData[0] +"</div>",
										buttons : [
											{
												text : messageData[1],
												fn : function(){
													_this.replaceDirect("account/index");
												}
											},
											{
												text : messageData[2],
												fn : function(){
													_this.replaceDirect("account/payPasswdLock");
												}
											}
											
										]
									});
									
									return;
								}								
								//大锁状态回调
								
								
								if(json.body.code == "200601"){
									this.alertMsg(json.body.message);
									return;
								}
								if(json.body.code == "200600"){
									this.validPayPasswdLocked = false;
									this.closePayPasswdDialog();
									//回调
									state.removeClass(on).addClass(off);
									$("#passwdMenuList").hide();
									return;
								}
								
							});
						});
					}, false);
				}else{
					//开启
					//state.removeClass(off).addClass(on);
					_this.redirect("account/payPasswdSet");
				}
			}
		}
		
	}, AppController);
	
	return _class_;
}); 
