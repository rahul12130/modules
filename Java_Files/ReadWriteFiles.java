import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteFiles {

    private static final String DEVICE_ID = "DEVICE_ID";
    private static final String DEVICE_IP = "DEVICE_IP";
    private static final String EQUAL = "=";
    private static final String PORT = "PORT";
    private static final String USER_NAME = "USER_NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String DEVICE_TYPE = "DEVICE_TYPE";
    private static final String SERIAL = "SERIAL";
    private static final String MANUFACTURER = "MANUFACTURER";
    private static final String HARDWARE_VERSION = "HARDWARE_VERSION";
    private static final String SOFTWARE_VERSION = "SOFTWARE_VERSION";
    private static final String DEVICE_ADDED = "DEVICE_ADDED";
    private static final String DEVICE_ID_FORMAT = "netconf:%s:830";
    public String readFile(String fileName) throws IOException {
        StringBuilder returnString = new StringBuilder("");
        System.out.println("Insida readFile");
        try {
            File file = new File("/home/amantya/modules/Java_Files/readMe.json");
            BufferedReader reader = new BufferedReader(new FileReader("/home/amantya/modules/Java_Files/readMe.json"));
            String contentLine = reader.readLine();
            while (contentLine != null) {
                returnString.append(contentLine);
                returnString.append("\n");
                contentLine = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returnString.toString();
    }
public void updateDeviceDetails(String filePath, String DEVICE_IP, String PORT, String USER_NAME, String PASSWORD, String DEVICE_NAME, String DEVICE_TYPE, String SERIAL, String MANUFACTURER, String HARDWARE_VERSION, String SOFTWARE_VERSION, boolean DEVICE_ADDED) throws IOException {

    System.out.println("Inside updateDeviceDetails");

   String contents =DEVICE_ID + EQUAL + String.format(DEVICE_ID_FORMAT, DEVICE_IP) + "\n" +
                    DEVICE_IP + EQUAL + DEVICE_IP + "\n" +
                    PORT + EQUAL + PORT + "\n" +
                    USER_NAME + EQUAL + USER_NAME + "\n" +
                    PASSWORD + EQUAL + PASSWORD + "\n" +
                    DEVICE_NAME + EQUAL + DEVICE_NAME + "\n" +
                    DEVICE_TYPE + EQUAL + DEVICE_TYPE + "\n" +
                    SERIAL + EQUAL + SERIAL + "\n" +
                    MANUFACTURER + EQUAL + MANUFACTURER + "\n" +
                    HARDWARE_VERSION + EQUAL + HARDWARE_VERSION + "\n" +
                    SOFTWARE_VERSION + EQUAL + SOFTWARE_VERSION + "\n" +
                    DEVICE_ADDED + EQUAL + (DEVICE_ADDED ? "true" : "false") + "\n" +
                    "#####";

    BufferedWriter writer = null;
    try {
        writer = new BufferedWriter(new FileWriter("/home/amantya/5gDevices/devices.txt", true));
        writer.write(contents);
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

}
