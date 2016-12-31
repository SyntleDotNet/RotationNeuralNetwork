package neuralnet;

public class Connection
{
	public Node sourceNode, destinationNode;
	public double weight;
	public boolean enabled;
	public int innovationNumber;
	
	Connection(Node sourceNode, Node destinationNode, double weight, boolean enabled, int innovationNumber) {
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.weight = weight;
		this.enabled = enabled;
		this.innovationNumber = innovationNumber;
	}
	
	public Node getSourceNode(){
		return this.sourceNode;
	}
	
	public void setSourceNode(Node sourceNode){
		this.sourceNode = sourceNode;
	}
	
	public Node getDestinationNode(){
		return this.destinationNode;
	}
	
	public void setDestinationNode(Node destinationNode){
		this.destinationNode = destinationNode;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public boolean getEnabled(){
		return this.enabled;
	}
	
	public int getInnovationNumber(){
		return this.innovationNumber;
	}
}
