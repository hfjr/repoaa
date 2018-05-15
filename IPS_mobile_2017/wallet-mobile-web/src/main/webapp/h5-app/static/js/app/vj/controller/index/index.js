/**
 * @actionClass index/index - 首页
*/
define("app/vj/controller/index/index", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "首页";
			this.styleList = [
				"index/index1"
			];
			this.needUserId = false;
			this.initData = null;
			this.showFooterNav = true;
			
		},
		
		
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("index/init");
			this.setXHR("index/init", function(){
				return this.postData(router.url, router.data, function(json){
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
			this._loadSwiper(function(){
				this.setInitData(function(){
					
					this.view("index/index", function(view){
						this.renderContainer(view);
						this.container.find(".page").html(this.render("tpl-main-view"));
						this.init();
					});
					
				});
			});
		},
		init : function(){
			var _this = this;
			this.banner();
			this.banner2();
			this.container.on("click", "[data-recommend-url]", function(e){
				var url = $(this).attr("data-recommend-url");
				var path = "";
				if(!url){
					return;
				}
				
				path = url.replace(/^(.*)#/, "");
				if(!path){
					return;
				}
				_this.redirect(path);
				
				
				//window.location.href = url;
				
			});
			
		},
		banner : function(){
			var _this = this;

			this.container.on("tap", "#focus-container .swiper-slide", function(e){
				e.preventDefault();
				e.stopPropagation();
				var t = $(this);
				var title = t.attr("data-title");
				var url = t.attr("data-url");
				var path = "";
				if(!url){
					return;
				}
				
				/*path = url.replace(/^(.*)#/, "");
				if(!path){
					return;
				}
				_this.redirect(path);
				*/
				
				window.location.href = url;
			});
						
			
			if(this.initData.bannerQueryList.length <= 1){
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
			
			this.objList["swiper"] = obj;
			this.addUninstallAction(function(){
				focusContainer.off();
				
			});
		},
		banner2 : function(){
			var _this = this;

			this.container.on("tap", "#recommend-container .swiper-slide", function(e){
				e.preventDefault();
				e.stopPropagation();
				
				/*var t = $(this);
				var title = t.attr("data-title");
				var url = t.attr("data-url");
				var path = "";
				if(!url){
					return;
				}
				path = url.replace(/^(.*)#/, "");
				if(!path){
					return;
				}
				*/
				
				_this.redirect("invest/show", {
					id : $(this).attr("data-id")
				});
				
			});
						
			
			if(this.initData.finacalRecommendList.length <= 1){
				return;
			}
			
			var focusContainer = $("#recommend-container");
			focusContainer.show();
			var container = focusContainer.find(".swiper-container")[0];
			var pagination = focusContainer.find(".pagination")[0];
			var obj = new Swiper(container, {
				mode : "horizontal",
				loop : true ,
				resistance : "100%" ,
				speed : 500,
				initialSlide : 0,
				//autoplay : 5000,
				//autoplayDisableOnInteraction : false,
				pagination : pagination,
				paginationClickable : true
			}); 
			focusContainer.on("mouseenter", function(){
				obj.stopAutoplay();
			}).on("mouseleave", function(){
				obj.startAutoplay();
			});
			
			this.objList["swiperRecommend"] = obj;
			this.addUninstallAction(function(){
				focusContainer.off();
				
			});
		}
	}, AppController);
	return _class_;
}); 
