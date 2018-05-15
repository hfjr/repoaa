/**
 * @actionClass eAccount/salaryDetail 
*/
define("app/vj/controller/eAccount/salaryDetail", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"eAccount/salaryDetail"
			];
			this.title = this.getParam("title");
			this.id = this.getParam("id");
			this.initData = null;
			this.needUserId = true;
			
		},
		setInitData : function(callback){
			callback = callback || function(){};
			var router = this.getApiRouter("queryPayRollDetail", {
				payRollId : this.id
			});
			this.setXHR("queryPayRollDetail", function(json){
				return this.postData(router.url, router.data, function(json){
					if(json == null){
						return;
					}
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
			
			var _this = this;
			if(this.billType === null || this.orderId === null){
				this.alertMsg("缺少id");
			}
			this.setInitData(function(){
				this.view("eAccount/salaryDetail", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		
		init : function(){
			var _this = this;
			var box = $(".salaryDetail");
			box.animate({
				marginTop : 0.625 + "rem"
			}, 3000, function(){
				$("#printStart").hide();
				$("#printEnd").show();
			});
			this.addUninstallAction(function(){
				box.stop();
			});
		}
		
		
		
	}, AppController);
	
	return _class_;
}); 
