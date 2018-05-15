<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no,email=no,address=no"/>
    <title>关于小金鱼</title>
    <link href="<c:url value='/static/v-client/v-client/static/css/base.css?201608261136'/>" rel="stylesheet">
    <style>
        .fix:after{content:".";display:block;height:0;clear:both;visibility:hidden}
        .fix{display:inline-block;min-height:1%}
        *html .fix{zoom:1}
        .fix{display:block}
        .answer .col-left{width:25px; box-sizing:border-box;padding-left:8px; float:left; margin-left:-100%;}
        .answer .col-right{float:left; width:100%; }
        .answer .col-right .con{padding-left:25px;}
        .bottom-img-wrap2{width:100%; display:block; height:auto;}

    </style>
    <script src="<c:url value='/static/v-client/v-client/static/lib/jquery.js'/>"></script>
    <script src="<c:url value='/static/v-client/v-client/static/lib/fastclick.js'/>"></script>
    <script src="<c:url value='/static/v-client/v-client/static/js/base.js'/>"></script>
    <script src="<c:url value='/static/v-client/v-client/static/js/index.js'/>"></script>
</head>
<body class="index AboutGoldfish">
<div class="detail">
    <h2 >关于小金鱼</h2>
    <div class="question">
        <p><span>Q: 鱼池是什么？ </span></p>
    </div>
    <div class="answer fix">
        <div class="col-right">
            <div class="con">按照任务卡完成任务，即可获得更多小金鱼，每条价值10000元。</div>
        </div>
        <span class="col-left">A: </span>
    </div>
    <div class="question">
        <p><span>Q: 怎么用小金鱼投资？   </span></p>
    </div>
    <div class="answer fix">
        <div class="col-right">
            <div class="con">当鱼池内有至少一条小金鱼时，您就可以用小金鱼投资我们为您准备的财富管理计划，最高收益达10%。</div>
        </div>
        <span class="col-left">A: </span>
    </div>


    <div class="question">
        <p><span>Q: 怎么让鱼池中的小金鱼变更多?   </span></p>
    </div>
    <div class="answer fix">
        <div class="col-right">
            <div class="con">单月投资金额超过10万元，鱼池中将会自动增加一条价值10000元的小金鱼，且鱼池中的小金鱼没有累积上限。</div>
        </div>
        <span class="col-left">A: </span>
    </div>

    <div class="question">
        <p><span>Q: 小金鱼收益怎么算?   </span></p>
    </div>
    <div class="answer fix">
        <div class="col-right">
            <div class="con">“小金鱼”是融桥宝用户专享的投资贷款体验金。您可以用"小金鱼”投资融桥宝中的短期财富管理计划。实际收益为“待收利差”=理财收益-应付利息。</div>
        </div>
        <span class="col-left">A: </span>
    </div>
    <img class="bottom-img-wrap2" src="<c:url value='/static/v-client/v-client/static/img/AboutGoldfish/bottomPic.png'/>" >
</div>

</body>
</html>