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
public class WhileStatement extends Statement{
    private Expression condition;
    private BlockOfStatements body;

    public WhileStatement(Expression condition, BlockOfStatements body) {
        this.condition = condition;
        this.body = body;
    }

    public BlockOfStatements getBody() {
        return body;
    }

    public Expression getCondition() {
        return condition;
    }

    @Override
    public void accept(IRVisitor visitor) {
        condition.accept(visitor);
        body.accept(visitor);
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("while " + condition.toString() + " {\n");
        sb.append(body.toString());
        sb.append("}");
        sb.append("\n");
        return sb.toString();
    }


}
