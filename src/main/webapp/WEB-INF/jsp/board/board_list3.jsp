<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <link rel="stylesheet" href="<c:url value='/css/jqgrid/daterangepicker.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/css/jqgrid/ui.jqgrid.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jqgrid/jquery.jqGrid.min.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/js/jqgrid/moment-with-locales.min.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/js/jqgrid/moment.min.js'/>" ></script>
</head>
<body>

<table id="jqGrid"></table>
<div id="gridpager"></div>

<script>
$(document).ready(function() {

$("#jqGrid").jqGrid({
	url : "<c:url value='/board/boardList3.do'/>",
	datatype : "json",
	mtype : "post",
	colNames : ["글번호", "글제목", "작성자", "작성일", "조회수"],
	colModel : [
		{
			name : "boardNum",
			index : "boardNum",
			width : 50,
			align : center,
		},
		{
			name : "title",
			index : "title",
			width : 50,
			align : center,
		},
		{
			name : "memId",
			index : "memId",
			width : 50,
			align : center,
		},
		{
			name : "modDate",
			index : "modDate",
			width : 50,
			align : center,
		},
		{
			name : "viewCount",
			index : "viewCount",
			width : 50,
			align : center,
		},
			
		],
	loadtext : "데이터 로딩중",
	caption : "일반 게시판",
	postData : {
			//데이터를 받아올 때 넘길 변수를 설정해줍니다.
			data1 : "data1";
			data2 : "data2";
		}
});
});
</script>
</body>



</html>