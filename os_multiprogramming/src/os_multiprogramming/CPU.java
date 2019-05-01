package os_multiprogramming;

import java.util.LinkedList;

public class CPU {



 public boolean idle = true;
 public long cpuStart = 0;
 public long cpuRunTime = 0;
 public LinkedList<Process> readyQeueu;
 public CPU(LinkedList<Process> readyQeueu) {
 this.readyQeueu = readyQeueu;
 }

 public void runProcess(Process p) {
 idle = false;

 cpuStart = System.currentTimeMillis();
 Long processStartTime = System.currentTimeMillis();
 int cb = p.cpuBurstsList.get(p.currentCB);
 int flag=0;
 if(p.level==1) {
     for(int i = 0;i<64;i++) {
        cb--; 
        if(cb==0) 
            break;flag=1;
     }
     if(cb>0) {
         p.level=2;
         p.cpuBurstsList.set(p.currentCB,cb );
     }
 }
 if(p.level==2) {
     for(int i = 0;i<128;i++) {
        cb--; 
        if(cb==0) 
            break;flag=1;
     }
     if(cb>0) {
         p.level=3;
         p.cpuBurstsList.set(p.currentCB,p.cpuBurstsList.get(p.currentCB)-cb );
     }
 }
     while(cb != 0&&p.level==3) {
         cb--;
         flag=1;
         try {
             Thread.sleep(1);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

     }

     p.cpuTime=(System.currentTimeMillis() - processStartTime);

     if(p.currentCB < p.cpuBurstsList.size()&&flag==1){
         p.currentCB++;
         p.state = pState.READY;
         readyQeueu.add(p);
     }
     cpuRunTime += (System.currentTimeMillis() - cpuStart);
     idle = true;
     }

 public boolean isIDLE() {
 return idle;
 }

 public Long getCpuRunTime() {
 return cpuRunTime;
 }

 }
	

