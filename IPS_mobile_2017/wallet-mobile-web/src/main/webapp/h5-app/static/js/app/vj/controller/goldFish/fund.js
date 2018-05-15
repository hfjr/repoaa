/**
 * @actionClass goldFish/fund - 公积金信息录入
*/
define("app/vj/controller/goldFish/fund", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "公积金信息录入";
			this.styleList = [
				"goldfish/completeInfor"
			];
			this.initData = {
				detailData : null,
				cityData : null
			};
			this.cityBoxTime = 200;
			this.cityBoxDom = {
				alpha : null,
				main : null,
				codeBar : null
			};
			this.needUserId = true;
			this.productTypeCode = this.productTypeMap.XIAOJINYU;
			this.selectCityCode = "";
			this.selectCityName = "";
			
		},
		
		
		
		setInitData : function(callback){
			callback = callback || function(){};
			this._setCityData(function(){
				this._setDetailData(function(){
					callback.call(this);
				});
			});
		},
		
		_setCityData : function(callback){
			var router = this.getApiRouter("goldFish/cityData", {
				productTypeCode : this.productTypeCode
			});
			this.setXHR("cityData", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}	
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData.cityData = json.body;
					callback.call(this);
				});
			});
		},
		_setDetailData : function(callback){
			var router = this.getApiRouter("goldFish/initFundSocial", {
				cityName : this.selectCityName,
				cityCode : this.selectCityCode,
				productTypeCode : this.productTypeCode
			});
			this.setXHR("initFundSocial", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}	
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData.detailData = json.body;
					callback.call(this);
				});
			});
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			if(!this.productTypeCode){
				this.alertMsg("undefined productTypeCode");
				return;
			}
			this.require(["lib/iscroll"], function(){
				this.setInitData(function(){
					
					this.view("goldFish/fund", function(view){
						this.renderContainer(view);
						this.container.find(".page").html(this.render("tpl-main-view"));
						this.init();
					});
				});
			});
			return;
			
		},
		_checkFrom : function(){
			var cityCode = $("#f-cityCode");
			var taskCode = $("#f-taskCode");
			var account = $("#f-account");
			var passwd = $("#f-passwd");
			var btn = $("#btnSubmit");
			var rs = false;
			if($.trim(cityCode.val()) != "" && $.trim(taskCode.val()) != "" &&  $.trim(account.val()) != "" && $.trim(passwd.val()) != ""){
				rs = true;
			}
			if(!rs){
				btn.attr("disabled", "disabled");
			}else{
				btn.removeAttr("disabled");
			}
			return rs;
		},
		init : function(){
			var _this = this;
			this.initCityBox();
			this.container.on("click", "#city-choose-handle", function(){
				_this.showCityBox();
			}).on("click", "#help-handle", function(){
				var t = $(this);
				var up = "turnup";
				var noBorder = "qa-item-noborder";
				if(t.hasClass(up)){
					t.removeClass(up);
					t.parents(".qa-item").addClass(noBorder);
					t.parents("li").find(".answer").hide();
					t.parents("li").find(".qa-item").eq(0).nextAll().hide();
				}else{
					t.addClass(up);
					t.parents("li").find(".answer").show();
					t.parents(".qa-item").removeClass(noBorder);
					t.parents("li").find(".qa-item").eq(0).nextAll().show();
				}
			}).on("input", "#f-account, #f-passwd", function(){
				_this._checkFrom();
			}).on("submit", "#mainForm", function(e){
				e.preventDefault();
				var formData = _this.getFormData(this);
				_this.storage.setItem("goldFishFundData", _this.json_encode({
					formData : formData,
					detailData : _this.initData.detailData
				}));
			 
				_this.replaceDirect("goldFish/social");
			});
			this._checkFrom();
		},
		
		initCityBox : function(){
			var _this = this;
			var fundBox = $("#fund-box");
			this.cityBoxDom.alpha = $("#city-choose-alpha");
			this.cityBoxDom.main = $("#city-choose-main");
			this.cityBoxDom.codeBar = this.cityBoxDom.main.find(".code-bar");
			this.cityBoxDom.codeBar.css({
				top : (408 - 42) / 2
			});
			this.cityBoxDom.alpha.on("click", function(){
				_this.hideCityBox();
			});
			this.cityBoxDom.main.on("click", ".close", function(e){
				_this.hideCityBox();
			}).on("click", "[data-city-id]", function(e){
				var t = $(this);
				_this.selectCityCode = t.attr("data-city-id");
				_this.selectCityName = t.attr("data-city-name");
				_this._setDetailData(function(){
					_this.hideCityBox();
					fundBox.html(_this.render("renderFundData"));
					_this._checkFrom();
				});
				
			}).on("click", "[data-code]", function(e){
				var code = $(this).attr("data-code");
				var ele = $("#handle-code-" + code);
				if(ele.size() == 0){
					return;
				}
				if(typeof(_this.objList.scrollObj) != "undefined"){
					_this.objList.scrollObj.scrollToElement(ele[0]);
				}
			});
			this.addUninstallAction(function(){
				this.cityBoxDom.alpha.off().stop();
				this.cityBoxDom.main.off().stop();
			});
			
		},
		
		
		
		showCityBox : function(){
			var _this = this;
			this.cityBoxDom.alpha.stop().show().animate({
				opacity : 0.4
			}, this.cityBoxTime);
			this.cityBoxDom.main.stop().slideDown(this.cityBoxTime, function(){
			
				if(typeof(_this.objList.scrollObj) == "undefined"){
					_this.objList.scrollObj = new IScroll("#scrollView", {
						scrollbars: true,
						mouseWheel: true,
						useTransform : true,
						interactiveScrollbars: true,
						shrinkScrollbars: 'scale',
						fadeScrollbars: false
					});
					console.log(_this.objList.scrollObj);
				}
			});
			
			
		},
		hideCityBox : function(){
			this.cityBoxDom.alpha.stop().animate({
				opacity : 0
			}, this.cityBoxTime, function(){
				$(this).hide();
			});
			this.cityBoxDom.main.stop().slideUp(this.cityBoxTime, function(){
				$(this).hide();
			});
		}
		
		
	}, AppController);
	
	return _class_;
}); 
