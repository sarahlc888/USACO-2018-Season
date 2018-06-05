import java.util.Arrays;

public class FamilyMembers {
	
	public static class Member {
		Member mother;
		Member father;
		Member[] children;
		String name;
		int age;
		Member spouse;
		int numChildren;
		
		public Member(String nameIn, int ageIn) {
			name = nameIn;
			age = ageIn;
			children = new Member[100];
			numChildren = 0;
		}
		public void getMarried(Member sp) {
			spouse = sp;
			sp.spouse = this;
			
		}
		public String toString() {
			return ("mother: " + (mother.name) + "\nfather: " + father.name + "\n children: " + Arrays.toString(children) + "\nname: " + name + "\nage: " + age + "\nspouse " + spouse.name);
			//return ("mother: " + mother.name);
		}
		
		public Member haveChild(Member spouse, String name) {
			Member child = (new Member("name", 0));
			child.father = this;
			child.mother = spouse;
			
			this.children[numChildren] = child;
			spouse.children[spouse.numChildren] = child;
			numChildren++;
			spouse.numChildren++;
			
			return child;
		}
		
		
		
	}
	public static void main(String[] args) {
		
		Member m1 = new Member("John", 27);
		Member m2 = new Member("Jay", 27);
		
		m1.getMarried(m2);
		m1.mother = new Member("Julia", 50);
		m1.father = new Member("James", 50);
		System.out.println(m1);
		System.out.println();
		Member m3 = m1.haveChild(m2, "Jo");
		
		System.out.println(m3);
		
	}

}
