<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css"
	rel="stylesheet" type="text/css">
<style>
    .pager .disabled {
        color: #ccc; /* 회색으로 표시 */
        pointer-events: none; /* 클릭할 수 없게 함 */
        cursor: default;
    }
</style>	
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> 
					<input type="submit" value="찾기">
				</form>

				<!-- findAll 결과 목록으로 보기  -->
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<!-- forEach -->
					<!-- 답글이면 들여쓰기 + 아이콘 넣기, 조건: 공백 * depth만큼 곱하기 -->
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<c:set var="padding" value="${20 * vo.depth}" />
						<tr>
							<td>${vo.no }</td>
							<td style="text-align:left; padding-left:${padding}px">
								<c:if test="${vo.depth != 0}">
									<img src='${pageContext.request.contextPath}/assets/images/reply.png'>
								</c:if>
								<a href="${pageContext.request.contextPath}/board?a=view&no=${vo.no}" method="post">${vo.title }</a></td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:if test="${not empty authUser and vo.userNo==authUser.no}">
								<td><a href="${pageContext.request.contextPath}/board?a=delete&no=${vo.no}" class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				
			<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<!-- 이전 페이지 링크 -->
						<c:if test="${currentPage > 1}">
							<li><a href="${pageContext.request.contextPath}/board?page=${currentPage - 1}">◀</a></li>
						</c:if>
						<!-- 페이지 번호 세팅 -->
	       				<c:set var="startPage" value="${currentPage-2}" />
     					<c:set var="endPage" value="${currentPage+2}" />
							
						<c:if test="${endPage > totalPages}">
							<c:set var = "endPage" value="${totalPages}" />
						</c:if>
						
						<c:if test="${startPage < 1}">
							<c:set var="startPage" value="1" />
							<c:set var="endPage" value="5" />
						</c:if>
						<c:if test="${endPage > totalPages}">
							<c:set var="endPage" value="${totalPages}" />
							<c:if test="${endPage - 4 > 0}">
								<c:set var="startPage" value="${endPage - 4}" />
							</c:if>
						</c:if>
												
						<!-- 정수로 변환 -->
				        <fmt:parseNumber var="startPageInt" value="${startPage}" type="number" integerOnly="true"/>
				        <fmt:parseNumber var="endPageInt" value="${endPage}" type="number" integerOnly="true"/>

						<!-- 페이지 번호 링크 -->
						<c:forEach var="i" begin="${startPage}" end="${endPage}">
							<c:choose>
								<c:when test="${i == currentPage}">
									<li class="selected">${i}</li>
								</c:when>
								<c:otherwise>
								
									<c:if test="${i <= totalPages}">
										<li><a href="${pageContext.request.contextPath}/board?page=${i}">${i}</a></li>
									</c:if>
									<c:if test="${i > totalPages}">
										<li class="disabled">${i}</li>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<!-- 빈 페이지 출력하기 -->
						<!-- 없는 페이지는 흐리게 표시하기 -->
						<c:forEach var="i" begin="${endPage + 1}" end="${startPage + 4}">
						    <li class="disabled">${i}</li>
						</c:forEach>
						
						<!-- 다음 페이지 링크 -->
						<c:if test="${currentPage < totalPages}">
							<li><a href="${pageContext.request.contextPath}/board?page=${currentPage + 1}">▶</a></li>
						</c:if>
					</ul>
				</div>
				

				<!-- 회원인 경우만 bottom을 표시 -->
				<c:if test="${not empty authUser}">
					<div class="bottom">
						<a
							href="${pageContext.request.contextPath}/board?a=writeform&reply=FALSE"
							method="post" id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>