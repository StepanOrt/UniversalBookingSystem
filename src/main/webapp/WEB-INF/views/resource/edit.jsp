<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="resource.pageTitle.edit" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="editProperties" code="label.editProperties" />
<c:url var="baseUrl" value="/resource" />

<html>
	<head>
		<title>${pageTitle}</title>
		<%@ include file="../includes/head.jspf"%>
	</head>
	<body>
		<%@ include file="../includes/navigation.jspf" %>
		<div class="container"> 
			<%@ include file="../includes/message.jspf" %>
			<div class="page-header">
				<h1>${pageTitle}</h1>
			</div>
			<c:set var="_method" value="POST" />
			<c:set var="_action" value="${baseUrl}" />
			<c:if test="${not empty resource.id}">
				<c:set var="_action" value="${baseUrl}/${resource.id}" />
				<c:set var="_method" value="PUT" />
			</c:if>
			<form:form cssClass="form-horizontal" method="${_method}" modelAttribute="resource" action="${_action}">
				<form:errors path="*">
					<div class="alert alert-danger"><spring:message code="error.global" /></div>
				</form:errors>
				
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
   				
   				<c:set var="groupError"><form:errors path='price'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="price"><spring:message code="resource.label.price"/> [<spring:message code="currency.sign"/>]</label>
       				<div class="controls col-xs-10">
       					<form:input path="price" cssClass="form-control" id="price" required="required" type="number" min="1"/>
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
				<c:set var="editation" value="${true}"/>
				<c:forTokens var="type" items="MAIN,DETAIL" delims=",">
				 	<%@ include file="includes/resourceParametersTable.jspf" %>
				</c:forTokens>
				<security:authorize ifAllGranted="PERM_RES_EDIT">
					<c:set var="type" value="INTERNAL"/>
					<%@ include file="includes/resourceParametersTable.jspf" %>
				</security:authorize>
				
				<div class="form-group">
					<div class="col-xs-offset-2 col-xs-10">				
						<button type="submit" class="btn btn-primary">${edit}</button>
					</div>
				</div>
			</form:form>
		</div>
		<%@ include file="../includes/footer.jspf" %>
	</body>
</html>