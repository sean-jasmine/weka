package main.weka.salesforce.attributes;
import weka.core.Attribute;
import weka.core.FastVector;

import com.sforce.soap.partner.Field;

public class ReferenceAttributeStrategy extends AttributeStrategy{
	
	public ReferenceAttributeStrategy(Field f, int i) {
		super(f, i);
	}
	
	@Override
	public Attribute buildAttribute() {
		// String attribute type
		return new Attribute( sField.getName(), (FastVector) null, this.getIndex() );
	}
}