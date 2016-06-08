package test.weka.salesforce.classifiers;

import junit.framework.Assert;
import main.weka.core.converters.salesforce.SObjectLoader;
import main.weka.salesforce.filters.SalesforceFilter;

import org.junit.Test;

import weka.clusterers.EM;
import weka.core.Instances;
import test.weka.test.TestBase;

public class EMClusteringTests extends TestBase {

	@Test
	public void classifierWithFiltersTests() throws Exception {
		final SObjectLoader dataLoader = this.getConnectedLoader();
		Assert.assertNull(dataLoader.getClassifier());

		dataLoader.setRelationName("Opportunity");
		dataLoader.setQuery("SELECT * FROM Opportunity LIMIT 50");

		Assert.assertFalse(dataLoader.validate().hasErrors());
		Assert.assertNotNull(dataLoader.getDataSet());

		Instances dataset = dataLoader.getDataSet();

		dataset = new SalesforceFilter(dataset,
				dataLoader.getAttributeStrategies()).RemoveField("Id")
				.StringsToNominal().RemoveEmptyNominals().RemoveUnaryNominals()
				.RemoveDates().getDataSet();

		System.out
				.println("Attributes post filtering. \r" + dataset.toString());

		final String[] options = new String[2];
		options[0] = "-I";
		options[1] = "100";
		final EM clusterer = new EM();
		clusterer.setOptions(options);
		clusterer.buildClusterer(dataset);

		System.out.println(clusterer.toString());
	}
}