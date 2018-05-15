/**
 * @actionClass account/payAgreement 
*/
define("app/vj/controller/account/payAgreement", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "工资钱包移动支付协议";
			this.showBackHomeNav = true;
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			
			this.view("account/payAgreement", function(view){
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
