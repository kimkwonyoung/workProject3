<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>


<header>
    <div id="logo">
      <a href="<c:url value='/main.do'/>">
        <h1>DreamHome</h1>
      </a>
    </div>
<nav>
      <ul id="topMenu">
        <li><a href="#">주거 공간<span>▼</span></a>
          <ul>
            <li><a href="#">가구1</a></li>
            <li><a href="#">가구2</a></li>
          </ul>
        </li>
        <li><a href="#">상업 공간<span>▼</span></a>
          <ul>
            <li><a href="#">상업1</a></li>
            <li><a href="#">상업2</a></li>
            <li><a href="#">상업3</a></li>
          </ul>
        </li>
        <li><a href="#">문의하기</a></li>
        <li>
<c:if test="${empty loginMember.memberid eq 'admin' }">        
	<a href="#" class="openbtn">관리자 메뉴 열기</a>
	<div class="sidebar">
		<a href="<c:url value='/admin/memberList.do'/>" class="forlink">전체 회원 보기</a>
		<a href="<c:url value='/admin/noticeList.do?scCode=20'/>" class="forlink">공지사항 게시판</a>
<%-- 		<a href="<c:url value='/board/boardList.do?board_code=10'/>" class="forlink">일반 게시판</a> --%>
		
<%-- 		<a href="<c:url value='/board/boardList2.do'/>" class="forlink">일반 더보기 게시판</a> --%>
	</div>
</c:if>
        </li>
      </ul>
</nav>
</header>

<script type="text/javascript">
	$(".openbtn").on("click", () => {
		$(".sidebar").css("width", "226px");
		$(this).css("display", "none");
	});
	$(".closebtn").on("click", () => {
		$(".sidebar").css("width", "0");
		$(".openbtn").css("display", "block");
	});

</script>