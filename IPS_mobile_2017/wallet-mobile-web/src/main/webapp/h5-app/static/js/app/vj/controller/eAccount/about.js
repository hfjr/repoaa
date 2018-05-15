/**
 * @actionClass eAccount/about - 关于我的电子工资单
*/
define("app/vj/controller/eAccount/about", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "我的电子工资单";
			this.needUserId = true;
			this.styleList = [
				//"eAccount/about"
			];
			
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("eAccount/about", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
		}
		
	}, AppController);
	
	return _class_;
}); 
