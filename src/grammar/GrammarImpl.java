package grammar;

import java.util.*;
import java.io.*;

/** Implementace bezkontextove gramatiky.
 *
 *  @author Miroslav.Benes@vsb.cz
 */
public class GrammarImpl implements Grammar
{
	/** Umoznuje pruchod pres vsechny terminalni symboly.
	 *  @return Iterator pres vsechny terminalni symboly.
	 */
	public Collection<Terminal> getTerminals()
	{
		return terminals.values();
	}

	/** Zarazeni noveho terminalniho symbolu do seznamu. Pokud
	 *  uz v seznamu symbol existuje, zarazeni se neprovede.
	 *  @param name Jmeno symbolu.
	 *  @return Terminalni symbol.
	 */
	public Terminal addTerminal(String name)
	{
		Terminal t = (Terminal)terminals.get(name);
		if( t == null ) {
			t = new Terminal(name);
			terminals.put(name, t);
		}
		return t;
	}

	/** Umoznuje pruchod pres vsechny nonterminalni symboly.
	 *  @return Iterator pres vsechny nonterminalni symboly.
	 */
	public Collection<Nonterminal> getNonterminals()
	{
		return nonterminals.values();
	}

	/** Zarazeni noveho neterminalniho symbolu do seznamu. Pokud
	 *  uz v seznamu symbol existuje, zarazeni se neprovede.
	 *  @param name Jmeno symbolu.
	 *  @return Neterminalni symbol.
	 */
	public Nonterminal addNonterminal(String name)
	{
		Nonterminal nt = (Nonterminal)nonterminals.get(name);
		if( nt == null ) {
			nt = new Nonterminal(name);
			nonterminals.put(name, nt);
		}
		return nt;
	}

	/** Umozni pruchod pres vsechna pravidla gramatiky.
	 *  @return Iterator pres vsechna pravidla gramatiky.
	 */
	public Collection<Rule> getRules()
	{
            ArrayList<Rule> rules= new ArrayList<Rule>();

            for (Nonterminal nt : this.getNonterminals()) {
                for(Rule r : nt.getRules()) {
                    rules.add(r);
                }
            }
            return rules;
	}

	/** Prida dalsi pravidlo do gramatiky.
	 *  @param rule Pridavane pravidlo.
	 */
	public void addRule(Rule rule)
	{
		rule.getLHS().addRule(rule);
	}

	/** Vrati startovaci nonterminal.
	 *  @return Startovaci nonterminal.
	 */
	public Nonterminal getStartNonterminal()
	{
		return startNonterminal;
	}

	/** Vrati startovaci nonterminal.
	 *  @param start Startovaci nonterminal.
	 */
	public void setStartNonterminal(Nonterminal start)
	{
		startNonterminal = start;
	}

	/** Vypis gramatiky na vystup.
	 *  @param out Vystupni stream.
	 */
	public void dump(PrintStream out)
	{
		out.print("Terminalni symboly:");
		for (Terminal t : getTerminals()) {
			out.print(" "+t.getName());
		}
		out.println();

		out.print("Neterminalni symboly:");
		for ( Nonterminal nt : getNonterminals()) {
			out.print(" "+nt.getName());
		}
		out.println();

		out.println("Startovaci nonterminal: "+getStartNonterminal().getName());

		out.println("Pravidla:");
		int i=0;
		for( Rule rule : getRules()) {
                        i++;
			out.print("[" + i + "] " + rule.getLHS().getName()+" -> ");

			for(Symbol s : rule.getRHS()){
				out.print(s.getName()+" ");
			}
			out.println();
		}

	}

	/** Seznam terminalnich symbolu. */
	private Map<String,Terminal> terminals = new TreeMap<String, Terminal>();

	/** Seznam neterminalnich symbolu. */
	private Map<String, Nonterminal> nonterminals = new TreeMap<String, Nonterminal>();

	/** Startovaci nonterminal. */
	private Nonterminal startNonterminal;
}
