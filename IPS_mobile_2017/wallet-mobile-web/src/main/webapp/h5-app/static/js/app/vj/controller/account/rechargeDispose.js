/**
 * @actionClass account/rechargeDispose
*/
define("app/vj/controller/account/rechargeDispose", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "充值中";
			this.callbackPath = this.storage.getItem("rechargeCallBackPath") || "index/index";
			console.log(this.callbackPath);
			this.styleList = [
				"account/recharge/response"
			];
			this.msg = this.getParam("msg");
			
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.init();
		},
		init : function(){
			var _this = this;
			
			this.view("account/recharge/dispose", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
			
			this.container.on("tap", ".ui-button", function(e){
				e.preventDefault();
				e.stopPropagation();
				//_this.replaceDirect(_this.callbackPath);
				history.go(-1);
			});
			
		}
		
		
	}, AppController);
	
	return _class_;
}); 
