<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<spring:message var="pageTitle" code="resourceProperty.pageTitle.edit" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="back" code="label.back" />
<c:url var="baseUrl" value="/resource/property" />

<html>
	<head>
		<title>${pageTitle}</title>
		<%@ include file="../../includes/head.jspf"%>
	</head>
	<body>
	    <c:set var="active" value="resources" />
		<%@ include file="../../includes/navigation.jspf" %>
		<div class="container"> 
			<%@ include file="../../includes/message.jspf" %>
			<div class="page-header">
				<h1>${pageTitle}</h1>
			</div>
			<c:set var="_method" value="POST" />
			<c:set var="_action" value="${baseUrl}" />
			<c:if test="${not empty resourceProperty.id}">
				<c:set var="_method" value="PUT" />
				<c:set var="_action" value="${baseUrl}/${resourceProperty.id}" />
			</c:if>
			<form:form cssClass="form-horizontal" method="${_method}"  modelAttribute="resourceProperty" action="${_action}">
				<form:errors path="*">
					<div class="alert alert-danger"><spring:message code="error.global" /></div>
				</form:errors>
				
				<c:set var="groupError"><form:errors path='name'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="name"><spring:message code="resourceProperty.label.name"/></label>
       				<div class="controls col-xs-10">
       					<form:textarea path="name" cssClass="form-control" id="name"/>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>
   				
   				<c:set var="groupError"><form:errors path='type'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="type"><spring:message code="resourceProperty.label.type"/></label>
       				<div class="controls col-xs-10">
       					<form:select path="type" cssClass="form-control" id="type"><form:options/></form:select>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>

				<c:set var="groupError"><form:errors path='defaultValue'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="defaultValue"><spring:message code="resourceProperty.label.defaultValue"/></label>
       				<div class="controls col-xs-10">
       					<form:textarea path="defaultValue" cssClass="form-control" id="defaultValue"/>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
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