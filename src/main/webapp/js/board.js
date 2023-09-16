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
	const loginmemid = "${loginmemid}";
	const param = {
			title: $("#writeTitle").val(),
			content: $("#writeContent").val(),
			memId: loginmemid,
			
	      };
    
	$.ajax({
		url: "<c:url value='/board/boardWrite.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
		  const board = json.boardRow;
		  const boardTemplate = $("#boardTemplate").html();
		  const tmpl = $.templates(boardTemplate);
		  const renderedboard = tmpl.render(board);
		  
		  const boardListHTML = $("#boardbottom");
		  $(boardListHTML).prepend(renderedboard);
		  

		}
	});
	
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
    //console.log(link)
    //console.log(param)
	
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
			}
	    	
	    	$("#infoboardmemid").val(info.memId);
	     	$("#author").text(info.memId);
	     	$("#date").text(info.modDate);
	     	$("#views").text(info.viewCount);
	     	$("#post-content").html(info.content);
	     	$("#dialogInfo").dialog("option", "title", info.title);
			
		}
	});
	$("#dialogInfo").data("type", type).dialog("open");
}