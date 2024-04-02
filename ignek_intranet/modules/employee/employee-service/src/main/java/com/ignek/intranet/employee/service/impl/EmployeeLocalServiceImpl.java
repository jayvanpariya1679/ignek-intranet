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

import com.ignek.intranet.employee.exception.NoSuchEmployeeException;
import com.ignek.intranet.employee.model.Employee;
import com.ignek.intranet.employee.service.base.EmployeeLocalServiceBaseImpl;
import com.ignek.intranet.employee.service.persistence.EmployeePersistence;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.util.PDFProcessorUtil;
import com.liferay.headless.commerce.admin.account.dto.v1_0.User;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "model.class.name=com.ignek.intranet.employee.model.Employee", service = AopService.class)
public class EmployeeLocalServiceImpl extends EmployeeLocalServiceBaseImpl {

//	@Reference
	CounterLocalService counterLocalService;

	@Reference
	UserPersistence userPersistence;
	
	@Reference
	RoleLocalService roleLocalService;
	
	public Employee findByFUserId(long userId) throws NoSuchEmployeeException {
		return employeePersistence.findByFUserId(userId);
	}
	
	public Employee addEmployee(long empId, long userUId, String firstName, String lastName, String emailAddress,
			long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode, String designation) {
		
		
		Employee employee = null;
		if (Validator.isNull(employee)) {
			employee = employeePersistence.create(empId);
			employee.setEmpId(empId);
			employee.setCreateDate(new Date());
		}
		
		employee.setUserId(userUId);
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
		
		return employeePersistence.update(employee);
	}

	public Employee updateEmployee(long empId, long userUId, String firstName, String lastName, String emailAddress,
			long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode, String designation)
			throws PortalException {

		Employee employee = employeePersistence.fetchByFUserId(userUId);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmailAddress(emailAddress);
		employee.setPhoneNumber(phoneNumber);
		employee.setAddressLine1(addressLine1);
		employee.setAddressLine2(addressLine2);
		employee.setCity(city);
		employee.setZipCode(zipCode);
		employee.setDesignation(designation);
		/*
		 * User user = (User) userLocalService.getUser(userId);
		 * 
		 * user.setFirstName(firstName); user.setLastName(lastName);
		 * user.setEmail(emailAddress);
		 * userLocalService.updateUser((com.liferay.portal.kernel.model.User) user);
		 */
		return employeePersistence.update(employee);
	}



	
}