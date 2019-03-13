import java.io.*;
import java.util.*;
/*
 * <= N-1 steps
 */
public class P2 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("sleepy.in"));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		int[] moves = new int[N];
		
		int[] oldArr = new int[N];
		oldArr = arr.clone();
		int[] newArr = new int[N];
		for (int i = 0; i < N-1; i++) { // turns
			int curCow = oldArr[0];
			
			int j;
			for (j = N-1; j >= 0; j--) {
				if (oldArr[j] < oldArr[0]) {
//					System.out.println("b1 " + j);
					break;
				}
				if (j+1 < N && oldArr[j] > oldArr[j+1]) {
//					System.out.println("b2 " + j);
					break;
				}
			}
//			System.out.println("curCow: " + curCow + " j: " + j);
			if (j == 0) break;
			for (int k = 0; k < j; k++) {
				newArr[k] = oldArr[k+1];
			}
			newArr[j] = curCow;
			for (int k = j+1; k < N; k++) {
				newArr[k] = oldArr[k];
			}
//			System.out.println(Arrays.toString(newArr));
			oldArr = newArr;
			newArr = new int[N];
			
			moves[i] = j;
			
		}
		ArrayList<Integer> ret = new ArrayList<Integer>();
//		System.out.println(Arrays.toString(moves));
		
		for (int i = 0; i < moves.length; i++) {
			if (moves[i] == 0) break;
			ret.add(moves[i]);
		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sleepy.out")));
		pw.println(ret.size());
//		System.out.println(ret.size());
		for (int i = 0; i < ret.size()-1; i++) {
			pw.print(ret.get(i) + " ");
//			System.out.print(ret.get(i) + " ");
		}
		pw.println(ret.get(ret.size()-1));
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
