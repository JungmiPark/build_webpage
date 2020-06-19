<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% pageContext.setAttribute("newLineChar", "\n"); %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>글 내용</title>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
 	<div class="container">
 	<%@ include file="/WEB-INF/views/menu.jsp" %>
 		<table class="table table-sm">
				<tr>				
					<th>글번호</th>
					<td class="tdbrdno">${obj.brd_no}</td>										
				</tr>
				<tr><th>글제목</th>
					<td>${obj.brd_title}</td>
				</tr>
				<tr>
					<th>글내용</th>
					<td>${fn:replace(obj.brd_content, newLineChar, "<br />")}</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${obj.brd_id}</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${obj.brd_hit}</td>
				</tr>
				<tr>
					<th>날짜</th>
					<td>${obj.brd_date}</td>
				</tr>			
		</table> 	
	
		<img src="${pageContext.request.contextPath}/board/getimg?no=${obj.brd_no}" width="200px" height="200px"><br />
		<hr />
		<a href="${pageContext.request.contextPath}/board/list" class="btn btn-success">목록</a>
		<a href="${pageContext.request.contextPath}/board/update?no=${obj.brd_no}" class="btn btn-success">수정</a>
		<a href="#" class="btn btn-success mydeletebtn">삭제</a>
		
		<c:if test="${prev != 0}">
		<a href="${pageContext.request.contextPath}/board/content?no=${prev}" class="btn btn-success">이전글</a>
		</c:if>
		<c:if test="${next != 0}">
		<a href="${pageContext.request.contextPath}/board/content?no=${next}" class="btn btn-success">다음글</a>
		</c:if>
	</div>
	
	
	<script src="${pageContext.request.contextPath}/resources/js/jquery-3.5.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
	<script type="text/javascript">
		//jquery 라이브러리 사용 시작
		$(function(){
			
			$('.mydeletebtn').click(function(){
				var idx = $(this).index('.mydeletebtn');
				var no = $('.tdbrdno').eq(idx).text();

				Swal.fire({
					title: '삭제확인',
					text: "삭제하시겠습니까?",
					icon: 'warning',
					showCancelButton: true,
					confirmButtonColor: '#3085d6',
					cancelButtonColor: '#d33',
					confirmButtonText: '확인',
					cancelButtonText: '취소'
				}).then((result) => {
					if (result.value) {
						window.location.href="/board/delete?no=" + no;
					}
				});
			});
		}); 
		// jquery라이브러리 사용 종료
	</script>
</body>
</html>