/**
 * @actionClass invest/contractTemplate
*/
define("app/vj/controller/invest/contractTemplate", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "工资钱包债权转让合同";
			this.needUserId = true;
			this.showBackHomeNav = true;
			this.productId = this.getParam("productId");
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			if(!this.productId){
				this.alertMsg("undefined productId");
				return;
			}
			this.view("invest/contractTemplate", function(view){
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
