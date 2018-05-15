/**
 * @actionClass goldFish/myLoanDetailOrder 
*/
define("app/vj/controller/goldFish/myLoanDetailOrder", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"goldfish/getDetail"
			];
			this.needUserId = true;
			this.initData = null;
			this.orderId = this.getParam("orderId");
			this.title = "借款详情";
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("goldFish/myLoanDetailOrder", {
				orderId : this.orderId
			});
			this.setXHR("goldFish/myLoanDetailOrder", function(){
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
			this._setInitData(function(){
				this.view("goldFish/myLoanDetail", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		init : function(){
			
		}
	}, AppController);
	
	return _class_;
}); 
