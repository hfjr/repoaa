/**
 * @actionClass account/rechargeLarge 
*/
define("app/vj/controller/account/rechargeLarge", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"account/recharge/largeRecharge"
			];
			this.title = "大额充值";
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("account/recharge/rechargeLarge", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
		},
		init : function(){
			
		}
	}, AppController);
	
	return _class_;
}); 
