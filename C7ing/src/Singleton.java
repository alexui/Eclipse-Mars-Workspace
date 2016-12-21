
public class Singleton {

	private static Singleton instance = null; //lazy loading vs eager loading
	
	private Singleton() {
		
	}
	
	public static Singleton getInstance() { //pt multithreading
		
		if (instance == null)
			synchronized(Singleton.class) {
				if (instance == null)
					instance = new Singleton();
			}
		return instance;
	}
}
