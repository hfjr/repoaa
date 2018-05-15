<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html class="html" lang="en-US">
 <head>

  <script type="text/javascript">
//    if(typeof Muse == "undefined") window.Muse = {}; window.Muse.assets = {"required":["jquery-1.8.3.min.js", "museutils.js", "jquery.watch.js", "index.css"], "outOfDate":[]};


</script>
    <script type="text/javascript" src="<c:url value='/admin/paper/msgdetail/js/jquery-1.8.3.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/admin/paper/msgdetail/js/museutils.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/admin/paper/msgdetail/js/jquery.watch.js'/>"></script>
    
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <meta name="generator" content="2014.3.0.295"/>
  <title>Home</title>
  <!-- CSS -->
  <link rel="stylesheet" type="text/css" href="<c:url value='/admin/paper/msgdetail/css/site_global.css?422415861'/>"/>
  <link rel="stylesheet" type="text/css" href="<c:url value='/admin/paper/msgdetail/css/index.css?3934513377'/>"/>
  <!-- Other scripts -->
  <script type="text/javascript">
   document.documentElement.className += ' js';
</script>
   </head>
 <body>

  <div class="clearfix" id="page"><!-- column -->
   <div class="position_content" id="page_position_content">
    <div class="clearfix colelem" id="pu120"><!-- group -->
     <div class="grpelem" id="u120"><!-- custom html -->
      <style>
body{
font-family: "Microsoft Yahei", "微软雅黑", "Helvetica Neue", "adobe雅黑";
}
</style>
</div>
     <div class="clearfix grpelem" id="u232-4"><!-- content -->
      <p>${message.title }</p>
     </div>
    </div>
    <div class="colelem" id="u227"><!-- simple frame --></div>
    <div class="clearfix colelem" id="u236-4"><!-- content -->
     <p><fmt:formatDate value="${message.createDate }" pattern="yyyy-MM-dd"/></p>
    </div>
    <div class="clip_frame colelem" id="u112"><!-- image -->
     <img class="block" id="u112_img" src="<c:url value='/admin/paper/msgdetail/images/down.jpg'/>" alt="" width="38" height="24"/>
    </div>
    <div class="clearfix colelem" id="u187-4"><!-- content -->
     <p>${message.content }</p>
    </div>
    <div class="verticalspacer"></div>
   </div>
  </div>
  <!-- JS includes -->

  <script type="text/javascript">
   window.jQuery || document.write('\x3Cscript src="<c:url value='/admin/paper/msgdetail/scripts/jquery-1.8.3.min.js'/>" type="text/javascript">\x3C/script>');
</script>
  <script src="scripts/museutils.js?3793461109" type="text/javascript"></script>
  <script src="scripts/jquery.watch.js?3766403489" type="text/javascript"></script>
  <!-- Other scripts -->
  <script type="text/javascript">
//    $(document).ready(function() { try {
// (function(){var a={},b=function(a){if(a.match(/^rgb/))return a=a.replace(/\s+/g,"").match(/([\d\,]+)/gi)[0].split(","),(parseInt(a[0])<<16)+(parseInt(a[1])<<8)+parseInt(a[2]);if(a.match(/^\#/))return parseInt(a.substr(1),16);return 0};(function(){$('link[type="text/css"]').each(function(){var b=($(this).attr("href")||"").match(/\/?css\/([\w\-]+\.css)\?(\d+)/);b&&b[1]&&b[2]&&(a[b[1]]=b[2])})})();(function(){$("body").append('<div class="version" style="display:none; width:1px; height:1px;"></div>');
// for(var c=$(".version"),d=0;d<Muse.assets.required.length;){var f=Muse.assets.required[d],g=f.match(/([\w\-\.]+)\.(\w+)$/),k=g&&g[1]?g[1]:null,g=g&&g[2]?g[2]:null;switch(g.toLowerCase()){case "css":k=k.replace(/\W/gi,"_").replace(/^([^a-z])/gi,"_$1");c.addClass(k);var g=b(c.css("color")),h=b(c.css("background-color"));g!=0||h!=0?(Muse.assets.required.splice(d,1),"undefined"!=typeof a[f]&&(g!=a[f]>>>24||h!=(a[f]&16777215))&&Muse.assets.outOfDate.push(f)):d++;c.removeClass(k);break;case "js":k.match(/^jquery-[\d\.]+/gi)&&
// typeof $!="undefined"?Muse.assets.required.splice(d,1):d++;break;default:throw Error("Unsupported file type: "+g);}}c.remove();if(Muse.assets.outOfDate.length||Muse.assets.required.length)c="Some files on the server may be missing or incorrect. Clear browser cache and try again. If the problem persists please contact website author.",(d=location&&location.search&&location.search.match&&location.search.match(/muse_debug/gi))&&Muse.assets.outOfDate.length&&(c+="\nOut of date: "+Muse.assets.outOfDate.join(",")),d&&Muse.assets.required.length&&(c+="\nMissing: "+Muse.assets.required.join(",")),alert(c)})()})();
// /* body */
// Muse.Utils.transformMarkupToFixBrowserProblemsPreInit();/* body */
// Muse.Utils.prepHyperlinks(true);/* body */
// Muse.Utils.fullPage('#page');/* 100% height page */
// Muse.Utils.showWidgetsWhenReady();/* body */
// Muse.Utils.transformMarkupToFixBrowserProblems();/* body */
// } catch(e) { if (e && 'function' == typeof e.notify) e.notify(); else Muse.Assert.fail('Error calling selector function:' + e); }});
</script>
   </body>
</html>
