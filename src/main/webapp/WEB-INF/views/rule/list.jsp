<%@page import="cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.PropertyType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="rule.pageTitle.list" />
<spring:message var="add" code="label.add" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="back" code="label.back" />
<c:url var="baseUrl" value="/rule" />

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
				<th><spring:message code="rule.label.name" /></th>
				<th><spring:message code="rule.label.enabled" /></th>
				<th><spring:message code="rule.label.action" /></th>
				<th><spring:message code="rule.label.priceChange" /></th>				
				<th><spring:message code="label.edit"/></th>
				<th><spring:message code="label.remove"/></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="rule" items="${ruleList}" >
				<tr>		 
						<td><c:out value="${rule.name}"/></td>
						<td><c:out value="${rule.enabled}"/></td>
						<td><c:out value="${rule.action}"/></td>
						<td><c:out value="${rule.priceChange.name}"/></td>				
						<td>
							<input type="button" value="${edit}" onClick="parent.location='${baseUrl}/${rule.id}?form'" />
						</td>
						<td>
							<form:form action="${baseUrl}/${rule.id}" method="DELETE">
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