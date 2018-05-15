define("app/vj/controller/demo/cryptojs", ["lib/encrypt/jsencrypt","lib/encrypt/hex2b64", "lib/encrypt/unicode", "lib/encrypt/rc4", "lib/encrypt/md5"], function(){
	var _class_ = XDK.Class.create({
		_init_ : function(){
			_class_.baseConstructor.apply(this, arguments);
			this.title = "crypto-js";
			this.publicKey = "";
			this.privateKey = "";
			this.randomKey = this.getRandomKey(128);
			this.md5Key = "VJWEALTH";
		},
		
		indexAction : function(){
			_class_.superClass.indexAction.apply(this, arguments);
			this.setKey(function(){
				this.init();
			});
			
		},
		setKey : function(callback){
			var _this = this;
			$.post("key/public.txt", {}, function(text){
				_this.publicKey = text;
				$.post("key/private.txt", {}, function(text){
					_this.privateKey = text;
					callback.call(_this);
				}, "text");
			}, "text");
		},
		init  : function(){
			var p = Unicode.getUni("1+1=2还是11呢？");
			var enc = this.encrypt(p);
			console.log("编码：", enc);
			var dec = this.pwdDec(enc);
			console.log("解码：", dec);
		},
		encrypt : function(passwd){
			var _this = this;
			var data = {
				sign : "",
				content : "",
				key : ""
			};
			
			//var randomKey = this.getRandomKey(128);
			
			var randomKey = "qdd5iya6416ftz@!0y7gx3pua4zhh^^!3c9bk^q%m%$reajctw88qx3i97l4^egf6x1t!xk$5#jga8l6d41r90v%5nxo%!frh#xl$%7b@augr97r5xx29d$z9$%!i4vx";
			console.log("randomKey", randomKey);
			var md5key = this.md5Key;
			var rasPublicKey = this.publicKey;
			
			//rc4编码->返回bin
			var rc4 = (function(){
				
				var encrypted = RC4.encrypt(randomKey, passwd);
				console.log("rc4 encrypted", encrypted);
				
				var decrypted = RC4.decrypt(randomKey, encrypted);
				console.log("rc4 decrypted", decrypted);
				return encrypted;
			})();
			
			//base16 rc4
			var content = (function(){
				var c = RC4.bin2hex(rc4);
				console.log("content", c);
				return c;
			})();
			
			//md5签名
			var md5sign = (function(){
				var md5Enc = md5(content + md5key);
				console.log("md5sign", md5Enc);
				return md5Enc;
			})();
			
			//RSA-encode 公钥加密
			var rsaEncKey = (function(){
				var encrypt = new JSEncrypt();
				encrypt.setPublicKey(rasPublicKey);
				var encList = [];
				var temp = [];
				var step = 117;
				
				for(var i = 0, l = randomKey.length; i < l; i += step){
					var _k  = randomKey.slice(i, step + i);
					var enc1 = encrypt.encrypt(_k);
					var enc = b64tohex(enc1);
					console.log("enc1.. ", enc1);
					console.log("enc.. ", enc);
					encList.push(enc);
				}
				var _encKey = encList.join("");
				console.log("rsaEncKey base16-hex encrypted", _encKey);
				data.keyList = XDK.core.json.encode(encList);
				data.key = _encKey;
			})();
			
			data.sign = md5sign;
			data.content = content;
		
			return data;
		},
		
		
		
		
		pwdDec :function(data){
			var _this = this;
			var md5key = this.md5Key;
			var content = RC4.hex2bin(data.content);
			console.log("content", content);
			console.log("md5key", md5key);
			
			var sign = md5(data.content + md5key);
			
			var keyList = XDK.core.json.decode(data.keyList);
			var _randomKey = "qdd5iya6416ftz@!0y7gx3pua4zhh^^!3c9bk^q%m%$reajctw88qx3i97l4^egf6x1t!xk$5#jga8l6d41r90v%5nxo%!frh#xl$%7b@augr97r5xx29d$z9$%!i4vx";
			var encrypted = Unicode.parse(RC4.encrypt(_randomKey, content));
			return {
				sign : sign,
				signChecked : sign === data.sign,
				passwd : encrypted,
				randomKey : _randomKey
			};
		},
	
		
		
		getRandomKey : function(len){
			var rs = [];
			var map = [
				"a", "b", "c", "d", "e", "f", 
				"g", "h", "i", "j", "k", "l", 
				"m", "n", "o", "p", "q", "r", 
				"s", "t", "u", "v", "w", "x", 
				"y", "z", "0" ,"1", "2", "3", 
				"4", "5", "6", "7", "8", "9",
				"!", "@", "#", "$", "%", "^"
			];
			for(var i = 0; i < len; i++){
				var index = this.random(0, map.length - 1);
				rs.push(map[index]);
			}
			return rs.join("");
		},
		random : function(a, b){
			var c = b - a + 1;
			return Math.floor(Math.random() * c) + a;
		}
	}, AppController);
	return _class_;
	
});
