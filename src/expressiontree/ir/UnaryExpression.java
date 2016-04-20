/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressiontree.ir;

import expressiontree.utils.IRVisitor;

/**
 *
 * @author beh01
 */
public class UnaryExpression extends Expression{
    private String operator;
    private Expression target;

    public UnaryExpression(String operator, Expression target) {
        this.operator = operator;
        this.target = target;
    }

    public String getOperator() {
        return operator;
    }

    public Expression getTarget() {
        return target;
    }

    @Override
    public void accept(IRVisitor visitor) {
        target.accept(visitor);
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return operator+target.toString();
    }

}
