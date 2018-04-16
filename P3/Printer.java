/*  
	Printer.java
	
	*Author: Benjamin Hogan
	*Student No: c3256846
	*Date: 2/10/2017
	*Descripion:
	*	Class for running a printer simulation with Jobs.
 */

import java.util.*;
import java.util.concurrent.Semaphore;

public class Printer extends Thread{
	private Thread thread = new Thread();
	private Queue<Job> mono = new Queue<Job>(), colour = new Queue<Job>();
	private int time = 0, x, y, z;
	private Job head1, head2, head3;
	
	// Constructor
	public Printer()
	{}

	// Monitor: The modes have a queue of respective jobs and 3 heads to use. Each try to access the printer which is monitored.
	// Role:    calculates the maximum number of the 3 Ints given
    // Args:    3 non negative integers
    // Return:  min int value
	public synchronized void printing(int jobs, int type)
	{
		Queue<Job> temp;
		x = 0; y= 0; z= 0;
		head1 = null; head2 = null; head3 = null;

		if(type == 0)
			temp = mono;
		else
			temp = colour;

		// Places jobs on the 3 heads if possible from the number of jobs given at start, removing from the head.
		if(jobs >= 3)
		{
			head1 = temp.removeHead().getData();
			head2 = temp.removeHead().getData();
			head3 = temp.removeHead().getData();
			jobs -= 3;
		}
		else if(jobs == 2)
		{
			head1 = temp.removeHead().getData();
			head2 = temp.removeHead().getData();
			jobs -= 2;
		}
		else
		{
			head1 = temp.removeHead().getData();
			jobs--;
		}
		
		// Prints the jobs on the heads.
		printHead(1, head1, time);
		printHead(2, head2, time);
		printHead(3, head3, time);
		int max = calcMaxTime(x, y, z);

		// If there are any more jobs that can be used, locates the head which finishes first and adds a job
		while(jobs!= 0)
		{
			int min = calcMinTime(x, y, z), count;
			Job headT = temp.removeHead().getData();

			// if head1 pages plus the new job is less than max and it finishes first.
			if(x + headT.getPages() <= max && x == min)
			{
				count = time + x;
				printHead(1, headT, count);
			}
			// head 2
			else if(y + headT.getPages() <= max && y == min)
			{
				count = time + y;
				printHead(2, headT, count);
			}
			// head 3
			else if(z + headT.getPages() <= max && z == min)
			{
				count = time + z;
				printHead(3, headT, count);
			}
			else
				break;
			jobs--;
		}
		time += max;// increases the time by the max time

		// if complete, then print DONE
		if(mono.size() == 0 && colour.size() == 0)
			System.out.println("(" + time + ") " + "DONE");
	}

	// Role:    calculates the maximum number of the 3 Ints given
    // Args:    3 non negative integers
    // Return:  min int value
	public int calcMaxTime(int a, int b, int c)
	{
		int temp = a;
		if(b > temp && b >= 0)
			temp = b;
		if(c > temp && c >= 0)
			temp = c;
		return temp;
	}

	// Role:    calculates the minimum number of the 3 Ints given
    // Args:    3 non negative integers
    // Return:  max int value
	public int calcMinTime(int a, int b, int c)
	{
		int temp = a;
		if(b < temp && b >= 0)
			temp = b;
		if(c < temp && c >= 0)
			temp = c;
		return temp;
	}

	// Role:    simulates printing a job on a head.
    // Args:    int head number and the Job on the head.
    // Return:  none
	public void printHead(int num, Job head, int clock)
	{
		try{
			if(head != null)
			{
				System.out.println("(" + clock + ") " + head.getID() + " uses head " + num + " (time: " + head.getPages() + ")");
					Thread.sleep(500);
				if(num == 1)
					x += head.getPages();
				else if(num == 2)
					y += head.getPages();
				else
					z += head.getPages();
			}
		}catch(InterruptedException e){}
	}

	// adds Jobs to the printer queues
	public void add(Job page, int type)
	{
		if(type == 0)
			mono.append(page);
		else
			colour.append(page);
	}
}