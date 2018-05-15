
/**
 * @actionClass vj/user/registerChoose - 注册/激活选择
*/
define("app/vj/controller/user/registerChoose", [], function(css){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["widget/3dflow", "passport/register"];
			this.title = "注册/激活";
			this.css = css;
			this.type = this.getParam("type") || "geren";
			this.sendToneCodePath = null;
			this.sendSMSCodePath = null;
			this.registerPath = null;
			
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadSwiperWidth3D(function(){
				this.init();
			});
		},
		
		init : function(){
			var _this = this;
			this.loginListener(function(){
				this.replaceDirect("index/index");
			}, function(){
				this.view("user/registerChoose", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					
					this.initSwiper();
					this.container.on("tap", "#p-swiper-container .btn-bar a", function(e){
						e.preventDefault();
						e.stopPropagation();
						var type = $(this).attr("data-type");
												
						_this.redirect("user/register", {
							type : type
						});
					});
				});
			});
			

		},
		
		pageBg : function(index){
			var con = $("#page-bg");
			var items = con.find(".page-item");
			var currentItem = items.eq(index);
			currentItem.siblings().stop();
			currentItem.css({
				"z-index" : 1
			}).stop().show().css({
				opacity : 0
			}).animate({
				opacity : 1
			}, 200, function(){
				$(this).stop().siblings().hide().css({zIndex : 0});
				$(this).css({zIndex  : 0});	
			});
		},
		initSwiper : function(){
			var _this = this;
			var _index = 0;
			var mySwiper = new Swiper('.swiper-container',{
				slidesPerView : 2,
				centeredSlides : true,
			    autoResize : false,	
				loop : false,
				tdFlow: {
					rotate : 10,
					stretch : -50,
					depth: 400,
					modifier : true,
					shadows : false
				},
				onTouchEnd : function(o){
					var index = o.activeIndex;
					if(index < 0){
						index = 0;
					}
					
					if(_index == index){
						return;
					}
					_index = index;
					_this.pageBg(index);
				},
				onSlideChangeStart : function(o){
					var index = o.activeIndex;
					
					if(_index == index){
						return;
					}
					_index = index;
					_this.pageBg(index);
				},
				initialSlide : this.type == "geren" ? 0 : 1
			});
			this.objList["mySwiper"] = mySwiper;
			
			
		}
		
		
		
	}, AppController);
	
	return _class_;
}); 
