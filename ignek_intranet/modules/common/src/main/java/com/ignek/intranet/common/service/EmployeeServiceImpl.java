package com.ignek.intranet.common.service;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.employee.model.Employee;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource;
import com.liferay.mail.reader.model.Message;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageStatusMessageListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Pagination;

@Component(immediate = true, service = EmployeeService.class)
public class EmployeeServiceImpl implements EmployeeService {

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private RoleLocalService roleLocalService;

	@Reference
	private EmployeeLocalService employeeLocalService;

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
					Integer.parseInt(IntranetConstants.BIRTHDAY_MONTH),
					Integer.parseInt(IntranetConstants.BIRTHDAY_DAY), Integer.parseInt(IntranetConstants.BIRTHDAY_YEAR),
					StringPool.BLANK, GetterUtil.DEFAULT_LONG_VALUES, GetterUtil.DEFAULT_LONG_VALUES,
					GetterUtil.DEFAULT_LONG_VALUES, GetterUtil.DEFAULT_LONG_VALUES, false, serviceContext);

			userId = user.getUserId();

			long roleId = roleLocalService.getRole(PortalUtil.getDefaultCompanyId(), IntranetConstants.EMPLOYEE_ROLE)
					.getRoleId();
			roleLocalService.addUserRole(userId, roleId);
			employeeLocalService.addEmployee(empId, userId, companyId, firstName, lastName, emailAddress, phoneNumber,
					addressLine1, addressLine2, city, zipCode, designation);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
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

			user = userLocalService.updateUser(user);

			employeeLocalService.updateEmployee(userUniqueId, companyId, empId, firstName, lastName, emailAddress,
					phoneNumber, addressLine1, addressLine2, city, zipCode, designation);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return user;
	}

	@Override
	public Hits getDataList() throws ParseException, SearchException {
		BooleanQuery mainQuery = new BooleanQueryImpl();
		BooleanQuery booleanQuery = new BooleanQueryImpl();
		mainQuery.addRequiredTerm(Field.COMPANY_ID, PortalUtil.getDefaultCompanyId());
		mainQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, Employee.class.getName());
		booleanQuery.add(mainQuery, BooleanClauseOccur.MUST);
		SearchContext searchContext = new SearchContext();
		searchContext.setCompanyId(PortalUtil.getDefaultCompanyId());
		searchContext.setStart(QueryUtil.ALL_POS);
		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setSorts(new Sort(IntranetConstants.EMP_ID, Sort.LONG_TYPE, false));
		searchContext.setSearchEngineId(SearchEngineHelperUtil.getDefaultSearchEngineId());
		IndexSearcher indexSearcher = SearchEngineHelperUtil
				.getSearchEngine(SearchEngineHelperUtil.getDefaultSearchEngineId()).getIndexSearcher();
		Hits hits = indexSearcher.search(searchContext, booleanQuery);

		return hits;
	}

	@Override
	public long fetchEmployeeById(long empId) throws PortalException {
		long userId = GetterUtil.DEFAULT_LONG;
		try {
			if (Validator.isNotNull(empId)) {
				userId = employeeLocalService.fetchEmployee(empId).getUserId();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return userId;
	}

	@Override
	public void deleteUser(long empId) throws PortalException {
		if (Validator.isNotNull(empId)) {
			userLocalService.deleteUser(fetchEmployeeById(empId));
			employeeLocalService.deleteEmployee(empId);
			log.info("User deleted");
		} else {
			log.info("User note deleted");
		}
	}

	@Override
	public Employee getEmployee(long empId) throws PortalException {
		return employeeLocalService.getEmployee(empId);
	}

	@Override
	public List<com.ignek.intranet.employee.model.Employee> getEmployees(Pagination pagination) throws Exception {
		return employeeLocalService.getEmployees(pagination.getStartPosition(), pagination.getEndPosition());
	}

	private Log log = LogFactoryUtil.getLog(EmployeeServiceImpl.class.getName());

}