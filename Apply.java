
import java.io.*;
import java.util.Base64;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

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
        try {
            URL url = new URL("http://localhost:8080/apply");
            String charset = "UTF-8";

            // Set up the parameters
            String postData = "resume=" + URLEncoder.encode(base64Resume, charset);

            // Set up the connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true); // Sets up the request to be POST automatically
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type",
                                    "application/x-www-form-urlencoded;charset=" + charset);
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());

            // Kick off the request and read the response
            // As soon as we start grabbing data from the response, the HTTP request will be made
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String currLine;      // Individual line
            String response = ""; // Overall Response
            while ((currLine = br.readLine()) != null) {
                response = response + currLine;
            }
            br.close();

            // Print out the response
            System.out.println(conn.getResponseCode());
            System.out.println(response.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
