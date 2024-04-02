<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="init.jsp"%>
<portlet:defineObjects />
<portlet:actionURL name="addEmployee" var="addEmployeeActionURL"/>
<div class="page-body">
<div class="form-body">
<span class="form-heading">Employee Form</span>
<aui:form action="<%=addEmployeeActionURL %>" name="employeeForm" method="POST" >
<div class="name-input">
	<aui:input cssClass="first-name" name="firstName" label="First Name" placeholder="Enter your first name">
 		<aui:validator name="required"/>
 		<aui:validator name="alpha"/>
	</aui:input>
	<aui:input cssClass="last-name" name="lastName" label="Last Name" placeholder="Enter your last name">
 		<aui:validator name="required"/>
 		<aui:validator name="alpha"/>
	</aui:input>
	</div>
	<div class="designation-form">
		<aui:input cssClass="designation-body" label="Designation" name="designation" placeholder="Enter your designation">
	 		<aui:validator name="required"/>
	 		<aui:validator name="string"/>
		</aui:input>
	</div>
	
	<div class="email-phone-input">
	<aui:input cssClass="email" label="Email" name="emailAddress" placeholder="Enter your email">
 		<aui:validator name="required"/>
 		<aui:validator name="email"/>
	</aui:input>
	<aui:input cssClass="phone" label="Phone" name="phoneNumber" placeholder="Enter your phone number">
 		<aui:validator name="required"/>
 		<aui:validator name="maxLength">10</aui:validator>
 		<aui:validator name="minLength">10</aui:validator>
	</aui:input>
	</div>
	
	<div class="addressline-input">
	<aui:input cssClass="address-1" label="Address Line 1" name="addressLine1" placeholder="Enter your house no / Bldg. / Appt.">
 		<aui:validator name="required"/>
 		<aui:validator name="string"/>
	</aui:input>
	<aui:input cssClass="address-2" label="Address Line 2" name="addressLine2" placeholder="Enter your street / lane / area">
 		<aui:validator name="required"/>
 		<aui:validator name="string"/>
	</aui:input>
	</div>
	
	<div class="city-zip-input">
	<aui:input cssClass="city-input" label="City" name="city" placeholder="Enter your city">
 		<aui:validator name="required"/>
 		<aui:validator name="alpha"/>
	</aui:input>
	<aui:input cssClass="zip-input" label="Post Code/ Zip Code" name="zipCode" placeholder="Enter your post code/ zip code">
 		<aui:validator name="required"/>
 		<aui:validator name="maxLength">10</aui:validator>
	</aui:input>
	</div>
	<div class="submit-button">
	<aui:button type="submit" name="submit" value="SUBMIT"></aui:button></div>
</aui:form>
</div>
</div>