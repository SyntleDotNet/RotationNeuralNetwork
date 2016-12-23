package neuralnet;

public class Generation
{
	public int speciesNumber = 0;
	public Species currentSpecies;
	public double[][][] fittestWeights;
	public double highestFitness;
	double[][][] generationWeighting;

	public Generation(double[][][] weights)
	{
		currentSpecies = new Species(Mutator.mutate(weights, AI.speciesMutation));
		fittestWeights = currentSpecies.weights;
		generationWeighting = weights;
	}

	public void onDeath(double fitness)
	{
		if (fitness > highestFitness)
		{
			highestFitness = fitness;
			fittestWeights = currentSpecies.weights;
		}

		currentSpecies = new Species(Mutator.mutate(generationWeighting, Math.pow(AI.speciesMutation, speciesNumber)));
		speciesNumber++;
	}
}