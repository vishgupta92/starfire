package es.upm.dit.gsi.starfire.diagnosisAgent;

/**
 * <p>
 * VlcLauncherPlan: Opens VLC with an rtsp url and creates an instance of session in the ontology
 * </p>
 * 
 * @version 0.1 (04/2011)
 * @author Jaime de Miguel (GSI-ETSIT-UPM)
 * 
 */

import jadex.bdi.runtime.IBelief;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;

import communeOntology.MyFactory;

public class VlcLauncherPlan extends Plan /*implements ActionListener*/ {

	private static final long serialVersionUID = -597969293105340110L;
	
	private static final String DIR_IP = "192.168.0.197";
	private static final String VIDEO = "bunny.ts";
	
	private boolean isLinuxServer = true;

//	private String serverIp = "";
//	private String file = "";
//	private String port = "";
//	protected JButton button;
//	protected JTextField textField1, textField2, textField3;
//	protected JFrame frame;
//	private boolean windowCreated = false;

	public VlcLauncherPlan() {
		getLogger().info("VLCPlan: Created: " + this);

	}

	public void body() {
		getLogger().info("VLCPlan: Plan begins");
//		while (true) {
//			createWindow();
//			waitFor(10000);
//			while (windowCreated)
//				waitFor(10000);
//		}
		startVLC(DIR_IP, "", VIDEO);
		
		openSessionInstance();
		
		IGoal searchRTP = createGoal("monitorizeRTP");
		dispatchSubgoalAndWait(searchRTP);
		getLogger().info("VlcPlan: Start monitoring request has been done");
	}

//	/**
//	 * When the button is pressed, submits the server ip, port and resource or the rtsp session. Checks that both aren't equal to empty string and the port, if written is a number
//	 */
//	public void actionPerformed(ActionEvent e) {
//		if ("submit".equals(e.getActionCommand())) {
//			serverIp = textField1.getText();
//			port = textField2.getText();
//			file = textField3.getText();
//
//			if (!serverIp.equals("") && !file.equals("")) {
//				try {
//					getLogger().warning("VLCPlan: A");
//					//InetAddress.getByName(serverIp);
//					if (file.contains(".")) {
//						try {
//							//if (!port.equals(""))
//							//	Integer.parseInt(port);
//							
//							startVLC(serverIp, port, file);
//							
//							frame.setVisible(false); //Close the window
//							frame.dispose();
//							windowCreated = false;
//							getLogger().warning("VLCPlan: The window is closed");
//						} catch (NumberFormatException ex) {
//							getLogger().warning("VLCPlan: Port not a number, try again");
//						}
//
//					} else
//						getLogger().warning("VLCPlan: Write the file with the file extension");
//				} catch (Exception e1) {
//					getLogger().warning("VLCPlan: Unknown host, try again");
//				}
//			}
//		}
//			
//	}
	
	/**
	 * Submits the server ip, port and resource or the rtsp session. Checks that both aren't equal to empty string and the port, if written is a number
	 */
	public void startVLC(String serverIp, String port, String file) {
		IBelief server = getBeliefbase().getBelief("serverIP"); //save the server ip as a believe
		server.setFact(serverIp);
		getLogger().warning("VLCPlan: Server IP: "+serverIp);
		
		IBelief beliefFile = getBeliefbase().getBelief("file"); //save the file name as a believe
		beliefFile.setFact(file);
		getLogger().warning("VLCPlan: File: "+file);
		
		openVlc(serverIp, port, file);
		getLogger().warning("VLCPlan: Vlc is opened");
	}

	/**
	 * Opens the VLC player with the specified URL
	 * 
	 */
	private void openVlc(String serverIp, String port, String file) {
		try {
			String url = null;
			if(isLinuxServer) {
				if (port.equals(""))
					port = "8554";
				url = "rtsp://" + serverIp + ":" + port + "/" + file;
			} else {
				url = "rtsp://" + serverIp + "/" + file;
			}
			getLogger().info("VLCPlan: Opening VLC with url: " + url);
			Runtime.getRuntime().exec("vlc " + url);
			getLogger().info("VLCPlan: VLC is opened");
		} catch (Exception e) {
			getLogger().info("VLCPlan: VLC couldn't be opened");
		}
	}

//	/**
//	 * Creates the window to put the server ip and file
//	 * 
//	 */
//	private void createWindow() {
//		//Create and set up the window.
//		frame = new JFrame("Set the video URL");
//		frame.setSize(100, 100);
//		frame.setResizable(false);
//		frame.setAlwaysOnTop(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		Container pane = frame.getContentPane();
//		pane.setLayout(new GridLayout(4, 2));
//
//		JLabel textLabel = new JLabel("Server Ip:");
//		textLabel.setPreferredSize(new Dimension(200, 20));
//		frame.getContentPane().add(textLabel);
//
//		textField1 = new JTextField(10);
//		textField1.addActionListener(this);
//		pane.add(textField1);
//
//		textLabel = new JLabel("Server port (default 554):");
//		frame.getContentPane().add(textLabel);
//
//		textField2 = new JTextField(10);
//		textField2.addActionListener(this);
//		pane.add(textField2);
//
//		textLabel = new JLabel("Video name:");
//		frame.getContentPane().add(textLabel);
//
//		textField3 = new JTextField(10);
//		textField3.addActionListener(this);
//		pane.add(textField3);
//
//		JButton button = new JButton("Open video");
//		button.setMnemonic(KeyEvent.VK_E);
//		button.setActionCommand("submit");
//		button.addActionListener(this);
//		pane.add(button);
//
//		frame.pack();
//		frame.setVisible(true);
//		windowCreated = true;
//	}

	/**
	 * 
	 * Crates the rtp session instance of the ontology.
	 */
	private void openSessionInstance() {
		getLogger().info("VlcPlan: Creating session instance");
		
		JenaOWLModel ontology = (JenaOWLModel) getBeliefbase().getBelief("ontology").getFact();
		MyFactory myFactory = new MyFactory(ontology);
		
		myFactory.createRTPSession("rtpSession");
		getLogger().info("VlcPlan: Session instance created");
	}
}
