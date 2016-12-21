
public class Processor implements Visitor{
	
	private static int COUNT = 0;
	
	private enum State {
		MODIFICAT,
		EXCLUXIV,
		SHARED,
		INVALID
	}
	
	public String pName;
	public State pState;
		
	private Processor() {
		COUNT++;
		pName = "P"+COUNT;
		pState = State.INVALID;
	}
	
	public static Processor getNextProccesor() {
		return new Processor();
	}

	@Override
	public void visitRead(Cache c) {
		if (c.firstVisitor == null) {
			c.firstVisitor = this;
			this.pState = State.EXCLUXIV;
			c.busState = BusState.BusRead;
			c.dataSource = String.valueOf(DataSource.Mem);
			c.memory = true;
			c.currentVisitors.add(this);
		}
		else {
			
			if (c.firstVisitor == this) {
				c.busState = BusState.Null;
				c.dataSource = String.valueOf(DataSource.Null);
			}
			else {
				for (Processor p : c.currentVisitors)
					p.pState = State.SHARED;
				this.pState = State.SHARED;
				c.dataSource = String.valueOf(DataSource.Cache + c.firstVisitor.pName);
				c.memory = true;
				if (c.currentVisitors.size() == 1)
					c.busState = BusState.Flush;
				else 
					c.busState = BusState.FlushIP;
				c.currentVisitors.add(this);
			}
		}
	}

	@Override
	public void visitWrite(Cache c) {
				
		if (c.firstVisitor == null) {
			c.firstVisitor = this;
			this.pState = State.MODIFICAT;
			c.busState = BusState.BusReadX;
			if (c.memory)
				c.dataSource = String.valueOf(DataSource.FlushIg);
			c.memory = true;
		}
		else {
			
				for (Processor p : c.currentVisitors)
					p.pState = State.INVALID;
			
				c.currentVisitors.clear();
				c.currentVisitors.add(this);
				c.firstVisitor = this;
				this.pState = State.MODIFICAT;
				if (c.memory)
					c.dataSource = String.valueOf(DataSource.FlushIg);
				c.memory = true;
				c.busState = BusState.BusReadX;
			}
	}
	
}
