package com.ignek.intranet.employeeweb.crud.action;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.employee.model.Employee;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.ignek.intranet.employeeweb.constants.EmployeeWebConstants;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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

	@Reference
	private EmployeeLocalService employeeLocalService;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		long empId = ParamUtil.getLong(resourceRequest, IntranetConstants.EMP_ID, GetterUtil.DEFAULT_LONG);
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
			document.open();
			Employee employee = employeeLocalService.getEmployee(empId);
			document.add(
					new Paragraph(IntranetConstants.REPORT_FIRST_NAME + StringPool.COLON + employee.getFirstName()));
			document.add(new Paragraph(IntranetConstants.REPORT_LAST_NAME + StringPool.COLON + employee.getLastName()));
			document.add(new Paragraph(
					IntranetConstants.REPORT_EMAIL_ADDRESS + StringPool.COLON + employee.getEmailAddress()));
			document.add(new Paragraph(
					IntranetConstants.REPORT_PHONE_NUMBER + StringPool.COLON + employee.getPhoneNumber()));
			document.add(new Paragraph(
					IntranetConstants.REPORT_ADDRESS_LINE_1 + StringPool.COLON + employee.getAddressLine1()));
			document.add(new Paragraph(
					IntranetConstants.REPORT_ADDRESS_LINE_2 + StringPool.COLON + employee.getAddressLine2()));
			document.add(new Paragraph(IntranetConstants.REPORT_CITY + StringPool.COLON + employee.getCity()));
			document.add(new Paragraph(IntranetConstants.REPORT_ZIPCODE + StringPool.COLON + employee.getZipCode()));
			document.add(
					new Paragraph(IntranetConstants.REPORT_DESIGNATION + StringPool.COLON + employee.getDesignation()));
			document.close();
			pdfWriter.close();

			byte[] bytes = byteArrayOutputStream.toByteArray();
			resourceResponse.setContentType("application/pdf");
			resourceResponse.setProperty(HttpHeaders.CONTENT_DISPOSITION, "attachement;filename="
					+ employee.getFirstName() + StringPool.UNDERLINE + employee.getLastName() + ".pdf");
			OutputStream portletOutputStream = resourceResponse.getPortletOutputStream();
			portletOutputStream.write(bytes);

		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(DownloadMVCResourceCommand.class.getName());

}