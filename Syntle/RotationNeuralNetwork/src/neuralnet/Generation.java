package neuralnet;

import java.util.ArrayList;

public class Generation
{
	public int speciesNumber = 1;
	public Species currentSpecies;
	public ArrayList<Double> fittestWeights;
	public double highestFitness;

	Generation(ArrayList<Double> weights)
	{
		currentSpecies = new Species(Mutator.Mutate(weights, 1));
	}
	
	
}
