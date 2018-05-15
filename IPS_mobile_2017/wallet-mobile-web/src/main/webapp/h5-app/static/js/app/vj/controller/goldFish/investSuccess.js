/**
 * @actionClass goldFish/investSuccess 
*/
define("app/vj/controller/goldFish/investSuccess", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"goldfish/tips"
			];
			this.productTypeCode = this.productTypeMap.XIAOJINYU;
			
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("goldFish/investSuccess", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
	}, AppController);
	
	return _class_;
}); 
