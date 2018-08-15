import java.io.*;
import java.util.*;
/*
 * USACO 2015 January Contest, Gold
 * Problem 2. Moovie Mooving
 * 
 * subset DP problem
 * 
 * 14/14 test cases
 * fixed issue with what answer is returned (had to peek at test data... but I think I would have figured it out)
 */
public class MM2 {
	static int N;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("movie.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // num movies
		int L = Integer.parseInt(st.nextToken()); // amt time to fill
		
		Movie[] movies = new Movie[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int duration = Integer.parseInt(st.nextToken());
			int numShows = Integer.parseInt(st.nextToken());
			Movie m = new Movie(duration, numShows);
			for (int j = 0; j < numShows; j++) {
				m.shows[j] = Integer.parseInt(st.nextToken());
			}
			movies[i] = m;
		}
		br.close();

//		System.out.println(Arrays.toString(movies));
		
		// DP[i] = max minutes coverable using subset i
		int[] DP = new int[(int)Math.pow(2, N)];
		
		for (int i = 0; i < (int)Math.pow(2, N); i++) {
			DP[i] = -1; // mark unvisited
		}
		
		DP[0] = 0; // basecase
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("movie.out")));

		int minAmt = Integer.MAX_VALUE;
		
		for (int i = 0; i < (int)Math.pow(2, N); i++) { // loop through all subsets
			if (DP[i] == -1) continue; // skip if invalid
			String cur = pad(Integer.toBinaryString(i)); // current subset
			
//			System.out.println("CUR: " + cur + "  min: " + DP[i]);
			
			for (int j = 0; j < N; j++) { // loop thru cur state
				if (cur.charAt(j) == '0') { // if the movie is not included, include it
					
//					System.out.println("  next: " + j + "  length: " + movies[j].length);
					
					int places = N-j-1; // number of places
					int newInd = i+(int)Math.pow(2, places);
					
					// find latest showing that starts before the end of prev movie (DP[i])
					
					int showTimeInd = greatestBelow(movies[j].shows, DP[i]);
					
					if (showTimeInd == -1) continue;
					
					int showTime = movies[j].shows[showTimeInd];
					int endTime = showTime + movies[j].length;
					
					if (endTime > DP[newInd] && endTime > DP[i]) {
						// if it made it last longer
						// TODO: check if this is right
						DP[newInd] = endTime;
					}
					
					
					
//					System.out.println("    new state: " + pad(newcur));
					
					if (endTime >= L) {
						String newcur = Integer.toBinaryString(newInd);
						int count = 0;
						for (int k = 0; k < newcur.length(); k++) {
							if (newcur.charAt(k) == '1') count++;
						}
//						System.out.println(count);
						minAmt = Math.min(minAmt, count);
						
					}
				}
			}
		}
		if (minAmt != Integer.MAX_VALUE) {
//			System.out.println(minAmt);
			pw.println(minAmt);
		} else {
//			System.out.println(-1);
			pw.println(-1);
		}
		pw.close();
//		System.out.println(-1);
		
		
	}
	public static int greatestBelow(int[] arr, long val) {
		// returns greatest i <= val

//		System.out.println("    showtimes: " + Arrays.toString(arr) + "  start by: " + val);
		
		int lo = -1;
		int hi = arr.length-1;

		if (arr[0] > val) { // if even lowest doesn't work
//			System.out.println("    no show1");
			return -1; 
		}
		
		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			
			if (arr[mid] <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		if (arr[hi] <= val) {
//			System.out.println("    showtime: " + arr[hi]);
			return hi;
		}
//		System.out.println("    no show2");
		return -1;
	}
	public static int greatestBelow2(int[] arr, long val) {
		// returns greatest i < val (NOT <= !!!)

//		System.out.println("    showtimes: " + Arrays.toString(arr) + "  start by: " + val);
		
		int lo = -1;
		int hi = arr.length-1;

		if (arr[0] >= val) {
//			System.out.println("    no show1");
			return -1; // if even lowest doesn't work
		}
		
		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			
			if (arr[mid] < val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		if (arr[hi] < val) {
//			System.out.println("    showtime: " + arr[hi]);
			return hi;
		}
//		System.out.println("    no show2");
		return -1;
	}
	public static String pad(String s) {
		while (s.length() < N) {
			s = "0" + s;
		}
		return s;
	}
	public static class Movie {
		int length;
		int[] shows; // stores start times of each show

		public Movie(int a, int b) {
			length = a;
			shows = new int[b];
		}
		
		public String toString() {
			return length + " " + Arrays.toString(shows);
		}
	}


}
