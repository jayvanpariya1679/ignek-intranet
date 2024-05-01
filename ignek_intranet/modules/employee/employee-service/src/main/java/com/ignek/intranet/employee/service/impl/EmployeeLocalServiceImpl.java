/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.ignek.intranet.employee.service.impl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

import com.ignek.intranet.employee.model.Employee;
import com.ignek.intranet.employee.service.base.EmployeeLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "model.class.name=com.ignek.intranet.employee.model.Employee", service = AopService.class)
public class EmployeeLocalServiceImpl extends EmployeeLocalServiceBaseImpl {

	public Employee addEmployee(long empId, long userId, long companyId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException {
		Employee employee = null;
		if (Validator.isNull(empId)) {
			empId = CounterLocalServiceUtil.increment();
			employee = employeePersistence.create(empId);
			employee.setEmpId(empId);
			employee.setCreateDate(new Date());
		}
		employee.setUserId(userId);
		employee.setModifiedDate(new Date());
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmailAddress(emailAddress);
		employee.setPhoneNumber(phoneNumber);
		employee.setAddressLine1(addressLine1);
		employee.setAddressLine2(addressLine2);
		employee.setCity(city);
		employee.setZipCode(zipCode);
		employee.setDesignation(designation);

		return employeeLocalService.updateEmployee(employee);
	}

	public Employee updateEmployee(long userUniqueId, long companyId, long empId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException {
		Employee employee = employeeLocalService.getEmployee(empId);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmailAddress(emailAddress);
		employee.setPhoneNumber(phoneNumber);
		employee.setAddressLine1(addressLine1);
		employee.setAddressLine2(addressLine2);
		employee.setCity(city);
		employee.setZipCode(zipCode);
		employee.setDesignation(designation);
		return employeeLocalService.updateEmployee(employee);
	}

}