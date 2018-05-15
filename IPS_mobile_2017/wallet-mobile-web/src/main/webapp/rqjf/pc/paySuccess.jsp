<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0">
              </head>
    
    <body >
       
        
        <script>

      
			function closewin() {
				self.opener = null;
				self.close();
			}
			function clock() {
				i = i - 1;
				document.title = "本窗口将在" + i
						+ "秒后自动关闭!";
				if (i > 0)
					setTimeout("clock();", 1000);
				else
					closewin();
			}
			var i = 5;
			clock();
		</script>
  
        <div id='buttons'></div> <div id='log'></div>
        
    </body>
</html>