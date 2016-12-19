package neuralnet;

import java.util.ArrayList;

public class Species
{
	double[][][] weights;

	Species(double[][][] weights)
	{
		this.weights = weights;
	}

	int FeedForward(ArrayList<Double> inputData)
	{
		double output = NeuralNetwork.FeedFoward(inputData, weights);
		if (output > AI.threshold)
			return 1;
		if (output < -AI.threshold)
			return -1;
		else return 0;
	}
}
