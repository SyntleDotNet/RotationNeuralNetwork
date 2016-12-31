package neuralnet;

import java.util.ArrayList;
import java.util.Random;
import neuralnet.Node.NodeType;

// Genome holds both node data and connection data
public class Genome
{
	ArrayList<Node> nodes = new ArrayList<>();
	ArrayList<Connection> connections = new ArrayList<>();
	Random rand = new Random();

	Genome(ArrayList<Node> nodes, ArrayList<Connection> connections)
	{
		this.nodes = nodes;
		this.connections = connections;
	}

	// Perturb the weight of an existing connection
	public void PointMutate()
	{
		int connectionNumber = rand.nextInt(connections.size());
		Connection connection = connections.get(connectionNumber);
		connection.weight += rand.nextGaussian();
		connections.set(connectionNumber, connection);
	}

	// Add a new connection
	public void LinkMutate()
	{
		Node source = SelectSourceNode();
		Node destination = SelectDestinationNode();
		connections.add(new Connection(source, destination, rand.nextDouble() * 4 - 2, true, AI.getInnovationNumber()));
	}

	// Replace a connection with a connection through a hidden node
	public void NodeMutate()
	{		
		int connectionNumber = rand.nextInt(connections.size());
		Connection connection = connections.get(connectionNumber);
		connection.setEnabled(false);
		
		Node source = connection.getSourceNode();
		Node destination = connection.getDestinationNode();
		
		Node nodeHidden = new Node(NodeType.HIDDEN);
		nodes.add(nodeHidden);
		
		connections.add(new Connection(source, nodeHidden, rand.nextDouble() * 4 - 2, true, AI.getInnovationNumber()));
		connections.add(new Connection(nodeHidden, destination, rand.nextDouble() * 4 - 2, true, AI.getInnovationNumber()));
	}
	
	public Node SelectSourceNode()
	{
		ArrayList<Node> possibleSourceNodes = nodes;

		for (int node = 0; node < possibleSourceNodes.size(); node++)
		{
			if (possibleSourceNodes.get(node).nodeType == NodeType.OUTPUT)
			{
				possibleSourceNodes.remove(node);
				node--;
			}
		}

		return possibleSourceNodes.get(rand.nextInt(possibleSourceNodes.size()));
		
	}

	public Node SelectDestinationNode()
	{
		ArrayList<Node> possibleDestinationNodes = nodes;

		for (int node = 0; node < possibleDestinationNodes.size(); node++)
		{
			if (possibleDestinationNodes.get(node).nodeType == NodeType.INPUT)
			{
				node--;
				possibleDestinationNodes.remove(node);
			}
		}

		return possibleDestinationNodes.get(rand.nextInt(possibleDestinationNodes.size()));
	}
}
