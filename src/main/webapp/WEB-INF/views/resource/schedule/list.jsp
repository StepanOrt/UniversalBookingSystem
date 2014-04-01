<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<spring:message var="pageTitle" code="schedule.pageTitle.list" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="add" code="label.add" />
<spring:message var="back" code="label.back" />
<spring:message var="cancel" code="label.cancel" />
<spring:message var="reserve" code="label.reserve" />
<spring:message var="schedules" code="label.schedules" />
<c:url var="baseUrl" value="/resource/${resource.id}/schedule" />

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
		<c:set var="type" value="MAIN"/>
		<%@ include file="../includes/resourceParametersTable.jspf"%>
		<h2>${schedules}</h2>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
				<tr>
					<th><spring:message code="schedule.label.start"/></th>
					<th><spring:message code="schedule.label.end"/></th>
					<th><spring:message code="schedule.label.capacity"/></th>
					<th><spring:message code="schedule.label.available"/></th>
					<th><spring:message code="schedule.label.price"/></th>
					<th><spring:message code="schedule.label.note"/></th>
					<security:authorize ifAllGranted="PERM_RESERVE">
					<th><spring:message code="schedule.label.reserve"/></th>
					</security:authorize>
					<security:authorize ifAllGranted="PERM_SCH_EDIT">
						<th><spring:message code="schedule.label.visible"/></th>
						<th><spring:message code="label.edit"/></th>
						<th><spring:message code="label.remove"/></th>		
					</security:authorize>
				</tr>				
			</thead>
			<tbody>
				<c:set var="schedulesSet" value="${resource.visibleSchedules}" />
				<security:authorize ifAllGranted="PERM_SCH_EDIT">
					<c:set var="schedulesSet" value="${resource.schedules}" />
				</security:authorize>
				<c:forEach var="schedule" items="${schedulesSet}">
				<tr>
					<fmt:formatDate value="${schedule.start}" var="startDateTimeString" pattern="dd/MM/yyyy HH:mm" />
					<fmt:formatDate value="${schedule.end}" var="endDateTimeString" pattern="dd/MM/yyyy HH:mm" />
					<td>${startDateTimeString}</td>
					<td>${endDateTimeString}</td>
					<td>${schedule.capacity}</td>
					<td>${schedule.capacityAvailable}</td>
					<td>${priceMap[schedule]}</td>
					<td>
						<c:if test="${not empty schedule.note}">
							<c:set var="noteLabel"><spring:message code="schedule.label.note"/></c:set>
							<a data-target="#note${schedule.id}" data-toggle="modal"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-information-circled"></i><span class="text">${noteLabel}</span></span></button></a>
							<div class="note modal fade" id="note${schedule.id}">
								<div class="modal-dialog modal-lg">
    								<div class="modal-content">
     									<div class="modal-header">
     										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        									<h4 class="modal-title">${noteLabel}</h4>
     									</div>
     								 	<div class="modal-body">${schedule.note}</div>
     								</div>
     							</div>	 
							</div>					
						</c:if>
					</td>
					<security:authorize ifAllGranted="PERM_RESERVE">
						<td>
						<c:choose>
						<c:when test="${reservationMap[schedule].status.toString().equals('RESERVED')}">
							<form:form action="${baseUrl}/${schedule.id}/reservation?cancel" method="PUT">
								<button class="btn btn-default" type="submit">${cancel}</button>
							</form:form>
						</c:when>
						<c:otherwise>
							<form:form action="${baseUrl}/${schedule.id}/reservation?reserve" method="PUT">
								<button class="btn btn-default" type="submit">${reserve}</button>
							</form:form>
						</c:otherwise>
						</c:choose>
						</td>
					</security:authorize>
					<security:authorize ifAllGranted="PERM_SCH_EDIT">
						<td>
							<span class="icon-fallback-glyph">
								<c:choose>
									<c:when test="${schedule.visible}">
										<i class="icon ion-checkmark-round"></i>
									</c:when>
									<c:otherwise>
										<i class="icon ion-close-round"></i>
									</c:otherwise>
								</c:choose>
								<span class="text">${schedule.visible}</span>
							</span>
						</td>
						<td class="col-lg-1">
							<button class="btn btn-default" onClick="parent.location='${baseUrl}/${schedule.id}?form'"><span class="icon-fallback-glyph"><i class="icon ion-edit"></i><span class="text">${edit}</span></span></button>
						</td>
						<td class="col-lg-1">
							<form:form action="${baseUrl}/${schedule.id}"
								method="DELETE">
								<a data-target="#confirmDelete" data-toggle="modal"><button  type="submit" class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-trash-a"></i><span class="text">${remove}</span></span></button></a>
							</form:form>
						</td>
					</security:authorize>
				</tr>				
				</c:forEach>
			</tbody>
		</table>
		</div>
		<security:authorize ifAllGranted="PERM_SCH_EDIT">
			<a href="${baseUrl}?form" title="${add}"><button class="btn btn-default"><span class="icon-fallback-glyph"><i class="icon ion-plus-round"></i><span class="text">${add}</span></span></button></a>
		</security:authorize>
	</div>
	<%@ include file="../../includes/footer.jspf" %>
	<div class="modals" hidden="hidden">
		<%@ include file="../../includes/confirmDelete.jspf" %>
	</div>
</body>
</html>