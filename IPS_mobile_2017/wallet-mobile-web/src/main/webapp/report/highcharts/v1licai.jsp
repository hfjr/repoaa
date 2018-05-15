<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript" src="<c:url value='/static/js/jquery-1.8.3.min.js'/>"></script>
 <script type="text/javascript" src="<c:url value='/static/highcharts/highcharts.js'/>"></script>
<body style="margin: 0px;">
<div id="container"></div>
</body>

<script type="text/javascript">
	
$(function () {
	var height = $(window).height(); //获取窗体高度
	var width= $(window).width(); //获取窗体宽度
	//如果是页面内容高宽可以将window替换为document即可

	//动态修改容器大小
	$("#container").height(height);
	$("#container").width(width);
	
	var categories = [];
	<c:forEach items="${categories}" varStatus="i" var="item">categories.push('${item }');</c:forEach>
	var huobiPositions = [];
	<c:forEach items="${huobiPositions}" varStatus="i" var="item">huobiPositions.push(${item });</c:forEach>

	$('#container').highcharts({
		chart: {  
            type: 'spline'
        },  
        title: {
            text: ''
        },
        exporting: {
            enabled:false
		},
        credits: {
          enabled:false
        },
        xAxis: {
            categories: categories,
    	   labels: {
               style: {
            	   color: '#A8A8A8', //刻度字体颜色
            	   fontFamily: '宋体',
            	   fontSize:'8px' //刻度字体大小 
           	   }
           },
           gridLineColor : '#EDEDED' 
        },
        yAxis: {
            tickPositions: [0, 2,4, 6,8,10],
            title: {
                text: ''
            },
            labels: {
            	style: {
             	   color: '#A8A8A8', //刻度字体颜色
             	   fontFamily: '宋体',
             	   fontSize:'8px' //刻度字体大小
            	}
            },
            gridLineColor : '#EDEDED' 
        },
        tooltip: {
            valueSuffix: '%'
        },
        legend: {
            //layout: 'vertical',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0
        },
//         labels:{//在报表上显示的一些文本  
//             items:[{  
//                 html:'v+理财',  
//                 style:{left:'20px', 'top':'50px'}  
//             }, {  
//                 html:'货币基金',  
//                 style:{left:'20px',top:'180px'}   
//             }, {  
//                 html:'银行活期',  
//                 style:{left:'20px',top:'270px'}   
//             }]  
//         },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        colors:[
            '#FB6420',
            '#F5A722',
            '#4AC0F0'],
        series: [{
            name: 'v+理财',
            data: [8, 8, 8, 8, 8, 8, 8]
        }, {
            name: '货币基金',
            data: huobiPositions
        }, {
            name: '银行活期',
            data: [0.35, 0.35, 0.35, 0.35, 0.35, 0.35, 0.35]
        }]
    });
     
});	
</script>
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
