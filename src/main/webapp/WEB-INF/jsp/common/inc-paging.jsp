<%-- <%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
/**
	server attribute에 search 객체를 추가해야 함.
	하단 goPage() 메서드 있음 : jquery.locator.js 필요
	test : <div>startPage : ${search.paging.startPage }, endPage : ${search.paging.endPage }, totalCount : ${search.paging.totalCount }</div>
*/
%>
<c:if test="${search.paging.totalCount > 0}">
	<div class="${search.paging.pagerClassName }">
		
		<c:choose>
			<c:when test="${search.paging.pageIndex gt 1 }">
				<a href="javascript:goPage(1);"><img src="/images/btn/btn_first.png" alt="처음" /></a>
			</c:when>
			<c:otherwise>
				<img src="/images/btn/btn_first.png" alt="처음" />
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${search.paging.currentBlockIndex gt 1}">
				<a href="javascript:goPage(${search.paging.blockCount * (search.paging.currentBlockIndex - 1) });" class="prev"><img src="/images/btn/btn_prev.png" alt="이전 블럭" /></a>
			</c:when>
			<c:otherwise>
				<a class="prev"><img src="/images/btn/btn_prev.png" alt="이전 블럭" /></a>
			</c:otherwise>
		</c:choose>
		
		<c:forEach begin="${search.paging.startPage}" end="${search.paging.endPage}" var="page" varStatus="s">
				<c:choose>
					<c:when test="${page eq search.paging.pageIndex }">
						<a class="present">${page }</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:goPage(${page });">${page }</a>
					</c:otherwise>
				</c:choose>
				<c:if test="${!s.last }">. </c:if>
		</c:forEach>
		<c:choose>
			<c:when test="${search.paging.totalPageCount gt search.paging.endPage }">
				<a href="javascript:goPage(${search.paging.endPage + 1});" class="next"><img src="/images/btn/btn_next.png" alt="다음 블럭" /></a>
			</c:when>
			<c:otherwise>
				<a class="next"><img src="/images/btn/btn_next.png" alt="다음 블럭" /></a>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${search.paging.pageIndex lt search.paging.totalPageCount }">
				<a href="javascript:goPage(${search.paging.totalPageCount });"><img src="/images/btn/btn_last.png" alt="마지막" /></a>
			</c:when>
			<c:otherwise>
				<img src="/images/btn/btn_last.png" alt="마지막" />
			</c:otherwise>
		</c:choose>	
	</div>
	<script type="text/javascript">
		function goPage(index) {
			$.Nav('go', location.pathname, {'cpage': index});
		}
	</script>
</c:if>