package os_multiprogramming;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class JobsQueue {

  LinkedList<Job> jopsQueue = new LinkedList<Job>(); 

 public JobsQueue(String filePath) {//takes a path to cpumemoryio.txt
     File f = new File(filePath);
     Scanner sc = null ;
         try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //scanner to read file
         while(sc.hasNextLine()) {// if there a line to read continue
             String line = sc.nextLine();//read line
                 List<String> list = Arrays.asList(line.trim().split(" ")); // store data as a list
                     split(list);//special method to parse list of size

             }
 
    sc.close();  
   
 }

     private void split(List<String> l) {
         String name = l.get(0).toString();
         Job jop = new Job(name);
         for(int j=1; j < 9;) {
             jop.addToCpuBurstsList(Integer.parseInt(l.get(j++).toString()));
             jop.addToReqMemoryList(Integer.parseInt(l.get(j++).toString()));
             jop.addToIoBurstsList(Integer.parseInt(l.get(j++).toString()));

         }
         jop.addToCpuBurstsList(Integer.parseInt(l.get(10).toString()));
         jop.addToReqMemoryList(Integer.parseInt(l.get(11).toString()));
         jop.addToCpuBurstsList(Integer.parseInt(l.get(12).toString()));
         jopsQueue.add(jop);
     }


 }