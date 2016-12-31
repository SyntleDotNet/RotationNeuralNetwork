package neuralnet;

import java.util.ArrayList;

import game.Player;
import neuralnet.Node.NodeType;

public class AI
{
	ArrayList<Double> inputs = new ArrayList<>();
	Player player;
	int generationNumber = 1;
	public static int innovationNumber = 1, nodeNumber = 0;
	Genome currentGenome;
	ArrayList<Node> baseNodes = new ArrayList<>();

	// Game Variables
	int outputCount = 2;
	
	public void Init(Player player)
	{
		this.player = player;
		inputs.add(player.getX());
		inputs.add((double) 0);
		inputs.add((double) 0);
		inputs.add((double) 0);
		
		// Initialise base nodes based on inputs and outputs
		for (int input = 0; input < inputs.size(); input++)
			baseNodes.add(new Node(NodeType.INPUT));
		for (int output = 0; output < outputCount; output++)
			baseNodes.add(new Node(NodeType.OUTPUT));
		
		// Create first genome
		currentGenome = new Genome(baseNodes, new ArrayList<Connection>());
	}

	public void Update(double gapX, double gapY)
	{
		inputs.set(0, player.getX());
		inputs.set(1, gapX);
		inputs.set(2, gapY);
		inputs.set(3, player.getSpeed());
	}


	public void Death(double objectiveValue, double oldScore)
	{
		
	}
	
	public static int getNodeNumber()
	{
		nodeNumber++;
		return nodeNumber - 1;
	}
	
	public static int getInnovationNumber()
	{
		innovationNumber++;
		return innovationNumber - 1;
	}
}