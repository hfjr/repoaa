/**
 * @actionClass account/index - 财富
*/
define("app/vj/controller/account/index", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "财富";
			this.styleList = [
				"index/index"
			];
			this.needUserId = true;
			this.initData = null;
			this.showMoney = true;
			this.pageCahe = this.getPageCache();
			//this.showHomeNav = true;
			this.showFooterNav = true;
			if(this.pageCahe){
				this.showMoney = this.pageCahe.showMoney;
			}
		},
		_setWidthDraws : function(callback){

			var _this = this;
			var router = this.getApiRouter("queryWithdrawMaAccountAndEAccountInfo");
			this.setXHR("queryWithdrawMaAccountAndEAccountInfo", function(){
				return this.postData(router.url ,router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		_setLoan : function(callback){
			var router = this.getApiRouter("loan/route");
			this.setXHR("loan/route", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("account/queryMAccountInfo.security");
			console.log(router);
			this.setXHR("queryMAccountInfo", function(){
				return this.postData(router.url, router.data, function(json){
					
					if(json === null){
						return;
					}
					
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData = json.body;
					this._setWidthDraws(function(json){
						this.initData.widthDrawals = json;
						this._setLoan(function(json){
							this.initData.loan = json;
							callback.call(this);
						});
					});
				});
			});
		},
		updateMoneyFormat : function(){
			var showMoney = this.showMoney;
			$("#money-totalAccountAmount, #money-amountFrozen, #money-amountAvailable, #money-investmentAmount, #money-yesterdayReceive, #money-allAreadyReceive").each(function(){
				var t = $(this);
				if(showMoney){
					t.html(t.attr("data-money"));
				}else{
					t.html("****");
				}
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.setInitData(function(){
				
				this.init();
			});
		},
		init : function(){
			var _this = this;
			var on = "eye-wrap-on";
			var off = "eye-wrap-off";
			this.view("account/index", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
			});
			this.container.on("tap", ".eye-wrap", function(){
				var t = $(this);
				if(t.hasClass(on)){
					t.removeClass(on).addClass(off);
					_this.showMoney = false;
				}else{
					t.removeClass(off).addClass(on);
					_this.showMoney = true;
				}
				_this.updateMoneyFormat();
				var targetCache = $.extend({}, _this.pageCahe, {
					showMoney : _this.showMoney
				});
				_this._setCache(targetCache);
				
			}).on("click", "#credit-link", function(e){
				
				//var url = $(this).attr("data-url");
				//window.location.href = url;
				_this.objList.eaccountTip = FixDialog.getInstance({
					showHandlebar : false,
					dialogClassName : "dialog-cqg",
					"content" : "<div class='bg'><p></p></div><div class='c-alert-m'><p></p></div><div class='c-alert-a'><a href='http://a.app.qq.com/o/simple.jsp?pkgname=com.zhuanyi.gzb'>APP版已发布，前往体验</a></div>",
					buttons : [
						{
							text : " "
						}
					]
				});
			}).on("click", "#cards-link", function(e){
				_this.objList.eaccountTip2 = FixDialog.getInstance({
					showHandlebar : false,
					dialogClassName : "dialog-cqg",
					"content" : "<div class='bg'><p class='bg-cards'></p></div><div class='c-alert-m'><p class='text-cards'></p></div><div class='c-alert-a'><a href='javascript:;' id='btnTip2'>我先看看别的</a></div>",
					buttons : [
						{
							text : " "
						}
					],
					events : {
						init : function(){
							var dialog = this;
							$("#btnTip2").click(function(){
								dialog.close();
							});
						},
						beforeclose : function(){
							$("#btnTip2").off();
						}
					}
				});
			}).on("click", ".btn-withdraw", function(e){
				e.preventDefault();
				e.stopPropagation();
				var url = $(this).attr("data-url");
				if(_this.initData.widthDrawals.footer.status != 200){
					_this.alertMsg(_this.initData.widthDrawals.footer.message);
				}else{
					_this.redirect(url);
				}
			}).on("tap", "#loanBtn", function(e){
				e.preventDefault();
				e.stopPropagation();
				if(_this.initData.loan.footer.status != 200){
					_this.alertMsg(_this.initData.loan.footer.message);
				}else{
					if(_this.initData.loan.body.code == "202601"){
						_this.redirect("loan/initApply");
					}else if(_this.initData.loan.body.code == "202602" || _this.initData.loan.body.code == "202603"){
						_this.redirect("loan/initLoan");
					}
				}
			}).on("click", "#account-info", function(e){
				if(!$(this).children("em").hasClass("k-active")){
					$(this).children("em").addClass("k-active");
					$(".account-balance").hide().next().show();
					$(".account-mask").show();
				}else{
					$(this).children("em").removeClass("k-active")
					$(".account-balance").show().next().hide();
					$(".account-mask").hide();
				}
			}).on("click", ".account-mask", function(e){
					$("#account-info").children("em").removeClass("k-active")
					$(".account-balance").show().next().hide();
					$(".account-mask").hide();
			});
			
			

		}
		
		
	}, AppController);
	
	return _class_;
}); 
