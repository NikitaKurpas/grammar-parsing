package cviceni9;

public class Grammar {

    boolean OK = true;
    private Scanner scanner;
    private Token symbol;
//    private java.util.Vector out;

    public Grammar(Scanner scanner) {
//        out = new java.util.Vector();
        this.scanner = scanner;
        symbol = scanner.nextToken();
    }

    public void start() {
        int result = e();
        if (OK) {
//            java.util.Enumeration e = out.elements();
//            while (e.hasMoreElements()) {
//                System.out.print(e.nextElement());
//                if (e.hasMoreElements())
//                    System.out.print(" ");
//            }
//            System.out.println();
            System.out.println(result);
        } else System.out.println("CHYBA");
    }

    private void expect(Token token) {
        if (symbol == token) symbol = scanner.nextToken();
        else OK = false;
    }

    private int e() {
//        out.add("1");
        return e1(t());
    }

    private int e1(int prev) {
        if (symbol == Token.PLUS) {
//            out.add("2");
            expect(Token.PLUS);
            return e1(prev + t());
        } else if (symbol == Token.MINUS) {
//            out.add("3");
            expect(Token.MINUS);
            return e1(prev - t());
        } else if (symbol == Token.EOL || symbol == Token.PZAV) {
//            out.add("4");
            return prev;
        } else {
            OK = false;
            throw new ParseException("Unexpected token " + symbol);
        }
    }

    private int t() {
//        out.add("5");
        return t1(f());
    }

    private int t1(int prev) {
        if (symbol == Token.KRAT) {
//            out.add("6");
            expect(Token.KRAT);
            return t1(prev * f());
        } else if (symbol == Token.DELENO) {
//            out.add("7");
            expect(Token.DELENO);
            return t1(prev / f());
        } else if (symbol == Token.EOL || symbol == Token.PZAV || symbol == Token.PLUS || symbol == Token.MINUS) {
//            out.add("8");
            return prev;
        } else {
            OK = false;
            throw new ParseException("Unexpected token " + symbol);
        }
    }

    private int f() {
        int result = 0;
        if (symbol == Token.LZAV) {
//            out.add("9");
            expect(Token.LZAV);
            result = e();
            expect(Token.PZAV);
        } else if (symbol == Token.NUMBER) {
//            out.add("10");
            result = scanner.getValue();
            expect(Token.NUMBER);
        } else {
            OK = false;
            throw new ParseException("Unexpected token " + symbol);
        }
        return result;
    }
}