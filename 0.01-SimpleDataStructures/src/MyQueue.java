public class MyQueue {
		
	private int[] arr;
	private int id;
	
	MyQueue() {
		arr = new int[10000];
		id = 0;
	}
	
	public void add(int x) {
		arr[id] = x;
		id++;
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
		MyQueue q = new MyQueue();
		
		int N = 10000;
		int[] testNum = new int[N];
		for (int i = 0; i < N; i++) {
			testNum[i] = (int) (Math.random() * 500);
			q.add(testNum[i]);
		}
		
		int[] outNum = new int[N];
		for (int i = 0; i < N; i++) {
			outNum[i] = q.pop();
		}
		
		boolean eq = true;
		
		for (int i = 0; i < N; i++) {
			if (testNum[i] != outNum[i]) {
				eq = false;
				break;
			}
		}
		
		if (eq) {
			System.out.println("equal");
		} else {
			System.out.println("no");
		}
		
		
		
		
	}

}