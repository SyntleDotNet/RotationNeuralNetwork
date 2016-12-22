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
	Generation currentGeneration;
	int generationNumber = 1;

	// Natural Selection / Evolution Model Configuration
	int speciesCount = 10;

	// Neural Network Configuration (suggest leaving layers,nodes + inputs as is)
	public static int layers = 1, nodes = 3, inputs = 4, outputs = 2;
	public static double speciesMutation = 5, threshold = 0.7;
	
	private Species bestSoFar;

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

		boolean[] output = currentGeneration.currentSpecies.FeedForward(input);

		if (output[0])
		{
			player.moveRight();
		}
		if (output[1])
		{
			player.moveLeft();
		}
	}

	double topScore = 0;

	public void Death(double objectiveValue, double oldScore)
	{
		if (objectiveValue > topScore)
		{
			topScore = objectiveValue;
			bestSoFar = currentGeneration.currentSpecies;
		}
		currentGeneration.OnDeath(Math.pow(oldScore, 3));
		if (currentGeneration.speciesNumber > speciesCount)
		{
			currentGeneration = new Generation(currentGeneration.fittestWeights);
			generationNumber++;
		}
		speciesMutation = 1.0 / objectiveValue;//100.0 / Math.pow(objectiveValue, 4);
		System.out.println(String.format("%05d: %03.0f (%.0f) : %f [%03.0f]", generationNumber, objectiveValue, oldScore, speciesMutation, topScore));
	}

	public boolean saveBestSpeciesSoFar()
	{
		if (bestSoFar == null)
			return false;
		
		File file = new File("Best.nn");
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				throw new RuntimeErrorException(new Error(e), "Failed to create new file to save NN!");
			}
		}
		try
		{
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			bestSoFar.save(out);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			throw new RuntimeErrorException(new Error(e), "Failed to save NN!");
		}
		return true;
	}
	
	public boolean loadSpecies()
	{
		File file = new File("Best.nn");
		if (!file.exists())
			return false;
		try
		{
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			Species species = new Species(in);
			in.close();
			
			currentGeneration.currentSpecies = species;
		}
		catch (Exception e)
		{
			throw new RuntimeErrorException(new Error(e), "Failed to load NN!");
		}
		return true;	
	}
}