package com.ignek.intranet.employeeweb.crud.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.common.service.EmployeeService;
import com.ignek.intranet.employeeweb.constants.EmployeeWebConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

@Component(immediate = true, property = { "javax.portlet.name=" + EmployeeWebConstants.PORTLET_ID,
		"mvc.command.name=deleteEmployee" }, service = MVCActionCommand.class)
public class DeleteMVCActionCommand extends BaseMVCActionCommand {

	@Reference
	EmployeeService employeeService;

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		long empId = ParamUtil.getLong(actionRequest, IntranetConstants.EMP_ID, GetterUtil.DEFAULT_LONG);
		if (Validator.isNotNull(empId)) {
			employeeService.deleteUser(empId);
		}
	}

}
