<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<spring:message var="pageTitle" code="resource.pageTitle.edit" />
<spring:message var="edit" code="label.edit" />
<spring:message var="remove" code="label.remove" />
<spring:message var="add" code="label.add" />
<spring:message var="back" code="label.back" />
<spring:message var="cancel" code="label.cancel" />
<spring:message var="reserve" code="label.reserve" />
<c:url var="baseUrl" value="/resource/${resource.id}/schedule" />

<html>
<head>
<title>${pageTitle}</title>
<%@ include file="../../includes/head.jspf"%>
</head>
<body>

	<div class="container">
		<%@ include file="../../includes/message.jspf"%>
		<c:set var="type" value="MAIN"/>
		<%@ include file="../includes/resourceParametersTable.jspf"%>
		<table>
			<caption>Schedules</caption>
			<thead>
				<tr>
					<th><spring:message code="schedule.label.start"/></th>
					<th><spring:message code="schedule.label.end"/></th>
					<th><spring:message code="schedule.label.capacity"/></th>
					<th><spring:message code="schedule.label.available"/></th>
					<th><spring:message code="schedule.label.price"/></th>
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
					<security:authorize ifAllGranted="PERM_RESERVE">
						<td>
						<c:choose>
						<c:when test="${reservationMap[schedule].status.toString().equals('RESERVED')}">
							<form:form action="${baseUrl}/${schedule.id}/reservation?cancel" method="PUT">
								<input type="submit" value="${cancel}">
							</form:form>
						</c:when>
						<c:otherwise>
							<form:form action="${baseUrl}/${schedule.id}/reservation?reserve" method="PUT">
								<input type="submit" value="${reserve}">
							</form:form>
						</c:otherwise>
						</c:choose>
						</td>
					</security:authorize>
					<security:authorize ifAllGranted="PERM_SCH_EDIT">
						<td>${schedule.visible}</td>
						<td><input type="button" value="${edit}" onClick="parent.location='${baseUrl}/${schedule.id}?form'"/></td>
						<td>
						<form:form action="${baseUrl}/${schedule.id}" method="DELETE">
							<input type="submit" value="${remove}" />
						</form:form>
						</td>
					</security:authorize>
				</tr>				
				</c:forEach>
			</tbody>
		</table>
		<span>
			<input type="button" value="${back}" onClick="parent.location='${baseUrl}/../'" />
		</span>
		<security:authorize ifAllGranted="PERM_SCH_EDIT">
			<span>
				<input type="button" value="${add}" onClick="parent.location='${baseUrl}?form'" />			
			</span>
		</security:authorize>
	</div>
</body>
</html>