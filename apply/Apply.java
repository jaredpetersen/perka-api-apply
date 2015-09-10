
import java.io.*;
import java.util.Base64;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Apply for a job at Perka via their API
 * @author Jared Petersen
 * @license MIT
 * @version 1.0.0
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
     * Grab the resume and convert it to a Base64 string
     * @return The resume as a Base64 encoded string
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
     * @param base64Resume Resume as a Base64 string
     */
     private static void applyViaAPI(String base64Resume) {
        try {
        	// API Endpoint
        	URL url = new URL("https://api.perka.com/1/communication/job/apply");

            // Set up the parameters
            String firstName   = "Jared";
            String lastName    = "Petersen";
            String email       = "jpetersen11@wou.edu";
            String positionID  = "GENERALIST";
            String explanation = "I wrote a small java program that grabbed my resume, " +
                                 "converted it to a Base64 string, and then made a POST request " +
                                 "to your API endpoint with all of my information. I wanted to " +
                                 "make sure that everything was being sent off properly before " +
                                 "applying, so I wrote a tiny Node.js API to query against. " +
                                 "If you would like, you can check out both programs on GitHub " +
                                 "via https://github.com/jaredpetersen/perka-api-apply.";
            // You have to send each item in the projects array separately in order for the API
            // to register it as an array
            String projects0   = "https://github.com/jaredpetersen";
            String projects1   = "http://www.api.tangr.am";
            String projects2   = "https://github.com/CDKGlobal/cd-performance-promotion";
            String source      = "I saw your company on the list of companies that were going to " +
                                 "be at the TechFestNW conference in Portland, OR.";

            // Get the JSON ready
            String postData = "{\"first_name\": \"%s\"," +
                               "\"last_name\": \"%s\"," +
                               "\"email\": \"%s\"," +
                               "\"position_id\": \"%s\"," +
                               "\"explanation\": \"%s\"," +
                               "\"projects\": [\"%s\", \"%s\", \"%s\"]," +
                               "\"source\": \"%s\"," +
                               "\"resume\": \"%s\"}";
            // Put the proper values into the JSON
            postData = String.format(postData, firstName, lastName, email, positionID,
                                     explanation, projects0, projects1, projects2, source,
                                     base64Resume);

            // Set up the connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true); // Sets up the request to be POST automatically
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");

            // Begin setting up the data to be sent
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());

            // Kick off the request and read the response
            // As soon as we start grabbing data from the response, the HTTP request will be made
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String currLine;      // Individual line of the response
            String response = ""; // Overall Response
            while ((currLine = br.readLine()) != null) {
                response = response + currLine;
            }
            br.close(); // Shut it down, we're done here

            // Print out the response
            System.out.println(conn.getResponseCode());
            System.out.println(response.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
