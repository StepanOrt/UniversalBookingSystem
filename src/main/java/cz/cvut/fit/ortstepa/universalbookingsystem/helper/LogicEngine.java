package cz.cvut.fit.ortstepa.universalbookingsystem.helper;

import java.util.Map;
import java.util.Map.Entry;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Rule;

public class LogicEngine {

	private static final String SCRIPT_HEADER = "java = undefined;\nPackages = undefined;\norg = undefined;\n\n";

	private static boolean eval(String script) throws Exception {
		ContextFactory f = new TimeLimitedContextFactory(1000);
		Context cx = f.enterContext();
        try {
            Scriptable scope = cx.initStandardObjects();
            String s = SCRIPT_HEADER;
            s += script;
            Object result = cx.evaluateString(scope, s, "script", 1, null);
            if (result instanceof Boolean)
            	return ((Boolean) result).booleanValue();
            else
            	throw new Exception("Invalid condition statement");
        } finally {
            Context.exit();
        }
	}

	public static boolean eval(Rule rule, Map<String, String> variableMap) throws Exception {
		String script = "";
		for (Entry<String, String> entry : variableMap.entrySet()) {
			script += "var " + entry.getKey() + " = " + entry.getValue() + ";";
		}
		script += "\n" + rule + ";\n";
		return eval(script);
	}
}