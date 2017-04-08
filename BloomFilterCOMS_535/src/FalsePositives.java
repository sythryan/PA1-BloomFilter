
public class FalsePositives {

	public static void main(String[] args) {
		double randomFalsePositiveAverage = 0.0;
		double deterministicFalsePositiveAverage = 0.0;
		
		int sampleSize = 8;
		int setSize = 100;
		
		for (int n = 1; n <= sampleSize; n++)
		{
			int bitsPerElement = 8;
			setSize	*= 2;
			
			int detFalsePositiveCount = 0;
			int ranFalsePositiveCount = 0;
			
			BloomFilterDet deterministicFilter = new BloomFilterDet(setSize, bitsPerElement);
			BloomFilterRan randomFilter = new BloomFilterRan(setSize, bitsPerElement);
			System.out.println("Using: " + deterministicFilter.numHashes() + " hashes and filter size of: " + deterministicFilter.filterSize());
			
			// fill filters
			for (int i = 0; i < setSize; i++) {
				deterministicFilter.add(Integer.toString(i));
				randomFilter.add(Integer.toString(i));
			}
			
			for (long i = setSize; i < setSize * 2; i++) {
				String testerString = Long.toString(i);
				if (deterministicFilter.appears(testerString)) {
					detFalsePositiveCount++;
				}
				
				if (randomFilter.appears(testerString)) {
					ranFalsePositiveCount++;
				}
			}
			
			randomFalsePositiveAverage += ((double)ranFalsePositiveCount / (double)setSize * 100.0);
			deterministicFalsePositiveAverage += ((double)detFalsePositiveCount / (double)setSize * 100.0);
			
			System.out.println("Deterministic False Positive Count: " + detFalsePositiveCount + " out of " + setSize);
			System.out.println("Random False Positive Count: " + ranFalsePositiveCount + " out of " + setSize);
			System.out.println("");
			System.out.println("Deterministic False Positive percent: " + (double)detFalsePositiveCount / (double)setSize * 100.0 + "%");
			System.out.println("Random False Positive percent: " + (double)ranFalsePositiveCount / (double)setSize * 100.0 + "%");
			System.out.println("----------------------------------------------------------");
			System.out.println("");
		}
		
		System.out.println("!!!!   Final Averages !!!!");
		System.out.println("deterministic average = " + deterministicFalsePositiveAverage / sampleSize +"%");
		System.out.println("random average = " + randomFalsePositiveAverage / sampleSize + "%");
		
	}

}
