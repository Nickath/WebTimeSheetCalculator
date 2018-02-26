package org.nick.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileHandler {

	private static int hourDivisor = 60;
	
	public File readFile(String path,CommonsMultipartFile file) {
		String filename=file.getOriginalFilename(); 
		System.out.println(file.getSize());
		
		
		byte[] bytes = file.getBytes();
        File file2 = new File("C:\\Users\\NICK\\Desktop\\TimeSheet.xlsx");
 
        try {
 
            OutputStream os = new FileOutputStream(file2);
            os.write(bytes);
            System.out.println("Write bytes to file.");
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
     
		return file2;
	}
	
	public void makeCalculations(File myFile) throws IOException {
		try {
			
			XSSFWorkbook myTimeSheet = new XSSFWorkbook (myFile);
			XSSFSheet mySheet = myTimeSheet.getSheetAt(0);
			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = mySheet.iterator();
			//iterate through the lines
			int days = 0;
			ArrayList<Integer> daySums = new ArrayList<>();
			ArrayList<Integer> inSums  = new ArrayList<>();
			ArrayList<Integer> outSums = new ArrayList<>();
			while (rowIterator.hasNext()) {
				days++;
                Row row = rowIterator.next();
			    
                Iterator<Cell> cellIterator = row.cellIterator();
                String col1 = "";
                String col2 = "";
                boolean first = true;
                while (cellIterator.hasNext()) {
                	Cell cell = cellIterator.next();
                	
                	DataFormatter dataFormatter = new DataFormatter();
                	String cellStringValue = dataFormatter.formatCellValue(cell);
                	//System.out.println(cellStringValue+"\t");
                	
                	if(first) {
                		col1 = cellStringValue;
                	}
                	else {
                		col2 = cellStringValue;
                	}
                	first = false;
                }
                

                daySums.add(calculateDifference(col1,col2));
                inSums.add(Integer.parseInt(col1.substring(0,col1.indexOf(":")))*60 + Integer.parseInt(col1.substring(col1.indexOf(":")+1,col1.length())));
                outSums.add(Integer.parseInt(col2.substring(0,col2.indexOf(":")))*60 + Integer.parseInt(col2.substring(col2.indexOf(":")+1,col2.length())));
			}
			int mean = daySums.stream().mapToInt(Integer::intValue).sum()/daySums.size();
			int meanHours= mean/hourDivisor;
			int meanMinutes= mean%hourDivisor;
			System.out.println("This month you have worked **"+ daySums.size()+"** days\n");
			System.out.println("Total Mean Average: " +mean +" ("+meanHours+":"+meanMinutes+")");
			if((((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60)<10) {
				System.out.println("Mean Average coming time: "+ (((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":0"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
				
			}
			else {
				System.out.println("Mean Average coming time: "+ (((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
				
			}
			if((((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60)<10)
			{
				System.out.println("Mean Average leaving time: "+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":0"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
				
			}
			else {
				System.out.println("Mean Average leaving time: "+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
				
			}
			System.out.println("\nHow many working days you have till the end of the month? ");
			Scanner pendingDaysScanner = new Scanner(System.in);
			int pendingDays  = pendingDaysScanner.nextInt();
			System.out.println("And how many hours you would like to have as monthly average? (f.e 9:10)");
			Scanner averageScanner = new Scanner(System.in);
			String monthAverage = averageScanner.nextLine();
			int monthAverageMins =  Integer.parseInt(monthAverage.substring(0, monthAverage.indexOf(":")))*60 + Integer.parseInt(monthAverage.substring(monthAverage.indexOf(":")+1,monthAverage.length()));
			int allMonthWorkingDays = daySums.size()+pendingDays;
			int leftWorkingMinutes = ( (allMonthWorkingDays*monthAverageMins)-(mean*daySums.size()) )/pendingDays ;
			System.out.println("The mean you can have till the end of the month is "+leftWorkingMinutes +" mins"
					+" ( "+(leftWorkingMinutes/60)+":" + (leftWorkingMinutes%60)+" )");
		} catch (InvalidFormatException e) {
			System.out.println("Invalid Format Exception "+e);
			e.printStackTrace();
		}
	}
	
	
	public static int calculateDifference(String col1, String col2) {

		int minutesCame = Integer.parseInt(col1.substring(0, col1.indexOf(":"))      )*hourDivisor
				          +Integer.parseInt(col1.substring(col1.indexOf(":")+1));
		int minutesGone = Integer.parseInt(col2.substring(0, col2.indexOf(":"))      )*hourDivisor
		          +Integer.parseInt(col2.substring(col2.indexOf(":")+1));
		int sumOfTheDay = minutesGone - minutesCame;
		return sumOfTheDay;
	}
	
	
}
