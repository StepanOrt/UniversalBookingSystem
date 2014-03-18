<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="resourceProperty.pageTitle.list" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="detail" code="label.detail" />
<spring:message var="add" code="label.add" />
<spring:message var="back" code="label.back" />
<c:url var="resourceUrl" value="/resource" />
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
			<thead>
			<tr>
				<th><spring:message code="resourceProperty.label.name" /></th>
				<th><spring:message code="resourceProperty.label.type" /></th>
				<th><spring:message code="resourceProperty.label.defaultValue" /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="resourceProperty" items="${resourcePropertyList}">	
				<tr>
					<td>${resourceProperty.name}</td>
					<td>${resourceProperty.type}</td>
					<td>${resourceProperty.defaultValue}</td>
					<td>
						<input type="button" value="${edit}" onClick="parent.location='${baseUrl}/${resourceProperty.id}?form'" />
					</td>
					<td>
						<form:form action="${baseUrl}/${resourceProperty.id}?delete" method="POST">
							<input type="submit" value="${remove}" />
						</form:form>
					</td>
					<td>
						<input type="button" value="${detail}" onClick="parent.location='${baseUrl}/${resourceProperty.id}'" />
					</td>
				</tr>
			</c:forEach>
			</tbody>
			</table>
			<span>
			    <input type="button" value="${back}" onClick="parent.location='${resourceUrl}'" />
				<input type="button" value="${add}" onClick="parent.location='${baseUrl}?form'" />			
			</span>
	    </div>
</body>
</html>