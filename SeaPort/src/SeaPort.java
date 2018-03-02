/*
 *  File name: SeaPort.java
 *  Date: March 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: Creates the SeaPort class, which extends the object thing 
 */

import java.util.ArrayList;
import java.util.Scanner;

class SeaPort extends Thing {
	ArrayList<Dock> docks; 
	ArrayList<Ship> que; 
	ArrayList<Ship> ships; 
	ArrayList<Person> person; 

	
	public SeaPort(Scanner sc) {
		super(sc);
		docks = new ArrayList<Dock>();
		que = new ArrayList<Ship>();
		ships = new ArrayList<Ship>();
		person = new ArrayList<Person>();
	}

	public String toString() {
		String st = "\n\n SeaPort: " + super.toString() + '\n';
		for (Dock md : docks) {
			st += "\n" + md;
		}
		st += "\n\n --- List of all ships in que:";
		for (Ship ms : que) {
			st += "\n  > " + ms;
		}
		st += "\n\n --- List of all ships:";
		for (Ship ms : ships) {
			st += "\n  > " + ms;
		}
		st += "\n\n --- List of all person:";
		for (Person mp : person) {
			st += "\n  > " + mp;
		}
		return st;
	}

	/******************************************************************************************
	 * Getter method for the docks objects
	 *******************************************************************************************/
	public ArrayList<Dock> getDocks() {
		return docks;
	}//End of the getDocks method here

	
	/********************************************************************************************
	 * Getter method for the Ship objects that are in Que
	 *******************************************************************************************/
	public ArrayList<Ship> getQue() {
		return que;
	}//End of the getQue method here
	
	/********************************************************************************************
	 * Getter method for the ships object
	 *******************************************************************************************/
	public ArrayList<Ship> getShips() {
		return ships;
	}//End of the getShips method here


	/********************************************************************************************
	 * Getter method for the person object
	 *******************************************************************************************/
	public ArrayList<Person> getPerson() {
		return person;
	}//End of the getPerson method here
	
} //End of the SeaPort class here























