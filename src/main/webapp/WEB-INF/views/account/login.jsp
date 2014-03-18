<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="postLoginUrl" value="/j_spring_security_check" />
<spring:message var="pageTitle" code="login.pageTitle" />

<!DOCTYPE html>
<html>
<head>
<title>${pageTitle}</title>
<%@ include file="../includes/head.jspf" %>
</head>
<body>
	<%@ include file="../includes/message.jspf" %>
	<h1>${pageTitle}</h1>
	
	<form class="main" action="${postLoginUrl}" method="post">
		<c:if test="${param.failed == true}">
			<div class="alert">
				Your login attempt failed. Please try again, or contact technical support for further assistance.
			</div>
		</c:if>
		<div class="panel grid" style="width:420px">
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">Email:</div>
				<div class="yui-u"><input type="email" autocomplete="on" required="required"  name="j_username" class="short" /></div>
			</div>
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">Password:</div>
				<div class="yui-u"><input type="password" required="required" name="j_password" class="short" /></div>
			</div>
			<div class="gridRow yui-gf">
				<div class="yui-u first"></div>
				<div class="yui-u"><input type="checkbox" name="_spring_security_remember_me" /> Remember me</div>
			</div>
			<div class="gridRow yui-gf">
				<div class="yui-u first"></div>
				<div class="yui-u"><input type="submit" value="Log in" /></div>
			</div>
		</div>
	</form>
</body>
</html>