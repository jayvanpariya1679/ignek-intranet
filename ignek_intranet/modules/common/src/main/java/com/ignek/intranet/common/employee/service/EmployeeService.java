package com.ignek.intranet.common.employee.service;

import javax.portlet.ActionRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

public interface EmployeeService {
	
//	public User addUser(long empId, long userId, String firstName, String lastName, String emailAddress,
//			long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode, String designation) throws PortalException;
//	
	public User addUser(long userId,long userUId, String firstName, String lastName,long companyId,String emailAddress) throws PortalException;
	
	public User updateUser(long userId,long userUid, String firstName, String lastName,long companyId,String emailAddress) throws PortalException;

}
