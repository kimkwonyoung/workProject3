<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script src="<c:url value='/js/jsrender.js'/>"></script>
<script src="<c:url value='/js/jsviews.js'/>"></script>
<script src="<c:url value='/js/jquery.locator.js'/>"></script>
<script src="<c:url value='/js/jquery.oLoader.min.js'/>"></script>
<script src="<c:url value='/js/jsUtil.js'/>"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">

<head>
<meta charset="UTF-8">

</head>
<body>
	<div id="container">
		<div id="header">
			<tiles:insertAttribute name="header"/>	
		</div>
		<div id="content">
			<tiles:insertAttribute name="body"/>
		</div>		
	</div>
</body>
</html>