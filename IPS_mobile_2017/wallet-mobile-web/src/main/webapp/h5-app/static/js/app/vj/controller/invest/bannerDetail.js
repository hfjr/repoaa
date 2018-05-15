/**
 * @actionClass vj/invest/bannerDetail
*/
define("app/vj/controller/invest/bannerDetail", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = this.getParam("title");
			this.url = this.getParam("url");
			this.showBackHomeNav = true;
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;
			this.view("invest/bannerDetail", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
	}, AppController);
	return _class_;
	
});

