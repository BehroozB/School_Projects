/*
 *  File name: ShipComparator.java
 *  Date: April 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: Creates the ShipComparator class 
 */

import java.util.Comparator;

public class ShipComparator implements Comparator<Ship> {

	private String typeOfSort;

	public ShipComparator(String typeOfSort) {
		this.typeOfSort = typeOfSort;
	}

	/******************************************************************************************************************
	 * The compare method below will take two arguments and compares them together to identify the parent.
	 *****************************************************************************************************************/
	public int compare(Ship c1, Ship c2) {
		int response = -1;
		switch (typeOfSort.charAt(0)) {
		case 'l':
			if (c1.getLength() == c2.getLength()) {
				response = 0;
			} else if (c1.getLength() > c2.getLength()) {
				response = 1;
			} else {
				response = -1;
			}
			break;
		case 'd':
			if (c1.getDockTime().getTime() == c2.getDockTime().getTime()) {
				response = 0;
			} else if (c1.getDockTime().getTime() > c2.getDockTime().getTime()) {
				response = 1;
			} else {
				response = -1;
			}
			break;

		case 'w':
			if (c1.getWeight() == c2.getWeight()) {
				response = 0;
			} else if (c1.getWeight() > c2.getWeight()) {
				response = 1;
			} else {
				response = -1;
			}
			break;
		case 'X':
			if (c1.getWidth() == c2.getWidth()) {
				response = 0;
			} else if (c1.getWidth() > c2.getWidth()) {
				response = 1;
			} else {
				response = -1;
			}
			break;

		default:
			if (c1.getWidth() == c2.getWidth()) {
				response = 0;
			} else if (c1.getWidth() > c2.getWidth()) {
				response = 1;
			} else {
				response = -1;
			}
			break;
		}//End of the switch statement here
		return response;
	}
}
