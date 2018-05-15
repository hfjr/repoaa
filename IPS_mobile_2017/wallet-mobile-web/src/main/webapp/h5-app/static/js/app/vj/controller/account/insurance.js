/**
 * @actionClass account/insurance 
*/
define("app/vj/controller/account/insurance", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "我的保障";
			this.needUserId = true;
			this.initData = null;
			this.showBackHomeNav = true;
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			
			this.view("account/insurance", function(view){
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
