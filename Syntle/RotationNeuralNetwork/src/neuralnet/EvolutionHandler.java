package neuralnet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EvolutionHandler
{
	public ArrayList<Node> baseNodes = new ArrayList<>();
	Genome[] genePool;
	Random rand = new Random();
	Genome currentGenome;
	int genePoolPosition = 1, poolSize, fittestGeneNumber = 0;
	double highestFitnessThisGen = 0;

	public EvolutionHandler(ArrayList<Node> baseNodes)
	{
		this.baseNodes = baseNodes;
	}

	public void CreateGenePool(int poolSize)
	{
		this.poolSize = poolSize;
		genePool = new Genome[poolSize];
		for (int geneNumber = 0; geneNumber < poolSize; geneNumber++)
		{
			Genome genome = new Genome(baseNodes, new ArrayList<Connection>());
			genome.LinkMutate();
			int num = rand.nextInt(30);
			if (num > 26)
			{
				genome.NodeMutate();
				if (num > 27)
					genome.LinkMutate();
				if (num > 28)
					genome.LinkMutate();
			}
			genePool[geneNumber] = genome;
		}

		currentGenome = genePool[0];
	}

	public void Death(double objectiveValue)
	{
		currentGenome.geneFitness = objectiveValue;
		if (objectiveValue > highestFitnessThisGen)
		{
			highestFitnessThisGen = objectiveValue;
			fittestGeneNumber = genePoolPosition - 1;
		}
		if (genePoolPosition < poolSize)
			currentGenome = genePool[genePoolPosition++];
		else
			NewGeneration();
	}

	public void NewGeneration()
	{
		baseNodes = genePool[fittestGeneNumber].nodes;
		ArrayList<Connection> connections = genePool[fittestGeneNumber].connections;

		for (int geneNumber = 0; geneNumber < poolSize; geneNumber++)
		{
			Genome genome = new Genome(baseNodes, connections);
			genome.PointMutate();
			int num = rand.nextInt(30);
			if (num > 20)
			{
				genome.EnableDisableMutate();
				if (num > 25)
				{
					genome.LinkMutate();

					if (num > 27)
					{
						genome.NodeMutate();
						if (num > 28)
							genome.LinkMutate();
					}
				}
			}
			genePool[geneNumber] = genome;
		}
		genePoolPosition = 1;
		currentGenome = genePool[0];
	}

	/*
	 * public void Speciation() {
	 * 
	 * }
	 */
}
