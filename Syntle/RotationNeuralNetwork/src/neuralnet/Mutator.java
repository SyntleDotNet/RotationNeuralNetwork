package neuralnet;

import java.util.Random;

public class Mutator
{
	static Random random = new Random();
	public static int scaleFactor = 2;

	public static double[][][] mutate(double[][][] weights, double magnitude)
	{
		// Weights to output nodes
		for (int i = 0; i < AI.outputs; i++)
		{
			for (int j = 0; j < AI.nodes; j++)
				weights[0][i][j] += (random.nextDouble() - 0.5) * magnitude;
		}

		// Weights for all other layers
		for (int i = 1; i < AI.layers + 1; i++)
		{
			if (i == AI.layers) // Weights from input nodes
			{
				for (int j = 0; j < AI.nodes; j++)
				{
					for (int k = 0; k < AI.inputs; k++)
						weights[i][j][k] += (random.nextDouble() - 0.5) * magnitude;
				}
			}
			else // Weights from other middle layer nodes
			{
				for (int j = 0; j < AI.nodes; j++)
				{
					for (int k = 0; k < AI.nodes; k++)
						weights[i][j][k] += (random.nextDouble() - 0.5) * magnitude;
				}
			}
		}
		return weights;
	}

	// Populate weights 3D array based on static data from AI.java
	public static double[][][] mutate()
	{
		// weights is a list of layers
		// weights[0] is a list of nodes in the last layer (this is one in this case)
		// weights[0][0] is a list of weights from each node to node 0 of the last layer
		double[][][] weights = new double[AI.layers + 1][][];
		weights[0] = new double[AI.outputs][];
		for (int i = 0; i < AI.outputs; i++)
		{
			weights[0][i] = new double[AI.nodes];
			for (int j = 0; j < AI.nodes; j++)
				weights[0][i][j] = (random.nextDouble()) * scaleFactor;
		}

		for (int i = 1; i < AI.layers + 1; i++)
		{
			weights[i] = new double[AI.nodes][];
			if (i == AI.layers)
			{
				for (int j = 0; j < AI.nodes; j++)
				{
					weights[i][j] = new double[AI.inputs];
					for (int k = 0; k < AI.inputs; k++)
						weights[i][j][k] = (random.nextDouble()) * scaleFactor;
				}
			}
			else
			{
				for (int j = 0; j < AI.nodes; j++)
				{
					weights[i][j] = new double[AI.nodes];
					for (int k = 0; k < AI.nodes; k++)
						weights[i][j][k] = (random.nextDouble()) * scaleFactor;
				}
			}
		}
		return weights;
	}
}
