package neuralnet;

public class Generation
{
	public int speciesNumber = 1;
	public Species currentSpecies;
	public DNA fittestDNA;
	public double highestFitness;
	DNA generationDNA;
	double mutationFactor = AI.generationMutation;

	Generation(DNA dna, double mutationFactor)
	{
		currentSpecies = new Species(dna);
		fittestDNA = currentSpecies.dna;
		generationDNA = dna;
		this.mutationFactor = mutationFactor;
	}
	
	Generation(DNA dna)
	{
		currentSpecies = new Species(dna);
		fittestDNA = currentSpecies.dna;
		generationDNA = dna;
	}

	public void OnDeath(double fitness)
	{
		if (fitness > highestFitness)
		{
			highestFitness = fitness;
			fittestDNA = currentSpecies.dna;
		}

		currentSpecies = new Species(Mutator.Mutate(generationDNA, mutationFactor));
		speciesNumber++;
	}
}