
import java.io.*;
import java.util.Base64;

/**
 * Apply for a job at Perka via their API
 * @author Jared Petersen
 * @license MIT
 * @version 0.0.1
 */
public class Apply {
    
	/**
	 * Main method
	 * @param args
	 */
    public static void main (String[] args) {
		applyViaAPI(getBase64Resume());
	}
	
    /**
     * Grab the resume and convert it to a base64 string
     * @return The resume as a base64 encoded string
     */
	private static String getBase64Resume() {
		// Setup the output string
		String outputString = null;
		try {
			// Read in all of the bytes from the PDF
			File resume = new File("./src/resume.pdf");
			// Open a stream to the file
			FileInputStream fis = new FileInputStream(resume);
			// Set up the byte array
			byte[] byteContent = new byte[(int)resume.length()];
			// Read the file into the byte array
			fis.read(byteContent);
			// Close out the stream
			fis.close();
			// Convert the file bytes to Base64
			outputString = Base64.getEncoder().encodeToString(byteContent);
		} 
		catch (Exception e) {
			// Something went wrong, just error out
			e.printStackTrace();
		}
		return outputString;
	}
	
	/**
	 * Send the data to the Perka API
	 */
	private static void applyViaAPI(String base64Resume) {
	    // TODO Add functionality for making the POST request
	    System.out.println(base64Resume);
	}
	
}
