package com.ignek.intranet.common.service;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.CommonConstants;
import com.ignek.intranet.common.employee.dto.Employee;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

@Component(immediate = true, service = EmployeeService.class)
public class EmployeeServiceImpl implements EmployeeService {

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private RoleLocalService roleLocalService;

	@Reference
	EmployeeLocalService employeeLocalService;

	private Log log = LogFactoryUtil.getLog(EmployeeServiceImpl.class.getName());

	public ServiceContext getServiceContext() {
		return new ServiceContext();
	}

	@Override
	public User addUser(long empId, long userId, long companyId, String firstName, String lastName, String emailAddress,
			long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode, String designation)
			throws PortalException {
		ServiceContext serviceContext = getServiceContext();
		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);
		User user = null;
		try {
			user = userLocalService.addUser(userId, PortalUtil.getDefaultCompanyId(), true, StringPool.BLANK,
					StringPool.BLANK, true, StringPool.BLANK, emailAddress, LocaleUtil.getDefault(), firstName,
					StringPool.BLANK, lastName, GetterUtil.DEFAULT_LONG, GetterUtil.DEFAULT_LONG, false,
					Integer.parseInt(CommonConstants.BIRTHDAY_MONTH), Integer.parseInt(CommonConstants.BIRTHDAY_DAY),
					Integer.parseInt(CommonConstants.BIRTHDAY_YEAR), StringPool.BLANK, GetterUtil.DEFAULT_LONG_VALUES,
					GetterUtil.DEFAULT_LONG_VALUES, GetterUtil.DEFAULT_LONG_VALUES, GetterUtil.DEFAULT_LONG_VALUES,
					false, serviceContext);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		userId = user.getUserId();

		long roleId = roleLocalService.getRole(PortalUtil.getDefaultCompanyId(), "Employee").getRoleId();
		roleLocalService.addUserRole(userId, roleId);
		employeeLocalService.addEmployee(empId, userId, companyId, firstName, lastName, emailAddress, phoneNumber,
				addressLine1, addressLine2, city, zipCode, designation);
		return user;
	}

	@Override
	public User updateUser(long userUniqueId, long companyId, long empId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException {
		User user = null;
		try {
			user = userLocalService.getUser(userUniqueId);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailAddress(emailAddress);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		user = userLocalService.updateUser(user);
		employeeLocalService.updateEmployee(userUniqueId, companyId, empId, firstName, lastName, emailAddress,
				phoneNumber, addressLine1, addressLine2, city, zipCode, designation);
		return user;
	}
	
	@Override
	public List<Employee> getDataList() throws ParseException {
		BooleanQuery mainQuery = new BooleanQueryImpl();
		BooleanQuery booleanQuery = new BooleanQueryImpl();
		mainQuery.addRequiredTerm(Field.COMPANY_ID, PortalUtil.getDefaultCompanyId());
		mainQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, com.ignek.intranet.employee.model.Employee.class.getName());
		booleanQuery.add(mainQuery, BooleanClauseOccur.MUST);
		SearchContext searchContext = new SearchContext();
		searchContext.setCompanyId(PortalUtil.getDefaultCompanyId());
		searchContext.setStart(QueryUtil.ALL_POS);
		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setSorts(new Sort(CommonConstants.EMP_ID, Sort.LONG_TYPE, false));
		searchContext.setSearchEngineId(SearchEngineHelperUtil.getDefaultSearchEngineId());
		IndexSearcher indexSearcher = SearchEngineHelperUtil
				.getSearchEngine(SearchEngineHelperUtil.getDefaultSearchEngineId()).getIndexSearcher();
		Hits hits = null;
		try {
			hits = indexSearcher.search(searchContext, booleanQuery);
		} catch (SearchException e) {
			e.printStackTrace();
		}
		Document[] documents = hits.getDocs();
		List<Employee> employeeList = new ArrayList<>();
		for (Document document : documents) {
			Employee employee = new Employee();
			long empId = GetterUtil.getLong(document.get(CommonConstants.EMP_ID));
			String firsrtName = document.get(CommonConstants.FIRST_NAME);
			String lastName = document.get(CommonConstants.LAST_NAME);
			String emailAddress = document.get(CommonConstants.EMAIL_ADDRESS);
			long phoneNumber = GetterUtil.getLong(document.get(CommonConstants.PHONE_NUMBER));
			String addressLine1 = document.get(CommonConstants.ADDRESS_LINE_1);
			String addressLine2 = document.get(CommonConstants.ADDRESS_LINE_2);
			String city = document.get(CommonConstants.CITY);
			long zipCode = GetterUtil.getLong(document.get(CommonConstants.ZIPCODE));
			String designation = document.get(CommonConstants.DESIGNATION);
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
		return employeeList;
	}
	
	@Override
	public User deleteUser(long empId, long userUniqueId) throws PortalException {
		employeeLocalService.deleteEmployee(empId);
		User user = userLocalService.deleteUser(userUniqueId);
		return user;
	}
	
	@Override
	public com.ignek.intranet.employee.model.Employee getEmployee(long empId) throws PortalException {
		com.ignek.intranet.employee.model.Employee employee = employeeLocalService.getEmployee(empId);
		return employee;
	}
	
	@Override
	public List<com.ignek.intranet.employee.model.Employee> getEmployees(Pagination pagination) throws Exception {
		List<com.ignek.intranet.employee.model.Employee> employees = employeeLocalService
				.getEmployees(pagination.getStartPosition(), pagination.getEndPosition());
		return employees;
	}
	
	@Override
	public long fetchEmployee(long empId) {
		return employeeLocalService.fetchEmployee(empId).getUserId();
	}
	
}