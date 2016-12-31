package neuralnet;

public final class Node
{
	public enum NodeType {
		INPUT, OUTPUT, HIDDEN
	}
	
	public final int nodeID;
	public final NodeType nodeType;
	
	Node(NodeType nodeType) {
		this.nodeType = nodeType;
		nodeID = AI.getNodeNumber();
	}
}
