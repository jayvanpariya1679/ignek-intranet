package com.ignek.intranet.common.employee.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

public interface EmployeeService {
	
	public User addUser(long userId,long userUId, String firstName, String lastName,long companyId,String emailAddress) throws PortalException;
	
	public User updateUser(long userId,long userUid, String firstName, String lastName,long companyId,String emailAddress) throws PortalException;

}
