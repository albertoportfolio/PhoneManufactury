import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class OrderApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int basicNum;
        int proNum;
        String basicInput;
        String proInput;

        /*
         * Loop to ensure the basic model quantity is double the pro model
         */
        do {
            System.out.println("Enter quantity for each model:");
            System.out.println("The basic model must be double the pro model.");
            System.out.print("Basic model: ");
            basicInput = scanner.nextLine();
            System.out.print("Pro model: ");
            proInput = scanner.nextLine();
            basicNum = Integer.parseInt(basicInput);
            proNum = Integer.parseInt(proInput);
        } while ((proNum * 2) != basicNum);

        System.out.print("Enter configuration (1, 2, or 3): ");
        String config = scanner.nextLine();

        System.out.print("Enter 'y' to save output to file, any other key to display on screen: ");
        String outputOption = scanner.nextLine();

        callManufacture(basicInput, proInput, config, outputOption);
    }

    public static void callManufacture(String basic, String pro, String config, String output) {

        try {

            String className = "src/Factory.java";
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");

            List<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-cp");
            command.add(classpath);
            command.add(className);
            command.add(basic);
            command.add(pro);
            command.add(config);

            ProcessBuilder builder = new ProcessBuilder(command);

            if (output.equalsIgnoreCase("y")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                File file = new File("Order_" + sdf.format(new Date()) + ".cmd");
                Process process = builder.redirectOutput(file).start();
                process.waitFor();
            } else {
                Process process = builder.inheritIO().start();
                process.waitFor();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
