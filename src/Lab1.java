//
//   Lab1.java
//
//   You should use this program for testing your Translit class.
//   To run it in a console:
//      $ java Lab1 <name of the file to convert>
//
//   To run it from Eclipse you need first to go to
//       Run/Run Configurations...
//   then click on the tab "(x)= Arguments" and enter the full access
//   path to the file in the "Program arguments:" entry field.
//

import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Define class Translit here.
// You can also define it as a public class in a separate file named
// Translit.java

class Translit {

    public String convert(String toBeTranslate) {

        //set the translit rule

        String[] Upper = new String[33];
        String[] Lower = new String[33];
        String[] English = new String[33];

        try (FileInputStream fis = new FileInputStream("translit_table.txt");
             InputStreamReader isr = new InputStreamReader(fis, "utf-8");
             BufferedReader bReader = new BufferedReader(isr)) {

            String line;
            int n = 0;
            while ((line = bReader.readLine()) != null) {
                String[] temp = line.split(",");
                Upper[n] = (temp[0].split(""))[1];
                Lower[n] = (temp[1].split(""))[2];
                English[n] = temp[2].substring(2, temp[2].length() - 1);
                n++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Character Encoding is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        }

        //do the translation

        StringBuilder s = new StringBuilder(new String(""));
        char[] contentInChar = toBeTranslate.toCharArray();

        for (char c : contentInChar) {
            boolean found = false;
            for (int j = 0; j < Upper.length; j++) {
                if (String.valueOf(c).equals(Upper[j]) || String.valueOf(c).equals(Lower[j])) {
                    s.append(English[j]);
                    found = true;
                }
            }
            if (!found) {
                s.append(c);
            }
        }
        return s.toString();
    }
}

public class Lab1 {

    static String fileContent = new String("");

    // This method reads the contents of a file into a String.
    // It specifies that the characters in the file are encoded
    // with the UTF-8 encoding scheme (this is the standard on the Web
    // and on Linux machines; Windows machines use a different default
    // encoding scheme)
    // We will see files in detail later in the course.

    private static void readFile(String fileName) {

        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, "utf-8");
             BufferedReader bReader = new BufferedReader(isr)) {

            String line;
            while ((line = bReader.readLine()) != null) {
                fileContent = fileContent + line + "\n";
            }

        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Character Encoding is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        // The program takes the name of the file from the command line.
        // When it runs, it finds command line parameters into the args array.

        if (args.length > 0) {
            try {

                // Load the content of the file in memory
                readFile(args[0]);

                // Display what has been read for control.
                System.out.println("Input:");
                System.out.println(fileContent);

                // Create a Translit object
                Translit tr = new Translit();

                // Convert and display. It will all be in lowercase.
                System.out.println("Output:");
                System.out.println(tr.convert(fileContent));

            } catch (Exception e) {
                // If anything goes wrong
                System.out.println("Something is wrong in this prossess");
                e.printStackTrace();

            }
        }
    }
}
