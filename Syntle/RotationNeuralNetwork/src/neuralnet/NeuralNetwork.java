package neuralnet;

import java.util.ArrayList;

public class NeuralNetwork
{
	public static double FeedFoward(ArrayList<Double> input, double[][][] weights)
	{
		double output = 0;

		// Initialise an array to store node values
		double[][] nodeValues = new double[AI.layers][];

		// Propogate from left to right, or high layers of the array (near inputs) to low layers of the array (near outputs)
		for (int layer = AI.layers - 1; layer >= 0; layer--)
		{
			if (layer == AI.layers - 1) // We are at the leftmost layer that is not the input layer
			{
				for (int node = 0; node < AI.nodes; node++) // Iterate through each node in this layer, computing its value based on inputs and their weight
				{
					double thisNodeValue = 0; // Create a temporary var to store the sum of the inputs to this node

					for (int currentInput = 0; currentInput < AI.inputs; currentInput++) // Iterate through inputs, multiply value by weight to this node and adding to nodes value
					{
						thisNodeValue += input.get(currentInput) * weights[layer][node][currentInput];
					}
					nodeValues[layer][node] = thisNodeValue;
				}
			}
			else // We are at another layer, which has AI.nodes number of nodes that it is connected to
			{
				for (int node = 0; node < AI.nodes; node++)
				{
					double thisNodeValue = 0;
					for (int previousNode = 0; previousNode < AI.nodes; previousNode++) // Iterate through leftwards nodes, multiply value by weight to this node and adding to nodes value
					{
						thisNodeValue += nodeValues[layer + 1][previousNode] * weights[layer][node][previousNode]; // Layer + 1 means previous layer (to the left)
					}
					nodeValues[layer][node] = thisNodeValue;
				}
			}
		}

		// --- Useful for when we want to implement multiple output nodes that can be activated simultaneously. ---
		/*
		 * for (int i = 0; i < AI.outputs; i++) { for (int j = 0; j < AI.nodes; j++) {
		 * 
		 * } }
		 */
		// --- ---

		// Apply last layer of node values to singular output through weightings of these connections
		for (int previousNode = 0; previousNode < AI.nodes; previousNode++)
		{
			output += nodeValues[0][previousNode] * weights[0][0][previousNode];
		}

		return output;
	}
}
