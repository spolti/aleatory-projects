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

import java.net.UnknownHostException;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;

import br.com.spolti.tsmreport.fileops.FileOperations;

/**
 * @author spolti@hrstatus.com.br
 */

public class SendEmail {

	public void senderWithAttachment(String mailSender, String Subject, String msg, String dests, String smtpHost, String outputFile) throws UnknownHostException {
		
		FileOperations readFile = new FileOperations();
		MultiPartEmail email = new MultiPartEmail();
		EmailAttachment attachment = new EmailAttachment();

		String dest_temp = dests;
		String[] dest = dest_temp.split(",");
		
		try {

			email.setHostName(smtpHost); 
			for (int i = 0; i < dest.length; i++) {
				email.addTo(dest[i]); 
			}

			attachment.setPath(outputFile);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription(outputFile);
			attachment.setName("tsmreport.txt");
			email.setFrom(mailSender); 
			email.setSubject(Subject);
			Logger.getLogger(br.com.spolti.tsmreport.functions.SendEmail.class).info(readFile.format2html(outputFile));
			msg = msg + " \n" + readFile.format2html(outputFile);
			msg = msg + "Developed by Spolti (spolti@hrstatus.com.br), if you wanna contribute please do not hesitate. Visit https://github.com/spolti/tsmreport.";
			email.setMsg(msg);
			email.getMailSession().getProperties().setProperty("mail.smtp.localhost", "localhost");
			email.attach(attachment);
			email.send();

			Logger.getLogger(br.com.spolti.tsmreport.functions.SendEmail.class).info("----> Email sent.");

		} catch (Exception e) {
			Logger.getLogger(br.com.spolti.tsmreport.functions.SendEmail.class).error("----> Email not sent.");
			Logger.getLogger(br.com.spolti.tsmreport.functions.SendEmail.class).error(e);
		}
	}
	
	public void senderWithoutAttachment(String mailSender, String Subject, String msg, String dests, String smtpHost, String outputFile) throws UnknownHostException {
		
		FileOperations readFile = new FileOperations();
		MultiPartEmail email = new MultiPartEmail();

		String dest_temp = dests;
		String[] dest = dest_temp.split(",");
		
		try {

			email.setHostName(smtpHost); 
			for (int i = 0; i < dest.length; i++) {
				email.addTo(dest[i]); 
			}

			email.setFrom(mailSender); 
			email.setSubject(Subject);
			Logger.getLogger(br.com.spolti.tsmreport.functions.SendEmail.class).info(readFile.format2html(outputFile));
			msg = msg + "\n\n\n" + readFile.format2html(outputFile);
			msg = msg + "Developed by Spolti (filippespolti@gmail.com), if you wanna contribute please do not hesitate. Visit https://github.com/spolti/tsmreport.";
			email.setMsg(msg);
			email.getMailSession().getProperties().setProperty("mail.smtp.localhost", "localhost");
			email.send();

			Logger.getLogger(br.com.spolti.tsmreport.functions.SendEmail.class).info("----> Email sent.");

		} catch (Exception e) {
			Logger.getLogger(br.com.spolti.tsmreport.functions.SendEmail.class).error("----> Email not sent.");
			Logger.getLogger(br.com.spolti.tsmreport.functions.SendEmail.class).error(e);
		}
	}
}
