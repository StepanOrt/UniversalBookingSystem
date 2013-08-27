import javax.script.ScriptException;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogicEngine le =  new LogicEngine();
		le.init();
		boolean b = false;
		try {
			b = le.eval2();
			System.out.println(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
