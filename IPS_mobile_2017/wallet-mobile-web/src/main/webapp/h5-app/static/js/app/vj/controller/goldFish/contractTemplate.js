/**
 * @actionClass goldFish/contractTemplate 
*/
define("app/vj/controller/goldFish/contractTemplate", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = this.getParam("contractTitle");
			this.needUserId = true;
			
			
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("goldFish/contractTemplate", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
	}, AppController);
	
	return _class_;
}); 
