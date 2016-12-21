
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Intersection {
	
	public static void main(String[] args) {
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(1);
		a1.add(2);
		a1.add(3);
		
		Integer v1[] = {1, 2 ,3}; //daca pun int nu merge
		Integer v2[] = {10, 20, 3};
		
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(10);
		a2.add(20);
		a2.add(3);
		
		System.out.println(a1);
		System.out.println(a2);
		ArrayList<Integer> a3= new ArrayList<Integer>();
		a1.retainAll(a2);
		System.out.println(a1);
		
		List<Integer> l = new ArrayList<Integer>(Arrays.asList(v1));
		System.out.println(l);
		l.retainAll(Arrays.asList(v2));
		System.out.println(l);
		
	}
}
