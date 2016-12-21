import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

class Persoana implements Serializable{
	
	private static final long serialVersionUID = 2898510743120159917L;
	private String nume;
	private String prenume;
	private String email;
	private String nrtelefon;
	private Adresa adresa;
	
	public HashMap<String, Object> updates;
	
	public Persoana(String nume, String prenume, String email, String nrtelefon, Adresa adresa) {
		super();
		this.nume = nume;
		this.prenume = prenume;
		this.email = email;
		this.nrtelefon = nrtelefon;
		this.adresa = adresa;
		this.updates = new HashMap<String, Object>();
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
		updates.put("nume", "'"+nume+"'");
	}

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
		updates.put("prenume", "'"+prenume+"'");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		updates.put("email", "'"+email+"'");
	}

	public String getNrtelefon() {
		return nrtelefon;
	}

	public void setNrtelefon(String nrtelefon) {
		this.nrtelefon = nrtelefon;
		updates.put("nrtelefon", "'"+nrtelefon+"'");
	}

	public Adresa getAdresa() {
		return adresa;
	}
	
	public int getAdresaId(HashMap<Integer, Adresa> hm) {
		int id = 1;
		for (Integer i : hm.keySet())
			if (adresa.equals(hm.get(i))) {
				id = i;
				break;
			}
		return id;
	}

	public void setAdresa(Adresa adresa, HashMap<Integer, Adresa> hm) {
		this.adresa = adresa;
	
		for (Integer i : hm.keySet())
			if (hm.get(i) == adresa) {
				updates.put("adresaid", String.valueOf(i));
				break;
			}
	}
		
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Persoana p = (Persoana)obj;
		return (this.nume.equals(p.nume) &&
				this.prenume.equals(p.prenume) &&
				this.email.equals(p.email) &&
				this.nrtelefon.equals(p.nrtelefon) &&
				this.adresa.equals(p.adresa)
				);
	}

	@Override
	public String toString() {
		return nume+" "+prenume+" "+email+" "+nrtelefon+"\n"+adresa;
	}
}

class Angajat extends Persoana implements Serializable{
		
	private static final long serialVersionUID = -5774514852953194975L;
	private int marca;
	
	public HashMap<String, Object> updates;
		
	public Angajat(String nume, String prenume, String email, String nrtelefon, Adresa adresa, int marca) {
		super(nume, prenume, email, nrtelefon, adresa);
		this.marca = marca;
		updates = new HashMap<String, Object>();
	}
 
	public int getMarca() {
		return marca;
	}
		
	public void setMarca(int marca) {
		this.marca = marca;
		updates.put("marca", marca);
	}
		
	@Override
	public String toString() {
		return super.toString()+" "+marca+"\n";
	}
}

class Adresa implements Serializable{
	
	private static final long serialVersionUID = 8001817233244469758L;
	private String strada;
	private int nr;
	private String bl;
	private String scara;
	private byte apartament;
	
	public HashMap<String, Object> updates;
	
	public Adresa(String strada, int nr, String bl, String scara, byte apartament) {
		super();
		this.strada = strada;
		this.nr = nr;
		this.bl = bl;
		this.scara = scara;
		this.apartament = apartament;
		updates = new HashMap<String, Object>();
	}

	public String getStrada() {
		return strada;
	}

	public void setStrada(String strada) {
		this.strada = strada;
		updates.put("strada", "'"+strada+"'");
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
		updates.put("nr", nr);
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
		updates.put("bl", "'"+bl+"'");
	}

	public String getScara() {
		return scara;
	}

	public void setScara(String scara) {
		this.scara = scara;
		updates.put("scara", "'"+scara+"'");
	}

	public byte getApartament() {
		return apartament;
	}

	public void setApartament(byte apartament) {
		this.apartament = apartament;
		updates.put("apartament", apartament);
	}	
	
	@Override
	public boolean equals(Object obj) {
		Adresa a = (Adresa)obj;
		return (this.apartament == a.apartament &&
				this.nr == a.nr &&
				this.bl.equals(a.bl) &&
				this.scara.equals(a.scara) &&
				this.strada.equals(a.strada)
				);
	}

	@Override
	public String toString() {
		return strada+" "+" "+nr+" "+bl+" "+scara+" "+apartament+"\n";
	}
}

public class DBConnect {
	
	static final int PERSOANA_SIZE = 10000;
	static final int ADRESA_SIZE = 10000;
	static final String PREFIX = "074";
	static final String NUM = "0123456789";
	static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	
	static HashMap<Integer, Persoana> persoanaList;
	static HashMap<Integer, Angajat> angajatList;
	static HashMap<Integer, Adresa> adresaList;
	
	static final String PERSOANA_FILE = "persoana.data";
	static final String ANGAJAT_FILE = "angajat.data";
	static final String ADRESA_FILE = "adresa.data";
	
	static ArrayList<String> threadFiles = new ArrayList<>();
	static final String THREAD_FILE = "thread"; // persoane, angajati, adresa
	
	private static String randomString( int len , String string){
	   StringBuilder sb = new StringBuilder( len );
	   int length = string.length();
	   for( int i = 0; i < len; i++ ) 
	      sb.append( string.charAt(rnd.nextInt(length)));
	   return sb.toString();
	}
	
	public static Connection connectToDb() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/";
		String database = "ingtema1";
		String userName = "root";
		String password = "root";
		return DriverManager.getConnection(url + database, userName, password);
	}
	
	public static void createDatabase() {
		System.out.println("Creating database");
		
		Connection c = null;
		Statement s = null;
		
		try {
			c = connectToDb();
			System.out.println("Connection successful");
			s = c.createStatement();
			
			String sql;
			sql = "drop table persoana if exists" ;
			
			sql = "create table persoana ...";
			// TODO
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteDatabase() {
		//TODO
	}
	
	public static void insertValues() {	
		System.out.println("Inserting values");
		
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = connectToDb();
			System.out.println("Connection successful");
			s = c.createStatement();
			
			int len;
			boolean angajat;
			int num_angajati = 0;
			
			String sql;
			String nume;
			String prenume;
			String email;
			String nrtelefon;
			int adresaid;
			int marca;
			int angajatid = 0;
			
			String strada;
			int nr;
			String bl;
			String scara;
			byte apt;
			
			try {
				sql = "ALTER TABLE angajat DROP FOREIGN KEY fk1;";
				s.executeUpdate(sql);
			} catch (MySQLSyntaxErrorException e) {
				System.out.println("Could not drop constraint angajat.");
			}
			try {
				sql = "ALTER TABLE persoana DROP FOREIGN KEY fk2;";
				s.executeUpdate(sql);
			} catch (MySQLSyntaxErrorException e) {
				System.out.println("Could not drop constraint persoana.");
			}
			
			sql = "TRUNCATE angajat";
			s.executeUpdate(sql);
						
			sql = "TRUNCATE persoana";
			s.executeUpdate(sql);
			
			sql = "ALTER TABLE angajat ADD CONSTRAINT fk1 FOREIGN KEY (id) REFERENCES persoana(id);";
			s.executeUpdate(sql);
						
			sql = "TRUNCATE adresa";
			s.executeUpdate(sql);
			
			sql = "ALTER TABLE persoana ADD CONSTRAINT fk2 FOREIGN KEY (adresaid) REFERENCES adresa(adresaid);";
			s.executeUpdate(sql);
			
			for (int i = 0; i < ADRESA_SIZE; i++) {
				len = 5 + rnd.nextInt(10);
				strada = randomString(len, AB);
				nr = rnd.nextInt(10);
				bl = String.valueOf(rnd.nextInt(80));
				scara = randomString(1, AB);
				apt = (byte) rnd.nextInt(100);
				sql = "INSERT INTO adresa" +
						" VALUES (default,'"+ strada + "',"+ nr + 
						",'"+ bl + "','"+ scara + "',"+ apt + ");";
				//System.out.println(sql);
				s.executeUpdate(sql);
			}
			System.out.printf("Inserted %d rows in adresa.\n", ADRESA_SIZE);
			
			for (int i = 0; i < PERSOANA_SIZE; i++) {
				len = 5 + rnd.nextInt(5);
				nume = randomString(len, AB);
				len = 5 + rnd.nextInt(5);
				prenume = randomString(len, AB);
				len = 5 + rnd.nextInt(5);
				email = randomString(len, AB);
				email += "@yahoo.com";
				nrtelefon = PREFIX+randomString(7, NUM);
				adresaid = rnd.nextInt(ADRESA_SIZE) + 1;
				sql = "INSERT INTO persoana" +
						" VALUES (default,'"+ nume + "','"+ prenume + 
						"','"+ email + "','"+ nrtelefon + "',"+ adresaid + ");";
				//System.out.println(sql);
				s.executeUpdate(sql);
				
				angajat = rnd.nextBoolean();
				if (angajat) {
					marca = rnd.nextInt(10000);
					sql = "SELECT * FROM persoana ORDER BY id DESC LIMIT 1";
					rs = s.executeQuery(sql);
					while (rs.next())
						angajatid = rs.getInt(1);
					sql = "INSERT INTO angajat" +
							" VALUES ("+ angajatid +","+marca+");";
					s.executeUpdate(sql);
					num_angajati++;
				}
				
			}
			System.out.printf("Inserted %d rows in persoana.\n", PERSOANA_SIZE);
			System.out.printf("Inserted %d rows in angajati.\n", num_angajati);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void retrieveValues() {
		
		System.out.println("Retrieving values");
		
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
						
		try {
			c = connectToDb();
			System.out.println("Connection successful");
			s = c.createStatement();
			
			persoanaList = new HashMap<Integer, Persoana>();
			adresaList = new HashMap<Integer, Adresa>();
			angajatList = new HashMap<Integer, Angajat>();
			
			String sql;
			
			sql = "SELECT * FROM adresa;";
			rs = s.executeQuery(sql);
			while (rs.next()) {
				adresaList.put(rs.getInt("adresaid"), new Adresa(
						rs.getString("strada"), 
						rs.getInt("nr"), 
						rs.getString("bl"), 
						rs.getString("scara"), 
						rs.getByte("apartament")));
			}
			System.out.println("Retrieved "+adresaList.size()+" elements from adresa.");
			
			sql = "SELECT p.* FROM persoana p LEFT JOIN angajat a ON p.id = a.id WHERE a.id IS NULL;";
			rs = s.executeQuery(sql);
			while (rs.next()) {
				persoanaList.put(rs.getInt("id"), new Persoana(
						rs.getString("nume"), 
						rs.getString("prenume"), 
						rs.getString("email"), 
						rs.getString("nrtelefon"),
						adresaList.get(rs.getInt("adresaid"))));
			}
			
			sql = "SELECT * FROM persoana p LEFT JOIN angajat a ON p.id = a.id WHERE a.id IS NOT NULL;";
			rs = s.executeQuery(sql);
			while (rs.next()) {
				
				Angajat a = new Angajat(
						rs.getString("nume"), 
						rs.getString("prenume"), 
						rs.getString("email"), 
						rs.getString("nrtelefon"),
						adresaList.get(rs.getInt("adresaid")),
						rs.getInt("marca"));
				
				angajatList.put(rs.getInt("id"), a);
				persoanaList.put(rs.getInt("id"), a);
			}
			System.out.println("Retrieved "+persoanaList.size()+" elements from persoana.");
			System.out.println("Retrieved "+angajatList.size()+" elements from angajat.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeObjects() {
		
		System.out.println("Writing objects to files.");
		
		try {
			Integer size;
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(new File(PERSOANA_FILE)));
			size = persoanaList.size();
			oos.writeObject(size);
			for (Integer i : persoanaList.keySet()) {
				oos.writeInt(i);
				oos.writeObject(persoanaList.get(i));
			}
			oos.close();
			
			oos = new ObjectOutputStream(new FileOutputStream(new File(ANGAJAT_FILE)));
			size = angajatList.size();
			oos.writeObject(size);
			for (Integer i : angajatList.keySet()) {
				oos.writeInt(i);
				oos.writeObject(angajatList.get(i));
			}
			oos.close();
			
			oos = new ObjectOutputStream(new FileOutputStream(new File(ADRESA_FILE)));
			size = adresaList.size();
			oos.writeObject(size);
			for (Integer i : adresaList.keySet()) {
				oos.writeInt(i);
				oos.writeObject(adresaList.get(i));
			}
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class WriteObjectsCallable implements Callable<String> {
		
		int numThread;
		List<Entry<Integer, Persoana>> persoanaArray;
		List<Entry<Integer, Angajat>> angajatArray;
		List<Entry<Integer, Adresa>> adresaArray;
		
		public WriteObjectsCallable(int threadNum,
									List<Entry<Integer, Persoana>> list,
									List<Entry<Integer, Angajat>> list2,
									List<Entry<Integer, Adresa>> list3) {
			this.numThread = threadNum;
			persoanaArray = list;
			angajatArray = list2;
			adresaArray = list3;
		}

		@Override
		public String call() throws Exception {
			System.out.println("Task "+numThread+": Writing objects to files.");
			String file = THREAD_FILE+numThread+".data";
			
			Integer size;
			ObjectOutputStream oos;
			
			oos = new ObjectOutputStream(new FileOutputStream(new File(file)));
			size = persoanaArray.size();
			oos.writeObject(size);
			for (Entry<Integer, Persoana> entry : persoanaArray) {
				oos.writeObject(entry.getKey());
				oos.writeObject(entry.getValue());
			}
			size = angajatArray.size();
			oos.writeObject(size);
			for (Entry<Integer, Angajat> entry : angajatArray) {
				oos.writeObject(entry.getKey());
				oos.writeObject(entry.getValue());
			}
			size = adresaArray.size();
			oos.writeObject(size);
			for (Entry<Integer, Adresa> entry : adresaArray) {
				oos.writeObject(entry.getKey());
				oos.writeObject(entry.getValue());
			}
			oos.close();
			return file;
		}
	}
	
	public static ArrayList<String> writeObjectsParallel(int numThreads) {
		ArrayList<String> threadFiles = new ArrayList<>();
		
		ArrayList<Entry<Integer, Persoana>> persoanaArray = new ArrayList<Entry<Integer, Persoana>>();
		persoanaArray.addAll(persoanaList.entrySet());
		ArrayList<Entry<Integer, Angajat>> angajatArray = new ArrayList<Entry<Integer, Angajat>>();
		angajatArray.addAll(angajatList.entrySet());
		ArrayList<Entry<Integer, Adresa>> adresaArray = new ArrayList<Entry<Integer, Adresa>>();
		adresaArray.addAll(adresaList.entrySet());
		
		ExecutorService es = Executors.newFixedThreadPool(numThreads);
		List<Future<String>> writingTasks = new ArrayList<>();
	
		int chunkPersoana = persoanaList.size()/numThreads;
		int chunkAngajat = angajatList.size()/numThreads;
		int chunkAdresa = adresaList.size()/numThreads;
		
		for (int i = 0; i < numThreads; i++) {
			Callable<String> woc;
			if(i != numThreads-1)
				woc = new WriteObjectsCallable(i, 
						persoanaArray.subList(i*chunkPersoana, (i+1)*chunkPersoana),
						angajatArray.subList(i*chunkAngajat, (i+1)*chunkAngajat),
						adresaArray.subList(i*chunkAdresa, (i+1)*chunkAdresa));
			else
				woc = new WriteObjectsCallable(i, 
						persoanaArray.subList(i*chunkPersoana, persoanaList.size()),
						angajatArray.subList(i*chunkAngajat, angajatList.size()),
						adresaArray.subList(i*chunkAdresa, adresaList.size()));
			Future<String> futureWrite = es.submit(woc);
			writingTasks.add(futureWrite);
		}
		
		for (Future<String> writeTask : writingTasks) {
			try {
				threadFiles.add(writeTask.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		es.shutdown();
		return threadFiles;
	}
	
	public static void readObjects() {
		System.out.println("Reading objects from files.");
		
		try {
			persoanaList = new HashMap<Integer, Persoana>();
			adresaList = new HashMap<Integer, Adresa>();
			angajatList = new HashMap<Integer, Angajat>();
			
			Integer size;
			ObjectInputStream ois;
			ois = new ObjectInputStream(new FileInputStream(new File(PERSOANA_FILE)));
			size = (Integer) ois.readObject();
			for (int i = 1; i <= size; i++)
				persoanaList.put(ois.readInt(), (Persoana)ois.readObject());
			ois.close();
			
			ois = new ObjectInputStream(new FileInputStream(new File(ANGAJAT_FILE)));
			size = (Integer) ois.readObject();
			for (int i = 1; i <= size; i++)
				angajatList.put(ois.readInt(), (Angajat)ois.readObject());
			ois.close();
			
			ois = new ObjectInputStream(new FileInputStream(new File(ADRESA_FILE)));
			size = (Integer) ois.readObject();
			for (int i = 1; i <= size; i++)
				adresaList.put(ois.readInt(), (Adresa)ois.readObject());
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static class DataList {
		List<Entry<Integer, Persoana>> persoanaArray;
		List<Entry<Integer, Angajat>> angajatArray;
		List<Entry<Integer, Adresa>> adresaArray;
		public DataList(List<Entry<Integer, Persoana>> persoanaArray, List<Entry<Integer, Angajat>> angajatArray,
				List<Entry<Integer, Adresa>> adresaArray) {
			super();
			this.persoanaArray = persoanaArray;
			this.angajatArray = angajatArray;
			this.adresaArray = adresaArray;
		}
	}
	
	public static class MyEntry<K, V> implements Entry<K, V> {

		K k;
		V v;
		
		public MyEntry(K k, V v) {
			this.k = k;
			this.v = v;
		}
		
		@Override
		public K getKey() {
			return k;
		}

		@Override
		public V getValue() {
			return v;
		}

		@Override
		public V setValue(V value) {
			V oldV = v;
			v = value;
			return oldV;
		}
	}
	
	public static class ReadObjectsTask extends RecursiveTask<DataList> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2599341192665292560L;
		DataList dataList;
		ArrayList<String> threadFiles;
		
		public ReadObjectsTask(ArrayList<String> tf) {
			threadFiles = new ArrayList<>();
			threadFiles.addAll(tf);
			dataList = new DataList(new ArrayList<Entry<Integer, Persoana>>(), 
					new ArrayList<Entry<Integer, Angajat>>(), 
					new ArrayList<Entry<Integer, Adresa>>());
		}

		@Override
		protected DataList compute() {
			
			if (threadFiles.size() == 1) {
				System.out.println("Task : Reading objects from file :" + threadFiles.get(0));

				Integer size;
				ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(new FileInputStream(new File(threadFiles.get(0))));
					size = (Integer) ois.readObject();
					for (int i = 1; i <= size; i++)
						dataList.persoanaArray.add(new MyEntry<>((Integer)ois.readObject(), (Persoana)ois.readObject()));
					
					size = (Integer) ois.readObject();
					for (int i = 1; i <= size; i++)
						dataList.angajatArray.add(new MyEntry<>((Integer)ois.readObject(), (Angajat)ois.readObject()));
								
					size = (Integer) ois.readObject();
					for (int i = 1; i <= size; i++)
						dataList.adresaArray.add(new MyEntry<>((Integer)ois.readObject(), (Adresa)ois.readObject()));
					ois.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
							
				return dataList;
			}
			else {
				ArrayList<String> first = new ArrayList<>();
				first.add(threadFiles.get(0));
				ArrayList<String> rest = new ArrayList<>();
				rest.addAll(threadFiles.subList(1, threadFiles.size()));
				ReadObjectsTask firstPart = new ReadObjectsTask(first);
				ReadObjectsTask secondPart = new ReadObjectsTask(rest);
				firstPart.fork();
				DataList dlRest = secondPart.compute();
				DataList dlFirst = firstPart.join();
				
				dlFirst.persoanaArray.addAll(dlRest.persoanaArray);
				dlFirst.angajatArray.addAll(dlRest.angajatArray);
				dlFirst.adresaArray.addAll(dlRest.adresaArray);
				
				return dlFirst;
			}
		}
	}
	
	public static void readObjectsParallel(int numThreads) {
		DataList dl;
				
		ForkJoinPool fjp = new ForkJoinPool(numThreads);
		dl = fjp.invoke(new ReadObjectsTask(threadFiles));

		persoanaList = new HashMap<Integer, Persoana>();
		adresaList = new HashMap<Integer, Adresa>();
		angajatList = new HashMap<Integer, Angajat>();
		
		for (Entry<Integer, Persoana> e : dl.persoanaArray)
			persoanaList.put(e.getKey(), e.getValue());
		for (Entry<Integer, Angajat> e : dl.angajatArray)
			angajatList.put(e.getKey(), e.getValue());
		for (Entry<Integer, Adresa> e : dl.adresaArray)
			adresaList.put(e.getKey(), e.getValue());
	}
	
	public static void insertValuesJDBC() {
		System.out.println("Inserting values JDBC");
		
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = connectToDb();
			System.out.println("Connection successful");
			s = c.createStatement();
			String sql;
			
			Angajat angajat;
			Adresa adresa;
			Persoana persoana;
			int angajatid = 0;
			int num_angajati = 0;
									
			try {
				sql = "ALTER TABLE angajat DROP FOREIGN KEY fk1;";
				s.executeUpdate(sql);
			} catch (MySQLSyntaxErrorException e) {
				System.out.println("Could not drop constraint angajat.");
			}
			try {
				sql = "ALTER TABLE persoana DROP FOREIGN KEY fk2;";
				s.executeUpdate(sql);
			} catch (MySQLSyntaxErrorException e) {
				System.out.println("Could not drop constraint persoana.");
			}
			
			sql = "TRUNCATE angajat";
			s.executeUpdate(sql);
						
			sql = "TRUNCATE persoana";
			s.executeUpdate(sql);
			
			sql = "ALTER TABLE angajat ADD CONSTRAINT fk1 FOREIGN KEY (id) REFERENCES persoana(id);";
			s.executeUpdate(sql);
						
			sql = "TRUNCATE adresa";
			s.executeUpdate(sql);
			
			sql = "ALTER TABLE persoana ADD CONSTRAINT fk2 FOREIGN KEY (adresaid) REFERENCES adresa(adresaid);";
			s.executeUpdate(sql);
			
			for (Integer i : adresaList.keySet()) {
				adresa = adresaList.get(i);
				sql = "INSERT INTO adresa" +
						" VALUES (default,'"+ adresa.getStrada() + "',"+ adresa.getNr() + 
						",'"+ adresa.getBl() + "','"+ adresa.getScara() + "',"+ adresa.getApartament() + ");";
				//System.out.println(sql);
				s.executeUpdate(sql);
			}
			System.out.printf("Inserted %d rows in adresa.\n", ADRESA_SIZE);
			
			for (Integer i : persoanaList.keySet()) {
				persoana = persoanaList.get(i);
				sql = "INSERT INTO persoana" +
						" VALUES (default,'"+ persoana.getNume() + "','"+ persoana.getPrenume() + 
						"','"+ persoana.getEmail() + "','"+ persoana.getNrtelefon() + "',"+ 
						persoana.getAdresaId(adresaList) + ");";
				//System.out.println(sql);
				s.executeUpdate(sql);
			
				angajat = angajatList.get(i);
				if (persoana.equals(angajat)) {
					sql = "SELECT * FROM persoana ORDER BY id DESC LIMIT 1";
					rs = s.executeQuery(sql);
					while (rs.next())
						angajatid = rs.getInt(1);
					sql = "INSERT INTO angajat" +
							" VALUES ("+ angajatid +","+angajat.getMarca()+");";
					s.executeUpdate(sql);
					num_angajati++;
				}
			}
			System.out.printf("Inserted %d rows in persoana.\n", PERSOANA_SIZE);
			System.out.printf("Inserted %d rows in angajati.\n", num_angajati);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updateDatabase(){
		
		System.out.println("Updating values");
		
		Connection c = null;
		Statement s = null;
		
		try {
			c = connectToDb();
			s = c.createStatement();
			String sql;
						
			for (Integer i : angajatList.keySet()) {
				Angajat a = angajatList.get(i);
				if (a.updates.keySet().size() != 0)
					System.out.println("updating angajat:" + a.updates.keySet().size());
				for (String key : a.updates.keySet()) {
					sql = "UPDATE angajat set "+key+"="+a.updates.get(key)+" WHERE id="+i+";";
					System.out.println(sql);
					s.executeUpdate(sql);
				}
			}
			
			for (Integer i : persoanaList.keySet()) {
				Persoana p = persoanaList.get(i);
				if (p.updates.keySet().size() != 0)
					System.out.println("updating persoana:" + p.updates.keySet().size());
				for (String key : p.updates.keySet()) {
					sql = "UPDATE persoana set "+key+"="+p.updates.get(key)+" WHERE id="+i+";";
					System.out.println(sql);
					s.executeUpdate(sql);
				}
			}
			
			for (Integer i : adresaList.keySet()) {
				Adresa a = adresaList.get(i);
				if (a.updates.keySet().size() != 0)
					System.out.println("updating angajat:" + a.updates.keySet().size());
				for (String key : a.updates.keySet()) {
					sql = "UPDATE adresa set "+key+"="+a.updates.get(key)+" WHERE id="+i+";";
					System.out.println(sql);
					s.executeUpdate(sql);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
	//Note : cand scriu in fisier elementele a doua liste care pointeaza catre aceleasi elemente
	// la citirea din fisier ele vor pointa catre elemente diferite
	// daca as fi scris intreaga lista poate ar fi pointat
	// scriu elementele pe rand pentru ca am nevoie de impartire pe taskuri
	public static void main(String[] args) {
		
		long startTime, elapsedTime;
		double time;
		int numThreads = 8;
		
		//insertValues();
		
		retrieveValues();
				
		startTime = System.nanoTime();    
		//writeObjects();
		elapsedTime = System.nanoTime() - startTime;
		time = (double)elapsedTime / 1000000;
		System.out.println("Writing serial : "+time);
		
		startTime = System.nanoTime(); 
		threadFiles = writeObjectsParallel(numThreads);
		elapsedTime = System.nanoTime() - startTime;
		time = (double)elapsedTime / 1000000;
		System.out.println("Writing parallel : "+time);
		
		startTime = System.nanoTime(); 
		//readObjects();
		elapsedTime = System.nanoTime() - startTime;
		time = (double)elapsedTime / 1000000;
		System.out.println("Reading serial : "+time);
		
		startTime = System.nanoTime(); 
		readObjectsParallel(numThreads);
		elapsedTime = System.nanoTime() - startTime;
		time = (double)elapsedTime / 1000000;
		System.out.println("Reading parallel : "+time);
		
		//persoanaList.get(2).setNume("ALEX");
		//persoanaList.get(2).setAdresa(adresaList.get(1), adresaList);
		//angajatList.get(3).setNume("JOJO");
		//updateDatabase();
		
		//insertValuesJDBC();
	}
}
