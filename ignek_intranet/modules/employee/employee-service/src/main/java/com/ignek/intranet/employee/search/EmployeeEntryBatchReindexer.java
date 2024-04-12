package com.ignek.intranet.employee.search;

public interface EmployeeEntryBatchReindexer {

	public void reindex(long empId, long companyId);
}
