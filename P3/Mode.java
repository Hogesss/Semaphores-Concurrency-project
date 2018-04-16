/*  
	Mode.java
	
	*Author: Benjamin Hogan
	*Student No: c3256846
	*Date: 2/10/2017
	*Descripion:
	*	Class for storing data related to a Mode.
 */

import java.util.*;
import java.util.concurrent.Semaphore;

public class Mode extends Thread{

	private Semaphore semaphore;
	private Thread thread = new Thread();
	private int type, count = 0, maxTime = 0, head1, head2, head3;
	private boolean xFull, yFull, zFull;
	private Queue<Job> printQueue = new Queue<Job>();
	private Printer printer;
	
	// Constructor
	public Mode(Printer print, int type)
	{
		printer = print;
		this.type = type;
	}

	// Overloaded Run() method for thread class.
	// Role:    starts the read running
    // Args:    none
    // Return:  none
	public void run()
	{
		// While there are jobs running, try to use the printer with the number of jobs
		while(printQueue.size() != 0)
		{
			xFull = false; yFull = false; zFull = false;
			head1 = -1; head2 = -1; head3 = -1;
			count = 0;

			// Uses 3 ints head1, head2, head3 to act as heads to get jobs from the queue and add up the page time.

			if(printQueue.checkHead() != null)
			{
				count++;
				head1 = printQueue.removeHead().getData().getPages();
			}
			else
				xFull = false;

			if(printQueue.checkHead() != null)
			{
				count++;
				head2 = printQueue.removeHead().getData().getPages();
			}
			else
				yFull = false;

			if(printQueue.checkHead() != null)
			{
				count++;
				head3 = printQueue.removeHead().getData().getPages();
			}
			else
				zFull = false;
			
			// check if other jobs could go on another head while the maximum page job is running.
			calcPossibleJobs();
			// then accesses the printer with the number of jobs and mode mono or colour.
			printer.printing(count, type);
		}
	}

	// Role:    finds if any other jobs can fit with the others on the heads by increasing the count for the printer jobs
    // Args:    none
    // Return:  none
	public void calcPossibleJobs()
	{
		// returns the number which is the largest
		maxTime = printer.calcMaxTime(head1, head2, head3);
		Job temp;
		int min = 0;
		
		// while the other 2 heads have room for more jobs, less than the maxtime.
		while(head1 < maxTime || head2 < maxTime || head3 < maxTime) 
		{
			// finds the lowest page time head.
			min = printer.calcMinTime(head1, head2, head3);
			
			try{
				int head = printQueue.checkHead().getPages();
				// if head1 has room, then increase count, else is full
				if(head1 < maxTime && head1 == min && !xFull)
				{
					if(head1 + head <= maxTime)
						{	
							head1 += head;
							count++;
							printQueue.removeHead();
						}
					else
						xFull = true;
				}
				// if head2 has room, then increase count, else is full
				else if(head2 < maxTime && head2 == min && !yFull)
				{
					if(head2 + head <= maxTime)
						{	
							head2 += head;
							count++;
							printQueue.removeHead();
						}
					else
						yFull = true;
				}
				// if head3 has room, then increase count, else is full
				else if(head3 < maxTime && head3 == min && !zFull)
				{
					if(head3 + head <= maxTime)
						{	
							head3 += head;
							count++;
							printQueue.removeHead();
						}
					else
						zFull = true;
				}
				else
					break;
			}catch(NullPointerException e){break;} // if queue is empty, break
		}
	}

	// adds a new Job object to the printQueue.
	public void add(Job page)
	{
		printQueue.append(page);
	}
}