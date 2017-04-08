import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;

public class BloomDifferential {
	String diffFile = "C:/Users/Syth/Downloads/DiffFile.txt";
	String dataBaseFile = "C:/Users/Syth/Downloads/database.txt";
	BloomFilterRan DeterministicFilter;
	static String record = "";
	
	public void creatFilter() {
		int setSize = countAllLines();
		int bitsPerElement = 10;
		DeterministicFilter = new BloomFilterRan(setSize, bitsPerElement);
		populateFilter();
	}
	
	public String retrieveRecord(String key) {
		if (DeterministicFilter.appears(key)) {
			if (searchFile(diffFile, key) == false) {
				if (searchFile(dataBaseFile, key) == false) {
					record = "";
				}
			}
		} else {
			if (searchFile(dataBaseFile, key) == false) {
				record = "";
			}
		}
		return record;
	}
	
	private boolean searchFile(String fileName, String key)
	{
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
	        
	        if (key.toLowerCase().equals(s.toLowerCase())){
	        	record = myScanner.nextLine();
	        	return true;
	        } else {
	        	myScanner.nextLine();
	        }
	    }
	    myScanner.close();
	    return false;
	}
	
	private void populateFilter() {
		Scanner myScanner = null;
	    try {
	        myScanner = new Scanner(new File (diffFile));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    myScanner.nextLine();
	    while (myScanner.hasNextLine()) {
            String s = "";
	        for(int i = 0; i < 4 ; i++) {
	        	if (myScanner.hasNext()) {
	        		s = s + " " + myScanner.next();
	        	}
	        }
	        DeterministicFilter.add(s);
	        myScanner.nextLine();
	    }
	    myScanner.close();
	}
	
	private int countAllLines() {
		int lineCount = 1;
		try {
			FileReader reader = new FileReader(new File (diffFile));
		    LineNumberReader numberReader = new LineNumberReader(reader);
		
	        while (numberReader.readLine() != null){
	        	lineCount++;
	        }
	
	        numberReader.close();
		} catch(IOException e) {
			System.out.println(e);
		}
		return lineCount;
	}
}
