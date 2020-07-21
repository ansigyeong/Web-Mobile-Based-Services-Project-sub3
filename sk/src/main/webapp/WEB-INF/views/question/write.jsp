<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<c:if test="${userinfo == null}">
	<c:redirect url="/main.do"/>
</c:if>
<c:if test="${userinfo != null}">
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>QnA작성</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
  <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Raleway:ital@1&display=swap" rel="stylesheet">
  <script type="text/javascript">
  function writeQuestion() {
	if(document.getElementById("title").value == "") {
		alert("제목 입력!!!!");
		return;
	} else if(document.getElementById("lang").value == "") {
		alert("주언어 입력!!!!");
		return;
	} else if(document.getElementById("contents").value == "") {
		alert("내용 입력!!!!");
		return;
	} else {
	  	document.getElementById("writeform").action = "writeQuestion";
	  	document.getElementById("writeform").submit();
	}
  }
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp"%>
<div class="container">
<div class="container" align="center">
	<div class="col-lg-10" align="center">
		<h2>QnA 작성</h2>
		<form id="writeform" method="post" action="writeQuestion">
		<input type="hidden" name="act" id="act" value="writequestion">
		<input type="hidden" name="writer" id="writer" value="${userinfo.uid}">
			<div class="form-group" align="left">
				<label for="title">제목:</label>
				<input type="text" class="form-control" id="title" name="title">
			</div>
			<div class="form-group" align="left">
				<label for="lang">주 언어:</label>
				<input type="text" class="form-control" id="lang" name="lang">
			</div>
			<div class="form-group" align="left">
				<label for="contents">내용:</label>
				<textarea class="form-control" rows="15" id="contents" name="contents"></textarea>
			</div>
			<button type="reset" class="btn btn-warning">초기화</button>
			<input type="submit" class="btn btn-primary" value="글작성">
		</form>
	</div>
</div><br>
<div align="center" style="text-align:center;">
</div>
</div>
</body>
</html>
</c:if>