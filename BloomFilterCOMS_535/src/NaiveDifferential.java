import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NaiveDifferential {
	String dataBaseFile = "C:/Users/Syth/Downloads/database.txt";
	
	public String retrieveRecord(String key) {
		return searchFile(dataBaseFile, key);
	}
	
	private String searchFile(String fileName, String key)
	{
		String retVal = "";
		Scanner myScanner = null;
	    try {
	        myScanner = new Scanner(new File (fileName));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    if (myScanner.hasNextLine()) {
	    	myScanner.nextLine();
	    }
	    while (myScanner.hasNextLine()) {
            String s = "";
	        for(int i = 0; i < 4 ; i++) {
	            s = s + " " + myScanner.next();
	        }
	        retVal = myScanner.nextLine();
	        if (key.toLowerCase().equals(s.toLowerCase())){
	        	myScanner.close();
	        	return retVal;
	        }
	    }
	    myScanner.close();
		return retVal;
	}
}
