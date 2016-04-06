package grammar;

/** Vyjimka zpusobena chybou pri cteni gramatiky
 *
 *  @author Miroslav.Benes@vsb.cz
 */
public class GrammarException extends Exception
{
	/** Vytvori instanci vyjimky ohlasujici chybu pri prekladu
	 *  gramatiky.
	 *  @param msg Text chyboveho hlaseni.
	 *  @param lineNo Cislo zdrojoveho radku s chybou.
	 */
	public GrammarException(String msg, int lineNo)
	{
		super(msg);
		this.lineNumber = lineNo;
	}

	/** Vrati cislo radku, na kterem byla chyba nalezena
	 *  @return Cislo radku s chybou
	 */
	public int getLineNumber()
	{
		return lineNumber;
	}

	private int lineNumber;
}
