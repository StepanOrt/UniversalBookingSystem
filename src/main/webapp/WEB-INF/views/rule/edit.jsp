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
	    <c:set var="active" value="rules" />
		<%@ include file="../includes/navigation.jspf" %>
		<div class="container"> 
			<%@ include file="../includes/message.jspf" %>
			<div class="page-header">
				<h1>${pageTitle}</h1>
			</div>
			<c:set var="_method" value="POST" />
			<c:set var="_action" value="${baseUrl}" />
			<c:if test="${not empty rule.id}">
				<c:set var="_action" value="${baseUrl}/${rule.id}" />
				<c:set var="_method" value="PUT" />
			</c:if>
			<form:form cssClass="form-horizontal" method="${_method}" modelAttribute="rule" action="${_action}">
				<form:errors path="*">
					<div class="alert alert-danger"><spring:message code="error.global" /></div>
				</form:errors>
				
				<c:set var="groupError"><form:errors path='name'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="name"><spring:message code="rule.label.name"/></label>
       				<div class="controls col-xs-10">
       					<form:input path="name" cssClass="form-control" id="name" required="required"/>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>
   				
   				<c:set var="groupError"><form:errors path='action'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="action"><spring:message code="rule.label.action"/></label>
       				<div class="controls col-xs-10">
       					<form:select path="action" cssClass="form-control" id="action" required="required"><form:options/></form:select>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>
   				
   				<c:set var="groupError"><form:errors path='priceChange'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
       				<label class="control-label col-xs-2" for="priceChange"><spring:message code="rule.label.priceChange"/></label>
       				<div class="controls col-xs-10">
       					<form:select path="priceChange" cssClass="form-control" id="priceChange" required="required">
       						<c:forEach items="${priceChangeList}" var="priceChangeItem">
       							<c:set var="priceChangeSelected" value="${false}"/>
       							<c:if test="${priceChangeItem.id.equals(rule.priceChange.id)}">
       								<c:set var="priceChangeSelected" value="${true}"/>
       							</c:if>
       							<form:option selected="${priceChangeSelected ? 'selected' : ''}" value="${priceChangeItem.id}" label="${priceChangeItem.name}"></form:option>
       						</c:forEach>
       					</form:select>
       				</div>
       				<c:if test="${not empty groupError}">
						<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
					</c:if>
   				</div>
   				
   				<c:set var="groupError"><form:errors path='enabled'/></c:set>
				<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
					<div class="controls col-xs-offset-2 col-xs-10">
						<label class="checkbox" for="enabled"><form:checkbox id="enabled" path="enabled" /> <spring:message code="rule.label.enabled" /></label>
						<c:if test="${not empty groupError}">
							<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
						</c:if>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						 <h2 class="panel-title"><spring:message code="rule.label.expression"/></h2>
					</div>
					<div class="panel-body">
						<div class="col-xs-12">
								<c:set var="groupError"><form:errors path='expression'/></c:set>
								<div class="control-group form-group ${not empty groupError ? 'has-error' : ''}">
				       				<div class="controls">
				       					<form:textarea path="expression" cssClass="form-control" id="expression" required="required"/>
				       				</div>
				       				<c:if test="${not empty groupError}">
										<div class="help-block"><ul role="alert"><li>${groupError}</li></ul></div>
									</c:if>
				   				</div>
						</div>
						<div class="col-xs-12">
								<ul class="nav nav-pills">
									<c:forEach var="variable" items="${exposedVariables}">
										<li><a class="fill" href="#">${variable}</a></li>
									</c:forEach>
				   				</ul>
						</div>
						<div class="col-xs-12">
								<ul class="nav nav-pills">
									<c:forTokens var="operator" delims=" " items="+ - * / & || && == != < > <= >= ( )">
										<li><a class="fill" href="#">${operator}</a></li>
									</c:forTokens>
				   				</ul>
						</div>
					</div>
				</div>

			<div class="form-group">
				<div class="col-xs-2"></div>
				<div class="col-xs-offset-2 col-xs-10">
					<button type="submit" class="btn btn-primary">${edit}</button>
				</div>
			</div>
		</form:form>
	</div>
	<%@ include file="../includes/footer.jspf" %>
</body>
</html>