
public class Zero {

	public static void main(String[] args) {
		
		String s = new String("atom");
		String p = s;
		System.out.println(p == s);
		s+="aa";  // se creaza un alt obiect se pierde referinta catre cel vechi
		System.out.println(s);
		System.out.println("p==s: "+(p == s));
		
		Person p1 = new Person("Alexey", 22);
		Person p2 = new Person("Alexey", 22);
		
		System.out.println("p1==p2:"+(p1 == p2));
		
		//p2.setNume("Codrin");
		
		System.out.println("p1==p2:"+(p1 == p2));
		System.out.println("p1.equals(p2):" + p1.equals(p2));
	}
}
