package neuralnet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Species implements Serializable
{
	private static final long serialVersionUID = 1915595989223667186L;
	
	double[][][] weights;

	public Species(double[][][] weights)
	{
		this.weights = weights;
	}
	
	public Species(DataInputStream in) throws IOException
	{
		load(in);
	}

	boolean[] feedForward(ArrayList<Double> inputData)
	{
		return NeuralNetwork.FeedFoward(inputData, weights);
	}
	
	public void save(DataOutputStream stream) throws IOException
	{
		stream.writeLong(serialVersionUID);
		stream.writeInt(weights.length);
		for (double[][] x : weights)
		{
			stream.writeInt(x.length);
			for (double[] y : x)
			{
				stream.writeInt(y.length);
				for (double z : y)
					stream.writeDouble(z);
			}
		}
	}
	
	public void load(DataInputStream stream) throws IOException
	{
		long serialVersionUID = stream.readLong();
		if (serialVersionUID == 1915595989223667186L)
		{
			int sizeX = stream.readInt();
			weights = new double[sizeX][][];
			for (int x = 0; x < sizeX; x += 1)
			{
				int sizeY = stream.readInt();
				weights[x] = new double[sizeY][];
				for (int y = 0; y < sizeY; y += 1)
				{
					int sizeZ = stream.readInt();
					weights[x][y] = new double[sizeZ];
					for (int z =0; z < sizeZ; z += 1)
						weights[x][y][z] = stream.readDouble();
				}
			}
		}
		else throw new RuntimeException("Unknown file format for NN!");
	}
}
