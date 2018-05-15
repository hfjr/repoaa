/**
 * @actionClass goldFish/myInvestDetail 
*/
define("app/vj/controller/goldFish/myInvestDetail", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"goldfish/investDetail"
			];
			this.needUserId = true;
			this.initData = null;
			this.orderId = this.getParam("orderId");
			this.title = "投资详情";
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("goldFish/investmentDetail", {
				orderId : this.orderId
			});
			this.setXHR("goldFish/investmentDetail", function(){
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
				this.view("goldFish/myInvestDetail", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		init : function(){
			var _this = this;
			this.container.on("tap", ".safe", function(e){
				e.preventDefault();
				e.stopPropagation();
				var t = $(this);
				var type = _this.initData.insurancePolicyNumberShowActionType;
				var msg = _this.initData.insurancePolicyNumberShowContent;
				var msgData = null;
				if(type == "redirect_page"){
					var url = _this.createURL("goldFish/investSafeDetail", {
						safeUrl : msg,
						safeId : _this.initData.insurancePolicyNumber
					});
					_this.redirect(url);
				}else if(type == "alert_tip"){
					msgData = msg.split("|");
					_this.objList.alertDialog = new FixDialog({
						id : "_ALERT_", 
						title : msgData[0],
						content : "<div class='c-alert'>" + msgData[1] + "</div>",
						buttons : [
							{
								text : msgData[2],
								id : "ok"
							}
						]
					});
				}
			});
		}
	}, AppController);

	return _class_;
});
