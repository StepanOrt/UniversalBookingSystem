<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="account.pageTitle.topup" />
<spring:message var="topup" code="account.label.topup" />
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
		<form:form method="PUT" modelAttribute="topup" action="${baseUrl}/${account.id}/topup">
			<table>
				<caption>${pageTitle}</caption>
				<tbody>
					<tr>
						<td><spring:message code="account.label.credit"/></td>
						<td>${account.credit}</td>
					</tr>
					<tr>
						<td><spring:message code="account.label.topup.amount" /></td>
						<td><form:input path="amount"/></td>
						<form:errors path="amount" element="td" cssClass="errorMessage"/>
					</tr>					
				</tbody>
			</table>
			<span>
				<input type="button" value="${back}" onClick="parent.location='${baseUrl}?admin'" />
			 	<input type="submit" name="submit" value="${topup}">
			</span>
		</form:form>
	</div>
</body>
</html>