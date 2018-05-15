/**
 * @actionClass loan/initSocialAccount 社保资料
*/
define("app/vj/controller/loan/initSocialAccount", [], function(){
	
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"loan/completeInfor"
			];
			this.title = "修改社保帐号";
			this.needUserId = true;
			this.cityCode = this.getParam("cityCode");
			this.cityName = this.getParam("cityName");
			this.borrowCode = this.getParam("borrowCode");
			this.initData = null;
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("loan/initSocialAccount", {
				cityCode : this.cityCode,
				borrowCode : this.borrowCode
			});
			this.setXHR("initSocialAccount", function(){
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
				this.view("loan/initSocialAccount", function(view){
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
			this.container.on("input", "[name='socialSecurityAccount'],[name='socialSecurityAccountPassword']", function(e){
				_this._checkForm();
			}).on("submit", "#mainForm", function(e){
				e.preventDefault();
				var data = _this.getFormData(mainForm);
				_this.storage.setItem("cacheSocial", _this.json_encode(data));
				history.go(-1);
			});
		},
		_checkForm : function(){
			var btn = $("#btnSubmit");
			var socialSecurityAccount = $("#socialSecurityAccount");
			var socialSecurityAccountPassword = $("#socialSecurityAccountPassword");
			var checked = true;
			if($.trim(socialSecurityAccount.val()) == ""){
				checked = false;
			}else if($.trim(socialSecurityAccountPassword.val()) == ""){
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
