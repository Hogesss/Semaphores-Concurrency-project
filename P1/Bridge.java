/*  
	Bridge.java
	
	*Author: Benjamin Hogan
	*Student No: c3256846
	*Date: 2/10/2017
	*Descripion:
	*	Class for simulating a bridge crossing.
 */

import java.util.*;
import java.util.concurrent.Semaphore;

public class Bridge extends Thread{

	// Local variables
	private Semaphore semaphore, north, south;
	private Thread thread = new Thread();
	private int count = 0, crit = 0;
	private Queue<Farmer> northside = new Queue<Farmer>();
    private Queue<Farmer> southside = new Queue<Farmer>();
	
	// Constructor which creates a bridge with north and south bound farmers and the problem number.
	public Bridge(int northbound, int southbound)
	{
		semaphore = new Semaphore(1);
		create(northbound, southbound);
	}

	// Role:    sets up the simulation with north and south side queues and starts each farmer to cross the bridge.
    // Args:    int north farmers, south farmers, int problem.
    // Return:  none
	public void create(int northbound, int southbound)
	{
		// Creates the northbound farmers inside a queue.
		for(int x = 1; x <= northbound; x++)
        {
        	Farmer temp = new Farmer("N_Farmer" + x, "South", this);
        	northside.append(temp);
        	System.out.println(temp.getID() + ": Wating for bridge. Going towards " + temp.getDirection());
        }
        // Creates the southbound farmers inside a queue.
        for(int x = 1; x <= southbound; x++)
        {
        	Farmer temp = new Farmer("S_Farmer" + x, "North", this);
        	southside.append(temp);
        	System.out.println(temp.getID() + ": Wating for bridge. Going towards " + temp.getDirection());
        }

        // works out which side has the most farmers to start
        int side = 0;
        if(northbound >= southbound)
        	side = northbound;
        else
        	side = southbound;

        // starts the farmers equally by alternating the thread start between sides.
        boolean north = false, south = false;
        northside.createIterator();
        southside.createIterator();
        for(int x = 0; x < side; x ++)
        {
        	try{
        		if(!north)
        			northside.nextIter().getData().start();
        	}catch(NoSuchElementException e){north = true;}
        	try{
        		if(!south)
        			southside.nextIter().getData().start();
        	}catch(NoSuchElementException e){south = true;}
        }
	}

	// Role:    gets the farmer and uses a semaphore to allow only one farmer at a time on the bridge
    // Args:    Farmer to cross.
    // Return:  None
	public void crossBridgeSingle(Farmer farmer)
    {
        try
        {
            semaphore.acquire();
            // CRITICAL SECTION adds to the NEON count at the end.
            System.out.printf("%s: Crossing bridge Step 5.\n",farmer.getID());
				Thread.sleep(10);
			System.out.printf("%s: Crossing bridge Step 10.\n",farmer.getID());
				Thread.sleep(10);
			System.out.printf("%s: Crossing bridge Step 15.\n",farmer.getID());
				Thread.sleep(10);
			System.out.printf("%s: Across the bridge.\n",farmer.getID());
			count++;
			System.out.println("NEON: " + count);
        }
        catch(InterruptedException e){}

        finally
        {
            // releases the semaphore and changes the direction of the farmer and adds them to the respective queue.
            semaphore.release();
            
            if(farmer.getDirection().equals("South"))
            	farmer.setDirection("North");
            else
            	farmer.setDirection("South");
        }
    }
}