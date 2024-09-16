<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="init.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<portlet:defineObjects />
<portlet:actionURL name="updateEmployee" var="updateEmployeeActionURL" />
<aui:form action="<%=updateEmployeeActionURL%>" name="employeeForm"
	method="POST" />
<aui:model-context bean="${employee}" model="<%= Employee.class %>" />

<div class="page-body">
	<div class="form-body">
		<button class="back-button"
			onClick="javascript: window.history.go(-1)">
			<svg width="18" height="18" viewBox="0 0 18 18" fill="none"
				xmlns="http://www.w3.org/2000/svg">
      			<path
					d="M17.7188 9C17.7188 4.18359 13.8164 0.28125 9 0.28125C4.18359 0.28125 0.28125 4.18359 0.28125 9C0.28125 13.8164 4.18359 17.7187 9 17.7188C13.8164 17.7188 17.7187 13.8164 17.7188 9ZM9 16.5938C4.82695 16.5938 1.40625 13.2152 1.40625 9C1.40625 4.82695 4.78477 1.40625 9 1.40625C13.173 1.40625 16.5938 4.78477 16.5938 9C16.5938 13.173 13.2152 16.5938 9 16.5938ZM10.125 12.375L6.75 9L10.125 5.625L10.125 12.375ZM11.25 5.625C11.25 4.62656 10.0371 4.12031 9.33047 4.83047L5.95547 8.20547C5.51602 8.64492 5.51602 9.35859 5.95547 9.79805L9.33047 13.173C10.0371 13.8797 11.25 13.3805 11.25 12.3785L11.25 5.625Z"
					fill="#C4C4C4" />
    		</svg>
		</button>
		<span class="form-heading">Employee Form</span>
		<aui:form action="<%=updateEmployeeActionURL%>" method="post">
		    <c:set var="readOnly" value="false" />

		    <c:if test="${param.view =='true'}">
		        <c:set var="readOnly" value="true" />
		    </c:if>

			<aui:input name="empId" type="hidden"
				value="<%=Long.parseLong(renderRequest.getParameter("empId"))%>" />
			<div class="name-input">
				<aui:input cssClass="first-name" label="First Name" name="firstName"
					type="text"
					placeholder="Enter your first name" readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="alpha" />
				</aui:input>
				<aui:input cssClass="last-name" label="Last Name" name="lastName"
					type="text"
					placeholder="Enter your last name" readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="alpha" />
				</aui:input>
			</div>
			<div class="designation-form">
				<aui:input cssClass="designation-body" label="Designation"
					name="designation" type="text"
					placeholder="Enter your designation" readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="string" />
				</aui:input>
			</div>

			<div class="email-phone-input">
				<aui:input cssClass="email" label="Email" name="emailAddress"
					type="text"
					placeholder="Enter your email" readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="email" />
				</aui:input>
				<aui:input cssClass="phone" label="Phone" name="phoneNumber"
					type="text"
					placeholder="Enter your phone number" readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="maxLength">10</aui:validator>
					<aui:validator name="minLength">10</aui:validator>
				</aui:input>
			</div>

			<div class="addressline-input">
				<aui:input cssClass="address-1" label="Address Line 1"
					name="addressLine1" type="text"
					placeholder="Enter your house no / Bldg. / Appt." readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="string" />
				</aui:input>
				<aui:input cssClass="address-2" label="Address Line 2"
					name="addressLine2" type="text"
					placeholder="Enter your street / lane / area" readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="string" />
				</aui:input>
			</div>

			<div class="city-zip-input">
				<aui:input cssClass="city-input" label="City" name="city"
					type="text" placeholder="Enter your city" readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="alpha" />
				</aui:input>
				<aui:input cssClass="zip-input" label="Post Code/ Zip Code"
					name="zipCode" type="text"
					placeholder="Enter your post code/ zip code" readonly="${readOnly}">
					<aui:validator name="required" />
					<aui:validator name="maxLength">10</aui:validator>
				</aui:input>
			</div>
			<c:if test="${readOnly == false}">
                <div class="submit-button">
                    <aui:input type="submit" value="SUBMIT" name="update"></aui:input>
                </div>
			</c:if>
		</aui:form>
	</div>
</div>