package neuralnet;

import java.util.ArrayList;
import java.util.Random;

import game.Player;
import neuralnet.Node.NodeType;

public class AI
{
	public static final double threshold = 0.5;
	public static final int outputCount = 2;
	
	Genome currentGenome;
	ArrayList<Node> baseNodes = new ArrayList<>();
	public static ArrayList<Double> inputs = new ArrayList<>();
	Player player;
	Random rand = new Random();
	
	int generationNumber = 1;
	public static int innovationNumber = 1, nodeNumber = 0;

	public void Init(Player player)
	{
		this.player = player;
		inputs.add(player.getX());
		inputs.add((double) 1);
		inputs.add((double) 1);
		inputs.add((double) 1);

		// Initialise base nodes based on inputs and outputs
		for (int input = 0; input < inputs.size(); input++)
			baseNodes.add(new Node(NodeType.INPUT));
		for (int output = 0; output < outputCount; output++)
			baseNodes.add(new Node(NodeType.OUTPUT));

		
		
		// Create first genome
		currentGenome = new Genome(baseNodes, new ArrayList<Connection>());
		currentGenome.LinkMutate();
	}

	public void Update(double gapX, double gapY)
	{
		inputs.set(0, player.getX());
		inputs.set(1, gapX);
		inputs.set(2, gapY);
		inputs.set(3, player.getSpeed());

		currentGenome.GenomeTick();
		boolean[] outputs = NeuralNetwork.FeedForward(currentGenome, inputs);
		
		if (outputs[0])
			player.moveLeft();
		else if (outputs[1])
			player.moveRight();
	}

	public void Death(double objectiveValue, double oldScore)
	{
		/*if (rand.nextInt(10000) > 9998)
			currentGenome.NodeMutate();*/
		currentGenome.PointMutate();
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