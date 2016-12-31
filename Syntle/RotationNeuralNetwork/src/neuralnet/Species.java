package neuralnet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Species implements Serializable
{
	private static final long serialVersionUID = 1915595989223667186L;
	
	DNA dna;

	public Species(DNA dna)
	{
		this.dna = dna;
	}
	
	public Species(DataInputStream in) throws IOException
	{
		load(in);
	}

	boolean[] FeedForward(ArrayList<Double> inputData)
	{
		return NeuralNetwork.FeedFoward(inputData, dna);
	}
	
	public void save(DataOutputStream stream) throws IOException
	{
		double[][][] weights = dna.getWeights();
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
			double[][][] weights = new double[sizeX][][];
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
			
			dna = new DNA(weights, dna.getBias());
		}
		else throw new RuntimeException("Unknown file format for NN!");
	}
}
