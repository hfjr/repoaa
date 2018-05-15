/**
 * @actionClass themes/zhongan 
*/
define("app/vj/controller/themes/zhongan", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				
			];
			this.title = "关于众安保险";
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("themes/zhongan", function(view){
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
