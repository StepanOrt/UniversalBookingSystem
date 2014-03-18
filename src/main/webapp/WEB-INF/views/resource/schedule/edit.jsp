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
	<h1>
		
	</h1>

	<div class="container">
		<%@ include file="../../includes/message.jspf"%>
		<c:set var="type" value="MAIN"/>
		<%@ include file="../includes/resourceParametersTable.jspf" %>		
		<c:set var="_method" value="POST" />
		<c:set var="_action" value="${baseUrl}" />
		<c:if test="${not empty schedule.id}">
			<c:set var="_method" value="PUT" />
			<c:set var="_action" value="${baseUrl}/${schedule.id}" />
		</c:if>
		<form:form method="${_method}" modelAttribute="schedule" action="${_action}">
			<table>
				<caption>${pageTitle}</caption>
				<tbody>
					<tr>
						<td><spring:message code="schedule.label.start" /></td>
						<td><form:input type="datetime-local" path="start"/></td>
						<form:errors path="start" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="schedule.label.duration" /></td>
						<td><form:input path="duration"/></td>
						<form:errors path="duration" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="schedule.label.capacity" /></td>
						<td><form:input path="capacity"/></td>
						<form:errors path="capacity" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="schedule.label.note" /></td>
						<td><form:input path="note"/></td>
						<form:errors path="note" element="td" cssClass="errorMessage"/>
					</tr>
				</tbody>
			</table>
			<span>
				<input type="button" value="${back}" onClick="parent.location='${baseUrl}'" />
			 	<input type="submit" name="submit" value="${edit}">
			</span>
		</form:form>
	</div>
</body>
</html>