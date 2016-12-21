
public class A {
	
	static {
		System.out.println("static from class A");
	}
	
	{
		System.out.println("nonstatic from A");
	}
	
	public A() {
		System.out.println("constructor from class A");
	}
	
	public void x() {
		System.out.println("methodX from class A");
	}
	
	public static void y() {
		System.out.println("static methodY from class A");
	}
}
