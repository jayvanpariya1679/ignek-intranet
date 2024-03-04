<%@ include file="init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<liferay-portlet:actionURL portletConfiguration="<%=true%>"
	var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="<%=true%>"
	var="configurationRenderURL" />
<link rel="stylesheet" href="main.scss">
<div class="config">
	<aui:form action="<%=configurationActionURL%>" method="post" name="fm">
		<aui:input name="<%=Constants.CMD%>" type="hidden"
			value="<%=Constants.UPDATE%>" />
		<aui:input name="redirect" type="hidden"
			value="<%=configurationRenderURL%>" />
		<aui:fieldset>
			<aui:select label="Card" name="card" value="<%=card%>">
				<aui:option selected="true" value="Employees">Employees</aui:option>
				<aui:option value="Technologies">Technologies</aui:option>
				<aui:option value="Images">Images</aui:option>
				<aui:option value="Users">Users</aui:option>
			</aui:select>
		</aui:fieldset>
		<aui:button-row>
			<aui:button type="submit"></aui:button>
		</aui:button-row>
	</aui:form>
</div>