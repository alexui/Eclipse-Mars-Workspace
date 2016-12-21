
public interface Visitable {
	public void acceptRead(Visitor v);
	public void acceptWrite(Visitor v);
}