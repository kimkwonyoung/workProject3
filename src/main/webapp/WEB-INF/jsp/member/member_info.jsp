<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="<c:url value='/css/memberinfo.css'/>">
</head>
<body>
  <div class="container">
    <h1>나의 회원 정보</h1>
    <ul class="info-list">
      <li><span class="info-label">이름:</span> ${loginMember.name}</li>
      <li><span class="info-label">아이디:</span> ${loginMember.memberid}</li>
      <li><span class="info-label">비밀번호:</span> ${loginMember.pwd}</li>
      <li><span class="info-label">핸드폰번호:</span> ${loginMember.phone}</li>
    </ul>
    <div id="linklist">
    	<a href="<c:url value='/main.do'/>" class="back-link">돌아가기</a>
    	<a href="#" id="updateMem" class="back-link">회원 정보 수정</a>
    	<a href="#" id="withdrawMem" class="back-link">회원 탈퇴</a>
    </div>
  </div>
  
<div id="dialogUpdate" title="회원 정보 수정">
    <div id="userForm">
        <h2 id="titleuser">회원 정보 수정</h2>
        <div class="form-group-insert">
            <label for="password">변경 비밀번호:</label>
            <input type="password" id="password" name="pwd" placeholder="비밀번호를 입력하세요" required>
        	<label class="pwdlabel" for="password">변경 비밀번호 확인:</label>
        	<input type="password" id="password2" name="pwd2" placeholder="비밀번호를 입력하세요" required>
        </div>
        <div class="form-group-insert">
            <label for="username">이름:</label>
            <input type="text" id="username" name="name" placeholder="이름을 입력하세요" required>
        </div>
        <div class="form-group-insert">
            <label for="tel">휴대폰 번호:</label>
            <input type="tel" id="tel" name="phone" placeholder="휴대폰 번호를 입력하세요" required>
        </div>
    </div>
</div>
<div id="dialogWithdraw" title="회원 탈퇴">
    <div id="userForm2">
        <h2>회원 탈퇴</h2>
        <div class="form-group-insert">
        <div class="form-group-insert">
            <label for="userid">아이디:</label>
            <input type="text" id="delUserid" name="memberid" placeholder="아이디를 입력하세요" required>
        </div>
            <label for="password">비밀번호:</label>
            <input type="password" id="delpassword" name="pwd" placeholder="비밀번호를 입력하세요" required>
        	<label class="pwdlabel" for="password">비밀번호 확인:</label>
        	<input type="password" id="delpassword2" name="pwd2" placeholder="비밀번호를 입력하세요" required>
        </div>
    </div>
</div> 
<script>
$(document).ready(function() {
	$("#dialogUpdate").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 500,
	    buttons: {
	        "정보수정": function() {
	      	  updateMem();
	        },
	        "Close": function() {
	            $(this).dialog("close");
	        }
	    }
	});

	$("#updateMem").on("click", function(e) {
		$("#dialogUpdate").dialog("open");
	});

	$("#dialogWithdraw").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 400,
	    height: 400,
	    buttons: {
	        "회원탈퇴": function() {
	      	  withdrawMem();
	        },
	        "Close": function() {
	            $(this).dialog("close");
	        }
	    }
	});

	$("#withdrawMem").on("click", function(e) {
		$("#dialogWithdraw").dialog("open");
	});
});
  
function updateMem() {
	var membernum = "${loginMember.membernum}";
	const param = {
			membernum: membernum,
		    pwd: password.value,
		    name: username.value,
		    phone: tel.value,
		  };
	$.ajax({
		url: "<c:url value='/member/memberUpdate.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
		    if (json.status) {
		    	alert("<s:message code='success.common.update' />");
		    	//location.href = "<c:url value='memberInfo.do'/>"; 
		    	
		    	location.reload();
		    }
		}
	});
	$("#dialogUpdate").dialog("close");
}

function withdrawMem() {
	if ($("#delpassword").val() !== $("#delpassword2").val()) {
			alert("비밀번호가 일치 하지 않습니다.")
		} else {
			if(confirm("정말로 회원 탈퇴 하시겠습니까?")) {
				const param = {
			    	memberid: delUserid.value,
				    pwd: delpassword.value
				};
				
				$.ajax({
					url: "<c:url value='/member/memberWithdraw.do'/>",
					type: "POST",
					contentType: "application/json; charset=UTF-8",
					data: JSON.stringify(param),
					dataType: "json",
					success: () => {
    			        if (json.status) {
    			          alert("회원 탈퇴 완료");
    			          
    			        }
					}
					
				});
			}
		}	
	$("#dialogWithdraw").dialog("close");
	location.href = "<c:url value='/main.do'/>"
}
  
  
  
  	
  	//var links = document.querySelectorAll("#linklist a");
//     var hrefArr = ["<c:url value='/main.do'/>", 
//     			   "<c:url value='/member/memberUpdateMove.do?scMemNum=" + membernum + "'/>",
//     			   "<c:url value='/member/memberWithdrawMove.do'/>"];
	

// 	$("#linklist a").each((i, link) => {
// 	    $(link).on("click", createClickHandler(hrefArr[i]));
// 	});
	
//   	function createClickHandler(href) {
//   		return () => {
//   		    window.location.href = href;
//   		  }
//   	}
</script>
</body>
</html>