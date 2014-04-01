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
	<%@ include file="../../includes/navigation.jspf"%>
	<div class="container">
		<%@ include file="../../includes/message.jspf"%>
		<div class="page-header">
			<h1>${pageTitle}</h1>
		</div>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
				<tr>
					<th><spring:message code="account.label.lastName" /></th>
					<th><spring:message code="account.label.firstName" /></th>
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
							<td><c:out value="${account.lastName}"/></td>
							<td><c:out value="${account.firstName}"/></td>
							<td><c:out value="${account.email}"/></td>
							<td>
								<span class="icon-fallback-glyph">
									<c:choose>
										<c:when test="${account.enabled}">
											<i class="icon ion-checkmark-round"></i>
										</c:when>
										<c:otherwise>
											<i class="icon ion-close-round"></i>
										</c:otherwise>
									</c:choose>
									<span class="text">${account.enabled}</span>
								</span>
							</td>
							<td>
								<c:if test="${account.isUser()}">
									<a href="${baseUrl}/${account.id}/topup"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-card"></i><span class="text">${topup}</span></span></button></a>
								</c:if>
							</td>
							<td class="col-lg-1">
								<a href="${baseUrl}/${account.id}"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-edit"></i><span class="text">${edit}</span></span></button></a>
							</td>
							<security:authorize ifAllGranted="PERM_MANAGE_ROLES">
							<td>
								<security:authentication property="principal.username" var="loggedEmail"/>
								<c:if test="${not account.email.equals(loggedEmail)}">
								<c:set var="makeParam" value="makeAdmin"/>
								<c:set var="role" value="user"/>
								<c:set var="makeLabel"><spring:message code="account.label.makeAdmin"/></c:set>
								<c:set var="icon" value="ion-person"/>
								<c:set var="otherIcon" value="ion-settings"/>
								<c:if test="${account.isAdmin()}">
									<c:set var="role" value="admin"/>
									<c:set var="icon" value="ion-settings"/>
									<c:set var="otherIcon" value="ion-person"/>
									<c:set var="makeParam" value="makeUser"/>
									<c:set var="makeLabel"><spring:message code="account.label.makeUser"/></c:set>
								</c:if>
								<form:form action="${baseUrl}/${account.id}?${makeParam}" method="PUT">
									<span>
										<button class="btn btn-default" type="submit">
											<span class="icon-fallback-glyph"><i class="icon ${icon}"></i> <i class="icon ion-arrow-right-c"></i> <i class="icon ${otherIcon}"></i><span class="text">${makeLabel}</span></span>
										</button>
									</span>
								</form:form>
								</c:if>
							</td>
							</security:authorize>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<%@ include file="../../includes/footer.jspf" %>
</body>
</html>