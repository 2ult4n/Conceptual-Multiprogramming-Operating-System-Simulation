package os_multiprogramming;

public class RamNode {
	
	String name ; 
	int NodeSize ;
	
	
	
	public RamNode(String name, int nodeSize) {
		super();
		this.name = name;
		NodeSize = nodeSize;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNodeSize() {
		return NodeSize;
	}
	public void setNodeSize(int nodeSize) {
		NodeSize = nodeSize;
	}
	
	

}
