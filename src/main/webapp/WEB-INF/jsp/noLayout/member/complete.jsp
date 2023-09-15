<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/css/complete.css'/>">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div class="complete" id="link">
    <p class="alert-message">${message}</p>
    <a class="back" href="#">돌아가기</a>
  </div>
  <script>
  	var type1 = '${search.type1}';
  	//var link = document.querySelector('#link a');
  	
  	var href = 'memberWrite.do';
  	
  	if(type1 == 'findid' || type1 =='findpwd') {
  		href = "<c:url value='/member/memberSearchMove.do?type1=" + type1 + "'/>"
  	}
  	
  	$("#link a").on("click", () => {
			window.location.href = href;
		});
  
  </script>
</body>
</html>