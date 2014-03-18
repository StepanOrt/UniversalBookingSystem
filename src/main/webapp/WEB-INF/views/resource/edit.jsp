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
<spring:message var="back" code="label.back" />
<spring:message var="editProperties" code="label.editProperties" />
<c:url var="baseUrl" value="/resource" />

<html>
<head>
<title>${pageTitle}</title>
<%@ include file="../includes/head.jspf"%>
</head>
<body>
	<h1>
		
	</h1>

	<div class="container">
		<%@ include file="../includes/message.jspf"%>
		
		<c:set var="_method" value="POST" />
		<c:set var="_action" value="${baseUrl}" />
		<c:if test="${not empty resource.id}">
			<c:set var="_action" value="${baseUrl}/${resource.id}" />
			<c:set var="_method" value="PUT" />
		</c:if>
		<form:form method="${_method}" modelAttribute="resource" action="${_action}">
			<table>
				<caption>${pageTitle}</caption>
				<tbody>
					<tr>
						<td><spring:message code="resource.label.capacity" /></td>
						<td><form:input path="capacity"/></td>
						<form:errors path="capacity" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="resource.label.duration" /></td>
						<td><form:input path="duration"/></td>
						<form:errors path="duration" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="resource.label.price" /></td>
						<td><form:input path="price"/></td>
						<form:errors path="price" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="resource.label.visible" /></td>
						<td><form:checkbox path="visible"/></td>
						<form:errors path="visible" element="td" cssClass="errorMessage"/>
					</tr>
				</tbody>
			</table>
			<c:set var="editation" value="${true}"/>
			<c:forTokens var="type" items="MAIN,DETAIL" delims=",">
			 	<%@ include file="includes/resourceParametersTable.jspf" %>
			</c:forTokens>
			<security:authorize ifAllGranted="PERM_RES_EDIT">
				<c:set var="type" value="INTERNAL"/>
				<%@ include file="includes/resourceParametersTable.jspf" %>
			</security:authorize>
			<span>
				<input type="button" value="${editProperties}" onClick="parent.location='${baseUrl}/property'" />
				<input type="button" value="${back}" onClick="parent.location='${baseUrl}'" />
			 	<input type="submit" name="submit" value="${edit}">
			</span>
		</form:form>
	</div>
</body>
</html>