/**
 * RC4 加密解密, 编码成16进制hex, 以及hex解码
*/
;(function(root, factory){
	root.RC4 = factory();
})(this, function(){
	return {
		bin2hex:function(data){
			var b16D='0123456789abcdef';var b16M=new Array();
			for(var i=0;i<256;i++){
				b16M[i]=b16D.charAt(i>>4)+b16D.charAt(i&15);				
			}
			var result=new Array();
			for(var i=0;i<data.length;i++){
				result[i]=b16M[data.charCodeAt(i)];			 	
			}
			return result.join('');
		},
		hex2bin:function(data){
			var b16D='0123456789abcdef';var b16M=new Array();
			for(var i=0;i<256;i++){
				b16M[b16D.charAt(i>>4)+b16D.charAt(i&15)]=String.fromCharCode(i);
			}
			if(!data.match(/^[a-f0-9]*$/i)){
				return false;
			}
			if(data.length%2){
				data='0'+data;
			}
			var result=new Array();
			var j=0;
			for(var i=0;i<data.length;i+=2){
				result[j++]=b16M[data.substr(i,2)];
			}
			return result.join('');
		},
		encrypt:function(key,pt){
			s=new Array();
			for (var i=0;i<256;i++){
				s[i]=i;
			};
			var j=0;var x;
			for (i=0;i<256;i++){
				j=(j+s[i]+key.charCodeAt(i % key.length)) % 256;
				x=s[i];s[i]=s[j];s[j]=x;
			}
			i=0;j=0;
			var ct='';
			for (var y=0;y<pt.length;y++){
				i=(i+1) % 256;j=(j+s[i]) % 256;x=s[i];s[i]=s[j];s[j]=x;
				ct+=String.fromCharCode(pt.charCodeAt(y) ^ s[(s[i]+s[j]) % 256]);
			}
			return ct;
		},
		decrypt:function(key,ct){
			return this.encrypt(key,ct);
		}
	}
});

