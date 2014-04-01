<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<spring:message var="resourcesCaption" code="resource.pageTitle.detail" />
<spring:message var="pageTitle" code="schedule.pageTitle.edit" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="back" code="label.back" />
<c:set var="resource" value="${schedule.resource}"/>
<c:url var="baseUrl" value="/resource/${resource.id}/schedule" />

<html>
	<head>
		<title>${pageTitle}</title>
		<%@ include file="../../includes/head.jspf"%>
	</head>
	<body>
		<%@ include file="../../includes/navigation.jspf" %>
		<div class="container"> 
			<%@ include file="../../includes/message.jspf" %>
			<div class="page-header">
				<h1>${pageTitle}</h1>
			</div>
			<c:set var="type" value="MAIN"/>
			<%@ include file="../includes/resourceParametersTable.jspf" %>
			<a href="${baseUrl}"><button class="btn btn-default"><i class="icon ion-android-alarm"></i><span class="text"> ${back}</span></button></a>	
			<c:set var="_method" value="POST" />
			<c:set var="_action" value="${baseUrl}" />
			<c:if test="${not empty schedule.id}">
				<c:set var="_method" value="PUT" />
				<c:set var="_action" value="${baseUrl}/${schedule.id}" />
			</c:if>
			<form:form cssClass="form-horizontal" method="${_method}" modelAttribute="schedule" action="${_action}">
				<form:errors path="*">
					<div class="alert alert-danger"><spring:message code="error.global" /></div>
				</form:errors>
				
				<c:set var="groupError"><form:errors path='start'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="start"><spring:message code="schedule.label.start"/></label>
       				<div class="controls col-xs-10">
       					<form:input path="start" cssClass="form-control" id="start" required="required" type="datetime-local"/>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>
   				
   				<c:set var="groupError"><form:errors path='duration'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="duration"><spring:message code="resource.label.duration"/></label>
       				<div class="controls col-xs-10">
       					<form:input path="duration" cssClass="form-control" id="duration" required="required" type="number" min="1"/>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>
   				
   				<c:set var="groupError"><form:errors path='capacity'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="capacity"><spring:message code="resource.label.capacity"/></label>
       				<div class="controls col-xs-10">
       					<form:input path="capacity" cssClass="form-control" id="capacity" required="required" type="number" min="1"/>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>

				<c:set var="groupError"><form:errors path='note'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="note"><spring:message code="schedule.label.note"/></label>
       				<div class="controls col-xs-10">
       					<form:textarea path="note" cssClass="form-control" id="note"/>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>

				<c:set var="groupError"><form:errors path='visible'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
					<div class="controls col-xs-offset-2 col-xs-10">
						<label class="checkbox" for="visible"><form:checkbox id="visible" path="visible" /> <spring:message code="resource.label.visible" /></label>
						<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-xs-offset-2 col-xs-10">				
						<button type="submit" class="btn btn-primary">${edit}</button>
					</div>
				</div>
		</form:form>
	</div>
	<%@ include file="../../includes/footer.jspf" %>
</body>
</html>