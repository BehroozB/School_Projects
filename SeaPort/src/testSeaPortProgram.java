import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;


public class testSeaPortProgram {
	
	/*
	 * The test method below will check the person class to make sure the class structure is working as intended. 
	 
	@Test
	public void test() {
		try{
			SeaPortProgram testing = new SeaPortProgram();
			World testingWorld = testing.getWorld();
			ArrayList<Person> tempJob = null;
			for (SeaPort seaPort : testingWorld.ports.values()) {
				for (Ship ship : seaPort.getShips()) {
					tempJob = seaPort.person;
				}
			}
			System.out.println(tempJob.get(0).toString());
			
			assertEquals(tempJob.get(0).toString(), "Person: Roosevelt 50035 stevedore");
		}catch(Exception e){
			System.out.println("Error In Test" + e);
		}//End of try/catch block here
	}//End of the test Junit
	*/
	
	/*
	 * TestTwo method below will call the Job class, run method. This is expected to fail and toss a nullPointerException.
	*/ 
	@Test
	public void testTwo(){
		Job tempJob = null;
		try{
			SeaPortProgram testing = new SeaPortProgram();
			World testingWorld = testing.getWorld();
			for (SeaPort seaPort : testingWorld.ports.values()) {
				for (Ship ship : seaPort.getShips()) {
					tempJob.run();		
				}
			}
		}catch(NullPointerException ex){
			System.out.println("Error In TestTwo \n");
			ex.printStackTrace();
		}//End of try/catch block here
	} 
	
	
}//testSeaPortProgram ends here


















