/*  
	Node.java
	
	*Author: Benjamin Hogan
	*Student No: c3256846
	*Date: 2/10/2017
	*Descripion:
	*	Simple node class containing next, prev pointers and data for the node.
 */

public class Node<T>{

	private T data;
	private Node<T> next, prev;

	// Role:    creates a node object with null data
    // Args:    none
	public Node()
	{
		data = null;
	}

	// sets new node wit data set to input
	public Node (T input)
	{
		data = input;
	}

	// returns the data in the node
	public T getData()
	{
		return data;
	}

	// sets the next node in the list
	public void setNext(Node<T> item)
	{
		next = item;
	}

	// sets the previous node in the list.
	public void setPrev(Node<T> item)
	{
		prev = item;;
	}

	// returns the node next to this node in the list.
	public Node<T> getNext()
	{
		return next;
	}

	// returns the node previous to this node in the list.
	public Node<T> getPrev()
	{
		return prev;
	}
}