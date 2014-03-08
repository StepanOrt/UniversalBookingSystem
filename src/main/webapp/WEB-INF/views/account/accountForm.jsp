<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<c:url var="submitAccountUrl" value="/account" />
<c:url var="changePasswordUrl" value="/account/password" />

<spring:message var="pageTitle" code="account.pageTitle" />
<spring:message var="edit" code="account.label.edit" />

<html>
	<head>
		<title><c:out value="${pageTitle}" /></title>
		<%@ include file="../includes/head.jspf" %>
	</head>
	<body>
		<%@ include file="../includes/message.jspf" %>
		<h1><c:out value="${pageTitle}" /></h1>

		<form:form cssClass="main" action="${submitAccountUrl}" modelAttribute="form" acceptCharset="UTF-8">
			<form:errors path="*">
				<div class="alert"><spring:message code="error.global" /></div>
			</form:errors>

			<p>
				<a href="${changePasswordUrl}">
					<spring:message code="account.label.changePassword" />
				</a>
			</p>

			<p><spring:message code="account.message.allFieldsRequired" /></p>

			
	
			<div class="panel grid">			
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.firstName" /></div>
					<div class="yui-u">
						<div><form:input path="firstName" cssClass="short" cssErrorClass="short error" /></div>
						<form:errors path="firstName" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.lastName" /></div>
					<div class="yui-u">
						<div><form:input path="lastName" cssClass="short" cssErrorClass="short error" /></div>
						<form:errors path="lastName" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.email" /></div>
					<div class="yui-u">
						<div><form:input path="email" cssClass="medium" cssErrorClass="medium error" /></div>
						<form:errors path="email" element="div" cssClass="errorMessage" htmlEscape="false"/>
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
						<form:checkbox id="emailOk" path="emailOk" />
						<label for="emailOk"><spring:message code="account.label.emailOk" /></label>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u">
						<form:checkbox id="calendarOk" path="calendarOk" />
						<label for="calendarOk"><spring:message code="account.label.calendarOk" /></label>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u">
						<form:checkbox id="twitterOk" path="twitterOk" />
						<label for="twitterOk"><spring:message code="account.label.twitterOk" /></label>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="account.label.currentPassword" /></div>
					<div class="yui-u">
						<div><form:password path="currentPassword" showPassword="false" cssClass="short" cssErrorClass="short error" required="required"/></div>
						<form:errors path="currentPassword" element="div" cssClass="errorMessage" htmlEscape="false"/>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u"><input type="submit" value="${edit}"></input></div>
				</div>
			</div>
		</form:form>
	</body>
</html>