
import java.util.*;
import java.io.*;
public class Flavor {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowpatibility.in"));
		
		int N = Integer.parseInt(br.readLine());

		// multiplicity
		int[] mult = new int[1000001];
		
		int[][] cows = new int[N][6];
		for(int k=0; k<N; k++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<5; j++) {
				cows[k][j] = Integer.parseInt(st.nextToken());
				mult[ cows[k][j] ]++;
			}
			Arrays.sort(cows[k], 0, 5);
		}
		br.close();
		
		// find the cows with unique flavors
		int M = 0;
		for(int k=0; k<N; k++) {
			
			boolean isUnique = true;
			for(int j=0; j<5; j++) {
				if( mult[ cows[k][j] ]>1 ) {
					isUnique = false;
					break;
				}
			}
			if( isUnique ) {
				cows[k][5] = N+1;
				M++;
			}
		}
		
		// sort the cows by group numbers
		Arrays.sort(cows, (a, b) -> a[5]-b[5]);
		
		// find the number of compatible pairs
		int K = N - M;

		long totalComp = 0;
		for(int r=0; r<K; r++) {
			for(int c=r+1; c<K; c++) {
				if( checkTwoCows(cows[r], cows[c]) ) {
					totalComp++;
				}
			}
		}

		totalComp = (long)N * (N-1) / 2 - totalComp;
		
		PrintWriter out = new PrintWriter(new File("cowpatibility.out"));
		out.println(totalComp);
		out.close();
	}

	public static boolean checkTwoCows(int[] a, int[] b) {
		
		int i=0, j=0;
		while( i<5 && j<5 ) {
			if( a[i]==b[j] )
				return true;
			else if( a[i]<b[j] ) {
				i++;
			}
			else 
				j++;
		}
		return false;
	}
}
