/**
 * @actionClass goldFish/investmentContractTemplate 
*/
define("app/vj/controller/goldFish/investmentContractTemplate", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = this.getParam("contractTitle");
			this.productId = this.getParam("productId");
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("goldFish/investmentContractTemplate", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
	}, AppController);
	
	return _class_;
}); 
