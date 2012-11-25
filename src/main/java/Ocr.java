import accountnumber.Decoder;
import accountnumber.Display;
import accountnumber.Number;
import exceptions.DecoderException;
import exceptions.InvalidAccountNumberDigitException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ocr {
    private static final String FILENAME = "accountnumbers.txt";
    private static final String ERROR_DIGITS = "Invalid Account Number! Use 9 digits please!";
    private static final String ERROR_NUMBER = "Not a valid Number! Exiting.";

    /**
     * Account-Nummern eingeben
     */
    private static void enterAccountNumbers() {
        Scanner scanner;
        scanner = new Scanner(System.in);
        boolean repeat;
        do {
            String input = scanner.nextLine();
            repeat = isAccountLine(input);
        } while (repeat);
        scanner.close();
    }

    private static boolean isAccountLine(String input) {
        boolean repeat = true;
        System.out.print("Enter 9 digit account number: ");
        if (isNotNumberic(input)) {
            System.out.println(ERROR_NUMBER);
            repeat = false;
        } else {
            try {
                Number accountNumber = new Number(input);
                Iterable<Number> accountNumbers = new AccountNumberArrayList(accountNumber);
                Display.print(accountNumbers);
            } catch (InvalidAccountNumberDigitException e) {
                System.out.println(ERROR_DIGITS);
            } catch (DecoderException e) {
                System.out.println(ERROR_DIGITS);
            }
        }
        return repeat;
    }

    /**
     * Pruefen, ob die Eingabe numerisch ist
     */
    private static boolean isNotNumberic(String input) {
        int inputInteger;
        try {
            inputInteger = Integer.parseInt(input);
        } catch (NumberFormatException nFE) {
            inputInteger = -1;
        }
        return inputInteger == -1;
    }

    /**
     * Account-Nummern aus der Datei accountnumbers.txt lesen
     */
    private static void readAccountNumbersFromFile() {
        try {
            String input = readFile();
            List<Number> accountNumbers = Decoder.encode(input);

            for (Number accountNumber : accountNumbers) {
                System.out.println(accountNumber);
            }
            int size = accountNumbers.size();
            System.out.printf("Read %d account numbers from file!%n", size);
        } catch (IOException e) {
            System.out.printf("File %s not found in directoy.%n", FILENAME);
            System.exit(1);
        } catch (InvalidAccountNumberDigitException e) {
            System.out.println("Invalid Accountnumber digit");
            System.exit(2);
        } catch (DecoderException e) {
            System.out.println("Error in decoding Accountnumber");
            System.exit(3);
        }
    }

    private static String readFile() throws IOException {
        File file = new File(FILENAME);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder filecontent = new StringBuilder();
        int character;
        do {
            character = bufferedReader.read();
            if (character > -1) {
                filecontent.append((char) character);
            }
        } while (character != -1);
        bufferedReader.close();
        return filecontent.toString();
    }

    public static void main(String[] args) {
        System.out.println("Reading account numbers from file.");
        readAccountNumbersFromFile();

        System.out.println();
        enterAccountNumbers();
    }

    private static class AccountNumberArrayList extends ArrayList<Number> {
        private AccountNumberArrayList(accountnumber.Number accountNumber) {
            this.add(accountNumber);
        }

        @Override
        public AccountNumberArrayList clone() {
            return (AccountNumberArrayList) super.clone();
        }
    }
}
