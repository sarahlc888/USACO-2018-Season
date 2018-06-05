import java.lang.reflect.Array;

public class GenericQueue<T> {
		
	private T[] arr;
	private int id;
	
	GenericQueue(Class<T> c) {
		arr = (T[]) Array.newInstance(c, 10000);
		// arr = new T[10000]; // doesn't work
		id = 0;
	}
	
	public void add(T x) {
		arr[id] = x;
		id++;
	}
	
	public T pop() {
		T ret = arr[0];
		for (int i = 1; i < id; i++) {
			arr[i-1] = arr[i];
		}
		id--;
		return ret;
	}
	public T peek() {
		return arr[id-1];
	}
	public int size() {
		return id;
	}
	public boolean isEmpty() {
		return (id == 0);
	}
	
	public static void main(String[] args) {
		
		
	}

}