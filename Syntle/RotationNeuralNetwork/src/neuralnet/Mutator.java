package neuralnet;

import java.util.Random;

public class Mutator
{
	static Random random = new Random();
	public static int scaleFactor = 2;

	public static DNA Mutate(DNA dna, double magnitude)
	{
		double[][][] weights = dna.getWeights();
		double[][] bias = dna.getBias();
		
		// Weights to output nodes
		for (int output = 0; output < AI.outputs; output++)
		{
			bias[0][output] += (random.nextDouble() - 0.5) * (magnitude * 0.2);
			for (int previousNode = 0; previousNode < AI.nodes; previousNode++)
			{
				weights[0][output][previousNode] += (random.nextDouble() - 0.5) * magnitude;
			}
		}

		// Weights for all other layers
		for (int layer = 1; layer < AI.layers + 1; layer++)
		{
			for (int thisNode = 0; thisNode < AI.nodes; thisNode++)
			{
				bias[layer][thisNode] += (random.nextDouble() - 0.5) * (magnitude * 0.2);
				
				if (layer == AI.layers) // Weights from input nodes
				{
					for (int inputNode = 0; inputNode < AI.inputs; inputNode++)
					{
						weights[layer][thisNode][inputNode] += (random.nextDouble() - 0.5) * magnitude;
					}
				}
				else // Weights from other middle layer nodes
				{
					for (int previousLayerNode = 0; previousLayerNode < AI.nodes; previousLayerNode++)
						weights[layer][thisNode][previousLayerNode] += (random.nextDouble() - 0.5) * magnitude;
				}
			}
		}
		return new DNA(weights, bias);
	}

	// Populate weights 3D array based on static data from AI.java
	public static DNA Mutate()
	{
		// weights is a list of layers
		// weights[0] is a list of nodes in the last layer (this is one in this case)
		// weights[0][0] is a list of weights from each node to node 0 of the last layer
		double[][][] weights = new double[AI.layers + 1][][];
		double[][] bias = new double[AI.layers + 1][];
		weights[0] = new double[AI.outputs][];
		bias[0] = new double[AI.outputs];
		for (int outputNode = 0; outputNode < AI.outputs; outputNode++)
		{
			weights[0][outputNode] = new double[AI.nodes];
			bias[0][outputNode] = (random.nextDouble()) * scaleFactor;
			for (int j = 0; j < AI.nodes; j++)
			{
				weights[0][outputNode][j] = (random.nextDouble()) * scaleFactor;
			}
		}

		for (int layer = 1; layer < AI.layers + 1; layer++)
		{
			weights[layer] = new double[AI.nodes][];
			bias[layer] = new double[AI.nodes];
			for (int node = 0; node < AI.nodes; node++)
			{
				bias[layer][node] = (random.nextDouble()) * scaleFactor;
				if (layer == AI.layers)
				{
					weights[layer][node] = new double[AI.inputs];
					for (int inputNode = 0; inputNode < AI.inputs; inputNode++)
					{
						weights[layer][node][inputNode] = (random.nextDouble()) * scaleFactor;
					}
				}
				else
				{
					weights[layer][node] = new double[AI.nodes];
					for (int previousLayerNode = 0; previousLayerNode < AI.nodes; previousLayerNode++)
						weights[layer][node][previousLayerNode] = (random.nextDouble()) * scaleFactor;
				}
			}
		}
		return new DNA(weights, bias);
	}
}
