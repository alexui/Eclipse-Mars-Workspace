import java.util.ArrayList;
import java.util.List;

public class GenericPerson {

	static <T extends Person> void printPerson(List<T> l) {
		for (Person p : l) {
			System.out.println(p.getNume());
			System.out.println(p.getVarsta());
		}
	}
	
	public static void main(String[] args) {
		
		List<Client> lista = new ArrayList<Client>();
		lista.add(new Client("Mircea", 20));
		lista.add(new Client("Sile", 22));
		lista.add(new Client("Piti", 11));
		lista.add(new Client("Motrea", 44));
		lista.add(new Client("Sorsa", 11));
		lista.add(new Client("Sugar", 4));
		
		printPerson(lista);
	}
}
