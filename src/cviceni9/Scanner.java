package cviceni9;

public class Scanner {
    private String inp;
    private int poz;
    private boolean isEol = false;
    private int value;

    /**
     * Tato trida vzdy cte o jeden znak napred, takze prvni uz ctu v konstruktoru
     */
    public Scanner(String inp) {
        this.inp = inp;
        poz = 0;
    }

    public Token nextToken() {
        skipSpaces();
        if (isEol) {
            return Token.EOL;
        }
        //je li prvni cislice, jde o cislo
        if (Character.isDigit(getChar())) {
            String s = "";
            while (!isEol && Character.isDigit(getChar())) {
                s += getChar();
                nextChar();
            }
            value = Integer.parseInt(s);
            return Token.NUMBER;
        }
        char tmp = getChar();
        nextChar();
        if (tmp == '+') {
            return Token.PLUS;
        }
        if (tmp == '-') {
            return Token.MINUS;
        }
        if (tmp == '*') {
            return Token.KRAT;
        }
        if (tmp == '/') {
            return Token.DELENO;
        }
        if (tmp == ')') {
            return Token.PZAV;
        }
        if (tmp == '(') {
            return Token.LZAV;
        }
        System.err.println("Doslo k chybe pri lexikalni analze!");
        return Token.INVALID;
    }

    /**
     * Preskoci "bile" znaky
     */
    private void skipSpaces() {
        while (!isEol && Character.isWhitespace(getChar())) {
            nextChar();
        }
    }

    private void nextChar() {
        if (poz + 1 >= inp.length()) {
            isEol = true;
        } else {
            poz++;
        }
    }

    private char getChar() {
        return inp.charAt(poz);
    }

    public int getValue() {
        return value;
    }
}
