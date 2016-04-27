package expressiontree.utils;

import expressiontree.ir.*;

/**
 * Created by nikit on 26-Apr-16
 */
public class TypeConvertingVisitor implements IRVisitor {

    private Type getType(BinaryExpression exp) {
        if (exp.getRight().getType().equals(Type.INT)) {
            if (exp.getLeft().getType().equals(Type.FLOAT)) {
                return Type.FLOAT;
            } else if (!exp.getLeft().getType().equals(Type.INT)) {
                return Type.ERROR;
            } else {
                return Type.INT;
            }
        } else if (exp.getLeft().getType().equals(Type.INT)) {
            if (exp.getRight().getType().equals(Type.FLOAT)) {
                return Type.FLOAT;
            } else if (!exp.getRight().getType().equals(Type.INT)) {
                return Type.ERROR;
            } else {
                return Type.INT;
            }
        } else if (!exp.getLeft().getType().equals(exp.getRight().getType())) {
            return Type.ERROR;
        } else {
            return exp.getLeft().getType();
        }
    }

    @Override
    public void visit(AssignmentStatement st) {
        if (st.getVariable().getType().equals(Type.FLOAT) && st.getExpression().getType().equals(Type.INT)) {
            st.getExpression().setType(Type.FLOAT);
        }
    }

    @Override
    public void visit(BinaryExpression exp) {
        if (exp.getOp().matches("(==|!=|<|>|<=|>=)")) {
            if (getType(exp).equals(Type.ERROR)) {
                exp.setType(Type.ERROR);
                exp.makeError("Incompatible types found");
            } else {
                exp.setType(Type.BOOLEAN);
            }
            return;
        }

        if (exp.getOp().matches("(\\*|/|%|\\+|-)")) {
            exp.setType(getType(exp));
            return;
        }

        if (exp.getOp().matches("(\\.)")) {
            if (exp.getLeft().getType().equals(Type.STRING) && exp.getRight().getType().equals(Type.STRING)) {
                exp.setType(Type.STRING);
            } else {
                exp.setType(Type.ERROR);
            }
            return;
        }

        exp.setType(Type.ERROR);
    }

    @Override
    public void visit(BlockOfStatements st) {
        ;
    }

    @Override
    public void visit(TernaryExpression exp) {
        exp.setType(getType(new BinaryExpression(exp.getLeftPart(), null, exp.getRightPart())));
    }

    @Override
    public void visit(IfStatement st) {
        ;
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
        if (exp.getOperator().matches("(!)")) {
            if (exp.getTarget().getType().equals(Type.BOOLEAN)) {
                exp.setType(Type.BOOLEAN);
            } else {
                exp.setType(Type.ERROR);
            }
            return;
        }

        if (exp.getOperator().matches("(\\+|-)")) {
            if (exp.getTarget().getType().equals(Type.INT) || exp.getTarget().getType().equals(Type.FLOAT)) {
                exp.setType(exp.getTarget().getType());
            } else {
                exp.setType(Type.ERROR);
            }
            return;
        }

        exp.setType(Type.ERROR);
    }

    @Override
    public void visit(Variable exp) {
        ;
    }

    @Override
    public void visit(WhileStatement st) {
        ;
    }

    @Override
    public void visit(ForStatement st) {
        ;
    }
}
