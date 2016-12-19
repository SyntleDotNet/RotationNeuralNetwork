package neuralnet;

import java.util.ArrayList;

import game.Player;

public class AI
{
	ArrayList<Double> input = new ArrayList<>();
	Player player;
	Generation currentGeneration;
	int generationNumber = 1;
	int speciesCount = 10;
	
	public void Init(Player player)
	{
		currentGeneration = new Generation(Mutator.Mutate());
		this.player = player;
		input.add(player.getX());
		input.add((double) 0);
		input.add((double) 0);
	}

	public void Update(double objectiveValue, double gapX, double gapY)
	{
		input.set(0, player.getX());
		input.set(1, gapX);
		input.set(2, gapY);
		
		int output = currentGeneration.currentSpecies.FeedForward(input);

		if (output > 0)
		{
			player.moveRight();
		}
		else if (output < 0)
		{
			player.moveLeft();
		}
	}
	
	public void Death(double objectiveValue)
	{
		
	}
	
	void CreateOffspring(double objectiveValue)
	{
		currentGeneration = new Generation(currentGeneration.fittestWeights);
		generationNumber++;
	}
}