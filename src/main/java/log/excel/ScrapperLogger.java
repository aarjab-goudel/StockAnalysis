/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package log.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import create.excel.data.service.SaveExcel;

public class ScrapperLogger {

	private static String loggerTxtFile = null;

	private ScrapperLogger() {

	}

	public static String getLoggerInstance() {
		if (loggerTxtFile == null) {
			try {
				Date currentDate = new Date();
				String loggerFileName = Long.toString(currentDate.getTime()) + "- Log.txt";
				loggerTxtFile = SaveExcel.addToPath(SaveExcel.getLogPath(), loggerFileName);
				File pathToTxtFile = new File(loggerTxtFile);
				if (pathToTxtFile.createNewFile()) {
					System.out.println("File created: " + pathToTxtFile.getName());
				} else {
					System.out.println("File already exists.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return loggerTxtFile;
	}

	public static void log(String strToWrite) throws IOException {
		String pathToWrite = ScrapperLogger.getLoggerInstance();
		FileWriter fstream = new FileWriter(pathToWrite, true);
		BufferedWriter info = new BufferedWriter(fstream);
		info.write(strToWrite);
		info.newLine();
		info.close();
	}
}
