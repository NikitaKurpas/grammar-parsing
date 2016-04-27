/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package expressiontree.utils;

import expressiontree.ir.*;

/**
 *
 * @author beh01
 */
public class TypeCheckingVisitor implements IRVisitor{

    @Override
    public void visit(AssignmentStatement st) {
        if (st.getExpression().getType().equals(Type.ERROR)) {
            st.makeError("Expression type error");
        }
        if (!st.getExpression().getType().equals(st.getVariable().getType())) {
            st.makeError("Variable/expression type mismatch: " + st.getVariable().getType() + "/" + st.getExpression().getType());
        }
    }

    @Override
    public void visit(BinaryExpression exp) {
        if (exp.getType().equals(Type.ERROR)) {
            exp.makeError("Expression type error");
        }
    }

    @Override
    public void visit(BlockOfStatements st) {
        ;
    }

    @Override
    public void visit(TernaryExpression exp) {
        if (!exp.getCondition().getType().equals(Type.BOOLEAN)) {
            exp.makeError("Ternary expression condition must be of type BOOLEAN, found " + exp.getCondition().getType());
        }

        if (!exp.getLeftPart().getType().equals(exp.getRightPart().getType())) {
            exp.makeError("Ternary expression left/right part type mismatch: " + exp.getLeftPart().getType() + "/" + exp.getRightPart().getType());
        }
    }

    @Override
    public void visit(IfStatement st) {
        if (!st.getCondition().getType().equals(Type.BOOLEAN)) {
            st.makeError("If statement condition must be of type BOOLEAN, found " + st.getCondition().getType());
        }
    }

    @Override
    public void visit(Constant exp) {
        ;
    }

    @Override
    public void visit(PrintStatement st) {
        ;
    }

    @Override
    public void visit(ReadStatement st) {
        ;
    }

    @Override
    public void visit(UnaryExpression exp) {
        if (exp.getType().equals(Type.ERROR)) {
            exp.makeError("Unary expression type mismatch");
        }
    }

    @Override
    public void visit(Variable exp) {
        ;
    }

    @Override
    public void visit(WhileStatement st) {
        if (!st.getCondition().getType().equals(Type.BOOLEAN)) {
            st.makeError("While statement condition must be of type BOOLEAN, found " + st.getCondition().getType());
        }
    }

    @Override
    public void visit(ForStatement st) {
        if (!st.getCondition().getType().equals(Type.BOOLEAN)) {
            st.makeError("For statement condition must be of type BOOLEAN, found " + st.getCondition().getType());
        }
    }
}
