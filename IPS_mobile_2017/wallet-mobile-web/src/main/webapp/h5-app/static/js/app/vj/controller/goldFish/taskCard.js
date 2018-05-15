/**
 * @actionClass goldFish/taskCard - 小金鱼任务卡
*/
define("app/vj/controller/goldFish/taskCard", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "任务卡";
			this.styleList = [
				"goldfish/index"
			];
			//this.showBackHomeNav = true;
			this.needUserId = true;
			this.productTypeCode = this.productTypeMap.XIAOJINYU;
			this.investList = [];
			this.isEnd = true;
			this.currentPage = 1;
		},
		setInitData : function(callback){
			callback = callback || function(){};
			this.setInitInvestList(function(){
				callback.call(this);
			});
		},
		setInitInvestList : function(callback){
			this._getList(1, function(end, data, json){
				if(json === null){
					return;
				}
				this.isEnd = end;
				this.investList = data;
				if(!end){
					this.currentPage += 1;
				}
				callback.call(this);
			}, true);
		},
		_appendInvestList : function(list, isInit){
			isInit = typeof(isInit) == "undefined" ? true : isInit;
			var listWrap = $("#page-data");
			var html = this.render("investList", {
				list : list,
				init : isInit
			});
			if(isInit){
				listWrap.empty().append(html);;
			}else{
				listWrap.find(".list").append(html);
			}
		},
		initDropLoad : function(){
			var _this = this;
			var mainPageContainer = this.container.find(".product-view");
			var listWrap = $("#page-data .list");
			
			var dropLoad = mainPageContainer.dropload({
				scrollArea : window,
				domUp : {
					domClass   : 'dropload-up',
					domRefresh : '<div class="dropload-refresh">↓下拉刷新</div>',
					domUpdate  : '<div class="dropload-update">↑释放更新</div>',
					domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
				},
				domDown : {
					domClass   : 'dropload-down',
					domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
					domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
					domNoData  : '<div class="dropload-noData">没有更多</div>'
				},
				autoLoad : false,
				loadUpFn : function(me){
					_this.currentPage = 1;
					_this._getList(_this.currentPage, function(end, data, json){
						if(json === null || json.footer.status != 200){
							me.resetload();
							me.unlock();
							return;
						}
						this.isEnd = end;
						this.investList = data;
						this._appendInvestList(data, true);
						me.resetload();
						me.unlock();
						me.noData(false);
						if(!end){
							_this.currentPage += 1;
							me.$domDown.show().html(me.opts.domDown.domRefresh);
						}else{
							me.$domDown.hide();
						}
						
					});
				},
				loadDownFn : function(me){
					 
					if(_this.isEnd){
						me.noData();
						me.lock();
						me.$domDown.hide();
						me.resetload();
						return false;
					} 
					console.log("loadDownFn page", _this.currentPage); 
					_this._getList(_this.currentPage, function(end, data, json){
						if(json === null || json.footer.status != 200){
							me.resetload();
							return;
						}
						_this._appendInvestList(data, false);
						this.investList = this.investList.concat(data);
						this.isEnd = end;
						console.log("isEnd: " + end);
						if(end){
							me.noData();
							me.lock();
							me.$domDown.hide();
						}else{
							_this.currentPage += 1;
						}
						me.resetload();
					});
					
				},
				threshold : 150				
			});
			
			if(this.isEnd){
				dropLoad.$domDown.hide();
			}
			this.setObj("dropLoad", dropLoad);
		},		
		
		_getList : function(page, callback, showLoadingBar){
			showLoadingBar = typeof(showLoadingBar) == "undefined" ? false : showLoadingBar;
			var router = this.getApiRouter("goldFish/taskList", {
				page : page
			});
			
			return this.setXHR("taskList", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						callback.call(this, null, null, null);
						return;
					}
					var data = [];
					var end = true;
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}else{
						data = json.body.records;
						var more = json.body.isMore == true || json.body.isMore == "true";
						end = !more;
					}
					callback.call(this, end, data, json);
				}, this.unlogincallback, showLoadingBar);
			});	
		},		
		
		
		indexAction : function(){
			var _this = this;
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadDropLoad(function(){
				this.scrollToTop();
				this.setInitData(function(){
					this.loadView();
				});
			});
		},
		/**
		 * 加载view
		*/
		loadView : function(callback){
			callback  = callback || function(){};
			this.view("goldFish/taskCard", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
				callback.call(this);
			});
		},
		init : function(){
			var _this = this;
			this.initDropLoad();
			this.container.on("tap", "#page-data li", function(e){
				e.stopPropagation();
				e.preventDefault();
				 
				var t = $(this);
				var data = _this.json_decode(t.attr("data-json"));
				var canRepeat = data.isRepeatTask == "true";
				var msgData = null;
				var content = "";
				console.log(canRepeat);
				if(canRepeat){
					if(data.taskCode == "total_investment"){
						_this.redirect("invest/index");
					}
				}else{
					if(data.taskCode == "bing_card"){
						if(data.taskState == "notCompletion" || data.taskState == "completeFail"){
							_this.redirect("account/recharge", {
								callbackPath : _this.path
							});
						}
					}else if(data.taskCode == "social_security_fund"){
						if(data.taskState == "notCompletion"){
							_this.redirect("goldFish/fund");
						}else if(data.taskState == "completeFail" ){
							
							msgData = data.failInformation.split("|");
							$.each(msgData[0].split(/\n/), function(i, v){
								content += "<p>" + v + "</p>";
							});
							_this.dialogList.socialDialog = FixDialog.getInstance({
								showHandlebar : false,
								content : "<div class='c-sysTip'>" + content + "</div>",
								buttons : [
									{
										text : msgData[1]
									},
									{
										text : msgData[2],
										fn : function(){
											_this.redirect("goldFish/fund");
										}
									}
								]
							});
						}
					}
				}
				
				
			});
			
		}
		
		
	}, AppController);
	
	return _class_;
}); 
