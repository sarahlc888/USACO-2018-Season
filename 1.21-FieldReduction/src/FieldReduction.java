import java.io.*;
import java.util.*;

// 1/10 test cases

public class FieldReduction {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("reduce.in"));
		int N = Integer.parseInt(br.readLine()); // number of cows
		
		
		ArrayList<Point> cows = new ArrayList<Point>();
		for (int i = 0; i < N; i++) { // scan everything in
			StringTokenizer st = new StringTokenizer(br.readLine());
			cows.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
			//System.out.println(cows.get(i).x+", " + cows.get(i).y);
		}
		br.close();
		
		Collections.sort(cows);
		
		/*
		for (int i = 0; i < N; i++) { 
			System.out.println(cows.get(i).x+", " + cows.get(i).y);
		}*/
		
		int minArea = Integer.MAX_VALUE;
		
		
		
		// loop through all four sides and kick off from 0 to 4 cows
		for (int i = 0; i <= 3; i++) {
			// kick from 0 to 3 cows from the left side
			
			//ArrayList<Point> curcows = (ArrayList<Point>) cows.clone();
			// a deep copy of cows to return to each time
			ArrayList<Point> curcows = new ArrayList<Point>(cows.size());
			for (int n = 0; n < cows.size(); n++) {
				curcows.add(new Point(cows.get(n).x, cows.get(n).y));
			}
			
			
			for (int n = 0; n < i; n++) { // kick off n cows
				curcows = deleteCow(0, curcows);
			}
			
			for (int j = 0; j <= 3-i; j++) {
				ArrayList<Point> curcows2 = new ArrayList<Point>(curcows.size());
				for (int n = 0; n < curcows.size(); n++) {
					curcows2.add(new Point(curcows.get(n).x, curcows.get(n).y));
				}
				
				for (int n = 0; n < j; n++) { // kick off n cows
					curcows2 = deleteCow(1, curcows2);
				}
				
				for (int k = 0; k <= 3-i-j; k++) {
					//ArrayList<Point> curcows3 = (ArrayList<Point>) curcows2.clone();
					
					ArrayList<Point> curcows3 = new ArrayList<Point>(curcows2.size());
					for (int n = 0; n < curcows2.size(); n++) {
						curcows3.add(new Point(curcows2.get(n).x, curcows2.get(n).y));
					}
					
					
					for (int n = 0; n < k; n++) { // kick off n cows
						curcows3 = deleteCow(2, curcows3);
					}
					
					for (int m = 0; m <= 3-i-j-k; m++) {
						// to make faster, set m to the highest possible value that will yield the min size
						
						//System.out.println("i: " + i + ", j: " + j + ", k: " + k + ", m: " + m);
						
						//ArrayList<Point> curcows4 = (ArrayList<Point>) curcows3.clone();
						ArrayList<Point> curcows4 = new ArrayList<Point>(curcows3.size());
						for (int n = 0; n < curcows3.size(); n++) {
							curcows4.add(new Point(curcows3.get(n).x, curcows3.get(n).y));
						}
						
						for (int n = 0; n < m; n++) { // kick off n cows
							curcows4 = deleteCow(3, curcows4);
						}
						
						for (int n = 0; n < curcows4.size(); n++) {
							//System.out.println(curcows4.get(n).x + ", " + curcows4.get(n).y);
						}
						
						Collections.sort(curcows4);
						int minx = curcows4.get(0).x;
						int maxx = curcows4.get(curcows4.size() - 1).x;
						for (int p = 0; p < curcows4.size(); p++) {
							curcows4.get(p).reverse();
						}
						Collections.sort(curcows4);
						int miny = curcows4.get(0).x;
						int maxy = curcows4.get(curcows4.size() - 1).x;
						for (int p = 0; p < curcows4.size(); p++) {
							curcows4.get(p).reverse();
						}
						//System.out.println("minx: " + minx);
						//System.out.println("maxx: " + maxx);
						//System.out.println("miny: " + miny);
						//System.out.println("maxy: " + maxy);
						
						int area = Math.abs(maxx - minx)*Math.abs(maxy-miny);
						
						if (area < minArea) {
							minArea = area;
							//System.out.println("NEW MIN! " + area);
						}
						//System.out.println();
					}
				}
			}
			
		}
		System.out.println(minArea);
		PrintWriter pw = new PrintWriter(new File("reduce.out"));
		pw.println(minArea);
		pw.close();
	}
	public static ArrayList<Point> deleteCow(int dir, ArrayList<Point> curCows) {
		
		if (dir == 0) {
			// delete one from the left side
			Collections.sort(curCows);
			curCows.remove(0);
			
		} else if (dir == 1) {
			// delete one from the bottom
			for (int i = 0; i < curCows.size(); i++) {
				curCows.get(i).reverse();
			}
			Collections.sort(curCows);
			// then pick a cow (the y coordinates are first now)
			curCows.remove(0);
			
			for (int i = 0; i < curCows.size(); i++) {
				curCows.get(i).reverse();
			}
			Collections.sort(curCows);
		} else if (dir == 2) {
			// delete one from the right side
			Collections.sort(curCows);
			curCows.remove(curCows.size() - 1);

		} else if (dir == 3) {
			// delete one from the top
			for (int i = 0; i < curCows.size(); i++) {
				curCows.get(i).reverse();
			}
			Collections.sort(curCows);
			// then pick a cow (the y coordinates are first now)
			curCows.remove(curCows.size() - 1);

			for (int i = 0; i < curCows.size(); i++) {
				curCows.get(i).reverse();
			}
			Collections.sort(curCows);
		}
		return curCows;
	}
	public static class Point implements Comparable<Point> {
		int x;
		int y;
		
		public Point(int rin, int cin) {
			x = rin;
			y = cin;
		}
		public int compareTo(Point p) {
			if (y < p.y) {
				return 1;
			} else if (y < p.y) {
				return -1;
			} else if (x > p.x) {
				return 1;
			} else {
				return -1;
			}
		}
		public void reverse() {
			int temp = x;
			x = y;
			y = temp;
		}
		
	}
}
