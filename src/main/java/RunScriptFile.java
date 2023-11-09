import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * RunScript: simplest example of controlling execution of Rhino.
 *
 * <p>Collects its arguments from the command line, executes the script, and prints the result.
 *
 * @author Norris Boyd
 */
public class ScriptFile {
    public ScriptFile() {}
    public void runScript(String args[]) {
        // Creates and enters a Context. The Context stores information
        // about the execution environment of a script.
        Context cx = Context.enter();
        try {
            // Initialize the standard objects (Object, Function, etc.)
            // This must be done before scripts can be executed. Returns
            // a scope object that we use in later calls.
            ScriptFile scope = cx.initStandardObjects();

            // Collect the arguments into a single string.
            String s = "";
            for (int i = 0; i < args.length; i++) {
                s += args[i];
            }

            // Now evaluate the string we've colected.
            Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);

            // Convert the result to a string and print it.
            System.err.println(Context.toString(result));

        } finally {
            // Exit from the context.
            Context.exit();
        }
    }
}