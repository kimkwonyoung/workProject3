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

<form name="mForm" id="mForm" action="<c:url value='/board/boardList.do'/>" method="get" >
	
  <div class="list-all" id="container">
    <div class="board-header">
      <div class="link-header" >
          <a class="write-button" id="del" style="display:none" >글삭제</a>
	      <a class="write-button" id="wr"  style="display:none" >글쓰기</a> 
      </div>
    </div>
    

    <table id="boardTable">
    <caption class="board-title">일반 게시판</caption>
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
      <tbody id="noticelist">
      <c:forEach var="notice" items="${result.noticeList }">
       <tr class="highlight">
      	  <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox" value="${notice.noticeNum }"></td>
          <td>${notice.noticeNum }</td>
          <td style="text-align: left;"><a href="#" onclick="info(${notice.noticeNum}, 'notice')">${notice.title}</a></td>
          <td>${notice.memId }</td>
          <td>${notice.modDate }</td>
          <td>${notice.viewCount }</td>
       </tr>
      </c:forEach>
      </tbody>
      <tbody id="boardbottom">
      
       <c:forEach var="board" items="${result.list}">
        <tr>
          <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox" value="${board.boardNum }"></td>
          <td>${board.boardNum }</td>
          <td style="text-align: left;">
          <span style="padding-left:${(board.level-1) * 20}px"></span>
          ${board.level != 1 ? "[답변] " : ""}
          <a href="#" onclick="info(${board.boardNum}, 'board')">${board.title}</a>
          </td>
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
<form id="fileForm">
<label for="title">제목:</label><br>
	  <input type="hidden" name="memId" value="${loginMember.memberid }" >
      <input type="text" id="writeTitle" name="title" class="form-input"><br>

      <label for="content">내용:</label><br>
      <textarea id="writeContent" name="content" rows="10" class="form-input"></textarea><br>

    <input type="button"  value="파일추가" onClick="fn_addFile()"/><br>
	<div id="d_file">
		<input  type='file' name='file' />
	</div>
</form>
</div>

<!-- 게시판 글 수정하기  -->
<div id="dialogUpdate" title="글 수정">
<label for="title">제목:</label><br>
      <input type="text" id="updateTitle" name="title" class="form-input"><br>

      <label for="content">내용:</label><br>
      <textarea id="updateContent" name="content" rows="10" class="form-input"></textarea><br>
</div>

<!-- 게시판 글 상세보기 -->
<div id="dialogInfo" title="">
<input type="hidden" id="infoboardnum" value="">
<input type="hidden" id="infoboardmemid" value="">
	<div>
      <p class="post-meta" id="post-meta">작성자: <span id="author"></span> | 작성일: <span id="date"></span> | 조회수: <span id="views"></span></p>
      <div class="post-content" id="post-content">
      </div>
      <div id="fileList" style="margin-top: 20px; padding: 5px;">
      </div>
    </div>

<div class="comment-form" >
    <h3 id="comment-count"></h3>
    <div class="comment-form" id="comment-tmpl">
    </div>
</div>

    <div id="comment" style="display:none">
    	 <h3 id="comment-memid"></h3>
	    <div class="comment-form">
        <form id="commentForm" >
        	<textarea id="write-detail" name="detail" rows="4" cols="50" placeholder="댓글 내용"></textarea>
        	<a class="write-comment" href="#">댓글 작성</a>
   	 	</form>
	    </div>
	    
    </div>
    	<div style="text-align: center; margin-top:10px">
    	<input type="button" id="moreBtn" value="더보기" />
    </div>

    
</div>

<c:set var="loginmem" value="${loginMember }" />
<c:set var="loginmemid" value="${loginMember.memberid }" />

<script id="fileTemplate" type="text/x-jsrender">
{{if ~containsImage(contentType, "image")}}
    <img src="<c:url value='/board/download.do?fileNo={{:file.fileNo}}'/>">
{{else}}
<span style="display: block;"><a style="color:blue" href="<c:url value='/board/download.do?fileNo={{:file.fileNo}}'/>">{{:file.fileNameOrg}}</a></span><br/> 
{{/if}}
</script>

<script id="commentTemplate" type="text/x-jsrender">
<div class="comment-list">
	<h5>{{:memId }}</h5>
	<div class = "comment-area">
		<div class="commentdetail">
			<span class="comment-content">{{:detail }}</span>
		</div>
		<div class="up-del-link">
			<h5 class="comment-date">{{:regDate}}</h5>
			<a href="#" class="edit-comment-link" style="margin-right:10px" data-mem-id="{{:memId }}" data-comment-id="{{:commentNum}}" >수정</a>
			<a href="#" class="delete-comment">삭제</a>
		</div>
	</div>
	<div class="edit-comment-form" style="display:none;">
		<textarea class="edit-comment-textarea" rows="4" cols="50">{{:detail}}</textarea>
		<a href="#" class="save-edited-comment" data-num-id="{{:commentNum}}">저장</a>
		<a href="#" class="cancel-edit-comment">취소</a>
	</div>
</div>
</script>

<script id="boardTemplate" type="text/x-jsrender">
	<tr>
		<td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox" value="{{:boardNum }}"></td>
    	<td>{{:boardNum}}</td>
		<td style="text-align: left;">
          <a href="#" onclick="info({{:boardNum}}, 'board')">{{:title}}</a>
        </td>
    	<td>{{:memId}}</td>
    	<td>{{:modDate }}</td>
    	<td>{{:viewCount }}</td>
	</tr>
</script>
	
<script>

function showLink() {
	const loginmemid = "${loginmemid}";
	$(".edit-comment-link").each((index, link) => {
    	const memid = $(link).attr('data-mem-id');
        if (loginmemid === memid) {
        	$(link).css("display", "block");
            $(".delete-comment").eq(index).css("display", "block");
       }
   });
}

$(document).ready(function() {
	$.views.helpers({
		containsImage: function(text, searchString) {
			console.log(text.indexOf(searchString));
			return text.indexOf(searchString) !== -1;
			
		}
	});
	
	const loginmem = "${loginmem}";
	const loginmemid = "${loginmemid}";
	//수정 하기 버튼 클릭 저장,취소 버튼 활성화
    $(document).on("click", ".edit-comment-link", function() {
        const index = $(".edit-comment-link").index(this);
        $(".edit-comment-form").eq(index).css("display", "block");
        $(".comment-content").eq(index).css("display", "none");
        $(".comment-area").eq(index).css("display", "none");
    });
	
	//취소 버튼 클릭, 수정 삭제 버튼 활성화
    $(document).on("click", ".cancel-edit-comment", function() {
        const index = $(".cancel-edit-comment").index(this);
        $(".edit-comment-form").eq(index).css("display", "none");
        $(".comment-content").eq(index).css("display", "block");
        $(".comment-area").eq(index).css("display", "block");
    });
	
	//댓글 삭제하기
	$(document).on("click", ".delete-comment", function() {
		if (confirm('정말로 삭제 하시겠습니까?')) {
			const index = $(".delete-comment").index(this);
			const delLink = $(this).closest(".up-del-link");
			commentNum = $(delLink).find(".edit-comment-link").data("comment-id");
			console.log("코멘트번호 = " + commentNum);
			
			const param = {
					boardNum: $("#infoboardnum").val(),
					commentNum: commentNum
			};
			
			$.ajax({
				url: "<c:url value='/board/commentDelete.do'/>",
				type: "POST",
				contentType: "application/json; charset=UTF-8",
				data: JSON.stringify(param),
				dataType: "json",
				success: (json) => {
					if (json.status) {
						$(".comment-list").eq(index).css("display", "none");
						$("#comment-count").text("댓글(" + json.commentCount + ")");
					}
					
				}
			});
		}
	});
	
	//댓글 작성하기
	$(document).on("click", ".write-comment", function() {
		const param = {
	     		boardNum: $("#infoboardnum").val(),
				detail: $("#write-detail").val(),
				memId: loginmemid
		 };
		
	     $.ajax({
				url: "<c:url value='/board/commentWrite.do'/>",
				type: "POST",
				contentType: "application/json; charset=UTF-8",
				data: JSON.stringify(param),
				dataType: "json",
				success: (json) => {
					if (json.status) {
						const commentData = json.comment;
				     	const commentTemplate = $("#commentTemplate").html();
						const tmpl = $.templates(commentTemplate);
						const renderedcomment = tmpl.render(commentData);
						  
						const commentListHTML = $("#comment-tmpl");
						commentListHTML.empty();
						$(commentListHTML).append(renderedcomment);
						$("#comment-count").text("댓글(" + json.commentCount + ")");
						showLink();
						
						$("#write-detail").val("");
					}
				}
			});
	});
	
	//수정된 댓글 저장하기
	$(document).on("click", ".save-edited-comment", function() {
		const index = $(".save-edited-comment").index(this);
		const editForm = $(this).closest(".edit-comment-form");
	    
	    const commentNum = $(editForm).find(".save-edited-comment").data("num-id");
	    const detail = $(editForm).find(".edit-comment-textarea").val();
		
		const param = {
	     		commentNum: commentNum,
				detail: detail 
		 };
	     $.ajax({
				url: "<c:url value='/board/commentUpdate.do'/>",
				type: "POST",
				contentType: "application/json; charset=UTF-8",
				data: JSON.stringify(param),
				dataType: "json",
				success: (json) => {
					if (json.status) {
						$(".comment-content").eq(index).text(json.comment.detail);
						$(".comment-date").eq(index).text(json.comment.regDate);
						
						$(".edit-comment-form").eq(index).css("display", "none");
				        $(".comment-content").eq(index).css("display", "block");
				        $(".comment-area").eq(index).css("display", "block");
					}
				}
			});
	});

	if (loginmem != "") {
        $("#wr").css("display", "inline-block");
    }
	if (loginmemid.includes("admin")) {
		$("#del").css("display", "inline-block");
	} else {
		$(".checkbox-all").css("display", "none");
		$(".checkbox-cell").css("display", "none");
	}
  

    
   $("#dialogInfo").dialog({
        autoOpen: false,
        modal: true,
        width: 1000,
        height: 800,
        buttons: {
       		"글 수정": function() {
       		 const type = $("#dialogInfo").data("type");
       			if (type === "board") {
           			const boardnum = $("#infoboardnum").val();
           			const memid = $("#infoboardmemid").val();
           			if (loginmemid !== memid) {
        				alert("글 작성자만 수정 가능");
        			} else {
        				//글 수정 창 띄우기
           				openUpdate(boardnum);
        			}
           			
       			} else if (type === "notice") {
       				alert("관리자만 수정 가능");
       			}
       		},
       		"글 삭제": function() {
        	const type = $("#dialogInfo").data("type");
        		if (type === "board") {
        			const boardnum = $("#infoboardnum").val();
        			const memid = $("#infoboardmemid").val();
        			if (loginmemid !== memid) {
        				alert("글 작성자만 삭제 가능");
        			} else {
        				//글 삭제 수행
        				boardDel(boardnum);
        			}
        		} else if (type === "notice") {
        			alert("공지사항 글은 삭제 불가능");
        		}
        	},
       		"답글 쓰기": function() {
          		 const type = $("#dialogInfo").data("type");
          		 const reply = "reply";
          			if (type === "board") {
          				if (loginmem != "") {
          					const boardnum = $("#infoboardnum").val();
                  			//답글 쓰기
              				openReply(boardnum, reply);
          				} else {
          					alert("로그인 해주세요");
          				}
              			
          			} else if (type === "notice") {
          				alert("공지사항 글은 답글 불가능");
          			}
          		},
            "Close": function() {
            	$(".comment-list").css("display", "none");
                $(this).dialog("close");
                
            }
    		
        }
    });
   
    $("#dialogWrite").dialog({
        autoOpen: false,
        modal: true,
        width: 800,
        height: 700,
        buttons: {
        	"글 작성": function() {
        		const reply = $("#dialogWrite").data("reply");
        		const boardnum = $("#dialogWrite").data("boardnum");
        		if (reply === "reply") {
        			addReply(boardnum);
        		} else {
        			addWrite();
        		}
        		
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
        		const boardnum = $("#dialogUpdate").data("boardnum");
        		addUpdate(boardnum)
        		$(this).dialog("close");
        	},
            "Close": function() {
                $(this).dialog("close");
            }
        }
    });
});



function openReply(boardnum, reply) {
	$("#dialogWrite")
	.data("boardnum", boardnum)
    .data("reply", reply)
    .dialog("open");
}

function boardDel(boardnum) {
	const matchingRows = $("td:contains(" + boardnum + ")").closest("tr");
	matchingRows.css("display", "none");

	if (confirm("정말로 삭제 하시겠습니까?")) {
		const param = {
				scBoardNum: boardnum,
				scBoardNum2: $("#boardTable tbody:last tr:last-child td:nth-child(2)").text(),
		      };
	    
		$.ajax({
			url: "<c:url value='/board/boardDelete.do'/>",
			type: "POST",
			contentType: "application/json; charset=UTF-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: (json) => {
				if (!json.status) {
					alert("삭제 오류")
				}
				location.reload();
			}
		});
		
		
	}
	$("#dialogInfo").dialog("close");
	
}

function addUpdate(boardnum) {
	const param = {
	        boardNum: boardnum,
	        title: $("#updateTitle").val(),
	        content: $("#updateContent").val(),
	      };
    
	$.ajax({
		url: "<c:url value='/board/boardUpdate.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
			if (!json.status) {
				alert("수정 실패");
			}
			location.reload();
		}
	});
	
	$("#dialogInfo").dialog("close");
	
}

function openUpdate(boardnum) {
	//console.log(boardnum)
	const param = {
	        boardNum: boardnum,
	      };
    
	$.ajax({
		url: "<c:url value='/board/boardInfo.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
	       	const board = json.info;
	   		$("#updateTitle").val(board.title);
	   		$("#updateContent").html(board.content);
   		  
		}
	});
	$("#dialogUpdate").data("boardnum", boardnum).dialog("open");
	
}

function addWrite() {
	const fileForm = document.querySelector("#fileForm");
	const formData = new FormData(fileForm);
 	console.log(formData);
 	
	fetch("<c:url value='/board/boardWrite.do'/>", {
		  method: "POST",
		  body: formData,
		})
		  .then((response) => response.json())
		  .then((json) => {
			  const board = json.boardRow;
			  const boardTemplate = $("#boardTemplate").html();
			  const tmpl = $.templates(boardTemplate);
			  const renderedboard = tmpl.render(board);
			  
			  const boardListHTML = $("#boardbottom");
			  $(boardListHTML).prepend(renderedboard);
			  $(".checkbox-all").css("display", "none");
		      $(".checkbox-cell").css("display", "none");
		  })
	
// 	$.ajax({
// 		url: "<c:url value='/board/boardWrite.do'/>",
// 		type: "POST",
// 		contentType: "application/json; charset=UTF-8",
// 		data: JSON.stringify(param),
// 		dataType: "json",
// 		success: (json) => {
// 		  const board = json.boardRow;
// 		  const boardTemplate = $("#boardTemplate").html();
// 		  const tmpl = $.templates(boardTemplate);
// 		  const renderedboard = tmpl.render(board);
		  
// 		  const boardListHTML = $("#boardbottom");
// 		  $(boardListHTML).prepend(renderedboard);

// 		}
// 	});
	
}

function addReply(boardnum) {
	const loginmemid = "${loginmemid}";
	const param = {
			pid:boardnum,
			title: $("#writeTitle").val(),
			content: $("#writeContent").val(),
			memId: loginmemid,
			
	      };
    
	$.ajax({
		url: "<c:url value='/board/boardReply.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
			if(!json.status) {
				alert("답글 오류")
			}
			location.reload();
		}
	});
	$("#dialogInfo").dialog("close");
}

function info(boardnum, type) {
	const loginmemid = "${loginmemid}";
	if (loginmemid != "") {
		$("#comment").css("display", "inline-block");
	}
	
	var link;
	var param;
	if (type === "notice") {
		link = "<c:url value='/admin/noticeInfo.do'/>";
		
		param = {
		        noticeNum: boardnum,
		};
	} else if (type === "board") {
		link = "<c:url value='/board/boardInfo.do'/>";
		param = {
			    boardNum: boardnum,
			};
	}
    console.log(link)
    console.log(param)
	
	$.ajax({
		url: link,
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
			if (!json.status) {
				alert("조회수 반영 실패");
			}
			const info = json.info;
			
			
			if (type === "notice") {
				$("#infoboardnum").val(info.noticeNum);
			} else if (type === "board") {
				$("#infoboardnum").val(info.boardNum);
				const filelist = json.filelist;
				const fileTemplate = $("#fileTemplate").html();
				const fileTmpl = $.templates(fileTemplate);
				
				const renderedFiles = filelist.map(fileItem => {
					  return {
					    file: fileItem,
					    contentType: fileItem.contentType
					  };
					});
				
				const renderedFile = fileTmpl.render(renderedFiles);
				const filelistHTML = $("#fileList");
				filelistHTML.empty();
				$(filelistHTML).append(renderedFile);
				
				$("#comment-count").text("댓글(" + json.commentCount + ")");
				$("#comment-memid").text(loginmemid);
				
		     	const commentData = json.comment;
		     	const commentTemplate = $("#commentTemplate").html();
				const tmpl = $.templates(commentTemplate);
				//const renderedcomment = tmpl.render({comments: commentData});
				const renderedcomment = tmpl.render(commentData);
				  
				const commentListHTML = $("#comment-tmpl");
				commentListHTML.empty();
				$(commentListHTML).append(renderedcomment);
				
				showLink();
				
			}
			
			$("#infoboardmemid").val(info.memId);
	     	$("#author").text(info.memId);
	     	$("#date").text(info.modDate);
	     	$("#views").text(info.viewCount);
	     	$("#post-content").html(info.content);
	     	$("#dialogInfo").dialog("option", "title", info.title);
			
			
		}
	});
  
//  	$("#dialogInfo").dialog("open");
 	$("#dialogInfo").data("type", type).dialog("open");
// 	$("#dialogInfo").data("buttons", buttons).dialog("open");
}


$("#wr").on("click", () => {
	$("#writeTitle").val("");
	$("#writeContent").val("");
	$("#d_file").empty();
	$("#dialogWrite").dialog("open");
})

// $.Nav('parent', './board_list.do', {bseq:'', srcWorkTpCd:''}, null, true);
// $.Nav('go', './price_proc.do',{srcPriceSeq:seq, status:'d',});

// function goPage(index) {
// 	$.Nav('go', location.pathname, {'cpage': index, });
// }

$("#mForm").on("submit", () => {
	
	return true;
});

//댓글 더보기
$("#moreBtn").on("click", () => {
	const loginmemid = "${loginmemid}";
	const lastComment = $("#comment-tmpl").find(".comment-list").last();
    const commentNum = lastComment.find(".edit-comment-link").data("comment-id");
	const param = {
			boardNum: $("#infoboardnum").val(),
			commentNum: commentNum
	      };
	
	$.ajax({
		url: "<c:url value='/board/commentAdd.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
			
	     	const commentData = json.comment;
	     	const commentTemplate = $("#commentTemplate").html();
			const tmpl = $.templates(commentTemplate);
			//const renderedcomment = tmpl.render({comments: commentData});
			const renderedcomment = tmpl.render(commentData);
			  
			const commentListHTML = $("#comment-tmpl");
			$(commentListHTML).append(renderedcomment);
			
			showLink();
		}
	});
});

//체크 박스 전체 선택
$("#checkAll").on("click", () => {
	$(".chkbox").prop("checked", $("#checkAll").prop("checked"));
});

$("#del").on("click", (event) => {
	if (confirm('정말로 삭제 하시겠습니까?')) {
// 		var count = 0;
// 		var deleteStr = "";
		const checkedboardNums = [];
		var isChk = true;
		
		$("input[name='chkBoardNum']").each((i, chkbox) => {
		    if ($(chkbox).prop("checked")) {
// 		        count++;
// 		        var boardNumCell = row.find('td:nth-child(2)');
// 		        var boardNum = boardNumCell.text().trim();
// 		        deleteStr += boardNum + ',';
		        const boardNum = $(chkbox).val(); 
		        checkedboardNums.push(boardNum); // 배열에 추가
		        var row = $(chkbox).closest('tr');
		        row.css("display", "none");
		        isChk = false;
		    }
		});
		if (isChk) {
			alert('삭제할 글을 선택 하세요.');
		} else {
// 			deleteStr = deleteStr.substr(0, deleteStr.length - 1);
// 			search.paging.pageIndex : 현재 페이지 (cpage)
			const param = {
// 					 	count: count,
// 				        deleteStr: deleteStr,
						scBoardChkNum: checkedboardNums,
						scBoardNum: $("#boardTable tbody:last tr:last-child td:nth-child(2)").text(),
				      };
			$.ajax({
				url: "<c:url value='/board/boardDeletechkbox.do'/>",
				type: "POST",
				contentType: "application/json; charset=UTF-8",
				data: JSON.stringify(param),
				dataType: "json",
				success: (json) => {
					window.location.reload();
					
				}
			});		
		}
	} else {
        event.preventDefault();
        return false;
    }
});

function fn_addFile(){
	$("#d_file").append("<br>"+"<input  type='file' name='file' />");
}

//파일 업로드
// document.querySelector("#uploadButton").addEventListener("click", e => {
// 	const fileForm = document.querySelector("#fileForm");
// 	const formData = new FormData(fileForm);

//  	console.log(formData);
//     const option = {
//         method : 'POST',
//         body: formData
//     };

//     fetch("<c:url value='/board/uploadFile.do'/>", option)
//     .then(response => response.json())
//     .then(json => {
//     	if (json.status) {
//     		alert("성공");
//     		console.log(json.filelist);
//     	} else {
//     		alert("실패");
//     	}
//     });
// });


   
   
</script>
</body>
</html>