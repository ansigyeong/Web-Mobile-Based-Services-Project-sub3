<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>회원가입</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Raleway:ital@1&display=swap" rel="stylesheet">
<script type="text/javascript">
function signin() {
	if(document.getElementById("uid").value == "") {
		alert("아이디를 입력해주세요");
		return;
	} else if(document.getElementById("name").value == "") {
		alert("이름을 입력해주세요");
		return;
	} else if(document.getElementById("email").value == "") {
		alert("이메일을 입력해주세요");
		return;
	} else if(document.getElementById("pw").value == "") {
		alert("비밀번호를 입력해주세요");
		return;
	} else if(document.getElementById("lang").value == "") {
		alert("주언어을 입력해주세요");
		return;
	} else {
		document.getElementById("userform").action = "${root}/main.do";
		document.getElementById("userform").submit();
	}
}
</script>
</head>
<body>
<div class="container">
<%@ include file="/WEB-INF/views/header.jsp"%>
<div class="container" align="center">
	<div class="col-lg-6" align="center">
		<form id="userform" method="post" action="signin">
		<input type="hidden" name="act" id="act" value="signin">
			<br><h2>회원 가입</h2>
			<div class="form-group" align="left">
				<label for="">아이디</label>
				<input type="text" class="form-control" id="uid" name="uid" placeholder="">
			</div>
			<div class="form-group" align="left">
				<label for="">이름</label>
				<input type="text" class="form-control" id="name" name="name" placeholder="">
			</div>
			<div class="form-group" align="left">
				<label for="email">이메일</label>
				<input type="text" class="form-control" id="email" name="email" placeholder="">
			</div>
			<div class="form-group" align="left">
				<label for="">비밀번호</label>
				<input type="password" class="form-control" id="pw" name="pw" placeholder="영문/숫자 포함">
			</div>			
			<div class="form-group" align="left">
				<label for="">이미지</label>
				<input type="text" class="form-control" id="img" name="img" placeholder="img경로">
			</div>
			<div class="form-group" align="left">
				<label for="">주언어</label>
				<input type="text" class="form-control" id="lang" name="lang" placeholder="">
			</div>
			
			<div class="form-group" align="center">
				<input type="submit" value="회원가입">
				<button type="reset" class="btn btn-warning">초기화</button>
				<a type="button" class="btn btn-secondary" href="main">취소</a>
			</div>
		</form>
	</div>
</div>
</div>
</body>
</html>