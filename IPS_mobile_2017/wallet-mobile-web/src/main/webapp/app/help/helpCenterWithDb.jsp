<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no">
    <title>融桥宝-帮助中心</title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/h5/css/vjmian.css'/>">
    <script type="text/javascript" src="<c:url value='/static/h5/js/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/h5/js/vjmjs.js'/>"></script>
    <script type="text/javascript">
        $(function() {
            var $menu = $(".menu");
            $(".header").click(function() {
                var flag = $(this).next().is(":hidden");
                $menu.hide();
                if (flag) {
                    $(this).next().show(100);
                }
            });
        });
    </script>
    <style>

        * , body{font-family:Helvetica,STHeiti,Droid Sans Fallback;letter-spacing:0;}
        .fix:after{content:".";display:block;height:0;clear:both;visibility:hidden}
        .fix{display:inline-block;min-height:1%}
        *html .fix{zoom:1}
        .fix{display:block}

        .menu {
            display: none;
            background: #F2F2F2;
            width: 100%+18px;
            margin-left: -18px;
            padding: 23px 0 23px 0;
        }

        .menu li {
            color: #5D7A8D;
            margin: 0 40px 0 40px;
            font-size: 0.82em;
            line-height: 1.5em;

        }

        .header {
            margin-bottom: 18px;
        }

        .PbulickZXa {
            padding-bottom: 0;
        }

        a,
        img,
        button,
        input,
        textarea,
        div {
            -webkit-tap-highlight-color: rgba(255, 255, 255, 0);
        }

        img{max-width:100%;}

        .accordion .link{color:#4a4a4a;font-size:16px;font-weight:normal;}

        .submenu-out{background:#fff;margin:0 auto;position:relative;}
        .submenu-padding{padding:0 26px;}
        .submenu-padding table{border-collapse:collapse; width:100%;}
        .submenu-padding table tr{border-bottom:1px solid #f2f2f2;width:100%;}
        .submenu-padding table tr:last-child{border-bottom:0;}
        .submenu-padding table tr:first-child{border-bottom:1px solid #f2f2f2;}
        .submenu-padding table th{color:#000; font-weight:normal;font-size:13px;height:58px;text-align:center;}
        .submenu-padding table th .submenu-font{font-size:13px;}
        .submenu-padding table td{color:#5d7a8d;font-size:12px;height:32px;text-align:center;}
        .submenu-tr1{height:18px;width:26px;position:absolute;top:66px;left:-2px;}
        .submenu-tr1 img{width:100%;}
        .submenu-tr2{height:18px;width:26px;position:absolute;top:100px;left:-2px;}
        .submenu-tr2 img{width:100%;}
        .submenu-tr3{height:18px;width:26px;position:absolute;top:132px;left:-2px;}
        .submenu-tr3 img{width:100%;}
        .submenu-tr4{height:18px;width:26px;position:absolute;top:166px;left:-2px;}
        .submenu-tr4 img{width:100%;}
        .accordion li.open i ,.accordion li.open .link{font-weight:bold;color:#4a4a4a;}
        .PublicBTP {padding: 23px 0 5px 9.375%;}
    </style>
</head>

<body>
<ul class="accordion MarginTop10" id="accordion">


    <c:if test="${helpCenter.footer.status=='200' }">


        <c:forEach var="helpCenterTypeWX"   items="${helpCenter.body.helpCenterTypeWX}">

            <article class="helparticcle">
                <p class="PublicBTP" id="${helpCenterTypeWX.typeCode}">${helpCenterTypeWX.typeName }</p>
            </article>
            <article class="PublicArticle BorderTB3f2">
                <c:forEach var="helpCenterTypeSubWX"   items="${helpCenter.body.helpCenterTypeSubWX}">
                    <c:if test="${helpCenterTypeWX.typeCode ==  helpCenterTypeSubWX.parentCode}">

                        <li>
                            <div class="link">
                                <i class="fa fa-chevron-down"></i> ${helpCenterTypeSubWX.typeSubName}
                            </div>
                            <!-- 答案start -->
                            <ul class="submenu">
                                <li>${helpCenterTypeSubWX.typeSubDetail}

                                    <c:if test="${not empty helpCenterTypeSubWX.typeSubImg}">
                                        <c:if test="${not empty helpCenterTypeSubWX.typeSubDetail  }">
                                            <br><br>
                                        </c:if>

                                        <%--<div><img src="<c:url value='${helpCenterTypeSubWX.typeSubImg }'/>" ></div>--%>
                                        <div><img src="<c:url value='/api/v3.3/help/down/${helpCenterTypeSubWX.typeSubImg }'/>" ></div>
                                    </c:if>
                                </li>
                            </ul>
                            <!-- 答案end-->
                        </li>

                    </c:if>
                </c:forEach>
            </article>
        </c:forEach>

    </c:if>

    <c:if test="${helpCenter.footer.status=='600'  }">
        <article class="helparticcle">
            <p class="PublicBTP">努力维护中...</p>
        </article>

    </c:if>























    <div class="cbdiv"></div>
</ul>
<div style="width:100%;height:60px;"></div>
<article class="PublicArticle BorderT3f2" style="position:fixed;bottom:0;width:100%;margin-bottom:0;">
    <div class="PbulickZXa PbulickZXas fix" style="margin-bottom:8px;border-bottom:0;">
        <a href="tel:${serviceHotline.phone_no}"><img src="<c:url value='/static/h5/img/phoneimg.png'/>" style="float:left;width:27px;height:28px;margin-right:12px;"></a>
        <div style="float:left;">
            <p style="margin-left:0px;float:none;">拨打客服电话: <a style="font-size:1em; color: #4ac0f0;" href="tel:${serviceHotline.phone_no}">${serviceHotline.phone_no}</a></p>
            <br>
            <pre style="width:27px;float:none; font-size:0.750em;margin-top:-6px;">${serviceHotline.service_time}</pre>
        </div>
    </div>
    <div class="cboth"></div>
</article>
</body>
<!-- 百度统计代码 wallet-mobile -->
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        //hm.src = "//hm.baidu.com/hm.js\?b0334162ce58a358a7ea396016a5ddea";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>

</html>
