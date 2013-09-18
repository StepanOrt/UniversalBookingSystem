package ubs.core.logicengine;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.ContextFactory;
import sun.org.mozilla.javascript.internal.NativeJavaObject;
import sun.org.mozilla.javascript.internal.NativeObject;
import sun.org.mozilla.javascript.internal.Scriptable;
import sun.org.mozilla.javascript.internal.Undefined;

public class LogicEngine {

	private static final String[][] TIME_UNIT_MEANINGS = { {"d", "Date"}, {"h", "Hours"}, {"m", "Minutes"} };
	private final int TIMEOUT = 1000;

	
	
	private String script = null;
	private ContextFactory contextFactory;
	private Map<String, Object> variables;

	public LogicEngine() {
		this.contextFactory = new TimeLimitedSandboxedContextFactory(TIMEOUT);
	}
	
	public Object eval() throws Exception {
		
		Context cx = null;
    	cx = contextFactory.enterContext();
    	//sandbox context
    	TimeLimitedSandboxedContextFactory.disableAccessToJavaClasses(cx);
    	
        Scriptable scope = cx.initStandardObjects();
        loadVariables(cx, scope);
        String s = "var NOW"  + " = new Date();\n\n";
        s += script;
        Object result = cx.evaluateString(scope, s, "script", 1, null);
        if (result.getClass().getName().equals("sun.org.mozilla.javascript.internal.NativeDate"))
        	result = Context.jsToJava(result, Date.class);
        else if (result instanceof Undefined) 
        	throw new Exception("Script has no return value!");
        /*
        for (String varName : variables.keySet()) {
        	Object varValue = scope.get(varName, scope);
        	System.out.println(varValue.getClass().getName());
        	System.out.println(varName + "=" + varValue);
        }
        */

        return result;
	}
	
	private void loadVariables(Context cx, Scriptable scope) {
		if (variables == null) return;
		DateFormat dateFormat = new SimpleDateFormat("MMMMM d, yyyy HH:mm:ss", Locale.US);
		for (String variableName : variables.keySet()) {
			Object variableValue = variables.get(variableName);
			if (variableValue instanceof Date) {
				Date dateVariableValue = (Date)variableValue;
				Object[] o = { dateFormat.format(dateVariableValue.getTime()) };
				Scriptable s = cx.newObject(scope, "Date", o);
				scope.put(variableName, scope, s);
			}
			else {
				Object o = Context.javaToJS(variableValue, scope);
				scope.put(variableName, scope, o);
			}
		}
		
	}

	public void loadConditionStatement(String condition, Map<String, Object> variables) {
		script = parseCondition(condition);
		this.variables = variables;
	}

	public static String parseCondition(String condition) {
		String decodedCondition = condition;
		Pattern pattern = Pattern.compile("(\\w+)#([\\+-]?\\d+)([dhmDHM])");
		Matcher matcher = pattern.matcher(condition);
		int start = 0;
		while (matcher.find(start)) {
			String variable = matcher.group(1);
			String offset = matcher.group(2);
			
			char firstCharOfOffset = offset.charAt(0);
			char sign = '+';
			if (firstCharOfOffset == '-') {
				offset = offset.substring(1);
				sign = '-';
			} else if (firstCharOfOffset == '+')
				offset = offset.substring(1);
	
 			char unit = matcher.group(3).toLowerCase().charAt(0);
			String replacement = decodedCondition.substring(matcher.start(), matcher.end());
			
			for (String[] timeUnitMeaning : TIME_UNIT_MEANINGS) {
				if (timeUnitMeaning[0].charAt(0) == unit) {
					replacement = variable + ".set" + timeUnitMeaning[1] + "(" + variable + ".get" + timeUnitMeaning[1] + "()" + sign + offset +")" ;
				}
			}
			decodedCondition = decodedCondition.replace(matcher.group(0), replacement);
			
			start = matcher.end();
		}
		return decodedCondition;
	}
	
}
