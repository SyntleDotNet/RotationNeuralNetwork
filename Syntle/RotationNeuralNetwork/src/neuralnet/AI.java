package neuralnet;

import java.util.ArrayList;

import game.Player;

public class AI
{
	ArrayList<Double> input = new ArrayList<>();
	Player player;
	Generation currentGeneration;
	int generationNumber = 1;
	
	public void Init(Player player)
	{
		currentGeneration = new Generation(Mutator.Mutate());
		this.player = player;
		input.add(player.getX());
		input.add((double) 0);
		input.add((double) 0);
	}

	public void UpdateLowestGap(double x, double y)
	{
		input.set(1, x);
		input.set(2, y);
	}

	public void Update(double objectiveValue)
	{
		input.set(0, player.getX());

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

	
	/*public void CreateOffspring(double objectiveValue)
	{
		currentGeneration = new Generation(Mutator.Mutate(currentGeneration., strength))
		generationNumber++;
	}*/
}