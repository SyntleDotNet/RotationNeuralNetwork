package neuralnet;

import java.util.ArrayList;

public class NeuralNetwork
{
	public static boolean[] FeedForward(Genome gene, ArrayList<Double> inputs){
		PopulateDestinationNodes(gene);
		boolean[] outputs = new boolean[AI.outputCount];
		ArrayList<Node> outputNodes = gene.GetOutputNodes();
		for(int i = 0; i < outputNodes.size(); i ++){
			outputs[i] = outputNodes.get(i).GetFired();
		}
		return outputs;
	}
	
	public static void PopulateDestinationNodes(Genome gene){
		for (int connectionNumber = 0; connectionNumber < gene.connections.size(); connectionNumber++)
		{
			Connection c = gene.connections.get(connectionNumber);
			c.destinationNode.AddConnection(c);
		}
	}
	
	public static boolean SigmoidActivation(double x){
		return (1 / (1 + Math.exp(-x))) > AI.threshold;
	}
}
