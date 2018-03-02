/*
 *  File name: Person.java
 *  Date: March 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: The person class extends things and contains the person constructor 
 */



import java.util.Scanner;

class Person extends Thing {
	public String skill;

	public Person(Scanner sc) {
		super(sc);
		if (sc.hasNext()) {
			this.skill = sc.next();
		}
	} 

	/**************************************************************************************
	 * Getter for the skill object
	 *************************************************************************************/
	public String getSkill() {
		return skill;
	}//End of the getSkill method here

	
	@Override
	public String toString() {
		return "Person: " + super.toString() + " " + skill;
	}
	

}//End of the person class  









