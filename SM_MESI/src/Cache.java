import java.util.ArrayList;

enum BusState {
	BusRead,
	BusReadX,
	Null,
	Flush,
	FlushIP //Inactive Processors
}

enum DataSource {
	Null,
	Mem,
	Cache,
	FlushIg //FlushIgnore
}

public class Cache implements Visitable{
	
	private static Cache instance = null;
	
	public BusState busState = BusState.Null;
	public String dataSource = String.valueOf(DataSource.Null);
	public Processor firstVisitor = null;
	public ArrayList<Processor> currentVisitors = null;
	public boolean memory = false; //exista sursa de date
	
	private Cache() {
		currentVisitors = new ArrayList<Processor>();
	}
	
	public static Cache getInstance() {
		if (instance == null)
			instance = new Cache();
		return instance;
	}
	
	@Override
	public void acceptRead(Visitor v) {
		v.visitRead(this);
	}
	
	@Override
	public void acceptWrite(Visitor v) {
		v.visitWrite(this);
	}
		
}
