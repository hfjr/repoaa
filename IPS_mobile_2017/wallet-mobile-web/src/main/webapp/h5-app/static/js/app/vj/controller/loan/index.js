/**
 * @actionClass loan/index - 时光钱包首页
*/
define("app/vj/controller/loan/index", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["goldfish/index"];
			this.initData = null;
			this.needUserId = true;
			this.aboutTimeWalletPath = null;
			
		},
		setInitData : function(callback){
			var router = this.getApiRouter("loan/productTypeList", {
				page : 1
			});
			this.setXHR("productTypeList", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}	
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData = json.body;
					this.aboutTimeWalletPath = this.createURL("loan/about", {title : this.initData.timeWalletTitle});
					this.setPageTitle(this.initData.timeWalletTitle);
					callback.call(this);
				});
				
			})
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.setInitData(function(){
				this.view("loan/index", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		_timeWalletDialog : function(){
			var _this = this;
			var timeWalletDialogState = this.storage.getItem("timeWalletDialogState");
			if(timeWalletDialogState != null){
				return;
			}
			this.storage.setItem("timeWalletDialogState", "true");
			var closeBtn;
			var goBtn;
			var dialog = FixDialog.getInstance({
				showHandlebar : false,
				dialogClassName : "dialog-time-wallet",
				content : "<div class='bg'><img src='"+ _g_dir.static_dir +"img/timewallet/timewallet2.jpg?2'><span class='alpha'></span><span class='close'>+</span><span g-act-tap='true' class='btn'>去未来</span></div>",
				events : {
					init : function(){
						var dialog = this;
						closeBtn = dialog.DOM.c.find(".close");
						goBtn = dialog.DOM.c.find(".btn");
						closeBtn.click(function(){
							dialog.close();
						});
						goBtn.click(function(){
							_this.redirect(_this.aboutTimeWalletPath);
						});
					},
					beforeclose : function(){
						console.log("unbind close btn event");
						closeBtn.off();
						goBtn.off();
						
					}
				}
			});
			_this.objList.timeWallet = dialog;
		},
		init : function(){
			var _this = this;
			this._timeWalletDialog();
			
		}
	}, AppController);
	
	return _class_;
}); 
