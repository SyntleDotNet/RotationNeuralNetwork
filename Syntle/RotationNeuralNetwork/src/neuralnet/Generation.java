package neuralnet;

public class Generation
{
	public int speciesNumber = 1;
	public Species currentSpecies;
	public double[][][] fittestWeights;
	public double highestFitness;
	double[][][] generationWeighting;

	Generation(double[][][] weights)
	{
		currentSpecies = new Species(Mutator.Mutate(weights, AI.speciesMutation));
		fittestWeights = currentSpecies.weights;
		generationWeighting = weights;
	}

	public void OnDeath(double fitness)
	{
		if (fitness > highestFitness)
		{
			highestFitness = fitness;
			fittestWeights = currentSpecies.weights;
		}

		currentSpecies = new Species(Mutator.Mutate(generationWeighting, AI.speciesMutation));
		speciesNumber++;
	}
}