/**
 * @actionClass vj/demo/tpl - 测试页面
*/
define("app/vj/controller/demo/tpl", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			//标题栏文字
			this.title = "测试";
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("demo/tpl", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
			
		}
	}, AppController);
	return _class_;
	
});