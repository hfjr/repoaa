
/**
 * @actionClass vj/user/getPasswordSuccess - 找回密码成功
*/
define("app/vj/controller/user/getPasswordSuccess", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "激活/忘记密码";
			this.styleList = [
				"passport/login"
			];
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.loginListener(function(){
				this.replaceDirect("index/index");
			}, function(){
				this.view("user/getPasswordSuccess", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		init : function(){
			var _this = this;
			this.container.on("click", "#btn", function(){
				_this.replaceDirect("user/login");
			})
		}
	}, AppController);
	
	return _class_;
}); 
