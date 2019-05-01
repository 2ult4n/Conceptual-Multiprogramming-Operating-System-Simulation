package os_multiprogramming;

import java.util.LinkedList;

public class Ram {
	
	 LinkedList<RamNode> memoryPages; // list of memory blocks
	final double MAX_SIZE;
	final double OSSize;
	double currentSize; // total used size
	
	public Ram(int MAX_SIZE,int OSSize) {
	this.MAX_SIZE = MAX_SIZE;
	this.OSSize = OSSize;
	currentSize = OSSize;
	memoryPages = new LinkedList<RamNode>();
	memoryPages.add(new RamNode("OS",OSSize));
	
	}
	
	public void addPage(String blockName,int s) { // used by LTS to add
	
		currentSize+=s;
		memoryPages.add(new RamNode(blockName,s));
		}
	
	
	
		public Process modifyPage(String PageName, int s , Process p) {
		
		p.state = pState.WAITING ; 
		
		p.memWatingTotalEnters++ ;
		
		RamNode page = findPage(PageName);
		if(s >= 0 && ((currentSize + s) / MAX_SIZE) * 100 <= 80) { // size is positive and can

			page.setNodeSize(s);
			currentSize += s; // increase current used size
			p.state = pState.READY ; 
			p.currentReqMem++; // increase pointer of next process required

		}
		return p;
		}
		
		
		
		private RamNode findPage(String name) {
			RamNode b = null;
		for(int i = 0; i<memoryPages.size(); i++) {
		if(memoryPages.get(i).getName().equals(name)) {
		b = memoryPages.get(i);
		break;
		}
		}
		return b;
		}
		
		public void removeBlock(String blockName) {
		RamNode page = findPage(blockName);
		memoryPages.remove(page);
		currentSize = currentSize -  page.getNodeSize();
		
		}
		
		public double getPercentage(int s) { 
            return ((currentSize + s) / MAX_SIZE) * 100 ;
            }
		
		public void printPercentage() {
            System.out.println("");
            System.out.println("current percentage:" + (currentSize / MAX_SIZE)*100 + " %");
            }

}
