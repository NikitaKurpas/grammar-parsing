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
public class PrintStatement extends Statement {

    ArrayList<Expression> expressions = new ArrayList<Expression>();

    public PrintStatement() {
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public void accept(IRVisitor visitor) {
        for (Expression e : expressions) {
            e.accept(visitor);
        }
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("print ");
        for (Expression e : expressions) {
            sb.append(e.toString() + ", ");
        }
        return sb.toString() + "\n";
    }

}
