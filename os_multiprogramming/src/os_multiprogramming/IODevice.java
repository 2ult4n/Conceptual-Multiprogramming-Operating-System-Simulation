package os_multiprogramming;

import java.util.Queue;

public class IODevice {
	
	
	private Queue<Process> ioWaitingQueue;
	private Queue<Process> readyQueue;
		
		public IODevice(Queue<Process> readyQueue , Queue<Process> waitingQueue) {
			this.ioWaitingQueue = waitingQueue ;
			this.readyQueue = readyQueue;
		 }
		
		 public void addToIoQueue(Process p) {
			 p.state = pState.WAITING;
			 p.ioTotalEnters++; 
			 ioWaitingQueue.add(p);
		 }
		
		 public void runIO() {
		 if(ioWaitingQueue.size() != 0) {
		 long start = System.currentTimeMillis();
		 Process p = ioWaitingQueue.remove();
		 int iob =  p.currentIOB;
		 while(iob != 0) {
			iob--;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		 p.ioTime =  p.ioTime + System.currentTimeMillis()-start;
		 p.state = pState.READY ;
		 p.currentIOB++;
		 readyQueue.add(p);
		 }
		 }
		
		 

}
