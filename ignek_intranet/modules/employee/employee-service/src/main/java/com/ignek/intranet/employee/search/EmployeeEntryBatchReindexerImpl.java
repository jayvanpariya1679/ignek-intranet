package com.ignek.intranet.employee.search;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.employee.model.Employee;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

@Component(immediate = true, service = EmployeeEntryBatchReindexer.class)
public class EmployeeEntryBatchReindexerImpl implements EmployeeEntryBatchReindexer{

	@Reference(target = "(indexer.class.name=com.ignek.intranet.employee.model.Employee)")
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(target = "(indexer.class.name=com.ignek.intranet.employee.model.Employee)")
	protected IndexerWriter<Employee> indexerWriter;

	@Override
	public void reindex(long empId, long companyId) {
		BatchIndexingActionable batchIndexingActionable = indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(dynamicQuery -> {

			Property empIdPropery = PropertyFactoryUtil.forName(IntranetConstants.EMP_ID);

			dynamicQuery.add(empIdPropery.eq(empId));
		});

		batchIndexingActionable.setCompanyId(companyId);

		batchIndexingActionable.setPerformActionMethod((Employee employee) -> {
			Document document = indexerDocumentBuilder.getDocument(employee);

			batchIndexingActionable.addDocuments(document);
		});

		batchIndexingActionable.performActions();

	}

}
