import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

public class HashMapComparison {
	
	private static final int dim = 5;
	private Map<Integer, String> map1;
	private Map<Integer, String> map2;
			
	public HashMapComparison() {
		map1 = new HashMap<Integer,String>();
		map2 = new HashMap<Integer,String>();
	}
	
	public static void populateMap(Map<Integer, String> map) {
		Random r = new Random();
		for (int i = 0; i < dim; i++) {
			int val = i;
			map.put(val, String.valueOf(val*2));
		}
	}
	
	public static <A, B> boolean compareMaps(Map<A, B> map1, Map<A, B> map2) {
		
		java.util.Iterator<Entry<A, B>> i1;
		
		Set<Entry<A, B>> set1= map1.entrySet();
		Set<Entry<A, B>> set2= map1.entrySet();
		i1 = set1.iterator();
		
		if (set1.size() != set2.size())
			return false;
				
		boolean found = true;
		//map entry equals nu e suprascris -> solutie 2 foruri
		for (Map.Entry<A, B> e1 : map1.entrySet()) {
			found = false;
			for (Map.Entry<A, B> e2 : map2.entrySet()) {
				if ((e1.getKey().equals(e2.getKey())) && (e1.getValue().equals(e2.getValue())))
					found = true;
			}
			if (!found)
				break;
		}
		
		return found;		
	}
	
	public static void main(String[] args) {
		
		HashMapComparison hmc = new HashMapComparison();
		
		populateMap(hmc.map1);
		populateMap(hmc.map2);
		System.out.println(hmc.map1);
		System.out.println(hmc.map2);
		System.out.println(hmc.compareMaps(hmc.map1, hmc.map2));
	}
}
