/**
 * @actionClass vj/invest/investSuccess - 投资成功
*/
define("app/vj/controller/invest/investSuccess", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["invest/investSuccess"];
			//标题栏文字
			this.title = "投资";
			this.needUserId = true;
			this.initData = null;
			this.id = this.getParam("id");
			this.showBackHomeNav = true;
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.initPayPasswdDialog(function(){
				this.view("invest/investSuccess", function(view){
					this.renderContainer(view);
					this.container.find(".page .main-view").html(this.render("tpl-main-view"));
					this.init();
				});
			});
			
		},
		
		init : function(){
			var _this = this;
			this.container.on("tap", ".btn-1", function(e){
				e.preventDefault();
				e.stopPropagation();
				_this.replaceDirect("account/invest");
			});
			this.container.on("tap", ".btn-2", function(e){
				e.preventDefault();
				e.stopPropagation();
				_this.replaceDirect("invest/index");
			});
			
		}
		
		
	}, AppController);
	return _class_;
	
});

