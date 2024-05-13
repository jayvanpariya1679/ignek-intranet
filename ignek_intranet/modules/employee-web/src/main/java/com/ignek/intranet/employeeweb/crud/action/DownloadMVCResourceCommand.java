package com.ignek.intranet.employeeweb.crud.action;

import java.io.OutputStream;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.common.util.CommonUtil;
import com.ignek.intranet.employee.model.Employee;
import com.ignek.intranet.employeeweb.constants.EmployeeWebConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

@Component(immediate = true, property = { "javax.portlet.name=" + EmployeeWebConstants.PORTLET_ID,
		"mvc.command.name=downloadEmployee" }, service = MVCResourceCommand.class)
public class DownloadMVCResourceCommand implements MVCResourceCommand {

	private CommonUtil commonUtil;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		long empId = ParamUtil.getLong(resourceRequest, IntranetConstants.EMP_ID, GetterUtil.DEFAULT_LONG);
		try {
			Employee employee = commonUtil.getEmployee(empId);
			byte[] bytes = commonUtil.getPDFDocument(empId).toByteArray();
			resourceResponse.setContentType(IntranetConstants.APPLICATION_PDF);
			resourceResponse.setProperty(HttpHeaders.CONTENT_DISPOSITION,
					IntranetConstants.ATTACHEMENT_FILENAME + employee.getFirstName() + StringPool.UNDERLINE
							+ employee.getLastName() + IntranetConstants.PDF_EXTENTION);
			OutputStream portletOutputStream = resourceResponse.getPortletOutputStream();
			portletOutputStream.write(bytes);

		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(DownloadMVCResourceCommand.class.getName());

}