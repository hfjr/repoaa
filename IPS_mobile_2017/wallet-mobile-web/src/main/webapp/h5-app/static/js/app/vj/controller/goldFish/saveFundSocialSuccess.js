/**
 * @actionClass goldFish/saveFundSocialSuccess - 社保公积金信息提交成功
*/
define("app/vj/controller/goldFish/saveFundSocialSuccess", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "信息提交";
			this.styleList = [
				"goldfish/tips"
			];
			this.formData = null;
			var	formData = this.getParam("formData");
			if(formData){
				this.formData = this.json_decode(formData);
			}
			
			this.needUserId = true;
			
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			if(!this.formData){
				this.alertMsg("undefined formData");
				return;
			}
			
			this.view("goldFish/saveFundSocialSuccess", function(view){
				this.renderContainer(view);
				this.container.find(".page").html(this.render("tpl-main-view"));
				this.init();
			});
			
		},
		init : function(){
			var _this = this;
			this.container.on("click", function(){
				history.go(-1);
			});
			
		}
		
	}, AppController);
	return _class_;
}); 
