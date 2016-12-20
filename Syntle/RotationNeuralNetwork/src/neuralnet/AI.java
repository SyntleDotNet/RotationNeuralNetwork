package neuralnet;

import java.util.ArrayList;

import game.Player;

public class AI
{
	ArrayList<Double> input = new ArrayList<>();
	Player player;
	Generation currentGeneration;
	int generationNumber = 1;
	
	// Natural Selection / Evolution Model Configuration
	int speciesCount = 10;

	// Neural Network Configuration (suggest leaving layers,nodes + inputs as is)
	public static int layers = 20, nodes = 10, inputs = 3, outputs = 1, threshold = 1;
	public static double speciesMutation = 5;
	
	public void Init(Player player)
	{
		currentGeneration = new Generation(Mutator.Mutate());
		this.player = player;
		input.add(player.getX());
		input.add((double) 0);
		input.add((double) 0);
	}

	public void Update(double gapX, double gapY)
	{
		input.set(0, player.getX());
		input.set(1, gapX);
		input.set(2, gapY);

		int output = currentGeneration.currentSpecies.FeedForward(input);

		if (output == 1)
		{
			player.moveRight();
		}
		else if (output == -1)
		{
			player.moveLeft();
		}
	}

	public void Death(double objectiveValue)
	{
		currentGeneration.OnDeath(objectiveValue);
		if (currentGeneration.speciesNumber > speciesCount)
		{
			currentGeneration = new Generation(currentGeneration.fittestWeights);
			generationNumber++;
		}
		speciesMutation = 1.0E9 / Math.pow(objectiveValue, 3);
		System.out.println(speciesMutation);
	}
}