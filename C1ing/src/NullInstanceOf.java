
public class NullInstanceOf {
	public static void main(String[] args) {
		String str = null;
		//str nu refera nimic - str nu e instanta
		if (str instanceof Object)
			System.out.println("str is Object");
		else
			System.out.println("str is not Object");
	}
}
