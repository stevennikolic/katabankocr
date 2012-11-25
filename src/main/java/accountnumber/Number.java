package accountnumber;

import exceptions.InvalidAccountNumberDigitException;

public class Number {
    private final Digit[] digits;

    public Number(Digit[] digits) {
        this.digits = digits;
    }

    public Number(CharSequence accountNumberString) throws NumberFormatException, InvalidAccountNumberDigitException {
        int inputLength = accountNumberString.length();
        if (inputLength != 9) {
            throw new InvalidAccountNumberDigitException("must be 9 digits");
        }
        this.digits = new Digit[9];
        for (int position = 0; position < inputLength; position++) {
            char c = accountNumberString.charAt(position);
            String tempCharAsString = Character.toString(c);
            Integer digit = new Integer(tempCharAsString);
            this.digits[position] = new Digit(digit);
        }
    }


    public Digit[] getAccountNumber() {
        return this.digits;
    }

    @Override
    public String toString() {
        StringBuilder accountNumber = new StringBuilder();
        for (Digit digit : this.digits) {
            String str = digit.toString();
            accountNumber.append(str);
        }
        return accountNumber.toString();
    }
}
