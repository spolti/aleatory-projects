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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

import br.com.spolti.tsmreport.security.Crypto;

/**
 * @author spolti@hrstatus.com.br
 */

public class ExecuteCommand {

	public String ExecuteTSMCommand(String binPath, String user, String pass, String command) throws IOException, InterruptedException {
		Process p = null;
		String out = null;
		String s = null;
		
		/////////////////////////////////////////////////////////////////////////////////
		//Descripting tsm password
		String decodedPass = null;
		try {
			decodedPass = String.valueOf(Crypto.decode(pass));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		//////////////////////////////////////////////////////////////////////////////////
		
		String formatedCommand = "cmd /c cd " + binPath + " && dsmadmc.exe";
		//formatedCommand = formatedCommand + "" + binPath;
		formatedCommand = formatedCommand + " -ID=" + user;
		formatedCommand = formatedCommand + " -PA=" + decodedPass;
		formatedCommand = formatedCommand + " " + command + " > save.out" ;

		try {
			p = Runtime.getRuntime().exec(formatedCommand);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((s = reader.readLine()) != null) {
				out += s;

				// i dont know why, but the cmd command line always returns null before
				// the result, like this: nullXXXXXX ......
				if (out.startsWith("n")) {
					String temp = out.replaceAll("null", "");
					out = temp;
				}
			}
			Logger.getLogger(br.com.spolti.tsmreport.functions.ExecuteCommand.class).info("Command executed sucessfully.");
		}catch (Exception e){
			Logger.getLogger(br.com.spolti.tsmreport.functions.ExecuteCommand.class).error(e);
		}

		return out;
	}
}