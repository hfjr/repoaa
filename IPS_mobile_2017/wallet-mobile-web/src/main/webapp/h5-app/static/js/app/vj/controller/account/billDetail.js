/**
 * @actionClass account/billDetail 
*/
define("app/vj/controller/account/billDetail", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "明细";
			this.styleList = [
				"account/dealDetail"
			];
			this.initData = null;
			this.needUserId = true;
			this.billType = this.getParam("billType");
			this.orderId = this.getParam("orderId");
			this.showBackHomeNav = true;
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("queryBillDetail", {
				billType : this.billType,
				orderId : this.orderId
			});
			this.setXHR("queryBillDetail", function(json){
				return this.postData(router.url, router.data, function(json){
					if(json == null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData = json.body;
					callback.call(this);
				});
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			
			var _this = this;
			if(this.billType === null || this.orderId === null){
				this.alertMsg("缺少订单id或者订单类型");
			}
			this.setInitData(function(){
				this.view("account/billDetail", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		
		init : function(){
			var _this = this;
			
		}
		
		
		
	}, AppController);
	
	return _class_;
}); 
