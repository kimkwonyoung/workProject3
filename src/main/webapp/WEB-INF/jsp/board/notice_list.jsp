<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="<c:url value='/css/board.css'/>">

  <title>게시판 목록</title>
</head>
<body>

<form name="mForm" id="mForm" action="<c:url value='/admin/noticeList.do'/>" method="post" >
	
  <div class="list-all" id="container">
    <div class="board-header">
      <div class="link-header">
          <a class="write-button" id="del">글삭제</a>
	      <a class="write-button" id="wr" href="#">글쓰기</a> 
      </div>
    </div>

    <table>
    
    <caption class="board-title">공지사항 게시판</caption>
      <thead>
        <tr>
          <th class="checkbox-all"><input type="checkbox" id="checkAll"></th>
          <th>글번호</th>
          <th>글제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="notice" items="${result.noticeList }">
       <tr class="highlight">
      	  <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox" value="${notice.noticeNum }"></td>
          <td>${notice.noticeNum }</td>
          <td><a href="<c:url value='boardInfo.do?board_num=${notice.noticeNum }'/>">${notice.title}</a></td>
          <td>${notice.memId }</td>
          <td>${notice.modDate }</td>
          <td>${notice.viewCount }</td>
       </tr>
      </c:forEach>
       <c:forEach var="board" items="${result.list}">
        <tr>
          <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox" value="${notice.noticeNum }"></td>
          <td>${board.noticeNum }</td>
          <td><a href="<c:url value='boardInfo.do?board_num=${board.noticeNum }'/>">${board.title}</a></td>
          <td>${board.memId }</td>
          <td>${board.modDate }</td>
          <td>${board.viewCount }</td>
        </tr>
        </c:forEach>
      </tbody>
      <c:if test="${search.totalCount <= 0}">
	  <tr>
	      <td colspan=6>검색결과가 없습니다</td>
	  </tr>
	  </c:if>
    </table>
   <div class="tableDiv">
   	<div class="selectBox">
	<select name="scRecodeCount" id="pageLength" class="pagelen" >
		<option value="10" ${search.scRecodeCount == 10 ? 'selected="selected"' : ''} >10건</option>
		<option value="20" ${search.scRecodeCount == 20 ? 'selected="selected"' : ''} >20건</option>
		<option value="50" ${search.scRecodeCount == 50 ? 'selected="selected"' : ''} >50건</option>
		<option value="100" ${search.scRecodeCount == 100 ? 'selected="selected"' : ''} >100건</option>
	</select>
    </div>  
  </div>
  <%@ include file="/WEB-INF/jsp/common/inc-paging-admin.jsp"%>
    
    <div style="margin:0px auto; margin-top: 20px;">
		<div style="display: flex; margin:0px auto; width:60%; justify-align: center">
			<div class="selectBox">
			<select name="type1" class="searchType" >
				<option value="title">제목</option>
				<option value="mem_id">작성자</option>
				
			</select>
			</div>
			<input type="text" name="searchWord" id="searchTitle" value="${search.searchWord}" style="flex:1">
			<input type="submit" value="검색" class="searchButton"/>
		</div>
	
	</div>
  </div>
</form>
<!-- 게시판 글 작성하기  -->
<div id="dialogWrite" title="글 쓰기">
<label for="title">제목:</label><br>
      <input type="text" id="writeTitle" name="title" class="form-input"><br>

      <label for="content">내용:</label><br>
      <textarea id="writeContent" name="content" rows="10" class="form-input"></textarea><br>
      
      <input type="checkbox" id="fixed-yn" name="fixedYn" value="Y">
      <label class="" id="fixed">상단 고정</label>
</div>

<!-- 게시판 글 수정하기  -->
<div id="dialogUpdate" title="글 수정">
<label for="title">제목:</label><br>
      <input type="text" id="updateTitle" name="title" class="form-input"><br>

      <label for="content">내용:</label><br>
      <textarea id="updateContent" name="content" rows="10" class="form-input"></textarea><br>
      <input type="checkbox" id="fixed-yn" name="fixedYn" value="Y">
      <label class="" id="fixed">상단 고정</label>
</div>

<!-- 게시판 글 상세보기 -->
<div id="dialogInfo" title="">
<input type="hidden" id="infoNoticenum" value="">
	<div>
      <p class="post-meta" id="post-meta">작성자: <span id="author"></span> | 작성일: <span id="date"></span> | 조회수: <span id="views"></span></p>
      <div class="post-content" id="post-content">
      </div>
    </div>
</div>

	
<script>
$(document).ready(function() {
    $("#dialogInfo").dialog({
        autoOpen: false,
        modal: true,
        width: 1000,
        height: 800,
        buttons: {
        	"글 수정": function() {
        		const noticenum = $("#infoNoticenum").val();
    			openUpdate(noticenum);
    		},
            "Close": function() {
                $(this).dialog("close");
            }
    		
        }
    });
    $("#dialogWrite").dialog({
        autoOpen: false,
        modal: true,
        width: 800,
        height: 500,
        buttons: {
        	"글 작성": function() {
        		addWrite();
        		$(this).dialog("close");
        	},
            "Close": function() {
                $(this).dialog("close");
            }
        }
    });
    $("#dialogUpdate").dialog({
        autoOpen: false,
        modal: true,
        width: 800,
        height: 500,
        buttons: {
        	"글 수정 완료": function() {
        		const noticenum = $("#dialogUpdate").data("noticenum");
        		addUpdate(noticenum)
        		$(this).dialog("close");
        	},
            "Close": function() {
                $(this).dialog("close");
            }
        }
    });
});

function addUpdate(noticenum) {
	const param = {
	        noticeNum: noticenum,
	        title: $("#updateTitle").val(),
	        content: $("#updateContent").val(),
	        fixedYn: $("#fixed-yn").val()
	      };
    
	$.ajax({
		url: "<c:url value='/admin/noticeUpdate.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
			location.reload();
		}
	});
	
	$("#dialogInfo").dialog("close");
	
}

function openUpdate(noticenum) {
	console.log(noticenum)
	const param = {
	        noticeNum: noticenum,
	      };
    
	$.ajax({
		url: "<c:url value='/admin/noticeUpdateInfo.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
	       	const notice = json.noticeInfo;
	   		$("#updateTitle").val(notice.title);
	   		$("#updateContent").html(notice.content);
	   		if (notice.fixedYn === "Y") {
	   			$("#fixed-yn").prop("checked", true);
	   		}
   		  
		}
	});
	$("#dialogUpdate").data("noticenum", noticenum).dialog("open");
	
}

function addWrite() {
	const param = {
			title: $("#writeTitle").val(),
			content: $("#writeContent").val(),
			mem_id: "admin"
	      };
    
	$.ajax({
		url: "<c:url value='/board/ajaxWrite2.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
		const list = json.boardAjaxOne;	
		const boardItem = $("#boardItem");
		const boardListHTML = $("#board_list");
		  
		  for (let i=0; i<list.length; i++) {
			  const board = list[i];
			  const newBoardItem = boardItem.clone(true);
			  const title = newBoardItem.find("#title");
			  
		   	  title.text(board.title);
		   	  title.on("click", () => info(board.board_num));
		      
		   	  newBoardItem.find("#boardN").text(board.board_num);
		      newBoardItem.find("#memId").text(board.mem_id);
		      newBoardItem.find("#mod_date").text(board.mod_date);
		      newBoardItem.find("#view_count").text(board.view_count);
		
		   	  newBoardItem.show();
			  boardListHTML.prepend(newBoardItem);
		  }
			
		}
	});
	
}

function info(noticenum) {
    const param = {
	        noticeNum: noticenum,
	      };
    
	$.ajax({
		url: "<c:url value='/board/ajaxInfo2.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
       	  const infoList = json.boardInfo;
       	  for (let i=0; i<infoList.length; i++) {
       		  const info = infoList[i];
       		  
       		  $("#infoNoticenum").val(info.noticeNum);
       		  $("#author").text(info.mem_id);
       	      $("#date").text(info.mod_date);
       	      $("#views").text(info.view_count);
       		  $("#post-content").html(info.content);
       		  $("#dialogInfo").dialog("option", "title", info.title);
       	  }
			
		}
	});
	$("#dialogInfo").dialog("open");
}

$("#wr").on("click", () => {
	$("#writeTitle").text("");
	$("#writeContent").text("");
	$("#dialogWrite").dialog("open");
})

// $.Nav('parent', './notice_list.do', {bseq:'', srcWorkTpCd:''}, null, true);
// $.Nav('go', './price_proc.do',{srcPriceSeq:seq, status:'d',});

// function goPage(index) {
// 	$.Nav('go', location.pathname, {'cpage': index, });
// }

$("#mForm").on("submit", () => {
	return true;
});




//체크 박스 전체 선택
$("#checkAll").on("click", () => {
	$(".chkbox").prop("checked", $("#checkAll").prop("checked"));
});

// var board_code =

//체크 박스 선택한것 삭제
$("#del").on("click", (event) => {
	if (confirm('정말로 삭제 하시겠습니까?')) {
		var deleteStr = "";
		$("input[name='chkBoardNum']").each((i, chk) => {
		    if ($(chk).prop("checked")) {
		        deleteStr += $(chk).val() + ',';
		    }
		});
		if (deleteStr === '') {
			alert('삭제할 글을 선택 하세요.');
		} else {
			deleteStr = deleteStr.substr(0, deleteStr.length - 1);
			window.location.href = 'boardDeleteChkbox.do?board_code=' + board_code + '&bnumStr=' + deleteStr;
		}
		
	} else {
        event.preventDefault();
        return false;
    }
});

/* var deleteLink = document.getElementById('del');
deleteLink.addEventListener('click', (event) => {		
	if (confirm('정말로 삭제 하시겠습니까?')) {
		var deleteStr = '';
		var chkb = document.getElementsByName('chkBoardNum');
		chkb.forEach((chkbox) => {
			if (chkbox.checked) deleteStr += chkbox.value + ','
		});
		
		if (deleteStr == '') {
			alert('삭제할 글을 선택 하세요.');
		} else {
			deleteStr = deleteStr.substr(0, deleteStr.length - 1);
			window.location.href = 'boardDeleteChkbox.do?board_code=' + board_code + '&bnumStr=' + deleteStr;
		}
	} else {
        event.preventDefault();
        return false;
    }
	
}); */
   
   
</script>
</body>
</html>