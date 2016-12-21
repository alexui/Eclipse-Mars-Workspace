
public class Main {

	public static void main(String[] args) {
		//polimorfism - pot sa asignez unui obiect de tip A un obiect derivat din A
		
		//compilere .java -> .class
		
		A xxx ;//= new B(); //la runtime - dynamic binding
		//xxx.x();
		A.y();
		
		Point2D p = new Point2D(10, 20);
		System.out.println(p);
	}
}
