import sun.org.mozilla.javascript.internal.Callable;
import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.ContextFactory;
import sun.org.mozilla.javascript.internal.Scriptable;

 class TimeLimitedContextFactory extends ContextFactory
 {
	 
	 private int maxTime;

     public TimeLimitedContextFactory(int maxTime) {
		super();
		this.maxTime = maxTime;
	}

	// Custom Context to store execution time.
     private static class TimeLimitedContext extends Context
     {
        public TimeLimitedContext(TimeLimitedContextFactory myFactory) {
			super(myFactory);
		}

		long startTime;
     }

     // Override makeContext()
     protected Context makeContext()
     {
         TimeLimitedContext cx = new TimeLimitedContext(this);
         cx.setGenerateObserverCount(true);
         return cx;
     }


     // Override observeInstructionCount(Context, int)
     protected void observeInstructionCount(Context cx, int instructionCount)
     {
         TimeLimitedContext mcx = (TimeLimitedContext)cx;
         long currentTime = System.currentTimeMillis();
         if (currentTime - mcx.startTime > maxTime) {
             // More then 10 seconds from Context creation time:
             // it is time to stop the script.
             // Throw Error instance to ensure that script will never
             // get control back through catch or finally.
             throw new Error();
         }
     }

     // Override doTopCall(Callable, Context, Scriptable, Scriptable, Object[])
     protected Object doTopCall(Callable callable,
                                Context cx, Scriptable scope,
                                Scriptable thisObj, Object[] args)
     {
         TimeLimitedContext mcx = (TimeLimitedContext)cx;
         mcx.startTime = System.currentTimeMillis();

         return super.doTopCall(callable, cx, scope, thisObj, args);
     }

 }