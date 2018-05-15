/**
 * @actionClass salaryLoan/index 
*/
define("app/vj/controller/salaryLoan/index", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"salaryLoan/salaryLoan"
			];
			this.title = "工资易贷";
			this.showFooterNav = true;
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.view("salaryLoan/index", function(view){
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
