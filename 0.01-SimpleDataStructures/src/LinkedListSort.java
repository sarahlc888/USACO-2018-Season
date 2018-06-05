import java.util.*;

public class LinkedListSort {
	public static LinkedList<Integer> sortNum(LinkedList<Integer> L) {
		int evenInd = 0; // the end of the evens
		int oddInd = 1; // the end of the odds
		for (int i = 0; i < L.size(); i++) {
			if (L.get(i) % 2 == 0) { // even
				int num = L.get(i);
				L.remove(i); // get rid of the old number
				L.add(evenInd, num); // move the number to the right place
				
				
			} else { // odd
				L.add(oddInd, L.get(i)); // move the number to the right place
				L.remove(i); // get rid of the old number
			}
		}
		
		return L;
	}
}
