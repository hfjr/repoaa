/**
 * @actionClass vj/invest/show - 标详情
*/
define("app/vj/controller/invest/show", [], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.styleList = ["invest/show"];
			//标题栏文字
			this.title = "投资项目";
			this.productId = this.getParam("id");
			this.showBackHomeNav = true;
			this.productData = null;
		},
		setProductData : function(callback, showLoadingBar){
			showLoadingBar = typeof(showLoadingBar) == "undefined" ? true : showLoadingBar;
			var router = this.getApiRouter("product/queryProductDetail", {
				productId : this.productId
			});
			this.setXHR("productData", function(){
				return this.postData(router.url, router.data, function(json){
					if(json === null){
						return;
					}
					if(json.footer.status == 200){
						this.productData = json.body;
						callback.call(this);
					}else{
						alert(json.footer.message);
					}
				}, this.unlogincallback, showLoadingBar);
			});
		},
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			var _this = this;

			this.container.on("tap", ".box-history", function(e){
				e.preventDefault();
				_this.redirect("invest/investRecord", {
					id : _this.productId
				});
			});
			
			this._loadDropLoad(function(){
				this.setProductData(function(){
					this.view("invest/show", function(view){
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
					_this.setProductData(function(){
						me.resetload();
						me.unlock();
						me.noData(false);
						if(this.productData){
							this.container.find(".page .main-view").html(this.render("tpl-main-view"));
						}
						
					}, false);
				}				
			});
			this.setObj("dropLoad", dropLoad);
			
		}
	}, AppController);
	return _class_;
	
});

