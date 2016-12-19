package neuralnet;

import java.util.ArrayList;

public class Mutator
{
	public static ArrayList<Double> Mutate(ArrayList<Double> weights, double d)
	{
		return weights;
	}

	public static ArrayList<Double> Mutate()
	{
		ArrayList<Double> weights = new ArrayList<>();
		weights.add((double) 5);
		weights.add((double) 2);
		weights.add((double) 4);
		return weights;
	}
}
