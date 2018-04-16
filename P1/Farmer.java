/*  
	Farmer.java
	
	*Author: Benjamin Hogan
	*Student No: c3256846
	*Date: 2/10/2017
	*Descripion:
	*	Class for storing data related to a Farmer.
 */

import java.util.*;

public class Farmer extends Thread{

	private String name, direction;
	private Thread thread = new Thread();
	private Bridge bridge;
	
	// Role:    creates a new farmer object
    // Args:    String id of the farmer, String direction of the farmer, Bridge the farmers will cross, Integer for the problem type
	public Farmer(String id, String way, Bridge cross)
	{
		name = id;
		direction = way;
		bridge = cross;
	}

	// Role:    returns the String id value of the farmer
    // Args:    none
    // Return:  String id.
	public String getID()
	{
		return name;
	}

	// Role:    sets the id of the farmer
    // Args:    String name of the farmer
    // Return:  none
	public void setID(String name)
	{
		this.name = name;
	}

	// Role:    returns the direction the farmer is going
    // Args:    none
    // Return:  String direction of the farmer
	public String getDirection()
	{
		return direction;
	}

	// Role:    sets the direction the farmer is travelling
    // Args:    String north or south
    // Return:  none
	public void setDirection(String way)
	{
		direction = way;
	}

	// Overloaded Run() method for thread class.
	// Role:    starts the read running
    // Args:    none
    // Return:  none
	public void run()
	{
		try{
			// infinite loop to cross the bridge
			while(true)
			{			
				// Crosses the bridge with this farmer and sleeps.
				bridge.crossBridgeSingle(this);

				Thread.sleep(10+(long)(Math.random()*20));
				System.out.println(this.getID() + ": Waiting for bridge. Going towards " + this.getDirection());
			}
		}catch(InterruptedException e){}
	}
}