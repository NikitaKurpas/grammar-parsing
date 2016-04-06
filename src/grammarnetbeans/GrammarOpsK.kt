package grammarnetbeans

import grammar.Grammar
import grammar.Nonterminal
import grammar.Symbol
import grammar.Terminal
import java.util.*

class GrammarOpsK(val g: Grammar) {
    val first : MutableMap<Nonterminal, MutableCollection<Symbol>> = HashMap();
    val emptyNonterminals : Set<Nonterminal> = TreeSet();

    init {
        compute_empty();
        prepareFirst();
        dump();
        solve(first);
        dump();
    }

    private fun prepareFirst() {
        for (rule in g.rules) {
            if (!first.containsKey(rule.lhs)) {
                first.put(rule.lhs, TreeSet<Symbol>())
            }

            val row = first[rule.lhs]
            for (symbol in rule.rhs) {
                row?.add(symbol)
                if (symbol is Terminal) {
                    break
                } else if (symbol is Nonterminal && !emptyNonterminals.contains(symbol)) {
                    break
                }
            }
        }
    }

    private fun solve(data: Map<Nonterminal, Collection<Symbol>>) {
//        var isChanged = true
//        while (isChanged) {
//            isChanged = false
//            for (nonterminal in data.keys) {
//                if (data[nonterminal] === null) {
//                    return;
//                }
//                val keys = data[nonterminal].filter({ symbol -> symbol is Nonterminal && symbol !== nonterminal }).map({ symbol -> symbol as Nonterminal })
//                for (key in keys) {
//                    val size = data[nonterminal]?.size ?: 0
//                    data[nonterminal].addAll(data[key])
//                    if (data[nonterminal]?.size > size) {
//                        isChanged = true
//                    }
//                }
//            }
//        }
    }

    private fun dump() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun compute_empty() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}