

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.ContextFactory;
import sun.org.mozilla.javascript.internal.Scriptable;

import com.sun.script.javascript.RhinoScriptEngineFactory;

public class LogicEngine {

	private ScriptEngine engine;
	private final String SCRIPT_HEADER = "java = undefined;\nPackages = undefined;\norg = undefined;\n\n";
	private String script = null;

	public LogicEngine() {
		RhinoScriptEngineFactory factory = new RhinoScriptEngineFactory();
        engine = factory.getScriptEngine();
	}
	
	public boolean eval2() throws Exception {
		ContextFactory f = new TimeLimitedContextFactory(1000);
		Context cx = f.enterContext();
        try {
            Scriptable scope = cx.initStandardObjects();
            String s = SCRIPT_HEADER;
            int promena = 1;
            s += "var promena"  + " = " + ((Object)promena).toString() + ";\n";
            s += "\n";
            s += script;
            s += ";";
            Object result = cx.evaluateString(scope, s, "script", 1, null);
            if (result instanceof Boolean)
            	return ((Boolean) result).booleanValue();
            else
            	throw new Exception("Invalid condition statement");
        } finally {
            // Exit from the context.
            Context.exit();
        }
	}
	
	public void init() {
		script = "(promena > 0)";
	}
	
	public boolean eval() throws Exception{
		boolean val = false;
		Object o;
		String s = "";
		try {
			o = engine.eval(script);
			s = o.toString();
			if (s.toLowerCase().equals("true") ) val = true;
			else if (s.toLowerCase().equals("false")) val = false;
			else
				throw new Exception("Invalid condition statement");
		} catch (ScriptException e) {
			throw e;
		}
		return val;
	}
}
