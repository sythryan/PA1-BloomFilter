import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EmpericalComparison {
	static String keysFile = "C:/Users/Syth/Downloads/grams.txt";
	static BloomDifferential Bloom = new BloomDifferential();
	static NaiveDifferential Naive = new NaiveDifferential();
	
	
	public static void main(String[] args) {
		long time;
		
		System.out.print("Creating Filter. . .    ");
		time = System.currentTimeMillis();
		Bloom.creatFilter();
		System.out.println("done!");
		System.out.println("Creating the filter took " + Long.toString(System.currentTimeMillis() - time) + " ms");
		time = System.currentTimeMillis();
		quereyKeysOnBloomDifferential(); 
		System.out.println("Bloom Differential took " + Long.toString(System.currentTimeMillis() - time) + " ms");
		time = System.currentTimeMillis();
		quereyKeysOnNaiveDifferential();
		System.out.println("Naive Differential took " + Long.toString(System.currentTimeMillis() - time) + " ms");
	}
	
	private static void quereyKeysOnBloomDifferential() {
		Scanner myScanner = null;
	    try {
	        myScanner = new Scanner(new File (keysFile));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    while (myScanner.hasNextLine()) {
	    	myScanner.nextLine();
            String s = "";
	        for(int i = 0; i < 4 ; i++) {
	        	if (myScanner.hasNext()) {
	        		s = s + " " + myScanner.next();
	        	}
	        }
	        Bloom.retrieveRecord(s.toLowerCase());
	    }
	    myScanner.close();
	}
		
	private static void quereyKeysOnNaiveDifferential() {
		Scanner myScanner = null;
	    try {
	        myScanner = new Scanner(new File (keysFile));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    while (myScanner.hasNextLine()) {
	    	myScanner.nextLine();
            String s = "";
	        for(int i = 0; i < 4 ; i++) {
	        	if (myScanner.hasNext()) {
	        		s = s + " " + myScanner.next();
	        	}
	        }
	        Naive.retrieveRecord(s.toLowerCase());
	    }
	    myScanner.close();
	}	

}
