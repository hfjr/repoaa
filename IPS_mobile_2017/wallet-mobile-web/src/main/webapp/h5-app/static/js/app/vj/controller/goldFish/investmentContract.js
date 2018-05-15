/**
 * @actionClass goldFish/investmentContract 
*/
define("app/vj/controller/goldFish/investmentContract", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.needUserId = true;
			this.orderId = this.getParam("orderId");
			this.title = "投资合同";
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("goldFish/investmentContract", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
	}, AppController);
	
	return _class_;
}); 
