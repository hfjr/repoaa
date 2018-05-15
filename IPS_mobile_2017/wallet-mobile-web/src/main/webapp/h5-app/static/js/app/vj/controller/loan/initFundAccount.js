/**
 * @actionClass loan/initFundAccount 公积金资料
*/
define("app/vj/controller/loan/initFundAccount", [], function(){
	
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"loan/completeInfor"
			];
			this.title = "修改公积金帐号";
			this.needUserId = true;
			this.cityCode = this.getParam("cityCode");
			this.cityName = this.getParam("cityName");
			this.borrowCode = this.getParam("borrowCode");
			this.initData = null;
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("loan/initFoundAccount", {
				cityCode : this.cityCode,
				borrowCode : this.borrowCode
			});
			this.setXHR("initFoundAccount", function(){
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
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._setInitData(function(){
				this.view("loan/initFundAccount", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		init : function(){
			var _this = this;
			var form = $("#mainForm");
			this._checkForm();
			this.container.on("input", "[name='fundAccount'],[name='fundAccountPassword']", function(e){
				_this._checkForm();
			}).on("submit", "#mainForm", function(e){
				e.preventDefault();
				var data = _this.getFormData(mainForm);
				_this.storage.setItem("cacheFund", _this.json_encode(data));
				history.go(-1);
			});
		},
		_checkForm : function(){
			var btn = $("#btnSubmit");
			var fundAccount = $("#fundAccount");
			var fundAccountPassword = $("#fundAccountPassword");
			var checked = true;
			if($.trim(fundAccount.val()) == ""){
				checked = false;
			}else if($.trim(fundAccountPassword.val()) == ""){
				checked = false;
			}
			if(!checked){
				btn.attr("disabled", "disabled");
			}else{
				btn.removeAttr("disabled");
			}
		}
		
	}, AppController);
	
	return _class_;
}); 
