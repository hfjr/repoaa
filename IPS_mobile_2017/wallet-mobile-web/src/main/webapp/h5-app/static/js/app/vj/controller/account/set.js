/**
 * @actionClass account/login - 设置
*/
define("app/vj/controller/account/set", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "设置";
			this.needUserId = true;
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.init();
		},
		init : function(){
			var _this = this;
			
			this.view("account/set", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
							
				
			});
			
			

		}
		
		
	}, AppController);
	
	return _class_;
}); 
