<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript" src="<c:url value='/static/js/jquery-1.8.3.min.js'/>"></script>
 <script type="text/javascript" src="<c:url value='/static/highcharts/highcharts.js'/>"></script>
<body style="margin: 0px;">
<div id="container" ></div>
</body>

<div id='loading-mask'></div>
<div id="loading">
    <div class="loading-indicator" style="display: none">
       
      <br/><span id="loading-msg" style="display: none">
	   </span>
    </div>
</div>

<style type="text/css">
#loading-mask{
        position:absolute;
        left:0;
        top:0;
        width:100%;
        height:100%;
        z-index:20000;
        /*background-color:gray;*/
    }
    #loading{
        position:absolute;
        left:45%;
        top:40%;
        padding:2px;
        z-index:20001;
        height:auto;
 }
    #loading .loading-indicator{
        background:white;
        color:#444;
        font:bold 20px tahoma,arial,helvetica;
        padding:10px;
        margin:0;
        height:auto;
    }
    #loading-msg {
        font: normal 18px arial,tahoma,sans-serif;
    }
</style>
<script type="text/javascript">
	
$(function () {
	var height = $(window).height(); //获取窗体高度
	var width= $(window).width(); //获取窗体宽度
	//如果是页面内容高宽可以将window替换为document即可

	//动态修改容器大小
	$("#container").height(height);
	$("#container").width(width);

	//x轴的坐标
	var categories1 =new Array();  
	<c:forEach items="${xPlosts }" var="xPlost">
	categories1.push('${xPlost }'); 
	</c:forEach>                    
	
	//y轴坐标
	var tickPositions =new Array();  
	<c:forEach items="${yPlosts }" var="yPlost">
	tickPositions.push('${yPlost }'); 
	</c:forEach>    
        
	//y轴的数据        
	var series=new Array();
	<c:forEach items="${yDotPlosts }" var="yDotPlost">
	series.push( ${yDotPlost }); 
	</c:forEach>
    $('#container').highcharts({
        credits:false,
        chart: {
        	type: 'spline',
            marginRight: 20
        },
        title: {
            text: ''
        },
       xAxis: {
    	   tickInterval : ${tickInterval },
    	   labels: {
               formatter: function() {
                   return categories1[this.value];
               },
               style: {
            	   color: '#A8A8A8', //刻度字体颜色
            	   fontFamily: '宋体',
            	   fontSize:'8px' //刻度字体大小 
           	   }
           },
           gridLineColor : '#EDEDED' 
        },
        yAxis: {
        	minPadding:0, 
            title: {
                text: ''
            },
            offset: -10,
            tickPositions: tickPositions,
            labels: {
				formatter:function(){
					if(this.value == 0){
						return 0;
					}
					return Highcharts.numberFormat(this.value, 1);
				},
            	style: {
             	   color: '#A8A8A8', //刻度字体颜色
             	   fontFamily: '宋体',
             	   fontSize:'8px' //刻度字体大小
            	}
            },
            gridLineColor : '#EDEDED' 
        },
        tooltip: {
            shared: false,
            style:{
            	display:'none'
           	}
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
        }]
    });
     
    Highcharts.setOptions({ 
        colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', 
    '#FFF263', '#6AF9C4'] 
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
