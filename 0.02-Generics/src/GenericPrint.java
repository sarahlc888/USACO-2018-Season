public class GenericPrint {

	public static void main(String[] args) {

		Integer[] A = {1, 2, 3, 4, 5};
		
		Double[] B = {1.1, 2.2, 3.3, 4.4, 5.5};
		
		String[] C = {"hello", "world", "I'm fine."};
		
		printArray(A);
		
		printArray(B);

		printArray(C);
	}

	/*
	public static void printArray(int[] A) {
		for(int i=0; i<A.length; i++)
			System.out.println(A[i]);
	}
	
	public static void printArray(double[] A) {
		for(int i=0; i<A.length; i++)
			System.out.println(A[i]);
	}
	
	public static void printArray(String[] C) {
		for(String s : C)
			System.out.println(s);
	}
	*/
	
	public static <T> void printArray(T[] arr) {
		for(int i=0; i<arr.length; i++)
			System.out.println(arr[i]);
		System.out.println();
	}
}