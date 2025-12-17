package Runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class SimpleRunner {
    public static void main(String[] args) {
        System.out.println("Starting Cucumber Test Execution...");

        Result result = JUnitCore.runClasses(RunnerClass.class);

        System.out.println("Tests run: " + result.getRunCount());
        System.out.println("Failures: " + result.getFailureCount());
        System.out.println("Success: " + result.wasSuccessful());

        for (Failure failure : result.getFailures()) {
            System.out.println("Failure: " + failure.toString());
            failure.getException().printStackTrace();
        }

        System.out.println("\nTest Execution Completed!");
    }
}
