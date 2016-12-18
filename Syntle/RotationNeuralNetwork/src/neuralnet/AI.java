package neuralnet;

import java.util.ArrayList;

import game.Player;

public class AI
{
	ArrayList<Double> input = new ArrayList<Double>();
	Player player;

	public void Init(Player player)
	{
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

		FeedForward(input);
	}

	void FeedForward(ArrayList<Double> inputData)
	{
		player.moveRight();
		player.moveLeft();
	}
}