<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>Qna</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
		<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css2?family=Raleway:ital@1&display=swap" rel="stylesheet">
		<script type="text/javascript">
		function movewrite() {
			location.href="${root}/main.do?act=mvwrite";
		}
		function searchQuestion() {
			if(document.getElementById("word").value == "") {
				alert("모든 목록 조회!!");
			}
			document.getElementById("searchform").action = "${root}/main.do";
			document.getElementById("searchform").submit();
		}
		function pageMove(pg) {
			document.getElementById("pg").value=pg;
			document.getElementById("pageform").action = "question";
			document.getElementById("pageform").submit();
		}
		</script>
	</head>
	<body>
	<form name="pageform" id="pageform" method="GET" action="">
		<input type="hidden" name="act" id="act" value="question">
		<input type="hidden" name="pg" id="pg" value="">
		<input type="hidden" name="key" id="key" value="${key}">
		<input type="hidden" name="word" id="word" value="${word}">
	</form>
<%@ include file="/WEB-INF/views/header.jsp"%>
	<div class="container">
	<div class="container" align="center" style="margin-top: 5%;">
	  <div align="center">
	  <h2>QnA</h2><br>
	  <c:if test="${userinfo != null}">
	  <table class="table table-borderless">
	  	<tr>
	  		<td align="right"><a href="writeQuestion">글쓰기</a></td>
	  	</tr>
	  </table>
	  </c:if>
	  <c:if test="${questions.size() != 0}">
	  <table class="table table-hover">
	    <tbody>
	      <tr>
	        <th>번호</th>
	        <th>제목</th>
	        <th>작성자</th>
	        <th>좋아요수</th>
	        <th>답글수</th>
	        <th>작성일</th>
	      </tr>
	  	<c:forEach var="question" items="${questions}">
	      <tr>
	        <td>${question.que_no}</td>
	        <td><a href="selectQuestion?que_no=${question.que_no}">${question.title}</a></td>
	        <td>${question.writer}</td>
	        <td>${question.que_like}</td>
	        <td>${rc[question.que_no-1]}</td>
	        <td>${question.create_date}</td>
	      </tr>
	  	</c:forEach>
	    </tbody>
	  </table>
	  <table>
		<tr>
		<td>
		${navigation.navigator}
		</td>
		</tr>
		</table>
	  </c:if>
	  </div>
	</div>
	</div>
	</body>
</html>