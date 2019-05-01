package os_multiprogramming;


import java.util.LinkedList;
import java.util.Scanner;

import com.opencsv.CSVWriter;

import java.io.*;


public class Simulator {

    public static void main(String[] args) {

       // Scanner sc = new Scanner(System.in);
        String inputFilePath;
       // System.out.println("Please enter path to the input file: ");
        inputFilePath = "C:\\Users\\isult\\Desktop\\tests\\test.txt" ;


        Ram memory = new Ram(256000,16000);
        JobsQueue jq = new JobsQueue(inputFilePath);
        memory.addPage("jops queue",1);
       
        longTermSchdular longTermScheduler = new longTermSchdular(jq.jopsQueue,memory);
        Thread thread1 = new Thread(longTermScheduler, "New Thread1");

        thread1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
       
        //shortTermSchdular shortTermScheduler = new shortTermSchdular(longTermScheduler,memory);
        shortTermSchdular shortTermScheduler = new shortTermSchdular(longTermScheduler,memory);
        Thread thread = new Thread(shortTermScheduler, "New Thread");

        thread.start();
       

       
      

   //  while(!shortTermScheduler.checkFineshed()) {
     //      memory.printPercentage();
     //   }
       // shortTermScheduler.stop();
        //longTermScheduler.stop();
        try {
			thread.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        thread1.stop();
        thread.stop();
        long cpuRunTime = shortTermScheduler.getCpuRunTime();
        File file = new File(System.getProperty("user.dir")+"\\plotStat.csv");
        FileWriter statFile;
       // System.out.println("Please enter path to output file:");
        String outputFilePath;
        outputFilePath = "C:\\Users\\isult\\Desktop\\tests\\out.txt" ;
        try {
        	statFile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(statFile); 
            PrintStream fileStream = new PrintStream(new File(outputFilePath));
            LinkedList<Process> tpl =
                    shortTermScheduler.getTerminatedProcessesList();
            System.out.println("trminated processes size = " + tpl.size());
            for(int i = 0 ; i < tpl.size();i++) {
                fileStream.println(tpl.get(i).getStats(cpuRunTime));
                String[] plot = {tpl.get(i).getWaitingStringValue(),tpl.get(i).getTurnAroundStringValue()};
                writer.writeNext(plot);
            }
            writer.close();
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


 }
 }