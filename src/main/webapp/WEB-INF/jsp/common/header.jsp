<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table border=0  width="100%">
  <tr>
     <td>
		<a href="<c:url value='/main.do'/>">
			<img src="<c:url value='/resources/image/duke_swing.gif'/>"  />
		</a>
     </td>
     <td>
       <h1><font size=30>스프링실습 홈페이지!!</font></h1>
     </td>
     
     <td>
       <c:choose>
          <c:when test="${isLogOn == true  && member!= null}">
            <h3>환영합니다. ${member.name }님!</h3>
            <a href="<c:url value='/member/logout.do'/>">로그아웃</a>
          </c:when>
          <c:otherwise>
	        <a href="<c:url value='/member/loginForm.do'/>">로그인</a>
	      </c:otherwise>
	   </c:choose>     
     </td>
  </tr>
</table>