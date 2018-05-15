/**
 * @actionClass account/rechargeSuccess
*/
define("app/vj/controller/account/rechargeSuccess", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "充值成功";
			this.styleList = [
				"account/recharge/index",
				"account/recharge/success"
			];
			this.needUserId = true;
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.init();
		},
		init : function(){
			var _this = this;
			
			this.view("account/recharge/success", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
							
				
			});
			
			

		}
		
		
	}, AppController);
	
	return _class_;
}); 
