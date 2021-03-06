package i5.las2peer.p2p.pastry;

import rice.p2p.commonapi.NodeHandle;

/**
 * a <i>content</i> to be published to an agent's scribe topic, if someone is looking for
 * running versions of this agent.
 * 
 * @author Holger Janssen
 * @version $Revision: 1.3 $, $Date: 2013/01/15 18:18:52 $
 *
 */
public class SearchAgentContent extends L2pScribeContent {

	/**
	 * the id of the agent to look for
	 */
	private long searchAgentId;
	
	
	
	
	/**
	 * serialization id 
	 */
	private static final long serialVersionUID = 8073537449959759751L;

	/**
	 * create a new search content looking for the agent of the given id, created at the given node (handle)
	 * @param from
	 * @param id
	 */
	public SearchAgentContent(NodeHandle from, long id) {
		super(from);
		
		searchAgentId = id;		
	}
	
	
	/**
	 * get the id of the agent to search
	 * @return id of the requested agent
	 */
	public long getAgentId () {
		return searchAgentId;
	}
	
	
	
	

}
