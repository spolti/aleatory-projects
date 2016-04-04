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

package br.com.spolti.tsmreport.functions;

import org.apache.log4j.Logger;

/**
 * @author spolti@hrstatus.com.br
 */

public class VerifyParameters {

	public void checkParms(String binPath, String username, String password, String command, String smtpHost, String mailFrom, String subject, String message, String rcptTo, String attachment, String sendAttachment){
    	
		if (binPath.equals(null)){
			Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmserver.binary.path parameter not found.");
			System.exit(0);
    	}
    	if (username.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmserver.username parameter not found.");
    		System.exit(0);
    	}
    	if(password.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmserver.password parameter not found.");
    		System.exit(0);
    	}
    	if(command.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmserver.report.command parameter not found.");
    		System.exit(0);
    	}
    	if(smtpHost.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmreport.stmp.hos parameter not found.");
    		System.exit(0);
    	}
    	if(mailFrom.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmreport.smtp.mailfrom parameter not found.");
    		System.exit(0);
    	}
    	if(subject.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmreport.smtp.subject parameter not found.");
    		System.exit(0);
    	}
    	if(message.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmreport.smtp.message parameter not found.");
    		System.exit(0);
    	}
    	
    	if(rcptTo.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmreport.smtp.rcptTo parameter not found.");
    		System.exit(0);
    	}
    	if(sendAttachment.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmreport.smtp.attachment parameter not found.");
    		System.exit(0);
    	}
    	if(attachment.equals(null)){
    		Logger.getLogger(br.com.spolti.tsmreport.functions.VerifyParameters.class).fatal("tsmreport.smtp.attachment.file parameter not found.");
    		System.exit(0);
    	}
	}
}