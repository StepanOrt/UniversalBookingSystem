<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="account.pageTitle.topup" />
<spring:message var="topup" code="account.label.topup" />
<spring:message var="back" code="label.back" />
<c:url var="baseUrl" value="/account" />

<html>
	<head>
		<title>${pageTitle}</title>
		<%@ include file="../../includes/head.jspf"%>
	</head>
	<body>
	    <c:set var="active" value="accounts" />
		<%@ include file="../../includes/navigation.jspf" %>
		<div class="container"> 
			<%@ include file="../../includes/message.jspf" %>
			<div class="page-header">
				<h1>${pageTitle}</h1>
			</div>
			<form:form cssClass="form-horizontal" method="PUT" modelAttribute="topup" action="${baseUrl}/${account.id}/topup">
				<form:errors path="*">
					<div class="alert alert-danger"><spring:message code="error.global" /></div>
				</form:errors>
	
				<div class="control-group form-group">
		      		<label class="control-label col-xs-2" for="credit"><spring:message code="account.label.credit"/></label>
		      		<span class="controls col-xs-10">${account.credit} <spring:message code="currency.sign"/></span>
		  		</div>
		  		
		  		<c:set var="groupError"><form:errors path='amount'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
		      		<label class="control-label col-xs-2" for="amount"><spring:message code="account.label.topup.amount"/></label>
		      		<div class="controls col-xs-10">
		      			<form:input path="amount" type="number" cssClass="form-control" id="amount" required="required" min="0"/>
		      		</div>
		      		<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
		  		</div>
		  		<div class="form-group">
					<div class="col-xs-2"></div>
					<div class="col-xs-offset-2 col-xs-10">
						<button type="submit" class="btn btn-primary">${topup}</button>
					</div>
				</div>
		</form:form>
	</div>
	<%@ include file="../../includes/footer.jspf" %>
</body>
</html>