package fr.mickaelbaron.polldle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;

public class AbstractCDIUnitTest {

	@After
	public void tearDown() throws NamingException {
		InitialContext initialContext = new InitialContext();
		initialContext.unbind("java:comp/BeanManager");
	}
}
