package os_multiprogramming;

 import java.util.LinkedList;
 
 public class shortTermSchdular implements Runnable {
 
 private LinkedList<Process> readyQueue;
 private LinkedList<Process> watingForMemQueue;
 private LinkedList<Process> ioWaitingQueue;
 private LinkedList<Process> terminatedProcessesList = new LinkedList<>();
 private longTermSchdular longTermScheduler;
 
 private Ram memory;
 private CPU cpu;
 private IODevice ioDevice;
 
 public shortTermSchdular(longTermSchdular lts, Ram memory) {
 this.longTermScheduler = lts;
 readyQueue = lts.getReadyQueue();
 watingForMemQueue = new LinkedList<>();
 ioWaitingQueue = new LinkedList<>();
 
 this.memory = memory;
 this.ioDevice = new IODevice(readyQueue,ioWaitingQueue);
 this.cpu = new CPU(readyQueue);
 //start();// start thread
 

 }

 
 public void run() {// create thread for short term
	
 while (true) {
	 
	 
	 if (readyQueue.size() > 0) {
	 
		 //Process p = findShortest();
		 Process p =readyQueue.remove();
		 //System.out.println(p.pName);
		// System.out.println("CP burst = " + p.cpuBurstsList.get(p.currentCB));
		 if (p.cpuBurstsList.get(p.currentCB)!=-1) {
			 waitForCpu();
			 p.state = pState.NEW;
			 p.cpuTotalEnters++;
			 cpu.runProcess(p);
			// System.out.println("CP burst after = " + p.cpuBurstsList.get(p.currentCB));
			 if(p.done==0) {
			 if (p.currentIOB < p.ioBurstsList.size()) {
            ioDevice.addToIoQueue(p);
            readyQueue.remove(p);
            ioDevice.runIO();
        }
        if (p.currentReqMem < p.reqMemoryList.size()) {
            readyQueue.remove(p);
            changeProcessReqMemory(p);
        }}
        else {if(p.cpuBurstsList.get(p.currentCB)!=-1) {
        
        	readyQueue.add(p);}
        	}
        
    } else {
    	System.out.println("TERMENATED");
        p.finishTime=System.currentTimeMillis();
       
       
        p.state= pState.TERMINATED;
        terminatedProcessesList.add(p);
      
        memory.removeBlock(p.pName);
        
 
 
    }
    }
	
    moveFromMemWatingToReadyQueue();
    checkDeadLock();
    
    	
    try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    if((readyQueue.size() == 0) && (ioWaitingQueue.size() == 0) &&(watingForMemQueue.size() == 0)) {
    	return;
    }
    }
    }
 
    public Process findShortest() {
        int shortestCB = 10000;
        int shortestIndex = 0;
        int j = 0;
        while (j < readyQueue.size()) {
           
            if (readyQueue.get(j).currentCB<readyQueue.get(j).cpuBurstsList.size() &&
                    readyQueue.get(j).cpuBurstsList.get(readyQueue.get(j).currentCB) <= shortestCB) {
                shortestCB = readyQueue.get(j).cpuBurstsList.get(readyQueue.get(j).currentCB);
                shortestIndex = j;
            }
            j++;
        }
 
        return readyQueue.remove(shortestIndex);
    }
 
    public void changeProcessReqMemory(Process p) {
     memory.modifyPage(p.pName, p.reqMemoryList.get(p.currentReqMem), p);
     if (p.state.equals(pState.READY)) {
         readyQueue.add(p);
     }else {
         watingForMemQueue.add(p);
     }
    }
 
    private void checkDeadLock() {
        if ((readyQueue.size() == 0) && (ioWaitingQueue.size() == 0) &&(watingForMemQueue.size() > 0)) {
        	//System.out.println("readyQueue.size() : "+ watingForMemQueue.size());
        	System.out.println("KILLED");
            Process p = findLargestMemProcess();
            p.state = pState.KILLED ;
            p.finishTime=System.currentTimeMillis();
            memory.removeBlock(p.pName);
            terminatedProcessesList.add(p);
            readyQueue.remove(p);
        }
    }
 
 
 
    private Process findLargestMemProcess() {
    	int largestSize = 0;
    	int lagestIndex = 0;
    	for (int i = 1; i < watingForMemQueue.size(); i++) {
    		if (watingForMemQueue.get(i).reqMemoryList.get(watingForMemQueue.get(i).currentReqMem)> largestSize){
    			largestSize = watingForMemQueue.get(i).reqMemoryList.get(watingForMemQueue.get(i).currentReqMem);
    			lagestIndex = i;
    		}
    	}
    	return watingForMemQueue.remove(lagestIndex);
    }
 
    private void waitForCpu() {
    	while (!(cpu.isIDLE())) {
    		
    	}
    }
    
    public LinkedList<Process> getTerminatedProcessesList() {
    	return terminatedProcessesList;
    }
 
    public boolean checkFineshed() {
    	if ((readyQueue.size() > 0) || (watingForMemQueue.size() > 0) || (longTermScheduler.getSize() > 0)) {
    		return false;
    	}
    	return true;
    }
 
    public void moveFromMemWatingToReadyQueue() {
    	if (watingForMemQueue.size() > 0) {
    		for (int i = 0; i < watingForMemQueue.size(); i++) {
    			if(memory.getPercentage(watingForMemQueue.get(i).reqMemoryList.get(watingForMemQueue.get(i).currentReqMem)) < 80) {
    				readyQueue.add(watingForMemQueue.remove(i));
    				break;
    			}
    		}
    	}
    }
 
    public Long getCpuRunTime() {
        return cpu.getCpuRunTime();
    }
 
 }