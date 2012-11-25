package accountnumber;

import exceptions.DecoderException;
import exceptions.InvalidAccountNumberDigitException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Bsp.:
 * _  _     _  _  _  _  _
 * | _| _||_||_ |_   ||_||_|
 * ||_  _|  | _||_|  ||_| _|
 * <p/>
 * 0 =
 * 1 =   |
 * 2 = |_
 * 3 =  _|
 * 4 = |_|
 * 5 = | |
 * 6 =  _
 * <p/>
 * 0             _    6             _    6                  0             _    6
 * | = 1             _| = 3             _| = 3            |_| = 4            |_  = 2
 * |   1            |_    2             _|   3              |   1             _|   3
 * <p/>
 * _    6             _    6             _    6            _    6             _    6
 * |_  = 2              | = 1            |_| = 4           |_| = 4            | | = 5
 * |_|   4              |   1            |_|   4            _|   3            |_|   4
 */
public class Decoder {
    private static final String LCD_PART_0 = "   ";
    private static final String LCD_PART_1 = "  |";
    private static final String LCD_PART_2 = "|_ ";
    private static final String LCD_PART_3 = " _|";
    private static final String LCD_PART_4 = "|_|";
    private static final String LCD_PART_5 = "| |";
    private static final String LCD_PART_6 = " _ ";

    private static String getDecoded(int part) throws DecoderException {
        String decodedpart;
        switch (part) {
            case 0:
                decodedpart = LCD_PART_0;
                break;
            case 1:
                decodedpart = LCD_PART_1;
                break;
            case 2:
                decodedpart = LCD_PART_2;
                break;
            case 3:
                decodedpart = LCD_PART_3;
                break;
            case 4:
                decodedpart = LCD_PART_4;
                break;
            case 5:
                decodedpart = LCD_PART_5;
                break;
            case 6:
                decodedpart = LCD_PART_6;
                break;
            default:
                throw new DecoderException("invalid part number");
        }
        return decodedpart;
    }

    public static String decode(Iterable<accountnumber.Number> accountNumbers) throws DecoderException {
        StringBuilder decodedNumbers = new StringBuilder();

        for (accountnumber.Number accountNumber : accountNumbers) {
            Digit[] digits = accountNumber.getAccountNumber();
            decodeNumber(decodedNumbers, digits);
        }

        return decodedNumbers.toString();
    }

    private static void decodeNumber(StringBuilder decodedNumbers, Digit[] digits) throws DecoderException {
        for (int position = 0; position < 3; position++) {
            decodeNumberLine(decodedNumbers, digits, position);
            decodedNumbers.append("\n");
        }
        decodedNumbers.append("\n");
    }

    private static void decodeNumberLine(StringBuilder decodedNumbers, Digit[] digits, Integer position) throws DecoderException {
        for (Digit digit : digits) {
            int line = digit.getPosition(position);
            String str = getDecoded(line);
            decodedNumbers.append(str);
        }
    }

    public static List<accountnumber.Number> encode(String input) throws InvalidAccountNumberDigitException, DecoderException {
        Scanner scanner = new Scanner(input);
        String currentLine;
        List<Integer> topCodes = new ArrayList<Integer>();
        List<Integer> middleCodes = new ArrayList<Integer>();
        List<Integer> bottomCodes = new ArrayList<Integer>();
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            if (currentLine.length() > 1) {
                encodeLine(currentLine, topCodes);
                currentLine = scanner.nextLine();
                encodeLine(currentLine, middleCodes);
                currentLine = scanner.nextLine();
                encodeLine(currentLine, bottomCodes);
            }
        }
        scanner.close();
        return encodeNumbers(topCodes, middleCodes, bottomCodes);
    }

    private static List<accountnumber.Number> encodeNumbers(List<Integer> topCodes, List<Integer> middleCodes, List<Integer> bottomCodes) throws InvalidAccountNumberDigitException {
        List<accountnumber.Number> accountNumbers = new ArrayList<accountnumber.Number>();
        int position = 0;
        do {
            accountnumber.Number accountNumber = encodeDigit(position, topCodes, middleCodes, bottomCodes);
            accountNumbers.add(accountNumber);
            position += 10;
        } while (position < topCodes.size());
        return accountNumbers;
    }

    private static accountnumber.Number encodeDigit(int position, List<Integer> topCodes, List<Integer> middleCodes, List<Integer> bottomCodes) throws InvalidAccountNumberDigitException {
        Digit[] digits = new Digit[9];
        for (int i = 0; i < 9; i++) {
            int index = position + i;
            Integer top = topCodes.get(index);
            Integer middle = middleCodes.get(index);
            Integer bottom = bottomCodes.get(index);
            Digit digit = new Digit(top, middle, bottom);
            digits[i] = digit;
        }
        return new accountnumber.Number(digits);
    }

    private static void encodeLine(String currentLine, Collection<Integer> codes) throws DecoderException {
        int positionen = currentLine.length() - 1;
        for (int position = 0; position < positionen; position += 3) {
            String token = currentLine.substring(position, position + 3);
            int encoded = encodePart(token);
            codes.add(encoded);
        }
    }

    private static int encodePart(String token) throws DecoderException {
        int value;
        if (token.equals(LCD_PART_0)) {
            value = 0;
        } else if (token.equals(LCD_PART_1)) {
            value = 1;
        } else if (token.equals(LCD_PART_2)) {
            value = 2;
        } else if (token.equals(LCD_PART_3)) {
            value = 3;
        } else if (token.equals(LCD_PART_4)) {
            value = 4;
        } else if (token.equals(LCD_PART_5)) {
            value = 5;
        } else if (token.equals(LCD_PART_6)) {
            value = 6;
        } else {
            throw new DecoderException("invalid part text");
        }
        return value;
    }
}

