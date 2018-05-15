;(function(){
	var prefix = "static/js/app/vj/view/layouts/"; 
	var fileList = {"main.html":"<# if(self.autoAppendMsgWraper){ #>\r\n<div class=\"ui-msgWraper\" id=\"<# print(self.domId(\"msgWraper\")); #>\" style=\"display:none;\"></div>\r\n<# }; #>\r\n<#\r\n\tprint(__content__);\r\n#>\t\t\r\n<# if(self.showHomeNav){ #>\r\n<span data-tap-path=\"index/index\" class=\"widget-back-home\" style=\"display:none;\"></span>\r\n<# } #>\r\n<#\r\n\tvar showFooter = false;\r\n\tvar showFooterTab = _g_getParam(\"showFooterTab\");\r\n\tif(showFooterTab !== null){\r\n\t\tshowFooter = showFooterTab == 1;\r\n\t}else{\r\n\t\tshowFooter = self.showFooterNav;\r\n\t}\r\n\tconsole.log(\"showFooterTab\", showFooterTab);\r\n\tconsole.log(\"showFooter\", showFooter);\r\n\t\r\n#>\r\n<# if(showFooter){ #>\r\n<div class=\"g-downNavPlace\"></div>\r\n<nav class=\"g-downnav border-box flex-box\">\r\n\t\r\n\t<dl data-tap-path=\"index/index\" <# if( self.routerPath == \"index/index\" ){ #> class=\"active\"  <# } #> >\r\n\t\t<dt>\r\n\t\t\t<span class=\"icon_nav\"></span>\r\n\t\t</dt>\r\n\t\t<dd>首页</dd>\r\n\t</dl>\r\n\t<dl data-tap-path=\"invest/index\" <# if(self.routerData.controller == \"eAccount\" || self.routerData.controller == \"invest\"){ #> class=\"active\" <# } #>>\r\n\t\t<dt>\r\n\t\t\t<span class=\"icon_nav\"></span>\r\n\t\t</dt>\r\n\t\t<dd>理财</dd>\r\n\t</dl>\r\n\t<dl  data-tap-path=\"salaryLoan/index\" <# if( self.routerData.controller == \"salaryLoan\" ){ #> class=\"active\"  <# } #>>\r\n\t\t<dt>\r\n\t\t\t<span class=\"icon_nav\"></span>\r\n\t\t</dt>\r\n\t\t<dd>信贷</dd>\r\n\t</dl>\r\n\t<dl data-tap-path=\"account/index\" <# if( self.routerData.controller == \"account\" ){ #> class=\"active\"  <# } #>>\r\n\t\t<dt>\r\n\t\t\t<span class=\"icon_nav\"></span>\r\n\t\t</dt>\r\n\t\t<dd>个人</dd>\r\n\t</dl>\r\n</nav>\r\n<# } #>\r\n"};
	for(var key in fileList){
		var moduleName = "js/lib/text-v2.0.10!" + prefix + key;
		var v = fileList[key];
		define(moduleName, v);
	}
})();