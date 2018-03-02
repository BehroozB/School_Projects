/*
 *  File name: CargoShip.java
 *  Date: March 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: Will create the class cargo ship, which will extend ship 
 */



import java.util.Scanner;

public class CargoShip extends Ship {
	double cargoValue;
	double cargoVolume;
	double cargoWeight;
	

	public CargoShip(Scanner sc, boolean busy) {
		super(sc, busy);
		if (sc.hasNextDouble()) {
			this.cargoWeight = sc.nextDouble();
		}
		if (sc.hasNextDouble()) {
			this.cargoVolume = sc.nextDouble();
		}
		if (sc.hasNextDouble()) {
			this.cargoValue = sc.nextDouble();
		}
	} 
	
	
	@Override
    public String toString() {
        return "Cargo Ship: " + super.toString();
    }
	
}//End of the cargoShip class here



























