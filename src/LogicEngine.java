

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
            Calendar c = Calendar.getInstance(Locale.US);
            String s = "";//SCRIPT_HEADER;
            c.set(2013, 7, 28, 0, 20, 0);
            DateFormat df = new SimpleDateFormat("MMMMM d, yyyy HH:mm:ss", Locale.US);
            String promena = "Date.parse('" + df.format(c.getTime()) + "')";
            s += "var promena"  + " = " + ((Object)promena).toString() + ";\n";
            s += "var DAY = " +	24*60*60*1000 + ";\n";
            s += "var NOW"  + " = new Date();\n";
            s += "NOW.setHours(NOW.getHours()+30);\n";
            s += "\n";
            s += script;
            s += ";";
            System.out.println(s + "\n--------------");
            Object result = cx.evaluateString(scope, s, "script", 1, null);
            Date o = (Date) Context.jsToJava(scope.get("NOW", scope), Date.class);
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
		script = "(promena > NOW)";
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
