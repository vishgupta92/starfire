package es.upm.dit.gsi.commune.agent.vlcLauncherAgent;

/**
 * <p>
 * VlcLauncherPlan: Opens VLC with an rtsp url and creates an instance of session in the ontology
 * </p>
 * 
 * @version 0.1 (04/2011)
 * @author Jaime de Miguel (GSI-ETSIT-UPM)
 * 
 */

import java.io.FileReader;
import java.net.InetAddress;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 

import jadex.bdi.runtime.IBelief;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

import edu.stanford.smi.protegex.owl.*;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;

import communeOntology.MyFactory;

public class VlcLauncherPlan extends Plan implements ActionListener{
//public class VlcLauncherPlan extends Plan {

	
	private static final long serialVersionUID = -597969293105340110L;

	private String serverIp ="";
	private String file="";
	private String port="";
	protected JButton button;
	protected JTextField textField1, textField2, textField3;
	protected JFrame frame;
	private boolean windowCreated = false;
	
	public VlcLauncherPlan() {
		getLogger().info("VLCPlan: Created: " + this);

	}
	
	public void body() {
		getLogger().info("VLCPlan: Plan begins ");
		while(true){
			createWindow();
			waitFor(10000);
			while(windowCreated)
				waitFor(10000);
		}
	}
	
	/**
	* When the button is pressed, submits the server ip, port and resource or the rtsp session. 
	* Checks that both aren't equal to empty string and the port, if written is a number
	*/
	public void actionPerformed(ActionEvent e) {
		synchronized(this){
        if ("submit".equals(e.getActionCommand())) {
        	serverIp = textField1.getText();
        	port = textField2.getText();
        	file = textField3.getText();
        	
        	if(!serverIp.equals("") && !file.equals("")){
        		try{
        			InetAddress.getByName(serverIp);
        			if(file.contains(".")){
        		        try {
            				if(!port.equals(""))
            					Integer.parseInt(port);
            				IBelief server = getBeliefbase().getBelief("serverIP"); //save the server ip as a believe
            				server.setFact(serverIp);
            				IBelief file = getBeliefbase().getBelief("file"); //save the file name as a believe
            				file.setFact(file);      				
            				openVlc();
                			frame.setVisible(false); //Close the window
                			frame.dispose();
					windowCreated = false;
                			openSessionInstance();
        		        } catch (NumberFormatException ex) {
        		        	getLogger().warning("VLCPlan: Port not a number, try again");
        		        }

        			}else getLogger().warning("VLCPlan: Write the file with the file extension");
        		}catch(Exception e1){
        			getLogger().warning("VLCPlan: Unknown host, try again");
        		}
        	}
        }
        }
	}
	
	/**
	 * Opens the VLC player with the specified URL
	 * 
	 */
	private void openVlc(){
		try {
			if (port.equals("")) port ="554";
			String url = "rtsp://"+serverIp+":"+port+"/"+file;
			getLogger().info("VLCPlan: Opening VLC with url: "+url);
			Runtime.getRuntime().exec("vlc "+url);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	/**
	 * Creates the window to put the server ip and file  
	 * 
	 */
	private void createWindow(){
		  //Create and set up the window.
		  frame = new JFrame("Set the video URL"); 
		  frame.setSize(100,100);
		  frame.setResizable(false);
		  frame.setAlwaysOnTop(true);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		  Container pane=frame.getContentPane();
		  pane.setLayout(new GridLayout(4,2));
		  
		  JLabel textLabel = new JLabel("Server Ip:"); 
		  textLabel.setPreferredSize(new Dimension(200, 20)); 
		  frame.getContentPane().add(textLabel); 
			  
		  textField1 = new JTextField(10);
		  textField1.addActionListener(this);
		  pane.add(textField1);
		  
		  textLabel = new JLabel("Server port (default 554):"); 
		  frame.getContentPane().add(textLabel); 
			  
		  textField2 = new JTextField(10);
		  textField2.addActionListener(this);
		  pane.add(textField2);
		  
		  
		  textLabel = new JLabel("Video name:"); 
		  frame.getContentPane().add(textLabel); 
			  
		  textField3 = new JTextField(10);
		  textField3.addActionListener(this);
		  pane.add(textField3);
		  
		  JButton button=new JButton("Open video");
		  button.setMnemonic(KeyEvent.VK_E);
		  button.setActionCommand("submit");
		  button.addActionListener(this);
		  pane.add(button);
		  
		  frame.pack(); 
		  frame.setVisible(true); 
		  windowCreated = true;
	}

	
	/**
	 * 
	 * Crates the rtp session instance of the ontology.
	 */
	private void openSessionInstance(){
		getLogger().info("VlcPlan: Creating session instance");
		OWLModel owlModel;
		try {
	//	    String uri = System.getProperty("user.dir")+"/ontologies/P2PDiagnosis.owl";
	//		String uri = "/home/j.miguel/workspace/commune/vlcLauncherAgent/ontologies/P2PDiagnosis.owl";
			String uri = "plugins/edu.stanford.smi.protegex.owl/P2PDiagnosis.owl";

			FileReader owlFile;
			try {
				owlFile = new FileReader(uri);
				owlModel = ProtegeOWL.createJenaOWLModelFromReader(owlFile);

//			    OWLNamedClass rtpSessionClass = owlModel.getOWLNamedClass("http://www.owl-ontologies.com/P2PDiagnosis.owl#RTPSession");
//				rtpSessionClass.createInstance("rtpSession");
				MyFactory myFactory = new MyFactory(owlModel);
//				myFactory.createRTPSession("rtpSession");
//				getLogger().info("VlcPlan: Session instance created");
//				getLogger().info("VlcPlan: Start monitoring");
//				IGoal searchRTP = createGoal("monitorizeRTP");
//				dispatchSubgoalAndWait(searchRTP);	
				


			} catch (Exception e) {
				e.printStackTrace();
			}	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
