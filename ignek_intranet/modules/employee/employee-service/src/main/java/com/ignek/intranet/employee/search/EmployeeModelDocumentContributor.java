package com.ignek.intranet.employee.search;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

import com.ignek.intranet.common.constants.CommonConstants;
import com.ignek.intranet.employee.model.Employee;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

@Component(
        immediate = true,
        property = "indexer.class.name=com.ignek.intranet.employee.model.Employee",
        service = ModelDocumentContributor.class
)
public class EmployeeModelDocumentContributor implements ModelDocumentContributor<Employee> {

	private static final Log _log = LogFactoryUtil.getLog(EmployeeModelDocumentContributor.class);

	@Override
	public void contribute(Document document, Employee employee) {
		try {
			document.addDate(Field.MODIFIED_DATE, employee.getModifiedDate());
			document.addNumber(CommonConstants.EMP_ID, employee.getEmpId());
			document.addText(CommonConstants.FIRST_NAME, employee.getFirstName());
			document.addText(CommonConstants.LAST_NAME, employee.getLastName());
			document.addText(CommonConstants.EMAIL_ADDRESS, employee.getEmailAddress());
			document.addNumber(CommonConstants.PHONE_NUMBER, employee.getPhoneNumber());
			document.addText(CommonConstants.ADDRESS_LINE_1, employee.getAddressLine1());
			document.addText(CommonConstants.ADDRESS_LINE_2, employee.getAddressLine2());
			document.addText(CommonConstants.CITY, employee.getCity());
			document.addNumber(CommonConstants.ZIPCODE, employee.getZipCode());
			document.addText(CommonConstants.DESIGNATION, employee.getDesignation());

			Locale defaultLocale = PortalUtil.getSiteDefaultLocale(employee.getGroupId());

			String localizedTitle = LocalizationUtil.getLocalizedName(Field.TITLE, defaultLocale.toString());

			document.addText(localizedTitle, employee.getUserName());

		} catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index employee " + employee.getEmpId(), pe);
			}
		}
	}
}