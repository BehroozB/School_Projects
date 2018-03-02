/*
 *  File name: Thing.java
 *  Date: April 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: Creates the class Thing 
 */


import java.util.Scanner;

public class Thing implements Comparable<Thing> {
	int index;
	String name;
	int parent;

	public Thing() {
		this.name = "";
		this.index = 0;
		this.parent = 0;
	} 
	
	public Thing(Scanner sc) {
		if (sc.hasNext()) {
			this.name = sc.next();
		}
		if (sc.hasNextInt()) {
			this.index = sc.nextInt();
		}
		if (sc.hasNextInt()) {
			this.parent = sc.nextInt();
		}
	} 

	/************************************************************************************************
	 * Getter method to return index values
	 *************************************************************************************************/
	public int getIndex() {
		return index;
	}//End of the getIndex method here

	/**************************************************************************************************
	 * Getter method for the name value
	 *************************************************************************************************/
	public String getName() {
		return name;
	}//End of the getName method here

	/**************************************************************************************************
	 * Getter method for the parent value
	 *************************************************************************************************/
	public int getParent() {
		return parent;
	}//End of the getParent method here
	


	@Override
	public int compareTo(Thing o) {
		if (this.index > o.index)
			return 1;
		else if (this.index == o.index)
			return 0;
		else
			return -1;
	}

	@Override
	public String toString() {
		return name + " " + index;
	}
	
}//End of the Thing class


























