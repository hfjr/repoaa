/**
 * @actionClass eAccount/index - 活期首页
*/
define("app/vj/controller/eAccount/index", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "活期·存钱罐";
			this.needUserId = true;
			this.styleList = [
				"eAccount/home"
			];
			this.showFooterNav = true;
			this.initData = {
				eAccountInfo : null,
				eAccountOutComeBalance : null		
			};
			this.withdrawCard = null;
			this.licaiTab = null;
		},
		setWithdrawCard : function(callback){
			var router = this.getApiRouter("cunqianguan/queryWithdrawCard");
			this.setXHR("queryWithdrawCard", function(){
				return this.postData(router.url, router.data, function(json){
					this.withdrawCard = json;
					callback.call(this);
				});
			});
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("cunqianguan/init");
			this.setXHR("init", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}	
					if(json.footer.status != 200){
						this.alertMsg(json.body.message);
						return;
					}
					
					this.initData.eAccountInfo = json.body;
					var router = this.getApiRouter("cunqianguan/queryTAccountOutComeBalance");
					this.setXHR("queryEAccountOutComeBalance", function(){
						return this.postData(router.url, router.data, function(json){
							if(json === null){
								return;
							}	
							if(json.footer.status != 200){
								this.alertMsg(json.body.message);
								return;
							}
							this.initData.eAccountOutComeBalance = json.body;
							callback.call(this);
						});
					});
				});
			});
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.renderWidgetByUrl("widget/licaiTab", { active : "eAccount" }, function(html){
				this.licaiTab = html;
				this.setWithdrawCard(function(){
					this.setInitData(function(){
						this.view("eAccount/index", function(view){
							this.renderContainer(view);
							this.container.find(".page").html(this.render("tpl-main-view"));
							this.init();
						});
					});
				});
			});
		},
		init : function(){
			var _this = this;
			var upClass = "turnup"; 
			var viewContent = $("#viewContent");
			this.container.on("click", "#transferred-out", function(e){
				e.preventDefault();
				e.stopPropagation();
				var params = {};
				if(_this.withdrawCard.footer.status != 200){
					_this.alertMsg(_this.withdrawCard.footer.message);
					return;
				}
				
				//_this.initData.eAccountOutComeBalance.amountAvailable_balance = _this.initData.eAccountInfo.investmentAmount;
				//_this.initData.eAccountOutComeBalance.amountAvailable_bank = _this.initData.eAccountInfo.investmentAmount;
				//_this.initData.eAccountOutComeBalance.toBalance_tipInput = "本次最多提现"+ _this.initData.eAccountInfo.investmentAmount +"元";
				//_this.initData.eAccountOutComeBalance.toBank_tipInput = "本次最多提现"+ _this.initData.eAccountInfo.investmentAmount +"元";
	

				params.eAccountOutComeBalance = _this.json_encode(_this.initData.eAccountOutComeBalance);
				params.withdrawCard = _this.json_encode(_this.withdrawCard.body);
				 
				_this.redirect("eAccount/transfer", params );
			}).on("click", "#transferred-into", function(e){
				e.preventDefault();
				e.stopPropagation();
				_this.redirect("eAccount/maToEa");
			}).on("click", "#viewHandle", function(e){
				/*
				var t = $(this);
				if(t.hasClass(upClass)){
					t.removeClass(upClass);
					viewContent.hide();
				}else{
					t.addClass(upClass);
					viewContent.show();
					$("body,html").scrollTop(t[0].offsetTop);
				}
				*/
			});
		}
		
		
	}, AppController);
	
	return _class_;
}); 
