// generic method that will find the maximum value of a two-dimensional array of any primitive type.
public class GenericComp {
	public static <T extends Comparable<T>> T maxArr(T[][] a) {
		int X = a.length;
		int Y = a[0].length;
		T max = a[0][0];
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++) {
				
				if (a[i][j].compareTo(max) > 0) {
					max = a[i][j];
				}
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		Integer[][] test = {{1, 2, 3}, {4, 5, 6}};
		System.out.println(maxArr(test));
		
	}
	
}
