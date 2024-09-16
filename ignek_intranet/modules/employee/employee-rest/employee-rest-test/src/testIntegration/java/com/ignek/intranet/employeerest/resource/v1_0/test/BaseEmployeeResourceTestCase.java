package com.ignek.intranet.employeerest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.ignek.intranet.employeerest.client.dto.v1_0.Employee;
import com.ignek.intranet.employeerest.client.http.HttpInvoker;
import com.ignek.intranet.employeerest.client.pagination.Page;
import com.ignek.intranet.employeerest.client.pagination.Pagination;
import com.ignek.intranet.employeerest.client.resource.v1_0.EmployeeResource;
import com.ignek.intranet.employeerest.client.serdes.v1_0.EmployeeSerDes;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author ignek
 * @generated
 */
@Generated("")
public abstract class BaseEmployeeResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_employeeResource.setContextCompany(testCompany);

		EmployeeResource.Builder builder = EmployeeResource.builder();

		employeeResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Employee employee1 = randomEmployee();

		String json = objectMapper.writeValueAsString(employee1);

		Employee employee2 = EmployeeSerDes.toDTO(json);

		Assert.assertTrue(equals(employee1, employee2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Employee employee = randomEmployee();

		String json1 = objectMapper.writeValueAsString(employee);
		String json2 = EmployeeSerDes.toJSON(employee);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Employee employee = randomEmployee();

		employee.setAddressLine1(regex);
		employee.setAddressLine2(regex);
		employee.setCity(regex);
		employee.setDesignation(regex);
		employee.setEmailAddress(regex);
		employee.setFirstName(regex);
		employee.setLastName(regex);
		employee.setStatusMessage(regex);

		String json = EmployeeSerDes.toJSON(employee);

		Assert.assertFalse(json.contains(regex));

		employee = EmployeeSerDes.toDTO(json);

		Assert.assertEquals(regex, employee.getAddressLine1());
		Assert.assertEquals(regex, employee.getAddressLine2());
		Assert.assertEquals(regex, employee.getCity());
		Assert.assertEquals(regex, employee.getDesignation());
		Assert.assertEquals(regex, employee.getEmailAddress());
		Assert.assertEquals(regex, employee.getFirstName());
		Assert.assertEquals(regex, employee.getLastName());
		Assert.assertEquals(regex, employee.getStatusMessage());
	}

	@Test
	public void testGetEmployeeById() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetEmployeeById() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetEmployeeByIdNotFound() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testUpdateEmployee() throws Exception {
		Employee randomEmployee = randomEmployee();

		Employee postEmployee = testUpdateEmployee_addEmployee(randomEmployee);

		assertEquals(randomEmployee, postEmployee);
		assertValid(postEmployee);
	}

	protected Employee testUpdateEmployee_addEmployee(Employee employee)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetEmployees() throws Exception {
		Page<Employee> page = employeeResource.getEmployees(
			Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		Employee employee1 = testGetEmployees_addEmployee(randomEmployee());

		Employee employee2 = testGetEmployees_addEmployee(randomEmployee());

		page = employeeResource.getEmployees(Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(employee1, (List<Employee>)page.getItems());
		assertContains(employee2, (List<Employee>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetEmployeesWithPagination() throws Exception {
		Page<Employee> totalPage = employeeResource.getEmployees(null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Employee employee1 = testGetEmployees_addEmployee(randomEmployee());

		Employee employee2 = testGetEmployees_addEmployee(randomEmployee());

		Employee employee3 = testGetEmployees_addEmployee(randomEmployee());

		Page<Employee> page1 = employeeResource.getEmployees(
			Pagination.of(1, totalCount + 2));

		List<Employee> employees1 = (List<Employee>)page1.getItems();

		Assert.assertEquals(
			employees1.toString(), totalCount + 2, employees1.size());

		Page<Employee> page2 = employeeResource.getEmployees(
			Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Employee> employees2 = (List<Employee>)page2.getItems();

		Assert.assertEquals(employees2.toString(), 1, employees2.size());

		Page<Employee> page3 = employeeResource.getEmployees(
			Pagination.of(1, totalCount + 3));

		assertContains(employee1, (List<Employee>)page3.getItems());
		assertContains(employee2, (List<Employee>)page3.getItems());
		assertContains(employee3, (List<Employee>)page3.getItems());
	}

	protected Employee testGetEmployees_addEmployee(Employee employee)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetEmployees() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteEmployee() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteEmployee() throws Exception {
		Assert.assertTrue(false);
	}

	protected void assertContains(Employee employee, List<Employee> employees) {
		boolean contains = false;

		for (Employee item : employees) {
			if (equals(employee, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			employees + " does not contain " + employee, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Employee employee1, Employee employee2) {
		Assert.assertTrue(
			employee1 + " does not equal " + employee2,
			equals(employee1, employee2));
	}

	protected void assertEquals(
		List<Employee> employees1, List<Employee> employees2) {

		Assert.assertEquals(employees1.size(), employees2.size());

		for (int i = 0; i < employees1.size(); i++) {
			Employee employee1 = employees1.get(i);
			Employee employee2 = employees2.get(i);

			assertEquals(employee1, employee2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Employee> employees1, List<Employee> employees2) {

		Assert.assertEquals(employees1.size(), employees2.size());

		for (Employee employee1 : employees1) {
			boolean contains = false;

			for (Employee employee2 : employees2) {
				if (equals(employee1, employee2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				employees2 + " does not contain " + employee1, contains);
		}
	}

	protected void assertValid(Employee employee) throws Exception {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("addressLine1", additionalAssertFieldName)) {
				if (employee.getAddressLine1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("addressLine2", additionalAssertFieldName)) {
				if (employee.getAddressLine2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("city", additionalAssertFieldName)) {
				if (employee.getCity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("designation", additionalAssertFieldName)) {
				if (employee.getDesignation() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("emailAddress", additionalAssertFieldName)) {
				if (employee.getEmailAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("empId", additionalAssertFieldName)) {
				if (employee.getEmpId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("firstName", additionalAssertFieldName)) {
				if (employee.getFirstName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("lastName", additionalAssertFieldName)) {
				if (employee.getLastName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (employee.getPhoneNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("statusCode", additionalAssertFieldName)) {
				if (employee.getStatusCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("statusMessage", additionalAssertFieldName)) {
				if (employee.getStatusMessage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (employee.getUserId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("zipCode", additionalAssertFieldName)) {
				if (employee.getZipCode() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<Employee> page) {
		boolean valid = false;

		java.util.Collection<Employee> employees = page.getItems();

		int size = employees.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.ignek.intranet.employeerest.dto.v1_0.Employee.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Employee employee1, Employee employee2) {
		if (employee1 == employee2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("addressLine1", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getAddressLine1(),
						employee2.getAddressLine1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressLine2", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getAddressLine2(),
						employee2.getAddressLine2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("city", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getCity(), employee2.getCity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("designation", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getDesignation(),
						employee2.getDesignation())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("emailAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getEmailAddress(),
						employee2.getEmailAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("empId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getEmpId(), employee2.getEmpId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("firstName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getFirstName(), employee2.getFirstName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("lastName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getLastName(), employee2.getLastName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getPhoneNumber(),
						employee2.getPhoneNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("statusCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getStatusCode(), employee2.getStatusCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("statusMessage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getStatusMessage(),
						employee2.getStatusMessage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getUserId(), employee2.getUserId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("zipCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						employee1.getZipCode(), employee2.getZipCode())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_employeeResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_employeeResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, Employee employee) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("addressLine1")) {
			sb.append("'");
			sb.append(String.valueOf(employee.getAddressLine1()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("addressLine2")) {
			sb.append("'");
			sb.append(String.valueOf(employee.getAddressLine2()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("city")) {
			sb.append("'");
			sb.append(String.valueOf(employee.getCity()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("designation")) {
			sb.append("'");
			sb.append(String.valueOf(employee.getDesignation()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("emailAddress")) {
			sb.append("'");
			sb.append(String.valueOf(employee.getEmailAddress()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("empId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("firstName")) {
			sb.append("'");
			sb.append(String.valueOf(employee.getFirstName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("lastName")) {
			sb.append("'");
			sb.append(String.valueOf(employee.getLastName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("phoneNumber")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("statusCode")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("statusMessage")) {
			sb.append("'");
			sb.append(String.valueOf(employee.getStatusMessage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("zipCode")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected Employee randomEmployee() throws Exception {
		return new Employee() {
			{
				addressLine1 = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				addressLine2 = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				city = StringUtil.toLowerCase(RandomTestUtil.randomString());
				designation = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				empId = RandomTestUtil.randomLong();
				firstName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				lastName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				phoneNumber = RandomTestUtil.randomLong();
				statusCode = RandomTestUtil.randomInt();
				statusMessage = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				userId = RandomTestUtil.randomLong();
				zipCode = RandomTestUtil.randomLong();
			}
		};
	}

	protected Employee randomIrrelevantEmployee() throws Exception {
		Employee randomIrrelevantEmployee = randomEmployee();

		return randomIrrelevantEmployee;
	}

	protected Employee randomPatchEmployee() throws Exception {
		return randomEmployee();
	}

	protected EmployeeResource employeeResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseEmployeeResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private com.ignek.intranet.employeerest.resource.v1_0.EmployeeResource
		_employeeResource;

}