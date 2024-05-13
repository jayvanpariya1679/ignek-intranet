package com.ignek.intranet.common.util;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.employee.model.Employee;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

@Component(immediate = true, service = CommonUtil.class)
public class CommonUtil {

	@Reference
	private EmployeeLocalService employeeLocalService;

	@Reference
	private SearchEngineHelper searchEngineHelper;

	public ServiceContext getServiceContext(long companyId, long userId) {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);
		return serviceContext;
	}

	public SearchContext setSearchContextData() {
		SearchContext searchContext = new SearchContext();
		searchContext.setCompanyId(PortalUtil.getDefaultCompanyId());
		searchContext.setStart(QueryUtil.ALL_POS);
		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setSorts(new Sort(IntranetConstants.EMP_ID, Sort.LONG_TYPE, false));
		searchContext.setSearchEngineId(searchEngineHelper.getDefaultSearchEngineId());
		return searchContext;
	}

	public Hits getDataList() throws ParseException, SearchException {
		BooleanQuery mainQuery = new BooleanQueryImpl();
		BooleanQuery booleanQuery = new BooleanQueryImpl();
		mainQuery.addRequiredTerm(Field.COMPANY_ID, PortalUtil.getDefaultCompanyId());
		mainQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, Employee.class.getName());
		booleanQuery.add(mainQuery, BooleanClauseOccur.MUST);
		SearchContext searchContext = setSearchContextData();
		IndexSearcher indexSearcher = SearchEngineHelperUtil
				.getSearchEngine(searchEngineHelper.getDefaultSearchEngineId()).getIndexSearcher();
		Hits hits = indexSearcher.search(searchContext, booleanQuery);
		return hits;
	}

	public long fetchUserIdByEmpId(long empId) throws PortalException {
		long userId = GetterUtil.DEFAULT_LONG;
		try {
			if (Validator.isNotNull(empId)) {
				userId = employeeLocalService.fetchEmployee(empId).getUserId();
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return userId;
	}

	public Employee getEmployee(long empId) throws PortalException {
		return employeeLocalService.getEmployee(empId);
	}

	public List<com.ignek.intranet.employee.model.Employee> getEmployees(int start, int end) throws Exception {
		return employeeLocalService.getEmployees(start, end);
	}

	public ByteArrayOutputStream getPDFDocument(long empId) throws DocumentException, PortalException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Employee employee = getEmployee(empId);
		Document document = new Document();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
		document.open();
		document.add(new Paragraph(IntranetConstants.REPORT_FIRST_NAME + StringPool.COLON + employee.getFirstName()));
		document.add(new Paragraph(IntranetConstants.REPORT_LAST_NAME + StringPool.COLON + employee.getLastName()));
		document.add(
				new Paragraph(IntranetConstants.REPORT_EMAIL_ADDRESS + StringPool.COLON + employee.getEmailAddress()));
		document.add(
				new Paragraph(IntranetConstants.REPORT_PHONE_NUMBER + StringPool.COLON + employee.getPhoneNumber()));
		document.add(
				new Paragraph(IntranetConstants.REPORT_ADDRESS_LINE_1 + StringPool.COLON + employee.getAddressLine1()));
		document.add(
				new Paragraph(IntranetConstants.REPORT_ADDRESS_LINE_2 + StringPool.COLON + employee.getAddressLine2()));
		document.add(new Paragraph(IntranetConstants.REPORT_CITY + StringPool.COLON + employee.getCity()));
		document.add(new Paragraph(IntranetConstants.REPORT_ZIPCODE + StringPool.COLON + employee.getZipCode()));
		document.add(
				new Paragraph(IntranetConstants.REPORT_DESIGNATION + StringPool.COLON + employee.getDesignation()));
		document.close();
		pdfWriter.close();
		return byteArrayOutputStream;
	}

	private Log _log = LogFactoryUtil.getLog(CommonUtil.class.getName());

}
