<%@page
	import="cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.PropertyType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>

<spring:message var="pageTitle" code="resource.pageTitle.list" />
<spring:message var="add" code="label.add" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="detail" code="label.detail" />
<spring:message var="schedules" code="label.schedules" />
<spring:message var="editProperties" code="label.editProperties" />
<c:url var="baseUrl" value="/resource" />

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
			<c:set var="type" value="MAIN" />
			<c:set var="firstResource" value="${resourceList[0]}" />
			<table class="table table-striped">
				<thead>
					<tr>
						<c:set var="propertyValueMap"
							value="${firstResource.propertyValuesMap}" />
						<c:forEach var="property" items="${propertyValueMap}">
							<c:set var="propertyName" value="${property.key}" />
							<c:if test="${propertyTypeMap[propertyName]==type}">
								<th><c:out value="${property.key}" /></th>
							</c:if>
						</c:forEach>
						<security:authorize ifAllGranted="PERM_RES_EDIT">
							<th><spring:message code="resource.label.capacity" /></th>
							<th><spring:message code="resource.label.duration" /></th>
							<th><spring:message code="resource.label.price" /></th>
							<th><spring:message code="resource.label.visible" /></th>
							<th><spring:message code="label.edit" /></th>
							<th><spring:message code="label.remove" /></th>
						</security:authorize>
						<th class="col-lg-1">${detail}</th>
						<th class="col-lg-1">${schedules}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="resource" items="${resourceList}">
						<c:set var="visible" value="${resource.visible}" />
						<security:authorize ifAllGranted="PERM_RES_EDIT">
							<c:set var="visible" value="${true}" />
						</security:authorize>
						<c:if test="${visible}">
							<tr>
								<c:set var="propertyValuesMap"
									value="${resource.propertyValuesMap}" />
								<c:forEach var="property" items="${propertyValuesMap}">
								    <c:set var="propertyName" value="${property.key}" />
								    <c:if test="${propertyTypeMap[propertyName]==type}">
										<td>${property.value}</td>
								    </c:if>
								</c:forEach>
								<security:authorize ifAllGranted="PERM_RES_EDIT">
									<td><c:out value="${resource.capacity}" /></td>
									<td><c:out value="${resource.duration}" /></td>
									<td><c:out value="${resource.price}" /> <spring:message code="currency.sign"/></td>
									<td>
										<span class="icon-fallback-glyph">
											<c:choose>
												<c:when test="${resource.visible}">
													<i class="icon ion-checkmark-round"></i>
												</c:when>
												<c:otherwise>
													<i class="icon ion-close-round"></i>
												</c:otherwise>
											</c:choose>
											<span class="text">${resource.visible}</span>
										</span>
									</td>
									<td class="col-lg-1">
										<a href="${baseUrl}/${resource.id}?form"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-edit"></i><span class="text">${edit}</span></span></button></a>
									</td>
									<td class="col-lg-1">
										<form:form action="${baseUrl}/${resource.id}"
											method="DELETE">
											<a data-target="#confirmDelete" data-toggle="modal"><button  type="submit" class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-trash-a"></i><span class="text">${remove}</span></span></button></a>
										</form:form>
									</td>
								</security:authorize>
								<td class="col-lg-1"><a href="${baseUrl}/${resource.id}"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-more"></i><span class="text">${detail}</span></span></button></a></td>
								<td class="col-lg-1"><a href="${baseUrl}/${resource.id}/schedule"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-android-alarm"></i><span class="text">${schedules}</span></span></button></a></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<security:authorize ifAllGranted="PERM_RES_EDIT">

			<a href="${baseUrl}/property"><button class="btn btn-default">${editProperties}</button></a>
			<a href="${baseUrl}?form" title="${add}"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-plus-round"></i><span class="text">${add}</span></span></button></a>
	
		</security:authorize>
	</div>
	<%@ include file="../includes/footer.jspf" %>
	<div class="modals" hidden="hidden">
		<%@ include file="../includes/confirmDelete.jspf" %>
	</div>
</body>
</html>