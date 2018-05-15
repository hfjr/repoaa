/**
 * @actionClass loan/about
*/
define("app/vj/controller/loan/about", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = this.getParam("title");
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("loan/about", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
	}, AppController);
	
	return _class_;
}); 
