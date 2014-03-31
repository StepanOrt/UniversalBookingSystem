<%@page import="cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.PropertyType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="account.pageTitle.list" />
<spring:message var="topup" code="account.label.topup" />
<spring:message var="edit" code="label.edit" />
<spring:message var="back" code="label.back" />
<c:url var="baseUrl" value="/account" />

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
				<th><spring:message code="account.label.firstName" /></th>
				<th><spring:message code="account.label.lastName" /></th>
				<th><spring:message code="account.label.email" /></th>
				<th><spring:message code="account.label.enabled" /></th>
				<th><spring:message code="account.label.topup"/></th>
				<th><spring:message code="label.edit"/></th>
				<security:authorize ifAllGranted="PERM_MANAGE_ROLES">
				<th><spring:message code="account.label.role"/></th>
				</security:authorize>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="account" items="${accountList}" >
				<tr>		 
						<td><c:out value="${account.firstName}"/></td>
						<td><c:out value="${account.lastName}"/></td>
						<td><c:out value="${account.email}"/></td>
						<td><c:out value="${account.enabled}"/></td>
						<td>
							<c:if test="${account.isUser()}">
							<input type="button" value="${topup}" onClick="parent.location='${baseUrl}/${account.id}/topup'" />
							</c:if>
						</td>
						<td>
							<input type="button" value="${edit}" onClick="parent.location='${baseUrl}/${account.id}'" />
						</td>
						<security:authorize ifAllGranted="PERM_MANAGE_ROLES">
						<td>
							<security:authentication property="principal.username" var="loggedEmail"/>
							<c:if test="${not account.email.equals(loggedEmail)}">
							<c:set var="makeParam" value="makeAdmin"/>
							<c:set var="makeLabel"><spring:message code="account.label.makeAdmin"/></c:set>
							<c:if test="${account.isAdmin()}">
								<c:set var="makeParam" value="makeUser"/>
								<c:set var="makeLabel"><spring:message code="account.label.makeUser"/></c:set>
							</c:if>
							<form:form action="${baseUrl}/${account.id}?${makeParam}" method="PUT">
								<input type="submit" value="${makeLabel}" />
							</form:form>
							</c:if>
						</td>
						</security:authorize>
				</tr>
			</c:forEach>
			</tbody>
			</table>
	    </div>
</body>
</html>