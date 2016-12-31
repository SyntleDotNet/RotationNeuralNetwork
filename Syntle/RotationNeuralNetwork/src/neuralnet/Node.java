package neuralnet;

import java.util.ArrayList;

public class Node
{
	public enum NodeType
	{
		INPUT, OUTPUT, HIDDEN
	}

	public final int nodeID;
	public final NodeType nodeType;
	public boolean nodeFired = false, updatedThisTick = false;
	ArrayList<Connection> connections = new ArrayList<>();
	
	Node(NodeType nodeType)
	{
		this.nodeType = nodeType;
		nodeID = AI.getNodeNumber();
	}
	
	// Solve recursively
	public boolean GetFired(){
		if (updatedThisTick)
			return nodeFired;
		
		double nodeValue = 0;
		
		for (int connection = 0; connection < connections.size(); connection++)
		{
			Connection thisConnection = connections.get(connection);
			if (thisConnection.enabled)
			{
				if (thisConnection.getSourceNode().nodeType == NodeType.INPUT)
					nodeValue += AI.inputs.get(thisConnection.getSourceNode().nodeID) * thisConnection.weight;
				else if (thisConnection.getSourceNode().GetFired())
					nodeValue += thisConnection.weight;
			}
		}
		
		ClearConnections();
		
		nodeFired = NeuralNetwork.SigmoidActivation(nodeValue);
		updatedThisTick = true;
		return nodeFired;
	}
	
	public void AddConnection(Connection connection){
		connections.add(connection);
	}
	
	public void ClearConnections(){
		connections.clear();
	}
}
