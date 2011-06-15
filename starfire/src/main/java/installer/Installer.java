package installer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Installer {
	
	public static boolean isLinux = false;
	public static String MAVEN_LOCAL_PATH = "C:\\Productos\\apache-maven-2.2.1\\bin\\mvn.bat";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("./mvn_lib/maven_dependencies.bat"));
		    String command;
		    
		    File file = new File("./mvn_lib");
		    Runtime runtime = Runtime.getRuntime();
		   
		    
		    while ((command = reader.readLine()) != null) {
		    	Process process;
		    	if(isLinux) {
		    		process = runtime.exec(command, null, file);
		    	} else {
		    		process = runtime.exec(MAVEN_LOCAL_PATH+command.replaceFirst("mvn",""), null, file);
		    	}
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while((line = in.readLine()) != null) {
					System.out.println(line);
				}
		    }
		    reader.close();	
		}
		// Catches any error conditions
		catch (IOException e)
		{
			System.err.println ("Unable to read from file");
			System.exit(-1);
		}
	}

}
