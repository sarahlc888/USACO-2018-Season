public class GenericContainer<T> {
	T data;

	GenericContainer() {
		data = null;
	}

	GenericContainer(T t) {
		data = t;
	}

	public T getData() {
		return data;
	}

	public void setData(T t) {
		data = t;
	}

	// other methods ...

	public static void main(String[] args) {

		GenericContainer<Integer> A = new GenericContainer<Integer>();
		A.setData(2017);
		System.out.println( A.getData() );

		
		GenericContainer<String> B = new GenericContainer<String>();
		B.setData("Hello");
		System.out.println(B.getData());
		
	}

}