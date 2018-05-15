/**
 * @actionClass goldFish/loanContract -  小金鱼借款合同
*/
define("app/vj/controller/goldFish/loanContract", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = this.getParam("title");
			this.borrowCode = this.getParam("borrowCode");
			this.contractCode = this.getParam("contractCode");
			this.needUserId = true;
			this.formUrl = this.getParam("url");
			
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;
			if(!this.formUrl){
				console.log("undefined formUrl");
				return;
			}
			
			
			
			this.view("goldFish/loanContract", function(view){
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


