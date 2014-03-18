<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<spring:message var="pageTitle" code="resourceProperty.pageTitle.detail" />
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

	<div class="container">
		<%@ include file="../../includes/message.jspf"%>

		<table>
			<caption>${pageTitle}</caption>
			<tbody>
				<tr>
					<td><spring:message code="resourceProperty.label.name" /></td>
					<td>${resourceProperty.name}</td>
				</tr>
				<tr>
					<td><spring:message code="resourceProperty.label.type" /></td>
					<td>${resourceProperty.type}</td>
				</tr>
				<tr>
					<td><spring:message code="resourceProperty.label.defaultValue" /></td>
					<td>${resourceProperty.defaultValue}</td>
				</tr>
			</tbody>
		</table>
		<form:form action="${baseUrl}/${resourceProperty.id}?delete" method="POST">
		<span>
			<input type="button" value="${back}" onClick="parent.location='${baseUrl}'" />
			<input type="button" value="${edit}" onClick="parent.location='${baseUrl}/${resourceProperty.id}?form'" />
			<input type="submit" value="${remove}" />
		</span>
		</form:form>
	</div>
</body>
</html>