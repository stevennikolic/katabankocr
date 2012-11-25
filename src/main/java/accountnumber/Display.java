package accountnumber;


import exceptions.DecoderException;

public class Display {
    public static void print(Iterable<Number> accountNumbers) throws DecoderException {
        String decode = Decoder.decode(accountNumbers);
        System.out.println(decode);
    }
}