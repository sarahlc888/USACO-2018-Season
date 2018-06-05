import java.io.IOException;

public class MyStack {
	private int[] arr;
	private int id;

	MyStack() {
		arr = new int[10000];
		id = 0;
	}
	public void push(int x) {
		arr[id] = x;
		id++;
	}
	public int pop() {
		id--;
		return arr[id];
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
		MyStack s = new MyStack();
	}
}