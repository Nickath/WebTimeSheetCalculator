package org.nick.pdf.templates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class PdfTemplates{

	
	 public static String mainCss;
 	 public static String startingHtmlTag = "<html><body>";
 	 public static String endingHtmlTag = "</html></body>";
 	 public static String startingCssTag = "<head><style>";
 	 public static String endingCssTag = "</style></head>";
	 
     public static String statisticsHeader = "<h1 style='text-align:center'> User Statistics </h1>";
	
	public static String readMainCss(HttpServletRequest req, HttpServletResponse res) {
		 String fileName = "main.css";
		 String fullPath = req.getServletContext().getRealPath("/resources/css/"+fileName);    
	     return readTextFromFile(fullPath);
	}
	
	public static String readTextFromFile(String fileName) {
        String result = null;
        String line = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            String text = "";
            while((line = bufferedReader.readLine()) != null) {
                text += "\n"+line;
            }   

            result = text;
            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            result = "Unable to open file";                
        }
        catch(IOException ex) {
            result = "Error reading file";
        }
        return result;
    }
	
	
	

}
