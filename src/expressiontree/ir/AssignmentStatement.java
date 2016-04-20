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
public class AssignmentStatement extends Statement {
    private Variable variable;
    private Expression expression;

    public AssignmentStatement(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public void accept(IRVisitor visitor) {
        variable.accept(visitor);
        expression.accept(visitor);

        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return variable.toString()+" = "+expression.toString()+"\n";
    }


}
