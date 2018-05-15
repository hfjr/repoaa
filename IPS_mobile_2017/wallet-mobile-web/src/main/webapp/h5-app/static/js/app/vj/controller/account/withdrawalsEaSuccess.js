/**
 * @actionClass account/withdrawalsEaSuccess
*/
define("app/vj/controller/account/withdrawalsEaSuccess", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = this.getParam("title");
			this.styleList = [
				"account/withdrawals/eaSuccess"
			];
			this.needUserId = false;
		},
		indexAction : function(){
			
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("account/withdrawals/eaSuccess", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
			
		},
		init : function(){
			var _this = this;
			this.container.on("tap", "#btnSuccee", function(e){
				e.preventDefault();
				e.stopPropagation();
				history.go(-1);
			});
		}
	}, AppController);
	
	return _class_;
}); 
