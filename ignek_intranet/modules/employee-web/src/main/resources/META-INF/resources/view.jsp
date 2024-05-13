<%@page import="com.liferay.petra.string.StringPool"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page
	import="com.ignek.intranet.employee.service.EmployeeLocalService"%>
<%@page
	import="com.liferay.counter.kernel.service.CounterLocalServiceUtil"%>
<%@page import="com.liferay.info.pagination.Pagination"%>
<%@page
	import="com.ignek.intranet.employee.service.EmployeeLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.ignek.intranet.common.employee.model.Employee"%>
<%@ include file="init.jsp"%>
<%@page
	import="com.ignek.intranet.employeeweb.crud.portlet.EmployeeWebCrud"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="java.util.stream.Collectors"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<portlet:defineObjects />

<liferay-ui:error embed="<%=false%>" key="error-for-existing-email"
	message="Your email is already used, Try with another email." />

<liferay-ui:success embed="<%=false%>" key="employee-created"
	message="Employee Successfully Created" />

<liferay-ui:error key="employee-not-created" embed="<%=false%>"
	message="Employee not Created" />

<liferay-ui:success embed="<%=false%>" key="employee-updated"
	message="Employee Successfully Updated" />

<liferay-ui:error embed="<%=false%>" key="employee-not-updated"
	message="Employee not Updated" />

<liferay-ui:success embed="<%=false%>" key="employee-deleted"
	message="Employee Successfully Deleted" />

<liferay-ui:error embed="<%=false%>" key="employee-not-deleted"
	message="Employee not Deleted" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</script>

<portlet:actionURL var="addEmployeeActionURL">
	<portlet:param name="mvcPath" value="/update-employee.jsp" />
	<portlet:param name="empId" value="0" />
</portlet:actionURL>

<div class="portlet-background">
	<div class="header-add">
		<span class="page-heading">Employees</span> <span class="mb-5">
			<%
			long desiredRoleId = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "HR").getRoleId();
			long[] userRoleIds = UserLocalServiceUtil.getUser(themeDisplay.getUserId()).getRoleIds();
			List list = Arrays.stream(userRoleIds).boxed().collect(Collectors.toList());
			boolean hasRole = list.contains(desiredRoleId);
			if (hasRole) {
			%> <a href="<%=addEmployeeActionURL%>"
			class="btn text-white btn-default add-employee"> ADD NEW EMPLOYEE</a>
			<%
			}
			%>
		</span>
	</div>

	<liferay-portlet:renderURL varImpl="iteratorURL" />
	<liferay-ui:search-container
		total="<%=EmployeeLocalServiceUtil.getEmployeesCount()%>" delta="10"
		emptyResultsMessage="no-employees-found"
		iteratorURL="<%=iteratorURL%>" cssClass="table table-employee">
		<%
		int srNo = searchContainer.getStart() + 1;
		%>
		<liferay-ui:search-container-results>
			<%
			results = ListUtil.subList((List<Employee>) request.getAttribute("employeeList"), searchContainer.getStart(),
					searchContainer.getEnd());
			total = EmployeeLocalServiceUtil.getEmployeesCount();
			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
			%>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row cssClass="table-row"
			className="com.ignek.intranet.common.employee.model.Employee"
			modelVar="emp" keyProperty="empId">

			<portlet:actionURL var="updateEmployeeActionURL">
				<portlet:param name="mvcPath" value="/update-employee.jsp" />
				<portlet:param name="firstName" value="${emp.firstName}" />
				<portlet:param name="lastName" value="${emp.lastName}" />
				<portlet:param name="designation" value="${emp.designation}" />
				<portlet:param name="phoneNumber" value="${emp.phoneNumber}" />
				<portlet:param name="emailAddress" value="${emp.emailAddress}" />
				<portlet:param name="addressLine1" value="${emp.addressLine1}" />
				<portlet:param name="addressLine2" value="${emp.addressLine2}" />
				<portlet:param name="city" value="${emp.city}" />
				<portlet:param name="zipCode" value="${emp.zipCode}" />
				<portlet:param name="empId" value="${emp.empId}" />
			</portlet:actionURL>

			<portlet:actionURL var="viewEmployeeRenderURL">
				<portlet:param name="mvcPath" value="/view-employee.jsp" />
				<portlet:param name="firstName" value="${emp.firstName}" />
				<portlet:param name="lastName" value="${emp.lastName}" />
				<portlet:param name="designation" value="${emp.designation}" />
				<portlet:param name="phoneNumber" value="${emp.phoneNumber}" />
				<portlet:param name="emailAddress" value="${emp.emailAddress}" />
				<portlet:param name="addressLine1" value="${emp.addressLine1}" />
				<portlet:param name="addressLine2" value="${emp.addressLine2}" />
				<portlet:param name="city" value="${emp.city}" />
				<portlet:param name="zipCode" value="${emp.zipCode}" />
				<portlet:param name="empId" value="${emp.empId}" />
			</portlet:actionURL>

			<portlet:actionURL name="deleteEmployee"
				var="deleteEmployeeActionURL">
				<portlet:param name="empId" value="${emp.empId}" />
			</portlet:actionURL>

			<portlet:resourceURL id="downloadEmployee"
				var="downloadEmployeeResourceURL">
				<portlet:param name="empId" value="${emp.empId}" />
			</portlet:resourceURL>

			<liferay-ui:search-container-column-text name="Sr No"
				cssClass="sr-no" value="<%=String.valueOf(srNo++)%>" />

			<liferay-ui:search-container-column-text name="Name"
				href="<%= viewEmployeeRenderURL %>"
				value="${emp.firstName} ${emp.lastName}" />
			<liferay-ui:search-container-column-text name="Designation"
				value="${emp.designation}" />
			<liferay-ui:search-container-column-text name="Phone Number"
				value="${emp.phoneNumber}" />
			<liferay-ui:search-container-column-text name="Email Address"
				value="${emp.emailAddress}" />
			<liferay-ui:search-container-column-text name="City"
				value="${emp.city}" />
			<liferay-ui:search-container-column-text name="Actions">
				<%
				if (hasRole) {
				%>
				<a href="<%=updateEmployeeActionURL%>"
					class="btn  btn-default btn-sm px-2 py-1"> <svg width="19"
						height="13" viewBox="0 0 19 13" fill="none"
						xmlns="http://www.w3.org/2000/svg">
            <path
							d="M18.3033 1.67125L16.9113 0.792114C16.4478 0.499146 15.8399 0.352661 15.2321 0.352661C14.6242 0.352661 14.0164 0.499146 13.5525 0.79188L0.475916 9.05079L0.00462689 11.7278C-0.0547481 12.0648 0.365701 12.3527 0.88783 12.3527C0.920858 12.3527 0.953885 12.3515 0.987654 12.3491L5.22332 12.0536L18.3036 3.79235C19.231 3.20665 19.231 2.25696 18.3033 1.67125ZM4.67818 11.3373L1.2259 11.579L1.61035 9.39532L11.4031 3.2104L14.4747 5.15032L4.67818 11.3373ZM17.4639 3.26219L15.3141 4.61993L12.2426 2.68001L14.3923 1.32227C14.6164 1.18071 14.9148 1.10266 15.2321 1.10266C15.5494 1.10266 15.8474 1.18071 16.0719 1.32227L17.4639 2.20141C17.9266 2.49391 17.9266 2.96969 17.4639 3.26219Z"
							fill="#00979E" />
          </svg>
				</a>

				<div id="alert-box-delete">
					<div class="message-text"></div>
					<button class="yes-button">YES</button>
					<button class="no-button">NO</button>
				</div>
				<button class="btn  btn-default btn-sm px-2 py-1"
					onclick="deleteEmployee(${emp.empId}, '<%=deleteEmployeeActionURL%>');">
					<svg width="14" height="13" viewBox="0 0 14 13" fill="none"
						xmlns="http://www.w3.org/2000/svg">
            <path
							d="M0.25 1.85266H3.5L4.55 0.802661C4.68972 0.662941 4.8709 0.549537 5.07918 0.471431C5.28746 0.393324 5.51713 0.352661 5.75 0.352661L8.25 0.352661C8.48287 0.352661 8.71254 0.393324 8.92082 0.471431C9.1291 0.549537 9.31028 0.662941 9.45 0.802661L10.5 1.85266H13.75C13.8163 1.85266 13.8799 1.87242 13.9268 1.90758C13.9737 1.94274 14 1.99043 14 2.04016V2.41516C14 2.46489 13.9737 2.51258 13.9268 2.54774C13.8799 2.58291 13.8163 2.60266 13.75 2.60266H13.1594L12.1219 11.3284C12.0883 11.6082 11.9164 11.8684 11.64 12.058C11.3636 12.2475 11.0026 12.3526 10.6281 12.3527H3.37187C2.99735 12.3526 2.63639 12.2475 2.35998 12.058C2.08356 11.8684 1.91168 11.6082 1.87813 11.3284L0.840625 2.60266H0.25C0.183696 2.60266 0.120108 2.58291 0.0732231 2.54774C0.0263386 2.51258 0 2.46489 0 2.41516V2.04016C0 1.99043 0.0263386 1.94274 0.0732231 1.90758C0.120108 1.87242 0.183696 1.85266 0.25 1.85266ZM8.65 1.25266C8.60326 1.20622 8.54283 1.16852 8.47344 1.1425C8.40405 1.11648 8.32758 1.10284 8.25 1.10266H5.75C5.67242 1.10284 5.59595 1.11648 5.52656 1.1425C5.45717 1.16852 5.39674 1.20622 5.35 1.25266L4.75 1.85266H9.25L8.65 1.25266ZM2.875 11.2605C2.88529 11.3539 2.94214 11.441 3.03422 11.5044C3.12631 11.5678 3.24686 11.6029 3.37187 11.6027H10.6281C10.7531 11.6029 10.8737 11.5678 10.9658 11.5044C11.0579 11.441 11.1147 11.3539 11.125 11.2605L12.1562 2.60266H1.84375L2.875 11.2605Z"
							fill="#00979E" />
          </svg>
				</button>
				<%
				}
				%>
				<%
				String pdf = (String) request.getAttribute("byteArrayOutputStream");
				%>

				<a class="btn btn-default btn-sm px-2 py-1"
					href="<%=downloadEmployeeResourceURL%>"> <svg width="16"
						height="16" viewBox="0 0 16 16" fill="none"
						xmlns="http://www.w3.org/2000/svg">
			        <path
							d="M8 14.5C6.27609 14.5 4.62279 13.8152 3.40381 12.5962C2.18482 11.3772 1.5 9.72391 1.5 8C1.5 6.27609 2.18482 4.62279 3.40381 3.40381C4.62279 2.18482 6.27609 1.5 8 1.5C9.72391 1.5 11.3772 2.18482 12.5962 3.40381C13.8152 4.62279 14.5 6.27609 14.5 8C14.5 9.72391 13.8152 11.3772 12.5962 12.5962C11.3772 13.8152 9.72391 14.5 8 14.5ZM8 0C5.87827 0 3.84344 0.842855 2.34315 2.34315C0.842855 3.84344 0 5.87827 0 8C0 10.1217 0.842855 12.1566 2.34315 13.6569C3.84344 15.1571 5.87827 16 8 16C10.1217 16 12.1566 15.1571 13.6569 13.6569C15.1571 12.1566 16 10.1217 16 8C16 5.87827 15.1571 3.84344 13.6569 2.34315C12.1566 0.842855 10.1217 0 8 0ZM11.7781 9.20625C11.9187 9.075 12 8.89062 12 8.69687C12 8.3125 11.6875 8 11.3031 8H9.5V5C9.5 4.44688 9.05313 4 8.5 4H7.5C6.94688 4 6.5 4.44688 6.5 5V8H4.69688C4.3125 8 4 8.3125 4 8.69687C4 8.89062 4.08125 9.075 4.22188 9.20625L7.56875 12.3281C7.6875 12.4375 7.84062 12.5 8 12.5C8.15938 12.5 8.31562 12.4375 8.43125 12.3281L11.7781 9.20625Z"
							fill="#00979E" fill-opacity="0.7" />
			      </svg>
				</a>

			</liferay-ui:search-container-column-text>

		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>

</div>

<script>
  function deleteEmployee(empId, deleteUrl) {
  		    
         var confirmBox = $("#alert-box-delete");
         confirmBox.find(".message-text").text("Are you sure, you want to delete\nthis employee ?");
  
         confirmBox.find(".yes-button").unbind().click(function () {
             location.href = deleteUrl; 
         });
  
         confirmBox.find(".no-button").unbind().click(function () {
             confirmBox.hide();
         });
  
         confirmBox.show();
     }
</script>