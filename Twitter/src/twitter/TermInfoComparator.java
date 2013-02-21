package twitter;

import java.util.Comparator;

public class TermInfoComparator implements Comparator<TermInfo> {
	@Override
	public int compare(TermInfo t0, TermInfo t1) {
//		TermInfo t0 = (TermInfo)arg0;
//		TermInfo t1 = (TermInfo)arg1;
		
		if (t0.frequency < t1.frequency) {
			return 1;
		} else if (t0.frequency > t1.frequency) {
			return -1;
		}
		
//		String string0 = (String)arg0;
//		String string1 = (String)arg1;
////		int freq0 = (Integer) frequencyMap.get(string0);
////		int freq1 = (Integer) frequencyMap.get(string1);
//		if (freq0 < freq1) {
//			return 1;
//		} else if (freq0 > freq1) {
//			return -1;
//		}
		
		return 0;
	}
}
