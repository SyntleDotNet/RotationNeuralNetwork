package neuralnet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;

import game.Player;

public class AI
{
	ArrayList<Double> input = new ArrayList<>();
	Player player;
	int generationNumber = 1;
	

	public void Init(Player player)
	{
		this.player = player;
		input.add(player.getX());
		input.add((double) 0);
		input.add((double) 0);
		input.add((double) 0);
	}

	public void Update(double gapX, double gapY)
	{
		input.set(0, player.getX());
		input.set(1, gapX);
		input.set(2, gapY);
		input.set(3, player.getSpeed());
	}

	double topScore = 0;

	public void Death(double objectiveValue, double oldScore)
	{
		
	}
}