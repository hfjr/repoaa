/**
 * @actionClass vj/invest/investRecord - 投资记录
*/
define("app/vj/controller/invest/investRecord", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["invest/recordList"];
			//标题栏文字
			this.title = "投资记录";
			this.recordList = [];
			this.productId = this.getParam("id");
			this.showBackHomeNav = true;
		},
		setRecordList : function(callBack, showLoadingBar){
			showLoadingBar = typeof(showLoadingBar) == "undefined" ? true : showLoadingBar;
			var router = this.getApiRouter("product/queryProductInvestmentRecordList", {
				productId : this.productId
			});
			this.setXHR("recordList", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status == 200){
						this.recordList = json.body.records;
						callBack.call(this);
					}else{
						alert(json.footer.message);
					}
					
				}, this.unlogincallback, showLoadingBar);
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this._loadDropLoad(function(){
				this.setRecordList(function(){
					this.view("invest/investRecord", function(view){
						this.renderContainer(view);
						this.container.find(".page .main-view").html(this.render("tpl-main-view"));
						this.initDropLoad();
					});
				});
			});
			
		},
		initDropLoad : function(){
			var _this = this;
			var mainPageContainer = this.container.find(".page");
			var dropLoad = mainPageContainer.dropload({
				scrollArea : window,
				domUp : {
					domClass   : 'dropload-up',
					domRefresh : '<div class="dropload-refresh">↓下拉刷新</div>',
					domUpdate  : '<div class="dropload-update">↑释放更新</div>',
					domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
				},
				autoLoad : false,
				loadUpFn : function(me){
					_this.setRecordList(function(){
						me.resetload();
						me.unlock();
						me.noData(false);
						
						this.container.find(".page .main-view").html(this.render("tpl-main-view"));
						
						
					}, false);
				}				
			});
			this.setObj("dropLoad", dropLoad);
			
		}
	}, AppController);
	return _class_;
	
});
