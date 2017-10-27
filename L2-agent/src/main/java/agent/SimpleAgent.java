package agent;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;

import beans.Bean;

/**
 *
 */
public class SimpleAgent {
    private static volatile Instrumentation instrumentation;

    public static void premain(final String agentArgs, final Instrumentation inst) {
        System.out.println("premain...");
        instrumentation = inst;
    }

    public static long getObjectSize(final Object object) {
        if (instrumentation == null) {
            throw new IllegalStateException("Agent not initialized.");
        }
        return instrumentation.getObjectSize(object);
    }

    public static void printInfo() {
        System.out.println(Arrays.toString(instrumentation.getAllLoadedClasses()));
        System.out.println(Arrays.toString(instrumentation.getInitiatedClasses(Bean.class.getClassLoader())));
    }
}
