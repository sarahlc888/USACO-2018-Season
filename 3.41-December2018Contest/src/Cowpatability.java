import java.io.*;
import java.util.*;
/*
 * 
 */
public class Cowpatability {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowpatibility.in"));
		int N = Integer.parseInt(br.readLine());
		
		int[][] nums = new int[N][6]; // index 5 is the group
		int[] count = new int[1000001];
		Pair[] pcount = new Pair[1000001];
		for (int i = 0; i < pcount.length; i++) {
			pcount[i] = new Pair(i, 0); // number, count
		}
		
		// entry, rows that have that entry
		TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<Integer, ArrayList<Integer>>(); 
		
		for (int i = 0; i < N; i++) { // scan everything in
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				nums[i][j] = Integer.parseInt(st.nextToken());
				count[nums[i][j]]++;
				pcount[nums[i][j]].ct++;
				// put the map
				if (!map.containsKey(nums[i][j])) {
					map.put(nums[i][j], new ArrayList<Integer>());
				} 
				map.get(nums[i][j]).add(i);
			}
			nums[i][5] = -1;
		}
		br.close();


		ArrayList<Integer> groupIDs = new ArrayList<Integer>();
		TreeMap<Integer, ArrayList<Integer>> groups = new TreeMap<Integer, ArrayList<Integer>>(); // group ID, indices of rows inside
		boolean[] isGroupID = new boolean[1000001];
		// group all entries
		Arrays.sort(pcount);
		int i = 0;
		while (i < pcount.length && pcount[i].ct > 1) {
			// while there are still things to group, group them
			ArrayList<Integer> grp = map.get(pcount[i].num); // all rows that contain pcount[i].num
			
			// fill the group
			for (int j = 0; j < grp.size(); j++) {
				int curInd = grp.get(j);
				if (nums[curInd][5] == -1) { // if the thing hasn't been assigned a group
					
					if (isGroupID[pcount[i].num]) { // group already started
						nums[curInd][5] = pcount[i].num; // label group
						groups.get(pcount[i].num).add(curInd); // add to map
					} else { // not already started
						nums[curInd][5] = pcount[i].num;
						groupIDs.add(pcount[i].num);
						isGroupID[pcount[i].num] = true;
						
						
						groups.put(pcount[i].num, new ArrayList<Integer>());
						groups.get(pcount[i].num).add(curInd); // add to map
					}
				}
			}
			i++;
		}
//		System.out.println(Arrays.toString(pcount));
		
//		
//		System.out.println("groupids: " + groupIDs);
//		System.out.println(groups);
		
		
		/*
		// sort by group
		Arrays.sort(nums, (a, b)-> a[5]-b[5]);
		for (i = 0; i < nums.length; i++) {
			System.out.println(Arrays.toString(nums[i]));
		}
		*/
		
		// compare between groups
		long compatibleCt = 0;
		for (i = 0; i < groupIDs.size(); i++) {
			int group1 = groupIDs.get(i);
			for (int j = i+1; j < groupIDs.size(); j++) {
				int group2 = groupIDs.get(j);
				
				
				
				for (int k = 0; k < groups.get(group1).size(); k++) {
					for (int m = 0; m < groups.get(group2).size(); m++) {
						
						int[] curNum1 = nums[groups.get(group1).get(k)];
						int[] curNum2 = nums[groups.get(group2).get(m)];
						
//						System.out.println(Arrays.toString(curNum1) + " " + Arrays.toString(curNum2));
						
						boolean comp = false;
						
here:					for (int p = 0; p < 5; p++) {
//							if (comp) break;
							for (int q = 0; q < 5; q++) {
//								if (comp) break;
								if (curNum1[p] == curNum2[q]) {
									// compatible bc they share a number
									comp = true; // stop looping this current numbers
									break here;
								}
							}
						}
						if (comp) compatibleCt++;
						
						
					}
				}
			}
		}
//		System.out.println("cc: " + compatibleCt);
		
		// add compatible pairs within groups
		for (i = 0; i < groupIDs.size(); i++) {
			int group1 = groupIDs.get(i);
			int glen = groups.get(group1).size();
			
			compatibleCt += ((long)(glen)*(glen-1))/2;
		}
//		System.out.println("cc: " + compatibleCt);
		
		long ret = N*(N-1)/2-compatibleCt;
		
//		System.out.println(ret);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
		pw.println(ret);
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int num;
		int ct;

		public Pair(int a, int b) {
			num = a;
			ct = b;
		}
		@Override
		public int compareTo(Pair o) { // sort ct descending
			return o.ct-ct;
		}
		public String toString() {
			return num + " " + ct;
		}
	}


}
