package com.ignek.intranet.employeerest.internal.graphql.query.v1_0;

import com.ignek.intranet.employeerest.dto.v1_0.Employee;
import com.ignek.intranet.employeerest.resource.v1_0.EmployeeResource;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author ignek
 * @generated
 */
@Generated("")
public class Query {

	public static void setEmployeeResourceComponentServiceObjects(
		ComponentServiceObjects<EmployeeResource>
			employeeResourceComponentServiceObjects) {

		_employeeResourceComponentServiceObjects =
			employeeResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {employeeById(empId: ___){userId, empId, firstName, lastName, emailAddress, phoneNumber, addressLine1, addressLine2, city, zipCode, designation, statusCode, statusMessage}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Employee employeeById(@GraphQLName("empId") Long empId)
		throws Exception {

		return _applyComponentServiceObjects(
			_employeeResourceComponentServiceObjects,
			this::_populateResourceContext,
			employeeResource -> employeeResource.getEmployeeById(empId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {employees(page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public EmployeePage employees(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_employeeResourceComponentServiceObjects,
			this::_populateResourceContext,
			employeeResource -> new EmployeePage(
				employeeResource.getEmployees(Pagination.of(page, pageSize))));
	}

	@GraphQLName("EmployeePage")
	public class EmployeePage {

		public EmployeePage(Page employeePage) {
			actions = employeePage.getActions();

			items = employeePage.getItems();
			lastPage = employeePage.getLastPage();
			page = employeePage.getPage();
			pageSize = employeePage.getPageSize();
			totalCount = employeePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Employee> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(EmployeeResource employeeResource)
		throws Exception {

		employeeResource.setContextAcceptLanguage(_acceptLanguage);
		employeeResource.setContextCompany(_company);
		employeeResource.setContextHttpServletRequest(_httpServletRequest);
		employeeResource.setContextHttpServletResponse(_httpServletResponse);
		employeeResource.setContextUriInfo(_uriInfo);
		employeeResource.setContextUser(_user);
		employeeResource.setGroupLocalService(_groupLocalService);
		employeeResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<EmployeeResource>
		_employeeResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}