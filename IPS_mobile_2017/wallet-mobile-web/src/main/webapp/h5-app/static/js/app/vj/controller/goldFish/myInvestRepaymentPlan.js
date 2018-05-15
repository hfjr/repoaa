/**
 * @actionClass goldFish/myInvestRepaymentPlan
*/
define("app/vj/controller/goldFish/myInvestRepaymentPlan", [], function(){
	//console.log(css);
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["goldfish/investRepaymentPlan"];
			this.title = "回款计划";
			this.needUserId = true;
			this.orderId = this.getParam("orderId");
			this.initData = [];
			this.showBackHomeNav = false;
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("goldFish/repaymentPlan", {
				orderId : this.orderId
			});
			this.setXHR("goldFish/repaymentPlan", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status == 200){
						this.initData = json.body;
					}
					callback.call(this);
				});
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.init();
		},
		init : function(){
			this.setInitData(function(){
				this.view("goldFish/myInvestRepaymentPlan", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));

				});
			});
		}
	}, AppController);
	return _class_;
});


