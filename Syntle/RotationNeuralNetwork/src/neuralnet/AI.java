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
	int speciesCount = 5;

	// Neural Network Configuration (suggest leaving layers,nodes + inputs as is)
	public static int layers = 1, nodes = 3, inputs = 4, outputs = 1, threshold = 1;
	public static double speciesMutation = 5;
	
	public void Init(Player player)
	{
		currentGeneration = new Generation(Mutator.Mutate());
		this.player = player;
		input.add(player.getX());
		input.add((double) 0);
		input.add((double) 0);
		input.add(0D);
	}

	public void Update(double gapX, double gapY)
	{
		input.set(0, player.getX());
		input.set(1, gapX);
		input.set(2, gapY);
		input.set(3, player.getSpeed());

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

	double topScore = 0;
	public void Death(double objectiveValue, double oldScore)
	{
		currentGeneration.OnDeath(objectiveValue);
		if (currentGeneration.speciesNumber > speciesCount)
		{
			currentGeneration = new Generation(currentGeneration.fittestWeights);
			generationNumber++;
		}
		if (objectiveValue > topScore)
			topScore = objectiveValue;
		speciesMutation = 100.0 / Math.pow(objectiveValue, 4);
		System.out.println(String.format("%05d: %03.0f (%.0f) : %f [%03.0f]", generationNumber, objectiveValue, oldScore, speciesMutation, topScore));
	}
}