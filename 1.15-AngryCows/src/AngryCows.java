import java.io.*;
import java.util.*;

// 10 out of 10 test cases !!!!

// find the minimum possible blast radius to detonate all the haybales
// binary search to find R
// figure out how many cows (missiles) are required to detonate all the haybales
// cow DOES NOT HAVE TO HIT A HAYBALE!!!

public class AngryCows {
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("angry.in"));
		int N = scan.nextInt(); // number of haybales
		int K = scan.nextInt(); // number of cows
		
		int[] haybales = new int[N]; 
		for (int i = 0; i < N; i++) {
			haybales[i] = scan.nextInt();
		}
		scan.close();
		
		Arrays.sort(haybales);
		
		
		// R can be up to [range of the haybales / 2]
		int upperbound = (haybales[N-1] - haybales[0]) / 2;
		
		/*
		int r;
		for (r = upperbound; r > 0; r--) { // radius
			if (ammocount(haybales, r) > K) {
				System.out.println("r: " + r);
				System.out.println("needed cows: " + ammocount(haybales, r));
				// if the blast radius requires more cows than given, you've found the turning point, so stop
				break;
			}
			
		}
		
		r++;
		
		System.out.println(r);
		PrintWriter pw = new PrintWriter(new File("angry.out"));
		pw.println(r);
		pw.close();
		*/

		int[] options = new int[upperbound+1]; // array with 0...upperbound
		for (int i = 0; i <= upperbound; i++)
			options[i] = i;
		
		int radius = modifiedBinarySearch(haybales, options, K);
		//System.out.println(radius);
		PrintWriter pw = new PrintWriter(new File("angry.out"));
		pw.println(radius);
		pw.close();
		
	}
	
    public static int modifiedBinarySearch(int bales[], int[] rads, int K) {
    		// returns the smallest radius where all haybales can be detonated with K cows
    		// searches an array of prospective radii
    	
        int lo = 0;
        int hi = rads.length - 1;

        if (ammocount(bales, rads[hi]) > K) { // if the biggest radius doesn't even work, then return -1
        		//System.out.println("  case1");
        		return -1;
        }
		
        if (ammocount(bales, rads[lo]) <= K) { // if the smallest radius works, return it
        		//System.out.println("  case2");
        		return rads[lo];
        }
        while (lo < hi) {
        		int mid = lo + (hi-lo)/2; // midpoint of lo and hi (will take the lower of the two midpts)
        	 
        		//System.out.println("loop - hi: " + hi + " lo: " + lo + " mid : " + mid);
        		
            //System.out.println("  ammo count: " + ammocount(bales, rads[mid]));
        		
            // if the ammo count for rads[mid] == K, decrease the radius further (multiple radii can fulfill that prop)
            if (ammocount(bales, rads[mid]) == K) {
            		//System.out.println("  case3");
            		hi = mid; // include mid in case it's the answer
            }
            // If the ammo count for rads[mid] > K (you need more ammo), increase the radius
            if (ammocount(bales, rads[mid]) > K) {
            		//System.out.println("  case4");
            		lo = mid + 1;
            	}
            // If the ammo count for rads[mid] < K (you don't need as much ammo), decrease the radius
            else {
        			//System.out.println("  case5");
        			hi = mid; // preserve mid because it could be the answer
            }
                
        }
        // lo is too low, but hi is on the correct side of the boundary line
        return hi;
    }
	public static int ammocount(int[] bales, int r) {
		// calculates how many cows are needed to detonate every haybale in bales (using blast radius r)
		
		int ind = 0; // the start of the next interval of 2*r
		
		int count = 0;
		
		while (bales[ind] + 2*r < bales[bales.length - 1]) {
			// while the interval starting at ind does not cover the last haybale, loop
			
			int maxind = ind + 1; // the first index after ind not covered by the interval
			while (bales[maxind] - bales[ind] <= 2*r && maxind < bales.length) {
				// while maxind is in range, increment it
				maxind++;
			}
			// by the end of the loop, maxind is not in the range of the interval
			// if maxind == bales.length, then the interval runs off of the end of bales
			
			ind = maxind; // maxind becomes the new ind (new start of the interval);
			
			count++; // the end of one interval, so increment
		}
		
		if (ind < bales.length) {
			// if ind is still in range, count++ to account for the last end bit
			count++;
		}
		
		return count;
	}
}
