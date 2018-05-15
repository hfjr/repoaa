/**
 * @actionClass account/bindCards 
*/
define("app/vj/controller/account/bindCards", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "我的银行卡";
			this.needUserId = true;
			this.styleList = ["account/bindCards"];
			this.initData = {};
			this.showBackHomeNav = true;
		},
		setInitData : function(callback){
			var  router = this.getApiRouter("queryMyBankCards");
			this.setXHR("queryMyBankCards", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
					}
					this.initData.cards = json.body.cards;
					this.initData.isBandCard = json.body.cards.length != 0;
					callback.call(this);
				});
			})
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.setInitData(function(){
				
				this.view("account/bindCards", function(view){
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
