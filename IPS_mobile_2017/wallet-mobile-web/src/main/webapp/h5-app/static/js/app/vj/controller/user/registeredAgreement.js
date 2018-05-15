
/**
 * @actionClass vj/user/registeredAgreement - 注册协议
*/
define("app/vj/controller/user/registeredAgreement", [], function(css){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "工资钱包服务协议";
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;
			this.view("user/registeredAgreement", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
		},
		init : function(){
			var router = this.getApiRouter("user/registeredAgreement");
			var iframe = this.container.find(".safeIframe");
			iframe.attr({
				src : router.url
			});
		}
	}, AppController);
	
	return _class_;
}); 
