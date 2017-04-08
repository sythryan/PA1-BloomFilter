// Author: Syth Ryan
// COMS_535

import java.math.BigInteger;
import java.util.*;

public class BloomFilterDet {
	static BitSet bloomFilter;
	static long filterSize;
	static int dataSize = 0;
	static int numHashFunctions;
	static long FNV64PRIME = nextPrime(2132466031 * 54713835);
	static long FNVINIT = nextPrime(1525473589 * 6347248);

	public BloomFilterDet(int setSize, int bitsPerElement) {
		filterSize = (long)setSize * (long)nextPrime(bitsPerElement * 2 + 255);
		
		if (filterSize > Integer.MAX_VALUE) {
			filterSize = Integer.MAX_VALUE - 1;
		}
		
		bloomFilter = new BitSet((int)filterSize); 
		numHashFunctions = (int)(Math.log(2.0) * ((double)(filterSize) / (double)setSize));
		
		if (numHashFunctions < 1) {
			numHashFunctions = 1;
		}
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
	
	public void add(String s) {
		
		if (appears(s) == false) {
			dataSize++;
			for (int i = 0; i < numHashFunctions; i++) {
				// modding the hash assures us that our index is in bounds of the filter
				bloomFilter.set(Math.abs((int)(DeterministicHashCode(FNVINIT, s.toLowerCase()) % filterSize)));
			}
		}
	}
	
	public boolean appears(String s) {
		int trueCount = 0;
		for (int i = 0; i < numHashFunctions; i++) {
			if (bloomFilter.get(Math.abs((int)(DeterministicHashCode(FNVINIT, s.toLowerCase()) % filterSize)))) {
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
	
	private static long DeterministicHashCode(long FNV64INIT, String s) {
		long h = FNV64INIT;
		BigInteger temp;
		for (int i = 0; i < s.length(); i++) {
			h ^= s.charAt(i);
			temp = BigInteger.valueOf(h).multiply(BigInteger.valueOf(FNV64PRIME));
			h = temp.mod(BigInteger.valueOf(2).pow(64)).longValue();
		}
		return h;
	}
}
