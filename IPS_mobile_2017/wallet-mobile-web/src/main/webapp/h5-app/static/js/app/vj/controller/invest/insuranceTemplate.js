/**
 * @actionClass invest/insuranceTemplate 
*/
define("app/vj/controller/invest/insuranceTemplate", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				
			];
			this.title = "保单示例";
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("invest/insuranceTemplate", function(view){
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
