import java.io.*;
import java.util.*;


public class DataComparatorSort {
	
	public static void main(String args[]) throws IOException {

		
		int N = 3; // number of pies baked by each
		
		/*System.out.println("N: " + N);
		System.out.println("D: " + D);*/
		Pie[] bes = new Pie[N];
		Pie[] els = new Pie[N];
		Pie[] pies = new Pie[2*N];
		for (int i = 0; i < N; i++) {
			// bessie's pies
			pies[i] = new Pie(i, 10-i, 0);
			bes[i] = new Pie(i, 10-i, 0);
		}
		for (int i = N; i < 2*N; i++) {
			// elsie's pies
			
			pies[i] = new Pie(i, 10-i, 0);
			els[i-N] = new Pie(i, 10-i, 0);
		}
		
		Arrays.sort(bes, Pie.BessiePieComparator); // sort bessie's pies based on bessie's ratings
		Arrays.sort(els, Pie.BessiePieComparator); // sort elsie's pies based on elsie's ratings
		
		for (int i = 0; i < N; i++) {
			System.out.println(bes[i].bVal);
		}
		
		
	}
	
	public static class Pie {
		int bVal; // bessie's tastiness value
		int eVal; // elsie's tastiness value
		int baker; // 0 for bessie, 1 for elsie
		public Pie(int b, int e, int bak) {
			bVal = b;
			eVal = e;
			baker = bak;
		}
		public static Comparator<Pie> BessiePieComparator = new Comparator<Pie>() {

			public int compare(Pie p1, Pie p2) {
				
				Integer bval1 = p1.bVal;
				Integer bval2 = p2.bVal;
				
				//ascending order
				return bval1.compareTo(bval2);

			}

		};
		public static Comparator<Pie> ElsiePieComparator = new Comparator<Pie>() {

			public int compare(Pie p1, Pie p2) {
				
				Integer eval1 = p1.eVal;
				Integer eval2 = p2.eVal;
				
				//ascending order
				return eval1.compareTo(eval2);

			}

		};
	}


}

