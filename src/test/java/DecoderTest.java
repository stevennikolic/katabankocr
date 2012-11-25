import accountnumber.Decoder;
import accountnumber.Number;
import junit.framework.TestCase;

import java.util.ArrayList;

public class DecoderTest extends TestCase {
    public void testDecode() throws Exception {
        String input = "123456789";
        Number accountNumber = new Number(input);
        Iterable<Number> accountNumbers = new AccountNumberArrayList(accountNumber);
        String decode = Decoder.decode(accountNumbers);
        String expected =
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n";
        assertEquals(decode, expected);
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
