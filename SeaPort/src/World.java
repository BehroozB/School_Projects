/*
 *  File name: World.java
 *  Date: April 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: This file works with the seaPortProgram.java file and creates the world class,
 *  	which extends the class thing. 
 */


import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

public class World extends Thing {
	HashMap<Integer, SeaPort> ports;
	PortTime time;
	JPanel subProgP = new JPanel();

	public World(Scanner sc, JPanel subProgP) {
		super(sc);
        this.subProgP = subProgP; 
        ports = new HashMap<Integer, SeaPort>();
		while (sc.hasNextLine()) {
			addEntry(sc.nextLine());
		}//End of while statement here
		generateTree();
	}//	End of the world constructor here  

	
	
	/********************************************************************************
	 * Setter method for the dock objects
	 ******************************************************************************/
	public void setDock(Scanner sc) {
		Dock tempDock = new Dock(sc);
		for (SeaPort seaPort : ports.values()) {
			if (seaPort.getIndex() == tempDock.getParent()) {
				seaPort.getDocks().add(tempDock);
			}
		}//End of the for loop here
	}//End of the addDock method here

	
	/********************************************************************************
	 * Setter method for the passenger ship objects
	 ******************************************************************************/
	public void setPassengerShip(Scanner sc) {
		PassengerShip tempPShip = new PassengerShip(sc, true);
		setShip(tempPShip);
	}//End of the setPassengerShip here

	
	/********************************************************************************
	 * Setter method for the cargo ship objects
	 ******************************************************************************/
	public void setCargoShip(Scanner sc) {
		CargoShip tempCShip = new CargoShip(sc, true);
		setShip(tempCShip);
	}//End of the setCargoShip method here

	
	/********************************************************************************
	 * Setter method for the person objects
	 ******************************************************************************/
	public void setPerson(Scanner sc) {
		Person tempPerson = new Person(sc);
		for (SeaPort seaPort : ports.values()) {
			if (seaPort.getIndex() == tempPerson.getParent()) {
				seaPort.getPerson().add(tempPerson);
			}
		}//End of the for loop method here
	}// End of the setPerson method here
	
	
	/********************************************************************************
	 * Setter method for the person objects
	 ******************************************************************************/
	@SuppressWarnings("unused")
	public void setJob(Scanner sc) {
		Job tempJob = new Job(sc, ports);
		if(tempJob == null) {
			return;
		}
//Below the for loop checks if a ship might contain a job
		for (SeaPort seaPort : ports.values()) {		
			for (Ship ship : seaPort.getShips()) {
				int tempParent = tempJob.parent;
				tempJob.jobShip = this.getShipByIndex(tempParent);
				//Below will be a conditional if statement for null value
				if ((ship.getIndex() != 0) && (this.getDockByIndex(tempParent) != null)) {
					ship.getJobs().add(tempJob);
					
				}//If statement here
			}//End of the ship for loop here			
			break;
		}//End of the for loop method here
	
	}// End of the setPerson method here
	
	
	/*****************************************************************************
	 * toString method, which will return the value of ports
	 ******************************************************************************/
	public String toString() {
		String result = "\n\n>>>>> The world:";
		if (ports.size() == 0) {
			return result;
		}//End of the if statement here
		for (SeaPort seaPort : ports.values()) {
			result += seaPort;
		}//End of the for loop method here
		return result;
	}//End of toString method here

	
	/*****************************************************************************
	 * The addEntry method below will call needed setters to set the values for the project.
	 ******************************************************************************/
	public void addEntry(String result) {
		Scanner sc = new Scanner(result);
		if (!sc.hasNext()) {
			sc.close();
			return;
		}
		switch (sc.next()) {
		case "port":
			SeaPort portTemp = new SeaPort(sc);//Passing the scanner, so it can create SeaPort
			ports.put(portTemp.index, portTemp);//Adding the SeaPort to the ports array
			break;
		case "pship":
			setPassengerShip(sc);
			break;
		case "dock":
			setDock(sc);
			break;
		case "cship":
			setCargoShip(sc);
			break;
		case "person":
			setPerson(sc);
			break;
		case "job":
			setJob(sc);
			break;
		default:
			break;
		}//End of switch statement here
	} //End of the addEntry method here

	
	/*****************************************************************************
     * Generate JTree 
     *****************************************************************************/
    void generateTree() {
        for (SeaPort port : ports.values()) {
            DefaultMutableTreeNode portNode = new DefaultMutableTreeNode(port.getName());
            SeaPortProgram.root.add(portNode);
            for (Dock dock : port.getDocks()) {
                DefaultMutableTreeNode dockNode = new DefaultMutableTreeNode(dock.getName());
                if (dock.getShip() != null) {
                    DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode(dock.getShip().getName());
                    dockNode.add(shipNode);
                }
                portNode.add(dockNode);
            }
            DefaultMutableTreeNode queShipsNode = new DefaultMutableTreeNode("All Ships in que");
            for (Ship ship : port.getShips()) {
                DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode(ship.name);
                DefaultMutableTreeNode weight = new DefaultMutableTreeNode("Weight : " + ship.weight);
                DefaultMutableTreeNode width = new DefaultMutableTreeNode("Width : " + ship.width);
                DefaultMutableTreeNode draft = new DefaultMutableTreeNode("Draft : " + ship.draft);
                DefaultMutableTreeNode length = new DefaultMutableTreeNode("Length : " + ship.length);
                shipNode.add(weight);
                shipNode.add(width);
                shipNode.add(draft);
                shipNode.add(length);
                if (ship instanceof PassengerShip) {
                    PassengerShip s = (PassengerShip) ship;
                    shipNode.add(new DefaultMutableTreeNode("No.Of Occupied Rooms : " + s.numberOfOccupiedRooms));
                    shipNode.add(new DefaultMutableTreeNode("No.Of Passengers     : " + s.numberOfPassengers));
                    shipNode.add(new DefaultMutableTreeNode("No.Of Rooms          : " + s.numberOfRooms));
                }
                if (ship instanceof CargoShip) {
                    CargoShip s = (CargoShip) ship;
                    shipNode.add(new DefaultMutableTreeNode("Cargo Value      : " + s.cargoValue));
                    shipNode.add(new DefaultMutableTreeNode("Cargo Volume     : " + s.cargoVolume));
                    shipNode.add(new DefaultMutableTreeNode("Cargo Weight     : " + s.cargoWeight));
                }
                queShipsNode.add(shipNode);
            }
            portNode.add(queShipsNode);
            DefaultMutableTreeNode allShipsNode = new DefaultMutableTreeNode("All Ships");
            for (Ship ship : port.getShips()) {

                DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode(ship.name);
                DefaultMutableTreeNode weight = new DefaultMutableTreeNode("Weight : " + ship.weight);
                DefaultMutableTreeNode width = new DefaultMutableTreeNode("Width : " + ship.width);
                DefaultMutableTreeNode draft = new DefaultMutableTreeNode("Draft : " + ship.draft);
                DefaultMutableTreeNode length = new DefaultMutableTreeNode("Length : " + ship.length);
                shipNode.add(weight);
                shipNode.add(width);
                shipNode.add(draft);
                shipNode.add(length);
                if (ship instanceof PassengerShip) {
                    PassengerShip s = (PassengerShip) ship;
                    shipNode.add(new DefaultMutableTreeNode("No.Of Occupied Rooms : " + s.numberOfOccupiedRooms));
                    shipNode.add(new DefaultMutableTreeNode("No.Of Passengers     : " + s.numberOfPassengers));
                    shipNode.add(new DefaultMutableTreeNode("No.Of Rooms          : " + s.numberOfRooms));
                }
                if (ship instanceof CargoShip) {
                    CargoShip s = (CargoShip) ship;
                    shipNode.add(new DefaultMutableTreeNode("Cargo Value      : " + s.cargoValue));
                    shipNode.add(new DefaultMutableTreeNode("Cargo Volume     : " + s.cargoVolume));
                    shipNode.add(new DefaultMutableTreeNode("Cargo Weight     : " + s.cargoWeight));
                }
                allShipsNode.add(shipNode);
                portNode.add(allShipsNode);

            }
            portNode.add(queShipsNode);
            DefaultMutableTreeNode personsNode = new DefaultMutableTreeNode("Persons");
            for (Person p : port.getPerson()) {
                personsNode.add(new DefaultMutableTreeNode(p.name + " Skill: " + p.skill));
            }
            portNode.add(personsNode);
        }
    }//End of the generate tree node method here
	
	/*****************************************************************************
	 * Getter method to retrieve the seaPort object by index value
	 ******************************************************************************/
	public SeaPort getSeaPortByIndex(int x) {
		for (SeaPort seaPort : ports.values()) {
			if (seaPort.getIndex() == x) {
				return seaPort;
			}
		}//End of the for loop statement here
		return null;
	} //End of the get seaport method here

	
	/*****************************************************************************
	 * Getter method to retrieve the Dock objects by index value
	 ******************************************************************************/
	public Dock getDockByIndex(int x) {
		for (SeaPort seaPort : ports.values()) {
			for (Dock dock : seaPort.getDocks()) {
				if (dock.getIndex() == x) {
					return dock;
				}
			}//End of the nested for loop statement here
		}//End of the first loop statement here
		return null;
	}//End of the getter method for the dock object by index value here 
	
	
	/********************************************************************************
	 * Setter method for the ship objects
	 *********************************************************************************/
	public void setShip(Ship ship) {
		Dock dock = getDockByIndex(ship.getParent());
		if (dock == null) {
			ship.busy = true;
			getSeaPortByIndex(ship.getParent()).getShips().add(ship);
			getSeaPortByIndex(ship.getParent()).getQue().add(ship);
		} 
		else {
			dock.setShip(ship);
			getSeaPortByIndex(dock.getParent()).getShips().add(ship);
		}//End of the if block here
	}//End of the setShip method here

	
	/********************************************************************************
	 * Getter for ship object, which will return a ship
	 *********************************************************************************/
	public Ship getShipByIndex (int x) {	
		for (SeaPort seaPort : ports.values()) {
			for (Ship ship : seaPort.getShips()) {
				if(ship.index == x) {
			          return ship;
			    }
			}
		}		
		return null;
	} // end getDockByIndex
	 
	
	/***********************************************************************************
	 * Search method below will allow the user to search for specific values
	 *********************************************************************************/
	public String search(String type, String target) {
		String result = "";
		switch (type) {
		case "Name":
			result += searchByName(target);
			break;
		case "Index":
			try {
				result += searchByIndex(Integer.parseInt(target));
				} 
			catch (NumberFormatException e) {
				result += "Not a valid search target for Index";
			}
			break;
		case "Skill":
			result += searchByType(target);
			break;
		default:
			break;
		}//End of the switch statement here
		return result;
	}//End of the search method here

	
	/***********************************************************************************
	 * searchByName method below will find the user argument that was given from the objects
	 *********************************************************************************/
	public String searchByName(String target) {
		for (SeaPort seaPort : ports.values()) {
			if (seaPort.getName().equals(target)) {
				return seaPort.toString();
			}
		for (Dock dock : seaPort.getDocks()) {
			if (dock.getName().equals(target)) {
				return dock.toString();
			}
		}
		for (Ship ship : seaPort.getShips()) {
			if (ship.getName().equals(target)) {
				return ship.toString();
			}
		}
		for (Person person : seaPort.getPerson()) {
			if (person.getName().equals(target)) {
				return person.toString();
				}
			}
		}
		return "Your Result of = " + target +  " Was Not Found";
	}//End of the searchByName method here

	
	/***********************************************************************************
	 * searchByIndex allows the program to find the need user given value and find it's index
	 *********************************************************************************/
	public String searchByIndex(int target) {
		for (SeaPort seaPort : ports.values()) {
			if (seaPort.getIndex() == target) {
				return seaPort.toString();
			}
			for (Dock dock : seaPort.getDocks()) {
				if (dock.getIndex() == target) {
					return dock.toString();
				}
			}
			for (Ship ship : seaPort.getShips()) {
				if (ship.getIndex() == target) {
					return ship.toString();
				}
			}
			for (Person person : seaPort.getPerson()) {
				if (person.getIndex() == target) {
					return person.toString();
				}
			}
		}
		return "Result Not Found";
	}//End of the searchByIndex method here

	
	/***********************************************************************************
	 * The searchByType method below allows the program to find the needed type
	 *********************************************************************************/
	public String searchByType(String target) {
		String result = "";
		for (SeaPort seaPort : ports.values()) {
			for (Person person : seaPort.getPerson()) {
				if (person.getSkill().equals(target)) {
					result += person.toString() + '\n';
				}
			}
		}
		if (result == "") {
			return "Result Not Found";
		}
		return result;
	}//End of the searchByType method here
}//End of the world class here ********************************************************************************

























