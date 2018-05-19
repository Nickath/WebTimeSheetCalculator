package org.nick.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nick.form.TimeSheetForm;
import org.nick.model.TimeSheet;
import org.nick.service.FileHandler;
import org.nick.web.contants.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service("fileHandler")
public class FileHandlerImpl implements FileHandler {
	
	private static final Logger LOGGER = Logger.getLogger(FileHandlerImpl.class.getName());


	@Override
	public File readFile(String path,CommonsMultipartFile file, TimeSheet timesheet) {
	
		byte[] bytes = file.getBytes();
		LOGGER.info("Path where the file is going to be stored is:\n" +FileHandlerImpl.class
                .getClassLoader().getResource("").getPath().toString());
        File file2 = new File(FileHandlerImpl.class
                .getClassLoader().getResource("").getPath().toString().replace("/", "\\")+ "timesheets\\"+"TimeSheet"+timesheet.getUser().getUsername()
                +""+timesheet.getMonth().getMonth()+".xlsx");
     

        try {
            OutputStream os = new FileOutputStream(file2);
            os.write(bytes);
            LOGGER.info("Write bytes to file.");
            os.close();
        } catch (Exception e) {
            LOGGER.severe("File was not written successfully :( "+e);
        }
		return file2;
	}
	
	@Override
	public TimeSheet makeCalculations(File myFile,TimeSheetForm timesheetform, TimeSheet timesheet) throws IOException {
		

		try {

			XSSFWorkbook myTimeSheet = new XSSFWorkbook (myFile);
			XSSFSheet mySheet = myTimeSheet.getSheetAt(0);
			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = mySheet.iterator();
			//iterate through the lines
		
			ArrayList<Integer> daySums = new ArrayList<>();
			ArrayList<Integer> inSums  = new ArrayList<>();
			ArrayList<Integer> outSums = new ArrayList<>();
			while (rowIterator.hasNext()) {
				
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                String col1 = "";
                String col2 = "";
                boolean first = true;
                while (cellIterator.hasNext()) {
                	Cell cell = cellIterator.next();
                	
                	DataFormatter dataFormatter = new DataFormatter();
                	String cellStringValue = dataFormatter.formatCellValue(cell);
                
                	
                	if(first) {
                		col1 = cellStringValue;
                	}
                	else {
                		col2 = cellStringValue;
                	}
                	first = false;
                }
                
                try {
                daySums.add(calculateDifference(col1,col2));
                }
                catch(Exception e) {
                	LOGGER.severe("------------------ Invalid Format of .xlsx file ---------------------");
                	break;
                }
                inSums.add(Integer.parseInt(col1.substring(0,col1.indexOf(":")))*60 + Integer.parseInt(col1.substring(col1.indexOf(":")+1,col1.length())));
                outSums.add(Integer.parseInt(col2.substring(0,col2.indexOf(":")))*60 + Integer.parseInt(col2.substring(col2.indexOf(":")+1,col2.length())));
			}
			int mean = daySums.stream().mapToInt(Integer::intValue).sum()/daySums.size();
			int meanHours= mean/Constants.hourDivisor;
			int meanMinutes= mean%Constants.hourDivisor;
			
			LOGGER.info("This month you have worked **"+ daySums.size()+"** days\n");
			
			LOGGER.info("Total Mean Average: " +mean +" ("+meanHours+":"+(  (meanMinutes<10)?("0"+meanMinutes):meanMinutes)+")");
			if((((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60)<10) {
				LOGGER.info("Mean Average coming time: "+ (((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":0"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
				timesheet.setInsertMean( (((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":0"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
			}
			else {
				LOGGER.info("Mean Average coming time: "+ (((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
				timesheet.setInsertMean((((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
			}
			if((((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60)<10)
			{
				LOGGER.info("Mean Average leaving time: "+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":0"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
				timesheet.setExitMean((((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":0"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
			}
			else {
				LOGGER.info("Mean Average leaving time: "+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
				timesheet.setExitMean((((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
			}
			
			
			int monthAverageMins =  Integer.parseInt(timesheetform.getDesiredMean().substring(0, timesheetform.getDesiredMean().indexOf(":")))*60 + Integer.parseInt(timesheetform.getDesiredMean().substring(timesheetform.getDesiredMean().indexOf(":")+1,timesheetform.getDesiredMean().length()));
			int allMonthWorkingDays = daySums.size()+timesheetform.getPendingDays();
			int leftWorkingMinutes = ( (allMonthWorkingDays*monthAverageMins)-(mean*daySums.size()) )/timesheetform.getPendingDays() ;
			
			LOGGER.info("The mean you can have till the end of the month is "+leftWorkingMinutes +" mins"
					+" ( "+(leftWorkingMinutes/60)+":" + ( (leftWorkingMinutes%60)<10?"0"+(leftWorkingMinutes%60):(leftWorkingMinutes%60) )+" )");
		    
			
            timesheet.setMean(" ("+meanHours+":"+(  (meanMinutes<10)?("0"+meanMinutes):meanMinutes)+")" );
            timesheet.setWorkingDays(daySums.size());
            timesheet.setRestAverage((leftWorkingMinutes/60)+":" + ( (leftWorkingMinutes%60)<10?"0"+(leftWorkingMinutes%60):(leftWorkingMinutes%60) ));
			timesheet.setDesiredMean(timesheetform.getDesiredMean());
			timesheet.setFile(myFile);
            return	timesheet ;
		
		} catch (InvalidFormatException e) {
			LOGGER.severe("Invalid Format Exception "+e);
			e.printStackTrace();
		}
		
		return timesheet;
	}
	
	
	@Override
	public TimeSheet makeCalculations(File myFile, TimeSheet timesheet) throws IOException {
		
		
		try {

			XSSFWorkbook myTimeSheet = new XSSFWorkbook (myFile);
			XSSFSheet mySheet = myTimeSheet.getSheetAt(0);
			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = mySheet.iterator();
			//iterate through the lines
		
			ArrayList<Integer> daySums = new ArrayList<>();
			ArrayList<Integer> inSums  = new ArrayList<>();
			ArrayList<Integer> outSums = new ArrayList<>();
			while (rowIterator.hasNext()) {
				
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                String col1 = "";
                String col2 = "";
                boolean first = true;
                while (cellIterator.hasNext()) {
                	Cell cell = cellIterator.next();
                	
                	DataFormatter dataFormatter = new DataFormatter();
                	String cellStringValue = dataFormatter.formatCellValue(cell);
                
                	
                	if(first) {
                		col1 = cellStringValue;
                	}
                	else {
                		col2 = cellStringValue;
                	}
                	first = false;
                }
                
                try {
                daySums.add(calculateDifference(col1,col2));
                }
                catch(Exception e) {
                	LOGGER.severe("------------------ Invalid Format of .xlsx file ---------------------");
                	break;
                }
                inSums.add(Integer.parseInt(col1.substring(0,col1.indexOf(":")))*60 + Integer.parseInt(col1.substring(col1.indexOf(":")+1,col1.length())));
                outSums.add(Integer.parseInt(col2.substring(0,col2.indexOf(":")))*60 + Integer.parseInt(col2.substring(col2.indexOf(":")+1,col2.length())));
			}
			int mean = daySums.stream().mapToInt(Integer::intValue).sum()/daySums.size();
			int meanHours= mean/Constants.hourDivisor;
			int meanMinutes= mean%Constants.hourDivisor;
			
			LOGGER.info("This month you have worked **"+ daySums.size()+"** days\n");
			
			LOGGER.info("Total Mean Average: " +mean +" ("+meanHours+":"+(  (meanMinutes<10)?("0"+meanMinutes):meanMinutes)+")");
			if((((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60)<10) {
				LOGGER.info("Mean Average coming time: "+ (((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":0"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
				timesheet.setInsertMean( (((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":0"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
			}
			else {
				LOGGER.info("Mean Average coming time: "+ (((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
				timesheet.setInsertMean((((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))/60)+":"+(((inSums.stream().mapToInt(Integer::intValue).sum()/inSums.size()))%60));
			}
			if((((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60)<10)
			{
				LOGGER.info("Mean Average leaving time: "+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":0"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
				timesheet.setExitMean((((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":0"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
			}
			else {
				LOGGER.info("Mean Average leaving time: "+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
				timesheet.setExitMean((((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))/60)+":"+(((outSums.stream().mapToInt(Integer::intValue).sum()/outSums.size()))%60));
			}
			
			
	
			
            timesheet.setMean(" ("+meanHours+":"+(  (meanMinutes<10)?("0"+meanMinutes):meanMinutes)+")" );
            timesheet.setWorkingDays(daySums.size());
			timesheet.setFile(myFile);
            return	timesheet ;
		
		} catch (InvalidFormatException e) {
			LOGGER.severe("Invalid Format Exception "+e);
			e.printStackTrace();
		}
		
		return timesheet;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static int calculateDifference(String col1, String col2) {

		int minutesCame = Integer.parseInt(col1.substring(0, col1.indexOf(":"))      )*Constants.hourDivisor
				          +Integer.parseInt(col1.substring(col1.indexOf(":")+1));
		int minutesGone = Integer.parseInt(col2.substring(0, col2.indexOf(":"))      )*Constants.hourDivisor
		          +Integer.parseInt(col2.substring(col2.indexOf(":")+1));
		int sumOfTheDay = minutesGone - minutesCame;
		return sumOfTheDay;
	}





	
	
}
