<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function() {
	$("#btn-check").click(function() {
		var email = $("#email").val();
		if(email == '') {
			return;
		}
		
		$.ajax({
			url: "/mysite3_class/user/api/checkemail?email=" + email,
			type: "get",
			dataType: "json",
			error: function(xhr, status, err){
				console.error(err);			
			},
			success: function(response){
				console.log(response)
				
				if(response.result == "fail"){
					console.error(response.message);
					retrun;
				}
				if(response.data) {
					alert("존재하는 이메일입니다. 다른 이메일을 사용해 주세요.");
					$("#email").val("");
					$("#email").focus();
					return;
				}
				
				// 사용할 수 있는 이메일
				$("#btn-check").hide();
				$("#img-check").show();
			}
		});
	})
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form
					modelAttribute="userVo"
				 	id="join-form" 
				 	name="joinForm" 
				 	method="post" 
				 	action="${pageContext.request.contextPath}/user/join">
				 	
					<input type='hidden' name="a" value="join">
					
					<label class="block-label" for="name">이름</label>
					
					<form:input path="name" value="${userVo.name}"/>
					
					<spring:hasBindErrors name="userVo">
					<p style="text-align:left; padding:0">
						<c:if test="${errors.hasFieldErrors('name')}">
							<!-- default message -->
							${errors.getFieldError('name').defaultMessage }
							 							 
						</c:if>
					</p>
					</spring:hasBindErrors>
					
					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					<input id ="btn-check" type="button" value="이메일확인">
					<img id="img-check" src="${pageContext.request.contextPath}/assets/images/check.jpg" style="vertical-align:bottom; width:24px; height:24px; display:none">
					<p style="text-align:left; padding:0">
						<form:errors path="email" />
					</p>
					
					
					<label class="block-label">
						<spring:message code="user.join.label.password" />
					</label>
					<form:input path="password" />
					
					<fieldset>
						<legend>성별</legend>
						
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>