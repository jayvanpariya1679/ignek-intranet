package com.ignek.intranet.employeerest.internal.graphql.servlet.v1_0;

import com.ignek.intranet.employeerest.internal.graphql.mutation.v1_0.Mutation;
import com.ignek.intranet.employeerest.internal.graphql.query.v1_0.Query;
import com.ignek.intranet.employeerest.resource.v1_0.EmployeeResource;

import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author ignek
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setEmployeeResourceComponentServiceObjects(
			_employeeResourceComponentServiceObjects);

		Query.setEmployeeResourceComponentServiceObjects(
			_employeeResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/employee-rest-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<EmployeeResource>
		_employeeResourceComponentServiceObjects;

}