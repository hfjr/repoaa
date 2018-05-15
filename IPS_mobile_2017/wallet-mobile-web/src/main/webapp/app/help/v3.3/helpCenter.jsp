<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>帮助中心</title>
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <meta content="no-cache,must-revalidate" http-equiv="Cache-Control">
    <meta content="no-cache" http-equiv="pragma">
    <meta content="0" http-equiv="expires">
    <meta content="telephone=no, address=no" name="format-detection">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

    <link href="<c:url value='/app/help/v3.3/css/reset.css'/>" rel="stylesheet">
    <link href="<c:url value='/app/help/v3.3/css/style.css'/>" rel="stylesheet">
    <script src="<c:url value='/app/help/v3.3/js/jquery-2.1.4.min.js'/>"></script>

</head>
<body class="helpCenter">
<div class="wrap">
    <div class="main">

        <c:forEach var="typeItem" varStatus="status"  items="${typeList}">

            <div class="question-wrap clearfix" id="item-${typeItem.typeCode}">
                <div class="question-left">
                        <%--<div class="question-img-wrap"><img src="<c:url value='/app/help/v3.3/img/icon/${typeItem.typeIconUrl}'/>"></div>--%>
                    <div class="question-img-wrap"><img src="<c:url value='/app/help/v3.3/img/icon/${typeItem.typeIconUrl}'/>"></div>
                    <div>${typeItem.typeName}</div>
                    <div class="up-down"></div>
                </div>
                <ul class="question-right clearfix">

                    <c:forEach var="typeSubItem" items="${typeItem.typeSubList}" varStatus="status">

                        <c:choose>
                            <c:when test="${status.last}">
                                <li class="last"><a href="javascript:void(0);" onclick="detail(${typeSubItem.typeSubId})">${typeSubItem.typeSubName }</a></li>
                            </c:when>
                            <c:otherwise>
                                <li ><a href="javascript:void(0);" onclick="detail(${typeSubItem.typeSubId})">${typeSubItem.typeSubName }</a></li>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </ul>
            </div>

        </c:forEach>

        <%--  <div class="margin-bottom-0 question-wrap  clearfix">
                 <li class="others">
                     <div class="dot-wrap"><img src="<c:url value='/app/help/v3.3/img/iconfont-qita@3x.png'/>"></div>
                     <p>其他问题</p>
                 </li>
         </div> --%>
    </div>
</div>
<script>
    $(function(){
        $('.question-left').click(function(){
            $(this).siblings('.question-right').children(' li:gt(1)'). toggle()
            $(this).siblings('.question-right').children(' li:nth-child(2)').toggleClass('border-bottom')
            $(this).children('.up-down').toggleClass('down')
            $(this).siblings('.question-right').toggleClass('question-right-auto');
            if($(this).siblings('.question-right').hasClass('question-right-auto')){
                var id = $(this).parents(".question-wrap").attr("id");

                window.location.hash =  "#" + id;
            }
        });

        var id = window.location.hash.replace("#", "");
        if(!id && $(".question-wrap").size() > 0){
            id = $(".question-wrap").eq(0).attr("id");
        }
        if(id) {

            $("#" + id).find(".question-left").trigger("click");
        }


    })


    function detail(detailId){

        window.location.href="<c:url value='/api/v3.3/app/user/helpCenterDetail'/>?detailId="+detailId;

    }

</script>
</body>
</html>