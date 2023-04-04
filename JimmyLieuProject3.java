import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JimmyLieuProject3 {
    public static void main(String[] args) throws IOException, InterruptedException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (true) {
            System.out.printf(">>>");
            try {
                input = reader.readLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting");
                break;
            }
            //Switch cases
            switch (input.toLowerCase()) {
            //load directory
                case "ls":
                    executeCommand("cmd /c dir \n");
                   
                    break;
                case "pwd":
                    executeCommand("cmd /c cd");
                    System.out.println();
                    break;
                case "clear":
                try{
                    if (System.getProperty("os.name").contains("Windows"))
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    else 
                        Runtime.getRuntime().exec("clear");
                } catch (IOException | InterruptedException ex) {}
                    
                    break;
                case "date":
                    // Get the current date and time
                    Date currentDate = new Date();

                    // Create a date formatter with the desired format
                    SimpleDateFormat formatter = new SimpleDateFormat ("E MMM dd HH:mm:ss z yyyy");

                    // Set the time zone of the formatter to CST
                    formatter.setTimeZone(TimeZone.getTimeZone("CST"));

                    // Format the current date and time using the formatter
                    String output = formatter.format(currentDate);

                    //Print the formatted date and time to the console
                    System.out.println(output);
                    break;
                case "help":
                    displayHelp();
                    break;
                case "whoami":
                    executeCommand("cmd /c echo %username%");
                    break;
                default:
                if (input.startsWith("help ")){ //check if input starts with "help "
                    String cmd = input.substring(5); // extract the command after "help "
                    displayCommandHelp(cmd); //display the help for the specified command
                }else{
                    System.out.println("Unknown command!");
                }
            }

        }
    }

    private static void executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            if (command.toLowerCase().equals("dir")) {
                System.out.println("Name\t\t\tSize\tLast Modified"); // Print header
                System.out.println("--------------------------------------------------"); // Print separator
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split("\\s+"); // Split line by whitespace
                    if (tokens.length >= 4) { // Check if line contains file info
                        String name = tokens[tokens.length - 1];
                        String size = tokens[tokens.length - 5];
                        String lastModified = tokens[tokens.length - 4] + " " + tokens[tokens.length - 3] + " " + tokens[tokens.length - 2];
                        System.out.printf("%-20s %-10s %-20s\n", name, size, lastModified); // Print formatted output
                    }
                }
            } else {
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void displayHelp() {
        System.out.println("MyShell, version 2.0, runs on Windows 10, developed by Jimmy Lieu");
        System.out.println("Release date March 24, 2023");
        System.out.println("These shell commands are defined internally. Type \'help\' to see this list.");
        System.out.println("Command and its parameters, if any, should be seperated by one space only.");
        System.out.println("Type \'help name\' to find more about the command \'name\'.");
        /*
         * "%-15s" Creates padding within the first 15 spaces and puts the string on the
         * left side
         */
        System.out.printf("%-15s %-15s\n", "Commands", "Functions");
        System.out.printf("%-15s %-15s\n", "=======", "========");
        // System.out.printf("%-10s %-10s %-10s\n", "one", "two", "three")""
        System.out.printf("%-15s %-15s\n", "ls", "Lists all files and directories in the current directory");
        System.out.printf("%-15s %-15s\n", "pwd", "Displays the current directory");
        System.out.printf("%-15s %-15s\n", "clear", "Clears the console");
        System.out.printf("%-15s %-15s\n", "date", "Displays the current day, time, and time zone");
        System.out.printf("%-15s %-15s\n", "help [cmd]",
                "Get help for command cmd (\'cmd\' is optional);");
        System.out.printf("%-15s %-15s\n", " ", "without cmd displays this list.");
        System.out.printf("%-15s %-15s\n", "exit", "Quit console");

        System.out.printf("%-15s %-15s\n", "whoami", "Displays the name of the programmer");

    }
    private static void displayCommandHelp(String cmd) {
        switch(cmd.toLowerCase()) {
            case "ls":
                System.out.println("Lists all files and directories in the current directory.");
                break;
            case "pwd":
                System.out.println("Displays the current directory.");
                break;
            case "clear":
                System.out.println("Clears the console.");
                break;
            case "date":
                System.out.println("Displays the current day, time, and time zone.");
                break;
            case "help":
                System.out.println("Get help for command cmd ('cmd' is optional). Without cmd, displays a list of available commands.");
                break;
            case "exit":
                System.out.println("Quit console.");
                break;
            case "whoami":
                System.out.println("Displays the name of the programmer.");
                break;
            default:
                System.out.println("Unknown command. Type 'help' for a list of available commands.");
                break;
        }
    }
    
}
