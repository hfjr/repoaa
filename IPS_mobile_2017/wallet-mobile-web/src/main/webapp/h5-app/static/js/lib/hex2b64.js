(function(root, factory){
	var o = factory();
	root.hex2b64 = o.hex2b64;
	root.b64tohex = o.b64tohex;
	root.str2hex = o.str2hex;
	root.hex2str = o.hex2str;
})(this, function(){

	var b64map="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var b64pad="=";
	var BI_RM = "0123456789abcdefghijklmnopqrstuvwxyz";
	function int2char(n) { return BI_RM.charAt(n); }
	function hex2b64(h) {
	  var i;
	  var c;
	  var ret = "";
	  for(i = 0; i+3 <= h.length; i+=3) {
		c = parseInt(h.substring(i,i+3),16);
		ret += b64map.charAt(c >> 6) + b64map.charAt(c & 63);
	  }
	  if(i+1 == h.length) {
		c = parseInt(h.substring(i,i+1),16);
		ret += b64map.charAt(c << 2);
	  }
	  else if(i+2 == h.length) {
		c = parseInt(h.substring(i,i+2),16);
		ret += b64map.charAt(c >> 2) + b64map.charAt((c & 3) << 4);
	  }
	  while((ret.length & 3) > 0) ret += b64pad;
	  return ret;
	}

	// convert a base64 string to hex
	function b64tohex(s) {
	  var ret = ""
	  var i;
	  var k = 0; // b64 state, 0-3
	  var slop;
	  for(i = 0; i < s.length; ++i) {
		if(s.charAt(i) == b64pad) break;
		v = b64map.indexOf(s.charAt(i));
		if(v < 0) continue;
		if(k == 0) {
		  ret += int2char(v >> 2);
		  slop = v & 3;
		  k = 1;
		}
		else if(k == 1) {
		  ret += int2char((slop << 2) | (v >> 4));
		  slop = v & 0xf;
		  k = 2;
		}
		else if(k == 2) {
		  ret += int2char(slop);
		  ret += int2char(v >> 2);
		  slop = v & 3;
		  k = 3;
		}
		else {
		  ret += int2char((slop << 2) | (v >> 4));
		  ret += int2char(v & 0xf);
		  k = 0;
		}
	  }
	  if(k == 1)
		ret += int2char(slop << 2);
	  return ret;
	}
	
	function hexdec (hex_string) {

	  hex_string = (hex_string + '').replace(/[^a-f0-9]/gi, '');
	  return parseInt(hex_string, 16);
	}	

	function chr (codePt) {
	  if(codePt > 0xFFFF) {
		codePt -= 0x10000;
		return String.fromCharCode(0xD800 + (codePt >> 10), 0xDC00 + (codePt & 0x3FF));
	  }
	  return String.fromCharCode(codePt);
	}	
	

	function hex2str($hex){
		
		var $string='';
		for (var $i = 0; $i < $hex.length -1; $i += 2){
			$string += chr(hexdec($hex[$i]+$hex[$i+1]));
		}
		return $string;
	}

	function str2hex(str) {
		
		var hex = '';
		for(var i=0;i<str.length;i++) {
			hex += ''+str.charCodeAt(i).toString(16);
		}
		return hex;
	}	
	

	
	return {
		hex2b64 : hex2b64,
		b64tohex : b64tohex,
		str2hex : str2hex,
		hex2str : hex2str
	};
});