/**
 * @actionClass account/payPasswdResetForRemember
*/
define("app/vj/controller/account/payPasswdResetForRemember", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "修改支付密码";
			this.styleList = ["account/payPasswd"];
			this.needUserId = true;
			//步骤
			//1 第一步
			//2 第二步 确认密码
			this.step = 0; 
			//两个步骤的密码存储
			this.passwd = [
				[],
				[]
			];
		},
		
		successTipDialog : function(text){
			var msg = this.render("successTip", {
				msg : text
			});
			var _this = this;
			this.dialogList.tipDialog = FixDialog.sysTip2(msg, function(){
				history.go(-1);
			}, 2000);
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			
			this.view("account/payPasswd/set", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				 
				this.init();
			});
		},
		
		/**
		 * 删除
		*/
		deleteCurrentPasswd : function(){
			var step = this.step;
			var targetIndex = -1;
			if(this.passwd[step].length > 0){
				targetIndex = this.passwd[step].length - 1;
				this.passwd[step].pop();
			};
			if(targetIndex > -1){
				$("#showInput").find(".box").eq(targetIndex).find(".dot").hide();
			}
			console.log(this.passwd);
			this.listenPasswdChange();
		},
		
		/**
		 * 添加一位密码
		*/
		addCurrentPasswd : function(number){
			var step = this.step;
			var targetIndex = -1;
			if(this.passwd[step].length < 6){
				this.passwd[step].push(number);
				targetIndex = this.passwd[step].length - 1;
			}else{
				return;
			}
			if(targetIndex > -1){
				$("#showInput").find(".box").eq(targetIndex).find(".dot").show();
			}
			console.log(this.passwd);
			this.listenPasswdChange();
		},
		listenPasswdChange : function(){
			var btn = $("#submitBtn");
			if(this.passwd[this.step].length < 6){
				btn.attr("disabled", "disabled");
			}else{
				btn.removeAttr("disabled");
			}
			
		},
		init : function(){
			var _this = this;
			var btn = $("#submitBtn");
			this.container.on("tap", "#passwdKeybox [data-act]", function(e){
				e.preventDefault();
				e.stopPropagation();
				var t =  $(this);
				var act = t.attr("data-act");
				var number = null;
				if(act == "delete"){
					_this.deleteCurrentPasswd();
					
				}else if(act == "input"){
					number = t.attr("data-number");
					_this.addCurrentPasswd(number);
					
				}
			}).on("click", "#submitBtn", function(e){
				e.preventDefault();
				if(btn.attr("disabled")){
					return;
				}
				if(_this.step == 0){
					_this.step = 1;
					btn.attr("disabled", "disabled");
					$("#tip-0").hide();
					$("#tip-1").show();
					$("#showInput").find(".box").find(".dot").hide();
				}else if(_this.step == 1){
					if(_this.passwd[0].join("") != _this.passwd[1].join("") ){
						_this.alertMsg("两次输入的密码不一致");
						return;
					}
					btn.attr("disabled", "disabled");
					_this._updatePasswd(_this.passwd[1].join(""), function(json){
						btn.removeAttr("disabled");
						if(json === null){
							return;
						}
						if(json.footer.status != 200){
							this.alertMsg(json.footer.message);
							return;
						}
						if(json.body.code != 200800){
							this.alertMsg(json.body.message);
							return;
						}
						this.successTipDialog(json.body.message);
					});
				}
			});
		},
		_updatePasswd : function(passwd, callback){
			var router = this.getApiRouter("resetPaymentPasswordForRememberPassword", {
				paymentPassword : passwd
			});
			callback = callback || function(){};
			console.log(router);
			this.setXHR("resetPaymentPasswordForRememberPassword", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
			
		}
	}, AppController);
	return _class_;
}); 
