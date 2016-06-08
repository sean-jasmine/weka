package main.weka.core.converters.salesforce;
import java.util.ArrayList;
import java.util.List;

import main.weka.salesforce.SalesforceConnection;
import weka.core.converters.DatabaseLoader;

public class SalesforceDataLoader extends DatabaseLoader {
	private static final long serialVersionUID = 1L;
	public List<String> Errors = new ArrayList<String>();

	public SalesforceDataLoader() throws Exception {
		super();
	}

	public boolean hasErrors() {
		return Errors.size() > 0;
	}

	private String m_Token = null;

	public void setToken(final String token) {
		m_Token = token;
	}

	public String getToken() {
		return m_Token;
	}

	private SalesforceConnection m_Connection = null;

	public SalesforceConnection getConnection() {
		if (m_Connection == null) {
			m_Connection = new SalesforceConnection().withUsername(getUser())
					.withPassword(getPassword()).withSecurityToken(getToken())
					.withLoginUrl(getUrl()).connectWithUserCredentials();

			if (!m_Connection.isValid()) {
				System.err
				.println("Could not establish Salesforce connection. Please check config.properties file.");
			}
		}
		return m_Connection;
	}
}