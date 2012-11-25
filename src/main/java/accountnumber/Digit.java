package accountnumber;

import exceptions.InvalidAccountNumberDigitException;

public class Digit {
    private int partTop = 6;
    private int middle = 5;
    private int bottom = 4;
    private int digit;


    private void setPositionen(int partTop, int partMiddle, int partBottom) {
        this.partTop = partTop;
        this.middle = partMiddle;
        this.bottom = partBottom;
    }

    /**
     * Create a custom digit by providing the digits digit;
     *
     * @param digit The digit of the digit.
     * @throws InvalidAccountNumberDigitException
     *          in case an invalid digit digit is provided.
     */
    public Digit(int digit) throws InvalidAccountNumberDigitException {
        switch (digit) {
            case 0:
                this.setPositionen(6, 5, 4);
                break;
            case 1:
                this.setPositionen(0, 1, 1);
                break;
            case 2:
                this.setPositionen(6, 3, 2);
                break;
            case 3:
                this.setPositionen(6, 3, 3);
                break;
            case 4:
                this.setPositionen(0, 4, 1);
                break;
            case 5:
                this.setPositionen(6, 2, 3);
                break;
            case 6:
                this.setPositionen(6, 2, 4);
                break;
            case 7:
                this.setPositionen(6, 1, 1);
                break;
            case 8:
                this.setPositionen(6, 4, 4);
                break;
            case 9:
                this.setPositionen(6, 4, 3);
                break;
            default:
                throw new InvalidAccountNumberDigitException("Invalid constructor parameter!");
        }
        this.digit = digit;
    }

    /**
     * Create a custom digit by providing the partTop, middle and bottom codes.
     *
     * @param partTop The code of the partTop line of the digit
     * @param middle  The code of the middle line of the digit
     * @param bottom  The code of the bottom line of the digit
     * @throws InvalidAccountNumberDigitException
     *          An InvalidAccountNumberDigitException will be thrown in case
     *          the partTop, middle and bottom code combination is invalid
     */
    public Digit(int partTop, int middle, int bottom) throws InvalidAccountNumberDigitException {
        this.digit = this.getValueByPositions(partTop, middle, bottom);
        this.setTop(partTop);
        this.setMiddle(middle);
        this.setBottom(bottom);
    }

    private int getValueByPositions(int partTop, int middle, int bottom) throws InvalidAccountNumberDigitException {
        int value;
        if (partTop == 0 && middle == 1 && bottom == 1) {
            value = 1;
        } else if (partTop == 0 && middle == 4 && bottom == 1) {
            value = 4;
        } else if (partTop == 6 && middle == 3 && bottom == 2) {
            value = 2;
        } else if (partTop == 6 && middle == 3 && bottom == 3) {
            value = 3;
        } else if (partTop == 6 && middle == 2 && bottom == 3) {
            value = 5;
        } else if (partTop == 6 && middle == 2 && bottom == 4) {
            value = 6;
        } else if (partTop == 6 && middle == 1 && bottom == 1) {
            value = 7;
        } else if (partTop == 6 && middle == 4 && bottom == 4) {
            value = 8;
        } else if (partTop == 6 && middle == 4 && bottom == 3) {
            value = 9;
        } else if (partTop == 6 && middle == 5 && bottom == 4) {
            value = 0;
        } else {
            throw new InvalidAccountNumberDigitException("The top, middle and bottom codes are not in a valid combination!");
        }
        return value;
    }

    void setTop(int top) {
        this.partTop = top;
    }

    void setMiddle(int middle) {
        this.middle = middle;
    }

    void setBottom(int bottom) {
        this.bottom = bottom;
    }

    int getTop() {
        return this.partTop;
    }

    int getMiddle() {
        return this.middle;
    }

    int getBottom() {
        return this.bottom;
    }

    int getDigit() {
        return this.digit;
    }

    public int getPosition(int position) {
        int line = -1;
        switch (position) {
            case 0:
                line = this.getTop();
                break;
            case 1:
                line = this.getMiddle();
                break;
            case 2:
                line = this.getBottom();
                break;
        }
        return line;
    }

    @Override
    public String toString() {
        int value = this.getDigit();
        return String.valueOf(value);
    }
}