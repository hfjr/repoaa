/**
 * @actionClass vj/goldFish/investRecord - 投资记录
*/
define("app/vj/controller/goldFish/investRecord", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["goldfish/investList"];
			//标题栏文字
			this.title = "投资记录";
			this.needUserId = true;
			this.investList = [];
			this.isEnd = true;
			this.currentPage = 1;

			this.productId = this.getParam("productId");
			this.showBackHomeNav = false;
		},
		_getList : function(page, callback, showLoadingBar){
			showLoadingBar = typeof(showLoadingBar) == "undefined" ? false : showLoadingBar;
			var router = this.getApiRouter("goldFish/queryProductInvestmentRecordList", {
				page : page,
				productId : this.productId,

			});

			return this.setXHR("goldFish/queryProductInvestmentRecordList", function(){
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

		setInitData : function(callback){
			callback = callback || function(){};

			this.setInitInvestList(function(){

				callback.call(this);

			});

		},
		setInitInvestList : function(callback){
			this._getList(this.currentPage, function(end, data, json){
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
				listWrap.find(".billul").append(html);
			}
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadDropLoad(function(){
				this.setInitData(function(){
					this.view("goldFish/investRecord", function(view){
						this.renderContainer(view);
						this.container.find(".page .main-view").html(this.render("tpl-main-view"));
						this.initDropLoad();
					});
				});
			});

		},
		initDropLoad : function(){
			var _this = this;
			var _this = this;
			var mainPageContainer = $("#page-data-wrap");

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

				}
			});
			if(this.isEnd){
				dropLoad.$domDown.hide();
			}
			this.setObj("dropLoad", dropLoad);


		}
	}, AppController);
	return _class_;

});
