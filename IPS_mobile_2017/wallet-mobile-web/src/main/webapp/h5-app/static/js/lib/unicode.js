/**
 * Unicode 编码解码
*/
var Unicode = {
 
	stringify: function (str) {
 
		var res = [],
			len = str.length;
	 
		for (var i = 0; i < len; ++i) {
			res[i] = ("00" + str.charCodeAt(i).toString(16)).slice(-4);
		}
	 
		return str ? "\\u" + res.join("\\u") : "";
	},
	 
	parse: function (str) {
	 
		str = str.replace(/\\/g, "%");
		return unescape(str);
	}
};	