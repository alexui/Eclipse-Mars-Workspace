
public class Person {

	/*
	 * this class is mutable
	 */
	
	private String nume;
	private Integer varsta;
	
	public Person(String n, Integer v) {
		nume = n;
		varsta = v;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public Integer getVarsta() {
		return varsta;
	}

	public void setVarsta(Integer varsta) {
		this.varsta = varsta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nume == null) ? 0 : nume.hashCode());
		result = prime * result + ((varsta == null) ? 0 : varsta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (nume == null) {
			if (other.nume != null)
				return false;
		} else if (!nume.equals(other.nume))
			return false;
		if (varsta == null) {
			if (other.varsta != null)
				return false;
		} else if (!varsta.equals(other.varsta))
			return false;
		return true;
	}
	
	
}
