package Algorithm;
import java.lang.Integer;

public class test {
    public static String stringToBinary(String s) {
        String out = "";
        for (int i = 0; i < s.length(); i++) {
            int val = (int) s.charAt(i);
            String bin = Integer.toBinaryString(val);
            int len = bin.length();
            for (int j = 0; j < 8 - len; j++) {
                bin = "0" + bin;
            }
            out += bin;
        }
        return out;
    }
    
    public static String nextBinaryString(String lastNumber) {
        String out = "";
        char curr = lastNumber.charAt(lastNumber.length() - 1);
        int pointer = lastNumber.length() - 1;
        
        if (curr == '0') {
            out = lastNumber.substring(0,lastNumber.length()-1) + "1";
            return out;
        }
        
        while (curr != '0') {
            out = "0" + out;
            
            pointer--;
            if (pointer == -1) {
                out = "1" + out;
                return out;
            } else {
                curr = lastNumber.charAt(pointer);
                continue;
            }
        }
        out = lastNumber.substring(0, pointer) + "1" + out;
        return out;     
    }
    
    public static String nextOctalString(String lastNumber) {
        String out = "";
        char curr = lastNumber.charAt(lastNumber.length() - 1);
        int pointer = lastNumber.length() - 1;
        
        if (curr != '7') {
            out = lastNumber.substring(0, lastNumber.length() - 1) + (char) (curr + 1);
            return out;
        }
        
        while (curr == '7') {
            out = "0" + out;
            pointer--;
            if (pointer == -1) {
                out = "1" + out;
                return out;
            } else {
                curr = lastNumber.charAt(pointer);
                continue;
            }
        }
        
        out = lastNumber.substring(0, pointer) + (char) (curr + 1) + out;
        
        return out;
    }
    
    public static String findLastBinary(String s) {
        String nextBin = "0";

        int first = s.indexOf(nextBin);
        int last = s.lastIndexOf(nextBin);
        
        if (first == -1 && last == -1) return s;

        while (first != -1 || last != -1) {
            // System.out.println(s);
            // System.out.println("next binary " + nextBin);
            // System.out.println("First " + first + " Last " + last);
            
            // if there exists only one option
            if (last - first < nextBin.length()) last = -1;
            if (first == last) {
                s = s.substring(0,first) + s.substring(first+nextBin.length());
            }
                else if (first == -1) {
                    s = s.substring(0, last) + s.substring(last+nextBin.length());
                } else if (last == -1) {
                    s = s.substring(0, first) + s.substring(first+nextBin.length());
                } else {
                    // remove your front and ends
                    // System.out.println(s.substring(0,first));
                    // System.out.println(s.substring(first+nextBin.length()));
                    // System.out.println(s.substring(last+nextBin.length()));
                    
                    s = s.substring(0,first) + s.substring(first+nextBin.length(), last) + s.substring(last+nextBin.length());
                }

            nextBin = nextBinaryString(nextBin);
            first = s.indexOf(nextBin);
            last = s.lastIndexOf(nextBin);
            
        }
        
        return s;
    }
    
    public static String getBinVal(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += (s.charAt(i) - (int) '0') * Math.pow(2, s.length() - i - 1);
        }
        return "" + sum;
    }
    
    public static String binaryToOctal(String s) {
        String out = "";
        int leftOver = s.length() % 3;
        
        if (leftOver == 1) {
            out += "" + s.charAt(0);
            for (int i = 1; i < s.length(); i = i+3) {
                out += getBinVal(s.substring(i, i+3));
            }
        } else if (leftOver == 2) {
            out += "" + getBinVal(s.substring(0, 2));
            for (int i = 2; i < s.length(); i = i+3) {
                out += getBinVal(s.substring(i, i+3));
            }
        } else {
            for (int i = 0; i < s.length(); i = i + 3) {
                out += getBinVal(s.substring(i, i + 3));
            }
        }
        int i = 0;
        // strip off the leading zeros
        while (out.charAt(i) == '0') {
            out = out.substring(1);
        } 
        
        return out;
    }
    
    public static Integer octalToDecimal(String s) {
        return Integer.valueOf(s, 8);
    }

    /*
     * Complete the 'findLastOctal' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int findLastOctal(String s) {
        if (s.length() == 0) return -1;
        // convert the word to binary
        String bin = stringToBinary(s);

        // System.out.println("Binary number " + bin);
        
        // find the last binary number
        String lastBinString = findLastBinary(bin);
        // System.out.println("last binary string " + lastBinString);
        
        // convert to octal
        String octal = binaryToOctal(lastBinString);
        // System.out.println("Octal " + octal);
        
        // findLastOctal
        String nextOct = "0";
        int first = octal.indexOf(nextOct);
        int last = octal.lastIndexOf(nextOct);
        
        if (first == -1 && last == -1) return -1; // if string does not contain zero
        
        while (first != -1 || last != -1) {
            if (last - first < nextOct.length()) {
                last = -1;
            }
            // System.out.println(nextOct);
            // if there exists only one option
            if (first == last) {
                octal = octal.substring(0,first) + octal.substring(first+nextOct.length());
            } else {
                if (first == -1) {
                    octal = octal.substring(0, last) + octal.substring(last+nextOct.length());
                } else if (last == -1) {
                    octal = octal.substring(0, first) + octal.substring(first+nextOct.length());
                } else {
                    // remove your front and ends
                    octal = octal.substring(0,first) + octal.substring(first+nextOct.length(), last) + octal.substring(last+nextOct.length());
                    
                }
            nextOct = nextOctalString(nextOct);
            first = octal.indexOf(nextOct);
            last = octal.lastIndexOf(nextOct);
            }
        }
        
        // find the last value that exists with in the string
        int out = octalToDecimal(nextOct) - 1;
        
        return out;
    }

    public static void main(String[] args) {
        int[] out = new int[3];
        System.out.println(out[0]);
        // String bin = "0101010001101000011010010111001100100000011010010111001100100000011000010010000001100011011010000110000101110010011000010110001101110100011001010111001000100000011000110110111101110101011011100111010001100101011100100010111000100000010101110110010100100000011101110110000101101110011101000010000001110100011011110010000001110101011100110110010100100000011101000111011101101111001000000110100001110101011011100110010001110010011001010110010000100000011000110110100001100001011100100110000101100011011101000110010101110010011100110010000001110111011010010111010001101000011010010110111000100000011101000110100001101001011100110010000001101100011011110111011001100101011011000111100100100000011011000110100101110100011101000110110001100101001000000111001101100101011100010111010101100101011011100110001101100101001011100010000001001001001000000111001101101111011011010110010101110100011010010110110101100101011100110010000001110111011000010110101101100101001000000111010101110000001000000110000101101110011001000010000001110111011011110110111001100100011001010111001000100000011101110110100001111001001000000111010001101000011001010010000001110011011010110111100100100000011010010111001100100000011100000110100101101110011010110010000001110111011010000110010101101110001000000111010001101000011001010010000001110011011101010110111000100000011100100110100101110011011001010111001100101100001000000110001001110101011101000010000001101001011101000010011101110011001000000110000100100000011011000110111101110110011001010110110001111001001000000111001001100101011011010110100101101110011001000110010101110010";
        // System.out.println(findLastOctal("This is a character counter. We want to use two hundred characters within this lovely little sequence. I sometimes wake up and wonder why the sky is pink when the sun rises, but it's a lovely reminder"));
        // boolean result = stringToBinary("This is a character counter. We want to use two hundred characters within this lovely little sequence. I sometimes wake up and wonder why the sky is pink when the sun rises, but it's a lovely reminder").equals(bin);
        // System.out.println(result);
        // String cat = "cat";
        // System.out.println(cat.indexOf("cats"));
        //System.out.println("0101001001101111011100110110010101110011001000000110000101110010011001010010000001110010011001010110010000101110".equals("0101001001101111011100110110010101110011001000000110000101110010011001010010000001110010011001010110010000101110"));
        // System.out.println(stringToBinary("Roses are red."));
        //System.out.println(findLastBinary("011110100"));
        /*
         *"0101010001101000011010010111001100100000011010010111001100100000011000010010000001100011011010000110000101110010011000010110001101110100011001010111001000100000011000110110111101110101011011100111010001100101011100100010111000100000010101110110010100100000011101110110000101101110011101000010000001110100011011110010000001110101011100110110010100100000011101000111011101101111001000000110100001110101011011100110010001110010011001010110010000100000011000110110100001100001011100100110000101100011011101000110010101110010011100110010000001110111011010010111010001101000011010010110111000100000011101000110100001101001011100110010000001101100011011110111011001100101011011000111100100100000011011000110100101110100011101000110110001100101001000000111001101100101011100010111010101100101011011100110001101100101001011100010000001001001001000000111001101101111011011010110010101110100011010010110110101100101011100110010000001110111011000010110101101100101001000000111010101110000001000000110000101101110011001000010000001110111011011110110111001100100011001010111001000100000011101110110100001111001001000000111010001101000011001010010000001110011011010110111100100100000011010010111001100100000011100000110100101101110011010110010000001110111011010000110010101101110001000000111010001101000011001010010000001110011011101010110111000100000011100100110100101110011011001010111001100101100001000000110001001110101011101000010000001101001011101000010011101110011001000000110000100100000011011000110111101110110011001010110110001111001001000000111001001100101011011010110100101101110011001000110010101110010"
         */
        /*
         * 011110100 
         * 1111010 remove 0
         * 11100 remove 1
         * 110 remove 10
         * 110 remove 11
        */
    }
}
