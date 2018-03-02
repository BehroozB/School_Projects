
/*
 *  File name: Job.java
 *  Date: April, 20 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: Will create the Job class 
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.HashMap;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Job extends Thing implements Runnable {
	
	HashMap<Integer, SeaPort> port = new HashMap<Integer, SeaPort>();
	SeaPort seaPort = null;
    Ship jobShip = null;
    Dock jobDock = null;
    Status status;
    
    
    ArrayList<String> requirements = new ArrayList<>();
    HashMap<String, Integer> skillNum = new HashMap<String, Integer>();
    HashMap<String, Integer> countPesonWithSkill = new HashMap<String, Integer>();
    JProgressBar progressBar;
    JPanel jobPanel = new JPanel();
    JScrollPane scroll;
    JButton pauseButton = new JButton("Pause");
    JButton cancelButton = new JButton("Cancel");
    
    
    boolean goFlag = true, noKillFlag = true;
    long jobTime;//Duration of job
    

    enum Status {RUNNING, SUSPENDED, WAITING, DONE};
    
    /********************************************************************************
	 * Job constructor here
	 ******************************************************************************/
    public Job( Scanner sc, HashMap<Integer, SeaPort> ports){
    	super(sc);
		jobTime = (long) sc.nextDouble();//Duration here
		this.status = Status.WAITING;//Status
		while(sc.hasNext()){
			requirements.add(sc.next());//All the jobs
		}//End of while loop here
		port = ports;
		seaPort = (SeaPort) port.get(parent);
		progressBar = new JProgressBar();
        progressBar.setStringPainted (true);
        
        
        String temp = ""+index;
        
        jobPanel.add(progressBar);
        jobPanel.add(new JLabel(this.name, SwingConstants.CENTER));
        jobPanel.add(new JLabel(temp, SwingConstants.CENTER));
        jobPanel.add(new JLabel(requirements.toString(), SwingConstants.CENTER));
        
        jobPanel.add(pauseButton);
        jobPanel.add(cancelButton);
        scroll = new JScrollPane(jobPanel);
        
        SeaPortProgram.jobConsole.add(scroll);

        pauseButton.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                toggleGoFlag();
              }
            });
        
        cancelButton.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
              setKillFlag();
            }
          });
        
        new Thread (this).start();//New Thread is started for each job
    }//End of the Job constructor here
		

    public void toggleGoFlag () {
        goFlag = !goFlag;
      } // end method toggleRunFlag
      
      public void setKillFlag () {
        noKillFlag = false;
        cancelButton.setBackground (Color.red);
      } // end setKillFlag
    
    /********************************************************************************
	 * show status method
	 ******************************************************************************/
	    void showStatus(Status st) {
	        status = st;
	        switch (status) {
	            case RUNNING:
	                pauseButton.setBackground(Color.GREEN);
	                pauseButton.setText("Running");
	                break;
	            case SUSPENDED:
	                pauseButton.setBackground(Color.YELLOW);
	                pauseButton.setText("Suspended");
	                break;
	            case WAITING:
	                pauseButton.setBackground(Color.ORANGE);
	                pauseButton.setText("Waiting turn");
	                break;
	            case DONE:
	                pauseButton.setBackground(Color.RED);
	                pauseButton.setText("Done");
	                break;
	        } // end switch on status
	    } // end showStatus

	    
	    /********************************************************************************
		 * run method for the progress bars
		 ******************************************************************************/
	    @Override
	    public void run() {
	    	long time = System.currentTimeMillis();
	        long startTime = time;
	        long stopTime = time + 1000 * jobTime;
	        double duration = stopTime - time;
	        boolean isComp = true;
	        String key = null;
	        
	        again:
	        for(SeaPort tempJ : port.values()){
	        	for(Dock tempDock : tempJ.getDocks()){
	        		if(tempDock.ship.busy) {
	        			this.status = Status.WAITING;
	        			showStatus(status);
	        			tempDock.ship.busy = false;
	        		}

	        		while(status == Status.WAITING){
	        			if(tempJ.name != null){
	        				synchronized (status) {	
	        					for (String r : this.requirements) {
	                                key = tempJ.name + "." + r;
	                                
	                                if(skillNum.get(key) == null){// Does the skill exist in the system
	                                	status = Status.SUSPENDED;
	                                	showStatus(status);
	                                	cancelButton.setEnabled(false);
	                                    pauseButton.setEnabled(false);
	                                    isComp = false;
	                                    break again;
	                                }//End of 1st nested if statement
	                                
	                                synchronized (skillNum.get(key)) {
		                                if(skillNum.get(key) == 0){
		                                	isComp = false;
		                                	continue again;//If the skillNum is the initial value then start again.
		                                } else {
		                                	isComp = true;
		                                }//End of if-else statement
	                                }
	        					}//-----------------------------	End of for loop here
	        					
	        					
	        					//If statement below should only be accessed if there are jobs
	        					if(isComp) {
	        						ArrayList<Job> jobList =  tempDock.ship.getJobs();
	        						boolean isCompleted = true;
	        						for(Job x : jobList) {
	        							if(status == Status.SUSPENDED && x.status == Status.RUNNING) {
	        								isCompleted = false;
	        								break;
	        							}
	        						}//End of for loop for job similarities
	        						
	        						
	        						if(isCompleted) {
	        							status = Status.RUNNING;
	        							updatePerCnt(tempJ.name, this.requirements, -1);
	        							isComp = false;
	        							try {
	                                        Thread.sleep(100);
	                                    } catch (Exception e) {
	                                        System.out.println(" An issue has occured! " + e);// for testing purposes
	                                    }
	                                    break again;
	        						}
	        					}//end of if statement here
	        				}
	        			}//End of if statement here
	        		}//-------------------------------- 1st While loop statement ends here	------------------------
	        		
	        		
	        		while (time < stopTime && status == Status.RUNNING) {
	        			showStatus (status);
	        			try{
	        				progressBar.setValue ((int)(((time - startTime) / duration) * 100));
	        				progressBar.repaint();
	        				
	        				Thread.sleep(100);
	        				time += 500;
	        			} catch (Exception e) {
		    	        	System.out.println("Error with job synchroniztion!");
	        			}
	        		}//End of while loop here
	        		
	        		try{
	        			if(status == Status.RUNNING){
	        				status = Status.DONE;
	        				showStatus(status);
	                        progressBar.setValue(100);
	                        progressBar.repaint();
	                        
	                        if(tempDock != null && tempDock.ship != null){
	                        	tempDock.ship = null;
	                        	SeaPortProgram.displayArea.append("Left from Dock " + tempDock.index + "\n");
	                        }//end of nested if statement here
	                        updatePerCnt(tempJ.name, this.requirements, 1);
	                        refreshJobPanel();
	                        progressBar.setString("Job Done");
	                        cancelButton.setEnabled(false);
	                        pauseButton.setEnabled(false);   
	        			}
	        		} catch (Exception e) {
	    	        	System.out.println("Error with progress bar! \n");
	    	        	e.printStackTrace();
	        		}//End of try/catch block here
	        	}
	        }//--------------------	End of first loop statement here 	------------------------------
	    } // end method run - implements runnable
	    
	    /*
	     * 
	     */
	    public void updatePerCnt(String portName, ArrayList<String> requirements, int another){
	    	String temp;
	    	
	    	for(String req : requirements){
	    		temp = portName + "." + req;
	    		skillNum.put(temp, skillNum.get(temp) + another);
	    	}
	    	refreshJobPanel();
	    }//End of updatePerCnt method here
	    
	    
	    /*
	     *	The refreshJobPanel below updates the panel with the new person, job data. 
	     */
	    public void refreshJobPanel() {
	    	SortedSet<String> temp = new TreeSet<>(countPesonWithSkill.keySet());
	    	
	    	String[] keyParts;
	        String prevPortName = "";
	        
	    	for(String tempTwo : temp){
	    		keyParts = tempTwo.split("\\.");
	    		if (!prevPortName.equals(keyParts[0])) {
	                SeaPortProgram.displayArea.append("Port : " + keyParts[0]);
	            }
	    		String[] header = new String[] {"Id", "Name, Number of skills"};
	    		SeaPortProgram.dtm.setColumnIdentifiers(header);
	    		SeaPortProgram.dtm.addRow(new Object[]{name, index, temp, skillNum.get(tempTwo)});
	            prevPortName = keyParts[0];
	    	}
	    }//End of the refreshJobPanel method here 
	    
	    
	    public String toString () {
	    	String sr = "Job: " + super.toString() + " " + requirements;
	        return sr;
	      } //end method toString
	    
}//End of the job class here























