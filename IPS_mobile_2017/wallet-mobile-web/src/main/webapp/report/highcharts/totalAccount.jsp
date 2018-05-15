<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
 <script type="text/javascript" src="<c:url value='/static/js/jquery-1.8.3.min.js'/>"></script>
 <script type="text/javascript" src="<c:url value='/static/highcharts/highcharts.js'/>"></script>
</head>
<body style="margin: 0px;">
	<img id="img" style="display: none;" src="<c:url value='/report/highcharts/ceshi.png' />"> 
	<div id="container"></div>
</body>

<script>
$(function () {
	var height = $(window).height(); //获取窗体高度
	var width= $(window).width(); //获取窗体宽度
	//如果是页面内容高宽可以将window替换为document即可

	var data = [];
	<c:forEach items="${data}" varStatus="i" var="item">data.push({name:'${item.name}', y:${item.amount}, realName:'${item.name}'});</c:forEach>
	
	//动态修改容器大小
	$("#container").height(height);
	$("#container").width(width);
    var chars = $('#container').highcharts({
    	chart: {
            type: 'pie'
            
        },
        exporting: {
            enabled:false
		},
        credits: {
          enabled:false
        },
        title: {
            text: '',
            //verticalAlign:''
        },
        subtitle: {
            text: ''
        },
        legend: {
	        	//layout: 'vertical',
	            align: 'center',
	            verticalAlign: 'bottom',
	            x: 0,
	            y: 0,
	            borderWidth: 0,
	            itemMarginTop:0,
	        	symbolPadding:3,
	        	symbolRadius:3,
	        	symbolWidth:13,
	            labelFormatter: function() {
	                return "<div align='center'>"+this.name+'<br/>  '+Highcharts.numberFormat(this.y, 2)+'元</div>';
	            },
	            useHTML:true
            },
        plotOptions: {
            pie: {
                innerSize: '86%',
                depth: 35,
                allowPointSelect: false,
                showInLegend: true,
                point :{
	               	events: {
	                   //监听图例的点击事件，为0时候不让点击
	                   legendItemClick: function (event) {
	                       if(this.y == 0){
	                       	   return false;
	                       }
	                       return true;
	                   }
	                 }
               	 }
            }
        },
         colors:[
            '#FB6420',
            '#F5A722',
            '#4AC0F0'],
        series: [{
            name: '账户金额',
            data: data
        }]
    });
	
    var chart = chars.highcharts();
    var isShow = true;
    $.each(data, function(index){
    	if(data[index]["y"] == 0){
		    chart.series[0].data[index].setVisible();
    	}else {
    		isShow = false;
    	}
    });
    if(isShow){
    	$("#img").show();
    	$("#container").remove();
    	//$("#img").height(height);
    	$("#img").width(width);
    }
});
</script>
</html>

