/**
 * @actionClass goldFish/myLoanDetail 
*/
define("app/vj/controller/goldFish/myLoanDetail", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = [
				"goldfish/getDetail"
			];
			this.needUserId = true;
			this.initData = null;
			this.borrowCode = this.getParam("borrowCode");
			this.title = "借款详情";
		},
		_setInitData : function(callback){
			var router = this.getApiRouter("goldFish/borrowRecordDetail", {
				borrowCode : this.borrowCode
			});
			this.setXHR("goldFish/borrowRecordDetail", function(){
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
			this._setInitData(function(){
				this.view("goldFish/myLoanDetail", function(view){
					this.renderContainer(view);
					this.container.find(".page").html(this.render("tpl-main-view"));
					this.init();
				});
			});
		},
		init : function(){
			
		}
	}, AppController);
	
	return _class_;
}); 
