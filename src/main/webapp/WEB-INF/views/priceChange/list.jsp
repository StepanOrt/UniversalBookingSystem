<%@page import="cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.PropertyType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="priceChange.pageTitle.list" />
<spring:message var="add" code="label.add" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="back" code="label.back" />
<c:url var="baseUrl" value="/priceChange" />

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
			<thead>
			<tr>
				<th><spring:message code="priceChange.label.name" /></th>
				<th><spring:message code="priceChange.label.type" /></th>
				<th><spring:message code="priceChange.label.value" /></th>
				<th><spring:message code="label.edit"/></th>
				<th><spring:message code="label.remove"/></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="priceChange" items="${priceChangeList}" >
				<tr>		 
						<td><c:out value="${priceChange.name}"/></td>
						<td><c:out value="${priceChange.type}"/></td>
						<td><c:out value="${priceChange.value}"/></td>
						<td>
							<input type="button" value="${edit}" onClick="parent.location='${baseUrl}/${priceChange.id}?form'" />
						</td>
						<td>
							<form:form action="${baseUrl}/${priceChange.id}" method="DELETE">
								<input type="submit" value="${remove}" />
							</form:form>
						</td>
				</tr>
			</c:forEach>
			</tbody>
			</table>
			<input type="button" value="${add}" onClick="parent.location='${baseUrl}?form'" />			
	    </div>
</body>
</html>