
/**
 * Underscore.js 1.5.2.1
*/
;(function(){var root=this;var previousUnderscore=root._;var breaker={};var ArrayProto=Array.prototype,ObjProto=Object.prototype,FuncProto=Function.prototype;var push=ArrayProto.push,slice=ArrayProto.slice,concat=ArrayProto.concat,toString=ObjProto.toString,hasOwnProperty=ObjProto.hasOwnProperty;var nativeForEach=ArrayProto.forEach,nativeMap=ArrayProto.map,nativeReduce=ArrayProto.reduce,nativeReduceRight=ArrayProto.reduceRight,nativeFilter=ArrayProto.filter,nativeEvery=ArrayProto.every,nativeSome=ArrayProto.some,nativeIndexOf=ArrayProto.indexOf,nativeLastIndexOf=ArrayProto.lastIndexOf,nativeIsArray=Array.isArray,nativeKeys=Object.keys,nativeBind=FuncProto.bind;var _=function(obj){if(obj instanceof _)return obj;if(!(this instanceof _))return new _(obj);this._wrapped=obj};root._=_;_.VERSION='1.5.2';var each=_.each=_.forEach=function(obj,iterator,context){if(obj==null)return;if(nativeForEach&&obj.forEach===nativeForEach){obj.forEach(iterator,context)}else if(obj.length===+obj.length){for(var i=0,length=obj.length;i<length;i++){if(iterator.call(context,obj[i],i,obj)===breaker)return}}else{var keys=_.keys(obj);for(var i=0,length=keys.length;i<length;i++){if(iterator.call(context,obj[keys[i]],keys[i],obj)===breaker)return}}};_.map=_.collect=function(obj,iterator,context){var results=[];if(obj==null)return results;if(nativeMap&&obj.map===nativeMap)return obj.map(iterator,context);each(obj,function(value,index,list){results.push(iterator.call(context,value,index,list))});return results};var ctor=function(){};_.bind=function(func,context){var args,bound;if(nativeBind&&func.bind===nativeBind)return nativeBind.apply(func,slice.call(arguments,1));if(!_.isFunction(func))throw new TypeError;args=slice.call(arguments,2);return bound=function(){if(!(this instanceof bound))return func.apply(context,args.concat(slice.call(arguments)));ctor.prototype=func.prototype;var self=new ctor;ctor.prototype=null;var result=func.apply(self,args.concat(slice.call(arguments)));if(Object(result)===result)return result;return self}};_.bindAll=function(obj){var funcs=slice.call(arguments,1);if(funcs.length===0)throw new Error("bindAll must be passed function names");each(funcs,function(f){obj[f]=_.bind(obj[f],obj)});return obj};var any=_.some=_.any=function(obj,iterator,context){iterator||(iterator=_.identity);var result=false;if(obj==null)return result;if(nativeSome&&obj.some===nativeSome)return obj.some(iterator,context);each(obj,function(value,index,list){if(result||(result=iterator.call(context,value,index,list)))return breaker});return!!result};_.extend=function(obj){each(slice.call(arguments,1),function(source){if(source){for(var prop in source){obj[prop]=source[prop]}}});return obj};_.once=function(func){var ran=false,memo;return function(){if(ran)return memo;ran=true;memo=func.apply(this,arguments);func=null;return memo}};_.keys=nativeKeys||function(obj){if(obj!==Object(obj))throw new TypeError('Invalid object');var keys=[];for(var key in obj)if(_.has(obj,key))keys.push(key);return keys};_.isEmpty=function(obj){if(obj==null)return true;if(_.isArray(obj)||_.isString(obj))return obj.length===0;for(var key in obj)if(_.has(obj,key))return false;return true};_.isEmpty=function(obj){if(obj==null)return true;if(_.isArray(obj)||_.isString(obj))return obj.length===0;for(var key in obj)if(_.has(obj,key))return false;return true};_.isElement=function(obj){return!!(obj&&obj.nodeType===1)};_.isArray=nativeIsArray||function(obj){return toString.call(obj)=='[object Array]'};_.isObject=function(obj){return obj===Object(obj)};each(['Arguments','Function','String','Number','Date','RegExp'],function(name){_['is'+name]=function(obj){return toString.call(obj)=='[object '+name+']'}});if(!_.isArguments(arguments)){_.isArguments=function(obj){return!!(obj&&_.has(obj,'callee'))}}if(typeof(/./)!=='function'){_.isFunction=function(obj){return typeof obj==='function'}}_.isFinite=function(obj){return isFinite(obj)&&!isNaN(parseFloat(obj))};_.isNaN=function(obj){return _.isNumber(obj)&&obj!=+obj};_.isBoolean=function(obj){return obj===true||obj===false||toString.call(obj)=='[object Boolean]'};_.isNull=function(obj){return obj===null};_.isUndefined=function(obj){return obj===void 0};_.has=function(obj,key){return hasOwnProperty.call(obj,key)};_.invert=function(obj){var result={};var keys=_.keys(obj);for(var i=0,length=keys.length;i<length;i++){result[obj[keys[i]]]=keys[i]}return result};_.defaults=function(obj){each(slice.call(arguments,1),function(source){if(source){for(var prop in source){if(obj[prop]===void 0)obj[prop]=source[prop]}}});return obj};var entityMap={escape:{'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#x27;'}};entityMap.unescape=_.invert(entityMap.escape);var entityRegexes={escape:new RegExp('['+_.keys(entityMap.escape).join('')+']','g'),unescape:new RegExp('('+_.keys(entityMap.unescape).join('|')+')','g')};_.each(['escape','unescape'],function(method){_[method]=function(string){if(string==null)return'';return(''+string).replace(entityRegexes[method],function(match){return entityMap[method][match]})}});_.result=function(object,property){if(object==null)return void 0;var value=object[property];return _.isFunction(value)?value.call(object):value};var idCounter=0;_.uniqueId=function(prefix){var id=++idCounter+'';return prefix?prefix+id:id};_.templateSettings={evaluate:/<%([\s\S]+?)%>/g,interpolate:/<%=([\s\S]+?)%>/g,escape:/<%-([\s\S]+?)%>/g};var noMatch=/(.)^/;var escapes={"'":"'",'\\':'\\','\r':'r','\n':'n','\t':'t','\u2028':'u2028','\u2029':'u2029'};var escaper=/\\|'|\r|\n|\t|\u2028|\u2029/g;_.template=function(text,data,settings){var render;settings=_.defaults({},settings,_.templateSettings);var matcher=new RegExp([(settings.escape||noMatch).source,(settings.interpolate||noMatch).source,(settings.evaluate||noMatch).source].join('|')+'|$','g');var index=0;var source="__p+='";text.replace(matcher,function(match,escape,interpolate,evaluate,offset){source+=text.slice(index,offset).replace(escaper,function(match){return'\\'+escapes[match]});if(escape){source+="'+\n((__t=("+escape+"))==null?'':_.escape(__t))+\n'"}if(interpolate){source+="'+\n((__t=("+interpolate+"))==null?'':__t)+\n'"}if(evaluate){source+="';\n"+evaluate+"\n__p+='"}index=offset+match.length;return match});source+="';\n";if(!settings.variable)source='with(obj||{}){\n'+source+'}\n';source="var __t,__p='',__j=Array.prototype.join,"+"print=function(){__p+=__j.call(arguments,'');};\n"+source+"return __p;\n";try{render=new Function(settings.variable||'obj','_',source)}catch(e){e.source=source;throw e;}if(data)return render(data,_);var template=function(data){return render.call(this,data,_)};template.source='function('+(settings.variable||'obj')+'){\n'+source+'}';return template}}).call(this);

/**
 * Backbone.js 1.1.2.1
*/
;(function(root,_,$){root.Backbone=(function(){function Backbone(){};var previousBackbone=root.Backbone;var array=[];var push=array.push;var slice=array.slice;var splice=array.splice;Backbone.VERSION='1.1.2';Backbone.$=$;Backbone.noConflict=function(){root.Backbone=previousBackbone;return this};var Events=Backbone.Events={on:function(name,callback,context){if(!eventsApi(this,'on',name,[callback,context])||!callback)return this;this._events||(this._events={});var events=this._events[name]||(this._events[name]=[]);events.push({callback:callback,context:context,ctx:context||this});return this},once:function(name,callback,context){if(!eventsApi(this,'once',name,[callback,context])||!callback)return this;var self=this;var once=_.once(function(){self.off(name,once);callback.apply(this,arguments)});once._callback=callback;return this.on(name,once,context)},off:function(name,callback,context){var retain,ev,events,names,i,l,j,k;if(!this._events||!eventsApi(this,'off',name,[callback,context]))return this;if(!name&&!callback&&!context){this._events=void 0;return this}names=name?[name]:_.keys(this._events);for(i=0,l=names.length;i<l;i++){name=names[i];if(events=this._events[name]){this._events[name]=retain=[];if(callback||context){for(j=0,k=events.length;j<k;j++){ev=events[j];if((callback&&callback!==ev.callback&&callback!==ev.callback._callback)||(context&&context!==ev.context)){retain.push(ev)}}}if(!retain.length)delete this._events[name]}}return this},trigger:function(name){if(!this._events)return this;var args=slice.call(arguments,1);if(!eventsApi(this,'trigger',name,args))return this;var events=this._events[name];var allEvents=this._events.all;if(events)triggerEvents(events,args);if(allEvents)triggerEvents(allEvents,arguments);return this},stopListening:function(obj,name,callback){var listeningTo=this._listeningTo;if(!listeningTo)return this;var remove=!name&&!callback;if(!callback&&typeof name==='object')callback=this;if(obj)(listeningTo={})[obj._listenId]=obj;for(var id in listeningTo){obj=listeningTo[id];obj.off(name,callback,this);if(remove||_.isEmpty(obj._events))delete this._listeningTo[id]}return this}};var eventSplitter=/\s+/;var eventsApi=function(obj,action,name,rest){if(!name)return true;if(typeof name==='object'){for(var key in name){obj[action].apply(obj,[key,name[key]].concat(rest))}return false}if(eventSplitter.test(name)){var names=name.split(eventSplitter);for(var i=0,l=names.length;i<l;i++){obj[action].apply(obj,[names[i]].concat(rest))}return false}return true};var triggerEvents=function(events,args){var ev,i=-1,l=events.length,a1=args[0],a2=args[1],a3=args[2];switch(args.length){case 0:while(++i<l)(ev=events[i]).callback.call(ev.ctx);return;case 1:while(++i<l)(ev=events[i]).callback.call(ev.ctx,a1);return;case 2:while(++i<l)(ev=events[i]).callback.call(ev.ctx,a1,a2);return;case 3:while(++i<l)(ev=events[i]).callback.call(ev.ctx,a1,a2,a3);return;default:while(++i<l)(ev=events[i]).callback.apply(ev.ctx,args);return}};var listenMethods={listenTo:'on',listenToOnce:'once'};_.each(listenMethods,function(implementation,method){Events[method]=function(obj,name,callback){var listeningTo=this._listeningTo||(this._listeningTo={});var id=obj._listenId||(obj._listenId=_.uniqueId('l'));listeningTo[id]=obj;if(!callback&&typeof name==='object')callback=this;obj[implementation](name,callback,this);return this}});Events.bind=Events.on;Events.unbind=Events.off;_.extend(Backbone,Events);var Router=Backbone.Router=function(options){options||(options={});if(options.routes)this.routes=options.routes;this._bindRoutes();this.initialize.apply(this,arguments)};var optionalParam=/\((.*?)\)/g;var namedParam=/(\(\?)?:\w+/g;var splatParam=/\*\w+/g;var escapeRegExp=/[\-{}\[\]+?.,\\\^$|#\s]/g;_.extend(Router.prototype,Events,{initialize:function(){},route:function(route,name,callback){if(!_.isRegExp(route))route=this._routeToRegExp(route);if(_.isFunction(name)){callback=name;name=''}if(!callback)callback=this[name];var router=this;Backbone.history.route(route,function(fragment){var args=router._extractParameters(route,fragment);router.execute(callback,args);router.trigger.apply(router,['route:'+name].concat(args));router.trigger('route',name,args);Backbone.history.trigger('route',router,name,args)});return this},execute:function(callback,args){if(callback)callback.apply(this,args)},navigate:function(fragment,options){Backbone.history.navigate(fragment,options);return this},_bindRoutes:function(){if(!this.routes)return;this.routes=_.result(this,'routes');var route,routes=_.keys(this.routes);while((route=routes.pop())!=null){this.route(route,this.routes[route])}},_routeToRegExp:function(route){route=route.replace(escapeRegExp,'\\$&').replace(optionalParam,'(?:$1)?').replace(namedParam,function(match,optional){return optional?match:'([^/?]+)'}).replace(splatParam,'([^?]*?)');return new RegExp('^'+route+'(?:\\?([\\s\\S]*))?$')},_extractParameters:function(route,fragment){var params=route.exec(fragment).slice(1);return _.map(params,function(param,i){if(i===params.length-1)return param||null;return param?param:null})}});var History=Backbone.History=function(){this.handlers=[];_.bindAll(this,'checkUrl');if(typeof window!=='undefined'){this.location=window.location;this.history=window.history}};var routeStripper=/^[#\/]|\s+$/g;var rootStripper=/^\/+|\/+$/g;var isExplorer=/msie [\w.]+/;var trailingSlash=/\/$/;var pathStripper=/#.*$/;History.started=false;_.extend(History.prototype,Events,{interval:50,atRoot:function(){return this.location.pathname.replace(/[^\/]$/,'$&/')===this.root},getHash:function(window){var match=(window||this).location.href.match(/#(.*)$/);return match?match[1]:''},getFragment:function(fragment,forcePushState){if(fragment==null){if(this._hasPushState||!this._wantsHashChange||forcePushState){fragment=decodeURI(this.location.pathname+this.location.search);var root=this.root.replace(trailingSlash,'');if(!fragment.indexOf(root))fragment=fragment.slice(root.length)}else{fragment=this.getHash()}}return fragment.replace(routeStripper,'')},start:function(options){if(History.started)throw new Error("Backbone.history has already been started");History.started=true;this.options=_.extend({root:'/'},this.options,options);this.root=this.options.root;this._wantsHashChange=this.options.hashChange!==false;this._wantsPushState=!!this.options.pushState;this._hasPushState=!!(this.options.pushState&&this.history&&this.history.pushState);var fragment=this.getFragment();var docMode=document.documentMode;var oldIE=(isExplorer.exec(navigator.userAgent.toLowerCase())&&(!docMode||docMode<=7));this.root=('/'+this.root+'/').replace(rootStripper,'/');if(oldIE&&this._wantsHashChange){var frame=Backbone.$('<iframe src="javascript:0" tabindex="-1">');this.iframe=frame.hide().appendTo('body')[0].contentWindow;this.navigate(fragment)}if(this._hasPushState){Backbone.$(window).on('popstate',this.checkUrl)}else if(this._wantsHashChange&&('onhashchange'in window)&&!oldIE){Backbone.$(window).on('hashchange',this.checkUrl)}else if(this._wantsHashChange){this._checkUrlInterval=setInterval(this.checkUrl,this.interval)}this.fragment=fragment;var loc=this.location;if(this._wantsHashChange&&this._wantsPushState){if(!this._hasPushState&&!this.atRoot()){this.fragment=this.getFragment(null,true);this.location.replace(this.root+'#'+this.fragment);return true}else if(this._hasPushState&&this.atRoot()&&loc.hash){this.fragment=this.getHash().replace(routeStripper,'');this.history.replaceState({},document.title,this.root+this.fragment)}}if(!this.options.silent)return this.loadUrl()},stop:function(){Backbone.$(window).off('popstate',this.checkUrl).off('hashchange',this.checkUrl);if(this._checkUrlInterval)clearInterval(this._checkUrlInterval);History.started=false},route:function(route,callback){this.handlers.unshift({route:route,callback:callback})},checkUrl:function(e){var current=this.getFragment();if(current===this.fragment&&this.iframe){current=this.getFragment(this.getHash(this.iframe))}if(current===this.fragment)return false;if(this.iframe)this.navigate(current);this.loadUrl()},loadUrl:function(fragment){fragment=this.fragment=this.getFragment(fragment);return _.any(this.handlers,function(handler){if(handler.route.test(fragment)){handler.callback(fragment);return true}})},navigate:function(fragment,options){if(!History.started)return false;if(!options||options===true)options={trigger:!!options};var url=this.root+(fragment=this.getFragment(fragment||''));fragment=fragment.replace(pathStripper,'');if(this.fragment===fragment)return;this.fragment=fragment;if(fragment===''&&url!=='/')url=url.slice(0,-1);if(this._hasPushState){this.history[options.replace?'replaceState':'pushState']({},document.title,url)}else if(this._wantsHashChange){this._updateHash(this.location,fragment,options.replace);if(this.iframe&&(fragment!==this.getFragment(this.getHash(this.iframe)))){if(!options.replace)this.iframe.document.open().close();this._updateHash(this.iframe.location,fragment,options.replace)}}else{return this.location.assign(url)}if(options.trigger)return this.loadUrl(fragment)},_updateHash:function(location,fragment,replace){if(replace){var href=location.href.replace(/(javascript:|#).*$/,'');location.replace(href+'#'+fragment)}else{location.hash='#'+fragment}}});Backbone.history=new History;var extend=function(protoProps,staticProps){var parent=this;var child;if(protoProps&&_.has(protoProps,'constructor')){child=protoProps.constructor}else{child=function(){return parent.apply(this,arguments)}}_.extend(child,parent,staticProps);var Surrogate=function(){this.constructor=child};Surrogate.prototype=parent.prototype;child.prototype=new Surrogate;if(protoProps)_.extend(child.prototype,protoProps);child.__super__=parent.prototype;return child};Router.extend=extend;return Backbone})()})(this,_,(this.jQuery||this.Zepto||this.ender||this.$));



/*
 RequireJS 2.1.9 Copyright (c) 2010-2012, The Dojo Foundation All Rights Reserved.
 Available via the MIT or new BSD license.
 see: http://github.com/jrburke/requirejs for details
*/
var requirejs,require,define;
(function(Z){function H(b){return"[object Function]"===L.call(b)}function I(b){return"[object Array]"===L.call(b)}function y(b,c){if(b){var e;for(e=0;e<b.length&&(!b[e]||!c(b[e],e,b));e+=1);}}function M(b,c){if(b){var e;for(e=b.length-1;-1<e&&(!b[e]||!c(b[e],e,b));e-=1);}}function t(b,c){return ga.call(b,c)}function l(b,c){return t(b,c)&&b[c]}function F(b,c){for(var e in b)if(t(b,e)&&c(b[e],e))break}function Q(b,c,e,h){c&&F(c,function(c,j){if(e||!t(b,j))h&&"string"!==typeof c?(b[j]||(b[j]={}),Q(b[j],
c,e,h)):b[j]=c});return b}function u(b,c){return function(){return c.apply(b,arguments)}}function aa(b){throw b;}function ba(b){if(!b)return b;var c=Z;y(b.split("."),function(b){c=c[b]});return c}function A(b,c,e,h){c=Error(c+"\nhttp://requirejs.org/docs/errors.html#"+b);c.requireType=b;c.requireModules=h;e&&(c.originalError=e);return c}function ha(b){function c(a,f,b){var d,m,c,g,e,h,j,i=f&&f.split("/");d=i;var n=k.map,p=n&&n["*"];if(a&&"."===a.charAt(0))if(f){d=l(k.pkgs,f)?i=[f]:i.slice(0,i.length-
1);f=a=d.concat(a.split("/"));for(d=0;f[d];d+=1)if(m=f[d],"."===m)f.splice(d,1),d-=1;else if(".."===m)if(1===d&&(".."===f[2]||".."===f[0]))break;else 0<d&&(f.splice(d-1,2),d-=2);d=l(k.pkgs,f=a[0]);a=a.join("/");d&&a===f+"/"+d.main&&(a=f)}else 0===a.indexOf("./")&&(a=a.substring(2));if(b&&n&&(i||p)){f=a.split("/");for(d=f.length;0<d;d-=1){c=f.slice(0,d).join("/");if(i)for(m=i.length;0<m;m-=1)if(b=l(n,i.slice(0,m).join("/")))if(b=l(b,c)){g=b;e=d;break}if(g)break;!h&&(p&&l(p,c))&&(h=l(p,c),j=d)}!g&&
h&&(g=h,e=j);g&&(f.splice(0,e,g),a=f.join("/"))}return a}function e(a){z&&y(document.getElementsByTagName("script"),function(f){if(f.getAttribute("data-requiremodule")===a&&f.getAttribute("data-requirecontext")===i.contextName)return f.parentNode.removeChild(f),!0})}function h(a){var f=l(k.paths,a);if(f&&I(f)&&1<f.length)return f.shift(),i.require.undef(a),i.require([a]),!0}function $(a){var f,b=a?a.indexOf("!"):-1;-1<b&&(f=a.substring(0,b),a=a.substring(b+1,a.length));return[f,a]}function n(a,f,
b,d){var m,B,g=null,e=f?f.name:null,h=a,j=!0,k="";a||(j=!1,a="_@r"+(L+=1));a=$(a);g=a[0];a=a[1];g&&(g=c(g,e,d),B=l(r,g));a&&(g?k=B&&B.normalize?B.normalize(a,function(a){return c(a,e,d)}):c(a,e,d):(k=c(a,e,d),a=$(k),g=a[0],k=a[1],b=!0,m=i.nameToUrl(k)));b=g&&!B&&!b?"_unnormalized"+(M+=1):"";return{prefix:g,name:k,parentMap:f,unnormalized:!!b,url:m,originalName:h,isDefine:j,id:(g?g+"!"+k:k)+b}}function q(a){var f=a.id,b=l(p,f);b||(b=p[f]=new i.Module(a));return b}function s(a,f,b){var d=a.id,m=l(p,
d);if(t(r,d)&&(!m||m.defineEmitComplete))"defined"===f&&b(r[d]);else if(m=q(a),m.error&&"error"===f)b(m.error);else m.on(f,b)}function v(a,f){var b=a.requireModules,d=!1;if(f)f(a);else if(y(b,function(f){if(f=l(p,f))f.error=a,f.events.error&&(d=!0,f.emit("error",a))}),!d)j.onError(a)}function w(){R.length&&(ia.apply(G,[G.length-1,0].concat(R)),R=[])}function x(a){delete p[a];delete T[a]}function E(a,f,b){var d=a.map.id;a.error?a.emit("error",a.error):(f[d]=!0,y(a.depMaps,function(d,c){var g=d.id,
e=l(p,g);e&&(!a.depMatched[c]&&!b[g])&&(l(f,g)?(a.defineDep(c,r[g]),a.check()):E(e,f,b))}),b[d]=!0)}function C(){var a,f,b,d,m=(b=1E3*k.waitSeconds)&&i.startTime+b<(new Date).getTime(),c=[],g=[],j=!1,l=!0;if(!U){U=!0;F(T,function(b){a=b.map;f=a.id;if(b.enabled&&(a.isDefine||g.push(b),!b.error))if(!b.inited&&m)h(f)?j=d=!0:(c.push(f),e(f));else if(!b.inited&&(b.fetched&&a.isDefine)&&(j=!0,!a.prefix))return l=!1});if(m&&c.length)return b=A("timeout","Load timeout for modules: "+c,null,c),b.contextName=
i.contextName,v(b);l&&y(g,function(a){E(a,{},{})});if((!m||d)&&j)if((z||da)&&!V)V=setTimeout(function(){V=0;C()},50);U=!1}}function D(a){t(r,a[0])||q(n(a[0],null,!0)).init(a[1],a[2])}function J(a){var a=a.currentTarget||a.srcElement,b=i.onScriptLoad;a.detachEvent&&!W?a.detachEvent("onreadystatechange",b):a.removeEventListener("load",b,!1);b=i.onScriptError;(!a.detachEvent||W)&&a.removeEventListener("error",b,!1);return{node:a,id:a&&a.getAttribute("data-requiremodule")}}function K(){var a;for(w();G.length;){a=
G.shift();if(null===a[0])return v(A("mismatch","Mismatched anonymous define() module: "+a[a.length-1]));D(a)}}var U,X,i,N,V,k={waitSeconds:7,baseUrl:"./",paths:{},pkgs:{},shim:{},config:{}},p={},T={},Y={},G=[],r={},S={},L=1,M=1;N={require:function(a){return a.require?a.require:a.require=i.makeRequire(a.map)},exports:function(a){a.usingExports=!0;if(a.map.isDefine)return a.exports?a.exports:a.exports=r[a.map.id]={}},module:function(a){return a.module?a.module:a.module={id:a.map.id,uri:a.map.url,config:function(){var b=
l(k.pkgs,a.map.id);return(b?l(k.config,a.map.id+"/"+b.main):l(k.config,a.map.id))||{}},exports:r[a.map.id]}}};X=function(a){this.events=l(Y,a.id)||{};this.map=a;this.shim=l(k.shim,a.id);this.depExports=[];this.depMaps=[];this.depMatched=[];this.pluginMaps={};this.depCount=0};X.prototype={init:function(a,b,c,d){d=d||{};if(!this.inited){this.factory=b;if(c)this.on("error",c);else this.events.error&&(c=u(this,function(a){this.emit("error",a)}));this.depMaps=a&&a.slice(0);this.errback=c;this.inited=!0;
this.ignore=d.ignore;d.enabled||this.enabled?this.enable():this.check()}},defineDep:function(a,b){this.depMatched[a]||(this.depMatched[a]=!0,this.depCount-=1,this.depExports[a]=b)},fetch:function(){if(!this.fetched){this.fetched=!0;i.startTime=(new Date).getTime();var a=this.map;if(this.shim)i.makeRequire(this.map,{enableBuildCallback:!0})(this.shim.deps||[],u(this,function(){return a.prefix?this.callPlugin():this.load()}));else return a.prefix?this.callPlugin():this.load()}},load:function(){var a=
this.map.url;S[a]||(S[a]=!0,i.load(this.map.id,a))},check:function(){if(this.enabled&&!this.enabling){var a,b,c=this.map.id;b=this.depExports;var d=this.exports,m=this.factory;if(this.inited)if(this.error)this.emit("error",this.error);else{if(!this.defining){this.defining=!0;if(1>this.depCount&&!this.defined){if(H(m)){if(this.events.error&&this.map.isDefine||j.onError!==aa)try{d=i.execCb(c,m,b,d)}catch(e){a=e}else d=i.execCb(c,m,b,d);this.map.isDefine&&((b=this.module)&&void 0!==b.exports&&b.exports!==
this.exports?d=b.exports:void 0===d&&this.usingExports&&(d=this.exports));if(a)return a.requireMap=this.map,a.requireModules=this.map.isDefine?[this.map.id]:null,a.requireType=this.map.isDefine?"define":"require",v(this.error=a)}else d=m;this.exports=d;if(this.map.isDefine&&!this.ignore&&(r[c]=d,j.onResourceLoad))j.onResourceLoad(i,this.map,this.depMaps);x(c);this.defined=!0}this.defining=!1;this.defined&&!this.defineEmitted&&(this.defineEmitted=!0,this.emit("defined",this.exports),this.defineEmitComplete=
!0)}}else this.fetch()}},callPlugin:function(){var a=this.map,b=a.id,e=n(a.prefix);this.depMaps.push(e);s(e,"defined",u(this,function(d){var m,e;e=this.map.name;var g=this.map.parentMap?this.map.parentMap.name:null,h=i.makeRequire(a.parentMap,{enableBuildCallback:!0});if(this.map.unnormalized){if(d.normalize&&(e=d.normalize(e,function(a){return c(a,g,!0)})||""),d=n(a.prefix+"!"+e,this.map.parentMap),s(d,"defined",u(this,function(a){this.init([],function(){return a},null,{enabled:!0,ignore:!0})})),
e=l(p,d.id)){this.depMaps.push(d);if(this.events.error)e.on("error",u(this,function(a){this.emit("error",a)}));e.enable()}}else m=u(this,function(a){this.init([],function(){return a},null,{enabled:!0})}),m.error=u(this,function(a){this.inited=!0;this.error=a;a.requireModules=[b];F(p,function(a){0===a.map.id.indexOf(b+"_unnormalized")&&x(a.map.id)});v(a)}),m.fromText=u(this,function(d,c){var e=a.name,g=n(e),B=O;c&&(d=c);B&&(O=!1);q(g);t(k.config,b)&&(k.config[e]=k.config[b]);try{j.exec(d)}catch(ca){return v(A("fromtexteval",
"fromText eval for "+b+" failed: "+ca,ca,[b]))}B&&(O=!0);this.depMaps.push(g);i.completeLoad(e);h([e],m)}),d.load(a.name,h,m,k)}));i.enable(e,this);this.pluginMaps[e.id]=e},enable:function(){T[this.map.id]=this;this.enabling=this.enabled=!0;y(this.depMaps,u(this,function(a,b){var c,d;if("string"===typeof a){a=n(a,this.map.isDefine?this.map:this.map.parentMap,!1,!this.skipMap);this.depMaps[b]=a;if(c=l(N,a.id)){this.depExports[b]=c(this);return}this.depCount+=1;s(a,"defined",u(this,function(a){this.defineDep(b,
a);this.check()}));this.errback&&s(a,"error",u(this,this.errback))}c=a.id;d=p[c];!t(N,c)&&(d&&!d.enabled)&&i.enable(a,this)}));F(this.pluginMaps,u(this,function(a){var b=l(p,a.id);b&&!b.enabled&&i.enable(a,this)}));this.enabling=!1;this.check()},on:function(a,b){var c=this.events[a];c||(c=this.events[a]=[]);c.push(b)},emit:function(a,b){y(this.events[a],function(a){a(b)});"error"===a&&delete this.events[a]}};i={config:k,contextName:b,registry:p,defined:r,urlFetched:S,defQueue:G,Module:X,makeModuleMap:n,
nextTick:j.nextTick,onError:v,configure:function(a){a.baseUrl&&"/"!==a.baseUrl.charAt(a.baseUrl.length-1)&&(a.baseUrl+="/");var b=k.pkgs,c=k.shim,d={paths:!0,config:!0,map:!0};F(a,function(a,b){d[b]?"map"===b?(k.map||(k.map={}),Q(k[b],a,!0,!0)):Q(k[b],a,!0):k[b]=a});a.shim&&(F(a.shim,function(a,b){I(a)&&(a={deps:a});if((a.exports||a.init)&&!a.exportsFn)a.exportsFn=i.makeShimExports(a);c[b]=a}),k.shim=c);a.packages&&(y(a.packages,function(a){a="string"===typeof a?{name:a}:a;b[a.name]={name:a.name,
location:a.location||a.name,main:(a.main||"main").replace(ja,"").replace(ea,"")}}),k.pkgs=b);F(p,function(a,b){!a.inited&&!a.map.unnormalized&&(a.map=n(b))});if(a.deps||a.callback)i.require(a.deps||[],a.callback)},makeShimExports:function(a){return function(){var b;a.init&&(b=a.init.apply(Z,arguments));return b||a.exports&&ba(a.exports)}},makeRequire:function(a,f){function h(d,c,e){var g,k;f.enableBuildCallback&&(c&&H(c))&&(c.__requireJsBuild=!0);if("string"===typeof d){if(H(c))return v(A("requireargs",
"Invalid require call"),e);if(a&&t(N,d))return N[d](p[a.id]);if(j.get)return j.get(i,d,a,h);g=n(d,a,!1,!0);g=g.id;return!t(r,g)?v(A("notloaded",'Module name "'+g+'" has not been loaded yet for context: '+b+(a?"":". Use require([])"))):r[g]}K();i.nextTick(function(){K();k=q(n(null,a));k.skipMap=f.skipMap;k.init(d,c,e,{enabled:!0});C()});return h}f=f||{};Q(h,{isBrowser:z,toUrl:function(b){var f,e=b.lastIndexOf("."),g=b.split("/")[0];if(-1!==e&&(!("."===g||".."===g)||1<e))f=b.substring(e,b.length),b=
b.substring(0,e);return i.nameToUrl(c(b,a&&a.id,!0),f,!0)},defined:function(b){return t(r,n(b,a,!1,!0).id)},specified:function(b){b=n(b,a,!1,!0).id;return t(r,b)||t(p,b)}});a||(h.undef=function(b){w();var c=n(b,a,!0),f=l(p,b);e(b);delete r[b];delete S[c.url];delete Y[b];f&&(f.events.defined&&(Y[b]=f.events),x(b))});return h},enable:function(a){l(p,a.id)&&q(a).enable()},completeLoad:function(a){var b,c,d=l(k.shim,a)||{},e=d.exports;for(w();G.length;){c=G.shift();if(null===c[0]){c[0]=a;if(b)break;b=
!0}else c[0]===a&&(b=!0);D(c)}c=l(p,a);if(!b&&!t(r,a)&&c&&!c.inited){if(k.enforceDefine&&(!e||!ba(e)))return h(a)?void 0:v(A("nodefine","No define call for "+a,null,[a]));D([a,d.deps||[],d.exportsFn])}C()},nameToUrl:function(a,b,c){var d,e,h,g,i,n;if(j.jsExtRegExp.test(a))g=a+(b||"");else{d=k.paths;e=k.pkgs;g=a.split("/");for(i=g.length;0<i;i-=1)if(n=g.slice(0,i).join("/"),h=l(e,n),n=l(d,n)){I(n)&&(n=n[0]);g.splice(0,i,n);break}else if(h){a=a===h.name?h.location+"/"+h.main:h.location;g.splice(0,i,
a);break}g=g.join("/");g+=b||(/^data\:|\?/.test(g)||c?"":".js");g=("/"===g.charAt(0)||g.match(/^[\w\+\.\-]+:/)?"":k.baseUrl)+g}return k.urlArgs?g+((-1===g.indexOf("?")?"?":"&")+k.urlArgs):g},load:function(a,b){j.load(i,a,b)},execCb:function(a,b,c,d){return b.apply(d,c)},onScriptLoad:function(a){if("load"===a.type||ka.test((a.currentTarget||a.srcElement).readyState))P=null,a=J(a),i.completeLoad(a.id)},onScriptError:function(a){var b=J(a);if(!h(b.id))return v(A("scripterror","Script error for: "+b.id,
a,[b.id]))}};i.require=i.makeRequire();return i}var j,w,x,C,J,D,P,K,q,fa,la=/(\/\*([\s\S]*?)\*\/|([^:]|^)\/\/(.*)$)/mg,ma=/[^.]\s*require\s*\(\s*["']([^'"\s]+)["']\s*\)/g,ea=/\.js$/,ja=/^\.\//;w=Object.prototype;var L=w.toString,ga=w.hasOwnProperty,ia=Array.prototype.splice,z=!!("undefined"!==typeof window&&"undefined"!==typeof navigator&&window.document),da=!z&&"undefined"!==typeof importScripts,ka=z&&"PLAYSTATION 3"===navigator.platform?/^complete$/:/^(complete|loaded)$/,W="undefined"!==typeof opera&&
"[object Opera]"===opera.toString(),E={},s={},R=[],O=!1;if("undefined"===typeof define){if("undefined"!==typeof requirejs){if(H(requirejs))return;s=requirejs;requirejs=void 0}"undefined"!==typeof require&&!H(require)&&(s=require,require=void 0);j=requirejs=function(b,c,e,h){var q,n="_";!I(b)&&"string"!==typeof b&&(q=b,I(c)?(b=c,c=e,e=h):b=[]);q&&q.context&&(n=q.context);(h=l(E,n))||(h=E[n]=j.s.newContext(n));q&&h.configure(q);return h.require(b,c,e)};j.config=function(b){return j(b)};j.nextTick="undefined"!==
typeof setTimeout?function(b){setTimeout(b,4)}:function(b){b()};require||(require=j);j.version="2.1.9";j.jsExtRegExp=/^\/|:|\?|\.js$/;j.isBrowser=z;w=j.s={contexts:E,newContext:ha};j({});y(["toUrl","undef","defined","specified"],function(b){j[b]=function(){var c=E._;return c.require[b].apply(c,arguments)}});if(z&&(x=w.head=document.getElementsByTagName("head")[0],C=document.getElementsByTagName("base")[0]))x=w.head=C.parentNode;j.onError=aa;j.createNode=function(b){var c=b.xhtml?document.createElementNS("http://www.w3.org/1999/xhtml",
"html:script"):document.createElement("script");c.type=b.scriptType||"text/javascript";c.charset="utf-8";c.async=!0;return c};j.load=function(b,c,e){var h=b&&b.config||{};if(z)return h=j.createNode(h,c,e),h.setAttribute("data-requirecontext",b.contextName),h.setAttribute("data-requiremodule",c),h.attachEvent&&!(h.attachEvent.toString&&0>h.attachEvent.toString().indexOf("[native code"))&&!W?(O=!0,h.attachEvent("onreadystatechange",b.onScriptLoad)):(h.addEventListener("load",b.onScriptLoad,!1),h.addEventListener("error",
b.onScriptError,!1)),h.src=e,K=h,C?x.insertBefore(h,C):x.appendChild(h),K=null,h;if(da)try{importScripts(e),b.completeLoad(c)}catch(l){b.onError(A("importscripts","importScripts failed for "+c+" at "+e,l,[c]))}};z&&!s.skipDataMain&&M(document.getElementsByTagName("script"),function(b){x||(x=b.parentNode);if(J=b.getAttribute("data-main"))return q=J,s.baseUrl||(D=q.split("/"),q=D.pop(),fa=D.length?D.join("/")+"/":"./",s.baseUrl=fa),q=q.replace(ea,""),j.jsExtRegExp.test(q)&&(q=J),s.deps=s.deps?s.deps.concat(q):
[q],!0});define=function(b,c,e){var h,j;"string"!==typeof b&&(e=c,c=b,b=null);I(c)||(e=c,c=null);!c&&H(e)&&(c=[],e.length&&(e.toString().replace(la,"").replace(ma,function(b,e){c.push(e)}),c=(1===e.length?["require"]:["require","exports","module"]).concat(c)));if(O){if(!(h=K))P&&"interactive"===P.readyState||M(document.getElementsByTagName("script"),function(b){if("interactive"===b.readyState)return P=b}),h=P;h&&(b||(b=h.getAttribute("data-requiremodule")),j=E[h.getAttribute("data-requirecontext")])}(j?
j.defQueue:R).push([b,c,e])};define.amd={jQuery:!0};j.exec=function(b){return eval(b)};j(s)}})(this);

/**
 * @license RequireJS Image Plugin <https://gist.github.com/821476>
 * @author Miller Medeiros
 * @version 0.0.4 (2010/02/14)
 * Released under the MIT License <http://www.opensource.org/licenses/mit-license.php>
 */
(function(){
    
    var CACHE_BUST_QUERY_PARAM = 'bust',
        CACHE_BUST_FLAG = '!bust';
    
    function cacheBust(url){
        url = url.replace(CACHE_BUST_FLAG, '');
        url += (url.indexOf('?') < 0)? '?' : '&';
        return url + CACHE_BUST_QUERY_PARAM +'='+ (new Date()).getTime();
    }
    
    //as of RequireJS 0.22 - define method for plugins needs to be a literal object
    //to be able to work together with the optimizer (see: https://github.com/jrburke/requirejs/issues#issue/70)
    define("js/lib/require-image", {
        load : function(name, req, onLoad, config){
            var img;
            if(config.isBuild){
                onLoad(null); //avoid errors on the optimizer since it can't inline image files.
            }else{
                img = new Image();
                img.onload = function(evt){
                    onLoad(img);
					try{	
						//release memory - suggested by John Hann
						delete img.onload;
					}catch(e){
					};
					
                };
				img.onerror = function(){
					if (onLoad.error) {
						onLoad.error("load img errror");
					}
					
					try{	
						delete img.onerror;
					}catch(e){
					};
				}
                img.src = req.toUrl(name);
            }
        },
        normalize : function (name, normalize) {
            //used normalize to avoid caching references to a "cache busted" request.
            return (name.indexOf(CACHE_BUST_FLAG) < 0)? name : cacheBust(name); 
        }
    });
    
}());


/**
* @license RequireJS text 2.0.10 Copyright (c) 2010-2012, The Dojo Foundation All Rights Reserved.
* Available via the MIT or new BSD license.
* see: http://github.com/requirejs/text for details
*/
define("js/lib/text-v2.0.10", ['module'],function(module){'use strict';var text,fs,Cc,Ci,xpcIsWindows,progIds=['Msxml2.XMLHTTP','Microsoft.XMLHTTP','Msxml2.XMLHTTP.4.0'],xmlRegExp=/^\s*<\?xml(\s)+version=[\'\"](\d)*.(\d)*[\'\"](\s)*\?>/im,bodyRegExp=/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im,hasLocation=typeof location!=='undefined'&&location.href,defaultProtocol=hasLocation&&location.protocol&&location.protocol.replace(/\:/,''),defaultHostName=hasLocation&&location.hostname,defaultPort=hasLocation&&(location.port||undefined),buildMap={},masterConfig=(module.config&&module.config())||{};function getJquery(callback,error_callback){callback=callback||function(){};if(window.jQuery){callback(jQuery)}else{error_callback()}};text={version:'2.0.10',strip:function(content){if(content){content=content.replace(xmlRegExp,"");var matches=content.match(bodyRegExp);if(matches){content=matches[1]}}else{content=""}return content},jsEscape:function(content){return content.replace(/(['\\])/g,'\\$1').replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r").replace(/[\u2028]/g,"\\u2028").replace(/[\u2029]/g,"\\u2029")},createXhr:masterConfig.createXhr||function(){var xhr,i,progId;if(typeof XMLHttpRequest!=="undefined"){return new XMLHttpRequest()}else if(typeof ActiveXObject!=="undefined"){for(i=0;i<3;i+=1){progId=progIds[i];try{xhr=new ActiveXObject(progId)}catch(e){}if(xhr){progIds=[progId];break}}}return xhr},parseName:function(name){var modName,ext,temp,strip=false,index=name.indexOf("."),isRelative=name.indexOf('./')===0||name.indexOf('../')===0;if(index!==-1&&(!isRelative||index>1)){modName=name.substring(0,index);ext=name.substring(index+1,name.length)}else{modName=name}temp=ext||modName;index=temp.indexOf("!");if(index!==-1){strip=temp.substring(index+1)==="strip";temp=temp.substring(0,index);if(ext){ext=temp}else{modName=temp}}return{moduleName:modName,ext:ext,strip:strip}},xdRegExp:/^((\w+)\:)?\/\/([^\/\\]+)/,useXhr:function(url,protocol,hostname,port){var uProtocol,uHostName,uPort,match=text.xdRegExp.exec(url);if(!match){return true}uProtocol=match[2];uHostName=match[3];uHostName=uHostName.split(':');uPort=uHostName[1];uHostName=uHostName[0];return(!uProtocol||uProtocol===protocol)&&(!uHostName||uHostName.toLowerCase()===hostname.toLowerCase())&&((!uPort&&!uHostName)||uPort===port)},finishLoad:function(name,strip,content,onLoad){content=strip?text.strip(content):content;if(masterConfig.isBuild){buildMap[name]=content}onLoad(content)},load:function(name,req,onLoad,config){if(config.isBuild&&!config.inlineText){onLoad();return}masterConfig.isBuild=config.isBuild;var parsed=text.parseName(name),nonStripName=parsed.moduleName+(parsed.ext?'.'+parsed.ext:''),url=req.toUrl(nonStripName),useXhr=(masterConfig.useXhr)||text.useXhr;if(url.indexOf('empty:')===0){onLoad();return}if(!hasLocation||useXhr(url,defaultProtocol,defaultHostName,defaultPort)){getJquery(function($){$.ajax({url:url,type:"get",data:{},success:function(content){text.finishLoad(name,parsed.strip,content,onLoad)},error:function(jqXHR,textStatus,errorThrown){if(onLoad.error){onLoad.error(textStatus+","+errorThrown)}},dataType:"text"})},function(){text.get(url,function(content){text.finishLoad(name,parsed.strip,content,onLoad)},function(err){if(onLoad.error){onLoad.error(err)}})})}else{req([nonStripName],function(content){text.finishLoad(parsed.moduleName+'.'+parsed.ext,parsed.strip,content,onLoad)})}},write:function(pluginName,moduleName,write,config){if(buildMap.hasOwnProperty(moduleName)){var content=text.jsEscape(buildMap[moduleName]);write.asModule(pluginName+"!"+moduleName,"define(function () { return '"+content+"';});\n")}},writeFile:function(pluginName,moduleName,req,write,config){var parsed=text.parseName(moduleName),extPart=parsed.ext?'.'+parsed.ext:'',nonStripName=parsed.moduleName+extPart,fileName=req.toUrl(parsed.moduleName+extPart)+'.js';text.load(nonStripName,req,function(value){var textWrite=function(contents){return write(fileName,contents)};textWrite.asModule=function(moduleName,contents){return write.asModule(moduleName,fileName,contents)};text.write(pluginName,nonStripName,textWrite,config)},config)}};if(masterConfig.env==='node'||(!masterConfig.env&&typeof process!=="undefined"&&process.versions&&!!process.versions.node&&!process.versions['node-webkit'])){fs=require.nodeRequire('fs');text.get=function(url,callback,errback){try{var file=fs.readFileSync(url,'utf8');if(file.indexOf('\uFEFF')===0){file=file.substring(1)}callback(file)}catch(e){errback(e)}}}else if(masterConfig.env==='xhr'||(!masterConfig.env&&text.createXhr())){text.get=function(url,callback,errback,headers){var xhr=text.createXhr(),header;xhr.open('GET',url,true);if(headers){for(header in headers){if(headers.hasOwnProperty(header)){xhr.setRequestHeader(header.toLowerCase(),headers[header])}}}if(masterConfig.onXhr){masterConfig.onXhr(xhr,url)}xhr.onreadystatechange=function(evt){var status,err;if(xhr.readyState===4){status=xhr.status;if(status>399&&status<600){err=new Error(url+' HTTP status: '+status);err.xhr=xhr;errback(err)}else{callback(xhr.responseText)}if(masterConfig.onXhrComplete){masterConfig.onXhrComplete(xhr,url)}}};xhr.send(null)}}else if(masterConfig.env==='rhino'||(!masterConfig.env&&typeof Packages!=='undefined'&&typeof java!=='undefined')){text.get=function(url,callback){var stringBuffer,line,encoding="utf-8",file=new java.io.File(url),lineSeparator=java.lang.System.getProperty("line.separator"),input=new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file),encoding)),content='';try{stringBuffer=new java.lang.StringBuffer();line=input.readLine();if(line&&line.length()&&line.charAt(0)===0xfeff){line=line.substring(1)}if(line!==null){stringBuffer.append(line)}while((line=input.readLine())!==null){stringBuffer.append(lineSeparator);stringBuffer.append(line)}content=String(stringBuffer.toString())}finally{input.close()}callback(content)}}else if(masterConfig.env==='xpconnect'||(!masterConfig.env&&typeof Components!=='undefined'&&Components.classes&&Components.interfaces)){Cc=Components.classes,Ci=Components.interfaces;Components.utils['import']('resource://gre/modules/FileUtils.jsm');xpcIsWindows=('@mozilla.org/windows-registry-key;1'in Cc);text.get=function(url,callback){var inStream,convertStream,fileObj,readData={};if(xpcIsWindows){url=url.replace(/\//g,'\\')}fileObj=new FileUtils.File(url);try{inStream=Cc['@mozilla.org/network/file-input-stream;1'].createInstance(Ci.nsIFileInputStream);inStream.init(fileObj,1,0,false);convertStream=Cc['@mozilla.org/intl/converter-input-stream;1'].createInstance(Ci.nsIConverterInputStream);convertStream.init(inStream,"utf-8",inStream.available(),Ci.nsIConverterInputStream.DEFAULT_REPLACEMENT_CHARACTER);convertStream.readString(inStream.available(),readData);convertStream.close();inStream.close();callback(readData.value)}catch(e){throw new Error((fileObj&&fileObj.path||'')+': '+e);}}}return text});

/**
 * 初始化fastclick
*/
(function(window, $, undefined){
	$(function(){
		
		var notNeed = FastClick.notNeeded(document.body);
		$.fn.triggerFastClick = function(){
			this.trigger("click");
			if(!notNeed){
				this.trigger("click");
			}
		};
		
		FastClick.attach(document.body);
	});
})(window, jQuery);

/**
 * 前端工具库 - XDK
*/
;(function(window, undefined) {
	var ns_str = "XDK";
	if (typeof(window[ns_str]) != "undefined") {
		return;
	};
	if(!window.console){
		window.console = {
			log : function(a){}	
		};	
	};
	var opt = Object.prototype.toString;
	var XDK = (function() {
		var J = {};
		window[ns_str] = J;
		var K = [];
		J.inc = function(M, L) {
			return true
		};
		J.register = function(N, L) {
			this.ns(ns_str + "." + N, L);
		};
		J.reg = J.register;
		J.ns = function(nsStr, closure) {
			var arr_ns = nsStr.split(".");
			var l = arr_ns.length;
			if (l == 0) {
				return
			};
			var root = arr_ns[0];
			if (typeof(window[root]) == "undefined") {
				window[root] = l == 1 ? closure(J) : {}
			};
			var rootObj = window[root];
			if (l == 1) {
				return
			};
			var currentNs = root;
			for (var i = 1; i < l; i++) {
				var _ns = arr_ns[i];
				
				if (i + 1 < l) {
					if (typeof(rootObj[_ns]) == "undefined") {
						rootObj[_ns] = {};
					};
				} else {
					if (typeof(rootObj[_ns]) == "undefined") {
						rootObj[_ns] = closure(J)
					};
				};
				rootObj = rootObj[_ns];
				currentNs += "." + _ns;
				
				 
			};
			
		};
		J.regShort = function(L, M) {
			if (J[L] !== undefined) {
				throw "[" + L + "] : short : has been register"
			}
			J[L] = M
		};
		J.IE = /msie/i.test(navigator.userAgent);
		J.E = function(L) {
			if (typeof L === "string") {
				return document.getElementById(L)
			} else {
				return L
			}
		};
		J.C = function(L) {
			var M;
			L = L.toUpperCase();
			if (L == "TEXT") {
				M = document.createTextNode("")
			} else {
				if (L == "BUFFER") {
					M = document.createDocumentFragment()
				} else {
					M = document.createElement(L)
				}
			}
			return M
		};
		J.getType = function(a){
			var ret;
			var l = arguments.length;
			if (l === 0 || typeof(a) === "undefined") {
				ret = "undefined"
			} else {
				ret = (a === null) ? "null": opt.call(a)
			};
			return ret
		};
		
		//对数组或对象 遍历
		J.each = function(a, fn) {
			a = a || null;
			fn = fn || function() {};
			if (a === null) {
				return
			};
			if (opt.call(a) === "[object Array]") {
				var i = 0,
				l = a.length;
				for (; i < l; i++) {
					var rs = fn.call(a[i], i, a[i]);
					if(rs === false){
						break;
					};
				}
			} else {
				if (a.length > 0) {
					var i = 0,
					l = a.length;
					for (; i < l; i++) {
						var rs = fn.call(a[i], i, a[i]);
						if(rs === false){
							break;
						};
					}
				} else {
					for (var key in a) {
						var rs = fn.call(a[key], key, a[key]);
						if(rs === false){
							break;
						};
					}
				}
			}
		};
		
		J.type = {
			is_array : function(a){
				return J.getType(a) === "[object Array]";
			},
			is_string : function(a){
				return J.getType(a) === "[object String]";
			},
			is_number : function(a){
				return J.getType(a) === "[object Number]";
			},
			is_object : function(a){
				return J.getType(a) === "[object Object]";
			},
			is_function : function(a){
				return J.getType(a) === "[object Function]";
			},
			is_null : function(a){
				return J.getType(a) === "null";
			},
			is_undefined : function(a){
				 return J.getType(a) === "undefined";
			}
		};
		
		J.extend = function(a, b){
			var ret = {};
			var argLen = arguments.length;
			if (argLen === 1) {
				ret = a
			};
			if (argLen === 2) {
				var _index = 0;
				if (J.type.is_object(b)) {
					J.each(b, 
					function() {
						_index++;
					})
				};
				if (_index === 0) {
					ret = a;
				};
				if (_index > 0) {
					for(var key in a){
						ret[key] = a[key];
					};
					for(var key in b){
						ret[key] = b[key];
					};
				};
			};
			return ret
		};
		
		J.widget = {};
		return J;
	})();
	
	
	XDK.register("core.obj.len", function(J) {
		return function(obj){
			var len = 0;
			J.each(obj, function(key, value){
				len += 1;
			});
			return len;
		};
	});
	
	XDK.register("core.str.trim", function(J) {
		return function(N) {
			if (typeof N !== "string") {
				throw "trim need a string as parameter"
			}
			var K = N.length;
			var M = 0;
			var L = /(\u3000|\s|\t|\u00A0)/;
			while (M < K) {
				if (!L.test(N.charAt(M))) {
					break
				}
				M += 1
			}
			while (K > M) {
				if (!L.test(N.charAt(K - 1))) {
					break
				}
				K -= 1
			}
			return N.slice(M, K)
		}
	});
	
	XDK.register("core.str.len", function(J) {
		return function(s){
			return s.replace(/[^\x00-\xff]/g, "ss").length;
		};
	});
	
	XDK.register("core.str.guid", function(J) {
		return function(){
			var guid = "";
			for (var i = 1; i <= 32; i++){
			  var n = Math.floor(Math.random()*16.0).toString(16);
			  guid +=   n;
			  if((i==8)||(i==12)||(i==16)||(i==20))
				guid += "-";
			}
			return guid;    
		};
	});
	
	XDK.register("core.str.subString", function(J){
		return function(str, len, hasDot){
			var newLength = 0; 
			var newStr = ""; 
			var chineseRegex = /[^\x00-\xff]/g; 
			var singleChar = ""; 
			var strLength = str.replace(chineseRegex,"**").length; 
			for(var i = 0;i < strLength;i++) 
			{ 
				singleChar = str.charAt(i).toString(); 
				if(singleChar.match(chineseRegex) != null) 
				{ 
					newLength += 2; 
				}     
				else 
				{ 
					newLength++; 
				} 
				if(newLength > len) 
				{ 
					break; 
				} 
				newStr += singleChar; 
			} 
			 
			if(hasDot && strLength > len) 
			{ 
				newStr += "..."; 
			} 
			return newStr; 
		};
	});
	
	XDK.register("core.str.getParam", function(J){
		var query = window.location.search != "" ? window.location.search.slice(1) : "";
		var GET = {};
		(function(){
			if(query == ""){
				return;
			};
			var arr = query.split(/&/);
			J.each(arr, function(i, v){
				if(v != ""){
					var o = v.split(/=/);
					if(o.length == 2){
						var key = o[0];
						var value = o[1];
						GET[key] = value;
					}else if(o.length == 1){
						var key = o[0];
						GET[key] = undefined;
					};
				};
			});
		})();
		return function(param){
			return typeof(GET[param]) != "undefined" ? GET[param] : undefined;
		};
	});
	
	
	//路由封装（获取域名）
	XDK.register("core.router", function(J) {
		return {
			//获取顶级域名
			getDomain: function(domain) {
				domain = domain || window.location.host;
				var pat = /^www\.(\.+)/;
				var _match = domain.match(pat);
				if (_match !== null) {
					return _match[1]
				};
				var ret = domain;
				var ext = ["com\.cn", "net\.cn", "org\.cn", "gov\.cn", "com\.hk", "com", "net", "org", "int", "edu", "gov", "mil", "arpa", "Asia", "biz", "info", "name", "pro", "coop", "aero", "museum", "ac", "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cq", "cr", "cu", "cv", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "es", "et", "ev", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gh", "gi", "gl", "gm", "gn", "gp", "gr", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "in", "io", "iq", "ir", "is", "it", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", "mg", "mh", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nt", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "pt", "pw", "py", "qa", "re", "ro", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st", "su", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tm", "tn", "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "us", "uy", "va", "vc", "ve", "vg", "vn", "vu", "wf", "ws", "ye", "yu", "za", "zm", "zr", "zw"];
				var i = 0,
					l = ext.length;
				for (; i < l; i++) {
					var val = ext[i];
					var expObj = new RegExp("^((([\\w\\-])+\\.)+)(" + val + ")((\\:\\d{1,})){0,1}$", "");
					 
					var _tempMatch = domain.match(expObj);
					var test = expObj.test(domain);
					if (_tempMatch != null) {
						if (_tempMatch.length === 7) {
							var domainExt = _tempMatch[4];
							var preFix = _tempMatch[1].replace(/(\.+)$/g, "");
							var preFixArr = preFix.split(".").reverse();
							ret = preFixArr[0] + "." + domainExt;
							break
						}
					}
				};
				return ret
			},
			//获取带http://的顶级域名
			getDomainByHttp : function(addSplit){
				addSplit = arguments.length > 0 ? addSplit : false;
				return "http://" + this.getDomain() + (addSplit ? "/" : "");
			},
			getParam : function(param){
				return this.params()[param];
			},
			
			params : function(url){
				var GET = {}; 
				var query = "";
				var defaultQuery = window.location.search;
				var queryArr = [];
				if(url){
					var o = url.split("?");
					if(o.length ==  2){
						query = "?" + o[1];
					};
				};
				if(url){
					if(query != ""){
						queryArr = query.slice(1).split("&");
					};
				}else{
					queryArr = defaultQuery ? defaultQuery.slice(1).split("&") : [];
				};
				var i = 0, l = queryArr.length;
				for(; i < l; i++){
					var arr = queryArr[i].split("=");
					var key = arr[0];
					var val = arr[1];
					GET[key] = val;
				};
				return GET;
			},
			
			paramsLen : function(){
				var ret = 0, params = this.params();
				for(var key in params){
					ret++;
				};
				return ret;
			},
			
			getURL : function(url, param_data){
				param_data = param_data || {};
				var preFixParam = {};
				var url_explode = url.split("?");
				var _url = url_explode.length > 1 ? url_explode[0] : url; 
				if(url_explode.length > 1){
					var _params = url_explode[1];
					var _arr = _params.split("&");
					J.each(_arr, function(i, v){
						var _o = v.split("=");
						preFixParam[_o[0]] = _o[1];
					});
				};
				J.each(preFixParam, function(k, v){
					if(typeof(param_data[k]) == "undefined"){
						param_data[k] = v;
					};
				});
				
				var params = "";
				J.each(param_data, function(key, value){
					params += key + "=" + value + "&";
				});
				if(params != ""){
					params = params.slice(0, -1);
				};
				
				return (params != "") ? (_url + "?" + params) :  _url;
			}
		};
		
		
	});
	
	//json封装	
	XDK.register("core.json", function(J){
		return (function(){
			function f(n) {
				return n < 10 ? '0' + n : n
			}
			 
			var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
				escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
				gap, indent, meta = {
					'\b': '\\b',
					'\t': '\\t',
					'\n': '\\n',
					'\f': '\\f',
					'\r': '\\r',
					'"': '\\"',
					'\\': '\\\\'
				},
				rep;

			function quote(string) {
				escapable.lastIndex = 0;
				return escapable.test(string) ? '"' + string.replace(escapable, function(a) {
					var c = meta[a];
					return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4)
				}) + '"' : '"' + string + '"'
			}
			function str(key, holder) {
				var i, k, v, length, mind = gap,
					partial, value = holder[key];
				if (value && typeof value === 'object' && typeof value.toJSON === 'function') {
					value = value.toJSON(key)
				}
				if (typeof rep === 'function') {
					value = rep.call(holder, key, value)
				}
				switch (typeof value) {
				case 'string':
					return quote(value);
				case 'number':
					return isFinite(value) ? String(value) : 'null';
				case 'boolean':
				case 'null':
					return String(value);
				case 'object':
					if (!value) {
						return 'null'
					}
					gap += indent;
					partial = [];
					if (Object.prototype.toString.apply(value) === '[object Array]') {
						length = value.length;
						for (i = 0; i < length; i += 1) {
							partial[i] = str(i, value) || 'null'
						}
						v = partial.length === 0 ? '[]' : gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' : '[' + partial.join(',') + ']';
						gap = mind;
						return v
					}
					if (rep && typeof rep === 'object') {
						length = rep.length;
						for (i = 0; i < length; i += 1) {
							k = rep[i];
							if (typeof k === 'string') {
								v = str(k, value);
								if (v) {
									partial.push(quote(k) + (gap ? ': ' : ':') + v)
								}
							}
						}
					} else {
						for (k in value) {
							if (Object.prototype.hasOwnProperty.call(value, k)) {
								v = str(k, value);
								if (v) {
									partial.push(quote(k) + (gap ? ': ' : ':') + v)
								}
							}
						}
					}
					v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
					gap = mind;
					return v
				}
			}
			if(typeof JSON !== 'object'){
				return {
					encode : function(value, replacer, space){
						var i;
						gap = '';
						indent = '';
						if (typeof space === 'number') {
							for (i = 0; i < space; i += 1) {
								indent += ' '
							}
						} else if (typeof space === 'string') {
							indent = space
						}
						rep = replacer;
						if (replacer && typeof replacer !== 'function' && (typeof replacer !== 'object' || typeof replacer.length !== 'number')) {
							throw new Error('JSON.stringify')
						}
						return str('', {
							'': value
						})
					
					},
					decode : function(text, reviver){
						var j;

						function walk(holder, key) {
							var k, v, value = holder[key];
							if (value && typeof value === 'object') {
								for (k in value) {
									if (Object.prototype.hasOwnProperty.call(value, k)) {
										v = walk(value, k);
										if (v !== undefined) {
											value[k] = v
										} else {
											delete value[k]
										}
									}
								}
							}
							return reviver.call(holder, key, value)
						}
						text = String(text);
						cx.lastIndex = 0;
						if (cx.test(text)) {
							text = text.replace(cx, function(a) {
								return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4)
							})
						}
						if (/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
							j = eval('(' + text + ')');
							return typeof reviver === 'function' ? walk({
								'': j
							}, '') : j
						}
						throw new SyntaxError('JSON.parse')
					}
					
				};
			}else{
				return {
					encode : JSON.stringify,
					decode : JSON.parse
				};
			};
		})();
	
	});
	
	XDK.register("core.arr", function(J){
		return {
			inArray : function(value, arr){
				var i = 0, l = arr.length;
				for(; i < l; i++){
					if(arr[i] === value){
						return true;
					};
				};
				return false;	
			},

			getIndex : function(value, arr){
				var i = 0, l = arr.length;
				for(; i < l; i++){
					if(arr[i] === value){
						return i;
					};
				};
				return -1;
			},
			
			unique : function(arr){
				var data = [];
				var i = 0, l = arr.length;
				for(; i < l; i++){
					var v = arr[i];
					if(this.getIndex(v, data) == -1){
						data.push(v);
					};
				};
				return data;	
			},
			
			//数组分组
			group : function(arr){
				var _2arr = [];
				for(var i = 0, l = arr.length; i < l; i++){
					var v = arr[i];
					//如果能在当前二维数组内找到值，则找到对应数组， 并添加到此数组内
					if(this.canFindIn2Arr(v, _2arr)){
						var targetArrIndex = this.get2arrIndex(v, _2arr);
						_2arr[targetArrIndex].push(v);
					}else{
						//如果找不到， 则新建一个数组， 将该数组添加到二维数组内
						_2arr.push([v]);
					};
				};
				return _2arr;
			},
			
			inObjList : function(key, value, objList){
				var i = 0, l = objList.length;
				for(; i < l; i++){
					if(objList[i][key] === value){
						return true;
					};
				};
				return false;	
			},
			
			getObjIndexFromObjList : function(key, value, objList){
				var i = 0, l = objList.length;
				for(; i < l; i++){
					if(objList[i][key] === value){
						return i;
					};
				};
				return -1;
			},
			
			groupObjList : function(arr, group_by_key){
				var _2arr = [];
				for(var i = 0, l = arr.length; i < l; i++){
					var o = arr[i];
					var v = o[group_by_key];
					//如果能在当前二维数组内找到值，则找到对应数组， 并添加到此数组内
					if(this.canFindObjIn2Arr(group_by_key, v, _2arr)){
						var targetArrIndex = this.get2arrObjIndex(group_by_key, v, _2arr);
						_2arr[targetArrIndex].push(o);
					}else{
						//如果找不到， 则新建一个数组， 将该数组添加到二维数组内
						_2arr.push([o]);
					};
				};
				return _2arr;
			},
			
			//key = "id"
			//value = 2
			//_2arr = [{id : 2}], [{id : 3}, {id : 3}], [{id : 1}, {id : 1}, {id : 1}], [{id : 4}, {id : 4}]
			canFindObjIn2Arr : function(key, value, _2arr){
				for(var i = 0, l = _2arr.length; i < l; i++){
					var _arr = _2arr[i];
					if(this.inObjList(key, value, _arr)){
						return true;
					};
				};
				return false;	
			},
			
			// key = "id"
			// value = 2, 
			//_2arr = [[{id:1},{id:2}], [{id:2},{id:2}], [{id:4},{id:4}]]
			// 返回某个数组所在索引
			get2arrObjIndex : function(key, value, _2arr){
				for(var i = 0, l = _2arr.length; i < l; i++){
					var arr = _2arr[i];
					if(this.inObjList(key, value, arr)){
						return i;
					}
				};
				return -1;
			},
			
			
			// value = 2, _2arr = [[1,1,1], [2,2], [3,3]]
			// 判断是否在某个数组内
			canFindIn2Arr : function(value, _2arr){
				for(var i = 0, l = _2arr.length; i < l; i++){
					var _arr = _2arr[i];
					if(this.inArray(value, _arr)){
						return true;
					};
				};
				return false;	
			},
			

			// value = 2, _2arr = [[1,1,1], [2,2], [3,3]]
			// 返回某个数组所在索引
			get2arrIndex : function(value, _2arr){
				for(var i = 0, l = _2arr.length; i < l; i++){
					var arr = _2arr[i];
					if(this.inArray(value, arr)){
						return i;
					}
				};
				return -1;
			}
		};
	});	

	/**
	 * 类构建（包含继承）
	*/
	XDK.register("Class.create", function(){
		var constructor_key = "_init_";
		var getInstance_key = "_getInstance_";
		var static_attr_pat = /^_(.+)_$/;
		return function(obj, baseClass){
			baseClass = baseClass || null;
			var cls = obj[constructor_key] || function(){};
			function __apply(){
				function _f(){};
				_f.prototype = fn;
				var obj = new _f();
				cls.apply(obj, arguments);
				return obj;
			};
			
			if(baseClass != null){
				XDK.Class.extend(cls, baseClass);
			};
			
			var fn = cls.prototype;
			for(var key in obj){
				if(key != constructor_key && key != getInstance_key){
					var rs = key.match(static_attr_pat);
					if(rs != null){
						cls[rs[1]] = obj[key];
					}else{
						fn[key] = obj[key];
					};
				};
			};
			cls.getInstance = obj[getInstance_key] || __apply;
			return cls;	
		};
	});

	/**
	 * 类继承
	*/
	XDK.register("Class.extend", function(){
		return function(subClass, baseClass){
			function init(){};
			init.prototype = baseClass.prototype;
			subClass.prototype = new init();
			subClass.prototype.constructor = subClass;
			subClass.baseConstructor = baseClass;
			subClass.superClass = baseClass.prototype;	
		};
	});
	
	XDK.register("core.base64", function(J){
		var Base64 = {
			_keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
			encode: function(input) {
				var output = "";
				var chr1,
				chr2,
				chr3,
				enc1,
				enc2,
				enc3,
				enc4;
				var i = 0;
				input = Base64._utf8_encode(input);
				while (i < input.length) {
					chr1 = input.charCodeAt(i++);
					chr2 = input.charCodeAt(i++);
					chr3 = input.charCodeAt(i++);
					enc1 = chr1 >> 2;
					enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
					enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
					enc4 = chr3 & 63;
					if (isNaN(chr2)) {
						enc3 = enc4 = 64
					} else if (isNaN(chr3)) {
						enc4 = 64
					}
					output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4)
				}
				return output
			},
			decode: function(input) {
				var output = "";
				var chr1,
				chr2,
				chr3;
				var enc1,
				enc2,
				enc3,
				enc4;
				var i = 0;
				input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
				while (i < input.length) {
					enc1 = this._keyStr.indexOf(input.charAt(i++));
					enc2 = this._keyStr.indexOf(input.charAt(i++));
					enc3 = this._keyStr.indexOf(input.charAt(i++));
					enc4 = this._keyStr.indexOf(input.charAt(i++));
					chr1 = (enc1 << 2) | (enc2 >> 4);
					chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
					chr3 = ((enc3 & 3) << 6) | enc4;
					output = output + String.fromCharCode(chr1);
					if (enc3 != 64) {
						output = output + String.fromCharCode(chr2)
					}
					if (enc4 != 64) {
						output = output + String.fromCharCode(chr3)
					}
				}
				output = Base64._utf8_decode(output);
				return output
			},
			_utf8_encode: function(string) {
				string = string.replace(/\r\n/g, "\n");
				var utftext = "";
				for (var n = 0; n < string.length; n++) {
					var c = string.charCodeAt(n);
					if (c < 128) {
						utftext += String.fromCharCode(c)
					} else if ((c > 127) && (c < 2048)) {
						utftext += String.fromCharCode((c >> 6) | 192);
						utftext += String.fromCharCode((c & 63) | 128)
					} else {
						utftext += String.fromCharCode((c >> 12) | 224);
						utftext += String.fromCharCode(((c >> 6) & 63) | 128);
						utftext += String.fromCharCode((c & 63) | 128)
					}
				}
				return utftext
			},
			_utf8_decode: function(utftext) {
				var string = "";
				var i = 0;
				var c = c1 = c2 = 0;
				while (i < utftext.length) {
					c = utftext.charCodeAt(i);
					if (c < 128) {
						string += String.fromCharCode(c);
						i++
					} else if ((c > 191) && (c < 224)) {
						c2 = utftext.charCodeAt(i + 1);
						string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
						i += 2
					} else {
						c2 = utftext.charCodeAt(i + 1);
						c3 = utftext.charCodeAt(i + 2);
						string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
						i += 3
					}
				}
				return string
			}
		};
		return Base64;
	});
	
	
	window[ns_str] = XDK;
})(window);


		
XDK.ns("FixDialog", function(){
	var supportTouch = "ontouchend" in document ? true : false;
	var touchEvents = {
		touchstart : supportTouch ?  "touchstart" : "mousedown",
		touchmove : supportTouch ? "touchmove" : "mousemove",
		touchend : supportTouch ? "touchend" : "mouseup"
	}

	var opt = Object.prototype.toString;
	var J = {};
	J.getType = function(a){
		var ret;
		var l = arguments.length;
		if (l === 0 || typeof(a) === "undefined") {
			ret = "undefined"
		} else {
			ret = (a === null) ? "null": opt.call(a)
		};
		return ret
	};
	J.type = {
		is_array : function(a){
			return J.getType(a) === "[object Array]";
		},
		is_string : function(a){
			return J.getType(a) === "[object String]";
		},
		is_number : function(a){
			return J.getType(a) === "[object Number]";
		},
		is_object : function(a){
			return J.getType(a) === "[object Object]";
		},
		is_function : function(a){
			return J.getType(a) === "[object Function]";
		},
		is_null : function(a){
			return J.getType(a) === "null";
		},
		is_undefined : function(a){
			 return J.getType(a) === "undefined";
		}
	};
	function uid(){
		var guid = "";
		for (var i = 1; i <= 32; i++){
		  var n = Math.floor(Math.random()*16.0).toString(16);
		  guid +=   n;
		  if((i==8)||(i==12)||(i==16)||(i==20))
			guid += "-";
		}
		return guid;    
	};	
	
	function render(tpl, data) {
		var __self = arguments.callee;
		if (J.type.is_array(data)) {
			var ret = "";
			$.each(data, function(key, obj) {
				ret += __self(tpl, obj)
			});
			return ret
		} else if (J.type.is_object(data)) {
			$.each(data, function(key, value) {
				tpl = tpl.replace(new RegExp("{" + key + "}", "g"), value);
			});
			return tpl
		} else {
			return null
		}
	};	
	var TPL = "" + 
	"<div class=\"fix-dialog-alpha  \" style=\"display:none;\" id=\"fix-dialog-alpha-{index}\"></div>" +	
	"<div class=\"fix-dialog  {dialogClassName}\" id=\"fix-dialog-{index}\" style=\"display:none;\">" +
	"	<table class=\"fix-dialog-table\" width=\"100%\" height=\"100%\" >" +
	"		<tr>" +
	"			<td>" +
	"				<div class=\"fix-dialog-wrap\" style='position:relative;'>" +
	"					<div class=\"handle-split\"></div>" +
	"					<div class=\"handle\"></div>" +
	"					<div class=\"c\"></div>" +
	"					<div class=\"bottom fix\"></div>" +
	"				</div>" +
	"			</td>" +
	"		</tr>" +
	"	</table>" +
	"</div>";
	var tapEvtName = "tap";
	
	var ZINDEX = 1000;
	var IDINDEX = -1;
	var DIALOG_LIST = {};
	//按钮设置
	var btnSet = {
		text : "ok",
		disabled : false,
		fn : function(btnObj, _self){},
		css : {},
		cssClass : "",
		id : ""
	};
	var __class__ = XDK.Class.create({
		wrapContent : function(msg){
			return "<div class=\"text-wrap\">" + msg + "</div>";
		},
		_getDialog_ : function(id){
			return typeof(DIALOG_LIST[id]) != "undefined" ? DIALOG_LIST[id] : null; 
		},
		_DIALOG_LIST_ : DIALOG_LIST,
		updateZIndex : function(){
			ZINDEX += 1;
			this.zIndex = ZINDEX;
			this.DOM.dialog.css({
				"z-index" : this.zIndex
			});
			this.DOM.alpha.css({
				"z-index" : this.zIndex
			});
		},
		/**
		 * @constructor 
		 * @param {Array} options.buttons
		*/
		_init_ : function(options){
			
			IDINDEX += 1;
			this.index = IDINDEX;
			
			this.set = $.extend({
				id : uid(),
				title : "提示",
				content : "<p>系统提示！</p>",
				alphaOpacity : 0.4,
				alphaBgColor : "#000000",
				buttonText : "确定",
				dialogClassName : "",
				buttons : [],
				btnActiveClass: "btn-active",
				//点击遮罩层关闭对话框
				closeOnAlphaClick : false,
				autoShow : true,
				showHandlebar : true,
				events : {},
				//true 隐藏dialog（同一个id的dialog可以缓存，不用每次创建）
				//false 强制关闭dialog(不同id的dialog)
				lazyClose : false,
				dialogTpl : TPL,
				appendTo : $("body")
			}, options);
			this.id = this.set.id;
			var dialogObj = __class__.getDialog(this.id);
			if(dialogObj){
				dialogObj.show();
				return dialogObj;
			};
			this.title = null;
			this.content = null;
			this.buttonText = null;
			this.lazyClose = this.set.lazyClose;
			this.set.events = $.extend({
				init : function(_self){},
				beforeopen : function(_self){},
				open : function(_self){},
				show : function(_self){},
				beforehide : function(_self){},
				hide : function(_self){},
				beforeclose : function(_self){return true;},
				close : function(_self){}
			}, this.set.events);
			DIALOG_LIST[this.id] = this;
			this.buttons = {};
			this.DOM = {};
			this.init();
		},
		_close : function(){
			if(!this.lazyClose){
				this.close()
			}else{
				this.hide();
			};
		},
		getBtn : function(id){
			var btn = this.buttons[id];
			 
			return typeof(btn) == "undefined" ? null : btn;
		},
		getBtnElement : function(id){
			var ele =  this.DOM.btnBar.find(".btn[data-id='" + id + "']").eq(0);
			return ele.size() == 0 ? null : ele;
		},
		_updateBtnSize : function(){
			var btnList = this.DOM.btnBar.find(".btn");
			var size = btnList.size();
			if(size == 0){
				return;
			};
			var w = ( 100 / size ) + "%";
			btnList.css({
				width : w
			});
			if(size == 1){
				return;
			}else if(size == 2){
				btnList.eq(1).css({
					borderLeft : 0
				});
			}else if(size > 2){
				btnList.eq(0).nextAll().css({
					borderLeft : 0
				});
			};
			
		},
		_renderBtn : function(ele, btnData){
			ele.attr({
				"data-id" : btnData.id
			}).html(btnData.text).addClass("btn").addClass(btnData.cssClass).css(btnData.css || {});
			if(btnData.disabled){
				this._disableBtn(ele);
			};
			return ele;
		},
		insertRenderBtn : function(btnData){
			return this._renderBtn($("<span data-type='btn' />"), btnData);
		},
		upateRenderBtn : function(id, btnData){
			var ele = this.getBtnElement(id);
			if(!ele){
				return;
			};
			return this._renderBtn(ele, btnData);
			
		},
		setBtnText : function(id, text){
			var btnData = this.getBtn(id);
			if(!btnData){
				return;
			};
			btnData.text = text;
			this.getBtnElement(id).html(text);
		},
		enableBtn : function(id){
			var btnData = this.getBtn(id);
			if(btnData){
				btnData.disabled = false;
				this.getBtnElement(id).removeAttr("disabled");
			};
		},
		disableBtn : function(id){
			var btnData = this.getBtn(id);
			if(btnData){
				btnData.disabled = true;
				this._disableBtn(this.getBtnElement(id));
			};
		},
		_disableBtn : function(btn){
			btn.attr("disabled", "disabled");
		},
		
		
		_setBtn : function(id, set){
			var btn = this.getBtn(id);
			if(!btn){
				return;
			};
			btn = $.extend(btn, set);
		},
		addBtn : function(btnData){
			this._setButtons([
				btnData
			]);
		},
		_setButtons : function(list){
			var _this = this;
			if(list.length == 0){
				_this.DOM.btnBar.hide();
				return;
			}
			
			$.each(list, function(i, obj){
				var btn = $.extend({}, btnSet, obj);
				if(!btn.id){
					btn.id = uid();
				};
				var tempBtn = _this.getBtn(btn.id);
				if(!tempBtn){
					_this.DOM.btnBar.append(_this.insertRenderBtn(btn));
				}else{
					_this.upateRenderBtn(btn.id, btn);
				};
				_this.buttons[btn.id] = btn;
			});
			this._updateBtnSize();
		},
		init : function(){
			var bodyEle = $(this.set.appendTo).eq(0);
			var html = render(this.set.dialogTpl, {
				index : this.index,
				dialogClassName : this.set.dialogClassName
			});
			var _this = this;
			var index = this.index;
			bodyEle.append(html);
			this.DOM.dialog = $("#fix-dialog-" + index);
			this.DOM.handle = this.DOM.dialog.find(".handle");
			this.DOM.alpha = $("#fix-dialog-alpha-" + index);
			this.DOM.dialogWrap = this.DOM.dialog.find(".fix-dialog-wrap");
			this.DOM.closeBtn = this.DOM.dialog.find(".close-btn");
			this.DOM.c = this.DOM.dialog.find(".c");
			this.DOM.btnBar = this.DOM.dialog.find(".bottom");
			if(!this.set.showHandlebar){
				this.DOM.handle.hide();
			};
			this.setTitle(this.set.title);
			this.setContent(this.set.content);
			this._setButtons(this.set.buttons);
			this.set.events.init.call(this, this);
			if(this.set.autoShow){
				this.show();
			};
			this.updateZIndex();
			this.DOM.alpha.css({
				"opacity" : this.set.alphaOpacity,
				"backgroundColor" : this.set.alphaBgColor
			}).on("click", function(e){
				e.preventDefault();
				e.stopPropagation();
				
				if(_this.set.closeOnAlphaClick){
					_this._close();
				};
			});
			 
			this.DOM.closeBtn.bind("click", function(e){
				e.preventDefault();
				e.stopPropagation();
				_this._close();
			});	
			
			
			this.DOM.dialog.on("click", function(e){
				_this.DOM.alpha.trigger("click");
			});
			this.DOM.dialogWrap.on("click", function(e){
				e.stopPropagation();
			});
			
			
			this.DOM.btnBar.on("click", ".btn", function(e){
				e.preventDefault();
				e.stopPropagation();
				var t = $(this);
				var id = t.attr("data-id");
				var btnData = _this.getBtn(id);
				//t.addClass(_this.set.btnActiveClass);
				//setTimeout(function(){
					//t.removeClass(_this.set.btnActiveClass);
					var rs = btnData.fn.call(_this, t, _this);
					if(rs !== false){
						_this._close();
					};
				//}, 200);
			
			}).on("touchstart.defaultTouch", ".btn", function(e){
				var t = $(this);
				 
				if(t.attr("disabled") || t.hasClass("ui-btn-disabled") || t.hasClass("ui-button-disabled")){
					return;
				}
				$(this).addClass(_this.set.btnActiveClass);
			}).on("touchend.defaultTouch touchcancel.defaultTouch", ".btn", function(e){
				var t = $(this);
				if(t.attr("disabled") || t.hasClass("ui-btn-disabled") || t.hasClass("ui-button-disabled")){
					return;
				}
				
				$(this).removeClass(_this.set.btnActiveClass);
			});
		},
		setContent : function(content){
			this.content = content;
			this.DOM.c.html(this.wrapContent(content));
		},
		setTitle : function(title){
			this.title = title;
			this.DOM.handle.html(title);
		},
		
		show : function(){
			this.DOM.alpha.show();
			this.DOM.dialog.show();
		},
		hide : function(){
			var rs = this.set.events.beforehide.call(this, this);
			this.DOM.alpha.hide();
			this.DOM.dialog.hide();
			this.set.events.hide.call(this, this);			
		},
		close : function(){
			var rs = this.set.events.beforeclose.call(this, this);
			if(rs === false){
				return;
			};
			this.DOM.alpha.off().hide().remove();
			this.DOM.dialog.off().hide();
			this.DOM.dialogWrap.off();
			this.DOM.closeBtn.off();
			this.DOM.btnBar.off();
			this.DOM.dialog.find("iframe").remove();
			this.DOM.dialog.remove();
			delete DIALOG_LIST[this.id];
			this.set.events.close.call(this, this);	
			 
		},
		destroy : function(){
			this.close.apply(this, arguments);
		}
	});
	return __class__;
});

XDK.ns("FixDialog.alert", function(){
	return function(msg, close){
		close = close || function(){};
		var opt = {
			autoShow : true,
			id : "_ALERT_", 
			content : msg,
			buttons : [
				{
					id : "ok",
					text : "确定",
					fn : function(){
						return close.apply(this, arguments);
					}
				}
			]
		};
		return new FixDialog(opt);
	};

});


/**
 * 系统提示框
 * @param {msg} 弹出消息html
 
*/
XDK.ns("FixDialog.sysTip", function(){
	return function(msg, close, closeTime){
		close = close || function(){};
		closeTime = typeof(closeTime) == "undefined" ? 2500 : parseInt(closeTime);
		var delay = null;
		function stopDelay(){
			if(delay != null){
				clearTimeout(delay);
				delay = null;
			}
		}
		var opt = {
			autoShow : true,
			id : "_SYSTIP_", 
			content : "<div class=\"c-sysTip\">" + msg + "</div>",
			showHandlebar : false,
			events : {
				close : function(){
				},
				beforeclose : function(){
					stopDelay();
				}
			},
			closeOnAlphaClick : true
			
		};
		var dialogObj = new FixDialog(opt);
		stopDelay();
		delay = setTimeout(function(){
			dialogObj.close();
			close.call(dialogObj, dialogObj);
		}, closeTime);
		return dialogObj;
	};

});


XDK.ns("FixDialog.sysTip2", function(){
	return function(msg, close, closeTime){
		close = close || function(){};
		closeTime = typeof(closeTime) == "undefined" ? 2500 : parseInt(closeTime);
		var delay = null;
		function stopDelay(){
			if(delay != null){
				clearTimeout(delay);
				delay = null;
			}
		}
		var opt = {
			autoShow : true,
			id : "_SYSTIP_", 
			content : "<div class=\"c-sysTip\">" + msg + "</div>",
			showHandlebar : false,
			events : {
				close : function(){
				},
				beforeclose : function(){
					stopDelay();
				}
			},
			closeOnAlphaClick : false
			
		};
		var dialogObj = new FixDialog(opt);
		stopDelay();
		delay = setTimeout(function(){
			dialogObj.close();
			close.call(dialogObj, dialogObj);
		}, closeTime);
		return dialogObj;
	};

});


XDK.ns("FixDialog.confirm", function(){
	return function(msg, ok_fn, cancel_fn){
		ok_fn = ok_fn || function(){};
		cancel_fn = cancel_fn || function(){};
		var opt = {
			autoShow : true,
			id : "_CONFIRM_",  
			content : msg,
			buttons : [
				{
					id : "ok",
					text : "确定",
					fn : function(){
						return ok_fn.apply(this, arguments);
					}
				},
				{
					id : "cancel",
					text : "取消",
					fn : function(){
						return cancel_fn.apply(this, arguments);
					}
				}
			]
		};
		return new FixDialog(opt);
	};
});


;(function(window, undefined){

	
	/**
	 * 设置underscore模版标记
	*/

	_.templateSettings = {
		interpolate: /\<\#\=(.+?)\#\>/gim,
		evaluate: /\<\#([\s\S]+?)\#\>/gim,
		escape: /\<\#\-(.+?)\#\>/gim
	};
		
	
	/**
	 * 设置静态资源目录，以及配置require相关模块选项
	*/
	(function(){
		var CONFIG_KEY = "REQUIRE_CONFIG";
		var cfg = typeof(window[CONFIG_KEY]) == "undefined" ? {
			debug : true,
			urlVersion : (new Date()).getTime()
		} : window[CONFIG_KEY];
		var static_dir = (function(){
			var dir = null;
			var scripts = document.getElementsByTagName("script");
			var pat = /((.*)(static\/))js\/lib/;
			for(var i = 0, l = scripts.length; i < l; i++){
				var srcipt = scripts[i];
				var src = srcipt.src;
				if(!src){
					continue;
				};
				
				var matchRs = src.match(pat);
				if(matchRs != null){
					dir = matchRs[1];
					break;
				};
			};
			return dir;
		})(); 
		if(static_dir == null){
			console.log("static_dir");
			return;
		};
		 
		var js = static_dir + "js/";	
		var lib = js + "lib/";
		var app = js + "app/";
		var style = static_dir + "css/";
		var getParam = (function(){
			var query = window.location.search != "" ? window.location.search.slice(1) : "";
			var GET = {};
			(function(){
				if(query == ""){
					return;
				};
				var arr = query.split(/&/);
				$.each(arr, function(i, v){
					if(v != ""){
						var o = v.split(/=/);
						if(o.length == 2){
							var key = o[0];
							var value = o[1];
							GET[key] = value;
						}else if(o.length == 1){
							var key = o[0];
							GET[key] = "";
						};
					};
				});
			})();
			return function(param){
				return typeof(GET[param]) != "undefined" ? GET[param] : null;
			};
		})();
		 
		 
		window._g_dir = {
			"static_dir" : static_dir,
			"js" : js,
			"lib" : lib,
			"app" : app,
			"style" : style
		};
		//console.log(cfg);
		require.config({
			map : {
			  '*' : {
				'css' : "js/lib/require-css",
				'image' : "js/lib/require-image",
				'text' : "js/lib/text-v2.0.10"
			  }
			},
			waitSeconds : 20,
			urlArgs :  (cfg.debug ? (new Date()).getTime() : cfg.urlVersion),
			paths : {
				"js" : js.replace(/(\/+)$/g, ""),
				"lib" : lib.replace(/(\/+)$/g, ""),
				"app" : app.replace(/(\/+)$/g, ""),
				"style" : style.replace(/(\/+)$/g, "")
			}
		});
		require.onError = function(){
			FixDialog.sysTip("网络连接错误");
			$("#loading-tip").hide();
		}
		
		window._g_getParam = getParam;
		 
	
	})();
	
	//require load source list
	(function(){
	
		function requireLoad(list, loaded){
			list = typeof(list) == "string" ? [list] : list;
			loaded = loaded || function(){};
			var loadingBar = $("#loading-tip");
			loadingBar.show();
			var context = this;
			//console.log(context);
			require(list, function(){
				loadingBar.hide();
				loaded.apply(context, arguments);
			}, function(error){
				console.log(error);
				loadingBar.hide();
				FixDialog.sysTip("网络连接错误");
			});
		};
		function changePosition(){
			var loadingBar = $("#loading-tip");		
			var top = $(window).scrollTop() + ( ($(window).innerHeight() - loadingBar[0].offsetHeight) / 2);
			loadingBar.css({
				top : top
			});
		};/*
		$(function(){
			$(window).resize(function(){
				changePosition();
			}).scroll(function(){
				changePosition();
			});
			changePosition();
		});*/
		window._g_requireLoad = requireLoad;
	})();
	
	
})(window);



/**
 * @class SPAMVC - create main webApplication
*/
(function(window, undefined){
	var APP_INDEX = -1;
	var app_object = {};
	function getApp(id){
		return typeof(app_object[id]) != "undefined" ? app_object[id] : null;
	};
	function getAppByRouterPath(routerPath, singleMode){
		singleMode = typeof(singleMode) == "undefined" ? true : singleMode; 
		var reg = new RegExp("^" + routerPath);
		var rs = [];
		for(var path in app_object){
			var app = app_object[path];
			if(reg.test(path)){
				if(singleMode){
					return app;
				};
				rs.push(app);
			};
		};
		return singleMode ? null : rs;
	};
	function addApp(id, obj){
		app_object[id] = obj;
	};
	function removeApp(id){
		if(getApp(id)){
			delete app_object[id]
		};
	};
	function getHash(){
		return ((window.location.hash || '').replace(/^#/, ''))
	};
	
	var Events = XDK.Class.create({
		_init_ : function(){
			this.eventList = {};
		},
		getEventList : function(event){
			return typeof(this.eventList[event]) !== "undefined" ? this.eventList[event] : [];
		},
		bind : function(event, func){
			this.eventList[event] = this.getEventList(event);
			this.eventList[event].push(func);
		},
		trigger : function(event){
			var targetEventList = this.getEventList(event);
			var i = 0;
			var l = targetEventList.length;
			var args = Array.prototype.slice.call(arguments, 1);
			for(; i < l; i++){
				var func = targetEventList[i];
				var rs = func.apply(this, args);
				if(rs === false){
					break;
				};
			};
		},
		unbind : function(event){
			delete this.eventList[event]; 
		}
	});
	
	var _class_ = XDK.Class.create({
		_init_ : function(opt){
			var _this = this;
			var args = Array.prototype.slice.call(arguments, 1);
			APP_INDEX += 1;
			this.appIndex = APP_INDEX;
			//布局模版路径
			this.layoutUrl = null;
			var set = $.extend({
				actionName : null,
				api : null, 
				params : {}, 
				routerObj : null, 
				path : null,
				routerPath : null,
				container : "#container",
				destroyPrevApp : true
			}, opt);
			this.set = set;
			this.actionName = set.actionName;
			this.api = set.api;
			this.params = set.params;
			this.path = set.path;
			this.routerPath = set.routerPath;
			this.routerData = (function(data){
				return {
					"action" : data[1],
					"controller" : data[0]
				};
			})(set.routerPath.split("/"));
			this.routerObj = set.routerObj;
			this.container = $(set.container);
			this.uninstallActionList = {};
			//保存前一个app的实时path
			var prevPath = this.container.attr("data-path") || null;
			this.prevPath = prevPath;
			
			//保存前一个app的初始化path
			//此app对象对应的key在对象列表里是以initPath为基准而不是prevPath
			var initPath = this.container.attr("data-init-path") || null;
			this.initPath = initPath;
			
			
			//dom树对象
			this.dom = {};
			//弹出框列表
			this.dialogList = {};
			//xhr请求列表
			this.xhrList = {};
			//obj对象类别
			this.objList = {};
		 
			this.prevAppObj = null;
			 
			//销毁前一个对象
			if(initPath){
				//销毁前缓存
				this.prevAppObj = this.getApp(this.initPath);
				if(this.set.destroyPrevApp){
					if(this.destroy(initPath) === false){
						return false;
					};
				};
			};
			this.container.attr("data-path", this.path);
			this.container.attr("data-init-path", this.path);
			this.sourcePath = this.path;
			this.sourceId = encodeURIComponent(decodeURIComponent(this.path));
			_class_.sourceId = this.sourceId;
			_class_.baseConstructor.apply(this, arguments);
			addApp(this.path, this);
			
			this.addUninstallAction(this.actionName, function(){
				$.each(this.dialogList, function(k, dialog){
					_this.destroyDialog(k);
					
				});
				$.each(this.xhrList, function(k, xhr){
					_this.abortXHR(k);
					
				});
				$.each(this.objList, function(k, o){
					_this.uninstallObj(k);
				});
			});
			$(window).on("unload", function(){
				_this.triggerUninstallAction();
			});
			this._initForm();
		},
		require : function(){
			_g_requireLoad.apply(this, arguments);
		},
		renderOnCurrentApp : function(callback){
			callback = callback || function(){};
			if(this.isCurrentApp()){
				callback.call(this);
			};
		},
		isCurrentApp : function(){
			return _class_.sourceId == this.sourceId;
		},
		_app_object_ : app_object,
		_getApp_ : getApp,
		getApp : getApp,
		_removeApp_ : removeApp,
		removeApp : removeApp,
		_getAppByRouterPath_ : getAppByRouterPath,
		getAppByRouterPath : getAppByRouterPath,
		
		renderContainer : function(view){
			//this.scrollToTop();
			this.resetHtml(this.container, view);
		},
		getSiblingsAppList : function(){
			var list = [];
			var sourcePath = this.sourcePath;
			$.each(app_object, function(path, app){
				if(path !== sourcePath){
					list.push(app);
				};
			});
			return list;
		},
		eachSiblingsAppList : function(callback){
			callback = callback || function(){};
			var _this = this;
			var list = this.getSiblingsAppList();
			var l = list.length;
			var i = 0;
			for(; i < l; i++){
				var app = list[i];
				var rs = callback.call(app, i, app);
				if(rs === false){
					return false;
				};
			};
		},
		initInpFocus : function(){
			var id = "spamvc-blank-focus";
			var tempFocusInp = $("#" + id);
			var inp; 
			if(tempFocusInp.size() == 0){
				inp = $("<input id='spamvc-blank-focus' type='text' style='width: 10px; height: 50px; overflow: hidden; background: none repeat scroll 0% 0% transparent; border: 0; position: absolute; top: 0px;' >");
				$("body").append(inp);
			}else{
				inp = tempFocusInp;
			};
			inp.focus();
			inp.hide();	
			
		},
		resetHtml : function(dom, html){
			dom = $(dom);
			dom.html(html);
			//this.initInpFocus();	
		},
		_initForm : function(){
			var s_input = ".g-form-input"; 
			var focus_input = "g-form-input-focus";
			var s_textarea = ".g-form-textarea";
			var focus_textarea = "g-form-textarea-focus";
			$("body").on("focus", s_input, function(){
				$(this).addClass("g-form-input-focus");
			}).on("blur", s_input, function(){
				$(this).removeClass("g-form-input-focus");
			});
			
			$("body").on("focus", s_textarea, function(){
				$(this).addClass(focus_textarea);
			}).on("blur", s_textarea, function(){
				$(this).removeClass(focus_textarea);
			});
		},
		/**
		 * 销毁一个控制器实例
		 * @param {String} path - 控制器实例的path
		 * @return Boolean
		*/
		destroy : function(path){
			var app_path = path.split("~")[0];
			var o = app_path.split("/");
			var action = o[1];
			var appObj = getApp(path);
			if(appObj){
				var rs = appObj.triggerUninstallAction(action) !== false;
				if(rs){
					console.log("destroy app - " + path);
					removeApp(path);
				};
				return rs;
			};
			return true;
		},
		
		/**
		 * 向队列内注入当前事件卸载方法，可以重复添加
		 * @prarm {String} action - 控制器事件名称
		 * @param {Function} func - 卸载方法
		*/
		addUninstallAction : function(action, func){
			if(arguments.length == 1){
				this.addUninstallAction(this.actionName, action);
				return;
			};
			if(typeof(this.uninstallActionList[action]) == "undefined"){
				this.uninstallActionList[action] = [];
			};
			this.uninstallActionList[action].push(func);
		},
		/**
		 * 循环执行当前事件队列的所有方法， 当遇到返回false的方法则退出
		 * @param {String} action - 当前控制器事件名称
		 * @return Boolean 
		*/
		triggerUninstallAction : function(action){
			action = action || this.actionName;
			var funcList = this.uninstallActionList[action];
			var _this = this;
			if(typeof(this.uninstallActionList[action]) == "undefined"){
				return;
			};
			for(var i = 0, l = funcList.length; i < l; i++){
				var func = funcList[i];
				var rs = func.call(this) !== false;
				if(rs === false){
					return false;
				};
			};
			this.container.empty();
			return true;
		},
		unescapeParams : function(params){
			var res = {};
			$.each(params, function(k, v){
				 
				//res[k] = unescape(v);
				res[k] = decodeURIComponent(v);
			});	
			return res;
		},
		getParam : function(paramName, isDecode){
			isDecode = typeof(isDecode) == "undefined" ? true : isDecode;
			var param = typeof(this.params[paramName]) !== "undefined" ? this.params[paramName] : null;
			if(param != null){
				if(isDecode){
					param = decodeURIComponent(param);
					//param = unescape(param);
				};
			};
			
			return param;
		},
		
		destroyDialog : function(dialog){
			if(XDK.type.is_object(dialog)){
				if(typeof(dialog.close) !== "undefined"){
					dialog.close();
				};
			}else if(XDK.type.is_string(dialog)){
				var obj = this.dialogList[dialog] || null;
				if(obj){
					console.log("destroy dialog - " + dialog);
					this.destroyDialog(obj);
					delete this.dialogList[dialog];
				};
			};
			 
		},
		setXHR : function(xhr_key, xhr_source_callback){
			this.abortXHR(xhr_key);
			this.xhrList[xhr_key] = XDK.type.is_function(xhr_source_callback) ? xhr_source_callback.call(this, this) : xhr_source_callback;
		},
		setObj : function(obj_key, obj_source_callback){
			
			this.uninstallObj(obj_key);
			this.objList[obj_key] = XDK.type.is_function(obj_source_callback) ? obj_source_callback.call(this, this) : obj_source_callback;
		},
		abortXHR : function(xhr){
			if(XDK.type.is_object(xhr)){
				xhr.abort();
			}else if(XDK.type.is_string(xhr)){
				
				var obj = this.xhrList[xhr] || null;
				if(obj){
					//console.log("destroy xhr - " + xhr);
					this.abortXHR(obj);
					delete this.xhrList[xhr];
				};
			};
		},
		uninstallObj : function(o){
			
			if(XDK.type.is_object(o)){
				if(typeof(o["destroy"]) != "undefined"){
					o.destroy();
				};
			}else if(XDK.type.is_string(o)){
				var obj = this.objList[o] || null;
				if(obj){
					//console.log("destroy object - " + o);
					this.uninstallObj(obj);
					delete this.objList[o];
				};
			};
			
		},
		unlogincallback : function(json){
		
		},
		showLoadingBar : function(){
			$("#loading-tip").show();
		},
		hideLoadingBar : function(){
			$("#loading-tip").hide();
		},
		_ajax : function(type, url, data, callback, unlogincallback, showLoadingBar){
			callback = callback || function(){};
			unlogincallback = unlogincallback || this.unlogincallback;
			showLoadingBar = typeof(showLoadingBar) == "undefined" ?  true : showLoadingBar;
			var _this = this;
			var loadingBar = $("#loading-tip");
			if(showLoadingBar){
				_this.showLoadingBar();
			}
			if(type == "get"){
				data = $.extend({
					_v_ : Math.random()
				}, data);
			};
			return $.ajax({
				type : type,
				url : url,
				data : data,
				success : function(json){
					if(showLoadingBar){
						_this.hideLoadingBar();
					}
					if(json.code == 10001){
						unlogincallback.call(_this, json, this);
					}else{
						callback.call(_this, json, this);	
					};
				},
				error : function(a, b){
					if(showLoadingBar){
						_this.hideLoadingBar();
					}
					console.log(XDK.core.json.encode(a));
				},
				dataType : "json"	
			});
		},
		getData : function(){
			var args = Array.prototype.slice.call(arguments);
			args.unshift("get");
			return this._ajax.apply(this, args);
		},
		postData : function(){
			var args = Array.prototype.slice.call(arguments);
			args.unshift("post");
			return this._ajax.apply(this, args);
		},
		
		/**
		 * 容器内插入iframe 跳转
		 * @param {String} url - iframe 地址,默认会自带 _path_ 参数，标识来源应用程序sourceId
		*/
		iframeTo : function(url){
			var url = this.getURL(url, {
				_path_ : this.sourceId,
				_ : Math.random()
			});
			 
			var p = $("<div class='lay-main-iframe main-iframe'>");
			var iframe = $("<iframe frameborder='0' srcolling='auto' data-sourceid='" + this.sourceId + "'>").attr({
				src : url,
				width : "100%",
				height : "100%"
				
			});
			this.container.append(p);
			p.append(iframe);
			this.addUninstallAction(function(){
				iframe.attr("src", "about:blank").remove();
			});			
		
		},
		
		/**
		 * 更改hash路径，同时绑定router对象的hashChangeEvent
		*/
		redirect  : function(){
			this.routerObj.navigate(this.createURL.apply(this, arguments), {
				trigger : true
			});
		},
		
		/**
		 * 更改hash路径，但是不绑定router对象的hashChangeEvent
		*/
		updateHash : function(){
			this.routerObj.navigate(this.createURL.apply(this, arguments), {
				trigger : false
			});
			this.updateParams();
		},
		
		updateHashExtendsSelfParams : function(path, params){
			params = $.extend({}, this.unescapeParams(this.params), params);
			this.updateHash(path, params);
		},
		updateParams : function(){
			var hash = getHash();
			var data = hash.split(/~/);
			var temp = {};
			if(data.length == 2){
				var query2Arr = data[1].split(/&/);
				var l = query2Arr.length;
				var i = 0;
				for(; i < l; i++){
					var o = query2Arr[i].split(/=/);
					var key = o[0];
					var v = o.length == 2 ? o[1] : "";
					//temp[key] = escape(unescape(v));
					temp[key] = encodeURIComponent(decodeURIComponent(v));
				};
			};
			this.params = $.extend({}, this.params, temp);
			this.path = hash;
			this.container.attr("data-path", this.path);
		},
		/**
		 * 创建URL
		 * @param path - 路径
		 * @param {Object|String} params - 传递参数 
		 * @return {Boolean}
		 * @example this.createURL("a/b", "id=1&vid=2") => a/b~id=1&vid=2;
		 * @example this.createURL("a/b", {id : 1, vid : 2}) =>	a/b~id=1&vid=2;
		*/
		createURL : function(path, params, isEncodeParams){
			var len = arguments.length;
			params = params || "";
			isEncodeParams = typeof(isEncodeParams) == "undefined" ? true : isEncodeParams;
			if(len == 1){
				return path;
			};
			if(typeof(params) == "string"){
				if(params == ""){
					return path;
				};
				return path + "~" + params;
			}
			if($.isPlainObject(params)){
				var queryList = [];
				$.each(params, function(k, v){
					//queryList.push(k + "=" + (isEncodeParams ? escape(v) : v) );
					queryList.push(k + "=" + (isEncodeParams ? encodeURIComponent(v) : v) );
				});
				var query = queryList.join("&");
				return query !== "" ? path + "~" + query : path;
			};
			
		},
		getURL : function(url, param_data){
			param_data = param_data || {};
			var preFixParam = {};
			var url_explode = url.split("?");
			var _url = url_explode.length > 1 ? url_explode[0] : url; 
			if(url_explode.length > 1){
				var _params = url_explode[1];
				var _arr = _params.split("&");
				$.each(_arr, function(i, v){
					
					var _o = v.split("=");
					preFixParam[_o[0]] = _o[1];
				});
			};
			$.each(preFixParam, function(k, v){
				if(typeof(param_data[k]) == "undefined"){
					param_data[k] = v;
				};
			});
			
			var params = "";
			$.each(param_data, function(key, value){
				params += key + "=" + value + "&";
			});
			if(params != ""){
				params = params.slice(0, -1);
			};
			
			return (params != "") ? (_url + "?" + params) :  _url;
		},
		form2json : function(form){
			 
			var data= {};
			form.find("input[type=hidden]").each(function(){
				var t = $(this);
				var key = t.attr("name");
				var value = t.val();
				data[key] = value;
			});
			return data;
		},
		select2json : function(select, ext){
			select = $(select);
			ext = $.extend({
				name_key : "name",
				value_key : "value",
				attr_list : []
			}, ext);
			var data = [];
			select.find("option").each(function(){
				var t = $(this);
				var o = {};
				if(this.selected){
					o.selected = true;
				};
				o[ext.name_key] = t.html();
				o[ext.value_key] = t.attr("value") || t.html();
				$.each(ext.attr_list, function(i, b){
					if(typeof(t.attr(b.option_attr)) != "undefined"){
						if(b.type == "boolean"){
							o[b.json_attr] = t.attr(b.option_attr) == "true" ? true : false;
						}else{
							o[b.json_attr] = t.attr(b.option_attr);
						};
					};
				});
				data.push(o);
			});
			return data;
		},
		
		json2select : function(data){
			var options = "";
			var pat = /^data\-/;
			$.each(data, function(i, o){
				var selectedAttr = ( typeof(o.selected) != "undefined" && o.selected === true ) ? "selected='selected'" : "";
				var dataAttrList = [];
				$.each(o, function(attr, value){
					if(pat.test(attr)){
						dataAttrList.push(attr + "='" + value + "'");
					};
				});
				
				options += "<option " + dataAttrList.join("  ") + selectedAttr + " value='" + (typeof(o.id) == "undefined" ? o.name : o.id) + "'>" + o.name + "</option>";
			});
			return options;
		},

		render : function(tpl_id, data){
			data = data || {};
			data.self = this;
			var tplNode = this.container.find("[data-tpl='" + tpl_id + "']");
			if(tplNode.size() == 0){
				tplNode = this.container.find("#" + tpl_id);
			}
			return _class_.render.apply(_class_, [tplNode, data]);
		},
		/**
		 * 渲染模板，需要underscore支持，调用_.template方法
		 * @param {String} tpl_id - 模板id，此项必填
		 * @param {Object} data - 数据对象，此项可选
		 * @return {String} || null
		*/
		_render_ : function(tpl_id, data){
			data = data || {};
			var script;
			if(XDK.type.is_string(tpl_id)){
				script = $("#" + tpl_id);
			}else{
				script = $(tpl_id);
			};
			return script.size() == 0 ? null : _.template(script.html(), data);
		},
		render2obj : function(tpl_id){
			var script;
			if(XDK.type.is_string(tpl_id)){
				script = $("#" + tpl_id);
			}else{
				script = $(tpl_id);
			};	
			return script.size() == 0 ? null : eval("(" + script.html() + ")");
		},
		render2Obj : function(){
			return this.render2obj.apply(this, arguments)
		},
		getUrlFromLink : function(linkObj){
			linkObj = $(linkObj);
			return this.getUrlFromHref(linkObj.attr("href"));
		},
		getUrlFromHref : function(href){
			return href.replace(/^((.*#+))/g, "");	
		},
		
		/**
		 * @param {String} url - 当前模板路径
		 * @param {Function} loaded
		*/
		view : function(url, loaded, options){
			loaded = loaded || function(){};
			var _this = this;
			options = $.extend({
				//布局模板数据对象
				layout_data : {},
				//公共资源
				require_source : []
			}, options);
			var pat = /^(text\!)/;
			options.layout_url = this.layoutUrl;
			if(options.layout_url){
				if(!pat.test(options.layout_url)){
					options.layout_url = "text!" + options.layout_url;
				};
			};
			//console.log(this.module);
			
			if(!pat.test(url)){
				url = "text!" + url;
			};
			
			//加载当前视图模版
			function loadContent(callback){
				callback = callback || function(){};
				_g_requireLoad([
					url
				], function(content){
					callback.call(_this, content);
				});
			};
			_g_requireLoad(options.require_source, function(){
				loadContent(function(content){
					if(options.layout_url === null){
						_this.renderOnCurrentApp(function(){
							loaded.call(_this, content);
						});
					}else{
						_g_requireLoad([
							options.layout_url
						], function(layout){
							options.layout_data.__content__ = content;
							options.layout_data.self = _this;
							layout = _.template(layout, options.layout_data);
							_this.renderOnCurrentApp(function(){
								loaded.call(_this, layout);
							});
						});
					}
				});
			});
		},
		
		/**
		 * 返回前一页
		 * @param {String} prevPath
		 
		*/
		back : function(routerPath){
			var pat = new RegExp("^" + routerPath);
			this.redirect((this.prevPath && pat.test(this.prevPath)) ? this.prevPath : routerPath);
		},
		/**
		 * 是否从上一页后退回来
		*/
		isFromPrevPage : function(){
			return this.prevAppObj ? this.path === this.prevAppObj.prevPath : false;
		}

	}, Events);
	window.SPAMVC = _class_;
})(window);



/**
 * @class CRouter - hash路由封装
*/
;(function(window, undefined){
	
	function cls(opt){
		var set = $.extend({
			defaultRoute : "",
			routes : {
			},
			routerAction : {
				r : function(router){
					 
				}
			}
		}, opt);
		
		var routerConfig = $.extend({}, {
			routes : set.routes
		}, set.routerAction);
		var clsRouter = Backbone.Router.extend(routerConfig, {});
		this.routerObj = new clsRouter();
		Backbone.history.start();
		Backbone.history.navigate(set.defaultRoute, {trigger:true});
	};
	window.CRouter = cls;
})(window);
/**
 * form2js
*/
(function (window){
	"use strict";
	function form2js(rootNode, delimiter, skipEmpty, nodeCallback, useIdIfEmptyName, getDisabled)
	{
		getDisabled = getDisabled ? true : false;
		if (typeof skipEmpty == 'undefined' || skipEmpty == null) skipEmpty = true;
		if (typeof delimiter == 'undefined' || delimiter == null) delimiter = '.';
		if (arguments.length < 5) useIdIfEmptyName = false;

		rootNode = typeof rootNode == 'string' ? document.getElementById(rootNode) : rootNode;

		var formValues = [],
			currNode,
			i = 0;

		/* If rootNode is array - combine values */
		if (rootNode.constructor == Array || (typeof NodeList != "undefined" && rootNode.constructor == NodeList))
		{
			while(currNode = rootNode[i++])
			{
				formValues = formValues.concat(getFormValues(currNode, nodeCallback, useIdIfEmptyName, getDisabled));
			}
		}
		else
		{
			formValues = getFormValues(rootNode, nodeCallback, useIdIfEmptyName, getDisabled);
		}

		return processNameValues(formValues, skipEmpty, delimiter);
	}

	function processNameValues(nameValues, skipEmpty, delimiter)
	{
		var result = {},
			arrays = {},
			i, j, k, l,
			value,
			nameParts,
			currResult,
			arrNameFull,
			arrName,
			arrIdx,
			namePart,
			name,
			_nameParts;

		for (i = 0; i < nameValues.length; i++)
		{
			value = nameValues[i].value;

			if (skipEmpty && (value === '' || value === null)) continue;

			name = nameValues[i].name;
			_nameParts = name.split(delimiter);
			nameParts = [];
			currResult = result;
			arrNameFull = '';

			for(j = 0; j < _nameParts.length; j++)
			{
				namePart = _nameParts[j].split('][');
				if (namePart.length > 1)
				{
					for(k = 0; k < namePart.length; k++)
					{
						if (k == 0)
						{
							namePart[k] = namePart[k] + ']';
						}
						else if (k == namePart.length - 1)
						{
							namePart[k] = '[' + namePart[k];
						}
						else
						{
							namePart[k] = '[' + namePart[k] + ']';
						}

						arrIdx = namePart[k].match(/([a-z_]+)?\[([a-z_][a-z0-9_]+?)\]/i);
						if (arrIdx)
						{
							for(l = 1; l < arrIdx.length; l++)
							{
								if (arrIdx[l]) nameParts.push(arrIdx[l]);
							}
						}
						else{
							nameParts.push(namePart[k]);
						}
					}
				}
				else
					nameParts = nameParts.concat(namePart);
			}

			for (j = 0; j < nameParts.length; j++)
			{
				namePart = nameParts[j];

				if (namePart.indexOf('[]') > -1 && j == nameParts.length - 1)
				{
					arrName = namePart.substr(0, namePart.indexOf('['));
					arrNameFull += arrName;

					if (!currResult[arrName]) currResult[arrName] = [];
					currResult[arrName].push(value);
				}
				else if (namePart.indexOf('[') > -1)
				{
					arrName = namePart.substr(0, namePart.indexOf('['));
					arrIdx = namePart.replace(/(^([a-z_]+)?\[)|(\]$)/gi, '');

					/* Unique array name */
					arrNameFull += '_' + arrName + '_' + arrIdx;

					/*
					 * Because arrIdx in field name can be not zero-based and step can be
					 * other than 1, we can't use them in target array directly.
					 * Instead we're making a hash where key is arrIdx and value is a reference to
					 * added array element
					 */

					if (!arrays[arrNameFull]) arrays[arrNameFull] = {};
					if (arrName != '' && !currResult[arrName]) currResult[arrName] = [];

					if (j == nameParts.length - 1)
					{
						if (arrName == '')
						{
							currResult.push(value);
							arrays[arrNameFull][arrIdx] = currResult[currResult.length - 1];
						}
						else
						{
							currResult[arrName].push(value);
							arrays[arrNameFull][arrIdx] = currResult[arrName][currResult[arrName].length - 1];
						}
					}
					else
					{
						if (!arrays[arrNameFull][arrIdx])
						{
							if ((/^[0-9a-z_]+\[?/i).test(nameParts[j+1])) currResult[arrName].push({});
							else currResult[arrName].push([]);

							arrays[arrNameFull][arrIdx] = currResult[arrName][currResult[arrName].length - 1];
						}
					}

					currResult = arrays[arrNameFull][arrIdx];
				}
				else
				{
					arrNameFull += namePart;

					if (j < nameParts.length - 1) /* Not the last part of name - means object */
					{
						if (!currResult[namePart]) currResult[namePart] = {};
						currResult = currResult[namePart];
					}
					else
					{
						currResult[namePart] = value;
					}
				}
			}
		}

		return result;
	}

    function getFormValues(rootNode, nodeCallback, useIdIfEmptyName, getDisabled)
    {
        var result = extractNodeValues(rootNode, nodeCallback, useIdIfEmptyName, getDisabled);
        return result.length > 0 ? result : getSubFormValues(rootNode, nodeCallback, useIdIfEmptyName, getDisabled);
    }

    function getSubFormValues(rootNode, nodeCallback, useIdIfEmptyName, getDisabled)
	{
		var result = [],
			currentNode = rootNode.firstChild;
		
		while (currentNode)
		{
			result = result.concat(extractNodeValues(currentNode, nodeCallback, useIdIfEmptyName, getDisabled));
			currentNode = currentNode.nextSibling;
		}

		return result;
	}

    function extractNodeValues(node, nodeCallback, useIdIfEmptyName, getDisabled) {
        if (node.disabled && !getDisabled) return [];

        var callbackResult, fieldValue, result, fieldName = getFieldName(node, useIdIfEmptyName);

        callbackResult = nodeCallback && nodeCallback(node);

        if (callbackResult && callbackResult.name) {
            result = [callbackResult];
        }
        else if (fieldName != '' && node.nodeName.match(/INPUT|TEXTAREA/i)) {
            fieldValue = getFieldValue(node, getDisabled);
            if (null === fieldValue) {
                result = [];
            } else {
                result = [ { name: fieldName, value: fieldValue} ];
            }
        }
        else if (fieldName != '' && node.nodeName.match(/SELECT/i)) {
	        fieldValue = getFieldValue(node, getDisabled);
	        result = [ { name: fieldName.replace(/\[\]$/, ''), value: fieldValue } ];
        }
        else {
            result = getSubFormValues(node, nodeCallback, useIdIfEmptyName, getDisabled);
        }

        return result;
    }

	function getFieldName(node, useIdIfEmptyName)
	{
		if (node.name && node.name != '') return node.name;
		else if (useIdIfEmptyName && node.id && node.id != '') return node.id;
		else return '';
	}


	function getFieldValue(fieldNode, getDisabled)
	{
		if (fieldNode.disabled && !getDisabled) return null;
		
		switch (fieldNode.nodeName) {
			case 'INPUT':
			case 'TEXTAREA':
				switch (fieldNode.type.toLowerCase()) {
					case 'radio':
			if (fieldNode.checked && fieldNode.value === "false") return false;
					case 'checkbox':
                        if (fieldNode.checked && fieldNode.value === "true") return true;
                        if (!fieldNode.checked && fieldNode.value === "true") return false;
			if (fieldNode.checked) return fieldNode.value;
						break;

					case 'button':
					case 'reset':
					case 'submit':
					case 'image':
						return '';
						break;

					default:
						return fieldNode.value;
						break;
				}
				break;

			case 'SELECT':
				return getSelectedOptionValue(fieldNode);
				break;

			default:
				break;
		}

		return null;
	}

	function getSelectedOptionValue(selectNode)
	{
		var multiple = selectNode.multiple,
			result = [],
			options,
			i, l;

		if (!multiple) return selectNode.value;

		for (options = selectNode.getElementsByTagName("option"), i = 0, l = options.length; i < l; i++)
		{
			if (options[i].selected) result.push(options[i].value);
		}

		return result;
	}

	window.form2js = form2js;
})(window);


/**
 * @class SPAController extends {Class} SPAMVC
*/
;(function(window, undefined){

	function htmlspecialchars(string, quote_style, charset, double_encode) {
		var optTemp = 0,
			i = 0,
			noquotes = false;
		if (typeof quote_style === 'undefined' || quote_style === null) {
			quote_style = 2
		}
		string = string.toString();
		if (double_encode !== false) {
			string = string.replace(/&/g, '&amp;')
		}
		string = string.replace(/</g, '&lt;').replace(/>/g, '&gt;');
		var OPTS = {
			'ENT_NOQUOTES': 0,
			'ENT_HTML_QUOTE_SINGLE': 1,
			'ENT_HTML_QUOTE_DOUBLE': 2,
			'ENT_COMPAT': 2,
			'ENT_QUOTES': 3,
			'ENT_IGNORE': 4
		};
		if (quote_style === 0) {
			noquotes = true
		}
		if (typeof quote_style !== 'number') {
			quote_style = [].concat(quote_style);
			for (i = 0; i < quote_style.length; i++) {
				if (OPTS[quote_style[i]] === 0) {
					noquotes = true
				} else if (OPTS[quote_style[i]]) {
					optTemp = optTemp | OPTS[quote_style[i]]
				}
			}
			quote_style = optTemp
		}
		if (quote_style & OPTS.ENT_HTML_QUOTE_SINGLE) {
			string = string.replace(/'/g, '&#039;')
		}
		if (!noquotes) {
			string = string.replace(/"/g, '&quot;')
		}
		return string
	};
	
	var instanceIndex = 0;
	var __class__ = XDK.Class.create({
	
		/**
		 * 类构造函数，子类继承时调用
		*/
		_init_ : function(opt){
			var _this = this;
			instanceIndex += 1;
			this.instanceIndex = instanceIndex;
			this.module = __class__.module;
			this.config = opt.config;
			__class__.baseConstructor.apply(this, arguments);
			this.pageId = this.routerPath.replace(/\//g, "-") + "-" + this.appIndex;
			//解绑事件
			this.addUninstallAction(function(){
				this.container.off();	
				//console.log("container event off");
			});			
		},
		
		domId : function(id){
			return this.pageId + "-" + id;
		},
		getDomById : function(id){
			return $("#" + this.domId(id));
		},
		disableBtn : function(btn){
			btn = $(btn);
			var cls = "btn-item-disabled";
			if(btn.attr("data-disabled")){
				return false;
			};
			btn.addClass(cls).attr("data-disabled", "disabled").attr("disabled", "disabled");
			return true;
		},
		
		removeDisabledBtn : function(btn){
			btn = $(btn);
			var cls = "btn-item-disabled";
			btn.removeClass(cls).removeAttr("data-disabled").removeAttr("disabled");
		},
		
		htmlspecialchars : htmlspecialchars,
		unlogincallback : function(json){
			alert("请先登录");
			return;
		},
		getFormData : function(form){
			form = $(form);
			if(typeof(form2js) == "undefined"){
				console.log("undefined form2js");
				return false;
			};
			if(form.size() == 0){
				console.log("undefined form");
				return false;
			};
			var _form = form[0];
			//console.log(_form);
			return form2js(_form, ".", false, function(){}, false, true);
		},
		indexAction : function(){
			var _this = this;
			this.container.on("click", "[data-g-act]", function(e){
				e.preventDefault();
				var act = $(this).attr("data-g-act");
				if(act == "reload"){
					_this.reload();
				}else if(act == "back"){
					_this.back();
				};
			});
			 
			
			
		},
		//重新加载某个tab
		reload : function(routerPath, params){
			var isCurrent = arguments.length == 0;
			var url = this.createURL.apply(this, arguments);
			routerPath = isCurrent ? this.path : url;
			var app_path = routerPath.replace(/\~.*$/g, "");
			var o = app_path.split("/");
			var c = o[0];
			var action = o[1];
			console.log(routerPath + " - " + app_path);
			//找到该path的app对象
			var appObj = this.getAppByRouterPath(app_path);
			console.log("find routerPath app -" + app_path, appObj);
			if(appObj){
				var rs = appObj.triggerUninstallAction(action) !== false;
				console.log(rs);
				if(rs){
					appObj.indexAction();
				};
			}else{
				this.redirect(url);
			};
		}
	}, SPAMVC);
	window.SPAController = __class__;
})(window);	


/**
 * @class AppController extends {Class} SPAController - 项目级主控制器
*/

(function(window, undefined){
	
	var TOKEN_KEY = "userData";
	var securityPattern = /((\.security)|(\.payment))((\?.*){0,1})$/i;
	var htmlEle = document.documentElement;
	function _winSize(){
		
		var l =  window.pageXOffset,
		t = window.pageYOffset,
		window_w = window.innerWidth,
		window_h = window.innerHeight,
		scrollWidth = window_w + l,
		scrollHeight = window_h + t;
		return {
			l: l,
			t: t,
			scrollHeight: scrollHeight,
			scrollWidth : scrollWidth,
			window_w: window_w,
			window_h: window_h
		};
	};	

	function onlyNum(e, maxLength) {
		maxLength = maxLength || 12;
		 
		if(!isNumber(e.value)){
			 
			if((e.value+'').lastIndexOf('.')==((e.value+'').length-1)&&((e.value+'').length-(e.value+'').replace('.','').length)==1){
				return false;
			}
			e.value='';
			return false;
		}
		if((e.value+'').length>maxLength){
			e.value=(e.value+'').substring(0,maxLength);
			return false; 
		}
		if((e.value+'').indexOf('.')>0&&((e.value+'').length-1-(e.value+'').indexOf('.'))>2){
			 
			e.value=(e.value+'').substring(0,(e.value+'').indexOf('.')+3);
			return false; 
		}
	}
	
	function isNumber(oNum) {  
		if (!oNum)  
			return false;  
		var strP = /^\d+(\.\d+)?$/;  
		if (!strP.test(oNum))  
			return false;  
		try {  
			if (parseFloat(oNum) != oNum)  
				return false;  
		} catch (ex) {  
			return false;  
		}  
		return true;  
	};	
	
	
	function getString(str){
		 
		var result = "";  
		for (var i = 0; i < str.length; i++) {
			var s = str.charCodeAt(i); 
			if ( s == 12288){ 
				result += String.fromCharCode(32);
			} else if (s > 65280 && s < 65375){
				result += String.fromCharCode(str.charCodeAt(i) - 65248);
			}else{
				result += String.fromCharCode(str.charCodeAt(i));
			}
		}	
		return result;
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
	
	
	
	//不支持本地缓存，建立一个全局js对象缓存数据，刷新该缓存为空。
	var CACHE = {};
	var storage = !window.localStorage ? {
		removeItem : function(key){
			delete CACHE[key];
		},
		setItem : function(key, value){
			CACHE[key] = value;
		},
		getItem : function(key){
			return typeof(CACHE[key]) == "undefined" ? null : CACHE[key];
		}
	} : window.localStorage;
	
	var __class__ = XDK.Class.create({
		_init_ : function(opt){
			__class__.baseConstructor.apply(this, arguments);
			this.module = __class__.module;
			this.layoutUrl = this.getViewUrl("layouts/main"); 
			//头部handle标题
			this.title = null;
			//头部返回按钮地址
			this.prevUrl = this.prevPath || "index/index";
			//console.log(this.path);
			//console.log(this.prevUrl);
			this.pageUrl = __class__.getPageUrl();
			this.userData = this.token2data();
			this.userId = this.getUserId();
			//是否需要登录
			this.needUserId = false;
			this.storage = storage;
			this.autoAppendMsgWraper = true; 
			this.userAccount = null;
			this.alertMsgTimer = null;
			this.styleList = [];
			this.cssHtmls = "";
			this.apiRoutes = this.config.apiRoutes;
			this.showBackHomeNav = false;
			this.showFooterNav = false;
			this.passwdValidBox = null;
			//待验证支付密码
			this.validPayPasswd = [];
			//是否正在提交密码验证
			this.validPayPasswdLocked = false;
			this.currentValidPayPasswdSuccessCallback = function(){};
			//弹出支付密码验证框输入密码后是否自动调用验证密码接口
			this.needAutoCheckPayPasswd = true;
			this.serviceTel = "4000078655";
			
		},
		cityMap : {
			"GUANGZHOU" : "01004",
			"GUANGZHOU_1" : "010004",
			"SHANGHAI" : "06002",
			"SHANGHAI_1" : "060002",
			"BEIJING" : "24001",
			"BEIJING_" : "240001",
			"SHENZHEN" : "01003",
			"SHENZHEN_1" : "010003"
		},
		productTypeMap : {
			BAILINGZHUANXIANG : "P2016061200010000",
			XIAOJINYU : "P2016061200010001",
			GONGZIXIANXIANG : "P2016061200010002",
			GONGZIYIDAI : "P2016061200010003",
			JINNANG : "P2016061200010004",
			LIUTONGBAO : "P2016061200010005"
		},
		setCssHtmlList : function(callback){
			var _this = this;
			var _list = [];
			$.each(this.styleList, function(i, file){
				_list.push("text!static/css/" + _this.module +"/"+ file +".css");
			});
			this.require(_list, function(){
				var list = Array.prototype.slice.call(arguments, 0);
				$.each(list, function(i, css){
					_this.cssHtmls += "<style>" + css.replace(/(\.\.\/)*img\//g, _g_dir.static_dir + "img/") + "</style>";
				});
				callback.call(_this);
			});
		},
		isNumber : isNumber,
		onlyNum : onlyNum,
		hex2str : hex2str,
		str2hex : str2hex,
		getViewUrl : function(viewPath){
			return this.getViewDir() + viewPath + ".html";
		},
		getViewDir : function(){
			return this.getNSPrefix() + "view/";
		},
		getNSPrefix : function(){
			return "static/js/app/" + this.module + "/";
		},
		json_encode : function(data){
			return XDK.core.json.encode(data);
		},
		json_decode : function(str){
			return XDK.core.json.decode(str);
		},
		view : function(url, loaded, options){
			loaded = loaded || function(){};
			var _this = this;
			options = $.extend({
				//布局模板数据对象
				layout_data : {},
				//公共资源
				require_source : []
			}, options);
			var pat = /^(text\!)/;
			options.layout_url = this.layoutUrl;
			if(options.layout_url){
				if(!pat.test(options.layout_url)){
					options.layout_url = "text!" + options.layout_url;
				};
			};
			//console.log(this.module);
			
			if(!pat.test(url)){
				url = "text!" + this.getViewUrl(url);
			};
			
			//加载当前视图模版
			function loadContent(callback){
				callback = callback || function(){};
				_g_requireLoad([
					url
				], function(content){
					content = _this.cssHtmls + content;
					callback.call(_this, content);
				});
			};
			_g_requireLoad(options.require_source, function(){
				loadContent(function(content){
					
					if(options.layout_url === null){
						_this.renderOnCurrentApp(function(){
							loaded.call(_this, content);
						});
					}else{
						_g_requireLoad([
							options.layout_url
						], function(layout){
							options.layout_data.__content__ = content;
							options.layout_data.self = _this;
							layout = _.template(layout, options.layout_data);
							_this.renderOnCurrentApp(function(){
								loaded.call(_this, layout);
							});
						});
					}
				});
			});
		},
		//添加api
		addApiOpt : function(path, opt){
			routerPathMap[path] = opt;
		},
		strSlice : function(str, len, isDot){
			var n = len * 2;
			var r = /[^\x00-\xff]/g;    
			isDot = typeof(isDot) == "undefined" ? false : isDot;
			if(str.replace(r, "mm").length <= n){
				 return str;
			};   
			var m = Math.floor(n/2);   
			for(var i = m; i < str.length; i++) {    
				 if(str.substr(0, i).replace(r, "mm").length >= n) {    
					return str.substr(0, i) + (isDot ? "..." : ""); 
				 }else{
					 return str; 
				 }
			}
		},
		
		hmtPush : function(path){
			path = "/wallet-weixin-client/wxh5/index.html?router=" + ( encodeURIComponent(decodeURIComponent(path || this.path)));
			_hmt.push(['_setAccount', 'c342e3a712d30623716dc6dd9f738d79']);
			_hmt.push(['_trackPageview', path]);
			console.log('_trackPageview - ', path);
		},
		stopMsgTimer : function(){
			if(this.alertMsgTimer === null){
				return;
			}
			clearTimeout(this.alertMsgTimer);
			this.alertMsgTimer = null;
		},
		alertMsg : function(msg){
			/*
			var _this = this;
			var msgWraper = this.getDomById("msgWraper");
			msgWraper.stop().hide().fadeIn().text(msg).addClass("status-bar-error");
			this.stopMsgTimer();
			this.alertMsgTimer = setTimeout(function(){
				_this.hideMsg();
			}, 1500);
			*/
			this.dialogList.alertMsg = FixDialog.sysTip(msg);
		},
		hideMsg : function(){
			var msgWraper = this.getDomById("msgWraper");
			msgWraper.stop();
			msgWraper.fadeOut().removeClass("status-bar-error");
			 
		},	
		setUserAccountData : function(success){
			this.getUserAccountData(function(json){
				this.userAccount = json ? json.body : null;
				success.call(this);
				
			});
		},
		getUserAccountData : function(success){
			if(!this.userId){
				success.call(this, null);
				return;
			};
			var router = this.getApiRouter("user/accountData", {}, "TEST");
			this.setXHR("user/accountData", function(){
				return this.postData(router.url, router.data, function(json){
					if(json.footer.status == 200){
						success.call(this, json);
					}else{
						alert(json.footer.message);
					}
				});
			});
		},
		
		getString  : function(){
			return getString.apply(this, arguments);
		},
		
		token2data : function(){
			return this.isLogin() ? XDK.core.json.decode(this.getToken()) : null;
		},
		getUserId : function(){
			var data = this.token2data();
			return data ? data.userId : null;
		},
		_getToken_ : function(){
			return storage.getItem(TOKEN_KEY);
		},
		getToken : function(){
			return __class__.getToken.apply(this, arguments);
		},
		_setToken_ : function(token){
			storage.setItem(TOKEN_KEY, token);
		},
		setToken : function(){
			__class__.setToken.apply(this, arguments);
		},
		_deleteToken_ : function(){
			storage.removeItem(TOKEN_KEY);
		},
		deleteToken : function(){
			__class__.deleteToken.apply(this, arguments);
			console.log("token 已删除", "getToken:", this.getToken());
		},
		
		/**
		 * 获取api接口地址和参数对象
		*/
		_getApiRouter_ : function(path, data){
			data = data || {};
			return {
				url : path,
				data : data
			};
		},
		
		getApiRouter : function(path, data, forceUrl){
			data = data || {};
			var _path = this.getApiRoute(path);
			var userData = this.token2data();
			if(securityPattern.test(_path)){
				//登录状态，传递userId uuid 等用户参数
				if(userData !== null){
					data = $.extend({}, {
						userId : userData.userId || "",
						uuid : userData.uuid || ""
					}, data);
				}
			}
			data.userChannelType = "weixin";
			forceUrl = typeof(forceUrl) == "undefined" ? false : forceUrl;
			var url = forceUrl ? path : _path;
			return __class__.getApiRouter.apply(this, [url , data]);
		},
		getApiRoute : function(path){
			return $.trim(typeof(this.apiRoutes[path]) == "undefined" ? path : this.apiRoutes[path]);
		},
		showLoadingBar : function(){
			$("#loading-tip").show();
		},
		hideLoadingBar : function(){
			$("#loading-tip").hide();
		},
		errorcallback : function(text, errorType){
			
			
		},
		requestErrorCallBack : function(errorType){
			console.log(errorType);
			if(errorType == "error"){
				this.alertMsg("网络连接错误");
			}else if(errorType == "timeout"){
				this.alertMsg("网络连接超时");
			}
		},
		
		_ajax : function(type, url, data, callback, unlogincallback, showLoadingBar, errorcallback, requestErrorCallBack){
			callback = callback || function(){};
			unlogincallback = unlogincallback || this.unlogincallback;
			showLoadingBar = typeof(showLoadingBar) == "undefined" ?  true : showLoadingBar;
			errorcallback = errorcallback || this.errorcallback;
			requestErrorCallBack = requestErrorCallBack || this.requestErrorCallBack;
			var _this = this;
			var loadingBar = $("#loading-tip");
			if(showLoadingBar){
				_this.showLoadingBar();
			}
			if(type == "get"){
				data = $.extend({
					_v_ : Math.random()
				}, data);
			};
			return $.ajax({
				type : type,
				url : url,
				data : data,
				timeout : 20000,
				success : function(text){
					var json;
					try{
						json = XDK.core.json.decode(text);
					}catch(e){
						json = null;
					}
					if(showLoadingBar){
						_this.hideLoadingBar();
					}
					if(!json){
						errorcallback.call(_this, text, null);
						return;
					}
					
					var code = json.footer["status"];
					//帐号登录超时拦截
					if(code == 610){
						_this.deleteToken();
						unlogincallback.call(_this, json, this);
					}else{
						callback.call(_this, json, this);	
					};
				},
				error : function(a, b){
					if(showLoadingBar){
						_this.hideLoadingBar();
					}
					requestErrorCallBack.call(_this, b);
					errorcallback.call(_this, null, b);
					callback.call(_this, null, this);
				},
				dataType : "text"	
			});
		},
		getData : function(){
			var args = Array.prototype.slice.call(arguments);
			args.unshift("get");
			return this._ajax.apply(this, args);
		},
		postData : function(){
			var args = Array.prototype.slice.call(arguments);
			args.unshift("post");
			return this._ajax.apply(this, args);
		},
	  
		_getPageUrl_ : function(){
			return window.location.href.replace(/#(.*)$/g, "");
		},
		scrollToTop : function(){
			this.scrollTo(0);
		},
		scrollTo : function(top){
			this.getScrollBody().scrollTop(top);
		},
		getScrollBody : function(){
			var $body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $("html") : $("body")) : $("html,body");
			return $body; 
		},
		getWinSize : _winSize,
		getPageScrollTop : function(){
			return this.getWinSize().t;
		},
		indexAction : function(){
			var _this = this;
			var body = $("body");
			var doc = $(document);
			__class__.superClass.indexAction.apply(this, arguments);
			var showFooterTab = _g_getParam("showFooterTab");
			if(showFooterTab !== null){
				this.showFooterNav = showFooterTab == 1;
			}
			
			this.scrollToTop();
			this.container.on("click", "[data-act='exit']", function(e){
				e.preventDefault();
				_this.exit();
			}).on("blur", "input[type='number'], input[type='tel']", function(){
				this.value = _this.getString(this.value);
			});

			this.container.on("touchstart.defaultTouch", "[g-act-tap]", function (e) {
				var t = $(this);
				if(t.attr("disabled") || t.hasClass("ui-btn-disabled") || t.hasClass("ui-button-disabled")){
					return;
				}
				$(this).addClass("g-act-taped");
				
				
			 
			}).on("touchmove.defaultTouch", "[g-act-tap]", function (e) {
				//$(this).removeClass("g-act-taped");
			}).on("touchend.defaultTouch touchcancel.defaultTouch", "[g-act-tap]", function(e){
				var t = $(this);
				if(t.attr("disabled") || t.hasClass("ui-btn-disabled") || t.hasClass("ui-button-disabled")){
					return;
				}
				
				$(this).removeClass("g-act-taped");
			}).on("tap", "[data-tap-path]", function(e){
				e.preventDefault();
				e.stopPropagation();
				var path = $(this).attr("data-tap-path");
				if(!path){
					return;
				}
				path = path.replace(/^#/g, "");
				_this.redirect(path);
			});  
		 
			(function(){
				if(typeof(wx) == "undefined"){
					return;
				}
				//微信配置
				wx.config({
					debug: false, 
					 // 必填，需要使用的JS接口列表
					jsApiList: [
						'hideOptionMenu',
						'closeWindow'
					]
				});

				wx.ready(function () {
					//wx.hideOptionMenu();
				});
				
			})();
			
			
			if(this.title){
				this.setPageTitle(this.title);
			}
			this.hmtPush();
			this.addUninstallAction(function(){
				this.stopMsgTimer();
				
				this.container.off("touchstart.defaultTouch touchend.defaultTouch touchcancel.defaultTouch touchmove.defaultTouch");
				this.closePayPasswdDialog();
			});
			
		},
		setPageTitle : function(title){
			var body = $("body");
			document.title  = title;
			var $iframe = $('<iframe src="' + _g_dir.lib + 'blank.html' +'" style="position:absolute;left:-100px;" height="20" width="20"></iframe>').on('load', function() {
				setTimeout(function() {
					$iframe.off('load').remove();
				}, 0);
			}).appendTo(body); 
						
		},
		exit : function(){
			this.deleteToken();
			this.replaceDirect("index/index");
		},
		
		//根据模版字符串渲染组件
		renderWidget : function(tplString, data){
			data = data || {};
			data.self = this;
			return _.template(tplString, data);
		},
		//渲染url组件
		renderWidgetByUrl : function(tplUrl, data, loaded){
			data = data || {};
			loaded = loaded || function(){};
			this.require("text!" + this.getViewUrl(tplUrl), function(tplString){
				loaded.call(this, this.renderWidget(tplString, data));
			});
		},
		 
		/**
		 * 判断是否登录
		 * @return {Boolean}
		*/
		isLogin : function(){
			return this.getToken() !== null;
		},
		/**
		 * 通过客户端的TOKEN_COOKIE对用户是否登录监听回调
		 * @param {Function} login_callback - 已登录回调 
		 * @param {Function} unlogin_callback - 未登录回调 
		*/
		loginListener : function(login_callback, unlogin_callback){
			var _this = this;
			login_callback = login_callback || function(){};
			unlogin_callback = unlogin_callback || function(){
				_this.goLogin();
			};	
			if(this.isLogin()){
				login_callback.call(this, this.getToken());
			}else{
				unlogin_callback.call(this);
			}
		},
		/**
		 * 前往登录页面
		*/
		goLogin : function(){
			this.replaceDirect("user/login");
		},
		replaceDirect : function(){
			var pat = /#(.*)$/g;
			var href = window.location.href.replace(pat, "");
			var path = this.createURL.apply(this, arguments);
			console.log("targetPath", href + "#" + path);
			
			window.location.replace(href + "#" + path);
		},
		/**
		 * ajax请求为未登录的code的回调函数
		*/
		unlogincallback : function(){
			this.goLogin();
		},
		redirectPrev : function(){
			if(this.prevPath){
				this.redirect(this.prevPath);
			}else{
				window.location.href = "/";
			}
		},
				
		/**
		 * 静态方法，dom初始化执行
		*/
		_createWebApp_ : function(opt){
			var args = arguments;
			var _this = this;
			opt = $.extend({
				defaultRoute : null,
				module : "index",
				API : {},
				initEmptyRoute : "index/index",
				apiRoutes : []
			}, opt);
			opt.API = $.extend({}, opt.API);
			var tempRouterList = opt.initEmptyRoute.split(/\//);
			var initEmptyRouter = {
				c : tempRouterList[0],
				r : tempRouterList[1]
			};
			if(!opt.module){
				console.log("opt.module cannot null");
				return;	
			};
			this.module = opt.module;
			this.opt = opt;
			this.router = new CRouter({
				defaultRoute : opt.defaultRoute,
				routes : {
					"" : "parse",
					":c/:r" : "parse"
				},
				
				
				routerAction : {
					parse : function(c, r){
						
						if(!c){
							c = initEmptyRouter.c;
							r = initEmptyRouter.r;	
						};
						
						
						var _this2 = this;
						var rs = r.match(/^(\w+)(~(.+)){0,1}/);
						var data = [rs[1]];
						if(rs[2]){
							data[1] = rs[3];
						}
						
						var action = data[0];
						var params = {};
						var _params = [];
						if(data.length == 2){
							var query2Arr = data[1].split(/&/);
							var l = query2Arr.length;
							var i = 0;
							for(; i < l; i++){
								var o = query2Arr[i].split(/=/);
								var key = o[0];
								var v = o.length == 2 ? o[1] : "";
								params[key] = encodeURIComponent(decodeURIComponent(v));
								_params.push(key + "=" + params[key]);
							};
						};
						
						var cacheHash = window.location.hash;
						_g_requireLoad(["app/" + _this.module + "/controller/" + c + "/" + action, "lib/apiRoutes"], function(controller, apiRoutes){
							opt.apiRoutes = apiRoutes;
							var newHash = window.location.hash;
							if(newHash != cacheHash){
								return;
							};
							var hash = newHash.replace(/^(#+)/g, "");
							var path = hash.replace(/~(.*)$/g, "");
							var routerPath = c + "/" + action;
							var app = new controller({
								actionName : action,
								api : opt.API[c],
								params : params, 
								routerObj : _this2, 
								//path : c + "/" + r,
								path : c + "/" + action + (_params.length > 0 ? ("~" + _params.join("&")) : ""),
								routerPath : routerPath,
								destroyPrevApp : true,
								config : opt,
								container : "#main-container"
							});
							app.actionName = action;
							function _init(){
								app.setCssHtmlList(function(){
									f.apply(app, args);
								});
							}
							var f = app["indexAction"];
							if(typeof(f) == "undefined"){
								return;
							}
							if(app.needUserId){
								app.loginListener(function(){
									_init();
								});
								
							}else{
								_init();
							}
							 
							 
						});
					}
				}
			});
		},
		removePageCache : function(){
			this.storage.removeItem(this.path);
		},
		getPageCache : function(){
			var v =  this.storage.getItem(this.path);
			return v !== null ? this.json_decode(v) : null;			
		},
		setPageCache : function(){
			this._setCache({});
		},
		_setCache : function(data){
			data = $.extend({}, {
				scrollTop : this.getPageScrollTop()
			}, data);
			var dataStr = this.json_encode(data);
			this.storage.setItem(this.path, dataStr);
		},
		getBankImgByCode : function(code){
			return code == "99999" ? _g_dir.static_dir + "img/99999.png" : "/wallet-mobile/resources/bank@3x/"+ code +".png"
		},
		//初始化验证支付密码
		initPayPasswdDialog : function(callback){
			var _this = this;
			if(this.passwdValidBox == null){
				this.renderWidgetByUrl("widget/validPayPasswdDialog", {},  function(html){
					$("body").append(html);
					this.passwdValidBox = $("#ui-passwdValidBox");
					this.passwdValidBox.on("tap", ".alpha", function(e){
						e.preventDefault();
						e.stopPropagation();
						setTimeout(function(){
							_this.closePayPasswdDialog();
						}, 150);
					}).on("tap", ".close", function(e){
						e.preventDefault();
						e.stopPropagation();
						_this.closePayPasswdDialog();
					}).on("tap", "#validPayPasswdKeybox .getpwd", function(e){
						e.preventDefault();
						e.stopPropagation();
						_this.redirect($(this).attr("data-url"));
					}).on("tap", "#validPayPasswdKeybox [data-act]", function(e){
						e.preventDefault();
						e.stopPropagation();
						var t =  $(this);
						var act = t.attr("data-act");
						var number = null;
						if(act == "delete"){
							if(_this.validPayPasswdLocked){
								return;
							}
							_this.deleteValidPayPasswd();
						}else if(act == "input"){
							number = t.attr("data-number");
							if(_this.validPayPasswdLocked){
								return;
							}
							_this.addValidPayPasswd(number, function(json){
								
								if(!this.needAutoCheckPayPasswd){
									this.currentValidPayPasswdSuccessCallback.call(this, null, null, this.validPayPasswd.join(""));
									
									return;
								}
								this.validPayPasswdLocked = false;
								var messageData;
								if(json === null){
									return;
								}
								if(json.footer.status != 200){
									this.alertMsg(json.footer.message);
									return;
								}
								
								//还剩余几次机会
								if(json.body.code == "200701"){
									this.emptyPayPasswd();
									messageData = json.body.message.split("|");
									this.dialogList.confirmDialog = FixDialog.getInstance({
										showHandlebar : false,
										content : "<div class=\"c-sysTip\">"+ messageData[0] +"</div>",
										buttons : [
											{
												text : messageData[1],
												fn : function(){
													_this.closePayPasswdDialog();
												}
											},
											{
												text : messageData[2],
												fn : function(){
													
												}
											}
											
										]
									});
									return;
								}
								//小锁状态
								if(json.body.code == "200702"){
									this.emptyPayPasswd();
									messageData = json.body.message.split("|");
									this.dialogList.confirmDialog = FixDialog.getInstance({
										showHandlebar : false,
										content : "<div class=\"c-sysTip\">"+ messageData[0] +"</div>",
										buttons : [
											{
												text : messageData[1],
												fn : function(){
													//_this.replaceDirect("account/index");
													//console.log("back");
													history.back();
												}
											},
											{
												text : messageData[2],
												fn : function(){
													_this.replaceDirect("account/payPasswdLock");
												}
											}
											
										]
									});
									
									return;
								}
								//大锁状态回调
								
								
								if(json.body.code == "200700"){
									this.currentValidPayPasswdSuccessCallback.call(this, json.body.message, json, this.validPayPasswd.join(""));
									this.closePayPasswdDialog();
								}else{
									this.alertMsg(json.body.message);
									this.emptyPayPasswd();
								}
									
									
									
								
								
								
							});
							
						}
					});
					this.addUninstallAction(function(){
					 
						this.passwdValidBox.off().remove();
						 
					});
					callback.call(this);
					
				});
			}else{
				
				callback.call(this);
			}
		},
		emptyPayPasswd : function(){
			this.validPayPasswdLocked = false;
			this.validPayPasswd = [];
			$("#showValidInput").find(".box").find(".dot").hide();
		},
		//打开支付密码框， 是否触发自动验证
		openPayPasswdDialog : function(callback, needAutoCheckPayPasswd){
			needAutoCheckPayPasswd = typeof(needAutoCheckPayPasswd) == "undefined" ? true : false;
			this.needAutoCheckPayPasswd = needAutoCheckPayPasswd;
			var time = 200;
			this.passwdValidBox.show();
			this.currentValidPayPasswdSuccessCallback = callback;
			this.passwdValidBox.find(".alpha").stop().animate({
				opacity : 0.2
			}, time);
			this.passwdValidBox.find(".content").stop().slideDown(time);
		},
		closePayPasswdDialog : function(){
			if(!this.passwdValidBox){
				return;
			}
			var time = 200;
			var _this = this;
			this.emptyPayPasswd();
			this.passwdValidBox.find(".alpha").stop().animate({
				opacity : 0
			}, time);
			this.passwdValidBox.find(".content").stop().slideUp(time, function(){
				_this.passwdValidBox.hide();
			});
		},
		listenValidPayPasswdChange : function(callback, cancelAutoCheckCallback){
			callback = callback || function(){};
			cancelAutoCheckCallback = cancelAutoCheckCallback || function(){};
			var router = null;
			if(this.validPayPasswd.length < 6){
				 console.log("error valid pay passwd length");
			}else{
				 console.log("valid pay passwd ok");
				 this.validPayPasswdLocked = true;
				 if(this.needAutoCheckPayPasswd){
					 router = this.getApiRouter("checkPaymentPassword", {
						 paymentPassword : this.validPayPasswd.join("")
					 });
					 this.setXHR("paymentPassword", function(){
						 return this.postData(router.url, router.data, function(json){
							 callback.call(this, json);
						 });
					 });
				 }else{
					 cancelAutoCheckCallback.call(this);
				 }
				 
			}
			
		},
		/**
		 * 删除一位验证支付密码输入
		*/
		deleteValidPayPasswd : function(){
			var step = this.step;
			var targetIndex = -1;
			if(this.validPayPasswd.length > 0){
				targetIndex = this.validPayPasswd.length - 1;
				this.validPayPasswd.pop();
			};
			if(targetIndex > -1){
				$("#showValidInput").find(".box").eq(targetIndex).find(".dot").hide();
			}
			console.log(this.validPayPasswd);
			this.listenValidPayPasswdChange();
		},
		
		/**
		 * 添加一位验证支付密码输入
		*/
		addValidPayPasswd : function(number, validRsCallback){
			validRsCallback = validRsCallback || function(){};
			var step = this.step;
			var targetIndex = -1;
			if(this.validPayPasswd.length < 6){
				this.validPayPasswd.push(number);
				targetIndex = this.validPayPasswd.length - 1;
			}else{
				return;
			}
			if(targetIndex > -1){
				$("#showValidInput").find(".box").eq(targetIndex).find(".dot").show();
			}
			console.log(this.validPayPasswd);
			this.listenValidPayPasswdChange(function(json){
				validRsCallback.call(this, json);
			}, function(){
				validRsCallback.call(this, null);
			});
		},
		
		_api_selfLockDialog : function(messageData){
			var _this = this;
			this.dialogList.confirmDialog = FixDialog.getInstance({
				showHandlebar : false,
				content : "<div class=\"c-sysTip\">"+ messageData[0] +"</div>",
				buttons : [
					{
						text : messageData[1],
						fn : function(){
							_this.closePayPasswdDialog();
						}
					},
					{
						text : messageData[2],
						fn : function(){
							_this.redirect("account/payPasswdLock");
						}
					}
					
				]
			});								
		},
		_api_serviceLockDialog : function(messageData){
			var _this = this;
			_this.dialogList.serviceHelpDialog = FixDialog.getInstance({
				showHandlebar : false,
				content : "<div class=\"c-sysTip\">"+ messageData[0] +"</div>",
				buttons : [
					{
						text : messageData[1]
					},
					{
						text : "<a href='tel:"+ _this.serviceTel +"' class='telBtn' style='display:block; color:#4ac0f0;'>" + messageData[2] + "</span>",
						fn : function(){
							
						}
					}
					
				],
				events : {
					init : function(){
						var dialog = this;
						this.DOM.btnBar.find(".telBtn").on("tap", function(e){
							e.stopPropagation();
							
							
						});
					},
					beforeclose : function(){
						this.DOM.btnBar.find(".telBtn").off();
					}
					
				}
			});								
			
		},
		_api_avlilableNumDialog : function(messageData){
			var _this = this;
			_this.dialogList.confirmDialog = FixDialog.getInstance({
				showHandlebar : false,
				content : "<div class=\"c-sysTip\">"+ messageData[0] +"</div>",
				buttons : [
					{
						text : messageData[1],
						fn : function(){
							_this.closePayPasswdDialog();
						}
					},
					{
						text : messageData[2],
						fn : function(){
							
						}
					}
					
				]
			});
			
		},
		_loadAsyValidator : function(callback){
			this.require(["lib/asyvalidator"], function(a){
				callback.call(this);
			});
		},
		_loadDropLoad : function(callback){
			this.require(["lib/dropload"], function(){
				callback.call(this);
			});
		},
		_loadSwiper : function(callback){
			this.require(["lib/swiper"], function(){
				callback.call(this);
			});
		},
		_loadSwiperWidth3D : function(callback){
			this._loadSwiper(function(){
				this.require(["lib/swiper.3dflow"], function(){
					callback.call(this);
				});
			});
		}
	}, SPAController);
	window.AppController  = __class__;
})(window);
