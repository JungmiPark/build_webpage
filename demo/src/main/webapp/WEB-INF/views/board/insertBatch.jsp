<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>게시글 일괄등록</title>
	<link
		href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
		rel="stylesheet" />
</head>
<body>
	<div class="container">
		<form action="/board/insertBatch" method="post">
		<table class="table table-sm">
			<c:forEach var="i" begin="1" end="5" step="1">
			<tr>				
				<td><input type="text" name = "title[]" placeholder="글제목" value="제목입니다"/></td>
				<td><input type="text" name = "content[]" placeholder="글내용" value="내용입니다"/></td>
				<td><input type="text" name = "id[]" placeholder="작성자" value="${userid}"  readonly/></td>								
			</tr>
			</c:forEach>
		</table>
		<input type="submit" class="btn btn-success" value="일괄추가" />
		</form>
	</div>

</body>
</html>