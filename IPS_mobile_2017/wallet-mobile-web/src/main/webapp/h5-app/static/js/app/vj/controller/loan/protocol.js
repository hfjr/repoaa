/**
 * @actionClass loan/protocol
*/
define("app/vj/controller/loan/protocol", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = this.getParam("title");
			this.url = this.getParam("url");
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("loan/protocol", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
	}, AppController);
	
	return _class_;
}); 
