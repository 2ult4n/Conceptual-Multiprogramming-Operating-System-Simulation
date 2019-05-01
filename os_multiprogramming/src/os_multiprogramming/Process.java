package os_multiprogramming;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import com.opencsv.CSVWriter;

enum pState { NEW , READY , WAITING , RUNNING , TERMINATED , KILLED } ;

public class Process {

	int PID;
	String pName;
	LinkedList<Integer> cpuBurstsList;
	LinkedList<Integer> ioBurstsList;
	LinkedList<Integer> reqMemoryList;
	pState state ;
	int currentIOB = 0;
	int currentCB = 0;
	int currentReqMem = 0;
	int level ;
	int done;
	int cpuburts=0;
	long araival;
	int cpuTotalEnters,ioTotalEnters,memWatingTotalEnters = 0;
	long cpuTime,ioTime,finishTime = 0;
	
	public Process(String pName,LinkedList<Integer>
	cBursts,LinkedList<Integer> ioBursts,LinkedList<Integer> memoryList) {
	this.pName = pName;
	Random r = new Random();
	this.PID = r.nextInt(1001) ;
	this.cpuBurstsList = cBursts;
	this.ioBurstsList = ioBursts;
	this.reqMemoryList = memoryList;
	this.state = pState.NEW;
	this.level = 1 ; 
	this.done=0;
	calcTheBurts();
	
	}
	public void calcTheBurts() {
		for(int i = 0 ; i<cpuBurstsList.size();i++) {
			cpuburts=cpuburts+cpuBurstsList.get(i);
		}
	}
	
	public String getStats(long cpuRunTime) {
	double cpuUtil = ((double) (cpuTime + ioTime)/(cpuRunTime)) * 100;
	
	String stat = 
			"+--------------------------+---------+\r\n" + 
			"|       Process info       | Values  |\r\n" + 
			"+--------------------------+---------+\r\n" + 
			"| PID                      | "+PID+" |\r\n" + 
			"| Program Name             | "+pName+" |\r\n" + 
			"| Ready Queue Loading Time | "+araival+" |\r\n" + 
			"| Total Time in CPU		| "+cpuTime+" |\r\n" + 
			"| Times Entered CPU        | "+cpuTotalEnters+" |\r\n" + 
			"| Number Of IO Operations  | "+ioTotalEnters+" |\r\n" + 
			"| Total Time in IO         | "+ioTime+" |\r\n" + 
			"| Number Of Times Waiting  | "+memWatingTotalEnters+" |\r\n" + 
			"| Time Of Termination      | "+finishTime+" |\r\n" + 
			"| Final State              | "+state+" |\r\n" + 
			"| CPU Utilaization         | "+(cpuUtil*100)+"%" +" |\r\n" + 
			"| Waiting Time             | "+(finishTime-araival-cpuburts)+" |\r\n" + 
			"| Turenaround Time         | "+(finishTime-araival)+" |\r\n" + 
			"+--------------------------+---------+\r\n" + 
			"";
	
	return stat;

	}
	
	public String getWaitingStringValue() {
		return String.valueOf(finishTime-araival-cpuTime);
	}
	
	public String getTurnAroundStringValue() {
		return String.valueOf(finishTime-araival);
	}
	
	 

	
}
	


