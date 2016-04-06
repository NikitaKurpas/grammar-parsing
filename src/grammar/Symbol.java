package grammar;

import java.util.Objects;

/** Spolecna bazova trida pro neterminalni a terminalni symboly.
 *
 *  @author Miroslav.Benes@vsb.cz
 */
public abstract class Symbol implements Comparable
{
	/** Vytvori instanci symbolu gramatiky.
	 *  @param name Jmeno symbolu.
	 */
	public Symbol(String name)
	{
		this.name = name;
	}

	/** Vrati jmeno symbolu
	 *  @return Jmeno symbolu.
	 */
	public String getName()
	{
		return name;
	}

	/** Porovna dva symboly podle jmena
	 *  @param obj Objekt, s nimz se porovnava. Musi to byt hodnota kompatibilni s typem Symbol.
	 *  @return Vrati vysledek porovnani jmen obou symbolu jako hodnotu <, = nebo > 0.
	 */
	public int compareTo(Object obj)
	{
		return name.compareTo(((Symbol)obj).name);
	}

	/** Jmeno symbolu. */
	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Symbol symbol = (Symbol) o;
		return Objects.equals(name, symbol.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return name;
	}
}
