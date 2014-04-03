<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<c:url var="submitAccountUrl" value="/account" />

<spring:message var="pageTitle" code="account.pageTitle" />
<spring:message var="edit" code="label.edit" />

<html>
	<head>
		<title>${pageTitle}</title>
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
			<div class="form-horizontal">
				<c:set var="calendarOkDisabled" value="true"/>
				<div class="form-group">
					<label class="control-label col-xs-2"><spring:message code="account.label.googleAccount"/></label>
					<div class="controls col-xs-10">
						<c:choose>
							<c:when test="${not empty googleUserinfo}">
								<form:form method="PUT" action="${baseUrl}?forgetGoogle">	
								<span>${googleUserinfo.name} <button class="btn btn-default" type="submit"><spring:message code="account.label.forgetGoogle"/></button></span>
								</form:form>
								<c:set var="calendarOkDisabled" value="false"/>						
							</c:when>
							<c:otherwise>
								<a href="${googleConnectUrl}"><spring:message code="account.label.connectToGoogle"/></a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<form:form action="${submitAccountUrl}" modelAttribute="form" acceptCharset="UTF-8">
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
		  		
				<c:set var="groupError"><form:errors path='marketingOk'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
					<div class="controls col-xs-offset-2 col-xs-10">
						<label class="checkbox" for="marketingOk"><form:checkbox id="marketingOk" path="marketingOk" /> <spring:message code="account.label.marketingOk" /></label>
						<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
					</div>
				</div>
				
				<c:set var="groupError"><form:errors path='emailOk'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
					<div class="controls col-xs-offset-2 col-xs-10">
						<label class="checkbox" for="emailOk"><form:checkbox id="emailOk" path="emailOk" /> <spring:message code="account.label.emailOk" /></label>
						<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
					</div>
				</div>

				
				<c:set var="groupError"><form:errors path='calendarOk'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
					<div class="controls col-xs-offset-2 col-xs-10">
						<label class="checkbox" for="calendarOk"><form:checkbox id="calendarOk" path="calendarOk" disabled="${calendarOkDisabled}"/> <spring:message code="account.label.calendarOk" /></label>
						<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
					</div>
				</div>
				
				<c:set var="groupError"><form:errors path='twitterOk'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
					<div class="controls col-xs-offset-2 col-xs-10">
						<label class="checkbox" for="twitterOk"><form:checkbox id="twitterOk" path="twitterOk" /> <spring:message code="account.label.twitterOk" /></label>
						<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
					</div>
				</div>
				
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
				
				<div class="form-group">
					<div class="col-xs-2"></div>
					<div class="col-xs-offset-2 col-xs-10">
						<button type="submit" class="btn btn-primary">${edit}</button>
					</div>
				</div>
			</form:form>
			</div>
		</div>
		<%@ include file="../includes/footer.jspf" %>
	</body>
</html>