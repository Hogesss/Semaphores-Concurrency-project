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
		semaphore = new Semaphore(2);
		north = new Semaphore(2);
		south = new Semaphore(2);
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

    // Role:    Used for the double bridge problem. Allows two farmers to cross the bridge only if they are from the same side.
    // Args:    The farmer to cross the bridge
    // Return:  None
    public void crossBridgeDouble(Farmer farmer)
    {
        try{
	        // Uses 2 semaphores north and south, waits for a pair of farmers from either side and then continues to the critical section. 
	        if(farmer.getDirection().equals("South"))
	        {
	        	south.acquire();
	        	// wait until another farmer enters the bridge.
	        	while(south.availablePermits() != 0){}
	        }
	    	if(farmer.getDirection().equals("North"))
	        {
	        	Thread.sleep(1); // Sleep the South farmers to allow the north farmers to sync initially. 
	        	north.acquire();
	        	// wait until another farmer enters the bridge.
	        	while(north.availablePermits() != 0){}
	        }
	        
	        try
	        {
	            semaphore.acquire();
	            // CRITICAL SECTION adds to the NEON count at the end.
	            crit++;
	            Thread.sleep(1);
	            // only releases the north/south semaphores when both pass this part of code. Releases both semaphores at same time to allow a smooth transition
	      		
	            if(farmer.getDirection().equals("South") && crit == 2)
	            {
					south.release(2);
					crit = 0;
	            }
	        	if(farmer.getDirection().equals("North") && crit == 2)
	        	{
					north.release(2);
					crit = 0;
	        	}

	            System.out.printf("%s: Crossing bridge Step 5.\n",farmer.getID());
					Thread.sleep(500);
				System.out.printf("%s: Crossing bridge Step 10.\n",farmer.getID());
					Thread.sleep(500);
				System.out.printf("%s: Crossing bridge Step 15.\n",farmer.getID());
					Thread.sleep(500);
				System.out.printf("%s: Across the bridge.\n",farmer.getID());
				count++;
				System.out.println("NEON: " + count);
	        }catch(InterruptedException e){}

	        finally
	        {
	            // releases the semaphore
	            semaphore.release();
	        }
		}catch(InterruptedException e){}
    }
}