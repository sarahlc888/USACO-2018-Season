import java.io.*;
import java.util.*;

/*
 * doesn't work
 */
public class LightsOut {
	static int L;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken()); // number of lights
		int T = Integer.parseInt(st.nextToken()); // length of pitchfork
		String s = br.readLine().trim(); // lights
		String p = br.readLine().trim(); // pitchfork
		long a = Long.parseLong(s, 2);
		//int b = Integer.parseInt(p, 2);
		
		Set<Long> visited = new TreeSet<Long>();
		visited.add(a);
		
		LinkedList<Pair> list = new LinkedList<Pair>();
		
		list.add(new Pair(s, 0));
		
		int prevNumLights = countLights(s);
		
		int max = 0;
		
		while (!list.isEmpty()) {
			Pair p1 = list.removeFirst();
			//System.out.println("pair: " + p1);
			String curS = p1.x;
			int numLights = countLights(curS);
			
			max = Math.max(p1.y, max);
			
			if (numLights > prevNumLights) {
				//System.out.println("cont");
				continue;
			}
			
			for (int i = 0; i < curS.length()-T; i++) {
				
				String nextS = new String(curS); // pressing at each point
				for (int j = 0; j < T; j++) {
					
					if (p.charAt(j) == '1') { // if there's a prong there
						if (nextS.charAt(i+j) == '1') { // if it's on, turn off
						//	System.out.println("off");
							nextS = nextS.substring(0, i+j) + "0" + nextS.substring(i+j+1);
						} else {
						//	System.out.println("on");
							nextS = nextS.substring(0, i+j) + "1" + nextS.substring(i+j+1);
						}
					}
				}
			//	System.out.println(nextS);
				if (!visited.contains(Long.parseLong(nextS, 2))) {
					visited.add(Long.parseLong(nextS, 2));
					list.add(new Pair(nextS, p1.y+1));
				}
				
//				if (!visited[Integer.parseInt(nextS, 2)]) {
//					visited[Integer.parseInt(nextS, 2)] = true;
//					list.add(new Pair(nextS, p1.y+1));
//				}
			}
			prevNumLights = numLights;
		}
		
		System.out.println(max);
	}
	public static class Pair {
		String x; 
		int y; // num pitchfork uses

		public Pair(String a, int b) {
			x = a;
			y = b;
		}
		public String toString() {
			return "pos: " + x + "  p uses: " + y ;
		}
	}
	public static int countLights(String s) {
		int n = 0;
		for (int i = 0; i < L; i++) {
			if (s.charAt(i) == '1') n++;
		}
		return n;
	}
}
