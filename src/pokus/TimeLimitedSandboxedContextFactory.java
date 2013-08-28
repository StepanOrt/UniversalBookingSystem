package pokus;

import sun.org.mozilla.javascript.internal.Callable;
import sun.org.mozilla.javascript.internal.ClassShutter;
import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.ContextFactory;
import sun.org.mozilla.javascript.internal.NativeJavaObject;
import sun.org.mozilla.javascript.internal.Scriptable;
import sun.org.mozilla.javascript.internal.WrapFactory;

class TimeLimitedSandboxedContextFactory extends ContextFactory {

	private int maxTime;

	public TimeLimitedSandboxedContextFactory(int maxTime) {
		super();
		this.maxTime = maxTime;
	}

	// Custom Context to store execution time.
	private static class TimeLimitedContext extends Context {
		public TimeLimitedContext(TimeLimitedSandboxedContextFactory myFactory) {
			super(myFactory);
		}

		long startTime;
	}

	private static class SandboxNativeJavaObject extends NativeJavaObject {
		public SandboxNativeJavaObject(Scriptable scope, Object javaObject,
				@SuppressWarnings("rawtypes") Class staticType) {
			super(scope, javaObject, staticType);
		}

		@Override
		public Object get(String name, Scriptable start) {
			if (name.equals("getClass")) {
				return NOT_FOUND;
			}

			return super.get(name, start);
		}
	}

	private static class SandboxWrapFactory extends WrapFactory {
		@Override
		public Scriptable wrapAsJavaObject(Context cx, Scriptable scope,
				Object javaObject, @SuppressWarnings("rawtypes") Class staticType) {
			return new SandboxNativeJavaObject(scope, javaObject, staticType);
		}
	}

	// Override makeContext()
	protected Context makeContext() {
		TimeLimitedContext cx = new TimeLimitedContext(this);
		cx.setGenerateObserverCount(true);
		cx.setWrapFactory(new SandboxWrapFactory());
		return cx;
	}

	// Override observeInstructionCount(Context, int)
	protected void observeInstructionCount(Context cx, int instructionCount) {
		TimeLimitedContext mcx = (TimeLimitedContext) cx;
		long currentTime = System.currentTimeMillis();
		if (currentTime - mcx.startTime > maxTime) {
			// More then 1 second from Context creation time:
			// it is time to stop the script.
			// Throw Error instance to ensure that script will never
			// get control back through catch or finally.
			throw new Error("Script evaluation timeout!");
		}
	}

	// Override doTopCall(Callable, Context, Scriptable, Scriptable, Object[])
	protected Object doTopCall(Callable callable, Context cx, Scriptable scope,
			Scriptable thisObj, Object[] args) {
		TimeLimitedContext mcx = (TimeLimitedContext) cx;
		mcx.startTime = System.currentTimeMillis();

		return super.doTopCall(callable, cx, scope, thisObj, args);
	}
	
	public static void disableAccessToJavaClasses(Context cx) {
		cx.setClassShutter(new ClassShutter() {
			public boolean visibleToScripts(String className) {					
			//	if(className.startsWith("adapter"))
			//		return true;
		 
				return false;
			}
		});
	}

}