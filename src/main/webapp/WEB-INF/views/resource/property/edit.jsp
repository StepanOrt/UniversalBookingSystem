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
	<h1>
		
	</h1>

	<div class="container">
		<%@ include file="../../includes/message.jspf"%>
		
		<c:set var="_method" value="POST" />
		<c:set var="_action" value="${baseUrl}?form" />
		<c:if test="${not empty resourceProperty.id}">
			<c:set var="_action" value="${baseUrl}/${resourceProperty.id}?form" />
		</c:if>
		<form:form method="${_method}" modelAttribute="resourceProperty" action="${_action}">
			<table>
				<caption>${pageTitle}</caption>
				<tbody>
					<tr>
						<td><spring:message code="resourceProperty.label.name" /></td>
						<td><form:input path="name"/></td>
						<form:errors path="name" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="resourceProperty.label.type" /></td>
						<td><form:select path="type"><form:options/></form:select></td>
						<form:errors path="type" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="resourceProperty.label.defaultValue" /></td>
						<td><form:textarea path="defaultValue"/></td>
						<form:errors path="defaultValue" element="td" cssClass="errorMessage"/>
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