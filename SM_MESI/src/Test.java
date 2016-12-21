import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	
	final static String read = "Rd";
	final static String write = "Wr";
	final static String fileName = "src/input";
	
	public static void main(String[] args) throws FileNotFoundException {
			
		int nP;
		ArrayList<String> sequence = new ArrayList<String>();
		ArrayList<Processor> processors = new ArrayList<Processor>();
		
		Scanner s = new Scanner(new File(fileName));
		nP = s.nextInt();
		System.out.println(nP + " processors");
		
		while (s.hasNext()) {
			sequence.add(s.next());
		}
		
		System.out.println("seq:" + sequence);
		
		for (int i = 0; i < 3; i++)
			processors.add(Processor.getNextProccesor());
		
		Cache cache = Cache.getInstance();
		
		//header
		System.out.printf("\n%10s|%20s|","t","Actiune procesor");
		for (int i = 0; i < nP; i++) {
			System.out.printf("  Stare P%d|",i+1);
		}
		System.out.printf("%20s|%15s|\n","Actiune magistrala","Sursa date");
		
		
		//linia 0
		System.out.printf("%10s|%20s|", "t0", "initial");
		
		for (int i = 0; i < processors.size(); i++)
			System.out.printf("%10s|",processors.get(i).pState);
		
		System.out.printf("%20s|%15s|\n",cache.busState,cache.dataSource);
		
		//secventa
		for (int k = 0; k < sequence.size(); k++) {
			
			int pName = Integer.valueOf(sequence.get(k).replaceAll("\\D+", ""));
			Processor p = processors.get(pName-1);
			
			String action = sequence.get(k).replaceAll("P\\d+", "");
			if (action.compareTo(read) == 0)
				p.visitRead(cache);
			if (action.compareTo(write) == 0)
				p.visitWrite(cache);
			
			System.out.printf("%10s|%20s|","t" + (k+1),sequence.get(k));
			for (int i = 0; i < processors.size(); i++)
				System.out.printf("%10s|",processors.get(i).pState);
			
			System.out.printf("%20s|%15s|\n",cache.busState,cache.dataSource);
			
			s.close();
		}
	}
}
