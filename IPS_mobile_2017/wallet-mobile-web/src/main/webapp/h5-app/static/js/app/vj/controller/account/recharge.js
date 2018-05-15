/**
 * @actionClass account/recharge
*/
define("app/vj/controller/account/recharge", [], function(){
		

	
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "充值";
			this.styleList = ["account/recharge/index"];
			this.showBackHomeNav = true;
			this.needUserId = true;
			this.callbackPath = this.getParam("callbackPath") || "index/index";
			this.storage.setItem("rechargeCallBackPath", this.callbackPath);
			
			console.log(this.callbackPath);
			//初始化数据
			this.initData = {
				isBandCard : false,
				bindedCards : [],
				supportsBanks : []
			};
			//当前选择卡号类型 - 缓存
			//cardId - 绑卡id 
			//bankCode - 银行代码
			this.selectedState = null;
			//当前选择银行代码 - 缓存
			this.selectedBankCode = null;
			//当前选择绑定卡号id - 缓存
			this.selectedBankCardId = null;
			//当前选择银行名称
			this.selectedBankName = null;
			//当前填写金额 - 缓存
			this.amount = null;
			//缓存数据对象
		},
		
		setInitData : function(callback){
			callback = callback || function(){};
			var _this = this;
			var queryRechargeCardRouter = this.getApiRouter("queryRechargeCard");
			var queryAllSupportBankListRouter = this.getApiRouter("queryAllSupportBankList");
			this.setXHR("queryRechargeCard", function(){
				return this.postData(queryRechargeCardRouter.url, queryRechargeCardRouter.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;	
					}
					this.initData.isBandCard = json.body.isBandCard !== "no";
					this.initData.bindedCards = json.body.cards || [];
					
					_this.setXHR("queryAllSupportBankList", function(){
						return _this.postData(queryAllSupportBankListRouter.url, queryAllSupportBankListRouter.data, function(json){
							if(json === null){
								return;
							}
							if(json.footer.status != 200){
								_this.alertMsg(json.footer.message);
								return;	
							}
							_this.initData.supportsBanks = json.body;
							callback.call(_this);
						});
					});
					
					
				});
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.require(["lib/iscroll"], function(){
				this.setInitData(function(){
					console.log(this.initData);
					this.init();
				});
			});
		},
		init : function(){
			var _this = this;
			this.view("account/recharge/index", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.initSlideBox();
				this.initSlideBox2();
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
				btn.attr("disabled", "disabled");
				
				var data = _this.getFormData(this);
				data.callbackPath = _this.callbackPath;
				//设置本页选择状态到缓存，返回该页面后初始化
				
				if(_this.selectedState == "cardId"){
					_this.replaceDirect("account/rechargeSendPhoneCode", data);
				}else if(_this.selectedState == "bankCode"){
					_this.replaceDirect("account/rechargeBindCard", data);
				}
				console.log(data);
			}).on("input", "#f-money", function(e){
				var t = $(this);
				var _t = this;
				_this.onlyNum(this, 12);
				if(check()){
					btn.removeAttr("disabled");
				}else{
					btn.attr("disabled", "disabled");
				}
			});
			
			function check(){
				var _v = $.trim(input.val());
				var v = _v;
				var max = Number(input.attr("data-max"));
				var min = Number(input.attr("data-min"));
				
				if(_this.selectedState === null){
					return false;
				}
				
				if(v == ""){
					return false;
				}
				v = Number(v);
				if(isNaN(v)){
					return false;
				}
				if(v == 0){
					return false;
				}
				if(v <= min || v > max){
					return false;
				}
				
				return true;
				
			}
			
		},
		
		/**
		 * 初始化选择默认卡号
		*/
		
		initSelect : function(){
			var btn = $("#btn-submit");
			
			var money = $("#f-money");
			if(this.selectedState === null){
				this._chooseDefaultBindedCard();
			}else if(this.selectedState == "cardId"){
				this.selectCardId($("#bindedBanks [data-item][data-card-id='"+ this.selectedBankCardId +"']"), function(){
					money.val(this.amount).trigger("input");
				});
			}else if(this.selectedState == "bankCode"){
				this.selectBankCode($("#supportBanks [data-item][data-bank-code='"+ this.selectedBankCode +"']"), function(){
					money.val(this.amount).trigger("input");
				});
			}
			if(this.selectedState !== null && $.trim(money.val()) != ""){
				btn.removeAttr("disabled");
			}
			
		},
		_chooseDefaultBindedCard : function(){
			var firstSeleceted = $("#bindedBanks [data-item][data-status='normal']").eq(0);
			if(firstSeleceted.size() > 0){
				this.selectCardId(firstSeleceted);
			}
		},
		initSlideBox : function(){
			var _this = this;
			var id = "#ui-slidebox";
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
				
				showBox();
			}).on("click",  id + " .alpha", function(e){
				
				setTimeout(function(){
					closeBox();
				}, 150);
			}).on("click", id + " .add-item", function(e){
				
				_second();
			}).on("click", id + " .back", function(e){
				
				_first();
			}).on("click", id + " .close", function(e){
				
				closeBox();
			}).on("click", "#supportBanks [data-item]", function(e){
				
				_this.selectBankCode(this, function(){
					closeBox();
				});
				
			}).on("click", "#bindedBanks [data-item]", function(e){
			
				_this.selectCardId(this, function(){
					closeBox();
				});
			});
			
			function showBox(){
				box.show();
				alpha.stop().animate({
					opacity : 0.2
				}, time);
				content.stop().slideDown(time, function(){
					if(typeof(_this.objList.scrollObjBindedBanks) == "undefined"){
						_this.objList.scrollObjBindedBanks = new IScroll("#bindedBanks", {
							scrollbars: true,
							mouseWheel: true,
							useTransform : true,
							interactiveScrollbars: true,
							shrinkScrollbars: 'scale',
							fadeScrollbars: false
						});
						
						_this.objList.scrollObjSupportBanks = new IScroll("#supportBanks", {
							scrollbars: true,
							mouseWheel: true,
							useTransform : true,
							interactiveScrollbars: true,
							shrinkScrollbars: 'scale',
							fadeScrollbars: false
						});
						
					}
					
				});
				_updateSize();
				win.on("resize.slideBox", function(){
					_updateSize();
					slideToItem(currentIndex, false);
				});
				
			}
			
			function closeBox(){
				_first(false);
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
				list.css({
					width : parentWidth * 2
				});
				slideItem.css({
					width : parentWidth
				});
				console.log("resize");
			}
			
			function getPWidth(){
				return content[0].offsetWidth;
			}
			function slideToItem(index, animate){
				animate = typeof(animate) == "undefined" ? true : animate;
				var parentWidth = getPWidth();
				var targetLeft = index == 0 ? 0 : - (parentWidth * index);
				list.stop();
				if(animate){
					list.animate({
						left : targetLeft
					}, time, function(){
						currentIndex = index;
					});
				}else{
					list.css({
						left : targetLeft
					});
					currentIndex = index;
				}				
				
			}
			function _first(animate){
				animate = typeof(animate) == "undefined" ? true : animate;
				slideToItem(0, animate);
			}
			function _second(animate){
				
				animate = typeof(animate) == "undefined" ? true : animate;
				slideToItem(1, animate);
				
			}
			this.initSelect();
			this.addUninstallAction(function(){
				win.off("resize.slideBox");
			});
		},
		initSlideBox2 : function(){
			var _this = this;
			var id = "#ui-slidebox2";
			var box = $(id);
			var alpha = box.find(".alpha");
			var content = box.find(".content");
			var listWrap = box.find(".list-wrap");
			var list = box.find(".list");
			var slideItem = box.find(".slide-item");
			var win = $(window);
			var time = 200;
			
			var currentIndex = 0;
			
			
			this.container.on("click", "#rechargeMaxTip", function(e){
				showBox();
			}).on("click", id + " .close", function(e){

				closeBox();
			}).on("click", id + " .alpha", function(e){

				closeBox();
			});
			
			function showBox(){
				box.show();
				alpha.stop().animate({
					opacity : 0.2
				}, time);
				content.stop().slideDown(time, function(){
					if(typeof(_this.objList.scrollObjSupportBanks2) == "undefined"){	
						_this.objList.scrollObjSupportBanks2 = new IScroll("#supportBanks2", {
							scrollbars: true,
							mouseWheel: true,
							useTransform : true,
							interactiveScrollbars: true,
							shrinkScrollbars: 'scale',
							fadeScrollbars: false
						});
					}
					
				});
				_updateSize();
				win.on("resize.slideBox", function(){
					_updateSize();
					slideToItem(currentIndex, false);
				});
				
			}
			
			function closeBox(){
				_first(false);
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
				list.css({
					width : parentWidth * 2
				});
				slideItem.css({
					width : parentWidth
				});
				console.log("resize");
			}
			
			function getPWidth(){
				return content[0].offsetWidth;
			}
			function slideToItem(index, animate){
				animate = typeof(animate) == "undefined" ? true : animate;
				var parentWidth = getPWidth();
				var targetLeft = index == 0 ? 0 : - (parentWidth * index);
				list.stop();
				if(animate){
					list.animate({
						left : targetLeft
					}, time, function(){
						currentIndex = index;
					});
				}else{
					list.css({
						left : targetLeft
					});
					currentIndex = index;
				}				
				
			}
			function _first(animate){
				animate = typeof(animate) == "undefined" ? true : animate;
				slideToItem(0, animate);
			}
			function _second(animate){
				
				animate = typeof(animate) == "undefined" ? true : animate;
				slideToItem(1, animate);
				
			}
			this.initSelect();
			this.addUninstallAction(function(){
				win.off("resize.slideBox");
			});
		},
		/**
		 * 选择已绑定银行卡
		*/
		selectCardId : function(item, callback){
			callback = callback || function(){};
			var money = $("#f-money");
			var rechargeTip = $("#rechargeTip");
			item = $(item);
			var cardId = item.attr("data-card-id");
			var status = item.attr("data-status");
			if(status != "normal"){
				return;
			}
			$("#selectedBankCardId").val(cardId);
			money.attr("data-min", item.attr("data-min-amount")).attr("data-max", item.attr("data-max-amount")).val("").trigger("input");
			rechargeTip.html(item.attr("data-content-tip"));
			this._renderCard(item);
			this._renderTip(item.attr("data-input-tip"));
			$("#selectedBankCode").val("");
			this.selectedState = "cardId";
			this.selectedBankCode = "";
			this.selectedBankCardId = cardId;
			this.selectedBankName = item.attr("data-bank-name");
			$("#selectedBankName").val(this.selectedBankName);
			callback.call(this);
			item.find(".choosed").show();
			item.siblings().find(".choosed").hide();
		},
		_renderCard : function(item){
			var selectedBankCard = $("#selectedBankCard");
			selectedBankCard.html(this.render("renderCard", {
				bankName : item.attr("data-bank-name"),
				bankCode : item.attr("data-bank-code")
			}));
		},
		_renderTip : function(tip){
			var tipBar = $("#tip2");
			if(tip){
				tipBar.show().text(tip);
			}else{
				tipBar.hide();
			}
		},
		/**
		 * 选择银行卡code
		*/
		selectBankCode : function(item, callback){
			callback = callback || function(){};
			var money = $("#f-money");
			var rechargeTip = $("#rechargeTip");
			item = $(item);
			var bankCode = item.attr("data-bank-code");
			var status = item.attr("data-status");
			if(status != "normal"){
				return;
			}
			$("#selectedBankCode").val(bankCode);
			money.attr("data-min", item.attr("data-min-amount")).attr("data-max", item.attr("data-max-amount")).val("").trigger("input");
			rechargeTip.html(item.attr("data-content-tip"));
			this._renderCard(item);
			this._renderTip(item.attr("data-input-tip"));
			$("#selectedBankCardId").val("");
			this.selectedState = "bankCode";
			this.selectedBankCode = bankCode;
			this.selectedBankCardId = "";
			this.selectedBankName = item.attr("data-bank-name");
			$("#selectedBankName").val(this.selectedBankName);
			$("#bindedBanks").find(".choosed").hide();
			callback.call(this);
			
		}
	}, AppController);
	
	return _class_;
}); 
