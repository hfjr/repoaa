/**
 * @actionClass account/help 
*/
define("app/vj/controller/account/help", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "帮助中心";
			this.needUserId = true;
			this.showBackHomeNav = true;
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			
			this.view("account/help", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
		
		},
		
		init : function(){
			var _this = this;
			
		}
		
		
	}, AppController);
	
	return _class_;
}); 
