package cz.cvut.fit.ortstepa.universalbookingsystem.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

public class LogicEngine {

	private static final String SCRIPT_HEADER = "java = undefined;\nPackages = undefined;\norg = undefined;\n\n";

	public static boolean eval(String script) throws Exception {
		ContextFactory f = new TimeLimitedContextFactory(1000);
		Context cx = f.enterContext();
        try {
            Scriptable scope = cx.initStandardObjects();
            Calendar c = Calendar.getInstance(Locale.US);
            String s = SCRIPT_HEADER;
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
            //Date o = (Date) Context.jsToJava(scope.get("NOW", scope), Date.class);
            if (result instanceof Boolean)
            	return ((Boolean) result).booleanValue();
            else
            	throw new Exception("Invalid condition statement");
        } finally {
            // Exit from the context.
            Context.exit();
        }
	}

	public static void test() {
		try {
			System.out.println("eval="+eval("(promena > NOW)"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}