<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>

<c:url var="submitPasswordChangeUrl" value="/account/password" />
<spring:message var="pageTitle" code="passwordChange.pageTitle" />
<spring:message var="passwordChange" code="passwordChange.label.submit" />

<html>
	<head>
		<title><c:out value="${pageTitle}" /></title>
		<%@ include file="../includes/head.jspf" %>
	</head>
	<body>
		<%@ include file="../includes/message.jspf" %>
		<h1><c:out value="${pageTitle}" /></h1>
		<form:form cssClass="main" action="${submitPasswordChangeUrl}" modelAttribute="form" acceptCharset="UTF-8">
			<form:errors path="*">
				<div class="alert"><spring:message code="error.global" /></div>
			</form:errors>

			<div class="panel grid">
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.currentPassword" /></div>
					<div class="yui-u">
						<div><form:password path="currentPassword" showPassword="false" cssClass="short" cssErrorClass="short error" required="required"/></div>
						<form:errors path="currentPassword" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.password" /></div>
					<div class="yui-u">
						<div><form:password path="password" showPassword="false" cssClass="short" cssErrorClass="short error" required="required"/></div>
						<form:errors path="password" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.confirmPassword" /></div>
					<div class="yui-u">
						<div><form:password path="confirmPassword" showPassword="false" cssClass="short" required="required"/></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u"><input type="submit" value="${passwordChange}"></input></div>
				</div>
			</div>
		</form:form>
	</body>
</html>