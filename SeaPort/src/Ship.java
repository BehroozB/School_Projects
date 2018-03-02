/*
 *  File name: Ship.java
 *  Date: April 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: Creates the Ship class, which extends the object things 
 */


import java.util.*;

import java.util.Scanner;

class Ship extends Thing {
	public PortTime arrivalTime = new PortTime(0);
	public PortTime dockTime = new PortTime(0);
	public double draft;
	public double length;
	public double weight;
	public double width;
	ArrayList<Job> jobs = new ArrayList<Job>();
	public boolean busy;
	

	public Ship(Scanner sc, boolean busy) {
		super(sc);
		if (sc.hasNextDouble()) {
			this.weight = sc.nextDouble(); 
		}
		if (sc.hasNextDouble()) {
			this.length = sc.nextDouble();
		}
		if (sc.hasNextDouble()) {
			this.width = sc.nextDouble();
		}
		if (sc.hasNextDouble()) {
			this.draft = sc.nextDouble();
		}
		this.busy = busy;
	} 

	/******************************************************************************************************************
	 * Getter method for the Dock Time value
	 *****************************************************************************************************************/
	public PortTime getDockTime() {
		return dockTime;
	}//End of the getDockTime method here	
	
	/******************************************************************************************************************
	 * Getter method for the job array
	 *****************************************************************************************************************/
	public ArrayList<Job> getJobs() {
		return jobs;
	}
	
	
	
} //End of the Ship class here

















































