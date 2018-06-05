import java.io.*;
import java.util.*;

// ten out of ten 

public class MMS {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("measurement.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken()); // number of measurements made
		int G = Integer.parseInt(st.nextToken()); // number of gallons initially
		
		int[][] measures = new int[N][3];
	
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int day = Integer.parseInt(st.nextToken());
			int cow = Integer.parseInt(st.nextToken());
			int diff = Integer.parseInt(st.nextToken());
			// measures[0] = accesses the proper day
			measures[i][0] = day;
			measures[i][1] = cow;
			measures[i][2] = diff;
		}
		
		Arrays.sort(measures, (a, b) -> Integer.compare(a[0], b[0])); // sort by day

		int numCows = 0; // number of distinct cows
		
		// maps IDs to index from 0 to numCows-1
		HashMap<Integer, Integer> idToInd = new HashMap<Integer, Integer>();
		for (int i = 0; i < N; i++) {
			if (!idToInd.containsKey(measures[i][1])) {
				idToInd.put(measures[i][1], numCows);
				numCows++;
			}
		}
		
		int[] cowMilk = new int[numCows + 1]; // amt milk from each cow at cur step
		for (int i = 0; i < cowMilk.length; i++) {
			cowMilk[i] = G;
		}
		// last cow will never change (baseline)
		numCows++; // account for the newly added cow
		
		int prevHi = G; // highest milk prod from last time
		ArrayList<Integer> prevInds = new ArrayList<Integer>();
		for (int i = 0; i < numCows; i++) {
			prevInds.add(i);
		}
		
		// number of changes
		int changes = 0;
		
		for (int i = 0; i < N; i++) {
			// go through all measurements
			
			int curCow = idToInd.get(measures[i][1]);
			
			//System.out.println("curCow: " + curCow);
			
			// update curcow's milk prod
			cowMilk[curCow] += measures[i][2];
			
			/*
			for (int j = 0; j < cowMilk.length; j++) {
				System.out.println("  milk: " + cowMilk[j]);
			}
			*/
			
			if (measures[i][2] > 0) { // positive change
				
				if (cowMilk[curCow] > prevHi && prevInds.size() == 1 && prevInds.contains(curCow)) {
					prevHi = cowMilk[curCow];
					// new record but no change
				} else if (cowMilk[curCow] > prevHi) {
					// cowInd is higher than last time, make it the new hi
					prevInds.clear();
					prevInds.add(curCow);
					prevHi = cowMilk[curCow];
					changes++;
				} else if (cowMilk[curCow] == prevHi) {
					// added to the highest group
					prevInds.add(curCow);
					changes++;
				}
				
			} else { // negative change
				
				if (prevInds.contains(curCow)) {
					// if cowInd was previously in the highest group, check if remove
					
					if (prevInds.size() > 1) { // if there are others in high group
						prevInds.remove((Integer)curCow); // remove 
						changes++;
					} else if (prevInds.size() == 1) {
						// only 1, so must determine the "new" highest out of cowMilk
						
						// find highest indices of cowMilk
						int curmax = 0;
						for (int j = 0; j < cowMilk.length; j++) {
							if (cowMilk[j] > curmax) {
								curmax = cowMilk[j];
							}
						}
						ArrayList<Integer> curinds = new ArrayList<Integer>();
						for (int j = 0; j < cowMilk.length; j++) {
							if (cowMilk[j] == curmax) {
								curinds.add(j);
							}
						}
						
						// if it's the same id only, no change
						// if there are multiple/different ones, then change++
						
						// update prevHi
						prevHi = curmax;
						
						if (curinds.size() == 1 && curinds.get(0) == curCow) {
							// if there's only one highest cow and it's still cowInd
							// no changes++
							
						} else {
							// curinds.size() > 1
							// may or max not include cowInd, but it doesn't matter
							// put proper maxes into prevInds
							prevInds.clear();
							prevInds.addAll(curinds);
							
							// changes++ anyway
							changes++;
						}
					}
				}
			}	
		}
		
		// System.out.println(changes);
		

		br.close();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));

		pw.println(changes);
		pw.close();
	}


}
