
public class MySet {
	private int[] arr;
	private int id;
	
	MySet() {
		arr = new int[10000];
		id = 0;
	}
	
	public void add(int x) {
		boolean included = false;
		for (int i = 0; i < id; i++) {
			if (arr[i] == x) {
				included = true;
				break;
			}
		}
		if (!included) {
			arr[id] = x;
			id++;
		}
	}
	
	public int pop() {
		int ret = arr[0];
		for (int i = 1; i < id; i++) {
			arr[i-1] = arr[i];
		}
		id--;
		return ret;
	}
	public int peek() {
		return arr[id-1];
	}
	public int size() {
		return id;
	}
	public boolean isEmpty() {
		return (id == 0);
	}
	
	public static void main(String[] args) {
		
		// create an object of MyQueue and test its performance
		
		
		
		
	}
}
