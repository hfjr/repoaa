/**
 * @actionClass account/investDetail
*/
define("app/vj/controller/account/investDetail", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["account/investDetail"];
			this.title = "投资详情";
			this.needUserId = true;
			this.orderId = this.getParam("orderId") || null;
			this.initData = null;
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("order/queryUserInvestmentDetail.security", {
				orderId : this.orderId
			});
			this.setXHR("investmentRecordSummary", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					this.initData = json.footer.status == 200 ? json.body : null;
					if(!this.initData){
						return;
					}
					callback.call(this);
				});
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			if(!this.orderId){
				console.log("undefined orderId");
				return;
			}
			this.setInitData(function(){
				this.view("account/investDetail", function(view){
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
					var url = _this.createURL("account/investSafeDetail", {
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


