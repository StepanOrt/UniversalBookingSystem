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
	<h1>
		
	</h1>

	<div class="container">
		<%@ include file="../../includes/message.jspf"%>
		<form:form method="PUT" modelAttribute="account" action="${baseUrl}/${account.id}">
			<table>
				<caption>${pageTitle}</caption>
				<tbody>
					<tr>
						<td><spring:message code="account.label.firstName" /></td>
						<td><form:input path="firstName"/></td>
						<form:errors path="firstName" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="account.label.lastName" /></td>
						<td><form:input path="lastName"/></td>
						<form:errors path="lastName" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="account.label.email" /></td>
						<td><form:input path="email"/></td>
						<form:errors path="email" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="account.label.emailOk" /></td>
						<td><form:checkbox path="emailOk"/></td>
						<form:errors path="emailOk" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="account.label.enabled" /></td>
						<td><form:checkbox path="enabled"/></td>
						<form:errors path="enabled" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="account.label.credit" /></td>
						<td><form:input path="credit"/></td>
						<form:errors path="credit" element="td" cssClass="errorMessage"/>
					</tr>
				</tbody>
			</table>
			<span>
				<input type="button" value="${back}" onClick="parent.location='${baseUrl}?admin'" />
			 	<input type="submit" name="submit" value="${edit}">
			</span>
		</form:form>
	</div>
</body>
</html>