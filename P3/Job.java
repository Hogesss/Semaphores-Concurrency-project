/*  
	Job.java
	
	*Author: Benjamin Hogan
	*Student No: c3256846
	*Date: 2/10/2017
	*Descripion:
	*	Class for storing data related to a Job.
 */

import java.util.*;

public class Job{

	private String name, type;
	private int pages;
	
	public Job()
	{}

	// returns the name of the job
	public String getID()
	{
		return name;
	}

	// sets the name of the job
	public void setID(String name)
	{
		this.name = name;
	}

	// returns the String type of the job
	public String getType()
	{
		return type;
	}

	// sets the type of the job
	public void setType(String type)
	{
		this.type = type;
	}

	// sets the number of pages in the job
	public void setPages(int pages)
	{
		this.pages = pages;
	}

	// returns the number of pages in the job
	public int getPages()
	{
		return pages;
	}
}