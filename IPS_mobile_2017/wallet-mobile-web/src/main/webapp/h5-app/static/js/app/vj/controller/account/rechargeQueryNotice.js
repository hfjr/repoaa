/**
 * @actionClass account/rechargeQueryNotice
*/
define("app/vj/controller/account/rechargeQueryNotice", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "充值处理";
			this.callbackPath = this.getParam("callbackPath") || "index/index"; 
			this.orderNo = this.getParam("orderNo");
			this.needUserId = true;
			this.delay1 = null;
			this.delay2 = null;
			this.pollingDelay = null;
		},
		
		stopDelay1 : function(){
			if(this.delay1 !== null){
				clearTimeout(this.delay1);
				this.delay1 = null;
			}
		},
		stopDelay2 : function(){
			if(this.delay2 !== null){
				clearTimeout(this.delay2);
				this.delay2 = null;
			}
		},
		stopPollingDelay : function(){
			if(this.pollingDelay !== null){
				clearTimeout(this.pollingDelay);
				this.pollingDelay = null;
			}
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			
			this.init();
			
		},
		init : function(){
			var _this = this;
			this.view("account/recharge/queryNotice", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.animate();	
				
				this.delay1 = setTimeout(function(){
					_this.pollingOrderState();
				}, 2000);
			});
			this.addUninstallAction(function(){
				this.stopDelay1();
				this.stopDelay2();
				this.stopPollingDelay();
			});
		},
		animate : function(){
			var stateBar = $("#query-state");
			var _this = this;
			var number = 1;
			(function(){
				var _func = arguments.callee;
				_this.stopDelay2();
				_this.delay2 = setTimeout(function(){
					number += 1;
					if(number > 3){
						number = 1;
					}
					stateBar[0].className = "query-state query-state-" + number;
					_func();
				}, 500);
			})()
		},
		pollingOrderState : function(){
			var router = this.getApiRouter("queryRechargeNotice", {
				orderNo : this.orderNo
			});
			var _this = this;
			var disposeNum = 0;
			(function(){
				var _func = arguments.callee;
				_this.setXHR("queryRechargeNotice", function(){
					return _this.postData(router.url, router.data, function(json){
						if(json == null){
							return;
						}
						
						//模拟充值处理中
						
						//json.footer.status = 200;
						//json.body = {};
						//json.body.code = "recharge_ma_confirm";
						//json.body.message = "充值成功";
						
						if(json.footer.status != 200){
							//_this.alertMsg(json.footer.message);
							_this.replaceDirect("account/rechargeFail", {
								msg : json.footer.message,
								callbackPath : _this.callbackPath
							});
							return;
						}
						
						
						
						if(json.body.code == "recharge_ma_confirm"){
							_this.replaceDirect("account/rechargeConfirm", {
								msg : json.body.message
								
							});
						}else if(json.body.code == "recharge_ma_confirm_fail"){
							_this.replaceDirect("account/rechargeFail", {
								msg : json.body.message
								
							});
						}else if(json.body.code == "recharge_ma_confirm_dispose"){
							disposeNum += 1;
							console.log(disposeNum);
							if(disposeNum >= 3){
								console.log("disposeNum is max, exit polling");
								_this.replaceDirect("account/rechargeDispose", {
									msg : json.body.message
									
								});
							}else{
								_this.stopPollingDelay();
								_this.pollingDelay = setTimeout(function(){
									_func.call(_this);
								}, 1000);
							}
						}
					}, _this.unlogincallback, false);
				});
			})();
			
		}
	}, AppController);
	
	return _class_;
}); 
