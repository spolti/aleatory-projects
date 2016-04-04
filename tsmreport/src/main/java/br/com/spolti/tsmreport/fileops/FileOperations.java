package br.com.spolti.tsmreport.fileops;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileOperations {

	
	public String format2html(String fileLocation) throws IOException{
		String content = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));   
	        
	        while(br.ready()){   
	        content = content + br.readLine()+"\n";
//	        +"<br>";
//	        
//	        if (content.contains("\t")){
//	        	Logger.getLogger(br.com.spolti.tsmreport.fileops.FileOperations.class).info("Possui tabulaçaõ, alterando por tabulação em linguagem html.");
//	        }

	        }   
	        br.close(); 
	        
		} catch (Exception e){
			Logger.getLogger(br.com.spolti.tsmreport.fileops.FileOperations.class).error(e);
		}
		return content;
	}

//	public static void main(String args[]) throws IOException{
//		
//		FileOperations open = new FileOperations();
//		open.format2html("");
//		
//	}
}