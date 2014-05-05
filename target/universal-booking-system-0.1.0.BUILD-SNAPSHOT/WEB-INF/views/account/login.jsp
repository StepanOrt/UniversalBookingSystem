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
	<c:set var="active" value="login" />
	<%@ include file="../includes/navigation.jspf" %>
	<div class="container">
	  <%@ include file="../includes/message.jspf" %>
      <form class="form-signin" role="form" action="${postLoginUrl}" method="post" novalidate="novalidate">
        <h2 class="form-signin-heading">Please sign in</h2>
        <div class="control-group">
	        <input name="j_username" type="email" class="form-control" placeholder="Email address" required="required" autofocus="autofocus">
        </div>
        <div class="control-group">
        	<input name="j_password" type="password" class="form-control" placeholder="Password" required="required" minlength="6">
        </div>
        <label class="checkbox">
        	<input name="_spring_security_remember_me" type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
    </div> <!-- /container -->
    <%@ include file="../includes/footer.jspf" %>
</body>
</html>