/**
 * Feedify v1.0.1 by Sarah Dayan | http://demos.sarahdayan.com/feedify
 * @update 插件改装为类，方便卸载 
 * By Jacky.wei
*/
(function(e, window) {
	var win = $(window);
	var instanceIndex = -1;
	function Feedify(ele){
		instanceIndex += 1;
		this.instanceIndex = instanceIndex;
		this.ele = $(ele).eq(0);
		this._init();
	}
	Feedify.prototype = {
		_init : function(){
			this._bind();
		},
		_getEventName : function(eventName){
			return eventName + ".feedify." + this.instanceIndex;
		},
		_unbind : function(){
			var scrollEvent = this._getEventName("scroll");
			var touchmoveEvent = this._getEventName("touchmove");
			win.off(scrollEvent).off(touchmoveEvent);
		},
		_bind : function(){
			var scrollEvent = this._getEventName("scroll");
			var touchmoveEvent = this._getEventName("touchmove");
			var i = this.ele;
			win.on(scrollEvent + " " + touchmoveEvent, function(){
				i.children(".feedify-item").each(function(){
					var d = e(this),
						t = i.width(),
						s = e(this).height(),
						h = e(this).children(".feedify-item-header").outerHeight(),
						f = d.offset(),
						o = f.top - e(window).scrollTop(),
						c = "-" + (s - h);
					o > c && 0 > o ? (e(this).addClass("fixed").removeClass("bottom").children(".feedify-item-header").css("width", t), e(this).children(".feedify-item-body").css("paddingTop", h), e(this).children(".feedify-item-header").css("width", t)) : c >= o ? (e(this).removeClass("fixed").addClass("bottom"), e(this).children(".feedify-item-body").css("paddingTop", h), e(this).children(".feedify-item-header").css("width", t)) : (e(this).removeClass("fixed").removeClass("bottom").children(".feedify-item-header").css("width", "auto"), e(this).children(".feedify-item-body").css("paddingTop", "0"), e(this).children(".feedify-item-header").css("width", "auto"))
				});				
			});
		},
		destroy : function(){
			this._unbind();
		}
		
	};
	
	window["Feedify"] = Feedify;	

})(jQuery, window);
