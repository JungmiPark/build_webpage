<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시판 글쓰기</title>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
	<div class="container">
		<div style="width: 1000px; padding: 20px; border: 1px solid #cccccc">
			<form action="/board/insert" method="post" enctype="multipart/form-data">
				<div class="form-inline" style="margin: 10px;">
					<label style="width: 100px;">글제목</label> 
					<input type="text" class="form-control" style="width: 800px;" name="brd_title" placeholder="제목 입력하세요." />
				</div>
				<div class="form-inline" style="margin: 10px;">
					<label style="width: 100px;">작성자</label> 
					<input type="text" class="form-control" style="width: 800x;" name="brd_id" value="${userid}" readonly/>
				</div>
				<div class="form-inline" style="margin: 10px;">
					<label style="width: 100px;">내용</label> 
					<textarea id="content" class="form-control" style="width: 800px;" name="brd_content" placeholder="내용을 입력하세요."></textarea>
				</div>
				<div class="form-inline" style="margin: 10px;">
					<label style="width: 100px;">파일</label>
					<input type="file" class="form-control" name="brd_image" placeholder="파일." />
				</div>
				<div class="form-inline" style="margin: 10px;">
					<label style="width: 100px;"></label> 
					<input type="submit" class="btn btn-success" value="작성" />&nbsp; 
						<a href="${pageContext.request.contextPath}/board/list"	class="btn btn-secondary">목록으로</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>