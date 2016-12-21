
public class Increment {
	public static void main(String[] args) {
		Integer i = 10;
		Integer j = 11;
		Integer k = ++i;
		Integer f = new Integer(11);
		System.out.println("k == j is " + (k==j));
		System.out.println("k.equals(j) is " + k.equals(j));
		System.out.println("k == f is " + (k==f));
		System.out.println("k.equals(f) is " + k.equals(f));
	}
}
