package com.aleatory.claim_free_book.utils.Properties;

import java.io.*;
import java.util.Properties;

/**
 * Created by fspolti on 3/30/16.
 */
public class LoadProperties {

    private static final String FILE_NAME = "claim-free-book.properties";
    private static final String PATH = System.getProperty("user.home");
    private static final String FILE_LOCATION = PATH + "/" + FILE_NAME;
    private static final String CREDENTIALS = System.getProperty("credentials", "");
    private static File file = new File(FILE_LOCATION);

    /*
    * Load the credentials from properties file.
    */
    public static Properties load() {

        Properties properties = new Properties();

        if (!checkPropertiesFile()) {
            createPropsFile();
        } else if (checkPropertiesFile() && !CREDENTIALS.isEmpty()){
            System.out.println("Overriding the properties file...");
            file.delete();
            createPropsFile();
        }

        try {
            FileInputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return properties;
    }

    /*
    * Verifies if the properties file with packtpub credentials exists.
    */
    private static boolean checkPropertiesFile() {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /*
    * Create a new properties file if it not exist.
    */
    private static void createPropsFile() {

        FileOutputStream fos;
        OutputStreamWriter osw = null;

        if (CREDENTIALS.isEmpty()) {
            System.out.println("Properties File not foud. Please supply the username and password.");
            System.out.println("java -jar -Dcredentials=myemail@example.com:myPassword packtpub-claim-free-book.jar");
            System.exit(0);

        } else {
            System.out.println("Credentials received [" + CREDENTIALS + "] - Trying to create a new properties file [" + FILE_LOCATION + "].");
            final String[] creds = CREDENTIALS.split(":");

            try {
                fos = new FileOutputStream(file);
                osw = new OutputStreamWriter(fos);
                osw.write("username=" + creds[0] + "\n");
                osw.write("password=" + creds[1]);
            } catch (IOException ioe) {
                ioe.printStackTrace();

            } finally {
                System.out.println("File created.");
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}