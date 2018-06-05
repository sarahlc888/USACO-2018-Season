
public class ParBal {
	public static void main(String[] args) {
		
	}
	public static boolean bal(String s) {
		if (s.length()%2 != 0) {
			return false;
		}
		if (s.length() == 0) {
			return true;
		}
		
		String s2 = s.substring(1, s.length()-1);
		return bal(s2);
	
		
	}
}
