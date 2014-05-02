<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="account.pageTitle.edit" />
<spring:message var="edit" code="label.edit" />
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
			<form:form cssClass="form-horizontal" method="PUT" modelAttribute="account" action="${baseUrl}/${account.id}">
				<form:errors path="*">
					<div class="alert alert-danger"><spring:message code="error.global" /></div>
				</form:errors>
	
				<c:set var="groupError"><form:errors path='firstName'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
	       			<label class="control-label col-xs-2" for="firstName"><spring:message code="account.label.firstName"/></label>
	       			<div class="controls col-xs-10">
	       				<form:input path="firstName" cssClass="form-control" id="firstName" required="required" maxlength="50"/>
	       			</div>
	       			<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
	   			</div>
				
				<c:set var="groupError"><form:errors path='lastName'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
	       			<label class="control-label col-xs-2" for="lastName"><spring:message code="account.label.lastName"/></label>
	       			<div class="controls col-xs-10">
	       				<form:input path="lastName" cssClass="form-control" id="lastName" required="required" maxlength="50"/>
	       			</div>
	       			<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
	   			</div>
				
				<c:set var="groupError"><form:errors path='email'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
	       			<label class="control-label col-xs-2" for="email"><spring:message code="account.label.email"/></label>
	       			<div class="controls col-xs-10">
	       				<form:input path="email" type="email" cssClass="form-control" id="email" required="required" maxlength="50"/>
	       			</div>
	       			<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
	   			</div>
				
				<c:set var="groupError"><form:errors path='enabled'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
					<div class="controls col-xs-offset-2 col-xs-10">
						<label class="checkbox" for="enabled"><form:checkbox id="enabled" path="enabled" /> <spring:message code="account.label.enabled" /></label>
						<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
					</div>
				</div>
				<c:if test="${account.isUser()}">
					<c:set var="groupError"><form:errors path='internal'/></c:set>
					<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
		       			<label class="control-label col-xs-2" for="internal"><spring:message code="account.label.internal"/></label>
		       			<div class="controls col-xs-10">
		       				<form:input path="internal" cssClass="form-control" id="internal"/>
		       			</div>
		       			<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
		   			</div>
					
					<c:set var="groupError"><form:errors path='credit'/></c:set>
					<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
			      		<label class="control-label col-xs-2" for="credit"><spring:message code="account.label.credit"/> [<spring:message code="currency.sign"/>]</label>
			      		<div class="controls col-xs-10">
			      			<form:input path="credit" type="number" cssClass="form-control" id="credit" required="required" min="0"/>
			      		</div>
			      		<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
			  		</div>
		  		</c:if>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<div class="col-xs-offset-2 col-xs-10">
						<button type="submit" class="btn btn-primary">${edit}</button>
					</div>
				</div>
		</form:form>
	</div>
	<%@ include file="../../includes/footer.jspf" %>
</body>
</html>