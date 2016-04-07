package grammarnetbeans;

import grammar.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *  Ukazkovy priklad pro praci s gramatikou
 */
public class Test {

	public static void main(String[] args)
	{
		// vytvorime prazdnou gramatiku
		Grammar grammar;

		// nacteme jeji obsah ze souboru
		try {
			GrammarReader inp = new GrammarReader(new FileReader(/*args[0]*/"g2.txt"));
			grammar = inp.read();
		}
		catch( GrammarException e ) {
			// chyba pri analyze textu
			System.err.println("Error("+e.getLineNumber()+") "+e.getMessage());
			return;
		}
		catch( IOException e ) {
			// chyba vstupu/vystupu
			System.err.println("Error: "+e.getMessage());
			return;
		}

		// muzeme opsat, co jsme precetli
		grammar.dump(System.out);

		GrammarOps go = new GrammarOps(grammar);

		// vypiseme mnozinu nonterminalu generujicich prazdne slovo
		
		for (Nonterminal nt : go.getEmptyNonterminals()) {
			System.out.print(nt.getName()+" ");
		}
		System.out.println();
		List<Symbol> symbols = Arrays.asList(new Nonterminal("E"), new Terminal("d"), new Terminal("a"));
		System.out.println(go.first(symbols).toString());
	}
}
