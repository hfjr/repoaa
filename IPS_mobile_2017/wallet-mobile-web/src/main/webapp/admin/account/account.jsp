<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <script type="text/javascript" src="<c:url value='/static/js/jquery-1.8.3.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/static/highcharts/highcharts.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/static/highcharts/exporting.js'/>"></script>
  
  
<!--   <script type="text/javascript" src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>  -->
<!--   <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>  -->
<!--   <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/exporting.js"></script> -->

<body >


<div id="container" style="margin-right: 20px"></div>

</body>


<script type="text/javascript">

	
$(function () {
    
	var height = $(window).height(); //获取窗体高度

	var width= $(window).width(); //获取窗体宽度

	//如果是页面内容高宽可以将window替换为document即可

	 

	//动态修改容器大小

	$("#container").height(height);

	$("#container").width(width);
	
// 	var categories1 =  ['06/12', '06/13', '06/14', '06/15', '06/16', '06/17', '06/18'];

	//x轴的坐标
	var categories1 =new Array();  
	<c:forEach items="${curve.curveData.xDotPlosts }" var="xDotPlosts">
	categories1.push('${xDotPlosts }'); 
	</c:forEach>                    
        
	//y轴的数据        
	var series=new Array();
	<c:forEach items="${curve.curveData.yDotPlosts }" var="yDotPlosts">
	series.push(${yDotPlosts }); 
	</c:forEach>
	
	//y轴坐标
	var tickPositions =new Array();  
	<c:forEach items="${curve.curveData.yPlosts }" var="yPlosts">
	tickPositions.push('${yPlosts }'); 
	</c:forEach>    
	
    $('#container').highcharts({
        credits:false,
        chart: {
            zoomType: 'x',
            marginRight: 20
//             spacingRight: 20
        },
        title: {
            text: ''
        },
//         subtitle: {
//             text: document.ontouchstart === undefined ?
//                 'Click and drag in the plot area to zoom in' :
//                 'Pinch the chart to zoom in'
//         },
       xAxis: {
    	   tickInterval : 1,
    	   labels: {
               formatter: function() {
                   return categories1[this.value];
               }
           }
        },
        yAxis: {
        	minPadding:0,
            title: {
                text: ''
            },
            offset: -10,
            tickPositions: tickPositions
        
        },
        tooltip: {
            shared: false
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                lineWidth: 1,
                marker: {
                    enabled: false
                },
                shadow: false,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },

       series: [{type: 'area',
            data: series  
            //data: [4.63,4.63, 4.62,4.62, 4.21,4.21, 4.10,4.10, 3.9,3.9, 4.5,4.5, 2.1,2.1]  
        }]
    });
    
    
});	
</script>
