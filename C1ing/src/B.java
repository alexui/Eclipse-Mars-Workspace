
public class B extends A{
	
	static {
		System.out.println("static from class B");
	}
	
	{
		System.out.println("nonstatic from B");
	}

	public B() {
		//super e implicit
		System.out.println("constructor from class B");
	}

	
	public void x() {
		System.out.println("methodX from class B");
	}
}
