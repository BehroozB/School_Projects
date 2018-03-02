/*
 *  File name: Dock.java
 *  Date: March 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: Will create the class for the Dock object, which will extend Things 
 */



import java.util.Scanner;

public class Dock extends Thing {
	public Ship ship;

	public Dock(Scanner sc) {
		super(sc);
	}

	public String toString() {
		return String.format("Dock: %s \n\tShip: %s\n", super.toString(), ship.toString());
	}

	/************************************************************************************************
	 * Getter method for the ship object
	 *************************************************************************************************/
	public Ship getShip() {
		return ship;
	}//End of the getShip method here
	
	/************************************************************************************************
	 * Setter method for the ship object
	 *************************************************************************************************/
	public void setShip(Ship ship) {
		this.ship = ship;
	}//End of the setShip method here
}//End of the Dock class here

























