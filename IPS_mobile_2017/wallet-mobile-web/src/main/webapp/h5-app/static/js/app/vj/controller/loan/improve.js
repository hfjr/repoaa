/**
 * @actionClass loan/improve 完善资料
*/
define("app/vj/controller/loan/improve", [], function(){
	
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"mobiscroll/mobiscroll",
				"loan/completeInfor"
			];
			this.needUserId = true;
			this.pageCache = null;
			this.title = "完善资料";
			this.initData = {
				//详细信息
				singleData : null,
				//表单缓存
				cacheFormData : null,
				//社保缓存
				cacheSocial : null,
				//公积金缓存
				cacheFund : null
			};
			//获取缓存
			this.pageCache = this.getPageCache(); 
			if(this.pageCache){
				this.initData.cacheFormData = this.pageCache.cacheFormData || null;
				this.initData.cacheSocial = this.storage.getItem("cacheSocial") ? this.json_decode(this.storage.getItem("cacheSocial")) : null;
				this.initData.cacheFund = this.storage.getItem("cacheFund") ? this.json_decode(this.storage.getItem("cacheFund")) : null;
				this.removePageCache(); 
				this.storage.removeItem("cacheSocial");
				this.storage.removeItem("cacheFund");
			}
			console.log("pageCache:", this.pageCache);
			console.log("cacheSocial:", this.initData.cacheSocial);
			console.log("cacheFund:", this.initData.cacheFund);
		},
		getWorkCityCode : function(city){
			var code = null;
			$.each(this.initData.singleData.jobInformation.workCitySelection, function(i, o){
				if(o.value == city){
					code = o.key;
					return false;
				}
			});
			return code;
			
		},
		getEducationCode : function(education){
			var code = null;
			$.each(this.initData.singleData.basicPersonalInformation.educationSelection, function(i, o){
				if(o.value == education){
					code = o.key;
					return false;
				}
			});
			return code;
		},
		getMaritalStatusCode : function(maritalStatus){
			var code = null;
			$.each(this.initData.singleData.basicPersonalInformation.maritalStatusSelection, function(i, o){
				if(o.value == maritalStatus){
					code = o.key;
					return false;
				}
			});
			return code;
		},
		getBankCardNumber : function(bankName){
			var bankCardNumber = null;
			$.each(this.initData.singleData.detailPersonalInformation.bankCards, function(i, o){
				if(o.bankName == bankName){
					bankCardNumber = o.bankCardNumber;
					return false;
				}
			});
			return bankCardNumber;
		},
		getBankName : function(bankCardNumber){
			var bankName = null;
			$.each(this.initData.singleData.detailPersonalInformation.bankCards, function(i, o){
				if(o.bankCardNumber == bankCardNumber){
					bankName = o.bankName;
					return false;
				}
			});
			return bankName;
		},
		
		_loadSource : function(callback){
			this.require([
				"lib/platform",
				"lib/mobiscroll"
			], function(){
				callback.call(this);
			});
		},
		setPageCache : function(){
			this._setCache({
				cacheFormData : this.getFormData($("#improveForm"))
			});
		},
		
		/**
		* 对学历 婚姻 工作城市三个字段的值设置为code
		*/
		changeFormData : function(formData){
			var singleData = this.initData.singleData;
			var personData = singleData.basicPersonalInformation;
			var jobData = singleData.jobInformation;
			var detailData = singleData.detailPersonalInformation;
			var maritalStatusSelection = personData.maritalStatusSelection;
			var educationSelection = personData.educationSelection;
			var workCitySelection = jobData.workCitySelection;
			$.each(educationSelection, function(i, o){
				
			});
			formData.education = "";
			formData.maritalStatus = "";
			formData.workCity = "";
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("loan/initImprove");
			this.setXHR("initImprove", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData.singleData = json.body;
					callback.call(this);
				});
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadSource(function(){
				this._setInitData(function(){
					this.view("loan/improve", function(view){
						this.renderContainer(view);
						this.container.find(".page").html(this.render("tpl-main-view"));
						this.init();
					});
				});
			});
		},
		_checkForm : function(){
			var btn = $("#btnSubmit");
			var checked = true;
			var education = $("#inp-education");
			var maritalStatus = $("#inp-maritalStatus");
			var workCity = $("#inp-workCity");
			var workUnit = $("#inp-workUnit");
			var monthlyIncome = $("#inp-monthlyIncome");
			var bankCardNumber = $("#inp-bankCardNumber");
			var fundAccount = $("#inp-fundAccount");
			var socialSecurityAccount = $("#inp-socialSecurityAccount");
			if($.trim(education.val()) == education.attr("data-empty")){
				checked = false;
			}else if($.trim(maritalStatus.val()) == maritalStatus.attr("data-empty")){
				checked = false;
			}else if($.trim(workCity.val()) == workCity.attr("data-empty")){
				checked = false;
			}else if($.trim(workUnit.val()) == ""){
				checked = false;
			}else if($.trim(monthlyIncome.val()) == ""){
				checked = false;
			}else if(bankCardNumber.attr("data-empty") && $.trim(bankCardNumber.val()) == bankCardNumber.attr("data-empty")){
				checked = false;
			}else if($.trim(fundAccount.val()) == ""){
				checked = false;
			}else if($.trim(socialSecurityAccount.val()) == ""){
				checked = false;
			}
			if(!checked){
				btn.attr("disabled", "disabled");
			}else{
				btn.removeAttr("disabled");
			}
		},
		init : function(){
			var _this = this;
			var form = $("#improveForm");
			this._checkForm();
			//婚姻选择
			(function(){
				var input = $("#inp-maritalStatus");
				var maritalStatusList = $("#maritalStatusList");
				var data = [];
				var mobiscrollObj;
				maritalStatusList.find("li").each(function(){
					var o = $(this);
					data.push({
						display : o.attr("data-name")
					});
				});
				input.mobiscroll({
					theme: "ios",
					mode: "mixed",
					display: "bottom",
					lang: "zh",
					
					wheels : [
						[
							{
								label : "choose",
								data : data
							}
						]
					],
					onSet : function(e, inst){
						var name = e.valueText;
						_this._checkForm();
						
					}
				});
				
				mobiscrollObj = input.mobiscroll("getInst");
				_this.objList.maritalStatusList = mobiscrollObj;
				_this.container.on("click", "#handle-maritalStatus", function(){
					mobiscrollObj.show();
				});
				
			})();
			
			//工作城市选择
			(function(){
				var input = $("#inp-workCity");
				
				var areaList = $("#areaList");
				var data = [];
				var mobiscrollObj;
				areaList.find("li").each(function(){
					var o = $(this);
					data.push({
						display : o.attr("data-name")
					});
				});
				
				input.mobiscroll({
					theme: "ios",
					mode: "mixed",
					display: "bottom",
					lang: "zh",
					
					wheels : [
						[
							{
								label : "choose",
								data : data
							}
						]
					],
					onSet : function(e, inst){
						var name = e.valueText;
						_this._checkForm();
					}
				});
				mobiscrollObj = input.mobiscroll("getInst");
				_this.objList.areaList = mobiscrollObj;
				_this.container.on("click", "#handle-workCity", function(){
					mobiscrollObj.show();
				});
			})();
			
			//学历选择
			(function(){
				var input = $("#inp-education"); 
				
				var educationList = $("#educationList");
				var data = [];
				var mobiscrollObj;
				educationList.find("li").each(function(){
					var o = $(this);
					data.push({
						display : o.attr("data-name")
					});
				});				
				input.mobiscroll({
					theme: "ios",
					mode: "mixed",
					display: "bottom",
					lang: "zh",
					
					wheels : [
						[
							{
								label : "choose",
								data : data
							}
						]
					],
					onSet : function(e, inst){
						var name = e.valueText;
						_this._checkForm();
						
					}
				});				
				mobiscrollObj = input.mobiscroll("getInst");
				_this.objList.educationList = mobiscrollObj;
				_this.container.on("click", "#handle-education", function(){
					mobiscrollObj.show();
				});
			})();
			
			//银行卡选择
			(function(){
				if(_this.initData.singleData.detailPersonalInformation.isShowAddBankCard == "false"){
					return;
				}
				var input = $("#inp-bankCardNumber"); 
				var defaultImgUrl = _this.getBankImgByCode("99999");
				var bankList = $("#bankList");
				var data = [];
				var mobiscrollObj;
				bankList.find("li").each(function(i){
					var o = $(this);
					data.push({
						display : o.attr("data-val") == "add" ? "<div class='add-item'>+&nbsp;添加新的银行卡</div>" : "<div class='bank-item'  ><img src='"+ _this.getBankImgByCode(o.attr("data-bank-code")) +"'>" + o.attr("data-name") + "</div>"
					});
				});		
				
				input.mobiscroll({
					
					theme: "ios",
					mode: "mixed",
					display: "bottom",
					lang: "zh",
					
					wheels : [
						[
							{
								label : "choose",
								data : data
							}
						]
					],
					
					onSet : function(e, inst){
						
						var v = e.valueText;
						var tempNode = $(v);
						
						if(/add\-item/.test(v)){
							input.val(input.attr("data-empty"));
							_this.setPageCache();
							_this.redirect("loan/recharge");
						}else{
							input.val(tempNode.text());
							_this._checkForm();
						}
						
					}
					
				});	
				
				mobiscrollObj = input.mobiscroll("getInst");
					
				_this.objList.bankList = mobiscrollObj;
				_this.container.on("click", "#handle-bankCardNumber", function(){
					
					mobiscrollObj.show();
				});
			})();
			
			this.container.on("click", "#handle-fundAccount", function(e){
				var input = form.find("[name='workCity']");
				var city = $.trim(input.val());
				var borrowCode = $.trim(form.find("[name='borrowCode']").val());
				if(city == input.attr("data-empty")){
					_this.alertMsg("请选择工作城市");
					return;
				}
				_this.setPageCache();
				console.log(_this.getPageCache());
				_this.redirect("loan/initFundAccount", {
					cityName : city,
					cityCode : _this.getWorkCityCode(city),
					borrowCode : borrowCode
				});
			}).on("click", "#handle-socialSecurityAccount", function(e){
				var input = form.find("[name='workCity']");
				var city = $.trim(input.val());
				var borrowCode = $.trim(form.find("[name='borrowCode']").val());
				if(city == input.attr("data-empty")){
					_this.alertMsg("请选择工作城市");
					return;
				}
				_this.setPageCache();
				console.log(_this.getPageCache());
				_this.redirect("loan/initSocialAccount", {
					cityName : city,
					cityCode : _this.getWorkCityCode(city),
					borrowCode : borrowCode
				});
			}).on("input", "[name='education'],[name='maritalStatus'],[name='inp-workCity'],[name='workCity'],[name='workUnit'],[name='monthlyIncome'],[name='bankCardNumber'],[name='fundAccount'],[name='socialSecurityAccount']", function(e){
				console.log("input");
				_this._checkForm();
			}).on("submit", "#improveForm", function(e){
				e.preventDefault();
				var btn = $("#btnSubmit");
				var formData = _this.getFormData(this);
				formData.education = _this.getEducationCode(formData.education);
				formData.maritalStatus = _this.getMaritalStatusCode(formData.maritalStatus);
				formData.workCity = _this.getWorkCityCode(formData.workCity);
				formData.bankCardNumber = _this.getBankCardNumber(formData.bankCardNumber);
				console.log(formData);
				btn.attr("disabled", "disabled");
				_this._save(formData, function(json){
					if(json === null){
						btn.removeAttr("disabled");	
						return;
					}
					if(json.footer.status != 200){
						btn.removeAttr("disabled");
						this.alertMsg(json.footer.message);
						return;
					}
					this.replaceDirect("loan/savePersonalInformationSuccess", json.body);
					
				});
			});
			
			
		},
		_save : function(formData, callback){
			var router = this.getApiRouter("loan/savePersonalInformation", formData);
			this.setXHR("savePersonalInformation", function(){
				return this.postData(router.url, router.data, function(json){
					callback.call(this, json);
				});
			});
		}
	}, AppController);
	
	return _class_;
}); 
