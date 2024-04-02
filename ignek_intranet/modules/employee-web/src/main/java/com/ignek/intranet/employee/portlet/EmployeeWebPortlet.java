package com.ignek.intranet.employee.portlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.employee.service.EmployeeServiceImpl;
//import com.ignek.intranet.common.employee.service.EmployeeServiceImpl;
import com.ignek.intranet.employee.constants.EmployeeWebPortletKeys;
import com.ignek.intranet.employee.model.Employee;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.ignek.intranet.employee.service.EmployeeLocalServiceUtil;
import com.ignek.intranet.employee.service.persistence.EmployeePersistence;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author ignek
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=EmployeeWeb", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp", "javax.portlet.name=" + EmployeeWebPortletKeys.EMPLOYEEWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)

public class EmployeeWebPortlet extends MVCPortlet {

	private Log log = LogFactoryUtil.getLog(this.getClass().getName());

	@Reference
	CounterLocalService counterLocalService;

	@Reference
	private EmployeeLocalService employeeLocalService;

	@Reference
	private UserLocalService userLocalService;
	
//	@Reference
	EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl();
	
	@ProcessAction(name = "addEmployee")
	public void addEmployee(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException {
		 ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		 long empId = counterLocalService.increment();
		long userUId=empId;
		long userId = themeDisplay.getUserId();
		long companyId= themeDisplay.getCompanyId();
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		String emailAddress = ParamUtil.getString(actionRequest, "emailAddress");
		long phoneNumber = ParamUtil.getLong(actionRequest, "phoneNumber");
		String addressLine1 = ParamUtil.getString(actionRequest, "addressLine1");
		String addressLine2 = ParamUtil.getString(actionRequest, "addressLine2");
		String city = ParamUtil.getString(actionRequest, "city");
		long zipCode = ParamUtil.getLong(actionRequest, "zipCode");
		String designation = ParamUtil.getString(actionRequest, "designation");
		
		employeeLocalService.addEmployee(empId, userUId, firstName, lastName, emailAddress, phoneNumber, addressLine1, addressLine2, city, zipCode, designation);
 
		employeeServiceImpl.addUser(userId,userUId, firstName, lastName,companyId,emailAddress);
		
	}
	
	
	
	
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		
		List<Employee> employeeList = employeeLocalService.getEmployees(QueryUtil.ALL_POS,QueryUtil.ALL_POS);
		renderRequest.setAttribute("employeeList", employeeList);
		
		super.render(renderRequest, renderResponse);
	}	
	

	
	@ProcessAction(name = "updateEmployee")
	public void updateEmployee(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long empId = ParamUtil.getLong(actionRequest, "empId", GetterUtil.DEFAULT_LONG);
		long empUserUId= employeeLocalService.fetchEmployee(empId).getUserId();
		long userUId= userLocalService.getUser(empUserUId+1).getUserId();
		long userId = themeDisplay.getUserId();
		long companyId= themeDisplay.getCompanyId();
		String firstName = ParamUtil.getString(actionRequest, "firstName", GetterUtil.DEFAULT_STRING);
		String lastName = ParamUtil.getString(actionRequest, "lastName", GetterUtil.DEFAULT_STRING);
		String emailAddress = ParamUtil.getString(actionRequest, "emailAddress", GetterUtil.DEFAULT_STRING);
		long phoneNumber = ParamUtil.getLong(actionRequest, "phoneNumber", GetterUtil.DEFAULT_LONG);
		String addressLine1 = ParamUtil.getString(actionRequest, "addressLine1", GetterUtil.DEFAULT_STRING);
		String addressLine2 = ParamUtil.getString(actionRequest, "addressLine2", GetterUtil.DEFAULT_STRING);
		String city = ParamUtil.getString(actionRequest, "city", GetterUtil.DEFAULT_STRING);
		long zipCode = ParamUtil.getLong(actionRequest, "zipCode", GetterUtil.DEFAULT_LONG);
		String designation = ParamUtil.getString(actionRequest, "designation", GetterUtil.DEFAULT_STRING);
		
		employeeLocalService.updateEmployee(empId, empUserUId, firstName, lastName, emailAddress, phoneNumber, addressLine1, addressLine2, city, zipCode, designation);
		
		employeeServiceImpl.updateUser(userId, userUId,firstName, lastName, companyId, emailAddress);
		
	}
 
	
	
	@ProcessAction(name = "deleteEmployee")
	public void deleteEmployee(ActionRequest actionRequest, ActionResponse actionResponse) {

		long empId = ParamUtil.getLong(actionRequest, "empId", GetterUtil.DEFAULT_LONG);
		long empUserUId= employeeLocalService.fetchEmployee(empId).getUserId();
		
		try {
			long userUId= userLocalService.getUser(empUserUId+1).getUserId();
			employeeLocalService.deleteEmployee(empId);
			userLocalService.deleteUser(userUId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	
	

}