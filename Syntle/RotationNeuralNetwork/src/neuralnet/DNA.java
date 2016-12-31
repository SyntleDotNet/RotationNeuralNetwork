package neuralnet;

final class DNA
{
	private final double[][][] weights;
	private final double[][] bias;
    
	public DNA(double[][][] weights, double[][] bias) {
        this.weights = weights;
        this.bias = bias;
    }

    public double[][][] getWeights() {
        return weights;
    }

    public double[][] getBias() {
        return bias;
    }
}
