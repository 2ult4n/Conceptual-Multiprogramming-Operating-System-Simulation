package os_multiprogramming;
import java.util.LinkedList;
public class longTermSchdular implements Runnable {
	
	private LinkedList<Job> jobsQueue; 
	private LinkedList<Process> readyQueue; 
	private Ram memory;
	
	public longTermSchdular(LinkedList<Job> jq, Ram memory) {
	 this.jobsQueue = jq;
	 this.memory = memory;
	 readyQueue = new LinkedList<>();
	
	 }
	
	 public void run() {
	 while (true) {
		 
	 int i = 0;
	 while (i < jobsQueue.size()) { 
		 
		
	 Job job = jobsQueue.get(i);
	 
	 int reqMem = job.getFirstReqMemory(); 
	 if ( ((memory.currentSize + reqMem) / memory.MAX_SIZE)*100 <= 80) {

		 Process p = new Process(job.name,job.cpuBurstsList, job.ioBurstsList,job.reqMemoryList);
		 p.araival=System.currentTimeMillis();
		
		 memory.addPage(job.name, reqMem); // add to
		 readyQueue.add(p);
		 p.currentReqMem++;
		 jobsQueue.removeFirst();
		 
		 i--;
		 
	 	}
	 
	 i++;
	 }
	
	 try {
		 Thread.sleep(200); 
	 } catch (InterruptedException e) {
		 e.printStackTrace();
	 }

	 }
	 }
	
	 public int getSize() {
	 return jobsQueue.size();
	 }
	
	
	
	 public LinkedList<Process> getReadyQueue() {
	 return readyQueue;
	 }
	
	 public LinkedList<Job> getJopsQueue() {
	 return jobsQueue;
	 }
 }
	
		


