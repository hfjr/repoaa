/**
 * @actionClass loan/savePersonalInformationSuccess - 资料提交成功
*/
define("app/vj/controller/loan/savePersonalInformationSuccess", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"loan/tips"
			];
			this.title = this.getParam("message");
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("loan/savePersonalInformationSuccess", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
		},
		init : function(){
			this.container.on("tap", "#btnSubmit", function(){
				history.go(-1);
			});
		}
	}, AppController);
	
	return _class_;
}); 
