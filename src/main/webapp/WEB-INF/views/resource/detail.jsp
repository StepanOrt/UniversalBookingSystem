<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="resource.pageTitle.detail" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="schedules" code="label.schedules" />
<spring:message var="back" code="label.back" />
<c:url var="baseUrl" value="/resource" />

<html>
<head>
<title>${pageTitle}</title>
<%@ include file="../includes/head.jspf"%>
</head>
<body>

	<div class="container">
		<%@ include file="../includes/message.jspf"%>

		<table>
			<caption>${pageTitle}</caption>
			<tbody>
				<tr>
					<td><spring:message code="resource.label.capacity" /></td>
					<td>${resource.capacity}</td>
				</tr>
				<tr>
					<td><spring:message code="resource.label.duration" /></td>
					<td>${resource.duration}</td>
				</tr>
				<tr>
					<td><spring:message code="resource.label.price" /></td>
					<td>${resource.price}</td>
				</tr>
				<tr>
					<td><spring:message code="resource.label.visible" /></td>
					<td>${resource.visible}</td>
				</tr>
			</tbody>
		</table>
		<c:forTokens var="type" items="MAIN,DETAIL" delims=",">
			<%@ include file="includes/resourceParametersTable.jspf" %>
		</c:forTokens>
		<security:authorize ifAllGranted="PERM_RES_EDIT">
			<c:set var="type" value="INTERNAL"/>
			<%@ include file="includes/resourceParametersTable.jspf" %>
		</security:authorize>
		<span>
			<input type="button" value="${back}" onClick="parent.location='${baseUrl}'" />
			<input type="button" value="${schedules}" onClick="parent.location='${baseUrl}/${resource.id}/schedule'" />
			<security:authorize ifAllGranted="PERM_RES_EDIT">
				<input type="button" value="${edit}" onClick="parent.location='${baseUrl}/${resource.id}?form'" />
				<form:form action="${baseUrl}/${resource.id}" method="DELETE">
					<input type="submit" value="${remove}" />
				</form:form>
			</security:authorize>
		</span>
	</div>
</body>
</html>