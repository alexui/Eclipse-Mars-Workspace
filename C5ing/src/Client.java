
import java.sql.*;

public class Client extends Person {
	
	public Client(String nume, int varsta) {
		super(nume, varsta);
	}

	private String nrContract;

	public String getNrContract() {
		return nrContract;
	}

	public void setNrContract(String nrContract) {
		this.nrContract = nrContract;
	}
	
	
}
