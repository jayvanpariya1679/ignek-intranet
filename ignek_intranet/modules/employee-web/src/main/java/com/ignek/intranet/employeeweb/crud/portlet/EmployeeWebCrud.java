package com.ignek.intranet.employeeweb.crud.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.common.employee.model.Employee;
import com.ignek.intranet.common.service.EmployeeService;
import com.ignek.intranet.common.service.EmployeeServiceImpl;
import com.ignek.intranet.employeeweb.constants.EmployeeWebConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author ignek
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=EmployeeWeb", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp", "javax.portlet.name=" + EmployeeWebConstants.PORTLET_ID,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)

public class EmployeeWebCrud extends MVCPortlet {

	@Reference
	private EmployeeService employeeService;

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		List<Employee> employeeList = new ArrayList<>();
		try {
			Hits hits = employeeService.getDataList();
			for (Document document : hits.getDocs()) {
				Employee employee = new Employee();
				long empId = GetterUtil.getLong(document.get(IntranetConstants.EMP_ID));
				String firsrtName = document.get(IntranetConstants.FIRST_NAME);
				String lastName = document.get(IntranetConstants.LAST_NAME);
				String emailAddress = document.get(IntranetConstants.EMAIL_ADDRESS);
				long phoneNumber = GetterUtil.getLong(document.get(IntranetConstants.PHONE_NUMBER));
				String addressLine1 = document.get(IntranetConstants.ADDRESS_LINE_1);
				String addressLine2 = document.get(IntranetConstants.ADDRESS_LINE_2);
				String city = document.get(IntranetConstants.CITY);
				long zipCode = GetterUtil.getLong(document.get(IntranetConstants.ZIPCODE));
				String designation = document.get(IntranetConstants.DESIGNATION);
				employee.setEmpId(empId);
				employee.setFirstName(firsrtName);
				employee.setLastName(lastName);
				employee.setEmailAddress(emailAddress);
				employee.setPhoneNumber(phoneNumber);
				employee.setAddressLine1(addressLine1);
				employee.setAddressLine2(addressLine2);
				employee.setCity(city);
				employee.setZipCode(zipCode);
				employee.setDesignation(designation);
				employeeList.add(employee);
			}
			renderRequest.setAttribute(IntranetConstants.EMPLOYEE_LIST, employeeList);
		} catch (ParseException | SearchException e) {
			_log.error(e.getMessage());
		}

		super.render(renderRequest, renderResponse);
	}

	private Log _log = LogFactoryUtil.getLog(EmployeeServiceImpl.class.getName());

}