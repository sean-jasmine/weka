package test.weka.salesforce.classifiers;

import junit.framework.Assert;
import main.weka.core.converters.salesforce.SObjectLoader;
import main.weka.salesforce.filters.SalesforceFilter;

import org.junit.Test;

import weka.classifiers.functions.LinearRegression;
import weka.classifiers.rules.ZeroR;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import test.weka.test.TestBase;

public class LinearRegressionTests extends TestBase {

	@Test
	public void linearRegressionTest() throws Exception {
		final SObjectLoader dataLoader = this.getConnectedLoader();
		Assert.assertNull(dataLoader.getClassifier());

		dataLoader.setRelationName("Opportunity");
		dataLoader.setQuery("SELECT * FROM Opportunity LIMIT 50");

		Assert.assertFalse(dataLoader.validate().hasErrors());
		Assert.assertNotNull(dataLoader.getDataSet());

		Instances dataset = dataLoader.getDataSet();

		dataset = new SalesforceFilter(dataset,
				dataLoader.getAttributeStrategies()).RemoveId()
				.RemoveReferences().RemoveField("OrderNumber__c")
				.RemoveField("IsWon").RemoveField("IsClosed")
				.RemoveField("IsPrivate").RemoveField("Name")
				.StringsToNominal().getDataSet();

		System.out
				.println("Attributes post filtering. \r" + dataset.toString());

		// Zero Rule Classifier
		final Instances zeroRuleDataset = new Instances(dataset);

		Attribute expectedRevenueAttrib = zeroRuleDataset
				.attribute("ExpectedRevenue");
		Assert.assertNotNull(expectedRevenueAttrib);
		Assert.assertEquals("ExpectedRevenue", expectedRevenueAttrib.name());
		zeroRuleDataset.setClass(expectedRevenueAttrib);

		final ZeroR zeroRuleClass = new ZeroR();
		zeroRuleClass.buildClassifier(zeroRuleDataset);
		System.out.println("------------- Zero Rule Classifier-----------\n"
				+ zeroRuleClass);

		// Linear Regression Classifier
		expectedRevenueAttrib = dataset.attribute("ExpectedRevenue");
		Assert.assertNotNull(expectedRevenueAttrib);
		Assert.assertEquals("ExpectedRevenue", expectedRevenueAttrib.name());
		dataset.setClass(expectedRevenueAttrib);

		final LinearRegression model = new LinearRegression();
		model.buildClassifier(dataset);
		System.out.println(model);

		final Instance inst = dataset.lastInstance();
		final double price = model.classifyInstance(inst);
		System.out.println("Last instance (" + inst + "): " + price);
	}
}