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
	double geneFitness = 0;

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
		UpdateNodes();
	}

	public void GenomeTick()
	{
		for (int node = 0; node < nodes.size(); node++)
		{
			nodes.get(node).updatedThisTick = false;
		}
	}

	public void UpdateNodes()
	{
		for (int node = 0; node < nodes.size(); node++)
		{
			nodes.get(node).ClearConnections();
			NeuralNetwork.PopulateDestinationNodes(this);
		}
	}

	// Add a new connection
	public void LinkMutate()
	{
		Node source = SelectSourceNode();
		Node destination = SelectDestinationNode(source.x);
		Connection connection = new Connection(source, destination, rand.nextDouble() * 4 - 2, true, AI.getInnovationNumber());

		System.out.println("Adding connection from " + source.nodeID + " to " + destination.nodeID);
		connections.add(connection);
		UpdateNodes();
	}

	// Replace a connection with a connection through a hidden node
	public void NodeMutate()
	{
		int connectionNumber = rand.nextInt(connections.size());
		Connection connection = connections.get(connectionNumber);
		connection.setEnabled(false);

		Node source = connection.getSourceNode();
		Node destination = connection.getDestinationNode();

		Node nodeHidden = new Node(NodeType.HIDDEN, (source.x + destination.x) * 0.5);
		nodes.add(nodeHidden);

		connections.add(new Connection(source, nodeHidden, rand.nextDouble() * 4 - 2, true, AI.getInnovationNumber()));
		connections.add(new Connection(nodeHidden, destination, rand.nextDouble() * 4 - 2, true, AI.getInnovationNumber()));
		System.out.println("Adding a hidden node between " + source.nodeID + " and " + destination.nodeID);
		UpdateNodes();
	}

	public void EnableDisableMutate()
	{
		int connectionNumber = rand.nextInt(connections.size());
		Connection connection = connections.get(connectionNumber);
		connection.setEnabled(!connection.enabled);
		System.out.println("Connection no. " + connectionNumber + " enabled = " + connection.enabled);
		UpdateNodes();
	}

	public ArrayList<Node> GetOutputNodes()
	{
		@SuppressWarnings("unchecked")
		ArrayList<Node> outputNodes = (ArrayList<Node>) nodes.clone();
		for (int node = 0; node < outputNodes.size(); node++)
		{
			if (outputNodes.get(node).nodeType != NodeType.OUTPUT)
			{
				outputNodes.remove(node);
				node--;
			}
		}

		return outputNodes;

	}

	Node SelectSourceNode()
	{
		@SuppressWarnings("unchecked")
		ArrayList<Node> possibleSourceNodes = (ArrayList<Node>) nodes.clone();

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

	Node SelectDestinationNode(double sourceNodeX)
	{
		@SuppressWarnings("unchecked")
		ArrayList<Node> possibleDestinationNodes = (ArrayList<Node>) nodes.clone();
		for (int node = 0; node < possibleDestinationNodes.size(); node++)
		{
			if (possibleDestinationNodes.get(node).x <= sourceNodeX)
			{
				possibleDestinationNodes.remove(node);
				node--;
			}
		}
		
return possibleDestinationNodes.get(rand.nextInt(possibleDestinationNodes.size()));
	}
}
