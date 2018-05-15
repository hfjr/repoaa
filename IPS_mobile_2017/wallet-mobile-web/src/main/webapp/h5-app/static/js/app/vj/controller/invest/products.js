/**
 * @actionClass vj/invest/products - 定期
*/
define("app/vj/controller/invest/products", [], function(){
	
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["invest/index"];
			this.title = "定期理财";
			this.bannerList = [];
			this.productList = [];
			this.isEnd = true;
			//是否有投资过
			this.whetherInvested = false;
			this.tempSwiper = null;
			this.currentPage = 1;
			this.investList = [];
			this.swiperIndex = 0;
			this.pageCache = null;
			this.showBackHomeNav = true;
			this.alphaUrl = null;
			//获取缓存
			if(this.isFromPrevPage()){
				
				this.pageCache = this.getPageCache(); 
				console.log("from prev page", this.pageCache);
				if(this.pageCache){
					
					this.investList = this.pageCache.investList.data;
					this.productList = this.pageCache.investList.data;
					this.currentPage = this.pageCache.investList.page;
					this.isEnd = this.pageCache.investList.isEnd;
					this.swiperIndex = this.pageCache.bannerFocus.index;
					this.bannerList = this.pageCache.bannerFocus.data;
					this.removePageCache(); 
					console.log("this.currentPage", this.currentPage);
				}
			}
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
		
		setListData : function(callback, showLoadingBar, onlyProduct){
			
			var _this = this;
			callback = callback || function(){};
			showLoadingBar = typeof(showLoadingBar) == "undefined" ? true : showLoadingBar;
			onlyProduct = typeof(onlyProduct) == "undefined" ? false : onlyProduct; 
			var routerBanner = this.getApiRouter("common/advertisement/queryInvestmentAdvertisementList");
			var routerProductList = this.getApiRouter("product/queryProductList", {
				page : 1
			});
			function _productList(){
				_this.setXHR("list", function(){
					return this.postData(routerProductList.url, routerProductList.data, function(json){
						if(json === null){
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
						
						
						this.isEnd = end;
						this.productList = data;
						this.investList = data;
						if(!end){
							this.currentPage += 1;
						}
						 
						callback.call(this);
					}, this.unlogincallback, showLoadingBar);
				});
				
			}
			
			this.setXHR("bannerList", function(){
				return this.postData(routerBanner.url, routerBanner.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status == 200){
						this.bannerList = json.body || [];
					}else{
						this.bannerList = [];
					}
					_productList();
					
				}, this.unlogincallback, showLoadingBar);
			});
				
			
			
		},
		loadView : function(callback){
			callback  = callback || function(){};
			this.view("invest/index", function(view){
				this.renderContainer(view);
				this.container.find(".page .main-view").html(this.render("tpl-main-view"));
				this.init();
				callback.call(this);
			});
		}, 
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;
			this._loadSource(function(){
				if(this.pageCache){
					this.loadView(function(){
						this.scrollTo(this.pageCache.scrollTop);
					});
					
				}else{
					this.scrollToTop();
					
					this.setListData(function(){
						this.loadView();
					});
					
				}
			});
		},
		/**
		 * 上拉刷新
		*/
		initDropLoad : function(){
			var _this = this;
			var mainPageContainer = this.container.find(".product-view");
			
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
					domNoData  : '<div class="dropload-noData">暂无更多</div>'
				},
				autoLoad : false,
				loadUpFn : function(me){
					_this.currentPage = 1;
					var routerProductList = _this.getApiRouter("product/queryProductList", {
						page : _this.currentPage
					});
					 
					_this.setXHR("list", function(){
						return this.postData(routerProductList.url, routerProductList.data, function(json){
							if(json === null){
								me.resetload();
								me.unlock();
								return;
							}
							if(json.footer.status != 200){
								this.alertMsg(json.footer.message);
								me.resetload();
								return;
							}
							
							var data = [];
							var end = true;
							
							data = json.body.records;
							var more = json.body.isMore == true || json.body.isMore == "true";
							end = !more;
							
							
							
							this.isEnd = end;
							this.productList = data;
							this.investList = data;
							var html = _this.render("investList", {
								list : data,
								init : true
							});
							$("#page-data .list").html(html);
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
						}, this.unlogincallback, false);
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
					var routerProductList = _this.getApiRouter("product/queryProductList", {
						page : _this.currentPage
					});
					console.log("list/" + _this.currentPage);
					_this.setXHR("list", function(){
						return this.postData(routerProductList.url, routerProductList.data, function(json){
							if(json === null){
								me.resetload();
								return;
							}
							if(json.footer.status != 200){
								this.alertMsg(json.footer.message);
								me.resetload();
								return;
							}
							var html = "";
							var data = [];
							var end = true;
							data = json.body.records;
							var more = json.body.isMore == true || json.body.isMore == "true";
							end = !more;
							var html = _this.render("investList", {
								list : data,
								init : false
							});
							$("#page-data .list").append(html);
							this.investList = this.investList.concat(data);
							this.isEnd = end;
							$.each(data, function(i, o){
								_this.animateProgress(o.productId, Number(o.schedule || 0));
							});
							
							console.log("isEnd: " + end);
							
							 
							if(end){
								me.noData();
								me.lock();
								me.$domDown.hide();
							}else{
								_this.currentPage += 1;
							}
							me.resetload();
							 
						}, this.unlogincallback, false);
					});
					
				},
				threshold : 150				
			});
			if(this.isEnd){
				dropLoad.$domDown.hide();
			}
			this.setObj("dropLoad", dropLoad);
		},		
		banner : function(){
			var _this = this;

			this.container.on("tap", "#focus-container .swiper-slide", function(e){
				e.preventDefault();
				e.stopPropagation();
				var t = $(this);
				var title = t.attr("data-title");
				var url = t.attr("data-url");
				if(!url){
					return;
				}
				_this.redirect("invest/bannerDetail", {
					title : title,
					url : url
				});
			});
						
			
			if(this.bannerList.length <= 1){
				return;
			}
			var focusContainer = $("#focus-container");
			focusContainer.show();
			var container = focusContainer.find(".swiper-container")[0];
			var pagination = focusContainer.find(".pagination")[0];
			var obj = new Swiper(container, {
				mode : "horizontal",
				loop : true ,
				resistance : "100%" ,
				speed : 500,
				initialSlide : 0,
				autoplay : 5000,
				autoplayDisableOnInteraction : false,
				pagination : pagination,
				paginationClickable : true
			}); 
			focusContainer.on("mouseenter", function(){
				obj.stopAutoplay();
			}).on("mouseleave", function(){
				obj.startAutoplay();
			});
			

			this.tempSwiper = obj;
			this.objList["swiper"] = obj;
			this.addUninstallAction(function(){
				focusContainer.off();
				
			});
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
		init : function(){
			var _this = this;
			this.banner();
			
			setTimeout(function(){
				_this.initFirstListEvt();
			}, 400);
			_this.initDropLoad();
			this.container.on("tap", "#page-data [data-invest-id]", function(e){
				e.stopPropagation();
				e.preventDefault();
				var id = $(this).attr("data-invest-id");
				_this.setPageCache();
				if(id){
					_this.redirect("invest/show", {
						id : id
					});
				}
			});
			$(window).on("resize.changeFixPosition", function(e){
				var blank = $(".focus-container-blank");
				var top;
				if(blank.size() == 0){
					top = 0;
				}else{
					top = blank[0].offsetHeight + blank[0].offsetTop;
				}
				$("#my-invest-bar").css({
					top : top
				});
			}).trigger("resize.changeFixPosition");
			
			this.addUninstallAction(function(){
				$(window).off("resize.changeFixPosition");
				
			});
		},
		
		setPageCache : function(){
			var top = this.getPageScrollTop();
			console.log("currentPage", this.currentPage);
			var pageCache = {
				bannerFocus : {
					index : this.tempSwiper ? this.tempSwiper.activeLoopIndex : -1,
					data : this.bannerList
				},
				investList : {
					isEnd : this.isEnd,
					page : this.currentPage,
					data : this.investList
				},
				scrollTop : top
			};
			var v  = this.json_encode(pageCache);
			this.storage.setItem(this.path, v);
			
		},
		initFirstListEvt : function(){
			var _this = this;
			$.each(this.productList, function(i, o){
				_this.animateProgress(o.productId, Number(o.schedule || 0));
			});
			
		}
	}, AppController);
	
	return _class_;
}); 
