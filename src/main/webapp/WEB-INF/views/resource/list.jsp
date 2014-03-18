<%@page import="cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.PropertyType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="resource.pageTitle.list" />
<spring:message var="add" code="label.add" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="detail" code="label.detail" />
<spring:message var="schedules" code="label.schedules" />
<spring:message var="back" code="label.back" />
<c:url var="baseUrl" value="/resource" />

<html>
<head>
<title>${pageTitle}</title>
<%@ include file="../includes/head.jspf"%>
</head>
<body>
	<div class="container">
		<%@ include file="../includes/message.jspf"%>
			<c:set var="type" value="MAIN"/>
			<c:set var="firstResource" value="${resourceList[0]}"/>
		   	<table>
				<caption>${pageTitle}</caption>
			<thead>
			<tr>
				<c:set var="propertyValueMap" value="${firstResource.propertyValuesMap}"/>
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
					<th><spring:message code="label.edit"/></th>
					<th><spring:message code="label.remove"/></th>
				</security:authorize>
				<th>${detail}</th>
				<th>${schedules}</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="resource" items="${resourceList}" >
				<c:set var="visible" value="${resource.visible}"/>
				<security:authorize ifAllGranted="PERM_RES_EDIT">
					<c:set var="visible" value="${true}"/>
				</security:authorize>
				<c:if test="${visible}">
				<tr>		 
					<c:set var="propertyValuesMap" value="${resource.propertyValuesMap}"/> 
					<c:forEach var="property" items="${propertyValuesMap}"> 
 						<td>${property.value}</td> 
					</c:forEach>				
					<security:authorize ifAllGranted="PERM_RES_EDIT">
						<td><c:out value="${resource.capacity}"/></td>
						<td><c:out value="${resource.duration}"/></td>
						<td><c:out value="${resource.price}"/></td>
						<td><c:out value="${resource.visible}"/></td>				
						<td>
							<input type="button" value="${edit}" onClick="parent.location='${baseUrl}/${resource.id}?form'" />
						</td>
						<td>
							<form:form action="${baseUrl}/${resource.id}" method="DELETE">
								<input type="submit" value="${remove}" />
							</form:form>
						</td>
					</security:authorize>
					<td>
						<input type="button" value="${detail}" onClick="parent.location='${baseUrl}/${resource.id}'" />
					</td>
					<td>
						<input type="button" value="${schedules}" onClick="parent.location='${baseUrl}/${resource.id}/schedule'" />
					</td>
				</tr>
				</c:if>
			</c:forEach>
			</tbody>
			</table>
			<security:authorize ifAllGranted="PERM_RES_EDIT">
			<span>
				<input type="button" value="${add}" onClick="parent.location='${baseUrl}?form'" />			
			</span>
			</security:authorize>
	    </div>
</body>
</html>