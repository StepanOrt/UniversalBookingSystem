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
		<c:set var="active" value="account" />
		<%@ include file="../includes/navigation.jspf"%>
		<div class="container">
			<%@ include file="../includes/message.jspf" %>
			<div class="page-header">
				<h1>${pageTitle}</h1>
			</div>
			<form:form cssClass="form-horizontal"  action="${submitPasswordChangeUrl}" modelAttribute="form" acceptCharset="UTF-8">
				<form:errors path="*">
					<div class="alert"><spring:message code="error.global" /></div>
				</form:errors>
	
				<c:set var="groupError"><form:errors path='currentPassword'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
	       			<label class="control-label col-xs-2" for="currentPassword"><spring:message code="account.label.currentPassword"/></label>
	       			<div class="controls col-xs-10">
	       				<form:password path="currentPassword" cssClass="form-control" id="currentPassword" showcurrentPassword="false" required="required" minlength="6" maxlength="50"/>
	       			</div>
	       			<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
	   			</div>
	   			
				<c:set var="groupError"><form:errors path='password'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
	       			<label class="control-label col-xs-2" for="password"><spring:message code="account.label.password"/></label>
	       			<div class="controls col-xs-10">
	       				<form:password path="password" cssClass="form-control" id="password" showPassword="false" required="required" minlength="6" maxlength="50"/>
	       			</div>
	       			<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
	   			</div>			
				<div class="control-group form-group">
	       			<label class="control-label col-xs-2" for="confirmPassword"><spring:message code="account.label.confirmPassword"/></label>
	       			<div class="controls col-xs-10">
	       				<c:set var="matchesMessage"><spring:message code="error.mismatch.password"/></c:set>
	       				<form:password path="confirmPassword" data-validation-matches-match="password" data-validation-matches-message="${matchesMessage}" required="required" cssClass="form-control" id="confirmPassword" showPassword="false"/>
	       			</div>
	   			</div>
	   			
	   			<div class="form-group">
					<div class="col-xs-2"></div>
					<div class="col-xs-offset-2 col-xs-10">
						<button type="submit" class="btn btn-primary">${passwordChange}</button>
					</div>
				</div>
			</form:form>
		</div>
		<%@ include file="../includes/footer.jspf" %>
	</body>
</html>