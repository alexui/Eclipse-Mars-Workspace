import java.util.ArrayList;
import java.util.List;

public class Generics {
	
	static void printList(List<?> lista) {
		for (Object o : lista) {
			System.out.println(o);
		}
	}
	
	static <T> void printTList(List<T> lista) {
		for (T o : lista) {
			System.out.println(o);
		}
	}
	
	static void print(Object o) {
		System.out.println(o);
	}
	
	public static void main(String[] args) {
		
		List<String> l = new ArrayList<>();
		l.add("111");
		l.add("222");
		l.add("333");
	//	l.add(222);
		printList(l);
		
		printTList(l);
		
		print("aaa"); // merge pentru ca String e un subtip de Object
		//nu inseamna ca List<String> e un subtip a lui String<Object>
	}
}
