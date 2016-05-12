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
public class IfStatement extends Statement {

    private Expression condition;
    private BlockOfStatements thenPart, elsePart;

    public IfStatement(Expression condition, BlockOfStatements thenPart, BlockOfStatements elsePart) {
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    public Expression getCondition() {
        return condition;
    }

    public BlockOfStatements getElsePart() {
        return elsePart;
    }

    public BlockOfStatements getThenPart() {
        return thenPart;
    }

    @Override
    public void accept(IRVisitor visitor) {
        condition.accept(visitor);
        thenPart.accept(visitor);
        if (elsePart != null)
            elsePart.accept(visitor);

        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("if " + condition.toString() + " {\n");
        sb.append(thenPart.toString() + "}");
        if (elsePart != null) {
            sb.append("else {\n");
            sb.append(elsePart.toString());
            sb.append("}");
        }
        sb.append("\n");
        return sb.toString();
    }

}
