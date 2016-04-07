package grammarnetbeans;

import grammar.*;
import java.util.*;

/** Implementace v�po�t� nad gramatikou
 */
public class GrammarOps {

    /** Vytvori instanci objektu a provede vypocet */
    public GrammarOps(Grammar g) {
        this.g = g;
        compute_empty();
        compute_first_table();
        compute_follow_table();
    }

    /** Vrati mnozinu nonterminalu generujicich prazdne slovo
     *  @return Mnozina symbolu typu Nonterminal.
     */
    public Set<Nonterminal> getEmptyNonterminals() {
        return emptyNonterminals;
    }

    public Set<Terminal> first(Collection<Symbol> rhs) {
        TreeSet<Terminal> result = new TreeSet<Terminal>();
        Set<Symbol> first = simulateFirst(rhs);
        for (Symbol s : first) {
            if (s instanceof Terminal) {
                result.add((Terminal) s);
            } else {
                for (Symbol added : firstTable.get((Nonterminal) s)) {
                    result.add((Terminal) added);
                }
            }
        }
        return result;
    }

    /** Vypocet mnoziny nonterminalu generujicich prazdne slovo.
     */
    private void compute_empty() {
        emptyNonterminals = new TreeSet<Nonterminal>();
        //TODO: Doplnit zde
        int size;
        do {
            size = emptyNonterminals.size();

            for (Rule r : g.getRules()) {
                boolean empty = true;
                for (Symbol s : r.getRHS()) {
                    if (s instanceof Terminal) {
                        empty = false;
                    } else {
                        if (!emptyNonterminals.contains(s)) {
                            empty = false;
                        }
                    }
                }
                if (empty) {
                    emptyNonterminals.add(r.getLHS());
                }
            }
        } while (size != emptyNonterminals.size());
    }
    /** Gramatika */
    Grammar g;
    /** Mnozina nonterminalu generujicich prazdny retezec */
    Set<Nonterminal> emptyNonterminals;
    Terminal epsilon = new Terminal("epsilon");
    TreeMap<Nonterminal, Set<Symbol>> firstTable = new TreeMap<Nonterminal, Set<Symbol>>();
    TreeMap<Nonterminal, Set<Symbol>> followTable = new TreeMap<Nonterminal, Set<Symbol>>();

    private Set<Symbol> simulateFirst(Collection<Symbol> rhs) {
        TreeSet<Symbol> result = new TreeSet<Symbol>();
        for (Symbol s : rhs) {
            result.add(s);
            if (s instanceof Terminal) {
                return result;
            } else {
                if (!emptyNonterminals.contains(s)) {
                    return result;
                }
            }
        }
        result.add(epsilon);
        return result;
    }

    private Set<Symbol> simulateFollow(Nonterminal n) {
        Set<Symbol> result = new TreeSet<Symbol>();
        for (Rule r : g.getRules()) {
            Object[] data = r.getRHS().toArray();
            for (int i = 0; i < data.length; i++) {
                if (data[i] == n) {
                    ArrayList<Symbol> rest = new ArrayList<Symbol>();
                    for (int j = i + 1; j < data.length; j++) {
                        rest.add((Symbol) data[j]);
                    }
                    result.addAll(first(rest));
                }
            }
            if (result.contains(epsilon)) {
                result.remove(epsilon);
                result.add(r.getLHS());
            }
        }
        return result;
    }

    private void compute_first_table() {
        for (Nonterminal n : g.getNonterminals()) {
            firstTable.put(n, new TreeSet<Symbol>());
            for (Rule r : n.getRules()) {
                Set<Symbol> oneFirst = simulateFirst(r.getRHS());
                oneFirst.remove(epsilon);
                firstTable.get(n).addAll(oneFirst);
            }
        }
        makeClosure(firstTable);
        cleanTable(firstTable);
    }

    private void cleanTable(TreeMap<Nonterminal, Set<Symbol>> table) {
        for (Nonterminal n : table.keySet()) {
            table.get(n).removeAll(g.getNonterminals());
        }
    }

    private void makeClosure(TreeMap<Nonterminal, Set<Symbol>> table) {
        boolean repeat;
        do {
            repeat = false;

            for (Nonterminal n : table.keySet()) {
                Set<Symbol> tmp = new TreeSet<Symbol>();
                for (Symbol s : table.get(n)) {
                    if (s instanceof Nonterminal) {
                        tmp.addAll(table.get(s));
                    }
                }
                int size = table.get(n).size();
                table.get(n).addAll(tmp);
                repeat |= size != table.get(n).size();
            }
        } while (repeat);
    }

    private void compute_follow_table() {
        for (Nonterminal n : g.getNonterminals()) {
            followTable.put(n, simulateFollow(n));
        }
        followTable.get(g.getStartNonterminal()).add(epsilon);
        makeClosure(followTable);
        cleanTable(followTable);
    }

    Set<Terminal> follow(Nonterminal n) {
        Set<Terminal> result = new TreeSet<Terminal>();
        for (Symbol s : followTable.get(n)) {
            result.add((Terminal) s);
        }
        return result;
    }

    public boolean isLL1() {
        for (Nonterminal n : g.getNonterminals()) {
            int count=0;
            Set<Terminal> all=new TreeSet<Terminal>();
            for (Rule r : n.getRules()) {
                Set<Terminal> select = first(r.getRHS());
                if (select.contains(epsilon)) {
                    select.remove(epsilon);
                    select.addAll(follow(n));
                }
                count+=select.size();
                all.addAll(select);
            }
            if (count!=all.size()) return false;
        }
        return true;
    }
}
