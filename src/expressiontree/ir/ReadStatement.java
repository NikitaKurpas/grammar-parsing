/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressiontree.ir;

import expressiontree.utils.IRVisitor;

import java.util.ArrayList;

/**
 *
 * @author beh01
 */
public class ReadStatement extends Statement {
    private ArrayList<Variable> variables = new ArrayList<Variable>();

    public ReadStatement() {
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    @Override
    public void accept(IRVisitor visitor) {
        for(Variable v: variables) {
            v.accept(visitor);
        }
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("read ");
        for(Variable v : variables) {
            sb.append(v.toString()+", ");
        }
        return sb.toString()+"\n";
    }


}
