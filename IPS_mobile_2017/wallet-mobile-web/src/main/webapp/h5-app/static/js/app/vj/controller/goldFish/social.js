/**
 * @actionClass goldFish/social - 社保信息录入
*/
define("app/vj/controller/goldFish/social", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "社保信息录入";
			this.styleList = [
				"goldfish/completeInfor"
			];
			this.initData = {
				detailData : null,
				formData : null
			};
			this.needUserId = true;
			var goldFishFundData = this.storage.getItem("goldFishFundData");
			if(goldFishFundData){
				goldFishFundData = this.json_decode(goldFishFundData);
				this.initData.formData = goldFishFundData.formData;
				this.initData.detailData = goldFishFundData.detailData;
				
			}
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			if(!this.initData.formData){
				this.alertMsg("undefined initData");
				return;
			}
			
			this.view("goldFish/social", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
			
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
			var btn = $("#btnSubmit");
			this.container.on("click", "#help-handle", function(){
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
				btn.attr("disabled", "disabled");
				_this._submit(formData, function(json){
					if(json.footer.status != 200){
						btn.removeAttr("disabled");
						this.alertMsg(json.footer.message);
						return;
					}
					if(json.body.code != "202900"){
						btn.removeAttr("disabled");
						this.alertMsg(json.body.message);
						return;
					}
					this.storage.removeItem("goldFishFundData");
					this.replaceDirect("goldFish/saveFundSocialSuccess", {
						formData : _this.json_encode(json.body)
					});
				});
			});
			this._checkFrom();
		},
		_submit : function(formData, callback){
			var router = this.getApiRouter("goldFish/saveFundSocial", formData);
			this.setXHR("saveFundSocial", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		}
	}, AppController);
	return _class_;
}); 
