/*
 *  File name: PassengerShip.java
 *  Date: April 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: Will create the passengerShip object that extends ship 
 */



import java.util.Scanner;

public class PassengerShip extends Ship {
	int numberOfOccupiedRooms;
	int numberOfPassengers;
	int numberOfRooms;

	public PassengerShip(Scanner sc, boolean busy) {
		super(sc, busy);
		if (sc.hasNextInt()) {
			this.numberOfPassengers = sc.nextInt();
		}
		if (sc.hasNextInt()) {
			this.numberOfRooms = sc.nextInt();
		}
		if (sc.hasNextInt()) {
			this.numberOfOccupiedRooms = sc.nextInt();
		}
	} // end Scanner Constructor	
	
	public String toString() {
		String st = "Passenger ship: " + super.toString();
		if (jobs.size() == 0) {
			return st;
		}
		for (Job mj : jobs) {
			st += "\n	- " + mj;
		}
		return st;
	}
	
} // end class PassengerShip






















