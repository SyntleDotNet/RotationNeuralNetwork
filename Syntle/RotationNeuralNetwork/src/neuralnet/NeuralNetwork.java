package neuralnet;

import java.util.ArrayList;

public class NeuralNetwork
{
	public static boolean[] FeedFoward(ArrayList<Double> input, DNA dna)
	{
		double[][][] weights = dna.getWeights();
		double[][] bias = dna.getBias();
		boolean[] output = new boolean[AI.outputs];
		
		// Initialise an array to store node values
		boolean[][] nodeValues = new boolean[AI.layers + 1][];

		// Propogate from left to right, or high layers of the array (near inputs) to low layers of the array (near outputs)
		for (int layer = AI.layers; layer > 0; layer--)
		{
			if (layer == AI.layers) // We are at the leftmost layer that is not the input layer
			{
				nodeValues[layer] = new boolean[AI.nodes];
				for (int node = 0; node < AI.nodes; node++) // Iterate through each node in this layer, computing its value based on inputs and their weight
				{
					double thisNodeValue = 0; // Create a temporary var to store the sum of the inputs to this node

					for (int currentInput = 0; currentInput < AI.inputs; currentInput++) // Iterate through inputs, multiply value by weight to this node and adding to nodes value
					{
						thisNodeValue += input.get(currentInput) * weights[layer][node][currentInput];
					}
					thisNodeValue *= bias[layer][node];
					nodeValues[layer][node] = Sigmoid(thisNodeValue);
				}
			}
			else // We are at another layer, which has AI.nodes number of nodes that it is connected to
			{
				nodeValues[layer] = new boolean[AI.nodes];
				for (int node = 0; node < AI.nodes; node++)
				{
					double thisNodeValue = 0;
					for (int previousNode = 0; previousNode < AI.nodes; previousNode++) // Iterate through leftwards nodes, multiply value by weight to this node and adding to nodes value
					{
						if(nodeValues[layer + 1][previousNode])
							thisNodeValue += weights[layer][node][previousNode]; // Layer + 1 means previous layer (to the left)
					}
					thisNodeValue *= bias[layer][node];
					nodeValues[layer][node] = Sigmoid(thisNodeValue);
				}
			}
		}

		// --- Useful for when we want to implement multiple output nodes that can be activated simultaneously. ---

		for (int outputNode = 0; outputNode < AI.outputs; outputNode++)
		{
			double outputValue = 0;
			for (int previousNode = 0; previousNode < AI.nodes; previousNode++)
			{
				if (nodeValues[1][previousNode])
					outputValue += weights[0][outputNode][previousNode];
			}
			outputValue *= bias[0][outputNode];
			output[outputNode] = Sigmoid(outputValue);
		}

		// --- ---

		// Apply last layer of node values to singular output through weightings of these connections
		/*for (int previousNode = 0; previousNode < AI.nodes; previousNode++)
		{
			output += nodeValues[1][previousNode] * weights[0][0][previousNode];
		}
*/
		return output;
	}

	public static boolean Sigmoid(double x)
	{
		return (1 / (1 + Math.exp(-x))) > AI.threshold;
	}
}
