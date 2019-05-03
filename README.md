# Conceptual Multiprogramming Operating System Simulation :-
this program simulate the behavior of multiprogramming operating system, Using given a specifications of computer.
The most integral part of the project is the multi-level feedback queue scheduling.

# Multi-level Feedback Queue Scheduling Design and Implementation :-
Its design consists of three queues. First queue uses Round Robin Algorithm for scheduling with quantum time of 64. Second queue also uses Round Robin Algorithm for scheduling with quantum time of 128. Last queue uses First come first served. 
We implemented it by giving a process property level which CPU can check this property and assign it accordingly to which queue.
For example process come to CPU, CPU will check level property if its 1 it will process it for 64 quantum time unless it interrupted by IO its will be set on writing until IO interrupt finish then it will be retrieved to the ready queue, and when it goes again to the CPU it will continue execution if the quantum time finished but the process isn’t level property will be set to 2 then it will do the same in level 2 if it didn’t finish at level 2 it i'll go to level 3 and the process will continue execution in level 3 until it finish without in further changing in level property. 

# Process File :-
process could be loaded via text file which contains the values of prcoess in delimiter-separated values
it could be in any form for example 


| Process Name  | Cpu Util      | IO Util |  Memory Req  |
| ------------- |:-------------:|:-------:|-------------:|
| XRQ           | 12            | 33      | 43           |

Process Name and the first three colmuns are necessary and can can grow for any number of the same colmuns.

### Sample : 

>DMX 71 3008 17 275 6874 19 167 6612 21 453 3145 -1

>VJD 66 4001 21 28 5996 10 268 4456 27 108 2462 -1

>SOO 136 5331 12 21 5734 34 188 6719 44 279 5503 -1

>JGN 62 165 28 298 5347 7 472 359 49 229 3682 -1

>JDF 90 1536 14 352 280 30 214 968 32 68 6261 -1

>BYF 124 3369 22 359 1301 28 473 4350 7 178 2082 -1



