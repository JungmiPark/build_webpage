<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>판매량보기</title>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet"/>
	<!-- 참고 http://c3js.org/ -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.3/c3.min.css" rel="stylesheet" />
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/5.9.7/d3.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.3/c3.min.js"></script>
</head>
<body>
	<div class="container">	
		<h4>Chart</h4>	
		<div class="chart1">		
	
		</div>
	</div>

	
	<script type="text/javascript">
		//jquery 라이브러리 사용 시작
		$(function(){
			$.get('/rest/itemorder.json', function(data){
				console.log(data);
				var cnt = data.ret.length;
				var tmp = [];
				for(var i = 0; i<cnt; i++){
					tmp.push([data.ret[i].itemname, data.ret[i].ordercnt]);
				}
				console.log(tmp);
				var chart = c3.generate({
					bindto: '.chart1',
			  	    data: {
			       	 columns: tmp,
			       	 type: 'bar'
			    	}
				}, 'json');			
			});	
		});

			//setTimeout - 1번 / setInterval-주기적으로
			/* setInterval(function () {
				$.get('/rest/itemorder.json', function(data){ 
					var tmp = eval(data.ret);
					console.log(tmp)
					console.log(typeof(tmp)) //String
					chart.load({
			        columns: [
			        	['사과', 30],
			            ['딸기', 50],
			            ['귤', 80]
			        ],
			        type: 'bar'
			   	 }); 
			    }, 'json');
			   			   
			}, 3000);		
		}); */
		// jquery라이브러리 사용 종료
	</script>	
</body>
</html>