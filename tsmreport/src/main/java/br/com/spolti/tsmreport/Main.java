/*
	Copyright 2014 Filippe Costa Spolti

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package br.com.spolti.tsmreport;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.spolti.tsmreport.functions.ExecuteCommand;
import br.com.spolti.tsmreport.functions.SendEmail;
import br.com.spolti.tsmreport.functions.VerifyParameters;
import br.com.spolti.tsmreport.security.Crypto;

/**
 * @author spolti@hrstatus.com.br
 */

public class Main {
	
    public Main() { 
    	//////////////////////////////////////////////////////////////////////////////////
    	//Loading log4j configurations
        ClassLoader classLoader =  Thread.currentThread().getContextClassLoader();  
        PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));  
   }
	
	public static void main( String[] args ) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
    	//Crypto pass = new Crypto();
    	//////////////////////////////////////////////////////////////////////////////////
    	//Se o primeiro parametro for encode entrará nesta função e sairá logo em seguida
    	if (args.length > 0 && args[0].equals("encode")){
    		try{
    			System.out.println("Your encoded passowrd for " + args [1] + " is " + Crypto.encode(args[1]));
    			Logger.getLogger(br.com.spolti.tsmreport.Main.class).info("Your encoded passowrd for " + args [1] + " is " + Crypto.encode(args[1]));
    			System.exit(0);
    		}catch(Exception e){
    			System.out.println("Usage: java -jar tsmreports.jar encode MyPassword");
    			Logger.getLogger(br.com.spolti.tsmreport.Main.class).error("Usage: java -jar tsmreports.jar encode MyPassword");
    			System.exit(0);
    		}
    	}
    	//////////////////////////////////////////////////////////////////////////////////
       	//Entering in the Main program
    	//Properties Loading
    	Properties props = new Properties();
    	ClassLoader classLoader = ClassLoader.getSystemClassLoader();  
    	
    	try {
    		props.load(classLoader.getResourceAsStream("application.properties"));
    	}catch (IOException ex){
    		Logger.getLogger(br.com.spolti.tsmreport.Main.class).error(ex);
    	}
    	//Load properties complete.    	
    	//////////////////////////////////////////////////////////////////////////////////
    	//Validating all parameters
    	VerifyParameters omg = new VerifyParameters();
    	omg.checkParms(props.getProperty("tsmserver.binary.path"), props.getProperty("tsmserver.username"), props.getProperty("tsmserver.password"),
    			props.getProperty("tsmserver.report.command"), props.getProperty("tsmreport.stmp.host"), props.getProperty("tsmreport.smtp.mailfrom"),
    			props.getProperty("tsmreport.smtp.subject"), props.getProperty("tsmreport.smtp.message"), props.getProperty("tsmreport.smtp.rcptTo"),
    			props.getProperty("tsmreport.smtp.attachment.file"), props.getProperty("tsmreport.smtp.attachment"));
    	//////////////////////////////////////////////////////////////////////////////////
    	//Running the command to get the backups information
    	ExecuteCommand tsmcli = new ExecuteCommand();
    	SendEmail sendMail = new SendEmail();
    	try {
    		tsmcli.ExecuteTSMCommand(props.getProperty("tsmserver.binary.path"), props.getProperty("tsmserver.username"), 
					props.getProperty("tsmserver.password"), props.getProperty("tsmserver.report.command"));
		} catch (IOException e) {
			Logger.getLogger(br.com.spolti.tsmreport.Main.class).error(e);
		} catch (InterruptedException e) {
			Logger.getLogger(br.com.spolti.tsmreport.Main.class).error(e);
		} 	
    	//////////////////////////////////////////////////////////////////////////////////
    	//Sending the email with the report
    	try {
    		if (props.getProperty("tsmreport.smtp.attachment").equals("true")){
    			Logger.getLogger(br.com.spolti.tsmreport.Main.class).info("Trying to send email with attachment");
    			sendMail.senderWithAttachment(props.getProperty("tsmreport.smtp.mailfrom"), props.getProperty("tsmreport.smtp.subject"),
    					props.getProperty("tsmreport.smtp.message"), props.getProperty("tsmreport.smtp.rcptTo"), props.getProperty("tsmreport.stmp.host"),
    					props.getProperty("tsmreport.smtp.attachment.file"));
    		} else if ((props.getProperty("tsmreport.smtp.attachment").equals("false")) || (props.getProperty("tsmreport.smtp.attachment").equals(""))){
    			Logger.getLogger(br.com.spolti.tsmreport.Main.class).info("Trying to send html email");
    			sendMail.senderWithoutAttachment(props.getProperty("tsmreport.smtp.mailfrom"), props.getProperty("tsmreport.smtp.subject"),
    					props.getProperty("tsmreport.smtp.message"), props.getProperty("tsmreport.smtp.rcptTo"), props.getProperty("tsmreport.stmp.host"),
    					props.getProperty("tsmreport.smtp.attachment.file"));
    		}

		} catch (UnknownHostException e) {
			Logger.getLogger(br.com.spolti.tsmreport.Main.class).error(e);
		}      
    }
}