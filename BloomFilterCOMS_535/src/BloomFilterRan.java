// Author: Syth Ryan
// COMS_535

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BloomFilterRan {
	
	//Required for Random Hashing because it would be hard to redo the hashes exactly. stores random numbers <a, b>
	List<List<Integer>> hashFunctionValues = new ArrayList<List<Integer>>();
	static BitSet bloomFilter;
	static long filterSize;
	static int dataSize = 0;
	static int numHashFunctions;
	static long p;

	public BloomFilterRan(int setSize, int bitsPerElement) {
		java.util.Random randomNumbers = new java.util.Random();
		
		filterSize = (long)setSize * (long)nextPrime(bitsPerElement + 255);
		
		if (filterSize > Integer.MAX_VALUE) {
			filterSize = Integer.MAX_VALUE - 1;
		}
		bloomFilter = new BitSet((int)filterSize); 
		p = nextPrime(filterSize + setSize); // pick a prime p > size of filter
		numHashFunctions = (int)(Math.log(2.0) * (double)(filterSize) / (double)setSize);
		
		if (numHashFunctions < 1) {
			numHashFunctions = 1;
		}
		
		// Generate hash function <a, b> values
		for(int i = 0; i < numHashFunctions; i++) {
			int a = Math.abs(randomNumbers.nextInt());
			int b = Math.abs(randomNumbers.nextInt());
			List<Integer> tempList = new ArrayList<Integer>();
			tempList.add(a);
			tempList.add(b);
			hashFunctionValues.add(tempList);
		}
	}
	
	public void add(String s) {
		if (appears(s) == false){
			dataSize++;
			for (int i = 0; i < numHashFunctions; i++) {
				// modding the hash assures us that our index is in bounds of the filter
				bloomFilter.set((int)(RandomHashCode(p,hashFunctionValues.get(i).get(0) , hashFunctionValues.get(i).get(1), s.toLowerCase()) % filterSize));
			}
		}
	}
	
	public boolean appears(String s) {		
		int trueCount = 0;
		for (int i = 0; i < numHashFunctions; i++) {
			if (bloomFilter.get((int)(RandomHashCode(p,hashFunctionValues.get(i).get(0) , hashFunctionValues.get(i).get(1), s.toLowerCase()) % filterSize))) {
				trueCount ++;
			}
		}
		if (trueCount == numHashFunctions) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public int filterSize() {
		return (int)filterSize;
	}
	
	public int dataSize() {
		return dataSize;
	}
	
	public int numHashes() {
		return numHashFunctions;
	}
	
	private static long RandomHashCode(long p, int a, int b, String s) {
		return ((long)a * (long)Math.abs(s.hashCode()) + (long)b) % p;
	}
	
	private static long nextPrime(long n) {
	    boolean primeFlag = false;
	    while (!primeFlag) {
	        n += 1;
	        long m = (long) Math.ceil(Math.sqrt(n));

	        primeFlag = true;
	        for (long i = 2; i <= m; i++) {
	            if (n % i == 0) {
	            	primeFlag = false;
	                break;
	            } 
	        }
	    }
	    return n;
	}
}

