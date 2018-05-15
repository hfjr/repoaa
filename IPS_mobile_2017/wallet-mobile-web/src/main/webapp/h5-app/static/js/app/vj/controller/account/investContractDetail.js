/**
 * @actionClass account/investContractDetail
*/
define("app/vj/controller/account/investContractDetail", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "投资合同";
			this.needUserId = true;
			this.orderId = this.getParam("orderId");
			this.initData = null;
			this.showBackHomeNav = true;
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;
			this.view("account/investmentContractDetail", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
		},
		init : function(){
		
		
		}
	}, AppController);
	return _class_;
});


