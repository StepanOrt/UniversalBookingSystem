<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<c:url var="submitRegistrationUrl" value="/account/registration" />

<spring:message var="pageTitle" code="newUserRegistration.pageTitle" />
<spring:message var="register" code="newUserRegistration.label.register" />

<html>
	<head>
		<title><c:out value="${pageTitle}" /></title>
		<%@ include file="../includes/head.jspf" %>
	</head>
	<body>
		<%@ include file="../includes/message.jspf" %>
		<h1><c:out value="${pageTitle}" /></h1>
		<form:form cssClass="main" action="${submitRegistrationUrl}" modelAttribute="form" acceptCharset="UTF-8">
			<form:errors path="*">
				<div class="alert"><spring:message code="error.global" /></div>
			</form:errors>

			<p><spring:message code="account.message.allFieldsRequired" /></p>

			<div class="panel grid">
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.firstName" /></div>
					<div class="yui-u">
						<div><form:input path="firstName" cssClass="short" cssErrorClass="short error" required="required"/></div>
						<form:errors path="firstName" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.lastName" /></div>
					<div class="yui-u">
						<div><form:input path="lastName" cssClass="short" cssErrorClass="short error" required="required"/></div>
						<form:errors path="lastName" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.email" /></div>
					<div class="yui-u">
						<div><form:input type="email" path="email" cssClass="medium" cssErrorClass="medium error" required="required"/></div>
						<form:errors path="email" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.password"/></div>
					<div class="yui-u">
						<div><form:password path="password" showPassword="false" cssClass="short" cssErrorClass="short error" required="required"/></div>
						<form:errors path="password" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.confirmPassword"/></div>
					<div class="yui-u">
						<div><form:password path="confirmPassword" showPassword="false" cssClass="short" required="required"/></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u">
						<form:checkbox id="marketingOk" path="marketingOk" />
						<label for="marketingOk"><spring:message code="account.label.marketingOk" /></label>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u">
						<div>
							<form:checkbox id="acceptTerms" path="acceptTerms" cssErrorClass="error" required="required"/>
							<label for="acceptTerms"><spring:message code="newUserRegistration.label.acceptTerms"/></label>
						</div>
						<form:errors path="acceptTerms" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u">
						<spring:message code="newUserRegistration.label.privacyPolicy" />
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u"><input type="submit" value="${register}"></input></div>
				</div>
			</div>
		</form:form>
	</body>
</html>