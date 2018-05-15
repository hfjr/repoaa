/**
 * @actionClass eAccount/billList 
*/
define("app/vj/controller/eAccount/billList", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "活期账单";
			this.styleList = [
				"eAccount/billList"
			];
			this.needUserId = true;
			
			//账单
			this.investList = [];
			this.isEnd = true;
			this.currentPage = 1;
			
			//收益
			this.investList2 = [];
			this.isEnd2 = true;
			this.currentPage2 = 1;
			
			
			this.pageCache = null;
			//this.showBackHomeNav = true;
			//获取缓存
			if(this.isFromPrevPage()){
				this.pageCache = this.getPageCache(); 
				console.log("from prev page", this.pageCache);
				if(this.pageCache){
					this.investList = this.pageCache.investList.data;
					this.currentPage = this.pageCache.investList.page;
					this.isEnd = this.pageCache.investList.isEnd;
					
					this.investList2 = this.pageCache.investList2.data;
					this.currentPage2 = this.pageCache.investList2.page;
					this.isEnd2 = this.pageCache.investList2.isEnd;
					
					this.removePageCache(); 
					console.log("this.currentPage", this.currentPage);
					console.log("this.currentPage2", this.currentPage2);
				}
			}		

			
		},
		
		setPageCache : function(){
			console.log("currentPage", this.currentPage);
			console.log("currentPage2", this.currentPage2);
			
			var pageCache = {
				
				investList : {
					isEnd : this.isEnd,
					page : this.currentPage,
					data : this.investList
				},
				investList2 : {
					isEnd : this.isEnd2,
					page : this.currentPage2,
					data : this.investList2
				}
			};
			this._setCache(pageCache);
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
		setInitInvestList2 : function(callback){
			this._getList2(this.currentPage2, function(end, data, json){
				if(json === null){
					return;
				}
				this.isEnd2 = end;
				this.investList2 = data;
				if(!end){
					this.currentPage2 += 1;
				}
				callback.call(this);
			}, true);
			
		},
		_appendInvestList : function(list, isInit){
			isInit = typeof(isInit) == "undefined" ? true : isInit;
			var listWrap = $("#page-data-1");
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
		_appendInvestList2 : function(list, isInit){
			isInit = typeof(isInit) == "undefined" ? true : isInit;
			var listWrap = $("#page-data-2");
			var html = this.render("investList2", {
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
			var mainPageContainer = $("#queryEAccountDetail");
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
		destroyDropLoad : function(){
			this.uninstallObj("dropLoad");
			//账单
			this.investList = [];
			this.isEnd = true;
			this.currentPage = 1;
			
			
		},
		destroyDropLoad2 : function(){
			this.uninstallObj("dropLoad2");
			//收益
			this.investList2 = [];
			this.isEnd2 = true;
			this.currentPage2 = 1;
		},
		initDropLoad2 : function(){
			var _this = this;
			var mainPageContainer = $("#queryEAccountReciveDetail");
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
					_this.currentPage2 = 1;
					_this._getList2(_this.currentPage2, function(end, data, json){
						if(json === null || json.footer.status != 200){
							me.resetload();
							me.unlock();
							return;
						}
						this.isEnd2 = end;
						this.investList2 = data;
						this._appendInvestList2(data, true);
						me.resetload();
						me.unlock();
						me.noData(false);
						if(!end){
							_this.currentPage2 += 1;
							me.$domDown.show().html(me.opts.domDown.domRefresh);
						}else{
							me.$domDown.hide();
						}
						
					});
				},
				loadDownFn : function(me){
					 
					if(_this.isEnd2){
						me.noData();
						me.lock();
						me.$domDown.hide();
						me.resetload();
						return false;
					} 
					console.log("loadDownFn page", _this.currentPage2); 
					_this._getList2(_this.currentPage2, function(end, data, json){
						if(json === null || json.footer.status != 200){
							me.resetload();
							return;
						}
						_this._appendInvestList2(data, false);
						this.investList2 = this.investList2.concat(data);
						this.isEnd2 = end;
						console.log("isEnd2: " + end);
						if(end){
							me.noData();
							me.lock();
							me.$domDown.hide();
						}else{
							_this.currentPage2 += 1;
						}
						me.resetload();
					});
					
				},
				threshold : 150				
			});
			
			if(this.isEnd2){
				dropLoad.$domDown.hide();
			}
			this.setObj("dropLoad2", dropLoad);
		},		
		
		_getList : function(page, callback, showLoadingBar){
			showLoadingBar = typeof(showLoadingBar) == "undefined" ? false : showLoadingBar;
			var router = this.getApiRouter("cunqianguan/queryTAccountDetail", {
				page : page
			});
			
			return this.setXHR("queryTAccountDetail", function(){
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
		_getList2 : function(page, callback, showLoadingBar){
			showLoadingBar = typeof(showLoadingBar) == "undefined" ? false : showLoadingBar;
			var router = this.getApiRouter("cunqianguan/queryTAccountReciveDetail", {
				page : page
			});
			
			return this.setXHR("queryEAccountReciveDetail", function(){
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
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;
			this._loadDropLoad(function(){
				if(this.pageCache){
					this.loadView(function(){
						_this.scrollTo(_this.pageCache.scrollTop);
					});
				}else{
					this.scrollToTop();
					this.setInitData(function(){
						this.loadView();
						
					});
				}
			});
		},
		
		/**
		 * 加载view
		*/
		loadView : function(callback){
			
			callback  = callback || function(){};
			this.view("eAccount/billList", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
				callback.call(this);
				
			});
		}, 
		init : function(){
			var _this = this;
			
			this.initDropLoad();
			
			this.container.on("tap", "#page-data-1 [data-order-id]", function(e){
				e.stopPropagation();
				e.preventDefault();
				var orderId = $(this).attr("data-order-id");
				var billType = $(this).attr("data-bill-type"); 
				var datail = $(this).attr("data-detail");
				_this.setPageCache();
				_this.redirect("account/billDetail", {
					orderId : orderId,
					billType : billType
				});
			}).on("tap", ".tabnav a", function(e){
				e.preventDefault();
				e.stopPropagation();
				var con1 = $("#page-data-1");
				var con2 = $("#page-data-2");
				var t = $(this);
				var tab = $("#" + t.attr("data-tab-id"));
				if(t.hasClass("active")){
					return;
				}
				t.addClass("active").siblings().removeClass("active");
				tab.show().siblings().hide();
				if(t.index() == 0){
					_this.destroyDropLoad2();
					_this.scrollToTop();
					con1.empty();
					_this.setInitInvestList(function(){
						var html = _this.render("investList", {
							list : _this.investList,
							init : true
						});
						con1.html(html);
						_this.initDropLoad();
					});
				}else{
					_this.destroyDropLoad();
					_this.scrollToTop();
					con2.empty();
					_this.setInitInvestList2(function(){
						var html = _this.render("investList2", {
							list : _this.investList2,
							init : true
						});
						con2.html(html);						
						_this.initDropLoad2();
					});
					
				}
			});
		}
	}, AppController);
	
	return _class_;
}); 
