package os_multiprogramming;
import java.util.LinkedList;
	
public class Job {

	
	String name;
	LinkedList<Integer> cpuBurstsList; 
	
	LinkedList<Integer> ioBurstsList; 
	
	LinkedList<Integer> reqMemoryList;
	
	
	
	 public Job(String name) {
		 this.name = name;
		 cpuBurstsList = new LinkedList<>();
		 ioBurstsList = new LinkedList<>();
		 reqMemoryList = new LinkedList<>();
	 }
	
	
	 public void addToCpuBurstsList(int cb) {
		 cpuBurstsList.add(cb);
	 }
	
	 public void addToIoBurstsList(int iob) {
		 ioBurstsList.add(iob);
	 }
	
	 public void addToReqMemoryList(int rm) {
		 reqMemoryList.add(rm);
	 }
	
	
	 public int getFirstReqMemory(){ // to handle first memory requirement
		 int r = reqMemoryList.get(0);//get first memory
		 if(r < 0)//check if first memory is negative
			 return Math.abs(r);
	
		 return r;
	 }
	
	 public String getName() {
		 return name;
	 }
	
	
	 public LinkedList<Integer> getCpuBurstsList() {
		 return cpuBurstsList;

	 }
	
	
	 public LinkedList<Integer> getIoBurstsList() {
		 return ioBurstsList;
	 }
	
	
	 public LinkedList<Integer> getReqMemoryList() {
		 return reqMemoryList;
	 }
	



	
}
