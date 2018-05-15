<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0">
              </head>
    
    <body >
       
        
        <script>
            window.onerror = function(err) {
                log('window.onerror: ' + err)
            }

	if(window.android){
          	window.android.rechargeSuccess('${tradeNo}');
	}

        
        /*这段代码是固定的，必须要放到js中*/
        function setupWebViewJavascriptBridge(callback) {
            if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
            if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
            window.WVJBCallbacks = [callback];
            var WVJBIframe = document.createElement('iframe');
            WVJBIframe.style.display = 'none';
            WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
            document.documentElement.appendChild(WVJBIframe);
            setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
        }
        
        /*与OC交互的所有JS方法都要放在此处注册，才能调用通过JS调用OC或者让OC调用这里的JS*/
        setupWebViewJavascriptBridge(function(bridge) {
                                     var uniqueId = 1
                                     function log(message, data) {
                                     var log = document.getElementById('log')
                                     var el = document.createElement('div')
                                     el.className = 'logLine'
                                     el.innerHTML = uniqueId++ + '. ' + message + ':<br/>' + JSON.stringify(data)
                                     if (log.children.length) {
                                     log.insertBefore(el, log.children[0])
                                     } else {
                                     log.appendChild(el)
                                     }
                                     }
                                     /* Initialize your app here */
                                     
                                     /*我们在这注册一个js调用OC的方法，不带参数，且不用ObjC端反馈结果给JS：打开本demo对应的博文*/
                                     // bridge.registerHandler('openWebviewBridgeArticle', function() {
                                     //    log("openWebviewBridgeArticle was called with by ObjC")
                                     // })
                                     // /*JS给ObjC提供公开的API，在ObjC端可以手动调用JS的这个API。接收ObjC传过来的参数，且可以回调ObjC*/
                                     // bridge.registerHandler('getUserInfos', function(data, responseCallback) {
                                     //   log("Get user information from ObjC: ", data)
                                     //   responseCallback({'userId': '123456', 'blog': '标哥的技术博客'})
                                     // })
                                     
                                     // /*JS给ObjC提供公开的API，ObjC端通过注册，就可以在JS端调用此API时，得到回调。ObjC端可以在处理完成后，反馈给JS，这样写就是在载入页面完成时就先调用*/
                                     // bridge.callHandler('getUserIdFromObjC', function(responseData) {
                                     //   log("JS call ObjC's getUserIdFromObjC function, and js received response:", responseData)
                                     // })
                                     
                                     
                                     document.getElementById('blogId').onclick = function (e) {
                                     log('js call objc: getBlogNameFromObjC')
                                     bridge.callHandler('CallHandlerID', {'tradeNo': '${tradeNo}'}, function(response) {
                                                        //log('JS got response', response)
                                                        })
                                     }
                                     })


  setTimeout("myInterval()",500);//1000为1秒钟
       function myInterval()
       {
           loadOC();
        }

	function loadOC(){
		 document.getElementById('blogId').onclick();
//	alert('调用js');
	}

                   //   loadOC();               
            </script>
        
        <div id='buttons'></div> <div id='log'></div>
        
        <div>
            <input type="hidden" value="按钮" id="blogId"/>

            <input type="hidden" value="按钮2" id="blogId2" onclick="loadOC()"/>
        </div>
    </body>
</html>