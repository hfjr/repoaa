/**
 * @actionClass loan/initApply
*/
define("app/vj/controller/loan/initApply", [], function(){
		
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "时光钱包";
			this.styleList = ["loan/index"];
			this.showBackHomeNav = true;
			this.needUserId = true;
			//初始化数据
			this.initData = null;
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("loan/initApply");
			this.setXHR("initApply", function(){
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
			this.setInitData(function(){
				this.view("loan/initApply", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		init : function(){
			var _this = this;
			var code = this.initData.code;
			this.container.on("tap", "#applyBtn", function(e){
				e.preventDefault();
				e.stopPropagation();
				var dialogConfig = null;
				//初始化或者审核失败，修改资料
				if(code == "201701" || code == "201703"){
					if(_this.initData.isTieBankCard == "false"){
						dialogConfig = _this.initData.tieBankCardMessage.split("|");
						_this.dialogList.unbindCardTipDialog =	FixDialog.getInstance({
							id : "unbindCardTipDialog",
							showHandlebar : false,
							content : "<div class='c-alert-m'>" + dialogConfig[0] + "</div>",
							buttons : [
								{
									text : dialogConfig[1]
								},
								{
									text : dialogConfig[2],
									fn : function(){
										_this.redirect("loan/recharge", {
											callbackPath : _this.path
										});
									}
								}
							]
						});

					}else{
						_this.redirect("loan/improve");
					}
				}
				
			}).on("tap", "#aboutBtn", function(e){
				e.preventDefault();
				e.stopPropagation();
				_this.redirect("loan/about", {
					url : _this.initData.applyWizardDetailURL,
					title : _this.initData.applyWizardDetailURLTitle
				});
			}).on("tap", "#protocolBtn", function(e){
				e.preventDefault();
				e.stopPropagation();
				_this.redirect("loan/protocol", {
					url : _this.initData.protocolDetailURL,
					title : _this.initData.protocolDetailURLTitle
				})
			}).on("tap", "#applyErrorBtn", function(e){
				e.preventDefault();
				e.stopPropagation();
				var data = _this.initData.applyFailMessage.split("|");
				_this.dialogList.applyFailMessage = FixDialog.getInstance({
					title : data[0],
					content : "<div class='c-alert-m2'>" + data[1] + "</div>",
					buttons : [
						{
							text : data[2]
						}
					]
				});
			});
			/*
			(function(){
				var citys = [
					"上海",
					"北京",
					"广州",
					"深圳",
					"长沙",
					"苏州",
					"连云港"
				];
				console.log(_this.json_encode(_this.getCitysMap(citys)));
			})();
			*/
		},
		getCitysMap : function(citys){
			//字母城市对象
			var keys = {};
			//字母列表
			var keysList = [];
			//排序后的字母城市对象
			var targetCitysMap = {};
			var pinyin = new JSPinyin();
			for(var i = 0, l = citys.length; i < l; i++){
				var city = citys[i];
				var _first = city.charAt(0)
				var key = pinyin.getCamelChars(_first);
				if(typeof(keys[key]) == "undefined"){
					keys[key] = [city];
				}else{
					keys[key].push(city);
				}
			}
			for(var key in keys){
				keysList.push(key);
			}
			//字母排序
			keysList = keysList.sort();
			$.each(keysList, function(i, key){
				targetCitysMap[key] = keys[key];
			});
			return targetCitysMap;
		}
	}, AppController);
	return _class_;
}); 
