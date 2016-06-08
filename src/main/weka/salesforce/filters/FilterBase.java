package main.weka.salesforce.filters;

import java.util.Map;

import main.weka.salesforce.attributes.AttributeStrategy;
import weka.core.Instances;

public abstract class FilterBase {
	protected Instances m_instances;
	protected Map<String, AttributeStrategy> m_attributeStrategies;

	public FilterBase(Instances dataset,
			Map<String, AttributeStrategy> attributeStrategies) {
		this.m_instances = dataset;
		this.m_attributeStrategies = attributeStrategies;
	}

	public abstract Instances filter() throws Exception;
}