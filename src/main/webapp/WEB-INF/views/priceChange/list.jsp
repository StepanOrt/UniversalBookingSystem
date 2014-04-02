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
	<c:set var="active" value="resources" />
	<%@ include file="../includes/navigation.jspf"%>
	<div class="container">
		<%@ include file="../includes/message.jspf"%>
		<div class="page-header">
			<h1>${pageTitle}</h1>
		</div>
		<div class="table-responsive">
			<table class="table table-striped">
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
						<td class="col-lg-1">
							<a href="${baseUrl}/${priceChange.id}?form"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-edit"></i><span class="text">${edit}</span></span></button></a>
						</td>
						<td class="col-lg-1">
							<form:form action="${baseUrl}/${priceChange.id}"
								method="DELETE">
								<a data-target="#confirmDelete" data-toggle="modal"><button  type="submit" class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-trash-a"></i><span class="text">${remove}</span></span></button></a>
							</form:form>
						</td>
				</tr>
			</c:forEach>
			</tbody>
			</table>
			<a href="${baseUrl}?form" title="${add}"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-plus-round"></i><span class="text">${add}</span></span></button></a>		
	    </div>
	</div>
	<%@ include file="../includes/footer.jspf" %>
	<div class="modals" hidden="hidden">
		<%@ include file="../includes/confirmDelete.jspf" %>
	</div>
</body>
</html>