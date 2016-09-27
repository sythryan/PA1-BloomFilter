import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class BloomDifferential {
	String fileName = "C:/Users/Syth/OneDrive/Documents/School_work/COMS_535/PA1/BloomFilterCOMS_535/src/DiffFile.txt";
	File diffFile = new File(fileName);
	BloomFilterDet DeterministicFilter;
	
	public void creatFilter() {
		int setSize = countAllLines();
		int bitsPerElement = 32; // 32 bits from 4 word phrase
		System.out.println(setSize);
		DeterministicFilter = new BloomFilterDet(setSize, bitsPerElement);
		
		//TODO Fill up Filter with keys
	}
	
	public void retrieveRecord(String key) {
		DeterministicFilter.appears(key);
	}
	
	private int countAllLines() {
		int lineCount = 1;
		try {
			FileReader reader = new FileReader(diffFile);
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
