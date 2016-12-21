
public class Angajat extends Person {
	
	public Angajat(String nume, int varsta) {
		super(nume, varsta);
	}

	private String marca;

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
}
