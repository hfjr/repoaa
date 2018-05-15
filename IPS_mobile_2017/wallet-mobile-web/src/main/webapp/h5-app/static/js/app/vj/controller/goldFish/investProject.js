/**
 * @actionClass goldFish/investProject 
*/
define("app/vj/controller/goldFish/investProject", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"goldfish/investProject"
			];
			this.needUserId = true;
			this.initData = null;
			this.productId = this.getParam("productId");
			this.productTypeCode = this.productTypeMap.XIAOJINYU;
			this.title = "投资项目";
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("goldFish/investProjectInit", {
				productId : this.productId
			});
			this.setXHR("goldFish/investProjectInit", function(){
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
				this.view("goldFish/investProject", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		_getInvestInitData : function(callback){
			var router = this.getApiRouter("goldFish/initInvest", {
				investmentAmount : 0,
				productId : this.productId
			});
			this.setXHR("initInvest", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		_canNotBuyTip : function(messageData){
			var _this = this;
			this.dialogList.canNotBuyTip = FixDialog.getInstance({
				showHandlebar : false,
				content : "<div class=\"c-sysTip\">"+ messageData[0] +"</div>",
				buttons : [
					{
						text : messageData[1],
						fn : function(){
							
						}
					},
					{
						text : messageData[2],
						fn : function(){
							_this.redirect("goldFish/taskCard", {productTypeCode : _this.productTypeCode});
						}
					}
					
				]
			});								
			
		},
		init : function(){
			var _this = this;
			var loading = false;
			this.container.on("click", "#invest-btn", function(e){
				e.preventDefault();
				if(loading){
					return;
				}
				loading = true;
				_this._getInvestInitData(function(json){
					loading = false;
					if(json == null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					data = json.body;
					if(data.isCanBuy != "true"){
						_this._canNotBuyTip(data.information.split("|"));
					}else{
						_this.redirect("goldFish/investInit", {
							productId : this.productId
						});
					}
					
				});
			});
		}
	}, AppController);
	
	return _class_;
}); 
