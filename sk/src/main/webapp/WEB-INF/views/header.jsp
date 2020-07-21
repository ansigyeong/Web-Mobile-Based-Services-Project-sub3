<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Raleway:ital@1&display=swap" rel="stylesheet">

<c:if test="${userinfo == null}">
<div style="width:70%; height: 30px; float:right; text-align:right; padding-right: 2%;">
<a href="login" style="color: white">로그인</a> &emsp;
<a href="signin" style="color: white">회원가입</a>
</div>
</c:if>
<c:if test="${userinfo != null}">
<div style="width:70%; height: 30px; float:right; text-align:right; padding-right: 2%;">
<strong>${userinfo.uid}</strong>님 환영합니다. &emsp;
<a href="logout" style="color: white">로그아웃</a> &emsp;
</div>
</c:if>
<div align="center" style="text-align:center;">
<header id="index_header" class="jumbotron text-center text-white mb-1" style="background-color: #66ccff;">
<h1 style="text-align:center; font-weight: bold; fontsize: 8vmin; font-family: 'Raleway', sans-serif;">blog</h1><br>
</header>
<nav class="navbar navbar-expand-sm justify-content-center bg-primary navbar-dark rounded" style="margin-bottom: 5%; text-align: center;">

</nav>
</div>