import java.util.*;
/*
 * it all seems to work now
 * was having trouble with this stuff earlier
 */
public class setComp {
	public static void main(String[] args) {
		TreeSet<Point> t = new TreeSet<Point>(edgeComparator);
		t.add(new Point(1, 2));
		t.remove(new Point(1, 2));
		System.out.println(t);
	}
	public static class Point {
		int x;
		int y;
		public Point(int a, int b) {
			x = a;
			y = b;
		}
	}
	public static Comparator<Point> edgeComparator = new Comparator<Point>(){

		@Override
		public int compare(Point a, Point b) {
			if (a.x == b.x) {
				return a.y - b.y;
			} 
			return a.x-b.x;
		}
	};
}
