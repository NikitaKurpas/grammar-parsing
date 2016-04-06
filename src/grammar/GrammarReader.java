package grammar;

import java.io.*;

/** Cteni bezkontextove gramatiky.
 *  Gramatika je popsana jako posloupnost pravidel, neterminalni symboly
 *  jsou pojmenovany identifikatory zacinajicimi velkym pismenem,
 *  terminalni symboly idetifikatory zacinajicimi malym pismenem.
 *  Startovacim nonterminalem je symbol na leve strane prvniho pravidla
 *  gramatiky. Pravidla maji tvar
 *  <pre>
 *    lhs : rhs1 | rhs2 | ... | rhsN ;
 *  </pre>
 *  kde <tt>lhs</tt> je neterminalni symbol a <tt>rhsi</tt> jsou
 *  posloupnosti terminalnich a neterminalnich symbolu. Ve zdrojovem
 *  textu mohou byt poznamky ohranicene slozenymi zavorkami.
 *
 *  @author Miroslav.Benes@vsb.cz
 */
public final class GrammarReader
{
	/** Vytvori instanci analyzatoru gramatiky
	 *  @param input Vstupni zdrojovy text
	 */
	public GrammarReader(Reader input)
	{
		inp = new LineNumberReader(input);
		inp.setLineNumber(1);
	}

	/** Nacte gramatiku z textove reprezentace.
	 *  @return Gramatika.
	 *  @throws GrammarException Chyba pri prekladu zdrojove reprezentace gramatiky.
	 *  @throws IOException Chyba pri praci se soubory.
	 */
	public Grammar read() throws GrammarException,IOException
	{
		GrammarImpl grammar = new GrammarImpl();

		ch = inp.read();
		int sym = getSym();

		while( sym != -1 ) {
			if( sym != SYM_NT )
				error("Na leve strane pravidla se ocekava nonterminal");
			Nonterminal lhs = grammar.addNonterminal(attr);
			if( grammar.getStartNonterminal() == null )
				grammar.setStartNonterminal(lhs);
			sym = getSym();
			if( sym != ':' )
				error("Ocekava se dvojtecka");
			do {
				sym = getSym();
				Rule rule = new Rule(lhs);

				while( sym == SYM_NT || sym == SYM_T ) {
					if( sym == SYM_NT ) {
						Nonterminal nt = grammar.addNonterminal(attr);
						rule.addSymbol(nt);
						sym = getSym();
					} else if( sym == SYM_T ) {
						Terminal t = grammar.addTerminal(attr);
						rule.addSymbol(t);
						sym = getSym();
					}
				}
				lhs.addRule(rule);
			} while( sym == '|' );
			if( sym != ';' )
				error("Ocekava se strednik");
			sym = getSym();
		}
		return grammar;
	}

	/** Hlaseni chyby pri prekladu
	 *  @param msg Text chyboveho hlaseni.
	 */
	private void error(String msg) throws GrammarException
	{
		throw new GrammarException(msg, inp.getLineNumber());
	}

	// Kody specialnich lexikalnich symbolu
	private static final int SYM_NT  = 'N';
	private static final int SYM_T   = 'T';
	private static final int SYM_EOF = -1;

	/** Lexikalni analyzator pro cteni gramatiky.
	 *  @return Kod nacteneho lexikalniho symbolu. Atribut symbolu je
	 *          ulozen v promenne attr.
	 *  @throws IOException Chyba pri praci se vstupnim souborem.
	 */
	private int getSym() throws IOException
	{
		// Preskocime mezery, konce radku a poznamky
		for(;;) {
			if( ch < 0 )
				return SYM_EOF;
			if( Character.isWhitespace((char)ch) )
				ch = inp.read();
			else if( ch == '{' ) {
				do {
					ch = inp.read();
				} while( ch >= 0 && ch != '}' );
				if( ch >= 0 )
					ch = inp.read();
			} else
				break;
		}

		// Identifikatory - jmena symbolu gramatiky
		if( Character.isLetter((char)ch) ) {
			StringBuffer buf = new StringBuffer();
			do {
				buf.append((char)ch);
				ch = inp.read();
			} while( ch > 0 && Character.isLetterOrDigit((char)ch) );
			attr = buf.toString();
			return Character.isLowerCase(attr.charAt(0)) ? SYM_T : SYM_NT;
		}


		// Ostatni jednoznakove symboly
		int sym = ch;
		ch = inp.read();
		return sym;
	}

	/** Zdrojovy text pro cteni gramatiky. */
	private LineNumberReader inp;

	/** Aktualni znak ve zdrojovem souboru. */
	private int ch;

	/** Atribut lexikalniho symbolu (pro identifikatory). */
	private String attr;
}
