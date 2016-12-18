package neuralnet;

import java.util.ArrayList;

public class Generation
{
	public Species currentSpecies;

	Generation(ArrayList<Double> weights)
	{
		currentSpecies = new Species(Mutator.Mutate(weights, 1));
	}
}
