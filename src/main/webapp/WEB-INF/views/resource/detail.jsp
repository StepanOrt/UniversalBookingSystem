<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="resource.pageTitle.detail" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="schedules" code="label.schedules" />
<spring:message var="back" code="label.back" />
<c:url var="baseUrl" value="/resource" />

<html>
	<head>
		<title>${pageTitle}</title>
		<%@ include file="../includes/head.jspf"%>
	</head>
	<body>
		<%@ include file="../includes/navigation.jspf" %>
		<div class="container" itemscope itemtype="http://schema.org/LocalBusiness"> 
			<%@ include file="../includes/message.jspf" %>
			<div class="page-header">
				<h1>${pageTitle}</h1>
			</div>
			<security:authorize ifAllGranted="PERM_RES_EDIT">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<label class="col-xs-2"><spring:message code="resource.label.price"/></label>
							<span class="col-xs-10">${resource.price} <spring:message code="currency.sign"/></span>
						</div>
						<div class="row">
							<label class="col-xs-2"><spring:message code="resource.label.capacity"/></label>
							<span class="col-xs-10">${resource.capacity}</span>
						</div>
						<div class="row">
							<label class="col-xs-2"><spring:message code="resource.label.duration"/></label>
							<span class="col-xs-10">${resource.duration}</span>
						</div>
						<div class="row">
							<label class="col-xs-2"><spring:message code="resource.label.visible"/></label>
							<span class="icon-fallback-glyph col-xs-10">
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
						</div>
					</div>
				</div>
			</security:authorize>
			<div>
				<c:forTokens var="type" items="MAIN,DETAIL" delims=",">
					<%@ include file="includes/resourceParametersTable.jspf" %>
				</c:forTokens>
				<security:authorize ifAllGranted="PERM_RES_EDIT">
					<c:set var="type" value="INTERNAL"/>
					<%@ include file="includes/resourceParametersTable.jspf" %>
				</security:authorize>
				<div class="btn-toolbar" role="toolbar">
					<div class="btn-group">
						<a href="${baseUrl}/${resource.id}/schedule" title="${schedules}"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-android-alarm"></i><span class="text">${schedules}</span></span></button></a>
					</div>
					<security:authorize ifAllGranted="PERM_RES_EDIT">
						<div class="btn-group">
							<a href="${baseUrl}/${resource.id}?form" title="${edit}"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-edit"></i><span class="text">${edit}</span></span></button></a>
						</div>
						<div class="btn-group">
							<form:form action="${baseUrl}/${resource.id}" method="DELETE">
								<a data-target="#confirmDelete" data-toggle="modal" title="${remove}"><button type="submit" class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-trash-a"></i><span class="text">${remove}</span></span></button></a>
							</form:form>
						</div>
					</security:authorize>
				</div>
			</div>
		</div>
		<%@ include file="../includes/footer.jspf" %>
		<div class="modals" hidden="hidden">
			<%@ include file="../includes/confirmDelete.jspf" %>
		</div>
	</body>
</html>