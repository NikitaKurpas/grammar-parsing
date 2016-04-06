package grammarnetbeans;

import grammar.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementace v�po�t� nad gramatikou
 */
public class GrammarOps {

    Map<Nonterminal, Collection<Symbol>> first;

    /**
     * Vytvori instanci objektu a provede vypocet
     */
    public GrammarOps(Grammar g) {
        this.g = g;
        first = new HashMap<>();
        compute_empty();
        prepareFirst();
        dump();
        solve(first);
        dump();
    }

    private void prepareFirst() {
        for (Rule rule : g.getRules()) {
            if (!first.containsKey(rule.getLHS())) {
                first.put(rule.getLHS(), new TreeSet<>());
            }

            Collection<Symbol> row = first.get(rule.getLHS());
            for (Symbol symbol : rule.getRHS()) {
                row.add(symbol);
                if (symbol instanceof Terminal) {
                    break;
                } else if (symbol instanceof Nonterminal && !emptyNonterminals.contains(symbol)) {
                    break;
                }
            }
        }
    }

    private void solve(Map<Nonterminal, Collection<Symbol>> data) {
        boolean isChanged = true;
        while (isChanged) {
            isChanged = false;
            for (Nonterminal nonterminal : data.keySet()) {
                Collection<Nonterminal> keys = data.get(nonterminal).stream()
                        .filter(symbol -> symbol instanceof Nonterminal && symbol != nonterminal)
                        .map(symbol -> (Nonterminal) symbol)
                        .collect(Collectors.toCollection(TreeSet::new));
                for (Nonterminal key : keys) {
                    int size = data.get(nonterminal).size();
                    data.get(nonterminal).addAll(data.get(key));
                    if (data.get(nonterminal).size() > size) {
                        isChanged = true;
                    }
                }
            }
        }
    }

    private void dump() {
        System.out.println("FIRST");
        first.forEach((nonterminal, symbols) -> {
            System.out.println(" " + nonterminal.getName() + " -> " + symbols.toString());
        });
    }

    public Collection<Terminal> computeFirst(Collection<Symbol> symbols) {
        List<Terminal> result = new ArrayList<>();
        for (Symbol symbol : symbols) {
            if (symbol instanceof Terminal) {
                result.add((Terminal) symbol);
                break;
            }
            if (symbol instanceof Nonterminal) {
                Collection<Symbol> firstSymbols = first.get(symbol);
                if (firstSymbols == null) {
                    continue;
                }
                result.addAll(
                        firstSymbols.stream()
                                .filter(s -> s instanceof Terminal)
                                .map(s -> (Terminal) s)
                                .collect(Collectors.toCollection(ArrayList::new))
                );
                if (!emptyNonterminals.contains(symbol)) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Vrati mnozinu nonterminalu generujicich prazdne slovo
     *
     * @return Mnozina symbolu typu Nonterminal.
     */
    public Set<Nonterminal> getEmptyNonterminals() {
        return emptyNonterminals;
    }

    /**
     * Vypocet mnoziny nonterminalu generujicich prazdne slovo.
     */
    private void compute_empty() {
        emptyNonterminals = new TreeSet<>();
        int size;
        do {
            size = emptyNonterminals.size();
            for (Rule rule : g.getRules()) {
                boolean test = true;
                for (Symbol symbol : rule.getRHS()) {
                    if (symbol instanceof Terminal || !emptyNonterminals.contains(symbol)) {
                        test = false;
                    }
                }
                if (test) {
                    emptyNonterminals.add(rule.getLHS());
                }
            }
        } while (size != emptyNonterminals.size());
    }

    /**
     * Gramatika
     */
    Grammar g;
    /**
     * Mnozina nonterminalu generujicich prazdny retezec
     */
    Set<Nonterminal> emptyNonterminals;
}
