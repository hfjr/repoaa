/**
 * @actionClass account/investSafeDetail
*/
define("app/vj/controller/account/investSafeDetail", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "保单详情";
			this.needUserId = true;
			this.safeId = this.getParam("safeId");
			this.safeUrl = this.getParam("safeUrl");
			this.pattern = /(.*)(\/wallet(\-|_)mobile\/(.*))/;
			this.initData = null;
			this.showBackHomeNav = true;
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;
			if(!this.safeUrl){
				console.log("undefined safeUrl");
				return;
			}
			this.view("account/investSafeDetail", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
			
		},
		init : function(){
			$("#safeFrom").trigger("submit");
			
		}
	}, AppController);
	return _class_;
});


