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
public class TernaryExpression extends Expression{
    private Expression condition, leftPart, rightPart;

    public TernaryExpression(Expression condition, Expression leftPart, Expression rightPart) {
        this.condition = condition;
        this.leftPart = leftPart;
        this.rightPart = rightPart;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getLeftPart() {
        return leftPart;
    }

    public Expression getRightPart() {
        return rightPart;
    }

    @Override
    public void accept(IRVisitor visitor) {
        condition.accept(visitor);
        leftPart.accept(visitor);
        rightPart.accept(visitor);

        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return "("+condition.toString()+")?"+leftPart.toString()+":"+rightPart.toString();
    }


}
