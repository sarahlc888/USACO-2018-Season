import java.io.*;
import java.util.*;
/*
 * USACO 2015 US Open, Silver
Problem 1. Bessie Goes Moo
 */
public class BGM {
	static String[] vars = {"B", "E", "S", "I", "G", "O", "M"}; // seven variables, BESIGOM
	static HashMap<String, long[]> modMap = new HashMap<String, long[]>(); // letter, [i]=num occurrences of val%7==i
	public static void main(String args[]) throws IOException {
		
		for (String x : vars) {
			modMap.put(x, new long[7]); // values 0...6 of (value, num occurrences)
		}
		BufferedReader br = new BufferedReader(new FileReader("bgm.in"));
		int N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String var = st.nextToken();
			int val = Integer.parseInt(st.nextToken());
			modMap.get(var)[(int)fit(val)]++;
		}
		br.close();
		
//		System.out.println(modMap);
		
		long count = 0;
		long[] Bs = modMap.get("B");
		long[] Es = modMap.get("E");
		long[] Ss = modMap.get("S");
		long[] Is = modMap.get("I");
		long[] Gs = modMap.get("G");
		long[] Ms = modMap.get("M");
		long[] Os = modMap.get("O");
		
		for (int b = 0; b < 7; b++) {
			for (int e = 0; e < 7; e++) {
				for (int s = 0; s < 7; s++) {
					for (int i = 0; i < 7; i++) {
						for (int g = 0; g < 7; g++) {
							for (int o = 0; o < 7; o++) {
								for (int m = 0; m < 7; m++) {
									long bessie = fit(b+e+s+s+i+e);
									long goes = fit(g+o+e+s);
									long moo = fit(m+o+o);
									if (fit(bessie*goes*moo)==0) {
										count+=Bs[b]*Es[e]*Ss[s]*Is[i]*Gs[g]*Os[o]*Ms[m];
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println(count);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("bgm.out")));

		pw.println(count);
		pw.close();
	}
	public static long fit(long a) {
		a %= 7;
		while (a < 0) a+=7;
		return a;
	}
	public static class Pair {
		int modVal;
		int numOccur;

		public Pair(int a, int b) {
			modVal = a;
			numOccur = b;
		}
		public String toString() {
			return modVal + " " + numOccur;
		}
	}
}


