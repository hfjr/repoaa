/**
 * @actionClass account/billList 
*/
define("app/vj/controller/account/billList", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "账单";
			this.styleList = [
				"widget/feedify",
				"account/billList"
			];
			this.needUserId = true;
			this.investList = [];
			this.billType = this.getParam("billType") || "all";
			this.isEnd = true;
			this.currentPage = 1;
			this.pageCache = null;
			//this.showBackHomeNav = true;
			//获取缓存
			
			this.pageCache = this.getPageCache(); 
			console.log("from prev page", this.pageCache);
			if(this.pageCache){
				this.investList = this.pageCache.investList.data;
				this.currentPage = this.pageCache.investList.page;
				this.isEnd = this.pageCache.investList.isEnd;
				this.billType = this.pageCache.investList.billType;
				this.removePageCache(); 
				console.log("this.currentPage", this.currentPage);
			}
					

			
		},
		
		getGroupsByList : function(list){
			if(list.length == 0 || !list){
				return null;
			}
			
			
			
			var groupMap = {};
			$.each(list, function(i, o){
				var inGroup = typeof(groupMap["group-" + o.groupId]) != "undefined";
				if(!inGroup){
					groupMap["group-" + o.groupId] = {list : [], groupId : o.groupId, groupTitle : o.groupTitle};
				}
				groupMap["group-" + o.groupId].list.push(o);
			});
		 
			return groupMap;
		},
		
		setPageCache : function(){
			console.log("currentPage", this.currentPage);
			
			var pageCache = {
				
				investList : {
					isEnd : this.isEnd,
					page : this.currentPage,
					data : this.investList,
					billType : this.billType
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
		_appendInvestList : function(list, isInit){
			var _this = this;
			isInit = typeof(isInit) == "undefined" ? true : isInit;
			var listWrap = $("#page-data .list");
			var groups = this.getGroupsByList(list);
			
			var html = "";
			if(isInit){
				html = this.render("groups", {
					groups : groups,
					init : isInit
				});
				listWrap.empty();
				listWrap.append(html);
			}else{
				if(!groups){
					return;
				}
				$.each(groups, function(groupId, group){
					var targetGroup = $("[data-group-id='"+ groupId +"']");
					var appendHtml = "";
					var singleGroups = {};
					
					if(targetGroup.size() == 0){
						singleGroups[groupId] = group;
						appendHtml = _this.render("groups", {
							groups : singleGroups,
							init : isInit
						});
						listWrap.append(appendHtml);
					}else{
						appendHtml = _this.render("itemList", {
							list : group.list
						});
						targetGroup.find(".item-list").append(appendHtml);
					}
				});
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
			var router = this.getApiRouter("queryBillList", {
				page : page,
				billType : this.billType
			});
			
			return this.setXHR("list", function(){
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
			this.require(["lib/feedify"], function(){
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
			});
		},
		
		/**
		 * 加载view
		*/
		loadView : function(callback){
			callback  = callback || function(){};
			this.view("account/billList", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
				callback.call(this);
				
			});
		}, 
		init : function(){
			var _this = this;
				
							
			_this.objList.feedifyObj = new Feedify($('.feedify'));
			
			this.initDropLoad();
			this.container.on("tap", "#page-data [data-order-id]", function(e){
				e.stopPropagation();
				e.preventDefault();
				var orderId = $(this).attr("data-order-id");
				var billType = $(this).attr("data-bill-type"); 
				_this.setPageCache();
				
				if(billType == "apply_ma_to_rf"){
					_this.redirect("account/investDetail", {
						orderId : orderId
					});
				}else if(billType == "apply_la_to_lf"){
					_this.redirect("goldFish/myInvestDetailOrder", {
						orderId : orderId
					});
				}else if(billType == "transfer_lf_to_la"){
					_this.redirect("goldFish/myLoanDetailOrder", {
						orderId : orderId
					});
				}else{
					_this.redirect("account/billDetail", {
						orderId : orderId,
						billType : billType
					});
				}
			});
		}
		
		
		
	}, AppController);
	
	return _class_;
}); 
