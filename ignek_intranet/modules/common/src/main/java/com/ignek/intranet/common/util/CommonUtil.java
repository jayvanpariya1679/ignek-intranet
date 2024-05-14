package com.ignek.intranet.common.util;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;

public class CommonUtil {

	public ServiceContext getServiceContext(long companyId, long userId) {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);
		return serviceContext;
	}

	public SearchContext setSearchContextData(int start, int end) {
		SearchContext searchContext = new SearchContext();
		searchContext.setCompanyId(PortalUtil.getDefaultCompanyId());
		searchContext.setStart(start);
		searchContext.setEnd(end);
		return searchContext;
	}

}