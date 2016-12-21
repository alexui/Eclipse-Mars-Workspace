import java.util.ArrayList;
import java.util.HashSet;

public class Colectii {

	public static void main(String[] args) {
		ArrayList<String> al = new ArrayList<String>();
		al.add(new String("salut"));
		al.add(new String("salut"));
		al.add(new String("salut"));
		al.add("bla");
		al.add("bla");
		System.out.println(al);
		
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(al);
		System.out.println(hs);
	}
}
