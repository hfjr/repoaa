/**
 * @actionClass about/coop 
*/
define("app/vj/controller/about/coop", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"about/coop"
			];
			this.title = "顶级合作伙伴";
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("about/coop", function(view){
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
