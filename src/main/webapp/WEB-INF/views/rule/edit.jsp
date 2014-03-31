<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<spring:message var="pageTitle" code="rule.pageTitle.edit" />
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
	<h1>
		
	</h1>

	<div class="container">
		<%@ include file="../includes/message.jspf"%>
		
		<c:set var="_method" value="POST" />
		<c:set var="_action" value="${baseUrl}" />
		<c:if test="${not empty rule.id}">
			<c:set var="_action" value="${baseUrl}/${rule.id}" />
			<c:set var="_method" value="PUT" />
		</c:if>
		<form:form method="${_method}" modelAttribute="rule" action="${_action}">
			<table>
				<caption>${pageTitle}</caption>
				<tbody>
					<tr>
						<td><spring:message code="rule.label.name" /></td>
						<td><form:input path="name"/></td>
						<form:errors path="name" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="rule.label.action" /></td>
						<td><form:select path="action"><form:options/></form:select></td>
						<form:errors path="action" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="rule.label.priceChange" /></td>
						<td>
						<form:select path="priceChange">
    						<form:options items="${priceChangeList}" itemLabel="name" itemValue="id"/>
						</form:select>
						</td>
						<form:errors path="enabled" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="rule.label.enabled" /></td>
						<td><form:checkbox path="enabled"/></td>
						<form:errors path="enabled" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td><spring:message code="rule.label.expression" /></td>
						<td><form:textarea path="expression"/></td>
						<form:errors path="expression" element="td" cssClass="errorMessage"/>
					</tr>
					<tr>
						<td colspan="2">
							<ul>
								<c:forEach var="variable" items="${exposedVariables}">
									<li>${variable}</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
			<span>
				<input type="button" value="${back}" onClick="parent.location='${baseUrl}'" />
			 	<input type="submit" name="submit" value="${edit}">
			</span>
		</form:form>
	</div>
</body>
</html>