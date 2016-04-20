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
public class ForStatement extends Statement{
    private Statement initialization;
    private Expression condition;
    private Statement update;
    private BlockOfStatements body;

    public ForStatement(Statement initialization, Expression condition, Statement update, BlockOfStatements body) {
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    public BlockOfStatements getBody() {
        return body;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getUpdate() {
        return update;
    }

    public Statement getInitialization() {
        return initialization;
    }

    @Override
    public void accept(IRVisitor visitor) {
        initialization.accept(visitor);
        condition.accept(visitor);
        update.accept(visitor);
        body.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "for (" + initialization.toString() + " " +
                condition.toString() + "; " +
                update.toString() + ") {\n" +
                body.toString() +
                "}" +
                "\n";
    }
}
