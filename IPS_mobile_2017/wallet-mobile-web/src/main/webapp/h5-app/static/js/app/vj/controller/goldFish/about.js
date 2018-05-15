/**
 * @actionClass goldFish/about 
*/
define("app/vj/controller/goldFish/about", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "小金鱼";
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("goldFish/about", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
	}, AppController);
	
	return _class_;
}); 
