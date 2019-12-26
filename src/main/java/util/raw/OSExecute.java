//: net/mindview/util/OSExecute.java
// Run an operating system command
// and send the output to the console.
package util.raw;

import exception.OSExecuteException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class OSExecute {
    private static final String contextClassPath;

    static {
        URL resource =
                Thread.currentThread().getContextClassLoader().getResource("");
        contextClassPath = resource.getPath();
    }
    public static void command(String command) {
        boolean err = false;
        try {
            Process process =
                    new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String s;
            String s1 = results.readLine();
            while (s1 != null) {
                s = s1;
                System.out.println(s);
                s1 = results.readLine();
            }
            BufferedReader errors = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            // Report errors and return nonzero value
            // to calling process if there are problems:
            while ((s = errors.readLine()) != null) {
                System.err.println(s);
                err = true;
            }
        } catch (Exception e) {
            // Compensate for Windows 2000, which throws an
            // exception for the default command line:
            if (!command.startsWith("CMD /C"))
                command("CMD /C " + command);
            else
                throw new RuntimeException(e);
        }
        if (err)
            throw new OSExecuteException("Errors executing " +
                    command);
    }

    public static void commandJavaExecute(Class cls, Object...args) {
        String className = cls.getName();
        StringBuilder builder = new StringBuilder();

        if (args != null) {
            for (Object arg : args) {
                builder.append(String.valueOf(arg));
                builder.append(" ");
            }
        }
        OSExecute.command(
                "java -classpath .;" + contextClassPath + " "  + className + " " + builder.toString());
    }

    public static void commandJavaDecode(Class cls, Object...args) {
        String className = cls.getName();
        StringBuilder builder = new StringBuilder();

        if (args != null) {
            for (Object arg : args) {
                builder.append(String.valueOf(arg));
                builder.append(" ");
            }
        }

        OSExecute.command(
                "javap -classpath .;" + contextClassPath + " "  + className + " " + builder.toString());

    }



} ///:~
