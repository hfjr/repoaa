/**
 * @actionClass goldFish/investConfirm 
*/
define("app/vj/controller/goldFish/investConfirm", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"goldfish/inforConfirm"
			];
			this.needUserId = true;
			var cache = this.getParam("formData");
			this.initData = null;
			this.formData = null;
			if(cache !== null){
				this.formData = this.json_decode(cache);
				
			}
			this.title = "借款信息确认";
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("goldFish/confirmInvest", {
				investmentAmount : this.formData.investmentAmount,
				productId : this.formData.productId
			});
			this.setXHR("confirmInvest", function(){
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
			if(!this.formData){
				this.alertMsg("请返回重新投资");
				return;
			}
			this._setInitData(function(){
				this.initPayPasswdDialog(function(){
					this.view("goldFish/investConfirm", function(view){
						this.renderContainer(view);
						this.container.find(".page").html(this.render("tpl-main-view"));
						this.init();
					});
				});
			});
		},
		_send : function(formData, callback){
			var router = this.getApiRouter("goldFish/submitInvest", formData);
			this.setXHR("goldFish/submitInvest", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		},
		init : function(){
			var _this = this;
			var btn = $("#f-btn");
			var form = $("#f-form");
			this.container.on("submit", "#f-form", function(e){
				e.preventDefault();
				 
				if(btn.attr("disabled")){
					return;
				}
				btn.attr("disabled", "disabled");
				var formData = _this.getFormData(form);
				(function(){
					var _func = arguments.callee;
					_this._send(formData, function(json){
						btn.removeAttr("disabled");
						
						var _code = null;
						var messageData = null;
						if(json === null){
							return;
						}
						if(json.footer.status != 200){
							this.alertMsg(json.footer.message);
							return;
						}
						if(typeof(json.body.code) !== "undefined"){
							_code = json.body.code;
							
						}
						if(_code == "200100"){
							_this.replaceDirect("goldFish/investSuccess", json.body);
							return;
						}else{
							this.alertMsg(json.body.message);
						}
					});
				})();
			});
			
			
						
			
		}
	}, AppController);
	
	return _class_;
}); 
