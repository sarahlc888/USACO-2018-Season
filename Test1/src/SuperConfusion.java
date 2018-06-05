// clearing things up for super constructors
// will either auto sup or needs explicit
public class SuperConfusion {
	public static class Plant {
		boolean flower;
		public Plant() {
			flower = true;
		}
		
	}
	public static class Tree extends Plant {
		int rings;
		
		/*public Tree() {
			super();
			rings = 7;
		}
		public Tree(int r) {
			super();
			rings = r;
		}
*/
		
	}
	public static void main(String args[]) {
		Plant pine = new Tree();
		System.out.println(pine.flower);
		System.out.println(((Tree) pine).rings);
	}
}
