<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DreamHome</title>
  
</head>
 
<body>
  <div id="container"> 
    <div id="slideShow">
      <div id="slides">
        <img src="<c:url value='/images/a-1.jpg'/>" alt="">
        <img src="<c:url value='/images/a-2.jpg'/>" alt="">
        <img src="<c:url value='/images/a-3.jpg'/>" alt="">
        <img src="<c:url value='/images/a-4.jpg'/>" alt="">
        <button id="prev">&lang;</button>
        <button id="next">&rang;</button>
      </div>
    </div>
    <div id="contents">
      <div id="tabMenu">
        <input type="radio" id="tab1" name="tabs" checked>
        <label for="tab1"><a href="<c:url value='/board/noticeList.do'/>">공지사항</a></label>
        <input type="radio" id="tab2" name="tabs">
        <label for="tab2">갤러리</label>
       	<input type="radio" id="tab3" name="tabs">
        <label id = "boardTab" for="tab3"><a href="<c:url value='/board/boardList.do'/>">게시판</a></label>
        <div id="notice" class="tabContent">
          <ul>
          <c:forEach var="notice" items="${noticeList }">           
            <li><a href="<c:url value='/board/noticeList.do'/>">${notice.title }</a></li>
          </c:forEach> 
          </ul>
        </div>
        <div id="gallery" class="tabContent">
          <ul>
            <li><img src="<c:url value='/images/c1.jpg'/>" ></li>
            <li><img src="<c:url value='/images/c2.jpg'/>" ></li>
            <li><img src="<c:url value='/images/c3.jpg'/>" ></li>
            <li><img src="<c:url value='/images/c4.jpg'/>" ></li>
            <li><img src="<c:url value='/images/f8.jpg'/>" ></li>  
            <li><img src="<c:url value='/images/f9.jpg'/>" ></li>                  
          </ul>
        </div>
        <div id="board" class="tabContent">
          <ul>            
            <c:forEach var="nomal" items="${nomalList }">           
            <li>
          		<span style="padding-left:${(nomal.level-1) * 20}px"></span>
          		${nomal.level != 1 ? "[답변] " : ""}
          		<a href="<c:url value='/board/boardList.do'/>">${nomal.title }</a>
            </li>
          </c:forEach> 
          </ul>
        </div>
        <div id="lightbox">
			<img src="<c:url value='/images/c1.jpg'/>" alt="" id="lightboxImage">			
    	</div>        
      </div>
      <div id="links">
        <ul>
          <li>
              <span id="quick-icon1"></span>
              <p>의자</p>
          </li>
          <li>
              <span id="quick-icon2"></span>
              <p>쇼파</p>
          </li>
          <li>
              <span id="quick-icon3"></span>
              <p>침대</p>
          </li>
        </ul>
      </div>
      <c:if test="${empty loginMember}">
	      <div id="login">
	        <form action="memberLogin.do" method="post" onsubmit="return false;">
	        	<div class="form-group">
	        		<h2>로그인</h2>
	        	</div>
	            <div id="form-group">
	            	<label for="username">아이디:</label>
	                <input type="text" name="memberid" id="memberid" placeholder="enter your id" required>
	            	
	            </div>
	            <div id="form-group">
	            	<label for="password">비밀번호:</label>
	                <input type="password" name="pwd" id="pwd" placeholder="enter your password" required>
	            	
	            </div>
	            <div id="form-group">
	                <input type="submit" id="memlogin" onclick="login()" value="로그인">              
	            </div>
	            <div id="userInsert">
	            	<a href="#" id="searchId">아이디 찾기</a>｜
	            	<a href="#" id="searchPwd">비밀번호 찾기</a>｜
	            	<a href="#" id="writeMem">회원 가입</a>
	            </div>
	        </form>
	    	</div>
    	</c:if>
    	<c:if test="${not empty loginMember}">
    		<div id="loginmember">
	            <div id="loginmessage">
	                <label>${loginMember.memberid} 회원님</label>
	            </div>
	            <div id="date">
	            	<ul>
	            	<li>진행 중 주문 0</li>
	            	<li>내 쿠폰 0</li>
	            	<li>포인트 0</li>
	            	</ul>
	            </div>
	            
	        	<div id="mlist">
	        		<ul>
	        			<li>장바구니</li>
	        			<li><a href="<c:url value='/member/memberFavorite.do'/>" class="forlink">사진모음</a></li>
	        			<li>나의문의내역</li>
	        		</ul>
	        	        	
	        	</div>
	          	<div id="userInfo">
	            	<a href="<c:url value='/member/memberInfo.do'/>" class="forlink"><span>나의 상세 정보 보기</span></a>
	            </div>       
	            <div id="loginOut">
	            	<a href="#" onclick="logout()" class="forlink"><span>로그 아웃</span></a>
	            </div>
	           
	    	</div>
	    	
    	</c:if>
    	
     
    </div>  
    <footer>    
      <div id="bottomMenu">
        <ul>
          <li><a href="#">회사 소개</a></li>
          <li><a href="#">개인정보처리방침</a></li>
          <li><a href="#">가구약관</a></li>
          <li><a href="#">사이트맵</a></li>
        </ul>
        <div id="sns">
          <ul>
          	<li><a href="#"><img src="<c:url value='/images/sns-1.png'/>"></a></li>
            <li><a href="#"><img src="<c:url value='/images/sns-2.png'/>"></a></li>
            <li><a href="#"><img src="<c:url value='/images/sns-3.png'/>"></a></li>
          </ul>
        </div>
      </div>
      <div id="company">
        <p>제주특별자치도 ***동 ***로 *** (대표전화) 123-456-7890</p> 
      </div>     
    </footer>  
  </div>

<div id="dialogSearchid" title="회원 아이디 찾기">
 <div class="form-group-insert">
	 <label for="username">이름:</label>
	 <input type="text" id="searchName" name="name" placeholder="이름을 입력하세요" required>
	 </div>
	 <div class="form-group-insert">
	     <label for="userid">휴대폰번호:</label>
	     <input type="text" id="searchTel" name="phone" placeholder="휴대폰번호를 입력하세요" required>
	 </div>
</div>

<div id="dialogSearchpwd" title="회원 비밀번호 찾기">
<div class="form-group-insert">
    <label for="userid">아이디:</label>
    <input type="text" id="pwdSearchId" name="memberid" placeholder="아이디를 입력하세요" required>
</div>
<div class="form-group-insert">
    <label for="username">이름:</label>
    <input type="text" id="pwdSearchName" name="name" placeholder="이름을 입력하세요" required>
</div>
</div> 

<div id="dialogWritemem" title="회원 가입">
	<div id="userForm">
	    <h2>회원 가입</h2>
	        <div class="form-group-insert">
	            <label for="userid">아이디:</label>
	            <input type="text" id="userid" name="memberid" placeholder="아이디를 입력하세요" required>
	        	<input type="button" id="existUid" value="중복확인"/>
	        </div>
	        <div class="form-group-insert">
	            <label for="password">비밀번호:</label>
	            <input type="password" id="password" name="pwd" placeholder="비밀번호를 입력하세요" required>
	        	<label for="password">비밀번호 확인:</label>
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
  
<script type="text/javascript">
$(document).ready(function(){
	$("#dialogSearchid").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 500,
	    buttons: {
	        "아이디 찾기": function() {
	        	const param = {
        		        name: searchName.value,
        		        phone: searchTel.value,
        		      };
        		$.ajax({
        			url: "<c:url value='/member/memberSearchid.do'/>",
        			type: "POST",
        			contentType: "application/json; charset=UTF-8",
        			data: JSON.stringify(param),
        			dataType: "json",
        			success: (json) => {
        				alert(json.message);
        				if (status) {
        					$("#dialogSearchid").dialog("close");
        				}
        			}
        		});
	        },
	        "Close": function() {
	            $(this).dialog("close");
	        }
	    }
	});
	
	$("#searchId").on("click", function(e) {
		$("#searchName").val("");
		$("#searchTel").val("");
		$("#dialogSearchid").dialog("open");
	});
	
	$("#dialogSearchpwd").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 500,
	    buttons: {
	        "비밀번호 찾기": function() {
	        	const param = {
        		        memberid: pwdSearchId.value,
        		        name: pwdSearchName.value,
        		      };
        		$.ajax({
        			url: "<c:url value='/member/memberSearchpwd.do'/>",
        			type: "POST",
        			contentType: "application/json; charset=UTF-8",
        			data: JSON.stringify(param),
        			dataType: "json",
        			success: (json) => {
        	          alert(json.message);
        	          if (status) {
        	        	  $("#dialogSearchpwd").dialog("close");
        	          }
	        			
        			}
        		});
	        },
	        "Close": function() {
	            $(this).dialog("close");
	        }
	    }
	});
	
	$("#searchPwd").on("click", function(e) {
		$("#pwdSearchId").val("");
		$("#pwdSearchName").val("");
		$("#dialogSearchpwd").dialog("open");
	});
	
	
	let existUidChecked = false;
	$("#existUid").on("click", () => {
		const param = {memberid: userid.value};
		
		$.ajax({
			url: "<c:url value='/member/memberExist.do'/>",
			type: "POST",
			contentType: "application/json; charset=UTF-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: (json) => {
		       if (json.status) {
		    	   alert("아이디가 존재 합니다");
		    	   userid.value = "";
		    	   userid.focus();
		  	       existUidChecked = false;
		       } else {
		    	   alert("사용 가능한 아이디 입니다");
		    	   existUidChecked = true;
		       }
			}
			
		});
	});
	
	$("#dialogWritemem").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 600,
	    buttons:  [
	        {
	            text: "회원 가입",
	            id: "register", 
	            click: function() {
	            	if (!existUidChecked) {
	        			alert("아이디 중복을 확인 해주세요");
	        			existUid.focus();
	        			return;
	        		}
	        		const param = {
	        	    		memberid: userid.value,
	        		        pwd: password.value,
	        		        name: username.value,
	        		        phone: tel.value,
	        		      };
	        		
	        		$.ajax({
	        			url: "<c:url value='/member/memberInsert.do'/>",
	        			type: "POST",
	        			contentType: "application/json; charset=UTF-8",
	        			data: JSON.stringify(param),
	        			dataType: "json",
	        			success: (json) => {
	        	          if (json.status) {
	        	        	  alert("회원가입 성공");
	        	          	}
	        			}
	        		});
	        		$("#dialogWritemem").dialog("close");
	            },
	        },
	        {
	            text: "Close",
	            click: function() {
	                $(this).dialog("close");
	            }
	        }
	    ]
	});
	
	$("#writeMem").on("click", function(e) {
		$("#dialogWritemem").dialog("open");
	});
});




	
/* 	function login() {
		const param = {
	    		memberid: memberid.value,
		        pwd: pwd.value
		};
	
	    fetch("/workProject/member/memberLogin.do", {
	      method: "POST",
	      headers: {
	        "Content-Type": "application/json; charset=UTF-8",
	      },
	      body: JSON.stringify(param),
	    })
	    .then((response) => response.json())
	    .then((json) => {
	  	    alert(json.message);
	        if (json.status) {
	      	  location.href = "<c:url value='mainIndex.do'/>"; 
	        }
	    });
	} */
	//로그인
	function login() {
	    const param = {
	        memberid: memberid.value,
	        pwd: pwd.value
	    };

	    $.ajax({
	        url: "<c:url value='/member/login.do'/>",
	        type: "POST",
	        contentType: "application/json; charset=UTF-8",
	        data: JSON.stringify(param),
	        dataType: "json",
	        success: (json) => {
	            if (json.status) {
	            	alert("<s:message code='login.success' />")
	            	location.href = "<c:url value='main.do'/>";
	            } else {
	            	alert("<s:message code='login.wrong' />")
	            }
	        }
	    });
	}
	//로그아웃
	function logout() {
		const param = {
		    };
		$.ajax({
			url: "<c:url value='/member/logout.do'/>",
			type: "POST",
			contentType: "application/json; charset=UTF-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: (json) => {
				if (json.status) {
					alert("<s:message code='login.logout' />");
		        	location.href = "<c:url value='main.do'/>";
				}
			}
		});
	}

	
/* 	function logout() {
		fetch("/workProject/member/memberLogout.do", {
	        method: "GET",
	        headers: {
	          "Content-Type": "application/json; charset=UTF-8",
	        },
	      })
	      .then((response) => response.json())
	      .then((json) => {
	         alert(json.message);
	         console.log(json);
	         location.href = "<c:url value='mainIndex.do'/>"; 
	      });
		
	} */
	
/* 	window.addEventListener("message", (event) => {
	    if (event.data === "closeIframe") {
	        var registrationIframe = document.getElementById("iframeForm");
	        if (registrationIframe) {
	            document.body.removeChild(registrationIframe);
	        }
	    }
	}); */
	
// 	//iframe 닫기 메시지 받는곳
// 	$(window).on("message", (event) => {
// 		if (event.data === "closeIframe") {
// 			 var registrationIframe = $("#iframeForm");
// 			 if (registrationIframe.length > 0) {
// 				 registrationIframe.remove();
// 			 }
// 		}
		
// 	});
	
// 	var showIframe = (href) => {
// 	    var iframe = $("<iframe>", {
// 	        src: href,
// 	        css: {
// 	            border: "1px solid gray",
// 	            width: "355px",
// 	            height: "510px"
// 	        }
// 	    });

// 	    var closeButton = $("<button>", {
// 	        class: "close-button",
// 	        text: "X",
// 	        css: {
// 	            position: "absolute",
// 	            top: "10px",
// 	            right: "10px"
// 	        },
// 	        click: () => {
// 	            iframeContainer.remove();
// 	        }
// 	    });

// 	    var iframeContainer = $("<div>", {
// 	        id: "iframeForm",
// 	        css: {
// 	            position: "fixed",
// 	            top: "50%",
// 	            left: "50%",
// 	            transform: "translate(-50%, -50%)",
// 	            backgroundColor: "#fff"
// 	        }
// 	    });

// 	    iframeContainer.append(iframe, closeButton);
// 	    $("body").append(iframeContainer);
// 	}
	
/* 	var showIframe = (href) => {
		var iframe = document.createElement("iframe");
		iframe.src = href;
		iframe.style.border = "1px solid gray";
		iframe.width = "355px";
		iframe.height = "480px";
		
		var closeButton = document.createElement("button");
        closeButton.className = "close-button";
        closeButton.textContent = "X";
        closeButton.style.position = "absolute";
        closeButton.style.top = "10px";
        closeButton.style.right = "10px";
        closeButton.addEventListener("click", () => {
            document.body.removeChild(iframeContainer);
        });
		
		var iframeContainer = document.createElement("div");
        iframeContainer.id = "iframeForm";
        iframeContainer.style.position = "fixed";
        iframeContainer.style.top = "50%";
        iframeContainer.style.left = "50%";
        iframeContainer.style.transform = "translate(-50%, -50%)";
        iframeContainer.style.backgroundColor = "#fff";
        
        iframeContainer.appendChild(iframe);
        iframeContainer.appendChild(closeButton);
        
        document.body.appendChild(iframeContainer);
		
		
	} */
	
	
// 	var hrefArr = [
// 		"<c:url value='/member/memberSearchMove.do?type1=findid'/>", 
// 		"<c:url value='/member/memberSearchMove.do?type1=findpwd'/>",
// 		"<c:url value='/member/memberWrite.do'/>"
// 		];

// 	$("#userInsert a").each((i, link) => {
// 	    $(link).on("click", createClickHandler(hrefArr[i]));
// 	});
	
/* 	var links = document.querySelectorAll("#userInsert a");
	
	for(let i = 0; i < links.length; i++) {
		let link = links[i];
		link.addEventListener("click", createClickHandler(hrefArr[i]));
	} */
	
// 	function createClickHandler(href) {
// 		return () => {
// 			showIframe(href);
// 		}
// 	}
	
	
	
/* 	var imgs = document.querySelectorAll("#gallery ul li img");
	var lightbox = document.getElementById("lightbox");
	var lightboxImage = document.getElementById("lightboxImage");
	var footer = document.querySelector("footer");
	var sns = document.querySelector("#sns");
	for (let i = 0; i < imgs.length; i++) {
		imgs[i].addEventListener("click", (event) => {
			lightbox.style.display = "block";
			lightboxImage.src = event.target.src;
			footer.style.display = "none";
			sns.style.display = "none";
		});
	}
	lightbox.onclick = () => {  
		lightbox.style.display = "none";
		footer.style.display = "block";
		sns.style.display = "block";
} */
	$("#gallery ul li img").on("click", (event) => {
		$("#lightbox").css("display", "block");
		$("#lightboxImage").attr("src", event.target.src);
		$("footer").css("display", "none");
		$("#sns").css("display", "none");
	});

	$("#lightbox").on("click", () => {
		$("#lightbox").css("display", "none");
		$("footer").css("display", "block");
		$("#sns").css("display", "block");
	});
	
</script>
<script src="<c:url value='/js/slideshow.js'/>"></script>
</body>
</html>