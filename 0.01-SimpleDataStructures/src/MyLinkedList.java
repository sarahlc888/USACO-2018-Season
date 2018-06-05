import java.lang.reflect.Array;

// doesn't really work...
public class MyLinkedList<T> {
	public static class Node<T> {
		T val;
		int next;
		public Node(T v) {
			val = v;
		}
		public void setN(int n) {
			next = n;
		}
	}
	
	private Node[] arr;
	private int id; // id of the next empty space
	private int n; // the number of elements in the list so far
	private int startval = 0;
	
	MyLinkedList() {
		arr = new Node[10000];
		id = 0;
		n = 0;
	}
	public Node retrieveNode(int num) {
		int steps = 0;
		int curID = -10;
		while (steps < num) {
			Node current = arr[Math.max(startval, curID)];
			curID = current.next;
			System.out.println("curID: " + curID);
			steps++;
		}
		return arr[curID];
	}
	public int retrieveInd(int num) {
		int steps = 0;
		int curID = -10;
		while (steps < num) {
			Node current = arr[Math.max(startval, curID)];
			curID = current.next;
			steps++;
		}
		return curID;
	}
	
	public void add(T v) {
		
		arr[id] = new Node(v);
		if (n != 0) { // if it's not the first, then rig up the past connection
			arr[retrieveInd(n)].next = id;
		}
		
		id++;
		n++;
	}
	public T pop() {
		T ret = (T) retrieveNode(n).val;
		System.out.println("n: " + n);
		n--;
		return ret;
	}
	public void insert(T v, int pos) {
		arr[pos-1].next = id;
		arr[id] = new Node(v);
		arr[id].next = pos+1;
		id++;
		n++;
	}
	public void delete(int pos) {
		arr[pos-1].next = pos+1;
		n--;
	}
	public static void main(String[] args) {
		MyLinkedList l = new MyLinkedList();
		Integer a = 9;
		Integer b = 10;
		Integer c = 11;
		l.add(a);
		l.add(c);
		l.insert(b, 1);
		System.out.println(l.pop());
		System.out.println(l.pop());
		System.out.println(l.pop());
	}
	
}
