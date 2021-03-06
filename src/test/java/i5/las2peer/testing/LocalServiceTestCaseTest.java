package i5.las2peer.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import i5.las2peer.api.Service;
import i5.las2peer.execution.NoSuchServiceException;
import i5.las2peer.persistency.MalformedXMLException;
import i5.las2peer.security.Agent;

import org.junit.Test;

public class LocalServiceTestCaseTest extends LocalServiceTestCase {


	@Test
	public void test() throws NoSuchServiceException {
		assertEquals ( TestService.class, getServiceClass() );
		assertEquals ( TestService.class.getName(), getMyAgent().getServiceClassName() );
		
		assertTrue ( getNode().hasAgent(getMyAgent().getId()));
		
		assertEquals ( TestService.class, getServiceInstance().getClass()  );
	}
	
	@Test
	public void testMockAgents () throws MalformedXMLException, IOException {
		Agent eve = MockAgentFactory.getEve();
		getNode().hasAgent(eve.getId());
		
		assertTrue (  getNode().hasAgent( MockAgentFactory.getAdam().getId() ));
		assertTrue ( getNode().hasAgent( MockAgentFactory.getAbel().getId()) );
	}

	@Override
	public Class<? extends Service> getServiceClass() {
		return TestService.class;
	}

}
