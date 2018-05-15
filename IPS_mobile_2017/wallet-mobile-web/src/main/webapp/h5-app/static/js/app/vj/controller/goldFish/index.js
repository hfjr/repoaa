/**
 * @actionClass goldFish/index 
*/
define("app/vj/controller/goldFish/index", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.needUserId = true;
			this.styleList = [
				"goldfish/taskList"
			];
			this.initData = {
				detail : null
			};
			this.showFooterNav = true;
			this.productTypeCode = this.productTypeMap.XIAOJINYU;
			 
			this.title = "小金鱼";
			this.alphaUrl = null;
			this.productList = [];
			this.investList = [];
			this.isEnd = true;
			this.currentPage = 1;
			this.pageCache = null;
			//获取缓存
			//if(this.isFromPrevPage()){
				
				this.pageCache = this.getPageCache(); 
				console.log("from prev page", this.pageCache);
				//alert(this.pageCache);
				if(this.pageCache){
					this.investList = this.pageCache.investList.data;
					this.productList = this.pageCache.investList.data;
					this.currentPage = this.pageCache.investList.page;
					this.isEnd = this.pageCache.investList.isEnd;
					this.initData.detail = this.pageCache.detail;
					this.removePageCache(); 
					console.log("this.currentPage", this.currentPage);
				}
			//}			
			
			
		},
		_setDetail : function(callback){
			var router = this.getApiRouter("goldFish/init", {
				page : 1,
				productTypeCode : this.productTypeCode
			});
			this.setXHR("goldFish/init", function(){
				return this.postData(router.url, router.data, function(json){
					if(json == null){
						return;
					}
					if(json.footer.status != 200){
						this.alertMsg(json.footer.message);
						return;
					}
					this.initData.detail = json.body;
					callback.call(this);
				});
			});
		},
		
		
		
		setPageCache : function(){
			console.log("currentPage", this.currentPage);
			
			var pageCache = {
				
				investList : {
					isEnd : this.isEnd,
					page : this.currentPage,
					data : this.investList
				},
				detail : this.initData.detail
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
			this._getList(1, function(end, data, json){
				if(json === null){
					return;
				}
				this.isEnd = end;
				this.investList = data;
				this.initData.detail = json.body;
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
						_this.initFirstListEvt();
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
						$.each(data, function(i, o){
							_this.animateProgress(o.productId, Number(o.schedule || 0));
						});
						
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
			var router = this.getApiRouter("goldFish/init", {
				page : page,
				productTypeCode : this.productTypeCode
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
					this.productList = data;
					callback.call(this, end, data, json);
				}, this.unlogincallback, showLoadingBar);
			});	
		},		
		
		_loadSource : function(callback){
			var _this = this;
			this._loadSwiper(function(){
				this._loadDropLoad(function(){
					this.require(["image!static/img/circle.png", "lib/animate"], function(img){
						this.alphaUrl = img.src;
						callback.call(_this);
					});
				});
			});
		},
		/**
		 * 初始化动画入口
		*/
		animateProgress : function(id, percent){
			
			//六边形遮罩盖住canvas饼图
			var alpha = $("#progress-bar-" + id).find(".alpha");
			alpha.css({
				"background-image" : "url("+ this.alphaUrl +")",
				"background-position" : "center center",
				"background-repeat" : "no-repeat",
				"background-size" : "80px 80px"
			});
			this.canvas(id, percent);
			this.objList["cvs_" + id] = {
				destroy : function(){
					//console.log("destroy canvas " + id);
				}
			};
			
		},
		/**
		 * canvas饼图模拟动画进度
		*/
		canvas : function(id, max){
			
			function getDeg(num){
				return (num  - 90) * ( Math.PI / 180);
			};
			var r = 39;
			var ele = $("#cvs-" + id);
			if(ele.size() == 0){
				return;
			}
			var context = ele[0].getContext("2d");
			context.translate(40, 40);
			context.lineWidth = 1;
			context.fillStyle = "#FFBD30";
			context.strokeStyle = "#FFBD30";
			context.save();
			context.beginPath();
			context.fillStyle = "#EFF6F9";
			context.moveTo(0, 0);
			context.arc(0, 0, r, 0, 2 * Math.PI);
			context.closePath();
			context.fill();
			context.restore();
			if(!max){
				return;
			} 
			paddingArc(0, 360 * max);
			function paddingArc(startValue, endValue){
				var prevAngle = getDeg(startValue);
				init();
				function init() {
					var obj = new Animate({
						start : startValue,
						end : endValue,
						duration : 500,
						onupdate : function(value){
							var targetAngle = getDeg(value);
							context.beginPath();
							context.moveTo(0, 0);
							context.arc(0, 0, r, prevAngle, targetAngle);
							context.closePath();
							context.fill();
							context.stroke();
							prevAngle = targetAngle;
							$("#progress-bar-" + id).find(".s-percent").text(Number((value/360 * 100).toFixed(2)) + "%");
						},
						onanimated : function(){
							$("#progress-bar-" + id).find(".s-percent").text(Number((max * 100).toFixed(2)) + "%");
						}
					});
				}
			}
		},
		initFirstListEvt : function(){
			var _this = this;
			$.each(this.productList, function(i, o){
				_this.animateProgress(o.productId, Number(o.schedule || 0));
			});
			
		},
				
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;
			this._loadSource(function(){
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
			this.view("goldFish/index", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
				callback.call(this);
			});
		},
		
		
		init : function(){
			var _this = this;
			setTimeout(function(){
				_this.initFirstListEvt();
			}, 400);
			this.container.on("tap", "#page-data [data-invest-id]", function(e){
				e.stopPropagation();
				e.preventDefault();
				var id = $(this).attr("data-invest-id");
				_this.setPageCache();
				if(id){
					_this.redirect("goldFish/investProject", {
						productId : id,
						productTypeCode : _this.productTypeCode
					});
				}
			});
			this.initDropLoad();
			
		}
	}, AppController);
	
	return _class_;
}); 
