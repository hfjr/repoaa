/**
 * @actionClass goldFish/myInvestDynamic 
*/
define("app/vj/controller/goldFish/myInvestDynamic", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"goldfish/dynamic"
			];
			this.needUserId = true;
			this.initData = null;
			this.orderId = this.getParam("orderId");
			this.title = "投资动态";
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("goldFish/investmentNewsFlow", {
				orderId : this.orderId
			});
			this.setXHR("goldFish/investmentNewsFlow", function(){
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
				this.view("goldFish/myInvestDynamic", function(view){
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
