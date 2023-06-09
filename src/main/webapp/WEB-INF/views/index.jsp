<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h4>WELCOME</h4>

<c:choose>
	<c:when test="${not empty sessionScope.authMember }">
		<a href='<c:url value="/mypage.do"></c:url>'>${authMember.memName } 님</a>
		<form name="logoutForm" action="<c:url value='/login/logout.do'></c:url>" method="post"></form>
		<a href="#" class="logoutBtn">로그아웃</a>
		<script>
			$(".logoutBtn").on("click",function(event){
				event.preventDefault();
				document.logoutForm.submit();
				return false;
			});
		</script>
	</c:when>
	<c:otherwise>
		<a href="<c:url value='/login/loginForm.jsp'></c:url>">로그인</a>
		<a href="<c:url value='/login/memberInsert.do'></c:url>">회원가입</a>
	</c:otherwise>
</c:choose>