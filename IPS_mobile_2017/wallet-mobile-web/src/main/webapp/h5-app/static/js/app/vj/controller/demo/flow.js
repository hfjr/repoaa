/**
 * @actionClass vj/demo/flow
*/


define("app/vj/controller/demo/flow", [], function(){
	
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			//±êÌâÀ¸ÎÄ×Ö
			this.title = "flow";
			this.styleList = ["widget/3dflow"];
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadSwiperWidth3D(function(){ 
				this.view("demo/flow", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
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
				slidesPerView:2,
				centeredSlides : true,
			    autoResize : false,	
				loop : false,
				tdFlow: {
					rotate : 0,
					stretch :10,
					depth: 500,
					modifier : 1,
					shadows:false
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
					console.log(index);
					_this.pageBg(index);
				},
				onSlideChangeStart : function(o){
					var index = o.activeIndex;
					if(_index == index){
						return;
					}
					_index = index;
					console.log(index);
					_this.pageBg(index);
				}
			});
			this.objList["mySwiper"] = mySwiper;
			
			
		},
		init : function(){
			this.initSwiper();
		}
		
	}, AppController);
	return _class_;
	
});
