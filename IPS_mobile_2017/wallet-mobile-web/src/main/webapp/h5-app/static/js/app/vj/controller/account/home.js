/**
 * @actionClass account/home - 个人中心
*/
define("app/vj/controller/account/home", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "个人中心";
			this.needUserId = true;
			this.styleList = [
				"account/index"
			];
			this.initData = {};
			this.showBackHomeNav = true;
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("accountCenterInfo");
			this.setXHR("accountCenterInfo", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}	
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData = json.body;
					callback.call(this);
				});
			})
			
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.setInitData(function(){
				this.view("account/home", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		init : function(){
			var _this = this;
			var btn = $("#payPasswdBtn");
			this.container.on("tap", "#payPasswdBtn", function(e){
				e.preventDefault();
				e.stopPropagation();
				var dialogData = null;
				var code = btn.attr("data-paypasswd-code");
				if(code == "201200" || code == "201201") {
					_this.redirect("account/payPasswd");
				}else if(code == "201202"){
					//触发小锁状态
					dialogData = btn.attr("data-dialog").split("|");
					_this._api_selfLockDialog(dialogData);
				}else if(code == "201203"){
					//触发大锁状态
					dialogData = btn.attr("data-dialog").split("|");
					_this._api_serviceLockDialog(dialogData);
				}
			});

		}
		
		
	}, AppController);
	
	return _class_;
}); 
