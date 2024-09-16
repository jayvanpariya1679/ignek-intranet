package com.ignek.intranet.employee.search;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.employee.model.Employee;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

@Component(immediate = true, property = "indexer.class.name=com.ignek.intranet.employee.model.Employee", service = ModelIndexerWriterContributor.class)
public class EmployeeModelIndexerWriterContributor implements ModelIndexerWriterContributor<Employee> {

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return dynamicQueryBatchIndexingActionableFactory
				.getBatchIndexingActionable(employeeLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Employee employee) {
		return employee.getCompanyId();
	}

	@Override
	public void modelIndexed(Employee employee) {
		employeeEntryBatchReindexer.reindex(employee.getEmpId(), employee.getCompanyId());
	}

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory dynamicQueryBatchIndexingActionableFactory;

	protected EmployeeEntryBatchReindexer employeeEntryBatchReindexer;

	@Reference
	protected EmployeeLocalService employeeLocalService;

	private Log log = LogFactoryUtil.getLog(this.getClass().getName());

	@Override
	public void customize(BatchIndexingActionable batchIndexingActionable,
			ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod((Employee employee) -> {
			Document document = modelIndexerWriterDocumentHelper.getDocument(employee);
			document.addNumber(IntranetConstants.EMP_ID, employee.getEmpId());
			document.addText(IntranetConstants.FIRST_NAME, employee.getFirstName());
			document.addText(IntranetConstants.LAST_NAME, employee.getLastName());
			document.addText(IntranetConstants.EMAIL_ADDRESS, employee.getEmailAddress());
			document.addNumber(IntranetConstants.PHONE_NUMBER, employee.getPhoneNumber());
			document.addText(IntranetConstants.ADDRESS_LINE_1, employee.getAddressLine1());
			document.addText(IntranetConstants.ADDRESS_LINE_2, employee.getAddressLine2());
			document.addText(IntranetConstants.CITY, employee.getCity());
			document.addNumber(IntranetConstants.ZIPCODE, employee.getZipCode());
			document.addText(IntranetConstants.DESIGNATION, employee.getDesignation());
			batchIndexingActionable.addDocuments(document);
		});

	}

}
