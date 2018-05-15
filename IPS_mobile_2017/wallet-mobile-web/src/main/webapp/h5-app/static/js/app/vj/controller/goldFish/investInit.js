/**
 * @actionClass goldFish/investInit 
*/
define("app/vj/controller/goldFish/investInit", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"goldfish/investForm"
			];
			this.needUserId = true;
			this.initData = null;
			this.productId = this.getParam("productId");
			
			this.investmentAmount = "0";
			this.title = "投资";
		},
		_setInitData : function(isInit, callback){
			var router = this.getApiRouter("goldFish/initInvest", {
				investmentAmount : this.investmentAmount,
				productId : this.productId
			});
			this.setXHR("initInvest", function(){
				return this.postData(router.url, router.data, function(json){
					if(json == null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData = json.body;
					callback.call(this, json);
				});
			});
		
			
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._setInitData(true, function(){
				
				this.view("goldFish/initInvest", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		_refresh : function(){
			var o = this.initData;
			$("#t-inputBottomTipContents").html(o.inputBottomTipContents);
			$("#t-detailInformation").html(this.render("renderDetailInformation", {
				list : o.detailInformation
			}));
			$("#t-product").html(this.render("renderProduct", {
				product : o.product
			}));
			$("#t-investmentTip").html(o.investmentTip);
			$("#f-token").html(o.token);
		},
		initForm : function(){
			var btn = $("#btn-submit");
			var form =  $("#f-form");
			var input = $("#f-investmentAmount");
			var _this = this;
			this.container.on("submit", "#f-form", function(e){
				e.preventDefault();
				btn.attr("disabled", "disabled");
				var formData = _this.getFormData(form);
				_this._send(formData, function(json){
					btn.removeAttr("disabled");
					if(json === null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.storage.removeItem("goldFish/index");

					this.redirect("goldFish/investConfirm", {
						formData : this.json_encode(formData)
					});
				});
			}).on("input", "#f-investmentAmount", function(e){
				var t = $(this);
				var _t = this;
				_this.onlyNum(this, 12);
				if(check()){
					btn.removeAttr("disabled");
				}else{
					btn.attr("disabled", "disabled");
				}
			}).on("blur", "#f-investmentAmount", function(e){
				if(check()){
					_this.investmentAmount = $.trim($(this).val());
					_this._setInitData(false, function(){
						this._refresh();
					});
				}
			});
			input.trigger("input");
			function check(){
				var _v = $.trim(input.val());
				var v = _v;
				var max = Number(input.attr("data-max"));
				var min = Number(input.attr("data-min"));
				var mol = Number(input.attr("data-mol"));
				if(v == ""){
					return false;
				}
				v = Number(v);
				
				if(isNaN(v)){
					return false;
				}
				
				if(v % mol != 0){
					return false;
				}
				
				
				if(v == 0){
					return false;
				}
				if(v < min || v > max){
					return false;
				}
				return true;
			}
		},
		_send : function(formData, callback){
			var router = this.getApiRouter("goldFish/confirmInvest", formData);
			this.setXHR("goldFish/confirmInvest", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		init : function(){
			var _this = this;
			this.initForm();
			
		}
	}, AppController);
	
	return _class_;
}); 
