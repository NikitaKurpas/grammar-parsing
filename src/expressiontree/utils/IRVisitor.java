/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expressiontree.utils;


import expressiontree.ir.*;

/**
 *
 * @author beh01
 */
public interface  IRVisitor {
    public void visit(AssignmentStatement st);
    public void visit(BinaryExpression exp);
    public void visit(BlockOfStatements st);
    public void visit(TernaryExpression exp);
    public void visit(IfStatement st);
    public void visit(Constant exp);
    public void visit(PrintStatement st);
    public void visit(ReadStatement st);
    public void visit(UnaryExpression exp);
    public void visit(Variable exp);
    public void visit(WhileStatement st);
}
