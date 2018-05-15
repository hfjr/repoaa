/**
 * @actionClass themes/reg
*/
define("app/vj/controller/themes/reg", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.logo = "static/img/logo.png";
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("themes/reg", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
		},
		init : function(){
			var _this = this;
			
			this.container.on("click", "#btn", function(){
				//_this.alertMsg("测试消息");
				
				window.location.href = "./index.html#user/login";
			});
		}
	}, AppController);
	return _class_;
	
});