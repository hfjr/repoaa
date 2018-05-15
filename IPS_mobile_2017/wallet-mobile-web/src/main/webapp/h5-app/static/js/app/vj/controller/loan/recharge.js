/**
 * @actionClass loan/recharge
*/
define("app/vj/controller/loan/recharge", [], function(){
		

	
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "充值";
			this.styleList = ["loan/recharge/index"];
			this.showBackHomeNav = true;
			this.needUserId = true;
			this.callbackPath = this.getParam("callbackPath") || "index/index";
			this.storage.setItem("rechargeCallBackPath", this.callbackPath);
			
			console.log(this.callbackPath);
			//初始化数据
			this.initData = null;
			
			//当前选择银行代码
			this.selectedBankCode = null;
			//当前选择银行名称
			this.selectedBankName = null;
		},
		
		setInitData : function(callback){
			callback = callback || function(){};
			var queryRechargeCardRouter = this.getApiRouter("loan/recharge/init");
			var queryAllSupportBankListRouter = this.getApiRouter("loan/recharge/queryAllSupportBankList");
			this.setXHR("init", function(){
				return this.postData(queryRechargeCardRouter.url, queryRechargeCardRouter.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;	
					}
					this.initData = json.body;
					this.setXHR("queryAllSupportBankList", function(){
						return this.postData(queryAllSupportBankListRouter.url, queryAllSupportBankListRouter.data, function(json){
							if(json === null){
								return;
							}
							if(json.footer.status != 200){
								this.alertMsg(json.footer.message);
								return;	
							}
							this.initData.supportsBanks = json.body;
							callback.call(this);
						});
					});
				});
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.setInitData(function(){
				console.log(this.initData);
				this.init();
			});
		},
		init : function(){
			var _this = this;
			this.view("loan/recharge/index", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.initSlideBox();
				this.initForm();
			});
		},
		initForm : function(){
			var btn = $("#btn-submit");
			var form =  $("#rechargeForm");
			var input = $("#f-money");
			var _this = this;
			this.container.on("submit", "#rechargeForm", function(e){
				e.preventDefault();
				if(btn.attr("disabled")){
					return;
				}
				var data = _this.getFormData(this);
				data.callbackPath = _this.callbackPath;
				_this.replaceDirect("loan/rechargeBindCard", data);
				console.log(data);
			});
			
			
		},
		
		
		initSlideBox : function(){
			var _this = this;
			var box = $("#ui-slidebox");
			var alpha = box.find(".alpha");
			var content = box.find(".content");
			var listWrap = box.find(".list-wrap");
			var list = box.find(".list");
			var slideItem = box.find(".slide-item");
			var win = $(window);
			var time = 200;
			
			var currentIndex = 0;
			var money = $("#f-money");
			
			this.container.on("click", ".choose", function(e){
				money.trigger("blur");
				showBox();
			}).on("click", ".alpha", function(e){
				e.preventDefault();
				e.stopPropagation();
				closeBox();
			}).on("click", ".close", function(e){
				closeBox();
			}).on("click", "#supportBanks [data-item]", function(e){
				_this.selectBankCode(this, function(){
					closeBox();
				});
				
			});
			
			function showBox(){
				box.show();
				alpha.stop().animate({
					opacity : 0.2
				}, time);
				content.stop().slideDown(time);
				_updateSize();
				win.on("resize.slideBox", function(){
					_updateSize();
					
				});
				
			}
			
			function closeBox(){
				
				alpha.stop().animate({
					opacity : 0
				}, time);
				content.stop().slideUp(time, function(){
					box.hide();
				});
				win.off("resize.slideBox");
				
			}
			
			function _updateSize(){
				var parentWidth = getPWidth();
				listWrap.css({
					width : parentWidth
				});
				
				slideItem.css({
					width : parentWidth
				});
				console.log("resize");
			}
			
			function getPWidth(){
				return content[0].offsetWidth;
			}
			
			
		
			this.addUninstallAction(function(){
				win.off("resize.slideBox");
			});
		},
		
		/**
		 * 选择银行卡code
		*/
		selectBankCode : function(item, callback){
			callback = callback || function(){};
			var selectedBankCard = $("#selectedBankCard");
			item = $(item);
			var bankCode = item.attr("data-bank-code");
			var status = item.attr("data-status");
			if(status != "normal"){
				return;
			}
			$("#selectedBankCode").val(bankCode);
			selectedBankCard.text(item.attr("data-bank-name"));
			$("#selectedBankCardId").val("");
			
			this.selectedBankCode = bankCode;
			this.selectedBankName = item.attr("data-bank-name");
			$("#selectedBankName").val(this.selectedBankName);
			callback.call(this);
			$("#btn-submit").removeAttr("disabled");
		}
	}, AppController);
	
	return _class_;
}); 
