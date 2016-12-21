
public interface Visitor {
	
	public void visitRead(Cache c);
	public void visitWrite(Cache c);
}