package com.ignek.portal.employee.web.crud.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.common.employee.response.EmployeeResponse;
import com.ignek.intranet.common.service.EmployeeService;
import com.ignek.portal.employee.web.constants.EmployeeWebConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

@Component(immediate = true, property = { "javax.portlet.name=" + EmployeeWebConstants.EMPLOYEE_WEB_PORTLET_KEY,
		"mvc.command.name=deleteEmployee" }, service = MVCActionCommand.class)
public class DeleteEmployeeMVCActionCommand extends BaseMVCActionCommand {

	@Reference
	private EmployeeService employeeService;

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		long empId = ParamUtil.getLong(actionRequest, IntranetConstants.EMP_ID, GetterUtil.DEFAULT_LONG);
		if (Validator.isNotNull(empId)) {
			EmployeeResponse employeeResponse = employeeService.deleteUser(empId);
			if (employeeResponse.getStatus() == Response.Status.OK) {
				SessionMessages.add(actionRequest, employeeResponse.getMessage());
				SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest)
						+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
			} else {
				SessionErrors.add(actionRequest, employeeResponse.getMessage());
				SessionMessages.add(actionRequest,
						PortalUtil.getPortletId(actionRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			}
		}
	}

}