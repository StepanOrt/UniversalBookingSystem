package ubs.core.logicengine;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		LogicEngine le = new LogicEngine();
		Map<String, Object> variables = new HashMap<String, Object>();
		
		Calendar c = Calendar.getInstance();
		c.set(2000, 0, 1, 0, 0, 0);
		variables.put("date_variable", c.getTime());
		variables.put("float_variable", new Float(12.25f));
		variables.put("string_value", "blabla");
		variables.put("number_value", new Integer(-464));
		variables.put("long_value", new Long(45464));
		
		//String condition = "(date_variable#-36h > NOW) && (float_variable > 12) || (string_value == \"bleble\") && (number_value > 0) || (long_value > 0.5)";
		String condition = "";
		
		le.loadConditionStatement(condition, variables);
		Object result = le.eval();
		System.out.println(result);
		if (result instanceof Boolean)
			System.out.println(result);
		else
			throw new Exception("Invalid condition statement");
	}

}
