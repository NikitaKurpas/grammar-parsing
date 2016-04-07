package cviceni9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Tato trida jen spusti Scaner a vypisuje texty pro jednotlive tokeny z rozhrani Token
 */
public class Uloha9 {
    static int readNumber(BufferedReader inp) throws IOException {
        return Integer.parseInt(inp.readLine().trim());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(
                new InputStreamReader(System.in));
        int pocet = 0;

        pocet = readNumber(inp);
        for (int i = 0; i < pocet; i++) {
            Scanner s = new Scanner(inp.readLine());
            Grammar g = new Grammar(s);
            g.start();
        }
    }
}
