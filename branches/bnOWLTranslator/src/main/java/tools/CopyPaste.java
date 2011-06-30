package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CopyPaste {
	
	public CopyPaste() {
		
	}

/**
 * Method to copy a file.
 * @param addressFile
 * @param addressDestination
 */
	public void copyFile(String addressFile, String addressDestination){
		FileInputStream fIn; 
		FileOutputStream fOut; 
		FileChannel fIChan, fOChan; 
		long fSize; 
		MappedByteBuffer mBuf; 

		try { 
			fIn = new FileInputStream(addressFile); 
			fOut = new FileOutputStream(addressDestination); 

			fIChan = fIn.getChannel(); 
			fOChan = fOut.getChannel(); 

			fSize = fIChan.size(); 

			mBuf = fIChan.map(FileChannel.MapMode.READ_ONLY, 0, fSize); 

			fOChan.write(mBuf); // this copies the file 

			fIChan.close(); 
			fIn.close(); 

			fOChan.close(); 
			fOut.close(); 
			
			//delete de initial file
			File f = new File(addressFile);
			if(f.exists()) {
				f.delete();
			}
			
		} catch (IOException exc) { 
			System.out.println(exc); 
			
		} catch (ArrayIndexOutOfBoundsException exc) { 
			System.out.println("Usage: Copy from to"); 
			 
		} 
	} 

}
