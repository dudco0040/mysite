<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<c:set var="vo" value="${vo}" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content" colspan=4><pre style="white-space: pre-wrap;">
								<!-- ${vo.contents } <!-- 줄 바꿈이 있으면 표시 해줘야함 -->
								<pre style="white-space: pre-wrap;">${vo.contents }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board">글목록</a>
					<!-- 비회원 기능 제한 -->			
					<c:if test="${not empty authUser}">
						<c:if test="${vo.userNo==authUser.no}">					
							<a href="${pageContext.request.contextPath}/board?a=modifyform&no=${vo.no}&title=${vo.title}&contents=${fn:escapeXml(vo.contents)}">글수정</a>
						</c:if>
						<a href="${pageContext.request.contextPath}/board?a=writeform&reply=TRUE&no=${vo.no}" method="post" id="new-book">답글달기</a>	<!-- &reply=TRUE -->
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>