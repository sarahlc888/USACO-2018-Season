import java.lang.reflect.Array;

public class GenericStack<T> {
	private T[] arr;
	private int id;

	GenericStack(Class<T> c) {
		arr = (T[]) Array.newInstance(c, 10000);
		id = 0;
	}
	public void push(T x) {
		arr[id] = x;
		id++;
	}
	public T pop() {
		id--;
		return arr[id];
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
		Integer a = 7;
		GenericStack s = new GenericStack(a.getClass());
		Integer b = 17;
		s.push(b);
		System.out.println(s.pop());
	}
}